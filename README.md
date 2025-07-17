Hecho por Jose Lafuente 17/7/25

=========== Cambios y actializaciones ============= 

-El boton de idioma ya funciona pero en la pantalla de login/sign up no deja cambiarlo

================= To DO. =================

PRIORIDAD: ALTA+++
-Crear el crud para trabajador y Administrador para ingresar PRODUCTOS
-Crear funcionalidad del carrito. que puedas comprar y facturar etc...
-Para hacer una compra no necesitas ser un usuario, un carrito lo puede hacer cualquier persona sin iniciar sesión.
-En el index deben de aparecer todos los productos, si quiere ver los productos específicos se tiene que seleccionar alguna de las 5 secciones que hay.
 en pocas palabras que cuando selecciones una sección especifica filtre solo los productos con el id y las muestre en una dirección aparte ej localhost/abarrotes

PRIORIDAD: MEDIA++
-si se intenta usar el favoritos sin estar logeado tiene que pedir logearse mostrando un mensaje emergente. 
-reemplazar la barra de búsqueda en el crud  del botón "Ver Usuario" para poder buscar un usuario.

PRIORIDAD: BAJA+
-modificar el texto de "Fresko por un png con el logotipo de la marca. Esta en mi imgur solo es de hacer el cambio por el link"
-solucionar error de boton lenguaje en el log in. (aun no encuentro el error)

=============== Puntos a mejorar o errores ==================

-error actual persistente. a la hora de hacer un pull en NetBeans el message.properties al parecer se corrompe. fijarse en: click derecho en el proyecto/properties/sources/"abajo en esta pestaña que el encoding sea UTF-8" ok y guardar. 
al parecer no entiendo por que se corrompen y no se muestran las tildes y algunas cosas de los .properties de forma adecuada. el error creo que soy yo pero no estoy seguro, no lo entiendo. si alguien encuentra solución pls informarme.

 
-A la sección de mi carrito le hace falta un botón de volver al igual que mi lista. 
un usuario administrador puede tener esa opción en el Crud
-el cambio de idioma el botón no me funciona aun no entiendo el porque.

==========================================

hecho 17/7/25 WIP por ALF
-nuevo html listado.html en templates/usuario
-actualice como se registraban los usuarios, administradores, y trabajadores ahora es ROLE_XXXX 
-ahora un administrador puede tener acceso a el crud para ver usuarios y a las funcionalidades del crud WIP... solo se puede eliminar por el momento
-no es necesario iniciar sesión para utilizar la pagina (medio hecho hay que actualizar funcionalidades carrito mas que nada)
-mi lista de favoritos solo funciona con usuarios registrados (desaparece el boton)
-solo un admin puede crear usuarios admin y trabajador si se registra de manera normal se registra un ROLE_CLIENT (si quieren usar cosas de privilegio admin inyectar en el SQL o actualizar un user con ROLE_ADMIN)
-actualización de los messages.properties 

Hay cosas que no se pueden hacer aun como lo del logotipo porq no lo eh subido pero si no se puede hacer algo mas avisar en el grupo para ver como lo solucionamos en equipo. PV!!!
