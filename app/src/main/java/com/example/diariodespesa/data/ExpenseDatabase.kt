package com.example.diariodespesa.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
    entities = [Expense::class],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var Instance: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ExpenseDatabase::class.java,
                    "expenses_database"
                ).build().also { Instance = it }
            }
        }
    }
}