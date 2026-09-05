# Base de Datos --- PL/SQL: Cursores explícitos y parametrizados

> \[!abstract\] Objetivo Comprender el uso de cursores explícitos y
> cursores parametrizados en PL/SQL, recorriendo resultados fila por
> fila y utilizando parámetros para filtrar los datos que devuelve un
> cursor.

> \[!info\] Cómo usar estos apuntes Estos apuntes siguen el contenido de
> los archivos trabajados en la clase 4. Se mantiene la estructura y los
> ejemplos de la clase, acompañándolos con explicaciones para entender
> qué hace cada parte del código.

------------------------------------------------------------------------

# 1. Contenido de la clase

En esta clase se trabajaron principalmente:

-   Cursores explícitos.
-   Uso de `FOR` para recorrer un cursor.
-   Consultas `JOIN` dentro de un cursor.
-   Cursores parametrizados.
-   Uso de `%ROWTYPE`.
-   Uso de `%TYPE`.
-   `OPEN`, `FETCH`, `%NOTFOUND` y `CLOSE` en un cursor parametrizado.

El material de la clase también deja indicado que estos contenidos se
relacionan con los temas que pueden aparecer en la prueba:

-   `JOIN` simple.
-   Filtros.
-   Consulta simple.
-   `VARRAY`.
-   Cursores explícitos.
-   Cursores parametrizados.

------------------------------------------------------------------------

# 2. ¿Qué es un cursor explícito?

Un cursor explícito es un cursor que nosotros declaramos para trabajar
con el resultado de una consulta.

En la clase se utiliza para recorrer los productos y obtener también la
información del fabricante asociado.

La estructura general es:

``` sql
DECLARE

    CURSOR nombre_cursor IS
        SELECT ...
        FROM ...;

BEGIN

    -- recorrer el cursor

END;
/
```

La consulta asociada al cursor define qué registros vamos a recorrer.

------------------------------------------------------------------------

# 3. Ejemplo base de la clase

En el primer ejemplo se utilizan dos tablas:

``` text
PRODUCTO
   |
   | codigo_fabricante
   ↓
FABRICANTE
```

La tabla `PRODUCTO` contiene:

-   `codigo`
-   `nombre`
-   `precio`
-   `codigo_fabricante`

La tabla `FABRICANTE` contiene:

-   `codigo_fabricante`
-   `nombre_fabricante`

Por lo tanto, `codigo_fabricante` permite relacionar ambas tablas.

------------------------------------------------------------------------

# 4. Creación de la tabla PRODUCTO

El material de la clase utiliza:

``` sql
CREATE TABLE PRODUCTO(
    codigo NUMBER PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    precio NUMBER NOT NULL,
    codigo_fabricante NUMBER NOT NULL
);
```

La tabla almacena información de cada producto y el código del
fabricante al que pertenece.

------------------------------------------------------------------------

# 5. Creación de la tabla FABRICANTE

La segunda tabla es:

``` sql
CREATE TABLE FABRICANTE(
    codigo_fabricante NUMBER PRIMARY KEY,
    nombre_fabricante VARCHAR2(50) NOT NULL
);
```

El campo `codigo_fabricante` identifica de forma única a cada
fabricante.

------------------------------------------------------------------------

# 6. Insertar los datos

Productos:

``` sql
INSERT INTO PRODUCTO VALUES(1, 'Monitor', 120000, 101);
INSERT INTO PRODUCTO VALUES(2, 'Mouse', 10000, 102);
INSERT INTO PRODUCTO VALUES(3, 'Teclado', 15000, 103);
```

Fabricantes:

``` sql
INSERT INTO FABRICANTE VALUES(101, 'Ignacio Arntz');
INSERT INTO FABRICANTE VALUES(102, 'Andres Mendoza');
INSERT INTO FABRICANTE VALUES(103, 'Simon Gonzalez');
```

Podemos visualizar la relación de esta manera:

``` text
PRODUCTO
1 → Monitor → 101
2 → Mouse   → 102
3 → Teclado → 103

FABRICANTE
101 → Ignacio Arntz
102 → Andres Mendoza
103 → Simon Gonzalez
```

