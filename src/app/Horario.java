package app;

import javafx.scene.control.CheckBox;

public class Horario extends CheckBox{

    private String hora;
    private String minutos;
    private boolean largo;

    public Horario(String hora, String minutos, boolean largo){
        this.hora = hora;
        this.minutos = minutos;
        this.largo = largo;
        if(this.largo){
            this.setText(this.hora + " : " + this.minutos + "  Largo");
        }else {
            this.setText(this.hora + " : " + this.minutos + "  Corto");
        }
    }
}
