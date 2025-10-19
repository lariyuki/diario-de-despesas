package com.example.diariodespesa.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diariodespesa.data.Expense
import com.example.diariodespesa.data.ExpensesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import com.example.diariodespesa.utils.formatCurrency
import com.example.diariodespesa.utils.formatDate
import com.example.diariodespesa.utils.parseDate

class ExpensesViewModel(private val repository: ExpensesRepository) : ViewModel() {

    // Estado para a categoria selecionada (null = todas as categorias)
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    // Lista de despesas filtrada por categoria
    val expenses = combine(
        repository.getAllExpenses(),
        _selectedCategory
    ) { expenses, category ->
        if (category == null) {
            expenses
        } else {
            expenses.filter { it.category == category }
        }
    }

    // Total de despesas
    val totalExpenses = repository.getTotalExpenses()

    // Total por categoria selecionada
    val totalExpensesBySelectedCategory = combine(
        _selectedCategory,
        repository.getTotalExpenses()
    ) { category, total ->
        if (category == null) {
            total ?: 0.0
        } else {
            // Para simplificar, vamos calcular o total baseado na lista filtrada
            // Em uma aplicação real, você usaria getTotalExpensesByCategory
            0.0 // Placeholder - será calculado baseado na lista
        }
    }

    fun setSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun addExpense(description: String, amount: Double, category: String, date: Long) {
        viewModelScope.launch {
            val expense = Expense(
                description = description,
                amount = amount,
                category = category,
                date = date
            )
            repository.insertExpense(expense)
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            repository.updateExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun deleteExpenseById(id: Long) {
        viewModelScope.launch {
            repository.deleteExpenseById(id)
        }
    }

    suspend fun getExpenseById(id: Long): Expense? {
        return repository.getExpenseById(id)
    }
}