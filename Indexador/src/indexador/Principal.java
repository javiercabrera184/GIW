/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexador;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author usuario
 */
public class Principal {

    /**
     * @param args the command line arguments
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws ParserConfigurationException, IOException {
        // TODO code application logic here
        
        Parser parser = new Parser();
        parser.ParsearDocumentos();
        
        Indexador index = new Indexador();
        int i=0;
        if(index.Index()){
            for(Documento docu: Parser.getDocumentos()){
                index.addDocumento(docu);
                System.out.println("Documento indexado: "+i);
                i++;
            }
        }
        
        index.finish();
    }
    
}
