package org.com.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 *
 * This class contains the base method for logging all game activities.
 *
 * @author Arvind Lakshmanan
 */

public class LogUtil {

    private static final String LOG_FILE_DIR;

    static {
        // Generate a unique log file name
        String l_timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        LOG_FILE_DIR = System.getProperty("user.dir") + "/src/main/resources/Logs/application-logs-" + l_timestamp + ".log";
    }

    /**
     * Logs a message with a specified logging level and class name.
     *
     * @param className The name of the class where the log message originates.
     * @param level The logging level of the message (e.g., INFO, WARNING, SEVERE).
     * @param message The log message to be recorded.
     */
    public static void Logger(String className, Level level, String message)
    {
        Logger l_logger = Logger.getLogger(className);
        try {
            // Removing the console handler from the default logger to prevent logs getting printed in the console.
            Logger l_rootLogger = Logger.getLogger("");
            Handler[] l_handlers = l_rootLogger.getHandlers();
            for (Handler l_handler : l_handlers) {
                if (l_handler instanceof ConsoleHandler) {
                    l_rootLogger.removeHandler(l_handler);
                }
            }

            // Storing the log message into a file
            FileHandler l_fileHandler = new FileHandler(LOG_FILE_DIR, true);
            l_fileHandler.setFormatter(new CustomFormatter());
            l_logger.addHandler(l_fileHandler);
            l_logger.log(level, "\n" + message);
            l_fileHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Custom formatter for log messages.
     * This formatter formats the log records to include the timestamp, class name, and the log message.
     */
    static class CustomFormatter extends Formatter {
        /**
         *
         * @param p_record The log record containing the log message and metadata.
         * @return A formatted string representing the log record.
         */
        @Override
        public String format(LogRecord p_record) {
            return String.format("Time :: %d || Class name :: %s || Message :: %s%n", p_record.getMillis(), p_record.getLoggerName(), p_record.getMessage());
        }
    }
}
