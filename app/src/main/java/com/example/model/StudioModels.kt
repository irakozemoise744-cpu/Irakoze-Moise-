package com.example.model

import androidx.annotation.DrawableRes
import com.example.R
import com.example.i18n.AppLanguage
import java.text.NumberFormat
import java.util.Locale

enum class ServiceCategory(
    val titleEn: String,
    val titleRw: String,
    val descriptionEn: String,
    val descriptionRw: String,
    val badgeEn: String,
    val badgeRw: String,
    val priceHintEn: String,
    val priceHintRw: String,
    @DrawableRes val imageRes: Int
) {
    INDOOR_PHOTOGRAPHY(
        titleEn = "Indoor Photography",
        titleRw = "Ifoto yo mu Nzu (Indoor)",
        descriptionEn = "Editorial studio portraiture with dramatic golden key lighting, seamless backdrops, and fine-art retouching.",
        descriptionRw = "Amafoto yo muri studio akoranywe amatara y'umwuga n'ubuhanga bwo gutunganya amashusho.",
        badgeEn = "Studio",
        badgeRw = "Studio",
        priceHintEn = "1,500 FRW / picture",
        priceHintRw = "1,500 FRW / ifoto",
        imageRes = R.drawable.img_event_portrait
    ),
    OUTDOOR_PHOTOGRAPHY(
        titleEn = "Outdoor Photography",
        titleRw = "Ifoto yo Hanze (Outdoor)",
        descriptionEn = "Golden-hour location portraits across Muhanga, Kigali, and scenic landscapes with natural depth.",
        badgeEn = "Location",
        badgeRw = "Hanze",
        descriptionRw = "Amafoto yo ku mugaragaro mu mucyo w'izuba rirenga i Muhanga, i Kigali n'ahandi heza.",
        priceHintEn = "2,000 FRW / picture",
        priceHintRw = "2,000 FRW / ifoto",
        imageRes = R.drawable.img_wedding_luxury
    ),
    WEDDING_PHOTOGRAPHY(
        titleEn = "Wedding Photography",
        titleRw = "Gufotora Ubukwe",
        descriptionEn = "Full-day luxury wedding storytelling covering traditional Gusaba, church vows, and evening reception.",
        descriptionRw = "Gufotora ubukwe bwose: Gusaba no Gukwa, Gusezerana imbere y'Imana n'ibirori byo kwiyakira.",
        badgeEn = "Signature",
        badgeRw = "Umwihariko",
        priceHintEn = "Custom & Full Day",
        priceHintRw = "Umunsi Wose",
        imageRes = R.drawable.img_wedding_luxury
    ),
    CIVIL_MARRIAGE_PHOTOGRAPHY(
        titleEn = "Civil Marriage Photography",
        titleRw = "Gusezerana mu Murenge",
        descriptionEn = "Refined coverage of your civil ceremony, family portraits, and golden celebratory keepsake edits.",
        descriptionRw = "Amafoto y'agaciro mu muhango wo gusezerana imbere y'amategeko n'ay'umuryango.",
        badgeEn = "85,000 FRW",
        badgeRw = "85,000 FRW",
        priceHintEn = "85,000 FRW Package",
        priceHintRw = "Paki ya 85,000 FRW",
        imageRes = R.drawable.img_wedding_luxury
    ),
    EVENTS_PHOTOGRAPHY(
        titleEn = "Events Photography",
        titleRw = "Gufotora Ibirori",
        descriptionEn = "Corporate galas, birthdays, graduations, and celebrations captured with crisp editorial elegance.",
        descriptionRw = "Ibirori by'akazi, amasabukuru, impamyabumenyi n'indi minsi mikuru mu mafoto meza.",
        badgeEn = "From 35,000 FRW",
        badgeRw = "Guhera 35,000 FRW",
        priceHintEn = "35k Purpose / 50k Party",
        priceHintRw = "35,000 – 50,000 FRW",
        imageRes = R.drawable.img_event_portrait
    ),
    MUSIC_VIDEO_PRODUCTION(
        titleEn = "Music Video Production",
        titleRw = "Amashusho y'Indirimbo",
        descriptionEn = "4K cinema camera direction, motorized gimbal movement, dramatic studio lighting, and color grading.",
        descriptionRw = "Gufata no gutunganya amashusho y'indirimbo muri 4K hakoreshejwe amatara n'ibikoresho bigezweho.",
        badgeEn = "4K Cinema",
        badgeRw = "Sinema 4K",
        priceHintEn = "Studio Quote",
        priceHintRw = "Ibiciro bya Studio",
        imageRes = R.drawable.img_cinema_production
    ),
    FILM_PRODUCTION(
        titleEn = "Film Production",
        titleRw = "Gukora Filime",
        descriptionEn = "Short films, documentaries, and brand commercials produced from script to cinema-grade master.",
        descriptionRw = "Filime ngufi, inyandiko-shusho n'amashusho yo kwamamaza atunganyijwe ku rwego mpuzamahanga.",
        badgeEn = "Cinema",
        badgeRw = "Filime",
        priceHintEn = "Studio Quote",
        priceHintRw = "Ibiciro bya Studio",
        imageRes = R.drawable.img_hero_studio
    ),
    COMEDY_VIDEO_PRODUCTION(
        titleEn = "Comedy Video Production",
        titleRw = "Amashusho y'Urwenya",
        descriptionEn = "Crisp multi-angle comedy skits, clean wireless audio capture, and punchy social-ready editing.",
        descriptionRw = "Gufata amashusho y'urwenya afite amajwi asobanutse n'imitunganyirize inogeye ijisho.",
        badgeEn = "Digital Series",
        badgeRw = "Urwenya",
        priceHintEn = "Studio Quote",
        priceHintRw = "Ibiciro bya Studio",
        imageRes = R.drawable.img_cinema_production
    );

    fun title(lang: AppLanguage): String = if (lang == AppLanguage.EN) titleEn else titleRw
    fun description(lang: AppLanguage): String = if (lang == AppLanguage.EN) descriptionEn else descriptionRw
    fun badge(lang: AppLanguage): String = if (lang == AppLanguage.EN) badgeEn else badgeRw
    fun priceHint(lang: AppLanguage): String = if (lang == AppLanguage.EN) priceHintEn else priceHintRw
}

