# Fullstack --- Clase 6: HTML, Login y JavaScript

## Objetivo de la clase

En esta clase se continúa trabajando con la integración de:

-   HTML
-   CSS
-   JavaScript

Los principales cambios de esta clase son:

-   Se crea una página de **inicio de sesión (login)**.
-   Se utilizan formularios HTML para recibir usuario y contraseña.
-   Se conectan elementos HTML con JavaScript mediante `id`.
-   Se trabaja con eventos `submit`.
-   Se utiliza `event.preventDefault()`.
-   Se crean objetos en JavaScript.
-   Se agregan validaciones mediante funciones.
-   Se compara la información ingresada con datos definidos previamente.
-   Se utiliza `alert()` para informar al usuario.
-   Se utiliza `window.location.href` para navegar entre páginas.
-   Se incorpora `input type="password"` para contraseñas.

> Esta documentación está basada en los archivos trabajados en la clase.
> El `README` anterior se ignora como fuente de contenido de esta clase,
> aunque su organización sirve como referencia para mantener una
> estructura similar en Obsidian.

------------------------------------------------------------------------

# 1. Estructura del proyecto

Los archivos utilizados en la clase son:

``` text
Proyecto
│
├── index.html
├── login.html
├── tabla.html
├── estilo.css
├── funcionalidades.js
└── login.js
```

Cada archivo tiene una responsabilidad:

``` text
index.html
    ↓
Formulario de registro

login.html
    ↓
Formulario de inicio de sesión

tabla.html
    ↓
Visualización de datos guardados

estilo.css
    ↓
Diseño visual

funcionalidades.js
    ↓
Lógica del formulario de registro

login.js
    ↓
Lógica del login
```

------------------------------------------------------------------------

# 2. HTML --- Formulario de Login

El archivo `login.html` contiene el formulario para iniciar sesión.

La estructura principal es:

``` html
<div class="formularioLogin">
    <form class="contenedorLogin" id="formLogin">

        <h1 class="tituloLogin">Inicio de Sesión</h1>

        <div class="login">
            ...
        </div>

        <div class="botonesLogin">
            ...
        </div>

    </form>
</div>
```

El elemento más importante es:

``` html
<form id="formLogin">
```

El `id` permite que JavaScript encuentre posteriormente este formulario.

En `login.js`:

``` javascript
const formularioLogin = document.getElementById("formLogin");
```

La relación es:

``` text
HTML
  ↓
id="formLogin"
  ↓
JavaScript
  ↓
getElementById("formLogin")
```

------------------------------------------------------------------------

# 3. `label` e `input`

Para el usuario:

``` html
<label>Usuario</label>
<input type="text" id="usuario" placeholder="pedrito666xxx">
```

Para la contraseña:

``` html
<label>Password</label>
<input type="password" id="password">
```

La estructura es:

``` text
label
  ↓
describe el campo

input
  ↓
permite ingresar el dato
```

------------------------------------------------------------------------

# 4. `type="text"`

El campo de usuario utiliza:

``` html
<input type="text">
```

Este tipo de `input` permite ingresar texto normalmente.

Ejemplo:

``` html
<input type="text" id="usuario">
```

El usuario puede escribir:

``` text
juanito
```

------------------------------------------------------------------------

# 5. `type="password"`

La contraseña utiliza:

``` html
<input type="password" id="password">
```

Este tipo de campo oculta visualmente los caracteres introducidos.

Por ejemplo, en lugar de mostrar:

``` text
12345678
```

el navegador normalmente muestra algo similar a:

``` text
••••••••
```

Esto permite que la contraseña no quede visible directamente en
pantalla.

> En este ejercicio, el `password` se utiliza como campo de contraseña,
> pero la autenticación todavía es una implementación local y
> simplificada. No existe un backend ni una base de datos de usuarios.

------------------------------------------------------------------------

# 6. `placeholder`

El campo de usuario contiene:

``` html
placeholder="pedrito666xxx"
```

`placeholder` muestra un texto de referencia mientras el campo está
vacío.

Por ejemplo:

``` text
Usuario
┌──────────────────────────┐
│ pedrito666xxx            │
└──────────────────────────┘
```

Cuando el usuario comienza a escribir, el `placeholder` desaparece.

Es importante distinguir:

``` text
placeholder
    ↓
texto de ayuda visual

value
    ↓
dato realmente ingresado
```

------------------------------------------------------------------------

# 7. Botón `Ingresar`

El botón principal es:

