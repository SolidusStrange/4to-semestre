# Guía de estudio: Oracle SQL y PL/SQL

**Taller de Bases de Datos — Preparación Evaluación Parcial 2**

**Tema:** tablas, consultas, procedimientos almacenados, funciones y paquetes

**Entorno:** Oracle Database / SQL Developer

---

## 1. Cómo usar esta guía

Esta guía funciona como material de estudio y como referencia para resolver ejercicios nuevos. Primero identifica qué pide el enunciado; después utiliza la plantilla correspondiente y adapta los nombres de tablas, columnas y parámetros.

Los ejemplos usan las tablas `CLIENTES`, `EMPLEADOS`, `ORDENES` y `DETALLE_ORDENES`. Si tu profesor entrega un modelo de datos propio, debes respetar sus nombres y tipos de datos; no crees tablas adicionales si no son necesarias. Los ejemplos de procedimientos son independientes: no ejecutes automáticamente cada bloque de principio a fin si ya creaste objetos o insertaste IDs que se repiten.

## 2. Ideas fundamentales

### SQL y PL/SQL

- **SQL** permite crear y modificar estructuras, consultar datos e insertar, actualizar o eliminar registros.
- **PL/SQL** agrega variables, condiciones, ciclos, procedimientos, funciones y manejo de errores a SQL.
- Un **procedimiento** ejecuta una tarea. Puede recibir parámetros y también devolver resultados mediante parámetros `OUT`.
- Una **función** devuelve un valor con `RETURN`.
- Un **paquete** agrupa procedimientos y funciones relacionados bajo un mismo nombre.

### Bloque PL/SQL básico

```sql
DECLARE
    -- Variables locales opcionales
BEGIN
    -- Instrucciones que se ejecutan
EXCEPTION
    -- Manejo opcional de errores
END;
/
```

- `DECLARE`: declara variables locales; se omite si no hacen falta.
- `BEGIN`: inicia las instrucciones ejecutables.
- `EXCEPTION`: permite capturar errores; también es opcional.
- `END;`: cierra el bloque.
- `/`: en SQL Developer/SQL*Plus, ejecuta el bloque almacenado en el búfer como unidad. Se escribe en una línea aparte al ejecutar bloques PL/SQL o definiciones de programas.

### Mostrar mensajes

Antes de probar código que utilice `DBMS_OUTPUT.PUT_LINE`, habilita la salida:

```sql
SET SERVEROUTPUT ON;
```

En SQL Developer también puedes abrir **View / Ver → DBMS Output**, activar el panel para tu conexión y volver a ejecutar el bloque.

```sql
DBMS_OUTPUT.PUT_LINE('Texto que aparecerá en la salida');
```

`DBMS_OUTPUT.PUT_LINE` imprime una línea de texto. No muestra automáticamente el resultado de un `SELECT` ni guarda información en una tabla.

## 3. Recordatorio de SQL: crear tablas y trabajar con datos

### Crear una tabla

```sql
CREATE TABLE CLIENTES (
    ID_CLIENTE NUMBER PRIMARY KEY,
    NOMBRE     VARCHAR2(100) NOT NULL
);
```

- `CREATE TABLE`: crea la estructura.
- `ID_CLIENTE NUMBER`: columna numérica.
- `VARCHAR2(100)`: texto de hasta 100 caracteres.
- `PRIMARY KEY`: identifica de forma única cada fila y no permite valores repetidos ni nulos.
- `NOT NULL`: exige que la columna tenga un valor.
- Cada definición de columna se separa con una coma; la última no lleva coma.

### Crear tablas auxiliares y relacionadas

