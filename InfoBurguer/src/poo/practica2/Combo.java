package poo.practica2;

import java.util.ArrayList;

/**
 * 
 * <b>Clase que hereda de Producto que agrupa varios productos.</b>
 *
 */
public class Combo extends Producto{
	
	/**
	 * Atributos propios de la clase
	 */
	private ArrayList<Producto> productos;
	private String descripcion;
	
	/**
	 * Hereda de la clase Producto: nombre,precio y calorias.
	 * @param nombre
	 * @param descripcion
	 */
	public Combo(String nombre, String descripcion){
		super(nombre,descripcion, 0, 0);
		productos = new ArrayList<Producto>();
		this.descripcion = descripcion;
	}
	
	/**
	 * 
	 * @return productos
	 */
	public ArrayList<Producto> getProductos(){
		return productos;
	}
	
	/**
	 * @return descripcion
	 */
	public String getDescripcion(){
		return descripcion;
	}
	
	/**
	 * Metodo que anade un producto simple al combo de productos ademas de actualizar el precio y las
	 * calorias totales. Se anden productos repetidos al combo, pero a la carta no.
	 * @param producto,carta
	 */
	public void anadirProductoSimple(ProductoSimple producto,ArrayList<Producto> carta){
			productos.add(producto);
			precio += producto.getPrecio();
			calorias += producto.getCalorias();
			if(!carta.contains(producto))
				carta.add(producto);
	}
	
	/**
	 * Metodo que obtiene el precio de todos los productos contenidos en el combo.
	 * Al obtener el precio del combo, realizamos sobre el un descuento del 20%.
	 * @return precio
	 */
	@Override
	public double getPrecio() {
		double precio = 0;
		for(Producto p:productos)
			precio += p.getPrecio();
		return precio * 0.8;
	}
	
	/**
	 * @return atributos del combo
	 */
	public String toString(){
		return  getNombre()+": "+"\n"+
				"Precio: "+getPrecio()+"\n"+
				"Descripcion: "+getDescripcion()+"\n"+
				"Calorias: "+calorias+"\n";
	}
	
}
