/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 */

 /**
 *Import de libreria de java
 */
import java.util.*;

/**
* Clase Comparable
* Maneja Setq, Cond y predicados
*/
public class Comparable 
{
  /**
  * Atributos
  * Stack que almacena valores
  */
  private static HashMap<String, String> SetqValores;

  /**
  * Constructor
  * Instancia un nuevo HashMap<String, String>
  */
  public Comparable(){
    SetqValores = new HashMap<String, String>();

  }

  public static boolean isValue(String value){
    return SetqValores.containsKey(value);
  }

  public static String getValue(String value){
    return SetqValores.get(value);
  }

  /**
  * Método vardefinidas
  * @return: HashMap instanciado en el constructor
  */
  public HashMap vardefinidas()
  {
    return SetqValores;
  }

  /**
  * Método Setq: Asigna un valor a una variable
  * @param variable: Variable a la que se le dará algún valor
  * @param valor: Valor que se le da a la variable.
  */
  public void Setq(String variable,String valor)
  {
    /**
    * Se verifica la existencia de la variable en el HashMap
    * Si no existe se agrega. De lo contrario se actualiza su valor
    */
    if(SetqValores.get(variable)==null)
    {
      SetqValores.put(variable,valor);
    }
    else
    {
      SetqValores.remove(variable);
      SetqValores.put(variable, valor);
    }

  }

  /**
  * Método equal: Compara dos números
  * @param a: double 1 a comparar
  * @param b: double 2 a comparar
  * @return: String resultado de la comparación (Nil = distintos; T = iguales)
  */
  public String Equal(double a, double b)
  {
    String estado="Nil";

    if(a==b)
    estado="T";

    return estado;
  }

  /**
  * Método Mayorque: Evalua si un número es mayor al otro
  * @param a: double 1 a evaluar
  * @param b: double 2 a evaluar
  * @return: String resultado de la comparación (Nil = No es mayor; T = Si es mayor)
  */
  public String Mayorque(double a, double b)
  {
    String estado="Nil";
    if(a>b)
    estado="T";

    return estado;
  }

/**
  * Método Menorque: Evalua si un número es menor al otro
  * @param a: double 1 a evaluar
  * @param b: double 2 a evaluar
  * @return: String resultado de la comparación (Nil = No es menor; T = Si es menor)
  */
  public String Menorque(double a, double b)
  {
    String estado="Nil";
    if(a<b)
    estado="T";

    return estado;
  }

  /**
  * Método Cond: Evalua condicionales en una expresion
  * @param condicion: Expresion que contiene algun condicional
  * @return estado: String del resultado para la expresión
  */
  public String Cond(String condicion)
  {
    
    if(condicion.trim().equalsIgnoreCase("t"))
          return "t";
      if(condicion.trim().equalsIgnoreCase("nil"))
          return "Nil";
    
    /**
    * String del estado default del resultado
    */
    String estado="Error en la condicion";

    /**
    * try-catch como programación segura
    * Atrapa cualquier tipo de excepcion que se de por ingreso erróneo de datos
    */
    try
    {
    
    /**
    * Se realiza un split para obtener ambos valores de la condicion
    */
    String comparar[]= condicion.split(" ");
    double a= checksetq(comparar[1]);
    double b= checksetq(comparar[2]);

    /**
    * Switch-case para comprobar que caso utilizar de acuerdo al condicional utilizado
    */
    switch(comparar[0])
      {
        case "<": 
          estado=Menorque(a,b);
        break;
        case ">":
          estado=Mayorque(a,b);
        break;
        case "=":
          estado=Equal(a,b);
        break;
      }
    }
    catch(Exception e)
    {
      System.out.print(estado);
    }

    return estado;
  }

  /**
  * Método checksetq: Comprueba que un valor se encuentre en el stack de valores Setq
  * @param temp: String del valor que se desea encontrar
  * @return valor: Double que corresponde al valor que se buscaba
  */
  private double checksetq(String temp)
  {
    double valor;
    
    /**
    * Se verifica si el valor existe, de no existir se asigna
    * De lo contrario se retorna lo que se encontró
    */ 
    if(SetqValores.get(temp)==null)
    {
      
      valor=Double.parseDouble(temp);
      
    }
    else
    {
      valor=Double.parseDouble(SetqValores.get(temp));
    }

    return valor;
  }
  
}