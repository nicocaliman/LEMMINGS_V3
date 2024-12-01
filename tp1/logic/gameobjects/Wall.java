package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Wall extends GameObject
{	

	public static final String WALL_SHORTCUT = "W";
	public static final String WALL_NAME = "Wall";
	//constructor
	public Wall(GameWorld game, Position position) 
	{
		super(game, position);
	}
	
	public Wall(Wall wall) {
		super(wall);
	}

	//methods
	@Override
	public String getIcon() { return Messages.WALL;	}	//return wall icon	

	@Override
	public boolean isSolid() { return true;	}	//is a wall
	
	@Override
	public void update() {}	//no update needed

	@Override
	public String toString() 
	{
		return this.position.toString() + this.getIcon();
	}
	
	@Override
	public void dead() { this.isAlive=false; }
		
	@Override
	public boolean receiveInteraction(GameItem other) 
	{
		return other.interactWith(this);
	}
	@Override
	public String getName() {
		return WALL_NAME;
	}

	@Override
	public String getShortCut() {
		return WALL_SHORTCUT;
	}	
	
	@Override
	public GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException {
		GameObject ob=null;
		Position p;

		p=getPositionFrom(line.split(" ")[0]);
		
		if(this.matchGameObjectName(line)) 
			ob=new Wall(game,p);
		
		return ob;
	}

	@Override
	public GameObject copy() {
		return new Wall(this);
	}	 
}
