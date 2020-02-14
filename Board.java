
/**
 * @author Gabe Kirkley
 * @version 1.0.0
 * A board class that will draw and store information for a board
 * given the dimensions provided. The board itself is an two-dimensional
 * array that stores strings. 
 */
public class Board
{
	public static final String  COLUMN_DIVIDER = " | ";
	public static final char	ROW_DIVIDER	   = '-';
	public static final int		EXTRA_SPACE	   = 1;
	
    private int columns;
    private int rows;
    private String[][] board;

    /**
     * @param rows    - the number of rows
     * @param columns - the number of columns
     * Sets up the board and sets all "cells" to an empty string.
     */
    public Board(int rows, int columns)
    {
        checkNumberNotNegative(columns);
        checkNumberNotNegative(rows);

        board = new String[rows][columns];

        this.columns = columns;
        this.rows    = rows;

        setAllEmpty();
    }

    /**
     * Prints the current state of the board to the console.
     */
    public void drawBoard()
    {
    	int 	longestRow 	= getLengthOfRows() + (this.columns * COLUMN_DIVIDER.length()) - EXTRA_SPACE;
    	String 	spacer   	= new String(new char[longestRow]).replace('\0', ROW_DIVIDER);

    	for(int i = 0; i < this.rows; i++)
        {	
        	System.out.println(spacer);
            drawColumns(i);
        }
    	
    	System.out.println();
    }

    /**
     * @param rowIndex - which row to draw columns for
     * Given the row that we are iterating through, this
     * prints the columns/cells for that row and the contents.
     */
    private void drawColumns(int rowIndex)
    {
        for(int i = 0; i < this.columns; i++)
        {
        	int longest 	= getLongestForColumn(i);
        	String current	= board[rowIndex][i];
        	
        	if(null == current)
        	{
        		System.out.print(String.format("%-" + longest + "s", "") + COLUMN_DIVIDER);
        		
        		checkFinalRow(i);
        		
        		continue;
        	}
        	
        	int length		 = current.length();
        	int difference	 = longest - length;
        	
        	if(length == longest)
        	{
        		System.out.print(current + COLUMN_DIVIDER);
        	}
        	else
        	{        		
        		System.out.print(current + 
        				String.format("%" + difference + "s", "") + 
        				COLUMN_DIVIDER);
            }
            
            checkFinalRow(i);
        }

    }

    /**
     * Is this the final column? If so, print a line.
     * Extracted in case something else can be done here.
     * @param i - the row index
     */
	private void checkFinalRow(int i) {
		if(i == this.columns - 1)
		{
		    System.out.println();
		}
	}
    
    /**
     * @param columnIndex - the index of the current column
     * @return the longest String in a given column
     */
    private int getLongestForColumn(int columnIndex)
    {   	
        if(null == board[0][columnIndex])
        {
            throw new IllegalArgumentException("Column is null");
        }
        
    	String 	longest = board[0][columnIndex];
    	
    	for(int i = 0; i < this.rows; i++)
    	{
    		if(null == board[i][columnIndex])
    		{
    			continue;
    		}
    		
    		String current = board[i][columnIndex];
    		
    		if(current.length() > longest.length())
    		{
    			longest = current;
    		}
    	}
    	
    	return longest.length();
    }
    
    /**
     * Used to space the rest of the rows to the same length
     * @return the length of the longest row in the table
     */
    private int getLengthOfRows()
    {
    	int lengthOfRows = 0;
    	
    	for(int colIndex = 0; colIndex < this.columns; colIndex++)
    	{
    		int longestInCol = 0;
    		
    		for(int rowIndex = 0; rowIndex < this.rows; rowIndex++) 
    		{    			
    			String current = board[rowIndex][colIndex];
    			
    			if(null == current)
    			{
    				continue;
    			}
    			
    			if(current.length() > longestInCol)
    			{
    				longestInCol = current.length();
    			}
    		}
    		
    		lengthOfRows += longestInCol;
    	}
    	
    	return lengthOfRows;
    }

    /**
     * @param row    - the row in the table
     * @param column - the column in the table
     * @param data   - the data to set at the row and column specified
     */
    public void setPosition(int row, int column, String data)
    {
        checkBounds(column, row);

        if(null == data)
        {
            throw new IllegalArgumentException("Can't store null at position" + column + " , " + row);
        }

        board[row][column] = data;
    }

    /**
     * @param column - the column of the table
     * @param row    - the row of the table
     * @return the value at the position given.
     */
    public String getPosition(int column, int row)
    {
        checkBounds(column, row);

        return board[row][column];
    }

    /**
     * @return the number of rows
     */
    public int getRows()
    {
        return rows;
    }

    /**
     * @return the number of columns
     */
    public int getColumns()
    {
        return columns;
    }

    /**
     * Set all the positions in the table to empty strings.
     */
    private void setAllEmpty()
    {
        for(int row = 0; row < this.rows; row++)
        {
            for(int column = 0; column < this.columns; column++)
            {
                board[row][column] = "";
            }
        }
    }

    /**
     * @param column - the column in the table
     * @param row    - the row in the table
     */
    private void checkBounds(int column, int row)
    {
        if(column < 0 || column > columns)
        {
            throw new IllegalArgumentException("Column " + column + " doesn't exist.");
        }

        if(row < 0 || row > rows)
        {
            throw new IllegalArgumentException("Row " + row + " doesn't exist.");
        }
    }

    /**
     * @param num - a number
     */
    private void checkNumberNotNegative(int num)
    {
        if(num < 0)
        {
            throw new IllegalArgumentException("Rows cannot be negative");
        }
    }
}