------------------------------------------------------------------------

# 7. Cursor explícito con JOIN

El cursor de la clase utiliza una consulta que relaciona ambas tablas:

``` sql
DECLARE

    CURSOR c_productos_fabricante IS
        SELECT
            p.codigo,
            p.nombre,
            p.precio,
            p.codigo_fabricante,
            f.nombre_fabricante
        FROM PRODUCTO P, FABRICANTE F
        WHERE p.codigo_fabricante = f.codigo_fabricante
        ORDER BY p.precio;

BEGIN

    ...

END;
/
```

La parte importante es:

``` sql
WHERE p.codigo_fabricante = f.codigo_fabricante
```

Esta condición relaciona el producto con su fabricante.

También se utiliza:

``` sql
ORDER BY p.precio;
```

Por lo tanto, los productos obtenidos por el cursor quedan ordenados por
precio.

------------------------------------------------------------------------

# 8. Cursor + FOR

Una de las formas mostradas en la clase para trabajar con el cursor es
utilizar un `FOR`:

``` sql
FOR registro IN c_productos_fabricante
LOOP

    DBMS_OUTPUT.PUT_LINE(
        'Código: ' || registro.codigo
    );

    DBMS_OUTPUT.PUT_LINE(
        'Nombre: ' || registro.nombre
    );

    DBMS_OUTPUT.PUT_LINE(
        'Precio: ' || registro.precio
    );

    DBMS_OUTPUT.PUT_LINE(
        'Fabricante: ' || registro.nombre_fabricante
    );

END LOOP;
```

Aquí no escribimos manualmente:

``` sql
OPEN
FETCH
CLOSE
```

El `FOR` se encarga de recorrer el cursor.

> \[!important\] Idea clave En este tipo de recorrido podemos
> concentrarnos directamente en el registro:
>
> ``` sql
> registro.codigo
> registro.nombre
> registro.precio
> ```
>
> El registro representa la fila actual que estamos procesando.

------------------------------------------------------------------------

# 9. ¿Qué es `registro`?

En:

``` sql
FOR registro IN c_productos_fabricante
```

`registro` es una variable de tipo registro que representa la fila
actual del cursor.

Por ejemplo:

``` sql
registro.codigo
registro.nombre
registro.precio
registro.codigo_fabricante
registro.nombre_fabricante
```

Cada vuelta del `FOR` trabaja con un registro diferente.

Conceptualmente:

``` text
Cursor
  ↓
Fila 1 → registro
  ↓
Fila 2 → registro
  ↓
Fila 3 → registro
```

------------------------------------------------------------------------

# 10. Consulta equivalente utilizando JOIN

El material también muestra la consulta equivalente utilizando `JOIN`:

``` sql
SELECT *
FROM PRODUCTO P
JOIN FABRICANTE F
    ON p.codigo_fabricante = f.codigo_fabricante;
```

La idea es la misma: obtener información de `PRODUCTO` y `FABRICANTE`
relacionándolas mediante `codigo_fabricante`.

> \[!tip\] Para la prueba Es importante reconocer que el `JOIN` puede
> estar dentro de la consulta utilizada por el cursor.

------------------------------------------------------------------------

# 11. Segundo ejercicio de la clase

El segundo ejercicio trabaja con:

``` text
ESTUDIANTE
     |
     | cod_asignatura
     ↓
ASIGNATURA
```

La tabla `ESTUDIANTE` contiene:

``` text
rut
primer_nombre
cod_asignatura
```

La tabla `ASIGNATURA` contiene:

``` text
cod_asignatura
nombre_asignatura
```

El objetivo es aplicar una consulta `JOIN` y utilizar un cursor para
mostrar los resultados.

------------------------------------------------------------------------

# 12. Cursor del ejercicio estudiante/asignatura

El cursor utilizado es:

``` sql
DECLARE

    CURSOR c_estudiante_asignatura IS
        SELECT
            e.rut,
            e.primer_nombre,
            e.cod_asignatura,
            NVL(a.nombre_asignatura, 'SIN ASIGNATURA')
                AS nombre_asignatura
        FROM ESTUDIANTE e
        LEFT JOIN ASIGNATURA a
            ON e.cod_asignatura = a.cod_asignatura;

BEGIN

    ...

END;
/
```

