package com.example.nookink.ui.dialogos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.nookink.model.Libro
import com.example.nookink.ui.theme.ColorTablaDeMadera

@Composable
fun ConfirmDeleteDialog(libro: Libro, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar libro") },
        text = { Text("¿Seguro que quieres eliminar «${libro.titulo}»?") },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) { Text("Eliminar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
fun FormField(value: String, onValueChange: (String) -> Unit, label: String, error: String? = null) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = ColorTablaDeMadera,
            unfocusedBorderColor = ColorTablaDeMadera
        )
    )
}
// Sirve para crear (libroToEdit = null) y para editar.
@Composable
fun AddEditLibroDialog(
    libroToEdit: Libro? = null,
    onDismiss: () -> Unit,
    onConfirm: (titulo: String, tituloLomo: String, descripcion: String, colorLomo: Color, valoracion: Double, imagen: String) -> Unit) {
    var titulo by rememberSaveable { mutableStateOf(libroToEdit?.titulo ?: "") }
    var tituloLomo by rememberSaveable { mutableStateOf(libroToEdit?.tituloLomo ?: "") }
    var descripcion by rememberSaveable { mutableStateOf(libroToEdit?.descripcion ?: "") }
    var valoracion by rememberSaveable { mutableStateOf(libroToEdit?.valoracion?.toString() ?: "") }
    var showErrors by rememberSaveable { mutableStateOf(false) }
    var colorLomo by rememberSaveable { mutableStateOf((libroToEdit?.colorLomo ?: PaletaLomo.first()).toArgb()) }
    var imagen by rememberSaveable { mutableStateOf(libroToEdit?.imagen ?: "") }
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
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (libroToEdit == null) "Nuevo libro" else "Editar libro") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FormField(titulo, { titulo = it }, "Título *", if (showErrors) tituloError else null)
                FormField(tituloLomo, { tituloLomo = it }, "Texto del lomo *", if (showErrors) lomoError else null)
                FormField(descripcion, { descripcion = it }, "Descripción *", if (showErrors) descripcionError else null)
                SelectorDeColor(colorLomo) { colorLomo = it }
                FormField(valoracion, { valoracion = it }, "Valoración (0 a 5) *", if (showErrors) valoracionError else null)
                FormField(imagen, { imagen = it }, "URL de la portada (opcional)")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (isFormValid && nota != null) {
                    onConfirm(titulo.trim(), tituloLomo.trim(), descripcion.trim(), Color(colorLomo), nota, imagen.trim())
                } else {
                    showErrors = true
                }
            }) { Text(if (libroToEdit == null) "Guardar" else "Actualizar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

val PaletaLomo = listOf(
    Color(0xFF8E3B2F), Color(0xFF2F6B5E), Color(0xFF3B4A7A), Color(0xFF9C6B1F), Color(0xFF5B2C83)
)

@Composable
fun SelectorDeColor(seleccionado: Int, onSeleccion: (Int) -> Unit) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text("Color del lomo")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 4.dp)) {
            PaletaLomo.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color, CircleShape)
                        .border(if (color.toArgb() == seleccionado) 3.dp else 1.dp, Color.Black, CircleShape)
                        .clickable { onSeleccion(color.toArgb()) }
                )
            }
        }
    }
}