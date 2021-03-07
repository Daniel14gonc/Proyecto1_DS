/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */
 import java.util.*;

public class Interprete {
  
  private Stack<String> parentesis;
  private HashMap<String, Funcion> funciones;
  private List<Object> operations;

  public Interprete(){
    
  }

  public String interpretar(String expresion){
    String result = parse(expresion);
    if(result.equals("error"))
      return result;

    return "";
  }

  public String evaluar(){
    return "";
  }

  private String parse(String expresion){
    String resultado = "";
    parentesis = new Stack<String>();
    operations = new LinkedList<Object>(); 
    Scanner scan = new Scanner(expresion);
    while(scan.hasNextLine()){
      String tempLine = scan.nextLine().trim();
      if(!tempLine.trim().substring(0, 1).equals(";"))
      {
        tempLine = tempLine.replaceAll("\\(", "\\ ( ");
        tempLine = tempLine.replaceAll("\\)", "\\ ) ");
        tempLine = tempLine.replaceAll("\\+", "\\ + ");
        tempLine = tempLine.replaceAll("\\-", "\\ - ");
        tempLine = tempLine.replaceAll("\\*", "\\ * ");
        tempLine = tempLine.replaceAll("\\/", "\\ / ");
        Scanner tempScan = new Scanner(tempLine);
        operations = parseExpresion(tempScan);
        System.out.println(operations);
      }
    }

    return "done";
  }

  private List<Object> parseExpresion(Scanner tempScan){
    List<Object> tempList = new LinkedList<Object>();
    while(tempScan.hasNext()){
      String temp = tempScan.next().trim().strip();
      if(temp.equals("(")){
        parentesis.push(temp);
        tempList.add(parseExpresion(tempScan));
      }
      else if(temp.equals(")")){
        parentesis.pop();
        return tempList;
      }
      else if(!temp.isBlank()){
        tempList.add(temp);
      }
    }
    return tempList;
  }



  public void crearFuncion(){

  }
}