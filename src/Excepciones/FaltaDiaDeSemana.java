package Excepciones;

public class FaltaDiaDeSemana extends Exception {

    @Override
    public String getMessage() {
        return "Falta seleccionar el dia de la semana, por favor seleccionelo.";
    }
}
