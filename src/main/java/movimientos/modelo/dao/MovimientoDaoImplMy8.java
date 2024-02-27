package movimientos.modelo.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import movimientos.modelo.entities.Cuenta;
import movimientos.modelo.entities.Movimiento;
import movimientos.modelo.repository.MovimientoRepositorio;

@Repository
public class MovimientoDaoImplMy8 implements MovimientoDao{

	@Autowired
	MovimientoRepositorio movimientorepositorio;
	
	@Override
	public List<Movimiento> encontrarTodosConDevolucion() {
		return movimientorepositorio.findAll();
	}

	@Override
	public Movimiento encontrarPorIdConDevolucion(int idMovimiento) {
		return movimientorepositorio.findById(idMovimiento).orElse(null);
	}
	
	@Override
	public boolean registro(Movimiento movimiento) {
		movimientorepositorio.save(movimiento);
		return true;
	}

	@Override
	public List<Movimiento> encontrarPorIdCuentaConDevolucion(int idCuenta) {
		return movimientorepositorio.encontrarPorIdCuentaConDevolucion(idCuenta);
	}
}
