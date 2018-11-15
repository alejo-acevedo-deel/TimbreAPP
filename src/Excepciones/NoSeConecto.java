package Excepciones;

public class NoSeConecto extends Exception{

    private String dispositivo;

    public NoSeConecto(String dispositivo){
        this.dispositivo = dispositivo;
    }

    @Override
    public String getMessage() {
        return "No se ha podido conectar al dispostivio " + this.dispositivo;
    }
}