``` html
<button type="submit" class="botonIngresar">
    Ingresar
</button>
```

Como utiliza:

``` html
type="submit"
```

su acción está relacionada con el envío del formulario.

Por eso JavaScript puede escuchar:

``` javascript
formularioLogin.addEventListener("submit", ...)
```

El flujo es:

``` text
Usuario presiona "Ingresar"
        ↓
Formulario genera submit
        ↓
addEventListener detecta el evento
        ↓
Se ejecuta la función JavaScript
```

------------------------------------------------------------------------

# 8. Botón `Registrar`

El botón Registrar es:

``` html
<button
    type="button"
    onclick="window.location.href='index.html'"
    class="botonRegistrar">
    Registrar
</button>
```

Aquí hay una diferencia importante.

El botón utiliza:

``` html
type="button"
```

y no:

``` html
type="submit"
```

Por lo tanto, no intenta enviar el formulario.

En cambio, utiliza:

``` html
onclick="window.location.href='index.html'"
```

Esto indica que cuando se hace clic:

``` text
click
  ↓
window.location.href
  ↓
index.html
```

Por lo tanto, permite volver al formulario de registro.

------------------------------------------------------------------------

# 9. `window.location.href`

La instrucción:

``` javascript
window.location.href = "index.html";
```

cambia la ubicación actual del navegador.

Por ejemplo:

``` text
login.html
    ↓
window.location.href = "index.html"
    ↓
index.html
```

Es una forma de realizar navegación entre páginas.

También aparece en el proyecto:

``` javascript
window.location = "tabla.html";
```

Ambas instrucciones tienen el mismo objetivo general: cambiar la página
actual.

------------------------------------------------------------------------

# 10. Conectar `login.html` con `login.js`

Al final de `login.html` aparece:

``` html
<script src="login.js"></script>
```

Esto carga el archivo JavaScript.

La estructura general es:

``` text
login.html
    ↓
<script src="login.js">
    ↓
login.js
```

De esta manera, JavaScript puede acceder a los elementos del HTML y
modificar el comportamiento de la página.

------------------------------------------------------------------------

# 11. JavaScript --- Obtener el formulario

En `login.js`:

``` javascript
const formularioLogin = document.getElementById("formLogin");
```

Primero se busca:

``` html
<form id="formLogin">
```

y se guarda el elemento en:

``` javascript
formularioLogin
```

Ahora JavaScript puede trabajar con ese formulario.

------------------------------------------------------------------------

# 12. `addEventListener("submit", ...)`

El código utiliza:

``` javascript
formularioLogin.addEventListener("submit", function(event){
    ...
});
```

`addEventListener()` permite escuchar eventos.

En este caso:

``` text
submit
```

es el evento que interesa.

La estructura general es:

``` javascript
elemento.addEventListener("evento", funcion);
```

Por ejemplo:

``` javascript
formularioLogin.addEventListener("submit", function(event) {
    ...
});
```

Significa:

> Cuando `formularioLogin` genere un evento `submit`, ejecuta esta
> función.

------------------------------------------------------------------------

# 13. `event`

La función recibe:

``` javascript
function(event)
```

El parámetro `event` representa información sobre el evento que ocurrió.

En este caso permite utilizar:

``` javascript
event.preventDefault();
```

------------------------------------------------------------------------

# 14. `event.preventDefault()`

Dentro del submit:

``` javascript
event.preventDefault();
```

Esto evita el comportamiento predeterminado del formulario.

Normalmente, enviar un formulario puede provocar que el navegador
realice una acción predeterminada, como recargar o navegar.

Aquí queremos que JavaScript controle el proceso.

Por eso:

``` javascript
event.preventDefault();
```

detiene ese comportamiento.

El flujo queda:

``` text
submit
  ↓
preventDefault()
  ↓
JavaScript continúa ejecutándose
  ↓
validaciones
  ↓
comparación de usuario y contraseña
```

------------------------------------------------------------------------

# 15. Obtener los datos ingresados

El usuario y la contraseña se obtienen mediante:

``` javascript
const usuario = document.getElementById("usuario").value.trim();
const password = document.getElementById("password").value.trim();
```

La estructura:

``` javascript
document.getElementById("usuario")
```

obtiene el elemento HTML.

Después:

``` javascript
.value
```

obtiene lo que escribió el usuario.

Finalmente:

``` javascript
.trim()
```

elimina espacios al comienzo y al final.

------------------------------------------------------------------------

# 16. `trim()`

