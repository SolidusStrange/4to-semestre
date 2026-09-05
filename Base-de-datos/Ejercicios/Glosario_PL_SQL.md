# Glosario --- PL/SQL

> Glosario de estudio basado en los contenidos y ejercicios trabajados
> durante la unidad de PL/SQL.

> **Objetivo:** poder reconocer cada término, entender para qué sirve y
> recordar su forma básica de uso sin necesidad de memorizar código
> completo.

------------------------------------------------------------------------

# 1. PL/SQL

**Definición:**\
PL/SQL es el lenguaje procedural de Oracle que permite combinar
instrucciones SQL con lógica de programación.

Con PL/SQL podemos utilizar:

-   Variables.
-   Condiciones.
-   Ciclos.
-   `RECORD`.
-   Cursores.
-   `VARRAY`.
-   Consultas SQL dentro de bloques.

**Uso básico:**

``` sql
DECLARE
    v_nombre VARCHAR2(50);

BEGIN
    v_nombre := 'Juan';

    DBMS_OUTPUT.PUT_LINE(v_nombre);
END;
/
```

**Idea clave:**\
SQL sirve principalmente para consultar y modificar datos. PL/SQL
permite además controlar cómo se ejecutan esas operaciones.

------------------------------------------------------------------------

# 2. Bloque anónimo

**Definición:**\
Es un bloque de código PL/SQL que se ejecuta directamente y no queda
almacenado como un procedimiento o función.

Tiene normalmente tres partes:

``` text
DECLARE
    declaraciones

BEGIN
    instrucciones

EXCEPTION
    manejo de errores

END;
```

En los ejercicios se trabaja principalmente con:

``` sql
DECLARE
    ...
BEGIN
    ...
END;
/
```

**Uso básico:**

``` sql
DECLARE
    v_numero NUMBER := 10;

BEGIN
    DBMS_OUTPUT.PUT_LINE(v_numero);
END;
/
```

**Idea clave:**\
`DECLARE` prepara las variables y estructuras; `BEGIN` contiene lo que
se ejecuta; `END` termina el bloque.

------------------------------------------------------------------------

# 3. DECLARE

**Definición:**\
Sección de un bloque PL/SQL donde se declaran variables, `RECORD`,
cursores y otros elementos antes de ejecutar el código.

**Uso:**

``` sql
DECLARE
    v_nombre VARCHAR2(50);
    v_edad NUMBER;
```

**Idea clave:**\
Todo lo que necesites crear antes de ejecutar el bloque normalmente se
declara aquí.

------------------------------------------------------------------------

# 4. BEGIN

**Definición:**\
Marca el inicio de las instrucciones que serán ejecutadas dentro del
bloque PL/SQL.

**Uso:**

``` sql
BEGIN
    DBMS_OUTPUT.PUT_LINE('Hola');
END;
/
```

**Idea clave:**\
Después de `BEGIN` empieza la parte ejecutable.

------------------------------------------------------------------------

# 5. END

**Definición:**\
Indica el final del bloque PL/SQL.

**Uso:**

``` sql
BEGIN
    DBMS_OUTPUT.PUT_LINE('Hola');
END;
/
```

**Idea clave:**\
`END` cierra el bloque.

------------------------------------------------------------------------

# 6. Variable

**Definición:**\
Espacio donde el programa almacena temporalmente un valor mientras se
ejecuta.

**Uso:**

``` sql
v_nombre VARCHAR2(50);
v_edad NUMBER;
```

También podemos asignar un valor inmediatamente:

``` sql
v_edad NUMBER := 25;
```

**Idea clave:**

``` text
variable → almacena un valor
```

------------------------------------------------------------------------

# 7. Variable de sustitución `&`

**Definición:**\
Permite solicitar un valor al usuario al ejecutar un bloque o script.

**Uso:**

``` sql
v_codigo NUMBER := &codigo;
```

Al ejecutar, Oracle solicita un valor para `codigo`.

Por ejemplo:

``` text
Enter value for codigo: 101
```

**Idea clave:**

``` text
&codigo
   ↓
valor ingresado por el usuario
```

------------------------------------------------------------------------

# 8. DBMS_OUTPUT.PUT_LINE

**Definición:**\
Instrucción utilizada para mostrar información en la salida de Oracle.

**Uso:**

``` sql
DBMS_OUTPUT.PUT_LINE('Hola');
```

Con variables:

``` sql
DBMS_OUTPUT.PUT_LINE('Nombre: ' || v_nombre);
```

