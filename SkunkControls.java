import java.util.ArrayList;

/**
 * @author Gabe Kirkley
 * @version 1.0.0
 * Controls for the game of SKUNK
 */
public class SkunkControls extends Controls
{
    public SkunkControls()
    {
        super();
	}

	public void runOtherControls(String input, Board board, ArrayList<Player> players)
	{
		if(null == input)
		{
			System.out.println("Hmmm... input can't be null");
		}

		if(input.equalsIgnoreCase(DISPLAY_BOARD))
		{
			board.drawBoard();
		}
		else if(input.equalsIgnoreCase(DISPLAY_CONTROLS))
		{
			this.listControls();
		}
		else if(input.equalsIgnoreCase(DISPLAY_SCORES))
		{
			for(Player player : players)
			{
				System.out.println(player.getName() + ": " + player.getScoreValue());
			}
		}
		else if(input.equalsIgnoreCase(DISPLAY_RULES))
		{
			this.listRules(2);
		}
		else if(input.equalsIgnoreCase(QUIT))
		{
			System.exit(0);
		}
		else
		{
			System.out.println("Hmmm... " + input + " doesn't work here...");
		}
	}

	/**
	 * List the controls of Skunk
	 */
    public void listControls()
	{
		System.out.println("Display Board: " + DISPLAY_BOARD);
		System.out.println("Display Controls: " + DISPLAY_CONTROLS);
		System.out.println("Display Scores: " + DISPLAY_SCORES);
		System.out.println("Display Rules: " + DISPLAY_RULES);
		System.out.println("Answer Yes: " + YES);
		System.out.println("Answer No: " + NO);
		System.out.println("Quit: " + QUIT);
		System.out.println();
    }
	
	/**
	 * List the rules of SKUNK
	 * @param numberOfDice - the number of dice in the game
	 */
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