package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.i18n.AppLanguage
import com.example.i18n.AppStrings
import com.example.model.GalleryCategory
import com.example.model.GalleryItem
import com.example.model.GalleryMediaType
import com.example.model.ServiceCategory
import com.example.model.StudioPortfolioCatalog
import com.example.ui.components.GoldHairlineDivider
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
fun GalleryScreen(
    strings: AppStrings,
    language: AppLanguage,
    onBookSimilarService: (ServiceCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMediaTab by rememberSaveable { mutableIntStateOf(0) } // 0 = Photo Gallery, 1 = Video Gallery
    var selectedCategory by rememberSaveable { mutableStateOf(GalleryCategory.ALL) }
    var activePreviewItem by remember { mutableStateOf<GalleryItem?>(null) }

    val itemsSource = if (selectedMediaTab == 0) {
        StudioPortfolioCatalog.photoProjects
    } else {
        StudioPortfolioCatalog.videoProjects
    }

    val filteredItems = remember(itemsSource, selectedCategory) {
        if (selectedCategory == GalleryCategory.ALL) {
            itemsSource
        } else {
            itemsSource.filter { it.category == selectedCategory }
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        val isWide = maxWidth >= 660.dp

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier
                        .widthIn(max = 920.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    LuxurySectionHeader(
                        eyebrow = "MOZZO PORTFOLIO",
                        title = strings.galleryHeaderTitle,
                        subtitle = strings.galleryHeaderSubtitle
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Separate Photo Gallery vs Video Gallery Switcher
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = ObsidianElevated,
                        border = BorderStroke(1.dp, ObsidianCardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(4.dp)) {
                            listOf(
                                Triple(0, strings.galleryPhotoTab, Icons.Filled.PhotoLibrary),
                                Triple(1, strings.galleryVideoTab, Icons.Filled.Videocam)
                            ).forEach { (idx, label, icon) ->
                                val active = selectedMediaTab == idx
                                Surface(
                                    onClick = {
                                        selectedMediaTab = idx
                                        selectedCategory = GalleryCategory.ALL
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (active) ChampagneGold else Color.Transparent,
                                    modifier = Modifier
                                        .weight(1f)
                                        .minimumInteractiveComponentSize()
                                        .testTag("gallery_tab_$idx")
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            tint = if (active) ObsidianBlack else WarmSilver,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
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

            // Category Filter Chips
            item {
                val categories = if (selectedMediaTab == 0) {
                    listOf(
                        GalleryCategory.ALL to strings.galleryFilterAll,
                        GalleryCategory.WEDDINGS to strings.galleryFilterWeddings,
                        GalleryCategory.STUDIO to strings.galleryFilterStudio
                    )
                } else {
                    listOf(
                        GalleryCategory.ALL to strings.galleryFilterAll,
                        GalleryCategory.CINEMA to strings.galleryFilterCinema,
                        GalleryCategory.WEDDINGS to strings.galleryFilterWeddings
                    )
                }

                LazyRow(
                    modifier = Modifier
                        .widthIn(max = 920.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categories) { (category, label) ->
                        val selected = selectedCategory == category
                        Surface(
                            onClick = { selectedCategory = category },
                            shape = RoundedCornerShape(50),
                            color = if (selected) GoldWash else ObsidianElevated,
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (selected) ChampagneGold else ObsidianCardBorder
                            ),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("gallery_filter_${category.name}")
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelLarge,
                                color = if (selected) LuminousGold else WarmSilver,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
            }

            // Responsive Project Cards
            val rows = if (isWide) filteredItems.chunked(2) else filteredItems.map { listOf(it) }
            items(rows, key = { row -> row.joinToString("_") { it.id } }) { rowItems ->
                Row(
                    modifier = Modifier
                        .widthIn(max = 920.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    rowItems.forEach { item ->
                        GalleryProjectCard(
                            item = item,
                            language = language,
                            onClick = { activePreviewItem = item },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (isWide && rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // Project Detail Lightbox / Cinema Preview Modal
        activePreviewItem?.let { preview ->
            GalleryPreviewDialog(
                item = preview,
                strings = strings,
                language = language,
                onDismiss = { activePreviewItem = null },
                onBookSimilar = {
                    val service = preview.relatedService
                    activePreviewItem = null
                    onBookSimilarService(service)
                }
            )
        }
    }
}

@Composable
private fun GalleryProjectCard(
    item: GalleryItem,
    language: AppLanguage,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LuxuryCard(
        onClick = onClick,
        modifier = modifier.testTag("gallery_card_${item.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.title(language),
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
                                    ObsidianBlack.copy(alpha = 0.25f),
                                    ObsidianSurface.copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                // Top-right badge (Shots or Video duration)
                Surface(
                    shape = RoundedCornerShape(50),
                    color = ObsidianBlack.copy(alpha = 0.85f),
                    border = BorderStroke(1.dp, ChampagneGold.copy(alpha = 0.65f)),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                ) {
                    Text(
                        text = item.durationOrCount,
                        style = MaterialTheme.typography.labelSmall,
                        color = LuminousGold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }

                // Video play emblem for Video Gallery items
                if (item.mediaType == GalleryMediaType.VIDEO) {
                    Surface(
                        shape = CircleShape,
                        color = ChampagneGold.copy(alpha = 0.92f),
                        modifier = Modifier
                            .size(54.dp)
                            .align(Alignment.Center)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Preview video project",
                                tint = ObsidianBlack,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = item.title(language),
                    style = MaterialTheme.typography.headlineMedium,
                    color = IvoryWhite
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.subtitle(language),
                    style = MaterialTheme.typography.bodyMedium,
                    color = WarmSilver
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = ChampagneGold,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = item.location,
                        style = MaterialTheme.typography.labelMedium,
                        color = ChampagneGold
                    )
                }
            }
        }
    }
}

@Composable
private fun GalleryPreviewDialog(
    item: GalleryItem,
    strings: AppStrings,
    language: AppLanguage,
    onDismiss: () -> Unit,
    onBookSimilar: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .widthIn(max = 560.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            color = ObsidianSurface,
            border = BorderStroke(1.2.dp, ChampagneGold.copy(alpha = 0.8f))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                ) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.title(language),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        ObsidianBlack.copy(alpha = 0.35f),
                                        Color.Transparent,
                                        ObsidianSurface
                                    )
                                )
                            )
                    )
                    Surface(
                        shape = CircleShape,
                        color = ObsidianBlack.copy(alpha = 0.78f),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                    ) {
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.testTag("gallery_preview_close_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = strings.galleryClosePreview,
                                tint = IvoryWhite
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = ObsidianBlack.copy(alpha = 0.88f),
                        border = BorderStroke(1.dp, ChampagneGold),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (item.mediaType == GalleryMediaType.VIDEO) {
                                    Icons.Filled.Videocam
                                } else {
                                    Icons.Filled.CameraAlt
                                },
                                contentDescription = null,
                                tint = LuminousGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = item.durationOrCount,
                                style = MaterialTheme.typography.labelMedium,
                                color = LuminousGold
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = item.title(language),
                        style = MaterialTheme.typography.headlineLarge,
                        color = IvoryWhite
                    )
                    Text(
                        text = item.subtitle(language),
                        style = MaterialTheme.typography.bodyLarge,
                        color = WarmSilver
                    )
                    GoldHairlineDivider()
                    Text(
                        text = strings.gallerySpecsLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = ChampagneGold
                    )
                    Text(
                        text = "${item.specs} • ${item.location}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = IvoryWhite
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    GoldPrimaryButton(
                        text = strings.galleryBookSimilar,
                        onClick = onBookSimilar,
                        icon = Icons.Filled.CalendarMonth,
                        testTag = "gallery_book_similar_btn",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
