package tds.rankings;

import java.util.ArrayList;
import java.util.List;

/** Clase Ranking
 * 
 * @author Oscar Fernandez Nuñez
 * @author Juan Carlos Gonzalez Cabrero
 *
 * @param <E>
 */
public class Ranking<E> {
	
	protected List<E> productos;
	
	public Ranking(List<E> productos) {
		if(productos.size() == 0)
			throw new IllegalArgumentException("Lista de productos vacia.");
		this.productos = productos;
	}

	/** Obtiene el numero de productos del ranking.
	 * @return
	 */
	public int size() {
		return productos.size();
	}
	
	/** Obtiene el producto segun una posicion en el ranking dada.
	 * @param i
	 * @return
	 */
	public E getProducto(int i){
		return productos.get(i-1);
	}

	/** Busca la posicion de un producto en el ranking, por referencia.
	 * @param productoTop
	 * @return
	 */
	public int getPosicion(E producto) {
		for(E p : productos){
			if(producto.equals(p))
				return productos.indexOf(p) + 1;
		}
		throw new RuntimeException("El producto no se encuentra en el ranking");
	}

	/** Comprueba la existencia del producto en el ranking, por referencia.
	 * @param productoTop
	 * @return
	 */
	public boolean isProducto(E producto) {
		return productos.contains(producto);
	}

	/** Compara el ranking con otro, siendo este el Ranking de referencia.
	 * Por tanto, si en el ranking pasado por parametro, un producto sube de posicion,
	 * devolvera un numero positivo; y en caso contrario negativo.
	 * @param ranking2
	 * @return
	 */
	public List<Integer> compareTo(Ranking<E> ranking2) {
		int tam = productos.size();
		if(tam != ranking2.getProductos().size())
			throw new RuntimeException("El ranking debe ser del mismo tamaño");
		List<Integer> comparacion = new ArrayList<Integer>();
		for(int i=1 ; i<=tam ; i++) {
			if(ranking2.isProducto(getProducto(i))) {
				int index = ranking2.getPosicion(getProducto(i));
				comparacion.add(i-index);
			} else comparacion.add(i-tam-1);
		}
		return comparacion;
	}

	/** Devuelve la lista de productos del ranking.
	 * @return
	 */
	public List<E> getProductos() {
		return productos;
	}
	
	@Override
	public String toString() {
		StringBuilder st = new StringBuilder();
		st.append("RANKING:\n");
		for(E producto:productos)
			st.append(producto.toString()+"\n");
		return st.toString();
	}

}
