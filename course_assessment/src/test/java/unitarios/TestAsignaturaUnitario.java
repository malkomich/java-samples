package unitarios;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import dominio.Asignatura;

/**
 * Test unitario de la clase Asignatura.
 * 
 * @author juangon
 *
 */
public class TestAsignaturaUnitario {

	private Asignatura asignatura;

	@Before
	public void setUp() {
		String nombre = "nombre";
		String descripcion = "descripcion";
		int maxima = 10;
		GregorianCalendar fechaIni = new GregorianCalendar();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.add(GregorianCalendar.YEAR, 1);
		asignatura = new Asignatura(nombre, descripcion, maxima, fechaIni,
				fechaFin);
	}

	// TESTS DEL CONSTRUCTOR

	/**
	 * Crear una asignatura con la el valor maximo de calificacion negativo.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calificacionMaximaNegativa() {
		new Asignatura("", "", -1, new GregorianCalendar(),
				new GregorianCalendar());
	}

	/**
	 * Crear una asignatura con valor maximo de calificacion igual a 0.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calificacionMaximaNula() {
		new Asignatura("", "", 0, new GregorianCalendar(),
				new GregorianCalendar());
	}

	/**
	 * Crear una asignatura con fecha de inicio posterior a la fecha final.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void errorFechas() {
		GregorianCalendar fechaIni = new GregorianCalendar();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaIni.add(GregorianCalendar.DAY_OF_MONTH, 1);
		new Asignatura("", "", 10, fechaIni, fechaFin);
	}

	// TESTS DE LA DEFINICION DE PERIODOS

	/**
	 * Comprobar que se crea un periodo inicial.
	 */
	@Test
	public void periodoInicial() {
		int numPeriodos = asignatura.getNumPeriodos();
		assertEquals(1, numPeriodos);
	}

	/**
	 * Asignar nuevos periodos a la asignatura. Se divide la asignatura en 2
	 * periodos de aproximadamente 6 meses.
	 */
	@Test
	public void definirPeriodo() {
		GregorianCalendar fechaIni = asignatura.getFechaInicial();
		GregorianCalendar fechaFin = asignatura.getFechaFinal();
		GregorianCalendar fechaIntermedia = new GregorianCalendar();
		fechaIntermedia.setTime(fechaIni.getTime());
		fechaIntermedia.add(GregorianCalendar.MONTH, 6);

		asignatura.definirPeriodo(fechaIni, fechaIntermedia);
		asignatura.definirPeriodo(fechaIntermedia, fechaFin);
		
		assertEquals(0.5, asignatura.getPesoPeriodo(1), 0.05);
		assertEquals(0.5, asignatura.getPesoPeriodo(2), 0.05);
	}

	/**
	 * Definir los mismos periodos, pero desordenados.
	 */
	@Test
	public void periodosDesordenados() {
		GregorianCalendar fechaIni = asignatura.getFechaInicial();
		GregorianCalendar fechaFin = asignatura.getFechaFinal();
		GregorianCalendar fechaIntermedia = new GregorianCalendar();
		fechaIntermedia.setTime(fechaIni.getTime());
		fechaIntermedia.add(GregorianCalendar.MONTH, 6);

		asignatura.definirPeriodo(fechaIntermedia, fechaFin);
		asignatura.definirPeriodo(fechaIni, fechaIntermedia);

		assertEquals(0.5, asignatura.getPesoPeriodo(1), 0.05);
		assertEquals(0.5, asignatura.getPesoPeriodo(2), 0.05);
	}
	
	/**
	 * Definir periodos desordenados, pero solapando las fechas de periodos anteriores.
	 */
	@Test(expected=RuntimeException.class)
	public void solapamientoDesordenado() {
		GregorianCalendar fechaIni1 = asignatura.getFechaInicial();
		GregorianCalendar fechaFin1 = new GregorianCalendar();
		fechaFin1.setTime(fechaIni1.getTime());
		fechaFin1.add(GregorianCalendar.MONTH, 6);
		
		GregorianCalendar fechaIni2 = new GregorianCalendar();
		fechaIni2.setTime(fechaFin1.getTime());
		fechaIni2.add(GregorianCalendar.MONTH, -1);
		GregorianCalendar fechaFin2 = asignatura.getFechaFinal();
		

		asignatura.definirPeriodo(fechaIni2, fechaFin2);
		asignatura.definirPeriodo(fechaIni1, fechaFin1);
	}

