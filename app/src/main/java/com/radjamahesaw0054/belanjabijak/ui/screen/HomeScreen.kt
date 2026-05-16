package com.radjamahesaw0054.belanjabijak.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.radjamahesaw0054.belanjabijak.R
import com.radjamahesaw0054.belanjabijak.database.BelanjaDb
import com.radjamahesaw0054.belanjabijak.navigation.Screen
import com.radjamahesaw0054.belanjabijak.ui.util.SettingsPreferences
import com.radjamahesaw0054.belanjabijak.ui.util.ViewModelFactory
import com.radjamahesaw0054.belanjabijak.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val database = BelanjaDb.getInstance(context)
    val preferences = SettingsPreferences(context)
    val factory = ViewModelFactory(database.dao, preferences)
    val viewModel: MainViewModel = viewModel(factory = factory)

    val pengeluaranList by viewModel.listPengeluaran.collectAsState()
    val bulanAktif by viewModel.filterBulan.collectAsState()
    val isGridLayout by viewModel.isGrid.collectAsState()

    var showMenuFilter by remember { mutableStateOf(false) }

    val listOpsiBulan = remember {
        val opsi = mutableListOf<String>()
        val format = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val kalender = Calendar.getInstance()
        (0 until 6).forEach { _ ->
            opsi.add(format.format(kalender.time))
            kalender.add(Calendar.MONTH, -1)
        }
        opsi
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${stringResource(R.string.title_home)} ($bulanAktif)") },
                actions = {
                    Box {
                        IconButton(onClick = { showMenuFilter = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = stringResource(R.string.month_filter))
                        }
                        DropdownMenu(
                            expanded = showMenuFilter,
                            onDismissRequest = { showMenuFilter = false }
                        ) {
                            listOpsiBulan.forEach { bulan ->
                                DropdownMenuItem(
                                    text = { Text(bulan) },
                                    onClick = {
                                        viewModel.simpanFilterBulan(bulan)
                                        showMenuFilter = false
                                    }
                                )
                            }
                        }
                    }

                    IconButton(onClick = { navController.navigate(Screen.RecycleBin.route) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.open_recycle_bin),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(onClick = { viewModel.simpanLayoutStatus(!isGridLayout) }) {
                        Icon(
                            painter = if (isGridLayout) {
                                painterResource(id = R.drawable.view_list)
                            } else {
                                painterResource(id = R.drawable.view_grid)
                            },
                            contentDescription = stringResource(R.string.toggle_layout)
                        )
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddPurchase.route) }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_spending))
            }
        },
        modifier = modifier
    ) { innerPadding ->
        if (pengeluaranList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.empty_state_text),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            if (isGridLayout) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp,
                    contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
                ) {
                    items(pengeluaranList) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Screen.EditPurchase.withId(item.id))
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = item.namaBarang, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Rp ${item.harga * item.jumlah}",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(text = item.kategori, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
                ) {
                    items(pengeluaranList) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Screen.EditPurchase.withId(item.id))
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = item.namaBarang, style = MaterialTheme.typography.titleMedium)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Rp ${item.harga * item.jumlah}",
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(text = item.kategori, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}