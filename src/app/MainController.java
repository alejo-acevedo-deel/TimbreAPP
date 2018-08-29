package app;



import Excepciones.EstaDesconectado;
import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import Excepciones.NoSeConecto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController implements Receptores{

   @FXML
   ListView<Horario> listaSubir;
   @FXML
   ListView<Horario> listaBajados;
   @FXML
   ComboBox<Timbre> comboTimbres;

   private MisTimbres misTimbres = new MisTimbres(this);
   private Timbre timbreSeleccionado;

   private MisHorarios misHorarios = new MisHorarios(this);
   private MisHorarios timbreHorarios = new MisHorarios(this);

   private AgregarHorariosController agregarHorariosController;
   private ModificarTimbresController modificarTimbresController;

   public MainController(Stage primaryStage) throws Exception{
       FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
       loader.setController(this);
       Parent root = loader.load();
       primaryStage.setTitle("Timbre");
       primaryStage.setScene(new Scene(root));
       primaryStage.show();
       actualizarComboTimbres();
       actualizarListaView();
   }

   public void agregarHorarios(ActionEvent actionEvent) throws Exception{
       this.agregarHorariosController = new AgregarHorariosController(this.misHorarios);
   }

   public void borrarHorarios(ActionEvent actionEvent) throws Exception{
       List<Horario> horarios = this.listaSubir.getSelectionModel().getSelectedItems();
       this.misHorarios.borrarHorario(horarios);
   }

   public void conectarTimbre(ActionEvent actionEvent){
       if(comboTimbres.getValue() == null){
           return;
       }
       try {
           if(this.timbreSeleccionado != null){
                this.timbreSeleccionado.desconectar();
           }
           this.timbreSeleccionado = this.comboTimbres.getSelectionModel().getSelectedItem();
           this.timbreSeleccionado.conectar();
           this.timbreSeleccionado.setearReceptor(this);
       }catch (NoSeConecto noSeConecto){
           new Alerta(noSeConecto);
           Platform.runLater(() -> comboTimbres.getSelectionModel().clearSelection());
           this.timbreSeleccionado = null;
       }catch (NullPointerException nullPointerException){
           System.out.println("");
       }catch (EstaDesconectado estaDesconectado){
       }
   }

   public void enviarHorarios(){
       try {
           for (Horario horario : misHorarios.obtenerHorarios()) {
               timbreSeleccionado.agregarHorario(horario);
               misHorarios.borrarHorario(horario);
               timbreHorarios.agregarHorario(horario);
           }
       }catch (EstaDesconectado estaDesconectado){
           new Alerta(estaDesconectado);
       }catch (FormatoHoraErroneo formatoHoraErroneo){

       }catch (FormatoMinutoErroneo formatoMinutoErroneo){

       }
   }

   public void modificarTimbres(ActionEvent actionEvent) throws Exception{
        this.modificarTimbresController = new ModificarTimbresController(this.misTimbres);
   }


   @Override
   public void llegoUnMensaje(String mensaje) {
        System.out.println(mensaje);
   }


   @Override
   public void agregaronUnTimbre() {
        actualizarComboTimbres();
   }

   @Override
   public void modificaronUnHorario() {
        actualizarListaView();
   }

   private void actualizarComboTimbres(){
        this.comboTimbres.getItems().clear();
        this.comboTimbres.getItems().addAll(this.misTimbres.obtenerTimbres());
   }

   private void actualizarListaView(){
       this.listaSubir.getItems().clear();
       this.listaSubir.getItems().addAll(misHorarios.obtenerHorarios());
       this.listaBajados.getItems().clear();
       this.listaBajados.getItems().addAll(timbreHorarios.obtenerHorarios());
   }
}
