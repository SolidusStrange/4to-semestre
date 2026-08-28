---
title: "Base de Datos — PL/SQL: VARRAY"
course: "Base de Datos"
topic: "PL/SQL — VARRAY"
class: 3
tags:
  - bases-de-datos
  - oracle
  - plsql
  - varray
  - colecciones
  - ejercicios
---

# Base de Datos — PL/SQL: VARRAY

> [!info] Objetivo
> Entender qué es un `VARRAY` en PL/SQL, cómo declararlo, inicializarlo, acceder a sus elementos, recorrerlo y modificar su tamaño mediante `EXTEND`. Al final se incluyen ejercicios progresivos para practicar.

> [!note] Base de estos apuntes
> Se conservaron los cuatro ejercicios realizados en clase, pero cada uno está acompañado de una explicación más detallada. También se agregaron precisiones de Oracle para evitar aprender conceptos de forma incorrecta.

---

# 1. ¿Qué es una colección en PL/SQL?

Una **colección** es un conjunto ordenado de elementos del mismo tipo.

En PL/SQL existen tres tipos principales de colecciones:

- `VARRAY`
- `Nested Table`
- `Associative Array`

En esta clase trabajamos específicamente con `VARRAY`. Oracle define un `VARRAY` como un arreglo cuyo número de elementos puede variar entre cero y un máximo previamente declarado. Los índices comienzan en `1`. citeturn198734search0turn198734search8

---

# 2. ¿Qué es un VARRAY?

`VARRAY` significa **Variable-Size Array**.

La idea es similar a un arreglo tradicional:

```text
Índice:    1    2    3    4    5
Valor:    10   20   30   40   50
```

La diferencia importante es que al declarar el tipo indicamos un **máximo de elementos**.

Ejemplo:

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;
```

Esto significa:

- `NumArray` → nombre del tipo.
- `VARRAY(5)` → puede contener como máximo 5 elementos.
- `OF NUMBER` → todos sus elementos deben ser `NUMBER`.

Por lo tanto:

```text
NumArray
máximo = 5
tipo = NUMBER
```

La declaración del límite no significa que la colección deba comenzar llena con cinco elementos. Puede tener desde 0 hasta 5 elementos. citeturn198734search0

---

# 3. Estructura general

La forma básica es:

```sql
TYPE nombre_tipo IS VARRAY(maximo) OF tipo_dato;
```

Ejemplos:

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;

TYPE NameArray IS VARRAY(10) OF VARCHAR2(30);

TYPE DateArray IS VARRAY(12) OF DATE;
```

Cada elemento de un `VARRAY` debe ser del tipo indicado.

Por ejemplo:

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;
```

Permite:

```text
1
2
3
4.5
100
```

pero no:

```text
'Juan'
'Chile'
```

porque esos valores son de tipo texto y no `NUMBER`.

---

# 4. Declarar el tipo ≠ declarar la variable

Este punto es fundamental.

Cuando escribimos:

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;
```

todavía **no tenemos una colección almacenando valores**.

Solo acabamos de crear un **tipo de colección**.

Después necesitamos declarar una variable de ese tipo:

```sql
v_numbers NumArray;
```

Ahora sí tenemos una variable llamada `v_numbers` que puede almacenar hasta cinco números.

La estructura mental es:

```text
TYPE
 ↓
crea el tipo

VARIABLE
 ↓
crea una variable de ese tipo
```

---

# 5. Inicialización de un VARRAY

Un `VARRAY` se inicializa utilizando el **constructor del tipo**, que tiene el mismo nombre que el tipo de colección. Oracle documenta esta sintaxis para VARRAY y nested tables. citeturn198734search3

Ejemplo:

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;

v_numbers NumArray := NumArray(1,2,3,4,5);
```

Aquí ocurre lo siguiente:

```text
NumArray(1,2,3,4,5)
       ↓
constructor
       ↓
colección con 5 elementos
       ↓
