package com.example.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.i18n.AppLanguage
import com.example.model.formatFrw
import java.net.URLEncoder

object StudioContactConfig {
    const val BUSINESS_NAME = "MOZZO Photography & Videography"
    const val LOCATION = "Muhanga, Rwanda"
    const val PHONE_DISPLAY = "0798557443"
    const val PHONE_WHATSAPP_INTL = "250798557443"
    const val EMAIL = "irakozemoise744@gmail.com"
}

object IntentHelpers {

    fun buildBookingWhatsAppMessage(
        language: AppLanguage,
        bookingRef: String,
        fullName: String,
        phone: String,
        email: String,
        serviceTitle: String,
        eventDate: String,
        location: String,
        estimatedTotalFrw: Long,
        additionalMessage: String
    ): String {
        val estimateLine = if (estimatedTotalFrw > 0L) {
            if (language == AppLanguage.EN) {
                "\nEstimated Package: ${formatFrw(estimatedTotalFrw)}"
            } else {
                "\nIgiciro Cyabarirweho: ${formatFrw(estimatedTotalFrw)}"
            }
        } else ""

        val notesLine = if (additionalMessage.isNotBlank()) {
            if (language == AppLanguage.EN) "\nMessage: $additionalMessage" else "\nUbutumwa: $additionalMessage"
        } else ""

        return if (language == AppLanguage.EN) {
            """
            *MOZZO PHOTOGRAPHY & VIDEOGRAPHY - NEW BOOKING*
            Ref: $bookingRef
            Name: $fullName
            Phone: $phone
            Email: $email
            Service: $serviceTitle
            Event Date: $eventDate
            Location: $location$estimateLine$notesLine
            """.trimIndent()
        } else {
            """
            *MOZZO PHOTOGRAPHY & VIDEOGRAPHY - GAHUNDA NSHYA*
            Nimero: $bookingRef
            Amazina: $fullName
            Telefoni: $phone
            Imeyili: $email
            Serivisi: $serviceTitle
            Itariki: $eventDate
            Aho bizabera: $location$estimateLine$notesLine
            """.trimIndent()
        }
    }

    fun buildPaymentWhatsAppMessage(
        language: AppLanguage,
        bookingRef: String,
        providerName: String,
        amountFrw: Long,
        payerAccount: String,
        referenceCode: String
    ): String {
        return if (language == AppLanguage.EN) {
            """
            *MOZZO STUDIO - PAYMENT NOTIFICATION*
            Booking Ref: $bookingRef
            Provider: $providerName
            Amount: ${formatFrw(amountFrw)}
            Payer Account: $payerAccount
            Transaction Ref: $referenceCode
            """.trimIndent()
        } else {
            """
            *MOZZO STUDIO - AMENYESHA YO KWISHYURA*
            Gahunda: $bookingRef
            Uburyo: $providerName
            Amafaranga: ${formatFrw(amountFrw)}
            Konti / MoMo: $payerAccount
            Icyemezo: $referenceCode
            """.trimIndent()
        }
    }

    /**
     * Attempts to open WhatsApp directly to MOZZO's number (+250798557443) with pre-filled message.
     * Falls back cleanly to Android share chooser if WhatsApp/browser is unavailable.
     * Returns true if an activity was launched.
     */
    fun openWhatsAppWithMessage(context: Context, message: String): Boolean {
        val encoded = URLEncoder.encode(message, "UTF-8")
        val waUri = Uri.parse("https://wa.me/${StudioContactConfig.PHONE_WHATSAPP_INTL}?text=$encoded")

        val viewIntent = Intent(Intent.ACTION_VIEW, waUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return try {
            context.startActivity(viewIntent)
            true
        } catch (_: Exception) {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, message)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                val chooser = Intent.createChooser(shareIntent, "Send via WhatsApp").apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(chooser)
                true
            } catch (_: Exception) {
                false
            }
        }
    }

    fun dialStudio(context: Context): Boolean {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${StudioContactConfig.PHONE_DISPLAY}")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return try {
            context.startActivity(intent)
            true
        } catch (_: Exception) {
            false
        }
    }

    fun emailStudio(context: Context, subject: String, body: String): Boolean {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${StudioContactConfig.EMAIL}")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return try {
            context.startActivity(intent)
            true
        } catch (_: Exception) {
            false
        }
    }

    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.setPrimaryClip(ClipData.newPlainText(label, text))
    }
}
