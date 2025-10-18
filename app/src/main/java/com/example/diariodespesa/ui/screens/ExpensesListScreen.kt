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
import com.example.diariodespesa.ui.components.ExpenseItemCard
import com.example.diariodespesa.ui.components.TotalExpensesCard

// CLASSE E FUNÇÃO TEMPORÁRIAS
data class Expense(val id: Long, val description: String, val amount: Double, val category: String, val date: Long)

fun formatCurrency(value: Double): String {
    return "R$ ${"%.2f".format(value).replace('.', ',')}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesListScreen(
    // vm: ExpensesViewModel,
    onOpenDetails: (Long) -> Unit,
    onAddNewExpense: () -> Unit
) {

    // 'null' significa que "Todos" estão selecionados.
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

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
                    onClick = { selectedCategory = null },
                    label = { Text("Todos") }
                )
                // Botões para cada categoria
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) }
                    )
                }
            }

            TotalExpensesCard(total = 235.50) //valor fixo

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val sampleExpenses = listOf(
                    Expense(1, "Almoço", 25.50, "Comida", System.currentTimeMillis()),
                    Expense(2, "Gasolina", 150.0, "Transporte", System.currentTimeMillis()),
                    Expense(3, "Cinema", 45.25, "Lazer", System.currentTimeMillis()),
                    Expense(4, "Café da tarde", 15.00, "Comida", System.currentTimeMillis())
                )


                val filteredExpenses = if (selectedCategory == null) {
                    sampleExpenses // Se nenhuma categoria estiver selecionada, mostra tudo.
                } else {
                    sampleExpenses.filter {

                        it.category == selectedCategory
                    }
                }

                items(filteredExpenses, key = { it.id }) { expense ->
                    ExpenseItemCard(
                        expense = expense,
                        onClick = { onOpenDetails(expense.id) },
                        onDeleteClick = {
                            Toast.makeText(context, "${expense.description} excluída!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}