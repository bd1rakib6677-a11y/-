package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UserEntity::class, TournamentEntity::class, RegistrationEntity::class, PaymentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val appDao: AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "booyah_arena_database"
                )
                .addCallback(DatabaseCallback())
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        prepulateData(database.appDao)
                    }
                }
            }

            private suspend fun prepulateData(dao: AppDao) {
                // 1. Insert default admin
                dao.insertUser(
                    UserEntity(
                        email = "bd1admin@gmail.com",
                        passwordHash = "BOOYAH_admin_2026",
                        fullName = "Super Admin Shanto",
                        phone = "01711223344",
                        ffUid = "555555",
                        ffIgn = "BOOYAH_ADMIN_OP",
                        balance = 99999.0,
                        isAdmin = true
                    )
                )

                // 2. Insert standard player
                dao.insertUser(
                    UserEntity(
                        id = 2,
                        email = "bd1rakib6677@gmail.com", // Pre-creating the user's email for a custom welcoming experience!
                        passwordHash = "rakib123",
                        fullName = "Rakib Hossain",
                        phone = "01999887766",
                        ffUid = "8472947192",
                        ffIgn = "RAKIB_FF_OP",
                        balance = 350.0,
                        isAdmin = false
                    )
                )

                // 3. Insert pre-loaded tournaments
                // -- Tournament 1: Upcoming Game (Joinable)
                dao.insertTournament(
                    TournamentEntity(
                        id = 1,
                        title = "Dhaka Weekly Solo Cup",
                        date = "2026-06-10",
                        time = "08:00 PM",
                        map = "Bermuda",
                        gameMode = "Solo",
                        entryFee = 30.0,
                        prizePool = 1200.0,
                        booyahPrize = 500.0,
                        perKillPrize = 10.0,
                        roomId = "",
                        roomPassword = "",
                        maxPlayers = 48,
                        status = "UPCOMING"
                    )
                )

                // -- Tournament 2: Ongoing / Starting Soon (Already registered and showing Room Details to registered users)
                val t2Id = dao.insertTournament(
                    TournamentEntity(
                        id = 2,
                        title = "Sunday Duo Showdown",
                        date = "2026-06-07",
                        time = "09:30 PM",
                        map = "Purgatory",
                        gameMode = "Duo",
                        entryFee = 50.0,
                        prizePool = 2000.0,
                        booyahPrize = 1000.0,
                        perKillPrize = 15.0,
                        roomId = "4820942",
                        roomPassword = "booyahwinner",
                        maxPlayers = 24,
                        status = "ONGOING"
                    )
                ).toInt()

                // Register Rakib for Sunday Duo to demonstrate how Active status showing room details behaves
                dao.insertRegistration(
                    RegistrationEntity(
                        tournamentId = t2Id,
                        userEmail = "bd1rakib6677@gmail.com",
                        ffUid = "8472947192",
                        ffIgn = "RAKIB_FF_OP"
                    )
                )

                // -- Tournament 3: Completed Game (Shows leaderboard)
                val t3Id = dao.insertTournament(
                    TournamentEntity(
                        id = 3,
                        title = "Championship Squad Battle",
                        date = "2026-06-01",
                        time = "06:00 PM",
                        map = "Bermuda (Remastered)",
                        gameMode = "Squad",
                        entryFee = 100.0,
                        prizePool = 5000.0,
                        booyahPrize = 2500.0,
                        perKillPrize = 25.0,
                        roomId = "998234",
                        roomPassword = "closed",
                        maxPlayers = 12,
                        status = "COMPLETED"
                    )
                ).toInt()

                // Insert standings for Completed Tournament
                dao.insertRegistration(
                    RegistrationEntity(
                        tournamentId = t3Id,
                        userEmail = "bd1rakib6677@gmail.com",
                        ffUid = "8472947192",
                        ffIgn = "RAKIB_FF_OP",
                        rank = 1,
                        kills = 8,
                        rewardEarned = 2700.0, // BOOYAH 2500 + 8 * 25 = 2700
                        claimed = true
                    )
                )
                dao.insertRegistration(
                    RegistrationEntity(
                        tournamentId = t3Id,
                        userEmail = "pro_gamer_bd@gmail.com",
                        ffUid = "1029384756",
                        ffIgn = "BD_Cyclone",
                        rank = 2,
                        kills = 5,
                        rewardEarned = 125.0, // 5 * 25 = 125
                        claimed = true
                    )
                )
                dao.insertRegistration(
                    RegistrationEntity(
                        tournamentId = t3Id,
                        userEmail = "shagor_boss@gmail.com",
                        ffUid = "9283746152",
                        ffIgn = "Boss_Shagor",
                        rank = 3,
                        kills = 12,
                        rewardEarned = 300.0, // 12 * 25 = 300
                        claimed = true
                    )
                )

                // Prepopulate 1 pending cash request for Demo
                dao.insertPayment(
                    PaymentEntity(
                        userEmail = "bd1rakib6677@gmail.com",
                        type = "DEPOSIT",
                        method = "bKash",
                        amount = 400.0,
                        phone = "01999887766",
                        transactionId = "BK847J9D6T",
                        status = "PENDING"
                    )
                )
            }
        }
    }
}
