package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // --- USER QUERIES ---
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users")
    fun getAllUsersFlow(): Flow<List<UserEntity>>

    @Query("UPDATE users SET balance = balance + :amount WHERE email = :email")
    suspend fun creditUserBalance(email: String, amount: Double)

    @Query("UPDATE users SET balance = balance - :amount WHERE email = :email")
    suspend fun debitUserBalance(email: String, amount: Double)

    // --- TOURNAMENT QUERIES ---
    @Query("SELECT * FROM tournaments ORDER BY id DESC")
    fun getAllTournamentsFlow(): Flow<List<TournamentEntity>>

    @Query("SELECT * FROM tournaments WHERE id = :id LIMIT 1")
    suspend fun getTournamentById(id: Int): TournamentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTournament(tournament: TournamentEntity): Long

    @Update
    suspend fun updateTournament(tournament: TournamentEntity)

    @Query("DELETE FROM tournaments WHERE id = :id")
    suspend fun deleteTournament(id: Int)

    // --- REGISTRATION QUERIES ---
    @Query("SELECT * FROM registrations WHERE tournamentId = :tournamentId")
    fun getRegistrationsForTournamentFlow(tournamentId: Int): Flow<List<RegistrationEntity>>

    @Query("SELECT * FROM registrations WHERE tournamentId = :tournamentId")
    suspend fun getRegistrationsForTournament(tournamentId: Int): List<RegistrationEntity>

    @Query("SELECT * FROM registrations WHERE userEmail = :email")
    fun getRegistrationsForUserFlow(email: String): Flow<List<RegistrationEntity>>

    @Query("SELECT * FROM registrations WHERE tournamentId = :tournamentId AND userEmail = :email LIMIT 1")
    suspend fun getRegistration(tournamentId: Int, email: String): RegistrationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistration(registration: RegistrationEntity): Long

    @Update
    suspend fun updateRegistration(registration: RegistrationEntity)

    // --- PAYMENT QUERIES ---
    @Query("SELECT * FROM payments ORDER BY timestamp DESC")
    fun getAllPaymentsFlow(): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM payments WHERE userEmail = :email ORDER BY timestamp DESC")
    fun getPaymentsForUserFlow(email: String): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM payments WHERE id = :id LIMIT 1")
    suspend fun getPaymentById(id: Int): PaymentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: PaymentEntity): Long

    @Update
    suspend fun updatePayment(payment: PaymentEntity)
}
