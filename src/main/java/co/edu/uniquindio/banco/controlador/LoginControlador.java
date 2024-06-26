package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Banco;
import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

/**
 * Clase que representa el controlador de la vista de login
 * @author caflorezvi
 */
public class LoginControlador {
    @FXML
    TextField txtIdentificacion;
    @FXML
    PasswordField txtPassword;

    private final Sesion sesion = Sesion.getInstancia();

    private final Banco banco = Banco.getInstancia();

    public void iniciarSesion(ActionEvent actionEvent){
        Usuario usuario = banco.validarUsuario(txtIdentificacion.getText(), txtPassword.getText());
        if (usuario != null){
            // sesion = Sesion.getInstancia();
            sesion.setUsuario(usuario);
            mostrarVentana();
        }else{
            crearAlerta("Datos incorrectos, intente nuevamente", Alert.AlertType.ERROR);
        }

    }

    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    public void mostrarVentana(){
        try {
            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/panelCliente.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva ventana
            PanelClienteControlador controller = loader.getController();
            controller.inicializarValores();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Panel cliente");

            // Mostrar la nueva ventana
            stage.show();

            //Cerrar la ventana actual.
            cerrarVentana();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de cerrar la ventana actual
     */
    public void cerrarVentana(){
        Stage stage = (Stage) txtIdentificacion.getScene().getWindow();
        stage.close();
    }
}
