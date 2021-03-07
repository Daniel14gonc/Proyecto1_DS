/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */
 
public class Evaluar implements Operable, Comparable{

  public String evaluacion(String expresion)
  {
    return"";
  }

  public float Suma(String Dato1, String Dato2){
    float n1 = (float)Dato1;
    float n2 = (float)Dato2;
    return  n1+n2;
  }

  public float Resta(String Dato1, String Dato2){
    float n1 = (float)Dato1;
    float n2 = (float)Dato2;
    return  n1-n2;
  } 

  public float Multiplicacion(String Dato1, String Dato2){
    float n1 = (float)Dato1;
    float n2 = (float)Dato2;
    return  n1*n2;
  }  

  public float Division(String Dato1, String Dato2){
    float n1 = (float)Dato1;
    float n2 = (float)Dato2;
    return  n1/n2;
  }  

  public String Equal(){
    String Equa = "";
    return Equa;
  }

  public String Mayorque(){
    String Mayor = "";
    return Mayor;
  }

  public String Menorque(){
    String Menor = "";
    return Menor;
  }


}