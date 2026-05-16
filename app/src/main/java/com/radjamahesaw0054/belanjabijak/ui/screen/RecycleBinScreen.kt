package com.radjamahesaw0054.belanjabijak.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.radjamahesaw0054.belanjabijak.R
import com.radjamahesaw0054.belanjabijak.database.BelanjaDb
import com.radjamahesaw0054.belanjabijak.model.Pengeluaran
import com.radjamahesaw0054.belanjabijak.ui.util.SettingsPreferences
import com.radjamahesaw0054.belanjabijak.ui.util.ViewModelFactory
import com.radjamahesaw0054.belanjabijak.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val database = BelanjaDb.getInstance(context)
    val preferences = SettingsPreferences(context)
    val factory = ViewModelFactory(database.dao, preferences)
    val viewModel: MainViewModel = viewModel(factory = factory)

    val recycleBinList by viewModel.listRecycleBin.collectAsState()

    var openDialogConfirm by remember { mutableStateOf(false) }
    var itemToBeDeleted by remember { mutableStateOf<Pengeluaran?>(null) }

    if (openDialogConfirm && itemToBeDeleted != null) {
        DisplayAlertDialog(
            onDismissRequest = {
                openDialogConfirm = false
                itemToBeDeleted = null
            },
            onConfirmation = {
                itemToBeDeleted?.let { item ->
                    viewModel.hapusPermanenPengeluaran(item)
                }
                openDialogConfirm = false
                itemToBeDeleted = null
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.recycle_bin)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.btn_back)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (recycleBinList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.empty_recycle_bin),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
            ) {
                items(recycleBinList, key = { it.id }) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.namaBarang,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Rp ${item.harga * item.jumlah} (${item.jumlah} pcs)",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Row {
                                IconButton(onClick = { viewModel.pulihkanDariRecycleBin(item) }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_restore),
                                        contentDescription = stringResource(R.string.restore_data),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        itemToBeDeleted = item
                                        openDialogConfirm = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = stringResource(R.string.permantent_delete),
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}