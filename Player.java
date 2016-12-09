package bomberman;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;


/**
 * This class represents the player in the game, it extends AbstractCharacter to gain methods
 * that are shared between Enemy and Player. The constructor does not need any parameters, the
 * start-coordinates are always the same, and therefore constants. The Player can pickup powerups,
 * which is why the fields explosionRadius and bombCount are in the class, these are potentially adjusted.
 */
public class Player extends AbstractCharacter
{
    // LOGGER is static because there should only be one logger per class.
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName() );
    // Constants are static by definition.
    private final static int PLAYER_START_X = 60;
    private final static int PLAYER_START_Y = 60;
    private final static int PLAYER_PIXELS_BY_STEP = 4;
    private int explosionRadius;
    private int bombCount;
    private Floor floor;
    /**
     * This method calls on the move-function with the parameter UP, which will move the player one pixelstep up.
     */
    public Action up = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    movePlayer(Move.UP);

	}
    };
    /**
     * This method calls on the move-function with the parameter RIGHT, which will move the player one pixelstep right.
     */
    public Action right = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    movePlayer(Move.RIGHT);

	}
    };
    /**
     * This method calls on the move-function with the parameter DOWN, which will move the player one pixelstep down.
     */
    public Action down = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    movePlayer(Move.DOWN);

	}
    };
    /**
     * This method calls on the move-function with the parameter LEFT, which will move the player one pixelstep left.
     */
    public Action left = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    movePlayer(Move.LEFT);

	}
    };

    /**
     * This Action-method adds a bomb to the bomblist if the demands are fulfilled.
     */
    public Action dropBomb = new AbstractAction()
    {
	public void actionPerformed(ActionEvent e) {
	    if(!floor.squareHasBomb(getRowIndex(), getColIndex()) && floor.getBombListSize() < getBombCount()){
		floor.addToBombList(new Bomb(getRowIndex(), getColIndex(), getExplosionRadius()));
	    }
	    floor.notifyListeners();
	}
    };

    public Player(BombermanComponent bombermanComponent, Floor floor) {
	super(PLAYER_START_X, PLAYER_START_Y, PLAYER_PIXELS_BY_STEP);
	explosionRadius = 1;
	bombCount = 1;
	this.floor = floor;
	setPlayerButtons(bombermanComponent);
    }

    public void setPlayerButtons(BombermanComponent bombermanComponent){
	bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
	bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
	bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
	bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
	bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "dropBomb");
	bombermanComponent.getActionMap().put("moveRight", right);
	bombermanComponent.getActionMap().put("moveLeft", left);
	bombermanComponent.getActionMap().put("moveUp", up);
	bombermanComponent.getActionMap().put("moveDown", down);
	bombermanComponent.getActionMap().put("dropBomb", dropBomb);
    }

    public int getBombCount() {
	return bombCount;
    }

    public void setBombCount(int bombCount) {
	this.bombCount = bombCount;
    }

    public int getExplosionRadius() {
	return explosionRadius;
    }

    public void setExplosionRadius(int explosionRadius) {
	this.explosionRadius = explosionRadius;
    }

    private void movePlayer(Move move) {
	move(move);
	if(floor.collisionWithBlock(this)){
	    moveBack(move);
	}
	if(floor.collisionWithBombs(this)){
	    moveBack(move);
	}
	if(floor.collisionWithEnemies()){
	    floor.setIsGameOver(true);
	}

	floor.checkIfPlayerLeftBomb();
	floor.collisionWithPowerup();
	floor.notifyListeners();
	LOGGER.info("Updated Player properties");
    }

}