v_numbers
```

También podemos crear una colección vacía:

```sql
v_numbers NumArray := NumArray();
```

Esto significa que la colección existe, pero actualmente contiene cero elementos. citeturn198734search3

> [!warning] Diferencia importante
> Un `VARRAY` declarado pero no inicializado es una **colección NULL**.
>
> Un `VARRAY` inicializado con `Tipo()` es una **colección vacía**.
>
> No son exactamente lo mismo.

---

# 6. Índices de un VARRAY

Los índices de un `VARRAY` comienzan en `1`.

Por ejemplo:

```sql
v_numbers := NumArray(10,20,30,40,50);
```

Tenemos:

```text
Índice    Valor

1         10
2         20
3         30
4         40
5         50
```

Por eso:

```sql
v_numbers(1)
```

devuelve:

```text
10
```

y:

```sql
v_numbers(3)
```

devuelve:

```text
30
```

> [!important]
> En los `VARRAY` de PL/SQL **no se parte desde el índice 0**, sino desde el índice **1**. citeturn198734search0

---

# 7. COUNT

`COUNT` devuelve la cantidad de elementos que contiene actualmente la colección. Para un `VARRAY`, `COUNT` coincide con `LAST`. citeturn198734search2turn198734search24

Ejemplo:

```sql
v_numbers NumArray := NumArray(10,20,30);

DBMS_OUTPUT.PUT_LINE(v_numbers.COUNT);
```

Resultado:

```text
3
```

No devuelve el máximo.

Si tenemos:

```sql
TYPE NumArray IS VARRAY(10) OF NUMBER;
```

y solo guardamos tres elementos:

```sql
NumArray(10,20,30)
```

entonces:

```text
LIMIT = 10
COUNT = 3
```

Este concepto es muy importante.

---

# 8. LIMIT

`LIMIT` devuelve la capacidad máxima declarada del `VARRAY`. Oracle documenta `LIMIT` específicamente como el máximo número de elementos que la colección puede contener. citeturn198734search4

Ejemplo:

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;

v_numbers NumArray := NumArray(10,20,30);
```

Tenemos:

```sql
v_numbers.COUNT
```

→ `3`

y:

```sql
v_numbers.LIMIT
```

→ `5`

Por tanto:

```text
COUNT = elementos actuales
LIMIT = máximo permitido
```

---

# 9. EXTEND

`EXTEND` permite agregar espacio al final de un `VARRAY`.

Ejemplo:

```sql
v_products.EXTEND(2);
```

Si teníamos:

```text
1 → Product A
2 → Product B
3 → Product C
```

Después de:

```sql
v_products.EXTEND(2);
```

tenemos espacio para:

```text
1 → Product A
2 → Product B
3 → Product C
4 → NULL
5 → NULL
```

Luego podemos asignar:

```sql
v_products(4) := 'Product D';
v_products(5) := 'Product E';
```

y queda:

```text
1 → Product A
2 → Product B
3 → Product C
4 → Product D
5 → Product E
```

Oracle documenta `EXTEND` como un método para agregar elementos al final de un `VARRAY` o nested table. citeturn198734search2turn198734search24

> [!important]
> `EXTEND` no debe confundirse con "aumentar el límite máximo".
>
> Si declaramos:
>
> ```sql
> VARRAY(5)
> ```
>
> nunca podremos tener seis elementos simplemente usando `EXTEND`.

---

# 10. TRIM

Aunque en clase el foco estuvo en `EXTEND`, es útil conocer su complemento.

`TRIM` elimina elementos desde el final del `VARRAY`. citeturn198734search2

Ejemplo:

```sql
v_numbers := NumArray(10,20,30,40,50);

v_numbers.TRIM(2);
```

Resultado:

```text
10
20
30
```

Es decir:

```text
COUNT antes = 5
COUNT después = 3
```

---

# 11. Métodos importantes de colecciones

Los métodos que más conviene reconocer en esta etapa son:

