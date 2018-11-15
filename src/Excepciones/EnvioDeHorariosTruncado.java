package Excepciones;

public class EnvioDeHorariosTruncado extends Exception {

    private int horariosEnviados;

    public EnvioDeHorariosTruncado(int enviados){
        this.horariosEnviados = enviados;
    }

    public int obtenerCantEnviados(){
        return this.horariosEnviados;
    }

    @Override
    public String getMessage() {
        return "Se han enviado solo los primeros " + String.valueOf(this.horariosEnviados) + "horarios.";
    }
}
