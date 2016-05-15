package piskvorky;

import java.io.IOException;

/**
 * Classes which implement this interface can be save into human-readable file.
 *
 * @author Ivan Kratochvíl
 */
public interface Saveable {

    /**
     * This method should take the important properties from class and make a
     * string out of them. Example: "name = Ivan\nai = false"
     *
     * @return
     */
    public String toSaveString();

    /**
     * This method should take particular string and take important properties
     * from it. Example: from string "name = Ivan\nai = false", it should load
     * Ivan into variable name and false into variable ai.
     *
     * @param saveString string to load
     * @throws IOException when the string is corrupted
     */
    public void loadFromString(String saveString) throws IOException;
}