Por ejemplo:

``` text
"   juanito   "
```

al aplicar:

``` javascript
.trim()
```

queda:

``` text
"juanito"
```

Esto es útil para evitar que espacios accidentales afecten las
validaciones.

------------------------------------------------------------------------

# 17. Objeto con los datos ingresados

El código crea:

``` javascript
const usuarios = {
    usuario,
    password,
}
```

Nuevamente, esto es un **objeto**, no un Array.

Aunque el comentario del código dice:

``` javascript
// Array
```

la estructura utilizada es:

``` javascript
{}
```

por lo que técnicamente corresponde a un objeto.

Un objeto almacena propiedades y valores:

``` text
usuarios
│
├── usuario
└── password
```

------------------------------------------------------------------------

# 18. Shorthand properties

El objeto:

``` javascript
const usuarios = {
    usuario,
    password,
}
```

es una forma abreviada de escribir:

``` javascript
const usuarios = {
    usuario: usuario,
    password: password
}
```

Cuando la variable y el nombre de la propiedad son iguales, JavaScript
permite omitir la repetición.

Por ejemplo:

``` javascript
const nombre = "Juan";

const persona = {
    nombre
};
```

equivale a:

``` javascript
const persona = {
    nombre: nombre
};
```

------------------------------------------------------------------------

# 19. Base de datos simulada

Antes del formulario se define:

``` javascript
const usuarioBDD = {
    nombre: "juanito",
    password: "123456",
}
```

Este objeto representa los datos que el programa considera válidos.

Conceptualmente:

``` text
usuarioBDD
│
├── nombre → "juanito"
└── password → "123456"
```

Cuando el usuario intenta iniciar sesión, sus datos se comparan con este
objeto.

> En el código de la clase, `BDD` representa una base de datos de forma
> conceptual, pero realmente estos datos están escritos directamente en
> JavaScript. No existe una conexión real con una base de datos.

------------------------------------------------------------------------

# 20. Comparación del Login

La función encargada de comprobar los datos es:

``` javascript
function validarUsuarioBDD(usuario, password){
    if (
        usuario === usuarioBDD.nombre &&
        password === usuarioBDD.password
    ){
        return true;
    } else {
        return false;
    }
}
```

Aquí se realizan dos comparaciones:

``` javascript
usuario === usuarioBDD.nombre
```

y:

``` javascript
password === usuarioBDD.password
```

Ambas deben ser verdaderas.

------------------------------------------------------------------------

# 21. Operador `&&`

La expresión:

``` javascript
condicion1 && condicion2
```

significa:

``` text
condición 1
Y
condición 2
```

En el login:

``` javascript
usuario === usuarioBDD.nombre &&
password === usuarioBDD.password
```

significa:

``` text
¿El usuario coincide?
        Y
¿La contraseña coincide?
```

Solo si ambas coinciden se obtiene:

``` javascript
true
```

------------------------------------------------------------------------

# 22. Operador `===`

Se utiliza:

``` javascript
===
```

para comparar valores de manera estricta.

Por ejemplo:

``` javascript
usuario === usuarioBDD.nombre
```

comprueba si ambos valores son iguales.

En este caso se utiliza correctamente para comparar strings.

------------------------------------------------------------------------

# 23. Función `validarUsuarioBDD()`

La función:

``` javascript
function validarUsuarioBDD(usuario, password){
    if (
        usuario === usuarioBDD.nombre &&
        password === usuarioBDD.password
    ){
        return true;
    } else{
        return false;
    }
}
```

puede entenderse como:

``` text
Recibe:
    usuario
    password

       ↓

Compara con:
    usuarioBDD.nombre
    usuarioBDD.password

       ↓

Si coinciden:
    true

Si no:
    false
```

Esto permite utilizar el resultado directamente en un `if`.

------------------------------------------------------------------------

# 24. Utilizar una función dentro de un `if`

El código principal utiliza:

``` javascript
if(validarUsuarioBDD(usuario, password)){
    alert("Conexión exitosa!")
}else{
    alert("Usuario o contraseña incorrecta.")
}
```

La función devuelve:

``` text
true
o
false
```

Por lo tanto:

``` javascript
if(validarUsuarioBDD(usuario, password))
```

equivale conceptualmente a:

``` text
Si la función devuelve true
    ↓
Login correcto

Si devuelve false
    ↓
Login incorrecto
```

------------------------------------------------------------------------

# 25. Validación de usuario vacío

Se crea una función:

