package com.example.diariodespesa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.diariodespesa.data.ExpenseDatabase
import com.example.diariodespesa.data.ExpensesRepository
import com.example.diariodespesa.navigation.AppNav
import com.example.diariodespesa.ui.screens.ExpensesViewModel
import com.example.diariodespesa.ui.theme.DiarioDespesaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiarioDespesaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val database = remember {
                        ExpenseDatabase.getDatabase(this@MainActivity)
                    }
                    val repository = remember {
                        ExpensesRepository(database.expenseDao())
                    }
                    val viewModel = remember {
                        ExpensesViewModel(repository)
                    }

                    AppNav(vm = viewModel)
                }
            }
        }
    }
}