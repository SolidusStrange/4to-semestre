 Taller de Base de Datos — Repaso PL/SQL

## Prerrequisitos

Antes de comenzar con PL/SQL, es necesario manejar los siguientes conceptos de SQL:

### JOIN

- `INNER JOIN`
- `LEFT JOIN`
- `RIGHT JOIN`
- `FULL JOIN`
- `JOIN` simple
    

### Agrupación y ordenamiento

- `GROUP BY`
- `ORDER BY`
    
- Ordenamiento utilizando múltiples criterios:
    
    - 2 criterios
        
    - 3 criterios
        
    - 5 criterios
        

### Funciones

- `NVL`
- `NVL2`
    

---

# Clase 1 — Bloques anónimos PL/SQL

## ¿Qué es PL/SQL?

**PL/SQL** es el lenguaje procedimental utilizado por Oracle para extender SQL, permitiendo incorporar lógica de programación como:

- Variables
- Condicionales
- Ciclos
- Operaciones
- Procesamiento de consultas
- Manejo de bloques de código

## Bloques anónimos

Un **bloque** es una estructura de código que permite agrupar instrucciones PL/SQL.

Se denomina **anónimo** porque:

- No tiene un nombre o identificador.
- No queda almacenado en la base de datos.
- Se ejecuta directamente.
### Estructura general

```sql
DECLARE
    -- Declaración de variables (opcional)

BEGIN
    -- Instrucciones y lógica del programa

END;
```

### Partes del bloque

#### `DECLARE`

Se utiliza para declarar variables.

Es opcional. Si el bloque no necesita variables, puede omitirse.

```sql
DECLARE
    v_num NUMBER(2) := 10;
```

#### `BEGIN`

Contiene las instrucciones que se ejecutarán.

Dentro de esta sección se pueden utilizar estructuras como:

- `IF`
- `CASE`
- `FOR`
- `WHILE`
- `LOOP`
- `FETCH`
    

```sql
BEGIN
    DBMS_OUTPUT.PUT_LINE('Hola');
```

#### `END;`

Indica el final del bloque.

```sql
END;
```
---

# Variables

Las variables permiten almacenar datos temporalmente durante la ejecución del bloque.

### Ejemplo

```sql
DECLARE
    v_num    NUMBER(2) := 10;
    v_cadena VARCHAR2(10) := 'Mauricio';
    v_fecha  DATE := SYSDATE;

BEGIN
    DBMS_OUTPUT.PUT_LINE('El valor de v_num es: ' || v_num);
    DBMS_OUTPUT.PUT_LINE('El valor de v_cadena es: ' || v_cadena);
    DBMS_OUTPUT.PUT_LINE('El valor de v_fecha es: ' || v_fecha);

END;
```

### Tipos utilizados

```text
NUMBER
VARCHAR2
DATE
```

El operador `:=` se utiliza para asignar un valor a una variable.

```sql
v_num := 10;
```

---

# Variable de sustitución

Una **variable de sustitución** permite solicitar un dato al usuario durante la ejecución del bloque.

Se utiliza `&` seguido del nombre de la variable:

```sql
&nombre_variable
```

### Ejemplo

```sql
DECLARE
    v_op1 NUMBER(2) := &operando1;
    v_op2 NUMBER(2) := &operando2;
    v_sum NUMBER(3);

BEGIN
    v_sum := v_op1 + v_op2;

    DBMS_OUTPUT.PUT_LINE(
        v_op1 || ' + ' || v_op2 || ' = ' || v_sum
    );

END;
```

Al ejecutar el bloque, Oracle solicitará los valores de:

```text
operando1
operando2
```

---

# DBMS_OUTPUT

`DBMS_OUTPUT.PUT_LINE()` permite mostrar información en la salida de Oracle.

```sql
DBMS_OUTPUT.PUT_LINE('Hola mundo');
```

También permite concatenar variables utilizando `||`:

```sql
DBMS_OUTPUT.PUT_LINE('El valor es: ' || v_num);
```

### Ejemplo básico

```sql
BEGIN

    DBMS_OUTPUT.PUT_LINE('Bienvenidos a Taller de Base de Datos');

END;
```

En este caso no es necesario utilizar `DECLARE`, porque no se están declarando variables.

---

# Operaciones matemáticas

Las variables numéricas pueden utilizarse para realizar operaciones matemáticas.

