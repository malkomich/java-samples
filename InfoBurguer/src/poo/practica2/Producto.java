package poo.practica2;

/**
 * 
 * Clase base de la que heredan dos tipos de productos: ProductoSimple(Necesitamos saber su precio
 * y poder modificarlo si es requerido) y Combo(Que solo necesitamos modificar el precio si es requerido).
 *
 */
public abstract class Producto {
	
	/**
	 * Atributos de la clase Productos, seran protegidos por temas de herencia.
	 */
	protected String nombre;
	protected String descripcion;
	protected double precio;
	protected double calorias;
	
	/**
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param precio
	 * @param calorias
	 */
	public Producto(String nombre,String descripcion,double precio,double calorias){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.calorias = calorias;
	}
	
	/**
	 * 
	 * @return nombre
	 */
	public String getNombre(){
		return nombre;
	}
	
	/**
	 * 
	 * @return nombre
	 */
	public String getDescripcion(){
		return descripcion;
	}
	
	/**
	 * 
	 * @return calorias
	 */
	public double getCalorias(){
		return calorias;
	}
	
	/**
	 * Metodo abstracto que se implementa en las clases que heredan esta clase.
	 * @return
	 */
	public abstract double getPrecio();
	
}
