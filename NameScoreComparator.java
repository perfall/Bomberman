package bomberman;

import java.util.Comparator;

/**
 * The NameScoreComparator class implements Comparator and defines a comparing function that is used to sort the
 * highscores of the game. The method takes two strings as parameters, splits them to retrieve their respective
 * integers and then compares them.
 */
public class NameScoreComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        int i1 = Integer.parseInt(s1.split(":")[1]);
        int i2 = Integer.parseInt(s2.split(":")[1]);
        if (i1 < i2) {
            return -1;
        } else if (i1 > i2) {
            return 1;
        } else {
            return 0;
        }
    }
}