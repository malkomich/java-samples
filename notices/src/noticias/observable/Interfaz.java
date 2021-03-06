package noticias.observable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import noticias.Noticia;


/**
 * @description Interfaz grafica para el observador que muestra las noticias del dossier. Es la clase observador
 */
@SuppressWarnings("serial")
public class Interfaz extends javax.swing.JFrame implements Observer{
	protected MiDossierObservable doss;
	protected GregorianCalendar primerDiaSemana;
	protected GregorianCalendar ultimoDiaSemana;
	
	
    public Interfaz(MiDossierObservable doss) {
    	this.doss = doss;
        initComponents();
    	doss.addObserver(this);
    	this.update(doss, null);
    }
    
	@Override
	public void update(Observable o, Object arg) {
		buscaNoticiasEnRango();
		System.out.println("Hay cambios en: "+o+" --> "+arg);
	}
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        BotonRetroceder = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextAreaNoticias = new javax.swing.JTextArea();
        TextFieldFecha = new javax.swing.JTextField();
        Salir = new javax.swing.JButton();
        BotonAdelante = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BotonRetroceder.setText("Retroceder");
        BotonRetroceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRetrocederActionPerformed(evt);
            }
        });

        TextAreaNoticias.setEditable(false);
        TextAreaNoticias.setColumns(100);
        TextAreaNoticias.setRows(10);
        jScrollPane1.setViewportView(TextAreaNoticias);

        TextFieldFecha.setEditable(false);
 
        //Rellena los campos Fecha y Noticias con las fechas de la semana y las noticias correspondientes
        textoCampoFechaYAreaNoticias();
        
        Salir.setText("Salir");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        BotonAdelante.setText("Adelante");
        BotonAdelante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAdelanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(BotonRetroceder)
                .addGap(38, 38, 38)
                .addComponent(TextFieldFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonAdelante)
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextFieldFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonAdelante)
                    .addComponent(BotonRetroceder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Salir)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>                        


	private void BotonRetrocederActionPerformed(java.awt.event.ActionEvent evt) {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
        
        primerDiaSemana = restaFecha(primerDiaSemana);
        ultimoDiaSemana = restaFecha(ultimoDiaSemana);
        
        Date fechaCalendario1 = primerDiaSemana.getTime();
        Date fechaCalendario2 = ultimoDiaSemana.getTime();
        
        String a = dateformat.format(fechaCalendario1);
        String b = dateformat.format(fechaCalendario2);
        //-------------------------------------------------------------------------------------
        
        TextFieldFecha.setText(a+"<-->"+b);

        buscaNoticiasEnRango();
    }

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {                                      
        System.exit(0);
    }                                     

    private void BotonAdelanteActionPerformed(java.awt.event.ActionEvent evt) {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
        
        primerDiaSemana = sumaFecha(primerDiaSemana);
        ultimoDiaSemana = sumaFecha(ultimoDiaSemana);
        
        Date fechaCalendario1 = primerDiaSemana.getTime();
        Date fechaCalendario2 = ultimoDiaSemana.getTime();
        
        String a = dateformat.format(fechaCalendario1);
        String b = dateformat.format(fechaCalendario2);
        //-------------------------------------------------------------------------------------
        
        TextFieldFecha.setText(a+"<-->"+b);
        
        buscaNoticiasEnRango();
    }                                             

    
    /**
     * FUNCIONES PARA LAS FECHAS
     */
    public GregorianCalendar restaFecha(GregorianCalendar calendar){
    	calendar.add(GregorianCalendar.DATE, -7);
        return calendar;
    }
    
    public GregorianCalendar sumaFecha(GregorianCalendar calendar){
    	calendar.add(GregorianCalendar.DATE, +7);
        return calendar;
    }
    
    public GregorianCalendar firstDayOfWeek(GregorianCalendar calendar){  
    	GregorianCalendar cal = (GregorianCalendar) calendar.clone();  
        int day = cal.get(GregorianCalendar.DAY_OF_YEAR);  
        while(cal.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.MONDAY){  
             cal.set(GregorianCalendar.DAY_OF_YEAR, --day);  
        }  
        return cal;  
   } 
    public GregorianCalendar lastDayOfWeek(GregorianCalendar calendar){  
    	GregorianCalendar cal = (GregorianCalendar) calendar.clone();  
        int day = cal.get(GregorianCalendar.DAY_OF_YEAR);  
        while(cal.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY){  
             cal.set(GregorianCalendar.DAY_OF_YEAR, ++day);  
        }  
        return cal;  
   } 
    //FIN FUNCIONES FECHAS
    
    private void textoCampoFechaYAreaNoticias() {
        // PARA INICIAR EN LA SEMANA ACTUAL --------------------------------------------------
        GregorianCalendar calendario = (GregorianCalendar) GregorianCalendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
        
        primerDiaSemana = firstDayOfWeek(calendario);
        ultimoDiaSemana = lastDayOfWeek(calendario);
        
        Date fechaCalendario1 = primerDiaSemana.getTime();
        Date fechaCalendario2 = ultimoDiaSemana.getTime();
        
        String a = dateformat.format(fechaCalendario1);
        String b = dateformat.format(fechaCalendario2);
        //-------------------------------------------------------------------------------------
        
        TextFieldFecha.setText(a+"<-->"+b);
        
        buscaNoticiasEnRango();
	}

    private void buscaNoticiasEnRango() {
    	SimpleDateFormat f = new SimpleDateFormat("dd-MMMM-yyyy");
    	
    	String concatena = "";
    	Iterator<Noticia> it;
    	it = doss.iterator();
    	
    	while(it.hasNext()){
    		Noticia actual = it.next();
			if(actual.fechaPublicacion().compareTo(primerDiaSemana) > 0 && actual.fechaPublicacion().compareTo(ultimoDiaSemana) < 0 || f.format(primerDiaSemana.getTime()).equalsIgnoreCase(f.format(actual.fechaPublicacion().getTime())) ||  f.format(ultimoDiaSemana.getTime()).equalsIgnoreCase(f.format(actual.fechaPublicacion().getTime()))){
    			concatena = concatena+actual.toString()+"\n";
    		}
    	}
    	TextAreaNoticias.setText(concatena);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton BotonAdelante;
    private javax.swing.JButton BotonRetroceder;
    private javax.swing.JButton Salir;
    private javax.swing.JTextArea TextAreaNoticias;
    private javax.swing.JTextField TextFieldFecha;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration
}