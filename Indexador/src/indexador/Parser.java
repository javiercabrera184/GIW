/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 *
 * @author usuario
 */
public class Parser {
    
    private static ArrayList<Documento> documentos;
    
    public Parser(){
        Parser.documentos= new ArrayList<>();
    }

    public static ArrayList<Documento> getDocumentos() {
        return documentos;
    }
    
    public void ParsearDocumentos() throws ParserConfigurationException{
        String directorio="/home/usuario/Master/GIW/ColeccionIniciativas/iniciativas08/";
        File f = new File(directorio);
        ArrayList<String> nombreficheros = new ArrayList<>();
        
        if(f.exists()){
            File[] ficheros = f.listFiles();
            for(File fichero : ficheros){
                nombreficheros.add(fichero.getName());
 
            }
            
            nombreficheros.forEach((String nombre) -> {
                File inputFile = new File(directorio+nombre);
                System.out.println("Leyendo fichero: "+nombre);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = null;
                try {
                    dBuilder = dbFactory.newDocumentBuilder();
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                }
                Document doc = null;
                try {
                    doc = dBuilder.parse(inputFile);
                } catch (SAXException | IOException ex) {
                    Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                }
                doc.getDocumentElement().normalize();
                
                
                Documento d= new Documento();
                String fecha="";
                fecha+=doc.getElementsByTagName("dia").item(0).getTextContent();
                fecha+=" de ";
                fecha+=doc.getElementsByTagName("mes").item(0).getTextContent();
                fecha+=" del ";
                fecha+=doc.getElementsByTagName("anio").item(0).getTextContent();
                
                
                d.setFecha(fecha);
                d.setPresidente(doc.getElementsByTagName("presidente").item(0).getTextContent());
                int num;
                try {
                    num = Integer.parseInt(doc.getElementsByTagName("numero_diario").item(0).getTextContent());
                }catch(NumberFormatException ex){
                    num = 0;
                }
                
                d.setNumero(num);
                String inter;
                try{
                    inter=doc.getElementsByTagName("proponentes").item(0).getTextContent();
                }catch(NullPointerException ex){
                    inter="";
                }
                d.setExtracto(doc.getElementsByTagName("extracto").item(0).getTextContent());
                d.setIniciativa(doc.getElementsByTagName("tipo_iniciativa").item(0).getTextContent());
                d.setInterventores(inter);
                d.setEpigrafe(doc.getElementsByTagName("tipo_epigrafe").item(0).getTextContent());
                d.setTipo(doc.getElementsByTagName("tipo_sesion").item(0).getTextContent());
                
                String materias;
                try{
                    materias=doc.getElementsByTagName("materias").item(0).getTextContent();
                }catch(NullPointerException ex){
                    materias="";
                }
                d.setMaterias(materias);
                NodeList nList = doc.getElementsByTagName("intervencion");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        d.addIntervencion(eElement.getElementsByTagName("interviniente").item(0).getTextContent());
                    }
                    
                }
                NodeList nList1 = doc.getElementsByTagName("discurso");
                for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
                    Node nNode1 = nList1.item(temp1);

                    Element eElement1 = (Element) nNode1;
                    for(int z=0;z<eElement1.getElementsByTagName("parrafo").getLength();z++){
                            String discurso;
                        try{
                            discurso=eElement1.getElementsByTagName("parrafo").item(z).getTextContent();
                        }catch (NullPointerException ex){
                            discurso="";
                        }
                        d.addDiscurso(discurso);
                    }


                }
                Parser.documentos.add(d);
            });
        }
    }
}
