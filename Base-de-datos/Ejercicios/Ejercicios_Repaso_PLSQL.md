# Ejercicios de Repaso — PL/SQL

> Ejercicios progresivos basados en las clases de la unidad de PL/SQL.
>
> **Modalidad:** resolver primero sin mirar soluciones. La idea es avanzar desde ejercicios simples hacia problemas que combinen varios conceptos.

---

## Clase 1 — Bloques anónimos

### Ejercicio 1 — Información básica

Crea un bloque anónimo que tenga:

- Una variable `v_nombre` de tipo `VARCHAR2`.
- Una variable `v_edad` de tipo `NUMBER`.
- Una variable `v_ciudad` de tipo `VARCHAR2`.

Asigna valores directamente a las variables y muestra mediante `DBMS_OUTPUT.PUT_LINE`:

```text
Nombre: Juan
Edad: 25
Ciudad: Santiago
```

**Practica:** `DECLARE`, variables, asignación y `DBMS_OUTPUT`.

---

### Ejercicio 2 — Calculadora con condición

Crea un bloque anónimo que solicite mediante variables de sustitución:

```text
Número 1:
Número 2:
```

El programa debe calcular:

- Suma.
- Resta.
- Multiplicación.
- División.

Además, debe determinar cuál de los dos números es mayor.

Ejemplo:

```text
Número 1: 20
Número 2: 5

Suma: 25
Resta: 15
Multiplicación: 100
División: 4
El número mayor es: 20
```

Debes utilizar al menos un `IF / ELSE`.

**Practica:** variables de sustitución, operaciones matemáticas, `IF` y salida por consola.

---

### Ejercicio 3 — Sistema de evaluación

Crea un bloque anónimo que solicite una nota entre `1.0` y `7.0`.

El programa debe indicar:

```text
1.0 - 3.9 → Reprobado
4.0 - 4.9 → Aprobado
5.0 - 5.9 → Bueno
6.0 - 7.0 → Excelente
```

Además:

- Si la nota está fuera del rango `1.0 - 7.0`, mostrar `"Nota inválida"`.
- Utiliza `IF / ELSIF / ELSE`.
- Luego realiza una segunda versión utilizando `CASE`.

**Practica:** entrada de datos, condiciones múltiples, `IF` y `CASE`.

---

# Clase 2 — RECORD

### Ejercicio 4 — RECORD de estudiante

Crea un `RECORD` llamado `estudiante_record` que tenga:

```text
id
nombre
edad
nota
```

Crea una variable de ese tipo y asígnale valores.

Después muestra:

```text
ID: 1
Nombre: Carlos
Edad: 22
Nota: 6.4
```

Finalmente utiliza un `IF` para determinar si está aprobado.

**Practica:** `TYPE ... IS RECORD`, variables `RECORD`, atributos y acceso mediante `.`.

---

### Ejercicio 5 — RECORD + `%TYPE`

Supongamos que tienes una tabla:

```text
EMPLEADO
---------
ID_EMPLEADO
NOMBRE
SUELDO
```

Crea un `RECORD` cuyos atributos utilicen `%TYPE`:

```text
id       → EMPLEADO.ID_EMPLEADO%TYPE
nombre   → EMPLEADO.NOMBRE%TYPE
sueldo   → EMPLEADO.SUELDO%TYPE
```

Luego asigna valores manualmente y muestra la información.

Después calcula un aumento del `10%` del sueldo y muestra:

```text
Empleado: Pedro
Sueldo actual: 600000
Sueldo con aumento: 660000
```

**Objetivo adicional:** comprender la diferencia entre declarar:

```sql
NUMBER
```

y:

```sql
EMPLEADO.SUELDO%TYPE
```

**Practica:** `RECORD`, `%TYPE`, acceso a atributos y operaciones.

---

### Ejercicio 6 — `%ROWTYPE` + `SELECT INTO`

Utiliza la tabla `EMPLEADO`.

Crea:

```sql
v_empleado EMPLEADO%ROWTYPE;
```

Solicita mediante `&` un `ID_EMPLEADO`.

Luego realiza un `SELECT ... INTO ...` para obtener ese empleado completo.

Finalmente muestra sus datos.

Agrega una condición:

```text
Si sueldo >= 700000
    → "Empleado con sueldo alto"

Si no
    → "Empleado con sueldo estándar"
```

**Practica:** `SELECT INTO`, `%ROWTYPE`, acceso a columnas mediante `.`, `IF` y `DBMS_OUTPUT`.

---

# Clase 3 — Cursores

## Ejercicio 7 — Cursor básico

Crea un cursor que consulte todos los empleados:

```sql
SELECT *
FROM EMPLEADO;
```

Utiliza:

```sql
EMPLEADO%ROWTYPE
```

para almacenar cada fila.

El cursor debe:

1. Declararse.
2. Abrirse.
3. Realizar un `FETCH`.
4. Comprobar `%NOTFOUND`.
5. Mostrar el nombre del empleado.
6. Repetirse hasta terminar los registros.
7. Cerrarse.

La salida podría ser:

```text
Empleados:

Juan
Pedro
Ana
Carlos
Maria
```

No agregues todavía cálculos ni condiciones.

**Objetivo:** dominar la estructura básica de un cursor:

```text
DECLARE
    CURSOR
    RECORD

BEGIN
    OPEN
    LOOP
        FETCH
        EXIT WHEN
        ...
    END LOOP;
    CLOSE;
END;
```

---

## Ejercicio 8 — Cursor + cálculo

Utiliza un cursor sobre `EMPLEADO`.

Por cada empleado debes mostrar:

