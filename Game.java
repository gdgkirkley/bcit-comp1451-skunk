import java.util.ArrayList;

public class Game
{
	private ArrayList<Player> 	players;
	private boolean				playing;

	public Game()
	{
		this.playing = true;
	}

	public void setPlaying(boolean playing)
	{
		this.playing = playing;
	}

	public boolean isPlaying()
	{
		return this.playing;
	}

	public void addPlayer(Player player)
	{
		this.players.add(player);
	}

	public void setPlayers(ArrayList<Player> players)
	{
		this.players = players;
	}

	public ArrayList<Player> getPlayers()
	{
		return this.players;
	}

	public void displayAllScores()
	{
		System.out.println("Scores");
		System.out.println("-------");
		for(Player player : players)
		{
			System.out.println(player.getName() + ": " + player.getScore());
		}
		System.out.println();
	}
	
	public Player getPlayerByName(String name)
	{
		if(null == name)
		{
			throw new IllegalArgumentException("Cannot find null name");
		}
		
		Player foundPlayer = null;

		for(Player player : players)
		{
			if(player.getName().equalsIgnoreCase(name))
			{
				foundPlayer = player;
			}
		}
		
		return foundPlayer;
	}
}