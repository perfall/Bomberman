package bomberman;

/**
 * This class is extended in Player and Enemy, and serves the purpose of defining the methods that each child shares. It is
 * constructed with pixelcoordinates for starting points as well as pixelsPerStep, which defines how many pixels each will
 * "walk" in each move. Apart from getters, the methods used for actually moving the characters, in regards to their
 * pixel-coordinates, are also included in this class.
 */
public class AbstractCharacter
{
    // Constants are static by definition.
    private final static int SIZE = 30;
    private int x;
    private int y;
    private int pixelsPerStep;

    protected AbstractCharacter(int x, int y, int pixelsPerStep) {
	this.x = x;
	this.y = y;
	this.pixelsPerStep = pixelsPerStep;
    }

    public enum Move
    {
	/**
	 * This enum-type represents the movement down, where the y-coordinate is +1.
	 */
	DOWN(0, 1),
	/**
	 * This enum-type represents the movement up, where the y-coordinate is -1.
	 */
	UP(0, -1), // Inspector complains on UP being to short, but this name is optimal.
	/**
	 * This enum-type represents the movement right, where the x-coordinate is +1.
	 */
	RIGHT(1, 0),
	/**
	 * This enum-type represents the movement left, where the x-coordinate is -1.
	 */
	LEFT(-1, 0);

	private final int deltaX;
	private final int deltaY;

	// Inspector complains on enum not being used, which is not the case.
	Move(final int deltaX, final int deltaY) {
	    this.deltaX = deltaX;
	    this.deltaY = deltaY;
	}
    }

    public void move(Move move) {
	y += move.deltaY * pixelsPerStep;
	x += move.deltaX * pixelsPerStep;
    }

    public void moveBack(Move currentDirection) {
	if (currentDirection == Move.DOWN) {
	    move(Move.UP);
	} else if (currentDirection == Move.UP) {
	    move(Move.DOWN);
	} else if (currentDirection == Move.LEFT) {
	    move(Move.RIGHT);
	} else if (currentDirection == Move.RIGHT) {
	    move(Move.LEFT);
	}
    }

    public int getSize() {
	return SIZE;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public int getColIndex() {
	return Floor.pixelToSquare(x);
    }

    public int getRowIndex() {
	return Floor.pixelToSquare(y);
    }
}
