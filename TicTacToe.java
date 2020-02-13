import java.util.ArrayList;

public class TicTacToe extends Game
{
    public static final int INITIAL_SCORE_VALUE = 0;
    public static final int INITIAL_ROUND_VALUE = 0;
    public static final int MAX_ROUNDS          = 9;
    public static final int MAX_INPUT_LENGTH    = 2;

    private Board board;
    private Input playerInput;
    private int   currentRound;

    public TicTacToe(Input playerInput)
    {
        if(null == playerInput)
        {
            throw new IllegalArgumentException("Input cannot be null");
        }

        board = createTicTacToeBoard();

        this.playerInput  = playerInput;
        this.currentRound = INITIAL_ROUND_VALUE;

        this.setPlayers(new ArrayList<Player>());
        this.addPlayer(new SkunkPlayer("Player", INITIAL_SCORE_VALUE, false));
        this.addPlayer(new SkunkPlayer("Computer", INITIAL_SCORE_VALUE, true));

        this.setPlaying(true);
    }

    public boolean startGame()
    {
        System.out.println("Welcome to TIC TAC TOE!!");
		System.out.println();

        while(this.isPlaying())
        {
            while(this.currentRound < MAX_ROUNDS)
            {
                this.playRound();
    
                this.currentRound += 1;
            }
    
            endGame();
        }

        return this.isPlaying();
    }

    private void playRound()
    {
        boolean playingRound = true;

        while(playingRound)
        {
            if(true)
            {
                // add logic to check if a win has happened
                System.out.println("Could be!");
            }

            playerChoice();
            computerChoice();

            board.drawBoard();
        }
    }

    private void endGame()
    {
        System.out.println("It's over");
    }

    private void playerChoice()
    {
        boolean choosing = true;

        while(choosing)
        {
            System.out.print("What's your move (ie. A1, B3): ");

            if(playerInput.hasNext())
            {
                String input = playerInput.getStringInput();

                if(input.length() == MAX_INPUT_LENGTH)
                {
                    
                }
                else
                {
                    System.out.println("Hmm..." + input + "doesn't work here.");
                }
            }
        }
    }

    private void computerChoice()
    {

    }

    private Board createTicTacToeBoard()
    {
        Board board = new Board(4,4);

        board.setPosition(0, 1, "1");
        board.setPosition(0, 2, "2");
        board.setPosition(0, 3, "3");
        board.setPosition(1, 0, "A");
        board.setPosition(2, 0, "B");
        board.setPosition(3, 0, "C");

        return board;
    }
}