Aquí aparece un elemento importante:

``` sql
LEFT JOIN
```

Esto permite mantener los estudiantes aunque no exista una asignatura
correspondiente.

Para mostrar un valor cuando no existe asignatura se utiliza:

``` sql
NVL(
    a.nombre_asignatura,
    'SIN ASIGNATURA'
)
```

------------------------------------------------------------------------

# 13. Recorrer el cursor del ejercicio

El cursor se recorre con:

``` sql
FOR registro IN c_estudiante_asignatura
LOOP

    DBMS_OUTPUT.PUT_LINE(
        'Información estudiante: ' || registro.rut
    );

    DBMS_OUTPUT.PUT_LINE(
        'Nombre: ' || registro.primer_nombre
    );

    DBMS_OUTPUT.PUT_LINE(
        'Código asignatura: ' || registro.cod_asignatura
    );

    DBMS_OUTPUT.PUT_LINE(
        'Asignatura: ' || registro.nombre_asignatura
    );

END LOOP;
```

Nuevamente, `registro` representa la fila actual.

Como la consulta devuelve cuatro valores, podemos acceder a ellos
mediante:

``` sql
registro.rut
registro.primer_nombre
registro.cod_asignatura
registro.nombre_asignatura
```

------------------------------------------------------------------------

# 14. Cursor parametrizado

El segundo gran contenido de la clase es el cursor parametrizado.

El material lo define como un:

> Cursor que recibe un parámetro que sirve para seleccionar un dato
> específico en base al parámetro especificado.

La diferencia principal es que el cursor recibe un valor que puede
utilizar dentro de su consulta.

Sintaxis:

``` sql
CURSOR nombre_cursor(parametro TIPO) IS
    SELECT ...
    FROM ...
    WHERE columna = parametro;
```

------------------------------------------------------------------------

# 15. Ejemplo de cursor parametrizado

El ejemplo de la clase es:

``` sql
DECLARE

    CURSOR c_producto_fabricante(p_cod_fab NUMBER) IS
        SELECT *
        FROM producto
        WHERE codigo_fabricante = p_cod_fab
        ORDER BY precio;

    ...

BEGIN

    ...

END;
/
```

El parámetro es:

``` sql
p_cod_fab NUMBER
```

Y se utiliza en:

``` sql
WHERE codigo_fabricante = p_cod_fab
```

Por lo tanto, el cursor no trabaja necesariamente con todos los
productos.

Trabaja con los productos que coincidan con el fabricante recibido como
parámetro.

------------------------------------------------------------------------

# 16. Parámetro del cursor

Podemos imaginarlo así:

``` text
OPEN
  ↓
p_cod_fab = 101
  ↓
SELECT *
FROM PRODUCTO
WHERE codigo_fabricante = 101
  ↓
productos del fabricante 101
```

El parámetro funciona como un dato que entregamos al cursor al momento
de utilizarlo.

------------------------------------------------------------------------

# 17. Abrir un cursor parametrizado

Para abrirlo se entrega el valor:

``` sql
OPEN c_producto_fabricante(v_codigo_fabricante);
```

En el ejemplo de la clase:

``` sql
v_codigo_fabricante
```

contiene el código que se utilizará como parámetro.

También podemos pensar directamente en un valor:

``` sql
OPEN c_producto_fabricante(101);
```

La idea es:

``` text
c_producto_fabricante
        +
    parámetro
        ↓
   resultado filtrado
```

------------------------------------------------------------------------

# 18. `%TYPE`

El ejemplo de la clase utiliza:

``` sql
v_codigo_fabricante
    fabricante.codigo_fabricante%TYPE := &codigo;
```

`%TYPE` permite declarar una variable utilizando el mismo tipo de dato
que una columna.

En este caso:

``` sql
fabricante.codigo_fabricante%TYPE
```

toma el tipo de:

``` sql
FABRICANTE.codigo_fabricante
```

Por lo tanto, la variable queda relacionada con el tipo de esa columna.

------------------------------------------------------------------------

