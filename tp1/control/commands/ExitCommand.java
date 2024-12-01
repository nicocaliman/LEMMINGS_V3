package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ExitCommand extends NoParamsCommand
{
	//private constants
	private static final String NAME = Messages.COMMAND_EXIT_NAME;
	private static final String SHORTCUT = Messages.COMMAND_EXIT_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_EXIT_DETAILS;
	private static final String HELP = Messages.COMMAND_EXIT_HELP;

	//constructor
	public ExitCommand() 
	{
		super(NAME, SHORTCUT, DETAILS, HELP); 
	}
	
	//methods
    @Override
	public void execute(GameModel game, GameView view)
    {
		game.exit();	//quit game
	}
}