**Idea clave:**\
Sirve principalmente para ver resultados mientras pruebas tus bloques
PL/SQL.

------------------------------------------------------------------------

# 9. `||`

**Definición:**\
Operador de concatenación. Permite unir textos y valores.

**Uso:**

``` sql
DBMS_OUTPUT.PUT_LINE(
    'Nombre: ' || v_nombre
);
```

Si:

``` text
v_nombre = 'Juan'
```

se obtiene:

``` text
Nombre: Juan
```

**Idea clave:**

``` text
texto || variable
```

→ une ambos valores.

------------------------------------------------------------------------

# 10. IF

**Definición:**\
Permite ejecutar instrucciones dependiendo de si una condición se
cumple.

**Uso:**

``` sql
IF v_sueldo >= 700000 THEN

    DBMS_OUTPUT.PUT_LINE('Sueldo alto');

END IF;
```

Con alternativa:

``` sql
IF v_sueldo >= 700000 THEN
    DBMS_OUTPUT.PUT_LINE('Sueldo alto');
ELSE
    DBMS_OUTPUT.PUT_LINE('Sueldo estándar');
END IF;
```

**Idea clave:**\
`IF` pregunta:

``` text
¿Se cumple la condición?
    ↓
Sí → ejecuta
No → continúa / ELSE
```

------------------------------------------------------------------------

# 11. ELSIF

**Definición:**\
Permite comprobar una segunda o tercera condición cuando la anterior no
se cumplió.

**Uso:**

``` sql
IF nota >= 6 THEN
    ...
ELSIF nota >= 4 THEN
    ...
ELSE
    ...
END IF;
```

**Idea clave:**\
Sirve para manejar varias condiciones consecutivas.

------------------------------------------------------------------------

# 12. ELSE

**Definición:**\
Contiene las instrucciones que se ejecutan cuando ninguna de las
condiciones anteriores se cumple.

**Uso:**

``` sql
IF edad >= 18 THEN
    DBMS_OUTPUT.PUT_LINE('Adulto');
ELSE
    DBMS_OUTPUT.PUT_LINE('Menor');
END IF;
```

------------------------------------------------------------------------

# 13. CASE

**Definición:**\
Estructura condicional que permite seleccionar una acción dependiendo de
un valor o condición.

En los ejercicios se utiliza como alternativa a `IF / ELSIF / ELSE`.

**Uso conceptual:**

``` sql
CASE
    WHEN nota >= 6 THEN
        ...
    WHEN nota >= 4 THEN
        ...
    ELSE
        ...
END CASE;
```

**Idea clave:**\
Permite expresar varias alternativas de manera ordenada.

------------------------------------------------------------------------

# 14. RECORD

**Definición:**\
Estructura que permite agrupar varios datos relacionados dentro de una
sola variable.

Por ejemplo, un estudiante puede tener:

``` text
id
nombre
edad
nota
```

**Uso:**

``` sql
TYPE estudiante_record IS RECORD (
    id NUMBER,
    nombre VARCHAR2(50),
    edad NUMBER,
    nota NUMBER
);

v_estudiante estudiante_record;
```

Los atributos se acceden mediante `.`:

``` sql
v_estudiante.nombre
v_estudiante.edad
v_estudiante.nota
```

**Idea clave:**

``` text
RECORD
 ├── id
 ├── nombre
 ├── edad
 └── nota
```

------------------------------------------------------------------------

# 15. TYPE IS RECORD

**Definición:**\
Sintaxis utilizada para crear un tipo de `RECORD` personalizado.

**Uso:**

``` sql
TYPE empleado_record IS RECORD (
    id NUMBER,
    nombre VARCHAR2(50),
    sueldo NUMBER
);
```

Después:

``` sql
v_empleado empleado_record;
```

**Idea clave:**\
Primero defines la estructura y luego creas una variable de ese tipo.

------------------------------------------------------------------------

# 16. `%TYPE`

**Definición:**\
Permite declarar una variable utilizando el mismo tipo de dato que una
columna existente.

**Uso:**

``` sql
v_sueldo EMPLEADO.SUELDO%TYPE;
```

Si el tipo de `EMPLEADO.SUELDO` cambia, la variable mantiene
automáticamente la referencia al tipo de esa columna.

**Idea clave:**

``` text
columna%TYPE
     ↓
mismo tipo de dato
```

------------------------------------------------------------------------

# 17. `%ROWTYPE`

**Definición:**\
Permite declarar una variable que representa una fila completa de una
tabla.

**Uso:**