``` javascript
function validarUsuarioVacio(usuario){
    if(usuario == ""){
        alert("Usuario no puede estar vacio")
        return true;
    }
}
```

Esta función comprueba si el usuario está vacío.

Si:

``` javascript
usuario == ""
```

se muestra:

``` text
Usuario no puede estar vacio
```

y devuelve:

``` javascript
true
```

------------------------------------------------------------------------

# 26. Validación de contraseña vacía

Se utiliza una función equivalente:

``` javascript
function validarPasswordVacio(password){
    if(password == ""){
        alert("Contraseña no puede estar vacia")
        return true;
    }
}
```

Su objetivo es comprobar si la contraseña está vacía.

------------------------------------------------------------------------

# 27. Validación del tipo de contraseña

También se crea:

``` javascript
function validarPasswordTipo(password){
    if (password.length <= 8) {
        alert("La contraseña debe tener al menos 8 caracteres.")
        return true;
    }
}
```

Aquí aparece una nueva propiedad importante:

``` javascript
.length
```

------------------------------------------------------------------------

# 28. `.length`

`.length` permite obtener la cantidad de caracteres de un string.

Por ejemplo:

``` javascript
const password = "123456";

console.log(password.length);
```

produce:

``` text
6
```

Entonces:

``` javascript
password.length <= 8
```

pregunta:

> ¿La contraseña tiene 8 caracteres o menos?

------------------------------------------------------------------------

# 29. Condición de longitud

El código utiliza:

``` javascript
if (password.length <= 8)
```

Por lo tanto, se muestra el mensaje cuando la contraseña tiene:

``` text
8 caracteres
7 caracteres
6 caracteres
5 caracteres
...
```

Sin embargo, el mensaje dice:

``` text
La contraseña debe tener al menos 8 caracteres.
```

Si la intención es exigir **mínimo 8 caracteres**, la condición debería
ser:

``` javascript
if (password.length < 8)
```

porque una contraseña de exactamente 8 caracteres debería ser válida.

Esto es una diferencia importante entre:

``` text
< 8
```

y:

``` text
<= 8
```

------------------------------------------------------------------------

# 30. Diferencia entre `<` y `<=`

``` javascript
password.length < 8
```

significa:

``` text
menor que 8
```

Por ejemplo:

``` text
7 → verdadero
8 → falso
```

En cambio:

``` javascript
password.length <= 8
```

significa:

``` text
menor o igual que 8
```

Por ejemplo:

``` text
7 → verdadero
8 → verdadero
9 → falso
```

Por lo tanto, para "al menos 8 caracteres":

``` javascript
password.length < 8
```

es la condición coherente.

------------------------------------------------------------------------

# 31. Orden de las validaciones

El código llama:

``` javascript
validarPasswordTipo(password);

validarUsuarioVacio(usuario);
validarPasswordVacio(password);
```

y posteriormente realiza:

``` javascript
if(validarUsuarioBDD(usuario, password)){
    ...
}
```

Esto permite identificar diferentes problemas.

Sin embargo, hay un detalle importante:

Las funciones de validación muestran un `alert()`, pero no se está
utilizando su valor de retorno para detener el proceso.

Por ejemplo:

``` javascript
validarUsuarioVacio(usuario);
```

si detecta un usuario vacío, devuelve:

``` javascript
true
```

pero ese `true` no se utiliza.

Por lo tanto, la ejecución continúa.

Una forma de utilizar el resultado sería:

``` javascript
if (validarUsuarioVacio(usuario)) {
    return;
}
```

Así:

``` text
Campo vacío
    ↓
función devuelve true
    ↓
if(true)
    ↓
return
    ↓
se detiene el submit
```

------------------------------------------------------------------------

# 32. Problema similar con `validarPasswordTipo()`

Actualmente:

``` javascript
validarPasswordTipo(password);
```

solo ejecuta la función.

Aunque la función devuelva `true`, ese valor no se utiliza.

Podría hacerse:

``` javascript
if (validarPasswordTipo(password)) {
    return;
}
```

Esto permitiría detener el proceso si la contraseña no cumple la
condición.

------------------------------------------------------------------------

# 33. `alert()`

Las validaciones utilizan:

``` javascript
alert("Mensaje");
```

`alert()` muestra una ventana emergente al usuario.

Ejemplo:

``` javascript
alert("Usuario no puede estar vacio");
```

Conceptualmente:

``` text
JavaScript
    ↓
alert()
    ↓
mensaje en pantalla
```

