package Excepciones;

public class FormatoIpErroneo extends Exception {

    @Override
    public String getMessage() {
        return "El formato de la IP es erroneo, ingrese una ip valida.";
    }
}
