package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val MozzoLuxuryColorScheme = darkColorScheme(
    primary = ChampagneGold,
    onPrimary = ObsidianBlack,
    primaryContainer = ObsidianElevated,
    onPrimaryContainer = LuminousGold,
    secondary = LuminousGold,
    onSecondary = ObsidianBlack,
    secondaryContainer = GoldWash,
    onSecondaryContainer = ChampagneGold,
    tertiary = BronzeGold,
    onTertiary = IvoryWhite,
    background = ObsidianBlack,
    onBackground = IvoryWhite,
    surface = ObsidianSurface,
    onSurface = IvoryWhite,
    surfaceVariant = ObsidianElevated,
    onSurfaceVariant = WarmSilver,
    outline = MutedGold,
    outlineVariant = ObsidianCardBorder,
    error = CrimsonError,
    onError = IvoryWhite
)

private val MozzoShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun MozzoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MozzoLuxuryColorScheme,
        typography = MozzoTypography,
        shapes = MozzoShapes,
        content = content
    )
}
