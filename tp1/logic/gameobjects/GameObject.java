package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;
import tp1.view.Messages;

public abstract class GameObject implements GameItem
{
	//attributes
	protected Position position;
	protected boolean isAlive;
	protected GameWorld game;
	
	//constructor
	public GameObject(GameWorld game, Position pos) 
	{
		this.isAlive = true;
		this.position = pos;
		this.game = game;
	}
	public GameObject(GameObject o) 
	{
		this.isAlive = true;
		this.position = o.position;
		this.game = o.game;
	}
	
	public abstract GameObject copy();
	
	//methods	
	@Override
	public boolean isInPosition(Position pos) 
	{
		return this.isAlive() && this.position.equals(pos);
	}
 	
	@Override
	public boolean isAlive() { return isAlive; }
	
	public boolean setRole(LemmingRole role) { return false; }	//wall/metal wall/exit door do not have a role
	
	@Override
	public boolean interactWith(Lemming lemming) { return false; }	//walker/wall/metal wall/exit door can not interact with another lemming

	@Override
	public boolean interactWith(Wall wall) { return false; }	//walker/wall/metal wall/exit door can not interact with a wall

	@Override
	public boolean interactWith(ExitDoor door) { return false; }	//walker/wall/metal wall/exit door can not interact with an exit door (can not modify the door)
	
	@Override
	public boolean interactWith(MetalWall metalWall) { return false; }	//walker/wall/metal wall/exit door can not interact with a metal wall
	
	@Override
	public abstract boolean isSolid();
	
	public void dead() {};
		
	public abstract void update();
	
	public abstract String getIcon();
	
	public abstract String getName();
	
	public abstract String getShortCut();
	
	protected static Position getPositionFrom(String line) throws ObjectParseException, OffBoardException 
	{
		Position p;
		try {
			String[] s=line.split("\\D");
			p=new Position(Integer.parseInt(s[2]),Integer.parseInt(s[1]));
		}
		catch (NumberFormatException e)
		{
			throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(line));
		}
		if(p.isOutOfBoard())	//object position is off board 
			throw new OffBoardException(Messages.OBJECT_POSITION_OFF_BOARD.formatted(line));
		return p;
	}
	
	private static String getObjectNameFrom(String line) throws ObjectParseException
	{
		String s;
		try {
			s=line.split(" ")[1];
		}catch (NullPointerException e){
			 throw new ObjectParseException();
		}
		return s;
	}
	
	public abstract GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException;

	protected boolean matchGameObjectName(String name) throws ObjectParseException 
	{
		String s=getObjectNameFrom(name);
		return getShortCut().equalsIgnoreCase(s) || getName().equalsIgnoreCase(s);
	}
}