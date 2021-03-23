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
      //System.out.println(operations.isEmpty());
      System.out.println(evaluar(operations));
    }
    catch(Exception e){
      
    }
    //System.out.print("Pase con exito interpretar \n");//ELIMINAR TRAS DEBUGUEAR
    return "";
  }




  public String evaluar(List<Object> ListEvaluar) throws Exception{
    try{
      //Incongruencia entre defun y operaciones
      if(!ListEvaluar.isEmpty())
      {
        String acum = "";
        calculadora = Calculadora.singletonCalculadora();
        converter = Converter.gConverter();
        String res;
        for(int i =0;i<ListEvaluar.size();i++)
        {        
          if(ListEvaluar.get(i) instanceof String)
          {
            String temp = (String)ListEvaluar.get(i);
            if(funciones.containsKey(temp)){
              String body = funciones.get(temp).getTasks();
              List<Object> tempList = parseFunction(body);
              acum += evaluar(tempList);
            }
            else
              acum+= temp + " ";
          }
          else if(ListEvaluar.get(i) instanceof Object)
          {
            if(ListEvaluar.get(i) instanceof List)
            {
              acum+= evaluar((List<Object>)ListEvaluar.get(i)) + " ";
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
        if(acum.trim().contains(" ")){
          res = converter.ConverterPrePos(acum);
          //System.out.println(res);
          return calculadora.Calculo(res);
        }
        else
          return acum;

      }
      return "";

    }
    catch(Exception e){
      throw new Exception(e.getMessage());
    }
  }

  private List<Object> parseFunction(String body){
    Scanner temp = new Scanner(body);
    return parseExpresion(temp);
  }


  private String parse(String expresion){
    String resultado = "";
    parentesis = new Stack<String>();
    operations = new LinkedList<Object>(); 
    String temp = deleteComments(expresion);
    //System.out.print("Pase con exito eliminar comentarios \n");//ELIMINAR TRAS DEBUGUEAR
    temp = temp.replaceAll("\\(", "\\ ( ");
    temp = temp.replaceAll("\\)", "\\ ) ");
    temp = temp.replaceAll("\\+", "\\ + ");
    temp = temp.replaceAll("\\-", "\\ - ");
    temp = temp.replaceAll("\\*", "\\ * ");
    temp = temp.replaceAll("\\/", "\\ / ");
    temp = temp.replaceAll("\\^", "\\ ^ ");
    Scanner scan = new Scanner(temp);
    operations = parseExpresion(scan);

    CheckList(operations);
    //System.out.print("Pase con exito Parse \n");//ELIMINAR TRAS DEBUGUEAR
    return "done";
  }

  private String deleteComments(String expresion){
    Scanner scan = new Scanner(expresion);
    String result = "";
    while(scan.hasNextLine()){
      String tempLine = scan.nextLine().trim().strip();
      if(!tempLine.isEmpty()){
        String temp = tempLine.substring(0, 1);
        if(!temp.trim().contains(";"))
        {
          result += tempLine;
        }
      }
    }
    return result;
  }

  //El defun de este metodo
  private List<Object> parseExpresion(Scanner tempScan){
    List<Object> tempList = new LinkedList<Object>();
    while(tempScan.hasNext()){
      String temp = tempScan.next().trim().strip(); 
      //System.out.println(temp);
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
        return tempList;
      }
      else if(!temp.isBlank()){
        tempList.add(temp);
      }
    }
    return tempList;
  }

  private void CheckList(List<Object> Check)
  {
    //System.out.println(Check);
    for(int i=0;i<Check.size();i++)
    {
      if(Check.get(i) instanceof List && ((List)Check.get(i)).isEmpty()){
        Check.remove(i);
      }
      else if(Check.get(i) instanceof List){
        CheckList((List)Check.get(i));
      }
    }
  }



  //Lista []
  //Este metodo
  public void crearFuncion(Scanner tempScan)
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

    //System.out.println(funcion.getActions());
    funciones.put(funcion.getNombre(), funcion);
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
        endFunc = true;
      }
    }

    return res;
  }
}