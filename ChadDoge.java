package project6;

/**
 * ChadDoge is one of the 2 playable character. He can shoot 2 streams
 * of DogTreat and get one more health. He has bigger hit box than Doge.
 */

public class ChadDoge extends Doge{
    public ChadDoge(){
        setImageFilename("chadDoge.png");
        location = new Location(450/2,400);
        height = 40;
        width = 50;
        health = 6;
    }

    /**
     *Chad Doge has confusing hit box so this override to make their hit box smaller while
     * keep the original display's size.
     */
    public void draw(SimpleCanvas canvas){
        canvas.drawImage(getLeftX(), getTopY()-10, imageFilename, width, height+10);
    }

}
