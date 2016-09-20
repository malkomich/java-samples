package aislamiento;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;

import dominio.Asignatura;
import dominio.Prueba;

/**
 * Test de aislamiento de la clase Asignatura.
 * 
 * @author juangon
 *
 */
public class TestAislamiento {

	private final String DNI_ALUMNO = "71647382B";
	private final double NOTA_ALEATORIA = 5.10;
	private final double NOTA_MAX_ASIGNATURA = 10.0;

	private Asignatura asignatura;

	@Mock
	private Prueba prueba1;
	@Mock
	private Prueba prueba2;

	@Before
	public void setUp() {
		String nombre = "nombre";
		String descripcion = "descripcion";
		GregorianCalendar fechaIni = new GregorianCalendar();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.add(GregorianCalendar.YEAR, 1);
		asignatura = new Asignatura(nombre, descripcion, NOTA_MAX_ASIGNATURA,
				fechaIni, fechaFin);
		// Primer periodo
		GregorianCalendar fechaPeriodo1 = new GregorianCalendar();
		fechaPeriodo1.setTime(fechaIni.getTime());
		fechaPeriodo1.add(GregorianCalendar.MONTH, 5);
		asignatura.definirPeriodo(fechaIni, fechaPeriodo1);
		// Segundo periodo
		GregorianCalendar fechaPeriodo2 = new GregorianCalendar();
		fechaPeriodo2.setTime(fechaFin.getTime());
		fechaPeriodo2.add(GregorianCalendar.MONTH, -5);
		asignatura.definirPeriodo(fechaPeriodo2, fechaFin);
	}

	// TESTS DE ASIGNACION DE PRUEBAS

	/**
	 * Vincular una prueba a la asignatura.
	 */
	@Test
	public void addPrueba() {
		prueba1 = createMock(Prueba.class);
		double peso = 1.0;

		GregorianCalendar fecha = asignatura.getFechaInicial();

		// Fijar las espectativas del mockObject
		expect(prueba1.getFecha()).andReturn(fecha).once();
		expect(prueba1.getPeso()).andReturn(peso).once();
		replay(prueba1);// Mock Object listo

		// Operacion en la que interviene el objeto a simular
		asignatura.crearPrueba(prueba1);

		// Verificacion de comportamiento del Mock Object
		verify(prueba1);

		assertEquals(1, asignatura.getPruebas().size());
	}

	/**
	 * Vincular una prueba que este fuera de las fechas que pertenezcan a algun
	 * periodo.
	 */
	@Test(expected = RuntimeException.class)
	public void addPruebaFueraPeriodo() {
		prueba1 = createMock(Prueba.class);
		double peso = 0.5;

		GregorianCalendar fechaPrueba = new GregorianCalendar();
		fechaPrueba.setTime(asignatura.getFechaInicial().getTime());
		fechaPrueba.add(GregorianCalendar.MONTH, 6);

		// Fijar las espectativas del mockObject
		expect(prueba1.getFecha()).andReturn(fechaPrueba).once();
		expect(prueba1.getPeso()).andReturn(peso).once();
		replay(prueba1);// Mock Object listo

		// Operacion en la que interviene el objeto a simular
		asignatura.crearPrueba(prueba1);

		// Verificacion de comportamiento del Mock Object
		verify(prueba1);
	}

	/**
	 * Crear prueba superando el peso maximo en un mismo periodo.
	 */
	@Test(expected = RuntimeException.class)
	public void superarPesoPeriodo() {
		prueba1 = createMock(Prueba.class);
		double peso1 = 0.6;
		prueba2 = createMock(Prueba.class);
		double peso2 = 0.7;

		GregorianCalendar fechaPrueba1 = new GregorianCalendar();
		fechaPrueba1.setTime(asignatura.getFechaInicial().getTime());

		GregorianCalendar fechaPrueba2 = new GregorianCalendar();
		fechaPrueba2.setTime(fechaPrueba1.getTime());
		fechaPrueba2.add(GregorianCalendar.DAY_OF_MONTH, 1);

		// Fijar las espectativas de los mockObject
		expect(prueba1.getFecha()).andReturn(fechaPrueba1).times(2);
		expect(prueba1.getPeso()).andReturn(peso1).times(2);
		replay(prueba1);

		expect(prueba2.getFecha()).andReturn(fechaPrueba2).times(2);
		expect(prueba2.getPeso()).andReturn(peso2).once();
		replay(prueba2);

		// Operacion en la que intervienen los objetos a simular
		asignatura.crearPrueba(prueba1);
		assertTrue(asignatura.getPesoPeriodo(1) <= 1);
		asignatura.crearPrueba(prueba2);

		// Verificacion de comportamiento de los Mock Object
		verify(prueba1);
		verify(prueba2);
	}

