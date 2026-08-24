## 1. ¿Qué es un RECORD?

Un `RECORD` permite agrupar una o varias columnas/variables relacionadas dentro de una sola estructura.

Por ejemplo, para representar una persona:

| RUT | NOMBRE | AP_PATERNO | AP_MATERNO |
|---|---|---|---|
| 12345678-9 | Jorge | Pérez | González |

En PL/SQL podemos crear un `RECORD` que contenga estos atributos.

### Analogía con Java

En Java podríamos pensar en algo similar a crear un objeto:

```java
Persona persona = new Persona();

persona.rut = "12345678-9";
persona.nombre = "Jorge Perez";
```

En PL/SQL:

```SQL
Persona = Persona_Rec
```

Y posteriormente asignamos valores a sus atributos:

```SQL
persona.rut := '12345678-9';
persona.nombre := 'Jorge Perez';
```

> La idea principal es que `RECORD` permite trabajar con varias variables relacionadas como una sola estructura.

## 2. ¿Dónde se declara un RECORD?

Los tipos `RECORD` se declaran normalmente dentro de la sección `DECLARE` de un bloque PL/SQL.

La estructura general es:

```SQL
DECLARE
    TYPE nombre_record IS RECORD(
        atributo1 tipo_dato,
        atributo2 tipo_dato,
        atributo3 tipo_dato
    );

    variable nombre_record;
BEGIN
    -- Asignación de valores
END;
```

Hay que diferenciar entre:

- `nombre_record`: el tipo de RECORD.
- `variable`: una variable que utiliza ese tipo.

Por ejemplo:

```SQL
TYPE empleado_record IS RECORD(
    id NUMBER,
    nombre VARCHAR2(100),
    sueldo NUMBER
);

empleado empleado_record;
```

Aquí:
- `empleado_record` → es el tipo.
- `empleado` → es la variable que utiliza ese tipo.

# 3. Formas de definir un RECORD

Existen distintas formas de definir los atributos de un `RECORD`.

### 3.1. Definiendo los tipos manualmente
Podemos especificar directamente el tipo de cada atributo:

```SQL
TYPE empleado_record IS RECORD(
    id NUMBER,
    nombre VARCHAR2(100),
    sueldo NUMBER
);
```

En este caso, nosotros definimos manualmente los tipos de datos.

---

### 3.2. Utilizando `%TYPE`
`%TYPE` permite que una variable o atributo herede el tipo de dato de una columna existente.

Por ejemplo:

```SQL
TYPE EmployeRec IS RECORD(
    emp_id emp.empno%TYPE,
    emp_name emp.ename%TYPE,
    emp_sal emp.sal%TYPE
);
```

Aquí:

```SQL
emp.empno%TYPE
```

significa:

> Utilizar el mismo tipo de dato que tiene la columna `empno` de la tabla `emp`.

Lo mismo ocurre con:

```SQL
emp.ename%TYPE
emp.sal%TYPE
```

### Ventaja
Si posteriormente cambia el tipo de la columna en la tabla, el `RECORD` se adapta automáticamente.

### 3.3. Utilizando `%ROWTYPE`
`%ROWTYPE` permite crear una estructura que representa una fila completa de una tabla.

Por ejemplo:

```SQL
DECLARE
    v_employee emp%ROWTYPE;
BEGIN
    ...
END;
```

En este caso, `v_employee` tendrá atributos correspondientes a las columnas de `emp`:

```SQL
v_employee.empno
v_employee.ename
v_employee.sal
```

### Diferencia entre `%TYPE` y `%ROWTYPE`

`%TYPE`:

> Hereda el tipo de dato de una columna específica.

```SQL
v_rut persona.rut%TYPE;
```

`%ROWTYPE`:

> Hereda la estructura completa de una fila de una tabla.

```SQL
v_employee emp%ROWTYPE;
```

# 4. RECORD temporal

