package org.com.GameLog;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * The `LogManager` class is responsible for managing the logging mechanism in the game.
 * It initializes the log buffer and log writer, and provides a method to log actions.
 *
 * Key Features:
 * - Creates a log file with a timestamped name in the `resources/logs` directory.
 * - Uses `LogEntryBuffer` to store log entries temporarily.
 * - Observes the buffer using `LogFileWriter` to persist log entries to the file.
 * - Provides a static method to log actions, ensuring a centralized logging mechanism.
 *
 * This class ensures that all game actions are logged persistently and efficiently.
 *
 * @author Devasenan Murugan
 */
public class LogManager implements Serializable {
    private static final LogEntryBuffer d_logBuffer;
    private static final LogFileWriter d_logWriter;

    static {
        Path l_logPath = Paths.get(System.getProperty("user.dir"), "src/main/resources/logs", "gamelog-" + LocalDateTime.now().toString().replaceAll(":", ".") + ".log");
        d_logBuffer = new LogEntryBuffer();
        d_logWriter = new LogFileWriter(l_logPath);
        d_logBuffer.addObserver(d_logWriter);
    }

    /**
     * Logs an action by setting the action information in the log buffer
     * The LogEntryBuffer will notify the LogFileWriter to write the log entry
     *
     * @param p_action The action information to log
     */
    public static void logAction(String p_action) {
        d_logBuffer.setActionInfo(p_action);
    }
}
