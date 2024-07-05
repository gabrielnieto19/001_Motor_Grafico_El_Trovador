package principal.mapas;

import principal.Constantes;
import principal.herramientas.CargadorRecursos;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Mapa {

    private  String[] partes;

    private final int ancho;
    private final int alto;

    private final Sprite[] paleta;
    private final boolean[] colisiones;
    public ArrayList<Rectangle> areasColicion = new ArrayList<Rectangle>();
    private final int[] sprites;
    private final int  MARGEN_X = Constantes.ANCHO_PANTALLA / 2 - Constantes.LADO_SPRITE / 2;
    private final int  MARGEN_Y = Constantes.ALTO_PANTALLA / 2 - Constantes.LADO_SPRITE / 2;

    public Mapa(final String ruta){
        String contenido = CargadorRecursos.leerArchivoTexto(ruta);

        partes = contenido.split("\\*");

        ancho = Integer.parseInt(partes[0]);
        alto = Integer.parseInt(partes[1]);

        String hojasUtilizadas = partes[2];
        String[] hojasSeparadas = hojasUtilizadas.split(",");

        //lectura de la paleta de sprites
        String paletaEntera = partes[3];
        String[] partesPaleta = paletaEntera.split("#");

        // asignar sprites aca
        paleta = asignarSprites(partesPaleta, hojasSeparadas);

        String colicionesEnteras = partes[4];
        colisiones = extraerColiciones(colicionesEnteras);

        String spritesEnteros = partes[5];
        String[] cadenasSprites =  spritesEnteros.split(" ");

        sprites = extraerSprites(cadenasSprites);

    }

    private Sprite[] asignarSprites(final String[] partesPaleta, final String[] hojasSeparadas){
        Sprite[] paleta = new Sprite[partesPaleta.length];

        HojaSprites hoja = new HojaSprites("/imagenes/hojasTexturas/" + hojasSeparadas[0] + ".png",32,true);

        for (int i = 0; i < partesPaleta.length; i++){
            String spriteTemporal = partesPaleta[i];
            String[] partesSprite = spriteTemporal.split("-");
            int indicePaleta = Integer.parseInt(partesSprite[0]);
            int indiceSpriteHoja = Integer.parseInt(partesSprite[2]);

            paleta[indicePaleta] = hoja.obtenerSprite(indiceSpriteHoja);
        }
        return  paleta;
    }
    private boolean[] extraerColiciones(final String cadenaColiciones) {
        boolean[] coliciones = new boolean[cadenaColiciones.length()];

        for (int i = 0; i < cadenaColiciones.length(); i++){
            if (cadenaColiciones.charAt(i) == '0'){
                coliciones[i] = false;
            }else {
                coliciones[i] = true;
            }
        }
        return coliciones;
    }
    private int[] extraerSprites(final String[] cadenasSprites){
        ArrayList<Integer> sprites = new ArrayList<Integer>();

        for (int i = 0; i < cadenasSprites.length; i++){
            if (cadenasSprites[i].length() == 2){
                sprites.add(Integer.parseInt(cadenasSprites[i]));
            }else {
                String uno = "";
                String dos = "";

                String error = cadenasSprites[i];

                uno += error.charAt(0);
                uno += error.charAt(1);

                dos += error.charAt(2);
                dos += error.charAt(3);

                sprites.add(Integer.parseInt(uno));
                sprites.add(Integer.parseInt(dos));
            }
        }
        int[] vectorSprites = new int[sprites.size()];

        for (int i = 0; i < sprites.size();i++){
            vectorSprites[i] = sprites.get(i);
        }
        return vectorSprites;
    }

    public void actualizar(final int posicionX,final int posicionY){
        actualizarAreasColicion(posicionX,posicionY);
    }
    private void actualizarAreasColicion(final int posicionX,final int posicionY){
        if (!areasColicion.isEmpty()){
            areasColicion.clear();
        }

        for (int y = 0 ; y < this.alto; y++){
            for (int x = 0; x < this.ancho; x++){

                int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
                int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

                if (colisiones[x + y * this.ancho]){
                    final Rectangle r = new Rectangle(puntoX,puntoY,Constantes.LADO_SPRITE,Constantes.LADO_SPRITE);
                    areasColicion.add(r);
                }
            }
        }
    }
    public void dibujar(Graphics g,final int posicionX,final int posicionY){

        for (int y = 0; y < this.alto;y++){
            for (int x = 0; x < this.ancho; x++){
                BufferedImage imagen = paleta[sprites[x+y*this.ancho]].obtenerImagen();

                int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
                int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

                g.drawImage(imagen,puntoX,puntoY,null);

/*
                g.setColor(Color.green);
                for (int r = 0; r < areasColicion.size(); r++){
                    Rectangle area = areasColicion.get(r);
                    g.drawRect(area.x,area.y,area.width,area.height);
                }
*/
            }
        }
    }
    public Rectangle obtenerBordes(final int posicionX, final int posicionY, final int anchoJugador, final int altoJugador){

        int x = MARGEN_X - posicionX + anchoJugador;
        int y = MARGEN_Y - posicionY + altoJugador;
        int ancho = this.ancho * Constantes.LADO_SPRITE - anchoJugador * 2;
        int alto = this.alto * Constantes.LADO_SPRITE - altoJugador * 2;

        return new Rectangle(x,y,ancho,alto);
    }

    public int obtenerAncho(){
        return this.ancho;
    }
    public int obtenerAlto(){
        return this.alto;
    }
    public Sprite obtenerSpritePaleta(final int indice){
        return paleta[indice];
    }
    public Sprite obtenerSpritePaleta(final int x,final int y){
        return paleta[x + y *this.ancho];
    }
    public Sprite[] obtenerPaleta(){
        return this.paleta;
    }
}
