import java.util.*;

/**
 * @author Daniel Gonzalez 20293
 * Modificacion: 02/24/2021
 * Clase: StackVector
 *
 * Descripcion:
 *  Clase que permite implementar las acciones de una calculadora
 *  para expresiones matematicas.
 */

public class Calculadora implements CalculadoraGeneral{

    private Stack<Integer> stack;
    private String nums;
    private String ops;
    private static CalculadoraGeneral instance;

    private Calculadora(){
        //Post: Crear un vector que almacene los resultados de las operaciones. Crear strings
        //      para determinar si el string es operador u operadno.
        nums = "0123456789";
        ops = "+-*/";
    }

    public static CalculadoraGeneral singletonCalculadora(){
      if(instance == null){
        instance = new Calculadora();
        return instance;
      }
      return instance;
    }

    @Override
    public String Calculo(String expresion) throws ArithmeticException {
        //Pre: Validar que se haya ingresado un numero.
        //Post: Determinar si se debe almacenar el numero en el vector o se debe hacer una operacion.
        stack = new Stack<Integer>();
        try {
            for(int i = 0; i<expresion.length(); i++){
                //Obtener cada caracter del string.
                char op = expresion.charAt(i);
                //Determinar si es un operador, operando o un termino no valido.
                if(nums.contains(Character.toString(op))){
                    stack.push(Character.getNumericValue(op));
                }
                else if(ops.contains(Character.toString(op))){
                    postFixEvalution(op);
                }
                else if(op != ' '){
                    return "Error: No se pudo realizar la operacion por invalidez de simbolos";
                }
            }

            //Al finalizar, se debe validar que el stack tenga un solo dato dentro, el cual sera
            //probablemente el resultado de la operacion.

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


    }

    /**Metodo que permite realizar las operaciones determinadas por los operadores
     * de la expresion postfix.*/
    private boolean postFixEvalution(char operator) throws ArithmeticException{
        //Pre: Validar que el stack no este vacio y que los operandos sean numeros.
        //Post: Realizar la operacion indicada
        try{
            if(!stack.empty()){
                //Se obtienen los dos operandos mas recientes del stack.
                Integer op2 = stack.pop();
                Integer op1 = stack.pop();

                //Se determina la operacion por hacer.
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
        catch (ArithmeticException e){
            String ex = "Error: Division por 0";
            throw new ArithmeticException(ex);
        }
    }

}
