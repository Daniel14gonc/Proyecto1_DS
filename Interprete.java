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
  private Comparable comparable;
  private Predicados predicados;
  private Stack<String> actualFunc;

  public Interprete(){
    funciones = new HashMap<String, Funcion>();
    comparable = new Comparable();
    predicados= new Predicados();
    actualFunc = new Stack<String>();
  }

  public String interpretar(String expresion) throws Exception{
    
    String result = parse(expresion);
    //System.out.println(operations);
    if(result.equals("error"))
      return result;

    try{
      System.out.println(operations.isEmpty());
      System.out.println(evaluar(operations));
    }
    catch(Exception e){
      throw new Exception(e.getMessage());
    }
    //System.out.print("Pase con exito interpretar \n");//ELIMINAR TRAS DEBUGUEAR
    return "";
  }

  public String evaluar(List<Object> ListEvaluar) throws Exception{
    try{
      //Incongruencia entre defun y operaciones
      if(!ListEvaluar.isEmpty())
      {
        boolean funcEval = false;
        System.out.println(ListEvaluar +" siuuu");
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
              actualFunc.push(temp);
              i++;
              String params = "";
              while(i < ListEvaluar.size()){
                if(ListEvaluar.get(i) instanceof List){
                  params += evaluar((List) ListEvaluar.get(i));
                }
                else if(ListEvaluar.get(i) instanceof String){
                  params += (String) ListEvaluar.get(i) + " ";
                }
                i++;

              }
              //System.out.println("ahhhh");
              System.out.println(params + " params");
              funciones.get(actualFunc.peek()).setParameters(params);
              String body = funciones.get(actualFunc.peek()).getTasks();
              List<Object> tempList = parseFunction(body);
              funcEval = true;
              acum += evaluar(tempList) + " ";
              //funcEval = false;
            }
            else if(!actualFunc.isEmpty() && funciones.get(actualFunc.peek()).isParameter(temp)){
              acum += funciones.get(actualFunc.peek()).getParameter(temp) + " ";
            }
            else{
              acum+= temp + " ";
            }
          }
          else if(ListEvaluar.get(i) instanceof Object)
          {
            if(ListEvaluar.get(i) instanceof List)
            {
              List<Object> tempList =(List) ListEvaluar.get(i);
              if(tempList.get(0).equals("cond")){
                int j = 1;
                boolean finish = false;
                while(j< tempList.size() && !finish){
                  if(((List) tempList.get(j)).get(0) instanceof List){
                    List<Object> tList = (List)((List)tempList.get(j)).get(0);
                    System.out.println(tList);
                    for(int a = 0; a< tList.size(); a++){
                      String temp = (String) tList.get(a);
                      System.out.println(temp);
                      if(!actualFunc.isEmpty() && funciones.get(actualFunc.peek()).isParameter(temp)){
                        tList.set(a, funciones.get(actualFunc.peek()).getParameter(temp));
                      }
                    }
                    if(comparable.Cond(convert(tList)).equalsIgnoreCase("t")){
                      acum += evaluar((List)((List)tempList.get(j)).get(1)) + " ";
                      finish = true;
                    }
                  
                    j+=1;
                  }
                  else if(((List) tempList.get(j)).get(0) instanceof String){
                    String temp = (String)((List)tempList.get(j)).get(0);
                    if(temp.equals("t") && j == tempList.size()-1){
                      acum += evaluar((List)((List)tempList.get(j)).get(1)) + " ";
                      finish = true;
                    }
                  }
                }
              }
              /*
              else if(((String)tempList.get(0)).equalsIgnoreCase("quote"))
              {
                System.out.println( predicados.quote(tempList.get(1)));
              }
              else if(tempList.get(0).equals("setq"))
              {
                comparable.Setq((String)tempList.get(1), (String)tempList.get(2));
              }
              else if(((String)tempList.get(0)).equalsIgnoreCase("atom"))
              {
                System.out.println( predicados.Atom((String)tempList.get(1)));
              }*/
              else
              {
                acum+= evaluar((List<Object>)ListEvaluar.get(i)) + " ";
              }
            }

          }
        }

        if(funcEval){
          funciones.get(actualFunc.peek()).deleteValues();     
          actualFunc.pop();
          funcEval = false;
        }
        System.out.println(acum);
        if(acum.trim().contains(" ")){
          res = converter.ConverterPrePos(acum);
          //System.out.println(res);
          return calculadora.Calculo(res);
        }
        else
          return acum.replaceAll("\\s","");

      }
      return "";

    }
    catch(Exception e){
      throw new Exception(e.getMessage());
    }
  }



  private String convert(List<Object> condition){
    String res = "";
    for(int i = 0; i< condition.size(); i++){
      res += condition.get(i) + " ";
    }
    return res;
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

        //Sigue en proceso, tiene algunas fallas
      } /*else if(temp.equalsIgnoreCase("quote") || temp.equals("'"))
      {

        String conte = ParsePredicados(tempScan);
        conte=predicados.quote(conte);
        System.out.print(conte);
        
      }
      else if(temp.equalsIgnoreCase("atom"))
      {
        System.out.print(predicados.Atom(ParsePredicados(tempScan)));
      }*/
      
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

  private String ParsePredicados(Scanner tempScan)
  {
    String next="";
    while(tempScan.hasNext())
    {
      System.out.println("Esto es next: "+next+"\n");
      next+= tempScan.next();
    }
    return next;
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

  private void parseCond(Scanner tempScan){

  }

  /*private String ContenidoObtenido(Scanner tempScan){
    Stack<String> tempStack = new Stack<String>();
    String res = "";
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
      res += next + " ";

      if(tempStack.empty()){
        endFunc = true;
      }
    }
    return res;
  }*/

}