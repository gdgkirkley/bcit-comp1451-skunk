import java.util.Scanner;

public class Input 
{
	private Scanner keyboardScanner;
	
	public Input()
	{
		keyboardScanner = new Scanner(System.in);
	}
	
	public Scanner getScanner()
	{
		return keyboardScanner;
	}

	public boolean hasNext()
	{
		return keyboardScanner.hasNext();
	}
	
	public boolean hasNextInt()
	{
		return keyboardScanner.hasNextInt();
	}
	
	public int getIntInput()
	{
		return keyboardScanner.nextInt();
	}
	
	public String getStringInput()
	{
		return keyboardScanner.next();
	}
}