package Excepciones;

public class FaltaNombre extends Exception{

    public String getMessage(){
        return "No se ha proporcionado un nombre para la accion a realizar.";
    }

}