```sql
CREATE TABLE EMPLEADOS (
    ID_EMPLEADO NUMBER PRIMARY KEY,
    NOMBRE      VARCHAR2(100) NOT NULL,
    SALARIO     NUMBER(10,2)
);

CREATE TABLE ORDENES (
    ID_ORDEN   NUMBER PRIMARY KEY,
    ID_CLIENTE NUMBER,
    CONSTRAINT FK_ORDEN_CLIENTE
        FOREIGN KEY (ID_CLIENTE)
        REFERENCES CLIENTES(ID_CLIENTE)
);

CREATE TABLE DETALLE_ORDENES (
    ID_DETALLE      NUMBER PRIMARY KEY,
    ID_ORDEN        NUMBER,
    CANTIDAD        NUMBER,
    PRECIO_UNITARIO NUMBER(10,2),
    CONSTRAINT FK_DETALLE_ORDEN
        FOREIGN KEY (ID_ORDEN)
        REFERENCES ORDENES(ID_ORDEN)
);
```

Una **clave foránea** relaciona una tabla con otra y protege la integridad de los datos. Por ejemplo, una orden no puede apuntar a un cliente que no existe. Si un detalle está relacionado con una orden, Oracle normalmente impedirá eliminar esa orden mientras existan detalles asociados, salvo que se haya definido una regla como `ON DELETE CASCADE`.

### Insertar un registro

```sql
INSERT INTO CLIENTES (ID_CLIENTE, NOMBRE)
VALUES (1, 'Pedro');
```

Es recomendable indicar los nombres de las columnas. Los valores deben corresponder, en orden, a las columnas indicadas.

### Insertar varios registros

```sql
INSERT INTO CLIENTES (ID_CLIENTE, NOMBRE) VALUES (2, 'Ana');
INSERT INTO CLIENTES (ID_CLIENTE, NOMBRE) VALUES (3, 'Camila');
```

### Consultar datos

```sql
SELECT * FROM CLIENTES;

SELECT NOMBRE
FROM CLIENTES
WHERE ID_CLIENTE = 1;
```

- `SELECT`: indica qué columnas consultar.
- `FROM`: indica la tabla de origen.
- `WHERE`: filtra las filas.
- `SELECT *`: devuelve todas las columnas; es práctico para revisar datos, aunque en consultas definitivas suele ser mejor indicar las columnas necesarias.

### Actualizar datos

```sql
UPDATE EMPLEADOS
SET SALARIO = 800000
WHERE ID_EMPLEADO = 1;
```

**Importante:** sin `WHERE`, el `UPDATE` modifica todos los registros.

### Eliminar datos

```sql
DELETE FROM CLIENTES
WHERE ID_CLIENTE = 3;
```

**Importante:** sin `WHERE`, el `DELETE` intenta eliminar todas las filas. Las claves foráneas pueden impedir una eliminación si hay registros relacionados.

### Confirmar o deshacer cambios

```sql
COMMIT;
ROLLBACK;
```

- `COMMIT` confirma los cambios de la transacción.
- `ROLLBACK` deshace los cambios que todavía no se hayan confirmado.
- Para ejercicios introductorios puede aparecer `COMMIT` dentro de un procedimiento, como en los ejemplos de esta guía. En sistemas reales suele ser preferible que quien llama al procedimiento decida cuándo confirmar o deshacer, porque un `COMMIT` interno limita esa posibilidad.

### Preparar datos de prueba

Este bloque es una muestra para practicar los ejercicios. Ejecútalo después de crear las cuatro tablas. Si ya existen filas con esos IDs, utiliza otros IDs o limpia los datos de prueba antes de volver a ejecutarlo.

```sql
INSERT INTO CLIENTES (ID_CLIENTE, NOMBRE) VALUES (1, 'Pedro');
INSERT INTO CLIENTES (ID_CLIENTE, NOMBRE) VALUES (2, 'Ana');

INSERT INTO EMPLEADOS (ID_EMPLEADO, NOMBRE, SALARIO)
VALUES (1, 'Juan', 500000);
INSERT INTO EMPLEADOS (ID_EMPLEADO, NOMBRE, SALARIO)
VALUES (2, 'María', 650000);

INSERT INTO ORDENES (ID_ORDEN, ID_CLIENTE) VALUES (100, 1);
INSERT INTO ORDENES (ID_ORDEN, ID_CLIENTE) VALUES (101, 1);
INSERT INTO ORDENES (ID_ORDEN, ID_CLIENTE) VALUES (102, 2);

INSERT INTO DETALLE_ORDENES
    (ID_DETALLE, ID_ORDEN, CANTIDAD, PRECIO_UNITARIO)
VALUES (1000, 100, 2, 1500);
INSERT INTO DETALLE_ORDENES
    (ID_DETALLE, ID_ORDEN, CANTIDAD, PRECIO_UNITARIO)
VALUES (1001, 100, 1, 2500);
INSERT INTO DETALLE_ORDENES
    (ID_DETALLE, ID_ORDEN, CANTIDAD, PRECIO_UNITARIO)
VALUES (1002, 101, 3, 1000);

COMMIT;
```