enum class PhotographyPackage(
    val titleEn: String,
    val titleRw: String,
    val rateLabelEn: String,
    val rateLabelRw: String,
    val priceFrw: Long,
    val isPerPicture: Boolean,
    val detailsEn: String,
    val detailsRw: String
) {
    INDOOR(
        titleEn = "Indoor",
        titleRw = "Mu Nzu (Indoor)",
        rateLabelEn = "1 picture = 1,500 FRW",
        rateLabelRw = "Ifoto 1 = 1,500 FRW",
        priceFrw = 1_500L,
        isPerPicture = true,
        detailsEn = "Studio backdrops, pro strobe lighting & retouching",
        detailsRw = "Amatara ya studio, guhitamo ibara ry'inyuma & gutunganya ifoto"
    ),
    OUTDOOR(
        titleEn = "Outdoor",
        titleRw = "Hanze (Outdoor)",
        rateLabelEn = "1 picture = 2,000 FRW",
        rateLabelRw = "Ifoto 1 = 2,000 FRW",
        priceFrw = 2_000L,
        isPerPicture = true,
        detailsEn = "Natural golden hour portrait shoot on location",
        detailsRw = "Amafoto afatiwe hanze mu mucyo mwiza kandi asobanutse"
    ),
    EVENT_PURPOSE(
        titleEn = "Event Purpose",
        titleRw = "Igikorwa Cyihariye (Event Purpose)",
        rateLabelEn = "35,000 FRW",
        rateLabelRw = "35,000 FRW",
        priceFrw = 35_000L,
        isPerPicture = false,
        detailsEn = "Dedicated coverage for official ceremonies, launches & focused events",
        detailsRw = "Gufotora inama, ibikorwa byihariye n'imihango y'umunsi umwe"
    ),
    EVENT_PARTY(
        titleEn = "Event Party",
        titleRw = "Ibirori & Umunsi Mukuru (Event Party)",
        rateLabelEn = "50,000 FRW",
        rateLabelRw = "50,000 FRW",
        priceFrw = 50_000L,
        isPerPicture = false,
        detailsEn = "Extended celebration coverage for birthdays, graduations & evening parties",
        detailsRw = "Gufotora ibirori by'amasabukuru, impamyabumenyi n'iminsi mikuru"
    ),
    CIVIL_MARRIAGE(
        titleEn = "Civil Marriage",
        titleRw = "Gusezerana mu Murenge (Civil Marriage)",
        rateLabelEn = "85,000 FRW",
        rateLabelRw = "85,000 FRW",
        priceFrw = 85_000L,
        isPerPicture = false,
        detailsEn = "Complete civil marriage ceremony, couple portraits & curated digital gallery",
        detailsRw = "Gufotora umuhango wo gusezerana imbere y'amategeko n'amafoto y'umuryango"
    );

    fun title(lang: AppLanguage): String = if (lang == AppLanguage.EN) titleEn else titleRw
    fun rateLabel(lang: AppLanguage): String = if (lang == AppLanguage.EN) rateLabelEn else rateLabelRw
    fun details(lang: AppLanguage): String = if (lang == AppLanguage.EN) detailsEn else detailsRw
}

