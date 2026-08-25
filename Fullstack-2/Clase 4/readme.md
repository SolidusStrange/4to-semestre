# Fullstack — Clase 4: Formulario, objetos, JSON y LocalStorage

## Objetivo de la clase

En esta clase se continúa trabajando con la integración de:

* HTML
* CSS
* JavaScript

A diferencia de la clase anterior, en esta sesión aparece una parte importante de JavaScript relacionada con el **manejo de datos**:

* Objetos
* Propiedades de objetos
* `localStorage`
* `JSON.stringify()`
* `JSON.parse()`
* Recuperación de información almacenada
* Generación dinámica de HTML
* Uso de `innerHTML`
* Navegación entre páginas mediante `window.location`

La aplicación desarrollada permite registrar un usuario en un formulario, guardar sus datos en el navegador y posteriormente mostrarlos en una tabla ubicada en otra página.

---

# 1. Estructura del proyecto

El proyecto puede organizarse de la siguiente manera:

```text
Proyecto
│
├── index.html
├── tabla.html
├── estilo.css
└── funcionalidades.js
```

Cada archivo tiene una responsabilidad diferente.

```text
index.html
    ↓
Formulario de registro

estilo.css
    ↓
Diseño y apariencia

funcionalidades.js
    ↓
Validación + creación del objeto + almacenamiento

tabla.html
    ↓
Recuperación de datos + generación de tabla
```

El flujo general es:

```text
Usuario
   ↓
Completa formulario
   ↓
index.html
   ↓
JavaScript obtiene los datos
   ↓
Se crea un objeto usuario
   ↓
Objeto → JSON
   ↓
localStorage
   ↓
Cambio a tabla.html
   ↓
localStorage → JSON
   ↓
JSON → objeto JavaScript
   ↓
Se muestran los datos en la tabla
```

---

# 2. HTML — Formulario de registro

El archivo HTML contiene la estructura de la aplicación.

La parte principal es:

```html
<form class="formulariostyle" id="formulario">
```

Se utilizan dos elementos especialmente importantes:

```text
class
id
```

La `class` se utiliza principalmente para aplicar estilos desde CSS.

El `id` permite identificar un elemento específico desde JavaScript.

Por ejemplo:

```html
<input type="text" id="nombre">
```

Posteriormente JavaScript puede encontrar este elemento con:

```javascript
document.getElementById("nombre")
```

---

# 3. Organización del formulario

El formulario se divide en filas:

```html
<div class="fila">
```

Dentro de cada fila existen distintos campos:

```html
<div class="campo">
```

Por ejemplo:

```html
<div class="campo">
    <label>Nombre</label>
    <input type="text" id="nombre" placeholder="Juan">
</div>
```

La estructura conceptual es:

```text
form
│
├── fila
│   ├── campo
│   │   ├── label
│   │   └── input
│   │
│   └── campo
│       ├── label
│       └── input
│
├── fila
│   ├── campo
│   ├── campo
│   └── campo
│
└── botones
```

La organización mediante `div` permite posteriormente distribuir los elementos utilizando CSS.

---

# 4. `id` como conexión entre HTML y JavaScript

Los elementos del formulario tienen identificadores:

```html
<input id="nombre">
<input id="apellido">
<input id="telefono">
<input id="correo">
<select id="pais">
```

Estos identificadores funcionan como puntos de conexión.

HTML:

```html
<input id="nombre">
```

JavaScript:

```javascript
document.getElementById("nombre")
```

Por lo tanto:

```text
HTML
  ↓
id="nombre"
  ↓
JavaScript
  ↓
getElementById("nombre")
```

Sin esta identificación, JavaScript no tendría una forma directa de localizar ese elemento específico utilizando `getElementById()`.

---

# 5. Botones

El formulario tiene dos botones:

```html
<button type="submit" class="botonGuardar">
    Guardar
</button>

<button type="submit" class="botonLimpiar">
    Limpiar
</button>
```

Ambos tienen:

```html
type="submit"
```

Por lo tanto, ambos pueden provocar el evento `submit` del formulario.

Esto es importante porque, tal como está escrito el código, **el botón "Limpiar" todavía no tiene una función propia de limpieza**.

Para que realmente limpie los campos habría que agregar otro comportamiento en JavaScript o cambiar su tipo a:

```html
<button type="button">
```

y agregar un evento específico.

---

