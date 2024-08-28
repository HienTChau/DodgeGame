package project6;

/**
 * Healthpack is a good GameObjects that will add 1 health
 * to the playable character. The maximum health that Doge
 * can have is 8.
 */
public class Healthpack extends Powerup {
    public Healthpack(){
        super();
        height = 18;
        width = 24;
        setImageFilename("medkit.png");
        location = new Location((int)(Math.random() * 420), -50);
        removeBorder();
    }
}
