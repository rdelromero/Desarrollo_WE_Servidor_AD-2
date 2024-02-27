package movimientos.modelo.entities;
import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="movimientos")
@NamedQuery(name="Movimiento.findAll", query="SELECT m FROM Movimiento m")

public class Movimiento {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_movimiento")
	private int idMovimiento;
	
	@ManyToOne
	@JoinColumn(name="id_cuenta")
	private Cuenta cuenta;
	
	private Date fecha;
	
	private double cantidad;
	
	private String operacion;

	public Movimiento(int idMovimiento, Cuenta cuenta, Date fecha, int cantidad, String operacion) {
		super();
		this.idMovimiento = idMovimiento;
		this.cuenta = cuenta;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.operacion = operacion;
	}

	public Movimiento() {
		super();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getIdMovimiento() {
		return idMovimiento;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public Date getFecha() {
		return fecha;
	}

	public double getCantidad() {
		return cantidad;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setIdMovimiento(int idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	@Override
	public String toString() {
		return "Movimiento [idMovimiento=" + idMovimiento + ", cuenta=" + cuenta + ", fecha=" + fecha + ", cantidad="
				+ cantidad + ", operacion=" + operacion + "]";
	}
}
