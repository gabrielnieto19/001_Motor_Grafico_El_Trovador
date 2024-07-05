package principal.graficos;

import principal.control.GestorControles;
import principal.control.Raton;
import principal.control.Teclado;
import principal.maquinaestado.GestorEstados;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serial;
import java.io.Serializable;

public class SuperficieDeDibujo extends Canvas implements Serializable {

    @Serial
    private static final long serialVersionUID = -1629856263608291444L;

    //atributos
    private int ancho;
    private int alto;
    private Raton raton;

    //costructor
    public SuperficieDeDibujo(final int ancho, final int alto) {
        this.ancho = ancho;
        this.alto = alto;

        this.raton = new Raton();

        setIgnoreRepaint(true);
        setCursor(raton.obtenerCursor());
        setPreferredSize(new Dimension(ancho, alto));
        addKeyListener(GestorControles.teclado);
        setFocusable(true);
        requestFocus();
    }
    public void dibujar (final GestorEstados ge){
        BufferStrategy buffer = getBufferStrategy();

        if (buffer == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = buffer.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,ancho,alto);

        ge.dibujar(g);

        Toolkit.getDefaultToolkit().sync();

        g.dispose();

        buffer.show();
    }
    public int obtenerAncho(){
        return ancho;
    }
    public int obtenerAlto(){
        return alto;
    }
    //metodos
}
