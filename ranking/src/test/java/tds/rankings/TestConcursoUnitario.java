package tds.rankings;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tds.rankings.Concurso;

/** Tests de la clase Concurso
 * 
 * @author Oscar Fernandez Nuñez
 * @author Juan Carlos Gonzalez Cabrero
 *
 */
public class TestConcursoUnitario {

	private final static int LIMITEPRODUCTOS = 20;
	private static Object[] productos;
	private Concurso<Object> concurso;
	
	@BeforeClass
	public static void init(){
		productos = new Object[LIMITEPRODUCTOS];
		for(int i=0;i<LIMITEPRODUCTOS;i++)
			productos[i] = new  Object();
	}
	
	@Before
	public void setUp(){
		GregorianCalendar fechaIni = new GregorianCalendar();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.add(GregorianCalendar.DAY_OF_MONTH, 1);
		concurso = new Concurso<Object>(LIMITEPRODUCTOS, fechaIni, fechaFin);
	}
	
	/**
	 * Nominar un producto
	 */
	@Test
	public void nominarUno(){
		Object producto = productos[1];
		concurso.nominar(producto);
		assertTrue(concurso.isNominated(producto));
	}
	
	/**
	 * Nominar varios productos
	 */
	@Test
	public void nominarVarios(){
		concurso.nominar(productos);
		for(Object o:productos)
			assertTrue(concurso.isNominated(o));
	}
	
	/**
	 * Nominar el numero limite de productos.
	 */
	@Test
	public void nominarLimite() {
		for (int i = 0; i < LIMITEPRODUCTOS; i++)
			concurso.nominar(productos[i]);
		assertEquals(LIMITEPRODUCTOS, concurso.size());
	}
	
	/**
	 * Nominar un numero de productos por debajo del limite.
	 */
	@Test
	public void nominarBajoLimite() {
		for (int i = 0; i < LIMITEPRODUCTOS - 1; i++)
			concurso.nominar(productos[i]);
		assertEquals(LIMITEPRODUCTOS - 1, concurso.size());
	}
	
	/**
	 * Nominar un numero de productos por encima del limite.
	 */
	@Test(expected = Exception.class)
	public void nominarSobreLimite() {
		for (int i = 0; i < LIMITEPRODUCTOS + 1; i++)
			concurso.nominar(productos[i]);
	}
	
	/**
	 * Votar un producto nominado
	 */
	@Test
	public void votar(){
		Object producto = productos[1];
		concurso.nominar(productos);
		concurso.votar(producto);
		assertEquals(1, concurso.getVotacion(producto));
	}
	
	/**
	 * Cerrar un concurso
	 */
	@Test
	public void cerrarConcurso(){
		GregorianCalendar fechaFin = new GregorianCalendar();
		concurso.setFechaFin(fechaFin);
		concurso.cerrarConcurso();
		assertTrue(concurso.isCerrado());
	}
	
	/**
	 * Generar un ranking Top10 de un concurso cerrado
	 */
	@Test
	public void generarTop10(){
		concurso.nominar(productos);
		concurso.votar(productos[2]);
		GregorianCalendar fechaFin = new GregorianCalendar();
		concurso.setFechaFin(fechaFin);
		concurso.cerrarConcurso();
		Ranking<Object> ranking = concurso.getRanking();
		assertEquals(10,ranking.size());
	}
	
	/**
	 * Generar un ranking de un concurso cerrado
	 */
	@Test
	public void generarRanking(){
		concurso.nominar(productos);
		concurso.votar(productos[2]);
		GregorianCalendar fechaFin = new GregorianCalendar();
		concurso.setFechaFin(fechaFin);
		concurso.cerrarConcurso();
		Ranking<Object> ranking = concurso.getRanking(5);
		assertEquals(5,ranking.size());
	}
	
	/**
	 * Obtener ranking de menos de un producto.
	 */
	@Test(expected = Exception.class)
	public void getEmptyRanking() {
		concurso.getRanking(0);
	}
	
	/**
	 * Obtener ranking del numero maximo de productos nominados.
	 */
	@Test
	public void getRankingLimite() {
		concurso.nominar(productos);
		concurso.setFechaFin(new GregorianCalendar());
		concurso.cerrarConcurso();
		Ranking<Object> ranking = concurso.getRanking(concurso.size());
		assertEquals(concurso.size(), ranking.size());
	}
	
	/**
	 * Obtener ranking de un producto mas del maximo de productos nominados.
	 */
	@Test(expected = Exception.class)
	public void getRankingSobreLimite(){
		concurso.getRanking(concurso.size() + 1);
	}
	
