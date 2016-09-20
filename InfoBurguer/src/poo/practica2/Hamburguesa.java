package poo.practica2;

/**
 * 
 * <b>Clase que hereda de ProductoSimple y que contiene lo necesario para modelar una hamburguesa.</b>
 *
 */
public class Hamburguesa extends ProductoSimple {
	
	/**
	 * Atributos de la propia clase.
	 */
	private String ingredientes;
	
	/**
	 * Hereda de la clase ProductoSimple: nombre,descripcion,precio,calorias.
	 * @param nombre
	 * @param descripcion
	 * @param precio
	 * @param calorias
	 * @param ingredientes
	 */
	public Hamburguesa(String nombre,String descripcion,double precio,double calorias,String ingredientes){
		super(nombre,descripcion,precio,calorias);
		this.ingredientes = ingredientes;
	}
	
	/**
	 * 
	 * @return ingredientes
	 */
	public String getIngredientes(){
		return ingredientes;
	}
	
	/**
	 * Implementacion del metodo abstracto de la clase padre.
	 * @return precio
	 */
	@Override
	public double getPrecio(){
		return precio;
	}
	
	/**
	 * Implementacion del metodo abstracto de la clase padre.
	 * Modifica el precio de la hamburguesa.
	 */
	@Override
	public void setPrecio(double precio){
		this.precio = precio;
	}
	
	/**
	 *
	 * @return caracteristicas de la hamburguesa
	 */
	public String toString(){
		return  "HAMBURGUESA"+"\n"+
				"Producto: "+getNombre()+"\n"+
				"Precio: "+getPrecio()+"\n"+
				"Descripcion: "+getDescripcion()+"\n"+
				"Calorias: "+getCalorias()+"\n"+
				"Ingredientes: "+ingredientes+"\n";
				
	}

}
