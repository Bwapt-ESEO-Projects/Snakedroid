package com.example.snakedroid;


//// Classe snake_item qui permet de créer un item du serpent
public class snake_item {

    private int posx,posY;
    String sens ="right";


    //// Constructeur de la classe snake_item
    public snake_item(int position_x, int position_y){
        this.posx = position_x;
        this.posY = position_y;



    }

    public int getPosx() {
        return posx;
    }   //// Getter de la position x

    public int getPosY() {
        return posY;
    }   //// Getter de la position y

    public void setPosx(int posx) {
        this.posx = posx;
    }   //// Setter de la position x

    public void setPosY(int posY) {
        this.posY = posY;
    }   //// Setter de la position y
}
