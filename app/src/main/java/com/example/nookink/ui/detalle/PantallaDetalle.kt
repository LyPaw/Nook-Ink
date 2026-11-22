package com.example.nookink.ui.detalle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nookink.model.Libro
import com.example.nookink.ui.theme.AppBibliotecaTheme
import com.example.nookink.ui.theme.ColorBordeLibro
import com.example.nookink.ui.theme.ColorFondoPantalla
import com.example.nookink.ui.theme.ColorTablaDeMadera


// Pantalla de detalle de un libro, siguiendo el prototipo:
// X, estrellas, libro con el título, botones Editar/Eliminar y descripción.
// Avisa con onVolver cuando se pulsa la X
@Composable
fun PantallaDetalle(
    libro: Libro,
    onVolver: () -> Unit,
    onEditar: () -> Unit,
    onBorrar: () -> Unit
) {


    // Forma de libro: esquinas casi rectas a la izquierda (el lomo) y más redondeadas a la derecha.
    val formaDeLibro = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp, topEnd = 16.dp, bottomEnd = 16.dp)

    // Scaffold deja el contenido dentro de la zona libre (sin tapar por las barras del sistema).
    Scaffold(
        containerColor = ColorFondoPantalla,
        contentColor = Color.Black
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())   // Scroll por si la descripción es larga.
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // X para volver, arriba a la izquierda.
            IconButton(
                onClick = onVolver,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("✕", fontSize = 28.sp)
            }

            // Valoración: estrellas parcialmente rellenas y la nota exacta.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Estrellas(libro.valoracion)
                    Text("%.1f".format(libro.valoracion), fontSize = 20.sp)
                }
            }

            // El libro: un rectángulo con la forma de libro, del color del libro y con el título en el centro.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .height(340.dp)
                    .background(libro.colorLomo, formaDeLibro)
                    .border(2.dp, ColorBordeLibro, formaDeLibro),
                contentAlignment = Alignment.Center
            ) {
                if (libro.tieneImagen) {
                    AsyncImage(
                        model = libro.imagen,
                        contentDescription = "Portada de ${libro.titulo}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().padding(2.dp).clip(formaDeLibro)
                    )
                } else {
                    Text(
                        text = libro.titulo,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(onClick = onEditar, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)) { Text("Editar") }
                OutlinedButton(onClick = onBorrar, modifier = Modifier.weight(1f) , colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)) { Text("Eliminar") }
            }

            // Descripción del libro.
            Text(
                text = libro.descripcion,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

// Cinco estrellas grises y, encima, las mismas en color recortadas según la nota.
@Composable
private fun Estrellas(valoracion: Double) {
    val relleno = (valoracion / 5).toFloat()

    Box {
        Text("★★★★★", fontSize = 32.sp, color = ColorTablaDeMadera.copy(alpha = 0.3f))
        Text(
            text = "★★★★★",
            fontSize = 32.sp,
            color = ColorTablaDeMadera,
            modifier = Modifier.drawWithContent {
                clipRect(right = size.width * relleno) {
                    this@drawWithContent.drawContent()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PantallaDetallePreview() {
    AppBibliotecaTheme {
        PantallaDetalle(
            libro = Libro(
                id = "1",
                titulo = "Don Quijote de la Mancha",
                tituloLomo = "Quijote",
                descripcion = "Un hidalgo enloquece leyendo novelas de caballerías y sale a recorrer La Mancha.",
                colorLomo = Color(0xFF8E3B2F),
                valoracion = 4.6
            ),
            onVolver = {},
            onEditar = {},
            onBorrar = {}
        )
    }
}