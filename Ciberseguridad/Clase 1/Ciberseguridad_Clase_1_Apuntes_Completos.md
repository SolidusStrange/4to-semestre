---
title: "Ciberseguridad — Clase 1"
course: "Ciberseguridad"
topic: "Fundamentos de la seguridad defensiva"
source: "Transcripción de clase"
tags:
  - ciberseguridad
  - seguridad-defensiva
  - blue-team
  - triada-CIA
  - gestion-de-riesgos
  - SOC
  - SIEM
  - EDR
---

# Ciberseguridad — Clase 1

> [!info] Fuente
> Apuntes elaborados a partir de la transcripción de la primera clase de Ciberseguridad. Se eliminaron saludos, conversaciones administrativas y repeticiones propias de una clase grabada, conservando los contenidos técnicos, ejemplos y actividades señaladas por el docente.
>
> Algunas siglas y nombres propios aparecieron deformados por el reconocimiento de voz. Cuando la corrección es evidente, se normalizó la escritura; cuando no lo es, se dejó el concepto señalado como fue explicado en clase.

---

## 1. Contexto de la asignatura

La asignatura comienza trabajando la **ciberseguridad defensiva**. El enfoque inicial es principalmente conceptual y posteriormente incorpora herramientas y actividades prácticas.

Los principales temas anunciados para la asignatura son:

- Introducción a la ciberseguridad defensiva.
- Componentes esenciales de las operaciones de ciberseguridad.
- Sistemas de gestión de alertas y eventos.
- Estrategias de protección.
- Monitoreo y protección proactiva frente a amenazas.
- Normativas, leyes y gobernanza.
- Gestión y respuesta ante incidentes.

El docente señala que la primera parte será más teórica para establecer una base conceptual. Más adelante se trabajarán herramientas y procedimientos prácticos.

---

## 2. Bibliografía y recursos mencionados

La asignatura utiliza, según lo indicado en clase, principalmente:

- **Cisco Cybersecurity Fundamentals**.
- Material de **Security Governance de ISC2**.
- Herramientas de inteligencia artificial disponibles para estudiantes, entre ellas **Gemini** y **NotebookLM**, según las condiciones de acceso indicadas por el docente.

### Gobernanza

La gobernanza se relaciona con:

- Políticas.
- Normativas.
- Continuidad del negocio.
- Continuidad operacional.
- Recuperación ante desastres.
- Respuesta frente a incidentes.
- Estándares nacionales e internacionales.

El docente relaciona estos contenidos con la seguridad defensiva porque las medidas técnicas deben estar respaldadas por políticas, procedimientos y controles.

---

# 3. ¿Qué es la seguridad defensiva?

La seguridad defensiva es un concepto amplio que busca **proteger una organización frente a amenazas y vulnerabilidades**.

Durante la clase aparecieron varias formas de entenderla:

- Evitar o reducir la posibilidad de un ataque.
- Identificar vulnerabilidades.
- Probar sistemas para encontrar fallas.
- Aplicar **hardening**.
- Prevenir vulnerabilidades.
- Monitorear continuamente.
- Prepararse para amenazas provenientes de Internet.
- Trabajar como **Blue Team**.
- Aplicar políticas, normativas y controles.
- Proteger los activos de la organización.

### Idea central

La explicación puede resumirse en:

> **La seguridad defensiva busca proteger los activos y servicios de una organización frente a amenazas, reduciendo vulnerabilidades y detectando, conteniendo y respondiendo ante incidentes.**

El docente la relaciona directamente con la protección de los tres pilares de la ciberseguridad: **confidencialidad, integridad y disponibilidad**.

---

# 4. La tríada CIA

Uno de los conceptos fundamentales de la clase es la llamada **tríada de la ciberseguridad**:

1. **Confidencialidad**
2. **Integridad**
3. **Disponibilidad**

En conjunto, estos tres principios buscan proteger la información y los servicios de una organización.

## 4.1 Confidencialidad

La información debe estar disponible únicamente para las personas que tienen autorización para acceder a ella.

Ejemplo explicado en clase:

Si se crea un archivo y solamente una determinada persona debe poder verlo, los demás usuarios no deberían tener acceso.

Cuando una persona sin autorización consigue acceder a esa información, se compromete la **confidencialidad**.

### Principio de mínimo privilegio

La confidencialidad se relaciona con el **principio del mínimo privilegio**:

> Cada usuario debe disponer solamente de los permisos que necesita para realizar sus funciones.

No se deben entregar privilegios innecesarios.

Ejemplo:

Un profesor que solamente necesita abrir determinadas salas debería tener permiso para esas salas y no para todas las salas de una institución.

Otro problema frecuente es mantener activos los permisos de personas que ya dejaron una organización o cambiaron de función. Esto aumenta la superficie de ataque.

### Objetivo del mínimo privilegio

Reducir los accesos innecesarios y, con ello, reducir la **superficie de ataque**.

---

## 4.2 Integridad

La información debe mantenerse correcta y sin modificaciones no autorizadas.

Si una persona no autorizada puede modificar un archivo, se compromete su integridad.

La clase utiliza como ejemplo un documento:

- Originalmente tiene un determinado contenido.
- Se modifica incluso un detalle pequeño.
- El resultado ya no representa exactamente el mismo archivo.
- El cambio puede detectarse utilizando un **hash**.

### Hash

Un **hash** se explica en clase como el resultado de aplicar un proceso o algoritmo matemático a un archivo.

Conceptualmente:

```text
Archivo → función/algoritmo hash → resultado (hash)
```

Ese resultado funciona como una huella o identificador del contenido.

Si el archivo cambia, aunque el cambio sea pequeño, el hash resultante cambia.

### Ejemplo conceptual

```text
Archivo original
      ↓
   Hash A

Se modifica el archivo
      ↓
   Hash B
```

Como los resultados son diferentes, se puede detectar que el contenido cambió.

### Uso en informática forense

La clase relaciona el uso de hashes con la **informática forense**, donde es importante comprobar que la evidencia digital no haya sido alterada.

> [!important] Para estudiar
> Confidencialidad = quién puede acceder.
>
> Integridad = que la información no sea alterada sin autorización.
>
> Hash = mecanismo matemático utilizado para obtener una representación del contenido que permite detectar modificaciones.

---

## 4.3 Disponibilidad

La información o el servicio debe estar disponible cuando los usuarios autorizados lo necesitan.

El docente utiliza como ejemplo los servicios en la nube: poder acceder a los archivos o servicios desde diferentes dispositivos y en distintos momentos representa disponibilidad.

### SLA

La disponibilidad se relaciona con el **SLA (Service Level Agreement)**, es decir, un acuerdo de nivel de servicio.

En el contrato de un servicio puede establecerse un porcentaje de disponibilidad.

La idea explicada es:

- Un servicio puede comprometer una determinada disponibilidad.
- Ese porcentaje se puede traducir en un tiempo máximo de indisponibilidad.
- Si la interrupción supera el nivel comprometido, pueden existir consecuencias contractuales.

Ejemplo utilizado en clase:

Una disponibilidad de **99,999 %** implica una cantidad muy pequeña de tiempo de indisponibilidad permitida durante un período determinado.

> [!note]
> En la clase se utilizó este concepto para explicar que la disponibilidad también se gestiona mediante compromisos medibles y no solamente como una característica técnica.

---

# 5. ¿Qué protege la tríada?

Proteger confidencialidad, integridad y disponibilidad permite proteger elementos importantes de una organización, entre ellos:

- Datos sensibles.
- Activos de la empresa.
- Continuidad del negocio.
- Servicios.
- Cumplimiento normativo.
- Confianza de los clientes.

La idea es que la ciberseguridad no solamente protege computadores: protege **información, activos y continuidad operacional**.

---

# 6. Normativas, gobernanza y SGSI

La clase relaciona la ciberseguridad defensiva con las leyes y normativas que debe cumplir una organización.

Se mencionan en la clase normas y leyes chilenas, entre ellas la **Ley 21.663**, además de referencias a operadores de importancia vital y servicios esenciales.

> [!warning] Importante
> Las referencias legales y fechas de entrada en vigor deben estudiarse según el material oficial de la asignatura. En esta nota se conserva lo explicado en la transcripción y no se está haciendo una verificación jurídica externa.

