# Java · Selenium · JUnit — ParaBank

[English](README.md)

Este piloto aplica la arquitectura consolidada en Robot Framework + SeleniumLibrary:
**escenarios de negocio → casos de uso → Pages declarativas**.
Configuración, ciclo de vida y datos quedan separados.

## Instalar y ejecutar

JDK 17+ (CI usa 21), Chrome e Internet para Maven/Selenium Manager.
Maven Wrapper fija la distribución y verifica su checksum. No utiliza lanzadores Python.

```powershell
.\mvnw.cmd -B verify
.\mvnw.cmd -B -Pquality spotless:check verify
.\mvnw.cmd -B -Dbrowser.headless=false verify
```

En Linux/macOS usa `./mvnw`. Formato: `./mvnw spotless:apply`.
URL pública predeterminada: https://parabank.parasoft.com/parabank/.
Otra instancia: `-Dparabank.url=http://127.0.0.1:8080/parabank/`.
Timeout configurable: `-Dbrowser.timeout=25`.

## Responsabilidades

| Carpeta bajo src/test/java/parabank | Contenido |
| --- | --- |
| tests | Recorridos de negocio, sin Selenium ni selectores |
| usecases | Operaciones completas y sus verificaciones |
| pages | Localizadores declarativos por página |
| config | Navegador por caso, composición, settings y carga CSV |
| data | Filas tipadas y credenciales con representación ocultada |
| architecture | Restricciones de dependencias verificables |

Los archivos de entrada están en `data/tables`. Cada caso recibe sus datos;
los casos de uso no leen archivos y las Pages no ejecutan acciones.
Se preserva el código postal 01000 como texto y se generan credenciales únicas por ejecución.

Ocho casos: dos recorridos de cliente, tres accesos incompletos, registro/cierre,
ahorro y transferencia. La transferencia comprueba importe, origen, destino,
saldos exactos y movimientos en ambas cuentas.
Resultados JUnit: `target/surefire-reports`; capturas al fallar: `target/evidence`.

[Arquitectura](docs/architecture.md) · [Matriz](docs/scenarios.md) · [Validación](VALIDATION.md).
El contenedor oficial de `compose.yaml` es opcional; CI prepara solo su propia instancia local.
