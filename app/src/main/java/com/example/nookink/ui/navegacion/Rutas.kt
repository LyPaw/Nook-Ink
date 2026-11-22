package com.example.nookink.ui.navegacion

import android.net.Uri

object Rutas {
    const val ESTANTERIA = "estanteria"
    const val DETALLE = "detalle/{libroId}"
    const val FORMULARIO = "formulario?libroId={libroId}"

    fun detalle(id: String) = "detalle/${Uri.encode(id)}"

}