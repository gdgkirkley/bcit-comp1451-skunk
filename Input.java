import java.util.Scanner;

/**
 * @author Gabe Kirkley
 * @version 1.0.0
 * Wrapper for a System.in scanner
 */
public class Input 
{
	private Scanner keyboardScanner;
	
	public Input()
	{
		keyboardScanner = new Scanner(System.in);
	}
	
	/**
	 * @return the scanner object
	 */
	public Scanner getScanner()
	{
		return keyboardScanner;
	}

	/**
	 * @return true if and only if this scanner has another token
	 */
	public boolean hasNext()
	{
		return keyboardScanner.hasNext();
	}
	
	/**
	 * @return true if and only if this scanner's next token is a valid int value
	 */
	public boolean hasNextInt()
	{
		return keyboardScanner.hasNextInt();
	}
	
	/**
	 * @return the int scanned from the input
	 */
	public int getIntInput()
	{
		return keyboardScanner.nextInt();
	}
	
	/**
	 * @return the next token
	 */
	public String getStringInput()
	{
		return keyboardScanner.next();
	}
}