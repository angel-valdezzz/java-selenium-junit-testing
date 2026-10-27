# Recorridos y trazabilidad

| Escenario | Datos | Operaciones y resultado |
| --- | --- | --- |
| Cliente de México | customers.csv, primera fila | Registro, cierre, acceso, identidad/cuentas y cierre |
| Cliente de Estados Unidos | customers.csv, segunda fila | Mismo negocio, con otra fila |
| Usuario vacío | access_cases.csv | Rechazo exacto y formulario de acceso disponible |
| Contraseña vacía | access_cases.csv | Rechazo exacto y formulario de acceso disponible |
| Ambos vacíos | access_cases.csv | Rechazo exacto y formulario de acceso disponible |
| Registro/cierre | Cliente nuevo | Confirmación de registro y vuelta al formulario |
| Ahorro | Cliente nuevo | Cuenta distinta, tipo SAVINGS y saldo no negativo |
| Transferencia | transfers.csv, 10.00 | Confirmación, débito/crédito exactos y movimientos en ambas cuentas |

No se omiten escenarios al fallar ParaBank. No se utilizan cuentas compartidas ni identificadores fijos.
La ejecución pública no modifica administración ni limpia la base de datos.
El contenedor oficial es opcional; CI inicializa únicamente su propia instancia en loopback.
