---
title: "Ciberseguridad — Clase 2 | SOC, gestión de incidentes y defensa"
course: "Introducción a la Ciberseguridad"
class: 2
topic: "SOC, alertas, incidentes, forense e inteligencia de amenazas"
tags:
  - ciberseguridad
  - seguridad-defensiva
  - SOC
  - SIEM
  - EDR
  - incident-response
  - triage
  - threat-intelligence
  - digital-forensics
  - playbook
  - runbook
  - ISO-27001
  - NIST
  - Chile
---

# Ciberseguridad — Clase 2

## SOC, gestión de alertas, incidentes y respuesta

> [!abstract] Objetivo de estudio
> Comprender cómo funciona un **SOC (Security Operations Center)**, qué hacen sus analistas, cómo se recibe y clasifica una alerta, cuándo una anomalía pasa a considerarse incidente, cómo se escala un caso y cómo se utilizan SIEM, EDR, herramientas forenses, inteligencia de amenazas, playbooks y runbooks para responder.

> [!info] Base del documento
> Estos apuntes parten de la transcripción de la segunda clase y conservan su enfoque y ejemplos. Se reorganizaron, eliminaron conversaciones ajenas al contenido técnico y se complementaron con documentación oficial de NIST, ISO y legislación chilena.
>
> Cuando una afirmación de la transcripción era demasiado informal, imprecisa o dependía de la organización concreta, se marca como **precisión técnica** para evitar estudiarla como una regla universal.

---

# 1. ¿Qué vimos en esta clase?

La clase se centra en el **Centro de Operaciones de Seguridad (SOC)** y en todo el proceso que ocurre desde que aparece una alerta hasta que el incidente es resuelto y posteriormente revisado.

Los grandes temas fueron:

- Concepto y función de un SOC.
- Diferencia entre SOC y NOC.
- Centralización de alertas y eventos.
- Analistas N1, N2 y N3.
- Triage de alertas e incidentes.
- Diferencia entre anomalía, alerta e incidente.
- SIEM.
- EDR.
- IDS e IPS.
- Gestión de incidentes.
- Contención, erradicación y recuperación.
- Análisis forense.
- Cadena de custodia.
- Hash e integridad de evidencia.
- Inteligencia de amenazas.
- MITRE ATT&CK.
- Gestión de vulnerabilidades.
- Playbooks y runbooks.
- Simulaciones.
- Backups y pruebas de recuperación.
- Mejora continua.
- Gobernanza y cumplimiento.
- ISO/IEC 27001.
- Leyes chilenas relacionadas con ciberseguridad y datos.

---

# 2. ¿Qué es un SOC?

**SOC = Security Operations Center / Centro de Operaciones de Seguridad.**

La clase lo presenta como un punto central desde el cual se monitorean:

- Eventos.
- Alertas.
- Anomalías.
- Amenazas.
- Sistemas.
- Redes.
- Endpoints.

El objetivo es poder detectar problemas y tomar decisiones de seguridad de manera coordinada.

NIST describe un SOC como un punto focal para las operaciones de seguridad y la defensa de redes de una organización, con monitoreo continuo, detección, análisis y respuesta ante incidentes. citeturn483350search51

### Idea clave

> Un SOC no es solamente una pantalla con gráficos. Es una **capacidad operacional** formada por personas, procesos y tecnología.

---

# 3. SOC vs NOC

La clase distingue:

### SOC

Se enfoca en:

- Seguridad.
- Amenazas.
- Alertas.
- Incidentes.
- Vulnerabilidades.
- Respuesta.

### NOC

**NOC = Network Operations Center.**

Se enfoca principalmente en:

- Disponibilidad de redes.
- Rendimiento.
- Conectividad.
- Infraestructura de red.
- Operación de servicios de red.

### Diferencia rápida

```text
NOC → operación y disponibilidad de la red

SOC → seguridad y amenazas
```

En la práctica pueden existir puntos de contacto entre ambos equipos.

---

# 4. Centralización

Una de las ventajas principales de un SOC es centralizar información.

Sin centralización:

```text
Firewall → revisar manualmente
Servidor → revisar manualmente
Router → revisar manualmente
Endpoint → revisar manualmente
Aplicación → revisar manualmente
```

Con una plataforma central:

```text
Firewall ─┐
Servidor ─┤
Router ───┤
EDR ──────┤
IDS/IPS ──┤
Aplicaciones
          ↓
         SIEM
          ↓
         SOC
```

Esto permite una visión más completa.

NIST señala que la centralización y correlación de eventos permite obtener información accionable desde múltiples fuentes y facilita la gestión de incidentes. citeturn376201search58turn376201search60

---

# 5. Personas + procesos + tecnología

Un SOC efectivo no depende solamente de software.

Se puede pensar como:

```text
           SOC
            │
    ┌───────┼───────┐
    ↓       ↓       ↓
 Personas Procesos Tecnología
```

### Personas

- Analistas.
- Ingenieros.
- Personal de respuesta.
- Gestión.

### Procesos

- Gestión de alertas.
- Gestión de incidentes.
- Escalamiento.
- Documentación.
- Recuperación.
- Mejora continua.

### Tecnología

- SIEM.
- EDR.
- IDS/IPS.
- Firewalls.
- Herramientas forenses.
- Threat intelligence.
- Ticketing.
- Automatización.

---

# 6. ¿Qué hace un analista SOC N1?

El **N1** es la primera línea de análisis.

La clase le asigna principalmente:

- Monitoreo.
- Recepción de alertas.
- Análisis preliminar.
- Triage.
- Clasificación.
- Registro del incidente.
- Identificación de falsos positivos.
- Escalamiento.

### Flujo

```text
Alerta
  ↓
SOC N1
  ↓
Analizar
  ↓
¿Es realmente sospechoso?
```

