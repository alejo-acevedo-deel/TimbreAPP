package Excepciones;

public class NoSeConecto extends Exception{
    @Override
    public String getMessage() {
        return "No se ha podido conectar al dispostivio seleccionado";
    }
}
