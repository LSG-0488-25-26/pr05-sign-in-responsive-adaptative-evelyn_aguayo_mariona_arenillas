package com.example.responsiveadaptative.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.responsiveadaptative.model.EstadoRegistro
import com.example.responsiveadaptative.navegacion.Rutas
import com.example.responsiveadaptative.ui.theme.*
import com.example.responsiveadaptative.viewmodel.RegistroViewModel

/**
 * Pantalla de Registre  *
 * - Nom complet, data naixement, email, telefon
 * - Nom usuari, password, confirmar password
 * - Instrument (DropdownMenu), Nivell (RadioButton)
 * - Acceptació de termes (Checkbox)
 * - Validacions de tots els camps
 */
@Composable
fun RegistroScreen(
    navController: NavHostController,
    viewModel: RegistroViewModel,
    windowWidthSize: WindowWidthSizeClass
) {
    // Observem l'estat del registre amb LiveData
    val estadoRegistro by viewModel.estadoRegistro.observeAsState()

    // Obtenim la configuració per saber l'orientació
    val configuration = LocalConfiguration.current
    val esHoritzontal = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Netegem l'estat al entrar
    LaunchedEffect(Unit) {
        viewModel.limpiarRegistro()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Banner amb botó de tornar
        BannerRegistro(
            windowWidthSize = windowWidthSize,
            onTornar = { navController.popBackStack() }
        )

        // Contingut principal - RESPONSIVE amb BoxWithConstraints
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            // Decidim layout segons WindowWidthSizeClass
            when (windowWidthSize) {
                WindowWidthSizeClass.Compact -> {
                    // Mobil - una columna
                    RegistroCompact(
                        estadoRegistro = estadoRegistro,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                WindowWidthSizeClass.Medium -> {
                    // Tablet petita - dues columnes
                    RegistroMedium(
                        estadoRegistro = estadoRegistro,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                WindowWidthSizeClass.Expanded -> {
                    // Tablet gran - amb panells laterals
                    RegistroExpanded(
                        estadoRegistro = estadoRegistro,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}

/**
 * Banner superior amb botó de tornar
 */
@Composable
fun BannerRegistro(
    windowWidthSize: WindowWidthSizeClass,
    onTornar: () -> Unit
) {
    val altoBanner = when (windowWidthSize) {
        WindowWidthSizeClass.Compact -> 100.dp
        WindowWidthSizeClass.Medium -> 120.dp
        else -> 140.dp
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(altoBanner)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(MoradoOscuro, MoradoPrincipal, MoradoClaro)
                )
            )
    ) {
        // Botó de tornar
        IconButton(
            onClick = onTornar,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Tornar",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        // Logo i títol centrats
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = "Logo",
                modifier = Modifier.size(36.dp),
                tint = DoradoAcento
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Crear Compte",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

/**
 * Layout COMPACT (mòbil) - Una columna
 */
@Composable
fun RegistroCompact(
    estadoRegistro: EstadoRegistro?,
    viewModel: RegistroViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Uneix-te a SoundWave Academy",
            style = MaterialTheme.typography.titleLarge,
            color = MoradoPrincipal
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Formulari complet en una columna
        FormularioRegistroComplet(
            estadoRegistro = estadoRegistro,
            viewModel = viewModel,
            duesColumnes = false
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botó de registre
        BotoRegistrar(viewModel = viewModel, navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Enllaç a login
        EnlaceLogin(navController = navController, viewModel = viewModel)
    }
}

/**
 * Layout MEDIUM (tablet petita) - Dues columnes
 */
@Composable
fun RegistroMedium(
    estadoRegistro: EstadoRegistro?,
    viewModel: RegistroViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Uneix-te a SoundWave Academy",
            style = MaterialTheme.typography.headlineMedium,
            color = MoradoPrincipal
        )

        Text(
            text = "Completa el formulari per crear el teu compte",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Formulari en dues columnes
        FormularioRegistroComplet(
            estadoRegistro = estadoRegistro,
            viewModel = viewModel,
            duesColumnes = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        BotoRegistrar(viewModel = viewModel, navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        EnlaceLogin(navController = navController, viewModel = viewModel)
    }
}

/**
 * Layout EXPANDED (tablet gran) - Amb panells laterals
 */
@Composable
fun RegistroExpanded(
    estadoRegistro: EstadoRegistro?,
    viewModel: RegistroViewModel,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Panell esquerre - Instruments disponibles
        Card(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            PanellInstruments()
        }

        // Formulari central
        Card(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear Compte",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MoradoPrincipal
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                FormularioRegistroComplet(
                    estadoRegistro = estadoRegistro,
                    viewModel = viewModel,
                    duesColumnes = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                BotoRegistrar(viewModel = viewModel, navController = navController)

                Spacer(modifier = Modifier.height(16.dp))

                EnlaceLogin(navController = navController, viewModel = viewModel)
            }
        }

        // Panell dret - Contacte i informacio
        Card(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            PanellContacte()
        }
    }
}

/**
 * Panell amb instruments disponibles (només a Expanded)
 */
@Composable
fun PanellInstruments() {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "🎸 INSTRUMENTS",
            style = MaterialTheme.typography.titleLarge,
            color = MoradoPrincipal
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        // Llista d'instruments
        val instruments = listOf(
            "🎸 Guitarra",
            "🎹 Piano",
            "🥁 Bateria",
            "🎸 Baix",
            "🎻 Violí",
            "🎷 Saxofon",
            "🎤 Cant"
        )

        for (instrument in instruments) {
            Text(
                text = instrument,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "📚 NIVELLS",
            style = MaterialTheme.typography.titleLarge,
            color = MoradoPrincipal
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        Text("✓ Principiant", modifier = Modifier.padding(vertical = 4.dp))
        Text("✓ Intermedi", modifier = Modifier.padding(vertical = 4.dp))
        Text("✓ Avançat", modifier = Modifier.padding(vertical = 4.dp))
    }
}

/**
 * Panell amb informacio de contacte (només a Expanded)
 */
@Composable
fun PanellContacte() {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "📞 CONTACTE",
            style = MaterialTheme.typography.titleLarge,
            color = MoradoPrincipal
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        Text(
            text = "📱 900 123 456",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "📧 info@soundwave.com",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "📍 C/ Música, 42\n    Barcelona",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "⏰ HORARIS",
            style = MaterialTheme.typography.titleLarge,
            color = MoradoPrincipal
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        Text("Dl-Dv: 9:00-21:00", modifier = Modifier.padding(vertical = 4.dp))
        Text("Ds: 10:00-14:00", modifier = Modifier.padding(vertical = 4.dp))
    }
}

/**
 * Formulari de registre complet
 * Pot mostrar-se en una o dues columnes
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistroComplet(
    estadoRegistro: EstadoRegistro?,
    viewModel: RegistroViewModel,
    duesColumnes: Boolean
) {
    // Estats per mostrar/ocultar contrasenyes
    var mostrarPassword by remember { mutableStateOf(false) }
    var mostrarConfirmar by remember { mutableStateOf(false) }

    // Estat per al dropdown d'instruments
    var expanditInstruments by remember { mutableStateOf(false) }

    // Llista d'instruments
    val llistaInstruments = listOf(
        "Guitarra",
        "Piano",
        "Bateria",
        "Baix",
        "Violí",
        "Saxofon",
        "Cant"
    )

    // Llista de nivells
    val llistaNivells = listOf("Principiant", "Intermedi", "Avançat")

    if (duesColumnes) {
        // LAYOUT DUES COLUMNES (Medium i Expanded)

        // Fila 1: Nom i Data
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nom complet
            OutlinedTextField(
                value = estadoRegistro?.usuario?.nombreCompleto ?: "",
                onValueChange = { viewModel.actualizarNombre(it) },
                label = { Text("Nom complet") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                isError = estadoRegistro?.errorNombre?.isNotEmpty() == true,
                supportingText = {
                    if (estadoRegistro?.errorNombre?.isNotEmpty() == true) {
                        Text(estadoRegistro.errorNombre, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            // Data de naixement
            OutlinedTextField(
                value = estadoRegistro?.usuario?.fechaNacimiento ?: "",
                onValueChange = { nouValor ->
                    // Formatem automaticament amb barres
                    val soloNumeros = nouValor.filter { it.isDigit() }
                    var resultat = ""
                    for (i in soloNumeros.indices) {
                        if (i == 2 || i == 4) {
                            resultat = resultat + "/"
                        }
                        if (i < 8) {
                            resultat = resultat + soloNumeros[i]
                        }
                    }
                    viewModel.actualizarFechaNacimiento(resultat)
                },
                label = { Text("Data naixement") },
                placeholder = { Text("DD/MM/AAAA") },
                leadingIcon = {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                },
                isError = estadoRegistro?.errorFecha?.isNotEmpty() == true,
                supportingText = {
                    if (estadoRegistro?.errorFecha?.isNotEmpty() == true) {
                        Text(estadoRegistro.errorFecha, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 2: Email i Telefon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Email
            OutlinedTextField(
                value = estadoRegistro?.usuario?.email ?: "",
                onValueChange = { viewModel.actualizarEmail(it) },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                isError = estadoRegistro?.errorEmail?.isNotEmpty() == true,
                supportingText = {
                    if (estadoRegistro?.errorEmail?.isNotEmpty() == true) {
                        Text(estadoRegistro.errorEmail, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            // Telefon
            OutlinedTextField(
                value = estadoRegistro?.usuario?.telefono ?: "",
                onValueChange = { viewModel.actualizarTelefono(it) },
                label = { Text("Telèfon") },
                leadingIcon = {
                    Icon(Icons.Default.Phone, contentDescription = null)
                },
                isError = estadoRegistro?.errorTelefono?.isNotEmpty() == true,
                supportingText = {
                    if (estadoRegistro?.errorTelefono?.isNotEmpty() == true) {
                        Text(estadoRegistro.errorTelefono, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 3: Usuari i Password
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Usuari
            OutlinedTextField(
                value = estadoRegistro?.usuario?.nombreUsuario ?: "",
                onValueChange = { viewModel.actualizarNombreUsuario(it) },
                label = { Text("Usuari") },
                leadingIcon = {
                    Icon(Icons.Default.AccountCircle, contentDescription = null)
                },
                isError = estadoRegistro?.errorUsuario?.isNotEmpty() == true,
                supportingText = {
                    if (estadoRegistro?.errorUsuario?.isNotEmpty() == true) {
                        Text(estadoRegistro.errorUsuario, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            // Password
            OutlinedTextField(
                value = estadoRegistro?.usuario?.password ?: "",
                onValueChange = { viewModel.actualizarPassword(it) },
                label = { Text("Contrasenya") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { mostrarPassword = !mostrarPassword }) {
                        Icon(
                            imageVector = if (mostrarPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation(),
                isError = estadoRegistro?.errorPassword?.isNotEmpty() == true,
                supportingText = {
                    if (estadoRegistro?.errorPassword?.isNotEmpty() == true) {
                        Text(estadoRegistro.errorPassword, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Confirmar password (ample complet)
        OutlinedTextField(
            value = estadoRegistro?.usuario?.confirmarPassword ?: "",
            onValueChange = { viewModel.actualizarConfirmarPassword(it) },
            label = { Text("Confirmar contrasenya") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = { mostrarConfirmar = !mostrarConfirmar }) {
                    Icon(
                        imageVector = if (mostrarConfirmar) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (mostrarConfirmar) VisualTransformation.None else PasswordVisualTransformation(),
            isError = estadoRegistro?.errorConfirmarPassword?.isNotEmpty() == true,
            supportingText = {
                if (estadoRegistro?.errorConfirmarPassword?.isNotEmpty() == true) {
                    Text(estadoRegistro.errorConfirmarPassword, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

    } else {
        // LAYOUT UNA COLUMNA (Compact)

        // Nom complet
        OutlinedTextField(
            value = estadoRegistro?.usuario?.nombreCompleto ?: "",
            onValueChange = { viewModel.actualizarNombre(it) },
            label = { Text("Nom complet") },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
            isError = estadoRegistro?.errorNombre?.isNotEmpty() == true,
            supportingText = {
                if (estadoRegistro?.errorNombre?.isNotEmpty() == true) {
                    Text(estadoRegistro.errorNombre, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Data de naixement
        OutlinedTextField(
            value = estadoRegistro?.usuario?.fechaNacimiento ?: "",
            onValueChange = { nouValor ->
                val soloNumeros = nouValor.filter { it.isDigit() }
                var resultat = ""
                for (i in soloNumeros.indices) {
                    if (i == 2 || i == 4) {
                        resultat = resultat + "/"
                    }
                    if (i < 8) {
                        resultat = resultat + soloNumeros[i]
                    }
                }
                viewModel.actualizarFechaNacimiento(resultat)
            },
            label = { Text("Data de naixement") },
            placeholder = { Text("DD/MM/AAAA") },
            leadingIcon = {
                Icon(Icons.Default.CalendarMonth, contentDescription = null)
            },
            isError = estadoRegistro?.errorFecha?.isNotEmpty() == true,
            supportingText = {
                if (estadoRegistro?.errorFecha?.isNotEmpty() == true) {
                    Text(estadoRegistro.errorFecha, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        OutlinedTextField(
            value = estadoRegistro?.usuario?.email ?: "",
            onValueChange = { viewModel.actualizarEmail(it) },
            label = { Text("Email") },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = null)
            },
            isError = estadoRegistro?.errorEmail?.isNotEmpty() == true,
            supportingText = {
                if (estadoRegistro?.errorEmail?.isNotEmpty() == true) {
                    Text(estadoRegistro.errorEmail, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Telefon
        OutlinedTextField(
            value = estadoRegistro?.usuario?.telefono ?: "",
            onValueChange = { viewModel.actualizarTelefono(it) },
            label = { Text("Telèfon") },
            leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = null)
            },
            isError = estadoRegistro?.errorTelefono?.isNotEmpty() == true,
            supportingText = {
                if (estadoRegistro?.errorTelefono?.isNotEmpty() == true) {
                    Text(estadoRegistro.errorTelefono, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Usuari
        OutlinedTextField(
            value = estadoRegistro?.usuario?.nombreUsuario ?: "",
            onValueChange = { viewModel.actualizarNombreUsuario(it) },
            label = { Text("Nom d'usuari") },
            leadingIcon = {
                Icon(Icons.Default.AccountCircle, contentDescription = null)
            },
            isError = estadoRegistro?.errorUsuario?.isNotEmpty() == true,
            supportingText = {
                if (estadoRegistro?.errorUsuario?.isNotEmpty() == true) {
                    Text(estadoRegistro.errorUsuario, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password
        OutlinedTextField(
            value = estadoRegistro?.usuario?.password ?: "",
            onValueChange = { viewModel.actualizarPassword(it) },
            label = { Text("Contrasenya") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = { mostrarPassword = !mostrarPassword }) {
                    Icon(
                        imageVector = if (mostrarPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation(),
            isError = estadoRegistro?.errorPassword?.isNotEmpty() == true,
            supportingText = {
                if (estadoRegistro?.errorPassword?.isNotEmpty() == true) {
                    Text(estadoRegistro.errorPassword, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirmar password
        OutlinedTextField(
            value = estadoRegistro?.usuario?.confirmarPassword ?: "",
            onValueChange = { viewModel.actualizarConfirmarPassword(it) },
            label = { Text("Confirmar contrasenya") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = { mostrarConfirmar = !mostrarConfirmar }) {
                    Icon(
                        imageVector = if (mostrarConfirmar) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (mostrarConfirmar) VisualTransformation.None else PasswordVisualTransformation(),
            isError = estadoRegistro?.errorConfirmarPassword?.isNotEmpty() == true,
            supportingText = {
                if (estadoRegistro?.errorConfirmarPassword?.isNotEmpty() == true) {
                    Text(estadoRegistro.errorConfirmarPassword, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    //DROPDOWN INSTRUMENTS
    ExposedDropdownMenuBox(
        expanded = expanditInstruments,
        onExpandedChange = { expanditInstruments = !expanditInstruments },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = estadoRegistro?.usuario?.instrumento ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Selecciona instrument") },
            leadingIcon = {
                Icon(Icons.Default.Piano, contentDescription = null)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanditInstruments)
            },
            isError = estadoRegistro?.errorInstrumento?.isNotEmpty() == true,
            supportingText = {
                if (estadoRegistro?.errorInstrumento?.isNotEmpty() == true) {
                    Text(estadoRegistro.errorInstrumento, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanditInstruments,
            onDismissRequest = { expanditInstruments = false }
        ) {
            for (instrument in llistaInstruments) {
                DropdownMenuItem(
                    text = { Text(instrument) },
                    onClick = {
                        viewModel.actualizarInstrumento(instrument)
                        expanditInstruments = false
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // RADIO BUTTONS PER NIVELL
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Selecciona el teu nivell:",
            style = MaterialTheme.typography.bodyLarge,
            color = if (estadoRegistro?.errorNivel?.isNotEmpty() == true) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        for (nivell in llistaNivells) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = estadoRegistro?.usuario?.nivel == nivell,
                    onClick = { viewModel.actualizarNivel(nivell) }
                )
                Text(
                    text = nivell,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        if (estadoRegistro?.errorNivel?.isNotEmpty() == true) {
            Text(
                text = estadoRegistro.errorNivel,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // CHECKBOX DE TERMES
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = estadoRegistro?.usuario?.aceptaTerminos ?: false,
            onCheckedChange = { viewModel.actualizarTerminos(it) }
        )
        Text(
            text = "Accepto els termes i condicions",
            style = MaterialTheme.typography.bodyMedium,
            color = if (estadoRegistro?.errorTerminos?.isNotEmpty() == true) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }

    if (estadoRegistro?.errorTerminos?.isNotEmpty() == true) {
        Text(
            text = estadoRegistro.errorTerminos,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 48.dp)
        )
    }
}

/**
 * Botó per registrar
 */
@Composable
fun BotoRegistrar(
    viewModel: RegistroViewModel,
    navController: NavHostController
) {
    Button(
        onClick = {
            val exito = viewModel.registrarUsuario()
            if (exito) {
                navController.navigate(Rutas.CONFIRMACION_REGISTRO) {
                    popUpTo(Rutas.LOGIN)
                }
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
            text = "REGISTRAR-ME",
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Enllaç per tornar al login
 */
@Composable
fun EnlaceLogin(
    navController: NavHostController,
    viewModel: RegistroViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Ja tens compte? ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TextButton(
            onClick = {
                viewModel.limpiarRegistro()
                navController.popBackStack()
            }
        ) {
            Text(
                text = "Inicia sessió",
                color = MoradoPrincipal,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
