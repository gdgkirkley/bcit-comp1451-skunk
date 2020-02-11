public class Player
{
	private Score 	score;
	private boolean computer;
    private	String	name;

    public Player(String name, int startingScore, boolean computer)
    {
        score 		= new Score(startingScore);
		this.name 	= name;
		this.computer  = computer;
	}
	
	public boolean isComputer()
	{
		return this.computer;
	}

	public Score getScore() 
	{
		return this.score;
	}

	public int getScoreValue()
	{
		return this.score.getScore();
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
}