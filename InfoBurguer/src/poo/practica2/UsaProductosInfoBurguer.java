package poo.practica2;

import java.util.ArrayList;

/**
 * 
 * <b>Clase que crea objetos de las clases: Hamburguesa,Bebida,Complemento y Combo.</b>
 *
 */
public class UsaProductosInfoBurguer {
	
	/**
	 * Atributos de la propia clase.
	 */
	private static  ArrayList<Producto> carta;
 	
	/**
	 * @param no parametros
	 */
	public UsaProductosInfoBurguer(){
		carta = new ArrayList<Producto>();
	}
	
	/**
	 * @return carta
	 */
	public ArrayList<Producto> getCarta(){
		return carta;
	}
	
	/** Devuelve una cadena con la lista de productos de la carta. 
	 * @return
	 */
	public String imprimirCarta() {
		ArrayList<Producto> carta = getCarta();
		
		StringBuilder st = new StringBuilder();
		for(Producto p:carta) {
			st.append(p.toString());
		}
		return st.toString();
	}
	
	/**
	 * Metodo que crea objetos de las clases mencionadas y los ñ�ade en la carta (carta.add), solo 
	 * añadimos a la carta objetos que no estan repetidos. 
	 * @return combo
	 */
	public static Combo anadirCombo1(){
		Combo combo = new Combo("COMBO1", "2BigMac + 2Agua + Patatas Deluxe");
		
		//HAMBURGUESAS
		Hamburguesa h = new Hamburguesa("BigMac","",5.95,195,"Ternera,Queso,Tomate,Bacon");
		combo.anadirProductoSimple(h,carta);
		combo.anadirProductoSimple(h,carta);
		
		//BEBIDAS
		Bebida b = new Bebida("Agua", "", 1.00, 20.5,false,false);
		combo.anadirProductoSimple(b,carta);
		combo.anadirProductoSimple(b,carta);
		
		//COMPLEMENTOS
		Complemento c =  new Complemento("Patatas Deluxe","",3.00,80.5, 1);
		combo.anadirProductoSimple(c,carta);
		
		return combo;
	}
	
	/**
	 * Metodo que crea objetos de las clases mencionadas y los añade en la carta (carta.add), solo 
	 * añadimos a la carta objetos que no estan repetidos.
	 * NOTA: Creamos dos metodos para crear dos combos diferentes.
	 * @return combo
	 */
	public static Combo anadirCombo2(){
		Combo combo = new Combo("COMBO2", "2McPollo + Nestea + Ensalada");
		
		//HAMBURGUESAS
		Hamburguesa h = new Hamburguesa("McPollo","",6.75,231,"Pollo,Queso,Tomate,Bacon");
		combo.anadirProductoSimple(h,carta);
		combo.anadirProductoSimple(h,carta);
		
		//BEBIDAS
		Bebida b = new Bebida("Nestea", "", 1.45, 43.6,true,false);
		combo.anadirProductoSimple(b,carta);
		
		//COMPLEMENTOS
		Complemento c = new Complemento("Ensalada","",3.95,105.05, 2);
		combo.anadirProductoSimple(c,carta);
		return combo;
	}
	
	/**
	 * Metodo que recibe los combos creados y los muestra por pantalla, ademas de los combos muestra 
	 * por pantalla la carta de productos con todas sus caracteristicas.
	 * @param combo1
	 * @param combo2
	 */
	public void mostrarMenu(Combo combo1,Combo combo2){
		System.out.println("============"+"\n"+"| COMBOS  |"+"\n"+"============");
		System.out.println(combo1+"\n"+combo2);
		
		System.out.println("==============="+"\n"+"| PRODUCTOS  |"+"\n"+"===============");
		System.out.println(imprimirCarta());
	}
	
	/**
	 * Metodo principal donde generamos el Menu por pantalla para poder ver nuestras ofertas y productos.
	 * @param args
	 */
	public static void main(String [] args){
		UsaProductosInfoBurguer carta = new UsaProductosInfoBurguer();
		carta.mostrarMenu(anadirCombo1(),anadirCombo2());
		
	}
	
}