Con estos datos, el cliente 1 tiene dos órdenes y el total de la orden 100 es `2 × 1500 + 1 × 2500 = 5500`.

## 4. Plantilla general de un procedimiento

```sql
CREATE OR REPLACE PROCEDURE NOMBRE_PROCEDIMIENTO (
    P_PARAMETRO IN NUMBER
) AS
    V_VARIABLE NUMBER;
BEGIN
    -- Lógica del procedimiento
    DBMS_OUTPUT.PUT_LINE('Resultado');
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No se encontró el registro.');
END NOMBRE_PROCEDIMIENTO;
/
```

- `CREATE OR REPLACE`: crea el procedimiento o reemplaza su versión anterior.
- `PROCEDURE`: declara que el objeto es un procedimiento almacenado.
- `P_...`: convención frecuente para parámetros.
- `IN`: el procedimiento recibe un valor; es el modo predeterminado.
- `AS` o `IS`: inicia la sección declarativa del procedimiento.
- `V_...`: convención frecuente para variables locales.
- `EXCEPTION`: opcional; permite gestionar errores previsibles.
- `NO_DATA_FOUND`: aparece, por ejemplo, cuando un `SELECT ... INTO` no encuentra ninguna fila.

### Modos de parámetros

| Modo | Para qué sirve | Ejemplo de uso |
|---|---|---|
| `IN` | Entrar un valor al procedimiento o función. | ID de empleado que se desea consultar. |
| `OUT` | Devolver un valor calculado al bloque que llamó al procedimiento. | Promedio calculado. |
| `IN OUT` | Recibir un valor y devolverlo modificado. | Se usa cuando el ejercicio necesita ambas cosas. |

### Ejecutar un procedimiento

```sql
BEGIN
    NOMBRE_PROCEDIMIENTO(123);
END;
/
```

Si tiene varios parámetros, deben pasarse en el orden declarado, salvo que se utilice notación por nombre, como `P_ID => 123`.

## 5. Operadores y construcciones que aparecen en estos ejercicios

| Expresión | Significado |
|---|---|
| `||` | Concatena textos. Ejemplo: `P_NOMBRE || ' ' || P_APELLIDO`. |
| `**` | Eleva a una potencia. Ejemplo: `P_NUMERO ** 2`. |
| `:=` | Asigna un valor a una variable PL/SQL. |
| `=` | Compara valores dentro de condiciones o se usa en el `SET` de un `UPDATE`. |
| `>=` | Mayor o igual que. |
| `COUNT(*)` | Cuenta las filas que cumplen el filtro. |
| `SUM(expresión)` | Suma el resultado de una expresión por cada fila. |
| `NVL(valor, alternativa)` | Si `valor` es `NULL`, devuelve la alternativa. |
| `SELECT ... INTO ...` | Guarda el resultado de una consulta en una o más variables PL/SQL. |
| `WHERE` | Filtra qué filas serán consultadas o modificadas. |
| `RETURN` | Devuelve el resultado de una función y termina su ejecución. |

En Oracle, `COUNT(*)` devuelve cero cuando no hay filas coincidentes. `SUM(...)`, en cambio, puede devolver `NULL` si no encuentra valores; por eso el ejercicio H utiliza `NVL(..., 0)`.

## 6. Ejercicios de la evaluación explicados

### A. Imprimir un mensaje fijo

