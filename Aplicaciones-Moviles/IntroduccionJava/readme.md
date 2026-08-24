# Actividad práctica — Sistema de Gestión de Empleados

## Objetivo

Construir un programa en **Java** que aplique los conceptos vistos en la Parte 1:

* Clases
* Encapsulamiento
* Herencia
* Polimorfismo
* Clases abstractas
* Interfaces
* Colecciones
* Menú de consola

La actividad es completamente en **Java**. No se utiliza Android ni Kotlin.

---

## 1. Estructura general

El programa estará compuesto por las siguientes clases e interfaz:

```text
Pagable
   │
   └── calcularSueldo()

Usuario
   │
   └── Empleado (abstracta)
          │
          ├── EmpleadoComun
          │
          └── EmpleadoVentas

GestorEmpleados

Main
```

### Relación entre las clases

```text
Usuario
   ▲
   │ extends
Empleado
   │
   ├── EmpleadoComun
   └── EmpleadoVentas

Empleado ── implements ──> Pagable
```

---

# 2. Interfaz `Pagable`

La interfaz representa el contrato que deben cumplir las clases que sepan calcular un sueldo.

Debe tener un único método:

```java
public interface Pagable {
    double calcularSueldo();
}
```

La idea es:

> Cualquier clase que implemente `Pagable` promete saber calcular su propio sueldo.

---

# 3. Clase `Usuario`

`Usuario` es la clase base.

Debe contener los datos generales de un usuario:

```text
rut
nombre
edad
```

El atributo `rut` debe ser de tipo `String`.

Todos los atributos deben estar encapsulados:

```java
private String rut;
```

El acceso se realiza mediante métodos `get` y `set`.

Ejemplo:

```java
public String getRut() {
    return rut;
}

public void setRut(String rut) {
    this.rut = rut;
}
```

---

# 4. Clase abstracta `Empleado`

`Empleado`:

* Extiende `Usuario`.
* Implementa `Pagable`.
* Es una clase `abstract`.
* No implementa `calcularSueldo()`.
* Tiene el atributo `sueldoBase`.
* Tiene el atributo `cargo`.
* Declara el método abstracto `tipoContrato()`.

Estructura conceptual:

```java
public abstract class Empleado extends Usuario implements Pagable {

    private double sueldoBase;
    private String cargo;

    public abstract String tipoContrato();
}
```

Como `Empleado` es abstracta, **no se puede crear directamente**:

```java
new Empleado(...); // Incorrecto
```

Solo se pueden crear objetos de sus clases hijas concretas:

```java
new EmpleadoComun(...);
new EmpleadoVentas(...);
```

---

# 5. Método `calcularSueldo()`

`Empleado` implementa la interfaz `Pagable`, pero deja pendiente la implementación de:

```java
double calcularSueldo();
```

Esto significa que las clases hijas deberán decidir cómo calcular el sueldo.

Por lo tanto:

```text
Pagable
    │
    └── calcularSueldo()
             ↑
             │
Empleado
    │
    ├── EmpleadoComun
    │      └── calcula sueldoBase
    │
    └── EmpleadoVentas
           └── calcula sueldoBase + comisión
```

---

# 6. Método abstracto `tipoContrato()`

Además del método proveniente de `Pagable`, `Empleado` declara su propio método abstracto:

```java
public abstract String tipoContrato();
```

Este método **no pertenece a la interfaz `Pagable`**.

La interfaz solamente exige:

```java
calcularSueldo()
```

Mientras que `Empleado` agrega una obligación adicional:

```java
tipoContrato()
```

Por lo tanto, las clases hijas deben implementar ambos métodos.

---

# 7. Clase `EmpleadoComun`

`EmpleadoComun` extiende `Empleado`.

Características:

```text
Sueldo = sueldoBase
Tipo de contrato = "Indefinido"
```

Debe implementar:

```java
@Override
public double calcularSueldo() {
    return sueldoBase;
}

@Override
public String tipoContrato() {
    return "Indefinido";
}
```

---

# 8. Clase `EmpleadoVentas`

`EmpleadoVentas` también extiende `Empleado`.

Además de los atributos heredados, posee:

```text
comision
metaVentas
```

El sueldo se calcula como:

```text
sueldo = sueldoBase + comision
```

Y su tipo de contrato es:

```text
"Por comisión"
```

Debe implementar:

```java
@Override
public double calcularSueldo() {
    return sueldoBase + comision;
}

@Override
public String tipoContrato() {
    return "Por comisión";
}
```

La `metaVentas` debe almacenarse como atributo aunque no participe directamente en el cálculo del sueldo indicado en la actividad.

---

# 9. Encapsulamiento

Todos los atributos deben ser `private`.

Ejemplo:

```java
private String rut;
private String nombre;
private int edad;
private double sueldoBase;
private String cargo;
private double comision;
private double metaVentas;
```

