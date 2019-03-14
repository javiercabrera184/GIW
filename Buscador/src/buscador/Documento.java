/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscador;

import java.util.ArrayList;


/**
 *
 * @author usuario
 */
public class Documento {
    
    private String tipo="";
    private int numero=0;
    private String presidente="";
    private String fecha;
    private String epigrafe="";
    private String iniciativa="";
    private String materias="";
    private String interventores="";
    private ArrayList<String> intervencion=new ArrayList<>();
    private ArrayList<String> discursos= new ArrayList<>();
    private String texto="";
    private String extracto="";
    
    public String mostrar(){
        String output="";
        
        output+="Numero de sesion: "+numero+"\n";
        output+="Tipo de sesion: "+tipo+"\n";
        output+="Fecha: "+fecha+"\n";
        output+="Epigrafe: "+epigrafe+"\n";
        output+="Tipo Iniciativa: "+iniciativa+"\n";
        output+="Presidente: "+presidente+"\n";
        output+="Materias: "+materias+"\n";
        output+="Intervienen: "+interventores+"\n";
        output+="Intervinientes: "+"\n";
        ArrayList<String> set = new ArrayList<>();
        intervencion.stream().filter((s) -> (!set.contains(s))).forEachOrdered((s) -> {
            set.add(s);
        });
        for(int i=0;i<set.size();i++){
            output+="\t "+set.get(i)+"\n";
        }
        output+= "Discurso: \n";
        for(int i=0;i<discursos.size();i++){
            output+="\t "+discursos.get(i)+"\n";
        }
        return output;
    }

    public String getExtracto() {
        return extracto;
    }

    public void setExtracto(String extracto) {
        this.extracto = extracto;
    }
    
    
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
    
    
    
    public Documento() {
        
    }
    public String intervencionOf(int i){
        return intervencion.get(i);
    }
    public String discursosOf(int i){
        return discursos.get(i);
    }
    public String getTipo() {
        return tipo;
    }

    public int getNumero() {
        return numero;
    }

    public String getPresidente() {
        return presidente;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEpigrafe() {
        return epigrafe;
    }

    public String getIniciativa() {
        return iniciativa;
    }

    public String getMaterias() {
        return materias;
    }

    public String getInterventores() {
        return interventores;
    }

    public ArrayList<String> getIntervencion() {
        return intervencion;
    }

    public ArrayList<String> getDiscursos() {
        return discursos;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setEpigrafe(String epigrafe) {
        this.epigrafe = epigrafe;
    }

    public void setIniciativa(String iniciativa) {
        this.iniciativa = iniciativa;
    }

    public void setMaterias(String materias) {
        this.materias = materias;
    }

    public void setInterventores(String interventores) {
        this.interventores = interventores;
    }

    public void setIntervencion(ArrayList<String> intervencion) {
        this.intervencion = intervencion;
    }

    public void setDiscursos(ArrayList<String> discuros) {
        this.discursos = discuros;
    }
    public void addIntervencion(String i){
        intervencion.add(i);
    }

    public void addDiscurso(String i){
        discursos.add(i);
    }
    @Override
    public String toString() {
        return "Documento{" + "tipo=" + tipo + ", numero=" + numero + ", presidente=" + presidente + ", fecha=" + fecha + ", epigrafe=" + epigrafe + ", iniciativa=" + iniciativa + ", materias=" + materias + ", interventores=" + interventores + ", intervencion=" + intervencion + ", discuros=" + discursos + '}';
    }
    
    
    
    
    
    
    
    
}
