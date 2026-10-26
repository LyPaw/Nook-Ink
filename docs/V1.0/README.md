# Nook-Ink
Proyecto Final de Programación Multimedia y Dispositivos Móviles (DAM): desarrollo de una aplicación Android de biblioteca personal interactiva con Kotlin y Jetpack Compose.

## Prototipo de pantallas

![Prototipos de las pantallas de Nook Ink](https://github.com/LyPaw/Nook-Ink/blob/6e2778b566f822d937bbc6b9a64845644c4e9344/docs/V1.0/prototipos/pantallas.png)

## Diagrama de Flujo

```mermaid
flowchart TB
    inicio([Abrir aplicación])
    presentacion["Vídeo de<br/>presentación"]
    navegacion["Navegación<br/>(barra inferior)"]

    inicio --> presentacion --> navegacion

    %% 1. Estantería
    estanteria["1 · Estantería"]
    verEstanteria["Ver estantería<br/>(libros por defecto<br/>si no hay sesión)"]
    hueco["Pulsar hueco<br/>gris"]
    decoracion["Elegir<br/>decoración"]
    detalle["Ver detalle de<br/>un libro"]
    editarEstanteria["Editar libro"]
    quitarEstanteria["Quitar de la<br/>estantería"]
    menu["Abrir menú<br/>lateral"]
    fondo["Cambiar color de<br/>fondo"]
    datosPerfil["Ver foto, nombre<br/>y correo"]

    navegacion --> estanteria
    estanteria --> verEstanteria
    verEstanteria --> hueco --> decoracion
    verEstanteria --> detalle
    detalle --> editarEstanteria
    detalle --> quitarEstanteria
    verEstanteria --> menu
    menu --> fondo
    menu --> datosPerfil

    %% 2. Añadir / editar
    anadir["2 · Añadir /<br/>editar"]
    formulario["Rellenar formulario del libro<br/>Título, lomo,<br/>descripción…"]
    valoracion["Valorar con<br/>estrellas"]
    portada["Importar portada"]
    origenPortada["Desde galería o<br/>cámara"]
    sesion{"¿Sesión<br/>iniciada?"}
    guardar["Guardar libro"]
    pedirSesion["Pedir inicio de<br/>sesión"]

    navegacion --> anadir
    anadir --> formulario
    formulario --> valoracion
    valoracion --> portada
    portada --> origenPortada
    origenPortada --> sesion
    sesion -->|Sí| guardar
    sesion -->|No| pedirSesion

    %% 3. Mapa
    mapa["3 · Mapa"]
    servicioMapas(["Servicio de<br/>mapas"])
    lugares["Ver librerías y<br/>bibliotecas<br/>cercanas"]
    filtro["Filtrar por tipo<br/>de lugar"]

    navegacion --> mapa
    mapa --> lugares
    servicioMapas --> lugares
    lugares --> filtro

    %% 4. Escáner
    escaner["4 · Escáner"]
    googleLens(["Google Lens"])
    escanear["Escanear portada<br/>o ISBN"]
    buscar["Buscar<br/>información del<br/>libro"]
    resultados["Mostrar datos<br/>encontrados"]

    navegacion --> escaner
    escaner --> escanear
    googleLens --> escanear
    escanear --> buscar
    buscar --> resultados

    %% 5. Usuario
    usuario["5 · Usuario"]
    google(["Google"])
    acceso["Iniciar sesión /<br/>Cerrar sesión"]
    crearCuenta["Crear cuenta"]
    politica["Política de la<br/>aplicación"]
    historial["Historial de<br/>libros"]
    librosCuenta["Ver libros de la<br/>cuenta"]
    elegirLibros["Elegir cuáles se<br/>ven en la<br/>estantería"]
    eliminarLibro["Eliminar libro"]
    editarCuenta["Editar libro"]
    fotoPerfil["Cambiar foto de<br/>perfil"]
    elegirFoto["Elegir predeterminada o<br/>importar de<br/>galería"]

    navegacion --> usuario
    google --> acceso
    usuario --> acceso
    acceso --> crearCuenta
    usuario --> politica
    usuario --> historial
    historial --> librosCuenta
    librosCuenta --> elegirLibros
    librosCuenta --> eliminarLibro
    librosCuenta --> editarCuenta
    usuario --> fotoPerfil
    fotoPerfil --> elegirFoto

    %% Estilos
    classDef inicioFin fill:#fff2c6,stroke:#c49a45,color:#292929,stroke-width:1.5px;
    classDef navegacion fill:#1e293b,stroke:#64748b,color:#ffffff,stroke-width:1.5px;
    classDef seccion fill:#0f172a,stroke:#0891b2,color:#ffffff,stroke-width:1.5px;
    classDef proceso fill:#f8fafc,stroke:#7b8794,color:#1f2937,stroke-width:1px;
    classDef accion fill:#dcfce7,stroke:#55a875,color:#1f2937,stroke-width:1.5px;
    classDef servicio fill:#f3e8ff,stroke:#9467bd,color:#4b3869,stroke-width:1.5px;
    classDef decision fill:#fff2c6,stroke:#c49a45,color:#292929,stroke-width:1.5px;

    class inicio inicioFin;
    class navegacion navegacion;
    class estanteria,anadir,mapa,escaner,usuario seccion;
    class presentacion,verEstanteria,hueco,decoracion,detalle,menu,datosPerfil,lugares,filtro,escanear,buscar,resultados,acceso,crearCuenta,politica,pedirSesion proceso;
    class editarEstanteria,quitarEstanteria,fondo,formulario,valoracion,portada,origenPortada,guardar,historial,librosCuenta,elegirLibros,eliminarLibro,editarCuenta,fotoPerfil,elegirFoto accion;
    class servicioMapas,google,googleLens servicio;
    class sesion decision;
```

## Diagrama de casos
<p align="center">
  <img src="https://github.com/LyPaw/Nook-Ink/blob/6e2778b566f822d937bbc6b9a64845644c4e9344/docs/V1.0/diagramas/diagrama_casos.png?raw=true" alt="Diagrama de casos de uso" width="400">
</p>


