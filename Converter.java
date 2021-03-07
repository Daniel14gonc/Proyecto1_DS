public class Converter{

  private static Converter converterInstance;

  private Converter(){

  }

  public static Converter gConverter(){
    if(converterInstance == null){
      converterInstance = new Converter();
    }

    return converterInstance;
  }



}