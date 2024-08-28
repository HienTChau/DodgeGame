package project6;

/**
 * SadDoge is the main enemy of the game. It will move randomly on the map
 * and can shoot out 2 types of Coin: Coin and CircleCoin. The player will
 * win if SadDoge's health is = 0.
 */

public class SadDoge extends Doge{
    private int speedX = 0;
    private int speedY = 0;
    public SadDoge(){
        super();
        setImageFilename("SadCheems.png");
        location = new Location(225,-80);
        height = 60;
        width = 45;
        super.removeBorder();
        health = 500;

    }

    public void move(){
        location.setX(location.getX() + speedX);
        location.setY(location.getY() + speedY);
    }
    public int getSpeedX(){
        return speedX;
    }
    public int getSpeedY(){
        return speedY;
    }
    public void setSpeedX(int speed){
        speedX = speed;
    }
    public void setSpeedY(int speed){
        speedY = speed;
    }

}
