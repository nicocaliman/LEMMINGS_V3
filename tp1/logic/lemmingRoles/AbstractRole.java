package tp1.logic.lemmingRoles;

import tp1.logic.Direction;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;

public abstract class AbstractRole implements LemmingRole 
{
	//attributes
	private final String name;
	private final String symbol;
	private final String help;
	
	//constructor
	public AbstractRole (String name, String symbol, String help) 
	{
		this.name = name;
		this.symbol = symbol;
		this.help = help;
	}
	
	//methods 	
	@Override
	public LemmingRole parse(String input)	 
	{
		LemmingRole role = null;
		if(input.equalsIgnoreCase(this.getName())||input.equalsIgnoreCase(this.getSymbol())) role = this;	//if role found
		return role;	
	}

	@Override
	public String getName() { return this.name; }	//get subclases role name
	
	@Override
	public String getHelp()	{ return this.help; }	//get subclasses role description
		
	public String getSymbol() { return this.symbol; }	//get subclasses role symbol
	
	@Override
	public boolean receiveInteraction(GameItem other, Lemming lemming) 
	{
		 return other.interactWith(lemming);
	}

	@Override
	public boolean interactWith(Lemming receiver, Lemming lemming) { return false; }	//default behaviour
	
	@Override
	public boolean interactWith(Wall wall, Lemming lemming)	{ return false; }	//default behaviour
	
	@Override
	public boolean interactWith(ExitDoor door, Lemming lemming)	{ 
		return lemming.isObjectInDir(Direction.NONE, door); 
		}	//default behaviour
		 
	@Override
	public boolean equals(Object obj) {
		return this == obj || obj != null && obj instanceof LemmingRole  && this.name.equals(((LemmingRole)obj).getName());
	}
}
