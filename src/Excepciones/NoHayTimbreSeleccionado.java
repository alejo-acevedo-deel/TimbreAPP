package Excepciones;

public class NoHayTimbreSeleccionado extends Exception {

    @Override
    public String getMessage() {
        return "No hay ningun timbre seleccionado. Por favor seleccione uno antes de proseguir.";
    }
}
