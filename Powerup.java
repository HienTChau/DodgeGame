package project6;

/**
 * PowerUp is a good GameObject that will double up the shoot speed of the playable
 * character for 10 seconds.
 */

public class Powerup extends GameObject {
    public Powerup (){
        super();
        height = 32;
        width = 18;
        setImageFilename("sword.png");
        location = new Location((int)(Math.random() * 420), -50);
        removeBorder();
    }

    public void move(){
        location.setY(location.getY() + 1);
    }
}