```sql
CREATE OR REPLACE PROCEDURE HOLA AS
BEGIN
    DBMS_OUTPUT.PUT_LINE('¡Hola, soy alumno de Duoc!');
END;
/

BEGIN
    HOLA;
END;
/
```

**Qué debes entender:** el procedimiento no recibe parámetros ni consulta tablas. Al ejecutarlo, imprime el texto indicado. La definición crea el procedimiento; el segundo bloque lo llama.

**Patrón para nuevos ejercicios:** cuando el enunciado diga “mostrar”, “imprimir” o “escribir un mensaje”, piensa en `DBMS_OUTPUT.PUT_LINE`.

### B. Mostrar nombre completo

```sql
CREATE OR REPLACE PROCEDURE NOMBRE_COMPLETO (
    P_NOMBRE   IN VARCHAR2,
    P_APELLIDO IN VARCHAR2
) AS
BEGIN
    DBMS_OUTPUT.PUT_LINE(P_NOMBRE || ' ' || P_APELLIDO);
END;
/

BEGIN
    NOMBRE_COMPLETO('Simon', 'Gonzalez');
END;
/
```

**Líneas clave:** ambos parámetros son `IN`, porque el procedimiento recibe texto. `VARCHAR2` es el tipo de dato de texto. El operador `||` une el nombre, un espacio y el apellido en una sola cadena.

**Prueba:** reemplaza los argumentos por tu nombre y apellido. Las cadenas de texto se escriben entre comillas simples.

### C. Calcular el cuadrado de un número

```sql
CREATE OR REPLACE PROCEDURE CUADRADO (
    P_NUMERO IN NUMBER
) AS
BEGIN
    DBMS_OUTPUT.PUT_LINE('El cuadrado es: ' || (P_NUMERO ** 2));
END;
/

BEGIN
    CUADRADO(3);
END;
/
```

**Líneas clave:** `NUMBER` recibe valores numéricos; `P_NUMERO ** 2` eleva el valor al cuadrado. Los paréntesis agrupan la operación antes de concatenarla con el texto.

**Idea reutilizable:** el parámetro de entrada es el dato con que se calcula; `PUT_LINE` muestra el resultado, pero no lo guarda.

### D. Insertar un cliente

```sql
CREATE OR REPLACE PROCEDURE INSERTAR_CLIENTE (
    P_ID_CLIENTE IN NUMBER,
    P_NOMBRE     IN VARCHAR2
) AS
BEGIN
    INSERT INTO CLIENTES (ID_CLIENTE, NOMBRE)
    VALUES (P_ID_CLIENTE, P_NOMBRE);
    COMMIT;
END;
/

BEGIN
    INSERTAR_CLIENTE(900, 'Pedro');
END;
/

SELECT * FROM CLIENTES;
```

**Líneas clave:** `INSERT INTO` indica la tabla y las columnas; `VALUES` entrega los datos. Los parámetros permiten reutilizar el procedimiento con distintos clientes. La tabla `CLIENTES` ya se creó en la sección 3, por lo que este ejemplo solo define y ejecuta el procedimiento.

**Atención:** el ID debe ser único. El ejemplo usa el ID 900 para reducir el riesgo de coincidir con los datos de prueba. `CREATE TABLE` se ejecuta una sola vez: volver a ejecutarlo en una tabla existente producirá un error de objeto duplicado.

**Nota sobre transacciones:** el `COMMIT` confirma la inserción de inmediato. En ejercicios puede ser parte de la solución pedida; en aplicaciones reales conviene decidir cuidadosamente dónde confirmar.

### E. Actualizar el salario de un empleado

```sql
CREATE OR REPLACE PROCEDURE ACTUALIZAR_SALARIO (
    P_ID_EMPLEADO  IN NUMBER,
    P_NUEVO_SALARIO IN NUMBER
) AS
BEGIN
    UPDATE EMPLEADOS
    SET SALARIO = P_NUEVO_SALARIO
    WHERE ID_EMPLEADO = P_ID_EMPLEADO;
    COMMIT;
END;
/

BEGIN
    ACTUALIZAR_SALARIO(1, 800000);
END;
/

SELECT * FROM EMPLEADOS;
```

