package movimientos.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import movimientos.modelo.dao.CuentaDao;

public class CuentaControladorAdmin {

	@Autowired
	private CuentaDao cuentadao;
	
	@GetMapping("/cuentas")
	public String getMap2 (Model modelo) {
		modelo.addAttribute("listaTodasCuentas", cuentadao.encontrarTodasConDevolucion());
		return "home_cuentas";
	}
}
