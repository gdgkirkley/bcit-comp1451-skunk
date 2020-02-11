import java.util.Random;

public class RandomGenerator 
{
	private Random 	r;
	private int		value;
	private int		min;
	private int		max;
	
	public RandomGenerator(int min, int max)
	{
		r 		 = new Random();
		this.min = min;
		this.max = max;
	}
	
	public int getNewRandom()
	{
		value 	= r.nextInt(this.max - this.min + 1) + this.min;
		return value;
	}

	public double getNewSmallRandom()
	{
		return r.nextDouble();
	}
}