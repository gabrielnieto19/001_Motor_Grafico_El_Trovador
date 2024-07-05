package principal.maquinaestado;

import principal.maquinaestado.estados.juego.GestorJuego;

import java.awt.*;

public class GestorEstados {
    private EstadoJuego[] estados;
    private  EstadoJuego estadoActual;

    public GestorEstados(){
        iniciarEstados();
        iniciarEstadoActual();
    }

    private void iniciarEstados() {
        estados = new EstadoJuego[1];
        estados[0] = new GestorJuego();
        // añadir e inicializar los demas estados a medida que los creemos
    }
    private void iniciarEstadoActual() {
        estadoActual = estados[0];
    }

    public void actualizar(){
        estadoActual.actualizar();
    }
    public void dibujar(final Graphics g){
        estadoActual.dibujar(g);
    }
    public void cambiarEstadoActual(final int nuevoEstado){
        estadoActual = estados[nuevoEstado];
    }
    public EstadoJuego obtenerEstadoActual(){
        return estadoActual;
    }
}