**Líneas clave:** `SET SALARIO = P_NUEVO_SALARIO` asigna el nuevo salario; `WHERE ID_EMPLEADO = P_ID_EMPLEADO` determina a qué empleado actualizar.

**Error típico:** olvidar `WHERE` modifica el salario de todos los empleados. Si el ID no existe, el procedimiento no actualizará ninguna fila; una mejora es revisar `SQL%ROWCOUNT` después del `UPDATE`.

```sql
IF SQL%ROWCOUNT = 0 THEN
    DBMS_OUTPUT.PUT_LINE('No se encontró el empleado.');
ELSE
    DBMS_OUTPUT.PUT_LINE('Salario actualizado.');
END IF;
```

`SQL%ROWCOUNT` debe consultarse inmediatamente después de la sentencia SQL que se quiere revisar.

### F. Eliminar una orden

```sql
CREATE OR REPLACE PROCEDURE ELIMINAR_ORDEN (
    P_ID_ORDEN IN NUMBER
) AS
BEGIN
    DELETE FROM ORDENES
    WHERE ID_ORDEN = P_ID_ORDEN;
    COMMIT;
END;
/

BEGIN
    ELIMINAR_ORDEN(100);
END;
/
```

**Líneas clave:** `DELETE FROM` elimina filas de la tabla y `WHERE` limita la operación a la orden indicada.

**Atención a las relaciones:** si la orden tiene filas en `DETALLE_ORDENES`, la clave foránea puede impedir que se elimine directamente. En ese caso, primero se eliminan los detalles asociados y después la orden, o se diseña explícitamente la clave foránea con `ON DELETE CASCADE`.

```sql
DELETE FROM DETALLE_ORDENES WHERE ID_ORDEN = 100;
DELETE FROM ORDENES WHERE ID_ORDEN = 100;
COMMIT;
```

Antes de borrar, es útil ejecutar `SELECT * FROM ORDENES WHERE ID_ORDEN = 100;` para confirmar qué registro se afectará.

### G. Contar las órdenes de un cliente

```sql
CREATE OR REPLACE PROCEDURE CONTAR_ORDENES (
    P_ID_CLIENTE IN NUMBER
) AS
    V_CANTIDAD NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO V_CANTIDAD
    FROM ORDENES
    WHERE ID_CLIENTE = P_ID_CLIENTE;

    DBMS_OUTPUT.PUT_LINE('Cantidad de ordenes: ' || V_CANTIDAD);
END;
/

BEGIN
    CONTAR_ORDENES(1);
END;
/
```

**Líneas clave:** `V_CANTIDAD` es una variable local. `COUNT(*)` cuenta las órdenes del cliente; `INTO V_CANTIDAD` guarda el resultado para utilizarlo después. `COUNT(*)` devuelve `0` si el cliente no tiene órdenes que coincidan.

**Patrón reutilizable:** para contar registros relacionados con un ID, utiliza `COUNT(*)`, `INTO` y `WHERE` sobre la columna de relación.

### H. Calcular el total de una orden

```sql
CREATE OR REPLACE PROCEDURE TOTAL_ORDEN (
    P_ID_ORDEN IN NUMBER
) AS
    V_TOTAL NUMBER;
BEGIN
    SELECT NVL(SUM(CANTIDAD * PRECIO_UNITARIO), 0)
    INTO V_TOTAL
    FROM DETALLE_ORDENES
    WHERE ID_ORDEN = P_ID_ORDEN;

    DBMS_OUTPUT.PUT_LINE('Total: ' || V_TOTAL);
END;
/

BEGIN
    TOTAL_ORDEN(100);
END;
/
```

**Líneas clave:** `CANTIDAD * PRECIO_UNITARIO` calcula el subtotal de cada detalle; `SUM(...)` suma esos subtotales para la orden; `NVL(..., 0)` devuelve cero si la suma resulta `NULL`; `INTO V_TOTAL` guarda el total.

Con los datos de prueba de esta guía, la orden 100 suma `5500`.

