package com.example.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.i18n.stringsFor
import com.example.ui.components.GoldHairlineDivider
import com.example.ui.components.MozzoTopAppBar
import com.example.ui.screens.BookingPaymentScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.GalleryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ServicesPackagesScreen
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.GoldWash
import com.example.ui.theme.IvoryWhite
import com.example.ui.theme.LuminousGold
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.WarmSilver
import com.example.viewmodel.MozzoViewModel
import kotlinx.coroutines.launch

const val ROUTE_HOME = "home"
const val ROUTE_SERVICES = "services"
const val ROUTE_GALLERY = "gallery"
const val ROUTE_BOOKING = "booking"
const val ROUTE_CONTACT = "contact"

private data class NavDestinationItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun MozzoAppShell(
    viewModel: MozzoViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsStateWithLifecycle()
    val calculatorState by viewModel.calculatorState.collectAsStateWithLifecycle()
    val bookingForm by viewModel.bookingForm.collectAsStateWithLifecycle()
    val paymentForm by viewModel.paymentForm.collectAsStateWithLifecycle()
    val savedBookings by viewModel.savedBookings.collectAsStateWithLifecycle()
    val bookingSubTab by viewModel.bookingSubTab.collectAsStateWithLifecycle()

    val strings = remember(language) { stringsFor(language) }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val destinations = remember(strings) {
        listOf(
            NavDestinationItem(ROUTE_HOME, strings.navHome, Icons.Filled.Home, Icons.Outlined.Home),
            NavDestinationItem(ROUTE_SERVICES, strings.navServices, Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome),
            NavDestinationItem(ROUTE_GALLERY, strings.navGallery, Icons.Filled.Collections, Icons.Outlined.Collections),
            NavDestinationItem(ROUTE_BOOKING, strings.navBooking, Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth),
            NavDestinationItem(ROUTE_CONTACT, strings.navContact, Icons.Filled.Phone, Icons.Outlined.Phone)
        )
    }

    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun showMessage(msg: String) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(msg)
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        val useNavigationRail = maxWidth >= 600.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = ObsidianBlack,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                MozzoTopAppBar(
                    strings = strings,
                    currentLanguage = language,
                    onSelectLanguage = viewModel::setLanguage
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = ObsidianElevated,
                        contentColor = LuminousGold,
                        actionColor = ChampagneGold
                    )
                }
            },
            bottomBar = {
                if (!useNavigationRail) {
                    Column(
                        modifier = Modifier
                            .background(ObsidianSurface)
                            .navigationBarsPadding()
                    ) {
                        GoldHairlineDivider()
                        NavigationBar(
                            containerColor = ObsidianSurface,
                            contentColor = IvoryWhite,
                            tonalElevation = 0.dp,
                            windowInsets = WindowInsets(0, 0, 0, 0)
                        ) {
                            destinations.forEach { item ->
                                val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { navigateTo(item.route) },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = item.label
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = item.label,
                                            style = MaterialTheme.typography.labelSmall,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = LuminousGold,
                                        selectedTextColor = LuminousGold,
                                        unselectedIconColor = WarmSilver,
                                        unselectedTextColor = WarmSilver,
                                        indicatorColor = GoldWash
                                    ),
                                    modifier = Modifier.testTag("nav_item_${item.route}")
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (useNavigationRail) {
                    NavigationRail(
                        containerColor = ObsidianSurface,
                        contentColor = IvoryWhite,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        destinations.forEach { item ->
                            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateTo(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                label = {
                                    Text(
                                        text = item.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        maxLines = 1
                                    )
                                },
                                colors = NavigationRailItemDefaults.colors(
                                    selectedIconColor = LuminousGold,
                                    selectedTextColor = LuminousGold,
                                    unselectedIconColor = WarmSilver,
                                    unselectedTextColor = WarmSilver,
                                    indicatorColor = GoldWash
                                ),
                                modifier = Modifier.testTag("rail_item_${item.route}")
                            )
                        }
                    }
                }

                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_HOME,
                        enterTransition = {
                            fadeIn(tween(240)) + slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up,
                                tween(240)
                            ) { it / 18 }
                        },
                        exitTransition = { fadeOut(tween(180)) }
                    ) {
                        composable(ROUTE_HOME) {
                            HomeScreen(
                                strings = strings,
                                language = language,
                                onExploreWork = { navigateTo(ROUTE_GALLERY) },
                                onBookNow = { navigateTo(ROUTE_BOOKING) },
                                onContactUs = { navigateTo(ROUTE_CONTACT) },
                                onOpenServicesAndPackages = { navigateTo(ROUTE_SERVICES) },
                                onSelectServiceToBook = { service ->
                                    viewModel.prefillBookingFromService(service)
                                    navigateTo(ROUTE_BOOKING)
                                }
                            )
                        }

                        composable(ROUTE_SERVICES) {
                            ServicesPackagesScreen(
                                strings = strings,
                                language = language,
                                calculatorState = calculatorState,
                                onUpdateIndoorCount = viewModel::updateIndoorCount,
                                onUpdateOutdoorCount = viewModel::updateOutdoorCount,
                                onToggleEventPurpose = viewModel::toggleEventPurpose,
                                onToggleEventParty = viewModel::toggleEventParty,
                                onToggleCivilMarriage = viewModel::toggleCivilMarriage,
                                onResetCalculator = viewModel::resetCalculator,
                                onBookService = { service ->
                                    viewModel.prefillBookingFromService(service)
                                    navigateTo(ROUTE_BOOKING)
                                },
                                onBookCalculatedPackage = {
                                    viewModel.prefillBookingFromCalculator()
                                    navigateTo(ROUTE_BOOKING)
                                }
                            )
                        }

                        composable(ROUTE_GALLERY) {
                            GalleryScreen(
                                strings = strings,
                                language = language,
                                onBookSimilarService = { service ->
                                    viewModel.prefillBookingFromService(service)
                                    navigateTo(ROUTE_BOOKING)
                                }
                            )
                        }

                        composable(ROUTE_BOOKING) {
                            BookingPaymentScreen(
                                strings = strings,
                                language = language,
                                subTab = bookingSubTab,
                                onSelectSubTab = viewModel::setBookingSubTab,
                                bookingForm = bookingForm,
                                paymentForm = paymentForm,
                                savedBookings = savedBookings,
                                onFullNameChange = viewModel::onFullNameChange,
                                onPhoneChange = viewModel::onPhoneChange,
                                onEmailChange = viewModel::onEmailChange,
                                onServiceSelected = viewModel::onServiceSelected,
                                onEventDateSelected = viewModel::onEventDateSelected,
                                onLocationChange = viewModel::onLocationChange,
                                onMessageChange = viewModel::onMessageChange,
                                onClearEstimate = viewModel::clearAttachedEstimate,
                                onSubmitBooking = { onSaved ->
                                    viewModel.submitBooking(onSaved)
                                },
                                onDeleteBooking = viewModel::deleteBooking,
                                onSelectBookingForPayment = viewModel::selectBookingForPayment,
                                onPaymentProviderSelected = viewModel::onPaymentProviderSelected,
                                onPaymentModeSelected = viewModel::onPaymentModeSelected,
                                onPaymentAmountChange = viewModel::onPaymentAmountChange,
                                onPayerAccountChange = viewModel::onPayerAccountChange,
                                onPaymentRefChange = viewModel::onPaymentManualRefChange,
                                onSubmitPayment = { onRecorded ->
                                    viewModel.submitPayment { result, amount, ref ->
                                        onRecorded(result.referenceCode, amount, ref)
                                    }
                                },
                                onShowMessage = ::showMessage
                            )
                        }

                        composable(ROUTE_CONTACT) {
                            ContactScreen(
                                strings = strings,
                                currentLanguage = language,
                                onSelectLanguage = viewModel::setLanguage,
                                onShowMessage = ::showMessage
                            )
                        }
                    }
                }
            }
        }
    }
}
