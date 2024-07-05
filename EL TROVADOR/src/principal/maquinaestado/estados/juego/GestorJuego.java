package principal.maquinaestado.estados.juego;

import principal.Constantes;
import principal.Ente.Jugador;
import principal.mapas.Mapa;
import principal.maquinaestado.EstadoJuego;

import java.awt.*;

public class GestorJuego implements EstadoJuego {
        Mapa mapa = new Mapa(Constantes.RUTA_MAPA);
        Jugador jugador = new Jugador(0,0, mapa);
    public void actualizar() {
        jugador.actualizar();
        mapa.actualizar((int)jugador.obtenerPosicionX(),(int)jugador.obtenerPosicionY());
    }

    public void dibujar(Graphics g) {

        mapa.dibujar(g,(int) jugador.obtenerPosicionX(),(int) jugador.obtenerPosicionY());
        jugador.dibujar(g);
        g.setColor(Color.white);
        g.drawString("x = "+ jugador.obtenerPosicionX(),20,20);
        g.drawString("y = "+ jugador.obtenerPosicionY(),20,35);
    }
}
