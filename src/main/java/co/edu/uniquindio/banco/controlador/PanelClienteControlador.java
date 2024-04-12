package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

   /**
     * Clase que se encarga de gestionar las acciones de la interfaz gráfica del panel del cliente.
     *
     */
    public class PanelClienteControlador {





        /**
         * Método que permite ir a la vista de Iniciar Sesión
         *
         * @param actionEvent Evento que representa el clic del botón
         */
        public void irIniciarSesion(ActionEvent actionEvent) {
            navegarVentana("/login.fxml", "Banco - Iniciar Sesión");

        }

        public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
            try {

                // Cargar la vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
                Parent root = loader.load();

                // Crear la escena
                Scene scene = new Scene(root);

                // Crear un nuevo escenario (ventana)
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle(tituloVentana);

                // Mostrar la nueva ventana
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

