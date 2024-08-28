package project6;

/**
 * Creps is an enemy that will spam randomly from the top
 * of the map and can shoot out coins. It deduces playable
 * character's health by 1 if collided.
 */
public class Crep extends SadDoge {
    public Crep(){
        super();
        health = 5;
        height = 30;
        width = 30;
        setImageFilename("crep.png");
        location = new Location((int)(Math.random() * 420), -50);
    }
    public void move(){
        location.setY(location.getY() + 1);
    }
}
