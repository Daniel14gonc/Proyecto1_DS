/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 * @version 1
 */

import java.util.*;
public class Predicados {
  
  public boolean evaluarPredicado(String expresion){

    boolean RespuestaFinal = false;
    return RespuestaFinal;

  }
  private boolean AtomP(String expresion){

    boolean ResultadoAtom;
    boolean listV = false;
    boolean atomV = false;

    if(atomV){
      //T
      ResultadoAtom = true;
    } else if (atomV && listV){
      System.out.println("No es posible evaluar la expresion");
      ResultadoAtom = false;
    } else {
      //Nil
      ResultadoAtom = false;
    }

    return ResultadoAtom;

  }
  private boolean equals(){

    boolean ResultadoEquals = false;
    return ResultadoEquals;

  }
  private boolean mayor(){

    boolean ResultadoMayor = false;
    return ResultadoMayor;

  }
  private boolean menor(){

    boolean ResultadoMenor = false;
    return ResultadoMenor;
  }


}