package com.radjamahesaw0054.belanjabijak.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.radjamahesaw0054.belanjabijak.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareProductScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var hargaA by remember { mutableStateOf("") }
    var ukuranA by remember { mutableStateOf("") }
    var hargaB by remember { mutableStateOf("") }
    var ukuranB by remember { mutableStateOf("") }

    val placeholder = stringResource(R.string.compare_placeholder)
    val formatA = stringResource(R.string.result_product_a_cheaper)
    val formatB = stringResource(R.string.result_product_b_cheaper)
    val formatEqual = stringResource(R.string.result_equal)

    val hasilAnalisis = remember(hargaA, ukuranA, hargaB, ukuranB) {
        val hA = hargaA.toDoubleOrNull() ?: 0.0
        val uA = ukuranA.toDoubleOrNull() ?: 1.0
        val hB = hargaB.toDoubleOrNull() ?: 0.0
        val uB = ukuranB.toDoubleOrNull() ?: 1.0

        if (hA > 0.0 && hB > 0.0 && uA > 0.0 && uB > 0.0) {
            val unitCostA = hA / uA
            val unitCostB = hB / uB
            when {
                unitCostA < unitCostB -> String.format(formatA, unitCostA)
                unitCostB < unitCostA -> String.format(formatB, unitCostB)
                else -> formatEqual
            }
        } else {
            placeholder
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_compare)) },
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
            Text(
                text = stringResource(R.string.compare_subtitle),
                style = MaterialTheme.typography.titleMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.product_a),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = hargaA,
                            onValueChange = { hargaA = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.label_price),
                                    fontSize = 11.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = ukuranA,
                            onValueChange = { ukuranA = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.label_size),
                                    fontSize = 11.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.product_b),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = hargaB,
                            onValueChange = { hargaB = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.label_price),
                                    fontSize = 11.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = ukuranB,
                            onValueChange = { ukuranB = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.label_size),
                                    fontSize = 11.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = hasilAnalisis,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}