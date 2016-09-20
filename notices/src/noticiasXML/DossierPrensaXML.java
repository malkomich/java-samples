package noticiasXML;

import java.io.IOException;
import java.util.GregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import noticias.DossierPrensa;
import noticias.Noticia;

public interface DossierPrensaXML extends DossierPrensa {
	
	/**
	 * @description Borra noticias dado un determinado titular
	 * @param titular - Cadena de caracteres que debera coincidir con alguna de las existentes del objeto para realizar la opcion
	 * @param documentoXML - Documento en formato XML que guarda el resultado
	 */
    void borrar(String titular, String documentoXML);

    /**
     * @description Busca noticias entre un determinado rango de fechas y una cadena de caracteres dada.
     * @param cadena - Cadena de caracteres que sera un substring para poder encontrar una determinada noticia
     * @param fechaPubli1 - Fecha menor del rango de fechas
     * @param fechaPubli2 - Fecha mayor del rango de fechas
     * @param documentoXML - Documento en formato XML en el que busca
     */
    void buscaNoticias(String cadena, GregorianCalendar fechaPubli1, GregorianCalendar fechaPubli2, String documentoXML);

    int cantidad();

    boolean estaNoticia(Noticia n);

    GregorianCalendar fechaPrimeraNoticia();

    GregorianCalendar fechaUltimaNoticia();

    void inserta(Noticia n);

    /**
     * @description Modifica una noticia dado un titular de una determinada noticia
     * @param eleccion - Cadena de caracteres que corresponde con el titular de la noticia
     * @param modificacion - Cadena de caracteres que sustituira a la eleccion
     * @param documentoXML - Documento en formato XML que guardara el resultado
     */
    void modifica(String eleccion, String modificacion, String documentoXML);
    
    /**
     * @description Dado un documento, pasa una Lista ArrayList a un documento en formato XML
     * @param documentoXML - ombre del documento donde se guarda el ArrayList
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    void pasarToArrayListXML(String documentoXML) throws ParserConfigurationException, SAXException, IOException;
}