## ISO 27001

La clase menciona **ISO/IEC 27001** como estándar relacionado con la gestión de la seguridad de la información.

El objetivo explicado es establecer una estructura organizada para gestionar la seguridad de la información mediante políticas y controles.

## SGSI

**SGSI = Sistema de Gestión de Seguridad de la Información.**

El docente enfatiza que un SGSI:

- No es un software.
- No es un servidor.
- No es una herramienta aislada.
- No es simplemente un servicio.

Es un sistema de gestión que incluye documentación, políticas, controles y procesos destinados a gestionar la seguridad de la información.

En la clase se menciona que el conjunto de controles de ISO 27001 se traduce en políticas que después deben aplicarse realmente en la organización.

### Ejemplos de políticas

Se mencionan políticas relacionadas con:

- Contraseñas.
- Longitud mínima.
- Uso de caracteres.
- Requisitos de seguridad.
- Gestión de activos.
- Protección de información.
- Respaldo de información.
- Accesos.

Una política no debería quedar solamente escrita: debe implementarse, comprobarse y auditarse.

---

# 7. Auditoría y cumplimiento

Las políticas y controles deben comprobarse periódicamente.

La auditoría permite verificar:

- Si las políticas realmente se cumplen.
- Si los controles funcionan.
- Si las medidas de seguridad están correctamente implementadas.
- Si es necesario corregir o mejorar procesos.

La clase destaca que una organización puede tener políticas muy buenas en papel, pero si no se aplican ni verifican, no entregan el mismo nivel de protección.

---

# 8. Defensa en profundidad

La clase presenta la idea de **defensa en profundidad** o seguridad por capas.

Se utiliza la analogía de una **cebolla**, porque existen varias capas de protección.

La idea fundamental es:

> No depender de una sola medida de seguridad.

Si una capa falla, otras capas deberían seguir protegiendo los activos.

## Capas mencionadas

### 1. Seguridad perimetral

Ejemplos:

- Cámaras.
- Controles de acceso físico.
- Lectores de huella.
- Firewall.
- Sistemas de detección de intrusiones.
- Sistemas de prevención de intrusiones.

### 2. Seguridad de networking

Se protegen los elementos de red, como:

- Routers.
- Switches.
- Access Points.
- Otros dispositivos de infraestructura.

Ejemplo de arquitectura simplificada:

```text
Computador
    ↓
  Switch
    ↓
  Router
    ↓
 Firewall
    ↓
 Internet
```

También pueden utilizarse controles como:

- Listas de control de acceso (ACL).
- Sistemas de detección/prevención.
- Políticas de seguridad.

### 3. Seguridad de dispositivos finales

Se aplica seguridad a:

- Computadores.
- Laptops.
- Tablets.
- Servidores y otros endpoints.

Se mencionan plataformas de administración y monitoreo centralizado de endpoints, capaces de mostrar riesgos y amenazas de múltiples equipos.

### 4. Seguridad de aplicaciones

Incluye medidas como:

- Web filtering.
- Restricciones sobre aplicaciones.
- Restricciones sobre protocolos o funcionalidades concretas.

Ejemplo explicado:

Una empresa puede restringir determinadas funcionalidades de una aplicación sin necesariamente bloquear toda la aplicación.

### 5. Seguridad de los datos

Incluye:

- Cifrado.
- Llaves de cifrado.
- Controles de acceso.
- Control de acceso basado en roles.
- Principio del mínimo privilegio.
- Gestión y protección de información.

Todas estas capas deben apoyarse en políticas y controles.

---

# 9. Seguridad basada en riesgos

La seguridad no significa tratar todos los activos exactamente de la misma manera.

Primero se identifican los activos y posteriormente se evalúa su criticidad.

## Activos

Un activo puede ser:

- Información.
- Un servidor.
- Un servicio.
- Equipamiento físico.
- Una base de datos.
- Un sistema.
- Cualquier elemento importante para la organización.

## Priorización

Ejemplo explicado:

Una empresa tiene diez servidores.

Uno de ellos contiene la base de datos principal y su pérdida tendría un impacto enorme. Otro servidor solamente se utiliza para pruebas.

Aunque ambos puedan presentar vulnerabilidades, la empresa debería concentrar primero sus recursos en el activo cuyo riesgo sea más importante.

---

# 10. Matriz de riesgos

Una matriz de riesgos sirve para evaluar y priorizar riesgos.

En la clase se explica de forma simplificada como una relación entre:

**Riesgo = probabilidad × impacto**

La puntuación sirve para saber en qué problemas se deben concentrar primero los recursos.

## Probabilidad

Representa qué tan probable es que ocurra el evento o que un atacante aproveche una vulnerabilidad.

Ejemplo:

Un servidor web publicado en Internet tiene una probabilidad alta de recibir intentos de ataque.

## Impacto

Representa qué consecuencias tendría el incidente para la organización.

Preguntas útiles:

- ¿Cuánta información se perdería?
- ¿Cuánto costaría recuperarse?
- ¿Se interrumpiría el negocio?
- ¿Se afectaría información sensible?
- ¿Cuál sería la consecuencia operacional?

## Ejemplo de la clase

### Servidor de base de datos

- Vulnerabilidad: posibilidad de SQL Injection.
- Probabilidad: alta/probable.
- Impacto: severo.
- Riesgo total: alto.

### Servidor web de pruebas

- Está expuesto y podría recibir ataques.
- Su probabilidad de ataque puede ser alta.
- Sin embargo, si solamente contiene sistemas de prueba, su impacto puede ser bajo.
- Por lo tanto, su riesgo total puede terminar siendo menor.

## Mitigación

Una forma de reducir el riesgo es disminuir la probabilidad.

Ejemplo:

```text
Antes:
Probabilidad alta × Impacto severo
                ↓
           Riesgo alto

Después de aplicar controles:
Probabilidad baja × Impacto severo
                ↓
             Riesgo menor
```

El impacto puede seguir siendo alto si el activo continúa siendo crítico, pero si reducimos la probabilidad de que ocurra el incidente, reducimos el riesgo.

### Colores de la matriz

Se utiliza una representación tipo semáforo:

- 🟢 Verde: riesgo bajo/aceptable.
- 🟠 Naranjo: requiere atención.
- 🔴 Rojo: riesgo crítico y atención prioritaria.

Los colores son una representación visual para facilitar la interpretación de la matriz.

---

# 11. Análisis cuantitativo y cualitativo

La clase diferencia dos formas de analizar el impacto.

## Análisis cuantitativo

Busca expresar el impacto en términos medibles, especialmente económicos.

Ejemplo:

> Recuperar un servidor podría costar $100 millones.

## Análisis cualitativo

Evalúa la importancia del activo o información aunque el costo directo de reemplazarlo sea bajo.

Ejemplo:

Un servidor puede costar relativamente poco, pero contener información crítica que sería extremadamente difícil de reconstruir.

Por eso, un análisis exclusivamente económico puede no reflejar el impacto real.

### Idea clave

> Un activo barato de reemplazar puede contener información extremadamente valiosa.

---

# 12. Inventario de activos

La gestión de riesgos comienza con saber **qué activos existen**.

Por eso es importante mantener un inventario.

El docente muestra una plataforma de prueba donde se registra un activo y automáticamente se genera o incorpora información relacionada con su riesgo.

El concepto importante para estudiar es:

```text
Inventario de activos
        ↓
Identificación de criticidad
        ↓
Evaluación de riesgos
        ↓
Priorización
        ↓
Aplicación de controles
        ↓
Reducción del riesgo
```

---

# 13. Mínimo privilegio y superficie de ataque

El principio del mínimo privilegio vuelve a aparecer como un concepto central.

Una persona no debería conservar permisos que ya no necesita.

Ejemplo:

Si una persona cambia de cargo o deja la empresa:

- Se deben retirar permisos innecesarios.
- Se deben actualizar sus accesos.
- No debería mantener acceso a sistemas antiguos.

### Relación con la superficie de ataque

Cuantos más accesos innecesarios existan, mayor puede ser la superficie disponible para un atacante.

