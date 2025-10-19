package com.example.diariodespesa.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.diariodespesa.data.Expense
import com.example.diariodespesa.ui.components.ExpenseItemCard
import com.example.diariodespesa.ui.components.TotalExpensesCard
import com.example.diariodespesa.utils.formatCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesListScreen(
    vm: ExpensesViewModel,
    onOpenDetails: (Long) -> Unit,
    onAddNewExpense: () -> Unit
) {

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Coletando os estados do ViewModel
    val expenses by vm.expenses.collectAsState(initial = emptyList())
    val totalExpenses by vm.totalExpenses.collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Diário de Despesas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewExpense) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar nova despesa")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            val categories = listOf("Comida", "Transporte", "Lazer")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Botão para "Todos"
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { vm.setSelectedCategory(null) },
                    label = { Text("Todos") }
                )
                // Botões para cada categoria
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { vm.setSelectedCategory(category) },
                        label = { Text(category) }
                    )
                }
            }

            TotalExpensesCard(total = totalExpenses ?: 0.0)

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(expenses, key = { it.id }) { expense ->
                    ExpenseItemCard(
                        expense = expense,
                        onClick = { onOpenDetails(expense.id) },
                        onDeleteClick = {
                            vm.deleteExpenseById(expense.id)
                            Toast.makeText(context, "${expense.description} excluída!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}