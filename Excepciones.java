public class Excepciones extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Excepciones(){
    }

    public Excepciones(String MensajeError){
        super(MensajeError);
    }

}