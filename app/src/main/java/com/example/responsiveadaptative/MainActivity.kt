package com.example.responsiveadaptative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.responsiveadaptative.navegacion.Navegacion
import com.example.responsiveadaptative.ui.theme.ResponsiveAdaptativeTheme
import com.example.responsiveadaptative.viewmodel.RegistroViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: RegistroViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            // Calculem la mida de la finestra per saber si
            // - Compact: mobil
            // - Medium: tablet petita
            // - Expanded: tablet gran o desktop
            val windowSizeClass = calculateWindowSizeClass(this)

            ResponsiveAdaptativeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Navegacion(
                        navController = navController,
                        viewModel = viewModel,
                        windowWidthSize = windowSizeClass.widthSizeClass
                    )
                }
            }
        }
    }
}