El N1 no debería intentar resolver cualquier incidente independientemente de sus capacidades.

Si el caso excede su nivel:

```text
N1 → N2
```

---

# 7. SOC N2

El N2 realiza una investigación más profunda.

Según la clase, puede:

- Analizar la causa.
- Investigar con más detalle.
- Determinar estrategias de mitigación.
- Aplicar contención.
- Utilizar herramientas forenses.
- Revisar evidencias.
- Escalar casos complejos.

```text
N1
 ↓
análisis inicial

N2
 ↓
investigación profunda
 ↓
contención / mitigación
```

---

# 8. SOC N3

N3 corresponde a un nivel aún más especializado.

La clase menciona:

- Amenazas avanzadas.
- Análisis de malware.
- Ingeniería inversa.
- Investigación avanzada.
- Desarrollo de playbooks y runbooks.
- Threat hunting.
- Solución de incidentes complejos.

### Idea clave

```text
N1 → detectar y clasificar
N2 → investigar y contener
N3 → investigación avanzada y casos complejos
```

La estructura exacta depende de cada organización.

> [!note]
> Los niveles N1/N2/N3 son una forma habitual de estructurar un SOC, pero no constituyen una clasificación universal obligatoria. Las responsabilidades pueden variar según empresa, tamaño y madurez.

---

# 9. Gerencia SOC

La gerencia se ocupa de aspectos como:

- Coordinación.
- Supervisión.
- Decisiones estratégicas.
- Gestión del equipo.
- Comunicación.
- Priorización de incidentes mayores.
- Relación con partes interesadas.
- Continuidad del negocio.

La clase también enfatiza las **habilidades blandas**:

- Comunicación.
- Liderazgo.
- Trabajo en equipo.
- Toma de decisiones.

### Idea importante

> En ciberseguridad no basta con conocimiento técnico. La capacidad de comunicar y coordinar también es fundamental.

---

# 10. Alerta, anomalía e incidente

Esta diferencia es muy importante para la clase.

## Anomalía

Comportamiento que se desvía de lo esperado.

Ejemplo:

```text
Usuario normalmente:
10:00 → inicia sesión desde Chile

Hoy:
04:00 → inicia sesión desde Brasil
```

Es una señal sospechosa, pero todavía no prueba necesariamente que exista un ataque.

## Alerta

Notificación generada por una herramienta o sistema porque detectó alguna condición.

Ejemplo:

```text
"20 intentos fallidos de login"
```

## Incidente

Evento que realmente compromete o puede comprometer la seguridad de la organización y requiere respuesta.

La clase resume la idea así:

```text
Anomalía
   ↓
investigación
   ↓
¿compromete seguridad?
   ├── No → alerta/falso positivo
   └── Sí → incidente
```

### Idea clave

> **No toda alerta es un incidente.**

---

# 11. Triage

El **triage** es el proceso de evaluar, clasificar y priorizar alertas/incidentes.

Preguntas típicas:

- ¿Qué ocurrió?
- ¿Es realmente malicioso?
- ¿Qué activo está afectado?
- ¿Qué tan grave es?
- ¿Qué impacto puede provocar?
- ¿Qué prioridad tiene?
- ¿Se debe escalar?

### Flujo

```text
Alerta
 ↓
Validación
 ↓
Clasificación
 ↓
Prioridad
 ↓
Respuesta / escalamiento
```

---

# 12. Falso positivo

Un **falso positivo** ocurre cuando un sistema genera una alerta, pero después del análisis se determina que la actividad no era realmente maliciosa.

Ejemplo:

```text
Sistema detecta:
"login sospechoso"

Investigación:
Era el administrador realizando una tarea autorizada.
```

Resultado:

```text
Falso positivo
```

### ¿Por qué importa?

Porque demasiados falsos positivos pueden generar:

- Sobrecarga del SOC.
- Pérdida de tiempo.
- Fatiga de alertas.
- Riesgo de pasar por alto eventos reales.

---

# 13. Gestión de tickets

La clase relaciona los incidentes con un sistema de gestión de tickets.

Un ticket permite registrar:

- Fecha.
- Hora.
- Analista.
- Alerta.
- Activo afectado.
- Evidencias.
- Acciones.
- Estado.
- Responsable.
- Escalamiento.
- Resolución.

### Flujo

```text
Alerta
 ↓
Ticket
 ↓
Análisis
 ↓
Escalamiento
 ↓
Resolución
 ↓
Cierre
 ↓
Documentación
```

La documentación permite reconstruir posteriormente lo sucedido.

---

# 14. SIEM

**SIEM = Security Information and Event Management.**

Un SIEM centraliza información de seguridad proveniente de múltiples fuentes.

Ejemplos de fuentes:

```text
Firewall
Servidor
Windows
Linux
Aplicación
IDS/IPS
EDR
Cloud
Router
```

Todo puede llegar a:

```text
              SIEM
               │
       ┌───────┼───────┐
       ↓       ↓       ↓
    Correlación Alertas Búsqueda
```

NIST describe el SIEM como una aplicación que recoge datos de seguridad desde diferentes componentes y los presenta de forma integrada para apoyar el análisis. citeturn376201search58

---

# 15. EDR

**EDR = Endpoint Detection and Response.**

Su foco principal son los endpoints.

Ejemplos:

- PCs.
- Laptops.
- Servidores compatibles.
- Otros dispositivos finales.

Puede permitir:

- Detectar procesos sospechosos.
- Observar actividad.
- Detectar malware.
- Aislar un equipo.
- Investigar eventos.
- Aplicar acciones de respuesta.
- Centralizar información de cientos de equipos.

La clase utiliza el ejemplo de una organización con cientos de computadores administrados desde una sola plataforma.

### Idea clave

```text
SIEM → visión de eventos de múltiples fuentes

EDR → visión profunda de endpoints
```