# 19. `%ROWTYPE`

El cursor parametrizado también utiliza:

``` sql
v_producto producto%ROWTYPE;
```

Esto permite tener una variable que representa la estructura de una fila
de `PRODUCTO`.

Luego podemos acceder a sus columnas:

``` sql
v_producto.codigo
v_producto.nombre
v_producto.precio
v_producto.codigo_fabricante
```

La relación mental es:

``` text
PRODUCTO
   ↓
%ROWTYPE
   ↓
v_producto
   ↓
v_producto.nombre
v_producto.precio
```

------------------------------------------------------------------------

# 20. `OPEN`, `FETCH` y `CLOSE`

En el cursor parametrizado de la clase se realiza el recorrido
manualmente.

Primero:

``` sql
OPEN c_producto_fabricante(v_codigo_fabricante);
```

Después:

``` sql
LOOP

    FETCH c_producto_fabricante INTO v_producto;

    EXIT WHEN c_producto_fabricante%NOTFOUND;

    ...

END LOOP;
```

Finalmente:

``` sql
CLOSE c_producto_fabricante;
```

La estructura que debemos reconocer es:

``` text
OPEN
 ↓
LOOP
 ↓
FETCH
 ↓
%NOTFOUND
 ↓
PROCESAR
 ↓
repetir
 ↓
CLOSE
```

------------------------------------------------------------------------

# 21. Código completo del cursor parametrizado

El ejemplo trabajado en la clase queda:

``` sql
DECLARE

    CURSOR c_producto_fabricante(p_cod_fab NUMBER) IS
        SELECT *
        FROM producto
        WHERE codigo_fabricante = p_cod_fab
        ORDER BY precio;

    v_producto producto%ROWTYPE;

    v_codigo_fabricante
        fabricante.codigo_fabricante%TYPE := &codigo;

BEGIN

    OPEN c_producto_fabricante(v_codigo_fabricante);

    LOOP

        FETCH c_producto_fabricante INTO v_producto;

        EXIT WHEN c_producto_fabricante%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE(v_producto.codigo);
        DBMS_OUTPUT.PUT_LINE(v_producto.nombre);
        DBMS_OUTPUT.PUT_LINE(v_producto.precio);

    END LOOP;

    CLOSE c_producto_fabricante;

END;
/
```

------------------------------------------------------------------------

# 22. Analizando el código paso a paso

## Paso 1 --- Declarar el cursor

``` sql
CURSOR c_producto_fabricante(p_cod_fab NUMBER) IS
```

Creamos un cursor llamado:

``` text
c_producto_fabricante
```

que recibe:

``` text
p_cod_fab
```

de tipo:

``` text
NUMBER
```

------------------------------------------------------------------------

## Paso 2 --- Definir la consulta

``` sql
SELECT *
FROM producto
WHERE codigo_fabricante = p_cod_fab
ORDER BY precio;
```

La consulta utiliza el parámetro.

Por ejemplo, si:

``` text
p_cod_fab = 101
```

la consulta busca productos cuyo:

``` text
codigo_fabricante = 101
```

Además, los ordena por:

``` sql
precio
```

------------------------------------------------------------------------

## Paso 3 --- Crear el registro

``` sql
v_producto producto%ROWTYPE;
```

Creamos una variable capaz de almacenar una fila de `PRODUCTO`.

------------------------------------------------------------------------

## Paso 4 --- Crear el parámetro externo

``` sql
v_codigo_fabricante
    fabricante.codigo_fabricante%TYPE := &codigo;
```

Se crea una variable cuyo tipo corresponde al tipo de la columna:

``` sql
fabricante.codigo_fabricante
```

El valor se obtiene mediante:

``` sql
&codigo
```

------------------------------------------------------------------------

## Paso 5 --- Abrir el cursor

``` sql
OPEN c_producto_fabricante(v_codigo_fabricante);
```

Aquí se entrega el valor del parámetro al cursor.

------------------------------------------------------------------------

## Paso 6 --- Obtener una fila

``` sql
FETCH c_producto_fabricante INTO v_producto;
```

El `FETCH` obtiene una fila del resultado y la coloca en:

``` sql
v_producto
```

Por eso después podemos utilizar:

``` sql
v_producto.codigo
v_producto.nombre
v_producto.precio
```

------------------------------------------------------------------------

## Paso 7 --- Comprobar si quedan filas

``` sql
EXIT WHEN c_producto_fabricante%NOTFOUND;
```

Si el `FETCH` no encuentra otra fila, el ciclo termina.

Es importante mantener el orden:

``` sql
FETCH
EXIT WHEN %NOTFOUND
PROCESAR
```

------------------------------------------------------------------------

## Paso 8 --- Mostrar los datos

``` sql
DBMS_OUTPUT.PUT_LINE(v_producto.codigo);
DBMS_OUTPUT.PUT_LINE(v_producto.nombre);
DBMS_OUTPUT.PUT_LINE(v_producto.precio);
```

Se muestran los datos del registro actual.

------------------------------------------------------------------------

## Paso 9 --- Cerrar el cursor

``` sql
CLOSE c_producto_fabricante;
```

Terminamos de trabajar con el cursor.

------------------------------------------------------------------------

# 23. Diferencia entre los dos estilos vistos

En la clase aparecen dos formas de recorrer cursores.

### Cursor recorrido con `FOR`

``` sql
FOR registro IN c_productos_fabricante
LOOP

    DBMS_OUTPUT.PUT_LINE(registro.nombre);

END LOOP;
```

El `FOR` simplifica el recorrido.

### Cursor recorrido manualmente

``` sql
OPEN c_producto_fabricante(v_codigo_fabricante);

LOOP

    FETCH c_producto_fabricante INTO v_producto;

    EXIT WHEN c_producto_fabricante%NOTFOUND;

    DBMS_OUTPUT.PUT_LINE(v_producto.nombre);

END LOOP;

CLOSE c_producto_fabricante;
```

Aquí controlamos explícitamente:

``` text
OPEN
FETCH
%NOTFOUND
CLOSE
```

> \[!important\] Diferencia para recordar
>
> `FOR` → facilita el recorrido del cursor.
>
> `OPEN + FETCH + %NOTFOUND + CLOSE` → control manual del cursor.

------------------------------------------------------------------------

# 24. Cursor normal vs cursor parametrizado

La diferencia principal está en la declaración.

Cursor normal:

``` sql
CURSOR c_productos IS
    SELECT *
    FROM producto;
```

Cursor parametrizado:

``` sql
CURSOR c_productos(p_codigo NUMBER) IS
    SELECT *
    FROM producto
    WHERE codigo_fabricante = p_codigo;
```

El segundo recibe un valor.

Luego:

``` sql
OPEN c_productos(101);
```

Ese `101` pasa al parámetro:

``` text
p_codigo = 101
```

------------------------------------------------------------------------

# 25. Ventaja del cursor parametrizado

Un cursor parametrizado permite reutilizar la misma consulta cambiando
el valor recibido.

Conceptualmente:

``` text
Mismo cursor
    ↓
parámetro 101 → productos del fabricante 101

Mismo cursor
    ↓
parámetro 102 → productos del fabricante 102

Mismo cursor
    ↓
parámetro 103 → productos del fabricante 103
```

No necesitamos crear un cursor diferente para cada fabricante.

------------------------------------------------------------------------

# 26. `JOIN` dentro de un cursor

Una consulta de un cursor puede trabajar con más de una tabla.

Ejemplo de la clase:

``` sql
CURSOR c_productos_fabricante IS
    SELECT
        p.codigo,
        p.nombre,
        p.precio,
        p.codigo_fabricante,
        f.nombre_fabricante
    FROM PRODUCTO P, FABRICANTE F
    WHERE p.codigo_fabricante = f.codigo_fabricante
    ORDER BY p.precio;
```

Por lo tanto:

``` text
CURSOR
  ↓
SELECT
  ↓
JOIN / relación entre tablas
  ↓
resultado
  ↓
FOR
  ↓
registro
```

------------------------------------------------------------------------

# 27. `JOIN` + filtro + cursor

Para resolver ejercicios de este tipo conviene separar mentalmente las
partes.

