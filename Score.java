public class Score
{
    private int score;

    public Score(int startingScore)
    {
        checkScore(startingScore);

        this.score = startingScore;
    }

    public void incrementScore(int scoreToAdd) 
	{
		if(scoreToAdd < 0)
		{
			throw new IllegalArgumentException("Score " + scoreToAdd + " is invalid for this method.");
		}

		int newScore = this.score + scoreToAdd;
		this.setScore(newScore);
    }
    
    public void decrementScore(int scoreToSubtract)
	{
		if(scoreToSubtract < 0)
		{
			throw new IllegalArgumentException("Score " + scoreToSubtract + " is invalid for this method.");
		}

		int newScore = this.score - scoreToSubtract;
		this.setScore(newScore);
	}

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        checkScore(score);

        this.score = score;
    }

    private void checkScore(int startingScore)
    {
        if(startingScore < 0)
        {
            throw new IllegalArgumentException("Score cannot start below 0");
        }
    }
}