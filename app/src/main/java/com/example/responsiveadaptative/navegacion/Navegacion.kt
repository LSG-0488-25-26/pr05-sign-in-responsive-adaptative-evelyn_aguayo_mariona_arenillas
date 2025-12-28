package com.example.responsiveadaptative.navegacion

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.responsiveadaptative.view.screens.ConfirmacionScreen
import com.example.responsiveadaptative.view.screens.LoginScreen
import com.example.responsiveadaptative.view.screens.RegistroScreen
import com.example.responsiveadaptative.viewmodel.RegistroViewModel

/**
 * Rutes: cada pantalla te una ruta unica
 */
object Rutas {
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val CONFIRMACION_REGISTRO = "confirmacion_registro"
    const val CONFIRMACION_LOGIN = "confirmacion_login"
}

/**
 * NavHost
 * @param navController El controlador de navegacio
 * @param viewModel El ViewModel compartit entre pantalles
 * @param windowWidthSize La mida de la finestra per disseny adaptatiu
 */
@Composable
fun Navegacion(
    navController: NavHostController,
    viewModel: RegistroViewModel,
    windowWidthSize: WindowWidthSizeClass
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN
    ) {
        // Login Screen
        composable(route = Rutas.LOGIN) {
            LoginScreen(
                navController = navController,
                viewModel = viewModel,
                windowWidthSize = windowWidthSize
            )
        }

        // Registre Screen
        composable(route = Rutas.REGISTRO) {
            RegistroScreen(
                navController = navController,
                viewModel = viewModel,
                windowWidthSize = windowWidthSize
            )
        }

        // Confirmacio de Registre Screen
        composable(route = Rutas.CONFIRMACION_REGISTRO) {
            ConfirmacionScreen(
                navController = navController,
                viewModel = viewModel,
                tipo = "registro",
                windowWidthSize = windowWidthSize
            )
        }

        // Confirmacio de Login Screen
        composable(route = Rutas.CONFIRMACION_LOGIN) {
            ConfirmacionScreen(
                navController = navController,
                viewModel = viewModel,
                tipo = "login",
                windowWidthSize = windowWidthSize
            )
        }
    }
}
