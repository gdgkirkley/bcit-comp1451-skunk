import java.util.ArrayList;

public class Skunk extends Game
{
    public static final int 	MIN_DICE 		  = 2;
	public static final int 	MAX_DICE 		  = 3;
	public static final int		MIN_DIE_VALUE     = 1;
	public static final int	    MAX_DIE_VALUE	  = 6;
	public static final int 	COMPUTER_PLAYERS  = 1;
	public static final int		SKUNK_ROLL		  = 1;
    public static final int		ROUNDS			  = 5;
    public static final double  ONE_SKUNK_ODDS    = 0.167;
    public static final double  DOUBLE_SKUNK_ODDS = 0.03125;
	
	private	int 			numberOfDice;
	private	RandomGenerator die;
	private int				currentRound;
	private	Input			playerInput;
	private int[]   		currentRolls;
    private Board           board; 
    private SkunkControls   controls;

    public Skunk(int numberOfDice, Input playerInput)
    {
        super();

        if(numberOfDice < MIN_DICE || numberOfDice > MAX_DICE)
		{
			throw new IllegalArgumentException("Invalid number of dice");
        }
        
        this.setPlayers(new ArrayList<Player>());
		
		this.addPlayer(new SkunkPlayer("Player", 0, false));
		
		for(int i = 0; i < COMPUTER_PLAYERS; i++)
		{
			this.addPlayer(new SkunkPlayer("Computer " + i, 0, true));
		}
		
		this.numberOfDice = numberOfDice;
		this.playerInput  = playerInput;
		
        board             = createSkunkBoard();
        this.controls     = new SkunkControls();
		this.die		  = new RandomGenerator(MIN_DIE_VALUE, MAX_DIE_VALUE);
		this.currentRound = 0;
		this.currentRolls = new int[numberOfDice];
		
		this.introduction();
    }

    private void introduction()
	{
        System.out.println("Welcome to SKUNK!!");
        controls.listRules(numberOfDice);
		System.out.println();

        while(this.currentRound < ROUNDS)
        {
            this.playSkunkRound();

            this.currentRound += 1;
        }

        endGame();
    }
    
    private void endGame()
    {
        int playerScore   = 0;
        int computerScore = 0;

        for(Player player : getPlayers())
        {
            if(player.isComputer())
            {
                computerScore += player.getScoreValue();
            }
            else
            {
                playerScore += player.getScoreValue();
            }
        }

        System.out.println("==================");

        if(playerScore > computerScore)
        {
            System.out.println("|   YOU WIN!!!!  |");
        }
        else if(playerScore == computerScore)
        {
            System.out.println("|   It's a tie!  |");
        }
        else
        {
            System.out.println("|   You lost...  |");
        }

        System.out.println("==================");

        System.out.println();
        
        boolean waiting = true;
        
        while(waiting)
        {
            System.out.print("Play again?");

            if(playerInput.hasNext())
            {
                String input = playerInput.getStringInput();

                if(input.equalsIgnoreCase(SkunkControls.YES))
                {
                    waiting = false;
                    // Reset variables
                    introduction();
                }
                else if(input.equalsIgnoreCase(SkunkControls.NO))
                {
                    waiting = false;
                    // reset game library
                }
                else
                {
                    System.out.println("Hmmm... " + input + " doesn't work here...");
                }
            }
        }
    }
	
	private void playSkunkRound()
	{
        int roundScore       = 0;
        boolean playingRound = true;

        System.out.println("================");
        System.out.println("Starting round " + (this.currentRound + 1));
        System.out.println("================");
        
        while(playingRound)
        {
            roll();

            int numberOfSkunkRolls  = checkSkunkRolls();
            int score               = getCurrentRollScore();

            if(numberOfSkunkRolls > 0)
            {
                if(!areAllPlayersStanding())
                {
                    playingRound = false;

                    if(numberOfSkunkRolls == this.numberOfDice)
                    {
                        skunkPlayersGame();
                    }
                    else
                    {
                        skunkPlayersRound(roundScore);
                    }
                }
            }
            else
            {
                scorePlayers(score);
                roundScore += score;
            }
            
            // Need to update this so that we only run playerChoice if the player is standing
            updateBoard();
            
            if(playingRound)
            {
                runChoices();
            }

            if(areAllPlayersSitting())
            {
                playingRound = false;
            }
        }

        resetPlayers();
    }

    private void runChoices()
    {
        for(Player player : getPlayers())
        {
            SkunkPlayer skunkPlayer = (SkunkPlayer)player;
            boolean standing        = skunkPlayer.isStanding();

            if(standing)
            {
                if(player.isComputer())
                {
                    computerChoice();
                }
                else
                {
                    playerChoice();
                }
            }
        }
    }   

    private void resetPlayers()
    {
        for(Player player : this.getPlayers())
        {
            if(player instanceof SkunkPlayer)
            {
                SkunkPlayer skunkPlayer = (SkunkPlayer)player;

                skunkPlayer.setStanding(true);
            }
        }
    }
	
