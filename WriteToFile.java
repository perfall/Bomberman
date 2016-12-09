package bomberman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class WriteToFile currently only has one method, which is updating the highscore-file. The method takes a highscore-list
 * as parameter and simply puts each elements on one row in the file.
 */
public final class WriteToFile
{
    private static final Logger LOGGER = Logger.getLogger(WriteToFile.class.getName() );

    private WriteToFile() {}

    // WriteToFile is never instantiated, but is a class that has the general method of writing to file, which
    // is why it is static.
    public static void writeToFile(Iterable<String> intList, String fileName) {
	try {
	    File logFile = new File(fileName);
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile))) {
		try {
		    for (String row : intList) {
			LOGGER.log(Level.FINE, "Writing row to file:", row );
			writer.write(row);
			writer.write("\n");
		    }
		} finally {
		    writer.close();
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