El acceso debe realizarse mediante métodos:

```java
get
set
```

La idea del encapsulamiento no se limita solamente a los atributos de las clases.

También se debe aplicar a la colección de empleados.

---

# 10. Clase `GestorEmpleados`

`GestorEmpleados` se encarga de administrar la colección de empleados.

La lista debe ser privada:

```java
private ArrayList<Empleado> empleados;
```

`Main` **no debe acceder directamente a esta lista**.

Debe existir un método para agregar empleados:

```java
public void agregarEmpleado(Empleado empleado)
```

Y un método para listar los empleados:

```java
public void listarEmpleados()
```

La estructura sería:

```text
Main
 │
 │ agregarEmpleado(...)
 ▼
GestorEmpleados
 │
 └── lista privada de Empleado
```

`Main` solamente le pide al gestor que agregue o liste empleados.

---

# 11. Polimorfismo

La lista puede ser de tipo:

```java
ArrayList<Empleado>
```

Aunque `Empleado` sea abstracta, puede almacenar objetos de sus clases hijas:

```java
Empleado empleado1 = new EmpleadoComun(...);
Empleado empleado2 = new EmpleadoVentas(...);
```

Ambos pueden almacenarse en:

```java
ArrayList<Empleado>
```

Esto es posible gracias al polimorfismo.

La variable es de tipo:

```java
Empleado
```

pero el objeto real puede ser:

```text
EmpleadoComun
```

o:

```text
EmpleadoVentas
```

---

# 12. Polimorfismo al calcular el sueldo

Al recorrer la lista no es necesario preguntar qué tipo de empleado es.

Incorrecto:

```java
if (empleado instanceof EmpleadoVentas) {
    // calcular como ventas
} else {
    // calcular como común
}
```

El programa simplemente debe hacer:

```java
empleado.calcularSueldo();
```

Java ejecutará automáticamente la versión correspondiente al objeto real.

Por ejemplo:

```text
Empleado
   │
   ├── EmpleadoComun
   │       └── calcularSueldo()
   │
   └── EmpleadoVentas
           └── calcularSueldo()
```

---

# 13. Polimorfismo al obtener el contrato

Lo mismo ocurre con:

```java
empleado.tipoContrato();
```

No es necesario preguntar:

```text
¿Es empleado común?
¿Es empleado de ventas?
```

Cada objeto resuelve su propio comportamiento.

Por ejemplo:

```java
for (Empleado empleado : empleados) {
    System.out.println(empleado.calcularSueldo());
    System.out.println(empleado.tipoContrato());
}
```

Aquí se utilizan **dos métodos polimórficos**:

```java
calcularSueldo()
tipoContrato()
```

---

# 14. Menú de consola

El programa debe funcionar mediante un menú dentro de un ciclo.

Opciones mínimas:

```text
1. Agregar empleado
2. Listar empleados
3. Salir
```

El menú debe repetirse hasta que el usuario seleccione `3`.

Estructura:

```java
do {
    // Mostrar menú

    switch (opcion) {
        case 1:
            // Agregar empleado
            break;

        case 2:
            // Listar empleados
            break;

        case 3:
            // Salir
            break;
    }

} while (opcion != 3);
```

---

# 15. Opción 1 — Agregar empleado

Primero se debe preguntar qué tipo de empleado se desea crear:

```text
¿Qué tipo de empleado desea agregar?

1. Empleado común
2. Empleado de ventas
```

Luego se solicitan los datos generales:

```text
Nombre
RUT
Edad
Cargo
Sueldo base
```

Si es un empleado de ventas, también:

```text
Comisión
Meta de ventas
```

Finalmente se crea el objeto correspondiente.

Empleado común:

```java
new EmpleadoComun(...)
```

Empleado de ventas:

```java
new EmpleadoVentas(...)
```

Nunca:

```java
new Empleado(...)
```

porque `Empleado` es abstracta.

---

# 16. Entregar el empleado al gestor

Una vez creado el objeto, `Main` no debe agregarlo directamente a una lista.

Debe entregárselo a `GestorEmpleados`:

```java
gestor.agregarEmpleado(empleado);
```

Por ejemplo:

```java
Empleado empleado;

if (tipo == 1) {
    empleado = new EmpleadoComun(...);
} else {
    empleado = new EmpleadoVentas(...);
}

gestor.agregarEmpleado(empleado);
```

Aquí vuelve a aparecer el polimorfismo:

```java
Empleado empleado;
```

puede contener un:

```java
EmpleadoComun
```

o un:

```java
EmpleadoVentas
```

---

# 17. Opción 2 — Listar empleados

`Main` solamente debe pedirle al gestor que realice el listado:

```java
gestor.listarEmpleados();
```

El gestor recorre internamente la lista.

Ejemplo conceptual:

```java
for (Empleado empleado : empleados) {
    System.out.println("Sueldo: " + empleado.calcularSueldo());
    System.out.println("Contrato: " + empleado.tipoContrato());
}
```

