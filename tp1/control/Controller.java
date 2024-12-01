package tp1.control;

import tp1.control.commands.Command;
import tp1.control.commands.CommandGenerator;
import tp1.exceptions.CommandException;
import tp1.logic.GameModel;
import tp1.view.GameView;

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller 
{
	private GameModel game;
	private GameView view;

	public Controller(GameModel game, GameView view) 
	{
		this.game = game;
		this.view = view;
	}

	/**
	 * Runs the game logic, coordinate Model(game) and View(view)
	 * 
	 */
	public void run()
	{		
		String[] words = null;

		view.showWelcome();	//lemmings [version]

		view.showGame();	//show board
		
		while ( !game.isFinished()) 
		{
			words = view.getPrompt();	//get command input
			try
			{
				Command command = CommandGenerator.parse(words); //parse command
							
				command.execute(game, view);	//execute command
			}
			catch (CommandException e) 	//Catch CommandParseException ^ CommandExecuteException
			{
	 			view.showError(e.getMessage());
	 			Throwable cause = e.getCause();
	 		
	 			if (cause != null) 
	 			    view.showError(cause.getMessage());
	 		}
		}
		
		view.showEndMessage();	//player [wins || loses || leaves the game] (ConsoleView)
	}
}		