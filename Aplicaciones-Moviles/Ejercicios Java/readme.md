# Ejercicios de repaso — Java

Progresión de ejercicios para practicar una base sólida de Java. La dificultad aumenta gradualmente y los ejercicios están planteados para resolverlos por cuenta propia, sin código de solución.

---

## Ejercicio 1 — Sistema de vehículos

Crea un sistema para administrar vehículos.

### Requisitos

- Una interfaz `Arrancable` con el método:
  - `void arrancar()`
- Una clase abstracta `Vehiculo` con:
  - `marca`
  - `modelo`
  - `anio`
  - un método abstracto `String tipoVehiculo()`
  - un método `double calcularValor()` que quede pendiente de implementación en las clases hijas.
- `Auto` extiende `Vehiculo`.
  - Tiene `numeroPuertas`.
  - Implementa los métodos abstractos.
- `Motocicleta` extiende `Vehiculo`.
  - Tiene `cilindrada`.
  - Implementa los métodos abstractos.
- Ambas clases deben implementar `Arrancable`.
- Una clase `Garaje` administra una lista privada de vehículos.
  - Agregar vehículo.
  - Listar vehículos.

### Menú

1. Agregar auto.
2. Agregar motocicleta.
3. Listar vehículos.
4. Salir.

### Condición importante

Al listar, no debes comprobar manualmente si el vehículo es un auto o motocicleta. Debes aprovechar el polimorfismo.

---

## Ejercicio 2 — Biblioteca

Amplía la idea del ejercicio anterior creando un sistema de biblioteca.

### Clases y comportamiento

- Una interfaz `Prestable` con:
  - `void prestar()`
  - `void devolver()`
- Una clase abstracta `MaterialBiblioteca` con:
  - `titulo`
  - `codigo`
  - `anioPublicacion`
  - `disponible`
  - método abstracto `String tipoMaterial()`
- `Libro` extiende `MaterialBiblioteca`.
  - Tiene `autor`.
- `Revista` extiende `MaterialBiblioteca`.
  - Tiene `numeroEdicion`.
- `Libro` y `Revista` implementan `Prestable`.
- Una clase `Biblioteca` mantiene una lista privada de materiales.

### Funciones

La biblioteca debe permitir:

- Agregar materiales.
- Listar materiales.
- Buscar un material por su código.
- Prestar un material.
- Devolver un material.
- Mostrar solamente los materiales disponibles.

### Menú

1. Agregar libro.
2. Agregar revista.
3. Listar todos.
4. Buscar por código.
5. Prestar material.
6. Devolver material.
7. Listar disponibles.
8. Salir.

### Condición importante

Para prestar o devolver, busca el material por código y deja que el propio objeto controle si la operación es válida.

No preguntes desde `Main` si es `Libro` o `Revista` para ejecutar `prestar()` o `devolver()`.

---

## Ejercicio 3 — Tienda de productos

Ahora agrega cálculo de precios y operaciones sobre una colección.

### Clases y comportamiento

- Una interfaz `Descontable` con:
  - `double calcularDescuento()`
- Una clase abstracta `Producto` con:
  - `codigo`
  - `nombre`
  - `precio`
  - método abstracto `String categoria()`
  - método para obtener el precio final.
- `ProductoElectronico`
  - Tiene `marca`.
  - Descuento del 10%.
- `ProductoRopa`
  - Tiene `talla`.
  - Descuento del 20%.
- `ProductoAlimento`
  - Tiene `fechaVencimiento`.
  - Descuento del 5%.
- Una clase `Tienda` administra una lista privada de productos.

### Funciones

La tienda debe permitir:

- Agregar productos.
- Listar productos.
- Buscar un producto por código.
- Eliminar un producto por código.
- Calcular el precio final de cada producto.
- Calcular cuánto dinero representan todos los productos almacenados.

### Menú

1. Agregar producto electrónico.
2. Agregar producto de ropa.
3. Agregar producto alimento.
4. Listar productos.
5. Buscar producto.
6. Eliminar producto.
7. Mostrar valor total del inventario.
8. Salir.

### Condición importante

El cálculo del descuento y del precio final debe resolverse mediante polimorfismo.

El `Main` no debería tener que preguntar qué tipo de producto está recorriendo para calcular sus precios.

---

## Ejercicio 4 — Sistema de trabajadores

En este ejercicio tendrás diferentes tipos de trabajadores y además algunos comportamientos opcionales.

### Clases y comportamiento

- Una clase `Persona` con:
  - `nombre`
  - `rut`
  - `edad`
- Una interfaz `Trabajable` con:
  - `double calcularPago()`
- Una interfaz `Bonificable` con:
  - `double calcularBono()`