# 6. CSS — Diseño del formulario

El archivo `estilo.css` se encarga exclusivamente de la apariencia.

---

## 6.1 Formulario

```css
.formulariostyle {
    padding: 30px;
    background-color: rgb(255, 255, 255);
    width: 500px;
    box-shadow: 0 10px 10px rgb(226, 226, 255);
}
```

### `padding`

```css
padding: 30px;
```

Agrega espacio interno.

```text
┌─────────────────────────────┐
│          padding            │
│   ┌─────────────────────┐   │
│   │      contenido      │   │
│   └─────────────────────┘   │
└─────────────────────────────┘
```

### `width`

```css
width: 500px;
```

Define el ancho del formulario.

### `box-shadow`

```css
box-shadow: 0 10px 10px rgb(226, 226, 255);
```

Agrega una sombra para generar profundidad visual.

---

# 7. CSS — Campos

```css
.campo {
    color: rgb(0, 0, 0);
    width: 100%;
    max-width: 500px;
    padding: 5px;
}
```

Se intenta que cada campo ocupe todo el ancho disponible, pero sin superar los `500px`.

```text
width: 100%
        ↓
ocupa todo el espacio disponible

max-width: 500px
        ↓
no supera los 500px
```

---

# 8. CSS — Filas

```css
.fila {
    display: flex;
    gap: 20px;
    margin-bottom: 30px;
}
```

### `display: flex`

Activa Flexbox.

Esto permite colocar los elementos de una fila uno al lado del otro.

### `gap`

```css
gap: 20px;
```

establece una separación de `20px` entre los elementos.

### `margin-bottom`

```css
margin-bottom: 30px;
```

agrega espacio exterior debajo de la fila.

---

# 9. CSS — Botones

```css
.botones {
    display: flex;
    color: rgb(0, 0, 0);
    width: 100%;
    max-width: 500px;
    padding: 20px;
    gap: 30px;
}
```

Nuevamente `display: flex` permite colocar los botones horizontalmente.

```text
[ Guardar ]     [ Limpiar ]
```

La propiedad:

```css
gap: 30px;
```

crea la separación entre ambos.

---

# 10. CSS — `input` y `select`

Se pueden aplicar estilos a varios elementos simultáneamente:

```css
input, select {
    width: 100%;
    border: 1px solid #c2c2c2;
    font-size: 15px;
}
```

La coma significa:

```text
input
y
select
```

ambos reciben las mismas propiedades.

---

# 11. CSS — Estado `focus`

```css
input:focus, select:focus {
    border-color: red;
    border: 5px;
}
```

`:focus` representa el estado en el cual el elemento está seleccionado o tiene el foco.

Por ejemplo:

```text
Usuario hace clic en el input
        ↓
input obtiene focus
        ↓
CSS aplica el estilo :focus
```

Conceptualmente, se utiliza para indicar visualmente al usuario cuál campo está activo.

### Observación

La propiedad:

```css
border: 5px;
```

no define correctamente un borde visible porque falta especificar el estilo.

Sería más apropiado:

```css
border: 5px solid red;
```

o simplemente:

```css
border: 5px solid;
```

---

# 12. CSS — `hover`

```css
button:hover {
    opacity: 0.70;
    transform: scale(1.05);
    transition: 300ms;
}
```

`:hover` se aplica cuando el cursor del mouse está sobre el elemento.

```text
mouse sobre botón
        ↓
:hover
        ↓
aplica efecto visual
```

### `opacity`

```css
opacity: 0.70;
```

Reduce la opacidad.

### `transform: scale()`

```css
transform: scale(1.05);
```

aumenta ligeramente el tamaño del botón.

### `transition`

```css
transition: 300ms;
```

hace que el cambio sea gradual.

---

# 13. JavaScript — Obtener el formulario

El código comienza con:

```javascript
const formularioJS = document.getElementById("formulario");
```

JavaScript obtiene el formulario HTML mediante su `id`.

HTML:

```html
<form id="formulario">
```

JavaScript:

```javascript
document.getElementById("formulario")
```

Esto conecta ambos archivos.

---

# 14. Escuchar el evento `submit`

Después se agrega un listener:

```javascript
formularioJS.addEventListener("submit", function(event) {
```

Esto significa:

> Cuando el formulario sea enviado, ejecutar esta función.

El flujo es:

```text
Usuario presiona Guardar
        ↓
formulario genera submit
        ↓
addEventListener detecta el evento
        ↓
se ejecuta la función
```

---

# 15. `preventDefault()`

Dentro del evento:

```javascript
event.preventDefault();
```

evita el comportamiento predeterminado del formulario.

Normalmente, al enviar un formulario, el navegador puede recargar la página o realizar un envío tradicional.

Aquí queremos controlar todo mediante JavaScript.

Por eso:

```javascript
event.preventDefault();
```

detiene ese comportamiento.

---

# 16. Obtener información del formulario

Después se obtienen los datos:

```javascript
const nombre = document.getElementById("nombre").value;
const apellido = document.getElementById("apellido").value.trim();
const telefono = document.getElementById("telefono").value.trim();
const correo = document.getElementById("correo").value.trim();
const pais = document.getElementById("pais").value.trim();
```

La estructura general es:

```javascript
document.getElementById("ID").value
```

Primero:

```javascript
document.getElementById("nombre")
```

obtiene el elemento.

Después:

```javascript
.value
```

obtiene el contenido ingresado.

---

# 17. `trim()`

Se utiliza:

```javascript
.value.trim()
```

`trim()` elimina los espacios del comienzo y del final.

Por ejemplo:

```text
"   Juan   "
```

se convierte en:

```text
"Juan"
```

Esto evita problemas cuando el usuario deja espacios accidentalmente.

---

# 18. Validación de campos

Antes de guardar los datos, se comprueba que ningún campo esté vacío:

```javascript
if (
    nombre === "" ||
    apellido === "" ||
    telefono === "" ||
    correo === "" ||
    pais === ""
) {
    alert("Campo no puede ser vacío");
    return;
}
```

El operador:

```text
||
```

significa **OR**.

La condición pregunta:

```text
¿nombre está vacío?
O
¿apellido está vacío?
O
¿telefono está vacío?
O
¿correo está vacío?
O
¿pais está vacío?
```

Si cualquiera es verdadero, se muestra el mensaje y se detiene la función mediante:

```javascript
return;
```

---

# 19. Comparar textos con `toLowerCase()`

Se comprueba que el nombre y el apellido no sean iguales:

```javascript
if (nombre.toLowerCase() === apellido.toLowerCase()) {
    alert("No puede ser igual");
}
```

`toLowerCase()` transforma el texto a minúsculas.

Por ejemplo:

```text
Juan
JUAN
juan
```

todos se convierten en:

```text
juan
```

Esto permite comparar sin depender de las mayúsculas.

### Importante

En el código actual, después del `alert()` el programa **no se detiene**.

Si la intención es impedir que se continúe, debería ser:

```javascript
if (nombre.toLowerCase() === apellido.toLowerCase()) {
    alert("No puede ser igual");
    return;
}
```

---

# 20. Creación del objeto `usuario`

Esta es una de las partes nuevas más importantes de la clase:

```javascript
const usuario = {
    nombre: nombre,
    apellido: apellido,
    telefono: telefono,
    correo: correo,
    pais: pais
};
```

Aquí no se está creando un Array.

Se está creando un **objeto JavaScript**.

Esta diferencia es importante.

## Objeto

Un objeto almacena información mediante pares:

```text
propiedad → valor
```

Por ejemplo:

```text
nombre   → Juan
apellido → Pérez
telefono → +569...
correo   → juan@gmail.com
pais     → chile
```

Visualmente:

```text
usuario
│
├── nombre
├── apellido
├── telefono
├── correo
└── pais
```

---

# 21. Objeto versus Array

El código de la clase utiliza:

```javascript
const usuario = {
    ...
};
```

Esto es un **objeto**.

Un Array sería:

```javascript
const usuarios = [
    ...
];
```

Por lo tanto:

```text
{} → objeto
[] → array
```

Un ejemplo de objeto:

```javascript
const usuario = {
    nombre: "Juan",
    apellido: "Pérez"
};
```

Un ejemplo de Array:

```javascript
const usuarios = [
    "Juan",
    "Pedro",
    "Ana"
];
```

### En esta clase

Se trabaja con un solo objeto `usuario`.

Todavía **no se está almacenando una lista de múltiples usuarios**.

---

# 22. Propiedades del objeto

Cada dato se convierte en una propiedad:

```javascript
usuario.nombre
usuario.apellido
usuario.telefono
usuario.correo
usuario.pais
```

