/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */
 
import java.util.*;

public class Comparable 
{
  private HashMap<String, String> SetqValores ;
  public Comparable(){
    SetqValores = new HashMap<String, String>();

  }

  

  public void Setq(String variable,String valor)
  {
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

  public String Equal(int a, int b)
  {
    String estado="Nil";

    if(a==b)
    estado="T";

    return estado;
  }

  public String Mayorque(int a, int b)
  {
    String estado="Nil";
    if(a>b)
    estado="T";

    return estado;
  }

  public String Menorque(int a, int b)
  {
    String estado="Nil";
    if(a<b)
    estado="T";

    return estado;
  }

  public String Cond(String condicion)
  {
    
    String estado="Error en la condicion";

    try
    {
    String comparar[]= condicion.split(" ");
    int a= checksetq(comparar[1]);
    int b= checksetq(comparar[2]);

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

  private int checksetq(String temp)
  {
    int valor;
    
    if(SetqValores.get(temp)==null)
    {
      
      valor=Integer.parseInt(temp);
      
    }
    else
    {
      valor=Integer.parseInt(SetqValores.get(temp));
    }

    return valor;
  }
  
}