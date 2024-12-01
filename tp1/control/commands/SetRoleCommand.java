package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.RoleParseException;
import tp1.logic.GameModel;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;
import tp1.logic.lemmingRoles.LemmingRoleFactory;
import tp1.view.GameView;
import tp1.view.Messages;

public class SetRoleCommand extends Command
{	
	//private constants
    private static final String NAME = Messages.COMMAND_SET_ROLE_NAME;
    private static final String SHORTCUT = Messages.COMMAND_SET_ROLE_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_SET_ROLE_DETAILS;
    private static final String HELP = Messages.COMMAND_SET_ROLE_HELP + LemmingRoleFactory.RoleHelp();
    private LemmingRole role;
    private Position position;
    
    //constructor
	public SetRoleCommand() 
	{
		super(NAME, SHORTCUT, DETAILS, HELP);	//call command constructor
	}
	
	private SetRoleCommand(LemmingRole role, Position position)
	{
		this();
		this.role = role;
		this.position = position;		
	}
	
	//methods
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException 
	{
		try
		{
			if (game.setRole(role, position))
			{
				game.update();	//update game
				view.showGame();	//show updated board	
			}
			
			else
				throw new CommandExecuteException(Messages.SETROLE_NOT_LEM_ERROR.formatted(position.toString(), role.getName()));
		} 
		catch (OffBoardException obe) //setRole from Game.java
		{
			throw new CommandExecuteException(Messages.COMMAND_EXECUTE_PROBLEM + Messages.LINE_SEPARATOR + Messages.ERROR2.formatted(obe.getMessage()));	//(message, cause)
		}		
	}  

	@Override
	public Command parse(String[] commandWords) throws CommandParseException
	{
		Command c = null;
				
		if((commandWords.length < 4 ||commandWords.length > 4) && this.matchCommandName(commandWords[0])) 	//setRole Walker I 2 33
			throw new CommandParseException((Messages.COMMAND_INCORRECT_PARAMETER_NUMBER));
				
		if(commandWords.length==4 && this.matchCommandName(commandWords[0])) 
		{	
			try
			{		
				//sr <role> <y><x> 
				int x = Integer.parseInt(commandWords[3])-1;	//coord. x
				int y = this.stringToInt(commandWords[2]);		//coord. y
			
				position = new Position(x, y);
				role = LemmingRoleFactory.parse(commandWords[1]);	//lemming role
				
				c = new SetRoleCommand(role, position);	//setRoleCommand
			}
			catch (NumberFormatException e) 
			{
			 	throw new CommandParseException(Messages.INVALID_POSITION.formatted(Messages.POSITION.formatted(commandWords[2], commandWords[3])));
			}
		    catch (RoleParseException e) //LemmingRoleFactory.parse()
			{
		    	throw new CommandParseException(Messages.INVALID_COMMAND_PARAMETERS + (Messages.LINE_SEPARATOR + Messages.ERROR2.formatted(e.getMessage().formatted(commandWords[1]))));
			}
		}
		
		return c;
	}
	
	private int stringToInt(String s) 
	{
		return s.hashCode()-"A".hashCode();
	}
}