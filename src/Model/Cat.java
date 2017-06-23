package Model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Handles random movements unique to the Cat. Ensures no backtracking unless no other options
 * available.
 */
public class Cat extends Entity {

    Cat (int col, int row, char lastMove) {
        super(col, row, lastMove);
        this.type = 3;
    }

    public void randomMove()
    {
        lastMove = reverseLastMove();
        boolean moved = false;

        int randomMove = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
        int tries = 0;

        while (moved == false){
            if (tries >= possibleMoves.size()) {
                movePosition(possibleMoves.get(randomMove));
                moved = true;
            }
            else if (possibleMoves.get(randomMove) == lastMove) {
                randomMove++;
                if (randomMove >= possibleMoves.size()){
                    randomMove = 0;
                    tries++;
                }
            }
            else {
                movePosition(possibleMoves.get(randomMove));
                moved = true;
            }
        }
    }

    private char reverseLastMove(){
        if (lastMove == 'w'){
            lastMove = 's';
        }
        else if (lastMove == 'a'){
            lastMove = 'd';
        }
        else if (lastMove == 's'){
            lastMove = 'w';
        }
        else if (lastMove == 'd'){
            lastMove = 'a';
        }
        return lastMove;
    }
}

