package com.example

import com.example.data.validation.BookingValidator
import com.example.i18n.AppLanguage
import com.example.i18n.stringsFor
import com.example.model.PhotographyPackage
import com.example.model.PriceCalculatorState
import com.example.model.ServiceCategory
import com.example.model.formatFrw
import com.example.util.IntentHelpers
import com.example.util.StudioContactConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MozzoLogicUnitTest {

    @Test
    fun `package rates match exact FRW specification`() {
        assertEquals(1_500L, PhotographyPackage.INDOOR.priceFrw)
        assertEquals(2_000L, PhotographyPackage.OUTDOOR.priceFrw)
        assertEquals(35_000L, PhotographyPackage.EVENT_PURPOSE.priceFrw)
        assertEquals(50_000L, PhotographyPackage.EVENT_PARTY.priceFrw)
        assertEquals(85_000L, PhotographyPackage.CIVIL_MARRIAGE.priceFrw)
    }

    @Test
    fun `price calculator computes combined FRW total accurately`() {
        val state = PriceCalculatorState(
            indoorPictureCount = 4,      // 6,000 FRW
            outdoorPictureCount = 3,     // 6,000 FRW
            includeEventPurpose = true,  // 35,000 FRW
            includeEventParty = true,    // 50,000 FRW
            includeCivilMarriage = true  // 85,000 FRW
        )
        assertEquals(6_000L, state.indoorSubtotalFrw)
        assertEquals(6_000L, state.outdoorSubtotalFrw)
        assertEquals(170_000L, state.packagesSubtotalFrw)
        assertEquals(182_000L, state.totalFrw)
        assertEquals("182,000 FRW", formatFrw(state.totalFrw))
    }

    @Test
    fun `booking validator rejects invalid inputs and accepts valid booking`() {
        val invalid = BookingValidator.validateBooking(
            fullName = "A",
            phoneNumber = "123",
            email = "bad-email",
            serviceCategory = null,
            eventDate = "",
            location = ""
        )
        assertFalse(invalid.isValid)
        assertTrue(invalid.fullNameError)
        assertTrue(invalid.phoneError)
        assertTrue(invalid.emailError)
        assertTrue(invalid.serviceError)
        assertTrue(invalid.dateError)
        assertTrue(invalid.locationError)

        val valid = BookingValidator.validateBooking(
            fullName = "Moise Irakoze",
            phoneNumber = "0798557443",
            email = "irakozemoise744@gmail.com",
            serviceCategory = ServiceCategory.WEDDING_PHOTOGRAPHY,
            eventDate = "18 Dec 2026",
            location = "Muhanga, Rwanda"
        )
        assertTrue(valid.isValid)
    }

    @Test
    fun `all eight services and bilingual strings exist`() {
        assertEquals(8, ServiceCategory.entries.size)
        val en = stringsFor(AppLanguage.EN)
        val rw = stringsFor(AppLanguage.RW)
        assertTrue(en.btnBookNow.isNotBlank())
        assertTrue(rw.btnBookNow.isNotBlank())
        assertEquals("0798557443", StudioContactConfig.PHONE_DISPLAY)
        assertEquals("irakozemoise744@gmail.com", StudioContactConfig.EMAIL)
        assertEquals("Muhanga, Rwanda", StudioContactConfig.LOCATION)

        val whatsappMsg = IntentHelpers.buildBookingWhatsAppMessage(
            language = AppLanguage.RW,
            bookingRef = "MZZ-1001",
            fullName = "Aline Uwase",
            phone = "0798557443",
            email = "aline@example.com",
            serviceTitle = ServiceCategory.CIVIL_MARRIAGE_PHOTOGRAPHY.title(AppLanguage.RW),
            eventDate = "22 Dec 2026",
            location = "Muhanga",
            estimatedTotalFrw = 85_000L,
            additionalMessage = "Morning session"
        )
        assertTrue(whatsappMsg.contains("MZZ-1001"))
        assertTrue(whatsappMsg.contains("85,000 FRW"))
    }
}
