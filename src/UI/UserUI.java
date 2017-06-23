package UI;

import java.util.Scanner;

/**
 * Handles all user interactions with input validation.
 */
public class UserUI {

    static private char charInput;
    
    public void displayStartMenu()
    {
        System.out.println("----------------------------------------");
        System.out.println("Welcome to Cat and Mouse Maze Adventure!");
        System.out.println("by Parmvir Johal and Kyle Henry");
        System.out.println("----------------------------------------");
        displayHelpScreen();
    }

    public void displayHelpScreen()
    {
        System.out.println("DIRECTIONS:");
        System.out.println("\tFind 5 cheese before a cat eats you!");

        System.out.println("LEGEND:");
        System.out.println("\t#: Wall");
        System.out.println("\t@: You (a mouse)");
        System.out.println("\t!: Cat");
        System.out.println("\t$: Cheese");
        System.out.println("\t.: Unexplored space");

    }

     public void displayNextTurn(int currentCheese)
    {
        final int TOTAL_CHEESE = 5;
        String input = "";
        boolean correctInput = false;
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Cheese collected: " + currentCheese + " of " + TOTAL_CHEESE);
        System.out.println("Enter your move [WASD?]: ");

        while(correctInput == false) {
            input = keyboard.next();
            correctInput = errorCheck(input.toLowerCase());

            if(correctInput == false)
            {
                System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
            }
        }
        if (input.toLowerCase().charAt(0) == 'm'){
            DisplayMaze.printWholeMaze();
        }
        else if (input.toLowerCase().charAt(0) == '?'){
            displayHelpScreen();
        }
        else {
            setUserInput(input.toLowerCase().charAt(0));
        }
    }

     private boolean errorCheck(String input)
    {
        char inputValue = input.charAt(0);
        boolean correctValue = inputValue == 'w' || inputValue == 'a' || inputValue == 's' || inputValue == 'd'
                || inputValue == 'm' || inputValue == '?';
        if (correctValue)
        {
            return true;
        }
        else{
            return false;
        }
    }

    static public void setUserInput(char input){

        System.out.println(input);

        charInput = input;
    }

    static public  char getUserInput() {

        System.out.println("get "+charInput);
        return charInput;
    }

    public static void invalidMoveMessage(){
        System.out.println("There's a wall there. Try a different direction.");
    }

    public void playerWonMessage() {

        System.out.println("Congratulations! You won!");
    }

    public void playerCaughtMessage() {
        System.out.println("I'm sorry, you have been eaten!");
    }
}