| Método | Para qué sirve |
|---|---|
| `COUNT` | Número de elementos actuales |
| `LIMIT` | Máximo de elementos |
| `EXTEND` | Agrega elementos al final |
| `TRIM` | Elimina elementos del final |
| `FIRST` | Primer índice |
| `LAST` | Último índice |
| `EXISTS(n)` | Comprueba si existe un elemento en el índice |
| `PRIOR(n)` | Índice anterior |
| `NEXT(n)` | Índice siguiente |

Oracle documenta estos métodos como operaciones estándar para trabajar con colecciones PL/SQL. citeturn198734search2turn198734search24

Para los ejercicios de esta clase, los más importantes son:

```text
COUNT
LIMIT
EXTEND
```

---

# 12. Recorrer un VARRAY

Un `VARRAY` se puede recorrer utilizando ciclos.

En clase se indicó que podemos utilizar `FOR`, `LOOP` o `WHILE`.

La forma más sencilla para un `VARRAY` es `FOR`.

Ejemplo:

```sql
FOR i IN 1..v_numbers.COUNT
LOOP
    DBMS_OUTPUT.PUT_LINE(v_numbers(i));
END LOOP;
```

Si tenemos:

```text
10
20
30
```

el ciclo hace:

```text
i = 1 → v_numbers(1)
i = 2 → v_numbers(2)
i = 3 → v_numbers(3)
```

---

# 13. ¿Por qué usamos COUNT en el FOR?

Podríamos escribir:

```sql
FOR i IN 1..5
```

pero eso sería incorrecto como práctica general si no sabemos que tenemos exactamente cinco elementos.

Es mejor:

```sql
FOR i IN 1..v_numbers.COUNT
```

porque recorremos solamente los elementos que existen actualmente.

Ejemplo:

```sql
TYPE NumArray IS VARRAY(10) OF NUMBER;

v_numbers NumArray := NumArray(10,20,30);
```

Aquí:

```sql
v_numbers.LIMIT
```

es `10`.

Pero:

```sql
v_numbers.COUNT
```

es `3`.

Por lo tanto:

```sql
FOR i IN 1..v_numbers.COUNT
```

recorre exactamente los tres elementos.

---

# 14. `DBMS_OUTPUT.PUT_LINE`

En los ejercicios se utiliza:

```sql
DBMS_OUTPUT.PUT_LINE(...)
```

Sirve para mostrar información en la salida de PL/SQL.

Ejemplo:

```sql
DBMS_OUTPUT.PUT_LINE('Hola');
```

Resultado:

```text
Hola
```

También podemos concatenar valores:

```sql
DBMS_OUTPUT.PUT_LINE('Cantidad: ' || v_numbers.COUNT);
```

El operador:

```sql
||
```

sirve para concatenar cadenas y valores.

---

# 15. Ejercicio 1 — Números

## Código original de clase

```sql
DECLARE

    -- Declarar VArray
    TYPE NumArray IS VARRAY(5) OF NUMBER;

    -- Declarar variable e inicializar
    v_numbers NumArray := NumArray(1,2,3,4,5);

BEGIN

    DBMS_OUTPUT.PUT_LINE('First number: ' || v_numbers(3));

    DBMS_OUTPUT.PUT_LINE('Total numbers: ' || v_numbers.COUNT);

END;
/
```

## ¿Qué estamos haciendo?

Primero definimos el tipo:

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;
```

Esto crea un tipo que puede almacenar como máximo cinco números.

Después:

```sql
v_numbers NumArray := NumArray(1,2,3,4,5);
```

Creamos una variable `v_numbers` y la inicializamos con cinco valores.

Tenemos:

```text
Índice  Valor

1       1
2       2
3       3
4       4
5       5
```

### Acceso al tercer elemento

```sql
v_numbers(3)
```

Como los índices empiezan en `1`, esto devuelve:

```text
3
```

### Cantidad de elementos

```sql
v_numbers.COUNT
```

devuelve:

```text
5
```

### Resultado esperado

```text
First number: 3
Total numbers: 5
```

## Qué deberías aprender de este ejercicio

Este ejercicio introduce cuatro ideas:

```text
TYPE VARRAY
      ↓
