package tp1;

import java.util.Locale;
import tp1.control.Controller;
import tp1.logic.Game;
import tp1.view.ConsoleColorsView;
import tp1.view.ConsoleView;
import tp1.view.GameView;
import tp1.view.Messages;

public class Main
{

	/**
	 * Lemmings entry point
	 * 
	 * @param args Arguments for the game.
	 */
	public static void main(String[] args)
	{
		// Required to avoid issues with tests
       //Locale.setDefault(new Locale("es", "ES"));
		Locale.of("es", "ES");
		
		try 
		{			
			int nLevel = 1;	//default level
			if (args.length != 0) nLevel = Integer.parseInt(args[0]);

			Game game = new Game(nLevel);
			GameView view = args.length>1 ? new ConsoleView(game): new ConsoleColorsView(game);
			Controller controller = new Controller(game, view);
				
			controller.run();
		} 
	
		catch (NumberFormatException e) 
		{
			System.out.println(String.format(Messages.LEVEL_NOT_A_NUMBER_ERROR, args[0]));
		}
	}
}