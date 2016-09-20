package tds.rankings;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/** Clase Concurso
 * 
 * @author Oscar Fernandez Nuñez
 * @author Juan Carlos Gonzalez Cabrero
 *
 * @param <E>
 */
public class Concurso<E> {

	protected HashMap<E, Integer> productosNominados;
	protected int limiteNominaciones;
	protected GregorianCalendar fechaIni;
	protected GregorianCalendar fechaFin;
	protected boolean cerrado;

	public Concurso(int limiteNominaciones, GregorianCalendar fechaIni,
			GregorianCalendar fechaFin) {
		if(fechaIni.after(fechaFin))
			throw new IllegalArgumentException("Fechas solapadas");
		if(limiteNominaciones <= 0)
			throw new IllegalArgumentException("El limite de productos nominados debe ser mayor que 0");
		productosNominados = new HashMap<E, Integer>();
		this.limiteNominaciones = limiteNominaciones;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		cerrado = false;
	}

	/** Nomina un producto unitario.
	 * @param producto
	 */
	public void nominar(E producto) {
		E[] productos = (E[]) new Object[1];
		productos[0] = producto;
		nominar(productos);
	}

	/** Nomina un array de productos.
	 * @param productos
	 */
	public void nominar(E[] productos) {
		if (!cerrado
				&& productosNominados.size() + productos.length <= limiteNominaciones) {
			for (int i = 0; i < productos.length; i++) {
				if (!isNominated(productos[i])) {
					productosNominados.put(productos[i], 0);
				}
			}
		} else {
			throw new RuntimeException("El concurso esta cerrado o el numero de nominaciones excede el limite");
		}
	}

	/** Vota un producto nominado anteriormente, si el concurso se ha iniciado
	 * y no se encuentra cerrado.
	 * @param producto
	 */
	public void votar(E producto) {
		if (!cerrado && !fechaIni.after(new GregorianCalendar())) {
			int votos = productosNominados.get(producto) + 1;
			productosNominados.put(producto, votos);
		} else throw new RuntimeException("No se permite la votacion");
	}

	/**
	 * Cierra el proceso de votacion, habiendo pasado la fecha fin del concurso.
	 */
	public void cerrarConcurso() {
		if(fechaFin.after(new GregorianCalendar()))
			throw new RuntimeException("Aun no se puede cerrar el concurso");
		cerrado = true;
	}

	/** Obtiene la lista de productos del ranking de un numero dado de productos.
	 * @param numProductos
	 * @return
	 */
	public List<E> getProductosRanking(int numProductos) {
		Ranking<E> ranking = getRanking(numProductos);
		return ranking.getProductos();
	}
	
	/** Obtiene el ranking de un numero dado de productos.
	 * @param numProductos
	 * @return
	 */
	public Ranking<E> getRanking(int numProductos) {
		if(!cerrado)
			throw new RuntimeException("El concurso debe estar cerrado para generar Rankings");
		if(numProductos > productosNominados.size())
			throw new RuntimeException("No hay suficientes productos nominados para crear el ranking");
		ComparadorVotos<E> comparador = new ComparadorVotos<E>(productosNominados);
		TreeMap<E, Integer> productosOrdenados = new TreeMap<E, Integer>(comparador);
		productosOrdenados.putAll(productosNominados);
		
		List<E> lista = new ArrayList<E>(productosOrdenados.keySet()).subList(0, numProductos);
		Ranking<E> ranking = crearRanking(lista);
		return ranking;
	}


	/** Obtiene un ranking por defecto de los 10 productos mas votados.
	 * @param productos
	 * @return
	 */
	public Ranking<E> getRanking() {
		return getRanking(10);
	}

	/** Devuelve el numero maximo de nominaciones.
	 * @return
	 */
	public int getLimiteNominaciones() {
		return limiteNominaciones;
	}

	/** Comprueba si un determinado producto se encuentra nominado
	 * en el Concurso.
	 * @param producto
	 * @return
	 */
	public boolean isNominated(E producto) {
		return productosNominados.containsKey(producto);
	}

	/** Obtiene el numero de votos de un determinado producto.
	 * @param producto
	 * @return
	 */
	public int getVotacion(E producto) {
		return productosNominados.get(producto);
	}

	/** Comprueba el estado del concurso.
	 * @return
	 */
	public boolean isCerrado() {
		return cerrado;
	}

	/** Devuelve el numero de productos nominados actualmente.
	 * @return
	 */
	public int size() {
		return productosNominados.size();
	}

	/** Setter que nos permite controlar la fecha para cerrar el concurso
	 * y realizar todas las pruebas que necesitamos.
	 * @param fechaFin
	 */
	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	@Override
	public String toString() {
		return productosNominados.toString();
	}
	
	/** Refactorizacion de la instanciacion de Ranking, para
	 * la realizacion de pruebas de aislamiento.
	 * @param productos
	 * @return
	 */
	public Ranking<E> crearRanking(List<E> productos) {
		return new Ranking<E>(productos);
	}

}
