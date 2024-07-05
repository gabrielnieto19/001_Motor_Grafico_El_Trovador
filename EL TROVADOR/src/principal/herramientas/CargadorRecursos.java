package principal.herramientas;



import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class CargadorRecursos {

    public static BufferedImage cargarImagenCompatibleOpaca(final String ruta){
        Image imagen = null;


        try {
            imagen = ImageIO.read(CargadorRecursos.class.getResource(ruta));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }



        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.OPAQUE);

        Graphics g = imagenAcelerada.getGraphics();
        g.drawImage(imagen,0,0,null);
        g.dispose();

        return imagenAcelerada;
    }
    public static BufferedImage cargarImagenCompatibleTranslicida(final String ruta){
        Image imagen = null;

        try {
            imagen = ImageIO.read(CargadorRecursos.class.getResource(ruta));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.TRANSLUCENT);

        Graphics g = imagenAcelerada.getGraphics();
        g.drawImage(imagen,0,0,null);
        g.dispose();

        return imagenAcelerada;
    }

    public static String leerArchivoTexto(final String ruta){
        String contenido = "";

        InputStream entradaBytes = CargadorRecursos.class.getResourceAsStream(ruta);
        BufferedReader lector = new BufferedReader(new InputStreamReader(entradaBytes));

        String linea;

        try {
            while ((linea = lector.readLine()) != null){
                contenido += linea;
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (entradaBytes != null){
                    entradaBytes.close();
                }
                if (lector != null){
                    lector.close();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }

        return contenido;
    }
}