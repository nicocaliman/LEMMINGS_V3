package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExitDoor extends GameObject
{ 	
	//publi constants
	public static final String EXIT_DOOR_SHORTCUT = "ED";
	public static final String EXIT_DOOR_NAME = "ExitDoor";
	
	//constructor
	public ExitDoor(GameWorld game, Position position)
	{
		super(game, position);
	}

	public ExitDoor(ExitDoor exitDoor) 
	{
		super(exitDoor);
	}

	//methods
	@Override
	public String getIcon()	{ return Messages.EXIT_DOOR; }	//return exit door icon
	
	@Override
	public boolean isSolid() { return false; }	//not a wall
	
	@Override
	public void update() {}	//no update needed

	@Override
	public String toString() 
	{
		return this.position.toString() + this.getIcon(); 
	}

	@Override
	public boolean receiveInteraction(GameItem other)
	{
		return other.interactWith(this);	//return false (gameobject method)
	}

	@Override
	public String getName() { return EXIT_DOOR_NAME; }

	@Override
	public String getShortCut() { return EXIT_DOOR_SHORTCUT; }
	
	@Override
	public GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException
	{
		GameObject ob=null;
		Position p;
		
		p=getPositionFrom(line);
		
		if(this.matchGameObjectName(line)) 
			ob=new ExitDoor(game,p);
		
		return ob;
	}

	@Override
	public GameObject copy() { return new ExitDoor(this); }	 
}