Por eso:

**Menos privilegios innecesarios → menor superficie de ataque.**

---

# 14. Malware

**Malware** es la combinación de *malicious software* y se refiere a software malicioso.

La clase lo describe como código creado para realizar acciones maliciosas.

Se mencionan varios tipos.

## 14.1 Troyano

Un **troyano** se presenta como algo aparentemente legítimo, pero contiene código malicioso.

En la explicación de clase puede utilizarse para:

- Crear una puerta trasera.
- Abrir un puerto.
- Permitir acceso remoto.
- Facilitar posteriormente otras acciones del atacante.

### Idea para recordar

> Troyano = malware que se disfraza o se presenta como algo legítimo para conseguir que la víctima lo ejecute.

---

## 14.2 Spyware

El **spyware** funciona como un software espía.

En clase se presenta como un malware capaz de registrar la actividad del usuario, por ejemplo capturando las teclas que escribe.

### Idea para recordar

> Spyware = espía la actividad del usuario y recopila información.

---

## 14.3 Ransomware

El **ransomware** puede cifrar información y posteriormente exigir un rescate.

La explicación de clase plantea el siguiente escenario:

```text
Ataque
  ↓
Cifrado de archivos
  ↓
La víctima pierde acceso
  ↓
Se exige un rescate
```

También se señala el problema de que pagar no garantiza necesariamente la recuperación de la información.

Además, la información puede ser robada y posteriormente divulgada o vendida.

### Idea para recordar

> Ransomware = malware utilizado para bloquear o cifrar información y exigir un rescate.

---

# 15. Phishing e ingeniería social

El **phishing** se presenta como un concepto general basado en engañar a una persona para lograr que realice una acción que beneficie al atacante.

La clase utiliza la analogía de una pesca:

- Se lanza el mensaje.
- Se espera a que alguien "caiga".
- La víctima que cae permite continuar el ataque.

El phishing puede utilizarse como puerta de entrada para otros tipos de malware o ataques.

Puede combinarse con:

- Troyanos.
- Ransomware.
- Robo de credenciales.
- Ingeniería social.

## Preparación del phishing

La clase relaciona el phishing con:

- Investigación de la víctima.
- Ingeniería social.
- Selección del objetivo.
- Creación de un mensaje convincente.

---

# 16. Tipos de phishing mencionados

## Phishing general

Se dirige a un grupo amplio de personas para aumentar la posibilidad de que alguien caiga.

## Whaling

Ataque dirigido a una persona específica o especialmente importante.

El objetivo puede ser una persona con:

- Alto nivel de responsabilidad.
- Acceso privilegiado.
- Información valiosa.
- Capacidad de autorizar acciones importantes.

## Smishing

Phishing realizado mediante **mensajes de texto/SMS**.

Ejemplo típico explicado:

Un mensaje contiene un enlace que induce al usuario a hacer clic.

Ese enlace puede conducir a una página maliciosa o a la descarga de malware.

## Vishing

Phishing realizado mediante **llamadas telefónicas**.

Ejemplo explicado:

El atacante llama simulando ser una entidad legítima y solicita códigos de seguridad o información.

La víctima puede entregar un código que posteriormente permite al atacante acceder a una cuenta.

> [!important] Diferencia
> **Phishing:** concepto general.
>
> **Whaling:** phishing muy dirigido a una persona importante.
>
> **Smishing:** phishing mediante mensajes de texto.
>
> **Vishing:** phishing mediante llamadas de voz.

---

# 17. Blue Team y Red Team

La clase presenta dos grandes perspectivas:

## Blue Team

Equipo o enfoque **defensivo**.

Funciones asociadas en la clase:

- Proteger sistemas.
- Monitorear.
- Detectar amenazas.
- Responder ante incidentes.
- Aplicar controles.
- Mejorar políticas.
- Reducir riesgos.

## Red Team

Equipo o enfoque de **ataque/prueba**.

Su objetivo, en un contexto autorizado, es poner a prueba las defensas de una organización.

> [!warning]
> Las pruebas de ataque deben realizarse con autorización de la organización y dentro del alcance acordado.

---

# 18. Ataques DDoS

La clase menciona los ataques de **denegación distribuida de servicio (DDoS)**.

El objetivo general es afectar la **disponibilidad** de un servicio.

Una forma explicada consiste en generar grandes cantidades de solicitudes hacia un sistema para que el servicio no pueda responder normalmente.

## Botnet

Una **botnet** es una red de equipos controlados por un atacante.

Los equipos comprometidos pueden convertirse en "zombies" y utilizarse para generar tráfico o realizar acciones sin que el usuario tenga conocimiento completo de lo que está ocurriendo.

### Relación con la disponibilidad

```text
Muchos equipos
      ↓
Muchísimas solicitudes
      ↓
Saturación del servicio
      ↓
Servicio inaccesible
      ↓
Disponibilidad comprometida
```

---

# 19. SQL Injection

La clase presenta **SQL Injection** como un ataque asociado a bases de datos y aplicaciones que no validan o manejan correctamente las entradas.

El escenario general explicado es:

- Existe una aplicación con acceso a una base de datos.
- Una entrada de usuario es procesada de manera insegura.
- El atacante puede introducir instrucciones SQL.
- La aplicación puede ejecutar consultas no previstas.

Esto puede permitir la obtención o manipulación de información, dependiendo de la vulnerabilidad.

### Relación con gestión de riesgos

La clase utiliza SQL Injection como ejemplo de vulnerabilidad que puede ser evaluada en una matriz de riesgos:

```text
Vulnerabilidad
     ↓
Probabilidad de explotación
     +
Impacto sobre el activo
     ↓
Nivel de riesgo
```

---

# 20. Vulnerabilidades y CVE

La clase menciona el identificador **CVE** como mecanismo para identificar vulnerabilidades conocidas.

Cuando aparece una vulnerabilidad nueva que todavía no ha sido conocida o identificada públicamente, se habla de una **vulnerabilidad de día cero (zero-day)**.

## Zero-day

La explicación de clase es:

- La vulnerabilidad no era conocida.
- Se descubre por primera vez.
- No existe todavía un historial previo equivalente para esa vulnerabilidad.
- Puede ser especialmente relevante porque las defensas pueden no estar preparadas.

La clase también relaciona esto con la importancia de reportar vulnerabilidades a los organismos correspondientes.

---

# 21. SOC

**SOC = Security Operations Center / Centro de Operaciones de Seguridad.**

Es el equipo o centro dedicado al monitoreo y operación de seguridad.

Según la explicación de clase, un SOC puede:

- Monitorear amenazas.
- Revisar alertas.
- Detectar ataques.
- Investigar incidentes.
- Escalar incidentes.
- Resolver tickets de seguridad.
- Vigilar eventos en tiempo real.

## Niveles del SOC

La clase presenta cuatro niveles:

1. **SOC N1**
2. **SOC N2**
3. **SOC N3**
4. **Gerencia SOC**

### Flujo de escalamiento

```text
SOC N1
  ↓ si no puede resolver
SOC N2
  ↓ si no puede resolver
SOC N3
  ↓ si es demasiado complejo
Gerencia SOC
```

El N1 corresponde a la primera línea de análisis.

Si no tiene los conocimientos, permisos o experiencia necesarios para resolver un incidente, debe **escalar** el caso al nivel superior correspondiente.

---

# 22. Equipo de gobierno / respuesta documental

La clase diferencia al SOC de otro equipo encargado de aspectos más documentales, procedimentales y de gobernanza.

Este equipo participa en:

- Desarrollo de estrategias.
- Desarrollo de políticas.
- Investigación de incidentes.
- Coordinación de respuestas.
- Procedimientos.
- Documentación.

La idea importante es que los equipos técnicos y de gobernanza trabajan hacia el mismo objetivo, pero cumplen funciones diferentes.

---

# 23. SIEM

La transcripción identifica este concepto con errores en algunos puntos; por contexto de la explicación se trata de un **SIEM**.

**SIEM = Security Information and Event Management.**

La idea explicada es centralizar información de seguridad proveniente de múltiples sistemas.

Sin centralización, un analista tendría que revisar cada dispositivo por separado:

