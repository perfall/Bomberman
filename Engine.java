package bomberman;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the engine of the Bomberman-game, where everything is initialised. It calls to Menu, which is the initial gameframe,
 * when a game is started it instantiates a Floor and a BombermanFrame. It is also home to the timer-methods that create the
 * time-aspects of the game, more specifically, the tick-method that can be viewed as the main loop. With ReadWithScanner and
 * WriteToFile Engine manages settings-parameters and highscores. This class is never instantiated, which is why it only has
 * static methods.
 */
public final class Engine
{
    // LOGGER is static because there should only be one logger per class.
    private static final Logger LOGGER = Logger.getLogger(Engine.class.getName() );
    // Constants are static by definition.
    //Default values if file-reading fails.
    private static final int TIME_STEP = 30;
    private static final int NANO_SECONDS_IN_SECOND = 1000000000;
    // The following fields are static since they they are general settings or properties for the game.
    private static int width = 5;
    private static int height = 5;
    private static int nrOfEnemies = 1;

    private static long startTime = 0L;
    private static long elapsedTime = 0L;
    private static Timer clockTimer = null;

    private Engine() {}

    // Main methods are static by definition.
    public static void main(String[] args) {
	// Inspector complains on the result of Menu not being used, as well as the main-method of Engine,
	// but a frame is opened within it, and that instance is disposed when the user starts a new game.
	// It is unnecessary to store it.
	new Menu();
    }

    // This method is static because it is the "general" instantiation of the game, and is not
    // belonging to an Engine-object. Engine is never instantiated.
    public static void startGame() {
	readSettings();
	Floor floor = new Floor(width, height, nrOfEnemies);
	BombermanFrame frame = new BombermanFrame("Bomberman", floor);
	startTime = System.nanoTime();
	floor.addFloorListener(frame.getBombermanComponent());

	Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		tick(frame, floor);
	    }
	};
	clockTimer = new Timer(TIME_STEP, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

    // Engine is never instantiated, therefore this method needs to be static.
    private static void readSettings() {
	Parser parser = new Parser("settings.txt");
	try {
	    parser.processLineByLine();
	    //Set the values read from file.
	    width = parser.getWidth();
	    height = parser.getHeight();
	    nrOfEnemies = parser.getNrOfEnemies();
	} catch (IOException ioe) {
	    JOptionPane.showMessageDialog(null, "Trouble reading from settings file: " + ioe.getMessage() +
						". Creating new file with standard settings.");
	    writeNewSettings();
	}
    }

    // Engine is never instantiated, therefore this method needs to be static.
    private static void gameOver(BombermanFrame frame, Floor floor) {
	long endTime = System.nanoTime();
	elapsedTime = (endTime - startTime) / NANO_SECONDS_IN_SECOND;
	clockTimer.stop();

	if (floor.getEnemyList().isEmpty()) {
	    if (getRank() > 10) {
		JOptionPane.showMessageDialog(null, "You beat the game! But you didn't make the highscorelist. :(");
	    } else {
		String name =
			JOptionPane.showInputDialog("You beat the game and made the highscorelist!\nPlease input your handle");
		writeHighscore(name, (int) elapsedTime);
	    }

	} else {
	    JOptionPane.showMessageDialog(null, "Game over!");
	    UIManager.put("JOptionPane.okButtonMnemonic", "O");  // for Setting 'O' as mnemonic
	}
	frame.dispose();
	if (askUser("Do you want to play again?")) {
	    startGame();
	}
    }

    // Engine is never instantiated, therefore this method needs to be static.
    private static void writeHighscore(String name, int newHighscore) {
	ArrayList<String> highscoreList = HighscoreFrame.readHighscore();
	highscoreList.remove(highscoreList.size() - 1);
	String newScore = name + ":" + Integer.toString(newHighscore);
	highscoreList.add(newScore);
	highscoreList = HighscoreFrame.sortHighscore(highscoreList);
	WriteToFile.writeToFile(highscoreList, "highscore.txt");
    }

    // Engine is never instantiated, therefore this method needs to be static.
    private static void writeNewSettings() {
	Collection<String> settingsList = new ArrayList<>();
	String strWidth = "width=" + Integer.toString(width);
	settingsList.add(strWidth);
	String strHeight = "height=" + Integer.toString(height);
	settingsList.add(strHeight);
	String strNrOfEnemies = "nrOfEnemies=" + Integer.toString(nrOfEnemies);
	settingsList.add(strNrOfEnemies);
	WriteToFile.writeToFile(settingsList, "settings.txt");
    }

    // Engine is never instantiated, therefore this method needs to be static.
    private static int getRank() {
	try (BufferedReader br = new BufferedReader(new FileReader("highscore.txt"))) {
	    int positionCounter = 0;
	    while (br.readLine() != null && positionCounter < 10) {
		positionCounter++;
		int listedScore = Integer.parseInt(br.readLine().split(":")[1]);
		if (elapsedTime < listedScore) {
		    return positionCounter;
		}
	    }
	} catch (IOException ioe) {
	    LOGGER.log(Level.FINE, "Trouble reading from the file: " + ioe.getMessage() );
	    System.out.println("Trouble reading from the file: " + ioe.getMessage());
	}
	return 10 + 1;
    }

    // Engine is never instantiated, therefore this method needs to be static.
    private static boolean askUser(String question) {
	return JOptionPane.showConfirmDialog(null, question, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // Engine is never instantiated, therefore this method needs to be static.
    private static void tick(BombermanFrame frame, Floor floor) {
	if (floor.getIsGameOver()) {
	    gameOver(frame, floor);
	} else {
	    floor.moveEnemies();
	    floor.bombCountdown();
	    floor.explosionHandler();
	    floor.characterInExplosion();
	    floor.notifyListeners();
	}
    }
}
