package project6;

import java.awt.*;
import java.awt.event.KeyEvent;

public class AlarmGame {
    private SimpleCanvas canvas;
    private boolean isVisible;

    private GameObject myself;
    private AlarmClock clock;


    public AlarmGame()
    {
        isVisible = false;
        canvas = new SimpleCanvas(600, 400, "Turn off the alarm clock!");

        myself = new GameObject();
        myself.setLocation(new Location(40, 80));
        myself.setImageFilename("chadDoge.png");
        myself.removeBorder();

        clock = new AlarmClock(5000);
        clock.setLocation(new Location(400, 300));
    }

    /** Draw the state of the game on the canvas. */
    public void draw()
    {
        canvas.clear(); // always clear first.

        // Draw all the game objects
        myself.draw(canvas);
        clock.draw(canvas);

        // Draw status message
        canvas.setPenColor(Color.BLACK);
        canvas.drawString(300, 10, "Time left: " + clock.getTimeLeft());

        // Show the window if needs be.
        if (!isVisible) {
            canvas.show();
            isVisible = true;
        }
        else {
            canvas.update();
        }
    }

    /** Is the game over? */
    private boolean isGameOver() {
        return myself.overlaps(clock); // you should probably replace this.
    }

    /** Start the game running. */
    public void runGame()
    {
        // Create starting screen
        draw();
        canvas.setPenColor(Color.BLACK);
        canvas.drawString(10, 10, "Click canvas to start!");
        canvas.update();
        canvas.waitForClick();

        // Loop while the game is not over:
        while (!isGameOver()) {
            // update the state of the game
            clock.update();
            if (clock.getTimeLeft() > 0) {
                System.out.println("Time left: " + clock.getTimeLeft());
            }

            // draw the state of the game
            draw();

            // handle keyboard
            handleKeyboard();

            // handle mouse
            handleMouse();

            canvas.pause(50); // can adjust this higher or lower as needed
        }
        // Game is now over,
        // draw the state of the game one last time in case there were any last updates
        // that we need to show.
        draw();

        canvas.setPenColor(Color.BLUE);
        if (clock.getTimeLeft() > 0) {
            canvas.drawStringCentered(300, 200, "You win!", 20);
        }
        else {
            canvas.drawStringCentered(300, 200, "You lose!", 20);
        }
        canvas.update();
    }

    private void handleKeyboard() {
        Location myLocation = myself.getLocation();
        if (canvas.isKeyPressed(KeyEvent.VK_UP)&&(myLocation.getY() - 10) >0){
            myLocation.setY(myLocation.getY() - 10);
        }
        if (canvas.isKeyPressed(KeyEvent.VK_DOWN)&&(myLocation.getY() + 10)< canvas.getHeight()) {
            myLocation.setY(myLocation.getY() + 10);
        }
        if (canvas.isKeyPressed(KeyEvent.VK_LEFT)&&((myLocation.getX() - 10)>0)) {
            myLocation.setX(myLocation.getX() - 10);
        }
        if (canvas.isKeyPressed(KeyEvent.VK_RIGHT)&&(myLocation.getX() - 10)< canvas.getWidth()) {
            myLocation.setX(myLocation.getX() + 10);
        }
    }

    private void handleMouse() {
        if (canvas.isMousePressed()) {
            System.out.println("Mouse click detected at " + canvas.getMouseClickX() + " " + canvas.getMouseClickY());
        }
    }
}
