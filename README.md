# Nook & Ink

**Versión 1.2 · CRUD en memoria y repositorios**

---

### Sobre el proyecto

Nook & Ink continúa su desarrollo ampliando la estantería y el detalle de la Versión 1.1. En esta versión, el usuario puede gestionar sus libros mediante un CRUD completo en memoria: puede añadir libros nuevos, editar los existentes y eliminarlos con confirmación.

Los datos se gestionan mediante un repositorio en memoria, separando el acceso a los datos de la interfaz. Las altas y las ediciones se realizan en diálogos con formularios validados, y el borrado requiere una confirmación antes de eliminar el libro.

### Esta versión incluye

- Pantalla principal de estantería con los libros del repositorio.
- Libro “+” para añadir un nuevo libro desde la estantería.
- Pantalla de detalle con la información completa de cada libro.
- Botones de editar y eliminar en el detalle.
- Diálogo de creación y edición con formulario validado.
- Diálogo de confirmación antes de eliminar un libro.
- Repositorio en memoria con operaciones de creación, consulta, actualización y borrado.
- ViewModel con `UiState` para gestionar el estado de la aplicación.
- Snackbar para confirmar las acciones realizadas.
- Navegación entre la estantería, el detalle y el formulario mediante `NavHost`.
- Barra inferior con acceso a la estantería y al formulario de alta.
- Adaptación de las pantallas a la orientación del dispositivo.
- Estructura preparada para futuras versiones con persistencia de datos.

### Funcionalidades

- **Añadir libro:** desde el lomo “+” de la estantería o desde la pestaña “Libro” de la barra inferior.
- **Editar libro:** desde el detalle, mediante el botón “Editar”.
- **Eliminar libro:** desde el detalle, mediante el botón “Eliminar” y confirmando la acción.
- **Validación del formulario:** el título, el texto del lomo, la descripción y la valoración son obligatorios.
- **Valoración:** debe ser un número entre 0 y 5.
- **Color del lomo:** se puede elegir entre cinco colores disponibles.
- **Portada opcional:** se puede añadir una URL de imagen para mostrar la portada en el detalle.
- **Actualización inmediata:** la estantería se actualiza al añadir, editar o eliminar un libro.

### Estructura del proyecto

```text
app/src/main/java/com/example/nookink/
├── MainActivity.kt
├── data/
│   ├── DatosLibros.kt
│   └── LibroRepository.kt
├── model/
│   └── Libro.kt
└── ui/
    ├── AppPantallas.kt
    ├── LibrosUiState.kt
    ├── LibrosViewModel.kt
    ├── detalle/
    │   └── PantallaDetalle.kt
    ├── dialogos/
    │   └── Dialogos.kt
    ├── estanteria/
    │   └── PantallaEstanteria.kt
    ├── formulario/
    │   └── PantallaFormulario.kt
    ├── navegacion/
    │   └── Rutas.kt
    └── theme/
        └── Theme.kt
```