``` sql
v_empleado EMPLEADO%ROWTYPE;
```

Después podemos acceder a sus columnas:

``` sql
v_empleado.id_empleado
v_empleado.nombre
v_empleado.sueldo
```

**Idea clave:**

``` text
%TYPE
→ una columna / un tipo

%ROWTYPE
→ una fila completa
```

------------------------------------------------------------------------

# 18. SELECT INTO

**Definición:**\
Permite realizar una consulta `SELECT` y almacenar el resultado
directamente en una variable o `RECORD`.

**Uso:**

``` sql
SELECT sueldo
INTO v_sueldo
FROM empleado
WHERE id_empleado = v_id;
```

Con `%ROWTYPE`:

``` sql
SELECT *
INTO v_empleado
FROM empleado
WHERE id_empleado = v_id;
```

**Idea clave:**

``` text
SELECT
   ↓
INTO
   ↓
variable / RECORD
```

------------------------------------------------------------------------

# 19. Cursor

**Definición:**\
Un cursor permite trabajar con el resultado de una consulta que puede
devolver varias filas, recorriéndolas una por una.

**Uso conceptual:**

``` sql
CURSOR c_empleados IS
    SELECT *
    FROM empleado;
```

Después podemos recorrerlo.

**Idea clave:**

``` text
SELECT devuelve varias filas
          ↓
        CURSOR
          ↓
fila 1 → fila 2 → fila 3 → ...
```

------------------------------------------------------------------------

# 20. Cursor explícito

**Definición:**\
Es un cursor que nosotros declaramos explícitamente en el bloque PL/SQL.

**Uso:**

``` sql
CURSOR c_productos IS
    SELECT *
    FROM producto;
```

Después puede recorrerse mediante un `FOR` o manualmente con `OPEN`,
`FETCH` y `CLOSE`.

**Idea clave:**\
El cursor contiene la consulta que queremos recorrer.

------------------------------------------------------------------------

# 21. Consulta del cursor

**Definición:**\
Es el `SELECT` que define qué información devolverá el cursor.

**Uso:**

``` sql
CURSOR c_productos IS
    SELECT codigo, nombre, precio
    FROM producto
    ORDER BY precio;
```

La consulta determina:

-   Qué columnas obtenemos.
-   De qué tablas.
-   Qué filtros aplicamos.
-   Cómo ordenamos los resultados.

------------------------------------------------------------------------

# 22. FOR

**Definición:**\
Estructura de repetición que permite ejecutar un conjunto de
instrucciones varias veces.

En PL/SQL también puede utilizarse directamente para recorrer un cursor.

**Uso con cursor:**

``` sql
FOR registro IN c_productos
LOOP

    DBMS_OUTPUT.PUT_LINE(registro.nombre);

END LOOP;
```

**Idea clave:**\
El `FOR` avanza automáticamente por las filas del cursor.

------------------------------------------------------------------------

# 23. FOR ... IN cursor

**Definición:**\
Forma simplificada de recorrer un cursor.

**Uso:**

``` sql
FOR registro IN c_productos
LOOP
    DBMS_OUTPUT.PUT_LINE(registro.nombre);
END LOOP;
```

No necesitamos escribir manualmente:

``` text
OPEN
FETCH
CLOSE
```

El `FOR` controla ese recorrido.

**Idea clave:**

``` text
FOR
 ↓
siguiente registro
 ↓
procesar
 ↓
siguiente registro
```

------------------------------------------------------------------------

# 24. Registro del cursor

**Definición:**\
Variable de registro creada automáticamente por el `FOR` para
representar la fila actual del cursor.

Por ejemplo:

``` sql
FOR registro IN c_productos
LOOP
    DBMS_OUTPUT.PUT_LINE(registro.nombre);
END LOOP;
```

Aquí:

``` text
registro
```

representa la fila actual.

Podemos acceder a sus campos:

``` sql
registro.codigo
registro.nombre
registro.precio
```

**Idea clave:**

``` text
cursor
  ↓
fila actual
  ↓
registro
```

------------------------------------------------------------------------

# 25. OPEN

**Definición:**\
Abre un cursor para comenzar a trabajar con sus resultados.

Se utiliza especialmente cuando controlamos manualmente el recorrido.

**Uso:**

``` sql
OPEN c_productos;
```

En un cursor parametrizado:

``` sql
OPEN c_productos(101);
```

**Idea clave:**

``` text
OPEN
→ prepara el cursor para comenzar a obtener filas.
```

------------------------------------------------------------------------

# 26. FETCH

