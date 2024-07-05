package principal.graficos;

import principal.herramientas.CargadorRecursos;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Ventana extends JFrame implements Serializable {

    private static final long serialVersionUID = -9083499791901082324L;
    private String titulo;
    private final ImageIcon icono;

    public Ventana(final String titulo, final SuperficieDeDibujo sd){
        this.titulo = titulo;

        BufferedImage imagen = CargadorRecursos.cargarImagenCompatibleTranslicida("/imagenes/iconos/iconoJuego.png");
        this.icono = new ImageIcon(imagen);

        configurarVentana(sd);
    }

    private void configurarVentana(final SuperficieDeDibujo sd) {
        setTitle(titulo);
        setIconImage(icono.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);
        setLayout(new BorderLayout());
        add(sd,BorderLayout.CENTER);
//        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
