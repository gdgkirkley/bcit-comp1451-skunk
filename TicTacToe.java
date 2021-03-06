import java.util.ArrayList;

public class TicTacToe extends Game
{
    public static final int INITIAL_SCORE_VALUE = 0;
    public static final int INITIAL_ROUND_VALUE = 0;
    public static final int MAX_ROUNDS          = 4;
    public static final int MAX_INPUT_LENGTH    = 2;
    public static final int COLUMNS             = 4;
    public static final int ROWS                = 4;
    public static final int COL_INDEX           = 1;
    public static final int ROW_INDEX           = 0;

    public static final String PLAYER_MARK      = "X";
    public static final String COMPUTER_MARK    = "O";

    private Board   board;
    private Input   playerInput;
    private int     currentRound;
    private boolean computerWin;
    private boolean playerWin;

    public TicTacToe(Input playerInput)
    {
        if(null == playerInput)
        {
            throw new IllegalArgumentException("Input cannot be null");
        }

        this.board = createTicTacToeBoard();

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
            while(this.currentRound <= MAX_ROUNDS)
            {
                this.playRound();
    
                this.playerWin = checkWin(PLAYER_MARK);
                this.computerWin = checkWin(COMPUTER_MARK);
                System.out.println("Oh a win");

                if(playerWin || computerWin)
                {
                    break;
                }
                
                this.currentRound += 1;
            }

            this.setPlaying(false);
            endGame();
        }

        return this.isPlaying();
    }

    private void playRound()
    {
        board.drawBoard();

        playerChoice();
        computerChoice();

        System.out.println("End of round " + this.currentRound);
    }

    private void endGame()
    {
        System.out.println("Game over!");
        board.drawBoard();

        System.out.println("==================");

        if(playerWin)
        {
            System.out.println("|   YOU WIN!!!!  |");
        }
        else if(computerWin)
        {
            System.out.println("|   You lost...  |");
        }
        else
        {
            System.out.println("|   Tie game...  |");
        }

        System.out.println("==================");
    }

    private boolean checkWin(String markToCheck)
    {
        boolean win = false;

        win = this.checkColumnMatches(markToCheck);

        System.out.println("Step 1: " + win);

        if(!win)
        {
            win = this.checkRowMatches(markToCheck);
        }

        System.out.println("Step 2: " + win);

        if(!win)
        {
            win = this.checkDiagonalMatches(markToCheck);
        }

        System.out.println("Step 3: " + win);

        return win;
    }

    private boolean checkColumnMatches(String markToCheck)
    {
        boolean matches = false;
        
        for(int i = 1; i < COLUMNS; i++)
        {
            for(int j = 1; j < ROWS; j++)
            {
                String contents = board.getPosition(i, j);
    
                if(contents.isBlank() || contents.isEmpty())
                {
                    matches = false;
                    break;
                }
                else if(contents.equals(markToCheck))
                {
                    matches = true;
                }
                else
                {
                    matches = false;
                    break;
                }
            }

            if(matches)
            {
                break;
            }
        }

        System.out.println("Col match: " + matches);
        return matches;
    }

    private boolean checkRowMatches(String markToCheck)
    {
        boolean matches = false;
        
        for(int i = 1; i < ROWS; i++)
        {
            for(int j = 1; j < COLUMNS; j++)
            {
                String contents = board.getPosition(j, i);
    
                if(contents.isBlank() || contents.isEmpty())
                {
                    matches = false;
                    break;
                }
                else if(contents.equals(markToCheck))
                {
                    matches = true;
                }
                else
                {
                    matches = false;
                    break;
                }
            }

            if(matches)
            {
                break;
            }
        }

        System.out.println("Row match: " + matches);
        return matches;
    }

    private boolean checkDiagonalMatches(String markToCheck)
    {
        boolean diagonalMatch = false;

        for(int i = 1; i < COLUMNS; i++)
        {
            String next  = board.getPosition(i, i);

            if(next.isBlank() || next.isEmpty())
            {
                diagonalMatch = false;
                break;
            }
            if(next.equals(markToCheck))
            {
                diagonalMatch = true;
            }
            else
            {
                diagonalMatch = false;
                break;
            }

            if(!diagonalMatch)
            {
                break;
            }
        }

        if(!diagonalMatch)
        {
            int j = 3;

            for(int i = 1; i < COLUMNS; i++)
            {
                String next  = board.getPosition(i, j);

                if(next.isBlank() || next.isEmpty())
                {
                    diagonalMatch = false;
                    break;
                }
                if(next.equals(markToCheck))
                {
                    diagonalMatch = true;
                }
                else
                {
                    diagonalMatch = false;
                }

                if(!diagonalMatch)
                {
                    break;
                }

                j--;
            }
        }

        System.out.println("Diagonal match: " + diagonalMatch);
        return diagonalMatch;
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
                    if(checkValidInput(input))
                    {
                        int[] choice = parseInputChoice(input);

                        if(!checkPosition(choice))
                        {
                            board.setPosition(choice[ROW_INDEX], choice[COL_INDEX], PLAYER_MARK);
                            choosing = false;
                        }
                        else
                        {
                            System.out.println("Already chosen...");
                        }
                    }
                    else
                    {
                        System.out.println("Hmmm..." + input + " isn't a square.");
                    }
                }
                else
                {
                    System.out.println("Hmm..." + input + " doesn't work here.");
                }
            }
        }
    }

    private void computerChoice()
    {
        boolean found = false;
        System.out.println("Computer choosing");

        for(int row = 1; row < ROWS; row++)
        {
            for(int col = 1; col < COLUMNS; col++)
            {
                int[] choice = new int[2];
                choice[ROW_INDEX] = row;
                choice[COL_INDEX] = col;
                if(!checkPosition(choice))
                {
                    board.setPosition(choice[ROW_INDEX], choice[COL_INDEX], COMPUTER_MARK);
                    found = true;
                    break;
                }
            }
            if(found)
            {
                break;
            }
        }
    }

    private boolean checkPosition(int[] choice)
    {
        String contents = board.getPosition(choice[COL_INDEX], choice[ROW_INDEX]);

        return (contents.length() > 0);
    }

    private boolean checkValidInput(String input)
    {
        boolean valid           = true;
        char    firstCharacter  = input.charAt(0);
        char    secondCharacter = input.charAt(COL_INDEX);

        if(!Character.isLetter(firstCharacter) &&
           !Character.isDigit(secondCharacter))
        {
            valid = false;
        }

        if(Character.getNumericValue(secondCharacter) > (COLUMNS - 1))
        {
            valid = false;
        }

        return valid;
    }

    private int[] parseInputChoice(String input)
    {
        char firstCharacter  = Character.toUpperCase(input.charAt(ROW_INDEX));
        char secondCharacter = input.charAt(COL_INDEX);
        int[] result         = new int[2];

        if(Character.compare(firstCharacter, 'A') == 0)
        {
            result[ROW_INDEX] = 1;    
        }
        else if(Character.compare(firstCharacter, 'B') == 0)
        {
            result[ROW_INDEX] = 2;
        }
        else if(Character.compare(firstCharacter, 'C') == 0)
        {
            result[ROW_INDEX] = 3;
        }

        result[COL_INDEX] = Character.getNumericValue(secondCharacter);

        return result;
    }

    private Board createTicTacToeBoard()
    {
        Board board = new Board(ROWS, COLUMNS);

        board.setPosition(0, 1, "1");
        board.setPosition(0, 2, "2");
        board.setPosition(0, 3, "3");
        board.setPosition(1, 0, "A");
        board.setPosition(2, 0, "B");
        board.setPosition(3, 0, "C");

        return board;
    }
}