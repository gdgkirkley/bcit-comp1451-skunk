public class SkunkConfig
{
    private int     numberOfDice;
    private int     numberOfComputerPlayers;
    private Input   playerInput;

    public SkunkConfig()
    {
        super();
    }

    public void startSkunk(Input playerInput)
	{
        this.playerInput = playerInput;

		System.out.println("You chose SKUNK!");
		System.out.println();
		
        setUpNumberOfDice();
        setUpNumberOfComputerPlayers();

        new Skunk(this.numberOfDice, this.numberOfComputerPlayers, playerInput);
    }
    
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
}