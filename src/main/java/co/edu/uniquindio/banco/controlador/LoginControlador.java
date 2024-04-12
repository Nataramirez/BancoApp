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
    Sesion sesion;
    Banco banco = Banco.getInstancia();
    public void iniciarSesion(ActionEvent actionEvent){
        Usuario usuario = banco.validarUsuario(txtIdentificacion.getText(), txtPassword.getText());
        if (usuario != null){
            mostrarVentana();
            sesion = Sesion.getInstancia();
            sesion.setUsuario(usuario);

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

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Panel cliente");

            // Mostrar la nueva ventana
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
