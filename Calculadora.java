/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 */

 /**
 *Import de libreria de java
 */
import java.util.*;

/**
 * @author Daniel Gonzalez 20293
 * Modificacion: 02/24/2021
 * Clase: Calculadora
 *
 * Descripcion:
 *  Clase que permite implementar las acciones de una calculadora
 *  para expresiones matematicas. Implementa una interfaz.
 */

public class Calculadora implements CalculadoraGeneral{

    /**
    * Atributos de la clase
    * stack como almacen, nums y ops como parte
    * de la operación y instance como la calculadora
    */
    private Stack<Double> stack;
    private String nums;
    private String ops;
    private static CalculadoraGeneral instance;

    /**
    * Constructor de la clase
    * Se establece cuales son los numeros y operadores permitidos
    * para realizar las operaciones
    */
    private Calculadora(){
        //Post: Crear un vector que almacene los resultados de las operaciones. Crear strings
        //      para determinar si el string es operador u operadno.
        nums = "0123456789";
        ops = "+-*/^";
    }

    /**
    * Patrón de diseño Singleton
    * Se comprueba la existencia de una única instancia 
    * de calculadora
    * @return: Instancia de calculadora
    */
    public static CalculadoraGeneral singletonCalculadora(){
      /**
      * Si no es encuentra ninguna instancia de calculadora
      * se crea una
      */
      if(instance == null){
        instance = new Calculadora();
        return instance;
      }
      return instance;
    }

    /**
    * Método que realiza el cálculo de una expresión
    * Override de la interfaz CalculadoraGeneral
    * @param: String que contiene la expresion a calcular
    * @return: String que contiene el resultado de la operacion
    */
    @Override
    public String Calculo(String expresion) throws ArithmeticException, Exception{
        /**
        * Pre: Validar que se haya ingresado un numero.
        * Post: Determinar si se debe almacenar el numero en el vector o * se debe hacer una operacion.
        */
        stack = new Stack<Double>();
        try {
            String res = "";
            for(int i =0; i< expresion.length(); i++){
                /**
                * Obtener cada caracter del string.
                */
                Character op = expresion.charAt(i);
                /**
                * Determinar si es un operador, operando o un termino no valido.
                */
                if(nums.contains(Character.toString(op)) || op.equals('.')){
                    res += op;
                }
                else if(ops.contains(Character.toString(op))){
                  postFixEvaluation(op);
                }
                else if(Character.toString(op).isBlank() && !res.isEmpty()){
                  stack.push(Double.parseDouble(res));
                  res = "";
                }
                else{
                  return "Error: No se pudo realizar la operacion por invalidez de simbolos";
                }
            }

            /**
            * Al finalizar, se debe validar que el stack tenga un solo dato dentro, el cual sera
            * probablemente el resultado de la operacion.
            */

            if(stack.empty() || stack.size() > 1){
                return "Error: Expresion invalida";
            }
            else{
                return stack.pop().toString();
            }
        }
        catch (ArithmeticException e){

            throw new ArithmeticException(e.getMessage());
        }
        catch(Exception e){
          throw new Exception(e.getMessage());
        }


    }

    /**
    * Metodo que permite realizar las operaciones determinadas por 
    * los operadores de la expresion postfix.
    * @param: char que representa un operador y determina la operacion a realizar
    * @return: boolean que muestra si se realizó la operación o no
    */
    private boolean postFixEvaluation(char operator) throws ArithmeticException, Exception{
        /**
        * Pre: Validar que el stack no este vacio y que los operandos sean numeros.
        * Post: Realizar la operacion indicada
        */
        try{
            if(!stack.empty()){
                /** 
                * Se obtienen los dos operandos mas recientes del stack. 
                */
                Double op2 = stack.pop();
                Double op1 = stack.pop();

                /**
                * Se determina la operacion por hacer.
                */
                if(op1 != null && op2 != null){
                    switch (operator){
                        case '+':
                            stack.push(op1 + op2);
                            break;
                        case '-':
                            stack.push(op1 - op2);
                            break;
                        case '*':
                            stack.push(op1 * op2);
                            break;
                        case '/':
                            /**
                            * Se asegura que la division no sea por 0
                            */
                            if(Double.isNaN(op1/op2) || Double.isInfinite(op1/op2)){
                              String ex = "Error: Division por 0";
                              throw new Exception(ex);
                            }
                            stack.push(op1 / op2);
                            break;
                    }
                    return true;
                }
                else
                    return false;
            }
            else{
                return false;
            }
        }
        /**
        * Se asegura que la division no sea por 0
        */
        catch (ArithmeticException e){
            String ex = "Error: Division por 0";
            throw new ArithmeticException(ex);
        }
    }

}
