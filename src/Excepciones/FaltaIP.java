package Excepciones;

public class FaltaIP extends Exception{
    public String getMessage(){
        return "No se ha proporcionado una IP para la accion a realizar.";
    }
}