Por ejemplo:

```javascript
console.log(usuario.nombre);
```

mostraría el nombre almacenado.

También se puede modificar:

```javascript
usuario.nombre = "Pedro";
```

Ahora la propiedad `nombre` contiene `"Pedro"`.

---

# 23. Forma simplificada del objeto

Como las variables tienen el mismo nombre que las propiedades, JavaScript permite escribir:

```javascript
const usuario = {
    nombre,
    apellido,
    telefono,
    correo,
    pais
};
```

Esto equivale a:

```javascript
const usuario = {
    nombre: nombre,
    apellido: apellido,
    telefono: telefono,
    correo: correo,
    pais: pais
};
```

La segunda forma es más explícita para aprender qué está sucediendo.

---

# 24. Mostrar el objeto en consola

Se utiliza:

```javascript
console.log("El arreglo es: ", usuario);
```

Aunque en el código se le llama "arreglo", técnicamente es un **objeto**.

Sería más correcto:

```javascript
console.log("El objeto usuario es: ", usuario);
```

La consola permite observar toda la estructura:

```text
{
    nombre: "...",
    apellido: "...",
    telefono: "...",
    correo: "...",
    pais: "..."
}
```

---

# 25. LocalStorage

Una de las partes principales de esta clase es `localStorage`.

`localStorage` permite guardar información en el navegador asociada al sitio web.

La información permanece disponible incluso después de cerrar o recargar la página, hasta que sea eliminada.

Conceptualmente:

```text
JavaScript
    ↓
localStorage
    ↓
Navegador
```

En este caso se utilizará para pasar información desde `index.html` hacia `tabla.html`.

---

# 26. `localStorage.setItem()`

Para guardar información se utiliza:

```javascript
localStorage.setItem("usuarioLocalStorage", valor);
```

La estructura es:

```text
setItem(clave, valor)
```

En el proyecto:

```javascript
localStorage.setItem(
    "usuarioLocalStorage",
    JSON.stringify(usuario)
);
```

Tenemos:

```text
Clave
↓
"usuarioLocalStorage"

Valor
↓
JSON.stringify(usuario)
```

La clave funciona como un nombre para localizar posteriormente la información.

---

# 27. ¿Por qué aparece `JSON.stringify()`?

Aquí aparece un concepto importante.

`localStorage` almacena información como **texto (`String`)**.

Pero nuestro usuario es un objeto:

```javascript
const usuario = {
    nombre: "Juan",
    apellido: "Pérez"
};
```

No se puede guardar directamente esperando conservarlo como objeto JavaScript.

Por eso se transforma:

```text
Objeto JavaScript
        ↓
JSON.stringify()
        ↓
String JSON
        ↓
localStorage
```

Ejemplo:

```javascript
JSON.stringify(usuario);
```

puede producir algo como:

```text
{"nombre":"Juan","apellido":"Pérez"}
```

Ahora la información está representada como texto.

---

# 28. `JSON.stringify()`

`JSON.stringify()` convierte un valor JavaScript en una cadena JSON.

```javascript
const usuario = {
    nombre: "Juan",
    edad: 31
};

const texto = JSON.stringify(usuario);
```

Resultado conceptual:

```text
{"nombre":"Juan","edad":31}
```

Es decir:

```text
Objeto → JSON String
```

---

# 29. Guardar el objeto en LocalStorage

Por eso la línea completa:

```javascript
localStorage.setItem(
    "usuarioLocalStorage",
    JSON.stringify(usuario)
);
```

puede entenderse como:

```text
usuario
  ↓
JSON.stringify()
  ↓
texto JSON
  ↓
localStorage
  ↓
clave: usuarioLocalStorage
```

---

# 30. Recuperar información con `getItem()`

En `tabla.html` se utiliza:

```javascript
localStorage.getItem("usuarioLocalStorage")
```

Esto busca la información previamente guardada mediante esa clave.

El proceso es:

```text
localStorage
    ↓
getItem("usuarioLocalStorage")
    ↓
String JSON
```

---

# 31. `JSON.parse()`

El resultado de `getItem()` es texto.

Necesitamos volver a convertirlo en un objeto JavaScript.

Para esto se utiliza:

```javascript
JSON.parse(...)
```

Por ejemplo:

```javascript
const obtenerDatosUsuario =
    JSON.parse(
        localStorage.getItem("usuarioLocalStorage")
    );
```

