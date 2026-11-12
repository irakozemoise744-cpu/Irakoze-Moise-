package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BookingEntity
import com.example.data.local.MozzoDatabase
import com.example.data.repository.BookingRepository
import com.example.data.repository.LanguageRepository
import com.example.data.validation.BookingValidationErrors
import com.example.data.validation.BookingValidator
import com.example.data.validation.PaymentValidationErrors
import com.example.i18n.AppLanguage
import com.example.model.PaymentExecutionResult
import com.example.model.PaymentGatewayAdapter
import com.example.model.PaymentProvider
import com.example.model.PriceCalculatorState
import com.example.model.ServiceCategory
import com.example.model.StudioPaymentGateway
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BookingFormState(
    val fullName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val selectedService: ServiceCategory = ServiceCategory.INDOOR_PHOTOGRAPHY,
    val eventDate: String = "",
    val location: String = "Muhanga, Rwanda",
    val additionalMessage: String = "",
    val attachedEstimateFrw: Long = 0L,
    val errors: BookingValidationErrors = BookingValidationErrors(),
    val isSubmitting: Boolean = false
)

enum class PaymentAmountMode { DEPOSIT_50, FULL_100 }

data class PaymentFormState(
    val selectedBookingId: Long? = null,
    val selectedProvider: PaymentProvider = PaymentProvider.MTN_MOMO,
    val amountMode: PaymentAmountMode = PaymentAmountMode.DEPOSIT_50,
    val amountFrwInput: String = "25000",
    val payerAccount: String = "",
    val manualReference: String = "",
    val errors: PaymentValidationErrors = PaymentValidationErrors(),
    val isProcessing: Boolean = false,
    val lastResult: PaymentExecutionResult? = null
)

class MozzoViewModel(application: Application) : AndroidViewModel(application) {

    private val database = MozzoDatabase.getInstance(application)
    private val bookingRepository = BookingRepository(database.bookingDao())
    private val languageRepository = LanguageRepository(application)
    private val paymentGateway: PaymentGatewayAdapter = StudioPaymentGateway()

