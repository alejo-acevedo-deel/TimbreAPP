package app.Timbres;

import Excepciones.*;
import app.CSV;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.*;

public class MisTimbres extends LinkedList<Timbre>{

    private static final String CSV_FILE = "Timbres.csv";
    private static final String[] CSV_HEADER = {"Nombre", "IP"};
    private CSV csv;
    private ComboBox<Timbre> view;
    private Timbre timbreSeleccionado;

}
