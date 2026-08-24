# Integración de HTML, CSS y JavaScript

## 1. Objetivo de la clase

En esta clase se trabajó la integración de tres tecnologías fundamentales del desarrollo web:

* **HTML** → estructura y contenido de la página.
* **CSS** → apariencia y estilos.
* **JavaScript** → comportamiento e interacción.

La idea principal es mantener cada responsabilidad separada en su propio archivo.

```text
Proyecto
│
├── index.html
├── estilo.css
└── funcionalidades.js
```

La relación entre ellos se realiza desde el HTML:

```text
HTML
 ├───> CSS
 │      └── apariencia
 │
 └───> JavaScript
        └── comportamiento
```

---

# 2. HTML — Estructura

HTML define los elementos que existen en la página.

En este ejercicio se creó un formulario con:

* Nombre
* Apellido
* Teléfono
* Correo
* País
* Botón Guardar

## 2.1 Estructura básica

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Document</title>

    <link rel="stylesheet" href="estilo.css">
</head>

<body>

    <!-- Contenido -->

    <script src="funcionalidades.js"></script>
</body>

</html>
```

### `<!DOCTYPE html>`

Indica al navegador que el documento utiliza HTML5.

### `<html>`

Es el elemento raíz del documento.

```html
<html lang="en">
```

El atributo `lang` indica el idioma principal de la página.

Si la página está en español, sería preferible:

```html
<html lang="es">
```

### `<meta charset="UTF-8">`

Define la codificación de caracteres.

Permite trabajar correctamente con caracteres como:

```text
á
é
í
ó
ú
ñ
```

### `<meta name="viewport">`

Es especialmente importante para que la página se adapte correctamente a distintos tamaños de pantalla.

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

---

# 3. Vincular HTML con CSS

Para utilizar un archivo CSS externo se utiliza:

```html
<link rel="stylesheet" href="estilo.css">
```

Esto significa:

```text
HTML
  │
  └── link
       │
       └── estilo.css
```

El HTML proporciona la estructura y CSS determina cómo se verá.

Por ejemplo, si HTML tiene:

```html
<div class="campo">
    <label>Nombre</label>
    <input type="text">
</div>
```

CSS puede definir cómo se verá ese elemento mediante:

```css
.campo {
    background-color: rgb(226, 226, 226);
}
```

---

# 4. Formularios en HTML

El elemento principal utilizado fue:

```html
<form id="formulario">
```

`form` representa un formulario.

El atributo:

```html
id="formulario"
```

permite identificarlo posteriormente desde JavaScript.

Esto será importante porque JavaScript necesita encontrar el formulario para poder reaccionar cuando se envíe.

---

# 5. `class` e `id`

En este ejercicio aparecen ambos conceptos.

## `class`

Ejemplo:

```html
<div class="campo">
```

Las clases se utilizan principalmente para aplicar estilos o agrupar elementos que comparten características.

En CSS se seleccionan utilizando:

```css
.campo {
    ...
}
```

## `id`

Ejemplo:

```html
<form id="formulario">
```

o:

```html
<input id="nombre">
```

El `id` identifica un elemento específico.

JavaScript puede buscarlo mediante:

```javascript
document.getElementById("nombre");
```

Una forma sencilla de recordar la diferencia:

```text
class → puede repetirse
id    → identifica un elemento específico
```

---

# 6. Campos del formulario

Ejemplo para el nombre:

```html
<div class="campo">
    <label>Nombre</label>
    <input type="text" id="nombre" placeholder="Juan">
</div>
```

El `input` permite ingresar información.

```html
type="text"
```

indica que se trata de un campo de texto.

```html
id="nombre"
```

permite localizarlo desde JavaScript.

```html
placeholder="Juan"
```

muestra un texto de referencia mientras el campo está vacío.

---

# 7. Select

Para seleccionar un país se utilizó:

```html
<select id="pais">

    <option value="">Seleccione su país</option>
    <option value="chile">Chile</option>
    <option value="perú">Perú</option>
    <option value="argentina">Argentina</option>
    <option value="brasil">Brasil</option>
    <option value="bolivia">Bolivia</option>
    <option value="uruguay">Uruguay</option>

