package Excepciones;

public class EstaDesconectado extends Exception{

    private final String dispositivo;

    public EstaDesconectado(String dispositivo){
        this.dispositivo = dispositivo;
    }

    @Override
    public String getMessage() {
        return "El dispositivo " + this.dispositivo + " esta desconectado";
    }
}
