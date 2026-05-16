package com.radjamahesaw0054.belanjabijak.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.radjamahesaw0054.belanjabijak.ui.screen.AddPurchaseScreen
import com.radjamahesaw0054.belanjabijak.ui.screen.CompareProductScreen
import com.radjamahesaw0054.belanjabijak.ui.screen.EditPurchaseScreen
import com.radjamahesaw0054.belanjabijak.ui.screen.HomeScreen
import com.radjamahesaw0054.belanjabijak.ui.screen.PurchaseHistoryScreen
import com.radjamahesaw0054.belanjabijak.ui.screen.RecycleBinScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.AddPurchase.route) {
            AddPurchaseScreen(navController = navController)
        }

        composable(
            route = Screen.EditPurchase.route,
            arguments = listOf(
                navArgument(KEY_ID_PURCHASE) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(KEY_ID_PURCHASE) ?: -1L
            EditPurchaseScreen(navController = navController, idPurchase = id)
        }

        composable(route = Screen.PurchaseHistoryScreen.route) {
            PurchaseHistoryScreen(navController = navController)
        }

        composable(route = Screen.CompareProduct.route) {
            CompareProductScreen(navController = navController)
        }

        composable(route = Screen.RecycleBin.route) {
            RecycleBinScreen(navController = navController)
        }
    }
}

