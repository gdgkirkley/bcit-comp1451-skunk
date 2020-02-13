import java.util.ArrayList;

public class Skunk extends Game
{
    public static final int 	MIN_DICE 		       = 2;
	public static final int 	MAX_DICE 		       = 3;
	public static final int		MIN_DIE_VALUE          = 1;
    public static final int	    MAX_DIE_VALUE	       = 6;
    public static final int     MIN_COMPUTER_PLAYERS   = 1;
    public static final int     MAX_COMPUTER_PLAYERS   = 3;
	public static final int 	COMPUTER_PLAYERS       = 1;
	public static final int		SKUNK_ROLL		       = 1;
    public static final int		ROUNDS			       = 5;
    public static final int     INITIAL_ROUND_VALUE    = 0;
    public static final int     INITIAL_SCORE_VALUE    = 0;
    public static final int     TRIPLE_SCORE           = 100;

    public static final double  ONE_SKUNK_ODDS       = 0.167;
    public static final double  DOUBLE_SKUNK_ODDS    = 0.03125;
	
    private	int 			numberOfDice;
    private int             numberOfComputerPlayers;
	private	RandomGenerator die;
	private int				currentRound;
	private	Input			playerInput;
	private int[]   		currentRolls;
    private Board           board; 
    private SkunkControls   controls;
    private String          playerName;

    // Could the player name their own player?
    public Skunk(int numberOfDice, int numberOfComputerPlayers, Input playerInput, String playerName)
    {
        super();

        if(null == playerInput)
        {
            throw new IllegalArgumentException("Input cannot be null");
        }

        if(numberOfDice < MIN_DICE || numberOfDice > MAX_DICE)
		{
			throw new IllegalArgumentException("Invalid number of dice");
        }

        if((numberOfComputerPlayers < MIN_COMPUTER_PLAYERS) || 
           (numberOfComputerPlayers > MAX_COMPUTER_PLAYERS))
		{
			throw new IllegalArgumentException("Invalid number of computer players");
        }

        this.numberOfDice            = numberOfDice;
        this.numberOfComputerPlayers = numberOfComputerPlayers;
        this.playerInput             = playerInput;
        this.playerName              = playerName;
        
        this.setPlayers(new ArrayList<Player>());

		this.addPlayer(new SkunkPlayer(this.playerName, INITIAL_SCORE_VALUE, false));
		
		for(int i = 0; i < this.numberOfComputerPlayers; i++)
		{
            boolean computer = true;

			this.addPlayer(new SkunkPlayer("Computer " + i, INITIAL_SCORE_VALUE, computer));
		}
		
        board             = createSkunkBoard();
        this.controls     = new SkunkControls();
		this.die		  = new RandomGenerator(MIN_DIE_VALUE, MAX_DIE_VALUE);
		this.currentRound = INITIAL_ROUND_VALUE;
        this.currentRolls = new int[numberOfDice];
        
        this.setPlaying(true);
    }

    public boolean startGame()
	{
        System.out.println("Welcome to SKUNK!!");
		System.out.println();

        while(this.isPlaying())
        {
            while(this.currentRound < ROUNDS)
            {
                this.playSkunkRound();
    
                this.currentRound += 1;
            }
    
            endGame();
        }

        return this.isPlaying();
    }
    
    private void endGame()
    {
        Player winningPlayer = getWinningPlayer();

        System.out.println("==================");

        if(winningPlayer.getName().equals(this.playerName))
        {
            System.out.println("|   YOU WIN!!!!  |");
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
                    
                    for(Player player : getPlayers())
                    {
                        player.getScore().setScore(INITIAL_SCORE_VALUE);
                    }

                    this.currentRound = INITIAL_ROUND_VALUE;
                    this.board        = createSkunkBoard();

                }
                else if(input.equalsIgnoreCase(SkunkControls.NO))
                {
                    waiting      = false;
                    
                    this.setPlaying(false);
                }
                else
                {
                    System.out.println("Hmmm... " + input + " doesn't work here...");
                }
            }
        }
    }

    private Player getWinningPlayer() 
    {
        Player winningPlayer = null;

        for(Player player : getPlayers())
        {
            if(null == winningPlayer)
            {
                winningPlayer = player;
            }
            else
            {
                if(player.getScoreValue() > winningPlayer.getScoreValue())
                {
                    winningPlayer = player;
                }
            }
        }

        return winningPlayer;
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

            int     numberOfSkunkRolls  = checkSkunkRolls();
            int     score               = getCurrentRollScore();
            boolean triple              = checkTriple();

            if(numberOfSkunkRolls > 0)
            {
                if(!areAllPlayersStanding())
                {
                    playingRound = false;

                    if(numberOfSkunkRolls == this.numberOfDice ||
                       (this.numberOfDice == MAX_DICE && numberOfSkunkRolls == this.numberOfDice - 1))
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
                if((this.numberOfDice == MAX_DICE) && (triple))
                {
                    score = TRIPLE_SCORE;
                }

                scorePlayers(score);
                roundScore += score;
            }
            
            updateBoard(roundScore);
            board.drawBoard();
            
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
        int playerScore = this.getPlayerByName(this.playerName).getScoreValue();

        for(Player player : getPlayers())
        {
            if(player instanceof SkunkPlayer)
            {
                SkunkPlayer skunkPlayer = (SkunkPlayer)player;
                boolean standing        = skunkPlayer.isStanding();

                if(standing)
                {
                    if(player.isComputer())
                    {
                        skunkPlayer.computerChoice(this.currentRound, playerScore, this.numberOfDice);
                    }
                    else
                    {
                        playerChoice();
                    }
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
                skunkPlayer.setRoundScore(0);
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

    private boolean checkTriple()
    {
        boolean triple = true;
        int     first  = this.currentRolls[0];

        for(int i = 0; i < this.currentRolls.length; i++)
        {
            if(first != this.currentRolls[i])
            {
                triple = false;
            }
        }

        return triple;
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
                    skunkPlayer.getRoundScore().incrementScore(score);;
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
                    skunkPlayer.getRoundScore().decrementScore(roundScore);;
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

                if(skunkPlayer.isStanding())
                {
                    skunkPlayer.getScore().setScore(0);
                    skunkPlayer.getRoundScore().setScore(0);
                }
            }
        }
    }

    private void playerChoice()
    {
        boolean standing = controls.checkPlayerStandChoice(this.playerInput);

        SkunkPlayer player = (SkunkPlayer)this.getPlayerByName(this.playerName);

        player.setStanding(standing);
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
    
    private void updateBoard(int roundScore)
    {
        String rowString    = "";
        String scoreString  = "";
        int    index        = 0;

        for(Player player : getPlayers())
        {
            if(null == player)
            {
                throw new NullPointerException("Player cannot be null");
            }

            if(player instanceof SkunkPlayer)
            {
                SkunkPlayer skunkPlayer = (SkunkPlayer)player;
                scoreString = "(+" + skunkPlayer.getRoundScoreValue() + ")";
            }

            rowString = player.getName() + ": " + player.getScoreValue() + " " + scoreString;

            board.setPosition(index + 1, this.currentRound, rowString);

            index++;
        }
    }
}