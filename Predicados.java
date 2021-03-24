/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */
import java.util.*;
public class Predicados {

  public Comparable comp=new Comparable();
  private HashMap<String, String> SetqValores;
  
  public boolean evaluarPredicado(String expresion){

    boolean RespuestaFinal = false;
    return RespuestaFinal;

  }
  public String quote(Object cuota)
  {
    return "'"+cuota;
  }

  public String Atom(String expresion)
  {
    System.out.println(expresion);
    String numeros="1234567890";
    SetqValores=comp.vardefinidas() ;
    String ResultadoAtom= "Nil";
    String[] verificar=expresion.split("( )");
    System.out.println(expresion.substring(0,1).contains(numeros));
    if(SetqValores.get(verificar[0])==null && !expresion.substring(0,1).equals("'") && !numeros.contains(expresion.substring(0,1)))
    {
      ResultadoAtom="- EVAL: variable "+expresion+" has no value";
    }else if(verificar.length==1)
    {
      ResultadoAtom="T\n";
    }

    return ResultadoAtom;
  }

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