package com.example.diariodespesa.navigation

object Routes {
    const val EXPENSES_LIST = "expenses_list"
    const val EXPENSE_ADD = "expense_add"

    const val EXPENSE_DETAIL_ROUTE = "expense_detail"
    const val EXPENSE_DETAIL_ARG = "id"
    const val EXPENSE_DETAIL = "$EXPENSE_DETAIL_ROUTE/{$EXPENSE_DETAIL_ARG}"

    const val EXPENSE_EDIT_ROUTE = "expense_edit"
    const val EXPENSE_EDIT_ARG = "id"
    const val EXPENSE_EDIT = "$EXPENSE_EDIT_ROUTE/{$EXPENSE_EDIT_ARG}"

    fun detail(id: Long) = "$EXPENSE_DETAIL_ROUTE/$id"
    fun edit(id: Long) = "$EXPENSE_EDIT_ROUTE/$id"
}