```sql
DECLARE
    v_n1 NUMBER(2) := 10;
    v_n2 NUMBER(2) := 2;

BEGIN

    DBMS_OUTPUT.PUT_LINE('Suma: ' || (v_n1 + v_n2));
    DBMS_OUTPUT.PUT_LINE('Resta: ' || (v_n1 - v_n2));
    DBMS_OUTPUT.PUT_LINE('Multiplicación: ' || (v_n1 * v_n2));
    DBMS_OUTPUT.PUT_LINE('División: ' || (v_n1 / v_n2));
    DBMS_OUTPUT.PUT_LINE('Potencia: ' || (v_n1 ** v_n2));

END;
```

Operadores:

|Operación|Operador|
|---|---|
|Suma|`+`|
|Resta|`-`|
|Multiplicación|`*`|
|División|`/`|
|Potencia|`**`|

---

# IF

`IF` permite ejecutar diferentes instrucciones dependiendo de una condición.

### Ejemplo: determinar el número mayor

```sql
DECLARE
    v_n1 NUMBER(2) := 1;
    v_n2 NUMBER(2) := 2;
    v_n3 NUMBER(2) := 3;

BEGIN

    IF v_n1 >= v_n2 AND v_n1 >= v_n3 THEN

        DBMS_OUTPUT.PUT_LINE(v_n1 || ' es el mayor');

    ELSIF v_n2 >= v_n3 THEN

        DBMS_OUTPUT.PUT_LINE(v_n2 || ' es el mayor');

    ELSE

        DBMS_OUTPUT.PUT_LINE(v_n3 || ' es el mayor');

    END IF;

END;
```

### Estructura

```sql
IF condicion THEN

    -- instrucciones

ELSIF otra_condicion THEN

    -- instrucciones

ELSE

    -- instrucciones

END IF;
```

---

# CASE

`CASE` permite seleccionar una acción dependiendo del valor de una expresión.

### Ejemplo: determinar el día de la semana

```sql
DECLARE
    v_dia NUMBER(1) := &dia;

BEGIN

    CASE v_dia

        WHEN 1 THEN
            DBMS_OUTPUT.PUT_LINE('Lunes');

        WHEN 2 THEN
            DBMS_OUTPUT.PUT_LINE('Martes');

        WHEN 3 THEN
            DBMS_OUTPUT.PUT_LINE('Miércoles');

        WHEN 4 THEN
            DBMS_OUTPUT.PUT_LINE('Jueves');

        WHEN 5 THEN
            DBMS_OUTPUT.PUT_LINE('Viernes');

        WHEN 6 THEN
            DBMS_OUTPUT.PUT_LINE('Sábado');

        WHEN 7 THEN
            DBMS_OUTPUT.PUT_LINE('Domingo');

        ELSE
            DBMS_OUTPUT.PUT_LINE(
                'Debe ingresar un valor entre 1 y 7'
            );

    END CASE;

END;
```

### Estructura

```sql
CASE variable

    WHEN valor1 THEN
        -- instrucciones

    WHEN valor2 THEN
        -- instrucciones

    ELSE
        -- instrucciones

END CASE;
```

---

# Ejercicio — Ajuste de sueldo

## Enunciado

Crear un bloque anónimo que:

1. Tenga una variable de texto para almacenar el nombre de una persona.
    
2. Tenga una variable numérica para almacenar su sueldo.
    
3. Calcule un aumento del **12%**.
    
4. Muestre el sueldo ajustado.
    

### Solución

```sql
DECLARE
    v_nombre          VARCHAR2(50) := 'Juan';
    v_sueldo          NUMBER(10) := 539000;
    v_sueldo_ajustado NUMBER(10);

BEGIN

    v_sueldo_ajustado := v_sueldo * 1.12;

    DBMS_OUTPUT.PUT_LINE(
        'El sueldo ajustado de ' ||
        v_nombre ||
        ' es: ' ||
        v_sueldo_ajustado
    );

END;
```

La operación:

```text
sueldo × 1.12
```

representa el sueldo original más un aumento del 12%.

---

# Ciclos

PL/SQL permite repetir instrucciones utilizando diferentes tipos de ciclos:

- `WHILE`
    
- `FOR`
    
- `LOOP`
    

---

# WHILE

