package ejecutable;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import dominio.Asignatura;
import dominio.Prueba;

public class Launcher {

	private final static String DNI = "71173624D";
	
	public static void main(String[] args) {
		// Creacion de la asignatura
		GregorianCalendar fechaIni = new GregorianCalendar(2014, 8, 22);
		GregorianCalendar fechaFin = new GregorianCalendar(2015, 1, 2);
		Asignatura asignatura = new Asignatura("TDS",
				"Una asignatura interesante", 10, fechaIni, fechaFin);

		// Deficion de los periodos
		GregorianCalendar inicioNavidad = new GregorianCalendar(2014, 11, 19);
		GregorianCalendar finNavidad = new GregorianCalendar(2015, 0, 7);
		asignatura.definirPeriodo(fechaIni, inicioNavidad);
		asignatura.definirPeriodo(finNavidad, fechaFin);

		// Creacion de Pruebas
		GregorianCalendar fechaPrueba1 = new GregorianCalendar(2014, 9, 20);
		Prueba prueba1 = new Prueba(fechaPrueba1, "Parcial 1", "Examen sobre la primera mitad del temario", 5, 0.6, 0);
		GregorianCalendar fechaPrueba2 = new GregorianCalendar(2014, 11, 10);
		Prueba prueba2 = new Prueba(fechaPrueba2, "Parcial 2", "Examen sobre la segunda mitad del temario", 5, 0.4, 0);
		GregorianCalendar fechaPrueba3 = new GregorianCalendar(2015, 1, 2);
		Prueba prueba3 = new Prueba(fechaPrueba3, "Final", "Examen final de la asignatura", 10, 1.0, 0);
		
		asignatura.crearPrueba(prueba1);
		asignatura.crearPrueba(prueba2);
		asignatura.crearPrueba(prueba3);

		// Calificacion
		prueba1.calificar(DNI, 5);
		prueba2.calificar(DNI, 5);
		prueba3.calificar(DNI, 10);
		
		// Impresion(prueba)
		SimpleDateFormat sdf = new SimpleDateFormat( "dd / MMMM / yyyy" );
		
		List<GregorianCalendar> periodos = asignatura.getPeriodos();
		System.out.println("Fecha de los períodos:");
		for(GregorianCalendar fecha:periodos)
			System.out.println(sdf.format(fecha.getTime()));
		
		List<Prueba> pruebas = asignatura.getPruebas();
		System.out.println("\nFechas de las pruebas:");
		for(Prueba prueba:pruebas)
			System.out.println(sdf.format(prueba.getFecha().getTime()));
		
		System.out.println("\nCalificacion de \"Juan Carlos\", con DNI " + DNI + ":");
		System.out.println(asignatura.getCalificaciones().get(DNI));
	}

}
