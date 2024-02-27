package movimientos.controller;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import movimientos.modelo.dao.CuentaDao;
import movimientos.modelo.dao.MovimientoDao;
import movimientos.modelo.entities.Cuenta;
import movimientos.modelo.entities.Movimiento;

@Controller
public class CuentaControlador {
	@Autowired
	private CuentaDao cuentadao;
	@Autowired
	private MovimientoDao movimientodao;
	
	@GetMapping("/")
	public String getMap1 () {
		return "home";
	}
	
    @PostMapping("/")
	public String login(@RequestParam int idCuenta, HttpSession session, Model modelo) {
	    if (cuentadao.encontrarPorIdConDevolucion(idCuenta) != null) {
	        //Establecemos una sesión que tiene un atributo de tipo int llamado idCuentaSesion
	    	//También podríamos haber establecido como atributo de sesion un atributo de tipo Cuenta, pero luego tendría que estar
	    	//actualizando el objeto sesion porque puede cambiar el valor de su atributo saldo
	        session.setAttribute("idCuentaSesion", idCuenta);
	        System.out.println("El objeto sesion es "+session.getAttribute("idCuentaSesion")+".");
	        return "redirect:/cuenta";
	    } else {
	        modelo.addAttribute("mensaje", "Cuenta con idCuenta "+idCuenta+" no existe.");
	        return "home";
	    }
	}
	
	//Opción equivalente
    /*@PostMapping
	public String login(@RequestParam int idCuenta, HttpSession session, RedirectAttributes redAtt) {
	    if (cuentadao.encontrarPorIdConDevolucion(idCuenta) != null) {
	        //Establecemos una sesión que tiene un atributo de tipo int llamado idCuentaSesion
	        session.setAttribute("idCuentaSesion", idCuenta);
	        System.out.println("El objeto sesion es "+session.getAttribute("idCuentaSesion")+".");
	        return "redirect:/cuenta";
	    } else {
	    	redAtt.addFlashAttribute("mensaje", "Cuenta con idCuenta "+idCuenta+" no existe.");
	        return "redirect:/";
	    }
	}*/
    
