```HTML
<!DOCTYPE html>

<html lang="en">
<head>

   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Document</title>

</head>

   <style>
       .formulario{
           background-color: rgb(255, 127, 127);
           font-size: large;
           width: 50%;
           border: red;
           color: white;
           padding: 50px;
           box-shadow: 0 20px 50px rgba(0,0,0,0.1);
       }
       .boton{
           display: flex;
           justify-content: center;
           margin-top: 20px;
       }
   </style>

<body>
   <form class ="formulario" id="formulario">
       <div class="fila">
           <label> Nombre: </label>
           <input type="text" id="nombre" placeholder="Marcelo">
       </div>

       <div class="fila">
           <label> Apellido: </label>
           <input type="text" id="apellido" placeholder="Soto">
       </div>

   <div class="boton">
       <button type="submit"> Guardar </button>
   </div>

   </form>

   <script>
       const formulario = document.getElementById("formulario");

       formulario.addEventListener("submit", function(event){
           event.preventDefault();
           console.log("Listo");

           const CapturandoNombre = document.getElementById("nombre").value;
           const CapturandoApellido = document.getElementById("apellido").value;

           console.log("Nombre: ", CapturandoNombre);
           console.log("Apellido: ", CapturandoApellido);
       })
   </script>

</body>

</html>

```

### Explicación
```html
<html lang="en">
<head>

   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Document</title>

</head>
```

```html
<html lang="es">
```
Indica el idioma principal del documento.

```html
<head>
</head>
```
Contiene información sobre la página, "configuración del documento".

```html
<meta charset="UTF-8">
```
Indica que utiliza UTF-8 para interpretar los caracteres. Este permite que funcionen correctamente:
```
á é í ó ú
ñ
¿ ?
¡ !
```

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```
Es importante para páginas que deben funcioanar en celulares. Basicamente, le dice que use el ancho real del dispositivo y comience con una escala normal.

```html
<title>Document</title>
```
Titulo que aparece normalmente en la pestaña del navegador.

```HTML
<body>
    ...
</body>
```
Aquí está prácticamente todo lo que el usuario verá e interactuará.

```html
<body>

   <form class="formulario" id="formulario">
       ...
   </form>

   <script>
       ...
   </script>

</body>
```

Se puede imaginar con esta estructura:
```
<html>
│
├── <head>
│   ├── configuración
│   ├── título
│   └── metadatos
│
└── <body>
    ├── formulario
    ├── textos
    ├── botones
    └── JavaScript
```

```html
<form class="formulario" id="formulario">
```
Representa un formulario. 

```html
class="formulario"
id="formulario"
```
Estos corresponden a aatributos que proporcionan información adicional.

Por ejemplo:
```html
<input type="text" id="nombre" placeholder="Marcelo">
```

El id sirve para identificar un elemento específico. 
```html
id="nombre"
```

Por ejemplo, después en JavaScript tenemos:
```javascript
document.getElementById("nombre")
```
Que quiere decir: "Busca en el documento el elemento cuyo `id` sea `nombre`."

Y encuentra:
```html
<input type="text" id="nombre" placeholder="Marcelo">
```

class
```
class="formulario"
```
```html
class="fila"
```
Sirven principalmente para agrupar elementos que comparten características.
Por ejemplo:
```html
<div class="fila">
```

Style
```css
<style>
    .formulario {
        background-color: rgb(255, 127, 127);
        font-size: large;
        width: 50%;
        border: red;
        color: white;
        padding: 50px;
        box-shadow: 0 20px 50px rgba(0,0,0,0.1);
    }
</style>
```
Esto ya es CSS dentro del html. Sirve para decir: **estos elementos HTML deben verse de esta manera.**

```CSS
.formulario {
    background-color: red;
}
```
Acá por ejemplo, con el . indica que debe buscar los elementos que tengan `class="formulario"`

Div
```html
<div>
...
</div>
```
Este es un contenedor. Basicamente un contenedor genérico
Por ejemplo:
```html
<div class="fila">
    <label>Nombre:</label>
    <input>
</div>
```
Es basicamente una caja
```
┌──────────────────────────────┐
│ Nombre: [______________]     │
└──────────────────────────────┘
```

`div` se utiliza mucha para organizar una página. No significa necesariamente "fila", "boton", "formulario", etc. Es simplemente un contenedor. La clase es la que le da un proposito visual o estructural.

Label
```HTML
<label> Nombre: </label>
```
Sería la etiqueta de texto para un campo. Normalmente asociada a un formulario.

input
```html
<input type="text" id="nombre" placeholder="Marcelo">
```
Esto crea un campo de texto:
```
┌────────────────────┐
│ Marcelo            │
└────────────────────┘
```
Pero "Marcelo" no es el valor real, eso es solo un `placeholder`. Es solamente un texto de ejemplo. Cuando el usuario escribe: José, ahí recién pasa a ser un valor real. Y así, JavaScript puede obtenerlo mediante:
```javascript
document.getElementById("nombre").value
```

JavaScript
```javascript
const formulario = document.getElementById("formulario");
```

`document` representa el documento HTML actual. Es decir, la página con la que estamos trabajando. Entonces:
```javascript
document.getElementById("formulario")
```
Lo que hace, es buscar dentro de esta página un elemento que tenga un `id=formulario`, esto va a encontrar:
```html
<form class="formulario" id="formulario">
```

Luego:
```javascript
const formulario = ...
```
va a guardar ese elemento en una variable llamada formulario. 

addEventListener
```javascript
formulario.addEventListener("submit", function(event) {
```
Aquí, indicamos que quiero escuchar cuando ocurra un evento `submit` en este formulario. Este puede ser:
```
click
submit
keydown
keyup
mouseover
change
input
```

Por lo tanto, la estructura sería: 
```
Usuario
   ↓
escribe nombre
   ↓
escribe apellido
   ↓
presiona Guardar
   ↓
<button type="submit">
   ↓
se produce "submit"
   ↓
JavaScript lo detecta

```