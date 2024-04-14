package com.example.snakedroid;

public class snake_item {

    private int posx,posY;



    public snake_item(int position_x, int position_y){
        this.posx = position_x;
        this.posY = position_y;



    }

    public int getPosx() {
        return posx;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
