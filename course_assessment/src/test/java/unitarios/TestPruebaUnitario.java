package unitarios;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import dominio.Prueba;

/**
 * Test unitario de la clase Prueba.
 * 
 * @author juangon
 *
 */
public class TestPruebaUnitario {

	private Prueba prueba;
	private final String DNI_ALUMNO = "71647382B";
	private final double NOTA_ALEATORIA = 5.10;
	private final double MAXIMO_EXTRA = 3.0;
	private final double NOTA_MAXIMA = 10.0;

	@Before
	public void setUp() {
		GregorianCalendar fecha = new GregorianCalendar();
		String nombre = "nombre";
		String descripcion = "descripcion";
		double peso = 0.5;
		prueba = new Prueba(fecha, nombre, descripcion, NOTA_MAXIMA, peso,
				MAXIMO_EXTRA);
	}

	// TESTS DEL CONSTRUCTOR

	/**
	 * Crear prueba con calificacion maxima igual a 0.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calificacionNula() {
		new Prueba(new GregorianCalendar(), "", "", 0, 1, 0);
	}

	/**
	 * Crear prueba con peso igual a 0.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void pesoNulo() {
		new Prueba(new GregorianCalendar(), "", "", 10, 0, 0);
	}

	/**
	 * Crear prueba con peso mayor que 1.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void pesoMayor() {
		new Prueba(new GregorianCalendar(), "", "", 10, 1.1, 0);
	}

	// TESTS DE CALIFICACION

	/**
	 * Calificar un unico alumno.
	 */
	@Test
	public void calificarUno() {
		prueba.calificar(DNI_ALUMNO, NOTA_ALEATORIA);
		assertEquals(NOTA_ALEATORIA, prueba.getCalificacion(DNI_ALUMNO), 0.1);
	}

	/**
	 * Calificar varios alumnos a la vez.
	 */
	@Test
	public void calificarVarios() {
		HashMap<String, Double> listado = new HashMap<String, Double>();
		String dni2 = "71647389D";
		double nota2 = 7.09;
		listado.put(DNI_ALUMNO, NOTA_ALEATORIA);
		listado.put(dni2, nota2);
		prueba.calificar(listado);
		assertEquals(2, prueba.getCalificaciones().size());
	}

	/**
	 * Test de Particion. Calificar con una nota sobre el limite de la prueba.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calificacionSobreMaximo() {
		double nota = 10.01;
		prueba.calificar(DNI_ALUMNO, nota);
	}

	/**
	 * Test de Particion. Calificar con una nota por debajo de 0.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calificacionNegativa() {
		double nota = -0.01;
		prueba.calificar(DNI_ALUMNO, nota);
	}

	/**
	 * Calificar tras haber completado la calificacion de la prueba(tras
	 * cerrar).
	 */
	@Test(expected = RuntimeException.class)
	public void calificarTrasCerrar() {
		prueba.cerrarCalificacion();
		prueba.calificar(DNI_ALUMNO, NOTA_ALEATORIA);
	}

	/**
	 * Cerrar el plazo de calificar antes de la fecha de realizacion.
	 */
	@Test(expected = RuntimeException.class)
	public void cerrarAntesDeRealizar() {
		GregorianCalendar newFecha = new GregorianCalendar();
		newFecha.add(GregorianCalendar.HOUR, 1);
		prueba.setFecha(newFecha);

		prueba.cerrarCalificacion();
	}

	/**
	 * Modificar la calificacion de un alumno.
	 */
	@Test
	public void modificarCalificacion() {
		prueba.calificar(DNI_ALUMNO, NOTA_ALEATORIA);
		prueba.modificarCalificacion(DNI_ALUMNO, NOTA_ALEATORIA / 2);
		assertEquals(NOTA_ALEATORIA / 2, prueba.getCalificacion(DNI_ALUMNO),
				0.001);
	}

	/**
	 * Calificar antes de la fecha de realizacion.
	 */
	@Test(expected = RuntimeException.class)
	public void calificarAntesDeRealizar() {
		GregorianCalendar newFecha = new GregorianCalendar();
		newFecha.add(GregorianCalendar.HOUR, 1);
		prueba.setFecha(newFecha);

		prueba.calificar(DNI_ALUMNO, NOTA_ALEATORIA);
	}