Primero pensamos en la consulta:

``` sql
SELECT ...
FROM ...
JOIN ...
ON ...
WHERE ...
ORDER BY ...
```

Después esa consulta se coloca dentro del cursor:

``` sql
CURSOR c_datos IS
    SELECT ...
    FROM ...
    ...;
```

Finalmente recorremos el cursor.

------------------------------------------------------------------------

# 28. Estructura mental del cursor con FOR

``` text
CURSOR
  ↓
SELECT
  ↓
resultado de varias filas
  ↓
FOR
  ↓
registro actual
  ↓
procesar
  ↓
siguiente fila
```

Ejemplo:

``` sql
FOR registro IN c_datos
LOOP

    DBMS_OUTPUT.PUT_LINE(registro.nombre);

END LOOP;
```

------------------------------------------------------------------------

# 29. Estructura mental del cursor parametrizado

``` text
CURSOR
  ↓
parámetro
  ↓
SELECT con filtro
  ↓
OPEN(parametro)
  ↓
FETCH
  ↓
%NOTFOUND
  ↓
procesar
  ↓
repetir
  ↓
CLOSE
```

Esta es la estructura que conviene reconocer en un ejercicio de prueba.

------------------------------------------------------------------------

# 30. Errores frecuentes

## 1. Confundir el parámetro con una columna

En:

``` sql
CURSOR c_producto_fabricante(p_cod_fab NUMBER) IS
```

`p_cod_fab` es el parámetro del cursor.

Después se utiliza:

``` sql
WHERE codigo_fabricante = p_cod_fab
```

No debemos confundirlos.

------------------------------------------------------------------------

## 2. Olvidar entregar el parámetro

Si el cursor fue declarado como:

``` sql
CURSOR c_productos(p_codigo NUMBER) IS
```

al abrirlo debemos entregar un valor:

``` sql
OPEN c_productos(101);
```

------------------------------------------------------------------------

## 3. Confundir `%TYPE` y `%ROWTYPE`

``` text
%TYPE
→ hereda el tipo de una columna.

%ROWTYPE
→ representa la estructura de una fila.
```

Ejemplos:

``` sql
v_codigo fabricante.codigo_fabricante%TYPE;
```

y:

``` sql
v_producto producto%ROWTYPE;
```

------------------------------------------------------------------------

## 4. Confundir cursor y registro

En:

``` sql
FETCH c_producto_fabricante INTO v_producto;
```

tenemos:

``` text
c_producto_fabricante
        ↓
      cursor
        ↓
      FETCH
        ↓
   v_producto
        ↓
     registro
```

El cursor entrega la fila y el registro la recibe.

------------------------------------------------------------------------

## 5. Olvidar `%NOTFOUND`

En el recorrido manual:

``` sql
FETCH c_producto_fabricante INTO v_producto;

EXIT WHEN c_producto_fabricante%NOTFOUND;
```

Primero obtenemos la fila y luego comprobamos si realmente existe.

------------------------------------------------------------------------

# 31. Fórmula para memorizar

Para un cursor explícito recorrido manualmente:

``` text
OPEN
 ↓
FETCH
 ↓
%NOTFOUND
 ↓
PROCESAR
 ↓
REPETIR
 ↓
CLOSE
```

Una forma simple de recordarlo:

> Abro → saco una fila → compruebo → proceso → repito → cierro.

Para un cursor recorrido con `FOR`:

``` text
CURSOR
 ↓
FOR
 ↓
REGISTRO
 ↓
PROCESAR
```

------------------------------------------------------------------------

# 32. Plantilla de cursor con FOR

``` sql
DECLARE

    CURSOR c_datos IS
        SELECT ...
        FROM ...;

BEGIN

    FOR registro IN c_datos
    LOOP

        DBMS_OUTPUT.PUT_LINE(
            registro.columna
        );

    END LOOP;

END;
/
```

------------------------------------------------------------------------

# 33. Plantilla de cursor parametrizado

