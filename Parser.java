package bomberman;

/**
 * Copied from http://www.javapractices.com/topic/TopicAction.do?Id=42
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Assumes UTF-8 encoding. JDK 7+. */
public class Parser {
    // LOGGER is static because there should only be one logger per class.
    private static final Logger LOGGER= Logger.getLogger(Parser.class.getName() );
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    // PRIVATE
    private final Path fFilePath;
    private String fileName;
    private int width;
    private int height;
    private int nrOfEnemies;
    private ArrayList<String> nameScores = new ArrayList<>();

    /**
     Constructor.
     @param aFileName full name of an existing, readable file.
     */
    public Parser(String aFileName){
	fFilePath = Paths.get(aFileName);
	fileName = aFileName;
    }

    /** Template method that calls {@link #processLine(String)}.  */
    public final void processLineByLine() throws IOException {
	try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
	    while (scanner.hasNextLine()){
		processLine(scanner.nextLine());
	    }
	}
    }

    protected void processLine(String aLine){
	//use a second Scanner to parse the content of each line
	try (Scanner scanner = new Scanner(aLine)) {
	    switch (fileName) {
		case "highscore.txt":
		    String nameScore = scanner.next();
		    LOGGER.log(Level.FINE, "Line is", quote(nameScore.trim()));

		    //log("Line is : " + quote(nameScore.trim()));
		    nameScores.add(nameScore);
		    break;
		case "settings.txt":
		    if (scanner.hasNext()) {
			scanner.useDelimiter("="); // Inspector want a try-with-resource statement,
			// however, it is already within in a statement.
			String name = scanner.next();
			int value = Integer.parseInt(scanner.next());
			switch (name) {
			    case "width":
				width = value;
				break;
			    case "height":
				height = value;
				break;
			    case "nrOfEnemies":
				nrOfEnemies = value;
			}
		    }
		    break;
		default:
		    LOGGER.info("Empty or invalid line. Unable to process.");
		    break;
	    }
	}
    }

    public ArrayList<String> getNameScores() {
	return nameScores;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public int getNrOfEnemies() {
	return nrOfEnemies;
    }

    private String quote(String aText){
	String quote = "'";
	return quote + aText + quote;
    }
}
