package bomberman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class build the frame of the main menu window. It defines its size via a constant and creates
 * the layout with JFrame methods. It is also home to mnemonic-actions that activate the buttons in the frame.
 * Since Menu is the first visible window of the used, it is also here that a new game is called upon, as well
 * as showing the HighscoreFrame if the user wants this view.
 */
public class Menu extends JFrame implements ActionListener {
    // Buttons
    // Constants are static by definition.
    private static final int MENU_SIZE = 150;
    private final JPanel buttonPanel = new JPanel();
    /**
     * This Action disposes the current frame to make room for a new game.
     */
    public Action newGameAction = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    //dispose();
	    Engine.startGame();
	}
    };
    /**
     * This Action is called when the user clicks on the highscore button, it creates a new HighscoreFrame.
     */
    public Action highscoreAction = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    // Inspector complains on the result of HighscoreFrame not being used, but a frame is opened within it, and
	    // that instance is disposed when the user closes it. It is unnecessary to store it.
	    new HighscoreFrame();
	}
    };
    /**
     * This Action terminates the current process.
     */
    public Action quitAction = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    System.exit(0);
	}
    };
    private JButton newGame;
    private JButton highscore;
    private JButton quit;

    public Menu() throws HeadlessException {
	super("Bomberman");
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	setLayout(new FlowLayout());
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

	// Buttons
	String newGameLabel = "<html><a style='text-decoration:underline'>N</a>ew Game</html>";
	String highscoreLabel = "<html><a style='text-decoration:underline'>H</a>ighscore</html>";
	String quitLabel = "<html><a style='text-decoration:underline'>Q</a>uit</html>";
	newGame = new JButton(newGameLabel);
	newGame.setToolTipText("Click this button to start a new game. Alternatively press 'N'.");
	highscore = new JButton(highscoreLabel);
	highscore.setToolTipText("Click this button to view the highscore. Alternatively press 'H'.");
	quit = new JButton(quitLabel);
	quit.setToolTipText("Click this button quit the game. Alternatively press 'Q'.");
	newGame.addActionListener(newGameAction);
	highscore.addActionListener(highscoreAction);
	quit.addActionListener(quitAction);
	setShortcuts();
	buttonPanel.add(newGame);
	buttonPanel.add(highscore);
	buttonPanel.add(quit);

	add(buttonPanel);

	//////////////////
	//End of buttons//
	//////////////////

	setSize(MENU_SIZE, MENU_SIZE);
	setVisible(true);
    }

    public void actionPerformed( ActionEvent evt) {

    }

    private void setShortcuts(){
	buttonPanel.getInputMap().put(KeyStroke.getKeyStroke("N"), "newGame");
	buttonPanel.getInputMap().put(KeyStroke.getKeyStroke("H"), "hs");
	buttonPanel.getInputMap().put(KeyStroke.getKeyStroke("Q"), "q");
	buttonPanel.getActionMap().put("newGame", newGameAction);
	buttonPanel.getActionMap().put("hs", highscoreAction);
	buttonPanel.getActionMap().put("q", quitAction);
    }
}