``` sql
DECLARE

    CURSOR c_datos(p_parametro NUMBER) IS
        SELECT *
        FROM tabla
        WHERE columna = p_parametro;

    v_dato tabla%ROWTYPE;

BEGIN

    OPEN c_datos(valor);

    LOOP

        FETCH c_datos INTO v_dato;

        EXIT WHEN c_datos%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE(
            v_dato.columna
        );

    END LOOP;

    CLOSE c_datos;

END;
/
```

------------------------------------------------------------------------

# 34. ¿Qué debo saber hacer para la prueba?

Según el material de la clase, conviene practicar especialmente:

### Consultas

``` sql
SELECT ...
```

### Filtros

``` sql
WHERE ...
```

### JOIN simple

``` sql
JOIN ...
ON ...
```

### Cursores explícitos

``` sql
CURSOR ...
```

### Recorrido con `FOR`

``` sql
FOR registro IN cursor
LOOP
    ...
END LOOP;
```

### Cursores parametrizados

``` sql
CURSOR c_datos(p_parametro NUMBER) IS
    ...
```

### Abrir un cursor parametrizado

``` sql
OPEN c_datos(valor);
```

### `%TYPE`

``` sql
variable tabla.columna%TYPE;
```

### `%ROWTYPE`

``` sql
variable tabla%ROWTYPE;
```

### Recorrido manual

``` sql
OPEN
FETCH
%NOTFOUND
CLOSE
```

### VARRAY

El material de la clase indica `VARRAY` dentro de los contenidos
considerados para la prueba.

------------------------------------------------------------------------

# 35. Comparación rápida

  Concepto      Función
  ------------- --------------------------------------------------
  `CURSOR`      Define una consulta que será recorrida
  `FOR`         Permite recorrer el cursor de forma simplificada
  `registro`    Representa la fila actual
  `OPEN`        Abre el cursor
  `FETCH`       Obtiene una fila
  `%NOTFOUND`   Indica que no se obtuvo otra fila
  `CLOSE`       Cierra el cursor
  `%TYPE`       Hereda el tipo de una columna
  `%ROWTYPE`    Representa la estructura de una fila
  Parámetro     Permite entregar un valor al cursor
  `JOIN`        Relaciona información de distintas tablas
  `WHERE`       Filtra registros
  `ORDER BY`    Ordena el resultado

------------------------------------------------------------------------

# 36. Autoevaluación

Intenta responder sin mirar los ejemplos.

1.  ¿Qué es un cursor explícito?
2.  ¿Para qué sirve un cursor?
3.  ¿Qué diferencia existe entre recorrer un cursor con `FOR` y hacerlo
    con `OPEN`, `FETCH` y `CLOSE`?
4.  ¿Qué representa `registro` dentro de un `FOR`?
5.  ¿Para qué sirve un `JOIN` dentro de un cursor?
6.  ¿Qué hace `WHERE` en la consulta del cursor?
7.  ¿Qué hace `ORDER BY`?
8.  ¿Qué es un cursor parametrizado?
9.  ¿Cómo se declara un parámetro de cursor?
10. ¿Cómo se entrega el parámetro al cursor?
11. ¿Qué hace `OPEN c_cursor(valor)`?
12. ¿Qué diferencia hay entre `%TYPE` y `%ROWTYPE`?
13. ¿Para qué sirve `FETCH`?
14. ¿Qué significa `%NOTFOUND`?
15. ¿Por qué se utiliza `CLOSE`?
16. ¿Qué ventaja tiene un cursor parametrizado?
17. ¿Qué función cumple `v_producto PRODUCTO%ROWTYPE`?
18. ¿Qué hace `NVL` en el ejercicio de estudiante y asignatura?
19. ¿Qué diferencia hay entre `JOIN` y `LEFT JOIN` en los ejemplos
    vistos?
20. ¿Qué contenidos de esta clase aparecen indicados como materia para
    la prueba?

------------------------------------------------------------------------

# 37. Mini prueba

> \[!question\] 1. ¿Cómo se declara un cursor parametrizado?

``` sql
CURSOR c_datos(p_parametro NUMBER) IS
    SELECT ...
    FROM ...;
```

> \[!question\] 2. ¿Cómo se entrega el parámetro?

``` sql
OPEN c_datos(valor);
```

