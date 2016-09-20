package tds.rankings;

import java.util.GregorianCalendar;

public class Launcher {

	public Launcher() {
		Concurso<String> concurso = crearConcurso(3);
		System.out.println("Concurso Cerrado. Los productos nominados fueron: producto1, producto2 y producto3. Gracias por sus votaciones.");
		
		Ranking<String> ranking = concurso.getRanking(3);
		System.out.println("El producto ganador del Ranking es: " + ranking.getProducto(1));
		System.out.println("El producto 1 ha quedado en la posicion: " + ranking.getPosicion("producto1"));
		System.out.println("Enhorabuena al ganador.");
	}
	
	private Concurso<String> crearConcurso(int limite) {
		GregorianCalendar fechaIni = new GregorianCalendar();
		GregorianCalendar fechaFin = new GregorianCalendar();
		fechaFin.add(GregorianCalendar.DAY_OF_MONTH, 1);
		Concurso<String> concurso = new Concurso<String>(limite, fechaIni, fechaFin);
		
		String[] productos = new String[limite];
		for(int i=0;i<limite;i++)
			productos[i] = "producto"+(i+1);
		
		concurso.nominar(productos);
		concurso.votar(productos[1]);
		concurso.votar(productos[1]);
		concurso.votar(productos[2]);
		
		concurso.setFechaFin(new GregorianCalendar());
		concurso.cerrarConcurso();
		
		return concurso;
	}

	public static void main(String[] args) {
		new Launcher();
	}

}