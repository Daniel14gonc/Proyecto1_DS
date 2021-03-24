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
* Clase Predicados
* Maneja quote, Atom y Listp
*/
public class Predicados {

  /**
  * Atributos
  */
  public Comparable comp=new Comparable();
  private HashMap<String, String> SetqValores;
  
  public boolean evaluarPredicado(String expresion){

    boolean RespuestaFinal = false;
    return RespuestaFinal;

  }

  /**
  * Métpdo quote
  * Si se llama a este método, no se opera la expresion. Solo se muestra
  * @param cuota: Objeto que contiene la expresion
  * @return cuota: Retorna la misma expresion
  */
  public String quote(Object cuota)
  {
    System.out.println("En QUOTE retorno: '"+cuota);
    return "'"+cuota;
  }

  /**
  * Método Atom
  * Evalua si la expresion pertenece a los valores permitidos por LISP
  * @param expresion: String que contiene la expresion
  * @return String: Resultado. Nil si no pertenece y T si pertenece
  */
  public String Atom(String expresion)
  {
    //System.out.println(expresion);
    /**
    * Valores permitidos
    */ 
    System.out.println(expresion);
    String numeros="1234567890";
    SetqValores=comp.vardefinidas() ;
    String ResultadoAtom= "Nil";
    /**
    * Se separa la expresion y se evalua
    */ 
    String[] verificar=expresion.split("( )");
    //System.out.println(expresion.substring(0,1).contains(numeros));
    if(SetqValores.get(verificar[0])==null && !expresion.substring(0,1).equals("'") && !numeros.contains(expresion.substring(0,1)))
    {
      ResultadoAtom="- EVAL: variable "+expresion+" has no value";
    }else if(verificar.length==1)
    {
      ResultadoAtom="T\n";
    }

    return ResultadoAtom;
  }

  /**
  * Método listp
  * Evalua si la expresion tiene una lista
  * @param expresion: String que contiene la expresion a evaluar
  * @return String: Nil si no contiene lista, T si contiene lista
  */
  public String Listp(String expresion)
  {
    String resultadoListp="";
    String Resultado= Atom(expresion);
    if(Resultado.equals("Nil"))
    {
      resultadoListp="T";
    }else if(Resultado.equals("T"))
    {
      resultadoListp="Nil";
    }
    else
    {
      resultadoListp=Resultado;
    }
    
    return resultadoListp;
  }
}