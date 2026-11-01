package com.example.nookink

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

// Decide qué pantalla se muestra según el estado (apartado 15.3 de los apuntes).
// Se guarda el id del libro seleccionado: null = estantería, con valor = detalle de ese libro.
// rememberSaveable hace que, al girar el móvil, no se vuelva a la estantería.
@Composable
fun AppPantallas() {

    var idSeleccionado by rememberSaveable { mutableStateOf<String?>(null) }

    // La posición de la lista se guarda aquí para que, al volver del detalle,
    // la estantería siga en el mismo punto y no salte al principio.
    val estadoDeLaLista = rememberLazyListState()

    // Busca el libro con ese id (si no hay ninguno seleccionado, será null).
    val libro = listaDeLibros.find { it.id == idSeleccionado }

    // No está en los apuntes: hace que el botón atrás del móvil también
    // vuelva a la estantería.
    BackHandler(
        enabled = libro != null,
        onBack = {
            idSeleccionado = null
        }
    )

    if (libro == null) {
        PantallaEstanteria(
            onLibroClick = { idSeleccionado = it.id },
            estadoDeLaLista = estadoDeLaLista
        )
    } else {
        PantallaDetalle(libro, onVolver = { idSeleccionado = null })
    }
}