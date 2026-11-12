package com.example.data.validation

import com.example.model.ServiceCategory

data class BookingValidationErrors(
    val fullNameError: Boolean = false,
    val phoneError: Boolean = false,
    val emailError: Boolean = false,
    val serviceError: Boolean = false,
    val dateError: Boolean = false,
    val locationError: Boolean = false
) {
    val isValid: Boolean
        get() = !fullNameError && !phoneError && !emailError && !serviceError && !dateError && !locationError
}

data class PaymentValidationErrors(
    val amountError: Boolean = false,
    val payerAccountError: Boolean = false
) {
    val isValid: Boolean
        get() = !amountError && !payerAccountError
}

object BookingValidator {
    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun validateBooking(
        fullName: String,
        phoneNumber: String,
        email: String,
        serviceCategory: ServiceCategory?,
        eventDate: String,
        location: String
    ): BookingValidationErrors {
        val digitsOnly = phoneNumber.filter { it.isDigit() }
        return BookingValidationErrors(
            fullNameError = fullName.trim().length < 2,
            phoneError = digitsOnly.length < 9,
            emailError = !EMAIL_REGEX.matches(email.trim()),
            serviceError = serviceCategory == null,
            dateError = eventDate.isBlank(),
            locationError = location.trim().length < 2
        )
    }

    fun validatePayment(
        amountText: String,
        payerAccount: String
    ): PaymentValidationErrors {
        val amount = amountText.filter { it.isDigit() }.toLongOrNull() ?: 0L
        val trimmedAccount = payerAccount.trim()
        val accountDigits = trimmedAccount.filter { it.isDigit() }
        return PaymentValidationErrors(
            amountError = amount <= 0L,
            payerAccountError = trimmedAccount.length < 6 || (accountDigits.length < 8 && !trimmedAccount.contains("@"))
        )
    }
}
