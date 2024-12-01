package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public abstract class Command 
{
	//private constants
	private final String name;
	private final String shorcut;
	private final String details;
	private final String help;
	
	//constructor
	public Command(String name, String shorcut, String details, String help)
	{
		this.name = name;
		this.shorcut = shorcut;
		this.details = details;
		this.help = help;
	}
	
	//methods
	protected String getName() { return name; }
	protected String getShortcut() { return shorcut; }
	protected String getDetails() { return details; }
	protected String getHelp() { return help; }

	public abstract void execute(GameModel game, GameView view) throws CommandExecuteException;	  
	public abstract Command parse(String[] commandWords) throws CommandParseException;

	protected boolean matchCommandName(String name) 
	{
		return getShortcut().equalsIgnoreCase(name) || getName().equalsIgnoreCase(name);
	}

	public String helpText()
	{
		return Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(getDetails(), getHelp()));
	}
}