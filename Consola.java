/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 */

/**
 *Import de librerias de java
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
* Clase Consola
* Encargada de manejar la interacción con el usuario
*/
public class Consola {

  /**
  * Atributos
  * Scanner para la obtención de datos e interprete para evaluar las expresiones de LISP
  */
  private Scanner scan;
  private Interprete interprete;

  /**
  * Constructor
  * Instancia de nuevo scanner e interprete
  */
  public Consola(){
    scan = new Scanner(System.in);
    interprete = new Interprete();
  }
  
  /**
  * Método Start
  * Inicia el intérprete
  */
  public void Start(){
    System.out.println("\n\t\t-----> Interprete de LISP <-----\n");

    /**
    * Ciclo While que simula la consola del interprete de LISP 
    */ 
    boolean verificador = true;
    while(verificador){
      /**
      * Try-catch para evitar errores por ingreso de datos incorrectos
      */
      try{
        System.out.print("CL-USER > ");
        /**
        * Si se detecta un archivo .txt o .lisp se lee su contenido 
        * y se convierte en un String acumulador que posteriormente es evaluado
        */
        String input = scan.nextLine();
        if(input.contains("txt")){
          ArrayList<String> operaciones = leerArchivo(input);
          String temporal = ArchivoToString(operaciones);
          interprete.interpretar(temporal);        
        } else if (input.contains("lisp")){
          ArrayList<String> operaciones = leerArchivo(input);
          String temporal = ArchivoToString(operaciones);
          interprete.interpretar(temporal); 

        /**
        * Si detecta que el usuario quiere salir termina el ciclo While
        */
        } else if (input.equals("Exit") || input.equals("exit")
        || input.equals("salir") || input.equals("Salir")){
          verificador = false;
        }
        /**
        * De no ser un archivo, evalua la linea ingresada
        */
        else {
          interprete.interpretar(input);
        }
      }
      catch(Exception e){
        System.out.println(e.getMessage());
      } 
    }
  }

  /**
  * Método leerArchivo
  * Se lee el contenido del archivo y se guarda en un ArrayList
  * @param nombreArchivo: Recibe el nombre del archivo 
  * @return: Arraylist con las lineas del archivo
  */
  public ArrayList<String> leerArchivo(String nombreArchivo){

    /**
    * Se crea un Arraylist que almacenara cada linea del archivo
    */
    ArrayList<String> Info = new ArrayList<String>();

    /**
    * Try-catch como medida de seguridad por si en dado caso el archivo no existe
    */ 
    try {

      /**
      * Se lee cada linea del archivo y se agrega al ArrayList
      */
      File Archivo = new File(nombreArchivo);
      Scanner Lector = new Scanner(Archivo);

      while(Lector.hasNextLine()){
        String Line = Lector.nextLine();
        Info.add(Line);
      }

    } catch (Exception e){
      System.out.println("Error! Archivo no encontrado");
      e.printStackTrace();
    }

    return Info;
  }

  /**
  * Método ArchivoToString
  * Se convierte en un String el ArrayList de un archivo
  * @param Lista: Arraylist que contiene las lineas de un archivo
  * @return Acumulador: String que contiene toda la información del archivo
  */
  public static String ArchivoToString(ArrayList<String> Lista){
    /**
    * String vacío para acumular las lineas del archivo
    */
    String Acumulador = "";
    
    /**
    * Por cada linea del Arraylist se agrega la misma al Acumulador con un salto de
    * linea 
    */
    for(String a: Lista){
      Acumulador += (a + "\n");
    }
    Acumulador = Acumulador.trim();
    return Acumulador;
  }
}


