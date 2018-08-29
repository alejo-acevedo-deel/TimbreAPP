package Excepciones;

public class SilenciosNegativos extends Exception {

    @Override
    public String getMessage() {
        return "Se ha ingresado una cantidad de silenios menor a 0. La misma debe ser mayor o igual a 0.";
    }
}
