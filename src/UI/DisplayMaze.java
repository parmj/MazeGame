package UI;

/**
 * Converts the maze from a 2D array of integers to characters, for user interaction.
 */
public class DisplayMaze {


   public DisplayMaze(int[][] array)
    {
        setDisplayMaze(array);
        setDisplayWholeMaze(array);
    }

    private char[][] displayMaze = new char[Model.Maze.MAXCOL][Model.Maze.MAXROW];
    private static char[][] displayWholeMaze = new char[Model.Maze.MAXCOL][Model.Maze.MAXROW];

    public char[][] getDisplayMaze(int[][] array)
    {
        setDisplayMaze(array);
        return displayMaze;
    }

    private void setDisplayMaze(int[][] array)
    {
        for(int c = 0; c < Model.Maze.MAXCOL; c++)
        {
            for(int r = 0; r < Model.Maze.MAXROW; r++)
            {
                displayMaze[c][r] = convertToChar(array[c][r]);
            }
        }
    }

    // 0 = Model.Player
    // 1/-1 = Wall / Invis
    // 2/-2 = Empty / Invis
    // 3/-3 = Model.Cat / Invis
    // 4 = Model.Cheese
    // 5 = Border
    private static char convertToChar(int value)
    {
        char charValue = ' ';

        if(value < 0)
            charValue = '.';
        else if(value == 1 || value == 5)
            charValue = '#';
        else if(value == 2)
            charValue = ' ';
        else if(value == 0)
            charValue = '@';
        else if(value == 3)
            charValue = '!';
        else if(value == 4)
            charValue = '$';

        return charValue;
    }

    public void printMaze(){
        for (int r = 0; r < Model.Maze.MAXROW; r++) {
            System.out.print("\n");
            for (int c = 0; c < Model.Maze.MAXCOL; c++) {
                System.out.print(" " + (displayMaze[c][r]) + " ");
            }
        }
        System.out.println("\n\n");
    }

    public static void printWholeMaze(){
        for (int r = 0; r < Model.Maze.MAXROW; r++) {
            System.out.print("\n");
            for (int c = 0; c < Model.Maze.MAXCOL; c++) {
                System.out.print(" " + (displayWholeMaze[c][r]) + " ");
            }
        }
        System.out.println("\n\n");
    }


    private static void setDisplayWholeMaze(int[][] array)
    {
        for(int c = 0; c < Model.Maze.MAXCOL; c++)
        {
            for(int r = 0; r < Model.Maze.MAXROW; r++)
            {
                displayWholeMaze[c][r] = convertToChar(Math.abs(array[c][r]));
            }
        }
    }
}
