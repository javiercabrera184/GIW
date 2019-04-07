/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuarios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class Usuarios {
    
    private Map<Integer, Map<Integer,Integer> > puntuaciones;

    public Map<Integer, Map<Integer, Integer>> getPuntuaciones() {
        return puntuaciones;
    }
    
    
    public Usuarios(){
        puntuaciones= new HashMap<>();
    }
    public Map<Integer,Integer> getPos(int i){
        return puntuaciones.get(i);
    }
    public int tamanio(){
        return puntuaciones.size();
    }
    public void leerUsuarios(String archivo) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea = br.readLine();
        while(linea!=null){
            String[] dividelinea=linea.split("\t");
            int usuario=Integer.parseInt(dividelinea[0]);
            int pelicula= Integer.parseInt(dividelinea[1]);
            int puntuacion=Integer.parseInt(dividelinea[2]);
            if(puntuaciones.containsKey(usuario)){
                puntuaciones.get(usuario).put(pelicula, puntuacion);
            }else{
                Map<Integer,Integer> punt=new HashMap<>();
                punt.put(pelicula,puntuacion);
                puntuaciones.put(usuario,punt);
            }
            linea= br.readLine();
            
        }
    }
    
}