</select>
```

`select` crea una lista desplegable.

Cada opción se representa mediante:

```html
<option>
```

El atributo `value` es especialmente importante porque JavaScript puede obtener ese valor posteriormente.

Por ejemplo:

```html
<option value="chile">Chile</option>
```

Al seleccionar Chile, JavaScript obtiene:

```text
"chile"
```

---

# 8. Botón del formulario

```html
<button type="submit">Guardar</button>
```

El atributo:

```html
type="submit"
```

indica que el botón enviará el formulario.

JavaScript puede detectar este evento mediante:

```javascript
formularioJS.addEventListener("submit", ...)
```

---

# 9. CSS — Apariencia

CSS se encarga de modificar la apariencia de los elementos HTML.

El archivo utilizado fue:

```text
estilo.css
```

---

# 10. Selector por clase

Para seleccionar una clase se utiliza:

```css
.nombreClase {
    propiedad: valor;
}
```

Por ejemplo:

```css
.campo {
    background-color: rgb(226, 226, 226);
}
```

Esto aplica el estilo a todos los elementos que tengan:

```html
class="campo"
```

---

# 11. Estilo del formulario

```css
.formulariostyle {
    background-color: rgb(243, 243, 243);
    padding: 30px;
}
```

### `background-color`

Define el color de fondo.

```css
background-color: rgb(243, 243, 243);
```

### `padding`

Define el espacio interior entre el contenido y el borde del elemento.

```css
padding: 30px;
```

Conceptualmente:

```text
┌─────────────────────────────┐
│        padding              │
│   ┌─────────────────────┐   │
│   │      contenido      │   │
│   └─────────────────────┘   │
└─────────────────────────────┘
```

---

# 12. Estilo de los campos

```css
.campo {
    background-color: rgb(226, 226, 226);
    color: rgb(0, 0, 0);
    width: 100%;
    max-width: 500px;
    box-shadow: 0 5px 20px;
    padding: 20px;
}
```

### `width`

```css
width: 100%;
```

Hace que el elemento intente ocupar todo el ancho disponible.

### `max-width`

```css
max-width: 500px;
```

Establece un límite máximo de 500 píxeles.

Esto evita que el elemento se vuelva excesivamente grande.

### `box-shadow`

```css
box-shadow: 0 5px 20px;
```

Agrega una sombra alrededor del elemento.

### `padding`

```css
padding: 20px;
```

Agrega espacio interior.

---

# 13. JavaScript — Comportamiento

JavaScript se encarga de responder a las acciones del usuario.

En este caso se utiliza para:

1. Detectar el envío del formulario.
2. Evitar que el navegador recargue la página.
3. Obtener los valores ingresados.
4. Validar que los campos no estén vacíos.
5. Mostrar información en la consola.
6. Determinar si el país corresponde a Chile o a otro país.

El archivo utilizado es:

```text
funcionalidades.js
```

---

# 14. Obtener un elemento HTML desde JavaScript

Para encontrar el formulario:

```javascript
const formularioJS = document.getElementById("formulario");
```

`document` representa el documento HTML actual.

```javascript
document.getElementById(...)
```

busca un elemento mediante su `id`.

Por ejemplo:

```javascript
document.getElementById("nombre");
```

busca:

```html
<input id="nombre">
```

---

# 15. Eventos

Una vez obtenido el formulario:

```javascript
const formularioJS = document.getElementById("formulario");
```

se puede escuchar un evento:

```javascript
formularioJS.addEventListener("submit", function(event) {

});
```

`addEventListener()` permite ejecutar código cuando ocurre un determinado evento.

En este caso:

```text
submit
```

significa que se envió el formulario.

---

# 16. `event.preventDefault()`

Al enviar un formulario HTML, el comportamiento normal del navegador es procesarlo y normalmente recargar o navegar.

Para evitar ese comportamiento:

```javascript
event.preventDefault();
```

Esto permite controlar el envío mediante JavaScript.

En otras palabras:

```text
Usuario presiona Guardar
        ↓
submit
        ↓
JavaScript intercepta el evento
        ↓
preventDefault()
        ↓
No se ejecuta el comportamiento predeterminado
        ↓
JavaScript continúa con la validación
```

---

# 17. Obtener valores de los campos

Para obtener el contenido de un `input`:

```javascript
const nombre = document.getElementById("nombre").value;
```

Aquí existen tres partes importantes:

```text
document.getElementById("nombre")
            ↓
     encuentra el elemento

