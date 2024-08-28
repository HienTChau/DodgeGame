package project6;
import java.lang.Math;

/**
 * CircleCoin is a subclass of Coin. This coin move in a straight
 * direction but with different angle depending on its initial position
 * and angle.
 */
public class CircleCoin extends Coin{
    private double vx, vy;
    private int speed=7;
    public CircleCoin(Location location, double angle) {
        super(location);
        vx = Math.cos(angle)*speed;
        vy = Math.sin(angle)*speed;
    }
    public void move(){
        location.setY((int) (location.getY() + vy));
        location.setX((int)(location.getX()+vx));
    }
}
