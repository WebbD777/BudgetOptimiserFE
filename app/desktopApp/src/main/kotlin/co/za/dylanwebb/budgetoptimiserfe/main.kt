package co.za.dylanwebb.budgetoptimiserfe

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.za.dylanwebb.budgetoptimiserfe.ui.MainScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BudgetOptimiserFE",
    ) {
        MainScreen()
    }
}