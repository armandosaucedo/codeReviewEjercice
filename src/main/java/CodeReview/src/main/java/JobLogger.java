package CodeReview.src.main.java;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLogger {
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	private static Map dbParams;
	private static Logger logger;

	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map dbParamsMap) {
		logger = Logger.getLogger("MyLog");
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
	}

	public static void LogMessage(String messageText, boolean message, boolean warning, boolean error)
			throws BusinessException {
		messageText = messageText.trim();
		if (messageText == null || messageText.length() == 0) {
			return;
		}
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new BusinessException("Invalid configuration");
		}
		if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
			throw new BusinessException("Error or Warning or Message must be specified");
		}

		if (logToFile) {
			writeLogToFile(messageText);
		}

		if (logToConsole) {
			writeLogToConsole(messageText);
		}

		if (logToDatabase) {
			writeLogToDatabase(messageText, message, warning, error);
		}
	}

	private static void writeLogToFile(String messageText) {

		try {
			File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			FileHandler fh;
			fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
			logger.addHandler(fh);
			logger.log(Level.INFO, messageText);
		} catch (SecurityException | IOException e) {
			logger.info("Cannot save log to file:" + e.getMessage());
		}
	}

	private static void  writeLogToConsole(String messageText) {
		ConsoleHandler ch = new ConsoleHandler();
		logger.addHandler(ch);
		logger.log(Level.INFO, messageText);
	}

	private static void writeLogToDatabase(String messageText, boolean message, boolean warning, boolean error) {
		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbParams.get("userName"));
		connectionProps.put("password", dbParams.get("password"));

		try {
			connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
					+ ":" + dbParams.get("portNumber") + "/", connectionProps);
			Statement stmt = connection.createStatement();

			int t = 0;
			if (message && logMessage) {
				t = 1;
			}

			if (error && logError) {
				t = 2;
			}

			if (warning && logWarning) {
				t = 3;
			}
			stmt.executeUpdate("insert into Log_Values('" + messageText + "', " + t + ")");
		} catch (SQLException e) {
			logger.info("Cannot save log to database:" + e.getMessage());
		}

	}
}
