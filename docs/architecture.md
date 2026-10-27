# Arquitectura del piloto Java

Referencia: [Robot Framework SeleniumLibrary](https://github.com/angel-valdezzz/robotframework-selenium-testing/blob/main/docs/architecture.md).
La separación es la misma: escenarios → casos de uso → Pages declarativas.
La composición de JUnit sustituye los imports de infraestructura de Robot.

| Paquete / carpeta | Responsabilidad |
| --- | --- |
| `parabank.tests` | Recorridos completos. Sin Selenium, selectores ni construcción de datos HTTP/HTML. |
| `parabank.usecases` | Registro, acceso, consulta y banca; interacciones y verificaciones de negocio. |
| `parabank.pages` | Clases finales con constantes By; sin métodos de interacción ni estado del navegador. |
| `parabank.config` | URL/opciones, sesión por test, composición de casos de uso y lectura CSV. |
| `parabank.data` | Filas tipadas, credenciales con representación ocultada y referencias de cuentas. |
| `data/tables` | Entradas y resultados esperados; se conservan texto y códigos postales con ceros. |
| `parabank.architecture` | Restricciones de dependencias y responsabilidades ejecutables. |

Todos estos paquetes están en src/test/java porque constituyen código de automatización, no una aplicación a distribuir.
Eso no convierte a config, Pages o casos de uso en escenarios: solo parabank.tests contiene los recorridos E2E.

## Un recorrido

CustomerJourneyTest recibe una fila Customer y casos de uso inyectados por BrowserSession:
registro → cierre → acceso → consulta de identidad y cuentas → cierre.
CustomerRegistration recibe la fila; no lee CSV ni genera usuarios. RegistrationPage solo contiene locators.
BrowserSession crea Chrome y abre el inicio antes del caso; conserva capturas de fallo y cierra la sesión en finally.

La suite incluye los ocho recorridos de la referencia: dos clientes, tres accesos incompletos,
registro/cierre, ahorro y transferencia. Transferencia comprueba confirmación, saldos exactos en ambas
cuentas y movimientos visibles. Los importes usan BigDecimal. Cada test crea sus propias cuentas.