El proceso completo es:

```text
Objeto
   ↓
JSON.stringify()
   ↓
String
   ↓
localStorage
   ↓
getItem()
   ↓
String
   ↓
JSON.parse()
   ↓
Objeto
```

Este concepto es fundamental.

---

# 32. `stringify` versus `parse`

Se pueden recordar como operaciones opuestas.

```text
JSON.stringify()
Objeto → JSON String
```

```text
JSON.parse()
JSON String → Objeto
```

Por lo tanto:

```text
Guardar:

Objeto
   ↓
stringify
   ↓
localStorage


Recuperar:

localStorage
   ↓
parse
   ↓
Objeto
```

---

# 33. Recuperar los datos en `tabla.html`

El código utilizado es:

```javascript
const obtenerDatosUsuario =
    JSON.parse(
        localStorage.getItem("usuarioLocalStorage")
    );
```

Ahora `obtenerDatosUsuario` vuelve a ser un objeto.

Podemos acceder a sus propiedades:

```javascript
obtenerDatosUsuario.nombre
obtenerDatosUsuario.apellido
obtenerDatosUsuario.telefono
obtenerDatosUsuario.correo
obtenerDatosUsuario.pais
```

---

# 34. Obtener la tabla

El HTML contiene:

```html
<tbody id="tablaDatos">
</tbody>
```

JavaScript obtiene ese elemento:

```javascript
const tabla = document.getElementById("tablaDatos");
```

De esta forma JavaScript sabe dónde insertar la información.

La conexión vuelve a ser:

```text
HTML
↓
id="tablaDatos"
↓
document.getElementById("tablaDatos")
↓
JavaScript
```

---

# 35. `innerHTML`

Para insertar contenido HTML dinámicamente se utiliza:

```javascript
tabla.innerHTML = `...`;
```

`innerHTML` permite modificar el contenido interno de un elemento utilizando HTML.

Por ejemplo:

```javascript
tabla.innerHTML = `
    <tr>
        <td>Juan</td>
        <td>Pérez</td>
    </tr>
`;
```

Esto genera HTML dentro del `<tbody>`.

---

# 36. Template literals

En el código se utiliza:

```javascript
tabla.innerHTML = `
    <td>${obtenerDatosUsuario.nombre}</td>
    <td>${obtenerDatosUsuario.apellido}</td>
`;
```

Las comillas invertidas:

```text
`
```

permiten crear **template literals**.

Dentro de ellos se pueden insertar variables utilizando:

```javascript
${variable}
```

Por ejemplo:

```javascript
`${obtenerDatosUsuario.nombre}`
```

permite insertar dinámicamente el nombre.

---

# 37. Generación dinámica de la tabla

La idea es que la tabla no tenga los datos escritos manualmente.

HTML proporciona únicamente la estructura:

```html
<table>
    <thead>
        ...
    </thead>

    <tbody id="tablaDatos">
    </tbody>
</table>
```

JavaScript genera los datos:

```javascript
tabla.innerHTML = `
    <tr>
        <td>${obtenerDatosUsuario.nombre}</td>
        <td>${obtenerDatosUsuario.apellido}</td>
        <td>${obtenerDatosUsuario.telefono}</td>
        <td>${obtenerDatosUsuario.correo}</td>
        <td>${obtenerDatosUsuario.pais}</td>
    </tr>
`;
```

Por lo tanto:

```text
HTML
  ↓
crea la tabla vacía

JavaScript
  ↓
obtiene datos
  ↓
genera HTML
  ↓
inserta los datos
```

---

# 38. La tabla debe utilizar `<tr>`

En el código original se insertan directamente varios `<td>`:

```javascript
tabla.innerHTML = `
    <td>...</td>
    <td>...</td>
`;
```

Es mejor generar explícitamente una fila:

```html
<tr>
    <td>...</td>
    <td>...</td>
    <td>...</td>
    <td>...</td>
    <td>...</td>
