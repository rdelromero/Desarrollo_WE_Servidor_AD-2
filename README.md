Desarrollo web en entorno servidor

# AD-2. Spring Data JPA : Cajero Virtual

En esta práctica, vamos a realizar la implementación de un cajero virtual, aplicando el patrón MVC con Spring Boot con capa de persistencia para acceso a los datoscon Spring Data JPA.

Para la realización de la práctica se empleará una base de datos con dos tablas: la tabla de cuentas, que almacena las cuentas del banco, y movimientos, que registratodos los movimientos realizados en cada cuenta.

La estructura de ambas tablas se muestra en la siguiente imagen:

![imagen](/Desarrollo_WE_Servidor_AD-2_imagen1.png)

El script de la base de datos lo aporta el profesor en Unidades formativas en la UF4 junto a la actividad.
La base de datos ya tiene dos cuentas para probar.
El aspecto de las páginas de la aplicación será similar al que se muestra en la siguiente imagen:

![imagen](/Desarrollo_WE_Servidor_AD-2_imagen2.png)

Al iniciarse la aplicación se solicitará el número de cuenta con la que se quiere operar y la cuenta se guardará en un atributo de sesión. Tras validar la misma:
- Si la cuenta no existe no se le deja seguir adelante, y se mostrará el mensaje ‘Cuenta no existe’.
- Si la cuenta existe, aparece un menú con las opciones que se pueden realizar desde el cajero, añadir cerrar sesión.

Al seleccionar ‘Ingresar’ o ‘Extraer’, se nos solicitará la cantidad y la operación quedará reflejada en la tabla de movimientos (abono, cargo), además de actualizar elsaldo de la cuenta, en la tabla de ‘Cuentas’.

Si al extraer dinero, el saldo da negativo impedir que se saque esa cantidad, con el mensaje, ‘saldo insuficiente’.

Con la opción de ‘Transferencia’, se solicitará la cuenta destino y la cantidad a transferir (si no tengo saldo, informar con un mensaje, e impedir la transferencia).

Si la cuenta no existe, se muestra el mensaje ‘Cuenta de destino no existe’.

En esta operación, se registrará un movimiento de extracción en la cuenta origen ("cargo por transferencia") y uno de ingreso en la de destino  ("abono portransferencia"), además de almacenar (modificar en la base de datos la tabla cuenta) los saldos de ambas cuentas.

Finalmente, la operación ‘Ver movimientos’, nos mostrará una página con la lista de movimientos realizados sobre la cuenta, además de mostrar el saldo de la misma.

Para probar la transferencia, cierras la sesión y te conectas a la cuenta de destino, y ves los movimientos para comprobar que el apunte esta en la lista.