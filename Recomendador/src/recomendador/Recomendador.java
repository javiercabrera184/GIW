/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recomendador;

import Peliculas.Peliculas;
import Usuarios.Usuarios;
import Utils.Utils;
import java.io.IOException;
import java.util.Comparator;

import java.util.HashMap;
import java.util.Iterator;


import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;


/**
 *
 * @author usuario
 */
public class Recomendador {

    /**
     * @param args the command line arguments
     */
    private static final boolean puntuacionesaleatorias=true;
    private static final int npeliculas=20;
    private static final int nrecomendaciones=20;
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Random random = new Random();
        Usuarios usuario=new Usuarios();
        Peliculas pelicula= new Peliculas(); 
        usuario.leerUsuarios("datos/u.data");
        pelicula.leerPeliculas("datos/u.item");
        
        Map<Integer,Integer> notas= new HashMap<>();
        System.out.println("Introduzca Puntuaciones a las siguientes peliculas\n");
        Scanner in = new Scanner(System.in);
        int nota;
        for(int i=0;i<npeliculas;i++){
            int id = random.nextInt(pelicula.tam());
            System.out.println(pelicula.nombre(id));
            do{
                
                if(puntuacionesaleatorias){
                    nota=random.nextInt(5) + 1;
                }else{
                    System.out.println("Valoracion 1-5");
                    nota=Integer.parseInt(in.nextLine());
                }
                
            }while(nota<1 || nota>5);
            
            notas.put(id, nota);
        }
        
        Utils util= new Utils();
        Map<Integer,Double> salida=new HashMap<>();
        salida= util.Pearson(notas, usuario.getPuntuaciones());
        
        ValueComparator comparador = new ValueComparator((HashMap<Integer, Double>) salida);
        TreeMap<Integer,Double> ordenado= new TreeMap<Integer,Double>(comparador);
        TreeMap<Integer,Double> vecindario= new TreeMap<Integer,Double>(comparador);
        ordenado.putAll(salida);
        
        Iterator it = ordenado.entrySet().iterator();
        int i = 0;
        while ( i < npeliculas) {
            Map.Entry entry = (Map.Entry) it.next();
            vecindario.put((int) entry.getKey(), (double) entry.getValue());
            i++;
            
        }
        Map<Integer,Double> p_recomendadas = new HashMap<>();
        p_recomendadas=util.recomendaciones(notas, usuario.getPuntuaciones(), vecindario, pelicula.getPeliculas());
        
        ValueComparator comparador1 = new ValueComparator((HashMap<Integer, Double>) p_recomendadas);
        TreeMap<Integer,Double> ordenado1= new TreeMap<>(comparador1);
        TreeMap<Integer,Double> vecindario1= new TreeMap<>(comparador1);
        ordenado1.putAll(p_recomendadas);
        
        Iterator it1 = ordenado1.entrySet().iterator();
        i = 0;
        while ( i < nrecomendaciones) {
            Map.Entry entry = (Map.Entry) it1.next();
            vecindario1.put((int) entry.getKey(), (double) entry.getValue());
            i++;
            
        }
        System.out.println("______________________");
        System.out.println("Peliculas recomendadas");
        System.out.println("______________________");
        Iterator it2 = vecindario1.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry entrada = (Map.Entry) it2.next();
            
            System.out.println(pelicula.nombre((int) entrada.getKey()) + "\nPuntuacion: " + entrada.getValue()+"\n");
                
            
        }
        
    }
    
    
}
class ValueComparator implements Comparator<Integer>{

    HashMap<Integer, Double> map = new HashMap<Integer, Double>();
   
    
    public ValueComparator(HashMap<Integer, Double> map){
            this.map.putAll(map);
    }

    

    
    public int compare(Integer s1, Integer s2) {
            if(map.get(s1) >= map.get(s2)){
                    return -1;
            }else{
                    return 1;
            }	
    }

    
    
}