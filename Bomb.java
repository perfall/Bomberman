package bomberman;

/**
 * The class defines the methods and parameters needed for the bombs in the game. The constructor needs row-
 * and column-indexes to define their place on the floor, as well as the explosionRadius which decides how
 * many squares the explosion will cover. A few constants that defines the size and countdown of the bombs,
 * and getters/setters are included in the class.
 */
public class Bomb
{
    // Constants are static by definition.
    private final static int BOMBSIZE = 30;
    private final static int STARTCOUNTDOWN = 100;
    private int timeToExplosion = STARTCOUNTDOWN;
    private final int rowIndex;
    private final int colIndex;
    private int explosionRadius;
    private boolean playerLeft;

    public Bomb(final int rowIndex, final int colIndex, int explosionRadius) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.explosionRadius = explosionRadius;
        playerLeft = false;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    // This method is static since every bomb has the same size.
    public static int getBOMBSIZE() {
        return BOMBSIZE;
    }

    public int getTimeToExplosion() {
        return timeToExplosion;
    }

    public void setTimeToExplosion(final int timeToExplosion) {
        this.timeToExplosion = timeToExplosion;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public boolean isPlayerLeft() {
        return playerLeft;
    }

    public void setPlayerLeft(final boolean playerLeft) {
        this.playerLeft = playerLeft;
    }
}