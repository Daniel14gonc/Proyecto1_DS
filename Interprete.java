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
    funciones = new HashMap<String, Funcion>();
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
      
        
        switch(acum) 
        {
          case acum.trim.Substring(0.4)== "defun":
            Funcion funcion = new Funcion();
          default:
            res = converter.ConverterPrePos(acum);
          break;
        }

      }
      System.out.println(acum);
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
    temp = temp.replaceAll("\\+", "\\ + ");
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
      String tempLine = scan.nextLine().trim().strip();
      String temp = tempLine.substring(0, 1);
      if(!temp.trim().contains(";")){
        result += tempLine;
      }
    }
    //System.out.println(result);
    return result;
  }

  //El defun de este metodo
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
      else if(temp.equals("defun"))
      {
        crearFuncion(tempScan);
      }
      else if(!temp.isBlank()){
        tempList.add(temp);
      }
    }
    return tempList;
  }

  //Este metodo
  public String crearFuncion(Scanner tempScan)
  {
    int cont = 0;
    Stack<String> tempStack = new Stack<String>();
    Funcion funcion = new Funcion();
    boolean funcCreated = false;
    boolean funcBody = false;
    String contenido = ParseFuncion(tempScan);
    Scanner tScan = new Scanner(contenido);
    String body = "";
    while(!funcCreated && tScan.hasNext()){
      //System.out.println(cont);
      String temp = tScan.next();
      if(temp.equals("(")){
        tempStack.push(temp);
      }
      else if(temp.equals(")")){
        tempStack.pop();
      }
      if(!temp.isBlank()){
        if(cont == 0 && !temp.equals("(")){
          funcion.setNombre(temp);
          cont++;
        }
        else if(cont == 1){
          if(!temp.equals("(") && !temp.equals(")"))
            funcion.addParameter(temp);
          else if(temp.equals(")"))
            cont++;
        }
        else{
          if(!tempStack.empty()){
            body += " " + temp+" ";
          }
          else{
            funcion.setTasks(body);
            funcBody = true;
          }
        }

        if(funcBody && tempStack.empty()){
          funcCreated = true;
        }
      }
    }

    System.out.println(funcion.getActions());
    funciones.put(funcion.getNombre(), funcion);

    return "";
  }

  //Este metodo
  private String ParseFuncion(Scanner tempScan){
    Stack<String> tempStack = new Stack<String>();
    String res = "(";
    tempStack.push("(");
    boolean endFunc = false;
    while(tempScan.hasNext() && !endFunc){
      String next = tempScan.next();
      //System.out.println(next);
      if(next.equals("(")){
        tempStack.push(next);
      }
      else if(next.equals(")")){
        tempStack.pop(); 
      }
      res += " " + next + " ";

      if(tempStack.empty()){
        endFunc = false;
      }
    }

    return res;
  }
}