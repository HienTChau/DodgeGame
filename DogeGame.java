package project6;
/*
      Name: Jessie Chau, Logan Wu
      Date: 4/25/2024
      Class: CS142
      Pledge: I have neither given nor received unauthorized aid on this program.
      Description: DogeGame is a bullet hell game where you can fight Sad Doge and his army of
       Creps as either Basic Doge or Chad Doge. You have to defeat Sad Doge and his
       Creps by shooting bone treats at them, and various powerups will spawn to
       help you fight. You win if you are able to take down Sad Doge, but you lose
       if you run out of hearts.
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class DogeGame {
    private SimpleCanvas canvas;
    final int intro = 0;
    final int pickChar = 1;
    final int instruction = 2;
    final int game = 3;
    final int gameOver = 4;
    private int mode;
    private boolean isVisible;
    /**
     * Main hero, controlled by player
     */
    private Doge doge;
    /**
     * Main villain
     */
    private SadDoge sad;
    private ArrayList<BoneTreat> treats;
    private ArrayList<Coin> coins;
    private ArrayList<Crep> creps;
    private ArrayList<Powerup> powerups;
    private ArrayList<Healthpack> healthpacks;
    private int dogeShootSpeed = 200;
    private int enemyShootSpeed = 3000;
    private int gameTime = 0;
    private int powerupTimeRemaining = 0;
    private int score = 0;
    /**
     * Constructor for the DogeGame class.
     */
    public DogeGame(){
        isVisible = false;
        canvas = new SimpleCanvas(700, 600, "Dodge the doge bullet!");
        canvas.setBackgroundColor(new Color(64, 55, 45));
        treats = new ArrayList<>();
        coins = new ArrayList<>();
        creps = new ArrayList<>();
        powerups = new ArrayList<>();
        healthpacks = new ArrayList<>();
        sad = new SadDoge();
    }
    /**
     * Show the title screen, selection screen, and post-game dialogue.
     */
    public void runGame(){
        drawBackGround();

        canvas.setPenColor(new Color(117, 95, 66));
        canvas.drawImage(170,75,"title.png",350,350);
        canvas.drawString(255, 400, "Click canvas to start!",20);
        canvas.update();
        canvas.waitForClick();

        drawBackGround();


        int character = selectCharacter();

        drawGameIntro(character);

        if (character == 0){
            this.doge = new Doge();
            playGame(this.doge);
        }else{
            this.doge = new ChadDoge();
            playGame(this.doge);
        }



        if (sad.isDead()){
            canvas.drawImage(0,0,"4.png");
        }
        else {
            canvas.drawImage(0,0,"5.png");
        }



        canvas.update();
    }

    /**
     * Show pre-game dialogue.
     */
    private void drawGameIntro(int character) {
        canvas.clear();
        drawBackGround();
        canvas.drawImage(0,0,"1.png",700,600);
        canvas.update();
        canvas.waitForClick();


        canvas.clear();
        drawBackGround();
        if (character==1){
            canvas.drawImage(0,0,"3.png",700,600);
        }
        else{
            canvas.drawImage(0,0,"2.png",700,600);
        }
        canvas.update();
        canvas.waitForClick();
    }

    /**
     * After a brief intro sequence, start the game after the screen is clicked.
     */
    private void playGame(Doge doge){

        introseq();

        drawBackGround();

        while (!isGameOver()){
            //if there is still powerup time remaining, double doge shoot speed
            if (powerupTimeRemaining > 0){
                dogeShootSpeed = 100;
            }
            else {dogeShootSpeed = 200;}

            //move SadDoge
            sadDogeMove();
            //spawn spawnables
            spawn();
            //let doge shoot out bones
            dogeShoot(doge);

            //let enemies shoot
            enemyShoot(sad);

            //after 30 seconds in the sadDoge will start shooting circular coins
            if(gameTime>30000){
                enemyShootCircle(sad);
            }


            for (Crep c: creps){
                enemyShoot(c);
            }

            //make objects move and consider to remove it
            handleObjects();

            drawGame();
            handleKeyboard();
            canvas.pause(50);
            gameTime += 50;
            if (powerupTimeRemaining > 0){
                powerupTimeRemaining -= 50;
            }
        }


    }

    /**
     * Make objects move and remove them if needed.
     */
    private void handleObjects(){
        //move the creps on the map + remove the coins that are out of map
        for (int i =0; i < creps.size();i++){
            Crep cr = creps.get(i);
            if (cr.getTopY() - 40 >canvas.getHeight()) {
                creps.remove(i);
            } else if (cr.overlaps(doge)) {
                doge.getHit();
                creps.remove(i);
            } else if (cr.isDead()){
                creps.remove(i);
            }
            cr.move();
        }
        //move the bones on the map + remove the bones that are out of map
        for (int i =0; i < treats.size();i++){
            BoneTreat t = treats.get(i);
            if (i<treats.size()-1){ //this might look dumb, but it is to stop .get(i) go out of bounds.
                if (treats.get(i).getBottomY() + 40 <0) {
                    treats.remove(i);
                }
                else if (treats.get(i).overlaps(sad)){
                    sad.getHit();
                    treats.remove(i);
                    score+=200;
                }
                else {
                    for (Crep c : creps) {
                        if (treats.get(i).overlaps(c)) {
                            c.getHit();
                            treats.remove(i);
                            score+=500;
                        }
                    }
                }
            }

            t.move();
        }
        //move the coins on the map + remove the coins that are out of map
        for (int i =0; i < coins.size();i++){
            Coin c = coins.get(i);
            if (coins.get(i).getTopY() - 40 >canvas.getHeight() || coins.get(i).getBottomY() + 40 <0|| coins.get(i).getRightX() + 40 <0) {
                coins.remove(i);
            } else if (coins.get(i).overlaps(doge)) {
                doge.getHit();
                coins.remove(i);
            }
            c.move();
        }

        //if doge comes into contact with powerup
        for (int i = 0; i < powerups.size(); i++){
            Powerup p = powerups.get(i);
            if (p.getTopY() - 40 >canvas.getHeight()) {
                powerups.remove(i);
            } else if (p.overlaps(doge)) {
                powerupTimeRemaining += 10000;
                powerups.remove(i);
            }
            p.move();
        }
        for (int i = 0; i < healthpacks.size(); i++){
            Healthpack h = healthpacks.get(i);
            if (h.getTopY() - 40 >canvas.getHeight()) {
                healthpacks.remove(i);
            } else if (h.overlaps(doge)) {
                if (doge.health < 8) {
                    doge.health++;
                }
                healthpacks.remove(i);
            }
            h.move();
        }
        sad.move();
    }
    /**
     * Draw the battlefield.
     */
    private void drawGame(){
        canvas.clear();
        drawBackGround();
        doge.draw(canvas);
        sad.draw(canvas);

        for (Crep cr: creps){
            cr.draw(canvas);
        }
        for (BoneTreat t: treats){
            t.draw(canvas);
        }
        for (Coin c: coins){
            c.draw(canvas);
        }
        for (Powerup p: powerups){
            p.draw(canvas);
        }
        for (Healthpack h: healthpacks){
            h.draw(canvas);
        }

        drawStats();

        if (!isVisible) {
            canvas.show();
            isVisible = true;
        }
        else {
            canvas.update();
        }
    }
    /**
     * Let Sad Doge shoot in a circle every so often.
     */
    public void enemyShootCircle(Doge enemy){
        if(gameTime%(enemyShootSpeed+20) == 0){
            double angle =0;
            while(angle<2*Math.PI){
                CircleCoin circleCoin = new CircleCoin(enemy.getLocation(),angle);
                coins.add(circleCoin);
                angle = angle + Math.PI/15;
            }
        }


    }
    /**
     * Every enemyShootSpeed every enemy (including boss and creep) will shoot out 1 coin
     */
    public void enemyShoot(Doge enemy){
        if(gameTime%enemyShootSpeed == 0){
            Coin c = new Coin(enemy.getLocation());
            coins.add(c);
        }

    }
    /**
     *Every dogeShootSpeed the basic doge will shoot out 1 bone, the chad doge will shoot out 2 bones.
     */
    public void dogeShoot(Doge doge){
        if (gameTime%dogeShootSpeed == 0){
            if (doge instanceof ChadDoge){
                BoneTreat b = new BoneTreat(new Location(doge.getCenterX()-20, doge.getCenterY()));
                treats.add(b);
                BoneTreat c = new BoneTreat(new Location(doge.getCenterX()+20, doge.getCenterY()));
                treats.add(c);
            }
            else {
                BoneTreat b = new BoneTreat(doge.getLocation());
                treats.add(b);
            }
        }

    }
    /**
     * Allow the user to control the Doge.
     */
    private void handleKeyboard() {
        Location myLocation = doge.getLocation();
        if ((canvas.isKeyPressed(KeyEvent.VK_UP)||canvas.isKeyPressed(KeyEvent.VK_W))&&(myLocation.getY() - 5) >0){
            myLocation.setY(myLocation.getY() - 5);
        }
        if ((canvas.isKeyPressed(KeyEvent.VK_DOWN)||canvas.isKeyPressed(KeyEvent.VK_S))&&(myLocation.getY() + 5)< canvas.getHeight()) {
            myLocation.setY(myLocation.getY() + 5);
        }
        if ((canvas.isKeyPressed(KeyEvent.VK_LEFT)||canvas.isKeyPressed(KeyEvent.VK_A))&&((myLocation.getX() - 5)>0)) {
            myLocation.setX(myLocation.getX() - 5);
        }
        if ((canvas.isKeyPressed(KeyEvent.VK_RIGHT)||canvas.isKeyPressed(KeyEvent.VK_D))&&(myLocation.getX() - 5)< canvas.getWidth() - (250 + doge.getWidth())) {
            myLocation.setX(myLocation.getX() + 5);
        }
    }

    /**
     * Returns true if either the Doge or Sad Doge is dead.
     */
    private boolean isGameOver(){
        return (doge.isDead()||sad.isDead());
    }

    /**
     * Create the character selection screen and allow user to select Doge or Chad Doge.
     */
    private int selectCharacter(){
        canvas.setPenColor(new Color(230, 193, 145));
        canvas.drawFilledRectangle(150,150,175,300);
        canvas.drawFilledRectangle(375,150,175,300);
        canvas.drawImage(195,210,"Cheems.png",100,140);
        canvas.drawImage(385,175,"chadDoge.png",160,160);
        canvas.setPenColor(new Color(117, 95, 66));
        canvas.drawString(175, 140, "Choose your character",35);

        canvas.drawString(205, 370, "Basic Doge",15);
        canvas.drawString(430, 370, "Chad Doge",15);
        canvas.drawString(205, 385, "x1 damage",15);
        canvas.drawString(205, 400, "More agility",15);
        canvas.drawString(430, 385, "x2 damage",15);
        canvas.drawString(430, 400, "+1 health",15);
        canvas.update();
        while (true){
            canvas.waitForClick();
            int xClick = canvas.getMouseClickX();
            int yClick = canvas.getMouseClickY();
            System.out.println("Mouse click detected at " + canvas.getMouseClickX() + " " + canvas.getMouseClickY());

            if (xClick>=150 && xClick<=325 && yClick>=150 && yClick <= 450){
                return 0;
            } else if (xClick>=375 && xClick<=550 && yClick>=150 && yClick <= 450) {
                return 1;
            }

        }
    }
    /**
     * Debugging for mouse clicks.
     */
    private void handleMouse() {
        if (canvas.isMousePressed()) {
            System.out.println("Mouse click detected at " + canvas.getMouseClickX() + " " + canvas.getMouseClickY());
        }
    }

    /**
     * Draw the background for the title screen, selection screen, and the battlefield.
     */
    private void drawBackGround() {
        canvas.drawImage(400,0,"pawBG.jpg",450,600);

        canvas.drawImage(0,0,"pawBG.jpg",450,600);

        if (!isVisible) {
            canvas.show();
            isVisible = true;
        }
        else {
            canvas.update();
        }

    }

    /**
     * Draw stats to the right of the battlefield, including Sad Doge health, Doge health, time elapsed, and score.
     */
    private void drawStats() {
        //draw SadDoge health
        canvas.setPenColor(new Color(64, 55, 45));
        canvas.drawFilledRectangle(450,0,250,600);
        canvas.setPenColor(Color.WHITE);
        canvas.drawString(460, 25, "SadDoge health:", 20);
        canvas.setPenColor(Color.BLACK);
        canvas.setLineThickness(3);
        canvas.drawRectangle(460, 40, 230, 19);
        canvas.setPenColor(Color.GREEN);
        canvas.drawFilledRectangle(463, 43, (int)(sad.health * 0.448), 13);

        //draw Doge health
        canvas.setPenColor(Color.WHITE);
        canvas.drawString(460, 85, "Doge health:", 20);
        for (int i = 0; i < doge.health; i++){
            canvas.drawImage(460 + i * 30, 95, "heartIcon.png", 20, 20);
        }

        //draw time
        canvas.drawString(575, 580, "Time: " + gameTime / 60000 + " min " + (gameTime % 60000) / 1000 + " s");

        //draw score
        String formattedScore = String.format("Score: %010d", score);
        canvas.drawString(460, 140, formattedScore, 25);
    }

    /**
     * Spawn Creps, Powerups, and Healthpacks at certain time intervals.
     */
    private void spawn() {
        if (gameTime % 8000 == 0) {
            creps.add(new Crep());
        }
        if ((gameTime - 10000) % 30000 == 0) {
            powerups.add(new Powerup());
        }
        if ((gameTime - 30000) % 60000 == 0) {
            healthpacks.add(new Healthpack());
        }
    }

    /**
     * Move Sad Doge across the battlefield.
     */
    private void sadDogeMove() {
        if (sad.getLocation().getY() < 30 || sad.getLocation().getY() > 200){
            sad.setSpeedY(-sad.getSpeedY());
        }
        if (sad.getLocation().getX() < 30 || sad.getLocation().getX() > 420){
            sad.setSpeedX(-sad.getSpeedX());
        }
        if (gameTime % 5000 == 0) {
            sad.setSpeedX((int)((Math.random()*10) - 5));
            sad.setSpeedY((int)((Math.random()*10) - 5));
        }
    }

    /**
     * Draw short intro sequence, move the Sad Doge into the canvas, and let the
     * user click to begin the fight.
     */
    private void introseq() {
        while (sad.getTopY() < 100){
            canvas.clear();
            canvas.drawImage(0,0,"pawBG.jpg",450,600);
            sad.location.setY(sad.location.getY() + 10);
            sad.draw(canvas);
            canvas.update();
            canvas.pause(50);
        }
        canvas.drawString(175, 300, "Click to begin!",20);
        canvas.update();
        canvas.waitForClick();
    }

}
