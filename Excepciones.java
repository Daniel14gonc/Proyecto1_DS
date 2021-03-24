/**
 * UVG-Algoritmos y Estructura de datos - Sección 10
 * Catedrático: Douglas Barrios
 * @author Grupo#1
 */

/**
 * Clase Excepciones
 * Muestra Excepciones creadas por el desarrollador
 */
public class Excepciones extends Exception{

    /**
     * Marca estática que corresponde a estas Excepciones
     */
    private static final long serialVersionUID = 1L;

    /**
    * Constructor
    */
    public Excepciones(){
    }

    /**
    * Constructor
    * @param MensajeError: String del mensaje a mostrar en caso de una excepcion
    */
    public Excepciones(String MensajeError){
        super(MensajeError);
    }

}