    val language: StateFlow<AppLanguage> = languageRepository.selectedLanguage
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppLanguage.EN
        )

    val savedBookings: StateFlow<List<BookingEntity>> = bookingRepository.bookings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _calculatorState = MutableStateFlow(PriceCalculatorState())
    val calculatorState: StateFlow<PriceCalculatorState> = _calculatorState.asStateFlow()

    private val _bookingForm = MutableStateFlow(BookingFormState())
    val bookingForm: StateFlow<BookingFormState> = _bookingForm.asStateFlow()

    private val _paymentForm = MutableStateFlow(PaymentFormState())
    val paymentForm: StateFlow<PaymentFormState> = _paymentForm.asStateFlow()

    private val _bookingSubTab = MutableStateFlow(0)
    val bookingSubTab: StateFlow<Int> = _bookingSubTab.asStateFlow()

    fun setLanguage(newLanguage: AppLanguage) {
        viewModelScope.launch {
            languageRepository.setLanguage(newLanguage)
        }
    }

    fun toggleLanguage() {
        val next = if (language.value == AppLanguage.EN) AppLanguage.RW else AppLanguage.EN
        setLanguage(next)
    }

    fun setBookingSubTab(index: Int) {
        _bookingSubTab.value = index.coerceIn(0, 2)
    }

    // Price Calculator Actions
    fun updateIndoorCount(delta: Int) {
        _calculatorState.update { current ->
            current.copy(indoorPictureCount = (current.indoorPictureCount + delta).coerceIn(0, 500))
        }
    }

    fun updateOutdoorCount(delta: Int) {
        _calculatorState.update { current ->
            current.copy(outdoorPictureCount = (current.outdoorPictureCount + delta).coerceIn(0, 500))
        }
    }

    fun toggleEventPurpose() {
        _calculatorState.update { it.copy(includeEventPurpose = !it.includeEventPurpose) }
    }

    fun toggleEventParty() {
        _calculatorState.update { it.copy(includeEventParty = !it.includeEventParty) }
    }

    fun toggleCivilMarriage() {
        _calculatorState.update { it.copy(includeCivilMarriage = !it.includeCivilMarriage) }
    }

    fun resetCalculator() {
        _calculatorState.value = PriceCalculatorState(indoorPictureCount = 0)
    }

    fun prefillBookingFromService(service: ServiceCategory) {
        _bookingForm.update {
            it.copy(
                selectedService = service,
                errors = it.errors.copy(serviceError = false)
            )
        }
        _bookingSubTab.value = 0
    }

    fun prefillBookingFromCalculator() {
        val calc = _calculatorState.value
        val currentLang = language.value
        val total = calc.totalFrw
        val summary = calc.buildSummary(currentLang)
        _bookingForm.update { form ->
            form.copy(
                selectedService = calc.suggestedCategory(),
                attachedEstimateFrw = total,
                additionalMessage = if (total > 0L) summary else form.additionalMessage,
                errors = form.errors.copy(serviceError = false)
            )
        }
        if (total > 0L) {
            val defaultDeposit = (total / 2L).coerceAtLeast(1000L)
            _paymentForm.update { pay ->
                pay.copy(
                    amountMode = PaymentAmountMode.DEPOSIT_50,
                    amountFrwInput = defaultDeposit.toString()
                )
            }
        }
        _bookingSubTab.value = 0
    }

    // Booking Form Input Handlers
    fun onFullNameChange(value: String) {
        _bookingForm.update {
            it.copy(fullName = value, errors = it.errors.copy(fullNameError = false))
        }
    }

    fun onPhoneChange(value: String) {
        _bookingForm.update {
            it.copy(phoneNumber = value, errors = it.errors.copy(phoneError = false))
        }
    }

    fun onEmailChange(value: String) {
        _bookingForm.update {
            it.copy(email = value, errors = it.errors.copy(emailError = false))
        }
    }

    fun onServiceSelected(service: ServiceCategory) {
        _bookingForm.update {
            it.copy(selectedService = service, errors = it.errors.copy(serviceError = false))
        }
    }

    fun onEventDateSelected(dateFormatted: String) {
        _bookingForm.update {
            it.copy(eventDate = dateFormatted, errors = it.errors.copy(dateError = false))
        }
    }

    fun onLocationChange(value: String) {
        _bookingForm.update {
            it.copy(location = value, errors = it.errors.copy(locationError = false))
        }
    }

    fun onMessageChange(value: String) {
        _bookingForm.update { it.copy(additionalMessage = value) }
    }

    fun clearAttachedEstimate() {
        _bookingForm.update { it.copy(attachedEstimateFrw = 0L) }
    }

    fun submitBooking(
        onSuccess: (BookingEntity) -> Unit
    ) {
        val state = _bookingForm.value
        val validation = BookingValidator.validateBooking(
            fullName = state.fullName,
            phoneNumber = state.phoneNumber,
            email = state.email,
            serviceCategory = state.selectedService,
            eventDate = state.eventDate,
            location = state.location
        )
        if (!validation.isValid) {
            _bookingForm.update { it.copy(errors = validation) }
            return
        }

        _bookingForm.update { it.copy(isSubmitting = true) }
        val bookingRef = "MZZ-${(1000..9999).random()}"
        val entity = BookingEntity(
            bookingRef = bookingRef,
            fullName = state.fullName.trim(),
            phoneNumber = state.phoneNumber.trim(),
            email = state.email.trim(),
            serviceCategory = state.selectedService.name,
            eventDate = state.eventDate.trim(),
            location = state.location.trim(),
            additionalMessage = state.additionalMessage.trim(),
            estimatedTotalFrw = state.attachedEstimateFrw
        )

        viewModelScope.launch {
            val insertedId = bookingRepository.saveBooking(entity)
            val savedEntity = entity.copy(id = insertedId)
            _bookingForm.update {
                BookingFormState(
                    selectedService = state.selectedService,
                    location = "Muhanga, Rwanda"
                )
            }
            if (savedEntity.estimatedTotalFrw > 0L) {
                val suggested = savedEntity.estimatedTotalFrw / 2L
                _paymentForm.update {
                    it.copy(
                        selectedBookingId = savedEntity.id,
                        amountMode = PaymentAmountMode.DEPOSIT_50,
                        amountFrwInput = suggested.toString(),
                        payerAccount = savedEntity.phoneNumber
                    )
                }
            } else {
                _paymentForm.update {
                    it.copy(
                        selectedBookingId = savedEntity.id,
                        payerAccount = savedEntity.phoneNumber
                    )
                }
            }
            onSuccess(savedEntity)
        }
    }

    fun deleteBooking(bookingId: Long) {
        viewModelScope.launch {
            bookingRepository.deleteBooking(bookingId)
            if (_paymentForm.value.selectedBookingId == bookingId) {
                _paymentForm.update { it.copy(selectedBookingId = null) }
            }
        }
    }

    // Payment Form Handlers
    fun selectBookingForPayment(booking: BookingEntity?) {
        val baseTotal = booking?.estimatedTotalFrw?.takeIf { it > 0L } ?: 50_000L
        val computed = if (_paymentForm.value.amountMode == PaymentAmountMode.DEPOSIT_50) {
            baseTotal / 2L
        } else {
            baseTotal
        }
        _paymentForm.update {
            it.copy(
                selectedBookingId = booking?.id,
                amountFrwInput = computed.toString(),
                payerAccount = booking?.phoneNumber ?: it.payerAccount,
                lastResult = null
            )
        }
        _bookingSubTab.value = 1
    }

    fun onPaymentProviderSelected(provider: PaymentProvider) {
        _paymentForm.update { it.copy(selectedProvider = provider, lastResult = null) }
    }

    fun onPaymentModeSelected(mode: PaymentAmountMode, linkedBooking: BookingEntity?) {
        val baseTotal = linkedBooking?.estimatedTotalFrw?.takeIf { it > 0L }
            ?: _calculatorState.value.totalFrw.takeIf { it > 0L }
            ?: 50_000L
        val computed = if (mode == PaymentAmountMode.DEPOSIT_50) baseTotal / 2L else baseTotal
        _paymentForm.update {
            it.copy(
                amountMode = mode,
                amountFrwInput = computed.toString(),
                errors = it.errors.copy(amountError = false)
            )
        }
    }

    fun onPaymentAmountChange(value: String) {
        val filtered = value.filter { it.isDigit() }
        _paymentForm.update {
            it.copy(
                amountFrwInput = filtered,
                errors = it.errors.copy(amountError = false),
                lastResult = null
            )
        }
    }

    fun onPayerAccountChange(value: String) {
        _paymentForm.update {
            it.copy(
                payerAccount = value,
                errors = it.errors.copy(payerAccountError = false),
                lastResult = null
            )
        }
    }

    fun onPaymentManualRefChange(value: String) {
        _paymentForm.update { it.copy(manualReference = value, lastResult = null) }
    }

    fun submitPayment(
        onPaymentRecorded: (PaymentExecutionResult, Long, String) -> Unit
    ) {
        val form = _paymentForm.value
        val validation = BookingValidator.validatePayment(
            amountText = form.amountFrwInput,
            payerAccount = form.payerAccount
        )
        if (!validation.isValid) {
            _paymentForm.update { it.copy(errors = validation) }
            return
        }

        val amountFrw = form.amountFrwInput.toLongOrNull() ?: 0L
        val linkedBooking = savedBookings.value.firstOrNull { it.id == form.selectedBookingId }
        val bookingRef = linkedBooking?.bookingRef ?: "DIRECT-STUDIO"

        _paymentForm.update { it.copy(isProcessing = true) }
        viewModelScope.launch {
            val result = paymentGateway.initiateStudioPayment(
                provider = form.selectedProvider,
                amountFrw = amountFrw,
                payerAccount = form.payerAccount.trim(),
                bookingReference = bookingRef,
                manualTransactionRef = form.manualReference
            )
            if (linkedBooking != null) {
                val statusTag = if (form.amountMode == PaymentAmountMode.FULL_100) "PAID" else "DEPOSIT"
                bookingRepository.recordPayment(
                    bookingId = linkedBooking.id,
                    provider = form.selectedProvider.displayName,
                    status = statusTag,
                    amountFrw = amountFrw,
                    reference = result.referenceCode
                )
            }
            _paymentForm.update {
                it.copy(
                    isProcessing = false,
                    lastResult = result,
                    manualReference = ""
                )
            }
            onPaymentRecorded(result, amountFrw, bookingRef)
        }
    }
}
