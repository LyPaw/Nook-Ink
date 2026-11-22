package com.example.nookink.ui

import com.example.nookink.model.Libro

data class LibrosUiState(
    val libroList: List<Libro> = emptyList(),
    val libroToEdit: Libro? = null,
    val libroToDelete: Libro? = null,
    val isAddDialogOpen: Boolean = false,
    val userMessage: String? = null
)