package com.example.nookink.data

import com.example.nookink.model.Libro

object LibroRepository {

    private val libros = librosIniciales.toMutableList()

    fun getLibros(): List<Libro> = libros.toList()

    fun getLibroById(id: String): Libro? = libros.find { it.id == id }

    fun addLibro(libro: Libro) { libros.add(libro) }

    fun updateLibro(libro: Libro) {
        val i = libros.indexOfFirst { it.id == libro.id }
        if (i != -1) libros[i] = libro
    }

    fun deleteLibro(id: String) { libros.removeAll { it.id == id } }
}