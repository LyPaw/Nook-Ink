package com.example.nookink.ui.formulario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nookink.ui.dialogos.FormField
import com.example.nookink.ui.dialogos.PaletaLomo
import com.example.nookink.ui.dialogos.SelectorDeColor
import com.example.nookink.ui.theme.ColorFondoPantalla
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.example.nookink.ui.theme.AppBibliotecaTheme
import com.example.nookink.ui.theme.ColorTablaDeMadera

// Pantalla para añadir un libro (la otra forma es el diálogo del libro "+").
@Composable
fun PantallaFormulario(
    onCancelar: () -> Unit,
    onGuardar: (titulo: String, tituloLomo: String, descripcion: String, colorLomo: Color, valoracion: Double, imagen: String) -> Unit
) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var tituloLomo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var valoracion by rememberSaveable { mutableStateOf("") }
    var showErrors by rememberSaveable { mutableStateOf(false) }
    var colorLomo by rememberSaveable { mutableIntStateOf(PaletaLomo.first().toArgb()) }
    var imagen by rememberSaveable { mutableStateOf("") }
    val nota = valoracion.replace(',', '.').toDoubleOrNull()

    val tituloError = if (titulo.isBlank()) "Obligatorio" else null
    val lomoError = when {
        tituloLomo.isBlank() -> "Obligatorio"
        tituloLomo.length > 14 -> "Máximo 14 caracteres"
        else -> null
    }
    val descripcionError = if (descripcion.isBlank()) "Obligatorio" else null
    val valoracionError = if (nota == null || nota < 0.0 || nota > 5.0) "Escribe un número de 0 a 5" else null
    val isFormValid = tituloError == null && lomoError == null && descripcionError == null && valoracionError == null
    Scaffold(
        containerColor = ColorFondoPantalla,
        contentColor = Color.Black
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Nuevo libro", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            FormField(titulo, { titulo = it }, "Título *", if (showErrors) tituloError else null)
            FormField(tituloLomo, { tituloLomo = it }, "Texto del lomo *", if (showErrors) lomoError else null)
            FormField(descripcion, { descripcion = it }, "Descripción *", if (showErrors) descripcionError else null)
            SelectorDeColor(colorLomo) { colorLomo = it }
            FormField(valoracion, { valoracion = it }, "Valoración (0 a 5) *", if (showErrors) valoracionError else null)
            FormField(imagen, { imagen = it }, "URL de la portada (opcional)")

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(onClick = onCancelar, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black) ) { Text("Cancelar") }
                Button(
                    onClick = {
                        if (isFormValid && nota != null) {
                            onGuardar(titulo.trim(), tituloLomo.trim(), descripcion.trim(), Color(colorLomo), nota, imagen.trim())
                        } else {
                            showErrors = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Guardar") }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PantallaFormularioPreview() {
    AppBibliotecaTheme {
        PantallaFormulario(
            onCancelar = {},
            onGuardar = { _, _, _, _, _, _ -> }
        )
    }
}