</tr>
```

Por lo tanto, una versión corregida sería:

```javascript
tabla.innerHTML = `
    <tr>
        <td>${obtenerDatosUsuario.nombre}</td>
        <td>${obtenerDatosUsuario.apellido}</td>
        <td>${obtenerDatosUsuario.telefono}</td>
        <td>${obtenerDatosUsuario.correo}</td>
        <td>${obtenerDatosUsuario.pais}</td>
    </tr>
`;
```

---

# 39. `<th>` y `<td>`

En la cabecera se utilizan:

```html
<th>Nombre</th>
<th>Apellido</th>
```

`th` significa **table header**.

Representa una celda de encabezado.

Los datos normales utilizan:

```html
<td>Juan</td>
```

`td` significa **table data**.

Por lo tanto:

```text
<th> → encabezado
<td> → dato
```

En el código original aparece:

```javascript
<th>${obtenerDatosUsuario.pais}</td>
```

Esto está incorrectamente combinado.

Debería ser:

```html
<td>${obtenerDatosUsuario.pais}</td>
```

---

# 40. Cambio de página con `window.location`

Después de guardar los datos:

```javascript
window.location = "tabla.html";
```

indica al navegador que debe navegar hacia:

```text
tabla.html
```

El flujo completo queda:

```text
Formulario
    ↓
Usuario presiona Guardar
    ↓
submit
    ↓
preventDefault()
    ↓
Obtener valores
    ↓
Validar
    ↓
Crear objeto
    ↓
JSON.stringify()
    ↓
localStorage.setItem()
    ↓
window.location
    ↓
tabla.html
```

---

# 41. Flujo de recuperación

Cuando se abre `tabla.html`:

```text
tabla.html
    ↓
localStorage.getItem()
    ↓
obtiene JSON
    ↓
JSON.parse()
    ↓
objeto usuario
    ↓
getElementById("tablaDatos")
    ↓
innerHTML
    ↓
tabla con información
```

---

# 42. Código principal de `funcionalidades.js`

Una versión organizada del código sería:

```javascript
const formularioJS = document.getElementById("formulario");

formularioJS.addEventListener("submit", function(event) {

    event.preventDefault();

    const nombre = document.getElementById("nombre").value.trim();
    const apellido = document.getElementById("apellido").value.trim();
    const telefono = document.getElementById("telefono").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const pais = document.getElementById("pais").value.trim();

    // Verificar si algún campo está vacío
    if (
        nombre === "" ||
        apellido === "" ||
        telefono === "" ||
        correo === "" ||
        pais === ""
    ) {
        alert("Campo no puede ser vacío");
        return;
    }

    // Evitar que nombre y apellido sean iguales
    if (nombre.toLowerCase() === apellido.toLowerCase()) {
        alert("Nombre y apellido no pueden ser iguales");
        return;
    }

    // Crear objeto usuario
    const usuario = {
        nombre: nombre,
        apellido: apellido,
        telefono: telefono,
        correo: correo,
        pais: pais
    };

    console.log("Objeto usuario:", usuario);

    // Convertir objeto a JSON y guardarlo
    localStorage.setItem(
        "usuarioLocalStorage",
        JSON.stringify(usuario)
    );

    // Ir a la página de la tabla
    window.location = "tabla.html";
});
```

---

# 43. Código principal de `tabla.html`

La parte JavaScript puede quedar:

```html
<script>

    const obtenerDatosUsuario =
        JSON.parse(
            localStorage.getItem("usuarioLocalStorage")
        );

    console.log("Recuperando datos:", obtenerDatosUsuario);

    const tabla = document.getElementById("tablaDatos");

    tabla.innerHTML = `
        <tr>
            <td>${obtenerDatosUsuario.nombre}</td>
            <td>${obtenerDatosUsuario.apellido}</td>
            <td>${obtenerDatosUsuario.telefono}</td>
            <td>${obtenerDatosUsuario.correo}</td>
            <td>${obtenerDatosUsuario.pais}</td>
        </tr>
    `;

</script>
```

---

# 44. Concepto fundamental: flujo de datos

Esta clase introduce una idea importante de Fullstack:

> Los datos ingresados en una interfaz pueden transformarse, almacenarse y posteriormente recuperarse para mostrarlos en otra parte de la aplicación.

En este ejercicio:

```text
INPUT
↓
JavaScript
↓
Objeto
↓
JSON
↓
LocalStorage
↓
JSON
↓
Objeto
↓
HTML dinámico
```

Este patrón es muy importante porque se repite posteriormente con bases de datos, APIs y servidores.

La diferencia es que en este ejercicio `localStorage` funciona como un almacenamiento local del navegador.

---

# 45. LocalStorage no es una base de datos

Es importante distinguir estos conceptos.

`localStorage`:

* Pertenece al navegador.
* Almacena datos como texto.
* Está asociado al sitio web.
* No es una base de datos del servidor.
* No permite compartir automáticamente los datos con otros usuarios o computadores.

En este ejercicio funciona como una forma sencilla de conservar información entre páginas.

Más adelante, en un sistema Fullstack real, esta información podría almacenarse en:

```text
Frontend
   ↓
