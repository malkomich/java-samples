package tds.rankings;

import java.util.Comparator;
import java.util.Map;

/**
 * Clase que implementa el comparador de votos para ordenar 
 * los productos del concurso.
 * @author Oscar Fernandez Nuñez
 * @author Juan Carlos Gonzalez Cabrero
 *
 * @param <E>
 */
public class ComparadorVotos<E> implements Comparator<E> {

	Map<E, Integer> map;
	public ComparadorVotos(Map<E, Integer> map) {
		 this.map = map;
	}

	@Override
	public int compare(E o1, E o2) {
		if (map.get(o1) >= map.get(o2)) {
            return -1;
        } else {
            return 1;
        }
	}

}
