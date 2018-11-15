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

    public void borrarHorariosSeleccionados() {
        for(int i = 0; i < super.size(); i++){
            if(super.get(i).getView().isSelected()){
                this.borrarHorario(i);
                i--;
            }
        }
    }

    public void borrarHorariosTodos(){
        this.view.clear();
        super.clear();
    }

    public void borrarHorario(int indice){
        super.remove(indice);
        this.view.remove(indice);
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

    public void borrarPrimeros(int primeros){
        for(int i = 0; i < primeros; i++){
            this.borrarHorario(0);
        }
    }

    public ObservableList<CheckBox> getView() {
        return this.view;
    }

    public void agregarHorariosDesdeMsg(LinkedList<String> horarios) throws FormatoMinutoErroneo, FormatoHoraErroneo {
        for(int i = 0; i<horarios.size()-1; i++){
            String[] horario = horarios.get(i).split(":");
            this.agregarHorario(horario[0], horario[1], horario[2].equals("L"));
            super.get(i).silenciarDurante(horario[3]);
        }
    }

    public LinkedList<Integer> obtenerIndicesSeleccionados() {
        LinkedList<Integer> indices = new LinkedList<>();
        for (int i = 0; this.size() > i; i++) {
            if (super.get(i).getView().isSelected()) {
                indices.push(i);
            }
        }
        return indices;
    }
}
