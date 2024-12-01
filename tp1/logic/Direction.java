package tp1.logic;

/**
 * Represents the allowed directions in the game
 *
 */
public enum Direction 
{
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), NONE(0,0);
	
	//attributes
	private int x;
	private int y;
	
	//constructor
	private Direction(int x, int y) 
	{
		this.x=x;
		this.y=y;
	}
	
	//methods	
	public int getX() 
	{
		return x;
	}

	public int getY() 
	{
		return y;
	}
	
	public Direction opposite()
	{		
		return this == RIGHT ? LEFT : RIGHT;
	}

	@Override
	public String toString()
	{
		return name();
	}	
	
	public static Direction stringToDir(String s)
	{
		Direction d = null;
		switch(s)
		{
			case "RIGHT":
				d=Direction.RIGHT;
			break;
			case "LEFT":
				d=Direction.LEFT;
			break;
			case "DOWN":
				d=Direction.DOWN;
			break;
			case "UP":
				d=Direction.UP;
			break;
		}
		
		return d;
	}
}