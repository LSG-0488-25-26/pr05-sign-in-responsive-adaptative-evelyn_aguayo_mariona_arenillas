package com.example.responsiveadaptative.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.responsiveadaptative.model.EstadoLogin
import com.example.responsiveadaptative.navegacion.Rutas
import com.example.responsiveadaptative.ui.theme.*
import com.example.responsiveadaptative.viewmodel.RegistroViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: RegistroViewModel,
    windowWidthSize: WindowWidthSizeClass
) {
    // Observem estat del login amb LiveData
    val estadoLogin by viewModel.estadoLogin.observeAsState()

    // Obtenim la config per saber orientacio del dispositiu
    val configuration = LocalConfiguration.current
    val esHoritzontal = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Netegem estat quan entrem
    LaunchedEffect(Unit) {
        viewModel.limpiarLogin()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Banner superior adaptatiu
        BannerLogin(windowWidthSize = windowWidthSize)

        // Contingut - Usem BoxWithConstraints per responsive
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            //layout segons WindowWidthSizeClass
            when (windowWidthSize) {
                WindowWidthSizeClass.Compact -> {
                    // Pantalla petita
                    if (esHoritzontal) {
                        // Mobil en horitzontal (layout en fila)
                        LoginCompactHoritzontal(
                            estadoLogin = estadoLogin,
                            viewModel = viewModel,
                            navController = navController
                        )
                    } else {
                        // Mobil en vertical (layout en columna)
                        LoginCompactVertical(
                            estadoLogin = estadoLogin,
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                }
                WindowWidthSizeClass.Medium -> {
                    // Tablet petita
                    LoginMedium(
                        estadoLogin = estadoLogin,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                WindowWidthSizeClass.Expanded -> {
                    // Tablet gran o desktop
                    LoginExpanded(
                        estadoLogin = estadoLogin,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                else -> {
                    // Per defecte
                    LoginCompactVertical(
                        estadoLogin = estadoLogin,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}

/**
 * Banner superior adaptatiu segons la mida de pantalla
 */
@Composable
fun BannerLogin(windowWidthSize: WindowWidthSizeClass) {
    // Mides segons la pantalla
    val altoBanner = when (windowWidthSize) {
        WindowWidthSizeClass.Compact -> 120.dp
        WindowWidthSizeClass.Medium -> 140.dp
        else -> 160.dp
    }

    val tamanoIcono = when (windowWidthSize) {
        WindowWidthSizeClass.Compact -> 40.dp
        WindowWidthSizeClass.Medium -> 48.dp
        else -> 56.dp
    }

    val tamanoTitulo = when (windowWidthSize) {
        WindowWidthSizeClass.Compact -> 24.sp
        WindowWidthSizeClass.Medium -> 28.sp
        else -> 32.sp
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(altoBanner)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(MoradoOscuro, MoradoPrincipal, MoradoClaro)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Logo
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = "Logo",
                modifier = Modifier.size(tamanoIcono),
                tint = DoradoAcento
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Nom i lema
            Column {
                Text(
                    text = "SOUNDWAVE",
                    fontSize = tamanoTitulo,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Desperta el teu talent musical",
                    fontSize = (tamanoTitulo.value * 0.5).sp,
                    color = DoradoAcento
                )
            }
        }
    }
}

/**
 * Layout COMPACT VERTICAL - Una sola columna amb scroll
 */
@Composable
fun LoginCompactVertical(
    estadoLogin: EstadoLogin?,
    viewModel: RegistroViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Iniciar Sessió",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Accedeix al teu compte d'estudiant",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Formulari de login
        FormularioLogin(
            estadoLogin = estadoLogin,
            viewModel = viewModel,
            navController = navController
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Enllaç a registre
        EnlaceRegistro(navController = navController, viewModel = viewModel)
    }
}

/**
 * Layout COMPACT HORITZONTAL (mobil en landscape)
 * Dues columnes: titol | formulari
 */
@Composable
fun LoginCompactHoritzontal(
    estadoLogin: EstadoLogin?,
    viewModel: RegistroViewModel,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Columna esquerra - Titol
        Column(
            modifier = Modifier.weight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Benvingut",
                style = MaterialTheme.typography.headlineLarge,
                color = MoradoPrincipal
            )
            Text(
                text = "Inicia sessió per continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        // Columna dreta - Formulari
        Column(
            modifier = Modifier
                .weight(0.6f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormularioLogin(
                estadoLogin = estadoLogin,
                viewModel = viewModel,
                navController = navController
            )

            Spacer(modifier = Modifier.height(16.dp))

            EnlaceRegistro(navController = navController, viewModel = viewModel)
        }
    }
}

/**
 * Layout MEDIUM (tablet petita)
 * Formulari centrat amb Card
 */
@Composable
fun LoginMedium(
    estadoLogin: EstadoLogin?,
    viewModel: RegistroViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 64.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Iniciar Sessió",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Accedeix al teu compte d'estudiant",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Card amb el formulari
        Card(
            modifier = Modifier.fillMaxWidth(0.8f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormularioLogin(
                    estadoLogin = estadoLogin,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        EnlaceRegistro(navController = navController, viewModel = viewModel)
    }
}

/**
 * Layout EXPANDED (tablet gran)
 * Card centrada gran
 */
@Composable
fun LoginExpanded(
    estadoLogin: EstadoLogin?,
    viewModel: RegistroViewModel,
    navController: NavHostController
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Card central amb el formulari
        Card(
            modifier = Modifier
                .width(450.dp)
                .padding(32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Iniciar Sessió",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Accedeix al teu compte d'estudiant",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                FormularioLogin(
                    estadoLogin = estadoLogin,
                    viewModel = viewModel,
                    navController = navController
                )

                Spacer(modifier = Modifier.height(24.dp))

                EnlaceRegistro(navController = navController, viewModel = viewModel)
            }
        }
    }
}

/**
 * Formulari de login reutilitzable
 * Conte els camps d'usuari i password
 */
@Composable
fun FormularioLogin(
    estadoLogin: EstadoLogin?,
    viewModel: RegistroViewModel,
    navController: NavHostController
) {
    // Estat per mostrar/ocultar contrasenya
    var mostrarPassword by remember { mutableStateOf(false) }

    // Camp d'usuari
    OutlinedTextField(
        value = estadoLogin?.nombreUsuario ?: "",
        onValueChange = { nuevoValor ->
            viewModel.actualizarLoginUsuario(nuevoValor)
        },
        label = { Text("Usuari") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Usuari"
            )
        },
        isError = estadoLogin?.errorUsuario?.isNotEmpty() == true,
        supportingText = {
            if (estadoLogin?.errorUsuario?.isNotEmpty() == true) {
                Text(
                    text = estadoLogin.errorUsuario,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Camp de contrasenya
    OutlinedTextField(
        value = estadoLogin?.password ?: "",
        onValueChange = { nuevoValor ->
            viewModel.actualizarLoginPassword(nuevoValor)
        },
        label = { Text("Contrasenya") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Contrasenya"
            )
        },
        trailingIcon = {
            IconButton(onClick = { mostrarPassword = !mostrarPassword }) {
                Icon(
                    imageVector = if (mostrarPassword) {
                        Icons.Default.VisibilityOff
                    } else {
                        Icons.Default.Visibility
                    },
                    contentDescription = if (mostrarPassword) "Ocultar" else "Mostrar"
                )
            }
        },
        visualTransformation = if (mostrarPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = estadoLogin?.errorPassword?.isNotEmpty() == true,
        supportingText = {
            if (estadoLogin?.errorPassword?.isNotEmpty() == true) {
                Text(
                    text = estadoLogin.errorPassword,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(24.dp))

    // Boto de login
    Button(
        onClick = {
            val exito = viewModel.hacerLogin()
            if (exito) {
                navController.navigate(Rutas.CONFIRMACION_LOGIN)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MoradoPrincipal
        )
    ) {
        Text(
            text = "ENTRAR",
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Enllaç per anar a la pantalla de registre
 */
@Composable
fun EnlaceRegistro(
    navController: NavHostController,
    viewModel: RegistroViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "No tens compte? ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TextButton(
            onClick = {
                viewModel.limpiarLogin()
                navController.navigate(Rutas.REGISTRO)
            }
        ) {
            Text(
                text = "Registra't",
                color = MoradoPrincipal,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
