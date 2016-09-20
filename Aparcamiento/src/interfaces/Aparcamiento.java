package interfaces;

import java.util.GregorianCalendar;
import java.util.Set;

import dominio.Tarjeta;
import exceptions.ParkingError;

/** Interfaz Aparcamiento
 * 
 * @author Juan Carlos Gonzalez Cabrero
 * @author Oscar Fernandez Nuñez
 * @version 1.0
 */
public interface Aparcamiento {
	
	/** Crea una instancia de <code>Tarjeta</code>, y la asociamos a este aparcamiento. 
	 * 
	 * @param nombre <code>String</code>.
	 * @param apellidos <code>String</code>.
	 * @param dni <code>String</code>.
	 * @throws ParkingError si DNI duplicado.
	 */
	public void crearTarjeta(String nombre, String apellidos, String dni) throws ParkingError;
	
	/** Recupera el conjunto de tarjetas que se encuentran dentro del aparcamiento.
	 * @return Coleccion de tarjetas.
	 */
	public Set<Tarjeta> getTarjetasDentro();
	
	/** Comprueba si la tarjeta asociada a un dni pasado, como parametro, se encuentra dentro del aparcamiento.
	 * @param dni <code>String</code>.
	 * @return <p>TRUE si esta dentro.</p> <p>FALSE si esta fuera.</p>
	 * @throws ParkingError si DNI no asociado a ninguna tarjeta.
	 */
	public boolean isTarjetaDentro(String dni) throws ParkingError;
	
	/** Coloca una tarjeta dentro del aparcamiento, mediante la insercion de esta en la coleccion de tarjetas que estan dentro.
	 * @param t <code>Tarjeta</code>.
	 * @throws ParkingError si la tarjeta no es de este aparcamiento, no esta pagada, o ya se encuentra dentro.
	 */
	public void setTarjetaDentro(Tarjeta t) throws ParkingError;
	
	/** Coloca una tarjeta fuera del aparcamiento, mediante la eliminacion de esta de la coleccion de tarjetas que estan dentro.
	 * @param t  <code>Tarjeta</code>.
	 * @throws ParkingError si la tarjeta no es de este aparcamiento, o ya se encuentra fuera.
	 */
	public void setTarjetaFuera(Tarjeta t) throws ParkingError;
	
	/** Reinicia el estado de la tarjeta, colocandola fuera del aparcamiento sin mas comprobacion alguna.
	 * @param t  <code>Tarjeta</code>.
	 * @throws ParkingError si la tarjeta no es de este aparcamiento.
	 */
	public void reinicioTarjeta(Tarjeta t) throws ParkingError;
	
	/** Procesa el pago realizado para entrar con una determinada tarjeta, estableciendo como pagado hasta el dia que se indica
	 * al realizar la operacion.
	 * @param t <code>Tarjeta</code>.
	 * @param fecha <code>GregorianCalendar</code>, fecha hasta la que se realiza el pago.
	 * @throws ParkingError si la tarjeta no es de este aparcamiento, o la fecha de pago es incorrecta, o ya se encuentra pagada en ese plazo.
	 */
	public void registrarPagoTarjeta(Tarjeta t, GregorianCalendar fecha) throws ParkingError;
	
	/** Recupera una <code>Tarjeta</code> a partir del dni asociadoa ella.
	 * @param dni <code>String</code>.
	 * @return <code>Tarjeta</code> a buscar.
	 * @throws ParkingError si DNI no asociado a ninguna tarjeta.
	 */
	public Tarjeta getTarjetaByDni(String dni) throws ParkingError;
	
}