`WHILE` repite un bloque de instrucciones mientras una condición sea verdadera.

### Ejemplo

```sql
DECLARE
    v_i NUMBER(2) := 1;

BEGIN

    DBMS_OUTPUT.PUT_LINE('WHILE');

    WHILE v_i <= 10 LOOP

        DBMS_OUTPUT.PUT_LINE(v_i);

        v_i := v_i + 2;

    END LOOP;

END;
```

### Estructura

```sql
WHILE condicion LOOP

    -- instrucciones

END LOOP;
```

Es importante modificar la variable utilizada en la condición para evitar un ciclo infinito.

---

# FOR

`FOR` permite repetir instrucciones durante un rango determinado.

### Ejemplo

```sql
DECLARE
    v_i NUMBER(2) := 1;

BEGIN

    DBMS_OUTPUT.PUT_LINE('FOR');

    FOR v_i IN 1..20 LOOP

        DBMS_OUTPUT.PUT_LINE(v_i);

    END LOOP;

END;
```

En este caso se recorren los valores desde `1` hasta `20`.

### Estructura

```sql
FOR variable IN inicio..fin LOOP

    -- instrucciones

END LOOP;
```

En un `FOR` de este tipo no es necesario incrementar manualmente la variable.

---

# LOOP

`LOOP` crea un ciclo que continúa ejecutándose hasta que se indique explícitamente cuándo salir.

### Ejemplo

```sql
DECLARE
    v_i NUMBER(2) := 1;

BEGIN

    DBMS_OUTPUT.PUT_LINE('LOOP');

    LOOP

        DBMS_OUTPUT.PUT_LINE(v_i);

        EXIT WHEN v_i = 10;

        v_i := v_i + 1;

    END LOOP;

END;
```

### Estructura

```sql
LOOP

    -- instrucciones

    EXIT WHEN condicion;

END LOOP;
```

`EXIT WHEN` permite establecer la condición de salida del ciclo.

---

# SQL Developer

**SQL Developer** es un cliente que permite conectarse a una base de datos Oracle y enviarle instrucciones SQL y PL/SQL.

Conceptualmente:

```text
Usuario
   ↓
SQL Developer
   ↓
Oracle Database
```

### Oracle On-Premise

Oracle está instalado físicamente en una infraestructura propia.

Ejemplo visto en clases:

```text
Oracle 21c
```

### Oracle Cloud

La base de datos se encuentra alojada en la nube.

Para algunas conexiones se requiere utilizar un **Wallet**, que contiene información y credenciales necesarias para establecer la conexión de forma segura.

---

# MongoDB

MongoDB es una base de datos NoSQL.

Para trabajar con MongoDB se puede utilizar:

**MongoDB Compass**

Compass funciona como cliente gráfico para conectarse y administrar bases de datos MongoDB.

---

# Lenguajes SQL

## DDL — Data Definition Language

Se utiliza para definir y modificar la estructura de la base de datos.

Principales comandos:

```sql
CREATE
DROP
ALTER
```

Ejemplo:

```sql
CREATE TABLE empleados (
    id NUMBER,
    nombre VARCHAR2(50)
);
```

---

## DML — Data Manipulation Language

Se utiliza para trabajar con los datos almacenados.

Principales comandos:

```sql
INSERT
SELECT
UPDATE
```

Ejemplos:

```sql
INSERT INTO empleados
VALUES (1, 'Juan');

SELECT *
FROM empleados;

UPDATE empleados
SET nombre = 'Pedro'
WHERE id = 1;
```

---

# Resumen de la Clase 1

```text
PL/SQL
│
├── Bloques anónimos
│   ├── DECLARE
│   ├── BEGIN
│   └── END
│
├── Variables
│   ├── NUMBER
│   ├── VARCHAR2
│   └── DATE
│
├── Entrada de datos
│   └── Variables de sustitución (&)
│
├── Salida
│   └── DBMS_OUTPUT.PUT_LINE()
│
├── Condicionales
│   ├── IF / ELSIF / ELSE
│   └── CASE
│
├── Ciclos
│   ├── WHILE
│   ├── FOR
│   └── LOOP / EXIT WHEN
│
└── SQL
    ├── DDL
    │   ├── CREATE
    │   ├── DROP
    │   └── ALTER
    │
    └── DML
        ├── INSERT
        ├── SELECT
        └── UPDATE
```