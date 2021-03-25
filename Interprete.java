/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */
 import java.util.*;

/**
* Clase Interprete
* Evalua las expresiones, trabajando como interprete de LISP
*/
public class Interprete {
  
  /**
  * Atributos
  */
  private Stack<String> parentesis;
  private HashMap<String, Funcion> funciones;
  private List<Object> operations;
  private CalculadoraGeneral calculadora;
  private Converter converter;
  private Comparable comparable;
  private Predicados predicados;
  private Stack<String> actualFunc;
  private String ops;

  /**
  * Constructor
  */
  public Interprete(){
    funciones = new HashMap<String, Funcion>();
    comparable = new Comparable();
    predicados= new Predicados();
    actualFunc = new Stack<String>();
    ops = "/*-+";
  }

  /**
  * Método interpretar
  * Evalua si la expresion es válida, si está en formato LISP
  * @param expresion: String que contiene la expresion a evaluar
  * @return String: Resultado de la evaluacion
  */
  public String interpretar(String expresion) throws Exception{
    
    /**
    * Se utiliza el metodo parse para dividir la expresion
    * Si en dado caso no se puede, se retorna un error
    */
    

    /**
    * Try-catch para evitar errores durante la evaluación de expresiones
    */
    try{
      String result = parse(expresion);
      if(result.equals("error"))
        return result;
      CheckList(operations);
      if(!operations.isEmpty()){
        return evaluar(operations);
      }
      else{
        return "";
      }

      //System.out.println(operations.isEmpty());
      //System.out.println(evaluar(operations));
    }
    catch(Exception e){
      reset();
      throw new Exception(e.getMessage());
    }
    //return "";
  }

  private void reset(){
    funciones = new HashMap<String, Funcion>();
    comparable = new Comparable();
    predicados= new Predicados();
    actualFunc = new Stack<String>();
  }