```text
Firewall → logs
Servidor → logs
Router → logs
Switch → logs
IPS → alertas
```

Con un SIEM:

```text
Firewall ─┐
Servidor ─┤
Router ───┤
Switch ───┤
IPS ──────┤
           ↓
          SIEM
           ↓
     Información centralizada
```

Esto permite observar los eventos desde un punto central.

---

# 24. EDR

**EDR = Endpoint Detection and Response.**

Se relaciona principalmente con la detección y respuesta en **dispositivos finales (endpoints)**.

Ejemplo:

Una empresa tiene 100 computadores.

Los equipos pueden registrarse en una plataforma EDR para centralizar información relacionada con:

- Amenazas.
- Riesgos.
- Eventos.
- Estado de los equipos.
- Respuesta ante incidentes.

Esto permite administrar y observar múltiples endpoints desde un lugar centralizado.

---

# 25. Detección basada en reglas

La clase explica que algunos sistemas pueden generar alertas basándose en reglas.

Ejemplo:

Si un usuario normalmente se conecta a las 10:00 y de repente realiza una actividad a las 04:00, una regla puede considerar ese comportamiento como anómalo.

La regla define qué comportamiento debería observarse y cuándo generar una alerta.

---

# 26. Detección por comportamiento

Otra técnica explicada es el análisis de comportamiento.

El sistema puede analizar patrones y detectar comportamientos anómalos.

Ejemplo:

```text
Comportamiento habitual:
Usuario → conexión desde Chile → horario habitual

Nuevo comportamiento:
Usuario → conexión desde Brasil → horario atípico
```

El sistema puede considerar el comportamiento sospechoso y generar una alerta.

---

# 27. Firmas, IDS, IPS y heurística

La clase menciona diferentes mecanismos de detección y prevención:

- **IDS**: sistema de detección de intrusiones.
- **IPS**: sistema de prevención de intrusiones.
- Detección basada en firmas.
- Análisis heurístico.
- Análisis de comportamiento.

La idea general es combinar mecanismos para obtener una detección más completa.

### Firmas

Permiten detectar patrones conocidos.

### Heurística

Busca comportamientos o patrones sospechosos utilizando reglas o criterios.

### Comportamiento

Busca desviaciones respecto de la actividad esperada.

---

# 28. Correlación de eventos

Una función importante de los sistemas de seguridad es relacionar distintos eventos para encontrar patrones.

La clase distingue al menos dos ejemplos:

## Correlación temporal

Analiza los eventos según el tiempo.

Ejemplo:

Un usuario normalmente se conecta a las 03:00, pero un día se conecta a las 04:00.

El cambio de horario puede convertirse en una señal relevante.

## Correlación contextual

Analiza el contexto del evento.

Ejemplo:

Un usuario normalmente se conecta desde Chile, pero aparece una conexión desde Brasil.

La ubicación cambia el contexto del evento y puede aumentar la sospecha.

### Idea clave

> Un evento aislado puede no ser suficiente. Varios eventos relacionados pueden formar un patrón.

---

# 29. Gestión de incidentes

La gestión de incidentes incluye diferentes etapas y actividades.

La clase destaca:

1. Preparación.
2. Monitoreo.
3. Análisis.
4. Contención.
5. Erradicación.
6. Recuperación.
7. Lecciones aprendidas / post-incidente.

---

## 29.1 Preparación

Antes de que ocurra un incidente se debe:

- Planificar.
- Crear políticas.
- Definir respuestas.
- Entrenar al personal.
- Realizar simulaciones.
- Preparar procedimientos.

---

## 29.2 Monitoreo continuo

Implica observar continuamente:

- Sistemas.
- Redes.
- Servicios.
- Eventos.
- Alertas.

El objetivo es identificar anomalías lo antes posible.

---

## 29.3 Análisis del incidente

Una vez detectado un evento, se analiza qué ocurrió y cuál es su impacto.

---

## 29.4 Contención

**Contener = aislar el problema para evitar que se propague.**

Ejemplo:

Si un computador está infectado, puede aislarse de la red para evitar que la amenaza afecte a otros equipos.

```text
Sistema comprometido
        ↓
Aislamiento
        ↓
Contención
```

---

## 29.5 Erradicación

Consiste en eliminar la amenaza.

Ejemplo:

- Eliminar el malware.
- Corregir la vulnerabilidad.
- Aplicar medidas de limpieza y corrección.

---

## 29.6 Recuperación

Después de eliminar la amenaza, se intenta devolver el sistema a un estado normal.

Puede involucrar:

- Restauración.
- Reinstalación.
- Recuperación de respaldos.
- Recuperación de servicios.

---

## 29.7 Lecciones aprendidas

Después del incidente se analiza:

- ¿Por qué ocurrió?
- ¿Qué falló?
- ¿Qué política no se cumplió?
- ¿Qué control falló?
- ¿Qué se debe modificar?
- ¿Qué debe documentarse?
- ¿Qué capacitación hace falta?

El objetivo es evitar repetir el problema.

---

# 30. Playbook y Runbook

La clase distingue dos tipos de documentación.

## Playbook

Indica **qué hacer** ante un escenario o tipo de incidente.

Ejemplo:

> Se detecta un correo malicioso confirmado → se activa el procedimiento correspondiente.

## Runbook

Indica **cómo hacerlo**, de manera más operacional y paso a paso.

Ejemplo de pasos mencionados:

1. Confirmar el correo malicioso mediante análisis.
2. Identificar los usuarios que recibieron el mensaje.
3. Bloquear dominios, URL o remitentes maliciosos mediante las herramientas de seguridad.
4. Retirar o eliminar el correo de los buzones afectados.

### Diferencia clave

> **Playbook = qué hacer.**
>
> **Runbook = cómo hacerlo.**

Ambos trabajan juntos.

---

# 31. Entrenamiento y simulaciones

La capacitación no debe limitarse a teoría.

La clase menciona la realización de:

- Simulacros.
- Pruebas.
- Ejercicios.
- Pentesting autorizado.
- Simulaciones de incidentes.

El objetivo es comprobar:

- Cuánto demora el equipo en reaccionar.
- Qué tan preparado está.
- Cómo se toman decisiones.
- Si las políticas funcionan.
- Si las responsabilidades están claras.

Después del ejercicio pueden aparecer oportunidades de mejora:

```text
Simulación
    ↓
Evaluación del desempeño
    ↓
Detección de fallas
    ↓
Actualización de políticas
    ↓
Capacitación
    ↓
Mejor tiempo de respuesta
```

---

# 32. Controles preventivos

Entre los controles preventivos mencionados están:

- Firewall.
- Políticas de contraseña.
- Autenticación multifactor (MFA).
- Cifrado de datos.

## MFA

La **autenticación multifactor** agrega una capa adicional al proceso de autenticación.

Aunque pueda hacer el inicio de sesión menos cómodo, la clase la presenta como una medida necesaria para aumentar la seguridad.

---

# 33. Inteligencia de amenazas / Threat Intelligence

La identificación de amenazas incluye la recopilación y análisis de información sobre amenazas.

La idea es conocer:

- Qué amenazas existen.
- Qué técnicas utilizan los atacantes.
- Qué activos pueden verse afectados.
- Qué información aparece en logs y sistemas.
- Qué controles se deberían adaptar.

Con esta información se pueden actualizar:

- Políticas.
- Firewalls.
- Controles.
- Procedimientos.
- Defensas.

---

# 34. MITRE ATT&CK

La transcripción reconoce de manera deformada el nombre, pero el concepto descrito corresponde a **MITRE ATT&CK**.

En clase se presenta como una referencia para conocer técnicas utilizadas por los atacantes.

Se mencionan ejemplos de categorías como:

- Reconocimiento.
- Acceso mediante credenciales.
- Descubrimiento.
- Escalación de privilegios.
- Movimiento lateral.

La utilidad para el enfoque defensivo es conocer cómo actúan los atacantes para poder desarrollar mejores medidas de detección y protección.

> [!important]
> La idea que se debe recordar es que conocer técnicas de ataque sirve para diseñar y mejorar defensas.

---

# 35. OWASP Top 10

La clase recomienda **OWASP** para el estudio de la seguridad de aplicaciones web.