Pueden trabajar juntos.

---

# 16. IDS vs IPS

## IDS

**Intrusion Detection System**

Detecta posibles actividades maliciosas y genera alertas.

```text
Tráfico
 ↓
IDS
 ↓
Detecta
 ↓
Alerta
```

## IPS

**Intrusion Prevention System**

Detecta y puede actuar para prevenir/bloquear la actividad.

```text
Tráfico
 ↓
IPS
 ↓
Detecta
 ↓
Actúa
 ↓
Bloquea / rechaza / previene
```

NIST diferencia los sistemas de detección de los de prevención precisamente por la capacidad de actuar ante la actividad detectada. 

---

# 17. Monitoreo de comportamiento

La clase presenta la detección de comportamientos anómalos.

Ejemplo:

```text
Patrón habitual:
Usuario → 10:00
Usuario → Chile
Usuario → equipo habitual

Nuevo evento:
Usuario → 04:00
Usuario → Brasil
Usuario → equipo distinto
```

Una herramienta puede marcar esto como sospechoso.

> [!note]
> Una anomalía no implica automáticamente una intrusión. El analista debe investigar el contexto.

---

# 18. Correlación de eventos

La correlación relaciona múltiples eventos para encontrar patrones.

## Temporal

Relaciona eventos por tiempo.

```text
10:00 → login
10:01 → ejecución de proceso
10:02 → acceso a servidor
10:04 → transferencia de datos
```

## Contextual

Considera información adicional.

```text
Usuario habitual → Chile
Actividad → Brasil
```

El contexto hace que el evento sea más sospechoso.

---

# 19. Gestión de incidentes

La clase plantea un ciclo general:

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

NIST SP 800-61 Rev. 3, publicado en abril de 2025, integra la respuesta a incidentes dentro de la gestión de riesgo de NIST CSF 2.0 y reemplaza la revisión anterior SP 800-61 Rev. 2. citeturn376201search2turn376201search5

---

# 20. Preparación

Antes de que ocurra un incidente se debería disponer de:

- Políticas.
- Roles.
- Procedimientos.
- Contactos.
- Herramientas.
- Backups.
- Playbooks.
- Runbooks.
- Capacitación.
- Simulaciones.

La preparación busca reducir el tiempo y la incertidumbre de respuesta.

---

# 21. Detección

Se recolecta información de:

- Logs.
- EDR.
- SIEM.
- Firewall.
- IDS/IPS.
- Sistemas de autenticación.
- Aplicaciones.

El objetivo es detectar señales de actividad maliciosa o anómala.

---

# 22. Análisis

Se busca responder:

```text
¿Qué ocurrió?
¿Dónde ocurrió?
¿Cuándo ocurrió?
¿Qué activo está afectado?
¿Quién o qué inició la actividad?
¿Qué impacto tiene?
¿Sigue ocurriendo?
```

También se determina el nivel de severidad.

---

# 23. Contención

La **contención** busca limitar la propagación y el impacto.

Ejemplo:

```text
Equipo infectado
       ↓
Aislar equipo
       ↓
Evitar propagación
```

Puede incluir:

- Aislamiento de endpoint.
- Bloqueo de IP.
- Revocación de credenciales.
- Segmentación.
- Bloqueo de dominios.
- Desactivación temporal de servicios.

---

# 24. Erradicación

La erradicación busca eliminar la amenaza y su mecanismo de permanencia.

Puede incluir:

- Eliminación de malware.
- Corrección de vulnerabilidades.
- Revocación de credenciales.
- Eliminación de persistencia.
- Aplicación de parches.
- Reconfiguración.

> [!important]
> Contener no significa erradicar.
>
> **Contención = limitar.**
>
> **Erradicación = eliminar la causa/amenaza.**

---

# 25. Recuperación

Después de erradicar la amenaza:

```text
Sistema limpio
     ↓
Restauración
     ↓
Validación
     ↓
Servicio normal
```

Puede implicar:

- Restaurar backups.
- Reinstalar sistemas.
- Recuperar servicios.
- Verificar integridad.
- Monitorear después de la recuperación.

---

# 26. Backups y pruebas de recuperación

La clase enfatiza que no basta con hacer copias.

Debemos comprobar que funcionan.

```text
Backup
  ↓
Prueba de restauración
  ↓
¿Funciona?
 ├── Sí → backup confiable
 └── No → corregir proceso
```

### Idea clave

> Un backup que nunca se ha probado no debería considerarse confiable simplemente porque "existe".

Las pruebas periódicas permiten detectar problemas de configuración, corrupción, permisos o procedimientos de recuperación.

---

# 27. Lecciones aprendidas

Después del incidente se analiza:

- Qué ocurrió.
- Qué falló.
- Qué funcionó.
- Qué controles fueron insuficientes.
- Qué procedimiento debe actualizarse.
- Qué capacitación hace falta.
- Qué cambios deben implementarse.

NIST considera las actividades posteriores al incidente y la mejora parte importante de la capacidad de respuesta. citeturn376201search0turn376201search2

---

# 28. Mejora continua

La seguridad no termina cuando el incidente se cierra.

```text
Incidente
   ↓
Respuesta
   ↓
Recuperación
   ↓
Lecciones aprendidas
   ↓
Actualizar controles
   ↓
Actualizar políticas
   ↓
Capacitar
   ↓
Volver a monitorear
```

Esto conecta con ISO/IEC 27001, cuyo SGSI está orientado a establecer, implementar, mantener y mejorar continuamente la gestión de seguridad de la información. citeturn376201search3

---

# 29. Playbook

Un **playbook** es una guía de respuesta para un tipo de situación.

La idea de la clase:

> **Playbook = qué hacer.**

Ejemplo:

```text
Incidente: ransomware

1. Identificar equipos afectados
2. Aislar sistemas
3. Revocar accesos comprometidos
4. Notificar
5. Investigar
6. Erradicar
7. Recuperar
```

No existe un único formato obligatorio.

---

# 30. Runbook

El **runbook** entrega instrucciones más operacionales.

> **Runbook = cómo hacerlo.**

Ejemplo:

Playbook:

```text
Aislar el equipo afectado.
```

Runbook:

```text
1. Abrir consola EDR.
2. Buscar hostname.
3. Seleccionar endpoint.
4. Ejecutar "Isolate Host".
5. Confirmar aislamiento.
6. Registrar acción en ticket.
```

### Memoria

```text
PLAYBOOK → QUÉ
RUNBOOK  → CÓMO
```

---

# 31. Ejemplo de respuesta ante ransomware

La clase presenta una secuencia parecida a:

```text
Detección
   ↓
Validación
   ↓
Aislamiento
   ↓
Revocar accesos comprometidos
   ↓
Notificar
   ↓
Contener
   ↓
Recopilar evidencia
   ↓
Aplicar parches/correcciones
   ↓
Erradicar
   ↓
Verificar sistemas
   ↓
Restaurar backup limpio
   ↓
Validar recuperación
   ↓
Informar cierre
   ↓
Lecciones aprendidas
```

### Punto especialmente importante

No se debe restaurar automáticamente un backup antes de asegurarse de que la amenaza fue eliminada.

De lo contrario:

```text
Malware sigue presente
        ↓
Restaurar backup
        ↓
Reinfección
```

---

# 32. Stakeholders

**Stakeholders = partes interesadas.**

En un incidente pueden incluir:

- Gerencia.
- Clientes.
- Proveedores.
- Personal interno.
- Equipos legales.
- Equipos de TI.
- Equipos de seguridad.
- Autoridades, cuando corresponda.

La comunicación debe realizarse según los procedimientos y obligaciones aplicables.

---

# 33. Gestión de crisis

Un incidente importante no es solamente un problema técnico.

Puede convertirse en una crisis si:

- Se interrumpen servicios críticos.
- Se afectan clientes.
- Existe exposición de datos.
- Existen obligaciones legales.
- Existe riesgo financiero o reputacional.

Por eso es importante coordinar:

```text
Técnico
 +
Legal
 +
Comunicaciones
 +
Gerencia
 +
Continuidad
```

---

# 34. Análisis forense digital

La informática forense busca examinar evidencia digital utilizando procedimientos científicos y preservando su integridad y trazabilidad.

NIST define la informática forense en relación con la adquisición, preservación, análisis y reporte de evidencia digital, incluyendo cadena de custodia, validación, herramientas adecuadas y documentación. citeturn483350search0turn483350search3

---

# 35. Cadena de custodia

La **cadena de custodia** registra el recorrido de una evidencia:

```text
Identificación
     ↓
Recolección
     ↓
Almacenamiento
     ↓
Transferencias
     ↓
Análisis
     ↓
Presentación / cierre
```

NIST define la cadena de custodia como un proceso que registra quién manipuló la evidencia, cuándo, cómo y con qué propósito durante su ciclo de vida. citeturn483350search1turn483350search10

### Datos que conviene registrar

- Quién encontró la evidencia.
- Fecha y hora.
- Lugar.
- Descripción.
- Quién la recibió.
- Transferencias.
- Almacenamiento.
- Acciones realizadas.

---

# 36. Hash en evidencia digital

En forense se puede calcular un hash de una imagen o archivo para ayudar a verificar que no haya cambiado.

Ejemplo:

```text
Evidencia original
       ↓
     SHA-256
       ↓
Hash A

Copia forense
       ↓
     SHA-256
       ↓
Hash A
```

Si coinciden:

```text
Hash original = Hash copia
```

existe evidencia de que el contenido es consistente con el original al momento de la comparación.

NIST recomienda utilizar hashes para apoyar la preservación de la integridad de evidencia digital. citeturn483350search50turn483350search2

---

# 37. Precisión sobre la evidencia digital

La transcripción plantea que cualquier contacto físico podría fragmentar un disco o destruir información. La situación real depende del tipo de dispositivo y de la técnica utilizada.

Lo importante para estudiar es:

> La evidencia debe manipularse y adquirirse mediante procedimientos que minimicen alteraciones y permitan demostrar su integridad.

En investigaciones forenses se busca trabajar sobre copias forenses o imágenes, preservando el original tanto como sea posible y documentando todas las operaciones.

---

# 38. Inteligencia de amenazas

**Threat Intelligence** es la recopilación, análisis y utilización de información sobre amenazas para mejorar la toma de decisiones de seguridad.

Puede ayudar a conocer:

- Amenazas actuales.
- Técnicas de ataque.
- Indicadores.
- Actores.
- Vulnerabilidades explotadas.
- Tendencias.
- Sectores afectados.

La clase la presenta como una forma de mantenerse al día respecto de las tácticas y técnicas utilizadas por atacantes.

---

# 39. MITRE ATT&CK

La clase vuelve a utilizar **MITRE ATT&CK** como referencia.

MITRE ATT&CK es una base de conocimiento de tácticas y técnicas de adversarios basada en observaciones del mundo real.

Puede utilizarse para:

- Entender cómo actúan los atacantes.
- Diseñar detecciones.
- Realizar threat hunting.
- Evaluar cobertura defensiva.
- Crear procedimientos.

Ejemplo:

```text
Táctica:
Privilege Escalation

        ↓

Técnicas utilizadas
        ↓

Detecciones
        ↓

Controles defensivos
```

---

# 40. Gestión de vulnerabilidades

La clase conecta la gestión de vulnerabilidades con:

```text
Identificar
   ↓
Evaluar
   ↓
Priorizar
   ↓
Mitigar
   ↓
Validar
   ↓
Monitorear
```

No todas las vulnerabilidades tienen la misma prioridad.

Se debe considerar:

- Criticidad del activo.
- Probabilidad de explotación.
- Exposición.
- Impacto.
- Controles existentes.
- Contexto de la organización.

---

# 41. CVE

**CVE = Common Vulnerabilities and Exposures.**

CVE identifica y cataloga vulnerabilidades de ciberseguridad divulgadas públicamente.

Ejemplo:

```text
CVE-2026-12345
```

### Relación con la clase anterior

```text
Vulnerabilidad conocida
      ↓
Registro / identificación
      ↓
CVE
```

Una vulnerabilidad nueva no equivale simplemente a "una vulnerabilidad sin CVE".

---

# 42. Zero-day

Una **zero-day** es una vulnerabilidad para la cual el atacante puede actuar antes de que exista una solución o defensa disponible de forma adecuada.

No debe estudiarse como:

```text
sin CVE = zero-day
```

Esa equivalencia es incorrecta.

---

# 43. Actualizaciones y gestión de parches

La clase recomienda no actualizar equipos indiscriminadamente.

Un enfoque más ordenado es:

```text
Nueva actualización
      ↓
Equipo de prueba
      ↓
Validación
      ↓
¿Funciona correctamente?
 ├── No → investigar
 └── Sí
      ↓
Planificar despliegue
      ↓
Implementar
      ↓
Verificar
```

Esto corresponde a una gestión controlada de cambios.

---

# 44. Planificación de actualizaciones

La clase menciona:

- Aviso previo.
- Recordatorios.
- Equipo de prueba.
- Validación.
- Despliegue posterior.

La idea es reducir el riesgo de que una actualización provoque indisponibilidad o problemas inesperados.

---

# 45. Simulaciones

Los ejercicios controlados permiten evaluar la preparación de la organización.

Ejemplo:

```text
Simulación de phishing
       ↓
Usuarios reciben campaña controlada
       ↓
Se mide comportamiento
       ↓
Se detectan debilidades
       ↓
Capacitación
       ↓
Nueva evaluación
```

También pueden realizarse simulaciones de incidentes y pruebas de respuesta.

### Objetivo

No es "pillar" al trabajador.

Es:

> Identificar oportunidades de mejora antes de que ocurra un incidente real.

---

# 46. Gobernanza

La clase relaciona el SOC y la respuesta con gobernanza.

Gobernanza incluye:

- Políticas.
- Roles.
- Responsabilidades.
- Gestión de riesgos.
- Cumplimiento.
- Supervisión.
- Continuidad.
- Toma de decisiones.

NIST CSF 2.0 incorpora **Govern** como una de sus seis funciones principales, junto con Identify, Protect, Detect, Respond y Recover. citeturn376201search58turn376201search59

---

# 47. ISO/IEC 27001

La clase menciona nuevamente ISO/IEC 27001.

La versión vigente es:

**ISO/IEC 27001:2022**

La norma define requisitos para un **SGSI (Sistema de Gestión de Seguridad de la Información)** y busca que la organización gestione sus riesgos de seguridad de forma sistemática y mejore continuamente. citeturn376201search3

### Relación con el SOC

```text
ISO 27001 / SGSI
       ↓
Políticas + controles + gestión
       ↓
Procesos operacionales
       ↓
SOC
       ↓
Monitoreo + detección + respuesta
```

Un SOC puede ser parte de una arquitectura de seguridad más amplia, pero ISO 27001 no equivale a "tener un SOC".

---

# 48. Chile: Ley 21.459

La **Ley 21.459** establece normas sobre delitos informáticos y adecua la legislación chilena al Convenio de Budapest.

Incluye materias como:

- Ataque a la integridad de un sistema.
- Acceso ilícito.
- Interceptación ilícita.
- Ataque a la integridad de datos.
- Falsificación informática.
- Fraude informático.
- Abuso de dispositivos.

citeturn483350search4turn483350search5

---

# 49. Chile: Ley 21.663

La **Ley 21.663** corresponde a la Ley Marco de Ciberseguridad.

Busca establecer institucionalidad, principios y normas generales para coordinar la ciberseguridad y establecer requisitos para prevención, contención, resolución y respuesta ante incidentes. citeturn975060search1turn975060search2

La ley creó la **Agencia Nacional de Ciberseguridad (ANCI)**. citeturn975060search5

También contempla el **CSIRT Nacional** dentro de la institucionalidad creada por la ley. citeturn975060search8

---

# 50. Chile: Ley 21.719

La **Ley 21.719** regula la protección y el tratamiento de datos personales y crea la Agencia de Protección de Datos Personales.

Su entrada en vigencia está fijada para:

**1 de diciembre de 2026.** citeturn975060search0turn975060search7

Esto es relevante para una empresa que almacena información de clientes porque agrega obligaciones relacionadas con el tratamiento y protección de datos personales.

> [!warning] Fecha importante
> La transcripción fue realizada antes de la entrada en vigencia de la Ley 21.719. Para estudiar legislación, utiliza la fecha oficial y el texto vigente de la Biblioteca del Congreso Nacional.

---

# 51. NDA

**NDA = Non-Disclosure Agreement.**

Es un acuerdo de confidencialidad.

Puede establecer:

- Información a la que se tendrá acceso.
- Información que no debe divulgarse.
- Restricciones de uso.
- Tratamiento de información.
- Obligaciones de las partes.
- Alcance de confidencialidad.

La clase lo relaciona con trabajos externos de auditoría y consultoría.

---

# 52. Auditoría y separación de funciones

La clase plantea que una persona u organización no debería evaluarse a sí misma cuando existe un conflicto de interés.

La idea general es válida:

