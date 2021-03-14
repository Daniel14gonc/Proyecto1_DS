import java.util.*;

public class Converter {

  private static Converter converterInstance;

  private Converter() {

  }

  public static Converter gConverter() {
    if (converterInstance == null) {
      converterInstance = new Converter();
    }

    return converterInstance;
  }

  public String ConverterPrePos(String Prefix) {

    Stack<String> almacen = new Stack<String>();

    try {
      for (int i = Prefix.length() - 1; i >= 0; i--) {
        if (Operadores(Prefix.charAt(i))) {

          String Signo1;
          String Signo2;

          Signo1 = almacen.peek();
          almacen.pop();
          Signo2 = almacen.peek();
          almacen.pop();

          String PostfixLine = Signo1 + Signo2 + Prefix.charAt(i);
          almacen.push(PostfixLine);

        } else {
          almacen.push(Prefix.charAt(i) + "");
        }
      }
      return almacen.peek();
    } catch (Exception e) {
      return "Expresión invalida";
    }
  }

  public static boolean Operadores(char Dato) {

    if (Dato == '+' || Dato == '-' || Dato == '*' || Dato == '/' || Dato == '^') {
      return true;
    }
    return false;
  }

  /*
   * public static void main(String[] args) {
   * 
   * String BPrueba = "+12*3/2"; System.out.println("\nOperacion en Prefix: " +
   * BPrueba); System.out.println("Operacion en Postfix: " +
   * ConverterPrePos(BPrueba) + "\n"); }
   */

}