Se menciona específicamente **OWASP Top 10**, presentado como una referencia sobre riesgos y ataques relevantes en aplicaciones web.

### Para estudiar

```text
Aplicaciones web
      ↓
Riesgos y vulnerabilidades
      ↓
OWASP
      ↓
OWASP Top 10
```

---

# 36. Actualización continua de las defensas

Un punto importante de la clase es que una defensa que funcionaba anteriormente puede dejar de ser suficiente porque las técnicas de ataque evolucionan.

Esto no significa necesariamente que la defensa haya sido implementada mal.

Puede ocurrir:

```text
Defensa implementada correctamente
            ↓
Nuevo método de ataque
            ↓
La defensa anterior ya no es suficiente
            ↓
Se actualizan controles y políticas
```

Por eso la seguridad es un proceso continuo.

### Dos escenarios distintos

**1. La defensa estaba mal implementada**

Existe una falla atribuible a la implementación.

**2. La defensa estaba correctamente implementada, pero aparece una técnica nueva**

La organización debe actualizar sus controles y adaptarse al nuevo escenario.

La clase enfatiza la importancia de las revisiones y actualizaciones periódicas.

---

# 37. Revisión y actualización de políticas

La seguridad debe revisarse continuamente.

Se deben considerar:

- Nuevas amenazas.
- Nuevas vulnerabilidades.
- Cambios tecnológicos.
- Incidentes anteriores.
- Resultados de auditorías.
- Resultados de simulaciones.

Esto permite modificar y mejorar políticas.

---

# 38. Relación entre conceptos

Una forma útil de conectar los contenidos de la clase es:

```text
Activos
   ↓
Identificación de amenazas y vulnerabilidades
   ↓
Evaluación de probabilidad e impacto
   ↓
Matriz de riesgos
   ↓
Priorización
   ↓
Controles de seguridad
   ↓
Monitoreo
   ↓
Detección
   ↓
Respuesta
   ↓
Recuperación
   ↓
Lecciones aprendidas
   ↓
Actualización de defensas
```

Todo el proceso busca proteger:

```text
Confidencialidad
Integridad
Disponibilidad
```

---

# 39. Ejemplo integrador

Supongamos que una empresa tiene un servidor de base de datos con información sensible.

### Paso 1: Identificación

El servidor se registra como activo crítico.

### Paso 2: Identificación de vulnerabilidad

Se detecta una posible vulnerabilidad relacionada con SQL Injection.

### Paso 3: Evaluación

Se evalúa:

- Probabilidad de explotación.
- Impacto para la empresa.

### Paso 4: Priorización

El riesgo obtiene una puntuación alta.

### Paso 5: Controles

Se aplican medidas de seguridad para reducir la probabilidad de explotación.

### Paso 6: Monitoreo

Se monitorean eventos y alertas.

### Paso 7: Detección

Un sistema puede generar una alerta ante un comportamiento sospechoso.

### Paso 8: Respuesta

El SOC analiza el incidente.

### Paso 9: Contención

Se aísla el sistema afectado si corresponde.

### Paso 10: Erradicación

Se elimina la amenaza y se corrige la causa.

### Paso 11: Recuperación

Se restaura el servicio y/o información.

### Paso 12: Lecciones aprendidas

Se revisa lo sucedido y se actualizan los controles.

---

# 40. Evaluación de la primera experiencia / actividad

La actividad presentada al final de la clase plantea casos prácticos relacionados con los conceptos estudiados.

## Caso 1: Ataque de denegación de servicios

Escenario:

Una empresa mediana ofrece:

- Desarrollo de software.
- Almacenamiento en la nube.
- Servicios a clientes a nivel nacional.

Tiene:

- Servidores web.
- Bases de datos.
- Almacenamiento distribuido.

La empresa sufre un ataque de **denegación de servicios**.

Durante el ataque:

- Los servidores reciben un volumen alto de tráfico.
- El tráfico proviene de múltiples direcciones IP.
- Algunos servicios de nube quedan inaccesibles.
- Los clientes no pueden acceder a archivos.
- Las aplicaciones web dejan de funcionar.
- El equipo de TI implementa medidas de mitigación.
- Los servicios se recuperan posteriormente.
- El equipo continúa monitoreando el tráfico.

### Preguntas de la actividad

1. ¿Cuál de los tres atributos principales de la seguridad fue afectado?
   - Confidencialidad.
   - Integridad.
   - Disponibilidad.

2. Justificar la respuesta basándose en la descripción del ataque.

3. Proponer estrategias para mitigar el riesgo de un ataque similar en el futuro.

### Concepto que se está evaluando

Un DDoS afecta principalmente la **disponibilidad**, porque impide que los usuarios accedan normalmente al servicio.

---

## Caso 2: Detección y respuesta

El segundo caso solicita analizar:

1. Qué atributos de la ciberseguridad fueron afectados.
2. Justificar la respuesta.
3. Proponer estrategias de mitigación.
4. Identificar qué herramienta o plataforma debería generar o gestionar la alerta para el analista.
5. Considerar herramientas como:
   - SIEM.
   - EDR.
   - IDS.
   - IPS.
   - Firewall.
6. Determinar qué debe hacer un analista SOC N1 si no tiene los conocimientos o experiencia necesarios para resolver el incidente.

### Concepto que se está evaluando

Si SOC N1 no puede resolver el incidente, debe **escalarlo al nivel superior correspondiente**, comenzando por SOC N2.

---

# 41. Conceptos que conviene memorizar

> [!tip] Lista rápida de estudio

| Concepto | Idea principal |
|---|---|
| Seguridad defensiva | Proteger sistemas, información y servicios frente a amenazas |
| Blue Team | Enfoque/equipo defensivo |
| Red Team | Pruebas de ataque autorizadas |
| Confidencialidad | Solo acceden los autorizados |
| Integridad | La información no debe alterarse sin autorización |
| Disponibilidad | El servicio debe estar disponible cuando se necesita |
| Mínimo privilegio | Cada usuario recibe solo los permisos necesarios |
| Hash | Resultado de un proceso matemático usado para identificar cambios en un archivo |
| SLA | Acuerdo sobre el nivel de servicio, incluida la disponibilidad |
| Defensa en profundidad | Protección mediante múltiples capas |
| Matriz de riesgos | Prioriza riesgos mediante probabilidad e impacto |
| Malware | Software malicioso |
| Troyano | Malware disfrazado de software legítimo |
| Spyware | Malware que espía y recopila actividad |
| Ransomware | Malware que cifra/bloquea información y exige rescate |
| Phishing | Engaño para conseguir información o provocar una acción |
| Whaling | Phishing dirigido a una persona importante |
| Smishing | Phishing mediante SMS/mensajes |
| Vishing | Phishing mediante llamadas |
| DDoS | Ataque distribuido para afectar la disponibilidad |
| SQL Injection | Ataque relacionado con consultas SQL introducidas mediante entradas inseguras |
| CVE | Identificador de vulnerabilidades conocidas |
| Zero-day | Vulnerabilidad nueva/desconocida previamente |
| SOC | Centro/equipo de operaciones de seguridad |
| SIEM | Centralización y análisis de información de eventos de seguridad |
| EDR | Detección y respuesta en endpoints |
| IDS | Detección de intrusiones |
| IPS | Prevención de intrusiones |
| Playbook | Qué hacer ante un incidente |
| Runbook | Cómo ejecutar el procedimiento |
| Contención | Aislar para evitar propagación |
| Erradicación | Eliminar la amenaza |
| Recuperación | Restaurar el sistema/servicio |
| OWASP Top 10 | Referencia de riesgos importantes en aplicaciones web |
| MITRE ATT&CK | Referencia para técnicas y tácticas utilizadas por atacantes |

---

# 42. Diferencias que pueden aparecer en una prueba

## Confidencialidad vs. Integridad

**Confidencialidad:** alguien no autorizado consigue ver la información.

**Integridad:** alguien no autorizado modifica la información.

## Integridad vs. Disponibilidad

**Integridad:** la información sigue siendo correcta y no alterada.

**Disponibilidad:** el servicio o información está accesible cuando se necesita.

## Playbook vs. Runbook

