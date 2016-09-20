package noticiasXML;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import noticias.Categoria;
import noticias.Noticia;

public class MiDossierXML implements DossierPrensaXML {

	protected GregorianCalendar fechaPrimNot;
    protected GregorianCalendar fechaUltmNot;
    protected ArrayList<Noticia> dossierList = new ArrayList<Noticia>();

    public MiDossierXML() {};

	/**
	 * Convierte un String que recibe del documento XML al formato GregorianCalendar 
	 * @param fecha : Recibe fecha en formato String
	 * @return : devuelve la fecha metida como String y la devuelve en GregorianCalendar
	 * @throws ParseException
	 */
	public static GregorianCalendar toGregCalendar(String fecha) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm:ss");
        Date date = df.parse(fecha);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public void pasarToArrayListXML(String documentoXML) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parser = factory.newDocumentBuilder();
        FileReader input = new FileReader(documentoXML);
        InputSource source = new InputSource(input);

        // Procesamos el fichero XML y obtenemos nuestro objeto Document
        Document document = parser.parse(source);

        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node instanceof Element) {
                MiNoticia emp = new MiNoticia();
                NodeList childNodes = node.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);

                    if (cNode instanceof Element) {
                        String content = cNode.getLastChild().getTextContent().trim();
                        switch (cNode.getNodeName()) {
                            case "titular":
                                emp.titular = content;
                                break;
                            case "fechaPublicacion":
                                try {
                                    emp.fechaPublicacion = toGregCalendar(content);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "fuente":
                                emp.fuente = content;
                                break;
                            case "enlaceAlContenido":
                                emp.enlaceAlContenido = new URL(content);
                                break;
                            case "categoria":
                                emp.categoria = Categoria.valueOf(content);
                                break;
                        }
                    }
                }
                inserta(emp);
            }
        }
    }

    @Override
    public boolean estaNoticia(Noticia n) {
        boolean tmp = false;
        if (dossierList.contains(n)) {
            tmp = true;
        } else {
            for (Noticia x : dossierList) {
                if (n.titular().equalsIgnoreCase(x.titular()) && n.fuente().equalsIgnoreCase(x.fuente())) {
                    tmp = true;
                }
            }
        }
        return tmp;
    }

    @Override
    public void inserta(Noticia n) {
        if (!estaNoticia(n)) {
            Comparator<Noticia> comparador = new Comparator<Noticia>() {
                public int compare(Noticia not1, Noticia not2) {
                    Integer item1 = (not1.categoria().ordinal());
                    Integer item2 = (not2.categoria().ordinal());
                    int resultado = item1.compareTo(item2);
                    if (resultado != 0) {
                        return resultado;
                    }
                    resultado = not1.fechaPublicacion().compareTo(not2.fechaPublicacion());
                    return resultado;
                }
            };
            dossierList.add(n);
            Collections.sort(dossierList, comparador);

            if (cantidad() == 1) {
                this.fechaPrimNot = n.fechaPublicacion();
                this.fechaUltmNot = n.fechaPublicacion();
            } else {
                if (fechaPrimeraNoticia().compareTo(n.fechaPublicacion()) > 0) {
                    this.fechaPrimNot = n.fechaPublicacion();
                }
                if (fechaUltimaNoticia().compareTo(n.fechaPublicacion()) < 0) {
                    this.fechaUltmNot = n.fechaPublicacion();
                }
            }
            escribeEnFicheroXMLarray(dossierList, "Documentos.xml");
        }
    }

    @Override
    public int cantidad() {
        return this.dossierList.size();
    }

    @Override
    public GregorianCalendar fechaPrimeraNoticia() {
        return this.fechaPrimNot;
    }

    @Override
    public GregorianCalendar fechaUltimaNoticia() {
        return this.fechaUltmNot;
    }

    public void borrar(String titular, String documentoXML) {
    	Iterator<Noticia> it = dossierList.iterator();
        while (it.hasNext()) {
            Noticia e = it.next();
            if (e.titular().equals(titular)) {
                it.remove();
            }
        }
        escribeEnFicheroXMLarray(dossierList, documentoXML);
    }

    public void modifica(String eleccion, String modificacion, String documentoXML) {
        int i = 0;
        for (Noticia e : dossierList) {
            if (dossierList.get(i).titular().contains(eleccion)) {
                e.setTitular(modificacion);
            }
            i++;
        }
        escribeEnFicheroXMLarray(dossierList, documentoXML);
    }

    public void buscaNoticias(String cadena, GregorianCalendar fechaPubli1, GregorianCalendar fechaPubli2, String documentoXML) {
        // Se controla que se meta una fecha mas reciente en primer lugar nos devolverá -1 si fechaPubli1 es menor que fechaPubli2, 0 si son iguales, y 1 en el otro caso.
        if (fechaPubli1.compareTo(fechaPubli2) > 0) {
            GregorianCalendar fechaAux = new GregorianCalendar();
            fechaAux = fechaPubli1;
            fechaPubli1 = fechaPubli2;
            fechaPubli2 = fechaAux;
        }

        DocumentBuilderFactory documentBuilder;
        DocumentBuilder parser;

        try {
            // Construimos el DocumentBuilder
            documentBuilder = DocumentBuilderFactory.newInstance();
            parser = documentBuilder.newDocumentBuilder();
            FileReader input = new FileReader(documentoXML);
            InputSource source = new InputSource(input);

            // Procesamos el fichero XML y obtenemos nuestro objeto Document
            Document doc = parser.parse(source);
            DocumentFragment docFrag = doc.createDocumentFragment();
            XPath xpath = XPathFactory.newInstance().newXPath();

            if (cadena == "*") {
                // IMPRIME TODAS LAS QUE ESTEN ENTRE EL RANGO DE FECHAS
                NodeList nList = (NodeList) xpath.evaluate("/dossier/noticia", doc, XPathConstants.NODESET);

                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);

                    Element elemento = (Element) nNode;
                    String fecha = elemento.getElementsByTagName("fechaPublicacion").item(0).getTextContent();

                    GregorianCalendar comparableFecha = toGregCalendar(fecha);

                    if ((fechaPubli1.compareTo(comparableFecha) < 0) && (fechaPubli2.compareTo(comparableFecha) > 0)) {
                        System.out.println(nNode.getTextContent());
                        docFrag.appendChild(nList.item(i));
                    }
                }
                escribeEnFicheroFilterXML(docFrag);

            } else {
                // Añadimos comillas simples a la cadena de entrada porque sino no reconoce la cadena
                cadena = "'" + cadena + "'";

                // Filtramos una etiqueta mediante XPath con la cadena que ha recibido la funcion
                NodeList filtradoTitular = (NodeList) xpath.evaluate("/dossier/noticia[contains(titular," + cadena + ")]", doc, XPathConstants.NODESET);

                if (filtradoTitular.getLength() == 0) {
                    System.out.println("No hay coincidencias");
                } else {
                    for (int i = 0; i < filtradoTitular.getLength(); i++) {
                        Node nNode = filtradoTitular.item(i);

                        Element elemento = (Element) nNode;
                        String fecha = elemento.getElementsByTagName("fechaPublicacion").item(0).getTextContent();

                        GregorianCalendar comparableFecha = toGregCalendar(fecha);

                        if ((fechaPubli1.compareTo(comparableFecha) < 0) && (fechaPubli2.compareTo(comparableFecha) > 0)) {
                            System.out.println(filtradoTitular.item(i).getTextContent());
                            docFrag.appendChild(filtradoTitular.item(i));
                        }
                    }
                }
                escribeEnFicheroFilterXML(docFrag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description Escribe/vuelca el contenido de un ArrayList a un fichero en formato XML
     *
     * @param list : ArrayList de noticias
     */
    public static void escribeEnFicheroXMLarray(List<Noticia> list, String documentoXML) {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("dossier");
            doc.appendChild(root);

            SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm:ss");

            for (int i = 0; i < list.size(); i++) {
                Element Details = doc.createElement("noticia");
                root.appendChild(Details);

                Element categoria = doc.createElement("categoria");
                categoria.appendChild(doc.createTextNode(String.valueOf(list.get(i).categoria())));
                Details.appendChild(categoria);

                Element enlace = doc.createElement("enlaceAlContenido");
                enlace.appendChild(doc.createTextNode(String.valueOf(list.get(i).enlaceAlContenido())));
                Details.appendChild(enlace);

                Element fecha = doc.createElement("fechaPublicacion");
                Date tmp = list.get(i).fechaPublicacion().getTime();

                fecha.appendChild(doc.createTextNode(formatoFecha.format(tmp)));
                Details.appendChild(fecha);

                Element fuente = doc.createElement("fuente");
                fuente.appendChild(doc.createTextNode(String.valueOf(list.get(i).fuente())));
                Details.appendChild(fuente);

                Element titular = doc.createElement("titular");
                titular.appendChild(doc.createTextNode(String.valueOf(list.get(i).titular())));
                Details.appendChild(titular);
            }

            // Guarda el documento al disco
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            // Formatea Xml indentado y de formas mas bonita
            aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

            aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            try {
                FileWriter fos = new FileWriter(documentoXML);
                StreamResult result = new StreamResult(fos);
                aTransformer.transform(source, result);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (TransformerException ex) {
            System.out.println("Error guardando el documento");
        } catch (ParserConfigurationException ex) {
            System.out.println("Error creando el documento");
        }
    }

    /**
     * @description Guarda el contenido de un filtrado en un fichero en formato XML
     *
     * @param docFrag : el fragmento despues de parsear y seleccionar las
     * noticias que coincidian con lo elegido
     * @throws ParserConfigurationException
     */
    public static void escribeEnFicheroFilterXML(DocumentFragment docFrag) throws ParserConfigurationException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("omit-xml-declaration", "yes");
            DOMSource domSource = new DOMSource(docFrag);

            // SALIDA A FICHERO
            StreamResult result = new StreamResult(new FileOutputStream("filter.xml"));
            transformer.transform(domSource, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


	@Override
	public Iterator<Noticia> iterator() {
		return dossierList.iterator();
	}
}
