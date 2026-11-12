package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.BookingEntity
import com.example.data.local.MozzoDatabase
import com.example.data.repository.BookingRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var database: MozzoDatabase
    private lateinit var repository: BookingRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MozzoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = BookingRepository(database.bookingDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `read app_name from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Mozzo Studio", appName)
    }

    @Test
    fun `save and record payment in Room booking repository`() = runBlocking {
        val booking = BookingEntity(
            bookingRef = "MZZ-2026",
            fullName = "Jean Claude",
            phoneNumber = "0798557443",
            email = "jean@example.com",
            serviceCategory = "CIVIL_MARRIAGE_PHOTOGRAPHY",
            eventDate = "20 Dec 2026",
            location = "Muhanga, Rwanda",
            additionalMessage = "Civil marriage ceremony",
            estimatedTotalFrw = 85_000L
        )
        val id = repository.saveBooking(booking)
        assertTrue(id > 0L)

        repository.recordPayment(
            bookingId = id,
            provider = "MTN MoMo",
            status = "DEPOSIT",
            amountFrw = 42_500L,
            reference = "PAY-MTN-123456"
        )

        val stored = repository.bookings.first()
        assertEquals(1, stored.size)
        assertEquals("DEPOSIT", stored.first().paymentStatus)
        assertEquals("PAY-MTN-123456", stored.first().paymentReference)

        repository.deleteBooking(id)
        assertEquals(0, repository.bookings.first().size)
    }
}
