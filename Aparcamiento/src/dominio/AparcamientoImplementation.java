package dominio;

import interfaces.Aparcamiento;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import exceptions.ParkingError;

/** Clase AparcamientoImplementation
 * 
 * @author Juan Carlos Gonzalez Cabrero
 * @author Oscar Fernandez Nuñez
 * @version 1.0
 */
public class AparcamientoImplementation implements Aparcamiento{

	/**
	 * Coleccion de tarjetas asociadas al aparcamiento.
	 */
	private Set<Tarjeta> tarjetas;
	/**
	 * Coleccion de tarjetas que se encuentran dentro del aparcamiento. 
	 */
	private Set<Tarjeta> tarjetasDentro;
	
	/**
	 * Inicializa un <code>Aparcamiento</code>.
	 */
	public AparcamientoImplementation() {
		tarjetas = new HashSet<Tarjeta>();
		tarjetasDentro = new HashSet<Tarjeta>();
	}
	
	@Override
	public void crearTarjeta(String nombre, String apellidos, String dni) throws ParkingError{
		
		if(!Utils.checkDNI(dni))
			throw new IllegalArgumentException("DNI incorrecto.");
		// Comprobamos que no exista otra tarjeta con el mismo DNI.
		if(findTarjeta(dni) != null)
			throw new ParkingError("Ya existe una tarjeta asociada al mismo titular.");
		
		Tarjeta t = new Tarjeta(nombre, apellidos, dni);
		tarjetas.add(t);
	}

	@Override
	public Set<Tarjeta> getTarjetasDentro() {
		return tarjetasDentro;
	}

	@Override
	public boolean isTarjetaDentro(String dni) throws ParkingError {
		
		Tarjeta t = getTarjetaByDni(dni);
		if(tarjetasDentro.contains(t))
			return true;
		return false;
	}

	@Override
	public void setTarjetaDentro(Tarjeta t) throws ParkingError {
		
		if(!tarjetaExists(t))
			throw new ParkingError("La tarjeta no pertenece a este aparcamiento.");
		
		// Comprobamos que este al corriente de pago y no se encuentre dentro.
		if(t.getFechaFin() == null || new GregorianCalendar().after(t.getFechaFin()))
			throw new ParkingError("La tarjeta no esta al corriente de pagos.");
		if( tarjetasDentro.contains(t))
			throw new ParkingError("Ya se encuentra dentro esta tarjeta.");
		else
			tarjetasDentro.add(t);
	}

	@Override
	public void setTarjetaFuera(Tarjeta t) throws ParkingError {
		
		if(!tarjetaExists(t))
			throw new ParkingError("La tarjeta no pertenece a este aparcamiento.");
		
		// Comprobamos que la tarjeta no este fuera.
		if(!tarjetasDentro.contains(t))
			throw new ParkingError("La tarjeta esta fuera. Contacte con conserjeria.");
		else
			tarjetasDentro.remove(t);
	}

	@Override
	public void reinicioTarjeta(Tarjeta t) throws ParkingError {
		
		if(!tarjetaExists(t))
			throw new ParkingError("La tarjeta no pertenece a este aparcamiento.");
		
		tarjetasDentro.remove(t);
	}

	@Override
	public void registrarPagoTarjeta(Tarjeta t, GregorianCalendar fecha) throws ParkingError {
		
		if(!tarjetaExists(t))
			throw new ParkingError("La tarjeta no pertenece a este aparcamiento.");
		if(fecha.before(GregorianCalendar.getInstance()))
			throw new ParkingError("La fecha no puede ser anterior a la actual.");
		if(t.getFechaFin() != null && !t.getFechaFin().before(fecha))
			throw new ParkingError("La tarjeta ya esta pagada hasta dicha fecha.");
		
		t.setFechaFin(fecha);
	}

	@Override
	public Tarjeta getTarjetaByDni(String dni) throws ParkingError {
		
		Tarjeta t = findTarjeta(dni);
		if(t == null)
			throw new ParkingError("No existe ninguna tarjeta asociada al DNI "+dni+".");
		return t;
	}
	
	/** <p>Nos permite buscar una tarjeta a partir del dni.</p>
	 * <p>Esta operacion nos permite reutilizar el codigo puesto que
	 * ademas de para devolver una tarjeta nos permite realizar comprobaciones
	 * para saber si existe alguna tarjeta asociada al dni.</p>
	 * @param dni <code>String</code>
	 * @return Devuelve la <code>Tarjeta</code> si existe, sino devuelve <b>null</b>.
	 */
	private Tarjeta findTarjeta(String dni) {
		for(Tarjeta t: tarjetas)
			if(t.getDni().equals(dni))
				return t;
		return null;
	}
	
	/**Comprueba si una tarjeta pertenece a este aparcamiento.
	 * @param t <code>Tarjeta</code>
	 * @return <p>TRUE si pertenece al conjunto de tarjetas del aparcamiento.</p>
	 * <p>FALSE si no existe esta entidad de tarjeta.</p>
	 */
	private boolean tarjetaExists(Tarjeta t) {
		return tarjetas.contains(t);
	}

}
