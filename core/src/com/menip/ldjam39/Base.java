package com.menip.ldjam39;


import com.badlogic.gdx.math.Rectangle;

/**
 * Created on 7/29/17.
 */
public class Base {
    Rectangle rectangle;
    public float energy;
    boolean isDestroyed = false;

    public Base (float startingEnergy){
        rectangle = new Rectangle(0 - Utils.BASE_SIZE / 2, 0 - Utils.BASE_SIZE / 2, Utils.BASE_SIZE, Utils.BASE_SIZE);
        this.energy = startingEnergy;
    }

}
