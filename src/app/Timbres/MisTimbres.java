package app.Timbres;

import Excepciones.*;
import app.CSV;
import app.Horarios.MisHorarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.*;

public class MisTimbres extends LinkedList<Timbre>{

    private static final String CSV_FILE = "Timbres.csv";
    private static final String[] CSV_HEADER = {"Nombre", "IP"};
    private CSV csv;
    private ObservableList view = FXCollections.observableArrayList();
    private Timbre timbreSeleccionado;
    private MisHorarios misHorarios = new MisHorarios();

    public MisTimbres(){
        super();
    }

    public ObservableList getView(){
        return this.view;
    }

    public MisHorarios getMisHorarios(){ return this.misHorarios;}
}