```text
Empleado: Juan
Sueldo actual: 500000
Sueldo con aumento: 550000
```

El aumento será del `10%`.

Además, si el sueldo original es mayor o igual a `700000`, muestra:

```text
Categoría: Sueldo alto
```

En caso contrario:

```text
Categoría: Sueldo estándar
```

Debes combinar:

```text
CURSOR
%ROWTYPE
FETCH
%NOTFOUND
IF
operaciones matemáticas
DBMS_OUTPUT
```

---

## Ejercicio 9 — Cursor + contador + acumulador

Utiliza un cursor para recorrer todos los empleados.

El programa debe calcular:

1. Cantidad total de empleados.
2. Suma de todos los sueldos.
3. Sueldo promedio.
4. Cantidad de empleados con sueldo mayor o igual a `700000`.
5. Empleado con el sueldo más alto.

Al final debería mostrar algo parecido a:

```text
===== RESUMEN =====

Cantidad de empleados: 8
Suma de sueldos: 5.800.000
Sueldo promedio: 725.000
Empleados con sueldo alto: 4

Empleado con mayor sueldo:
Ana
Sueldo: 950.000
```

Para resolverlo necesitarás pensar en variables auxiliares como:

```text
contador
acumulador
promedio
mayor_sueldo
nombre_mayor_sueldo
contador_sueldo_alto
```

Estas variables deberán actualizarse mientras el cursor recorre cada fila.

**Objetivo:** comprender qué ocurre con cada registro durante la ejecución de un cursor y combinar cursores con lógica de programación.

---

# Clase 3 — VARRAY

## Ejercicio 10 — VARRAY básico

Crea un `VARRAY` con capacidad máxima para 5 números.

Inicialízalo con:

```text
10
20
30
40
50
```

Recorre el VARRAY utilizando un `FOR` y muestra:

```text
Elemento 1: 10
Elemento 2: 20
Elemento 3: 30
Elemento 4: 40
Elemento 5: 50
```

**Practica:** declaración de `VARRAY`, constructor, índices y `FOR`.

---

## Ejercicio 11 — VARRAY de nombres

Crea un `VARRAY` que pueda almacenar hasta 5 nombres.

Inicialízalo con:

```text
Juan
Pedro
Ana
Carlos
Maria
```

Recórrelo y muestra solamente los nombres que tengan más de 4 caracteres.

Resultado esperado:

```text
Pedro
Carlos
Maria
```

Debes combinar:

```text
VARRAY
FOR
IF
LENGTH()
```

---

## Ejercicio 12 — VARRAY + procesamiento

Crea un `VARRAY` de números con capacidad máxima para 10 elementos.

Comienza con:

```text
10
20
30
40
```

Luego utiliza `EXTEND` para agregar nuevos elementos y termina teniendo:

```text
10
20
30
40
50
60
```

Después recorre el VARRAY y calcula:

- Suma total.
- Promedio.
- Número mayor.
- Número menor.

Salida esperada:

```text
Suma: 210
Promedio: 35
Mayor: 60
Menor: 10
```

**Objetivo:** comprender la diferencia entre:

- Capacidad máxima del `VARRAY`.
- Cantidad actual de elementos.
- `EXTEND`.
- Acceso mediante índices.
- Recorrido de elementos.

---

# Orden recomendado

Se recomienda resolver los ejercicios en este orden:

```text
CLASE 1
│
├── Ejercicio 1 → Fácil
├── Ejercicio 2 → Medio
└── Ejercicio 3 → Medio+
        │
        ▼
CLASE 2 — RECORD
│
├── Ejercicio 4 → Fácil
├── Ejercicio 5 → Medio
└── Ejercicio 6 → Medio+
        │
        ▼
CLASE 3 — CURSORES
│
├── Ejercicio 7 → Fácil
├── Ejercicio 8 → Medio
└── Ejercicio 9 → Difícil
        │
        ▼
CLASE 3 — VARRAY
│
├── Ejercicio 10 → Fácil
├── Ejercicio 11 → Medio
└── Ejercicio 12 → Difícil
```

# Forma de trabajo

La idea es resolver cada ejercicio sin mirar una solución completa.

Cuando haya dudas:

1. Intentar plantear primero la estructura.
2. Escribir el código.
3. Ejecutarlo en Oracle.
4. Revisar el error si aparece.
5. Compartir el código para revisarlo.
6. Corregirlo entendiendo por qué falló, en lugar de reemplazarlo directamente por una solución.

El objetivo no es solamente conseguir que el código funcione, sino poder explicar qué hace cada parte del bloque PL/SQL.

---

## Conceptos que se repasan

| Clase | Conceptos |
|---|---|
| Clase 1 | Bloques anónimos, variables, `DBMS_OUTPUT`, variables de sustitución, operaciones, `IF`, `CASE`, ciclos |
| Clase 2 | `RECORD`, `%TYPE`, `%ROWTYPE`, atributos, `SELECT INTO` |
| Clase 3 | Cursores explícitos, `OPEN`, `FETCH`, `%NOTFOUND`, `CLOSE`, `%ROWTYPE` |
| Clase 3 | `VARRAY`, constructor, índices, `FOR`, `EXTEND`, procesamiento |

## Progresión general

```text
Variables
   ↓
Condiciones
   ↓
RECORD
   ↓
%TYPE
   ↓
%ROWTYPE
   ↓
SELECT INTO
   ↓
Cursores
   ↓
Recorrer múltiples filas
   ↓
Contadores y acumuladores
   ↓
VARRAY
   ↓
Procesamiento de estructuras
```
