package project6;

import project6.GameObject;

import java.awt.*;

/**
 * BoneTreat is an object that is shot out by the playable character. It moves
 * upwards in the canvas and will deduce 1 health from the enemy.
 */

public class BoneTreat extends GameObject {
    public BoneTreat(Location location){
        this.location.setY(location.getY());
        this.location.setX(location.getX());
        imageFilename = "boneTreat.png";
        height = 30;
        width = 25;
        removeBorder();
    }

    /**
     * Move the bone up the canvas by 10 for every frame.
     */
    public void move(){
        location.setY(location.getY()-10);
    }

}