	/**
	 * Crear prueba superando el peso maximo en periodos distintos.
	 */
	@Test
	public void pesoRepartido() {
		prueba1 = createMock(Prueba.class);
		double peso1 = 0.6;
		prueba2 = createMock(Prueba.class);
		double peso2 = 0.7;

		GregorianCalendar fechaPrueba1 = new GregorianCalendar();
		fechaPrueba1.setTime(asignatura.getFechaInicial().getTime());

		GregorianCalendar fechaPrueba2 = new GregorianCalendar();
		fechaPrueba2.setTime(asignatura.getFechaFinal().getTime());
		fechaPrueba2.add(GregorianCalendar.DAY_OF_MONTH, -1);

		// Fijar las espectativas de los mockObject
		expect(prueba1.getFecha()).andReturn(fechaPrueba1).times(2);
		expect(prueba1.getPeso()).andReturn(peso1).once();
		replay(prueba1);

		expect(prueba2.getFecha()).andReturn(fechaPrueba2).once();
		expect(prueba2.getPeso()).andReturn(peso2).once();
		replay(prueba2);

		// Operacion en la que intervienen los objetos a simular
		asignatura.crearPrueba(prueba1);
		assertTrue(asignatura.getPesoPeriodo(1) <= 1);
		asignatura.crearPrueba(prueba2);
		assertTrue(asignatura.getPesoPeriodo(2) <= 1);

		// Verificacion de comportamiento de los Mock Object
		verify(prueba1);
		verify(prueba2);

	}

	// TESTS DE LAS CALIFICACIONES DE LA ASIGNATURA

	/**
	 * Obtener el listado de calificaciones de uno de los periodos.
	 */
	@Test
	public void calificacionesPeriodo() {
		prueba1 = createMock(Prueba.class);
		double notaMaxima1 = 8;
		double peso1 = 0.4;
		prueba2 = createMock(Prueba.class);
		double notaMaxima2 = 5;
		double peso2 = 0.3;

		// Creamos una tercera prueba en otro periodo para comprobar que no la
		// toma en cuenta.
		Prueba prueba3 = createMock(Prueba.class);

		GregorianCalendar fechaPrueba1 = new GregorianCalendar();
		fechaPrueba1.setTime(asignatura.getFechaInicial().getTime());

		GregorianCalendar fechaPrueba2 = new GregorianCalendar();
		fechaPrueba2.setTime(fechaPrueba1.getTime());
		fechaPrueba2.add(GregorianCalendar.DAY_OF_MONTH, 1);

		GregorianCalendar fechaPrueba3 = new GregorianCalendar();
		fechaPrueba3.setTime(asignatura.getFechaFinal().getTime());
		fechaPrueba3.add(GregorianCalendar.DAY_OF_MONTH, -1);

		Map<String, Double> notas = new HashMap<String, Double>();
		notas.put(DNI_ALUMNO, NOTA_ALEATORIA);

		// Fijar las espectativas de los mockObject
		expect(prueba1.getFecha()).andReturn(fechaPrueba1).times(4);
		expect(prueba1.getCalificaciones()).andReturn(notas).once();
		expect(prueba1.getPeso()).andReturn(peso1).times(3);
		expect(prueba1.getNotaMaxima()).andReturn(notaMaxima1).once();
		replay(prueba1);

		expect(prueba2.getFecha()).andReturn(fechaPrueba2).times(3);
		expect(prueba2.getCalificaciones()).andReturn(notas).once();
		expect(prueba2.getPeso()).andReturn(peso2).times(2);
		expect(prueba2.getNotaMaxima()).andReturn(notaMaxima2).once();
		replay(prueba2);

		expect(prueba3.getFecha()).andReturn(fechaPrueba3).times(2);
		expect(prueba3.getPeso()).andReturn(peso1).once();
		replay(prueba3);

		// Operacion en la que intervienen los objetos a simular
		asignatura.crearPrueba(prueba1);
		asignatura.crearPrueba(prueba2);
		asignatura.crearPrueba(prueba3);
		Map<String, Double> listado = asignatura.getCalificaciones(1);

		// Verificacion de comportamiento de los Mock Object
		verify(prueba1);
		verify(prueba2);
		verify(prueba3);
		double notaEsperada = (NOTA_ALEATORIA * peso1 * NOTA_MAX_ASIGNATURA / notaMaxima1)
				+ (NOTA_ALEATORIA * peso2 * NOTA_MAX_ASIGNATURA / notaMaxima2);
		assertEquals(notaEsperada, listado.get(DNI_ALUMNO), 0.1);
	}

