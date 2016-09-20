package noticias;

import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;
import com.google.java.contract.Ensures;
import java.util.GregorianCalendar;

@Invariant(value={
	"cantidad()>=0","cantidad()==0 || fechaPrimeraNoticia()!=null","cantidad()==0 || fechaUltimaNoticia()!=null",
	"cantidad()==0 || fechaPrimeraNoticia().compareTo(fechaUltimaNoticia())<=0"
})

public interface DossierPrensa extends Iterable<Noticia>  {
		
	/**
	 * @description Cantidad de noticias en el dossier.
	 * @return Número de noticias en el dossier.
	 */
	int cantidad();
	
	/**
	 * @description Comprueba si se encuentra la noticia en el dossier.
	 * @param n - la noticia a buscar en el dossier.
	 * @return true si se encuentra la noticia en el dossier, false en otro caso.
	 */
	boolean estaNoticia(Noticia n);
	
	/**
	 * @description Comprueba y devuelve la fecha de la noticia mas antigua tras ser llamada
	 * @return la fecha de la noticia con mayor antiguedad
	 */
	@Requires(value="cantidad() != 0")
	GregorianCalendar fechaPrimeraNoticia();
	
	/**
	 * @description Comprueba y devuelve la fecha de la noticia mas reciente tras ser llamada
	 * @return la fecha de la noticia mas reciente
	 */
	@Requires(value="cantidad() != 0")
	GregorianCalendar fechaUltimaNoticia();
	
	/**
	 * @description Inserta una noticia en el dossier de prensa en orden respecto a la categoria y a la fecha de publicacion. Primero apareceran las noticias en Categoria Nacional, despues Internacional, y asi en este orden Sociedad, Economia, Deporte, Cultura. Dentro de cada categoria, primero apareceran las noticias con fecha de publicacion anterior. Si dos noticias coinciden en categoria y fecha de publicacion, la noticia que ha entrado mas tarde se inserta por detras de las que estaban ya insertadas en el dossier.
	 * @param n - la noticia a insertar en el dossier
	 */
	@Requires(value={"n != null","!esta(n)"})
	@Ensures(value={"cantidad() == old (cantidad()) + 1","esta(n)"})
	void inserta(Noticia n);
}
