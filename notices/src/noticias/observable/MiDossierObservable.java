package noticias.observable;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Observable;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import noticias.Noticia;
import noticiasXML.DossierPrensaXML;
import noticiasXML.MiDossierXML;

/**
 * @description Es la clase observada por el/los observadores
 */
public class MiDossierObservable extends Observable implements DossierPrensaXML {

	protected MiDossierXML elDossier = new MiDossierXML();
	
	public MiDossierObservable() {}
	
	@Override
	public void borrar(String titular, String documentoXML) {
		elDossier.borrar(titular, documentoXML);
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void buscaNoticias(String cadena, GregorianCalendar fechaPubli1,GregorianCalendar fechaPubli2, String documentoXML) {
		elDossier.buscaNoticias(cadena, fechaPubli1, fechaPubli2, documentoXML);
	}

	@Override
	public int cantidad() {
		return elDossier.cantidad();
	}

	@Override
	public boolean estaNoticia(Noticia n) {
		return elDossier.estaNoticia(n);
	}

	@Override
	public GregorianCalendar fechaPrimeraNoticia() {
		return elDossier.fechaPrimeraNoticia();
	}

	@Override
	public GregorianCalendar fechaUltimaNoticia() {
		return elDossier.fechaUltimaNoticia();
	}

	@Override
	public void inserta(Noticia n) {
		elDossier.inserta(n);
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void modifica(String eleccion, String modificacion,String documentoXML) {
		elDossier.modifica(eleccion, modificacion, documentoXML);
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void pasarToArrayListXML(String documentoXML)throws ParserConfigurationException, SAXException, IOException {
		elDossier.pasarToArrayListXML(documentoXML);
	}

	@Override
	public Iterator<Noticia> iterator() {
		return elDossier.iterator();
	}
}
