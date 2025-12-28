package com.example.responsiveadaptative.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.responsiveadaptative.model.EstadoLogin
import com.example.responsiveadaptative.model.EstadoRegistro
import com.example.responsiveadaptative.model.Usuario

/**
 * ViewModel que gestiona l'estat de l'aplicació
 */
class RegistroViewModel : ViewModel() {

    // REGISTRE

    // Estat privat que podem modificar
    private val _estadoRegistro = MutableLiveData(EstadoRegistro())

    val estadoRegistro: LiveData<EstadoRegistro> = _estadoRegistro
    private val _usuarioRegistrado = MutableLiveData<Usuario>()
    val usuarioRegistrado: LiveData<Usuario> = _usuarioRegistrado

    // Funció per actualitzar el nom complet
    fun actualizarNombre(nombre: String) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario
            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(nombreCompleto = nombre),
                errorNombre = "" // Netegem l'error quan l'usuari escriu
            )
        }
    }

    // Funció per actualitzar la data de naixement
    fun actualizarFechaNacimiento(fecha: String) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario
            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(fechaNacimiento = fecha),
                errorFecha = ""
            )
        }
    }

    // Funció per actualitzar email
    fun actualizarEmail(email: String) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario
            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(email = email),
                errorEmail = ""
            )
        }
    }

    // Funció per actualitzar el telèfon
    fun actualizarTelefono(telefono: String) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario

            // Filtrem per a que només tingui números
            var soloNumeros = ""
            for (caracter in telefono) {
                if (caracter.isDigit()) {
                    soloNumeros = soloNumeros + caracter
                }
            }

            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(telefono = soloNumeros),
                errorTelefono = ""
            )
        }
    }

    // Funció per actualitzar el nom d'usuari
    fun actualizarNombreUsuario(nombreUsuario: String) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario
            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(nombreUsuario = nombreUsuario),
                errorUsuario = ""
            )
        }
    }

    // Funció per actualitzar el password
    fun actualizarPassword(password: String) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario
            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(password = password),
                errorPassword = ""
            )
        }
    }

    // Funció per actualitzar confirmar password
    fun actualizarConfirmarPassword(confirmarPassword: String) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario
            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(confirmarPassword = confirmarPassword),
                errorConfirmarPassword = ""
            )
        }
    }

    // Funció per actualitzar instrument seleccionat
    fun actualizarInstrumento(instrumento: String) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario
            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(instrumento = instrumento),
                errorInstrumento = ""
            )
        }
    }

    // Funció per actualitzar el nivell seleccionat
    fun actualizarNivel(nivel: String) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario
            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(nivel = nivel),
                errorNivel = ""
            )
        }
    }

    // Funció per actualitzar acceptació de termes
    fun actualizarTerminos(acepta: Boolean) {
        val estadoActual = _estadoRegistro.value
        if (estadoActual != null) {
            val usuarioActual = estadoActual.usuario
            _estadoRegistro.value = estadoActual.copy(
                usuario = usuarioActual.copy(aceptaTerminos = acepta),
                errorTerminos = ""
            )
        }
    }

    /**
     * Valida tots els camps i registra l'usuari
     */
    fun registrarUsuario(): Boolean {
        val estadoActual = _estadoRegistro.value
        if (estadoActual == null) {
            return false
        }

        val usuario = estadoActual.usuario

        // Validem camps
        val errorNombre = validarNombre(usuario.nombreCompleto)
        val errorFecha = validarFecha(usuario.fechaNacimiento)
        val errorEmail = validarEmail(usuario.email)
        val errorTelefono = validarTelefono(usuario.telefono)
        val errorUsuario = validarNombreUsuario(usuario.nombreUsuario)
        val errorPassword = validarPassword(usuario.password)
        val errorConfirmar = validarConfirmarPassword(usuario.password, usuario.confirmarPassword)
        val errorInstrumento = validarInstrumento(usuario.instrumento)
        val errorNivel = validarNivel(usuario.nivel)
        val errorTerminos = validarTerminos(usuario.aceptaTerminos)

        // Actualitzem l'estat amb els errors
        _estadoRegistro.value = estadoActual.copy(
            errorNombre = errorNombre,
            errorFecha = errorFecha,
            errorEmail = errorEmail,
            errorTelefono = errorTelefono,
            errorUsuario = errorUsuario,
            errorPassword = errorPassword,
            errorConfirmarPassword = errorConfirmar,
            errorInstrumento = errorInstrumento,
            errorNivel = errorNivel,
            errorTerminos = errorTerminos
        )

        // Comprovem si hi ha algun error
        val hayErrores = errorNombre.isNotEmpty() ||
                errorFecha.isNotEmpty() ||
                errorEmail.isNotEmpty() ||
                errorTelefono.isNotEmpty() ||
                errorUsuario.isNotEmpty() ||
                errorPassword.isNotEmpty() ||
                errorConfirmar.isNotEmpty() ||
                errorInstrumento.isNotEmpty() ||
                errorNivel.isNotEmpty() ||
                errorTerminos.isNotEmpty()

        // Si no hi ha errors, el registre és correcte
        if (hayErrores == false) {
            _usuarioRegistrado.value = usuario
            return true
        }

        return false
    }

    // Funció per netejar l'estat del registre
    fun limpiarRegistro() {
        _estadoRegistro.value = EstadoRegistro()
        _usuarioRegistrado.value = null
    }

    // VALIDACIONS

    private fun validarNombre(nombre: String): String {
        // Comprovem que no estigui buit
        if (nombre.isEmpty()) {
            return "El nom és obligatori"
        }
        // Comprovem longitud mínima
        if (nombre.length < 3) {
            return "El nom ha de tenir mínim 3 caràcters"
        }
        // Comprovem que només tingui lletres i espais
        for (caracter in nombre) {
            if (caracter.isLetter() == false && caracter != ' ') {
                return "El nom només pot contenir lletres"
            }
        }
        return ""
    }

    private fun validarFecha(fecha: String): String {
        // Comprovem que no estigui buit
        if (fecha.isEmpty()) {
            return "La data és obligatòria"
        }
        // Comprovem longitud (DD/MM/AAAA)
        if (fecha.length != 10) {
            return "Format: DD/MM/AAAA"
        }
        // Comprovem format basic
        if (fecha[2] != '/' || fecha[5] != '/') {
            return "Format: DD/MM/AAAA"
        }
        return ""
    }

    private fun validarEmail(email: String): String {
        // Comprovem que no estigui buit
        if (email.isEmpty()) {
            return "L'email és obligatori"
        }
        // Comprovem que contingui @
        if (email.contains("@") == false) {
            return "L'email ha de contenir @"
        }
        // Comprovem que tingui un punt després del @
        if (email.contains(".") == false) {
            return "Email no vàlid"
        }
        return ""
    }

    private fun validarTelefono(telefono: String): String {
        // Comprovem que no estigui buit
        if (telefono.isEmpty()) {
            return "El telèfon és obligatori"
        }
        // Comprovem que tingui 9 dígits
        if (telefono.length != 9) {
            return "El telèfon ha de tenir 9 dígits"
        }
        return ""
    }

    private fun validarNombreUsuario(nombreUsuario: String): String {
        // Comprovem que no estigui buit
        if (nombreUsuario.isEmpty()) {
            return "L'usuari és obligatori"
        }
        // Comprovem longitud minim
        if (nombreUsuario.length < 4) {
            return "Mínim 4 caràcters"
        }
        return ""
    }

    private fun validarPassword(password: String): String {
        // Comprovem que no estigui buit
        if (password.isEmpty()) {
            return "La contrasenya és obligatòria"
        }
        // Comprovem longitud mínima
        if (password.length < 8) {
            return "Mínim 8 caràcters"
        }
        // Comprovem que tingui almenys un número
        var tieneNumero = false
        for (caracter in password) {
            if (caracter.isDigit()) {
                tieneNumero = true
            }
        }
        if (tieneNumero == false) {
            return "Ha de contenir almenys un número"
        }
        return ""
    }

    private fun validarConfirmarPassword(password: String, confirmar: String): String {
        // Comprovem que no estigui buit
        if (confirmar.isEmpty()) {
            return "Confirma la contrasenya"
        }
        // Comprovem que coincideixin
        if (password != confirmar) {
            return "Les contrasenyes no coincideixen"
        }
        return ""
    }

    private fun validarInstrumento(instrumento: String): String {
        // Comprovem que hagi seleccionat un
        if (instrumento.isEmpty()) {
            return "Selecciona un instrument"
        }
        return ""
    }

    private fun validarNivel(nivel: String): String {
        // Comprovem que hagi seleccionat un
        if (nivel.isEmpty()) {
            return "Selecciona el teu nivell"
        }
        return ""
    }

    private fun validarTerminos(acepta: Boolean): String {
        // Comprovem que hagi acceptat
        if (acepta == false) {
            return "Has d'acceptar els termes"
        }
        return ""
    }

    // LOGIN

    private val _estadoLogin = MutableLiveData(EstadoLogin())
    val estadoLogin: LiveData<EstadoLogin> = _estadoLogin

    fun actualizarLoginUsuario(usuario: String) {
        val estadoActual = _estadoLogin.value
        if (estadoActual != null) {
            _estadoLogin.value = estadoActual.copy(
                nombreUsuario = usuario,
                errorUsuario = ""
            )
        }
    }

    fun actualizarLoginPassword(password: String) {
        val estadoActual = _estadoLogin.value
        if (estadoActual != null) {
            _estadoLogin.value = estadoActual.copy(
                password = password,
                errorPassword = ""
            )
        }
    }

    fun hacerLogin(): Boolean {
        val estadoActual = _estadoLogin.value
        if (estadoActual == null) {
            return false
        }

        var errorUsuario = ""
        var errorPassword = ""

        // Validem usuari
        if (estadoActual.nombreUsuario.isEmpty()) {
            errorUsuario = "Introdueix el teu usuari"
        }

        // Validem password
        if (estadoActual.password.isEmpty()) {
            errorPassword = "Introdueix la teva contrasenya"
        }

        // Actualitzem estat amb errors
        _estadoLogin.value = estadoActual.copy(
            errorUsuario = errorUsuario,
            errorPassword = errorPassword
        )

        // Si no hi ha errors, login exitós
        if (errorUsuario.isEmpty() && errorPassword.isEmpty()) {
            return true
        }

        return false
    }

    fun limpiarLogin() {
        _estadoLogin.value = EstadoLogin()
    }
}
