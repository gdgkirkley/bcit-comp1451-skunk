/**
 * @author Gabe Kirkley
 * @version 1.0.0
 * The config for setting up a game of SKUNK
 */
public class SkunkConfig
{
	public static final int     PLAYER_NAME_MAX_LENGTH = 12;
	public static final String  PLAYER_DEFAULT_NAME    = "Player";

    private int     numberOfDice;
    private int     numberOfComputerPlayers;
	private Input   playerInput;
	private String  playerName;

    public SkunkConfig()
    {
		super();
    }

	/**
	 * Start the config for a game of SKUNK
	 * @param playerInput - the player input object
	 * @return true if the game is still running
	 */
    public boolean startSkunk(Input playerInput)
	{
        this.playerInput = playerInput;

		System.out.println("You chose SKUNK!");
		System.out.println();
		
        setUpNumberOfDice();
		setUpNumberOfComputerPlayers();
		setUpPlayerName();

		Skunk skunk = new Skunk(this.numberOfDice, this.numberOfComputerPlayers, playerInput, this.playerName);
		
		return skunk.startGame();
    }
	
	/**
	 * Get a number of dice from the player
	 */
    private void setUpNumberOfDice()
    {
        boolean choosing = true;

        while(choosing)
		{
            System.out.print("Choose a number of dice (" + Skunk.MIN_DICE + " or " + Skunk.MAX_DICE + "): ");

			if(playerInput.hasNext())
			{
				if(playerInput.hasNextInt())
				{
					int input = playerInput.getIntInput();
					
					if(input == Skunk.MIN_DICE || input == Skunk.MAX_DICE)
					{
                        this.numberOfDice = input;
                        choosing = false;
					}
					else
					{
						System.out.println("Hmmm..." + input + " isn't an option.");
					}
				}
			}
		}
    }

	/**
	 * Get a number of computer players from the player
	 */
    private void setUpNumberOfComputerPlayers()
    {
        boolean choosing = true;

        while(choosing)
		{
            System.out.print("Choose a number of computer players (" + 
                             Skunk.MIN_COMPUTER_PLAYERS + " or " + 
                             Skunk.MAX_COMPUTER_PLAYERS + "): ");

			if(playerInput.hasNext())
			{
				if(playerInput.hasNextInt())
				{
					int input = playerInput.getIntInput();
					
					if(input >= Skunk.MIN_COMPUTER_PLAYERS && input <= Skunk.MAX_COMPUTER_PLAYERS)
					{
                        this.numberOfComputerPlayers = input;
                        choosing = false;
					}
					else
					{
						System.out.println("Hmmm..." + input + " isn't an option.");
					}
				}
			}
		}
	}
	
	/**
	 * Get a name from the player
	 */
	private void setUpPlayerName()
	{
		boolean choosing = true;

        while(choosing)
		{
			System.out.print("What's your name? (" + 
							 "Enter " + Controls.USE_DEFAULT + 
							 " for default name): ");

			if(playerInput.hasNext())
			{
				String input = playerInput.getStringInput();
				
				if(input.equalsIgnoreCase(Controls.USE_DEFAULT))
				{
					this.playerName = PLAYER_DEFAULT_NAME;
					choosing        = false;
				}
				else if(input.length() <= PLAYER_NAME_MAX_LENGTH)
				{
					this.playerName = input;
					choosing        = false;
				}
				else
				{
					System.out.println("Hmmm..." + input + " is too long.");
				}	
			}
		}
	}
}