package by.bsuir.vadzim.lab2

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Preview (showBackground = true)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
             MainScreen(navController = navController)
        }
        composable(
            route = Screen.DetailsScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "no-name"
                    nullable = true
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(250)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(250)
                )
            }

        ) {entry ->
            DetailScreen(name = entry.arguments?.getString("name"), navController = navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {
    val maxChar = 30
    var text by rememberSaveable {
        mutableStateOf("")
    }
    var displayError by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp)
    ) {


        TextField(
            value = text,
            onValueChange = {
                if(it.length <= maxChar) text = it
                displayError = false
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            supportingText = {
                Text(
                    text = "${text.length} / $maxChar",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                if (displayError) {
                    Text(
                        "error",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Red
                    )
                }
            },
            trailingIcon = {
                if (displayError)
                    Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
            },


        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if(text.isNotBlank()) {
                    navController.navigate(Screen.DetailsScreen.withArgs(text))
                } else {
                    displayError = true
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Next")
        }
    }
}

@Composable
fun DetailScreen(name: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = "Hello, $name")
    }
}

@Composable
fun DetailScreen(name: String?, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Hello, $name",
            modifier = Modifier.align(Alignment.Center)
        )
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("Back")
        }
    }
}



