package app;

import Excepciones.*;
import app.Horarios.MisHorarios;
import app.Timbres.Estado;
import app.Timbres.MisTimbres;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainController{

    @FXML
    private ListView<CheckBox> horariosUsuariosView;
    @FXML
    private ListView<CheckBox> horariosTimbreView;
    @FXML
    private ComboBox misTimbresView;
    @FXML
    private TableView estadoTabla;

    private MisTimbres misTimbres = new MisTimbres();
    private MisHorarios misHorarios = new MisHorarios();

    public MainController(Stage primaryStage)throws IOException {
        this.loadView(primaryStage);
        this.horariosUsuariosView.setItems(this.misHorarios.getView());
        this.horariosTimbreView.setItems(this.misTimbres.getMisHorarios().getView());
        this.misTimbresView.setItems(this.misTimbres.getView());
        this.loadTable();

    }

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
        } catch (EstaDesconectado | FormatoMinutoErroneo | NoSeRecibioRespuesta | FormatoHoraErroneo | NoHayTimbreSeleccionado  estaDesconectado) {
            new Alerta(estaDesconectado);
        }
    }

    public void borrarHorariosTodosTimbre(ActionEvent actionEvent){
        try {
            this.misTimbres.borrarHorariosTodos();
        } catch (IOException | FormatoMinutoErroneo | FormatoHoraErroneo  e) {
            e.printStackTrace();
        } catch (EstaDesconectado |NoSeRecibioRespuesta | NoHayTimbreSeleccionado estaDesconectado) {
            new Alerta(estaDesconectado);
        }
    }

    public void seleccionarTimbre(ActionEvent actionEvent){
        try {
            this.misTimbres.seleccionarTimbre(this.misTimbresView.getSelectionModel().getSelectedIndex());
        } catch (FormatoMinutoErroneo formatoMinutoErroneo) {
            formatoMinutoErroneo.printStackTrace();
        } catch (NoSeRecibioRespuesta noSeRecibioRespuesta) {
            noSeRecibioRespuesta.printStackTrace();
        } catch (FormatoHoraErroneo formatoHoraErroneo) {
            formatoHoraErroneo.printStackTrace();
        } catch (EstaDesconectado estaDesconectado) {
            estaDesconectado.printStackTrace();
        } catch (NoSeConecto noSeConecto) {
            new Alerta(noSeConecto);
            Platform.runLater(() -> this.misTimbresView.getSelectionModel().select(null));
        } catch (NoHayTimbreSeleccionado noHayTimbreSeleccionado) {
            new Alerta(noHayTimbreSeleccionado);
        }
    }

    public void tranferirHorarios(ActionEvent actionEvent){
        try {
            this.misTimbres.tranferirHorarios(this.misHorarios.obtenerComoMensaje());
            this.misHorarios.borrarHorariosTodos();
        } catch (EstaDesconectado estaDesconectado) {
            new Alerta(estaDesconectado);
        } catch (EnvioDeHorariosTruncado envioDeHorariosTruncado) {
            new Alerta(envioDeHorariosTruncado);
            this.misHorarios.borrarPrimeros(envioDeHorariosTruncado.obtenerCantEnviados());
        } catch (FormatoHoraErroneo formatoHoraErroneo) {
            formatoHoraErroneo.printStackTrace();
        } catch (FormatoMinutoErroneo formatoMinutoErroneo) {
            formatoMinutoErroneo.printStackTrace();
        } catch (NoSeRecibioRespuesta noSeRecibioRespuesta) {
            noSeRecibioRespuesta.printStackTrace();
        } catch (NoHayTimbreSeleccionado noHayTimbreSeleccionado) {
            new Alerta(noHayTimbreSeleccionado);
        }
    }

    public void chequearEstado(ActionEvent actionEvent){
        this.estadoTabla.getItems().clear();
        Thread t = new HiloLambda(() -> {
            try {
                this.estadoTabla.setItems(this.misTimbres.obtenerEstados());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EstaDesconectado estaDesconectado) {
                estaDesconectado.printStackTrace();
            }
        });
        t.start();
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


}
