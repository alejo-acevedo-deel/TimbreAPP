package app;

import Excepciones.*;
import app.Horarios.MisHorarios;
import app.Timbres.Estado;
import app.Timbres.MisTimbres;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.event.ChangeEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class MainController{

    static String[] DIAS_SEMANA = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado" } ;

    /***** Declaracion de elementos visuales del apartado horarios del  MainView *****/
    @FXML
    private ListView<CheckBox> horariosUsuariosView, horariosTimbreView;
    @FXML
    private ComboBox comboMisTimbres;

    /***** Declaracion de elementos visuales del apartado estado del MainView *****/
    @FXML
    private TableView estadoTabla;

    /***** Declaracion de elementos visuales del apartado configuracion del MainView *****/
    @FXML
    private ComboBox comboConfiguracion, comboDias;
    @FXML
    private TextField txtHora, txtMinutos, txtDuracionLarga, txtDuracionCorta;
    @FXML
    private RadioButton radioLunes, radioMartes, radioMiercoles, radioJueves, radioViernes, radioSabado, radioDomingo;

    private LinkedList<RadioButton> radioDias;
    private MisTimbres misTimbres = new MisTimbres();
    private MisHorarios misHorarios = new MisHorarios();

    public MainController(Stage primaryStage)throws IOException {
        this.loadView(primaryStage);
        this.horariosUsuariosView.setItems(this.misHorarios.getView());
        this.horariosTimbreView.setItems(this.misTimbres.getMisHorarios().getView());
        this.comboMisTimbres.setItems(this.misTimbres.getView());
        this.comboConfiguracion.setItems(this.misTimbres.getView());
        this.comboDias.setItems(FXCollections.observableArrayList(DIAS_SEMANA));
        this.radioDias = new LinkedList<>(Arrays.asList(radioDomingo, radioLunes, radioMartes, radioMiercoles, radioJueves, radioViernes, radioSabado));
        this.loadTable();
    }

    public void cambioDeTab(Event event){
        try{
            this.seleccionarTimbre(-1);
            this.comboMisTimbres.getSelectionModel().select(null);
            this.comboConfiguracion.getSelectionModel().select(null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**** Metodos usados por el apartado horarios del MainView *****/


    public void mostrarAgregarHorariosView(ActionEvent actionEvent) throws IOException{
        new AgregarHorariosController(this.misHorarios);
    }

    public void mostrarModificarTimbresView(ActionEvent actionEvent) throws  IOException{
        new ModificarTimbresController(this.misTimbres);
    }

    public void borrarHorariosSeleccionadosUsuario(ActionEvent actionEvent){
        this.misHorarios.borrarHorariosSeleccionados();
    }

    public void borrarHorariosTodosUsuario(ActionEvent actionEvent){
        this.misHorarios.borrarHorariosTodos();
    }

    public void borrarHorariosSeleccionadosTimbre(ActionEvent actionEvent){
        try {
            this.misTimbres.borrarHorariosSeleccionados();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EstaDesconectado | FormatoMinutoErroneo | NoSeRecibioRespuesta | FormatoHoraErroneo | NoHayTimbreSeleccionado  e) {
            new Alerta(e);
        }
    }

    public void borrarHorariosTodosTimbre(ActionEvent actionEvent){
        try {
            this.misTimbres.borrarHorariosTodos();
        } catch (IOException | FormatoMinutoErroneo | FormatoHoraErroneo  e) {
            e.printStackTrace();
        } catch (EstaDesconectado |NoSeRecibioRespuesta | NoHayTimbreSeleccionado e) {
            new Alerta(e);
        }
    }


    public void tranferirHorarios(ActionEvent actionEvent){
        try {
            this.misTimbres.tranferirHorarios(this.misHorarios.obtenerComoMensaje());
            this.misHorarios.borrarHorariosTodos();
        } catch (EstaDesconectado | NoHayTimbreSeleccionado e) {
            new Alerta(e);
        } catch (EnvioDeHorariosTruncado envioDeHorariosTruncado) {
            new Alerta(envioDeHorariosTruncado);
            this.misHorarios.borrarPrimeros(envioDeHorariosTruncado.obtenerCantEnviados());
        } catch (FormatoHoraErroneo | FormatoMinutoErroneo | NoSeRecibioRespuesta e) {
            e.printStackTrace();
        }
    }

    public void comboMisTimbesSeleccionado(ActionEvent actionEvent){
        try {
            this.misTimbres.seleccionarTimbre(this.comboMisTimbres.getSelectionModel().getSelectedIndex());
            if(this.comboMisTimbres.getSelectionModel().getSelectedIndex() != -1)
            this.misTimbres.obtenerHorarios();
        } catch (FormatoMinutoErroneo | EstaDesconectado | FormatoHoraErroneo | NoSeRecibioRespuesta e) {
            e.printStackTrace();
        } catch (NoSeConecto noSeConecto) {
            new Alerta(noSeConecto);
            Platform.runLater(() -> {
                this.comboMisTimbres.getSelectionModel().select(null);
                this.comboConfiguracion.getSelectionModel().select(null);
            });
        } catch (NoHayTimbreSeleccionado noHayTimbreSeleccionado) {
            new Alerta(noHayTimbreSeleccionado);
        }
    }

    public void silenciar(ActionEvent actionEvent){
        try {
            this.misTimbres.silenciar();
        } catch (IOException | FormatoMinutoErroneo | NoSeRecibioRespuesta | FormatoHoraErroneo | NoHayTimbreSeleccionado e) {
            e.printStackTrace();
        } catch (EstaDesconectado estaDesconectado) {
            new Alerta(estaDesconectado);
        }
    }

    public void desilenciar(ActionEvent actionEvent){
        try {
            this.misTimbres.desilenciar();
        } catch (IOException | FormatoMinutoErroneo | NoSeRecibioRespuesta | FormatoHoraErroneo | NoHayTimbreSeleccionado e) {
            e.printStackTrace();
        } catch (EstaDesconectado estaDesconectado) {
            new Alerta(estaDesconectado);
        }
    }

    public void silenciarTodos(ActionEvent actionEvent){
        try {
            misTimbres.silenciarTodos();
        } catch (IOException | NoSeRecibioRespuesta | FormatoHoraErroneo | NoHayTimbreSeleccionado | FormatoMinutoErroneo e) {
            e.printStackTrace();
        } catch (EstaDesconectado estaDesconectado) {
            new Alerta(estaDesconectado);
        }
    }

    /***** Metodos usados por el apartado estado del MainView *****/

    public void chequearEstado(ActionEvent actionEvent){
        this.estadoTabla.getItems().clear();
        try {
            this.estadoTabla.setItems(this.misTimbres.obtenerEstados());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EstaDesconectado estaDesconectado) {
            estaDesconectado.printStackTrace();
        }
    }

    /***** Metodos usados por el apartado configuracion del MainView *****/
    public void comboConfiguracionSeleccionado(ActionEvent actionEvent){
        try {
            this.misTimbres.seleccionarTimbre(this.comboConfiguracion.getSelectionModel().getSelectedIndex());
        } catch (FormatoMinutoErroneo | EstaDesconectado | FormatoHoraErroneo | NoSeRecibioRespuesta e) {
            e.printStackTrace();
        } catch (NoSeConecto noSeConecto) {
            new Alerta(noSeConecto);
            Platform.runLater(() -> {
                this.comboMisTimbres.getSelectionModel().select(null);
                this.comboConfiguracion.getSelectionModel().select(null);
            });
        } catch (NoHayTimbreSeleccionado noHayTimbreSeleccionado) {
            new Alerta(noHayTimbreSeleccionado);
        }
    }

    public void configurarHoraAutomaticamente(ActionEvent actionEvent){
        try {
            LinkedList<Exception> noSeConectaron =  this.misTimbres.configurarHoraAutomaticamete();
            new Mensaje("El horario se actualizo automaticamente");
            for(Exception noSeConecto : noSeConectaron){
                new Alerta(noSeConecto);
            }
        } catch (IOException | EstaDesconectado e) {
            e.printStackTrace();
        }

    }

    public void configurarHoraManualmente(ActionEvent actionEvent){
        try {
            LinkedList<Exception> noSeConectaron = this.misTimbres.configurarHoraManualmente(this.txtHora.getText(), this.txtMinutos.getText(), this.comboDias.getSelectionModel().getSelectedIndex());
            new Mensaje("El horario se actualizo correctamente");
            for(Exception noSeConecto : noSeConectaron){
                new Alerta(noSeConecto);
            }
        } catch (FormatoMinutoErroneo | FormatoHoraErroneo | FaltaDiaDeSemana e) {
            new Alerta(e);
        } catch (IOException | EstaDesconectado e) {
            e.printStackTrace();
        }
    }

    public void configurarDuracion(ActionEvent actionEvent) {
        try {
            LinkedList<Exception> noSeConectaron = this.misTimbres.configurarDuracion(this.txtDuracionLarga.getText(), this.txtDuracionCorta.getText());
            new Mensaje("La duracion de sonado se actualizo correctamente");
            for(Exception noSeConecto : noSeConectaron){
                new Alerta(noSeConecto);
            }
        } catch (FormatoDeDuracionErroneo e) {
            new Alerta(e);
        } catch (IOException | EstaDesconectado e) {
            e.printStackTrace();
        }
    }

    public void configurarLibres(ActionEvent actionEvent){
        try{
            LinkedList<Exception> noSeConectaron = this.misTimbres.configurarLibres(this.radioDias);
            new Mensaje("Los dias libres se configuraron correctamente");
            for(Exception noSeConecto : noSeConectaron){
                new Alerta(noSeConecto);
            }
        } catch (IOException | EstaDesconectado e) {
            e.printStackTrace();
        } catch (NoSeConecto noSeConecto) {
            new Alerta(noSeConecto);
        }
    }

    public void activarVacaciones(ActionEvent actionEvent){
        try{
            LinkedList<Exception> noSeConectaron = this.misTimbres.activarVacaciones();
            new Mensaje("El modo vacaciones se activo correctamente");
            for(Exception noSeConecto : noSeConectaron){
                new Alerta(noSeConecto);
            }
        } catch (IOException | EstaDesconectado e) {
            e.printStackTrace();
        } catch (NoSeConecto noSeConecto) {
            new Alerta(noSeConecto);
        }
    }

    public void desactivarVacaciones(ActionEvent actionEvent){
        try{
            LinkedList<Exception> noSeConectaron = this.misTimbres.desactivarVacaciones();
            new Mensaje("El modo vacaciones se desactivo correctamente");
            for(Exception noSeConecto : noSeConectaron){
                new Alerta(noSeConecto);
            }
        } catch (IOException | EstaDesconectado e) {
            e.printStackTrace();
        } catch (NoSeConecto noSeConecto) {
            new Alerta(noSeConecto);
        }
    }



    private void loadView(Stage stage)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        stage.setTitle("Controlador de Timbres");
        stage.setScene(new Scene(root));
        //stage.setResizable(false);
        stage.show();
    }

    private void loadTable(){
        ObservableList<TableColumn> columnas = this.estadoTabla.getColumns();
        for(TableColumn columna : columnas){
            columna.setCellValueFactory(new PropertyValueFactory<>(columna.getId()));
        }
        this.estadoTabla.setRowFactory(tv -> {
            TableRow<Estado> row = new TableRow<>();
            BooleanBinding contains = Bindings.createBooleanBinding(() -> {
                if(row.getItem()==null){
                    return true;
                }
                if(row.getItem().getSeConecto()){
                    return true;
                }
                return false;
            }, row.itemProperty());
            row.styleProperty().bind(Bindings.when(contains)
                    .then("")
                    .otherwise("-fx-background-color: red;"));
            return row;
        });
    }

    private void seleccionarTimbre(int indice){
        try {
            this.misTimbres.seleccionarTimbre(indice);
        } catch (FormatoMinutoErroneo | EstaDesconectado | FormatoHoraErroneo | NoSeRecibioRespuesta e) {
            e.printStackTrace();
        } catch (NoSeConecto noSeConecto) {
            new Alerta(noSeConecto);
            Platform.runLater(() -> {
                this.comboMisTimbres.getSelectionModel().select(null);
                this.comboConfiguracion.getSelectionModel().select(null);
            });
        } catch (NoHayTimbreSeleccionado noHayTimbreSeleccionado) {
            new Alerta(noHayTimbreSeleccionado);
        }
    }


}