Declaración de variable
      ↓
Constructor
      ↓
Acceso mediante índice + COUNT
```

---

# 16. Ejercicio 2 — VARRAY de texto

## Código original de clase

```sql
DECLARE

    TYPE t_name_type IS VARRAY(2) OF VARCHAR2(20) NOT NULL;

    t_names t_name_type := t_name_type('Juan', 'Pedro');

    t_enames t_name_type := t_name_type();

BEGIN

    DBMS_OUTPUT.PUT_LINE(
        'The numbers of elements in t_names: ' || t_names.COUNT
    );

    DBMS_OUTPUT.PUT_LINE(
        'The numbers of elements in t_enames: ' || t_enames.COUNT
    );

END;
/
```

## ¿Qué cambia respecto al ejercicio 1?

Ahora el tipo es:

```sql
VARRAY(2) OF VARCHAR2(20)
```

Por lo tanto:

```text
Máximo = 2
Tipo   = VARCHAR2
```

Se permiten cadenas de texto de hasta 20 caracteres.

---

## Primera colección

```sql
t_names t_name_type := t_name_type('Juan', 'Pedro');
```

Tenemos:

```text
1 → Juan
2 → Pedro
```

Por eso:

```sql
t_names.COUNT
```

es:

```text
2
```

---

## Segunda colección

```sql
t_enames t_name_type := t_name_type();
```

Aquí utilizamos el constructor sin valores.

La colección está inicializada, pero no contiene elementos.

Por eso:

```sql
t_enames.COUNT
```

es:

```text
0
```

### Idea importante

```text
t_enames = colección vacía
COUNT = 0
```

Esto es diferente de tener una colección no inicializada.

---

## ¿Qué significa `NOT NULL`?

La declaración:

```sql
TYPE t_name_type IS VARRAY(2) OF VARCHAR2(20) NOT NULL;
```

indica que los elementos de la colección no pueden contener `NULL`.

Ejemplo válido:

```sql
t_name_type('Juan', 'Pedro');
```

Pero intentar almacenar un elemento `NULL` no cumple esa restricción.

---

# 17. Ejercicio 3 — `%TYPE`

## Código original de clase

```sql
CREATE TABLE PAIS(
    id_pais NUMBER PRIMARY KEY,
    nombre_pais VARCHAR2(50)
);
```

Después:

```sql
DECLARE

    TYPE varray_paises IS VARRAY(4) OF pais.nombre_pais%TYPE;

    varray_paises_ varray_paises;

    v_elementos NUMBER(3);

BEGIN

    varray_paises_ := varray_paises(
        'Chile',
        'Perú',
        'Argentina',
        'Brasil'
    );

    v_elementos := varray_paises_.COUNT();

    DBMS_OUTPUT.PUT_LINE(
        'Elementos almacenados en el VARRAY: '
    );

    FOR i IN 1..v_elementos
    LOOP
        DBMS_OUTPUT.PUT_LINE(varray_paises_(i));
    END LOOP;

END;
/
```

---

# 18. ¿Qué es `%TYPE`?

Esta parte es especialmente importante:

```sql
pais.nombre_pais%TYPE
```

Significa:

> Utiliza el mismo tipo de dato definido para `pais.nombre_pais`.

La tabla tiene:

```sql
nombre_pais VARCHAR2(50)
```

Por lo tanto:

```sql
pais.nombre_pais%TYPE
```

hereda ese tipo.

Así:

```sql
TYPE varray_paises IS VARRAY(4) OF pais.nombre_pais%TYPE;
```

equivale conceptualmente a:

```sql
TYPE varray_paises IS VARRAY(4) OF VARCHAR2(50);
```

pero con una ventaja: si cambia el tipo de la columna, el código basado en `%TYPE` puede seguir alineado con la definición de la tabla.

---

# 19. Crear la colección

Tenemos:

```sql
varray_paises_ := varray_paises(
    'Chile',
    'Perú',
    'Argentina',
    'Brasil'
);
```

La colección queda:

```text
1 → Chile
2 → Perú
3 → Argentina
4 → Brasil
```

Entonces:

```sql
varray_paises_.COUNT
```

devuelve:

```text
4
```

---

# 20. Guardar COUNT en una variable

La clase utiliza:

```sql
v_elementos NUMBER(3);