data class PriceCalculatorState(
    val indoorPictureCount: Int = 5,
    val outdoorPictureCount: Int = 0,
    val includeEventPurpose: Boolean = false,
    val includeEventParty: Boolean = false,
    val includeCivilMarriage: Boolean = false
) {
    val indoorSubtotalFrw: Long
        get() = indoorPictureCount.coerceAtLeast(0) * PhotographyPackage.INDOOR.priceFrw

    val outdoorSubtotalFrw: Long
        get() = outdoorPictureCount.coerceAtLeast(0) * PhotographyPackage.OUTDOOR.priceFrw

    val packagesSubtotalFrw: Long
        get() = (if (includeEventPurpose) PhotographyPackage.EVENT_PURPOSE.priceFrw else 0L) +
            (if (includeEventParty) PhotographyPackage.EVENT_PARTY.priceFrw else 0L) +
            (if (includeCivilMarriage) PhotographyPackage.CIVIL_MARRIAGE.priceFrw else 0L)

    val totalFrw: Long
        get() = indoorSubtotalFrw + outdoorSubtotalFrw + packagesSubtotalFrw

    fun suggestedCategory(): ServiceCategory = when {
        includeCivilMarriage -> ServiceCategory.CIVIL_MARRIAGE_PHOTOGRAPHY
        includeEventParty || includeEventPurpose -> ServiceCategory.EVENTS_PHOTOGRAPHY
        outdoorPictureCount > indoorPictureCount -> ServiceCategory.OUTDOOR_PHOTOGRAPHY
        else -> ServiceCategory.INDOOR_PHOTOGRAPHY
    }

    fun buildSummary(lang: AppLanguage): String {
        val parts = mutableListOf<String>()
        if (indoorPictureCount > 0) {
            parts.add(
                if (lang == AppLanguage.EN) {
                    "Indoor x$indoorPictureCount (${formatFrw(indoorSubtotalFrw)})"
                } else {
                    "Mu nzu x$indoorPictureCount (${formatFrw(indoorSubtotalFrw)})"
                }
            )
        }
        if (outdoorPictureCount > 0) {
            parts.add(
                if (lang == AppLanguage.EN) {
                    "Outdoor x$outdoorPictureCount (${formatFrw(outdoorSubtotalFrw)})"
                } else {
                    "Hanze x$outdoorPictureCount (${formatFrw(outdoorSubtotalFrw)})"
                }
            )
        }
        if (includeEventPurpose) {
            parts.add("${PhotographyPackage.EVENT_PURPOSE.title(lang)} (${formatFrw(PhotographyPackage.EVENT_PURPOSE.priceFrw)})")
        }
        if (includeEventParty) {
            parts.add("${PhotographyPackage.EVENT_PARTY.title(lang)} (${formatFrw(PhotographyPackage.EVENT_PARTY.priceFrw)})")
        }
        if (includeCivilMarriage) {
            parts.add("${PhotographyPackage.CIVIL_MARRIAGE.title(lang)} (${formatFrw(PhotographyPackage.CIVIL_MARRIAGE.priceFrw)})")
        }
        return if (parts.isEmpty()) {
            if (lang == AppLanguage.EN) "No items selected" else "Nta kintu cyahiswemo"
        } else {
            parts.joinToString(" + ")
        }
    }
}

