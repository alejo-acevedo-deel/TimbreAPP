package app;

import app.Horarios.MisHorarios;
import app.Timbres.MisTimbres;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MainController{

    Controlador controlador;

   @FXML
   private ListView horariosUsuariosView;
   @FXML
   private ListView horariosTimbreView;
   @FXML
   private ComboBox misTimbresView;

   public MainController(Stage primaryStage) throws Exception{
       FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
       loader.setController(this);
       Parent root = loader.load();
       primaryStage.setTitle("Timbre");
       primaryStage.setScene(new Scene(root));
       primaryStage.show();
   }

   public void setControlador(Controlador controlador){
       this.controlador = controlador;
   }

   public void mostrarAgregarHorariosView(ActionEvent actionEvent){
       this.controlador.mostrarAgregarHorariosView();
   }

   public void borrarHorariosUsuario(ActionEvent actionEvent){
       while(!this.horariosUsuariosView.getSelectionModel().getSelectedItems().isEmpty()){
           this.horariosUsuariosView.getItems().remove(this.horariosUsuariosView.getSelectionModel().getSelectedIndex());
           this.controlador.borrarUnHorarioUsuario(this.horariosUsuariosView.getSelectionModel().getSelectedIndex());
       }
   }

   public void borrarTodosHorariosUsuario(ActionEvent actionEvent){
       while(!this.horariosUsuariosView.getItems().isEmpty()){
           this.horariosUsuariosView.getItems().remove(0);
           this.controlador.borrarUnHorarioUsuario(0);
       }
   }

   public void seleccionarTimbre(ActionEvent actionEvent){
       if(this.misTimbresView.getValue() == null){
           return;
       }
       this.controlador.seleccionarUnTimbre(this.misTimbresView.getSelectionModel().getSelectedIndex());
   }

   public void mostrarModificarTimbresView(){
       this.controlador.mostrarModificarTimbresView();
   }

   public void enviarHorario(){
       this.controlador.enviarHorario();
   }

   public void modificarTimbres(ActionEvent actionEvent) throws Exception{
        this.controlador.mostrarModificarTimbresView();
   }

   public void obtenerHorario(){
       this.controlador.obtenerHorariosTimbre();
   }

   public void actualizarComboTimbres(MisTimbres misTimbres){
       Platform.runLater(
               () -> {
                   this.misTimbresView.getItems().clear();
                   this.misTimbresView.getItems().addAll(misTimbres);
               }
       );
   }

   public void actualizarHorariosUsuariosView(MisHorarios misHorarios){
        this.actualizarListaView(misHorarios, horariosUsuariosView);
    }

    public void actualizarHorariosTimbreView(MisHorarios misHorarios){
        this.actualizarListaView(misHorarios, horariosTimbreView);
    }

   private void actualizarListaView(MisHorarios misHorarios, ListView listView){
       Platform.runLater(
               () -> {
                   listView.getItems().clear();
                   listView.getItems().addAll(misHorarios);
               }
       );
   }
}