v_elementos := varray_paises_.COUNT;
```

Ahora:

```text
v_elementos = 4
```

Esto permite utilizar:

```sql
FOR i IN 1..v_elementos
```

---

# 21. Recorrer el VARRAY

El ciclo:

```sql
FOR i IN 1..v_elementos
LOOP
    DBMS_OUTPUT.PUT_LINE(varray_paises_(i));
END LOOP;
```

funciona así:

```text
i = 1 → Chile
i = 2 → Perú
i = 3 → Argentina
i = 4 → Brasil
```

Resultado:

```text
Elementos almacenados en el VARRAY:
Chile
Perú
Argentina
Brasil
```

### Forma equivalente

También podríamos escribir directamente:

```sql
FOR i IN 1..varray_paises_.COUNT
LOOP
    DBMS_OUTPUT.PUT_LINE(varray_paises_(i));
END LOOP;
```

En este caso no necesitamos la variable intermedia `v_elementos`.

---

# 22. Ejercicio 4 — EXTEND

## Código original de clase

```sql
CREATE TABLE PRODUCTO(
    id_prod NUMBER PRIMARY KEY,
    nombre_prod VARCHAR2(50) NOT NULL
);
```

Después:

```sql
DECLARE

    TYPE ProductArray IS VARRAY(5) OF VARCHAR2(50);

    v_products ProductArray :=
        ProductArray('Product A','Product B','Product C');

BEGIN

    v_products.EXTEND(2);

    v_products(4) := 'Product D';
    v_products(5) := 'Product E';

    FOR i IN 1..v_products.COUNT
    LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Product: ' || i || ':' || v_products(i)
        );
    END LOOP;

END;
/
```

---

# 23. ¿Qué pasa antes de EXTEND?

Inicializamos:

```sql
ProductArray(
    'Product A',
    'Product B',
    'Product C'
);
```

Tenemos:

```text
COUNT = 3
LIMIT = 5
```

Visualmente:

```text
1 → Product A
2 → Product B
3 → Product C
4 → no existe todavía
5 → no existe todavía
```

---

# 24. ¿Qué hace `EXTEND(2)`?

Ejecutamos:

```sql
v_products.EXTEND(2);
```

Ahora agregamos dos elementos al final:

```text
1 → Product A
2 → Product B
3 → Product C
4 → NULL
5 → NULL
```

Y:

```text
COUNT = 5
LIMIT = 5
```

Ahora podemos acceder a:

```sql
v_products(4)
v_products(5)
```

---

# 25. Asignar los nuevos elementos

Después:

```sql
v_products(4) := 'Product D';
v_products(5) := 'Product E';
```

Queda:

```text
1 → Product A
2 → Product B
3 → Product C
4 → Product D
5 → Product E
```

Finalmente:

```sql
FOR i IN 1..v_products.COUNT
```

recorre los cinco elementos.

Resultado:

```text
Product: 1:Product A
Product: 2:Product B
Product: 3:Product C
Product: 4:Product D
Product: 5:Product E
```

---

# 26. Qué sucede si intentamos superar LIMIT

Tenemos:

```sql
TYPE ProductArray IS VARRAY(5) OF VARCHAR2(50);
```

El máximo es:

```text
5
```

Si ya tenemos cinco elementos:

```sql
v_products.EXTEND(1);
```

intentaría superar la capacidad máxima y provocaría un error.

La regla es:

```text
COUNT <= LIMIT
```

Siempre.

---

# 27. Modelo mental del VARRAY

Conviene visualizarlo así:

```text
TYPE
 ↓
VARRAY(5)
 ↓
máximo de 5 elementos

VARIABLE
 ↓
constructor
 ↓
elementos actuales

COUNT
 ↓
