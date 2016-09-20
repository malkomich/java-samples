package dominio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Asignatura {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private String nombre;
	private String descripcion;
	private double calificacionMaxima;
	private GregorianCalendar fechaIni;
	private GregorianCalendar fechaFin;

	private List<GregorianCalendar> periodos;
	private List<Prueba> pruebas;
	private long totalTiempoPeriodos;

	/**
	 * @param nombre
	 *            Nombre identificativo.
	 * @param descripcion
	 *            Breve descripcion de la Asignatura.
	 * @param maxima
	 *            Calificacion maxima a obtener.
	 * @param fechaIni
	 *            Fecha inicial.
	 * @param fechaFin
	 *            Fecha final.
	 */
	public Asignatura(String nombre, String descripcion, double maxima,
			GregorianCalendar fechaIni, GregorianCalendar fechaFin) {
		if (fechaIni.after(fechaFin))
			throw new IllegalArgumentException(
					"La fecha inicial es posterior a la fecha final.");
		if (maxima <= 0)
			throw new IllegalArgumentException(
					"La calificacion maxima debe ser mayor a 0.");
		this.nombre = nombre;
		this.descripcion = descripcion;
		calificacionMaxima = maxima;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;

		totalTiempoPeriodos = 0;
		periodos = new ArrayList<GregorianCalendar>() {
			// Modifico el metodo add para que ademas de añadir el objeto fecha,
			// realice una ordenacion de la lista.
			@Override
			public boolean add(GregorianCalendar fecha) {
				super.add(fecha);
				Collections.sort(periodos, new Comparator<GregorianCalendar>() {
					@Override
					public int compare(GregorianCalendar o1,
							GregorianCalendar o2) {
						return o1.getTime().compareTo(o2.getTime());
					}
				});
				return true;
			}
		};
		pruebas = new ArrayList<Prueba>();
		definirPeriodo(fechaIni, fechaFin);
	}

	/**
	 * Define un nuevo período lectivo.
	 * 
	 * @param fechaIni
	 *            Fecha Inicial del Período.
	 * @param fechaFin
	 *            Fecha Final del Período.
	 */
	public void definirPeriodo(GregorianCalendar fechaIni,
			GregorianCalendar fechaFin) {
		if (fechaIni.after(fechaFin))
			throw new IllegalArgumentException(
					"La fecha inicial es posterior a la fecha final.");
		if (this.fechaIni.after(fechaIni) || fechaFin.after(this.fechaFin)) // CASO
																			// 8
			throw new IllegalArgumentException(
					"El período se encuentra fuera de las fechas de la asignatura.");

		// Comprobamos que no se solapen las fechas nuevas con fechas existentes
		int periodo = buscarPeriodo(fechaIni);
		if (periodo != 0) {
			GregorianCalendar inicioPeriodo = periodos.get(periodo * 2 - 2);
			GregorianCalendar finPeriodo = periodos.get(periodo * 2 - 1);
			if (fechaFin.after(finPeriodo) && fechaIni.before(finPeriodo)) // CASO
																			// 5
				throw new RuntimeException(
						"Solapamiento con periodo existente: "
								+ sdf.format(inicioPeriodo.getTime()) + " - "
								+ sdf.format(finPeriodo.getTime()));
			if (fechaIni.equals(inicioPeriodo)) {
				if (fechaFin.equals(finPeriodo)) // CASO 1
					throw new IllegalArgumentException("El Periodo ya existe.");
				else { // CASO 2
					periodos.remove(finPeriodo);
					periodos.add(fechaFin);
					totalTiempoPeriodos -= finPeriodo.getTimeInMillis()
							- fechaFin.getTimeInMillis();
				}
			}
			if (fechaIni.after(inicioPeriodo)) {
				if (fechaFin.equals(finPeriodo)) { // CASO 3
					periodos.remove(inicioPeriodo);
					periodos.add(fechaIni);
					totalTiempoPeriodos -= fechaIni.getTimeInMillis()
							- inicioPeriodo.getTimeInMillis();
				} else if (fechaFin.before(finPeriodo)) { // CASO 4
					addPeriodoCompleto(fechaIni, fechaIni);
					addPeriodoCompleto(fechaFin, fechaFin);
				} else { // CASO 6
					addPeriodoCompleto(fechaIni, fechaFin);
					actualizarTiempoTotal(fechaIni, fechaFin);
				}
			}
		} else {
			periodo = buscarPeriodo(fechaFin);
			if (periodo != 0) {
				GregorianCalendar inicioPeriodo = periodos.get(periodo * 2 - 2);
				GregorianCalendar finPeriodo = periodos.get(periodo * 2 - 1);
				if (fechaFin.after(inicioPeriodo)) // CASO 7
					throw new RuntimeException(
							"Solapamiento con periodo existente: "
									+ sdf.format(inicioPeriodo.getTime())
									+ " - " + sdf.format(finPeriodo.getTime()));
			}
			// CASO 6
			addPeriodoCompleto(fechaIni, fechaFin);
			actualizarTiempoTotal(fechaIni, fechaFin);
		}
	}

	/**
	 * @param fechaIni
	 * @param fechaFin
	 */
	private void addPeriodoCompleto(GregorianCalendar fechaIni,
			GregorianCalendar fechaFin) {
		periodos.add(fechaIni);
		periodos.add(fechaFin);
	}

	/**
	 * @param fechaIni
	 *            Fecha inicial de periodo
	 * @param fechaFin
	 *            Fecha final de periodo
	 */
	private void actualizarTiempoTotal(GregorianCalendar fechaIni,
			GregorianCalendar fechaFin) {
		totalTiempoPeriodos += fechaFin.getTimeInMillis()
				- fechaIni.getTimeInMillis();
	}

	/**
	 * Busca, en la de lista períodos de la asignatura, un período que contenga
	 * una determinada fecha, devolviendo su fecha inicial.
	 * 
	 * @param fecha
	 *            Fecha de la cual se busca el período.
	 * @return Fecha inicial del Período.
	 */
	private int buscarPeriodo(GregorianCalendar fecha) {
		int i = 0;
		while (i < periodos.size()) {
			if (!periodos.get(i).after(fecha)
					&& !periodos.get(i + 1).before(fecha))
				return (i + 2) / 2;
			i += 2;
		}
		return 0;
	}

	/**
	 * El peso corresponde al porcentaje de tiempo del período respecto al total
	 * de tiempo de todos los períodos.
	 * 
	 * @param numPeriodo
	 *            Numero de periodo, segun orden cronologico.
	 * @return Peso del período en la asignatura.
	 */
	public double getPesoPeriodo(int numPeriodo) {
		int indicePeriodo = numPeriodo * 2;
		if (periodos.size() < indicePeriodo)
			throw new IllegalArgumentException(
					"El número de períodos es menor a " + numPeriodo);
		double inicioPeriodo = periodos.get(indicePeriodo - 2)
				.getTimeInMillis();
		double finPeriodo = periodos.get(indicePeriodo - 1).getTimeInMillis();
		return ((finPeriodo - inicioPeriodo) / totalTiempoPeriodos);
	}

	/**
	 * Obtiene la coleccion de calificaciones de un determinado periodo.
	 * 
	 * @param numPeriodo
	 *            Numero de periodo, segun orden cronologico.
	 * @return Mapa de notas(value) asociadas a alumnos(key).
	 */
	public Map<String, Double> getCalificaciones(int numPeriodo) {
		Map<String, Double> calificaciones = new HashMap<String, Double>();
		GregorianCalendar inicioPeriodo = periodos.get(numPeriodo * 2 - 2);
		GregorianCalendar finPeriodo = periodos.get(numPeriodo * 2 - 1);
		for (Prueba p : pruebas) {
			GregorianCalendar fechaPrueba = p.getFecha();
			if ((!(fechaPrueba.compareTo(inicioPeriodo) < 0))
					&& (!(fechaPrueba.compareTo(finPeriodo) > 0))) {
				double notaMaxima = p.getNotaMaxima();
				double peso = p.getPeso();
				for (Entry<String, Double> calificacion : p.getCalificaciones()
						.entrySet()) {
					String alumno = calificacion.getKey();
					double nota = getNota(calificaciones, alumno);
					nota += calificacion.getValue() * peso * calificacionMaxima
							/ notaMaxima;
					calificaciones.put(alumno, nota);
				}
			}
		}
		return calificaciones;
	}

	/**
	 * @param calificaciones
	 *            Coleccion de calificaciones de los alumnos.
	 * @param alumno
	 *            Identificador del alumno.
	 * @return Nota del alumno almacenada en el Map.
	 */
	private double getNota(Map<String, Double> calificaciones, String alumno) {
		double nota = 0;
		if (calificaciones.containsKey(alumno)) {
			nota = calificaciones.get(alumno);
		}
		return nota;
	}

	/**
	 * Obtiene la coleccion de calificaciones globales, es decir, el total de
	 * las calificaciones de todos los periodos.
	 * 
	 * @return Mapa de notas(value) asociadas a alumnos(key).
	 */
	public Map<String, Double> getCalificaciones() {
		Map<String, Double> calificaciones = new HashMap<String, Double>();
		for (int i = 1; i <= getNumPeriodos(); i++) {
			Map<String, Double> notas_temp = getCalificaciones(i);
			double pesoPeriodo = getPesoPeriodo(i);
			for (Entry<String, Double> calificacion : notas_temp.entrySet()) {
				String alumno = calificacion.getKey();
				double nota = getNota(calificaciones, alumno);
				nota += calificacion.getValue() * pesoPeriodo;
				calificaciones.put(alumno, nota);
			}
		}
		return calificaciones;
	}

	/**
	 * @return Numero de periodos existentes en la asignatura.
	 */
	public int getNumPeriodos() {
		return periodos.size() / 2;
	}

	/**
	 * Vincula una Prueba a la asignatura.
	 * 
	 * @param prueba
	 *            Prueba definida.
	 */
	public void crearPrueba(Prueba prueba) {
		int periodo = buscarPeriodo(prueba.getFecha());
		if (periodo == 0)
			throw new RuntimeException(
					"La prueba no se encuentra en ningun período.");
		if (getPesoPruebas(periodo) + prueba.getPeso() <= 1.0) {
			pruebas.add(prueba);
		} else
			throw new RuntimeException("Superado el peso maximo del período.");
	}

	/**
	 * Obtiene el total de los pesos de las pruebas correspondientes a un
	 * período dado.
	 * 
	 * @param numPeriodo
	 *            Numero de periodo, segun orden cronologico.
	 * @return Indica el porcentaje de la nota que representan las pruebas del
	 *         periodo sobre dicho periodo.
	 */
	public double getPesoPruebas(int numPeriodo) {
		double peso = 0;
		GregorianCalendar inicioPeriodo = periodos.get(numPeriodo * 2 - 2);
		GregorianCalendar finPeriodo = periodos.get(numPeriodo * 2 - 1);
		for (Prueba p : pruebas) {
			GregorianCalendar fechaPrueba = p.getFecha();
			if (!(fechaPrueba.compareTo(inicioPeriodo) < 0)
					&& !(fechaPrueba.compareTo(finPeriodo) > 0))
				peso += p.getPeso();
		}
		return peso;
	}

	/**
	 * @return Fecha inicial de la asignatura.
	 */
	public GregorianCalendar getFechaInicial() {
		return fechaIni;
	}

	/**
	 * @return Fecha final de la asignatura.
	 */
	public GregorianCalendar getFechaFinal() {
		return fechaFin;
	}

	/**
	 * @return Lista de fechas de los periodos de la asignatura.
	 */
	public List<GregorianCalendar> getPeriodos() {
		return periodos;
	}

	/**
	 * @return Listado de Pruebas vinculadas a la asignatura.
	 */
	public List<Prueba> getPruebas() {
		return pruebas;
	}

}
