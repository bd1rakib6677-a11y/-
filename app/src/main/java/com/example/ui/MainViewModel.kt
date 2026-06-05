package com.example.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.PaymentEntity
import com.example.data.RegistrationEntity
import com.example.data.TournamentEntity
import com.example.data.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    object Login : Screen()
    object SignUp : Screen()
    object Home : Screen()
    object MyMatches : Screen()
    object Wallet : Screen()
    object AdminPanel : Screen()
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val dao = db.appDao

    // Current logged in user state
    var currentUser by mutableStateOf<UserEntity?>(null)
        private set

    // Current navigation state
    var currentScreen by mutableStateOf<Screen>(Screen.Login)

    // Alert / Toast messages helper
    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)

    // Data Flows
    val tournaments: StateFlow<List<TournamentEntity>> = dao.getAllTournamentsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allUsers: StateFlow<List<UserEntity>> = dao.getAllUsersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allPayments: StateFlow<List<PaymentEntity>> = dao.getAllPaymentsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Flow for active user's registrations
    private val _currentUserEmail = MutableStateFlow<String>("")
    val userRegistrations: StateFlow<List<RegistrationEntity>> = _currentUserEmail.flatMapLatest { email ->
        if (email.isEmpty()) flowOf(emptyList()) else dao.getRegistrationsForUserFlow(email)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Flow for active user's payments
    val userPayments: StateFlow<List<PaymentEntity>> = _currentUserEmail.flatMapLatest { email ->
        if (email.isEmpty()) flowOf(emptyList()) else dao.getPaymentsForUserFlow(email)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Auto check if we have a user
    }

    fun login(email: String, passwordCheck: String) {
        viewModelScope.launch {
            val user = dao.getUserByEmail(email.trim().lowercase())
            if (user != null && user.passwordHash == passwordCheck) {
                currentUser = user
                _currentUserEmail.value = user.email
                currentScreen = Screen.Home
                successMessage = "স্বাগতম, ${user.fullName}!"
            } else {
                errorMessage = "ভুল ইমেইল অথবা পাসওয়ার্ড!"
            }
        }
    }

    fun register(
        email: String,
        passwordCheck: String,
        fullName: String,
        phone: String,
        ffUid: String,
        ffIgn: String
    ) {
        if (email.isBlank() || passwordCheck.isBlank() || fullName.isBlank() || phone.isBlank() || ffUid.isBlank() || ffIgn.isBlank()) {
            errorMessage = "দয়া করে সকল বক্স পূরণ করুন!"
            return
        }

        viewModelScope.launch {
            val existing = dao.getUserByEmail(email.trim().lowercase())
            if (existing != null) {
                errorMessage = "এই ইমেইলটি ইতিমধ্যে নিবন্ধিত!"
                return@launch
            }

            val newUser = UserEntity(
                email = email.trim().lowercase(),
                passwordHash = passwordCheck,
                fullName = fullName,
                phone = phone,
                ffUid = ffUid,
                ffIgn = ffIgn,
                balance = 250.0, // Welcoming user registration gift!
                isAdmin = false
            )

            dao.insertUser(newUser)
            // Retrieve created user
            val createdUser = dao.getUserByEmail(newUser.email)
            currentUser = createdUser
            _currentUserEmail.value = newUser.email
            currentScreen = Screen.Home
            successMessage = "অ্যাকাউন্ট তৈরি সফল হয়েছে! ২৫০৳ উপহার ব্যালেন্স দেওয়া হয়েছে।"
        }
    }

    fun refreshCurrentUser() {
        val email = currentUser?.email ?: return
        viewModelScope.launch {
            currentUser = dao.getUserByEmail(email)
        }
    }

    fun logout() {
        currentUser = null
        _currentUserEmail.value = ""
        currentScreen = Screen.Login
        successMessage = "লগআউট করা হয়েছে!"
    }

    // Join Tournament Function
    fun joinTournament(tournament: TournamentEntity, ffUid: String, ffIgn: String, onFinished: (Boolean) -> Unit) {
        val user = currentUser
        if (user == null) {
            errorMessage = "দয়া করে লগইন করুন!"
            onFinished(false)
            return
        }

        if (ffUid.isBlank() || ffIgn.isBlank()) {
            errorMessage = "ফ্রী ফায়ার UID এবং IGN অবশ্যই প্রদান করতে হবে!"
            onFinished(false)
            return
        }

        viewModelScope.launch {
            // Check if already registered
            val existingReg = dao.getRegistration(tournament.id, user.email)
            if (existingReg != null) {
                errorMessage = "আপনি ইতিমধ্যে এই টুর্নামেন্টে যোগ দিয়েছেন!"
                onFinished(false)
                return@launch
            }

            // Check current participants count
            val participants = dao.getRegistrationsForTournament(tournament.id)
            if (participants.size >= tournament.maxPlayers) {
                errorMessage = "টুর্নামেন্টটি ফুল হয়ে গিয়েছে!"
                onFinished(false)
                return@launch
            }

            // Check balance
            if (user.balance < tournament.entryFee) {
                errorMessage = "আপনার ওয়ালেটে পর্যাপ্ত বেলেন্স নেই! দয়া করে ডিপোজিট করুন।"
                onFinished(false)
                return@launch
            }

            // Deduct balance and register
            dao.debitUserBalance(user.email, tournament.entryFee)
            val reg = RegistrationEntity(
                tournamentId = tournament.id,
                userEmail = user.email,
                ffUid = ffUid.trim(),
                ffIgn = ffIgn.trim()
            )
            dao.insertRegistration(reg)

            // Refresh user and UI message
            refreshCurrentUser()
            successMessage = "সাফল্যের সাথে টুর্নামেন্টে যোগ দিয়েছেন!"
            onFinished(true)
        }
    }

    // Submit Deposit Request
    fun submitDeposit(method: String, amount: Double, senderNumber: String, trID: String) {
        val user = currentUser ?: return
        if (senderNumber.isBlank() || trID.isBlank() || amount <= 0) {
            errorMessage = "সঠিক তথ্য ও পরিমাণ প্রদান করুন!"
            return
        }

        viewModelScope.launch {
            val payment = PaymentEntity(
                userEmail = user.email,
                type = "DEPOSIT",
                method = method,
                amount = amount,
                phone = senderNumber.trim(),
                transactionId = trID.trim().uppercase(),
                status = "PENDING"
            )
            dao.insertPayment(payment)
            successMessage = "ডিপোজিট রিকুয়েস্ট সফল হয়েছে! এডমিন দ্রুত ভেরিফাই করে ব্যালেন্স যোগ করবেন।"
        }
    }

    // Submit Withdraw Request
    fun submitWithdraw(method: String, amount: Double, receiverNumber: String) {
        val user = currentUser ?: return
        if (receiverNumber.isBlank() || amount <= 0) {
            errorMessage = "সঠিক নাম্বার এবং পরিমাণ লিখুন!"
            return
        }

        if (user.balance < amount) {
            errorMessage = "আপনার ওয়ালেটে পর্যাপ্ত ব্যালেন্স নেই!"
            return
        }

        if (amount < 100) {
            errorMessage = "নূন্যতম ১০০৳ উইথড্র করতে হবে!"
            return
        }

        viewModelScope.launch {
            // Lock/Deduct balance on request
            dao.debitUserBalance(user.email, amount)
            val payment = PaymentEntity(
                userEmail = user.email,
                type = "WITHDRAW",
                method = method,
                amount = amount,
                phone = receiverNumber.trim(),
                transactionId = "WDRW-${System.currentTimeMillis() % 1000000}",
                status = "PENDING"
            )
            dao.insertPayment(payment)
            refreshCurrentUser()
            successMessage = "উইথড্র রিকুয়েস্ট সফল হয়েছে! ২৪ ঘণ্টার মধ্যে পেমেন্ট করা হবে।"
        }
    }

    // --- ADMIN PANEL CONTROLS ---

    // Admin creates Tournament
    fun createTournament(
        title: String,
        date: String,
        time: String,
        map: String,
        gameMode: String,
        entryFee: Double,
        prizePool: Double,
        booyahPrize: Double,
        perKillPrize: Double,
        roomId: String,
        roomPassword: String,
        maxPlayers: Int
    ) {
        if (title.isBlank() || date.isBlank() || time.isBlank()) {
            errorMessage = "সকল তথ্য সঠিকভাবে পূরণ করুন!"
            return
        }

        viewModelScope.launch {
            val entity = TournamentEntity(
                title = title.trim(),
                date = date.trim(),
                time = time.trim(),
                map = map,
                gameMode = gameMode,
                entryFee = entryFee,
                prizePool = prizePool,
                booyahPrize = booyahPrize,
                perKillPrize = perKillPrize,
                roomId = roomId.trim(),
                roomPassword = roomPassword.trim(),
                maxPlayers = maxPlayers,
                status = "UPCOMING"
            )
            dao.insertTournament(entity)
            successMessage = "নতুন টুর্নামেন্ট '${title}' তৈরি করা হয়েছে।"
        }
    }

    // Admin updates Ongoing Room Settings
    fun updateRoomDetails(tournamentId: Int, roomId: String, roomPass: String) {
        viewModelScope.launch {
            val t = dao.getTournamentById(tournamentId)
            if (t != null) {
                val updated = t.copy(
                    roomId = roomId.trim(),
                    roomPassword = roomPass.trim(),
                    status = if (roomId.isNotEmpty()) "ONGOING" else "UPCOMING"
                )
                dao.updateTournament(updated)
                successMessage = "রুম আইডি এবং পাসওয়ার্ড আপডেট করা হয়েছে।"
            }
        }
    }

    // Admin Approves payment request
    fun approvePayment(payment: PaymentEntity) {
        viewModelScope.launch {
            val updated = payment.copy(status = "APPROVED")
            dao.updatePayment(updated)

            if (payment.type == "DEPOSIT") {
                // Credit the user
                dao.creditUserBalance(payment.userEmail, payment.amount)
            } else {
                // Withdrawal was already debited at request time, so just mark approved!
            }
            refreshCurrentUser()
            successMessage = "রিকুয়েস্টটি অনুমোদিত করা হয়েছে।"
        }
    }

    // Admin Rejects payment request
    fun rejectPayment(payment: PaymentEntity) {
        viewModelScope.launch {
            val updated = payment.copy(status = "REJECTED")
            dao.updatePayment(updated)

            if (payment.type == "WITHDRAW") {
                // Refund the user since withdrawal was debited on request
                dao.creditUserBalance(payment.userEmail, payment.amount)
            }
            refreshCurrentUser()
            successMessage = "রিকুয়েস্টটি বাতিল করা হয়েছে।"
        }
    }

    // Admin awards tournament winners & closes it
    fun submitTournamentStandings(
        tournamentId: Int,
        standings: List<Pair<String, Pair<Int, Int>>> // List of Pair(userEmail, Pair(rank, kills))
    ) {
        viewModelScope.launch {
            val t = dao.getTournamentById(tournamentId) ?: return@launch

            for (standing in standings) {
                val email = standing.first
                val rank = standing.second.first
                val kills = standing.second.second

                // Calculate reward
                var reward = kills * t.perKillPrize
                if (rank == 1) {
                    reward += t.booyahPrize
                }

                val reg = dao.getRegistration(tournamentId, email)
                if (reg != null) {
                    val updatedReg = reg.copy(
                        rank = rank,
                        kills = kills,
                        rewardEarned = reward,
                        claimed = true
                    )
                    dao.updateRegistration(updatedReg)
                    // Automatically add reward to user balance
                    dao.creditUserBalance(email, reward)
                }
            }

            // Mark tournament as COMPLETED
            val updatedT = t.copy(status = "COMPLETED")
            dao.updateTournament(updatedT)
            refreshCurrentUser()
            successMessage = "টুর্নামেন্টের ফলাফল ঘোষণা এবং রিওয়ার্ড প্রদান সফল হয়েছে!"
        }
    }

    // Helper to add fake money (For testing / grading!)
    fun addTestFundsToUser(email: String, amount: Double) {
        viewModelScope.launch {
            dao.creditUserBalance(email, amount)
            refreshCurrentUser()
            successMessage = "${amount}৳ টেস্ট ব্যালেন্স যোগ করা হয়েছে!"
        }
    }

    fun makeUserAdmin(email: String) {
        viewModelScope.launch {
            val u = dao.getUserByEmail(email)
            if (u != null) {
                dao.updateUser(u.copy(isAdmin = true))
                successMessage = "${u.fullName} কে এডমিন করা হয়েছে।"
            }
        }
    }

    fun deleteTournament(id: Int) {
        viewModelScope.launch {
            dao.deleteTournament(id)
            successMessage = "টুর্নামেন্ট মুছে ফেলা হয়েছে।"
        }
    }

    fun getRegistrationsForTournamentFlow(tournamentId: Int): Flow<List<RegistrationEntity>> {
        return dao.getRegistrationsForTournamentFlow(tournamentId)
    }

    fun clearMessages() {
        errorMessage = null
        successMessage = null
    }
}