cuántos tengo ahora

LIMIT
 ↓
cuántos puedo tener como máximo

EXTEND
 ↓
agrega posiciones al final

TRIM
 ↓
quita posiciones del final

FOR
 ↓
recorre los elementos
```

---

# 28. Errores frecuentes

## Confundir COUNT con LIMIT

Incorrecto conceptualmente:

```text
VARRAY(10)
COUNT = 10
```

Solo sería cierto si realmente tenemos diez elementos.

Correcto:

```text
VARRAY(10)
LIMIT = 10
COUNT = cantidad actual
```

---

## Intentar utilizar índice 0

Esto no corresponde a los `VARRAY` de PL/SQL.

Usamos:

```sql
v_numbers(1)
```

y no:

```sql
v_numbers(0)
```

Los índices comienzan en `1`. citeturn198734search0

---

## Olvidar inicializar la colección

Esto:

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;

v_numbers NumArray;
```

declara la variable, pero no crea todavía una colección utilizable con elementos.

Debemos inicializarla:

```sql
v_numbers := NumArray();
```

o:

```sql
v_numbers := NumArray(1,2,3);
```

---

## Usar EXTEND pensando que aumenta el máximo

Esto es incorrecto:

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;
v_numbers.EXTEND(10);
```

`EXTEND` no cambia el `LIMIT`.

---

## Recorrer hasta LIMIT en vez de COUNT

No conviene:

```sql
FOR i IN 1..v_numbers.LIMIT
```

si solamente tenemos algunos elementos.

Preferible:

```sql
FOR i IN 1..v_numbers.COUNT
```

---

# 29. Lo que entra en la prueba I

Según la información de la clase, la prueba I considera:

- Records.
- VARRAY.
- Cursor implícito.
- Cursor parametrizado.
- Ciclos.
- Ciclos anidados.

> [!important]
> Aunque esta nota se concentra en `VARRAY`, conviene recordar que la evaluación incluye más contenidos.

---

# 30. Relación entre VARRAY y ciclos

El uso del `VARRAY` normalmente se combina con ciclos.

Ejemplo completo:

```sql
DECLARE

    TYPE NumArray IS VARRAY(5) OF NUMBER;

    v_numbers NumArray := NumArray(10,20,30,40,50);

BEGIN

    FOR i IN 1..v_numbers.COUNT
    LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Índice ' || i || ': ' || v_numbers(i)
        );
    END LOOP;

