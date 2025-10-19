package com.example.diariodespesa.data

import kotlinx.coroutines.flow.Flow

class ExpensesRepository(private val expenseDao: ExpenseDao) {

    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()

    fun getExpensesByCategory(category: String): Flow<List<Expense>> =
        expenseDao.getExpensesByCategory(category)

    suspend fun getExpenseById(id: Long): Expense? =
        expenseDao.getExpenseById(id)

    suspend fun insertExpense(expense: Expense): Long =
        expenseDao.insertExpense(expense)

    suspend fun updateExpense(expense: Expense) =
        expenseDao.updateExpense(expense)

    suspend fun deleteExpense(expense: Expense) =
        expenseDao.deleteExpense(expense)

    suspend fun deleteExpenseById(id: Long) =
        expenseDao.deleteExpenseById(id)

    fun getTotalExpenses(): Flow<Double?> =
        expenseDao.getTotalExpenses()

    fun getTotalExpensesByCategory(category: String): Flow<Double?> =
        expenseDao.getTotalExpensesByCategory(category)
}