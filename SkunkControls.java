public class SkunkControls extends Controls
{
    public SkunkControls()
    {
        super();
	}
	
	public boolean checkPlayerStandChoice(Input playerInput)
	{
		boolean waiting = true;
		boolean remainStanding = true;

		
		while(waiting)
		{
			System.out.print("Would you like to sit? ");

			if(playerInput.hasNext())
			{
				String input = playerInput.getStringInput();

				if(input.equalsIgnoreCase(YES))
				{
					remainStanding = false;
					waiting		   = false;
				}
				else if(input.equalsIgnoreCase(NO))
				{	
					waiting = false;
				}
				else
				{
					// Check main control loop
					System.out.println("Hmmm... " + input + " doesn't work here. Try again!");
				}
			}
		}

		return remainStanding;
	}

    public void listControls()
	{
		System.out.println("Display Board: " + SkunkControls.DISPLAY_BOARD);
		System.out.println("Display Controls: " + SkunkControls.DISPLAY_CONTROLS);
		System.out.println("Display Scores: " + SkunkControls.DISPLAY_SCORES);
		System.out.println();
    }
    
    public void listRules(int numberOfDice)
	{
		System.out.println("There are " + Skunk.ROUNDS + " rounds in the game. One round corresponds with each letter of the word SKUNK.");
		System.out.println("Players start the game standing up. " + numberOfDice + " dice are rolled.");
		System.out.println("The players have a choice before each roll whether to remain standing or whether to sit down.");
		System.out.println("If a player is standing when a roll that does NOT contain a " + Skunk.SKUNK_ROLL + " is rolled, they get those points.");
		System.out.println("If all players are standing when a " + Skunk.SKUNK_ROLL + " is rolled, the roll does not count.");
		System.out.println("If a player sits down, they get to keep their total points from that round.");
		System.out.println("If a player is standing when a " + Skunk.SKUNK_ROLL + " is rolled, they lose ALL their points for that round.");
		System.out.println("If a player is standing when two " + Skunk.SKUNK_ROLL + "s (snake eyes) are rolled, they lose ALL their points from ALL rounds!");
		System.out.println("Each round ends when a " + Skunk.SKUNK_ROLL + " is rolled or when all players are sitting.");
		System.out.println();
	}
}