END;
/
```

Aquí se combinan tres elementos:

```text
VARRAY
+
COUNT
+
FOR
```

Esta combinación es muy probable que tengas que poder escribir sin consultar apuntes.

---

# 31. Ejercicios para practicar

Los siguientes ejercicios están diseñados para que practiques exactamente lo trabajado en clase. Se ordenan de menor a mayor dificultad.

## Ejercicio 1 — Números básicos

Crea un `VARRAY` llamado `NumbersArray` con capacidad máxima para 5 números.

Guarda:

```text
10
20
30
40
50
```

Luego muestra:

1. El primer elemento.
2. El tercer elemento.
3. La cantidad total de elementos.

### Objetivo

Practicar:

```text
TYPE
constructor
índice
COUNT
DBMS_OUTPUT
```

---

## Ejercicio 2 — VARRAY vacío

Crea:

```sql
TYPE NameArray IS VARRAY(5) OF VARCHAR2(30);
```

Declara una colección vacía.

Muestra su `COUNT`.

Después inicialízala con:

```text
Ana
Pedro
Juan
```

Vuelve a mostrar `COUNT`.

### Objetivo

Entender la diferencia entre:

```text
colección vacía
colección con elementos
```

---

## Ejercicio 3 — Recorrer con FOR

Crea un `VARRAY(5)` de `VARCHAR2(30)` y almacena cinco nombres.

Recórrelo con:

```sql
FOR i IN 1.....
```

y muestra:

```text
1 - Ana
2 - Pedro
3 - Juan
...
```

### Objetivo

Practicar:

```sql
v_names(i)
v_names.COUNT
FOR
```

---

## Ejercicio 4 — COUNT vs LIMIT

Crea:

```sql
TYPE ProductArray IS VARRAY(10) OF VARCHAR2(30);
```

Inicializa solamente tres productos.

Muestra:

```text
Elementos actuales: X
Capacidad máxima: Y
```

El resultado debería demostrar que:

```text
COUNT = 3
LIMIT = 10
```

### Objetivo

Aprender definitivamente la diferencia entre ambos.

---

## Ejercicio 5 — EXTEND

Crea un `VARRAY(5)` con:

```text
Producto A
Producto B
Producto C
```

Usa:

```sql
EXTEND(2)
```

y agrega:

```text
Producto D
Producto E
```

Finalmente recorre la colección.

### Objetivo

Rehacer prácticamente el ejercicio 4 de clase sin copiarlo.

---

## Ejercicio 6 — Promedio

Crea un `VARRAY(5)` de `NUMBER` que represente notas.

Guarda:

```text
5.5
6.0
4.8
6.2
5.9
```

Usando un ciclo:

1. Recorre las notas.
2. Súmalas.
3. Calcula el promedio.
4. Muestra el resultado.

### Pista

Necesitarás una variable:

```sql
v_sum NUMBER := 0;
```

y dentro del ciclo:

```sql
v_sum := v_sum + v_notes(i);
```

Después:

```sql
v_average := v_sum / v_notes.COUNT;
```

---

## Ejercicio 7 — Buscar un elemento

Crea un `VARRAY(10)` de nombres.

Busca si existe exactamente el nombre:

```text
Pedro
```

Recorre los elementos con un `FOR` y utiliza `IF`.

El programa debe mostrar:

```text
Pedro encontrado
```

o:

```text
Pedro no encontrado
```

### Objetivo

Combinar:

```text
VARRAY
FOR
IF
COUNT
```

---

## Ejercicio 8 — Mayor número

Crea un `VARRAY(10)` de números.

Ejemplo:

```text
15
8
30
22
11
45
17
```

Recorre la colección y encuentra el número mayor.

Resultado:

```text
Mayor: 45
```

### Objetivo

Practicar procesamiento de los elementos de una colección.

---

## Ejercicio 9 — Tabla + `%TYPE`

Crea una tabla:

```sql
CREATE TABLE CIUDAD(
    id_ciudad NUMBER PRIMARY KEY,
    nombre_ciudad VARCHAR2(50)
);
```

Después crea un:

```sql
VARRAY(5)
```

cuyo tipo de dato se obtenga mediante:

```sql
ciudad.nombre_ciudad%TYPE
```

Guarda cinco ciudades chilenas y recórrelas.

### Objetivo

Practicar `%TYPE`.

---

## Ejercicio 10 — EXTEND condicionado

Crea un `VARRAY(5)` inicialmente con dos elementos.

Muestra:

```text
COUNT
LIMIT
```

Luego agrega tres elementos mediante `EXTEND`.

Antes de hacer cada expansión, verifica mediante `IF` que no se supere `LIMIT`.

### Objetivo

Combinar:

```text
COUNT
LIMIT
EXTEND
IF
```

Este ejercicio ya se parece más a una situación de programación real.

---

# 32. Desafío final

> [!challenge] Mini proyecto

Crea un programa PL/SQL que administre las notas de una asignatura.

### Requisitos

Usa un:

```sql
VARRAY(10) OF NUMBER
```

El programa debe:

1. Inicializar al menos cinco notas.
2. Mostrar todas las notas.
3. Mostrar cuántas notas existen.
4. Mostrar el límite máximo.
5. Calcular el promedio.
6. Encontrar la nota mayor.
7. Encontrar la nota menor.
8. Indicar cuántas notas son mayores o iguales a 4.0.
9. Usar al menos un ciclo `FOR`.
10. Usar al menos un `IF`.

### Resultado esperado aproximado

```text
Notas:
1 → 5.5
2 → 6.0
3 → 3.8
4 → 4.5
5 → 6.2

