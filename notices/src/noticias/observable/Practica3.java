package noticias.observable;

import java.io.IOException;
import java.net.URL;
import java.util.GregorianCalendar;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import noticias.Categoria;
import noticiasXML.MiNoticia;

public class Practica3 {
    public static void main(String[] args) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
        
        if (args.length > 1) {	//si hay más de 1 parámetro
            System.out.println("Hay demasiados parámetros. Solo hace falta el nombre del fichero");
        } else if (args.length == 0) { //si no hay parámetros      
            System.out.println("Falta el nombre del fichero");
        } else {
            final MiDossierObservable prueba = new MiDossierObservable();
            prueba.pasarToArrayListXML(args[0]);
            
        	
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(Practica3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(Practica3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(Practica3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(Practica3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run()  {

                    new Interfaz(prueba).setVisible(true);
                }
            });
            
            Thread.sleep(5000);
			GregorianCalendar fecha = new GregorianCalendar(2014,00,25,07,51,00);
			URL enlace = new URL("http://www.marca.com/");
			MiNoticia noticia1 = new MiNoticia("El Atleti vuelve a las andadas",fecha,"As",enlace,Categoria.Deporte);

			GregorianCalendar fecha2 = new GregorianCalendar(2014,01,02,23,52,20);
			URL enlace2 = new URL("http://www.marca.com/");
			MiNoticia noticia2 = new MiNoticia("Nadal no juega en Pekin",fecha2,"As",enlace2,Categoria.Deporte);
			
			GregorianCalendar fecha3 = new GregorianCalendar(2014,00,28,06,40,35);
			URL enlace3 = new URL("http://www.nytimes.com/");
			MiNoticia noticia3 = new MiNoticia("All right",fecha3,"New York Times",enlace3,Categoria.Internacional);


			prueba.inserta(noticia1);
			prueba.inserta(noticia2);
			prueba.inserta(noticia3);
			
			
			
			Thread.sleep(5000);
			prueba.modifica("El Atleti vuelve a las andadas", "VAMOOOOS!!!", args[0]);
			prueba.modifica("Nadal no juega en Pekin", "Un mallorquin no juega", args[0]);
			prueba.modifica("All right", "Amazing", args[0]);
			
			
			
			Thread.sleep(5000);
			prueba.borrar("VAMOOOOS!!!", args[0]);
			prueba.borrar("Un mallorquin no juega", args[0]);
			prueba.borrar("Amazing", args[0]);

       }//fin else
    }//FIN MAIN
}