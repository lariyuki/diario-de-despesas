package com.example.diariodespesa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diariodespesa.data.Expense  // Import correto
import com.example.diariodespesa.ui.components.DetailItem
import com.example.diariodespesa.utils.formatCurrency
import com.example.diariodespesa.utils.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDetailScreen(
    vm: ExpensesViewModel,
    id: Long,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit,
    onDelete: () -> Unit
) {
    val expenseState = remember { mutableStateOf<Expense?>(null) }


    LaunchedEffect(id) {
        val expense = vm.getExpenseById(id)
        expenseState.value = expense
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhe da Despesa") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { onEdit(id) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Excluir")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            expenseState.value?.let { expense ->
                DetailItem(label = "Descrição", value = expense.description)
                DetailItem(label = "Valor", value = formatCurrency(expense.amount))
                DetailItem(label = "Categoria", value = expense.category)
                DetailItem(label = "Data", value = formatDate(expense.date))
            } ?: run {
                // Mostrar loading ou mensagem se a despesa não for encontrada
                Text("Carregando...")
            }
        }
    }
}