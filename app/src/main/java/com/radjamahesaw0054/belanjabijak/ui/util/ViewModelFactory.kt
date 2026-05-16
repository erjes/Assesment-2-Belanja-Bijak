package com.radjamahesaw0054.belanjabijak.ui.util


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.radjamahesaw0054.belanjabijak.database.BelanjaDao
import com.radjamahesaw0054.belanjabijak.ui.viewmodel.MainViewModel

class ViewModelFactory(
    private val dao: BelanjaDao,
    private val preferences: SettingsPreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao, preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}