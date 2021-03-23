import java.util.*;

public class Converter {

  private static Converter converterInstance;
  private String nums;

  private Converter() {
    nums = "0123456789";
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
      String res = "";
      for (int i = Prefix.length() - 1; i >= 0; i--) {
        if (Operadores(Prefix.charAt(i))) {

          String Signo1;
          String Signo2;

          Signo1 = almacen.peek();
          almacen.pop();
          Signo2 = almacen.peek();
          almacen.pop();

          String PostfixLine = Signo1 +" "+ Signo2 + " " + Prefix.charAt(i);
          almacen.push(PostfixLine);

        } 
        
        else if(nums.contains(Character.toString(Prefix.charAt(i)))) {
          res = Prefix.charAt(i) + res;
        }
        else if(Character.toString(Prefix.charAt(i)).isBlank() && !res.isEmpty()){
          almacen.push(res);
          res = "";
        }
      }
      return almacen.peek();
    } catch (Exception e) {
      return "Expresion invalida";
    }
  }

  public boolean Operadores(char Dato) {

    if (Dato == '+' || Dato == '-' || Dato == '*' || Dato == '/' || Dato == '^') {
      return true;
    }
    return false;
  }

}