package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.i18n.AppLanguage
import com.example.i18n.AppStrings
import com.example.ui.components.GoldHairlineDivider
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.GoldPrimaryButton
import com.example.ui.components.LanguageSwitcherPill
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.components.MozzoLogoBadge
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.GoldWash
import com.example.ui.theme.IvoryWhite
import com.example.ui.theme.LuminousGold
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.WarmSilver
import com.example.util.IntentHelpers
import com.example.util.StudioContactConfig

@Composable
fun ContactScreen(
    strings: AppStrings,
    currentLanguage: AppLanguage,
    onSelectLanguage: (AppLanguage) -> Unit,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

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
                    eyebrow = "MUHANGA • RWANDA",
                    title = strings.contactHeaderTitle,
                    subtitle = strings.contactHeaderSubtitle
                )
            }

            // Official Studio Brand & Details Card
            item {
                LuxuryCard(highlighted = true, modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            MozzoLogoBadge(size = 58.dp)
                            Column {
                                Text(
                                    text = strings.contactBusinessNameLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ChampagneGold
                                )
                                Text(
                                    text = StudioContactConfig.BUSINESS_NAME,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = IvoryWhite
                                )
                                Text(
                                    text = strings.studioTagline,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = WarmSilver
                                )
                            }
                        }

                        GoldHairlineDivider()

                        ContactDetailRow(
                            icon = Icons.Filled.Storefront,
                            label = strings.contactBusinessNameLabel,
                            value = StudioContactConfig.BUSINESS_NAME,
                            onCopy = null
                        )

                        ContactDetailRow(
                            icon = Icons.Filled.LocationOn,
                            label = strings.contactLocationLabel,
                            value = StudioContactConfig.LOCATION,
                            onCopy = {
                                IntentHelpers.copyToClipboard(context, "Location", StudioContactConfig.LOCATION)
                                onShowMessage("${strings.contactCopiedMsg}: ${StudioContactConfig.LOCATION}")
                            }
                        )

                        ContactDetailRow(
                            icon = Icons.Filled.Phone,
                            label = strings.contactPhoneLabel,
                            value = StudioContactConfig.PHONE_DISPLAY,
                            onCopy = {
                                IntentHelpers.copyToClipboard(context, "Phone", StudioContactConfig.PHONE_DISPLAY)
                                onShowMessage("${strings.contactCopiedMsg}: ${StudioContactConfig.PHONE_DISPLAY}")
                            }
                        )

                        ContactDetailRow(
                            icon = Icons.Filled.Email,
                            label = strings.contactEmailLabel,
                            value = StudioContactConfig.EMAIL,
                            onCopy = {
                                IntentHelpers.copyToClipboard(context, "Email", StudioContactConfig.EMAIL)
                                onShowMessage("${strings.contactCopiedMsg}: ${StudioContactConfig.EMAIL}")
                            }
                        )

                        ContactDetailRow(
                            icon = Icons.Filled.AccessTime,
                            label = strings.contactHoursTitle,
                            value = strings.contactHoursValue,
                            onCopy = null
                        )

                        GoldHairlineDivider()

                        // Functional WhatsApp, Call, and Email Buttons
                        GoldPrimaryButton(
                            text = strings.contactWhatsAppBtn,
                            onClick = {
                                val greeting = if (currentLanguage == AppLanguage.EN) {
                                    "Hello MOZZO Photography & Videography, I would like to inquire about a photography/videography session."
                                } else {
                                    "Muraho MOZZO Photography & Videography, nifuzaga amakuru ku bijyanye no gufata amafoto cyangwa amashusho."
                                }
                                IntentHelpers.openWhatsAppWithMessage(context, greeting)
                            },
                            icon = Icons.Filled.Chat,
                            testTag = "contact_whatsapp_btn",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            GoldOutlinedButton(
                                text = strings.contactCallBtn,
                                onClick = { IntentHelpers.dialStudio(context) },
                                icon = Icons.Filled.Phone,
                                testTag = "contact_call_btn",
                                modifier = Modifier.weight(1f)
                            )
                            GoldOutlinedButton(
                                text = strings.contactEmailBtn,
                                onClick = {
                                    IntentHelpers.emailStudio(
                                        context = context,
                                        subject = "MOZZO Studio Inquiry",
                                        body = "Hello MOZZO Photography & Videography,"
                                    )
                                },
                                icon = Icons.Filled.Email,
                                testTag = "contact_email_btn",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Bilingual Language Switcher Card
            item {
                LuxuryCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = GoldWash,
                                border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.5f)),
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Filled.Language,
                                        contentDescription = null,
                                        tint = LuminousGold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = strings.contactLanguageTitle,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = IvoryWhite,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = strings.contactLanguageSubtitle,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = WarmSilver
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        LanguageSwitcherPill(
                            currentLanguage = currentLanguage,
                            onSelectLanguage = onSelectLanguage
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactDetailRow(
    icon: ImageVector,
    label: String,
    value: String,
    onCopy: (() -> Unit)?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = ObsidianElevated,
                border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.35f)),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = ChampagneGold,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmSilver
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = IvoryWhite,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        if (onCopy != null) {
            IconButton(onClick = onCopy) {
                Icon(
                    imageVector = Icons.Filled.ContentCopy,
                    contentDescription = "Copy $label",
                    tint = ChampagneGold,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
