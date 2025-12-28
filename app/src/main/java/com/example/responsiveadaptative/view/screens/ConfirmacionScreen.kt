package com.example.responsiveadaptative.view.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.responsiveadaptative.model.*
import com.example.responsiveadaptative.navegacion.*
import com.example.responsiveadaptative.ui.theme.*
import com.example.responsiveadaptative.viewmodel.*

/**
 * Pantalla de confirmacio despres de registre o login correcte
 * Utilitza AnimatedVisibility per l'entrada
 */
@Composable
fun ConfirmacionScreen(
    navController: NavHostController,
    viewModel: RegistroViewModel,
    tipo: String, // registro o login
    windowWidthSize: WindowWidthSizeClass
) {
    // Observem l'usuari registrat i l'estat del login
    val usuarioRegistrado by viewModel.usuarioRegistrado.observeAsState()
    val estadoLogin by viewModel.estadoLogin.observeAsState()

    // Estat per l'animacio d'entrada
    var mostrarContingut by remember { mutableStateOf(false) }

    // Activem l'animacio al entrar
    LaunchedEffect(Unit) {
        mostrarContingut = true
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Banner
        BannerConfirmacio()

        // Contingut amb AnimatedVisibility
        AnimatedVisibility(
            visible = mostrarContingut,
            enter = fadeIn() + slideInVertically()
        ) {
            when (windowWidthSize) {
                WindowWidthSizeClass.Expanded -> {
                    // Tablet gran
                    ConfirmacioExpanded(
                        tipo = tipo,
                        usuario = usuarioRegistrado,
                        nomUsuariLogin = estadoLogin?.nombreUsuario,
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                else -> {
                    // Mobil i tablet
                    ConfirmacioCompact(
                        tipo = tipo,
                        usuario = usuarioRegistrado,
                        nomUsuariLogin = estadoLogin?.nombreUsuario,
                        navController = navController,
                        viewModel = viewModel,
                        windowWidthSize = windowWidthSize
                    )
                }
            }
        }
    }
}

/**
 * Banner petit per la confirmacio
 */
@Composable
fun BannerConfirmacio() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(MoradoOscuro, MoradoPrincipal, MoradoClaro)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = "Logo",
                modifier = Modifier.size(28.dp),
                tint = DoradoAcento
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "SOUNDWAVE",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

/**
 * Layout per mobil i tablet petit
 */
@Composable
fun ConfirmacioCompact(
    tipo: String,
    usuario: Usuario?,
    nomUsuariLogin: String?,
    navController: NavHostController,
    viewModel: RegistroViewModel,
    windowWidthSize: WindowWidthSizeClass
) {
    val paddingHoritzontal = when (windowWidthSize) {
        WindowWidthSizeClass.Compact -> 24.dp
        else -> 48.dp
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = paddingHoritzontal, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Icona de successful
        IconaExit()

        Spacer(modifier = Modifier.height(24.dp))

        // Titol segons el tipus
        Text(
            text = if (tipo == "registro") "Registre Correcte!" else "Benvingut/da!",
            style = MaterialTheme.typography.headlineMedium,
            color = MoradoPrincipal,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contingut segons el tipus
        if (tipo == "registro" && usuario != null) {
            ContingutRegistre(usuario = usuario)
        } else {
            ContingutLogin(nomUsuari = nomUsuariLogin)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botons d'accio
        BotonsConfirmacio(
            tipo = tipo,
            navController = navController,
            viewModel = viewModel
        )
    }
}

/**
 * Layout per tablet gran
 */
@Composable
fun ConfirmacioExpanded(
    tipo: String,
    usuario: Usuario?,
    nomUsuariLogin: String?,
    navController: NavHostController,
    viewModel: RegistroViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(550.dp)
                .padding(32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icona de successful
                IconaExit()

                Spacer(modifier = Modifier.height(24.dp))

                // Titol
                Text(
                    text = if (tipo == "registro") "Registre Exitós!" else "Benvingut/da!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MoradoPrincipal,
                    fontWeight = FontWeight.Bold
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))

                // Contingut
                if (tipo == "registro" && usuario != null) {
                    ContingutRegistre(usuario = usuario)
                } else {
                    ContingutLogin(nomUsuari = nomUsuariLogin)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* Veure cursos */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("Veure cursos")
                    }

                    Button(
                        onClick = {
                            viewModel.limpiarRegistro()
                            viewModel.limpiarLogin()
                            navController.navigate(Rutas.LOGIN) {
                                popUpTo(Rutas.LOGIN) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MoradoPrincipal
                        )
                    ) {
                        Text(
                            text = if (tipo == "registro") "ANAR AL LOGIN" else "CONTINUAR",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

/**
 * Icona de successful
 */
@Composable
fun IconaExit() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                color = VerdeExito,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Èxit",
            modifier = Modifier.size(56.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

/**
 * Contingut per confirmacio de registre
 */
@Composable
fun ContingutRegistre(usuario: Usuario) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Dades del teu compte:",
                style = MaterialTheme.typography.titleMedium,
                color = MoradoPrincipal,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            // Mostrem les dades del user
            FilaDada(
                icona = Icons.Default.Person,
                etiqueta = "Nom",
                valor = usuario.nombreCompleto
            )

            FilaDada(
                icona = Icons.Default.Email,
                etiqueta = "Email",
                valor = usuario.email
            )

            FilaDada(
                icona = Icons.Default.Phone,
                etiqueta = "Telèfon",
                valor = usuario.telefono
            )

            FilaDada(
                icona = Icons.Default.Piano,
                etiqueta = "Instrument",
                valor = usuario.instrumento
            )

            FilaDada(
                icona = Icons.Default.Star,
                etiqueta = "Nivell",
                valor = usuario.nivel
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "T'hem enviat un email de confirmació a ${usuario.email}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )
}

/**
 * Contingut per confirmacio de login
 */
@Composable
fun ContingutLogin(nomUsuari: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hola, ${nomUsuari ?: "estudiant"}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Has iniciat sessió correctament a SoundWave",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Proximes classes
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "🎵 Les teves pròximes classes:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("• Guitarra - Dilluns 18:00")
                Text("• Teoria Musical - Dimecres 17:00")
                Text("• Pràctica Grupal - Divendres 19:00")
            }
        }
    }
}

/**
 * Fila amb icon i dades
 */
@Composable
fun FilaDada(
    icona: ImageVector,
    etiqueta: String,
    valor: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icona,
            contentDescription = null,
            tint = MoradoPrincipal,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = valor,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Botons de la pantalla de confirmacio
 */
@Composable
fun BotonsConfirmacio(
    tipo: String,
    navController: NavHostController,
    viewModel: RegistroViewModel
) {
    Button(
        onClick = {
            viewModel.limpiarRegistro()
            viewModel.limpiarLogin()
            navController.navigate(Rutas.LOGIN) {
                popUpTo(Rutas.LOGIN) { inclusive = true }
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
            text = if (tipo == "registro") "ANAR AL LOGIN" else "CONTINUAR",
            fontWeight = FontWeight.Bold
        )
    }
}
