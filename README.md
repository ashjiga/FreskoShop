Hecho por Jose Lafuente 7/7/25

=========== Cambios y actializaciones ============= 

Por aquello hay que hacerle una modificación a la DB hay que agregarle una columna mas a la tabla de Usuario si no da error a la hora de registrar un usuario

solo abran el workbench y agregen estas líneas y la ejecutan debería de funcionar.

USE fresko;
ALTER TABLE usuario ADD COLUMN rol VARCHAR(30);

(modifique el fresco sql que esta dentro del proyecto por si quieren hacerlo desde cero con este cambio)

-Cambio del aspecto grafico en general.
-modifique el index 
-modifique los message properties muchas veces que cansado con eso...
-modifique el query en recursos del proyecto. con querys para llenar las tablas (hay unas que son importantes porfa no saltarselas). 
-Muchos cambios ya no me acuerdo.
-Hay cambios en el penpot por si lo quieren ver hice unas prevews. MUY vagas de como se tendría que ver maso menos si quieren hacerlo de otra forma o se les hace mas sencillo acomodar algo de otro modo, nada mas hacerlo pero sin desfasarse mucho de como se ve el diseño. (Parece un FB de comida TBH)

================= To DO. =================

-El inicio de sesión no es obligatorio. Es nada mas para administradores (administrar usuarios CRUD) y para usuarios que quieren utilizar la herramienta de "Mi lista de favoritos" 

-si se intenta usar el favoritos sin estar logeado tiene que pedir logearse. (creo que hay que modificar un poco el SQL para esto y para el punto superior también por como esta funcionando en este momento el comprar. Segun la logica del crud)

-Para hacer una compra no necesitas ser un usuario, un carrito lo puede hacer cualquier persona sin iniciar sesión. 

-en el index deben de aparecer todos los productos, si quiere ver los productos se tiene que seleccionar alguna de las 5 secciones que hay. 

-modificar el texto de "Fresko por un png  con el logotipo de la marca. Esta en  mi imgur solo es de hacer el cambio por el link"

-Si inicias sesión como Administrador te debe de aparecer un botón a un lado del de cerrar sesión el cual te lleve a una lista tipo Crud en donde puedas eliminar usuarios ya sean trabajadores, administradores o clientes y que tengan las funcionalidades básicas de un crud vaya... jajaja 

- A la  sección de mi carrito le hace falta un botón de volver al igual que mi lista. 
=============== Puntos a mejorar o errores ==================
-al registrarse solo se puede registrar como usuario no puede dar la opción de que cualquiera se pueda registrar como quiera solo un usuario administrador puede tener esa opción en el Crud

- el cambio de idioma el botón no me funciona aun no entiendo el porque. 


==========================================

Hay cosas que no se pueden hacer aun como lo del logotipo porq no lo eh subido pero si no se puede me dicen. PV!!!
