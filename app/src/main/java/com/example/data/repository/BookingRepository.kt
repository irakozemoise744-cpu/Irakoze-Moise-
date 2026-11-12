package com.example.data.repository

import com.example.data.local.BookingDao
import com.example.data.local.BookingEntity
import kotlinx.coroutines.flow.Flow

class BookingRepository(private val bookingDao: BookingDao) {
    val bookings: Flow<List<BookingEntity>> = bookingDao.observeAllBookings()

    suspend fun saveBooking(booking: BookingEntity): Long {
        return bookingDao.insertBooking(booking)
    }

    suspend fun recordPayment(
        bookingId: Long,
        provider: String,
        status: String,
        amountFrw: Long,
        reference: String
    ) {
        bookingDao.updatePaymentDetails(
            bookingId = bookingId,
            provider = provider,
            status = status,
            amountFrw = amountFrw,
            reference = reference
        )
    }

    suspend fun deleteBooking(bookingId: Long) {
        bookingDao.deleteBookingById(bookingId)
    }
}
