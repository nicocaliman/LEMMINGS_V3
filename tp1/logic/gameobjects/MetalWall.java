package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class MetalWall extends GameObject 
{
	//public constants
	public static final String METAL_WALL_SHORTCUT = "MW";
	public static final String METAL_WALL_NAME = "MetalWall";
	
	//constructor
	public MetalWall(GameWorld game, Position pos) 
	{
		super(game, pos);
	}
	
	public MetalWall(MetalWall metalWall)
	{
		super(metalWall);
	}

	//methods
	@Override
	public boolean isSolid() { return true; }	//is solid
		
	@Override
	public void update() {}

	@Override
	public String getIcon()	{ return Messages.METALWALL; }	//return metal wall icon
		
	@Override
	public String toString() { return this.position.toString() + this.getIcon(); }
	
	@Override
	public boolean receiveInteraction(GameItem other) 
	{
		return other.interactWith(this);
	}
	
	@Override
	public String getName() { return METAL_WALL_NAME; }

	@Override
	public String getShortCut() { return METAL_WALL_SHORTCUT; }	
	
	@Override
	public GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException 
	{
		GameObject ob=null;
		Position p;
			
		p=getPositionFrom(line.split(" ")[0]);
		
		if(this.matchGameObjectName(line))
			ob=new MetalWall(game,p);
		
		return ob;
	}

	@Override
	public GameObject copy() { return new MetalWall(this); }	 
}
