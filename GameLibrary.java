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
	
	public void introduction()
	{
		System.out.println("Welcome to Games World!");
		System.out.println("What game would you like to play?");
		System.out.println(SKUNK_ID + ". SKUNK");
		System.out.println(TIC_TAC_TOE_ID + ". Tic Tac Toe");
		System.out.print("Enter your choice: ");
		
		while(choosing)
		{		
			if(playerInput.hasNext())
			{
				if(playerInput.hasNextInt())
				{
					int input = playerInput.getIntInput();
					
					if(input == SKUNK_ID)
					{
						choosing = false;
						new SkunkConfig().startSkunk(playerInput);
					}
					else if(input == TIC_TAC_TOE_ID)
					{
						choosing = false;
						System.out.println("You chose Tic Tac Toe!");
					}
					else
					{
						System.out.println("Hmm... doesn't seem to be an option. Try again!");
					}
				}
				
				System.out.println("Hmm..." + playerInput.getStringInput() + " is not a valid option. Try again!");
			}
		}
	}
}