**Definición:**\
Obtiene una fila del resultado del cursor y la coloca en una variable o
`RECORD`.

**Uso:**

``` sql
FETCH c_productos INTO v_producto;
```

Si:

``` sql
v_producto PRODUCTO%ROWTYPE;
```

entonces `FETCH` coloca la fila obtenida dentro de `v_producto`.

**Idea clave:**

``` text
CURSOR
  ↓
FETCH
  ↓
una fila
  ↓
RECORD
```

------------------------------------------------------------------------

# 27. LOOP

**Definición:**\
Estructura que permite repetir instrucciones.

En los cursores manuales se utiliza para continuar haciendo `FETCH`
hasta que no queden registros.

**Uso:**

``` sql
LOOP

    FETCH c_productos INTO v_producto;

    EXIT WHEN c_productos%NOTFOUND;

    DBMS_OUTPUT.PUT_LINE(v_producto.nombre);

END LOOP;
```

**Idea clave:**\
`LOOP` repite indefinidamente hasta que alguna instrucción provoque la
salida.

------------------------------------------------------------------------

# 28. EXIT

**Definición:**\
Permite salir de un ciclo.

**Uso:**

``` sql
EXIT WHEN condicion;
```

En los cursores:

``` sql
EXIT WHEN c_productos%NOTFOUND;
```

**Idea clave:**\
Sirve para indicar cuándo debemos dejar de repetir el `LOOP`.

------------------------------------------------------------------------

# 29. EXIT WHEN

**Definición:**\
Permite salir automáticamente de un ciclo cuando una condición se
cumple.

**Uso:**

``` sql
EXIT WHEN c_productos%NOTFOUND;
```

Esto significa:

``` text
¿No quedan registros?
      ↓
Sí → salir del LOOP
No → continuar
```

------------------------------------------------------------------------

# 30. `%NOTFOUND`

**Definición:**\
Atributo del cursor que indica que la última operación `FETCH` no
encontró otra fila.

**Uso:**

``` sql
FETCH c_productos INTO v_producto;

EXIT WHEN c_productos%NOTFOUND;
```

**Idea clave:**\
Es la señal que permite saber cuándo terminar un recorrido manual.

------------------------------------------------------------------------

# 31. CLOSE

**Definición:**\
Cierra el cursor después de terminar de utilizarlo.

**Uso:**

``` sql
CLOSE c_productos;
```

**Idea clave:**

``` text
OPEN
 ↓
FETCH
 ↓
...
 ↓
CLOSE
```

------------------------------------------------------------------------

# 32. Recorrido manual de un cursor

**Definición:**\
Forma de recorrer un cursor controlando explícitamente cada etapa.

La estructura fundamental es:

``` sql
OPEN c_productos;

LOOP

    FETCH c_productos INTO v_producto;

    EXIT WHEN c_productos%NOTFOUND;

    -- procesar registro

END LOOP;

CLOSE c_productos;
```

**Idea clave para memorizar:**

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

# 33. Cursor parametrizado

**Definición:**\
Cursor que recibe uno o más parámetros para utilizar esos valores dentro
de su consulta.

**Uso:**

``` sql
CURSOR c_producto_fabricante(p_cod_fab NUMBER) IS
    SELECT *
    FROM producto
    WHERE codigo_fabricante = p_cod_fab;
```

Al abrirlo:

``` sql
OPEN c_producto_fabricante(101);
```

El `101` se entrega al parámetro:

``` text
p_cod_fab = 101
```

**Idea clave:**\
Permite reutilizar el mismo cursor con diferentes valores.

------------------------------------------------------------------------

# 34. Parámetro de cursor

**Definición:**\
Valor que recibe un cursor parametrizado y que puede utilizarse dentro
de su consulta.

**Uso:**

``` sql
CURSOR c_productos(p_codigo NUMBER) IS
    SELECT *
    FROM producto
    WHERE codigo_fabricante = p_codigo;
```

Aquí:

``` text
p_codigo
```

es el parámetro.

------------------------------------------------------------------------

# 35. OPEN con parámetro

**Definición:**\
Al abrir un cursor parametrizado debemos entregarle el valor que
utilizará su parámetro.

**Uso:**

``` sql
OPEN c_productos(101);
```

O mediante una variable:

``` sql
OPEN c_productos(v_codigo_fabricante);
```

**Idea clave:**

``` text
cursor(parametro)
       ↓
OPEN(valor)
```

------------------------------------------------------------------------

# 36. JOIN

