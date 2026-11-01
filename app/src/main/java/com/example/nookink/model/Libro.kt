package com.example.nookink.model

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class Libro(
    val id: String,
    val titulo: String,
    val tituloLomo: String,
    val descripcion: String,
    val colorLomo: Color,            // Ahora es un Color directamente (antes era un texto "#8E3B2F").
    val valoracion: Double,
    val tieneImagen: Boolean = false,
    val imagen: String = "",
    val mostradoEnEstanteria: Boolean = true,
    val altoEnDp: Int = Random.nextInt(85, 131)  // Altura del lomo en dp.
)