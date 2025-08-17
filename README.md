# Proyecto Fresko 

---

**Hecho por:** Jose Lafuente (17 de agosto de 2025)

---

## Cambios y Actualizaciones Grandes

- El botón de Gestion de productos esta implementado.
- Implementación de firebase para las imágenes de los productos.
- Detalle de productos mejorado.
- Ahora en detalle productos hay una sección de productos relacionados.
- Visualización de la lista de carritos de forma mejorada y mas agradable para el usuario.
- En el carrito de compras la lista poder subir la cantidad de productos algo como "(-[producto]+)" hecho con un cuadro de texto y dos botones, cuando se haga una compra se tiene que restar la cantidad de productos disponibles en la base de datos.
- Si selecciona un producto de la lista tiene que mostrarse la pagina de detalle de ese producto especifico.
- Se creo la carpeta de carrito dentro de templates, para el orden de los archivos .HTML de carrito.
- Se creo carpeta de .js.

---

## Tareas Pendientes (TO-DO)

### Prioridad: ALTA +++

**En ".producto" (BACKEND)**
- Agregar al carrito de detalles producto no funciona.
- Los relacionados deben de tener un botón de añadir al carrito (BOTON DE AGREGAR AL CARRITO CON LA MISMA FUNCIONALIDAD QUE LA QUE TIENE EL INDEX "use one time") y el botón de favoritos. Si se clickea la tarjeta debe de mostrar el detalle de ese producto (como en el index vaya, hay que replicar la estetica para mantener armonia.)

**En ".templates/index" (BACKEND)**
- Hacer que la barra de búsqueda funcione para buscar productos en el index. También en las demás pestañas. Ejemplo si estas en la pestaña de usuarios que se cambie de searchbar y se puedan buscar personas, así con cada implementación "listado" que lo requiera menos el carrito de compras. (productos index, listado de admin de productos al igual que el de personas y la pestaña favoritos igual.)

**En ".carrito" (BACKEND)**
- El tema de facturación en el proyecto no estoy claro hay que revisar eso no estoy seguro de si funciona correctamente.

**Visual general (FRONT END)**
- Arreglar internacionalización no funciona correctamente de nuevo. (tiene que funcionar en to lao)
- Hay secciones que no están internacionalizados, hace falta crear keys y asignarlas a los textos necesarios (hay que irse fijando poco a poco).

---

### Prioridad: MEDIA ++

**Favoritos (FRONT END)**
- Título centrado.
- Las tablas tienen que ser del mismo tamaño.
- Reducir el tamaño de las tarjetas a uno mas.. bonito.
- Ver el detalle de producto en favoritos si se cliquea la tarjeta.
- Poder add al carrito desde favs (no funca).

**Index (FRONT END)**
- Dividir los productos por secciones.
- Arreglar que cuando quite o ponga un favorito me envíe hasta arriba de la pagina y no se mantenga en la posición en la que esta.
- Botón de volver de secciones de categorías (index).

---

### Prioridad: BAJA +

- **Logotipo de Fresko:** Reemplazar el texto "Fresko" con el logotipo de la marca (disponible en Imgur). Solo se requiere el cambio del enlace de la imagen.

**Detalleproducto (FRONT END)**
- Poner en vez algo que diga en stock o no hay stock (si las existencias son 0, y si las existencias no dejar poder añadir al carrito).

**Carrito (FRONT END)**
- Botón volver en el carrito.

**Gestionar productos (FRONT END)**
- Botón de volver de la pagina de gestionar productos.

**Ver usuarios (FRONT END)**
- Botón de volver de la pagina de gestionar de ver usuarios.

---

## Puntos a Mejorar o Errores Conocidos

- Hay que hacer un testeo general para evaluar nuevos errores. Si se encuentra alguno anotar en esa sección.

---
## Work in Progress (WIP por ALF)

- Se esta creando un .js para peticiones ajax el cual sea dinamico, este funciona para que cuando haya una peticion o actualizacion la pagina no se vaya todo hasta arriba cada que se selecciona un boton o se hace un cambio. (Por el momento el boton de eliminar de el carrito no funciona se estan haciendo pruebas con el.)

---
## Notas
Para asignar privilegios de administrador, es necesario inyectar el rol en SQL o actualizar el usuario existente a `ROLE_ADMIN`.

- RECORDAR SIEMPRE HACER PULL ANTES DE EMPEZAR A PROGRAMAR.
- Hay que buscar algo ""innovador"" para ponerle a la pagina. (que no se haya visto en clase).
