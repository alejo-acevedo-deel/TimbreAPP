package app.Horarios;

import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import Excepciones.SilenciosNegativos;
import com.sun.glass.ui.Pixels;
import javafx.scene.control.CheckBox;

class Horario extends CheckBox{

    private String hora;
    private String minuto;
    private boolean largo;
    private String silencios;
    private CheckBox view = new CheckBox();

    public Horario(String hora, String minuto, boolean largo){
        this.hora = hora;
        this.minuto = minuto;
        this.largo = largo;
        this.view.setText(hora+":"+minuto);
    }

    public CheckBox getView(){
        return this.view;
    }


}