Cantidad de notas: 5
Capacidad máxima: 10
Promedio: 5.2
Nota mayor: 6.2
Nota menor: 3.8
Notas aprobadas: 4
```

> [!tip] No mires la solución inmediatamente
> Intenta construirlo desde cero utilizando solamente esta secuencia mental:
>
> ```text
> TYPE
> ↓
> variable
> ↓
> constructor
> ↓
> COUNT
> ↓
> FOR
> ↓
> IF
> ↓
> resultado
> ```

---

# 33. Solución del desafío: guía, no código

Para resolverlo sin copiar una respuesta completa, divide el problema en variables:

```text
VARRAY → notas
v_sum → suma
v_average → promedio
v_max → máximo
v_min → mínimo
v_approved → aprobados
```

Después:

```text
1. Crear VARRAY
2. Inicializar notas
3. Recorrer con FOR
4. Acumular suma
5. Comparar máximo
6. Comparar mínimo
7. Contar aprobados
8. Calcular promedio
9. Mostrar resultados
```

---

# 34. Chuleta de sintaxis

## Declarar tipo

```sql
TYPE NumArray IS VARRAY(5) OF NUMBER;
```

## Declarar variable

```sql
v_numbers NumArray;
```

## Inicializar vacío

```sql
v_numbers := NumArray();
```

## Inicializar con valores

```sql
v_numbers := NumArray(10,20,30);
```

## Acceder por índice

```sql
v_numbers(1)
```

## Cantidad actual

```sql
v_numbers.COUNT
```

## Capacidad máxima

```sql
v_numbers.LIMIT
```

## Agregar posiciones

```sql
v_numbers.EXTEND(2);
```

## Quitar desde el final

```sql
v_numbers.TRIM(2);
```

## Recorrer

```sql
FOR i IN 1..v_numbers.COUNT
LOOP
    DBMS_OUTPUT.PUT_LINE(v_numbers(i));
END LOOP;
```

## Heredar tipo desde una columna

```sql
tabla.columna%TYPE
```

---

# 35. Resumen final

> [!important] Lo que debes dominar
>
> Un `VARRAY` es una colección ordenada de elementos del mismo tipo con un **máximo de elementos definido al declarar el tipo**.
>
> ```sql
> TYPE Nombre IS VARRAY(5) OF NUMBER;
> ```
>
> Se crea una variable de ese tipo:
>
> ```sql
> v_datos Nombre := Nombre(1,2,3);
> ```
>
> Los índices comienzan en `1`.
>
> ```sql
> v_datos(1)
> ```
>
> `COUNT` indica cuántos elementos existen actualmente.
>
> `LIMIT` indica cuántos elementos puede contener como máximo.
>
> `EXTEND` agrega elementos al final.
>
> `TRIM` elimina elementos del final.
>
> Para recorrerlo:
>
> ```sql
> FOR i IN 1..v_datos.COUNT
> LOOP
>     ...
> END LOOP;
> ```
>
> Y `%TYPE` permite basarse en el tipo de una columna existente:
>
> ```sql
> tabla.columna%TYPE
> ```

---

# 36. Fuentes y complementos

La explicación principal se basa en el material entregado para la clase. fileciteturn2file0L45-L71 fileciteturn2file0L105-L153

Las precisiones técnicas fueron contrastadas con documentación oficial de Oracle:

- Oracle Database — *PL/SQL Collections and Records*: definición y características de `VARRAY`. citeturn198734search0
- Oracle Database — métodos de colecciones (`COUNT`, `LIMIT`, `EXTEND`, `TRIM`, etc.). citeturn198734search2turn198734search24
- Oracle Database — constructores de colecciones. citeturn198734search3
- Oracle Database — `LIMIT` y características de `COUNT`. citeturn198734search4

> [!note]
> En los apuntes se prioriza lo que necesitas para esta clase y para practicar. Se evitó agregar características más avanzadas de colecciones que todavía no aparecen en el material de la clase.