**Detalle importante:** este procedimiento calcula el total con las filas existentes en `DETALLE_ORDENES`. Si el ID de orden no existe o no tiene detalles, la suma se mostrará como cero; la versión básica no distingue esos dos casos.

### I. Consultar el salario actual

```sql
CREATE OR REPLACE PROCEDURE CONSULTAR_SALARIO (
    P_ID_EMPLEADO IN NUMBER
) AS
    V_SALARIO NUMBER;
BEGIN
    SELECT SALARIO
    INTO V_SALARIO
    FROM EMPLEADOS
    WHERE ID_EMPLEADO = P_ID_EMPLEADO;

    DBMS_OUTPUT.PUT_LINE('Salario actual: ' || V_SALARIO);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No existe un empleado con ese ID.');
END;
/

BEGIN
    CONSULTAR_SALARIO(1);
END;
/
```

**Líneas clave:** a diferencia de `COUNT(*)`, esta consulta espera encontrar una fila y guardar `SALARIO` en `V_SALARIO`. Si no encuentra al empleado, `SELECT ... INTO` genera `NO_DATA_FOUND`; el bloque `EXCEPTION` permite responder de una forma controlada.

Si no se incluye la sección `EXCEPTION`, el error se devuelve al entorno de ejecución. El manejo mostrado es una mejora útil para que el procedimiento sea más claro al probarlo.

### J. Aumentar los salarios por un porcentaje

```sql
CREATE OR REPLACE PROCEDURE AUMENTAR_SALARIOS (
    P_PORCENTAJE IN NUMBER
) AS
BEGIN
    UPDATE EMPLEADOS
    SET SALARIO = SALARIO * (1 + P_PORCENTAJE / 100);
    COMMIT;
END;
/

BEGIN
    AUMENTAR_SALARIOS(10);
END;
/
```

**Línea clave:** si el porcentaje es 10, la operación multiplica el salario por `1.10`, es decir, suma un 10 %. Si el salario era 500.000, después del aumento será 550.000.

**Diferencia respecto del ejercicio E:** E actualiza un empleado concreto mediante `WHERE`; J no contiene `WHERE`, por lo que modifica todos los empleados.

**Antes de ejecutar:** usa un `SELECT` para revisar los salarios actuales. Volver a ejecutar el procedimiento aplica otro aumento del 10 % sobre los salarios ya aumentados; no restaura los valores originales.

### K. Paquete PKG_NOTAS: procedimiento y función

El paquete agrupa operaciones que trabajan sobre el mismo tema. Se crea en dos partes: la **especificación** declara lo que otros bloques pueden invocar; el **cuerpo** implementa la lógica.

#### Especificación del paquete

```sql
CREATE OR REPLACE PACKAGE PKG_NOTAS AS
    PROCEDURE SP_CALCULAR_PROMEDIO (
        P_NOTA1   IN NUMBER,
        P_NOTA2   IN NUMBER,
        P_NOTA3   IN NUMBER,
        P_PROMEDIO OUT NUMBER
    );

    FUNCTION FN_ESTADO_ALUMNO (
        P_PROMEDIO IN NUMBER
    ) RETURN VARCHAR2;
END PKG_NOTAS;
/
```

#### Cuerpo del paquete

```sql
CREATE OR REPLACE PACKAGE BODY PKG_NOTAS AS
    PROCEDURE SP_CALCULAR_PROMEDIO (
        P_NOTA1   IN NUMBER,
        P_NOTA2   IN NUMBER,
        P_NOTA3   IN NUMBER,
        P_PROMEDIO OUT NUMBER
    ) AS
    BEGIN
        P_PROMEDIO := (P_NOTA1 + P_NOTA2 + P_NOTA3) / 3;
    END SP_CALCULAR_PROMEDIO;

    FUNCTION FN_ESTADO_ALUMNO (
        P_PROMEDIO IN NUMBER
    ) RETURN VARCHAR2 AS
    BEGIN
        IF P_PROMEDIO >= 4.0 THEN
            RETURN 'APROBADO';
        ELSE
            RETURN 'REPROBADO';
        END IF;
    END FN_ESTADO_ALUMNO;
END PKG_NOTAS;
/
```

