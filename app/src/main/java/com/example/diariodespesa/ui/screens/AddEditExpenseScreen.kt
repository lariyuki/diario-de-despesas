package com.example.diariodespesa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.diariodespesa.utils.parseDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    vm: ExpensesViewModel,
    id: Long?,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val isFormValid = description.isNotBlank() &&
            amount.isNotBlank() &&
            category.isNotBlank() &&
            date.length >= 8

    val topBarTitle = if (id == null) "Adicionar Despesa" else "Editar Despesa"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topBarTitle) },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.Close, contentDescription = "Cancelar")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            // Converter valores e salvar
                            val amountValue = amount.toDoubleOrNull() ?: 0.0
                            val dateTimestamp = parseDate(date)

                            vm.addExpense(description, amountValue, category, dateTimestamp)
                            onSave()
                        },
                        enabled = isFormValid
                    ) {
                        Text("Salvar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Valor (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoria") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = date,
                onValueChange = {
                    if (it.length <= 10) {
                        date = it
                    }
                },
                label = { Text("Data") },
                placeholder = { Text("DD/MM/AAAA") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}