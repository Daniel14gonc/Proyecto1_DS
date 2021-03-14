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
  private CalculadoraGeneral calculadora;
  private Converter converter;

  public Interprete(){
    
  }

  public String interpretar(String expresion){
    String result = parse(expresion);
    if(result.equals("error"))
      return result;

    try{
      System.out.println(evaluar(operations));
    }
    catch(Exception e){
      
    }

    return "";
  }

  
  public String evaluar(List<Object> ListEvaluar) throws Exception{
    try{
      String acum="";
      calculadora = Calculadora.singletonCalculadora();
      converter = Converter.gConverter();
      String res;
      for(int i =0;i<ListEvaluar.size();i++)
      {
        if(ListEvaluar.get(i) instanceof String )
        {
          acum+=(String) ListEvaluar.get(i);
        }
        else if(ListEvaluar.get(i) instanceof Object)
        {
          if(ListEvaluar.get(i) instanceof List)
          {
            acum+= evaluar((List<Object>)ListEvaluar.get(i));
          }
        }


        /*
        switch(acum) 
        {
          case acum.Trim.Substring(0.4)== "defun":
            Funcion funcion = new Funcion();
          default:
            res = converter.ConverterPrePos(acum);
          break;
        }*/

      }
    //Mientras tanto que retorne esto 
    res = converter.ConverterPrePos(acum);
    return calculadora.Calculo(res);
    }
    catch(Exception e){
      throw new Exception(e.getMessage());
    }
  }


  private String parse(String expresion){
    String resultado = "";
    parentesis = new Stack<String>();
    operations = new LinkedList<Object>(); 
    String temp = deleteComments(expresion);
    temp = temp.replaceAll("\\(", "\\ ( ");
    temp = temp.replaceAll("\\)", "\\ ) ");
    temp = temp.replaceAll("\\-", "\\ - ");
    temp = temp.replaceAll("\\*", "\\ * ");
    temp = temp.replaceAll("\\/", "\\ / ");
    Scanner scan = new Scanner(temp);
    operations = parseExpresion(scan);
    return "done";
  }

  private String deleteComments(String expresion){
    Scanner scan = new Scanner(expresion);
    String result = "";
    while(scan.hasNextLine()){
      String tempLine = scan.nextLine().trim().strip().substring(0, 1);
      if(!tempLine.trim().contains(";")){
        result += tempLine;
      }
    }
    
    return result;
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
      /*
      else if(temp.equals("defun"))
      {
        crearFuncion(tempScan);
      }*/
      else if(!temp.isBlank()){
        tempList.add(temp);
      }
    }
    return tempList;
  }

/*
  public void crearFuncion(Scanner tempScan)
  {
    int cont = 0;
    Funcion funcion = new Funcion();
    String temp = tempScan.next();
    boolean funcCreated = false;
    while(!funcCreated){
      if(!temp.isBlank()){
        if(cont == 0){
          funcion.setNombre(temp);
          cont++;
        }
      }
    }
    
    funciones.put(funcion.getNombre(), funcion);
  }*/
}