API
   ↓
Backend
   ↓
Base de datos
```

En esta clase, en cambio:

```text
Frontend
   ↓
LocalStorage
```

---

# 46. Objeto actual versus múltiples usuarios

Actualmente se guarda:

```javascript
const usuario = {
    nombre,
    apellido,
    telefono,
    correo,
    pais
};
```

Por lo tanto, solo se maneja **un usuario**.

Si más adelante se quisiera guardar múltiples usuarios, sería necesario utilizar un Array:

```javascript
const usuarios = [];
```

y cada usuario podría ser un objeto:

```javascript
const usuarios = [
    {
        nombre: "Juan",
        apellido: "Pérez"
    },
    {
        nombre: "Ana",
        apellido: "Gómez"
    }
];
```

Aquí tendríamos:

```text
Array
│
├── Objeto usuario 1
│   ├── nombre
│   └── apellido
│
└── Objeto usuario 2
    ├── nombre
    └── apellido
```

Esto será importante para comprender posteriormente cómo almacenar múltiples registros.

---

# 47. Resumen de la arquitectura

## HTML

Se encarga de la estructura:

```text
Formulario
Inputs
Select
Botones
Tabla
```

## CSS

Se encarga de la apariencia:

```text
Colores
Espaciado
Tamaños
Flexbox
Hover
Focus
Sombras
```

## JavaScript

Se encarga de la lógica:

```text
Eventos
Validación
Obtención de datos
Objetos
JSON
LocalStorage
Navegación
Generación dinámica de HTML
```

---

# 48. Conceptos nuevos de la clase

Los conceptos principales que conviene dominar antes de continuar son:

* [ ] Crear objetos con `{}`.
* [ ] Diferenciar un objeto de un Array.
* [ ] Acceder a propiedades mediante `objeto.propiedad`.
* [ ] Utilizar `JSON.stringify()`.
* [ ] Utilizar `JSON.parse()`.
* [ ] Guardar datos con `localStorage.setItem()`.
* [ ] Recuperar datos con `localStorage.getItem()`.
* [ ] Entender el concepto de clave y valor en `localStorage`.
* [ ] Generar HTML dinámicamente.
* [ ] Utilizar `innerHTML`.
* [ ] Utilizar template literals con `` ` ` ``.
* [ ] Insertar valores mediante `${}`.
* [ ] Utilizar `window.location`.
* [ ] Entender el flujo de datos entre dos páginas.

---

# 49. Conceptos que conectan las clases

La clase anterior se centraba principalmente en:

```text
HTML
   ↓
CSS
   ↓
JavaScript
   ↓
Eventos y validaciones
```

Esta clase agrega:

```text
JavaScript
   ↓
Objetos
   ↓
JSON
   ↓
LocalStorage
   ↓
Otra página
   ↓
JSON.parse()
   ↓
HTML dinámico
```

Por lo tanto, la principal evolución de esta clase es que JavaScript deja de utilizarse solamente para reaccionar ante eventos y comienza a **manipular y transportar datos**.

---

# 50. Idea central para recordar

La secuencia más importante de esta clase es:

```text
1. El usuario ingresa datos
        ↓
2. JavaScript obtiene los datos
        ↓
3. Se crea un objeto
        ↓
4. Se convierte a JSON
        ↓
5. Se guarda en LocalStorage
        ↓
6. Se cambia de página
        ↓
7. Se recupera el JSON
        ↓
8. JSON.parse() lo convierte nuevamente en objeto
        ↓
9. JavaScript obtiene las propiedades
        ↓
10. innerHTML genera la fila de la tabla
```

La idea fundamental es:

```text
OBJETO
   ↓
JSON.stringify()
   ↓
LOCALSTORAGE
   ↓
JSON.parse()
   ↓
OBJETO
```

Este proceso es uno de los conceptos más importantes de la clase 4 porque constituye una introducción al manejo de datos que posteriormente se utilizará con APIs, backend y bases de datos.
