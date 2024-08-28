package project6;

import java.awt.*;
import java.awt.event.KeyEvent;

/** This class represents a generic game on a canvas.  */
public class Game {
    private SimpleCanvas canvas;
    private boolean isVisible;

    // add more instance variables here as necessary.

    /** Construct a new instance of the game with a given width and height. */
    public Game(int width, int height)
    {
        isVisible = false;
        canvas = new SimpleCanvas(width, height, "My Game");

        // Create game objects here if they should appear at the beginning of the game.
    }

    /** Draw the state of the game on the canvas. */
    public void draw()
    {
        canvas.clear(); // always clear first.

        // Call draw() on your GameObjects here.

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
        return false;
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
            // Update the state of the game.
            // This might involve any number of things, but probably
            // involves moving any GameObjects however they should be moved,
            // updating any GameObjects that should be updated,
            // and checking for overlaps.

            // Draw the state of the game.
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

        // Do any other game wrapup things here.
    }

    /** Do any keyboard-related things here.  You can test for any key being
     * pressed.  Use this list of "VK_" constants to find the one you want:
     * https://docs.oracle.com/en/java/javase/13/docs/api/java.desktop/java/awt/event/KeyEvent.html
     * You can take any other actions you want on your GameObjects here, based
     * on keypresses, like moving objects, interacting with other objects (attacking, defending, throwing
     * or giving objects, etc), or changing the state of an object (such as switching
     * between spells to cast, for instance).  You can also check for overlaps here, of course.
     */
    private void handleKeyboard() {
        if (canvas.isKeyPressed(KeyEvent.VK_UP)) {
            System.out.println("Up arrow is pressed.");
        }
        if (canvas.isKeyPressed(KeyEvent.VK_DOWN)) {
            System.out.println("Down arrow is pressed.");
        }
        if (canvas.isKeyPressed(KeyEvent.VK_LEFT)) {
            System.out.println("Left arrow is pressed.");
        }
        if (canvas.isKeyPressed(KeyEvent.VK_RIGHT)) {
            System.out.println("Right arrow is pressed.");
        }
        // add any other tests you want!
    }

    /** Do any mouse-related things here.  This would probably involve using the
     * location of a mouse click to do things like interact with a clicked GameObject
     * in some way.  You can use the isInside() method in GameObject to test if a
     * mouse click (x, y) is inside the object.
     */
    private void handleMouse() {
        if (canvas.isMousePressed()) {
            int clickX = canvas.getMouseClickX();
            int clickY = canvas.getMouseClickY();
            System.out.println("Mouse click detected at " + clickX + " " + clickY);
        }
    }
}