> La independencia de quien audita ayuda a reducir conflictos de interés y mejora la objetividad de la evaluación.

Sin embargo, no debe estudiarse como una regla absoluta de que "toda implementación ISO 27001 debe hacerla obligatoriamente un externo". Los requisitos concretos de certificación, auditoría y consultoría dependen del rol y del esquema de evaluación.

---

# 53. El ciclo completo del SOC

Este es uno de los esquemas más importantes para memorizar:

```text
                 ALERTA
                   ↓
              SOC N1
                   ↓
                TRIAGE
                   ↓
          ¿Es incidente real?
            /             \
          NO               SÍ
          ↓                 ↓
     Falso positivo       Ticket
                            ↓
                          N1/N2
                            ↓
                         Análisis
                            ↓
                  ¿Puede resolverse?
                     /          \
                   SÍ            NO
                   ↓              ↓
                Contener         N3
                   ↓              ↓
               Erradicar      Investigación
                   ↓              ↓
               Recuperar       Respuesta
                   ↓              ↓
                   └──────┬───────┘
                          ↓
                   Lecciones aprendidas
                          ↓
                    Mejora continua
```

---

# 54. Ejemplo completo

Supongamos que un EDR detecta comportamiento sospechoso en un computador.

## Paso 1 — Detección

```text
EDR
 ↓
Alerta
```

## Paso 2 — N1

El analista revisa:

- Equipo.
- Usuario.
- Proceso.
- Hora.
- IP.
- Contexto.

## Paso 3 — Triage

Determina:

```text
¿Falso positivo?
¿Actividad sospechosa?
¿Incidente?
```

## Paso 4 — Escalamiento

Si es un caso complejo:

```text
N1 → N2
```

## Paso 5 — Contención

Se aísla el endpoint.

## Paso 6 — Investigación

Se revisan:

- Logs.
- Procesos.
- Archivos.
- Conexiones.
- Evidencia.

## Paso 7 — Erradicación

Se elimina malware/persistencia y se corrige la causa.

## Paso 8 — Recuperación

Se restaura el equipo a un estado seguro.

## Paso 9 — Cierre

Se documenta.

## Paso 10 — Mejora

Se actualizan:

- Detecciones.
- Playbooks.
- Políticas.
- Capacitación.

---

# 55. Errores comunes de la transcripción

> [!warning] No memorizar literalmente

La transcripción automática deformó varios términos.

| Transcripción | Forma correcta |
|---|---|
| shock / shock de seguridad | **SOC** |
| NOC | **NOC** |
| CIEM | **SIEM** |
| GDR / EBR | **EDR** cuando se refiere a Endpoint Detection and Response |
| Instruction Detection System | **Intrusion Detection System** |
| Instruction Prevention System | **Intrusion Prevention System** |
| ANSI | **ANCI** en el contexto chileno |
| CSIR | normalmente **CSIRT** cuando se refiere al equipo de respuesta a incidentes |
| Mitreatak / Mitreatal | **MITRE ATT&CK** |
| CV / Common Vulnerabilities Exposed | **CVE — Common Vulnerabilities and Exposures** |
| razonware / razon web | **ransomware** |
| fiching / fitching | **phishing** |
| botnet | **botnet** |

---

# 56. Afirmaciones de la clase que requieren contexto

## "Todas las vulnerabilidades deben reportarse a ANCI"

No debe estudiarse de forma general así.

Las obligaciones de reporte dependen del marco legal, del tipo de entidad, del tipo de incidente y de las disposiciones aplicables.

La Ley 21.663 establece deberes específicos para las instituciones comprendidas en su ámbito. citeturn975060search1

## "Una vulnerabilidad sin CVE es automáticamente zero-day"

Incorrecto.

CVE y zero-day son conceptos diferentes.

## "Un backup se prueba una vez"

Insuficiente.

Las pruebas deben realizarse periódicamente porque los sistemas, procedimientos y dependencias cambian.

## "Tener un SIEM significa tener un SOC"

Incorrecto.

Un SIEM es una tecnología. Un SOC incluye personas, procesos y tecnología.

---

# 57. Relación con NIST CSF 2.0

La clase puede organizarse muy bien con NIST CSF 2.0:

```text
GOVERN
 ↓
Políticas y gestión

IDENTIFY
 ↓
Activos y riesgos

PROTECT
 ↓
Controles

DETECT
 ↓
SOC + SIEM + EDR + IDS/IPS

RESPOND
 ↓
Triage + contención + erradicación

RECOVER
 ↓
Restauración + continuidad
```

NIST CSF 2.0 contiene las seis funciones **Govern, Identify, Protect, Detect, Respond y Recover**, que deben utilizarse de manera complementaria para gestionar el riesgo de ciberseguridad. citeturn376201search59turn376201search60

---

# 58. Comparación de conceptos

| Concepto | Pregunta que responde |
|---|---|
| Alerta | ¿Ocurrió algo que merece atención? |
| Anomalía | ¿El comportamiento se desvía de lo esperado? |
| Incidente | ¿La seguridad fue comprometida y requiere respuesta? |
| Triage | ¿Qué tan importante es y qué hago primero? |
| SIEM | ¿Qué ocurre en múltiples fuentes? |
| EDR | ¿Qué ocurre dentro de los endpoints? |
| IDS | ¿Detectó actividad sospechosa? |
| IPS | ¿Puede detectarla y actuar? |
| SOC N1 | ¿Qué está ocurriendo? |
| SOC N2 | ¿Por qué está ocurriendo y cómo contenerlo? |
| SOC N3 | ¿Cómo resolver un caso avanzado? |
| Playbook | ¿Qué debemos hacer? |
| Runbook | ¿Cómo lo hacemos? |
| Forense | ¿Qué ocurrió y qué evidencia existe? |
| Threat Intelligence | ¿Qué amenazas y técnicas debemos conocer? |
| MITRE ATT&CK | ¿Qué tácticas y técnicas utilizan los adversarios? |

