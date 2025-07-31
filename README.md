# Proyecto Fresko 

---

**Hecho por:** Jose Lafuente (30 de julio de 2025)

---

## Cambios y Actualizaciones Grandes

-   Ahora un administrador puede tener acceso a el crud para ver usuarios y a las funcionalidades del CRUD(ya implementadas)
-   **Creación de Usuarios por Administrador:** Solo un administrador puede crear usuarios con roles de `ADMIN` y `WORKER`. Si un usuario se registra de forma normal, se registra como `ROLE_CLIENT`. 
-   **Visualización de Productos en el Index:** Mostrar todos los productos en la página principal (`index`).
-   **Filtrado por Categoría:** Al seleccionar una de las 5 secciones (categorías), se deben filtrar y mostrar solo los productos de esa categoría en una URL específica (ej. `localhost/abarrotes`).
---

## Tareas Pendientes (TO-DO)

### Prioridad: ALTA +++

-   **CRUD de Productos:** Crear la funcionalidad CRUD (Crear, Leer, Actualizar, Eliminar) para que los trabajadores y administradores puedan gestionar productos.
-   **Funcionalidad del Carrito:** Implementar la lógica completa del carrito de compras, incluyendo la capacidad de comprar y facturar.

### Prioridad: MEDIA ++

-   **Acceso a Favoritos:** Si un usuario intenta usar la función de "Favoritos" sin estar logueado, se debe mostrar un mensaje emergente solicitando el inicio de sesión.
-   **Búsqueda de Usuarios en CRUD:** Reemplazar la barra de búsqueda en el CRUD del botón "Ver Usuario" para permitir la búsqueda de usuarios específicos.

### Prioridad: BAJA +

-   **Logotipo de Fresko:** Reemplazar el texto "Fresko" con el logotipo de la marca (disponible en Imgur). Solo se requiere el cambio del enlace de la imagen.
-   **Error del Botón de Idioma en Login:** Solucionar el error que impide el funcionamiento del botón de idioma en la página de inicio de sesión. (El error aún no se ha identificado).

---

## Puntos a Mejorar o Errores Conocidos

-   **Falta de Botones "Volver":** Las secciones "Mi Carrito" y "Mi Lista" necesitan un botón para volver a la página anterior.
-   **Opciones de CRUD para Administrador:** Un usuario administrador debería tener opciones de edición completas en el CRUD (actualmente solo se puede eliminar).
-   **Funcionamiento del Botón de Cambio de Idioma:** El botón de cambio de idioma sigue sin funcionar correctamente; la razón aún no se ha determinado.

---
## Work in Progress (WIP por ALF)

-   Interfaz visual
-   Funcionalidad completa de favoritos
-   corrección de errores generales. 
---
## Notas
Para asignar privilegios de administrador, es necesario inyectar el rol en SQL o actualizar el usuario existente a `ROLE_ADMIN`.

-   RECORDAR SIEMPRE HACER PULL ANTES DE EMPEZAR A PROGRAMAR.
-   Hay que buscar algo ""innovador"" para ponerle a la pagina. (que no se haya visto en clase)
