package noticias;

import java.net.URL;
import java.util.GregorianCalendar;

import com.google.java.contract.Invariant;

/**
 * Modela la interfaz de una noticia para un dossier de prensa
 */
@Invariant(value={
	"titular()!=null","fechaPublicacion()!=null","fuente()!=null","enlaceAlContenido()!=null"
})

public interface Noticia extends Comparable<Noticia>{
	/**
	 * @description Titular de la noticia
	 * @return Retorna un String. Ejemplo: "España se clasifica para el mundial Brasil 2014"
	 */
	Categoria categoria();
	
	/**
	 * @description Permite comparar dos noticias respecto de su fecha de publicación.
	 * @return 
	 0 si coinciden en la fecha de publicacion,
	<0 si la fecha de publicacion es anterior a la de la “otra”,
	>0 si la fecha de publicacion es posterior a la de la “otra”
	 */
	int compareTo(Noticia otra);
	
	/**
	 * @description URL donde se encuentra el contenido completo de la noticia.
	 * @return Para ello se utiliza un objeto de tipo URL. Para construir el objeto URL se utiliza la cadena con la url. Ejemplo: new URL("http://www.huffingtonpost.es/2013/10/15/espana-mundialbrasil_n_4102323.html")
	 */
	URL enlaceAlContenido();
	
	/**
	 * @description Fecha en la que aparecio publicada
	 * @return Retorna un Objeto Calendar Ejemplo: "Si la noticia fue publicada el 15-10-2013 a las 22:55h se tendra un objeto que pudo ser obtenido como new GregorianCalendar(2013,10,15,22,55,0)"
	 */
	GregorianCalendar fechaPublicacion();
	
	/**
	 * @description Fuente de la noticia: agencia de prensa, periodico, etc.
	 * @return Retorna un string con su nombre. Ejemplo: "huffingtonpost"
	 */
	String fuente();
		
	/**
	 * @description Titular de la noticia
	 * @return Retorna un string
	 */
	String titular();
	
	/**
	 * @description Proporciona un resumen de la noticia en una sola linea
	 * @return Retorna un String que contiene el titular, la fecha de publicacion, la fuente, la Categoría y la URL donde se encuentra el contenido completo de la noticia. Se sugiere utilizar la clase java.text.SimpleDateFormat Ejemplo: "España se clasifica para el mundial Brasil 2014. Martes 15 Octubre 2013 22:55:00. huffingtonpost. Deporte. http://www.huffingtonpost.es/2013/10/15/espana-mundialbrasil_n_4102323.html"
	 */
	String toString();

	/**
	 * @description Setter para modificar el valor de titular en el momento de la llamada
	 * @param t : el valor por el que el valor actual sera modificado
	 */
	void setTitular(String modificacion);
}