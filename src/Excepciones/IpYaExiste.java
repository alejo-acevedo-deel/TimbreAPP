package Excepciones;

public class IpYaExiste extends Exception{

    public String getMessage(){
        return "Ya existe un dispositivo con el ip proporcinado";
    }
}
