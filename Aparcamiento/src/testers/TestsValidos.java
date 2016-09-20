package testers;

import static org.junit.Assert.assertEquals;
import interfaces.Aparcamiento;

import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dominio.AparcamientoImplementation;
import dominio.Tarjeta;
import exceptions.ParkingError;

/** Bateria de pruebas de casos de uso funcionales.
 * 
 * @author Juan Carlos Gonzalez Cabrero
 * @author Oscar Fernandez Nuñez
 * @version 1.0
 */
public class TestsValidos {
	
	Aparcamiento aparcamiento;

	@Before
	public void setUp() throws Exception {
		aparcamiento = new AparcamientoImplementation();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void creacionTarjeta() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
	}
	
	@Test
	public void comprobacionTarjetaDentro() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(t, fecha);
		aparcamiento.setTarjetaDentro(t);
		boolean isDentro = aparcamiento.isTarjetaDentro("71173624D");
		assertEquals(true, isDentro);
	}
	
	@Test
	public void listaTarjetasDentro() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta tarjeta = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(tarjeta, fecha);
		aparcamiento.setTarjetaDentro(tarjeta);
		Set<Tarjeta> lista = aparcamiento.getTarjetasDentro();
		assertEquals(1, lista.size());
	}
	
	@Test
	public void reinicioTarjeta() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(t, fecha);
		aparcamiento.setTarjetaDentro(t);
		aparcamiento.reinicioTarjeta(t);
		boolean isDentro = aparcamiento.isTarjetaDentro("71173624D");
		assertEquals(false, isDentro);
	}
	
	@Test
	public void registroPago() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(t, fecha);
		assertEquals(true, t.isPagada());
	}
	
	@Test
	public void busquedaTarjeta() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		assertEquals("Juan", t.getNombre());
	}
	
	@Test
	public void entradaParking() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(t, fecha);
		aparcamiento.setTarjetaDentro(t);
		assertEquals(true, aparcamiento.getTarjetasDentro().contains(t));
	}
	
	@Test
	public void salidaParking() throws ParkingError {
		aparcamiento.crearTarjeta("Juan", "Gomez", "71173624D");
		Tarjeta t = aparcamiento.getTarjetaByDni("71173624D");
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.add(GregorianCalendar.YEAR, 1);
		aparcamiento.registrarPagoTarjeta(t, fecha);
		aparcamiento.setTarjetaDentro(t);
		aparcamiento.setTarjetaFuera(t);
		assertEquals(false, aparcamiento.getTarjetasDentro().contains(t));
	}
	
}
