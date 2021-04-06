/**
 * @author Daniel Gonzalez
 * Carné: 20293
 * Modificacion: 01/31/2021
 * Interfaz: CalculadoraGeneral
 *
 * Descripcion:
 *  Interfaz que permite determinar las operaciones de una calulcadora.
 */

public interface CalculadoraGeneral {

    /**Metodo que permite evaluar expresiones en postfix.
    * @param: String que contiene una operacion a calcular
    */
    public String Calculo(String expresion) throws Exception;
}