#### Probar el paquete

```sql
DECLARE
    V_PROMEDIO NUMBER;
    V_ESTADO   VARCHAR2(20);
BEGIN
    PKG_NOTAS.SP_CALCULAR_PROMEDIO(
        5.0, 6.0, 4.0, V_PROMEDIO
    );

    V_ESTADO := PKG_NOTAS.FN_ESTADO_ALUMNO(V_PROMEDIO);

    DBMS_OUTPUT.PUT_LINE('Promedio: ' || ROUND(V_PROMEDIO, 2));
    DBMS_OUTPUT.PUT_LINE('Estado: ' || V_ESTADO);
END;
/
```

**Qué debes entender:**

- La especificación es la interfaz pública del paquete.
- El cuerpo contiene los cálculos y las condiciones.
- `P_NOTA1`, `P_NOTA2` y `P_NOTA3` son parámetros de entrada (`IN`).
- `P_PROMEDIO OUT NUMBER` permite devolver el promedio al bloque que llamó al procedimiento.
- `:=` asigna a `P_PROMEDIO` el resultado de la operación.
- La función indica `RETURN VARCHAR2` porque devuelve texto.
- `IF ... THEN ... ELSE ... END IF;` decide el estado según el promedio.
- La llamada usa el nombre del paquete como prefijo: `PKG_NOTAS.SP_CALCULAR_PROMEDIO(...)`.

Con las notas 5,0; 6,0 y 4,0, el promedio es 5,0 y el estado es `APROBADO`. No se necesitan tablas para este ejercicio.

## 7. Patrones para reconocer en un enunciado nuevo

| Si el enunciado pide… | Piensa en… |
|---|---|
| Mostrar un texto o resultado | `DBMS_OUTPUT.PUT_LINE(...)` |
| Recibir datos desde quien llama | Parámetros `IN` |
| Calcular un valor y devolvérselo a quien llama | Procedimiento con parámetro `OUT` o una función con `RETURN` |
| Consultar una columna de una fila | `SELECT columna INTO variable ... WHERE ...` |
| Contar registros relacionados | `COUNT(*) INTO variable` |
| Sumar subtotales | `SUM(...)`; considera `NVL(..., 0)` |
| Insertar una fila | `INSERT INTO ... VALUES (...)` |
| Modificar filas existentes | `UPDATE ... SET ... WHERE ...` |
| Eliminar filas | `DELETE FROM ... WHERE ...` |
| Evaluar una condición | `IF condición THEN ... ELSE ... END IF;` |
| Agrupar funciones relacionadas | `PACKAGE` y `PACKAGE BODY` |

## 8. Errores frecuentes y cómo evitarlos

1. **No aparece el mensaje de `DBMS_OUTPUT`.** Habilita `SET SERVEROUTPUT ON` o activa DBMS Output en SQL Developer.
2. **Falta el punto y coma.** La mayoría de las instrucciones PL/SQL terminan en `;`.
3. **Se olvida `/` al final.** Ejecuta la definición del procedimiento, función, paquete o bloque desde SQL Developer/SQL*Plus.
4. **Se ejecuta `CREATE TABLE` varias veces.** Crea la tabla una sola vez o elimina conscientemente la tabla de prueba antes de recrearla.
5. **Se repite una clave primaria.** Verifica los IDs existentes antes de insertar.
6. **Un `UPDATE` o `DELETE` afecta más datos de lo esperado.** Confirma que el `WHERE` esté presente y que use la columna correcta.
7. **`SELECT ... INTO` no devuelve una fila.** Puede generar `NO_DATA_FOUND`; agrega una sección `EXCEPTION` si debes manejar ese caso.
8. **Hay datos relacionados.** La clave foránea puede impedir eliminar primero el registro padre. Revisa y elimina los hijos primero cuando corresponda.
9. **El paquete no compila.** Crea primero la especificación y después el cuerpo; comprueba que nombres, parámetros y tipos coincidan en ambas partes.
10. **Se aumenta el salario varias veces.** Cada ejecución aplica un nuevo aumento al valor actual; usa datos de prueba y verifica antes de ejecutar.
11. **Se confirma una transacción sin querer.** `COMMIT` guarda los cambios; no podrás deshacerlos con `ROLLBACK` después de confirmarlos.

