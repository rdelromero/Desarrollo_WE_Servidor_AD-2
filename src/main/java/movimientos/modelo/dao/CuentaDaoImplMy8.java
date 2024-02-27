package movimientos.modelo.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import movimientos.modelo.entities.Cuenta;
import movimientos.modelo.repository.CuentaRepositorio;

@Repository
public class CuentaDaoImplMy8 implements CuentaDao{
	@Autowired
	CuentaRepositorio cuentarepositorio;

	@Override
	public List<Cuenta> encontrarTodasConDevolucion() {
		return cuentarepositorio.findAll();
	}
	
	/*El método llamado findById(int) se implementa a través de JpaRepository en CuentaRepositorio.java*/
	
	/*Encuentra y devuelve el objeto de clase Cuenta con el id en cuestión*/
	@Override
	public Cuenta encontrarPorIdConDevolucion(int idCuenta) {
		return cuentarepositorio.findById(idCuenta).orElse(null);
	}
	
	@Override
	public int actualizarCuenta(Cuenta cuenta) {
		int filas = 0;
		Cuenta c1 = null;
		try {
			c1 = cuentarepositorio.findById(cuenta.getIdCuenta()).orElse(null);
			c1 = cuenta;
			cuentarepositorio.save(c1);
			filas=1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return filas;
	}
	
}
