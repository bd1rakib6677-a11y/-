package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val passwordHash: String,
    val fullName: String,
    val phone: String,
    val ffUid: String,
    val ffIgn: String,
    val balance: Double = 500.0, // Starting bonus balance for demo/testing!
    val isAdmin: Boolean = false
)

@Entity(tableName = "tournaments")
data class TournamentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: String,
    val time: String,
    val map: String, // Bermuda, Purgatory, Kalahari
    val gameMode: String, // Solo, Duo, Squad
    val entryFee: Double,
    val prizePool: Double,
    val booyahPrize: Double,
    val perKillPrize: Double,
    val roomId: String = "",
    val roomPassword: String = "",
    val maxPlayers: Int = 48,
    val status: String = "UPCOMING" // UPCOMING, ONGOING, COMPLETED
)

@Entity(tableName = "registrations")
data class RegistrationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tournamentId: Int,
    val userEmail: String,
    val ffUid: String,
    val ffIgn: String,
    val rank: Int = 0,
    val kills: Int = 0,
    val rewardEarned: Double = 0.0,
    val claimed: Boolean = false
)

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val type: String, // DEPOSIT, WITHDRAW
    val method: String, // bKash, Nagad, Rocket
    val amount: Double,
    val phone: String,
    val transactionId: String,
    val status: String = "PENDING", // PENDING, APPROVED, REJECTED
    val timestamp: Long = System.currentTimeMillis()
)