**Definición:**\
Permite combinar información proveniente de dos o más tablas
relacionadas.

**Uso:**

``` sql
SELECT *
FROM producto p
JOIN fabricante f
    ON p.codigo_fabricante = f.codigo_fabricante;
```

**Idea clave:**\
`JOIN` conecta filas de diferentes tablas mediante una condición.

------------------------------------------------------------------------

# 37. ON

**Definición:**\
Indica la condición mediante la cual se relacionan las tablas de un
`JOIN`.

**Uso:**

``` sql
JOIN fabricante f
    ON p.codigo_fabricante = f.codigo_fabricante
```

La condición dice:

``` text
PRODUCTO.codigo_fabricante
=
FABRICANTE.codigo_fabricante
```

------------------------------------------------------------------------

# 38. LEFT JOIN

**Definición:**\
Tipo de `JOIN` que mantiene todas las filas de la tabla ubicada a la
izquierda, aunque no exista una coincidencia en la tabla derecha.

**Uso:**

``` sql
FROM estudiante e
LEFT JOIN asignatura a
    ON e.cod_asignatura = a.cod_asignatura
```

**Idea clave:**\
Aunque un estudiante no tenga una asignatura correspondiente, el
estudiante sigue apareciendo en el resultado.

------------------------------------------------------------------------

# 39. WHERE

**Definición:**\
Permite filtrar las filas que devuelve una consulta.

**Uso:**

``` sql
WHERE precio >= 50000
```

En un cursor:

``` sql
CURSOR c_productos IS
    SELECT *
    FROM producto
    WHERE precio >= 50000;
```

**Idea clave:**

``` text
SELECT
 ↓
WHERE
 ↓
solo filas que cumplen la condición
```

------------------------------------------------------------------------

# 40. ORDER BY

**Definición:**\
Permite ordenar los resultados de una consulta.

**Uso:**

``` sql
ORDER BY precio;
```

También podemos indicar el orden:

``` sql
ORDER BY precio ASC;
```

o:

``` sql
ORDER BY precio DESC;
```

**Idea clave:**\
No filtra datos; solamente cambia el orden en que aparecen.

------------------------------------------------------------------------

# 41. NVL

**Definición:**\
Permite reemplazar un valor `NULL` por otro valor.

**Uso:**

``` sql
NVL(a.nombre_asignatura, 'SIN ASIGNATURA')
```

Si:

``` text
a.nombre_asignatura = NULL
```

se obtiene:

``` text
SIN ASIGNATURA
```

**Idea clave:**

``` text
valor existe → valor original
valor NULL   → valor alternativo
```

------------------------------------------------------------------------

# 42. COUNT

**Definición:**\
Función que permite contar registros o valores.

En los ejercicios se utiliza conceptualmente para obtener cantidades
como la cantidad de empleados o productos.

**Uso SQL:**

``` sql
SELECT COUNT(*)
FROM empleado;
```

También puede utilizarse un contador manual dentro de un cursor:

``` sql
v_contador := v_contador + 1;
```

**Idea clave:**\
`COUNT` cuenta directamente en SQL; un contador permite contar mientras
recorremos registros en PL/SQL.

------------------------------------------------------------------------

# 43. SUM

**Definición:**\
Función que calcula la suma de valores.

**Uso:**

``` sql
SELECT SUM(sueldo)
FROM empleado;
```

También podemos realizar un acumulador manual:

``` sql
v_suma := v_suma + v_empleado.sueldo;
```

**Idea clave:**

``` text
SUM → suma en SQL
acumulador → suma mientras recorremos datos
```

------------------------------------------------------------------------

# 44. AVG

**Definición:**\
Función que calcula el promedio de un conjunto de valores.

**Uso:**

``` sql
SELECT AVG(sueldo)
FROM empleado;
```

O manualmente:

``` sql
v_promedio := v_suma / v_contador;
```

------------------------------------------------------------------------

# 45. MAX

**Definición:**\
Función que obtiene el valor máximo.

**Uso:**

``` sql
SELECT MAX(sueldo)
FROM empleado;
```

Al recorrer un cursor también podemos mantener manualmente el mayor
valor:

``` sql
IF v_empleado.sueldo > v_mayor_sueldo THEN
    v_mayor_sueldo := v_empleado.sueldo;
END IF;
```

------------------------------------------------------------------------

# 46. MIN

**Definición:**\
Función que obtiene el valor mínimo.

**Uso:**

``` sql
SELECT MIN(sueldo)
FROM empleado;
```

------------------------------------------------------------------------