Podemos crear un `RECORD` completamente independiente de una tabla.

Por ejemplo:

```SQL
DECLARE
    TYPE empleado_record IS RECORD(
        id NUMBER,
        nombre VARCHAR2(100),
        sueldo NUMBER
    );

    empleado empleado_record;

BEGIN
    empleado.id := 1;
    empleado.nombre := 'Mauricio';
    empleado.sueldo := 300000;

    DBMS_OUTPUT.PUT_LINE(
        'Id: ' || empleado.id ||
        ' Nombre: ' || empleado.nombre ||
        ' Sueldo: ' || empleado.sueldo
    );
END;
```

## Explicación

Primero creamos el tipo:

```SQL
TYPE empleado_record IS RECORD(
    id NUMBER,
    nombre VARCHAR2(100),
    sueldo NUMBER
);
```

Después creamos una variable de ese tipo:

```SQL
empleado empleado_record;
```

Finalmente asignamos valores a sus atributos:

```SQL
empleado.id := 1;
empleado.nombre := 'Mauricio';
empleado.sueldo := 300000;
```

Para acceder a un atributo utilizamos:

```SQL
variable.atributo
```

Por ejemplo:

```SQL
empleado.nombre
```

# 5. RECORD utilizando `%TYPE`

Primero creamos una tabla:

```SQL
CREATE TABLE emp(
    empno NUMBER PRIMARY KEY,
    ename VARCHAR2(50) NOT NULL,
    sal NUMBER NOT NULL
);
```

Ahora podemos crear un `RECORD` utilizando los tipos de datos de las columnas:

```SQL
DECLARE
    TYPE EmployeRec IS RECORD(
        emp_id emp.empno%TYPE,
        emp_name emp.ename%TYPE,
        emp_sal emp.sal%TYPE
    );

    v_employee EmployeRec;

BEGIN
    v_employee.emp_id := 2;
    v_employee.emp_name := 'Juan';
    v_employee.emp_sal := 999999;

    DBMS_OUTPUT.PUT_LINE(
        'El nombre del empleado es: ' || v_employee.emp_name ||
        ', su ID es: ' || v_employee.emp_id ||
        ', su salario es: ' || v_employee.emp_sal
    );
END;
```

Aquí los atributos del `RECORD` toman sus tipos directamente desde la tabla `emp`.

```SQL
empno → emp.empno%TYPE
ename → emp.ename%TYPE
sal   → emp.sal%TYPE
```

# 6. Obtener datos de una tabla utilizando un RECORD

También podemos utilizar un `RECORD` para almacenar el resultado de una consulta.

Primero insertamos algunos datos:

```SQL
INSERT INTO EMP(EMPNO, ENAME, SAL)
VALUES(1, 'Pedro Rodriguez', 500000);

INSERT INTO EMP(EMPNO, ENAME, SAL)
VALUES(2, 'Maria Perez', 500000);
```

Luego podemos obtener una fila mediante `SELECT INTO`:

```SQL
DECLARE
    TYPE EmployeRec IS RECORD(
        emp_id emp.empno%TYPE,
        emp_name emp.ename%TYPE,
        emp_sal emp.sal%TYPE
    );

    v_employee EmployeRec;

BEGIN

    SELECT EMPNO, ENAME, SAL
    INTO v_employee
    FROM EMP
    WHERE empno = 2;

    DBMS_OUTPUT.PUT_LINE(
        'El nombre del empleado es: ' || v_employee.emp_name ||
        ', su ID es: ' || v_employee.emp_id ||
        ', su salario es: ' || v_employee.emp_sal
    );

END;
```

## ¿Qué ocurre aquí?

La consulta:

```SQL
SELECT EMPNO, ENAME, SAL
INTO v_employee
FROM EMP
WHERE empno = 2;
```

obtiene una fila de la tabla y almacena sus valores dentro del `RECORD`.

La correspondencia es:

```sql
EMPNO → v_employee.emp_id
ENAME → v_employee.emp_name
SAL   → v_employee.emp_sal
```

Después podemos acceder a cada atributo:

```sql
v_employee.emp_id
v_employee.emp_name
v_employee.emp_sal
```

### Importante

El `SELECT INTO` debe devolver exactamente una fila cuando se utiliza de esta forma.

Si no encuentra ninguna fila, se produce:

```sql
NO_DATA_FOUND
```

Si encuentra más de una fila, se produce:

```sql
TOO_MANY_ROWS
```

# 7. No siempre necesitamos un RECORD

Si solamente necesitamos obtener un valor, no es necesario crear un `RECORD`.

Por ejemplo, si queremos conocer la cantidad de empleados:

```sql
DECLARE
    v_employee_cantidad NUMBER := 0;

BEGIN

    SELECT COUNT(*)
    INTO v_employee_cantidad
    FROM EMP;

    DBMS_OUTPUT.PUT_LINE(
        'La cantidad de registros es: ' || v_employee_cantidad
    );

END;
```

Aquí solamente necesitamos una variable:

```sql
v_employee_cantidad NUMBER;
```

El resultado de:

```sql
SELECT COUNT(*)
FROM EMP;
```

se almacena directamente en:

```sql
v_employee_cantidad
```

# 8. RECORD vs variable simple

### Variable simple

Cuando necesitamos un solo valor:

```sql
DECLARE
    v_cantidad NUMBER;

BEGIN
    SELECT COUNT(*)
    INTO v_cantidad
    FROM EMP;
END;
```

### RECORD

Cuando necesitamos almacenar varios valores relacionados:

```SQL
DECLARE
    TYPE empleado_record IS RECORD(
        id NUMBER,
        nombre VARCHAR2(100),
        sueldo NUMBER
    );

    empleado empleado_record;
END;
```

La diferencia conceptual es:

```SQL
Variable
    ↓
Un valor

RECORD
    ↓
Varios atributos relacionados
```

# 9. Conceptos relacionados

En el trabajo con PL/SQL y tablas aparecen varios conceptos que conviene diferenciar:

### DDL — Data Definition Language

Se utiliza para definir o modificar la estructura de la base de datos.

Ejemplos:

```SQL
CREATE TABLE
ALTER TABLE
DROP TABLE
```

### DML — Data Manipulation Language

Se utiliza para manipular los datos almacenados.

Ejemplos:

```SQL
INSERT
UPDATE
DELETE
```

### Control de transacciones

Permite controlar las operaciones realizadas sobre los datos.

Ejemplos:

```SQL
COMMIT;
ROLLBACK;
```

# 10. Resumen

|Concepto|Función|
|---|---|
|`RECORD`|Agrupa varios atributos relacionados|
|`%TYPE`|Hereda el tipo de una columna o variable|
|`%ROWTYPE`|Hereda la estructura completa de una fila|
|`SELECT INTO`|Guarda el resultado de una consulta en una variable o RECORD|
|`COUNT(*)`|Cuenta la cantidad de registros|
|`COMMIT`|Confirma una transacción|
|`ROLLBACK`|Revierte una transacción|
|DDL|Define la estructura de la BD|
|DML|Manipula los datos de la BD|

## Idea clave

Un `RECORD` puede entenderse como una estructura que agrupa varios valores relacionados.

```SQL
RECORD
│
├── atributo 1
├── atributo 2
└── atributo 3
```

Y una variable puede utilizar ese tipo:

```SQL
TYPE empleado_record IS RECORD(...);

empleado empleado_record;
```

Después podemos trabajar con sus atributos:

```SQL
empleado.id
empleado.nombre
empleado.sueldo
```

Si los atributos corresponden a una tabla existente, podemos evitar definir manualmente sus tipos utilizando `%TYPE` o utilizar `%ROWTYPE` para representar una fila completa.