fun formatFrw(amount: Long): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return "${formatter.format(amount)} FRW"
}

enum class GalleryMediaType { PHOTO, VIDEO }

enum class GalleryCategory { ALL, WEDDINGS, STUDIO, CINEMA }

data class GalleryItem(
    val id: String,
    val mediaType: GalleryMediaType,
    val category: GalleryCategory,
    val titleEn: String,
    val titleRw: String,
    val subtitleEn: String,
    val subtitleRw: String,
    val location: String,
    val specs: String,
    val durationOrCount: String,
    val relatedService: ServiceCategory,
    @DrawableRes val imageRes: Int
) {
    fun title(lang: AppLanguage): String = if (lang == AppLanguage.EN) titleEn else titleRw
    fun subtitle(lang: AppLanguage): String = if (lang == AppLanguage.EN) subtitleEn else subtitleRw
}

object StudioPortfolioCatalog {
    val photoProjects: List<GalleryItem> = listOf(
        GalleryItem(
            id = "photo_royal_wedding",
            mediaType = GalleryMediaType.PHOTO,
            category = GalleryCategory.WEDDINGS,
            titleEn = "Kigali & Muhanga Golden Hour Wedding",
            titleRw = "Ubukwe bw'Agaciro mu Mucyo w'Izuba",
            subtitleEn = "Intimate bridal portraits with warm champagne light and timeless couture framing.",
            subtitleRw = "Amafoto y'abageni mu mucyo mwiza w'umugoroba n'imigenderanire y'agaciro.",
            location = "Muhanga, Rwanda",
            specs = "Sony Alpha • 85mm f/1.4 GM • Warm Gold LUT",
            durationOrCount = "180 Curated Photos",
            relatedService = ServiceCategory.WEDDING_PHOTOGRAPHY,
            imageRes = R.drawable.img_wedding_luxury
        ),
        GalleryItem(
            id = "photo_studio_chiaroscuro",
            mediaType = GalleryMediaType.PHOTO,
            category = GalleryCategory.STUDIO,
            titleEn = "Obsidian & Gold Studio Editorial",
            titleRw = "Amafoto yo muri Studio mu Mukara na Zahabu",
            subtitleEn = "High-contrast indoor studio portraits sculpted with dual rim lights.",
            subtitleRw = "Amafoto yo mu nzu akozwe mu buhanga bw'amatara asobanura isura neza.",
            location = "MOZZO Studio, Muhanga",
            specs = "50mm f/1.2 • Profoto Key Light • 1,500 FRW/pic",
            durationOrCount = "24 Studio Portraits",
            relatedService = ServiceCategory.INDOOR_PHOTOGRAPHY,
            imageRes = R.drawable.img_event_portrait
        ),
        GalleryItem(
            id = "photo_civil_marriage",
            mediaType = GalleryMediaType.PHOTO,
            category = GalleryCategory.WEDDINGS,
            titleEn = "Civil Marriage Eternal Vows",
            titleRw = "Gusezerana mu Murenge mu Cyubahiro",
            subtitleEn = "Complete legal ceremony coverage, ring exchange, and family celebration portraits.",
            subtitleRw = "Gufotora umuhango wo gusezerana imbere y'amategeko n'umuryango wose.",
            location = "Nyamabuye, Muhanga",
            specs = "35mm + 85mm Dual Body • 85,000 FRW Package",
            durationOrCount = "95 Edited Stills",
            relatedService = ServiceCategory.CIVIL_MARRIAGE_PHOTOGRAPHY,
            imageRes = R.drawable.img_wedding_luxury
        ),
        GalleryItem(
            id = "photo_gala_night",
            mediaType = GalleryMediaType.PHOTO,
            category = GalleryCategory.STUDIO,
            titleEn = "Black Tie Gala & Event Party",
            titleRw = "Ibirori by'Umugoroba & Event Party",
            subtitleEn = "Crisp evening party storytelling with warm ambient chandelier tones.",
            subtitleRw = "Amashusho y'ibirori byo kwizihiza umunsi mukuru mu mucyo mwiza.",
            location = "Kigali Heights & Muhanga",
            specs = "24-70mm f/2.8 GM II • 50,000 FRW Package",
            durationOrCount = "120 Event Highlights",
            relatedService = ServiceCategory.EVENTS_PHOTOGRAPHY,
            imageRes = R.drawable.img_event_portrait
        ),
        GalleryItem(
            id = "photo_outdoor_heritage",
            mediaType = GalleryMediaType.PHOTO,
            category = GalleryCategory.WEDDINGS,
            titleEn = "Outdoor Heritage & Couple Session",
            titleRw = "Amafoto yo Hanze mu Busitani",
            subtitleEn = "Open-air fine-art portrait session capturing authentic joy in natural scenery.",
            subtitleRw = "Amafoto yo hanze agaragaza ibyishimo nyakuri mu bidukikije byiza.",
            location = "Muhanga Hills, Rwanda",
            specs = "85mm f/1.4 • Natural Reflector • 2,000 FRW/pic",
            durationOrCount = "30 Outdoor Stills",
            relatedService = ServiceCategory.OUTDOOR_PHOTOGRAPHY,
            imageRes = R.drawable.img_wedding_luxury
        )
    )

