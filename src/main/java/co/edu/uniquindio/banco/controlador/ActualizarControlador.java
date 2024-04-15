package co.edu.uniquindio.banco.controlador;
import co.edu.uniquindio.banco.modelo.Banco;
import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
public class ActualizarControlador implements Initializable {
    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnActualizar;

    private Sesion sesion = Sesion.getInstancia();
    private Usuario usuario = sesion.getUsuario();
    private Banco banco = Banco.getInstancia();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        llenarDatos();
    }

    private void llenarDatos() {
        txtIdentificacion.setText(usuario.getNumeroIdentificacion());
        txtNombre.setText(usuario.getNombre());
        txtCorreo.setText(usuario.getCorreoElectronico());
        txtDireccion.setText(usuario.getDireccion());
    }

    public void actualizar(ActionEvent actionEvent) {
        if (!sonCamposValidos()) {
            crearAlerta("Por favor, completa todos los campos obligatorios.", Alert.AlertType.INFORMATION);
            return;
        }

        try {
            banco.actualizarUsuario(
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtIdentificacion.getText(),
                    txtDireccion.getText(),
                    txtPassword.getText()
            );

            crearAlerta("Actualización exitosa.", Alert.AlertType.CONFIRMATION);
        } catch (Exception e) {
            crearAlerta("Hubo un error al actualizar los datos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean sonCamposValidos() {
        // Verifica que los campos obligatorios estén llenos y sean válidos
        return !txtCorreo.getText().isBlank() &&
                !txtDireccion.getText().isBlank() &&
                !txtPassword.getText().isBlank();
    }

    private void crearAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
