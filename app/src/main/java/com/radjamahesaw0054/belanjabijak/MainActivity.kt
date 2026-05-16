package com.radjamahesaw0054.belanjabijak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumberedRtl
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.radjamahesaw0054.belanjabijak.database.BelanjaDb
import com.radjamahesaw0054.belanjabijak.navigation.Screen
import com.radjamahesaw0054.belanjabijak.navigation.SetupNavGraph
import com.radjamahesaw0054.belanjabijak.ui.theme.BelanjaBijakTheme
import com.radjamahesaw0054.belanjabijak.ui.util.SettingsPreferences
import com.radjamahesaw0054.belanjabijak.ui.util.ViewModelFactory
import com.radjamahesaw0054.belanjabijak.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val database = BelanjaDb.getInstance(applicationContext)
            val preferences = SettingsPreferences(applicationContext)
            val factory = ViewModelFactory(database.dao, preferences)

            val viewModel: MainViewModel = viewModel(factory = factory)
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            BelanjaBijakTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                                label = {
                                    Text(
                                        text = stringResource(R.string.title_home),
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                selected = currentRoute == Screen.Home.route,
                                onClick = {
                                    if (currentRoute != Screen.Home.route) {
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )

                            NavigationBarItem(
                                icon = { Icon(Icons.Default.FormatListNumberedRtl, contentDescription = null) },
                                label = {
                                    Text(
                                        text = stringResource(R.string.title_history),
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                selected = currentRoute == Screen.PurchaseHistoryScreen.route,
                                onClick = {
                                    if (currentRoute != Screen.PurchaseHistoryScreen.route) {
                                        navController.navigate(Screen.PurchaseHistoryScreen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )

                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.compare),
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(R.string.title_compare),
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                selected = currentRoute == Screen.CompareProduct.route,
                                onClick = {
                                    if (currentRoute != Screen.CompareProduct.route) {
                                        navController.navigate(Screen.CompareProduct.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    SetupNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}