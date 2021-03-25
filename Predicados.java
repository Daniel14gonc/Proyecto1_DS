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
      ResultadoAtom="T";
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
    if(Resultado.equalsIgnoreCase("nil"))
    {
      resultadoListp="T";
    }else if(Resultado.equalsIgnoreCase("t"))
    {
      resultadoListp="Nil";
      //System.out.println("entre");
    }
    else
    {
      resultadoListp=Resultado;
    }
    
    return resultadoListp;
  }

  public String Equalx(String a,String b) throws Exception
  {
    try{
      Object v1 = getValue(a);
      Object v2 = getValue(b);
      System.out.println(v1);
      System.out.println(v2);
      
      if(v1.equals(v2))
        return "T";
      else
        return "Nil";
    }
    catch(Exception e){
      throw new Exception(e.getMessage());
    }
  }

  private Object getValue(String value) throws Exception{
    try{
      if(value.trim().substring(0,1).equals("'")){
      return value;
      }
      else if(Comparable.isValue(value)){
        return Comparable.getValue(value);
      }
      else{
        
        return (Double.parseDouble(value));  
      }
    }
    catch(Exception e){
      throw new Exception("Invalidez de simbolos ingresados en equals.");
    }
  }

  public String menor(String a, String b)
  {
    try
    {
      
      Object v1=getValue(a);
      Object v2=getValue(b);
      String menor="Nil";

      if(v1 instanceof String && v2 instanceof String)
      {
        if(a.length() < b.length())
        {
          menor="T";
        }
      }
      else
      {
        
        if(Double.parseDouble(a)<Double.parseDouble(b))
        {
          menor="T";
        }
      }

      return menor;
    }
    catch(Exception e)
    {
      return"Invalidez en comparacion";
    }
  }

  public String mayor(String a, String b)
  {
    try
    {
      Object v1=getValue(a);
      Object v2=getValue(b);
      String menor="Nil";

      if(v1 instanceof String && v2 instanceof String)
      {
        if(a.length() > b.length())
        {
          menor="T";
        }
      }
      else
      {
        
        if(Double.parseDouble(a)>Double.parseDouble(b))
        {
          menor="T";
        }
      }

      return menor;
    }
    catch(Exception e)
    {
      return"Invalidez en comparacion";
    }
  }


}