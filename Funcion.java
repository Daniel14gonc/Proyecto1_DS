/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Stack;

public class Funcion {
  private String nombre;
  private HashMap<String, Stack<String>> parametros;
  private String tasks;

  public Funcion (String Nombre, HashMap<String, String> Parametros, LinkedList<String> actions){
  }

  public Funcion(){
    parametros = new HashMap<String, Stack<String>>();
  }

  public void setNombre(String nombre){
    this.nombre = nombre;
  }

  public String getNombre(){
    return nombre;
  }


  public String getTasks(){

    return tasks;

  }

  public void addParameter(String parameter){
    parametros.put(parameter, new Stack<String>());
  }

  public void setTasks(String body){
    tasks = body;
  }


  public HashMap<String, String> getParameters(){

    HashMap<String, String> Param = null;

    return Param;

  }

  public void setParameters(String params) throws Exception{ 
    int cont = 0;
    String[] pm = params.split("\\s+");
    if(pm.length != parametros.size()){
      throw new Exception("La cantidad de parametros para la funcion "+ nombre+" ingresada no es correcta.");
    }
    for(String key: parametros.keySet()){
      Stack<String> temp = parametros.get(key);
      temp.add(pm[cont]);
      parametros.replace(key, temp);
      cont++;
    }
  }

  public boolean isParameter(String p){
    return parametros.containsKey(p);
  }

  public String getParameter(String k){
    System.out.println(parametros.get(k) + " Stack");
    return parametros.get(k).peek();
  }

  public void deleteValue(String k){
    parametros.get(k).pop();
  }

  public void deleteValues(){
    for(String key: parametros.keySet()){
      parametros.get(key).pop();
    }
  }
}