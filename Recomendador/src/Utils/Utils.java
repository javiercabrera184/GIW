/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author usuario
 */
public class Utils {

    public Utils() {
    }
    
   
    public Map<Integer,Double> Pearson(Map<Integer, Integer> usuario1, Map<Integer, Map <Integer, Integer> > puntuaciones){
        Map<Integer,Double> vecinos = new HashMap<>();
        Map<Integer,Double> salida = new HashMap<>();
        for(int usuario:puntuaciones.keySet()){
            ArrayList<Integer> coincidencias = new ArrayList<>();
            for(int pelicula: usuario1.keySet()){
                if(puntuaciones.get(usuario).containsKey(pelicula)){
                    coincidencias.add(pelicula);
                }
            }
            double coeficientePearson=0;
            if(coincidencias.size()>0){
                double numerador=0;
                double den1=0;
                double den2=0;
                for(int pelicula:coincidencias){
                    
                    double mediausuario=0;
                    for(int valor:usuario1.values()){
                        mediausuario=mediausuario+valor;
                    }
                    mediausuario/=usuario1.size();
                    double media=0;
                    for(int valor:puntuaciones.get(usuario).values()){
                        media=media+valor;
                    }
                    media/=puntuaciones.get(usuario).size();
                    double num1=usuario1.get(pelicula)-mediausuario;
                    double num2=puntuaciones.get(usuario).get(pelicula)-media;
                    numerador+=num1*num2;
                    den1+=Math.pow(num1,2);
                    den2+=Math.pow(num2,2);
                    
                    
                }
                double denominador=Math.sqrt(den1)*Math.sqrt(den2);
                if(denominador!=0){
                    coeficientePearson=numerador/denominador;
                }
            }
            vecinos.put(usuario,coeficientePearson);
            
        }
       
        Iterator<Map.Entry<Integer, Double>> i =vecinos.entrySet().iterator();
        while(i.hasNext()){
            Map.Entry entrada = (Map.Entry) i.next();
            if((double)entrada.getValue()>0){
                salida.put((int)entrada.getKey(), (double)entrada.getValue());
            }
        }
        return salida;
        
    }
    
    public Map<Integer,Double> recomendaciones(Map<Integer,Integer> usuario1 ,Map<Integer, Map <Integer, Integer> > puntuaciones,TreeMap<Integer,Double> vecinos,Map<Integer,String> peliculas){
        Map<Integer, Double> predicciones = new HashMap<>();
        for(int pelicula: peliculas.keySet()){
            if(!usuario1.containsKey(pelicula)){
                
                double numerador=0;
                double denominador=0;
                for(Integer vecino:vecinos.keySet()){
                    if(puntuaciones.get(vecino).containsKey(pelicula)){
                        Set<Integer> k=vecinos.keySet();
                        Collection<Double> v=vecinos.values();
                        Double[] values =  v.toArray(new Double[0]);
                        double coeficiente=0;
                        Iterator<Integer> it = k.iterator();
                        int i=0;
                        while(it.hasNext()){
                           
                           if(it.next()==vecino){
                               coeficiente=values[i];
                           }
                           i++;
                        }
                        
                        double media=0;
                        for(int valor:puntuaciones.get(vecino).values()){
                            media=media+valor;
                        }
                        media/=puntuaciones.get(vecino).size();
                        numerador+= coeficiente*(puntuaciones.get(vecino).get(pelicula)-media);
                        denominador+=Math.abs(coeficiente);
                    }
                }
                double prediccion=0;
                int prediccionredondeada=0;
                if(denominador>0){
                    double mediausuario=0;
                    for(int valor:usuario1.values()){
                        mediausuario=mediausuario+valor;
                    }
                    mediausuario/=usuario1.size();
                    prediccion=mediausuario+numerador/denominador;
                    prediccionredondeada= (int)Math.round(prediccion);
                    if(prediccionredondeada>5){
                        prediccionredondeada=5;
                    }
                    
                }
                predicciones.put(pelicula,(double)prediccionredondeada);
            }
        }
        return predicciones;
    }
    
}
