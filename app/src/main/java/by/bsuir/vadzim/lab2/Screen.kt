package by.bsuir.vadzim.lab2

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

sealed class Screen (val route: String) {
    object MainScreen: Screen("main_screen")
    object DetailsScreen: Screen("details_screen")

    fun withArgs(vararg  args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

