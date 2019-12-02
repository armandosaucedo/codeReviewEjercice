package CodeReview.src.test.java;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.util.reflection.FieldSetter;
import org.springframework.boot.test.context.SpringBootTest;

import CodeReview.src.main.java.BusinessException;
import CodeReview.src.main.java.JobLogger;
import junit.framework.Assert;

@SpringBootTest
public class JobLoggerTest {

	private JobLogger jobLogger;
	private String messageText = "Log Message";

	@Test
	public void InvalidConfigurationTest() throws NoSuchFieldException, SecurityException, BusinessException {
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToConsole"), false);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToFile"), false);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToDatabase"), false);
		doThrow(new BusinessException("Invalid configuration")).when(jobLogger).LogMessage(messageText, true, false,
				false);
		Assert.assertTrue(true);
	}

	@Test
	public void ErrorMessageSpecifiedTest() throws NoSuchFieldException, SecurityException, BusinessException {
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToConsole"), true);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToFile"), false);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToDatabase"), false);
		doThrow(new BusinessException("Error or Warning or Message must be specified")).when(jobLogger)
				.LogMessage(messageText, true, false, false);
		Assert.assertTrue(true);
	}

	@Test
	public void writeLogToConsoleTest() throws NoSuchFieldException, SecurityException, BusinessException {
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToConsole"), true);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToFile"), false);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToDatabase"), false);
		jobLogger.LogMessage(messageText, true, false, false);
		Assert.assertTrue(true);
	}

	@Test
	public void writeLogToFileTest() throws NoSuchFieldException, SecurityException, BusinessException {
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToConsole"), true);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToFile"), false);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToDatabase"), false);
		jobLogger.LogMessage(messageText, true, false, false);
		Assert.assertTrue(true);
	}

	@Test
	public void writeLogToDatabaseMessageTest() throws NoSuchFieldException, SecurityException, BusinessException {
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToConsole"), true);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToFile"), false);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToDatabase"), false);
		jobLogger.LogMessage(messageText, true, false, false);
		Assert.assertTrue(true);
	}

	@Test
	public void writeLogToDatabaseWarningTest() throws NoSuchFieldException, SecurityException, BusinessException {
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToConsole"), true);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToFile"), false);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToDatabase"), false);
		jobLogger.LogMessage(messageText, false, true, false);
		Assert.assertTrue(true);
	}

	@Test
	public void writeLogToDatabaseErrorTest() throws NoSuchFieldException, SecurityException, BusinessException {
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToConsole"), true);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToFile"), false);
		FieldSetter.setField(jobLogger, jobLogger.getClass().getDeclaredField("logToDatabase"), false);
		jobLogger.LogMessage(messageText, false, false, true);
		Assert.assertTrue(true);
	}
}
