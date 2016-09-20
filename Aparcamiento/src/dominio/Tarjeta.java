package dominio;

import java.util.GregorianCalendar;

/** Clase Tarjeta
 * 
 * @author Juan Carlos Gonzalez Cabrero
 * @author Oscar Fernandez Nuñez
 * @version 1.0
 *
 */
public class Tarjeta {

	private String nombre;
	private String apellidos;
	/**
	 * DNI del titular.
	 */
	private String dni;
	/**
	 * Fecha hasta la que esta al corriente de pago la tarjeta.
	 */
	private GregorianCalendar fechaFin;
	
	public Tarjeta(){
		
	}
	
	/** Inicializa una <code>Tarjeta</code>.
	 * 
	 * @param nombre <code>String</code>
	 * @param apellidos <code>String</code>
	 * @param dni <code>String</code>
	 */
	public Tarjeta(String nombre, String apellidos, String dni) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		fechaFin = null;
	}
	
	/**
	 * @return <code>String</code>
	 */
	public String getNombre(){
		return nombre;
	}
	
	/**
	 * @return <code>String</code>.
	 */
	public String getDni(){
		return dni;
	}
	
	/**
	 * @return <code>GregorianCalendar</code> fecha hasta la que esta pagada la tarjeta.
	 */
	public GregorianCalendar getFechaFin(){
		return fechaFin;
	}
	
	/** Comprueba si el pago de la tarjeta cubre el dia actual.
	 * @return <p>TRUE si la fecha del pago de la tarjeta es posterior al dia actual.</p>
	 * <p>FALSE si la tarjeta esta pendiente de pago.</p>
	 */
	public boolean isPagada(){
		if(fechaFin == null || fechaFin.before(GregorianCalendar.getInstance()))
			return false;
		return true;
	}

	/**
	 * @param fecha <code>GregorianCalendar</code> fecha de fin de pago.
	 */
	public void setFechaFin(GregorianCalendar fecha) {
		fechaFin = fecha;
	}
	
	public String toString(){
		return (dni+" : "+nombre+" "+apellidos);
	}

}
