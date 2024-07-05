package principal.mapas;

import principal.sprites.Sprite;

import java.awt.*;

public class Tile {

    private final Sprite sprite;
    private final  int id;
    private boolean solido;

    public Tile(final Sprite sprite, final int id){
        this.sprite = sprite;
        this.id = id;
        solido = false;
    }
    public Tile(final Sprite sprite, final int id, final boolean solido){
        this.sprite = sprite;
        this.id = id;
        this.solido = false;
    }
    public Sprite obtenerSprite(){
        return sprite;
    }
    public int obtenerId(){
        return id;
    }
    public void establecerSolido(final boolean solido){
        this.solido = solido;
    }
    public Rectangle obtenerLimite(final int x, final int y){
        return new Rectangle(x,y,sprite.obtenerAncho(),sprite.obtenerAlto());
    }
}