  /**
  * Método evaluar
  * Verifica la expresión y en base a que contenga elige la operacion (Factory)
  * @param ListEvaluar: Lista que contiene la informacion de la expresion
  */
  public String evaluar(List<Object> ListEvaluar) throws Exception{
    /**
    * Try-catch como programación defensiva para evitar errores durante la evaluacion
    */
    try{
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
                  if((ops.contains((String) ListEvaluar.get(i))))
                    params += (String) ListEvaluar.get(i);
                  else
                    params += (String) ListEvaluar.get(i) + " ";
                }
                i++;

              }
              //System.out.println("ahhhh");
              //System.out.println(params + " params");
              if(!params.equals(""))
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
            else if(Comparable.isValue(temp)){
              acum += Comparable.getValue(temp) + " ";
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
                    //System.out.println(tList);
                    for(int a = 0; a< tList.size(); a++){
                      String temp = (String) tList.get(a);
                      //System.out.println(temp);
                      if(!actualFunc.isEmpty() && funciones.get(actualFunc.peek()).isParameter(temp)){
                        tList.set(a, funciones.get(actualFunc.peek()).getParameter(temp));
                      }
                    }
                    List<Object> temporal = new LinkedList<Object>();
                    temporal.add(tList);
                    String ev = evaluar(temporal);
                    if(comparable.Cond(ev).equalsIgnoreCase("t")){
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
              else if(tempList.get(0).equals("quote")||tempList.get(0).equals("'"))
              {
                acum = predicados.quote(tempList.get(1));
                System.out.println(acum);
                return(acum);
                //System.out.println( predicados.quote(tempList.get(1)));
              }
              else if(tempList.get(0).equals("setq"))
              {
                String setq = "";
                if(tempList.get(2) instanceof List){
                  List<Object> temporal = new LinkedList<Object>();
                  temporal.add((List) tempList.get(2));
                  setq += evaluar(temporal);
                }
                else if(tempList.get(1) instanceof String){
                  setq += (String)tempList.get(2);
                }
                comparable.Setq((String)tempList.get(1), setq);
                
              }
              else if(tempList.get(0).equals("<"))
              {
                String menor = "";
                String menor1 = "";
                if(tempList.get(1) instanceof List){
                  List<Object> temporal = new LinkedList<Object>();
                  temporal.add((List) tempList.get(1));
                  menor = evaluar(temporal);
                }
                if(tempList.get(2) instanceof List){
                  List<Object> temporal = new LinkedList<Object>();
                  temporal.add((List) tempList.get(2));
                  menor1 = evaluar(temporal);
                }
                if(tempList.get(1) instanceof String){
                  menor = (String)tempList.get(1);
                }
                if(tempList.get(2) instanceof String){
                  menor1 = (String)tempList.get(2);
                }
                return predicados.menor(menor,menor1);
              }
              else if(tempList.get(0).equals(">"))
              {
                String mayor = "";
                String mayor1 = "";
                if(tempList.get(1) instanceof List){
                  List<Object> temporal = new LinkedList<Object>();
                  temporal.add((List) tempList.get(1));
                  mayor = evaluar(temporal);
                }
                if(tempList.get(2) instanceof List){
                  List<Object> temporal = new LinkedList<Object>();
                  temporal.add((List) tempList.get(2));
                  mayor1 = evaluar(temporal);
                }
                if(tempList.get(1) instanceof String){
                  mayor = (String)tempList.get(1);
                }
                if(tempList.get(2) instanceof String){
                  mayor1 = (String)tempList.get(2);
                }
                return predicados.mayor(mayor,mayor1);
              }
              else if(tempList.get(0).equals("equal")||tempList.get(0).equals("="))
              {
                String equal = "";
                String equal1 = "";
                if(tempList.get(1) instanceof List){
                  List<Object> temporal = new LinkedList<Object>();
                  temporal.add((List) tempList.get(1));
                  equal = evaluar(temporal);
                }
                if(tempList.get(2) instanceof List){
                  List<Object> temporal = new LinkedList<Object>();
                  temporal.add((List) tempList.get(2));
                  equal1 = evaluar(temporal);
                }
                if(tempList.get(1) instanceof String){
                  equal = (String)tempList.get(1);
                }
                if(tempList.get(2) instanceof String){
                  equal1 = (String)tempList.get(2);
                }

                return predicados.Equalx(equal, equal1);
                //System.out.println(predicados.Equalx(equal, equal1));
                
              }
              else if(tempList.get(0).equals("atom"))
              {
                String atom = "";
                if(tempList.get(1) instanceof List){
                  List<Object> temporal = new LinkedList<Object>();
                  temporal.add((List) tempList.get(1));
                  atom += evaluar(temporal);
                }
                else if(tempList.get(1) instanceof String){
                  atom += (String)tempList.get(1);
                }
                return predicados.Atom(atom);
                //System.out.println(predicados.Atom(atom));
              }
              else if(tempList.get(0).equals("listp"))
              {
                String listp = "";
                if(tempList.get(1) instanceof List){
                  List<Object> temporal = new LinkedList<Object>();
                  temporal.add((List) tempList.get(1));
                  listp += evaluar(temporal);
                }
                else if(tempList.get(1) instanceof String){
                  listp += (String)tempList.get(1);
                }
                return predicados.Listp(listp); 
                //System.out.println(predicados.Listp(listp));
              }
              else
              {
                acum+= evaluar((List<Object>)ListEvaluar.get(i)) + " ";
              }
            }

          }
        }

        if(funcEval){
          if(!actualFunc.isEmpty()){
            funciones.get(actualFunc.peek()).deleteValues();     
            actualFunc.pop();
            funcEval = false;
          }
        }
        if(acum.trim().contains(" ")){
          res = converter.ConverterPrePos(acum);
          //System.out.println(res + "res");
          return calculadora.Calculo(res);
        }
        else
          return acum.replaceAll("\\s","");

      }
      return "";

    }
    catch(Exception e){
      //System.out.println("aqui");
      throw new Exception(e.getMessage());
    }
  }

  /**
  * Método convert
  * Evalua la condición y la separa por medio de un espacio en blanco
  * @param condition: Lista que contiene una expresión
  * @return String: resultado de esta separación
  */
  private String convert(List<Object> condition){
    String res = "";
    for(int i = 0; i< condition.size(); i++){
      res += condition.get(i) + " ";
    }
    return res;
  }

  /**
  * Método parseFunction
  * Evalua la expresión y separa la funcion. Separa el cuerpo.
  * @param body: String que contiene el cuerpo de la función
  * @return List<Object>: Retorna una lista con la funcion separada
  */
  private List<Object> parseFunction(String body) throws Exception{
    try{
      Scanner temp = new Scanner(body);
      return parseExpresion(temp);
    }
    catch(Exception e){
      throw new Exception(e.getMessage());
    }

  }

  /**
  * Método parse
  * Separa la expresion permitiendo evaluarla posteriormente
  * @param expresion: String que contiene la expresion a evaluar
  * @return String: Expresion separada para poder evaluarla
  */
  private String parse(String expresion) throws Exception{
    /**
    * Try-catch como programacion defensiva para evitar errores
    * durante la separacion de las expresiones
    */
    try{
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
      //CheckList(operations);
      //System.out.print("Pase con exito Parse \n");//ELIMINAR TRAS DEBUGUEAR
      return "done";
    }
    catch(Exception e){
      throw new Exception(e.getMessage());
    }
    
  }

  /**
  * Método deleteComments
  * Elimina cualquier comentario con formato LISP
  * @param expresion: String que contiene la expresion
  * @return String: Expresion sin comentarios
  */
  private String deleteComments(String expresion){
    Scanner scan = new Scanner(expresion);
    String result = "";
    /**
    * Ciclo While que busca ; en cada expresion y los
    * "elimina" dejando la expresion sin comentarios
    */
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

  /**
  * Método parseExpresion
  * Separación de la expresion completa
  * @param tempScan: Expresion completa
  * @return List<Object>: Expresion separada
  */
  private List<Object> parseExpresion(Scanner tempScan) throws Exception{
    /**
    * Try-catch como programacion defensiva para evitar errores
    * durante la separacion de las expresiones
    */
    try{
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
        /**
        *Si se encuentra la palabra defun se procede a crear una nueva funcion
        */
        else if(temp.equals("defun"))
        {
          crearFuncion(tempScan);
          return tempList; 
        } /*
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
    catch(Exception e){

      throw new Exception(e.getMessage());
    }

  }

  /**
  * Método CheckList
  * Se evalua la existencia de una lista en la expresion
  * @param Check: List<Object> que contiene la expresion a evaluar
  */
  private void CheckList(List<Object> Check)
  {
    int size = Check.size();
    if(!Check.isEmpty() && Check != null){
      for(int i=0;i<size;i++)
      {
        if(Check.get(0) instanceof List){
          if(((List)Check.get(0)).isEmpty()){
            Check.remove(0);            
          }
          else
            CheckList((List)Check.get(i));
        }
      }
    }
  }

  /**
  * Método crearFuncion
  * Creacion de una funcion que incluya nombre, parametros y cuerpo
  * @param tempScan: Expresion separada para ser evaluada
  */
  public void crearFuncion(Scanner tempScan) throws Exception
  {
    int cont = 0;
    Stack<String> tempStack = new Stack<String>();
    Funcion funcion = new Funcion();
    boolean funcCreated = false;
    boolean funcBody = false;
    String contenido = ParseFuncion(tempScan);
    Scanner tScan = new Scanner(contenido);
    String body = "";
    /**
    * Ciclo While para ir separando parte por parte la expresion
    * Posteriormente se junta la funcion por su nombre, parametros y cuerpo
    */
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
          if(funciones.containsKey(temp))
            throw new Exception("Error: La funcion " + temp + " ya existe");
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

  /**
  * Método ParsePredicados
  * Se separan los predicados de la expresion
  * @param tempScan: Expresion a evaluar
  */
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

  /**
  * Método ParseFuncion
  * Separacion de la funcion para poder ser evaluada por partes
  * @param tempScan: Scanner que contiene la expresion 
  * @return: Expresion con espacios en blanco para diferenciar las partes
  */
  private String ParseFuncion(Scanner tempScan){
    Stack<String> tempStack = new Stack<String>();
    String res = "(";
    tempStack.push("(");
    boolean endFunc = false;
    while(tempScan.hasNext() && !endFunc){
      String next = tempScan.next();
      /**
      * Si se encuentra un parentesis realiza una separacion
      * hasta encontrar el parentesis que lo concluye
      * Se obtiene el contenido y se concatena con un espacio en blanco
      * de por medio
      */
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

}