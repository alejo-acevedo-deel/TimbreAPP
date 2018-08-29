package Excepciones;

public class FormatoMinutoErroneo extends Exception{

    @Override
    public String getMessage() {
        return "El formato de los minutos es erroneo, el mismo debe ser mayor a 0 y menor a 59";
    }
}
