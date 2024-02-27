drop database if exists cajero_2023;
create database cajero_2023;
use cajero_2023;
drop table if exists cuentas;
create table cuentas
(id_cuenta int not null auto_increment primary key,
saldo double not null,
tipo_cuenta varchar(20) not null,
check (tipo_cuenta in ('AHORRO','CORRIENTE','JUVENIL'))
);
INSERT INTO CUENTAS VALUES(1000, 2000,'AHORRO');
INSERT INTO CUENTAS VALUES(2000, 12000,'CORRIENTE');
commit;

CREATE TABLE MOVIMIENTOS
(ID_MOVIMIENTO INT NOT NULL auto_increment PRIMARY KEY,
ID_CUENTA INT NOT NULL,
FECHA DATETIME,
CANTIDAD DOUBLE,
OPERACION VARCHAR(45),
FOREIGN KEY(ID_CUENTA) REFERENCES CUENTAS(ID_CUENTA)
);

INSERT MOVIMIENTOS VALUES(1, 1000, "2023-10-31 12:30:45", 100, 'ingreso');
INSERT MOVIMIENTOS VALUES(2, 2000, "2023-11-01 17:30:45", 100, 'extraccion');

drop user ucajero_edix;
CREATE USER ucajero_edix identified by 'ucajero';
grant all privileges on cajero_2023.* to ucajero_edix;
