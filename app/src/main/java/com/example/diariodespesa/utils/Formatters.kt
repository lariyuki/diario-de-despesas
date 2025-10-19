package com.example.diariodespesa.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatCurrency(value: Double): String {
    return "R$ ${"%.2f".format(value).replace('.', ',')}"
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun parseDate(dateString: String): Long {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.parse(dateString)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}