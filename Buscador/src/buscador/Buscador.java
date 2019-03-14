/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscador;

import java.io.IOException;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author usuario
 */
public class Buscador {

    private static final SpanishAnalyzer analyzer = new SpanishAnalyzer();
    private static final int max =1000;
    private static final String directorioindice="/home/usuario/Master/GIW/ColeccionIniciativas/indices/";

    public Buscador() {
    }
    public String[] resaltar(String consulta,String tipo,String texto) throws ParseException, IOException, InvalidTokenOffsetsException{
        String [] s=null;
        Formatter formatter= new SimpleHTMLFormatter();
        QueryParser parser = new QueryParser(tipo,analyzer);
        Query q= parser.parse(consulta);
        QueryScorer scorer = new QueryScorer(q);
        Highlighter highlighter = new Highlighter(formatter, scorer);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, max);
        highlighter.setTextFragmenter(fragmenter);
        s=highlighter.getBestFragments(analyzer, tipo,texto, max);
        return s;
    }
    public ArrayList<Documento> buscar(String consulta,String tipo) throws IOException, ParseException, java.text.ParseException{
        ArrayList<Documento> documentos = new ArrayList();
        
        FSDirectory directory = FSDirectory.open(Paths.get(Buscador.directorioindice));
        
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);   
        Sort sort;
        TopDocs hits;
        
        QueryParser parser = new QueryParser(tipo,analyzer);
        Query q= parser.parse(consulta);
        sort = new Sort();
        sort.setSort(SortField.FIELD_DOC);
        hits = isearcher.search(q,max,sort);
        
        ScoreDoc[] resultados = hits.scoreDocs;
        
        for (ScoreDoc resultado : resultados) {
            Document doc = isearcher.doc(resultado.doc);
            Documento d = new Documento();
            d.setTipo(doc.get("tipo"));
            d.setNumero(Integer.parseInt(doc.get("numero")));
            d.setPresidente(doc.get("presidente"));
            d.setFecha(doc.get("fecha"));
            d.setEpigrafe(doc.get("epigrafe"));
            d.setIniciativa(doc.get("iniciativas"));
            d.setMaterias(doc.get("materias"));
            d.setInterventores(doc.get("intervinientes"));
            d.setExtracto(doc.get("extracto"));
            d.setIntervencion(new ArrayList<>( Arrays.asList(doc.getValues("intervenciones"))));
            d.setDiscursos(new ArrayList<>(Arrays.asList(doc.getValues("discursos"))));
            d.setTexto(doc.get("texto"));
            documentos.add(d);
        }
        
        return documentos;
    }
    
}
