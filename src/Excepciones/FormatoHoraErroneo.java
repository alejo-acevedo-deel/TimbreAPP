package Excepciones;

public class FormatoHoraErroneo extends Exception {

    public String getMessage(){return "La hora ingresada tiene formato erroneo, la misma no puede ser menor a 0 o mayor a 24";}
}
