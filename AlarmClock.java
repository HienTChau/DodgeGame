package project6;

import java.awt.*;

public class AlarmClock extends GameObject {
    private int timeLeft = 5000;

    public AlarmClock(int time)
    {
        timeLeft = time;
        setImageFilename("alarm.png");
        setBorderColor(Color.BLUE);
    }

    public void update() {
        timeLeft -= 50;
        if (timeLeft <= 0) {
            setBorderColor(Color.RED);
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
