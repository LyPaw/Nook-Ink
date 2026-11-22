package com.example.nookink.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nookink.ui.detalle.PantallaDetalle
import com.example.nookink.ui.dialogos.AddEditLibroDialog
import com.example.nookink.ui.dialogos.ConfirmDeleteDialog
import com.example.nookink.ui.estanteria.PantallaEstanteria
import com.example.nookink.ui.formulario.PantallaFormulario
import com.example.nookink.ui.navegacion.Rutas
import com.example.nookink.ui.theme.ColorFondoPantalla
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun AppPantallas(viewModel: LibrosViewModel = viewModel()) {

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState = viewModel.uiState
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
            viewModel.onUserMessageShown()
        }
    }

    // Un único Scaffold para toda la app
    Scaffold(
        containerColor = ColorFondoPantalla,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BarraInferior(
                estanteriaSeleccionada = rutaActual == Rutas.ESTANTERIA || rutaActual == Rutas.DETALLE,
                libroSeleccionado = rutaActual == Rutas.FORMULARIO,
                onEstanteria = { navController.popBackStack(Rutas.ESTANTERIA, inclusive = false) },
                onLibro = { irAlFormulario(navController) }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Rutas.ESTANTERIA,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300)) }
        ) {

            // El estado se lee DENTRO de cada destino, no fuera del NavHost: si el bloque
            // del NavHost capturara uiState se reconstruiría en cada cambio.
            composable(Rutas.ESTANTERIA) {
                PantallaEstanteria(
                    libros = viewModel.uiState.libroList,
                    onLibroClick = { libro ->
                        navController.navigate(Rutas.detalle(libro.id)) { launchSingleTop = true }
                    },
                    onAddClick = { viewModel.onAddLibroClicked() }
                )
            }

            composable(
                Rutas.DETALLE,
                arguments = listOf(navArgument("libroId") { type = NavType.StringType })
            ) { entrada ->
                val id = entrada.arguments?.getString("libroId")
                val libro = viewModel.uiState.libroList.find { it.id == id }

                // Tras eliminar, libro es null mientras dura la animación de salida.
                if (libro != null) {
                    PantallaDetalle(
                        libro = libro,
                        onVolver = { volver(navController) },
                        onEditar = { viewModel.onEditLibroClicked(libro) },
                        onBorrar = { viewModel.onDeleteLibroClicked(libro) }
                    )
                }
            }

            // Pestaña del libro: pantalla con el formulario de alta.
            composable(Rutas.FORMULARIO) {
                PantallaFormulario(
                    onCancelar = { volver(navController) },
                    onGuardar = { titulo, lomo, descripcion, color, valoracion, imagen ->
                        viewModel.onInsertLibro(titulo, lomo, descripcion, color, valoracion, imagen)
                        volver(navController)
                    }
                )
            }
        }
    }

    // Confirmación de borrado, encima de la pantalla actual.
    uiState.libroToDelete?.let { libro ->
        ConfirmDeleteDialog(
            libro = libro,
            onDismiss = { viewModel.onDismissDeleteDialog() },
            onConfirm = {
                viewModel.onConfirmDeleteLibro()
                volver(navController)        // vuelve a la estantería ya actualizada
            }
        )
    }

    if (uiState.isAddDialogOpen) {
        AddEditLibroDialog(
            onDismiss = { viewModel.onDismissAddDialog() },
            onConfirm = { titulo, lomo, descripcion, color, valoracion, imagen ->
                viewModel.onInsertLibro(titulo, lomo, descripcion, color, valoracion, imagen)
            }
        )
    }

    uiState.libroToEdit?.let { libro ->
        AddEditLibroDialog(
            libroToEdit = libro,
            onDismiss = { viewModel.onDismissEditDialog() },
            onConfirm = { titulo, lomo, descripcion, color, valoracion, imagen ->
                viewModel.onUpdateLibro(libro.id, titulo, lomo, descripcion, color, valoracion, imagen)
            }
        )
    }
}



// Se vuelve con popBackStack, no con navigate(ESTANTERIA), que apilaría otra estantería.
// El if evita que, con dos toques rápidos, el segundo pop saque también la estantería.
private fun volver(navController: NavController) {
    if (navController.previousBackStackEntry != null) navController.popBackStack()
}

// Abre la pestaña del libro (alta). Sale de cualquier pantalla y deja la estantería debajo.
private fun irAlFormulario(navController: NavController) {
    navController.navigate(Rutas.FORMULARIO) {
        popUpTo(Rutas.ESTANTERIA)
        launchSingleTop = true
    }
}

@Composable
private fun BarraInferior(
    estanteriaSeleccionada: Boolean,
    libroSeleccionado: Boolean,
    onEstanteria: () -> Unit,
    onLibro: () -> Unit
) {
    Surface() {
        // El padding de la barra del sistema va fuera de la altura de 56 dp.
        Row(modifier = Modifier.fillMaxWidth().height(56.dp)) {
            ItemBarra(Icons.Default.Home, "Estantería", estanteriaSeleccionada, onEstanteria, Modifier.weight(1f))
            ItemBarra(Icons.Default.Add, "Libro", libroSeleccionado, onLibro, Modifier.weight(1f))
        }
    }
}

@Composable
private fun ItemBarra(
    icono: ImageVector,
    etiqueta: String,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val color = if (seleccionado) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = modifier.fillMaxHeight().clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // El texto de debajo ya describe la acción, así que el icono no lleva descripción.
        Icon(icono, contentDescription = null, tint = color)
        Text(
            text = etiqueta,
            fontSize = 11.sp,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
            color = color
        )
    }
}