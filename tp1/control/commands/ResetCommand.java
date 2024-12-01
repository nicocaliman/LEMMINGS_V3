package tp1.control.commands;


import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends Command 
{	
	//private constants
	private static final String NAME = Messages.COMMAND_RESET_NAME;
    private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
    private static final String HELP = Messages.COMMAND_RESET_HELP;
    
    //attributes
    private int nLevel;
    
    //constructor
    public ResetCommand() 
    {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.nLevel=-1;
	}
    
	private ResetCommand(int level) 
	{
		this();
		this.nLevel=level;
	}
	
    //methods
    @Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException
    {
    	if(this.nLevel >= -1 && this.nLevel <= 2)	//reset
    	{
    		game.reset(this.nLevel); //reset the game
    		view.showGame();	//show board	
    	}
    	
    	else
    		throw new CommandExecuteException(Messages.INVALID_LEVEL_NUMBER);	//show error
    }

	@Override
	public Command parse(String[] commandWords) throws CommandParseException 
	{
		try 
		{
			Command command = null;
			if (commandWords.length > 0 && matchCommandName(commandWords[0])) 
			{
				if (commandWords.length == 1)
					command = new ResetCommand();
				else if (commandWords.length == 2)
					command =new ResetCommand(Integer.parseInt(commandWords[1]));
				else
					throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
			return command;
		} 
		catch (NumberFormatException e) 
		{
			throw new CommandParseException(Messages.INVALID_LEVEL_NUMBER_1.formatted(commandWords[1]));
		} 
	}
}
