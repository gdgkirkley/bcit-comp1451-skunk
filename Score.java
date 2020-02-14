/**
 * @author Gabe Kirkley
 * @version 1.0.0
 * A score with methods to increment and decrement
 */
public class Score
{
    private int score;

    /**
     * Create a new score 
     * @param startingScore - the starting value for this score
     */
    public Score(int startingScore)
    {
        checkScore(startingScore);

        this.score       = startingScore;
    }

    /**
     * Add a value to the existing score
     * @param scoreToAdd - a positive value to add to the score
     */
    public void incrementScore(int scoreToAdd) 
	{
		if(scoreToAdd < 0)
		{
			throw new IllegalArgumentException("Score " + scoreToAdd + " is invalid for this method.");
		}

        int newScore = this.score + scoreToAdd;

		this.setScore(newScore);
    }
    
    /**
     * Subtract a value from the existing score
     * @param scoreToSubtract - A positive value to subtract from the score
     */
    public void decrementScore(int scoreToSubtract)
	{
		if(scoreToSubtract < 0)
		{
			throw new IllegalArgumentException("Score " + scoreToSubtract + " is invalid for this method.");
		}

        int newScore = this.score - scoreToSubtract;

		this.setScore(newScore);
    }

    /**
     * @return the current score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * @param score - a new value for the score
     */
    public void setScore(int score)
    {
        checkScore(score);

        this.score = score;
    }

    /**
     * Check if the score is valid
     * @param startingScore - a positive value for the score
     */
    private void checkScore(int startingScore)
    {
        if(startingScore < 0)
        {
            throw new IllegalArgumentException("Score cannot start below 0");
        }
    }
}