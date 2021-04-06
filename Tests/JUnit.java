/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 */

/**
 *Import de librerias de java
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias
 */
public class JUnit {
    
    /**
     * Instancias
     */
    Consola consola = new Consola();
    private Converter converter;
    Interprete interprete = new Interprete();
    private CalculadoraGeneral calculadora;
    Predicados predicados = new Predicados();
    Comparable comparador = new Comparable();

    /**
     * Test#1: Como lisp trabaja con operaciones prefix se decidio
     * realizar un conversor a operaciones postfix pues anteriormente habíamos trabajado
     * con estas anteriormente. Este primer test convierte las expresiones Prefix a Postfix
     * y posteriormente se calcula el resultado. Este también se comprueba puesto que se 
     * utilizaron doubles para mayor exactitud en los números
    */
    @Test
    public void ConversionPrePos_Evaluacion(){
        
        /**
         * Se instancian el conversor y la calculadora
         */
        converter = Converter.gConverter();
        calculadora = Calculadora.singletonCalculadora();

        /**
         * Se muestran las entradas Prefix y se realiza la conversión
         */
        System.out.println("\nEntrada Prefix: + 1 31.5");
        String cambio = converter.ConverterPrePos("+ 1 31.5");
        System.out.println("Entrada Prefix: * 3 5");
        String cambio2 = converter.ConverterPrePos("* 3 5");
        System.out.println("Entrada Prefix: - 4 8");
        String cambio3 = converter.ConverterPrePos("- 4 8");
        System.out.println("Entrada Prefix: / 9 3.5");
        String cambio4 = converter.ConverterPrePos("/ 9 3.5");

        /**
         * Se realiza un assertEquals para comprobar si cada resultado es 
         * igual al esperado
         */
        Assertions.assertEquals(cambio, "1 31.5 +");
        Assertions.assertEquals(cambio2, "3 5 *");
        Assertions.assertEquals(cambio3, "4 8 -");
        Assertions.assertEquals(cambio4, "9 3.5 /");

        /**
         * Se imprime el cambio para poder comparar si se realizó correctamente
         */
        System.out.println("\nEntrada Postfix 1: " + cambio);
        System.out.println("Entrada Postfix 2: " + cambio2);
        System.out.println("Entrada Postfix 3: " + cambio3);
        System.out.println("Entrada Postfix 4: " + cambio4);

        /**
         * Try-catch que previene errores con el cálculo
         */
        try {

            /**
             * Se realiza el calculo correspondiente
             */
            String resultado1 = calculadora.Calculo(cambio);
            String resultado2 = calculadora.Calculo(cambio2);
            String resultado3 = calculadora.Calculo(cambio3);
            String resultado4 = calculadora.Calculo(cambio4);

            /**
             * Se comprueba que el resultado sea el esperado
             */
            Assertions.assertEquals(resultado1, "32.5");
            Assertions.assertEquals(resultado2, "15.0");
            Assertions.assertEquals(resultado3, "-4.0");
            Assertions.assertEquals(resultado4, "2.5714285714285716");

            /**
             * Se imprimen los resultados
             */
			System.out.println("\nResultado 1: "+ resultado1);
			System.out.println("Resultado 2: "+ resultado2);
            System.out.println("Resultado 3: "+ resultado3);
			System.out.println("Resultado 4: "+ resultado4);

		} catch (Exception e) {
		}
    }
    /**
     * Test#2: En este test se comprueba el funcionamiento de
     * predicados y comparadores utilizados en el proyecto. También se 
     * comprueba la funcionalidad de setq.
    */
    @Test
    public void PredicadosComparadores(){

        try {
            /**
             * Evaluación de setq y atom
             */
            System.out.println("Prueba de setq: (setq s 2)");
            System.out.println("Prueba de atom: (atom s)");

            System.out.println("Resultados: ");
            interprete.interpretar("(setq s 2)");
            interprete.interpretar("(atom s)");
            System.out.println();

            /**
             * Evaluación de comparadores y predicados
             */
            String MaQ = comparador.Mayorque(2, 3);
            String MeQ = comparador.Menorque(5, 5.5);
            String EQ = comparador.Equal(10, 10.0);
            String Quote = interprete.interpretar("(quote (+ 1 2))");
            String Suma = interprete.interpretar("(+ 1 2)");

            /**
             * Se comprueba que los resultados sean los esperados
             */
            Assertions.assertEquals(MaQ, "Nil");
            Assertions.assertEquals(MeQ, "T");
            Assertions.assertEquals(EQ, "T");
            Assertions.assertEquals(Quote, "'[+, 1, 2]");
            Assertions.assertEquals(Suma, "3.0");

            /**
             * Se imprimen los resultados finales
             */
            System.out.println("Resultado de Mayor que: "+ MaQ);
            System.out.println("Resultado de Menor que: "+ MeQ);
            System.out.println("Resultado de Equal: "+ EQ);
            System.out.println("Resultado de quote: " + Quote);
            System.out.println("Resultado de Suma: " + Suma);


        } catch (Exception e) {
        }
    }
}