- Una clase abstracta `Trabajador` que extiende `Persona` e implementa `Trabajable`.
  - Tiene `cargo`.
  - Puede tener `sueldoBase`.
  - Declara un método abstracto `String tipoTrabajador()`.
- `TrabajadorTiempoCompleto`
  - Tiene sueldo fijo.
- `TrabajadorPorHora`
  - Tiene `horasTrabajadas`.
  - Tiene `valorHora`.
- `TrabajadorComision`
  - Tiene `ventas`.
  - Tiene `porcentajeComision`.
  - Puede implementar `Bonificable`.
- Decide qué otras clases deberían implementar `Bonificable` y bajo qué condiciones.

### Funciones de la empresa

Crea una clase `Empresa` que mantenga una lista privada de trabajadores.

Debe permitir:

- Agregar trabajadores.
- Listarlos.
- Buscar un trabajador por RUT.
- Eliminar un trabajador por RUT.
- Calcular el total que la empresa debe pagar.
- Mostrar cuánto recibe cada trabajador.
- Mostrar el bono de los trabajadores que tengan ese comportamiento.

### Menú

1. Agregar trabajador.
2. Listar trabajadores.
3. Buscar trabajador por RUT.
4. Eliminar trabajador.
5. Mostrar total de pagos.
6. Mostrar bonos.
7. Salir.

### Condición importante

No todos los trabajadores tienen que poder recibir un bono.

El programa debe ser capaz de trabajar con trabajadores que implementan `Bonificable` y otros que no, sin modificar la clase base para obligar a todos a tener un bono.

---

## Ejercicio 5 — Sistema de pedidos

Este ejercicio combina herencia, interfaces, colecciones, búsqueda, estados y varias operaciones.

### Clases y comportamiento

- Una clase `Cliente` con:
  - `nombre`
  - `rut`
- Una interfaz `Rastreable` con:
  - `String obtenerEstado()`
- Una clase abstracta `Pedido` con:
  - `numero`
  - `cliente`
  - `fecha`
  - `estado`
  - método abstracto `double calcularTotal()`
  - método abstracto `String tipoPedido()`
- `PedidoNormal`
  - Tiene un costo de envío fijo.
- `PedidoExpress`
  - Tiene un costo de envío mayor.
  - Tiene prioridad.
- `PedidoInternacional`
  - Tiene un costo de envío.
  - Tiene un impuesto adicional.
- Algunos tipos de pedido deben implementar `Rastreable`.

### Estados

Un pedido puede encontrarse, por ejemplo, en:

- `CREADO`
- `PREPARANDO`
- `ENVIADO`
- `ENTREGADO`
- `CANCELADO`

Controla que no todas las transiciones sean válidas.

Por ejemplo:

- Un pedido entregado no debería volver a estado enviado.
- Un pedido cancelado no debería poder marcarse como entregado.
- Un pedido creado sí puede pasar a preparando.

### Clase `GestorPedidos`

Debe mantener una lista privada de pedidos y permitir:

- Crear pedidos.
- Listar pedidos.
- Buscar pedido por número.
- Eliminar un pedido.
- Cambiar el estado de un pedido.
- Calcular el total de un pedido.
- Calcular cuánto dinero representan todos los pedidos.
- Mostrar solamente los pedidos que estén en determinado estado.
- Mostrar el estado de los pedidos que sean rastreables.

### Menú

1. Crear pedido normal.
2. Crear pedido express.
3. Crear pedido internacional.
4. Listar pedidos.
5. Buscar pedido.
6. Cambiar estado.
7. Eliminar pedido.
8. Mostrar total de pedidos.
9. Filtrar pedidos por estado.
10. Mostrar seguimiento.
11. Salir.

### Condición importante

El `Main` debe encargarse principalmente de la interacción con el usuario.

La lógica relacionada con pedidos debe quedar distribuida entre las clases correspondientes.

Evita llenar `Main` con grandes bloques de `if`, `switch` o comprobaciones de tipo. Utiliza métodos, encapsulamiento y polimorfismo.

---

# Orden recomendado

Hazlos en este orden:

1. **Vehículos** — herencia, abstracción, interfaces y polimorfismo.
2. **Biblioteca** — agrega búsqueda, estados y operaciones sobre objetos.
3. **Tienda** — agrega cálculos y eliminación dentro de la colección.
4. **Trabajadores** — agrega múltiples interfaces y comportamientos opcionales.
5. **Pedidos** — combina prácticamente todo lo anterior y añade gestión de estados.

La idea es que en cada ejercicio reutilices conceptos anteriores, pero sin copiar exactamente la misma estructura.
