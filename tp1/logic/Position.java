package tp1.logic;

import tp1.view.Messages;

/**
 * 
 * Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public final class Position 
{
	//attributes
	private final int col;
	private final int row;
	
	//constructors
	public Position(int column, int row) 
	{
		this.col = column;
		this.row = row;
	}
	
	public Position(Direction direction, Position position) 
	{
		this.col = position.col + direction.getX();
		this.row = position.row + direction.getY();
	}

	//methods
	@Override
	public String toString() 
	{
		return String.format(Messages.POSITION, this.row, this.col);	//"(" + this.row + "," + this.col + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj || obj != null && obj instanceof Position && col == ((Position) obj).col && row == ((Position) obj).row;
	}
	
	public boolean isOutOfBoard() 
	{
		return this.col >= Game.DIM_X || this.col < 0 || this.row >= Game.DIM_Y;
	}
}
