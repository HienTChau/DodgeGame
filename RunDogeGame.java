package project6;
/*
      Name: Jessie Chau, Logan Wu
      Date: 4/25/2024
      Class: CS142
      Pledge: I have neither given nor received unauthorized aid on this program.
      Description: DogeGame is a game where you can fight Sad Doge and his army of
       Creps as either Basic Doge or Chad Doge. You have to defeat Sad Doge and his
       Creps by shooting bone treats at them, and various powerups will spawn to
       help you fight. You win if you are able to take down Sad Doge, but you lose
       if you run out of hearts.
  */

public class RunDogeGame {
    public static void main(String[] args)
    {
        DogeGame theGame = new DogeGame();
        theGame.runGame();
    }
}
