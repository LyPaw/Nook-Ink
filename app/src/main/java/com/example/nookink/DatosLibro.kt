package com.example.nookink

import androidx.compose.ui.graphics.Color
import com.example.nookink.model.Libro

// Dieciocho libros de ejemplo. Los datos son: id, título, título del lomo,
// descripción, color del lomo y valoración. El resto (altura, imagen...) lo rellena Libro.
val listaDeLibros = listOf(
    Libro("1", "Don Quijote de la Mancha", "Quijote",
        "Un hidalgo enloquece leyendo novelas de caballerías y sale a recorrer La Mancha como caballero andante.", Color(0xFF8E3B2F), 4.6),
    Libro("2", "Cien años de soledad", "Cien años",
        "La historia de la familia Buendía a lo largo de siete generaciones en el pueblo de Macondo.", Color(0xFF2F6B5E), 4.7),
    Libro("3", "La sombra del viento", "La sombra",
        "En la Barcelona de posguerra, un niño descubre un libro misterioso en el Cementerio de los Libros Olvidados.", Color(0xFF3B4A7A), 4.5),
    Libro("4", "1984", "1984",
        "Winston Smith vive vigilado por el Gran Hermano en un régimen que controla hasta el pensamiento.", Color(0xFF555555), 4.4),
    Libro("5", "El principito", "Principito",
        "Un aviador perdido en el desierto conoce a un pequeño príncipe que viaja de planeta en planeta.", Color(0xFF9C6B1F), 4.8),
    Libro("6", "Fahrenheit 451", "Fahrenheit",
        "Guy Montag es un bombero que quema libros, hasta que empieza a cuestionarse su trabajo.", Color(0xFFC0392B), 4.2),
    Libro("7", "Rayuela", "Rayuela",
        "Una novela que puede leerse en distintos órdenes, entre París y Buenos Aires.", Color(0xFF6B4423), 4.3),
    Libro("8", "Crónica de una muerte anunciada", "Crónica",
        "Todo el pueblo sabe que van a matar a Santiago Nasar, pero nadie logra evitarlo.", Color(0xFF7A3B6B), 4.4),
    Libro("9", "El señor de los anillos", "Los anillos",
        "Un hobbit emprende un viaje para destruir un anillo que puede dominar la Tierra Media.", Color(0xFF1F5F3A), 4.8),
    Libro("10", "Harry Potter y la piedra filosofal", "Harry Potter",
        "Un niño huérfano descubre que es mago e ingresa en el colegio Hogwarts.", Color(0xFF5B2C83), 4.7),
    Libro("11", "Orgullo y prejuicio", "Orgullo",
        "Elizabeth Bennet y el señor Darcy superan sus prejuicios en la Inglaterra del siglo XIX.", Color(0xFFA23B5E), 4.5),
    Libro("12", "Matar a un ruiseñor", "Ruiseñor",
        "Una niña del sur de Estados Unidos ve cómo su padre abogado defiende a un hombre acusado injustamente.", Color(0xFF2E5A7A), 4.6),
    Libro("13", "Los miserables", "Miserables",
        "Jean Valjean intenta rehacer su vida mientras lo persigue el inspector Javert en la Francia del XIX.", Color(0xFF4A3B2A), 4.6),
    Libro("14", "Drácula", "Drácula",
        "Un abogado viaja a Transilvania y descubre el terrible secreto del conde Drácula.", Color(0xFF7B1E1E), 4.2),
    Libro("15", "Frankenstein", "Frankenstein",
        "Un joven científico da vida a una criatura y debe afrontar las consecuencias de su obra.", Color(0xFF35553B), 4.1),
    Libro("16", "El nombre de la rosa", "La rosa",
        "Un monje franciscano investiga unas muertes misteriosas en una abadía medieval.", Color(0xFF86334D), 4.4),
    Libro("17", "Rebelión en la granja", "Rebelión",
        "Los animales de una granja expulsan al granjero y crean un sistema que acaba corrompiéndose.", Color(0xFF5E4B8B), 4.3),
    Libro("18", "El Hobbit", "Hobbit",
        "Bilbo Bolsón se une a un grupo de enanos para recuperar un tesoro custodiado por un dragón.", Color(0xFF4B6E2E), 4.7)
)

// Reparte los libros en baldas automáticamente: la primera lleva 6, la segunda 3,
// la tercera 6, la cuarta 3...
private fun crearBaldas(libros: List<Libro>): List<List<Libro>> {

    val baldas = mutableListOf<List<Libro>>()
    var baldaActual = mutableListOf<Libro>()
    var capacidad = 6

    // Recorremos todos los libros.
    libros.forEach { libro ->
        baldaActual.add(libro)

        // Si la balda se ha llenado, la guardamos y empezamos una nueva.
        if (baldaActual.size == capacidad) {
            baldas.add(baldaActual)
            baldaActual = mutableListOf()
            capacidad = if (capacidad == 6) 3 else 6
        }
    }

    // Si quedan libros sueltos en una balda a medias, la guardamos también.
    if (baldaActual.isNotEmpty()) baldas.add(baldaActual)

    return baldas
}

val listaDeBaldas = crearBaldas(listaDeLibros)