## 9. Lista de comprobación antes de entregar

- [ ] ¿Identifiqué si el ejercicio requiere `PROCEDURE`, `FUNCTION` o `PACKAGE`?
- [ ] ¿Definí todos los parámetros y sus modos (`IN`, `OUT` o `IN OUT`)?
- [ ] ¿Declaré las variables locales que necesito?
- [ ] ¿Cada consulta que entrega un único valor usa `INTO`?
- [ ] ¿El `WHERE` filtra el registro correcto en `UPDATE` y `DELETE`?
- [ ] ¿Consideré claves primarias y foráneas?
- [ ] ¿La salida está escrita mediante `DBMS_OUTPUT.PUT_LINE` cuando corresponde?
- [ ] ¿Activé la salida y probé el procedimiento con un bloque `BEGIN ... END; /`?
- [ ] ¿Revisé el resultado con un `SELECT` cuando el ejercicio modifica tablas?
- [ ] ¿Evité confirmar cambios con `COMMIT` antes de verificar lo que hacen?

## 10. Plantillas rápidas para reutilizar

### Procedimiento que recibe un dato y muestra un cálculo

```sql
CREATE OR REPLACE PROCEDURE CALCULAR (
    P_VALOR IN NUMBER
) AS
    V_RESULTADO NUMBER;
BEGIN
    V_RESULTADO := P_VALOR * 2;
    DBMS_OUTPUT.PUT_LINE('Resultado: ' || V_RESULTADO);
END CALCULAR;
/
```

### Consulta de un valor mediante ID

```sql
CREATE OR REPLACE PROCEDURE CONSULTAR_VALOR (
    P_ID IN NUMBER
) AS
    V_VALOR NUMBER;
BEGIN
    SELECT COLUMNA_NUMERICA
    INTO V_VALOR
    FROM NOMBRE_TABLA
    WHERE ID = P_ID;

    DBMS_OUTPUT.PUT_LINE('Valor: ' || V_VALOR);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No se encontró el registro.');
END CONSULTAR_VALOR;
/
```

Reemplaza `COLUMNA_NUMERICA`, `NOMBRE_TABLA` e `ID` por los nombres reales del ejercicio.

### Insertar usando parámetros

```sql
CREATE OR REPLACE PROCEDURE INSERTAR_REGISTRO (
    P_ID     IN NUMBER,
    P_NOMBRE IN VARCHAR2
) AS
BEGIN
    INSERT INTO NOMBRE_TABLA (ID, NOMBRE)
    VALUES (P_ID, P_NOMBRE);
END INSERTAR_REGISTRO;
/
```

Esta plantilla no incluye `COMMIT` deliberadamente: añade el control transaccional según lo que solicite el ejercicio y las reglas del entorno.

### Función que devuelve un estado

```sql
CREATE OR REPLACE FUNCTION ESTADO (
    P_VALOR IN NUMBER
) RETURN VARCHAR2 AS
BEGIN
    IF P_VALOR >= 4 THEN
        RETURN 'APROBADO';
    ELSE
        RETURN 'REPROBADO';
    END IF;
END ESTADO;
/
```

Una función debe devolver un valor compatible con el tipo indicado después de `RETURN`.

---

## Resumen final

Para resolver estos ejercicios, identifica primero qué operación se necesita. Usa `DBMS_OUTPUT.PUT_LINE` para mostrar resultados; variables locales y `SELECT ... INTO` para guardar valores consultados; `COUNT` para contar; `SUM` para sumar; `INSERT`, `UPDATE` y `DELETE` para modificar tablas; `IN` para recibir datos; `OUT` para devolver resultados de un procedimiento; y `RETURN` para devolver un valor desde una función. En paquetes, declara las rutinas en la especificación e implementa su lógica en el cuerpo.
