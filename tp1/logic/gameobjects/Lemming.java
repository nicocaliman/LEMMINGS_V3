package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.RoleParseException;
import tp1.logic.Direction;
import tp1.logic.Game;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;
import tp1.logic.lemmingRoles.LemmingRoleFactory;
import tp1.logic.lemmingRoles.WalkerRole;
import tp1.view.Messages;

public class Lemming extends GameObject 
{
	private static final int FALL = 2;
	public static final String LEMMING_SHORTCUT = "L";
	public static final String LEMMING_NAME = "Lemming";
	//attributes
	private Direction direction;
	private LemmingRole role;
	private int fallForce;

	//constructor
	public Lemming(Game game, Position position)
	{
		super(game,position);
		this.direction = Direction.RIGHT;	//default movement
		this.role = new WalkerRole();
		this.fallForce = 0;	//initially 0
	}
	public Lemming(Lemming l) {
		super(l);
		this.direction = l.direction;	//default movement
		this.role = l.role;
		this.fallForce = l.fallForce;	
	}
	public Lemming(GameWorld game, Position position, String line) throws ObjectParseException
	{
		super(game,position);
		this.direction = getLemmingDirectionFrom(line);	
		this.role = getLemmingRoleFrom(line);
		this.fallForce = getLemmingHeigthFrom(line);	
	}
	
	//methods
	@Override
	public void update() 
	{
		if (this.isAlive)		//if is alive 
		{			
			this.game.receiveInteractionsFrom(this);	//game world receives an interaction from a lemming
			role.play(this);
		}
	}
	
	@Override
	public void dead() { this.isAlive = false; }	//set lemming´s life	
	
	public boolean isInAir() 
	{
		Position below = new Position(Direction.DOWN, this.position);	//position below lemming
		
		//return true if position below lemming is not solid, else return false if there is a wall down below lemming´s position
		return game.isInAir(below);	
	}
	
	public void walkOrFall()
	{	
		if (this.isInAir()) 
		{	//if lemming is falling				
			this.doFall();				//fall action					
		}
			
		else 
		{	//if lemming is walking
			this.doWalk();		//walk action	
		}		
	}
	
	private void doWalk()
	{
		if (this.fallForce > FALL) 
		{
			this.dead();
			this.game.lemmingDead();
		}
		
		else
		{
			Position newPosition;
			//this.fallForce = 0;
			this.fallForceToZero();
			
			if (this.getDirection() == Direction.RIGHT) {	//if moving towards right	
				newPosition = new Position(Direction.RIGHT, this.position);
				this.moveAction(newPosition);
			}
			
			else if (this.getDirection() == Direction.LEFT) {	//if moving towards left
				newPosition = new Position(Direction.LEFT, this.position);
				this.moveAction(newPosition);
			}
		}		
	}

	private void moveAction(Position newPosition) 
	{
		if (newPosition.isOutOfBoard()|| this.game.isSolid(newPosition)) 
		{ //if new position touches right wall
			this.direction = this.direction.opposite();	//set lemming´s direction
		}
		
		else
		{	// if there is no obstacle
			this.position = newPosition;	//set new position
		}		
	}

	public void doFall()
	{
		this.fallForce++;
		Position newPosition = new Position(Direction.DOWN, this.position);
						
		if (newPosition.isOutOfBoard())
		{	//if lemming jumps into the void
			this.dead();	//lemming dies
			this.game.lemmingDead();
		}
		
		else if (this.game.isSolid(newPosition) && this.fallForce > FALL) 
		{
				this.dead();
				this.game.lemmingDead();
		}
		
		else 
		{
			this.position = newPosition;
		}
	}
	
	public void fallForceToZero() {	this.fallForce = 0; }

	@Override
	public String getIcon() 
	{
		return this.role.getIcon(this);	//if lemming´s direction is set to the right, show RIGHT LEMMING, else, show LEFT LEMMING representation
	}
	
	@Override
	public String toString() 
	{
		return this.position.toString() + this.getIcon() + this.direction.toString();
	}
	
	public Direction getDirection() {return this.direction; }	//lemming´s direction
	
	@Override
	public boolean isSolid() { return false; }	//not a wall
			
	@Override
	public boolean setRole(LemmingRole role) 
	{
		boolean isSet = true;
		if(role.equals(this.role)) isSet = false;
		else this.role=role;
		return isSet;
	}
	
	public void disableRole() 
	{
		WalkerRole role = new WalkerRole();
		setRole(role);
	}
	
	public boolean isObjectInDir(Direction d, GameObject go) 
	{
		Position p=new Position(d, this.position);
		return go.isInPosition(p);
	}
	
	@Override
	public boolean interactWith(Wall wall)	//lemming interacts with wall
	{
		return this.role.interactWith(wall, this);	//call down caver role 
	}
	
	@Override
	public boolean interactWith(ExitDoor obj)	//lemming interacts with exit door
	{
		
		boolean interact = this.role.interactWith(obj, this);
		
	    if (interact)		
	    {	       
	        this.dead();	//lemming dead	
	        this.game.lemmingArrived(); 	//update numberoflemminginboard & numberoflemmingsexit	
	    }
	    
	    return interact; // No hay interacción si no está en la posición de la puerta
	}
	
	@Override
	public boolean receiveInteraction(GameItem other) 	
	{
		return other.interactWith(this);	//call interactWith() from other (object)
    }
	
	@Override
	public String getName() { return LEMMING_NAME; }

	@Override
	public String getShortCut() { return LEMMING_SHORTCUT; }	
	
	private static Direction getLemmingDirectionFrom(String line) throws ObjectParseException
	{
		Direction dir;
		try {
			String parts[] = line.split("\\s+");
 			dir = Direction.stringToDir(parts[2]);
		}catch (NumberFormatException e){
			 throw new ObjectParseException();
		}
		catch (NullPointerException e){
			throw new ObjectParseException();
		}
		if(dir==null) //ex.: NORTH
			throw new ObjectParseException(Messages.UNKNOWN_DIRECTION.formatted(line));
		else if (dir == Direction.UP || dir == Direction.DOWN)
			throw new ObjectParseException(Messages.INVALID_DIRECTION.formatted(line));
			
		return dir;
	}
	
	private static int getLemmingHeigthFrom(String line) throws ObjectParseException 
	{
		int i;
		try {

			String parts[] = line.split("\\s+");
			i=Integer.parseInt(parts[3]);
		}catch (NumberFormatException e){
			 throw new ObjectParseException(Messages.UNKNOWN_DIRECTION.formatted(line));
		}
		catch (NullPointerException e){
			throw new ObjectParseException();
		}
		
		return i;
	}
	private static LemmingRole getLemmingRoleFrom(String line) throws ObjectParseException
	{
		LemmingRole r=null;
		try 
		{
			String parts[] = line.split("\\s+");
			r= LemmingRoleFactory.parse(parts[4]);
		} 
		catch (RoleParseException e) 
		{
			throw new ObjectParseException(Messages.INVALID_ROLE.formatted(line));
		}
		return r;
		
	}
	
	@Override
	public GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException
	{
		GameObject ob=null;
		Position p;
			
		p=getPositionFrom(line);
		
		if(this.matchGameObjectName(line)) 
			ob=new Lemming(game,p,line);
		
		return ob;
	}
	@Override
	public GameObject copy() { return new Lemming(this); }	 
}