Es una forma sencilla de comunicar resultados durante este tipo de
ejercicios.

------------------------------------------------------------------------

# 34. Flujo completo del Login

El proceso completo puede representarse así:

``` text
Usuario escribe:
    usuario
    password
        ↓
Presiona "Ingresar"
        ↓
submit
        ↓
preventDefault()
        ↓
JavaScript obtiene .value
        ↓
trim()
        ↓
Validaciones
        ↓
Comparación con usuarioBDD
        ↓
¿Coinciden?
   /          \
 Sí            No
 ↓              ↓
true           false
 ↓              ↓
alert           alert
"Conexión       "Usuario o contraseña
exitosa!"       incorrecta."
```

------------------------------------------------------------------------

# 35. Código completo de `login.js`

El código trabajado en clase es:

``` javascript
const formularioLogin = document.getElementById("formLogin");

const usuarioBDD = {
    nombre: "juanito",
    password: "123456",
}


formularioLogin.addEventListener("submit", function(event){
    event.preventDefault();

    const usuario = document.getElementById("usuario").value.trim();
    const password = document.getElementById("password").value.trim();

    // Array
    const usuarios = {
        usuario,
        password,
    }

    validarPasswordTipo(password);

    validarUsuarioVacio(usuario);
    validarPasswordVacio(password);

    if(validarUsuarioBDD(usuario, password)){
        alert("Conexión exitosa!")
    }else{
        alert("Usuario o contraseña incorrecta.")
    }
    }
)


// Validaciones
function validarUsuarioVacio(usuario){
    if(usuario == ""){
        alert("Usuario no puede estar vacio")
        return true;
    }
}


function validarPasswordVacio(password){
    if(password == ""){
        alert("Contraseña no puede estar vacia")
        return true;
    }
}

function validarUsuarioBDD(usuario, password){
    if (usuario === usuarioBDD.nombre &&
        password === usuarioBDD.password){
            return true;
    } else{
        return false;
    }
}

function validarPasswordTipo(password){
    if (password.length <= 8) {
        alert("La contraseña debe tener al menos 8 caracteres.")
        return true;
    }
}
```

------------------------------------------------------------------------

# 36. JavaScript del formulario de registro

La clase también mantiene el formulario de registro trabajado
anteriormente.

El archivo `funcionalidades.js` comienza con:

``` javascript
const formularioJS = document.getElementById("formulario");

formularioJS.addEventListener("submit", function(event){
    event.preventDefault();

    console.log("Boton funcionando");
```

Se repite el mismo patrón visto en el login:

``` text
obtener formulario
       ↓
addEventListener
       ↓
submit
       ↓
preventDefault()
```

Esto es importante porque muestra que el patrón de manejo de formularios
puede reutilizarse en distintas páginas.

------------------------------------------------------------------------

# 37. Obtener los datos del registro

Se obtienen:

``` javascript
const nombre = document.getElementById("nombre").value.trim();
const apellido = document.getElementById("apellido").value.trim();
const telefono = document.getElementById("telefono").value.trim();
const correo = document.getElementById("correo").value.trim();
const pais = document.getElementById("pais").value.trim();
```

El patrón es siempre:

``` javascript
document.getElementById("id").value.trim()
```

Por ejemplo:

``` javascript
document.getElementById("nombre")
```

busca:

``` html
<input id="nombre">
```

y:

``` javascript
.value
```

obtiene lo escrito.

------------------------------------------------------------------------

# 38. Función `validarInputVacio()`

Se utiliza:

``` javascript
function validarInputVacio(nombre, apellido, telefono, correo, pais){
    if (
        nombre === "" ||
        apellido === "" ||
        telefono === "" ||
        correo === "" ||
        pais === ""
    ) { 
        alert("Campo no puede ser vacio")
        return true;

    }else{
        return false;
    }
}
```

La función recibe varios parámetros:

``` text
nombre
apellido
telefono
correo
pais
```

y comprueba si alguno está vacío.

------------------------------------------------------------------------

# 39. Operador `||`

La condición:

``` javascript
nombre === "" ||
apellido === "" ||
telefono === "" ||
correo === "" ||
pais === ""
```

utiliza `||`.

`||` significa **OR**.

Por lo tanto, basta con que uno sea verdadero:

``` text
nombre vacío → true
```

para que toda la condición sea verdadera.

------------------------------------------------------------------------

# 40. Validación de cantidad de caracteres

Otra función es:

