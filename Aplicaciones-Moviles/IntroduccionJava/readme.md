Qué debe hacer tu programa
Una interfaz Pagable con un único método: double calcularSueldo();. Es el contrato: "cualquier clase que implemente esto promete saber calcular su propio sueldo".
Empleado es una clase abstracta que extiende Usuario e implementa Pagable, pero no implementa calcularSueldo(): deja esa obligación pendiente para sus clases hijas. Además de sueldoBase, tiene el atributo cargo (String), y declara su propio método abstracto: abstract String tipoContrato();. Como Empleado es abstracta, no se puede hacer new Empleado(...) directamente, solo sus hijas concretas.
EmpleadoComun y EmpleadoVentas extienden Empleado y son las que sí se pueden instanciar, cada una implementando calcularSueldo() y tipoContrato() a su manera:
EmpleadoComun: sueldo = sueldoBase. Tipo de contrato: "Indefinido".
EmpleadoVentas: tiene además comision y metaVentas. Sueldo = sueldoBase + comisión. Tipo de contrato: "Por comisión".
Usuario incluye el atributo rut (String), con su encapsulamiento correcto (atributos private, acceso por métodos) igual que el resto.
Una clase GestorEmpleados administra la colección: guarda la lista de forma privada y expone solo dos comportamientos, agregar un empleado y listarlos todos. Main no toca la lista directamente, mismo encapsulamiento que ya aplicaron en cada atributo privado, ahora sobre una colección completa.
El menú que ya conocen, en bucle (while) hasta que el usuario elija salir, con estas opciones mínimas:
Agregar un empleado (preguntar si es común o de ventas, y pedir todos los datos: nombre, rut, edad, cargo, sueldo base, y si es de ventas también comisión y meta de ventas)
Listar todos los empleados con su sueldo calculado y su tipo de contrato
Salir
Al listar, cada empleado debe resolver dos cosas por su cuenta llamando a calcularSueldo() y a tipoContrato(), sin que el programa necesite preguntar "¿es de ventas o no?" en ningún momento: esa decisión ya la resuelve el polimorfismo solo, dos veces.