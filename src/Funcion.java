/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 */

/**
 *Import de librerias de java
 */
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Stack;

/**
* Clase Funcion
* Crea las funciones y controla el manejo de las mismas
*/
public class Funcion {

  /**
  * Atributos
  * Informacion necesaria para la construccion de una funcion
  */
  private String nombre;
  private HashMap<String, Stack<String>> parametros;
  private String tasks;

  /**
  * Constructor
  * @param Nombre: String del nombre de la funcion
  * @param Parametros: HashMap<String, String> de los parametros de la funcion
  * @param actions: LinkedList<String> de las acciones de la funcion
  */
  public Funcion (String Nombre, HashMap<String, String> Parametros, LinkedList<String> actions){
  }

  /**
  * Constructor
  * Instancia un nuevo HashMap<String,String> para los parametros
  */
  public Funcion(){
    parametros = new HashMap<String, Stack<String>>();
  }

  /**
  * Setter para cambiar el nombre de la funcion
  * @param nombre: Nuevo nombre de la funcion
  */
  public void setNombre(String nombre){
    this.nombre = nombre;
  }

  /**
  * Getter para obtener el nombre de la funcion
  * @return: Nombre de la funcion
  */
  public String getNombre(){
    return nombre;
  }

  /**
  * Getter para obtener las tareas de la funcion
  * @return tasks: String de las tareas de la funcion
  */
  public String getTasks(){
    return tasks;
  }

  /**
  * Método addParameter
  * Se agregan parametros al stack de parametros
  * @param parameter: String del nuevo parametro
  */
  public void addParameter(String parameter){
    parametros.put(parameter, new Stack<String>());
  }

  /**
  * Setter para definir el cuerpo de la funcion
  * @param body: String que corresponde al cuerpo de la funcion
  */
  public void setTasks(String body){
    tasks = body;
  }

  /**
  * Getter para obtener los parametros en el HashMap
  * @return param: Parametros del HashMap
  */
  public HashMap<String, String> getParameters(){

    HashMap<String, String> Param = null;
    return Param;
  }

  /**
  * Método setParameters
  * Evalua si parametros de una funcion son suficientes
  * @param params: String de los nuevos parametros
  */
  public void setParameters(String params) throws Exception{ 

    int cont = 0;
    /**
    * Verifica que los parametros sean suficientes para evaluar una expresion
    */
    String[] pm = params.split("\\s+");
    if(pm.length != parametros.size()){
      throw new Exception("La cantidad de parametros para la funcion "+ nombre+" ingresada no es correcta.");
    }
    /**
    * Se agrega el nuevo parametro
    */
    for(String key: parametros.keySet()){
      Stack<String> temp = parametros.get(key);
      temp.add(pm[cont]);
      parametros.replace(key, temp);
      cont++;
    }
  }

  /**
  * Método isParameter
  * @param p: String que se verificara si es un parametro
  * @return boolean: true, si es un parametro; false si no lo es
  */
  public boolean isParameter(String p){
    return parametros.containsKey(p);
  }

  /**
  * Método getParameter
  * Getter para obtener un parametro del stack
  * @param k: String por el cual se buscara el parametro en el HashMap
  * @return String: Valor del parametro
  */
  public String getParameter(String k){
    return parametros.get(k).peek();
  }

  /**
  * Método deleteValue
  * Elimina un parametro del HashMap
  * @param k: String del parametro que se quiere eliminar
  */
  public void deleteValue(String k){
    parametros.get(k).pop();
  }

  /**
  * Método deleteValues
  * Elimina los parametros del HashMap
  */
  public void deleteValues(){
    for(String key: parametros.keySet()){
      parametros.get(key).pop();
    }
  }
}