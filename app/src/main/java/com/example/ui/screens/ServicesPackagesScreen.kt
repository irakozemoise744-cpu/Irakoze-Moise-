package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.i18n.AppLanguage
import com.example.i18n.AppStrings
import com.example.model.PhotographyPackage
import com.example.model.PriceCalculatorState
import com.example.model.ServiceCategory
import com.example.model.formatFrw
import com.example.ui.components.GoldHairlineDivider
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.GoldPrimaryButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.GoldWash
import com.example.ui.theme.IvoryWhite
import com.example.ui.theme.LuminousGold
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.WarmSilver

@Composable
fun ServicesPackagesScreen(
    strings: AppStrings,
    language: AppLanguage,
    calculatorState: PriceCalculatorState,
    onUpdateIndoorCount: (Int) -> Unit,
    onUpdateOutdoorCount: (Int) -> Unit,
    onToggleEventPurpose: () -> Unit,
    onToggleEventParty: () -> Unit,
    onToggleCivilMarriage: () -> Unit,
    onResetCalculator: () -> Unit,
    onBookService: (ServiceCategory) -> Unit,
    onBookCalculatedPackage: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        val isWide = maxWidth >= 660.dp

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier
                        .widthIn(max = 920.dp)
                        .fillMaxWidth()
                ) {
                    LuxurySectionHeader(
                        eyebrow = "MOZZO STUDIO",
                        title = strings.servicesHeaderTitle,
                        subtitle = strings.servicesHeaderSubtitle
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Segmented Switcher: 8 Services | Packages & Calculator
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = ObsidianElevated,
                        border = BorderStroke(1.dp, ObsidianCardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(4.dp)) {
                            listOf(
                                strings.tabAllServices,
                                strings.tabPackagesCalculator
                            ).forEachIndexed { idx, label ->
                                val active = selectedTab == idx
                                Surface(
                                    onClick = { selectedTab = idx },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (active) ChampagneGold else Color.Transparent,
                                    modifier = Modifier
                                        .weight(1f)
                                        .minimumInteractiveComponentSize()
                                        .testTag("services_tab_$idx")
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelLarge,
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
            }

            if (selectedTab == 0) {
                val servicePairs = if (isWide) {
                    ServiceCategory.entries.chunked(2)
                } else {
                    ServiceCategory.entries.map { listOf(it) }
                }

                items(servicePairs) { rowItems ->
                    Row(
                        modifier = Modifier
                            .widthIn(max = 920.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        rowItems.forEach { service ->
                            ServiceDetailCard(
                                service = service,
                                language = language,
                                bookLabel = strings.btnBookNow,
                                onBookClick = { onBookService(service) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (isWide && rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                // Quick bridge CTA at bottom of Services tab to jump straight into FRW Calculator
                item {
                    Column(
                        modifier = Modifier
                            .widthIn(max = 920.dp)
                            .fillMaxWidth()
                    ) {
                        GoldOutlinedButton(
                            text = strings.tabPackagesCalculator,
                            onClick = { selectedTab = 1 },
                            icon = Icons.Filled.Calculate,
                            testTag = "jump_to_calculator_btn",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                // Photography Packages (FRW) + Automatic Price Calculator
                item {
                    Column(
                        modifier = Modifier
                            .widthIn(max = 920.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = strings.packagesSectionTitle,
                            style = MaterialTheme.typography.headlineLarge,
                            color = LuminousGold
                        )
                        Text(
                            text = strings.packagesSectionSubtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = WarmSilver
                        )

                        PhotographyPackage.entries.forEach { pkg ->
                            LuxuryCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("package_card_${pkg.name}")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = pkg.title(language),
                                            style = MaterialTheme.typography.titleLarge,
                                            color = IvoryWhite
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = pkg.details(language),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = WarmSilver
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Surface(
                                        color = GoldWash,
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.6f))
                                    ) {
                                        Text(
                                            text = pkg.rateLabel(language),
                                            style = MaterialTheme.typography.labelLarge,
                                            color = LuminousGold,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Interactive Automatic Price Calculator
                item {
                    LuxuryCard(
                        highlighted = true,
                        modifier = Modifier
                            .widthIn(max = 920.dp)
                            .fillMaxWidth()
                            .testTag("price_calculator_card")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Calculate,
                                        contentDescription = null,
                                        tint = ChampagneGold,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = strings.calculatorTitle,
                                            style = MaterialTheme.typography.headlineMedium,
                                            color = IvoryWhite
                                        )
                                        Text(
                                            text = strings.calculatorSubtitle,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = WarmSilver
                                        )
                                    }
                                }
                                TextButton(
                                    onClick = onResetCalculator,
                                    modifier = Modifier.testTag("calculator_reset_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Refresh,
                                        contentDescription = strings.calcResetLabel,
                                        tint = ChampagneGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = strings.calcResetLabel,
                                        color = ChampagneGold,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }

                            GoldHairlineDivider()

                            // Indoor Pictures Stepper (1,500 FRW / picture)
                            PictureQuantityRow(
                                title = strings.calcIndoorLabel,
                                rateText = "1,500 ${strings.calcPerPictureSuffix}",
                                count = calculatorState.indoorPictureCount,
                                subtotalFrw = calculatorState.indoorSubtotalFrw,
                                onDecrement = { onUpdateIndoorCount(-1) },
                                onIncrement = { onUpdateIndoorCount(1) },
                                tagPrefix = "calc_indoor"
                            )

                            // Outdoor Pictures Stepper (2,000 FRW / picture)
                            PictureQuantityRow(
                                title = strings.calcOutdoorLabel,
                                rateText = "2,000 ${strings.calcPerPictureSuffix}",
                                count = calculatorState.outdoorPictureCount,
                                subtotalFrw = calculatorState.outdoorSubtotalFrw,
                                onDecrement = { onUpdateOutdoorCount(-1) },
                                onIncrement = { onUpdateOutdoorCount(1) },
                                tagPrefix = "calc_outdoor"
                            )

                            GoldHairlineDivider()

                            Text(
                                text = strings.calcEventPackagesLabel,
                                style = MaterialTheme.typography.titleMedium,
                                color = LuminousGold
                            )

                            PackageToggleRow(
                                title = PhotographyPackage.EVENT_PURPOSE.title(language),
                                priceFrw = PhotographyPackage.EVENT_PURPOSE.priceFrw,
                                checked = calculatorState.includeEventPurpose,
                                onToggle = onToggleEventPurpose,
                                testTag = "calc_toggle_event_purpose"
                            )

                            PackageToggleRow(
                                title = PhotographyPackage.EVENT_PARTY.title(language),
                                priceFrw = PhotographyPackage.EVENT_PARTY.priceFrw,
                                checked = calculatorState.includeEventParty,
                                onToggle = onToggleEventParty,
                                testTag = "calc_toggle_event_party"
                            )

                            PackageToggleRow(
                                title = PhotographyPackage.CIVIL_MARRIAGE.title(language),
                                priceFrw = PhotographyPackage.CIVIL_MARRIAGE.priceFrw,
                                checked = calculatorState.includeCivilMarriage,
                                onToggle = onToggleCivilMarriage,
                                testTag = "calc_toggle_civil_marriage"
                            )

                            // Total Output Box
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = ObsidianBlack,
                                border = BorderStroke(1.2.dp, ChampagneGold.copy(alpha = 0.7f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = strings.calcEstimatedTotalLabel,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = WarmSilver
                                    )
                                    AnimatedContent(
                                        targetState = calculatorState.totalFrw,
                                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                                        label = "totalFrw"
                                    ) { total ->
                                        Text(
                                            text = formatFrw(total),
                                            style = MaterialTheme.typography.displayMedium,
                                            color = LuminousGold,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.testTag("calculator_total_text")
                                        )
                                    }
                                    Text(
                                        text = "${strings.calcBreakdownFormat}: ${calculatorState.buildSummary(language)}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = WarmSilver
                                    )
                                }
                            }

                            GoldPrimaryButton(
                                text = strings.calcBookPackageCta,
                                onClick = onBookCalculatedPackage,
                                icon = Icons.Filled.CalendarMonth,
                                testTag = "calculator_book_cta",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceDetailCard(
    service: ServiceCategory,
    language: AppLanguage,
    bookLabel: String,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LuxuryCard(
        modifier = modifier.testTag("service_card_${service.name}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
            ) {
                Image(
                    painter = painterResource(id = service.imageRes),
                    contentDescription = service.title(language),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, ObsidianSurface.copy(alpha = 0.92f))
                            )
                        )
                )
                Surface(
                    shape = RoundedCornerShape(50),
                    color = ObsidianBlack.copy(alpha = 0.85f),
                    border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.6f)),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                ) {
                    Text(
                        text = service.badge(language),
                        style = MaterialTheme.typography.labelSmall,
                        color = LuminousGold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = service.title(language),
                    style = MaterialTheme.typography.headlineMedium,
                    color = IvoryWhite
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = service.description(language),
                    style = MaterialTheme.typography.bodyMedium,
                    color = WarmSilver
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = service.priceHint(language),
                        style = MaterialTheme.typography.labelLarge,
                        color = ChampagneGold
                    )
                    GoldOutlinedButton(
                        text = bookLabel,
                        onClick = onBookClick,
                        testTag = "book_service_${service.name}"
                    )
                }
            }
        }
    }
}

@Composable
private fun PictureQuantityRow(
    title: String,
    rateText: String,
    count: Int,
    subtotalFrw: Long,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    tagPrefix: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = IvoryWhite
            )
            Text(
                text = "$rateText • ${formatFrw(subtotalFrw)}",
                style = MaterialTheme.typography.bodyMedium,
                color = ChampagneGold
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = ObsidianBlack,
                border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.5f))
            ) {
                IconButton(
                    onClick = onDecrement,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("${tagPrefix}_minus")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "Decrease $title",
                        tint = LuminousGold
                    )
                }
            }
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = IvoryWhite,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .testTag("${tagPrefix}_count")
            )
            Surface(
                shape = CircleShape,
                color = ChampagneGold
            ) {
                IconButton(
                    onClick = onIncrement,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("${tagPrefix}_plus")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Increase $title",
                        tint = ObsidianBlack
                    )
                }
            }
        }
    }
}

@Composable
private fun PackageToggleRow(
    title: String,
    priceFrw: Long,
    checked: Boolean,
    onToggle: () -> Unit,
    testTag: String
) {
    Surface(
        onClick = onToggle,
        shape = RoundedCornerShape(12.dp),
        color = if (checked) GoldWash else ObsidianBlack,
        border = BorderStroke(
            width = 1.dp,
            color = if (checked) ChampagneGold else ObsidianCardBorder
        ),
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = if (checked) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = if (checked) LuminousGold else WarmSilver,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = IvoryWhite
                )
            }
            Text(
                text = formatFrw(priceFrw),
                style = MaterialTheme.typography.labelLarge,
                color = LuminousGold,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
