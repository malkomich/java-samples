package poo.practica2;

/**
 * 
 *<b> Clase que hereda de ProductoSimple y que contiene lo necesario para modelar un complemento.</b>
 *
 */
public class Complemento extends ProductoSimple {
	
	/**
	 * Atributos de la propia clase.
	 */
	private int numComensales;
	
	/**
	 * Hereda de la clase ProductoSimple: nombre,descripcion,precio,calorias.
	 * @param nombre
	 * @param descripcion
	 * @param precio
	 * @param calorias
	 * @param numComensales
	 */
	public Complemento(String nombre,String descripcion,double precio,double calorias,int numComensales){
		super(nombre,descripcion,precio,calorias);
		this.numComensales = numComensales;
	}
	
	/**
	 * 
	 * @return numComensales
	 */
	public int getNumComensales(){
		return numComensales;
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
	 * Modifica el precio de un complemento.
	 */
	@Override
	public void setPrecio(double precio){
		this.precio = precio;
	}
	
	/**
	 *
	 * @return caracteristicas del complemento
	 */
	public String toString(){
		return 	"COMPLEMENTO"+"\n"+
				"Producto: "+getNombre()+"\n"+
				"Precio: "+getPrecio()+"\n"+
				"Descripcion: "+getDescripcion()+"\n"+
				"Calorias: "+getCalorias()+"\n"+
				"Numero de Comensales: "+numComensales+"\n";
	}
	
}