.value
   ↓
obtiene lo escrito
```

Por ejemplo, si el usuario escribió:

```text
Juan
```

entonces:

```javascript
nombre
```

contendrá:

```text
"Juan"
```

---

# 18. `trim()`

En algunos campos se utilizó:

```javascript
.value.trim();
```

`trim()` elimina espacios al comienzo y al final del texto.

Por ejemplo:

```text
"   Juan   "
```

se transforma en:

```text
"Juan"
```

Esto es útil para validaciones.

Por ejemplo:

```javascript
const apellido = document
    .getElementById("apellido")
    .value
    .trim();
```

---

# 19. Validación de campos vacíos

Se puede comprobar si alguno de los campos está vacío mediante:

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

Aquí se utiliza el operador lógico:

```text
||
```

que significa **OR**.

La condición será verdadera si al menos uno de los campos está vacío.

Por ejemplo:

```text
nombre = "Juan"
apellido = "Pérez"
telefono = ""
correo = "juan@gmail.com"
pais = "chile"
```

Como `telefono === ""` es verdadero, se ejecuta el `if`.

---

# 20. `return`

Dentro de la validación aparece:

```javascript
return;
```

En este caso sirve para detener la ejecución de la función del evento.

El flujo sería:

```text
Campo vacío
    ↓
alert()
    ↓
return
    ↓
Se detiene el código
```

De esta forma, si hay errores de validación, no se continúa con el resto del procesamiento.

---

# 21. `console.log()`

Durante el desarrollo se utilizó:

```javascript
console.log(nombre);
console.log(apellido);
console.log(telefono);
console.log(correo);
```

`console.log()` permite mostrar información en la consola del navegador.

Es muy útil para comprobar qué valores está obteniendo JavaScript.

Por ejemplo:

```javascript
console.log("Botón funcionando");
```

permite comprobar que el evento `submit` está siendo detectado.

---

# 22. `toLowerCase()`

También se comenzó a trabajar con:

```javascript
apellido.toLowerCase()
```

Este método transforma el texto a minúsculas.

Por ejemplo:

```text
"PEREZ"
```

se transforma en:

```text
"perez"
```

Es útil cuando queremos comparar textos sin importar si el usuario escribió mayúsculas o minúsculas.

Por ejemplo:

```javascript
if (pais.toLowerCase() === "chile") {
    ...
}
```

---

# 23. Clasificación según el país

El código utiliza:

```javascript
if (pais == "chile") {
    console.log("Nacional");
} else {
    console.log("Extranjero");
}
```

La lógica es:

```text
¿pais es "chile"?
       │
   ┌───┴───┐
   Sí      No
   ↓        ↓
Nacional  Extranjero
```

Una forma más recomendable en JavaScript moderno es utilizar `===`:

```javascript
if (pais === "chile") {
    console.log("Nacional");
} else {
    console.log("Extranjero");
}
```

`===` realiza una comparación estricta de valor y tipo.

---

# 24. Código JavaScript organizado

Una versión más limpia del código trabajado en clase sería:

```javascript
const formularioJS = document.getElementById("formulario");

formularioJS.addEventListener("submit", function(event) {

    event.preventDefault();

    console.log("Botón funcionando");

    const nombre = document.getElementById("nombre").value.trim();
    const apellido = document.getElementById("apellido").value.trim();
    const telefono = document.getElementById("telefono").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const pais = document.getElementById("pais").value.trim();

    if (
        nombre === "" ||
        apellido === "" ||
        telefono === "" ||
        correo === "" ||
        pais === ""
    ) {
        alert("El campo no puede estar vacío");
        return;
    }

    console.log(nombre);
    console.log(apellido);
    console.log(telefono);
    console.log(correo);

    if (pais === "chile") {
        console.log("Nacional");
    } else {
        console.log("Extranjero");
    }
});
```

---

# 25. CSS organizado

Una versión inicial del CSS podría quedar:

```css
.formulariostyle {
    background-color: rgb(243, 243, 243);
    padding: 30px;
}

.campo {
    background-color: rgb(226, 226, 226);
    color: rgb(0, 0, 0);
    width: 100%;
    max-width: 500px;
    box-shadow: 0 5px 20px;
    padding: 20px;
}

