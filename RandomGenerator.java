import java.util.Random;

/**
 * @author Gabe Kirkley
 * @version 1.0.0
 * A wrapper for a Random to get a new random number
 */
public class RandomGenerator 
{
	private Random 	r;
	private int		value;
	private int		min;
	private int		max;
	
	/**
	 * Create a new random generator by passing the min and max values
	 * @param min - the minimum value to return
	 * @param max - the maximum value to return
	 */
	public RandomGenerator(int min, int max)
	{
		r 		 = new Random();
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Get a new random int value
	 * @return a random int value between the min and max specified
	 */
	public int getNewRandom()
	{
		value 	= r.nextInt(this.max - this.min + 1) + this.min;
		return value;
	}

	/**
	 * Get a new random double value between 0 and 1
	 * @return a randomn double value
	 */
	public double getNewSmallRandom()
	{
		return r.nextDouble();
	}
}