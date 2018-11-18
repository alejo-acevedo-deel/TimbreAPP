package Excepciones;

public class FormatoDeDuracionErroneo extends Exception {
    @Override
    public String getMessage() {
        return "El formato de la duracion ingresada es incorecta, deben ingresarse numero mayores a 0";
    }
}
