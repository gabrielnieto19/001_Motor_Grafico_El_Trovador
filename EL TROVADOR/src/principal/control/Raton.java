package principal.control;

import principal.Constantes;
import principal.herramientas.CargadorRecursos;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Raton {
    private final Cursor cursor;

    public Raton (){
        Toolkit configuracion = Toolkit.getDefaultToolkit();

        BufferedImage icono = CargadorRecursos.cargarImagenCompatibleTranslicida(Constantes.RUTA_ICONO_RATON);

        Point punta = new Point(0,0);

        this.cursor = configuracion.createCustomCursor(icono,punta,"Cursor por defecto");
    }
    public Cursor obtenerCursor(){
        return this.cursor;
    }
}