# 47. Contador

**Definición:**\
Variable utilizada para llevar la cantidad de veces que ocurre algo.

**Uso:**

``` sql
v_contador NUMBER := 0;

...

v_contador := v_contador + 1;
```

En un cursor:

``` text
registro 1 → contador = 1
registro 2 → contador = 2
registro 3 → contador = 3
```

**Idea clave:**\
Normalmente comienza en `0` y aumenta en cada iteración.

------------------------------------------------------------------------

# 48. Acumulador

**Definición:**\
Variable que va almacenando una suma a medida que se recorren datos.

**Uso:**

``` sql
v_suma NUMBER := 0;

...

v_suma := v_suma + v_empleado.sueldo;
```

**Idea clave:**

``` text
0
 ↓
+ sueldo 1
 ↓
+ sueldo 2
 ↓
+ sueldo 3
 ↓
total
```

------------------------------------------------------------------------

# 49. Promedio

**Definición:**\
Valor que representa la media de un conjunto de números.

En un recorrido manual podemos calcularlo usando un acumulador y un
contador.

**Uso:**

``` sql
v_promedio := v_suma / v_contador;
```

**Idea clave:**

``` text
promedio = suma / cantidad
```

------------------------------------------------------------------------

# 50. VARRAY

**Definición:**\
Estructura de PL/SQL que permite almacenar una cantidad ordenada de
elementos del mismo tipo con una capacidad máxima definida.

**Uso conceptual:**

``` sql
TYPE numeros IS VARRAY(5) OF NUMBER;

v_numeros numeros;
```

Podemos tener hasta:

``` text
5 elementos
```

**Idea clave:**

``` text
VARRAY
→ varios elementos
→ mismo tipo
→ ordenados por índice
→ capacidad máxima
```

------------------------------------------------------------------------

# 51. Constructor de VARRAY

**Definición:**\
Forma utilizada para crear/inicializar un `VARRAY` entregándole sus
elementos.

Si tenemos:

``` sql
TYPE numeros IS VARRAY(5) OF NUMBER;
```

podemos crear el valor:

``` sql
v_numeros := numeros(10, 20, 30, 40, 50);
```

**Idea clave:**

``` text
tipo VARRAY
     ↓
constructor
     ↓
elementos
```

------------------------------------------------------------------------

# 52. Índice

**Definición:**\
Posición utilizada para acceder a un elemento de una estructura como un
`VARRAY`.

Los elementos se acceden mediante:

``` sql
v_numeros(1)
v_numeros(2)
v_numeros(3)
```

Por ejemplo:

``` sql
DBMS_OUTPUT.PUT_LINE(v_numeros(1));
```

muestra el primer elemento.

**Idea clave:**\
El índice indica la posición del elemento.

------------------------------------------------------------------------

# 53. EXTEND

**Definición:**\
Método utilizado en colecciones PL/SQL para agregar espacio para nuevos
elementos.

En un `VARRAY`, permite ampliar la cantidad actual de elementos sin
superar su capacidad máxima.

**Uso conceptual:**

``` sql
v_numeros.EXTEND;
```

Después podemos asignar el nuevo elemento:

``` sql
v_numeros(5) := 50;
```

**Idea clave:**

``` text
capacidad máxima
      ≠
cantidad actual de elementos
```

`EXTEND` aumenta la cantidad de posiciones disponibles dentro de la
colección hasta su límite.

------------------------------------------------------------------------

# 54. LENGTH

**Definición:**\
Función que devuelve la cantidad de caracteres de un texto.

**Uso:**

``` sql
LENGTH(nombre)
```

Por ejemplo:

``` sql
IF LENGTH(v_nombre) > 4 THEN
    ...
END IF;
```

**Idea clave:**\
Sirve para conocer el tamaño de una cadena.

------------------------------------------------------------------------

# 55. Ciclo de un cursor con FOR

Cuando utilizamos:

``` sql
FOR registro IN c_productos
LOOP
    ...
END LOOP;
```

podemos pensar en el proceso como:

``` text
1. Tomar primera fila
        ↓
2. Guardarla en registro
        ↓
3. Ejecutar instrucciones
        ↓
4. Tomar siguiente fila
        ↓
5. Repetir
        ↓
6. Terminar cuando no queden filas
```

No necesitamos controlar manualmente `OPEN`, `FETCH` y `CLOSE`.

------------------------------------------------------------------------

# 56. Ciclo de un cursor manual

Cuando no utilizamos `FOR`, el proceso es explícito:

``` sql
OPEN c_productos;

LOOP

    FETCH c_productos INTO v_producto;

    EXIT WHEN c_productos%NOTFOUND;

    -- procesar

END LOOP;

CLOSE c_productos;
```

La secuencia que debes recordar es:

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

# 57. Diferencia entre `FOR` y `LOOP` en cursores

### `FOR`

Simplifica el recorrido:

``` sql
FOR registro IN c_productos
LOOP
    ...
END LOOP;
```

No necesitamos controlar manualmente:

``` text
OPEN
FETCH
CLOSE
```

### `LOOP`

Permite controlar manualmente el recorrido:

``` sql
OPEN c_productos;

LOOP
    FETCH c_productos INTO v_producto;
    EXIT WHEN c_productos%NOTFOUND;
    ...
END LOOP;

CLOSE c_productos;
```

**Idea clave:**

``` text
FOR
→ recorrido simplificado

LOOP
→ recorrido controlado manualmente
```

------------------------------------------------------------------------

# 58. Diferencia entre `RECORD`, `%ROWTYPE` y registro del `FOR`

Estos conceptos están relacionados, pero no son exactamente lo mismo.

### `RECORD`

Estructura que nosotros podemos definir:

``` sql
TYPE persona IS RECORD (
    nombre VARCHAR2(50),
    edad NUMBER
);
```

### `%ROWTYPE`

Estructura que toma automáticamente las columnas de una tabla:

``` sql
v_empleado EMPLEADO%ROWTYPE;
```

### Registro del `FOR`

Variable que representa automáticamente la fila actual del cursor:

``` sql
FOR registro IN c_empleados
LOOP
    registro.nombre
END LOOP;
```

**Resumen:**

``` text
RECORD
→ estructura definida por nosotros

%ROWTYPE
→ estructura basada en una fila de una tabla

registro del FOR
→ fila actual que estamos recorriendo
```

------------------------------------------------------------------------

# 59. Diferencia entre `%TYPE` y `%ROWTYPE`

Esta diferencia es fundamental.

``` sql
v_sueldo EMPLEADO.SUELDO%TYPE;
```

Significa:

``` text
v_sueldo
→ tiene el tipo de la columna SUELDO
```

Mientras:

``` sql
v_empleado EMPLEADO%ROWTYPE;
```

significa:

``` text
v_empleado
→ puede representar una fila completa de EMPLEADO
```

**Para memorizar:**

``` text
%TYPE     → tipo de UNA columna
%ROWTYPE  → UNA FILA completa
```

------------------------------------------------------------------------

# 60. Diferencia entre cursor normal y parametrizado

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

El segundo permite cambiar el criterio de búsqueda al utilizarlo:

``` sql
OPEN c_productos(101);
```

**Idea clave:**

``` text
Normal
→ misma consulta

Parametrizado
→ misma consulta + valor variable
```

------------------------------------------------------------------------

# 61. `JOIN` + cursor

Un cursor no está limitado a consultas simples.

Puede contener una consulta con `JOIN`:

``` sql
CURSOR c_productos IS
    SELECT
        p.codigo,
        p.nombre,
        p.precio,
        f.nombre_fabricante
    FROM producto p
    JOIN fabricante f
        ON p.codigo_fabricante = f.codigo_fabricante;
```

Después:

``` sql
FOR registro IN c_productos
LOOP
    DBMS_OUTPUT.PUT_LINE(registro.nombre);
END LOOP;
```

**Idea clave:**\
Primero construyes la consulta que necesitas y después utilizas esa
consulta como cursor.

------------------------------------------------------------------------

# 62. Filtro dentro de un cursor

Un cursor también puede utilizar `WHERE`.

Ejemplo:

``` sql
CURSOR c_productos IS
    SELECT *
    FROM producto
    WHERE precio >= 50000;
```

El cursor solamente recorrerá los registros que cumplen la condición.

**Idea clave:**

``` text
tabla
 ↓
WHERE
 ↓
filas filtradas
 ↓
cursor
 ↓
recorrido
```

------------------------------------------------------------------------

# 63. Cursor parametrizado + filtro

Es una combinación importante de la Clase 4.

``` sql
CURSOR c_productos(p_codigo NUMBER) IS
    SELECT *
    FROM producto
    WHERE codigo_fabricante = p_codigo;
```

Luego:

``` sql
OPEN c_productos(101);
```

El parámetro controla el filtro.

**Idea clave:**

``` text
parámetro
   ↓
WHERE
   ↓
resultado filtrado
```

