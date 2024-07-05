package principal.Ente;

import principal.Constantes;
import principal.control.GestorControles;
import principal.mapas.Mapa;
import principal.sprites.HojaSprites;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Jugador {
    private double posicionX;
    private double posicionY;
    private int direccion;
    private double velocidad = 1;
    private boolean enMovimiento;
    private HojaSprites hs;
    private BufferedImage imagenActual;
    private final int anchoJugador = 16;
    private final int altoJugador = 16;
    private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X +316, Constantes.CENTRO_VENTANA_Y +185, anchoJugador,1);
    private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X +316, Constantes.CENTRO_VENTANA_Y +193, anchoJugador,1);
    private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X +316, Constantes.CENTRO_VENTANA_Y +185, 1,altoJugador/2);
    private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X +332, Constantes.CENTRO_VENTANA_Y +185, 1,altoJugador/2);
    private int animacion;
    private int estado;
    private Mapa mapa;

    public Jugador(double posicionX, double posicionY, Mapa mapa){
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.mapa = mapa;

        direccion = 0;

        enMovimiento = false;

        hs = new HojaSprites(Constantes.RUTA_PERSONAJE,Constantes.LADO_SPRITE,false);

        imagenActual = hs.obtenerSprite(0).obtenerImagen();
        animacion = 0;
        estado = 0;
    }
    public void actualizar(){
        cambiarAnimacionEstado();
        enMovimiento = false;
        determinarDireccion();
        animar();
    }

    private void cambiarAnimacionEstado() {
        if (animacion < 30){
            animacion++;
        }else {
            animacion = 0;
        }
        if (animacion < 15){
            estado = 1;
        }else {
            estado = 2;
        }
    }
    private void determinarDireccion() {
        final int volocidadX = evaluarVelocidadX();
        final int velocidadY = evaluarVelocidadY();

        if (volocidadX == 0 && velocidadY == 0){
            return;
        }
        if ((volocidadX != 0 && velocidadY == 0) || (volocidadX == 0 && velocidadY != 0)){
            mover(volocidadX,velocidadY);
        }else {
            // izquierda y arriba
            if (volocidadX == -1 && velocidadY == -1){
                if (GestorControles.teclado.izquierda.obtenerUltimaPulsacion() > GestorControles.teclado.arriba.obtenerUltimaPulsacion()){
                    mover(volocidadX,0);
                }else {
                    mover(0,velocidadY);
                }
            }
            // izquierda y abajo
            if (volocidadX == -1 && velocidadY == 1){
                if (GestorControles.teclado.izquierda.obtenerUltimaPulsacion() > GestorControles.teclado.abajo.obtenerUltimaPulsacion()){
                    mover(volocidadX,0);
                }else {
                    mover(0,velocidadY);
                }
            }
            // derecha y arriba
            if (volocidadX == 1 && velocidadY == -1){
                if (GestorControles.teclado.derecha.obtenerUltimaPulsacion() > GestorControles.teclado.arriba.obtenerUltimaPulsacion()){
                    mover(volocidadX,0);
                }else {
                    mover(0,velocidadY);
                }
            }
            // derecha y abajo
            if (volocidadX == 1 && velocidadY == 1){
                if (GestorControles.teclado.derecha.obtenerUltimaPulsacion() > GestorControles.teclado.abajo.obtenerUltimaPulsacion()){
                    mover(volocidadX,0);
                }else {
                    mover(0,velocidadY);
                }
            }
        }
    }
    private int evaluarVelocidadX() {
        int velocidadX = 0;

        if (GestorControles.teclado.izquierda.estaPulsada() && !GestorControles.teclado.derecha.estaPulsada()) {
            velocidadX = -1;
        } else if (GestorControles.teclado.derecha.estaPulsada() && !GestorControles.teclado.izquierda.estaPulsada()){
            velocidadX = 1;
        }
        return velocidadX;
    }
    private int evaluarVelocidadY(){
        int velocidadY = 0;

        if (GestorControles.teclado.arriba.estaPulsada() && !GestorControles.teclado.abajo.estaPulsada()) {
            velocidadY = -1;
        } else if (GestorControles.teclado.abajo.estaPulsada() && !GestorControles.teclado.arriba.estaPulsada()){
            velocidadY = 1;
        }
        return velocidadY;
    }
    private void mover(final int volocidadX,final int volocidadY){
        enMovimiento = true;

        cambiarDireccion(volocidadX,volocidadY);

        if (!fueraMapa(volocidadX,volocidadY)){
            if (volocidadX == -1 && !enColicionIzquierda(volocidadX)){
                posicionX += volocidadX * velocidad;
            }if (volocidadX == 1 && !enColicionDerecha(volocidadX)){
                posicionX += volocidadX * velocidad;
            }
            if (volocidadY == -1 && !enColicionArriba(volocidadY)){
                posicionY += volocidadY * velocidad;
            }
            if (volocidadY == 1 && !enColicionAbajo(volocidadY)){
                posicionY += volocidadY * velocidad;
            }
        }
    }
    private boolean enColicionArriba (int velocidadY){
        for (int r = 0; r < mapa.areasColicion.size(); r++){
            final Rectangle area = mapa.areasColicion.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) velocidad + 3 * (int)  velocidad;

            final Rectangle areaFutura = new Rectangle(origenX,origenY,Constantes.LADO_SPRITE,Constantes.LADO_SPRITE);

            if (LIMITE_ARRIBA.intersects(areaFutura)){
                return true;
            }
        }
        return false;
    }
    private boolean enColicionAbajo (int velocidadY){
        for (int r = 0; r < mapa.areasColicion.size(); r++){
            final Rectangle area = mapa.areasColicion.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) velocidad - 3 * (int)  velocidad;

            final Rectangle areaFutura = new Rectangle(origenX,origenY,Constantes.LADO_SPRITE,Constantes.LADO_SPRITE);

            if (LIMITE_ABAJO.intersects(areaFutura)){
                return true;
            }
        }
        return false;
    }
    private boolean enColicionIzquierda (int velocidadX){
        for (int r = 0; r < mapa.areasColicion.size(); r++){
            final Rectangle area = mapa.areasColicion.get(r);

            int origenX = area.x + velocidadX * (int) velocidad + 3 * (int)  velocidad;
            int origenY = area.y ;

            final Rectangle areaFutura = new Rectangle(origenX,origenY,Constantes.LADO_SPRITE,Constantes.LADO_SPRITE);

            if (LIMITE_IZQUIERDA.intersects(areaFutura)){
                return true;
            }
        }
        return false;
    }
    private boolean enColicionDerecha (int velocidadX){
        for (int r = 0; r < mapa.areasColicion.size(); r++){
            final Rectangle area = mapa.areasColicion.get(r);

            int origenX = area.x + velocidadX * (int) velocidad - 3 * (int)  velocidad;
            int origenY = area.y ;

            final Rectangle areaFutura = new Rectangle(origenX,origenY,Constantes.LADO_SPRITE,Constantes.LADO_SPRITE);

            if (LIMITE_DERECHA.intersects(areaFutura)){
                return true;
            }
        }
        return false;
    }
    private boolean fueraMapa(final int velocidadX, final int velocidadY){
        int posicionFuturaX = (int)posicionX + velocidadX * (int)velocidad;
        int posicionFuturaY = (int)posicionY + velocidadY * (int)velocidad;

        final Rectangle bordesMapa = mapa.obtenerBordes(posicionFuturaX,posicionFuturaY,anchoJugador, altoJugador-7);

        final boolean fuera;

        if (LIMITE_ARRIBA.intersects(bordesMapa) || LIMITE_ABAJO.intersects(bordesMapa) || LIMITE_IZQUIERDA.intersects(bordesMapa) || LIMITE_DERECHA.intersects(bordesMapa)){
            fuera = false;
        }else {
            fuera = true;
        }

        return fuera;
    }
    private void cambiarDireccion(final int volocidadX,final int volocidadY){
        if (volocidadX == 1 ){
            direccion = 2;
        } else if (volocidadX == -1){
            direccion = 3;
        }
        if (volocidadY == -1){
            direccion = 1;
        }else if (volocidadY == 1){
            direccion = 0;
        }
    }
    private void animar() {
        if (!enMovimiento){
            estado = 0;
            animacion = 0;
        }
        imagenActual = hs.obtenerSprite(direccion, estado).obtenerImagen();
    }
    public void dibujar(Graphics g){
        final int centroX = Constantes.ANCHO_PANTALLA /2 - Constantes.LADO_SPRITE/2;
        final int centroY = Constantes.ALTO_PANTALLA /2 - Constantes.LADO_SPRITE/2;

        g.setColor(Color.white);
        g.drawImage(imagenActual, centroX,centroY,null);
/*
        g.setColor(Color.red);
        g.drawRect(LIMITE_ARRIBA.x, LIMITE_ARRIBA.y, LIMITE_ARRIBA.width,LIMITE_ARRIBA.height);
        g.drawRect(LIMITE_ABAJO.x, LIMITE_ABAJO.y, LIMITE_ABAJO.width,LIMITE_ABAJO.height);
        g.drawRect(LIMITE_IZQUIERDA.x, LIMITE_IZQUIERDA.y, LIMITE_IZQUIERDA.width, LIMITE_IZQUIERDA.height);
        g.drawRect(LIMITE_DERECHA.x, LIMITE_DERECHA.y, LIMITE_DERECHA.width,LIMITE_DERECHA.height);
*/
    }

    public void establecerPosicionX (double posicionX){
        this.posicionX = posicionX;
    }
    public void establecerPosicionY (double posicionY){
        this.posicionY = posicionY;
    }
    public double obtenerPosicionX(){
        return posicionX;
    }
    public double obtenerPosicionY(){
        return posicionY;
    }
}