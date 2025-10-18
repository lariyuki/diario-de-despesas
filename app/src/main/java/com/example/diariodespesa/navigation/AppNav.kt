package com.example.diariodespesa.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.diariodespesa.ui.screens.AddEditExpenseScreen
import com.example.diariodespesa.ui.screens.ExpenseDetailScreen
import com.example.diariodespesa.ui.screens.ExpensesListScreen

@Composable
fun AppNav() {
    val nav = rememberNavController()
    // val vm: ExpensesViewModel = viewModel() // Seu amigo vai descomentar e implementar

    NavHost(navController = nav, startDestination = Routes.EXPENSES_LIST) {

        composable(Routes.EXPENSES_LIST) {
            ExpensesListScreen(
                // vm = vm,
                onOpenDetails = { id -> nav.navigate(Routes.detail(id)) },
                onAddNewExpense = { nav.navigate(Routes.EXPENSE_ADD) }
            )
        }

        composable(
            route = Routes.EXPENSE_DETAIL,
            arguments = listOf(navArgument(Routes.EXPENSE_DETAIL_ARG) { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(Routes.EXPENSE_DETAIL_ARG) ?: 0L
            ExpenseDetailScreen(
                // vm = vm,
                id = id,
                onBack = { nav.navigateUp() },
                onEdit = { expenseId -> nav.navigate(Routes.edit(expenseId)) },
                onDelete = {
                    // vm.delete(id)
                    nav.navigateUp()
                }
            )
        }

        composable(route = Routes.EXPENSE_ADD) {
            AddEditExpenseScreen(
                // vm = vm,
                id = null,
                onCancel = { nav.navigateUp() },
                onSave = { nav.navigateUp() }
            )
        }

        composable(
            route = Routes.EXPENSE_EDIT,
            arguments = listOf(navArgument(Routes.EXPENSE_EDIT_ARG) { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(Routes.EXPENSE_EDIT_ARG) ?: 0L
            AddEditExpenseScreen(
                // vm = vm,
                id = id,
                onCancel = { nav.navigateUp() },
                onSave = { nav.navigateUp() }
            )
        }
    }
}