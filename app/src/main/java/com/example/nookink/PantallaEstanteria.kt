package com.example.nookink

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nookink.model.Libro
import com.example.nookink.ui.theme.AppBibliotecaTheme
import com.example.nookink.ui.theme.ColorBordeLibro
import com.example.nookink.ui.theme.ColorFondoPantalla
import com.example.nookink.ui.theme.ColorFondoTarjetaBalda
import com.example.nookink.ui.theme.ColorTablaDeMadera


// Pantalla principal: una lista vertical con una tarjeta por balda.
// Avisa con onLibroClick cuando se pulsa un libro.
@Composable
fun PantallaEstanteria(
    onLibroClick: (Libro) -> Unit,
    estadoDeLaLista: LazyListState = rememberLazyListState()
) {

    // Scaffold deja el contenido dentro de la zona libre (sin tapar por las barras del sistema).
    Scaffold(
        containerColor = ColorFondoPantalla,
        contentColor = Color.Black
    ) { innerPadding ->

        LazyColumn(
            state = estadoDeLaLista,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "NOOK & INK",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            itemsIndexed(listaDeBaldas) { index, libros ->
                TarjetaDeBalda(libros, index + 1, onLibroClick)
            }
        }
    }
}


// Tarjeta de Material con una balda: la fila de libros y la tabla de madera.
@Composable
private fun TarjetaDeBalda(libros: List<Libro>, numeroBalda: Int, onLibroClick: (Libro) -> Unit) {

    // Las baldas de 3 libros llevan decoración.
    val hayDecoracion = libros.size < 6

    // Balda 2 y 6: izquierda. Balda 4 y 8: derecha.
    val decoracionALaIzquierda = (numeroBalda / 2) % 2 == 1

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {

        // El color de fondo de la tarjeta va aquí, antes del padding para que cubra también el margen.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorFondoTarjetaBalda)
                .padding(12.dp)
        ) {

            // Todos apoyados abajo. Cada libro pesa 1; la decoración pesa 3.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.Bottom
            ) {

                if (hayDecoracion && decoracionALaIzquierda) {
                    Decoracion(Modifier.weight(3f))
                }

                libros.forEach { libro ->
                    LomoDeLibro(libro, Modifier.weight(1f), onClick = { onLibroClick(libro) })
                }

                if (hayDecoracion && !decoracionALaIzquierda) {
                    Decoracion(Modifier.weight(3f))
                }
            }

            // Tabla de madera bajo los libros: una caja de 8 dp de alto de color madera.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(ColorTablaDeMadera)
            )
        }
    }
}


// Lomo de un libro: rectángulo de su color con el título escrito en vertical.
@Composable
private fun LomoDeLibro(libro: Libro, modifier: Modifier, onClick: () -> Unit) {

    Box(
        modifier = modifier
            .height(libro.altoEnDp.dp)
            .clickable(onClick = onClick)
            .background(libro.colorLomo)
            .border(1.dp, ColorBordeLibro),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = libro.tituloLomo,
            color = Color.White,
            fontSize = 11.sp,
            modifier = Modifier
                .wrapContentWidth(unbounded = true)
                .rotate(90f)
        )
    }
}


// Imagen decorativa de la balda (la fila ya la apoya abajo).
// ContentScale.Fit muestra la imagen entera sin recortarla al girar el móvil.
@Composable
private fun Decoracion(modifier: Modifier) {

    Image(
        painter = painterResource(id = R.drawable.gato),
        contentDescription = "Decoración sobre la balda",
        modifier = modifier.height(100.dp),
        contentScale = ContentScale.Fit
    )
}


@Preview(showBackground = true)
@Composable
private fun PantallaEstanteriaPreview() {
    AppBibliotecaTheme {
        PantallaEstanteria(onLibroClick = {})
    }
}