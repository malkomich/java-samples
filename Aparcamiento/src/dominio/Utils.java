package dominio;

/** Clase Utils
 * <p>Esta clase nos proporciona metodos estaticos de caracter general.</p>
 * 
 * @author Juan Carlos Gonzalez Cabrero
 * @author Oscar Fernandez Nuñez
 * @version 1.0
 *
 */
public class Utils {

	/** <h1>checkDNI</h1>
	 * <p>public static boolean checkDNI(String dni)</p>
	 * @param dni - <code>String</code>
	 * @return <p><b>true</b> si la cadena es un DNI valido.</p>
	 * <p><b>false</b> si no se valida como DNI.</p>
	 */
	public static boolean checkDNI(String dni){
		final String letra= "TRWAGMYFPDXBNJZSQVHLCKE";
		boolean isDNI= false;
		if(dni.length()==9){
			isDNI=true;
			for(int i=0;i<dni.length()-1;i++){
				isDNI= isDNI&&Character.isDigit(dni.charAt(i));
			}
			int valor= new Integer(dni.substring(0, 8));
			int aux= valor%23;
			if(dni.charAt(8)==letra.charAt(aux)){
				isDNI= true;
			}
		}
		return isDNI;
	}
	
}
