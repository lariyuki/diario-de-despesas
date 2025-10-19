package com.example.diariodespesa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val amount: Double,
    val category: String,
    val date: Long // Timestamp
)