---

# 59. Preguntas de estudio

## SOC

1. ¿Qué significa SOC?
2. ¿Cuál es la función principal de un SOC?
3. ¿Qué diferencia existe entre SOC y NOC?
4. ¿Por qué centralizar información?
5. ¿Qué funciones realiza un analista N1?
6. ¿Qué diferencia a N2 de N1?
7. ¿Qué tipo de trabajo puede realizar N3?
8. ¿Qué responsabilidades tiene la gerencia?

## Alertas

9. ¿Qué es una anomalía?
10. ¿Qué es una alerta?
11. ¿Qué es un incidente?
12. ¿Qué es un falso positivo?
13. ¿Qué es triage?
14. ¿Por qué es importante priorizar?

## Herramientas

15. ¿Qué es SIEM?
16. ¿Qué es EDR?
17. ¿Qué diferencia existe entre SIEM y EDR?
18. ¿Qué es IDS?
19. ¿Qué es IPS?
20. ¿Cuál es la diferencia entre IDS e IPS?

## Incidentes

21. ¿Cuáles son las etapas de respuesta?
22. ¿Qué significa contención?
23. ¿Qué significa erradicación?
24. ¿Qué significa recuperación?
25. ¿Por qué no se debería restaurar un backup antes de eliminar la amenaza?
26. ¿Qué son las lecciones aprendidas?

## Forense

27. ¿Qué es informática forense?
28. ¿Qué es cadena de custodia?
29. ¿Para qué se utiliza un hash en evidencia digital?
30. ¿Por qué se intenta preservar la evidencia original?

## Documentación

31. ¿Qué es un playbook?
32. ¿Qué es un runbook?
33. ¿Cuál es la diferencia entre ambos?

## Threat Intelligence

34. ¿Qué es inteligencia de amenazas?
35. ¿Qué es MITRE ATT&CK?
36. ¿Para qué sirve en defensa?

## Gobernanza

37. ¿Qué es ISO/IEC 27001?
38. ¿Qué es un SGSI?
39. ¿Qué función cumple Govern en NIST CSF 2.0?
40. ¿Qué relación existe entre gobernanza y SOC?

---

# 60. Mini prueba

> [!question] 1. Una herramienta detecta un inicio de sesión a una hora inusual. ¿Es automáticamente un incidente?

<details>
<summary>Respuesta</summary>

No. Es una **alerta/anomalía** que debe investigarse. Puede ser legítima o convertirse en un incidente dependiendo del contexto.

</details>

> [!question] 2. ¿Qué hace el N1?

<details>
<summary>Respuesta</summary>

Realiza el monitoreo y análisis inicial, hace triage, clasifica las alertas y escala los casos que exceden su capacidad.

</details>

> [!question] 3. ¿Qué hace el SIEM?

<details>
<summary>Respuesta</summary>

Centraliza y analiza eventos provenientes de múltiples fuentes para ayudar a detectar y responder ante amenazas.

</details>

> [!question] 4. ¿Qué hace un EDR?

<details>
<summary>Respuesta</summary>

Detecta e investiga actividad sospechosa en endpoints y puede proporcionar capacidades de respuesta.

</details>

> [!question] 5. ¿Cuál es la diferencia entre contención y erradicación?

<details>
<summary>Respuesta</summary>

Contención limita la propagación o el impacto. Erradicación busca eliminar la amenaza y su causa/persistencia.

</details>

> [!question] 6. ¿Qué diferencia hay entre playbook y runbook?

<details>
<summary>Respuesta</summary>

Playbook = qué hacer. Runbook = cómo hacerlo.

</details>

> [!question] 7. ¿Para qué sirve la cadena de custodia?

<details>
<summary>Respuesta</summary>

Para registrar y demostrar la trazabilidad de una evidencia durante su recolección, almacenamiento, transferencia y análisis.

</details>

> [!question] 8. ¿Para qué sirve el hash en informática forense?

<details>
<summary>Respuesta</summary>

Ayuda a verificar la integridad y detectar cambios en archivos o imágenes de evidencia.

</details>

> [!question] 9. ¿Qué significa CVE?

<details>
<summary>Respuesta</summary>

Common Vulnerabilities and Exposures.

</details>

> [!question] 10. ¿Cuál es la función adicional de NIST CSF 2.0 respecto del esquema de cinco funciones del CSF 1.1?

<details>
<summary>Respuesta</summary>

**Govern.**

</details>

---

# 61. Ejercicios de práctica

## Ejercicio 1 — Clasificación de eventos

Clasifica cada situación como:

```text
Anomalía
Alerta
Incidente
Falso positivo
```

### A

Un usuario inicia sesión a las 04:00, pero normalmente trabaja a las 10:00.

### B

Un EDR detecta un proceso sospechoso y luego se confirma que es una herramienta legítima autorizada.

### C

Un atacante cifra los archivos de un servidor crítico.

### D

Un sistema genera una alerta por cinco intentos fallidos y se confirma que el usuario olvidó su contraseña.

### Objetivo

Aprender a diferenciar:

```text
señal
vs
alerta
vs
incidente
```

---

# 62. Ejercicio 2 — Flujo SOC

Supón que un SIEM genera una alerta de comportamiento sospechoso.

Describe:

1. Qué hace N1.
2. Cómo realiza el triage.
3. Cuándo escala a N2.
4. Qué podría investigar N2.
5. Cuándo interviene N3.
6. Qué se documenta.
7. Qué ocurre después del cierre.

---

# 63. Ejercicio 3 — Ransomware

Una empresa detecta:

```text
Archivos cifrados
Conexiones sospechosas
EDR genera alertas
Usuarios sin acceso a archivos
```

