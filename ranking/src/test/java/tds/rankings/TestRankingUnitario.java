package tds.rankings;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/** Tests de la clase Ranking
 * 
 * @author Oscar Fernandez Nuñez
 * @author Juan Carlos Gonzalez Cabrero
 *
 */
public class TestRankingUnitario {

	private static List<Object> listaProductos;
	private Ranking<Object> ranking;
	private static Object producto1, producto2;
	
	@BeforeClass
	public static void init(){
		listaProductos = new ArrayList<Object>();
		producto1 = new Object();
		producto2 = new Object();
		
		listaProductos.add(producto1);
		listaProductos.add(producto2);
	}
	
	@Before
	public void setUp(){
		ranking = new Ranking<Object>(listaProductos);
		//Ranking<Object> ranking = new Ranking<Object>((List<Object>) productos);
	}
	
	/**
	 * Consultar un producto de un ranking generado
	 */
	@Test
	public void consultaProducto(){
		Object productoBuscado = ranking.getProducto(1);
		assertEquals(producto1,productoBuscado);
	}
	
	/**
	 * Comprobar si un producto esta en el ranking
	 */
	@Test
	public void consultaExistencia(){
		assertTrue(ranking.isProducto(producto1));
	}
	
	/**
	 * Comparar rankings del mismo tipo
	 */
	@Test
	public void compararRankings(){
		List<Object> productos2 =  new ArrayList<Object>();
		productos2.add(producto2);
		productos2.add(producto1);
		Ranking<Object> ranking2 = new Ranking<Object>(productos2);
		List<Integer> comparacion = ranking.compareTo(ranking2);

		assertEquals(-1,comparacion.get(0).intValue());
		assertEquals(1,comparacion.get(1).intValue());
	}
	
	/**
	 * Obtener el producto en el limite inferior del rango
	 */
	@Test
	public void consultaPosicionLimiteInf() {
		assertEquals(1, ranking.getPosicion(producto1));
	}

	/**
	 * Obtener el producto en el limite superior del rango
	 */
	@Test
	public void consultaPosicionLimiteSup() {
		assertEquals(2, ranking.getPosicion(producto2));
	}

	/**
	 * Consultar un producto en la posicion 0.
	 */
	@Test(expected = Exception.class)
	public void consultaPosicion0() {
		ranking.getProducto(0);
	}

	/**
	 * Consultar un producto en la posicion limite.
	 */
	@Test
	public void consultaPosicionLimite() {
		ranking.getProducto(ranking.size() - 1);
	}

	/**
	 * Consultar un producto en una posicion por encima del limite.
	 */
	@Test(expected = Exception.class)
	public void consultaPosicionSobreLimite() {
		ranking.getProducto(ranking.size()+1);
	}

	/**
	 * Comparar 2 rankings con un producto fuera de uno de los rankings. El
	 * valor de comparacion de ese producto sera la diferencia del puesto que
	 * ocupa en el primer ranking con el numero de productos del ranking.
	 */
	@Test
	public void comparaRanking() {
		List<Object> listaProductos2 = new ArrayList<Object>();
		listaProductos2.add(new Object());
		listaProductos2.add(producto1);
		Ranking<Object> ranking2 = new Ranking<Object>(listaProductos2);
		List<Integer> comparacion = ranking.compareTo(ranking2);

		assertEquals(-1, comparacion.get(0).intValue());
		assertEquals(-1, comparacion.get(1).intValue());
	}
	
	/**
	 * Obtener un ranking vacio.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void rankingVacio(){
		new Ranking<Object>(new ArrayList<Object>());
	}
	
	/**
	 * Obtener producto en una posicion fuera del ranking.
	 */
	@Test(expected=Exception.class)
	public void obtenerProductoFueraRanking(){
		ranking.getProducto(1000);
	}
	
	/**
	 * Obtener posicion de un producto fuera del ranking.
	 */
	@Test(expected=Exception.class)
	public void obtenerPosicionFueraRanking(){
		ranking.getPosicion(new Object());
	}
	
	/**
	 * Comparar 2 rankings de distinto tamaño.
	 */
	@Test(expected=Exception.class)
	public void compararDistintoTam() {
		List<Object> listaProductos2 = new ArrayList<Object>();
		listaProductos2.add(new Object());
		Ranking<Object> ranking2 = new Ranking<Object>(listaProductos2);
		ranking.compareTo(ranking2);
	}

	/**
	 * Metodo toString utilizado para comprobaciones.
	 */
	@Test
	public void imprimirRanking() {
		assertNotNull(ranking.toString());
	}

}
