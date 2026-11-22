package com.example.nookink.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.nookink.data.LibroRepository
import com.example.nookink.model.Libro
import java.util.UUID

class LibrosViewModel : ViewModel() {

    var uiState by mutableStateOf(LibrosUiState())
        private set

    init {
        uiState = uiState.copy(libroList = LibroRepository.getLibros())
    }


    // Alta

    fun onAddLibroClicked() { uiState = uiState.copy(isAddDialogOpen = true) }
    fun onDismissAddDialog() { uiState = uiState.copy(isAddDialogOpen = false) }
    fun onInsertLibro(
        titulo: String, tituloLomo: String, descripcion: String,
        colorLomo: Color, valoracion: Double, imagen: String
    ) {
        LibroRepository.addLibro(
            Libro(
                id = UUID.randomUUID().toString(),
                titulo = titulo, tituloLomo = tituloLomo, descripcion = descripcion,
                colorLomo = colorLomo, valoracion = valoracion,
                tieneImagen = imagen.isNotBlank(), imagen = imagen
            )
        )
        uiState = uiState.copy(
            libroList = LibroRepository.getLibros(),
            isAddDialogOpen = false,
            userMessage = "Libro añadido"
        )
    }

    // Editar

    fun onEditLibroClicked(libro: Libro) { uiState = uiState.copy(libroToEdit = libro) }
    fun onDismissEditDialog() { uiState = uiState.copy(libroToEdit = null) }
    fun onUpdateLibro(
        id: String, titulo: String, tituloLomo: String, descripcion: String,
        colorLomo: Color, valoracion: Double, imagen: String
    ) {
        val original = LibroRepository.getLibroById(id) ?: return
        LibroRepository.updateLibro(
            original.copy(
                titulo = titulo, tituloLomo = tituloLomo, descripcion = descripcion,
                colorLomo = colorLomo, valoracion = valoracion,
                tieneImagen = imagen.isNotBlank(), imagen = imagen
            )
        )
        uiState = uiState.copy(
            libroList = LibroRepository.getLibros(),
            libroToEdit = null,
            userMessage = "Libro actualizado"
        )
    }

    // Borrar
    fun onDeleteLibroClicked(libro: Libro) { uiState = uiState.copy(libroToDelete = libro) }
    fun onDismissDeleteDialog() { uiState = uiState.copy(libroToDelete = null) }

    fun onConfirmDeleteLibro() {
        val libro = uiState.libroToDelete ?: return
        LibroRepository.deleteLibro(libro.id)
        uiState = uiState.copy(
            libroList = LibroRepository.getLibros(),
            libroToDelete = null,
            userMessage = "Libro eliminado"
        )
    }

    // Mensaje
    fun onUserMessageShown() { uiState = uiState.copy(userMessage = null) }
}