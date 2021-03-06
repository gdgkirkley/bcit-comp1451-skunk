/**
 * @author Gabe Kirkley
 * @version 1.0.0
 * A game library that registers available games.
 */
public class GameLibrary
{
    public static final int SKUNK_ID 		= 1;
	public static final int TIC_TAC_TOE_ID  = 2;
	
	private Input 	playerInput;
    private boolean choosing;

    public GameLibrary()
    {
		super();
		
        playerInput = new Input();
		choosing	= true;
		
		introduction();
    }
	
	/**
	 * Run the introduction to the Game Library to choose a game.
	 */
	public void introduction()
	{
		System.out.println("Welcome to Games World!");
		
		while(choosing)
		{		
			System.out.println("What game would you like to play?");
			System.out.println(SKUNK_ID + ". SKUNK");
			System.out.println(TIC_TAC_TOE_ID + ". Tic Tac Toe");
			System.out.print("Enter your choice: (" + Controls.QUIT + " to quit) ");

			if(playerInput.hasNext())
			{
				if(playerInput.hasNextInt())
				{
					int input = playerInput.getIntInput();
					
					if(input == SKUNK_ID)
					{
						boolean playing = new SkunkConfig().startSkunk(playerInput);

						if(!playing)
						{
							continue;
						}
					}
					else if(input == TIC_TAC_TOE_ID)
					{
						boolean playing = new TicTacToe(playerInput).startGame();

						if(!playing)
						{
							continue;
						}
					}
					else
					{
						System.out.println("Hmm... doesn't seem to be an option. Try again!");
					}
				}
				else 
				{
					String input = playerInput.getStringInput();

					if(input.equalsIgnoreCase(Controls.QUIT))
					{
						System.exit(0);
					}
					else
					{
						System.out.println("Hmm..." + input + " is not a valid option. Try again!");
					}
				}
			}
		}
	}
}