	/**
	 * Test de Particion. Modificar calificacion con una nota sobre el limite de
	 * la prueba.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void modificarSobreMaximo() {
		prueba.calificar(DNI_ALUMNO, NOTA_ALEATORIA);
		double nota = 10.01;
		prueba.modificarCalificacion(DNI_ALUMNO, nota);
	}

	/**
	 * Test de Particion. Modificar calificacion con una nota por debajo de 0.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void modificarNotaNegativa() {
		prueba.calificar(DNI_ALUMNO, NOTA_ALEATORIA);
		double nota = -0.01;
		prueba.modificarCalificacion(DNI_ALUMNO, nota);
	}

	/**
	 * Modificar la calificacion de un alumno que no ha sido calificado
	 */
	@Test(expected = IllegalArgumentException.class)
	public void modificarNuevoAlumno() {
		prueba.modificarCalificacion(DNI_ALUMNO, NOTA_ALEATORIA);
	}

	// GETTERS

	/**
	 * Comprueba que la nota maxima es la indicada en el constructor.
	 */
	@Test
	public void notaMaxima() {
		assertEquals(10, prueba.getNotaMaxima(), 0.01);
	}

	/**
	 * Comprueba que peso es el indicado en el constructor.
	 */
	@Test
	public void peso() {
		assertEquals(0.5, prueba.getPeso(), 0.01);
	}

	/**
	 * Comprueba que la fecha de realizacion es la indicada en el constructor.
	 */
	@Test
	public void fecha() {
		assertEquals(new GregorianCalendar(), prueba.getFecha());
	}

	// BONIFICACION - PENALIZACION

	/**
	 * Poner penalizacion mayor al maximo de penalizacion.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void penalizarBajoLimite() {
		prueba.calificar(DNI_ALUMNO, NOTA_ALEATORIA);
		String motivoPenalizacion = "Mal comportamiento";
		prueba.penalizar(DNI_ALUMNO, MAXIMO_EXTRA + 0.1, motivoPenalizacion);
	}

	/**
	 * Poner bonificacion mayor al maximo de bonificacion.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void bonificarSobreLimite() {
		prueba.calificar(DNI_ALUMNO, NOTA_ALEATORIA);
		String motivoBonificacion = "Buen comportamiento";
		prueba.bonificar(DNI_ALUMNO, MAXIMO_EXTRA + 0.1, motivoBonificacion);
	}

	/**
	 * Poner penalizacion mayor a la nota. La calificacion no puede ser
	 * negativa, por tanto sera 0.
	 */
	@Test
	public void penalizarDemasiado() {
		double nota = MAXIMO_EXTRA - 1;
		prueba.calificar(DNI_ALUMNO, nota);
		String motivoPenalizacion = "Mal comportamiento";
		prueba.penalizar(DNI_ALUMNO, MAXIMO_EXTRA, motivoPenalizacion);
		assertEquals(0, prueba.getCalificacion(DNI_ALUMNO), 0.1);
	}

	/**
	 * Poner bonificacion que junto con la nota supere la nota maxima. La
	 * calificacion no puede exceder a la maxima, por tanto la nota sera la
	 * maxima.
	 */
	@Test
	public void bonificarDemasiado() {
		double nota = NOTA_MAXIMA - MAXIMO_EXTRA + 1;
		prueba.calificar(DNI_ALUMNO, nota);
		String motivoBonificacion = "Buen comportamiento";
		prueba.bonificar(DNI_ALUMNO, MAXIMO_EXTRA, motivoBonificacion);
		assertEquals(NOTA_MAXIMA, prueba.getCalificacion(DNI_ALUMNO), 0.1);
	}

	/**
	 * Poner bonificaciones y penalizaciones a un mismo alumno.
	 */
	@Test
	public void bonificarYpenalizar() {
		prueba.calificar(DNI_ALUMNO, NOTA_ALEATORIA);
		String motivoBonificacion = "Buen comportamiento";
		prueba.bonificar(DNI_ALUMNO, MAXIMO_EXTRA-1.0, motivoBonificacion);
		String motivoPenalizacion = "Mal comportamiento";
		prueba.penalizar(DNI_ALUMNO, MAXIMO_EXTRA, motivoPenalizacion);
		assertEquals(NOTA_ALEATORIA-1.0, prueba.getCalificacion(DNI_ALUMNO), 0.1);
	}
}
