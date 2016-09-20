package noticiasXML;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import noticias.Categoria;
import noticias.Noticia;

public class MiNoticia implements Noticia{

	protected String titular;
	protected GregorianCalendar fechaPublicacion;
	protected String fuente;
	protected URL enlaceAlContenido;
	protected Categoria categoria;
	
	public MiNoticia(String titular,GregorianCalendar fechaPublicacion,String fuente,URL enlaceAlContenido,Categoria categoria){
		this.titular = titular;
		this.fechaPublicacion = fechaPublicacion;
		this.fuente = fuente;
		this.enlaceAlContenido = enlaceAlContenido;
		this.categoria = categoria;
		
	}
	
	public MiNoticia(){}
	
	@Override
	public void setTitular(String t){
		this.titular = t;
	}
	
	@Override
	public Categoria categoria() {
		return this.categoria;
	}

	@Override
	public int compareTo(Noticia otra) {
		return this.fechaPublicacion.compareTo(otra.fechaPublicacion());
	}

	@Override
	public URL enlaceAlContenido() {
		return this.enlaceAlContenido;
	}

	@Override
	public GregorianCalendar fechaPublicacion() {
		return this.fechaPublicacion;
	}

	@Override
	public String fuente() {
		return this.fuente;
	}

	@Override
	public String titular() {
		return this.titular;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat  formatoFecha=new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm:ss");
		Date fechaPublicacion = this.fechaPublicacion.getTime();
		
		String imprim = formatoFecha.format(fechaPublicacion);
		return categoria+" "+enlaceAlContenido+" (" + imprim + ") " + fuente + " "+ titular;
	}
}