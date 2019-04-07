/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Peliculas;

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
public class Peliculas {
    private Map<Integer,String> peliculas;
    
    public Peliculas(){
        peliculas=new HashMap<>();
    }

    public Map<Integer, String> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(Map<Integer, String> peliculas) {
        this.peliculas = peliculas;
    }
    public int tam(){
        return peliculas.size();
    }
    public String nombre(int id){
        return peliculas.get(id);
    }
    public void leerPeliculas(String archivo) throws FileNotFoundException, IOException{
      BufferedReader br = new BufferedReader(new FileReader(archivo));
      String linea = br.readLine();
      while(linea != null) {
          String[] dividelinea=linea.split("\\|");
          peliculas.put(Integer.parseInt(dividelinea[0]),dividelinea[1]);
          linea=br.readLine();
      }
    }
}