**Playbook:** qué hacer.

**Runbook:** cómo hacerlo.

## SOC N1 vs. N2

**N1:** primera línea de análisis y respuesta.

**N2:** nivel superior al que se escala cuando N1 no puede resolver el incidente.

## Troyano vs. Spyware

**Troyano:** busca engañar al usuario y puede proporcionar una puerta de entrada o acceso.

**Spyware:** espía y recopila actividad/información del usuario.

## Phishing vs. Smishing vs. Vishing vs. Whaling

**Phishing:** concepto general.

**Smishing:** mediante mensajes.

**Vishing:** mediante llamadas.

**Whaling:** ataque altamente dirigido a una persona de interés.

---

# 43. Preguntas de autoevaluación

Estas preguntas permiten repasar los contenidos trabajados en la clase.

1. ¿Cuáles son los tres pilares de la tríada CIA?
2. ¿Qué significa confidencialidad?
3. ¿Qué significa integridad?
4. ¿Qué significa disponibilidad?
5. ¿Qué es el principio del mínimo privilegio?
6. ¿Por qué el mínimo privilegio reduce la superficie de ataque?
7. ¿Qué es un hash?
8. ¿Para qué puede utilizarse un hash en informática forense?
9. ¿Qué es un SLA?
10. ¿Qué significa defensa en profundidad?
11. ¿Cuáles son las principales capas de seguridad mencionadas en clase?
12. ¿Qué es una matriz de riesgos?
13. ¿Cómo se relacionan probabilidad e impacto con el riesgo?
14. ¿Por qué no todos los activos deben recibir exactamente los mismos recursos de seguridad?
15. ¿Cuál es la diferencia entre análisis cuantitativo y cualitativo?
16. ¿Qué es malware?
17. ¿Cuál es la diferencia entre un troyano y spyware?
18. ¿Qué hace un ransomware?
19. ¿Qué es phishing?
20. ¿Qué diferencia existe entre smishing y vishing?
21. ¿Qué es whaling?
22. ¿Qué es una botnet?
23. ¿Por qué un DDoS afecta principalmente la disponibilidad?
24. ¿Qué es SQL Injection?
25. ¿Qué es un CVE?
26. ¿Qué es una vulnerabilidad zero-day?
27. ¿Qué función cumple un SOC?
28. ¿Qué ocurre si SOC N1 no puede resolver un incidente?
29. ¿Qué función cumple un SIEM?
30. ¿Qué función cumple un EDR?
31. ¿Qué diferencia hay entre IDS e IPS?
32. ¿Qué es correlación temporal?
33. ¿Qué es correlación contextual?
34. ¿Qué significa contención?
35. ¿Qué significa erradicación?
36. ¿Qué significa recuperación?
37. ¿Qué se hace durante las lecciones aprendidas?
38. ¿Cuál es la diferencia entre playbook y runbook?
39. ¿Para qué sirven los simulacros de incidentes?
40. ¿Qué es OWASP Top 10?
41. ¿Qué representa MITRE ATT&CK para el trabajo defensivo?
42. ¿Por qué las políticas de seguridad deben actualizarse continuamente?

---

# 44. Resumen final de la clase

La primera clase establece que la **ciberseguridad defensiva** consiste en proteger los activos, sistemas, servicios e información de una organización frente a amenazas.

El fundamento es la **tríada CIA**:

```text
             Ciberseguridad
                   │
       ┌───────────┼───────────┐
       ↓           ↓           ↓
Confidencialidad  Integridad  Disponibilidad
```

La protección se aplica mediante **defensa en profundidad**, utilizando múltiples capas y controles.

La organización debe identificar sus activos y evaluar sus riesgos utilizando **probabilidad e impacto**, de manera que los recursos se concentren primero en los activos más críticos.

Las políticas y controles deben estar documentados, implementados, auditados y actualizados.

Cuando ocurre un incidente, se aplican procesos de **detección, contención, erradicación y recuperación**, seguidos por la revisión de **lecciones aprendidas**.

En el ámbito operacional, equipos como el **SOC** realizan monitoreo, análisis y respuesta, apoyándose en herramientas como **SIEM, EDR, IDS e IPS**.

Finalmente, la defensa debe evolucionar porque las técnicas de ataque también evolucionan.

> [!important] Idea central para recordar
> **Identificar activos → evaluar riesgos → aplicar controles → monitorear → detectar → responder → recuperar → aprender → mejorar.**

---

## Nota sobre la transcripción

Algunos términos aparecieron deformados por el reconocimiento automático de voz. Entre los casos más evidentes se encuentran nombres de herramientas, siglas y conceptos técnicos. Para los apuntes se utilizó la terminología técnica más clara cuando el significado era inequívoco, especialmente en casos como:

- SIEM.
- EDR.
- IDS / IPS.
- OWASP.
- MITRE ATT&CK.
- Playbook / Runbook.
- Phishing / Smishing / Vishing / Whaling.
- DDoS.
- CVE.
- ISO 27001.
- SGSI.

Las afirmaciones legales y normativas se mantienen como contenidos de la clase y no han sido verificadas externamente en este documento.


# Complemento y verificación técnica — 2026

> [!info] Cómo leer esta sección
> Esta sección agrega precisión técnica y contexto actualizado a los contenidos de la clase. Cuando existe una diferencia entre una explicación coloquial de la clase y una definición formal, para estudiar conviene utilizar la definición técnica indicada aquí.

## 1. Actualización: NIST Cybersecurity Framework 2.0

La clase utiliza como idea general el ciclo **Identify → Protect → Detect → Respond → Recover**. Esa estructura corresponde al enfoque clásico del NIST CSF 1.1.

Actualmente, **NIST Cybersecurity Framework (CSF) 2.0** incorpora una sexta función: **Govern**.

```text
GOVERN
   ↓
IDENTIFY
   ↓
PROTECT
   ↓
DETECT
   ↓
RESPOND
   ↓
RECOVER
```

La versión 2.0 fue publicada el 26 de febrero de 2024 y está pensada para organizaciones de cualquier tamaño y sector. NIST define las seis funciones como: **Govern, Identify, Protect, Detect, Respond y Recover**.

Esto conecta directamente con la clase porque la gobernanza reúne políticas, roles, responsabilidades, estrategia y gestión del riesgo.

