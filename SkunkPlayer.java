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

    public SkunkPlayer(String name, int startingScore, boolean computer)
    {
        super(name, startingScore, computer);

        standing = true;
    }

    public void setStanding(boolean standing)
    {
        this.standing = standing;
    }

    public boolean isStanding()
    {
        return this.standing;
    }

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