package app.Horarios;

import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import app.Timbres.MisTimbres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.LinkedList;

public class MisHorarios extends LinkedList<Horario> {

    private ObservableList view = FXCollections.observableArrayList();

    public MisHorarios() {
        super();
    }

    public void agregarHorario(String hora, String minuto, boolean largo){
        Horario horarioAux = new Horario(hora, minuto, largo);
        super.add(horarioAux);
        this.view.add(horarioAux.getView());
    }

    public ObservableList getView() {
        return this.view;
    }
}
