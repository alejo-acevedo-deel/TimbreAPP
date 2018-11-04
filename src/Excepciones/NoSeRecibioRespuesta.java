package Excepciones;

public class NoSeRecibioRespuesta extends Exception {
    @Override
    public String getMessage() {
        return "No se recibio respuesta del timbre seleccionado pruebe volviendolo a conectar.";
    }
}
