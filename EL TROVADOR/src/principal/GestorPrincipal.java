package principal;

import principal.graficos.SuperficieDeDibujo;
import principal.graficos.Ventana;
import principal.maquinaestado.GestorEstados;

public class GestorPrincipal {

    private boolean enFuncionamiento = false;
    private String titulo;
    private int ancho;
    private int alto;

    private SuperficieDeDibujo sd;
    private Ventana ventana;
    private GestorEstados ge;

    private GestorPrincipal(final  String titulo, final int ancho, final int alto){
        this.titulo = titulo;
        this.ancho = ancho;
        this.alto = alto;
    }
    public static void main(String[] args){
        GestorPrincipal gp = new GestorPrincipal("EL TROVADOR", 640,360);

        Constantes.ANCHO_PANTALLA = 640;
        Constantes.ALTO_PANTALLA = 360;

        gp.iniciarJuego();
        gp.iniciarBuclePrincipal();
    }
    private void iniciarJuego() {
        enFuncionamiento = true;
        inicializar();
    }
    private void inicializar() {
        sd = new SuperficieDeDibujo(ancho,alto);
        ventana = new Ventana(titulo, sd);
        ge = new GestorEstados();
    }
    private void iniciarBuclePrincipal() {
        int aps = 0;
        int fps = 0;

        final int NS_POR_SEGUNDO = 1000000000;
        final int APS_OBJETIVO = 60;
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();

        double tiempoTranscurrido;
        double delta = 0;


        while (enFuncionamiento){
            final long inicioBucle = System.nanoTime();

            tiempoTranscurrido = inicioBucle - referenciaActualizacion;
            referenciaActualizacion = inicioBucle;

            delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

            while (delta >= 1){
                actualizar();
                aps ++;
                Constantes.APS = aps;
                delta--;
            }


            dibujar();
            fps++;

            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO){
                System.out.println("FPS: " + fps + "\naps: " + aps);

                aps = 0;
                Constantes.APS = aps;
                fps = 0;
                referenciaContador = System.nanoTime();
            }
        }
    }
    private void actualizar(){
        ge.actualizar();
    }
    private void dibujar(){
        sd.dibujar(ge);
    }
}