package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.local.BookingEntity
import com.example.i18n.AppLanguage
import com.example.i18n.AppStrings
import com.example.model.PaymentProvider
import com.example.model.ServiceCategory
import com.example.model.formatFrw
import com.example.ui.components.GoldHairlineDivider
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.GoldPrimaryButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.theme.AmberPending
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CrimsonError
import com.example.ui.theme.EmeraldConfirm
import com.example.ui.theme.GoldWash
import com.example.ui.theme.IvoryWhite
import com.example.ui.theme.LuminousGold
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.WarmSilver
import com.example.util.IntentHelpers
import com.example.viewmodel.BookingFormState
import com.example.viewmodel.PaymentAmountMode
import com.example.viewmodel.PaymentFormState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingPaymentScreen(
    strings: AppStrings,
    language: AppLanguage,
    subTab: Int,
    onSelectSubTab: (Int) -> Unit,
    bookingForm: BookingFormState,
    paymentForm: PaymentFormState,
    savedBookings: List<BookingEntity>,
    onFullNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onServiceSelected: (ServiceCategory) -> Unit,
    onEventDateSelected: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onClearEstimate: () -> Unit,
    onSubmitBooking: ((BookingEntity) -> Unit) -> Unit,
    onDeleteBooking: (Long) -> Unit,
    onSelectBookingForPayment: (BookingEntity?) -> Unit,
    onPaymentProviderSelected: (PaymentProvider) -> Unit,
    onPaymentModeSelected: (PaymentAmountMode, BookingEntity?) -> Unit,
    onPaymentAmountChange: (String) -> Unit,
    onPayerAccountChange: (String) -> Unit,
    onPaymentRefChange: (String) -> Unit,
    onSubmitPayment: ((String, Long, String) -> Unit) -> Unit,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 860.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                LuxurySectionHeader(
                    eyebrow = "MOZZO CONCIERGE",
                    title = strings.bookingHeaderTitle,
                    subtitle = strings.bookingHeaderSubtitle
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Sub-tabs: Booking Form | Payment | Saved Bookings
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = ObsidianElevated,
                    border = BorderStroke(1.dp, ObsidianCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(4.dp)) {
                        val historyLabel = if (savedBookings.isNotEmpty()) {
                            "${strings.bookingTabHistory} (${savedBookings.size})"
                        } else {
                            strings.bookingTabHistory
                        }
                        listOf(
                            0 to strings.bookingTabForm,
                            1 to strings.bookingTabPayment,
                            2 to historyLabel
                        ).forEach { (idx, label) ->
                            val active = subTab == idx
                            Surface(
                                onClick = { onSelectSubTab(idx) },
                                shape = RoundedCornerShape(10.dp),
                                color = if (active) ChampagneGold else Color.Transparent,
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("booking_subtab_$idx")
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp)
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = if (active) FontWeight.Bold else FontWeight.Medium,
                                        color = if (active) ObsidianBlack else WarmSilver,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            when (subTab) {
                0 -> {
                    item {
                        BookingFormSection(
                            strings = strings,
                            language = language,
                            form = bookingForm,
                            onFullNameChange = onFullNameChange,
                            onPhoneChange = onPhoneChange,
                            onEmailChange = onEmailChange,
                            onServiceSelected = onServiceSelected,
                            onOpenDatePicker = { showDatePicker = true },
                            onLocationChange = onLocationChange,
                            onMessageChange = onMessageChange,
                            onClearEstimate = onClearEstimate,
                            onSubmitAndWhatsApp = {
                                onSubmitBooking { saved ->
                                    val serviceTitle = ServiceCategory.entries
                                        .firstOrNull { it.name == saved.serviceCategory }
                                        ?.title(language) ?: saved.serviceCategory
                                    val msg = IntentHelpers.buildBookingWhatsAppMessage(
                                        language = language,
                                        bookingRef = saved.bookingRef,
                                        fullName = saved.fullName,
                                        phone = saved.phoneNumber,
                                        email = saved.email,
                                        serviceTitle = serviceTitle,
                                        eventDate = saved.eventDate,
                                        location = saved.location,
                                        estimatedTotalFrw = saved.estimatedTotalFrw,
                                        additionalMessage = saved.additionalMessage
                                    )
                                    val launched = IntentHelpers.openWhatsAppWithMessage(context, msg)
                                    onShowMessage(
                                        if (launched) strings.bookingSavedToast else strings.bookingWhatsAppFallback
                                    )
                                }
                            },
                            onSaveOnly = {
                                onSubmitBooking {
                                    onShowMessage(strings.bookingSavedToast)
                                    onSelectSubTab(2)
                                }
                            }
                        )
                    }
                }

                1 -> {
                    item {
                        PaymentCenterSection(
                            strings = strings,
                            language = language,
                            paymentForm = paymentForm,
                            savedBookings = savedBookings,
                            onSelectBooking = onSelectBookingForPayment,
                            onSelectProvider = onPaymentProviderSelected,
                            onSelectMode = onPaymentModeSelected,
                            onAmountChange = onPaymentAmountChange,
                            onPayerAccountChange = onPayerAccountChange,
                            onManualRefChange = onPaymentRefChange,
                            onSubmitPayment = {
                                onSubmitPayment { refCode, amountFrw, bookingRef ->
                                    onShowMessage(strings.paymentSuccessMsg)
                                    val msg = IntentHelpers.buildPaymentWhatsAppMessage(
                                        language = language,
                                        bookingRef = bookingRef,
                                        providerName = paymentForm.selectedProvider.displayName,
                                        amountFrw = amountFrw,
                                        payerAccount = paymentForm.payerAccount.trim(),
                                        referenceCode = refCode
                                    )
                                    IntentHelpers.openWhatsAppWithMessage(context, msg)
                                }
                            }
                        )
                    }
                }

                else -> {
                    if (savedBookings.isEmpty()) {
                        item {
                            LuxuryCard(modifier = Modifier.fillMaxWidth()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(28.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.BookmarkAdded,
                                        contentDescription = null,
                                        tint = ChampagneGold,
                                        modifier = Modifier.size(44.dp)
                                    )
                                    Text(
                                        text = strings.historyEmptyTitle,
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = IvoryWhite
                                    )
                                    Text(
                                        text = strings.historyEmptyBody,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = WarmSilver
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    GoldPrimaryButton(
                                        text = strings.btnBookNow,
                                        onClick = { onSelectSubTab(0) },
                                        icon = Icons.Filled.CalendarMonth
                                    )
                                }
                            }
                        }
                    } else {
                        items(savedBookings, key = { it.id }) { booking ->
                            SavedBookingCard(
                                booking = booking,
                                strings = strings,
                                language = language,
                                onResendWhatsApp = {
                                    val serviceTitle = ServiceCategory.entries
                                        .firstOrNull { it.name == booking.serviceCategory }
                                        ?.title(language) ?: booking.serviceCategory
                                    val msg = IntentHelpers.buildBookingWhatsAppMessage(
                                        language = language,
                                        bookingRef = booking.bookingRef,
                                        fullName = booking.fullName,
                                        phone = booking.phoneNumber,
                                        email = booking.email,
                                        serviceTitle = serviceTitle,
                                        eventDate = booking.eventDate,
                                        location = booking.location,
                                        estimatedTotalFrw = booking.estimatedTotalFrw,
                                        additionalMessage = booking.additionalMessage
                                    )
                                    IntentHelpers.openWhatsAppWithMessage(context, msg)
                                },
                                onPayBooking = { onSelectBookingForPayment(booking) },
                                onDeleteBooking = { onDeleteBooking(booking.id) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            }
                            onEventDateSelected(formatter.format(Date(millis)))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK", color = LuminousGold, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = WarmSilver)
                }
            },
            colors = DatePickerDefaults.colors(containerColor = ObsidianSurface)
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = ObsidianSurface,
                    titleContentColor = LuminousGold,
                    headlineContentColor = IvoryWhite,
                    weekdayContentColor = WarmSilver,
                    selectedDayContainerColor = ChampagneGold,
                    selectedDayContentColor = ObsidianBlack,
                    todayDateBorderColor = ChampagneGold,
                    todayContentColor = LuminousGold
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookingFormSection(
    strings: AppStrings,
    language: AppLanguage,
    form: BookingFormState,
    onFullNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onServiceSelected: (ServiceCategory) -> Unit,
    onOpenDatePicker: () -> Unit,
    onLocationChange: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onClearEstimate: () -> Unit,
    onSubmitAndWhatsApp: () -> Unit,
    onSaveOnly: () -> Unit
) {
    var serviceDropdownExpanded by remember { mutableStateOf(false) }

    LuxuryCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (form.attachedEstimateFrw > 0L) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = GoldWash,
                    border = BorderStroke(1.dp, ChampagneGold),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strings.bookingEstimatedAttach,
                                style = MaterialTheme.typography.labelSmall,
                                color = ChampagneGold
                            )
                            Text(
                                text = formatFrw(form.attachedEstimateFrw),
                                style = MaterialTheme.typography.titleLarge,
                                color = LuminousGold
                            )
                        }
                        TextButton(onClick = onClearEstimate) {
                            Text("Clear", color = WarmSilver)
                        }
                    }
                }
            }

            // Full Name
            OutlinedTextField(
                value = form.fullName,
                onValueChange = onFullNameChange,
                label = { Text(strings.fieldFullName) },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = ChampagneGold) },
                isError = form.errors.fullNameError,
                supportingText = if (form.errors.fullNameError) {
                    { Text(strings.errFullNameRequired, color = CrimsonError) }
                } else null,
                singleLine = true,
                colors = luxuryFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_fullname_input")
            )

            // Phone Number
            OutlinedTextField(
                value = form.phoneNumber,
                onValueChange = onPhoneChange,
                label = { Text(strings.fieldPhone) },
                leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = null, tint = ChampagneGold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = form.errors.phoneError,
                supportingText = if (form.errors.phoneError) {
                    { Text(strings.errPhoneInvalid, color = CrimsonError) }
                } else null,
                singleLine = true,
                colors = luxuryFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_phone_input")
            )

            // Email Address
            OutlinedTextField(
                value = form.email,
                onValueChange = onEmailChange,
                label = { Text(strings.fieldEmail) },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = ChampagneGold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = form.errors.emailError,
                supportingText = if (form.errors.emailError) {
                    { Text(strings.errEmailInvalid, color = CrimsonError) }
                } else null,
                singleLine = true,
                colors = luxuryFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_email_input")
            )

            // Service Category Dropdown
            ExposedDropdownMenuBox(
                expanded = serviceDropdownExpanded,
                onExpandedChange = { serviceDropdownExpanded = !serviceDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = form.selectedService.title(language),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(strings.fieldServiceCategory) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = serviceDropdownExpanded) },
                    colors = luxuryFieldColors(),
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                        .testTag("booking_service_dropdown")
                )
                ExposedDropdownMenu(
                    expanded = serviceDropdownExpanded,
                    onDismissRequest = { serviceDropdownExpanded = false },
                    containerColor = ObsidianElevated
                ) {
                    ServiceCategory.entries.forEach { service ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(
                                        text = service.title(language),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = IvoryWhite
                                    )
                                    Text(
                                        text = service.priceHint(language),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ChampagneGold
                                    )
                                }
                            },
                            onClick = {
                                onServiceSelected(service)
                                serviceDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Event Date Picker Trigger
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOpenDatePicker)
            ) {
                OutlinedTextField(
                    value = form.eventDate.ifBlank { "" },
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    label = { Text(strings.fieldEventDate) },
                    placeholder = { Text(strings.fieldSelectDateHint, color = WarmSilver) },
                    leadingIcon = { Icon(Icons.Filled.CalendarMonth, contentDescription = null, tint = ChampagneGold) },
                    isError = form.errors.dateError,
                    supportingText = if (form.errors.dateError) {
                        { Text(strings.errDateRequired, color = CrimsonError) }
                    } else null,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = IvoryWhite,
                        disabledBorderColor = if (form.errors.dateError) CrimsonError else ChampagneGold.copy(alpha = 0.45f),
                        disabledLabelColor = ChampagneGold,
                        disabledPlaceholderColor = WarmSilver,
                        disabledLeadingIconColor = ChampagneGold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("booking_date_picker_field")
                )
            }

            // Location
            OutlinedTextField(
                value = form.location,
                onValueChange = onLocationChange,
                label = { Text(strings.fieldLocation) },
                leadingIcon = { Icon(Icons.Filled.LocationOn, contentDescription = null, tint = ChampagneGold) },
                isError = form.errors.locationError,
                supportingText = if (form.errors.locationError) {
                    { Text(strings.errLocationRequired, color = CrimsonError) }
                } else null,
                singleLine = true,
                colors = luxuryFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_location_input")
            )

            // Additional Message
            OutlinedTextField(
                value = form.additionalMessage,
                onValueChange = onMessageChange,
                label = { Text(strings.fieldMessage) },
                minLines = 3,
                maxLines = 5,
                colors = luxuryFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_message_input")
            )

            Spacer(modifier = Modifier.height(4.dp))

            GoldPrimaryButton(
                text = strings.btnSubmitWhatsApp,
                onClick = onSubmitAndWhatsApp,
                icon = Icons.Filled.Send,
                enabled = !form.isSubmitting,
                testTag = "booking_submit_whatsapp_btn",
                modifier = Modifier.fillMaxWidth()
            )

            GoldOutlinedButton(
                text = strings.btnSaveBookingOnly,
                onClick = onSaveOnly,
                icon = Icons.Filled.EventAvailable,
                testTag = "booking_save_only_btn",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentCenterSection(
    strings: AppStrings,
    language: AppLanguage,
    paymentForm: PaymentFormState,
    savedBookings: List<BookingEntity>,
    onSelectBooking: (BookingEntity?) -> Unit,
    onSelectProvider: (PaymentProvider) -> Unit,
    onSelectMode: (PaymentAmountMode, BookingEntity?) -> Unit,
    onAmountChange: (String) -> Unit,
    onPayerAccountChange: (String) -> Unit,
    onManualRefChange: (String) -> Unit,
    onSubmitPayment: () -> Unit
) {
    var bookingDropdownExpanded by remember { mutableStateOf(false) }
    val selectedBooking = savedBookings.firstOrNull { it.id == paymentForm.selectedBookingId }

    LuxuryCard(highlighted = true, modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Payments,
                        contentDescription = null,
                        tint = ChampagneGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = strings.paymentSectionTitle,
                        style = MaterialTheme.typography.headlineMedium,
                        color = IvoryWhite
                    )
                }
                Surface(
                    shape = RoundedCornerShape(50),
                    color = GoldWash,
                    border = BorderStroke(1.dp, ChampagneGold)
                ) {
                    Text(
                        text = strings.paymentProviderReadyBadge,
                        style = MaterialTheme.typography.labelSmall,
                        color = LuminousGold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Text(
                text = strings.paymentSectionSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = WarmSilver
            )

            GoldHairlineDivider()

            // Optional Booking Selector
            ExposedDropdownMenuBox(
                expanded = bookingDropdownExpanded,
                onExpandedChange = { bookingDropdownExpanded = !bookingDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedBooking?.let { "${it.bookingRef} • ${it.fullName}" }
                        ?: strings.paymentNoBookingHint,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(strings.paymentSelectBookingLabel) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = bookingDropdownExpanded) },
                    colors = luxuryFieldColors(),
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = bookingDropdownExpanded,
                    onDismissRequest = { bookingDropdownExpanded = false },
                    containerColor = ObsidianElevated
                ) {
                    DropdownMenuItem(
                        text = { Text(strings.paymentNoBookingHint, color = WarmSilver) },
                        onClick = {
                            onSelectBooking(null)
                            bookingDropdownExpanded = false
                        }
                    )
                    savedBookings.forEach { booking ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "${booking.bookingRef} • ${booking.fullName} (${booking.eventDate})",
                                    color = IvoryWhite
                                )
                            },
                            onClick = {
                                onSelectBooking(booking)
                                bookingDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Provider Selector
            Text(
                text = strings.paymentProviderLabel,
                style = MaterialTheme.typography.labelLarge,
                color = LuminousGold
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PaymentProvider.entries.forEach { provider ->
                    val selected = paymentForm.selectedProvider == provider
                    Surface(
                        onClick = { onSelectProvider(provider) },
                        shape = RoundedCornerShape(12.dp),
                        color = if (selected) GoldWash else ObsidianBlack,
                        border = BorderStroke(
                            width = if (selected) 1.3.dp else 1.dp,
                            color = if (selected) ChampagneGold else ObsidianCardBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("payment_provider_${provider.name}")
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = provider.displayName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = IvoryWhite
                                )
                                Text(
                                    text = if (language == AppLanguage.EN) provider.subtitleEn else provider.subtitleRw,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = WarmSilver
                                )
                            }
                            if (selected) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = null,
                                    tint = LuminousGold
                                )
                            }
                        }
                    }
                }
            }

            // Selected Provider Instructions Banner
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = ObsidianBlack,
                border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (language == AppLanguage.EN) {
                        paymentForm.selectedProvider.instructionsEn
                    } else {
                        paymentForm.selectedProvider.instructionsRw
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = LuminousGold,
                    modifier = Modifier.padding(12.dp)
                )
            }

            // Deposit 50% / Full 100% Quick Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf(
                    PaymentAmountMode.DEPOSIT_50 to strings.paymentModeDeposit,
                    PaymentAmountMode.FULL_100 to strings.paymentModeFull
                ).forEach { (mode, label) ->
                    val active = paymentForm.amountMode == mode
                    Surface(
                        onClick = { onSelectMode(mode, selectedBooking) },
                        shape = RoundedCornerShape(10.dp),
                        color = if (active) ChampagneGold else ObsidianBlack,
                        border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.6f)),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (active) ObsidianBlack else LuminousGold
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = paymentForm.amountFrwInput,
                onValueChange = onAmountChange,
                label = { Text(strings.paymentAmountLabel) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = paymentForm.errors.amountError,
                supportingText = if (paymentForm.errors.amountError) {
                    { Text(strings.errPaymentAmountInvalid, color = CrimsonError) }
                } else null,
                singleLine = true,
                colors = luxuryFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("payment_amount_input")
            )

            OutlinedTextField(
                value = paymentForm.payerAccount,
                onValueChange = onPayerAccountChange,
                label = { Text(strings.paymentAccountPhoneLabel) },
                isError = paymentForm.errors.payerAccountError,
                supportingText = if (paymentForm.errors.payerAccountError) {
                    { Text(strings.errPaymentPhoneInvalid, color = CrimsonError) }
                } else null,
                singleLine = true,
                colors = luxuryFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("payment_account_input")
            )

            OutlinedTextField(
                value = paymentForm.manualReference,
                onValueChange = onManualRefChange,
                label = { Text(strings.paymentRefLabel) },
                singleLine = true,
                colors = luxuryFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("payment_ref_input")
            )

            paymentForm.lastResult?.let { result ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = EmeraldConfirm.copy(alpha = 0.14f),
                    border = BorderStroke(1.dp, EmeraldConfirm),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${strings.paymentSuccessMsg} (${result.providerName} • ${result.referenceCode})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = EmeraldConfirm,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            GoldPrimaryButton(
                text = strings.paymentSubmitBtn,
                onClick = onSubmitPayment,
                icon = Icons.Filled.CreditCard,
                enabled = !paymentForm.isProcessing,
                testTag = "payment_submit_btn",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SavedBookingCard(
    booking: BookingEntity,
    strings: AppStrings,
    language: AppLanguage,
    onResendWhatsApp: () -> Unit,
    onPayBooking: () -> Unit,
    onDeleteBooking: () -> Unit
) {
    val serviceTitle = ServiceCategory.entries
        .firstOrNull { it.name == booking.serviceCategory }
        ?.title(language) ?: booking.serviceCategory

    val statusText = when (booking.paymentStatus) {
        "PAID" -> strings.statusPaidRecorded
        "DEPOSIT" -> strings.statusDepositRecorded
        else -> strings.statusConfirmedLocal
    }
    val statusColor = when (booking.paymentStatus) {
        "PAID" -> EmeraldConfirm
        "DEPOSIT" -> LuminousGold
        else -> AmberPending
    }

    LuxuryCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("saved_booking_card_${booking.bookingRef}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${booking.bookingRef} • ${booking.fullName}",
                    style = MaterialTheme.typography.titleMedium,
                    color = IvoryWhite,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    shape = RoundedCornerShape(50),
                    color = statusColor.copy(alpha = 0.15f),
                    border = BorderStroke(1.dp, statusColor.copy(alpha = 0.65f))
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Text(
                text = "$serviceTitle • ${booking.eventDate}",
                style = MaterialTheme.typography.bodyLarge,
                color = ChampagneGold
            )
            Text(
                text = "${booking.location} • ${booking.phoneNumber}",
                style = MaterialTheme.typography.bodyMedium,
                color = WarmSilver
            )
            if (booking.estimatedTotalFrw > 0L) {
                Text(
                    text = formatFrw(booking.estimatedTotalFrw),
                    style = MaterialTheme.typography.titleMedium,
                    color = LuminousGold
                )
            }
            if (booking.paymentReference.isNotBlank()) {
                Text(
                    text = "${booking.paymentProvider} (${booking.paymentReference}) • ${formatFrw(booking.paymentAmountFrw)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = EmeraldConfirm
                )
            }

            GoldHairlineDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GoldOutlinedButton(
                    text = strings.historyResendWhatsApp,
                    onClick = onResendWhatsApp,
                    icon = Icons.Filled.Send,
                    modifier = Modifier.weight(1f)
                )
                GoldPrimaryButton(
                    text = strings.historyPayBooking,
                    onClick = onPayBooking,
                    icon = Icons.Filled.Payments,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    onClick = onDeleteBooking,
                    shape = RoundedCornerShape(14.dp),
                    color = ObsidianElevated,
                    border = BorderStroke(1.dp, CrimsonError.copy(alpha = 0.45f)),
                    modifier = Modifier.minimumInteractiveComponentSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DeleteOutline,
                            contentDescription = strings.historyDeleteBooking,
                            tint = CrimsonError,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun luxuryFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = IvoryWhite,
    unfocusedTextColor = IvoryWhite,
    focusedBorderColor = ChampagneGold,
    unfocusedBorderColor = ObsidianCardBorder,
    focusedLabelColor = LuminousGold,
    unfocusedLabelColor = WarmSilver,
    cursorColor = LuminousGold,
    errorBorderColor = CrimsonError,
    errorLabelColor = CrimsonError
)
