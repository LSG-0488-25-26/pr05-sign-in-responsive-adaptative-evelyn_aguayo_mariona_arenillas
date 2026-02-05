package com.example.responsiveadaptative.model

data class Usuario(
    val nombreCompleto: String = "",
    val fechaNacimiento: String = "",
    val email: String = "",
    val telefono: String = "",
    val nombreUsuario: String = "",
    val password: String = "",
    val confirmarPassword: String = "",
    val instrumento: String = "",
    val nivel: String = "",
    val aceptaTerminos: Boolean = false
)


data class EstadoRegistro(
    val usuario: Usuario = Usuario(),
    val errorNombre: String = "",
    val errorFecha: String = "",
    val errorEmail: String = "",
    val errorTelefono: String = "",
    val errorUsuario: String = "",
    val errorPassword: String = "",
    val errorConfirmarPassword: String = "",
    val errorInstrumento: String = "",
    val errorNivel: String = "",
    val errorTerminos: String = "",
    val cargando: Boolean = false,
    val registroExitoso: Boolean = false
)


data class EstadoLogin(
    val nombreUsuario: String = "",
    val password: String = "",
    val errorUsuario: String = "",
    val errorPassword: String = "",
    val cargando: Boolean = false,
    val loginExitoso: Boolean = false
)