	@GetMapping("/cuenta")
	public String vgetMap2(HttpSession session, Model modelo) {
	    modelo.addAttribute("cuenta", cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion")));
	    return "home_cuenta";
	}
    
	@GetMapping("/cuenta/cerrarsesion")
	public String getMap3(HttpSession session) {
	    session.invalidate();
	    return "home";
	}
    
	@GetMapping("/cuenta/ingresar")
	public String getMap4(HttpSession session, Model modelo) {
		modelo.addAttribute("cuenta", cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion")));
	    return "home_cuenta_ingresar";
	}
	
	@PostMapping("/cuenta/ingresar")
	public String ingresarDinero(@RequestParam double cantidad, HttpSession session, RedirectAttributes redAtt) {
		Movimiento movimiento= new Movimiento();
		movimiento.setCuenta(cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion")));
		movimiento.setFecha(new Date());
		movimiento.setCantidad(cantidad);
		movimiento.setOperacion("ingreso");
	    Cuenta cuenta = cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion"));
	    cuenta.setSaldo(cuenta.getSaldo()+cantidad);
		//Actualizo base de datos
		movimientodao.registro(movimiento);
	    cuentadao.actualizarCuenta(cuenta);
		redAtt.addFlashAttribute("mensaje", "Ingreso realizado correctamente.");
	    return "redirect:/cuenta"; // Redirige a la página de la cuenta
	}
	
	@GetMapping("/cuenta/extraer")
	public String getMap5(HttpSession session, Model modelo) {
		modelo.addAttribute("cuenta", cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion")));
	    return "home_cuenta_extraer";
	}
	
	@PostMapping("/cuenta/extraer")
	public String extraerDinero(@RequestParam double cantidad, HttpSession session, RedirectAttributes redAtt) {
		Cuenta cuenta = cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion"));
		if (cuenta.getSaldo()>=cantidad) {
			Movimiento movimiento= new Movimiento();
			movimiento.setCuenta(cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion")));
			movimiento.setFecha(new Date());
			movimiento.setCantidad(cantidad);
			movimiento.setOperacion("extracción");
		    
		    cuenta.setSaldo(cuenta.getSaldo()-cantidad);
			//Actualizo base de datos
			movimientodao.registro(movimiento);
		    cuentadao.actualizarCuenta(cuenta);
			redAtt.addFlashAttribute("mensaje", "Extracción realizada correctamente.");
		    return "redirect:/cuenta"; // Redirige a la página de la cuenta
		} else {
			redAtt.addFlashAttribute("mensaje", "Saldo insuficiente. No se ha podido realizar el ingreso.");
			return "redirect:/cuenta";
		}
	}
	
	@GetMapping("/cuenta/movimientos")
	public String getMap6(HttpSession session, Model modelo) {
		Cuenta cuenta = cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion"));
		modelo.addAttribute("mensaje", "Movimientos de la cuenta de id "+cuenta.getIdCuenta()+" cuyo saldo actual es "+cuenta.getSaldo()+" € .");
	    modelo.addAttribute("listaMovimientos", movimientodao.encontrarPorIdCuentaConDevolucion(cuenta.getIdCuenta()));
	    return "home_cuenta_movimientos";
	}
	
	@GetMapping("/cuenta/transferir")
	public String getMap7(HttpSession session, Model modelo) {
	    modelo.addAttribute("cuenta", cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion")));
	    return "home_cuenta_transferir";
	}

	@PostMapping("/cuenta/transferir")
	public String transferirDinero(@RequestParam double cantidad, @RequestParam int idCuentaDestino, HttpSession session, RedirectAttributes redAtt) {
		Cuenta cuentaorigen = cuentadao.encontrarPorIdConDevolucion((int)session.getAttribute("idCuentaSesion"));
		Cuenta cuentadestino = cuentadao.encontrarPorIdConDevolucion(idCuentaDestino);
		if (cuentaorigen.getIdCuenta()==cuentadestino.getIdCuenta()) {
			redAtt.addFlashAttribute("mensaje", "La cuenta destino no puede ser la cuenta de origen. No se ha podido realizar la transferencia.");
		} else if (cuentaorigen.getSaldo()>=cantidad && cuentadestino != null) {
			cuentaorigen.setSaldo(cuentaorigen.getSaldo()-cantidad);
			cuentadestino.setSaldo(cuentadestino.getSaldo()+cantidad);
			//////////////////
			Movimiento movimientoorigen = new Movimiento();
			movimientoorigen.setCuenta(cuentaorigen);
			movimientoorigen.setFecha(new Date());
			movimientoorigen.setCantidad(cantidad);
			movimientoorigen.setOperacion("extracción (cargo por transferencia)");
			cuentadao.actualizarCuenta(cuentaorigen);
			
			Movimiento movimientodestino = new Movimiento();
			movimientodestino.setCuenta(cuentaorigen);
			movimientodestino.setFecha(new Date());
			movimientodestino.setCantidad(cantidad);
			movimientodestino.setOperacion("ingreso (abono por transferencia)");
			cuentadao.actualizarCuenta(cuentadestino);
			
			//Actualizo base de datos
			movimientodao.registro(movimientoorigen);
			movimientodao.registro(movimientodestino);
			redAtt.addFlashAttribute("mensaje", "Transferencia realizada correctamente.");
		} else if (cuentaorigen.getSaldo()<cantidad && cuentadestino != null){
			redAtt.addFlashAttribute("mensaje", "Saldo insuficiente. No se ha podido realizar la transferencia.");
		} else if (cuentaorigen.getSaldo()>=cantidad && cuentadestino == null){
			redAtt.addFlashAttribute("mensaje", "Cuenta de destino no existe. No se ha podido realizar la transferencia");
		} else {
			redAtt.addFlashAttribute("mensaje", "Saldo insuficiente y además la cuenta de destino no existe.");
		}
		return "redirect:/cuenta";
	}
	
}