package movimientos.modelo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import movimientos.modelo.entities.Cuenta;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer>{

	
	
}