``` javascript
function validarCantidadCaracteres(nombre, apellido){
    if(nombre.length <= 3 || apellido.length <= 3){
        alert("Nombre debe tener más de 3 caracteres")
        return;
    }
}
```

Aquí se combinan dos conceptos:

``` text
.length
+
||
```

Se comprueba la longitud de nombre y apellido.

------------------------------------------------------------------------

# 41. `return`

En las funciones de validación aparece:

``` javascript
return;
```

Un `return` sin valor termina la ejecución de esa función.

Por ejemplo:

``` javascript
function ejemplo() {

    alert("Mensaje");

    return;

    alert("Esto no se ejecutará");
}
```

El segundo `alert()` queda después del `return`, por lo que no se
ejecutaría.

------------------------------------------------------------------------

# 42. Crear el objeto usuario

Después de obtener los datos:

``` javascript
const usuario = {
    nombre,
    apellido,
    telefono,
    correo,
    pais,
}
```

Se crea un objeto.

La estructura es:

``` text
usuario
│
├── nombre
├── apellido
├── telefono
├── correo
└── pais
```

Este concepto continúa siendo importante para la clase.

------------------------------------------------------------------------

# 43. `console.log()`

El código muestra los datos:

``` javascript
console.log(nombre)
console.log(apellido)
console.log(telefono)
console.log(correo)
console.log(pais)

console.log("El arreglo es: ", usuario)
```

Esto permite comprobar en la consola del navegador si los datos fueron
obtenidos correctamente.

Nuevamente, aunque el comentario dice:

``` javascript
// Creamos el arreglo
```

la variable `usuario` es un objeto:

``` javascript
{}
```

no un Array:

``` javascript
[]
```

------------------------------------------------------------------------

# 44. LocalStorage y JSON

El objeto se guarda mediante:

``` javascript
localStorage.setItem(
    "usuarioLocalStorage",
    JSON.stringify(usuario)
)
```

El proceso es:

``` text
usuario
   ↓
JSON.stringify()
   ↓
JSON
   ↓
localStorage
```

La clave utilizada es:

``` text
usuarioLocalStorage
```

------------------------------------------------------------------------

# 45. Recuperar los datos en `tabla.html`

En `tabla.html`:

``` javascript
const obtenerDatosUsuario =
    JSON.parse(
        localStorage.getItem("usuarioLocalStorage")
    )
```

El proceso inverso es:

``` text
localStorage
   ↓
getItem()
   ↓
JSON
   ↓
JSON.parse()
   ↓
objeto JavaScript
```

Así se recupera el usuario almacenado anteriormente.

------------------------------------------------------------------------

# 46. Mostrar datos dinámicamente

Se obtiene:

``` javascript
const tabla = document.getElementById("tablaDatos")
```

y posteriormente:

``` javascript
tabla.innerHTML = `
    <td>${obtenerDatosUsuario.nombre}</td>
    <td>${obtenerDatosUsuario.apellido}</td>
    <td>${obtenerDatosUsuario.telefono}</td>
    <td>${obtenerDatosUsuario.correo}</td>
    <td>${obtenerDatosUsuario.pais}</td>
`
```

Aquí se utiliza `innerHTML` para insertar HTML desde JavaScript.

------------------------------------------------------------------------

# 47. Template literals

El uso de:

``` javascript
`
```

permite escribir un template literal.

Dentro se pueden insertar variables mediante:

``` javascript
${...}
```

Por ejemplo:

``` javascript
${obtenerDatosUsuario.nombre}
```

JavaScript reemplaza esa expresión por el valor correspondiente.

------------------------------------------------------------------------

# 48. Correcciones importantes observadas en el código

Esta sección es útil para distinguir entre:

``` text
Código visto en clase
```

y:

``` text
Código que sería conveniente ajustar
```

## 48.1 Comentario "Array"

En:

``` javascript
const usuario = {
    nombre,
    apellido,
    telefono,
    correo,
    pais,
}
```

se comenta que se está creando un arreglo.

Pero:

``` text
{} → objeto
[] → Array
```

Por lo tanto, es un objeto.

------------------------------------------------------------------------

## 48.2 Validaciones que no detienen la ejecución

En el login:

``` javascript
validarUsuarioVacio(usuario);
validarPasswordVacio(password);
```

las funciones pueden devolver `true`, pero el resultado no se utiliza.

Por ejemplo, sería posible hacer:

``` javascript
if (validarUsuarioVacio(usuario)) {
    return;
}
```

y:

``` javascript
if (validarPasswordVacio(password)) {
    return;
}
```

Esto haría que la validación realmente detuviera el login.

------------------------------------------------------------------------

## 48.3 Contraseña mínima

El código utiliza:

``` javascript
if (password.length <= 8)
```

pero el mensaje indica:

``` text
al menos 8 caracteres
```

Si exactamente 8 caracteres deben ser aceptados, debería utilizarse:

``` javascript
if (password.length < 8)
```

------------------------------------------------------------------------

## 48.4 Datos de usuario escritos en JavaScript

El login utiliza:

``` javascript
const usuarioBDD = {
    nombre: "juanito",
    password: "123456",
}
```

Esto no representa una base de datos real.

Los datos están directamente en el código JavaScript.

En una aplicación real, las credenciales deberían gestionarse mediante
un backend y un sistema de autenticación apropiado.

------------------------------------------------------------------------

## 48.5 Contraseña visible en el código

El código contiene:

``` javascript
password: "123456"
```

Esto sirve para practicar la lógica del login, pero no es una forma
segura de almacenar contraseñas.

En una aplicación real no se deberían guardar contraseñas de esta forma.

------------------------------------------------------------------------

# 49. Relación entre las páginas

Ahora existen varias páginas relacionadas:

``` text
index.html
    │
    │ Registrar
    ↓
login.html
    │
    │ Ingresar
    ↓
validación del usuario
```

También:

``` text
index.html
    │
    │ Guardar
    ↓
tabla.html
```

Y desde el login:

``` text
login.html
    │
    │ Registrar
    ↓
index.html
```

Esto introduce una idea importante: una aplicación puede estar formada
por varias páginas HTML que se conectan mediante navegación.

------------------------------------------------------------------------

# 50. Flujo completo del proyecto

## Registro

``` text
Usuario
   ↓
Completa formulario
   ↓
index.html
   ↓
submit
   ↓
preventDefault()
   ↓
JavaScript obtiene .value
   ↓
trim()
   ↓
validaciones
   ↓
crear objeto usuario
   ↓
JSON.stringify()
   ↓
localStorage.setItem()
   ↓
window.location
   ↓
tabla.html
```

## Login

``` text
Usuario
   ↓
login.html
   ↓
ingresa usuario y password
   ↓
submit
   ↓
preventDefault()
   ↓
JavaScript obtiene .value
   ↓
trim()
   ↓
validaciones
   ↓
comparación con usuarioBDD
   ↓
true / false
   ↓
alert()
```

------------------------------------------------------------------------

# 51. Conceptos nuevos o reforzados

Los conceptos que conviene dominar después de esta clase son:

-   [ ] Crear un formulario con `<form>`.
-   [ ] Utilizar `<input type="text">`.
-   [ ] Utilizar `<input type="password">`.
-   [ ] Entender `placeholder`.
-   [ ] Diferenciar `type="submit"` y `type="button"`.
-   [ ] Utilizar `onclick`.
-   [ ] Utilizar `window.location.href`.
-   [ ] Obtener elementos con `getElementById()`.
-   [ ] Obtener valores con `.value`.
-   [ ] Limpiar espacios con `.trim()`.
-   [ ] Escuchar eventos con `addEventListener()`.
-   [ ] Trabajar con el evento `submit`.
-   [ ] Utilizar `event.preventDefault()`.
-   [ ] Crear objetos con `{}`.
-   [ ] Diferenciar objetos de Arrays.
-   [ ] Utilizar propiedades de objetos.
-   [ ] Utilizar funciones para validaciones.
-   [ ] Utilizar `return`.
-   [ ] Utilizar `alert()`.
-   [ ] Utilizar `.length`.
-   [ ] Utilizar `===`.
-   [ ] Utilizar `&&`.
-   [ ] Utilizar `||`.
-   [ ] Entender la diferencia entre `<` y `<=`.
-   [ ] Guardar objetos mediante `JSON.stringify()`.
-   [ ] Recuperar objetos mediante `JSON.parse()`.
-   [ ] Utilizar `localStorage`.
-   [ ] Generar HTML mediante `innerHTML`.
-   [ ] Utilizar template literals y `${}`.

------------------------------------------------------------------------

# 52. Lo más importante para estudiar

Hay varios patrones de código que conviene reconocer rápidamente.

## Obtener un elemento

``` javascript
document.getElementById("id")
```

## Obtener lo escrito

``` javascript
document.getElementById("id").value
```

## Obtener y limpiar espacios

``` javascript
document.getElementById("id").value.trim()
```

## Escuchar un formulario

``` javascript
formulario.addEventListener("submit", function(event) {
    event.preventDefault();
});
```

## Crear un objeto

``` javascript
const usuario = {
    nombre,
    apellido
};
```

## Guardar un objeto

``` javascript
localStorage.setItem(
    "clave",
    JSON.stringify(usuario)
);
```

## Recuperar un objeto

``` javascript
const usuario =
    JSON.parse(
        localStorage.getItem("clave")
    );
