package poo.practica2;

/**
 *
 *<b> Clase que hereda de ProductoSimple y que contiene lo necesario para modelar una bebida.</b>
 *
 */
public class Bebida extends ProductoSimple {
	
	/**
	 * Atributos de la propia clase
	 */
	private boolean conAzucar;
	private boolean conCafeina;
	
	/**
	 * Hereda de la clase ProductoSimple: nombre,descripcion,precio,calorias.
	 * @param nombre
	 * @param descripcion
	 * @param precio
	 * @param calorias
	 * @param conAzucar
	 * @param conCafeina
	 */
	public Bebida(String nombre,String descripcion,double precio,double calorias,boolean conAzucar,boolean conCafeina){
		super(nombre,descripcion,precio,calorias);
		this.conAzucar = conAzucar;
		this.conCafeina = conCafeina;
	}
	
	/**
	 * 
	 * @return conCafeina
	 */
	public boolean isCafeina(){
		return conCafeina;
	}
	
	/**
	 * 
	 * @return conAzucar
	 */
	public boolean isAzucar(){
		return conAzucar;
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
	 * Modifica el precio de una bebida.
	 */
	@Override
	public void setPrecio(double precio){
		this.precio = precio;
	}
	
	/**
	 * @return caracteristicas de la bebida
	 */
	public String toString(){
		return 	"BEBIDA"+"\n"+
				"Producto: "+getNombre()+"\n"+
				"Precio: "+getPrecio()+"\n"+
				"Descripcion: "+getDescripcion()+"\n"+
				"Calorias: "+getCalorias()+"\n"+
				"Con azucar: "+conAzucar+"\n"+
				"Con cafeina: "+conCafeina+"\n";
	}
	
}