    val videoProjects: List<GalleryItem> = listOf(
        GalleryItem(
            id = "video_music_gold_stage",
            mediaType = GalleryMediaType.VIDEO,
            category = GalleryCategory.CINEMA,
            titleEn = "Nights in Gold — Music Video",
            titleRw = "Amashusho y'Indirimbo — Nights in Gold",
            subtitleEn = "Synchronized dolly track and motorized gimbal choreography with tungsten haze.",
            subtitleRw = "Amashusho y'indirimbo yafashwe na kamera igendera ku murongo n'amatara ya sinema.",
            location = "MOZZO Soundstage, Muhanga",
            specs = "4K 60fps RAW • Motorized Gimbal • Anamorphic LUT",
            durationOrCount = "03:48 Cinema Master",
            relatedService = ServiceCategory.MUSIC_VIDEO_PRODUCTION,
            imageRes = R.drawable.img_cinema_production
        ),
        GalleryItem(
            id = "video_short_film",
            mediaType = GalleryMediaType.VIDEO,
            category = GalleryCategory.CINEMA,
            titleEn = "Echoes of Muhanga — Short Film",
            titleRw = "Filime Ngufi — Echoes of Muhanga",
            subtitleEn = "Narrative cinema short shot with prime lenses, atmospheric lighting, and surround mix.",
            subtitleRw = "Filime ngufi yatunganyijwe ku rwego rwo hejuru mu buryo bwa sinema.",
            location = "Muhanga & Kigali, Rwanda",
            specs = "Cinema Camera Rig • 24fps 4K • Prime Lenses",
            durationOrCount = "14:20 Short Film",
            relatedService = ServiceCategory.FILM_PRODUCTION,
            imageRes = R.drawable.img_hero_studio
        ),
        GalleryItem(
            id = "video_wedding_cinema",
            mediaType = GalleryMediaType.VIDEO,
            category = GalleryCategory.WEDDINGS,
            titleEn = "Eternal Vows — Wedding Highlight Film",
            titleRw = "Filime y'Ubukwe — Eternal Vows",
            subtitleEn = "Emotional wedding cinema reel blending clear vow audio with golden-hour sequences.",
            subtitleRw = "Amashusho y'ubukwe afite amajwi meza y'amasezerano n'imigenzo yose.",
            location = "Muhanga, Rwanda",
            specs = "Dual 4K Cameras • Lapel Audio • Drone Aerials",
            durationOrCount = "06:15 Wedding Film",
            relatedService = ServiceCategory.WEDDING_PHOTOGRAPHY,
            imageRes = R.drawable.img_wedding_luxury
        ),
        GalleryItem(
            id = "video_comedy_skit",
            mediaType = GalleryMediaType.VIDEO,
            category = GalleryCategory.CINEMA,
            titleEn = "Muhanga Chronicles — Comedy Skit",
            titleRw = "Urwenya — Muhanga Chronicles",
            subtitleEn = "Sharp two-camera comedy production with crisp dialogue audio and dynamic pacing.",
            subtitleRw = "Amashusho y'urwenya afashwe na kamera ebyiri n'amajwi asobanutse.",
            location = "Muhanga, Rwanda",
            specs = "Dual 4K Mirrorless • Wireless Boom Audio",
            durationOrCount = "04:10 Comedy Episode",
            relatedService = ServiceCategory.COMEDY_VIDEO_PRODUCTION,
            imageRes = R.drawable.img_cinema_production
        )
    )
}