.botones {
    color: rgb(0, 0, 0);
    width: 100%;
    max-width: 500px;
    padding: 20px;
}
```

### Observación sobre `border`

En el código original aparece:

```css
border: 100px;
```

Esto no produce un borde válido de la forma esperada porque `border` normalmente necesita especificar al menos un estilo, por ejemplo:

```css
border: 1px solid black;
```

Por otro lado, si la intención era generar espacio alrededor del elemento, probablemente se buscaba utilizar:

```css
margin: 100px;
```

Mientras que el espacio interior corresponde a:

```css
padding: 100px;
```

Por lo tanto:

```text
padding → espacio interior
margin  → espacio exterior
border  → borde
```

---

# 26. HTML completo

Una versión organizada del HTML sería:

```html
<!DOCTYPE html>

<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta
        name="viewport"
        content="width=device-width, initial-scale=1.0"
    >

    <title>Formulario</title>

    <link rel="stylesheet" href="estilo.css">
</head>

<body>

    <form class="formulariostyle" id="formulario">

        <div class="fila">

            <div class="campo">
                <label>Nombre</label>
                <input
                    type="text"
                    id="nombre"
                    placeholder="Juan"
                >
            </div>

            <div class="campo">
                <label>Apellido</label>
                <input
                    type="text"
                    id="apellido"
                    placeholder="Perez"
                >
            </div>

        </div>

        <div class="fila">

            <div class="campo">
                <label>Telefono</label>
                <input
                    type="text"
                    id="telefono"
                    placeholder="+56976644785"
                >
            </div>

            <div class="campo">
                <label>Correo</label>
                <input
                    type="text"
                    id="correo"
                    placeholder="ejemplo@gmail.com"
                >
            </div>

            <div class="campo">

                <label>Pais</label>

                <select id="pais">
                    <option value="">Seleccione su pais</option>
                    <option value="chile">Chile</option>
                    <option value="perú">Perú</option>
                    <option value="argentina">Argentina</option>
                    <option value="brasil">Brasil</option>
                    <option value="bolivia">Bolivia</option>
                    <option value="uruguay">Uruguay</option>
                </select>

            </div>

        </div>

        <div class="botones">
            <button type="submit">Guardar</button>
        </div>

    </form>

    <script src="funcionalidades.js"></script>

</body>

</html>
```

---

# 27. Integración de los tres archivos

El resultado final se puede entender como tres capas:

```text
                    PROYECTO WEB

                         │
             ┌───────────┼───────────┐
             │           │           │
             ▼           ▼           ▼
         index.html  estilo.css  funcionalidades.js
             │           │           │
             ▼           ▼           ▼
         Estructura   Apariencia  Comportamiento
             │           │           │
             └───────────┼───────────┘
                         ▼
                    Página web
```

### HTML

Se encarga de:

```text
¿Qué elementos existen?
```

### CSS

Se encarga de:

```text
¿Cómo se ven?
```

### JavaScript

Se encarga de:

```text
¿Qué hacen?
```

Esta separación permite mantener el código organizado y facilita posteriormente modificar la estructura, apariencia o comportamiento de la aplicación sin tener que colocar todo en un único archivo.

---

# Conceptos clave de la clase

* [ ] Crear una estructura HTML.
* [ ] Utilizar `class` e `id`.
* [ ] Crear formularios con `form`.
* [ ] Utilizar `input`, `select`, `option` y `button`.
* [ ] Vincular CSS externo mediante `<link>`.
* [ ] Vincular JavaScript externo mediante `<script>`.
* [ ] Utilizar selectores por clase en CSS.
* [ ] Comprender `padding`, `margin` y `border`.
* [ ] Obtener elementos HTML con `getElementById()`.
* [ ] Obtener valores mediante `.value`.
* [ ] Utilizar `addEventListener()`.
* [ ] Detectar el evento `submit`.
* [ ] Utilizar `event.preventDefault()`.
* [ ] Validar campos con `if`.
* [ ] Utilizar operadores lógicos `||`.
* [ ] Utilizar `trim()`.
* [ ] Utilizar `toLowerCase()`.
* [ ] Mostrar información con `console.log()`.
* [ ] Comparar valores con `===`.
* [ ] Mantener separadas estructura, apariencia y comportamiento.