> \[!question\] 3. ¿Qué hace `%ROWTYPE`?

Permite declarar una variable con la estructura de una fila de una
tabla.

Ejemplo:

``` sql
v_producto PRODUCTO%ROWTYPE;
```

> \[!question\] 4. ¿Qué hace `%TYPE`?

Permite declarar una variable utilizando el tipo de una columna.

Ejemplo:

``` sql
v_codigo FABRICANTE.codigo_fabricante%TYPE;
```

> \[!question\] 5. ¿Qué hace `FETCH`?

Obtiene una fila del cursor y la almacena en una variable o registro.

> \[!question\] 6. ¿Qué significa `%NOTFOUND`?

Indica que el último `FETCH` no obtuvo otra fila.

> \[!question\] 7. ¿Qué diferencia hay entre estos dos cursores?

``` sql
CURSOR c_productos IS
    SELECT *
    FROM producto;
```

y:

``` sql
CURSOR c_productos(p_codigo NUMBER) IS
    SELECT *
    FROM producto
    WHERE codigo_fabricante = p_codigo;
```

El segundo es parametrizado y puede recibir un valor para filtrar el
resultado.

> \[!question\] 8. ¿Qué diferencia hay entre `%TYPE` y `%ROWTYPE`?

``` text
%TYPE
→ una columna / tipo.

%ROWTYPE
→ una fila completa.
```

------------------------------------------------------------------------

# 38. Resumen de la clase

La clase trabaja con dos ideas principales.

Primero, los cursores explícitos permiten recorrer el resultado de una
consulta.

Un cursor puede utilizar una consulta simple:

``` sql
CURSOR c_datos IS
    SELECT *
    FROM tabla;
```

o una consulta más elaborada con `JOIN`, filtros y ordenamiento:

``` sql
CURSOR c_datos IS
    SELECT ...
    FROM tabla1
    JOIN tabla2
        ON ...
    WHERE ...
    ORDER BY ...;
```

El cursor puede recorrerse utilizando un `FOR`:

``` sql
FOR registro IN c_datos
LOOP
    ...
END LOOP;
```

La segunda idea importante es el cursor parametrizado:

``` sql
CURSOR c_datos(p_parametro NUMBER) IS
    SELECT ...
    WHERE columna = p_parametro;
```

Al utilizarlo:

``` sql
OPEN c_datos(valor);
```

el valor se entrega al parámetro del cursor.

Para el recorrido manual se utiliza:

``` text
OPEN
 ↓
FETCH
 ↓
%NOTFOUND
 ↓
PROCESAR
 ↓
REPETIR
 ↓
CLOSE
```

------------------------------------------------------------------------

# 39. Diagrama mental definitivo

``` text
                         CURSOR
                            │
              ┌─────────────┴─────────────┐
              │                           │
          Cursor normal             Parametrizado
              │                           │
           SELECT                    parámetro
              │                           │
              │                       SELECT
              │                           │
              └─────────────┬─────────────┘
                            ↓
                         resultado
                            │
                 ┌──────────┴──────────┐
                 │                     │
                FOR              OPEN + FETCH
                 │                     │
             registro              %NOTFOUND
                 │                     │
              procesar              procesar
                                       │
                                     repetir
                                       │
                                     CLOSE
```

------------------------------------------------------------------------

# 40. La frase que debes recordar

> \[!important\] Para cursor manual **CURSOR = OPEN → FETCH → NOTFOUND →
> PROCESAR → REPETIR → CLOSE**

> \[!important\] Para cursor con FOR **CURSOR → FOR → registro →
> PROCESAR**

> \[!important\] Para cursor parametrizado **CURSOR(parametro) →
> OPEN(valor) → FETCH → PROCESAR → CLOSE**

Y para los tipos:

``` text
%TYPE
→ una columna

%ROWTYPE
→ una fila
```

------------------------------------------------------------------------

# 41. Fuentes

## Material de la clase

-   `CURSOR EXPLÍCITO.txt`
-   `CURSOR PARAMETRIZADO.txt`

Estos apuntes se construyeron tomando como base los ejemplos, conceptos
y contenidos indicados en los archivos de la clase.
