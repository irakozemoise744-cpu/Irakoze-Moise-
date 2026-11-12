package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY createdAtMillis DESC")
    fun observeAllBookings(): Flow<List<BookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity): Long

    @Query(
        """
        UPDATE bookings 
        SET paymentProvider = :provider,
            paymentStatus = :status,
            paymentAmountFrw = :amountFrw,
            paymentReference = :reference
        WHERE id = :bookingId
        """
    )
    suspend fun updatePaymentDetails(
        bookingId: Long,
        provider: String,
        status: String,
        amountFrw: Long,
        reference: String
    )

    @Query("DELETE FROM bookings WHERE id = :bookingId")
    suspend fun deleteBookingById(bookingId: Long)
}