	/**
	 * Crear concurso con fecha fin posterior a la fecha inicial.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void fechasSolapadas() {
		GregorianCalendar fechaIni = new GregorianCalendar();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaIni.add(GregorianCalendar.DAY_OF_MONTH, 1);
		concurso = new Concurso<Object>(LIMITEPRODUCTOS, fechaIni, fechaFin);
	}
	
	/**
	 * Crear concurso con limite de nominaciones igual a 0.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void concursoVacio(){
		new Concurso<Object>(0, new GregorianCalendar(), new GregorianCalendar());
	}

	/**
	 * Crear concurso con limite de nominaciones negativo.
	 */
	@Test(expected=Exception.class)
	public void concursoLimiteNegativo(){
		new Concurso<Object>(-1, new GregorianCalendar(), new GregorianCalendar());
	}
	
	/**
	 * Nominar mas de una vez un producto para el mismo Concurso.
	 */
	@Test
	public void renominate() {
		Object object = new Object();
		concurso.nominar(object);
		concurso.nominar(object);
		assertEquals(1, concurso.productosNominados.size());
	}
	
	/**
	 * Votar un producto que no esta nominado para el Concurso. 
	 */
	@Test(expected=Exception.class)
	public void voteNotNominated(){
		Object producto = new Object();
		concurso.votar(producto);
	}
	
	/**
	 * Obtener el ranking con un numero de productos mayor a
	 * la cantidad existente.
	 */
	@Test(expected=Exception.class)
	public void rankingNoProducts(){
		concurso.setFechaFin(new GregorianCalendar());
		concurso.cerrarConcurso();
		concurso.getRanking(1);
	}
	
	/**
	 * Obtener ranking antes de cerrar la votacion.
	 */
	@Test(expected=Exception.class)
	public void rankingIfNotCLosed(){
		concurso.nominar(new Object());
		concurso.getRanking(1);
	}
	
	/**
	 * Votar tras cerrar la votacion.
	 */
	@Test(expected=Exception.class)
	public void voteWhenClosed(){
		Object producto = new Object();
		concurso.nominar(producto);
		GregorianCalendar fechaFin = new GregorianCalendar();
		concurso.setFechaFin(fechaFin);
		concurso.cerrarConcurso();
		concurso.votar(producto);
	}
	
	/**
	 * Nominar tras cerrar la votacion.
	 */
	@Test(expected=Exception.class)
	public void nominateWhenClosed(){
		Object producto = new Object();
		GregorianCalendar fechaFin = new GregorianCalendar();
		concurso.setFechaFin(fechaFin);
		concurso.cerrarConcurso();
		concurso.nominar(producto);
	}
	
	/**
	 * Nominar mas productos del limite de nominaciones.
	 */
	@Test(expected=Exception.class)
	public void nominateLimit(){
		int limit = concurso.getLimiteNominaciones();
		for(int i=0;i<=limit;i++)
			concurso.nominar(new Object());
	}
	
	/**
	 * Consultar en el ranking un producto que no esta.
	 */
	@Test(expected=Exception.class)
	public void consultarProductoNoIncluido(){
		Object object1 = new Object();
		Object object2 = new Object();
		concurso.nominar(object1);
		concurso.votar(object1);
		GregorianCalendar fechaFin = new GregorianCalendar();
		concurso.setFechaFin(fechaFin);
		concurso.cerrarConcurso();
		concurso.getRanking();
		concurso.getVotacion(object2);
	}
	/**
	 * Obtener un ranking vacio.
	 */
	@Test(expected=Exception.class)
	public void generarRankingVacio(){
		GregorianCalendar fechaFin = new GregorianCalendar();
		concurso.setFechaFin(fechaFin);
		concurso.cerrarConcurso();
		concurso.getRanking();
	}
	
	/**
	 * Votar productos antes de la fecha de inicio del concurso.
	 */
	@Test(expected = Exception.class)
	public void votarPronto() {
		GregorianCalendar fechaIni = new GregorianCalendar();
		fechaIni.add(GregorianCalendar.DAY_OF_MONTH, 1);
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.add(GregorianCalendar.DAY_OF_MONTH, 2);
		concurso = new Concurso<Object>(LIMITEPRODUCTOS, fechaIni, fechaFin);
		Object producto = new Object();
		concurso.nominar(producto);
		concurso.votar(producto);
	}
	
	/**
	 * Cerrar concurso antes de la fecha fin.
	 */
	@Test(expected = Exception.class)
	public void cerrarPronto() {
		concurso.cerrarConcurso();
	}

	/**
	 * Metodo toString utilizado para comprobaciones.
	 */
	@Test
	public void imprimirConcurso() {
		assertNotNull(concurso.toString());
	}

}
