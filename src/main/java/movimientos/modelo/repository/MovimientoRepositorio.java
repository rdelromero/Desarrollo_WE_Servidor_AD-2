package movimientos.modelo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import movimientos.modelo.entities.Movimiento;

public interface MovimientoRepositorio extends JpaRepository<Movimiento, Integer>{

	
	//Ponemos que devuelva un Integer porque podría devolver null si la suma de las cantidades es 0.
	@Query("select m from Movimiento m where m.cuenta.idCuenta = ?1")
	public List<Movimiento> encontrarPorIdCuentaConDevolucion(int idCuenta);
}
