package project6;

/**
 * Doge (or BasicDoge) is a playable character.
 * It can move up, down, right, left on canvas.
 * It will continuously shoot out a stream of BoneTreats.
 */
public class Doge extends GameObject{
    protected int health;

    public Doge(){
        setImageFilename("Cheems.png");
        location = new Location(450/2,400);
        height = 40;
        width = 30;
        super.removeBorder();
        health = 5;
    }
    public void getHit(){
        health--;
        System.out.println("Doge health at: "+ health);
    }

    /**
     *Return if true the Doge is dead (out of health).
     */
    public boolean isDead(){
        return health <= 0;
    }
}
