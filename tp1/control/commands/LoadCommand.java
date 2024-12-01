package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameLoadException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class LoadCommand extends Command
{
	//private constants
    private static final String NAME = Messages.COMMAND_LOAD_NAME;
    private static final String SHORTCUT = Messages.COMMAND_LOAD_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_LOAD_DETAILS;
    private static final String HELP = Messages.COMMAND_LOAD_HELP;
    private String conf;
    
    //constructor
   	public LoadCommand() 
   	{
   		super(NAME, SHORTCUT, DETAILS, HELP);	//call command constructor
   	}

   	//methods
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException 
	{
		try
		{
			game.load(conf);
			view.showGame();	//show board
		}
		catch (GameLoadException e) 
		{
			throw new CommandExecuteException(e.getMessage());
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException
	{
		Command c = null;
		
		if(commandWords.length==2 && this.matchCommandName(commandWords[0])) 	//correct load command structure
		{				
			c = this;	
			conf=commandWords[1];	//config file name
		}
		
		return c;
	}
}
