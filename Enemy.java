package bomberman;

/**
 * This class represents the enemies that are present in the game, it extends AbstractCharacter
 * to gain methods that are shared between Enemy and Player. The constructor needs an x and y
 * coordinate that will be the start-position of the current enemy. The constructor also needs
 * the boolean vertical, that decides whether the enemy move navigate up/down or left/right. Apart
 * from a getter-method Enemy has a randomDirection-method that decides what direction the enemy
 * will navigate through initially, as well as a changeDirection that simply reveres the current Direction.
 */

public class Enemy extends AbstractCharacter
{
    private Move currentDirection;

    public Enemy(int x, int y, boolean vertical) {
        super(x, y, 1);
        currentDirection = randomDirection(vertical);
    }

    public void changeDirection() {
        if (currentDirection == Move.DOWN) {
            currentDirection = Move.UP;
        } else if (currentDirection == Move.UP) {
            currentDirection = Move.DOWN;
        } else if (currentDirection == Move.LEFT) {
            currentDirection = Move.RIGHT;
        } else {
            currentDirection = Move.LEFT;
        }
    }

    public Move getCurrentDirection() {
        return currentDirection;
    }

    private Move randomDirection(boolean vertical) {
        assert Move.values().length == 4;
        int pick = (int) (Math.random() * (Move.values().length-2));
        if(vertical) {
            return Move.values()[pick];
        }
        else{
            return Move.values()[pick+2];
        }

    }
}
