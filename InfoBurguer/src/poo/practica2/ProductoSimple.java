package poo.practica2;

/**
 * 
 * Clase que hereda de Producto y que agrupa productos de forma individual. Creamos otra clase abstracta 
 * porque se pueden modificar los precios de los productos indivduales, no asi en los combos.
 *
 */
public abstract class ProductoSimple extends Producto{
	
	/**
	 * Hereda de la clase Producto: nombre,descripcion,precio y calorias.
	 * @param nombre
	 * @param descripcion
	 * @param precio
	 * @param calorias
	 */
	public ProductoSimple(String nombre, String descripcion, double precio,double calorias) {
		super(nombre, descripcion, precio, calorias);
	}
	
	/**
	 * M�todo abstracto que se implementa en las clases que heredan esta clase
	 * @return
	 */
	public abstract double getPrecio();
	
	/**
	 * Metodo abstracto que se implementara en las clases que hereden de esta.Este metodo es el requerido 
	 * por los productos individuales y no por el combo.
	 * @param precio
	 */
	public abstract void setPrecio(double precio);

}
