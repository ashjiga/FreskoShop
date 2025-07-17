# Proyecto Fresko 

---

**Hecho por:** Jose Lafuente (17 de julio de 2025)

---

## Cambios y Actualizaciones

-   El botón de idioma ya funciona, pero no permite cambiar el idioma en las pantallas de inicio de sesión/registro.

---

## Tareas Pendientes (TO-DO)

### Prioridad: ALTA +++

-   **CRUD de Productos:** Crear la funcionalidad CRUD (Crear, Leer, Actualizar, Eliminar) para que los trabajadores y administradores puedan gestionar productos.
-   **Funcionalidad del Carrito:** Implementar la lógica completa del carrito de compras, incluyendo la capacidad de comprar y facturar.
    -   No es necesario ser un usuario registrado para realizar una compra; cualquier persona puede usar el carrito sin iniciar sesión.
-   **Visualización de Productos en el Index:** Mostrar todos los productos en la página principal (`index`).
-   **Filtrado por Categoría:** Al seleccionar una de las 5 secciones (categorías), se deben filtrar y mostrar solo los productos de esa categoría en una URL específica (ej. `localhost/abarrotes`).

### Prioridad: MEDIA ++

-   **Acceso a Favoritos:** Si un usuario intenta usar la función de "Favoritos" sin estar logueado, se debe mostrar un mensaje emergente solicitando el inicio de sesión.
-   **Búsqueda de Usuarios en CRUD:** Reemplazar la barra de búsqueda en el CRUD del botón "Ver Usuario" para permitir la búsqueda de usuarios específicos.

### Prioridad: BAJA +

-   **Logotipo de Fresko:** Reemplazar el texto "Fresko" con el logotipo de la marca (disponible en Imgur). Solo se requiere el cambio del enlace de la imagen.
-   **Error del Botón de Idioma en Login:** Solucionar el error que impide el funcionamiento del botón de idioma en la página de inicio de sesión. (El error aún no se ha identificado).

---

## Puntos a Mejorar o Errores Conocidos

-   **Error persistente: Corrupción de `messages.properties` en NetBeans:**
    -   **Problema:** Al realizar un "pull" en NetBeans, el archivo `messages.properties` parece corromperse, mostrando tildes y otros caracteres de forma incorrecta.
    -   **Posible Solución Temporal:** Asegúrate de que la codificación de fuentes en la configuración del proyecto sea **UTF-8**. Puedes verificarlo en `Clic derecho en el proyecto > Properties > Sources > Encoding: UTF-8`.
    -   **Incertidumbre:** Aún no se comprende la causa raíz de por qué se corrompen estos archivos. Si alguien encuentra una solución definitiva, por favor informar.

-   **Falta de Botones "Volver":** Las secciones "Mi Carrito" y "Mi Lista" necesitan un botón para volver a la página anterior.
-   **Opciones de CRUD para Administrador:** Un usuario administrador debería tener opciones de edición completas en el CRUD (actualmente solo se puede eliminar).
-   **Funcionamiento del Botón de Cambio de Idioma:** El botón de cambio de idioma sigue sin funcionar correctamente; la razón aún no se ha determinado.

---

## Actualizaciones Recientes (17 de julio de 2025 - WIP por ALF)

-   **`listado.html`:** Se añadió un nuevo archivo `listado.html` en `templates/usuario`.
-   **Registro de Usuarios:** Se actualizó la forma en que se registran los usuarios, administradores y trabajadores. Ahora utilizan el formato `ROLE_XXXX` (ej. `ROLE_ADMIN`, `ROLE_WORKER`).
-   **Funcionalidades de Administrador (CRUD):** Los administradores ahora tienen acceso al CRUD para ver usuarios y algunas funcionalidades (por el momento, solo se puede eliminar).
-   **Acceso a la Página sin Iniciar Sesión:** Ya no es estrictamente necesario iniciar sesión para utilizar la página (esto está a medio hacer, principalmente requiere la actualización de las funcionalidades del carrito).
-   **Lista de Favoritos:** La función "Mi lista de favoritos" solo está disponible para usuarios registrados (el botón desaparece si no hay sesión iniciada).
-   **Creación de Usuarios por Administrador:** Solo un administrador puede crear usuarios con roles de `ADMIN` y `WORKER`. Si un usuario se registra de forma normal, se registra como `ROLE_CLIENT`. Para asignar privilegios de administrador, es necesario inyectar el rol en SQL o actualizar el usuario existente a `ROLE_ADMIN`.
