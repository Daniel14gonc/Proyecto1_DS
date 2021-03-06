/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */
 
import java.util.HashMap;
import java.util.Stack;
import java.util.Scanner;

public class Interprete {
  
  private Stack<String> datos;
  private HashMap<String, Funcion> funciones;

  public Interprete(){

  }

  public void interpretar(String expresion){
    String res = parse(expresion);
    System.out.println(res);
  }

  public String evaluar(){
    return "";
  }

  private String parse(String expresion){
    String resultado = "";
    datos = new Stack<String>();  
    Scanner scan = new Scanner(resultado);
    while(scan.hasNextLine()){
      String tempLine = scan.nextLine();
      if(!tempLine.trim().substring(0, 1).equals(";"))
      {
        Scanner tempScan = new Scanner(tempLine);
        while(tempScan.hasNext()){
          String temp = tempScan.next();
          if(temp == "(")
            datos.push(temp);
          else if(temp == ")")
            datos.pop();
          else if(temp != " ")
            resultado += temp;
        }
      }

    }

    return resultado;
  }

  public void crearFuncion(){

  }
}