------------------------------------------------------------------------

# 64. Cursor parametrizado + `%ROWTYPE`

Otra combinación importante:

``` sql
CURSOR c_productos(p_codigo NUMBER) IS
    SELECT *
    FROM producto
    WHERE codigo_fabricante = p_codigo;

v_producto PRODUCTO%ROWTYPE;
```

Después:

``` sql
OPEN c_productos(101);

LOOP
    FETCH c_productos INTO v_producto;

    EXIT WHEN c_productos%NOTFOUND;

    DBMS_OUTPUT.PUT_LINE(v_producto.nombre);
END LOOP;

CLOSE c_productos;
```

**Idea clave:**

``` text
Cursor parametrizado
       +
   %ROWTYPE
       +
 OPEN/FETCH/CLOSE
```

------------------------------------------------------------------------

# 65. Mapa mental de PL/SQL visto

``` text
PL/SQL
│
├── Bloque anónimo
│   ├── DECLARE
│   ├── BEGIN
│   └── END
│
├── Variables
│   ├── NUMBER
│   ├── VARCHAR2
│   └── & variable de sustitución
│
├── Condiciones
│   ├── IF
│   ├── ELSIF
│   ├── ELSE
│   └── CASE
│
├── Estructuras
│   ├── RECORD
│   ├── %TYPE
│   ├── %ROWTYPE
│   └── VARRAY
│
├── Consultas
│   ├── SELECT INTO
│   ├── JOIN
│   ├── LEFT JOIN
│   ├── ON
│   ├── WHERE
│   └── ORDER BY
│
└── Cursores
    ├── CURSOR
    ├── FOR
    ├── registro
    ├── OPEN
    ├── FETCH
    ├── LOOP
    ├── EXIT
    ├── %NOTFOUND
    ├── CLOSE
    └── parametrizados
```

------------------------------------------------------------------------

# 66. Lo mínimo que debes recordar

Si estás preparando una prueba, estas asociaciones son especialmente
importantes:

``` text
%TYPE
→ tipo de una columna

%ROWTYPE
→ fila completa

RECORD
→ agrupar varios datos

CURSOR
→ recorrer varias filas

FOR registro IN cursor
→ recorrer cursor de forma simplificada

OPEN
→ abrir cursor

FETCH
→ obtener una fila

%NOTFOUND
→ no quedan filas

CLOSE
→ cerrar cursor

LOOP
→ repetir instrucciones

EXIT WHEN
→ salir cuando se cumple una condición

CURSOR(parametro)
→ cursor parametrizado

OPEN cursor(valor)
→ entregar parámetro

JOIN
→ relacionar tablas

WHERE
→ filtrar

ORDER BY
→ ordenar

NVL
→ reemplazar NULL

VARRAY
→ colección ordenada con capacidad máxima

EXTEND
→ agregar una posición al VARRAY

contador
→ contar

acumulador
→ sumar progresivamente
```

------------------------------------------------------------------------

# 67. Las tres estructuras que debes reconocer

## Cursor con `FOR`

``` sql
CURSOR c_datos IS
    SELECT *
    FROM tabla;

BEGIN

    FOR registro IN c_datos
    LOOP
        ...
    END LOOP;

END;
/
```

## Cursor manual

``` sql
CURSOR c_datos IS
    SELECT *
    FROM tabla;

v_dato tabla%ROWTYPE;

BEGIN

    OPEN c_datos;

    LOOP
        FETCH c_datos INTO v_dato;

        EXIT WHEN c_datos%NOTFOUND;

        ...
    END LOOP;

    CLOSE c_datos;

END;
/
```

## Cursor parametrizado

``` sql
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

        ...
    END LOOP;

    CLOSE c_datos;

END;
/
```

------------------------------------------------------------------------

# 68. Regla general para estudiar

Cuando veas un ejercicio de PL/SQL, intenta identificar primero:

``` text
1. ¿Necesito variables?
        ↓
2. ¿Necesito una condición?
        ↓
3. ¿Necesito un RECORD?
        ↓
4. ¿Necesito obtener una fila? → SELECT INTO
        ↓
5. ¿Necesito varias filas? → CURSOR
        ↓
6. ¿El cursor necesita un filtro variable? → PARAMETRIZADO
        ↓
7. ¿Lo recorreré automáticamente? → FOR
        ↓
8. ¿Necesito controlarlo manualmente? → OPEN/FETCH/LOOP/CLOSE
```

La idea no es memorizar bloques completos, sino reconocer qué
herramienta corresponde al problema.
