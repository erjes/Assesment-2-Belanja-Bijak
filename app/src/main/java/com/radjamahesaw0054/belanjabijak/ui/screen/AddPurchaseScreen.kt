package com.radjamahesaw0054.belanjabijak.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.radjamahesaw0054.belanjabijak.R
import com.radjamahesaw0054.belanjabijak.database.BelanjaDb
import com.radjamahesaw0054.belanjabijak.ui.util.SettingsPreferences
import com.radjamahesaw0054.belanjabijak.ui.util.ViewModelFactory
import com.radjamahesaw0054.belanjabijak.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPurchaseScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val database = BelanjaDb.getInstance(context)
    val preferences = SettingsPreferences(context)
    val factory = ViewModelFactory(database.dao, preferences)
    val viewModel: MainViewModel = viewModel(factory = factory)

    var namaBarang by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }

    val kategoriKeys = listOf("PRIMER", "SEKUNDER", "TERSIER")
    val kategoriDisplayOptions = listOf(
        stringResource(R.string.cat_primer),
        stringResource(R.string.cat_sekunder),
        stringResource(R.string.cat_tersier)
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedDisplayKategori by remember { mutableStateOf(kategoriDisplayOptions[0]) }
    var selectedKategoriKey by remember { mutableStateOf(kategoriKeys[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_add)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = namaBarang,
                onValueChange = { namaBarang = it },
                label = { Text(stringResource(R.string.label_product_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = harga,
                    onValueChange = { harga = it },
                    label = { Text(stringResource(R.string.label_price)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = jumlah,
                    onValueChange = { jumlah = it },
                    label = { Text(stringResource(R.string.label_quantity)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedDisplayKategori,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.label_category)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    kategoriDisplayOptions.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedDisplayKategori = option
                                selectedKategoriKey = kategoriKeys[index]
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val nominalHarga = harga.toDoubleOrNull()
                    val totalJumlah = jumlah.toIntOrNull()

                    if (namaBarang.isBlank() || nominalHarga == null || totalJumlah == null) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_empty_fields),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val formatBulanSistem = SimpleDateFormat("yyyy-MM", Locale.getDefault())
                        val tanggalBulanSekarang = formatBulanSistem.format(Date())

                        viewModel.tambahPengeluaran(
                            nama = namaBarang,
                            harga = nominalHarga,
                            jumlah = totalJumlah,
                            kategori = selectedKategoriKey,
                            tanggal = tanggalBulanSekarang
                        )

                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(stringResource(R.string.btn_save))
            }
        }
    }
}