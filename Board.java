public class Board
{
	public static final String  COLUMN_DIVIDER = " | ";
	public static final char	ROW_DIVIDER	   = '-';
	public static final int		EXTRA_SPACE	   = 1;
	
    private int columns;
    private int rows;
    private String[][] board;

    public Board(int rows, int columns)
    {
        checkNumberNotNegative(columns);
        checkNumberNotNegative(rows);

        board = new String[rows][columns];

        this.columns = columns;
        this.rows    = rows;
    }

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

	private void checkFinalRow(int i) {
		if(i == this.columns - 1)
		{
		    System.out.println();
		}
	}
    
    private int getLongestForColumn(int columnIndex)
    {   		
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

    public void setPosition(int row, int column, String data)
    {
        checkBounds(column, row);

        if(null == data)
        {
            throw new IllegalArgumentException("Can't store null at position" + column + " , " + row);
        }

        board[row][column] = data;
    }

    public String getPosition(int column, int row)
    {
        checkBounds(column, row);

        return board[row][column];
    }

    public int getRows()
    {
        return rows;
    }

    public int getColumns()
    {
        return columns;
    }

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

    private void checkNumberNotNegative(int num)
    {
        if(num < 0)
        {
            throw new IllegalArgumentException("Rows cannot be negative");
        }
    }
}