	/**
	 * Obtener el listado de calificaciones de la asignatura en total.
	 */
	@Test
	public void calificacionesTotal() {
		prueba1 = createMock(Prueba.class);
		double notaMaxima1 = 8;
		double peso1 = 0.4;
		prueba2 = createMock(Prueba.class);
		double notaMaxima2 = 6;
		double peso2 = 0.3;

		GregorianCalendar fechaPrueba1 = new GregorianCalendar();
		fechaPrueba1.setTime(asignatura.getFechaInicial().getTime());

		GregorianCalendar fechaPrueba2 = new GregorianCalendar();
		fechaPrueba2.setTime(asignatura.getFechaFinal().getTime());
		fechaPrueba2.add(GregorianCalendar.DAY_OF_MONTH, -1);

		Map<String, Double> notas = new HashMap<String, Double>();
		notas.put(DNI_ALUMNO, NOTA_ALEATORIA);

		// Fijar las espectativas de los mockObject
		expect(prueba1.getFecha()).andReturn(fechaPrueba1).times(4);
		expect(prueba1.getCalificaciones()).andReturn(notas).once();
		expect(prueba1.getPeso()).andReturn(peso1).times(2);
		expect(prueba1.getNotaMaxima()).andReturn(notaMaxima1).once();
		replay(prueba1);

		expect(prueba2.getFecha()).andReturn(fechaPrueba2).times(3);
		expect(prueba2.getCalificaciones()).andReturn(notas).once();
		expect(prueba2.getPeso()).andReturn(peso2).times(2);
		expect(prueba2.getNotaMaxima()).andReturn(notaMaxima2).once();
		replay(prueba2);

		// Operacion en la que intervienen los objetos a simular
		asignatura.crearPrueba(prueba1);
		asignatura.crearPrueba(prueba2);
		Map<String, Double> listado = asignatura.getCalificaciones();

		// Verificacion de comportamiento de los Mock Object
		verify(prueba1);
		verify(prueba2);

		double pesoPeriodo1 = asignatura.getPesoPeriodo(1);
		double pesoPeriodo2 = asignatura.getPesoPeriodo(2);
		double notaEsperada = pesoPeriodo1
				* (NOTA_ALEATORIA * peso1 * NOTA_MAX_ASIGNATURA / notaMaxima1)
				+ pesoPeriodo2
				* (NOTA_ALEATORIA * peso2 * NOTA_MAX_ASIGNATURA / notaMaxima2);
		assertEquals(notaEsperada, listado.get(DNI_ALUMNO), 0.1);
	}

	/**
	 * Comprobar que el peso total de las pruebas de un periodo es menor o igual
	 * a 1.
	 */
	@Test
	public void pesoPruebasPeriodo() {
		prueba1 = createMock(Prueba.class);
		double peso1 = 0.4;
		prueba2 = createMock(Prueba.class);
		double peso2 = 0.3;

		GregorianCalendar fechaPruebas = new GregorianCalendar();
		fechaPruebas.setTime(asignatura.getFechaInicial().getTime());

		// Fijar las espectativas de los mockObject
		expect(prueba1.getFecha()).andReturn(fechaPruebas).times(3);
		expect(prueba1.getPeso()).andReturn(peso1).times(3);
		replay(prueba1);

		expect(prueba2.getFecha()).andReturn(fechaPruebas).times(2);
		expect(prueba2.getPeso()).andReturn(peso2).times(2);
		replay(prueba2);

		// Operacion en la que intervienen los objetos a simular
		asignatura.crearPrueba(prueba1);
		asignatura.crearPrueba(prueba2);
		double pesoPruebas = asignatura.getPesoPruebas(1);

		// Verificacion de comportamiento de los Mock Object
		verify(prueba1);
		verify(prueba2);

		assertEquals(0.7, pesoPruebas, 0.01);
	}

}
