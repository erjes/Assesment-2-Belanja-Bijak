package com.radjamahesaw0054.belanjabijak.navigation

const val KEY_ID_PURCHASE = "idPurchase"

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object AddPurchase: Screen("AddPurchaseScreen")
    data object EditPurchase: Screen("EditPurchaseScreen/{${KEY_ID_PURCHASE}}"){
        fun withId(id: Long) = "EditPurchaseScreen/$id"
    }
    data object PurchaseHistoryScreen: Screen("PurchaseHistoryScreen")
    data object CompareProduct: Screen("CompareProductScreen")
    data object RecycleBin: Screen("RecycleBinScreen")
}