Diseña un playbook.

Debe contener al menos:

```text
Detección
Validación
Contención
Notificación
Erradicación
Recuperación
Cierre
Lecciones aprendidas
```

---

# 64. Ejercicio 4 — Runbook

A partir del ejercicio anterior, crea un runbook para:

> **Aislar un endpoint comprometido mediante EDR.**

Debe tener:

```text
Precondiciones
Pasos
Validación
Registro
Comunicación
```

No necesitas utilizar una marca real.

---

# 65. Ejercicio 5 — SIEM + EDR

Explica qué información recibirías de:

```text
EDR
Firewall
Servidor
IDS
Aplicación
```

y cómo utilizarías el SIEM para correlacionarla.

---

# 66. Ejercicio 6 — Forense

Supón que tienes una imagen forense de un disco.

Explica el proceso:

```text
Identificar evidencia
↓
Registrar cadena de custodia
↓
Crear/verificar imagen
↓
Calcular hash
↓
Analizar copia
↓
Registrar hallazgos
↓
Comparar hashes
↓
Generar informe
```

---

# 67. Desafío final — SOC completo

Una empresa de 300 trabajadores tiene:

- 300 endpoints.
- 10 servidores.
- Firewall.
- Servicios cloud.
- Bases de datos.
- Correo corporativo.

El SIEM genera una alerta:

```text
Usuario:
inicio de sesión desde país inusual

Segundos después:
descarga masiva de archivos

Después:
ejecución de proceso sospechoso

EDR:
alerta crítica
```

### Tu tarea

Diseña el flujo completo:

```text
SIEM
 ↓
SOC N1
 ↓
Triage
 ↓
¿Incidente?
 ↓
N2
 ↓
Contención
 ↓
Forense
 ↓
N3 si corresponde
 ↓
Erradicación
 ↓
Recuperación
 ↓
Lecciones aprendidas
```

Además debes indicar:

- Qué datos revisarías.
- Qué herramienta utilizarías.
- Qué acciones tomarías.
- Qué parte del proceso documentarías.
- Qué podría convertirse posteriormente en una nueva regla de detección.

---

# 68. Chuleta de estudio

```text
SOC
→ Centro de Operaciones de Seguridad

N1
→ monitorea + analiza + clasifica + escala

N2
→ investiga + contiene + profundiza

N3
→ casos avanzados + malware + reversing + investigación

SIEM
→ centraliza y correlaciona eventos

EDR
→ detecta y responde en endpoints

IDS
→ detecta

IPS
→ detecta + puede prevenir/bloquear

TRIAGE
→ clasificar y priorizar

INCIDENTE
→ compromiso que requiere respuesta

CONTENCIÓN
→ limitar

ERRADICACIÓN
→ eliminar

RECUPERACIÓN
→ restaurar

PLAYBOOK
→ qué hacer

RUNBOOK
→ cómo hacerlo

FORENSE
→ investigar evidencia digital

CADENA DE CUSTODIA
→ trazabilidad de la evidencia

HASH
→ verificar integridad

THREAT INTELLIGENCE
→ conocer amenazas

MITRE ATT&CK
→ tácticas y técnicas de adversarios

ISO 27001
→ SGSI

NIST CSF 2.0
→ Govern + Identify + Protect + Detect + Respond + Recover
```

---

# 69. Resumen final

La segunda clase profundiza en la operación práctica de la ciberseguridad defensiva.

El **SOC** reúne personas, procesos y tecnologías para monitorear y responder ante amenazas.

El flujo más importante es:

```text
ALERTA
 ↓
TRIAGE
 ↓
INVESTIGACIÓN
 ↓
INCIDENTE
 ↓
ESCALAMIENTO
 ↓
CONTENCIÓN
 ↓
ERRADICACIÓN
 ↓
RECUPERACIÓN
 ↓
LECCIONES APRENDIDAS
 ↓
MEJORA CONTINUA
```

Las herramientas se complementan:

```text
SIEM → eventos y correlación
EDR → endpoints
IDS → detección
IPS → detección + prevención
Firewall → control de tráfico
Forense → investigación de evidencia
Threat Intelligence → conocimiento de amenazas
```

Y los procesos deben estar respaldados por:

```text
Políticas
+
Procedimientos
+
Playbooks
+
Runbooks
+
Capacitación
+
Auditoría
+
Mejora continua
```

Finalmente, la clase conecta la operación del SOC con la gobernanza y el cumplimiento normativo. NIST CSF 2.0 organiza la gestión mediante seis funciones: **Govern, Identify, Protect, Detect, Respond y Recover**. citeturn376201search59

---

# 70. Fuentes utilizadas para complementar

## NIST

**NIST Cybersecurity Framework 2.0**  
https://www.nist.gov/publications/nist-cybersecurity-framework-csf-20

**NIST SP 800-61 Rev. 3 — Incident Response**  
https://csrc.nist.gov/pubs/sp/800/61/r3/final

**NIST Digital Forensics Glossary**  
https://csrc.nist.gov/glossary/term/digital_forensics

**NIST Chain of Custody Glossary**  
https://csrc.nist.gov/glossary/term/chain_of_custody

**NIST Digital Evidence Preservation**  
https://nvlpubs.nist.gov/nistpubs/ir/2022/NIST.IR.8387.pdf

## ISO

**ISO/IEC 27001:2022**  
https://www.iso.org/isoiec-27001-information-security.html

## Chile

**Ley 21.459 — Delitos informáticos**  
https://www.bcn.cl/leychile/navegar?i=1177743

**Ley 21.663 — Ley Marco de Ciberseguridad**  
https://www.bcn.cl/leychile/navegar?i=1202434

**Ley 21.719 — Protección y tratamiento de datos personales**  
https://www.bcn.cl/leychile/navegar?i=1209272