	private void roll()
	{
		for(int i = 0; i < numberOfDice; i++)
		{
            System.out.print("Rolling");

            for(int j = 0; j < 3; j++){
                
                try 
                {
                    Thread.sleep(500);
                } 
                catch(InterruptedException in)
                {
                    Thread.currentThread().interrupt();
                }

                System.out.print(".");
            }

			this.currentRolls[i] = die.getNewRandom();
            System.out.println("DIE #" + (i + 1) + ": " + this.currentRolls[i] + " ");    
        }
    }
    
    private int getCurrentRollScore()
    {
        int score = 0;

        for(int i = 0; i < numberOfDice; i++)
		{
			score += this.currentRolls[i];   
        }

        return score;
    }
	
	private int checkSkunkRolls()
	{
        int numberOfSkunkRolls = 0;

        for(int i = 0; i < this.currentRolls.length; i++)
        {
            if(this.currentRolls[i] == SKUNK_ROLL)
            {
                numberOfSkunkRolls++;
            }
        }

        return numberOfSkunkRolls;
    }

    private boolean areAllPlayersStanding()
    {
        boolean allStanding = true;

        for(Player player : this.getPlayers())
        {
            if(player instanceof SkunkPlayer)
            {
                SkunkPlayer skunkPlayer = (SkunkPlayer)player;

                if(!skunkPlayer.isStanding())
                {
                    allStanding = false;
                }
            }
        }

        return allStanding;
    }

    private boolean areAllPlayersSitting()
    {
        boolean allSitting = true;

        for(Player player : this.getPlayers())
        {
            if(player instanceof SkunkPlayer)
            {
                SkunkPlayer skunkPlayer = (SkunkPlayer)player;

                if(skunkPlayer.isStanding())
                {
                    allSitting = false;
                }
            }
        }

        return allSitting;
    }
    
    private void scorePlayers(int score)
    {
        for(Player player : this.getPlayers())
        {
            if(player instanceof SkunkPlayer)
            {
                SkunkPlayer skunkPlayer = (SkunkPlayer)player;

                if(skunkPlayer.isStanding())
                {
                    skunkPlayer.getScore().incrementScore(score);
                }
            }
        }
    }

    private void skunkPlayersRound(int roundScore)
    {
        for(Player player : this.getPlayers())
        {
            if(player instanceof SkunkPlayer)
            {
                SkunkPlayer skunkPlayer = (SkunkPlayer)player;

                if(skunkPlayer.isStanding())
                {
                    skunkPlayer.getScore().decrementScore(roundScore);
                }
            }
        }
    }

    private void skunkPlayersGame()
    {
        for(Player player : this.getPlayers())
        {
            if(player instanceof SkunkPlayer)
            {
                SkunkPlayer skunkPlayer = (SkunkPlayer)player;

                if(!skunkPlayer.isStanding())
                {
                    skunkPlayer.getScore().setScore(0);
                }
            }
        }
    }

    private void playerChoice()
    {
        boolean standing = controls.checkPlayerStandChoice(this.playerInput);

        SkunkPlayer player = (SkunkPlayer)this.getPlayerByName("Player");

        player.setStanding(standing);
    }

    private void computerChoice()
    {
        RandomGenerator r     = new RandomGenerator(0, 1);

        double      threshold     = 1;
        double      decision      = ONE_SKUNK_ODDS + DOUBLE_SKUNK_ODDS;
        int         computerScore = 0;
        int         playerScore   = 0;
        int         pointGap      = 0;
        String      message       = "";
        SkunkPlayer computer      = null;
        
        for(Player player : getPlayers())
        {
            if(player.isComputer())
            {
                computerScore = player.getScoreValue();
                computer      = (SkunkPlayer)player;
            }
            else
            {
                playerScore = player.getScoreValue();
            }
        }

        if(computerScore > playerScore)
        {
            if(this.currentRound == ROUNDS)
            {
                decision += 1;
            }
            else
            {
                decision += 0.6;
            }
        }
        else if(computerScore == playerScore)
        {
            decision += 0.3;
        }
        else
        {
            pointGap = playerScore - computerScore;

            decision -= (pointGap / 100);
        }

        double random =  r.getNewSmallRandom();
        decision      += random;

        // For debugging:
        //System.out.println("Computer decision..." + decision + ". Random was " + random);

        if(decision > threshold)
        {
            computer.setStanding(false);

            message = "sit";
        }
        else
        {
            message = "stay standing";
        }

        System.out.println(computer.getName() + " has chosen to " + message);
    }

	private Board createSkunkBoard()
	{
        int numberOfRows = getPlayers().size() + 1;

		Board board = new Board(numberOfRows, 5);

        board.setPosition(0, 0, "S");
        board.setPosition(0, 1, "K");
        board.setPosition(0, 2, "U");
        board.setPosition(0, 3, "N");
        board.setPosition(0, 4, "K");
        
        return board;
    }
    
    private void updateBoard()
    {
        String rowString = "";

        for(int i = 0; i < getPlayers().size(); i++)
        {  
            Player player = getPlayers().get(i);

            rowString = player.getName() + ": " + player.getScore().getScore();

            board.setPosition(i + 1, this.currentRound, rowString);
        }

        board.drawBoard();
    }
}