enum class PaymentProvider(
    val displayName: String,
    val subtitleEn: String,
    val subtitleRw: String,
    val instructionsEn: String,
    val instructionsRw: String
) {
    MTN_MOMO(
        displayName = "MTN MoMo",
        subtitleEn = "Mobile Money • Instant Rwanda Transfer",
        subtitleRw = "MTN Mobile Money • Kwishyura Ako Kanya",
        instructionsEn = "Send to MOZZO Studio MoMo: 0798557443 (MOISE IRAKOZE), then record your reference below.",
        instructionsRw = "Ohereza kuri MoMo ya MOZZO: 0798557443 (MOISE IRAKOZE), hanyuma wandike icyemezo hano."
    ),
    AIRTEL_MONEY(
        displayName = "Airtel Money",
        subtitleEn = "Airtel Money • Fast Wallet Transfer",
        subtitleRw = "Airtel Money • Kwishyura Byihuse",
        instructionsEn = "Transfer to studio number 0798557443 and attach your Airtel Money transaction ID.",
        instructionsRw = "Ohereza kuri 0798557443 hanyuma wandike numero y'icyemezo cya Airtel Money."
    ),
    CARD_BANK_GATEWAY(
        displayName = "Bank / Card Gateway",
        subtitleEn = "Provider-Ready Visa / Mastercard / Bank",
        subtitleRw = "Banki / Ikarita (Yiteguye Guhuzwa)",
        instructionsEn = "Ready for live Flutterwave, Paypack, or Stripe provider hookup. Record bank/card reference now.",
        instructionsRw = "Yiteguye guhuzwa na Flutterwave cyangwa Paypack. Andika numero ya banki hano."
    )
}

/**
 * Clean abstraction ready to connect to live Rwandan/International payment provider SDKs
 * (e.g., MTN MoMo Collections API, Paypack, Flutterwave) without changing UI screens.
 */
interface PaymentGatewayAdapter {
    suspend fun initiateStudioPayment(
        provider: PaymentProvider,
        amountFrw: Long,
        payerAccount: String,
        bookingReference: String,
        manualTransactionRef: String
    ): PaymentExecutionResult
}

data class PaymentExecutionResult(
    val referenceCode: String,
    val status: String,
    val providerName: String
)

class StudioPaymentGateway : PaymentGatewayAdapter {
    override suspend fun initiateStudioPayment(
        provider: PaymentProvider,
        amountFrw: Long,
        payerAccount: String,
        bookingReference: String,
        manualTransactionRef: String
    ): PaymentExecutionResult {
        val normalizedRef = manualTransactionRef.trim().ifBlank {
            "PAY-${provider.name.take(3)}-${(100000..999999).random()}"
        }
        return PaymentExecutionResult(
            referenceCode = normalizedRef,
            status = "RECORDED",
            providerName = provider.displayName
        )
    }
}
