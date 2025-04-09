package org.com.Utils;

import org.com.Handlers.GamePhaseHandler;

import java.io.*;

/**
 * The SaveGameUtil class is used to handle saving and loading game state.
 * It Serializes and deserializes the GamePhaseHandler object.
 */
public class SaveGameUtil implements Serializable {

    private static final String SAVE_DIR = System.getProperty("user.dir") + "/src/main/resources/GameSaves/";

    // To check if the directory exists.
    static {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Saves the game state to a specified file.
     *
     * @param p_gameState The current GamePhaseHandler representing the game state.
     * @param p_fileName  The name of the file to save to.
     * @throws IOException If an I/O error occurs during writing.
     */
    public static void saveGame(GamePhaseHandler p_gameState, String p_fileName) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(SAVE_DIR + p_fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(p_gameState);
            System.out.println("Game successfully saved to " + p_fileName);
        }
    }

    /**
     * Loads the game state from a specified file.
     *
     * @param p_fileName The name of the file to load from.
     * @return A deserialized GamePhaseHandler representing the previous game state.
     * @throws IOException            If an I/O error occurs during reading.
     * @throws ClassNotFoundException If the GamePhaseHandler class is not found.
     */
    public static GamePhaseHandler loadGame(String p_fileName) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(SAVE_DIR + p_fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            System.out.println("Game successfully loaded from " + p_fileName);
            return (GamePhaseHandler) in.readObject();
        }
    }
}