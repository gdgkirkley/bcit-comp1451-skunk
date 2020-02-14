/**
 * @author Gabe Kirkley
 * @version 1.0.0
 * A generic player for a Game. Has a Score object by default.
 */
public class Player
{
	private Score 	score;
	private boolean computer;
    private	String	name;

	/**
	 * Create a player
	 * @param name 			- the name or identifier of the player
	 * @param startingScore - the starting score for the game
	 * @param computer		- is the player playable or automated?
	 */
    public Player(String name, int startingScore, boolean computer)
    {
        score 		= new Score(startingScore);
		this.name 	= name;
		this.computer  = computer;
	}
	
	/**
	 * @return is the player automated or playable?
	 */
	public boolean isComputer()
	{
		return this.computer;
	}

	/**
	 * @return the Score object.
	 */
	public Score getScore() 
	{
		return this.score;
	}

	/**
	 * @return the value of the score in the Score object
	 */
	public int getScoreValue()
	{
		return this.score.getScore();
	}

	/**
	 * @return the player's name or identifier
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * @param name - a new name or identifier for the player
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
}