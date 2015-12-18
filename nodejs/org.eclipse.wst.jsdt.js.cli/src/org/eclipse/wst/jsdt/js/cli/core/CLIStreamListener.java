package org.eclipse.wst.jsdt.js.cli.core;

import java.util.Scanner;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;
/**
 * Stream listener for CLI output. 
 * 
 * @author Gorkem Ercan
 *
 */
public class CLIStreamListener implements IStreamListener {
	private static final String ERROR_PREFIX = "Error:"; //$NON-NLS-1$
	private static final String WIN_ERROR_PATTERN = "is not recognized as an internal or external command"; //$NON-NLS-1$
	private StringBuffer errorMessage = new StringBuffer();
	private final StringBuffer message = new StringBuffer();
	
	@Override
	public void streamAppended(String text, IStreamMonitor monitor) {
		final Scanner scanner = new Scanner(text);
		boolean error = false;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			line = line.trim(); // remove leading whitespace
			if(line.startsWith(ERROR_PREFIX)){
				error = true;
				errorMessage = errorMessage.append(line.substring(ERROR_PREFIX.length(), line.length()));
			} else if (line.contains(WIN_ERROR_PATTERN) && !line.isEmpty()) {
				error = true;
				errorMessage = errorMessage.append(line);
			} else {
				if(error){
					errorMessage.append(System.lineSeparator());	
					errorMessage.append(line);
				}
				else{
					message.append(line);
					message.append(System.lineSeparator());
				}
			}
		}
		scanner.close();
	}

	/**
	 * Returns the last error message encountered. 
	 * Can return empty String if no error messages are present
	 * @return last error message or empty string
	 */
	public String getErrorMessage() {
		return errorMessage.toString();
	}
	
	/**
	 * Returns all the messages returned 
	 * excluding the error messages
	 * @return
	 */
	public String getMessage(){
		return message.toString();
	}

}