**Fuente:** [NIST — CSF 2.0](https://www.nist.gov/publications/nist-cybersecurity-framework-csf-20)

## 2. Actualización: respuesta a incidentes

La clase presenta una secuencia de preparación, detección, análisis, contención, erradicación, recuperación y lecciones aprendidas.

El complemento actual de NIST es **SP 800-61 Rev. 3**, publicado en abril de 2025. Esta revisión reemplazó a SP 800-61 Rev. 2 y alinea la respuesta a incidentes con CSF 2.0.

La idea importante es que la respuesta a incidentes no debe verse como una actividad aislada después de un ataque. Forma parte de la gestión global del riesgo.

**Fuente:** [NIST SP 800-61 Rev. 3](https://csrc.nist.gov/pubs/sp/800/61/r3/final)

## 3. Precisión sobre la tríada CIA

NIST utiliza las propiedades de **confidencialidad, integridad y disponibilidad** para caracterizar aspectos centrales de la seguridad de la información.

### Confidencialidad

Evitar divulgación o acceso no autorizado.

### Integridad

Evitar modificación o destrucción no autorizada y preservar la exactitud/confiabilidad de la información.

### Disponibilidad

Garantizar que la información o servicio sea accesible y utilizable cuando una entidad autorizada lo necesita.

**Fuente:** [NIST Cybersecurity Glossary](https://csrc.nist.gov/glossary)

## 4. Precisión sobre hashes

Un hash criptográfico transforma una entrada de longitud arbitraria en una salida de longitud fija.

No debe memorizarse como un identificador matemáticamente imposible de repetir. Existen teóricamente colisiones; los algoritmos criptográficos adecuados buscan que encontrarlas sea computacionalmente inviable.

Para estudiar:

```text
Datos
  ↓
Función hash
  ↓
Digest / hash
```

El hash permite comparar representaciones del contenido y detectar modificaciones, pero **no es cifrado** y no está diseñado para recuperar el contenido original.

**Fuente:** [NIST — Cryptographic Hash Function](https://csrc.nist.gov/glossary/term/Cryptographic_hash_function)

## 5. Precisión sobre mínimo privilegio

NIST define **least privilege** como el principio de entregar a usuarios o procesos solamente los privilegios mínimos necesarios para realizar sus funciones.

Relación para memorizar:

```text
Menos privilegios innecesarios
             ↓
Menor superficie de ataque
             ↓
Menor impacto potencial de una cuenta comprometida
```

**Fuente:** [NIST — Least Privilege](https://csrc.nist.gov/glossary/term/least_privilege)

## 6. Precisión sobre SIEM

**SIEM = Security Information and Event Management.**

Su función central es recopilar y presentar datos de seguridad provenientes de distintas fuentes para facilitar análisis, correlación y respuesta.

```text
Firewall ─┐
Servidor ─┤
Router ───┤
IDS/IPS ──┤──→ SIEM → Analista/SOC
EDR ──────┘
```

**Fuente:** [NIST — SIEM Tool](https://csrc.nist.gov/glossary/term/Security_Information_and_Event_Management_Tool)

## 7. Precisión sobre EDR

**EDR = Endpoint Detection and Response.**

El foco está en los endpoints: estaciones de trabajo, laptops, servidores u otros dispositivos administrados, dependiendo de la solución.

Un EDR permite observar actividad del endpoint y apoyar la detección e investigación, además de aplicar acciones de respuesta según las capacidades del producto.

**Fuente:** [NIST — Endpoint Detection and Response](https://csrc.nist.gov/glossary/term/endpoint_detection_and_response)

## 8. IDS vs. IPS

### IDS

Detecta actividad que puede ser intrusiva o maliciosa y genera una alerta.

### IPS

Además de detectar, puede intentar detener la actividad antes de que alcance su objetivo.

```text
IDS → detectar + alertar
IPS → detectar + actuar preventivamente
```

**Fuente:** [NIST — IDPS](https://csrc.nist.gov/glossary/term/intrusion_detection_and_prevention_system)

## 9. DDoS

NIST define DDoS como una técnica de denegación de servicio que utiliza numerosos hosts para realizar el ataque.

El objetivo típico es afectar la disponibilidad:

```text
Muchos hosts
     ↓
Muchas solicitudes/tráfico
     ↓
Sobrecarga
     ↓
Servicio degradado o inaccesible
```

**Fuente:** [NIST — DDoS](https://csrc.nist.gov/glossary/term/ddos)

## 10. Phishing, smishing, vishing y whaling

CISA define phishing como una forma de ingeniería social en la que un actor malicioso se hace pasar por una entidad confiable para inducir a la víctima a entregar información, acceso o ejecutar acciones peligrosas.

| Concepto | Significado |
|---|---|
| Phishing | Concepto general de engaño |
| Spearphishing | Phishing dirigido a un individuo/grupo específico |
| Whaling | Phishing dirigido a una persona de alto perfil |
| Smishing | Phishing mediante mensajes de texto |
| Vishing | Phishing mediante voz |

**Fuente:** [CISA — Phishing](https://www.cisa.gov/topics/cyber-threats-and-advisories/phishing)

## 11. Precisión sobre CVE

**CVE = Common Vulnerabilities and Exposures.**

El programa CVE identifica, define y cataloga vulnerabilidades de ciberseguridad divulgadas públicamente.

Un **CVE ID** es un identificador alfanumérico para una vulnerabilidad específica.

Ejemplo:

```text
CVE-2026-12345
```

Importante: **CVE no significa "vulnerabilidad expuesta"** y una vulnerabilidad sin CVE no se convierte automáticamente en una zero-day.

**Fuentes:**
- [CVE Program](https://www.cve.org/)
- [CVE Process](https://www.cve.org/about/Process)

## 12. Zero-day

Una vulnerabilidad zero-day es una vulnerabilidad para la cual el defensor no dispone todavía de una solución ampliamente disponible en el momento crítico en que aparece o es explotada.

No debe estudiarse como sinónimo de “sin CVE”. Una vulnerabilidad puede recibir un CVE posteriormente.

## 13. OWASP Top 10: versión vigente

La clase menciona OWASP Top 10 como referencia para aplicaciones web.

A agosto de 2026, la referencia vigente es **OWASP Top 10:2025**.

Las categorías son:

1. Broken Access Control
2. Security Misconfiguration
3. Software Supply Chain Failures
4. Cryptographic Failures
5. Injection
6. Insecure Design
7. Authentication Failures
8. Software or Data Integrity Failures
9. Security Logging & Alerting Failures
10. Mishandling of Exceptional Conditions

No conviene memorizar que OWASP Top 10 significa “los diez ataques más utilizados”. Es un documento de concienciación que representa los riesgos más críticos para aplicaciones web.

**Fuente:** [OWASP Top 10:2025](https://owasp.org/Top10/2025/)

## 14. MITRE ATT&CK

MITRE ATT&CK es una base de conocimiento de tácticas y técnicas de adversarios basada en observaciones del mundo real.

Para la defensa, es útil para pensar en:

```text
Táctica → ¿qué busca conseguir el atacante?
Técnica → ¿cómo intenta conseguirlo?
```

Ejemplos de tácticas Enterprise incluyen:

- Initial Access.
- Execution.
- Persistence.
- Privilege Escalation.
- Credential Access.
- Discovery.
- Lateral Movement.
- Collection.
- Command and Control.
- Exfiltration.
- Impact.

**Fuente:** [MITRE ATT&CK — Enterprise](https://attack.mitre.org/tactics/enterprise/)

## 15. Chile: Ley 21.663

La **Ley N.º 21.663, Ley Marco de Ciberseguridad**, establece la institucionalidad y el marco general chileno en materia de ciberseguridad.

### ANCI

**ANCI = Agencia Nacional de Ciberseguridad.**

Esta es una corrección importante respecto de la transcripción, donde aparece repetidamente “ANSI”.

### CSIRT Nacional

La ley establece un **CSIRT Nacional** encargado, entre otras funciones, de responder ante ciberataques o incidentes de efecto significativo y coordinar actividades relacionadas con incidentes a escala nacional.

### Servicios esenciales y OIV

La ley contempla instituciones que prestan **servicios esenciales** y aquellas calificadas como **operadores de importancia vital (OIV)**.

### Reporte de incidentes

Las instituciones comprendidas en el ámbito de aplicación tienen obligaciones de reporte para determinados incidentes significativos. La ley establece una alerta temprana dentro de un máximo de **3 horas** desde que se tiene conocimiento de un ciberataque o incidente que pueda tener impactos significativos.

**Fuentes:**
- [BCN — Ley 21.663](https://www.bcn.cl/leychile/navegar?idNorma=1202434)
- [BCN — Artículo 9 y deber de reportar](https://www.bcn.cl/leychile/navegar?i=1202434)

> [!warning] No generalizar
> Estas obligaciones legales aplican según el ámbito definido por la ley y sus reglamentos. No se debe estudiar como “toda persona que encuentre una vulnerabilidad debe reportarla directamente a ANCI” sin considerar el contexto jurídico.

## 16. ISO/IEC 27001 y SGSI

**ISO/IEC 27001:2022** especifica requisitos para un **Sistema de Gestión de Seguridad de la Información (SGSI)**.

Un SGSI integra personas, procesos, políticas, controles y tecnología para gestionar riesgos de seguridad de la información y mejorar continuamente.

```text
Contexto + riesgos
       ↓
SGSI
       ↓
Políticas + controles
       ↓
Implementación
       ↓
Evaluación
       ↓
Mejora continua
```

No es correcto estudiar ISO/IEC 27001 como una herramienta de software ni como una “norma americana”. Es un estándar internacional.

**Fuente:** [ISO/IEC 27001:2022](https://www.iso.org/es/norma/27001)

## 17. Conceptos que deben diferenciarse

| Concepto A | Concepto B | Diferencia |
|---|---|---|
| Confidencialidad | Integridad | Quién puede acceder vs. si la información fue alterada |
| Integridad | Disponibilidad | Información confiable vs. servicio accesible |
| IDS | IPS | Detecta vs. detecta y puede bloquear |
| SIEM | EDR | Eventos de múltiples fuentes vs. foco en endpoints |
| Playbook | Runbook | Qué hacer vs. cómo hacerlo |
| Phishing | Smishing | General vs. mediante mensajes |
| Smishing | Vishing | Mensajes vs. voz |
| CVE | Zero-day | Identificador de vulnerabilidad divulgada vs. condición de novedad/explotación |
| ISO 27001 | NIST CSF | Requisitos de SGSI vs. marco de resultados de ciberseguridad |
| SOC | CSIRT | Operación/monitoreo vs. respuesta especializada a incidentes |

# Guía de estudio activa

## Paso 1 — Memoriza estas 10 relaciones

```text
CIA → Confidencialidad / Integridad / Disponibilidad
Mínimo privilegio → Solo lo necesario
Hash → Integridad
SLA → Nivel de servicio acordado
DDoS → Disponibilidad
IDS → Detecta
IPS → Detecta + previene
SIEM → Centraliza eventos
EDR → Endpoints
Playbook → Qué / Runbook → Cómo
```

## Paso 2 — Explica cada concepto con un ejemplo

No basta con reconocer la definición. Para cada concepto intenta explicar un caso realista en una o dos frases.

Ejemplo:

**Confidencialidad:** un trabajador obtiene acceso a una carpeta de clientes sin autorización.

**Integridad:** un atacante modifica los datos de una base de datos.

**Disponibilidad:** un DDoS deja inaccesible un servicio web.

## Paso 3 — Resuelve problemas de clasificación

Ante cualquier escenario pregunta:

```text
¿Se leyó información no autorizada?
→ Confidencialidad

¿Se modificó/destruyó información?
→ Integridad

¿Se impidió utilizar el servicio?
→ Disponibilidad
```

## Paso 4 — Resuelve problemas de riesgo

```text
1. ¿Cuál es el activo?
2. ¿Qué vulnerabilidad existe?
3. ¿Qué amenaza puede aprovecharla?
4. ¿Qué tan probable es?
5. ¿Qué impacto tendría?
6. ¿Qué control reduciría el riesgo?
7. ¿Cuál sería el riesgo residual?
```

## Paso 5 — Resuelve problemas de SOC

Ante una alerta:

```text
Evento
 ↓
Detección
 ↓
Análisis
 ↓
Clasificación
 ↓
Priorización
 ↓
¿N1 puede resolver?
 ├─ Sí → procedimiento
 └─ No → escalamiento
```

## Paso 6 — Resuelve incidentes

```text
Preparación
 ↓
Detección
 ↓
Análisis
 ↓
Contención
 ↓
Erradicación
 ↓
Recuperación
 ↓
Lecciones aprendidas
 ↓
Mejora
```

# Banco de preguntas de repaso

## Nivel 1 — Memoria

1. ¿Qué significa CIA?
2. ¿Qué significa SIEM?
3. ¿Qué significa EDR?
4. ¿Qué significa CVE?
5. ¿Qué significa EDR?
6. ¿Qué significa MFA?
7. ¿Qué significa SOC?
8. ¿Qué significa CSIRT?
9. ¿Qué significa SGSI?
10. ¿Qué significa DDoS?

## Nivel 2 — Comprensión

11. ¿Por qué el mínimo privilegio reduce el riesgo?
12. ¿Por qué un DDoS afecta principalmente la disponibilidad?
13. ¿Por qué un hash puede ayudar a comprobar integridad?
14. ¿Por qué SIEM y EDR no son la misma tecnología?
15. ¿Por qué un firewall no equivale a “seguridad completa”?
16. ¿Por qué es útil la defensa en profundidad?
17. ¿Por qué una matriz de riesgos permite priorizar recursos?
18. ¿Por qué las lecciones aprendidas forman parte de la seguridad defensiva?

## Nivel 3 — Aplicación

19. Una cuenta de administrador es usada para todas las tareas de una empresa. ¿Qué principio está siendo ignorado?
20. Un usuario no autorizado visualiza información de clientes. ¿Qué propiedad CIA se afecta?
21. Un atacante modifica un archivo de configuración. ¿Qué propiedad CIA se afecta?
22. Un servicio deja de responder por sobrecarga de tráfico distribuido. ¿Qué propiedad se afecta?
23. Un correo contiene un enlace malicioso que roba credenciales. ¿Qué técnica de ingeniería social puede estar involucrada?
24. Un usuario recibe el mismo engaño mediante SMS. ¿Cómo se clasifica?
25. Un atacante llama por teléfono y solicita un código de autenticación. ¿Cómo se clasifica?
26. Una alerta reúne eventos de firewall, servidor y endpoint. ¿Qué tecnología puede centralizarlos?
27. Un malware es detectado en un laptop y se necesita aislarlo. ¿Qué tipo de herramienta puede apoyar la respuesta?
28. SOC N1 no puede resolver el incidente. ¿Qué debería hacer?

# Respuestas rápidas

1. Confidentiality, Integrity, Availability.
2. Security Information and Event Management.
3. Endpoint Detection and Response.
4. Common Vulnerabilities and Exposures.
5. Endpoint Detection and Response.
6. Multi-Factor Authentication.
7. Security Operations Center.
8. Computer Security Incident Response Team.
9. Sistema de Gestión de Seguridad de la Información.
10. Distributed Denial of Service.
11. Porque limita los permisos a lo estrictamente necesario y reduce posibilidades de abuso.
12. Porque impide o degrada el acceso normal a un recurso o servicio.
13. Porque un cambio en el contenido normalmente produce un digest diferente.
14. Porque SIEM tiene un enfoque de centralización/correlación de eventos y EDR se especializa en endpoints.
15. Porque protege determinados flujos y funciones; no elimina por sí solo todas las amenazas ni vulnerabilidades.
16. Porque una segunda o tercera capa puede compensar la falla de otra.
17. Porque permite concentrar recursos en los riesgos con mayor prioridad.
18. Porque permite corregir controles, procedimientos y políticas después de un incidente.
19. Mínimo privilegio y, potencialmente, segregación de funciones.
20. Confidencialidad.
21. Integridad.
22. Disponibilidad.
23. Phishing.
24. Smishing.
25. Vishing.
26. SIEM.
27. EDR.
28. Escalar según el procedimiento y nivel de la organización.

# Fuentes principales utilizadas

- [NIST — Cybersecurity Framework 2.0](https://www.nist.gov/publications/nist-cybersecurity-framework-csf-20)
- [NIST — CSF 2.0 Resource Center](https://www.nist.gov/cyberframework)
- [NIST — SP 800-61 Rev. 3](https://csrc.nist.gov/pubs/sp/800/61/r3/final)
- [NIST — Cybersecurity Glossary](https://csrc.nist.gov/glossary)
- [NIST — Least Privilege](https://csrc.nist.gov/glossary/term/least_privilege)
- [NIST — Cryptographic Hash Function](https://csrc.nist.gov/glossary/term/Cryptographic_hash_function)
- [NIST — SIEM Tool](https://csrc.nist.gov/glossary/term/Security_Information_and_Event_Management_Tool)
- [NIST — EDR](https://csrc.nist.gov/glossary/term/endpoint_detection_and_response)
- [NIST — IDPS](https://csrc.nist.gov/glossary/term/intrusion_detection_and_prevention_system)
- [NIST — DDoS](https://csrc.nist.gov/glossary/term/ddos)
- [ISO — ISO/IEC 27001:2022](https://www.iso.org/es/norma/27001)
- [MITRE ATT&CK](https://attack.mitre.org/)
- [MITRE ATT&CK — Enterprise Tactics](https://attack.mitre.org/tactics/enterprise/)
- [OWASP Top 10:2025](https://owasp.org/Top10/2025/)
- [CISA — Phishing](https://www.cisa.gov/topics/cyber-threats-and-advisories/phishing)
- [CVE Program](https://www.cve.org/)
- [CVE — Process](https://www.cve.org/about/Process)
- [BCN — Ley 21.663, Ley Marco de Ciberseguridad](https://www.bcn.cl/leychile/navegar?idNorma=1202434)

