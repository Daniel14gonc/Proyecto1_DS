/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */
 


import java.util.LinkedList;
import java.util.HashMap;

public class Funcion {
  private String nombre;
  private HashMap<String, String> parametros;
  private String tasks;

  public Funcion (String Nombre, HashMap<String, String> Parametros, LinkedList<String> actions){


  }

  public Funcion(){
    parametros = new HashMap<String, String>();
  }

  public void setNombre(String nombre){
    this.nombre = nombre;
  }

  public String getNombre(){
    return nombre;
  }


  public String getActions(){

    return tasks;

  }

  public void addParameter(String parameter){
    parametros.put(parameter, "");
  }

  public void setTasks(String body){
    tasks = body;
  }


  public HashMap<String, String> getParameters(){

    HashMap<String, String> Param = null;

    return Param;

  }
}