package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.i18n.AppLanguage
import com.example.i18n.AppStrings
import com.example.model.PhotographyPackage
import com.example.model.ServiceCategory
import com.example.ui.components.GoldHairlineDivider
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.GoldPrimaryButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.MozzoLogoBadge
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.GoldWash
import com.example.ui.theme.IvoryWhite
import com.example.ui.theme.LuminousGold
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.WarmSilver

@Composable
fun HomeScreen(
    strings: AppStrings,
    language: AppLanguage,
    onExploreWork: () -> Unit,
    onBookNow: () -> Unit,
    onContactUs: () -> Unit,
    onOpenServicesAndPackages: () -> Unit,
    onSelectServiceToBook: (ServiceCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        val isWide = maxWidth >= 600.dp

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Luxury Hero Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isWide) 460.dp else 510.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hero_studio),
                        contentDescription = "MOZZO luxury cinema and photography studio",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    // Deep luxury obsidian & golden scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        ObsidianBlack.copy(alpha = 0.55f),
                                        ObsidianBlack.copy(alpha = 0.72f),
                                        ObsidianBlack.copy(alpha = 0.98f)
                                    )
                                )
                            )
                    )

                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(tween(550)) + slideInVertically(tween(550)) { it / 8 },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .widthIn(max = 860.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 22.dp)
                    ) {
                        Column(
                            horizontalAlignment = if (isWide) Alignment.Start else Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                MozzoLogoBadge(size = 64.dp)
                                Column {
                                    Text(
                                        text = strings.homeEyebrow,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = LuminousGold,
                                        letterSpacing = 1.6.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "MOZZO PHOTOGRAPHY & VIDEOGRAPHY",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = IvoryWhite,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = strings.homeWelcomeTitle,
                                style = MaterialTheme.typography.displayLarge,
                                color = IvoryWhite
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = strings.homeWelcomeBody,
                                style = MaterialTheme.typography.bodyLarge,
                                color = WarmSilver
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Requested Home Screen Buttons: Explore Our Work, Book Now, Contact Us
                            if (isWide) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    GoldPrimaryButton(
                                        text = strings.btnExploreWork,
                                        onClick = onExploreWork,
                                        icon = Icons.Filled.Collections,
                                        testTag = "home_explore_work_button",
                                        modifier = Modifier.weight(1f)
                                    )
                                    GoldPrimaryButton(
                                        text = strings.btnBookNow,
                                        onClick = onBookNow,
                                        icon = Icons.Filled.CalendarMonth,
                                        testTag = "home_book_now_button",
                                        modifier = Modifier.weight(1f)
                                    )
                                    GoldOutlinedButton(
                                        text = strings.btnContactUs,
                                        onClick = onContactUs,
                                        icon = Icons.Filled.Phone,
                                        testTag = "home_contact_us_button",
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            } else {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        GoldPrimaryButton(
                                            text = strings.btnExploreWork,
                                            onClick = onExploreWork,
                                            icon = Icons.Filled.Collections,
                                            testTag = "home_explore_work_button",
                                            modifier = Modifier.weight(1f)
                                        )
                                        GoldPrimaryButton(
                                            text = strings.btnBookNow,
                                            onClick = onBookNow,
                                            icon = Icons.Filled.CalendarMonth,
                                            testTag = "home_book_now_button",
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    GoldOutlinedButton(
                                        text = strings.btnContactUs,
                                        onClick = onContactUs,
                                        icon = Icons.Filled.Phone,
                                        testTag = "home_contact_us_button",
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Studio Credibility Strip
            item {
                Column(
                    modifier = Modifier
                        .widthIn(max = 860.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    GoldHairlineDivider()
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TrustPill(
                            icon = Icons.Filled.LocationOn,
                            label = strings.homeTrustMuhanga,
                            modifier = Modifier.weight(1f)
                        )
                        TrustPill(
                            icon = Icons.Filled.Movie,
                            label = strings.homeTrustCinema,
                            modifier = Modifier.weight(1f)
                        )
                        TrustPill(
                            icon = Icons.Filled.Verified,
                            label = strings.homeTrustDelivery,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Signature Craftsmanship Horizontal Carousel
            item {
                Column(
                    modifier = Modifier
                        .widthIn(max = 860.dp)
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = strings.homeSignatureServicesTitle,
                            style = MaterialTheme.typography.headlineLarge,
                            color = IvoryWhite,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            onClick = onOpenServicesAndPackages,
                            color = Color.Transparent,
                            modifier = Modifier.testTag("home_view_all_services_btn")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = "8",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = ChampagneGold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = strings.homeViewAllServices,
                                    tint = ChampagneGold,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(ServiceCategory.entries) { service ->
                            LuxuryCard(
                                onClick = { onSelectServiceToBook(service) },
                                modifier = Modifier
                                    .width(240.dp)
                                    .testTag("home_service_card_${service.name}")
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(132.dp)
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
                                                        listOf(
                                                            Color.Transparent,
                                                            ObsidianSurface.copy(alpha = 0.85f)
                                                        )
                                                    )
                                                )
                                        )
                                        Surface(
                                            color = ObsidianBlack.copy(alpha = 0.82f),
                                            shape = RoundedCornerShape(50),
                                            border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.55f)),
                                            modifier = Modifier
                                                .align(Alignment.TopStart)
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
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text(
                                            text = service.title(language),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = IvoryWhite,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = service.priceHint(language),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = ChampagneGold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Transparent FRW Rates Preview Card
            item {
                Column(
                    modifier = Modifier
                        .widthIn(max = 860.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 22.dp)
                ) {
                    LuxuryCard(
                        highlighted = true,
                        onClick = onOpenServicesAndPackages,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_packages_preview_card")
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.CameraAlt,
                                        contentDescription = null,
                                        tint = ChampagneGold,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = strings.homePackagesPreviewTitle,
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = LuminousGold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            PhotographyPackage.entries.forEach { pkg ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = pkg.title(language),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = IvoryWhite,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = pkg.rateLabel(language),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = ChampagneGold,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            GoldOutlinedButton(
                                text = strings.homeViewAllServices,
                                onClick = onOpenServicesAndPackages,
                                icon = Icons.AutoMirrored.Filled.ArrowForward,
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
private fun TrustPill(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = ObsidianElevated,
        border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.25f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ChampagneGold,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = IvoryWhite,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
