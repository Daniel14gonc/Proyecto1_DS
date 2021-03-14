/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */
 
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;

public class Consola {
  private Scanner scan;
  private Interprete interprete;

  public Consola(){
    scan = new Scanner(System.in);
    interprete = new Interprete();
  }
  
  public void Start(){

    System.out.println();
    while(true){
      System.out.print("CL-USER > ");
      String input = scan.nextLine();
      if(input.contains("txt")){
        ArrayList<String> operaciones = leerArchivo(input);
        String temporal = ArchivoToString(operaciones);
        System.out.print("\nResultados: \n");
        for(String da : operaciones){
          interprete.interpretar(da);
        }
        /*System.out.println();
        System.out.println();
        System.out.println(temporal);
        System.out.println();
        System.out.println();*/

      }
      else {
        interprete.interpretar(input);
      }
    }
  }

  public ArrayList<String> leerArchivo(String nombreArchivo){

    ArrayList<String> Info = new ArrayList<String>();

    try {

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

  public static String ArchivoToString(ArrayList<String> Lista){
    String Acumulador = "";
        
    for(String a: Lista){
      Acumulador += (a + "\n");
    }
    Acumulador = Acumulador.trim();
    return Acumulador;
  }

  
}


