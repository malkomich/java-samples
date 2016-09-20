package exceptions;

/** Clase ParkingError.
 * Nos permite capturar los errores del sistema de Aparcamiento.
 * 
 * @author Juan Carlos Gonzalez Cabrero
 * @author Oscar Fernandez Nuñez
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ParkingError extends Exception{

	private String mensaje;
	
	/**
	 * @param msg <code>String</code> mensaje de error.
	 */
	public ParkingError(String msg) {
        this.mensaje = msg;
    }

    @Override
    public String toString() {
        return this.mensaje;
    }
}
