package dominio;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.javatuples.Pair;

public class Prueba {

	private final double PESO_MAXIMO = 1;

	private String nombre;
	private String descripcion;
	private GregorianCalendar fecha;
	private double notaMaxima;
	private double peso;
	private double maximoExtra;

	private Map<String, Double> notas;
	private boolean cerrada;
	private Map<String, List> bonificaciones;

	/**
	 * @param fecha
	 *            Fecha de realizacion de la Prueba.
	 * @param nombre
	 *            Nombre identificativo.
	 * @param descripcion
	 *            Breve descripcion de la Prueba.
	 * @param maxima
	 *            Calificacion maxima que se puede obtener.
	 * @param peso
	 *            Peso de la Prueba sobre el período al que corresponde.
	 * @param maximoExtra
	 *            Maximo de bonificacion/penalizacion
	 */
	public Prueba(GregorianCalendar fecha, String nombre, String descripcion,
			double maxima, double peso, double maximoExtra) {
		if (maxima <= 0)
			throw new IllegalArgumentException(
					"La calificacion máxima debe ser mayor a 0.");
		if (peso <= 0 || peso > PESO_MAXIMO)
			throw new IllegalArgumentException(
					"La peso de la prueba debe estar entre 0 y " + PESO_MAXIMO);
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		notaMaxima = maxima;
		this.peso = peso;
		this.maximoExtra = maximoExtra;

		notas = new HashMap<String, Double>();
		cerrada = false;

		bonificaciones = new HashMap<String, List>();
	}

	/**
	 * Calificar un grupo de alumnos.
	 * 
	 * @param listado
	 *            Mapa de notas(value) asociadas a alumnos(key).
	 */
	public void calificar(HashMap<String, Double> listado) {
		for (Entry<String, Double> calificacion : listado.entrySet())
			calificar(calificacion.getKey(), calificacion.getValue());
	}

	/**
	 * Calificar a un alumno concreto.
	 * 
	 * @param alumno
	 *            Identificador unico del alumno(DNI).
	 * @param nota
	 *            Calificacion del alumno en la prueba.
	 */
	public void calificar(String alumno, double nota) {
		if (cerrada || fecha.after(new GregorianCalendar()))
			throw new RuntimeException(
					"No se permite la calificacion actualmente.");
		comprobarNota(nota);
		notas.put(alumno, nota);
	}

	/**
	 * Obtiene una coleccion de calificaciones de todos los alumnos evaluados.
	 * 
	 * @return Mapa de notas(value) asociadas a alumnos(key).
	 */
	public Map<String, Double> getCalificaciones() {
		return notas;
	}

	/**
	 * Cierra el plazo de calificacion. A partir de que este cerrado solo se
	 * podrán modificar las notas que haya, no añadir notas nuevas.
	 */
	public void cerrarCalificacion() {
		if (fecha.after(new GregorianCalendar()))
			throw new RuntimeException("Aun no se ha celebrado la Prueba");
		cerrada = true;
	}

	/**
	 * Modificar la nota de un alumno concreto una vez cerradas las
	 * calificaciones.
	 * 
	 * @param alumno
	 *            Identificador unico del alumno(DNI).
	 * @param nota
	 *            Calificacion del alumno en la prueba.
	 */
	public void modificarCalificacion(String alumno, double nota) {
		comprobarNota(nota);
		if (notas.containsKey(alumno))
			notas.put(alumno, nota);
		else
			throw new IllegalArgumentException(
					"El alumno no tiene calificacion.");
	}

	/**
	 * @param nota
	 *            Valor de nota a comprobar
	 */
	private void comprobarNota(double nota) {
		if (nota < 0 || nota > notaMaxima)
			throw new IllegalArgumentException("La nota debe estar entre 0 y "
					+ notaMaxima);
	}

	/**
	 * Obtiene la calificacion obtenida en la prueba de un alumno concreto.
	 * 
	 * @param alumno
	 *            Identificador unico del alumno(DNI).
	 * @return Calificacion del alumno en la prueba.
	 */
	public double getCalificacion(String alumno) {
		return notas.get(alumno);
	}

	/**
	 * @param newFecha
	 *            Nueva fecha de realizacion de la prueba.
	 */
	public void setFecha(GregorianCalendar newFecha) {
		fecha = newFecha;
	}

	/**
	 * @return Fecha de realizacion de la prueba.
	 */
	public GregorianCalendar getFecha() {
		return fecha;
	}

	/**
	 * @return Peso de la prueba sobre el periodo al que corresponde.
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * @return Nota maxima que se puede obtener en la prueba.
	 */
	public double getNotaMaxima() {
		return notaMaxima;
	}

	/**
	 * Agrega una bonificacion que subira la nota al alumno seleccionado.
	 * 
	 * @param alumno
	 *            Identificador del alumno(DNI).
	 * @param valorNota
	 *            Puntos de bonificacion sobre la nota.
	 * @param motivo
	 *            Descripcion.
	 */
	public void bonificar(String alumno, double valorNota, String motivo) {
		if (valorNota > maximoExtra)
			throw new IllegalArgumentException(
					"La bonificacion excede el limite.");
		List<Pair> listaBonificaciones;
		Pair<String, Double> bonificacion = new Pair<String, Double>(motivo, valorNota);
		addExtra(alumno, bonificacion);
		double nuevaNota = getCalificacion(alumno) + valorNota;
		if(nuevaNota > notaMaxima)
			modificarCalificacion(alumno, notaMaxima);
		else
			modificarCalificacion(alumno, nuevaNota);
	}

	/**
	 * Agrega una penalizacion que bajara la nota al alumno seleccionado.
	 * 
	 * @param alumno
	 *            Identificador del alumno(DNI).
	 * @param valorNota
	 *            Puntos de penalizacion sobre la nota
	 * @param motivo
	 *            Descripcion.
	 */
	public void penalizar(String alumno, double valorNota, String motivo) {
		if (valorNota > maximoExtra)
			throw new IllegalArgumentException(
					"La penalizacion excede el limite.");
		List<Pair> listaBonificaciones;
		Pair<String, Double> bonificacion = new Pair<String, Double>(motivo, -valorNota);
		addExtra(alumno, bonificacion);
		double nuevaNota = getCalificacion(alumno) - valorNota;
		if(nuevaNota < 0)
			modificarCalificacion(alumno, 0);
		else
			modificarCalificacion(alumno, nuevaNota);
	}

	/**
	 * @param alumno Identificador del alumno(DNI).
	 * @param extra Valor de la bonificacion o penalizacion.
	 */
	private void addExtra(String alumno, Pair<String, Double> extra) {
		List<Pair> listaBonificaciones;
		if(bonificaciones.containsKey(alumno)) {
			listaBonificaciones = bonificaciones.get(alumno);
		} else {
			listaBonificaciones = new ArrayList<Pair>();
			bonificaciones.put(alumno, listaBonificaciones);
		}
		listaBonificaciones.add(extra);
	}

}
