/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 */

/**
 *Import de librerias de java
 */
import java.util.*;

/**
* Clase converter 
* Realiza la conversion de expresiones Prefix a Postfix
*/
public class Converter {

  /**
  * Atributos
  */
  private static Converter converterInstance;
  private String nums;

  /**
  * Constructor
  * Se establece un String de los numeros a utilizar
  */
  private Converter() {
    nums = "0123456789";
  }

  /**
  * Singleton para comprobar la existencia de una
  * única instancia de converter
  */
  public static Converter gConverter() {
    /**
    * Si no existe una instancia se crea
    */
    if (converterInstance == null) {
      converterInstance = new Converter();
    }

    return converterInstance;
  }

  /**
  * Método ConverterPrePos
  * Realiza la conversion de una expresion en formato Prefix a formato Postfix
  * @param Prefix: String de la expresion en formato Prefix
  * @return Postfix: String de la expresion en formato Postfix
  */
  public String ConverterPrePos(String Prefix) {
    /**
    * Stack para almacenar caracteres de la expresion
    */
    Stack<String> almacen = new Stack<String>();

    /**
    * Try-catch para evitar errores por ingreso de datos incorrectos
    */
    try {
      /**
      * Se evalua si el caracter es un operador u operando
      * Luego de diferenciar estos dos tipos se crea la expresion en formato Postfix
      * y se almacena en el Stack
      */
      String res = "";
      boolean number = false;
      for (int i = Prefix.length() - 1; i >= 0; i--) {
        if (Operadores(Prefix.charAt(i)) && i == 0) {

          String Signo1;
          String Signo2;
          
          if(almacen.peek().equals("-"))
          Signo1 = almacen.pop()+almacen.pop();
          else
          Signo1 = almacen.pop();

          if(almacen.peek().equals("-"))
          Signo2 = almacen.pop()+almacen.pop();
          else
          Signo2 = almacen.pop();

          
          

          


          String PostfixLine = Signo1 +" "+ Signo2 + " " + Prefix.charAt(i);
          almacen.push(PostfixLine);

        } 

        //+ - 5 5
        
        
        /**
        * Se evalua que no existan puntos o espacios vacios en la expresion
        * para evitar errores al momento de calcular
        */
        else if(nums.contains(Character.toString(Prefix.charAt(i))) || Character.toString(Prefix.charAt(i)).equalsIgnoreCase(".") || Operadores(Prefix.charAt(i))){
          res = Prefix.charAt(i) + res;
        }
        else if(Character.toString(Prefix.charAt(i)).isBlank() && !res.isEmpty()){
          almacen.push(res);
          res = "";
        }
      }
      return almacen.peek();
      
    } catch (Exception e) {
      return "Expresion invalida";
    }
  }

  /**
  * Método Operadores
  * @param Dato: char proveniente de la expresion Prefix
  * @return boolean: true si el char es un operador, false si no lo es
  */
  public boolean Operadores(char Dato) {

    if (Dato == '+' || Dato == '-' || Dato == '*' || Dato == '/' || Dato == '^') {
      return true;
    }
    return false;
  }

}