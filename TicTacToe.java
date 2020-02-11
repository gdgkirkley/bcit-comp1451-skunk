public class TicTacToe extends Game
{
    private Board board;

    public TicTacToe()
    {
        board = createTicTacToeBoard();
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