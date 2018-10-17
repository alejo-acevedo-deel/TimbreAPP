package app.Horarios;

import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import Excepciones.SilenciosNegativos;
import com.sun.glass.ui.Pixels;
import javafx.scene.control.CheckBox;

class Horario extends CheckBox{

    private String hora;
    private String minutos;
    private boolean largo;
    private String silencios;
    private CheckBox view;


}
