package movimientos.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import movimientos.modelo.dao.CuentaDao;
import movimientos.modelo.dao.MovimientoDao;
import movimientos.modelo.entities.Movimiento;

@Controller
public class MovimientoControlador {

	@Autowired
	private MovimientoDao movimientodao;
	
	@GetMapping("/movimientos")
	public String getMap2 (Model modelo) {
		modelo.addAttribute("listaTodosMovimientos", movimientodao.encontrarTodosConDevolucion());
		return "home_movimientos";
	}
	
	@GetMapping("/movimientos/leer/{id}")
	public String getMap2(@PathVariable("id") int idMovimiento, Model modelo, RedirectAttributes redAtt) {
		Movimiento movimiento = movimientodao.encontrarPorIdConDevolucion(idMovimiento);
		if (movimiento != null) {
			modelo.addAttribute("movimiento", movimiento);
			return "home_movimientos_leer";
		} else {
			redAtt.addFlashAttribute("mensaje", "No hay datos de un movimiento con ese id.");
		    //En caso de que no exista el movimiento, volvemos a usuarios.
		    return "redirect:/movimientos";
		}
	}
}
