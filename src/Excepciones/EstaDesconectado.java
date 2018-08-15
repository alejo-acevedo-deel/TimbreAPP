package Excepciones;

public class EstaDesconectado extends Exception{
    @Override
    public String getMessage() {
        return "El dispositivo esta desconectado";
    }
}
