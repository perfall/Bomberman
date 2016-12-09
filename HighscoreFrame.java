package bomberman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class build the frame of the highscore window. It defines its size via constants and creates the layout
 * with JFrame methods. It also stores the highscore in an ArrayList which in the method readHighscore(), that
 * creates a Parser object is updated. It is also home to mnemonic-actions that activate the buttons in the frame.
 *
 */
public class HighscoreFrame extends JFrame implements ActionListener {
    private static final int FRAME_SIZE = 400;
    private static final int FONT_SIZE = 16;
    private final JPanel panel = new JPanel();
    /**
     * This action is called when the user want to close the highscore-frame.
     */
    public Action closeAction = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    dispose();
	}
    };
    private Iterable<String> nameScores = new ArrayList<>();
    // Buttons
    private JButton close;

    public HighscoreFrame() throws HeadlessException {
	super("Highscore");
	nameScores = readHighscore();
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setAlignmentX(Component.CENTER_ALIGNMENT);
	showHighscores();


	// Buttons
	String closeLabel = "<html><a style='text-decoration:underline'>C</a>lose</html>";
	close = new JButton(closeLabel);
	close.setToolTipText("Click this button to close window and get back to main menu. Alternatively press 'C'.");
	close.addActionListener(closeAction);
	close.setAlignmentX(Component.CENTER_ALIGNMENT);
	setShortcuts();
	panel.add(close);
	add(panel);
	//////////////////
	//End of buttons//
	//////////////////

	setSize(FRAME_SIZE, FRAME_SIZE);
	setVisible(true);
    }

    // This method is static since the reading of highScore is not dependent on the instaniated object of HighscoreFrame.
    public static ArrayList<String> readHighscore() {
	ArrayList<String> highscoreList;
	Parser parser = new Parser("highscore.txt");
	try {
	    parser.processLineByLine();
	} catch (IOException ioe) {
	    System.out.println("Trouble reading from the file: " + ioe.getMessage());
	}

	highscoreList = parser.getNameScores();
	assert highscoreList.size() < 10+1;
	return sortHighscore(highscoreList);
    }

    // This method is static because it is a general sorting method.
    public static ArrayList<String> sortHighscore(ArrayList<String> highscoreList) {
	Collections.sort(highscoreList, new NameScoreComparator());
	return highscoreList;
    }

    private void showHighscores() {
	for(String nameScore : nameScores) {

	    Font font = new Font("Jokerman", Font.PLAIN, FONT_SIZE);
	    JLabel textLabel = new JLabel((nameScore));
	    textLabel.setFont(font);
	    textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(textLabel);
	}
    }

    public void actionPerformed( ActionEvent evt) {
    }

    private void setShortcuts(){
	panel.getInputMap().put(KeyStroke.getKeyStroke("C"), "close");
	panel.getActionMap().put("close", closeAction);
    }
}