```

## Navegar a otra página

``` javascript
window.location.href = "index.html";
```

## Comprobar una condición

``` javascript
if (condicion) {
    ...
}
```

## Comparar estrictamente

``` javascript
valor1 === valor2
```

------------------------------------------------------------------------

# 53. Resumen conceptual

La clase conecta tres partes principales:

``` text
HTML
 ↓
estructura y entrada de datos

CSS
 ↓
apariencia

JavaScript
 ↓
comportamiento y lógica
```

En el login:

``` text
HTML
 ↓
formulario
 ↓
usuario + contraseña
 ↓
JavaScript
 ↓
validaciones
 ↓
comparación
 ↓
resultado
```

En el registro:

``` text
HTML
 ↓
formulario
 ↓
JavaScript
 ↓
objeto
 ↓
JSON.stringify()
 ↓
localStorage
 ↓
tabla.html
 ↓
JSON.parse()
 ↓
objeto
 ↓
innerHTML
 ↓
tabla
```

La idea central que se sigue desarrollando es que **JavaScript conecta
la interfaz HTML con la lógica de la aplicación y permite trabajar con
los datos introducidos por el usuario**.

------------------------------------------------------------------------

# 54. Preguntas de repaso

Antes de considerar dominada la clase, deberías poder responder sin
mirar los apuntes:

1.  ¿Para qué sirve el `id` de un elemento HTML?
2.  ¿Qué diferencia existe entre `type="text"` y `type="password"`?
3.  ¿Qué diferencia existe entre `type="submit"` y `type="button"`?
4.  ¿Para qué sirve `addEventListener()`?
5.  ¿Qué evento se utiliza para detectar el envío del formulario?
6.  ¿Qué hace `event.preventDefault()`?
7.  ¿Qué devuelve `.value`?
8.  ¿Qué hace `.trim()`?
9.  ¿Qué diferencia existe entre un objeto `{}` y un Array `[]`?
10. ¿Qué significa `===`?
11. ¿Qué significa `&&`?
12. ¿Qué significa `||`?
13. ¿Para qué sirve `.length`?
14. ¿Qué hace `return` dentro de una función?
15. ¿Qué hace `JSON.stringify()`?
16. ¿Qué hace `JSON.parse()`?
17. ¿Qué diferencia existe entre `localStorage.setItem()` y
    `localStorage.getItem()`?
18. ¿Por qué se utiliza `JSON.stringify()` antes de guardar un objeto?
19. ¿Qué hace `window.location.href`?
20. ¿Qué permite hacer `innerHTML`?
21. ¿Para qué sirven los template literals?
22. ¿Cómo se inserta una variable dentro de un template literal?
23. ¿Por qué `usuarioBDD` no es realmente una base de datos?
24. ¿Qué problema existe con la condición `password.length <= 8` si se
    exige un mínimo de 8 caracteres?
25. ¿Por qué una función de validación que devuelve `true` no detiene
    automáticamente la ejecución?

------------------------------------------------------------------------

# 55. Idea central de la clase

El patrón más importante que se agrega al trabajo anterior es:

``` text
HTML
  ↓
Usuario ingresa datos
  ↓
JavaScript obtiene los datos
  ↓
JavaScript valida los datos
  ↓
JavaScript toma una decisión
  ↓
JavaScript puede guardar, mostrar o utilizar esos datos
```

En el caso del login:

``` text
Usuario + Password
        ↓
     validar
        ↓
 comparar con datos conocidos
        ↓
    true / false
        ↓
      alert()
```

En el caso del registro:

``` text
Datos del formulario
        ↓
      objeto
        ↓
JSON.stringify()
        ↓
  localStorage
        ↓
JSON.parse()
        ↓
      objeto
        ↓
   innerHTML
        ↓
      tabla
```

Estos patrones son una base importante para continuar avanzando desde
páginas HTML estáticas hacia aplicaciones web que manipulan datos y
posteriormente se comunican con un backend.
