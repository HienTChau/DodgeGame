package project6;

/**
 * Coin is a bad GameObject that is shot out by enemies such as
 * SadDoge and Creps. It will deduce 1 health from playable character.
 */

public class Coin extends GameObject{
    public Coin(Location location){
        this.location.setY(location.getY());
        this.location.setX(location.getX());
        imageFilename = "Dogecoin.png";
        height = 20;
        width = 20;
        removeBorder();
    }
    public void move(){
        location.setY(location.getY() + 3);
    }
    /**
     *Coin has confusing hit box so this override to make their hit box smaller while
     * keep the original display's size.
     */
    public void draw(SimpleCanvas canvas){
        canvas.drawImage(getLeftX()-10, getTopY()-10, imageFilename, width+10, height+10);

    }

}