	/**
	 * Crear un periodo con la fecha de inicio posterior a la fecha final.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void inicioPeriodoTarde() {
		GregorianCalendar fechaIni = asignatura.getFechaFinal();
		GregorianCalendar fechaFin = asignatura.getFechaInicial();
		asignatura.definirPeriodo(fechaIni, fechaFin);
	}

	/**
	 * Test de particion. Crear un periodo con la fecha de inicio anterior a la
	 * fecha inicial de la asignatura.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void periodoAnterior() {
		GregorianCalendar fechaAnterior = new GregorianCalendar();
		fechaAnterior.setTime(asignatura.getFechaInicial().getTime());
		fechaAnterior.add(GregorianCalendar.DAY_OF_MONTH, -1);
		GregorianCalendar fechaFinal = asignatura.getFechaFinal();
		asignatura.definirPeriodo(fechaAnterior, fechaFinal);
	}

	/**
	 * Test de particion. Crear un periodo con fecha final posterior a la fecha
	 * final de la asignatura.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void periodoPosterior() {
		GregorianCalendar fechaInicial = asignatura.getFechaInicial();
		GregorianCalendar fechaPosterior = new GregorianCalendar();
		fechaPosterior.setTime(asignatura.getFechaFinal().getTime());
		fechaPosterior.add(GregorianCalendar.DAY_OF_MONTH, 1);
		asignatura.definirPeriodo(fechaInicial, fechaPosterior);
	}

	/**
	 * Crear un periodo con fechas que se solapen con otro periodo ya existente.
	 */
	@Test(expected = RuntimeException.class)
	public void periodosSolapados() {
		GregorianCalendar fechaIni1 = asignatura.getFechaInicial();
		GregorianCalendar fechaFin1 = new GregorianCalendar();
		fechaFin1.setTime(fechaIni1.getTime());
		fechaFin1.add(GregorianCalendar.MONTH, 6);
		GregorianCalendar fechaIni2 = new GregorianCalendar();
		fechaIni2.setTime(fechaIni1.getTime());
		fechaIni2.add(GregorianCalendar.MONTH, 5);
		GregorianCalendar fechaFin2 = asignatura.getFechaFinal();

		asignatura.definirPeriodo(fechaIni1, fechaFin1);
		asignatura.definirPeriodo(fechaIni2, fechaFin2);
	}

	/**
	 * Crear un periodo ya existente.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void duplicarPeriodo() {
		GregorianCalendar fechaIni = asignatura.getFechaInicial();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.setTime(fechaIni.getTime());
		fechaFin.add(GregorianCalendar.MONTH, 6);

		asignatura.definirPeriodo(fechaIni, fechaFin);
		asignatura.definirPeriodo(fechaIni, fechaFin);
	}

	/**
	 * Crear un periodo habiendo un periodo con la misma fecha final y una fecha
	 * inicial mas temprana a la indicada.
	 */
	@Test
	public void periodoFechaIniPosterior() {
		GregorianCalendar fechaIni = asignatura.getFechaInicial();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.setTime(fechaIni.getTime());
		fechaFin.add(GregorianCalendar.MONTH, 6);
		asignatura.definirPeriodo(fechaIni, fechaFin);

		GregorianCalendar fechaIni2 = new GregorianCalendar();
		fechaIni2.setTime(fechaIni.getTime());
		fechaIni2.add(GregorianCalendar.MONTH, 1);
		asignatura.definirPeriodo(fechaIni2, fechaFin);

		assertEquals(fechaIni2, asignatura.getPeriodos().get(0));
	}

	/**
	 * Crear un periodo que este dentro de otro periodo existente.
	 */
	@Test
	public void periodoInterno() {
		GregorianCalendar fechaIni = asignatura.getFechaInicial();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.setTime(fechaIni.getTime());
		fechaFin.add(GregorianCalendar.MONTH, 6);
		asignatura.definirPeriodo(fechaIni, fechaFin);

		GregorianCalendar fechaIni2 = new GregorianCalendar();
		fechaIni2.setTime(fechaIni.getTime());
		fechaIni2.add(GregorianCalendar.MONTH, 1);
		GregorianCalendar fechaFin2 = new GregorianCalendar();
		fechaFin2.setTime(fechaFin.getTime());
		fechaFin2.add(GregorianCalendar.MONTH, -1);
		asignatura.definirPeriodo(fechaIni2, fechaFin2);

		 assertEquals(3, asignatura.getNumPeriodos());
	}
	
	//TESTS DE OBTENCION DE PESOS
	
	/**
	 * Obtener el total del peso de todos los periodos.
	 */
	@Test
	public void pesoTotal() {
		GregorianCalendar fechaIni = asignatura.getFechaInicial();
		GregorianCalendar fechaMedia = new GregorianCalendar();
		fechaMedia.add(GregorianCalendar.MONTH, 6);
		GregorianCalendar fechaFin = asignatura.getFechaFinal();
		asignatura.definirPeriodo(fechaIni, fechaMedia);
		asignatura.definirPeriodo(fechaMedia, fechaFin);

		int numPeriodos = asignatura.getNumPeriodos();
		double peso = 0;
		for (int i = 1; i <= numPeriodos; i++)
			peso += asignatura.getPesoPeriodo(i);
		assertEquals(1, peso, 0.001);
	}
	
	/**
	 * Obtener el peso de un numero de periodo menor al total de periodos existentes.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void pesoPeriodoInvalido() {
		asignatura.getPesoPeriodo(2);
	}
}
