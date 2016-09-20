package testers;

import interfaces.Aparcamiento;

import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dominio.AparcamientoImplementation;
import dominio.Tarjeta;
import exceptions.ParkingError;

/** Bateria de pruebas de errores esperados.
 * 
 * @author Juan Carlos Gonzalez Cabrero
 * @author Oscar Fernandez Nuñez
 * @version 1.0
 */
public class TestsFallo {

	Aparcamiento aparcamiento;
	
	@Before
	public void setUp() throws Exception {
		aparcamiento = new AparcamientoImplementation();
	}

	@After
	public void tearDown() throws Exception {
	}

	/** Prueba de creacion de tarjeta con un DNI ya asociado a una tarjeta.
	 * @throws ParkingError DNI repetido.
	 */
	@Test(expected=ParkingError.class)
	public void titularTarjetaRepetido() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		aparcamiento.crearTarjeta("Braulio", "De Todos Los Santos", "71173624D");
	}
	
	/** Prueba de creacion de tarjeta con un DNI no valido.
	 * @throws ParkingError DNI incorrecto.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void dniInvalido() throws ParkingError {
		aparcamiento.crearTarjeta("Burgundofora", "Bieber", "123X");
	}
	
	/** Prueba de comprobacion de tarjeta(dentro o fuera) con un DNI no asociado a ninguna.
	 * @throws ParkingError Tarjeta no encontrada.
	 */
	@Test(expected=ParkingError.class)
	public void dniInexistente() throws ParkingError {
		aparcamiento.isTarjetaDentro("DNI inexistente");
	}
	
	/** Prueba de busqueda de tarjeta con un DNI no asociado a ninguna.
	 * @throws ParkingError Tarjeta no encontrada.
	 */
	@Test(expected=ParkingError.class)
	public void dniInexistente2() throws ParkingError{
		aparcamiento.getTarjetaByDni("DNI inexistente");
	}
	
	/** Prueba de entrada con una tarjeta no valida.
	 * @throws ParkingError Tarjeta no encontrada.
	 */
	@Test(expected=ParkingError.class)
	public void tarjetaIncorrecta() throws ParkingError {
		aparcamiento.setTarjetaDentro(new Tarjeta());
	}
	
	/** Prueba de salida con una tarjeta no valida.
	 * @throws ParkingError Tarjeta no encontrada.
	 */
	@Test(expected=ParkingError.class)
	public void tarjetaIncorrecta2() throws ParkingError {
		aparcamiento.setTarjetaFuera(new Tarjeta());
	}
	
	/** Prueba de reinicio de una tarjeta no valida.
	 * @throws ParkingError Tarjeta no encontrada.
	 */
	@Test(expected=ParkingError.class)
	public void tarjetaIncorrecta3() throws ParkingError {
		aparcamiento.reinicioTarjeta(new Tarjeta());
	}
	
	/** Prueba de pago de una tarjeta no valida.
	 * @throws ParkingError Tarjeta no encontrada.
	 */
	@Test(expected=ParkingError.class)
	public void tarjetaIncorrecta4() throws ParkingError {
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(new Tarjeta(), fecha);
	}
	
	/**
	 * @throws ParkingError La tarjeta no esta al corriente de pagos.
	 */
	@Test(expected=ParkingError.class)
	public void entrarSinPagar() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		aparcamiento.setTarjetaDentro(t);
	}
	
	/** Prueba de pago con fecha en un plazo pagado.
	 * @throws ParkingError Pago duplicado.
	 */
	@Test(expected=ParkingError.class)
	public void pagoDuplicado() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(t, fecha);
		aparcamiento.registrarPagoTarjeta(t, fecha);
	}
	
	/** Prueba de salida sin haber entrado.
	 * @throws ParkingError La tarjeta esta fuera.
	 */
	@Test(expected=ParkingError.class)
	public void falloSalida() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(t, fecha);
		aparcamiento.setTarjetaFuera(t);
	}
	
	/** Prueba de entrada habiendo ya entrado.
	 * @throws ParkingError La tarjeta esta dentro.
	 */
	@Test(expected=ParkingError.class)
	public void falloEntrada() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(t, fecha);
		aparcamiento.setTarjetaDentro(t);
		aparcamiento.setTarjetaDentro(t);
	}
	
	/** Prueba de pago mediante una fecha inferior a la actual.
	 * @throws ParkingError Fecha de pago invalida.
	 */
	@Test(expected=ParkingError.class)
	public void pagoInvalido() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.MONTH, -1);
		aparcamiento.registrarPagoTarjeta(t, fecha);
	}
	
}
