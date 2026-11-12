package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val bookingRef: String,
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val serviceCategory: String,
    val eventDate: String,
    val location: String,
    val additionalMessage: String,
    val estimatedTotalFrw: Long = 0L,
    val paymentProvider: String = "",
    val paymentStatus: String = "PENDING",
    val paymentAmountFrw: Long = 0L,
    val paymentReference: String = "",
    val createdAtMillis: Long = System.currentTimeMillis()
)
