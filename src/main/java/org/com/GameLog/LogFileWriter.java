package org.com.GameLog;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * The `LogFileWriter` class is responsible for writing log entries to a specified log file.
 * It implements the `Observer` interface to receive updates from observable objects,
 * such as `LogEntryBuffer`, and writes the received log information to the file.
 *
 * Key Features:
 * - Ensures the log file and its parent directories are created if they do not exist.
 * - Appends log entries to the file whenever notified by the observable.
 * - Handles file I/O operations with error handling for potential exceptions.
 *
 * This class is part of the logging mechanism in the game, ensuring that all
 * significant actions and events are recorded persistently.
 *
 * @author Devasenan Murugan
 */
public class LogFileWriter implements Observer, Serializable {
    private final Path d_logFilePath;


    /**
     * Constructor for LogFileWriter.
     *
     * @param p_logFilePath The complete path to the log file.
     */
    public LogFileWriter(Path p_logFilePath) {
        this.d_logFilePath = p_logFilePath;

        File logFile = p_logFilePath.toFile();
        File logDir = logFile.getParentFile();
        if (logDir != null && !logDir.exists()) {
            logDir.mkdirs();
        }
    }

    /**
     * Writes the log info to the game log file
     *
     * @param p_observable Observable object
     */
    @Override
    public void update(Observable p_observable) {
        if (p_observable instanceof LogEntryBuffer) {
            String l_logInfo = ((LogEntryBuffer) p_observable).getActionInfo();
            writeToLogFile(l_logInfo);
        }
    }

    /**
     * Writes the given info to the log file.
     *
     * @param p_info The info to write.
     */
    private void writeToLogFile(String p_info) {
        try {
            Files.write(d_logFilePath, (p_info + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException l_e) {
            System.err.println("Error writing to log file: " + l_e.getMessage());
        }
    }
}
