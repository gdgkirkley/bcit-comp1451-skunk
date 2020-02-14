/**
 * @author Gabe Kirkley
 * @version 1.0.0
 * A player for the game of SKUNK
 */
public class SkunkPlayer extends Player
{
    public static final int    POINT_GAP_DIVISOR       = 100;
    public static final int    COMPUTER_SIT_THRESHOLD  = 1;

    public static final double COMPUTER_TIE_VALUE      = 0.3;
    public static final double COMPUTER_WINNING_VALUE  = 0.6;

    public static final double  ONE_SKUNK_ODDS         = 0.167;
    public static final double  DOUBLE_SKUNK_ODDS      = 0.03125;
    public static final double  TRIPLE_ODDS            = 0.0278;

    private boolean standing;
    private Score   roundScore;

    /**
     * Create a player for the game of SKUNK and set up a new
     * Score to track the player's round score
     * @param name          - the name or identifier of the player
     * @param startingScore - the starting score of the player
     * @param computer      - is the player playable or computer controlled
     */
    public SkunkPlayer(String name, int startingScore, boolean computer)
    {
        super(name, startingScore, computer);

        standing = true;

        this.roundScore = new Score(0);
    }

    /**
     * Set the round score value for this player
     * @param score - the value of the round score for this player
     */
    public void setRoundScore(int score)
    {
        this.roundScore.setScore(score);
    }

    /**
     * Get the Score object for the round score
     * @return the Score object for the round score
     */
    public Score getRoundScore()
    {
        return this.roundScore;
    }

    /**
     * Get the value of the round Score object
     * @return the value of the round Score object
     */
    public int getRoundScoreValue()
    {
        return this.roundScore.getScore();
    }

    /**
     * Set whether the player is standing or sitting
     * @param standing - if the player is standing
     */
    public void setStanding(boolean standing)
    {
        this.standing = standing;
    }

    /**
     * Get whether the player is sitting or standing
     * @return true if the player is standing
     */
    public boolean isStanding()
    {
        return this.standing;
    }

    /**
     * Make a sitting or standing choice for a computer player
     * @param currentRound - the current round of the game
     * @param playerScore  - the score of the playable player
     * @param numberOfDice - the number of dice the game is played with
     */
    public void computerChoice(int currentRound, int playerScore, int numberOfDice)
    {
        RandomGenerator r         = new RandomGenerator(0, 1);

        double      threshold     = COMPUTER_SIT_THRESHOLD;
        double      decision      = ONE_SKUNK_ODDS + DOUBLE_SKUNK_ODDS;
        int         computerScore = this.getScoreValue();
        int         pointGap      = 0;
        String      message       = "";

        if(numberOfDice == Skunk.MAX_DICE)
        {
            decision -= TRIPLE_ODDS;
        }

        if(computerScore > playerScore)
        {
            if(currentRound == Skunk.ROUNDS)
            {
                decision += COMPUTER_SIT_THRESHOLD;
            }
            else
            {
                decision += COMPUTER_WINNING_VALUE;
            }
        }
        else if(computerScore == playerScore)
        {
            decision += COMPUTER_TIE_VALUE;
        }
        else
        {
            pointGap = playerScore - computerScore;

            decision -= (pointGap / POINT_GAP_DIVISOR);
        }

        double random =  r.getNewSmallRandom();
        decision      += random;

        // For debugging:
        //System.out.println("Computer decision..." + decision + ". Random was " + random);

        if(decision > threshold)
        {
            this.setStanding(false);

            message = "sit";
        }
        else
        {
            message = "stay standing";
        }

        System.out.println(this.getName() + " has chosen to " + message);
    }
}