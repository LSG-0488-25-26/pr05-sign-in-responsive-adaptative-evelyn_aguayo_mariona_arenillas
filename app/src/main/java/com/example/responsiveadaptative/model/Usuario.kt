package com.example.responsiveadaptative.model

/**
 * Data class que representa un usuari
 */
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
