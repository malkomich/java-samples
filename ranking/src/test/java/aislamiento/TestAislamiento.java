package aislamiento;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.Mock;
import org.junit.BeforeClass;
import org.junit.Test;

import tds.rankings.Concurso;
import tds.rankings.Ranking;

/** Test de aislamiento con Mock Object
 * 
 * @author Oscar Fernandez Nuñez
 * @author Juan Carlos Gonzalez Cabrero
 *
 */
public class TestAislamiento {

	private static final int LIMITE = 20;
	@Mock
	Ranking<Object> mockRanking;
	private static Object[] productos;
	
	@BeforeClass
	public static void setUpClass() {
		productos = new Object[LIMITE];
		for(int i=0;i<LIMITE;i++)
			productos[i] = new  Object();
	}
	
	@Test
	public void test() {
		GregorianCalendar fechaIni = new GregorianCalendar();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.add(GregorianCalendar.DAY_OF_MONTH, 1);
		Concurso<Object> concurso = new Concurso<Object>(LIMITE, fechaIni, fechaFin) {
			//Sobreescribimos la operacion de crear un ranking
			public Ranking<Object> crearRanking(List<Object> productos) {
				mockRanking = createMock(Ranking.class);
				
				//Fijar las espectativas del mockObject
				expect(mockRanking.getProductos()).andReturn(null);
				expectLastCall().times(1);
				replay(mockRanking);//Mock Object listo.
				
				return mockRanking;
			}
		};
		
		concurso.nominar(productos);
		concurso.votar(productos[0]);
		concurso.votar(productos[1]);
		concurso.votar(productos[2]);
		concurso.setFechaFin(new GregorianCalendar());
		concurso.cerrarConcurso();
		
		//Operacion en la que interviene el objeto a simular.
		concurso.getProductosRanking(2);
		
		//Verificacion de comportamiento del Mock Object
		verify(mockRanking);
	}
	
	/**
	 * Prueba el metodo crearRanking, creado para refactorizar la
	 * instanciacion de Rankings, facilitando el uso de Mock Objects.
	 */
	@Test
	public void ranking() {
		mockRanking = createMock(Ranking.class);
		expect(mockRanking.size()).andReturn(LIMITE);
		replay(mockRanking);
		GregorianCalendar fechaIni = new GregorianCalendar();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.add(GregorianCalendar.DAY_OF_MONTH, 1);
		Concurso<Object> concurso = new Concurso<Object>(LIMITE, fechaIni, fechaFin) {
			//Sobreescribimos la operacion de crear un ranking
			public Ranking<Object> crearRanking(List<Object> productos) {
				mockRanking = createMock(Ranking.class);
				
				//Fijar las espectativas del mockObject
				expect(mockRanking.size()).andReturn(LIMITE);
				replay(mockRanking);//Mock Object listo.
				
				return mockRanking;
			}
		};
		concurso.nominar(productos);
		mockRanking = concurso.crearRanking(Arrays.asList(productos));
		
		assertEquals(LIMITE, mockRanking.size());
		verify(mockRanking);
	}
	
}