No se debe preguntar qué tipo de empleado es.

El polimorfismo se encarga de resolverlo.

---

# 18. Opción 3 — Salir

Cuando el usuario seleccione:

```text
3. Salir
```

se muestra:

```text
Saliendo...
```

y termina el ciclo.

---

# 19. Flujo completo del programa

```text
Inicio
  │
  ▼
Crear GestorEmpleados
  │
  ▼
Mostrar menú
  │
  ├── 1. Agregar empleado
  │       │
  │       ├── Preguntar tipo
  │       │
  │       ├── Pedir datos
  │       │
  │       ├── Crear EmpleadoComun
  │       │       o
  │       │   EmpleadoVentas
  │       │
  │       └── gestor.agregarEmpleado()
  │
  ├── 2. Listar empleados
  │       │
  │       └── gestor.listarEmpleados()
  │               │
  │               ├── calcularSueldo()
  │               │
  │               └── tipoContrato()
  │
  └── 3. Salir
          │
          ▼
         Fin
```

---

# 20. Esqueleto de `Main`

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        GestorEmpleados gestor = new GestorEmpleados();
        Scanner sc = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("1. Agregar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Salir");

            opcion = sc.nextInt();

            switch (opcion) {

                case 1:
                    // Preguntar tipo
                    // Pedir datos
                    // Crear EmpleadoComun o EmpleadoVentas
                    // Entregar empleado al gestor
                    break;

                case 2:
                    gestor.listarEmpleados();
                    break;

                case 3:
                    System.out.println("Saliendo...");
                    break;
            }

        } while (opcion != 3);
    }
}
```

---

# 21. Conceptos que debe demostrar la actividad

| Concepto                     | Aplicación                                 |
| ---------------------------- | ------------------------------------------ |
| Encapsulamiento              | Atributos `private` y métodos `get`/`set`  |
| Herencia                     | `Empleado extends Usuario`                 |
| Herencia                     | `EmpleadoComun extends Empleado`           |
| Herencia                     | `EmpleadoVentas extends Empleado`          |
| Interfaz                     | `Empleado implements Pagable`              |
| Clase abstracta              | `Empleado`                                 |
| Método abstracto             | `tipoContrato()`                           |
| Polimorfismo                 | `Empleado` puede almacenar hijos concretos |
| Polimorfismo                 | `calcularSueldo()`                         |
| Polimorfismo                 | `tipoContrato()`                           |
| Encapsulamiento de colección | Lista privada dentro de `GestorEmpleados`  |
| Menú                         | `do-while` + `switch`                      |

---

# 22. Checklist de implementación

* [ ] Crear interfaz `Pagable`
* [ ] Crear método `calcularSueldo()`
* [ ] Crear clase `Usuario`
* [ ] Agregar `rut`, `nombre` y `edad`
* [ ] Aplicar encapsulamiento
* [ ] Crear clase abstracta `Empleado`
* [ ] Hacer que `Empleado` extienda `Usuario`
* [ ] Hacer que `Empleado` implemente `Pagable`
* [ ] Agregar `sueldoBase`
* [ ] Agregar `cargo`
* [ ] Declarar `tipoContrato()` como abstracto
* [ ] Crear `EmpleadoComun`
* [ ] Implementar `calcularSueldo()`
* [ ] Implementar `tipoContrato()`
* [ ] Crear `EmpleadoVentas`
* [ ] Agregar `comision`
* [ ] Agregar `metaVentas`
* [ ] Implementar `calcularSueldo()`
* [ ] Implementar `tipoContrato()`
* [ ] Crear `GestorEmpleados`
* [ ] Mantener la lista como `private`
* [ ] Crear método para agregar empleados
* [ ] Crear método para listar empleados
* [ ] Crear menú en `Main`
* [ ] Implementar opción para agregar
* [ ] Implementar opción para listar
* [ ] Implementar opción para salir
* [ ] Verificar que `Main` no acceda directamente a la lista
* [ ] Verificar que el listado utilice `calcularSueldo()` y `tipoContrato()` mediante polimorfismo

---

# 23. Importante

El objetivo principal no es solamente conseguir que el programa funcione.

La implementación debe demostrar correctamente la relación entre:

```text
Interfaz
    ↓
Pagable
```

```text
Clase base
    ↓
Usuario
```

```text
Clase abstracta
    ↓
Empleado
```

```text
Clases concretas
    ↓
EmpleadoComun
EmpleadoVentas
```

y el uso del polimorfismo:

```java
Empleado empleado;
```

puede representar diferentes tipos de empleados sin que `Main` tenga que conocer los detalles de cada uno.

## Migración a Kotlin

La conversión de este programa a Kotlin corresponde a una actividad posterior.

Por ahora, el objetivo es implementar y comprender correctamente la versión en **Java**.
