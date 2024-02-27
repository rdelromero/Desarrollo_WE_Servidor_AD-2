package movimientos.modelo.dao;

import java.util.List;

import movimientos.modelo.entities.Cuenta;

public interface CuentaDao {
	public abstract List<Cuenta> encontrarTodasConDevolucion(); 
	public abstract Cuenta encontrarPorIdConDevolucion(int idPerfil);
	public abstract int actualizarCuenta(Cuenta cuenta); 

}
