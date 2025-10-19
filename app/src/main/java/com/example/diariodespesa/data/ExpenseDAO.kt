package com.example.diariodespesa.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses_table ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expenses_table WHERE id = :id")
    suspend fun getExpenseById(id: Long): Expense?

    @Query("SELECT * FROM expenses_table WHERE category = :category ORDER BY date DESC")
    fun getExpensesByCategory(category: String): Flow<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense): Long

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("DELETE FROM expenses_table WHERE id = :id")
    suspend fun deleteExpenseById(id: Long)

    @Query("SELECT SUM(amount) FROM expenses_table")
    fun getTotalExpenses(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM expenses_table WHERE category = :category")
    fun getTotalExpensesByCategory(category: String): Flow<Double?>
}