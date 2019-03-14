/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexador;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.es.SpanishAnalyzer;

import org.apache.lucene.store.Directory;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;



/**
 *
 * @author usuario
 */
public class Indexador {
    static final String INDEX_DIRECTORY="/home/usuario/Master/GIW/ColeccionIniciativas/indices/";
    IndexWriter iWriter;

    public Indexador() {
    }
    
    
    
    public boolean Index() throws IOException{
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
        File stop = new File("/home/usuario/Master/GIW/palabras_vacias_utf8.txt");
        Collection<String> list;
        try (Scanner s = new Scanner(stop)) {
            list = new ArrayList<>();
            while (s.hasNext()){
                list.add(s.next());
            }
        }
        CharArraySet stop1=new CharArraySet(Arrays.asList(list),true);
        SpanishAnalyzer analyzer = new SpanishAnalyzer(stop1);
        
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(OpenMode.CREATE);
        
        iWriter = new IndexWriter(dir, config);
        
        return true;
    }
    
    public void addDocumento(Documento d) throws IOException{
        Document doc = new Document();
        
        doc.add(new Field("tipo",d.getTipo(),TextField.TYPE_STORED));
        doc.add(new Field("numero",Integer.toString(d.getNumero()),StringField.TYPE_STORED));
        doc.add(new Field("presidente",d.getPresidente(),TextField.TYPE_STORED));
        doc.add(new Field("fecha",d.getFecha(),TextField.TYPE_STORED));
        doc.add(new Field("epigrafe",d.getEpigrafe(),TextField.TYPE_STORED));
        doc.add(new Field("iniciativas",d.getIniciativa(),TextField.TYPE_STORED));
        doc.add(new Field("materias",d.getMaterias(),TextField.TYPE_STORED));
        doc.add(new Field("intervinientes",d.getIntervinientes(),TextField.TYPE_STORED));
        doc.add(new Field("extracto",d.getExtracto(),TextField.TYPE_STORED));
        String discurso="";
        String intervencion="";
        
        for(int i=0;i<d.getIntervencion().size();i++){
            doc.add(new Field("intervenciones",d.intervencionOf(i),TextField.TYPE_STORED));
            intervencion+=d.intervencionOf(i);
            intervencion+=" ";
        }
        for(int i=0;i<d.getDiscursos().size();i++){
            doc.add(new Field("discursos",d.discursosOf(i),TextField.TYPE_STORED));
            discurso+=d.discursosOf(i);
            discurso+=" ";
            
        }
        
        
        doc.add(new Field("texto",d.getTipo()+" "+Integer.toString(d.getNumero())+" "+d.getPresidente()+" "+d.getFecha()+" "+d.getEpigrafe()+" "+d.getIniciativa()+" "+d.getMaterias()+" "+d.getIntervinientes()+" "+intervencion+" "+discurso,TextField.TYPE_STORED));
        iWriter.addDocument(doc);
        
        
    }
    
    public void finish() throws IOException{
        iWriter.commit();
        iWriter.close();
    }
    
    
    
}
