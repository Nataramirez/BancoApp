package co.edu.uniquindio.banco.modelo;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class Sesion {
    public static Sesion INSTANCIA;
    private Usuario usuario;

    public static Sesion getInstancia(){
        if (INSTANCIA==null){
            INSTANCIA = new Sesion();
        }
        return INSTANCIA;
    }

    public void cerrarSesion(){
        usuario = null;
    }

}
