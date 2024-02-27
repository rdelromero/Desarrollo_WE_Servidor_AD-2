package movimientos.modelo.dao;
import java.util.List;
import movimientos.modelo.entities.Movimiento;

public interface MovimientoDao {
	
	public abstract List<Movimiento> encontrarTodosConDevolucion(); 
	public abstract boolean registro(Movimiento movimiento);
	public abstract Movimiento encontrarPorIdConDevolucion(int idMovimiento);
	public abstract List<Movimiento> encontrarPorIdCuentaConDevolucion(int idCuenta);
	
}
