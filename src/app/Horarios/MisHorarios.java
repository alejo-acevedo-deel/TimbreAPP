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

    private ObservableList<CheckBox> view = FXCollections.observableArrayList();
    private LinkedList<Horario> horariosSeleccionados = new LinkedList<>();

    public MisHorarios() {
        super();
    }

    public void agregarHorario(String hora, String minuto, boolean largo) throws FormatoMinutoErroneo, FormatoHoraErroneo {
        Horario horarioAux = new Horario(hora, minuto, largo);;
        super.add(horarioAux);
        this.view.add(horarioAux.getView());
    }

    public void borrarHorarios() {
        for(int i = 0; i < super.size(); i++){
            if(super.get(i).getView().isSelected()){
                super.remove(i);
                this.view.remove(i);
            }
        }
    }

    public void silenciarDurante(String dias){
        for(int i = 0; i < super.size(); i++){
            if(super.get(i).getView().isSelected()){
                super.get(i).silenciarDurante(dias);
            }
        }
    }

    public LinkedList obtenerComoMensaje(){
        LinkedList horarios = new LinkedList();
        for(Horario horario : this){
            horarios.add(horario.obtenerComoMensaje());
        }
        return horarios;
    }

    public ObservableList<CheckBox> getView() {
        return this.view;
    }
}
