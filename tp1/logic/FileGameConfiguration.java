package tp1.logic;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.view.Messages;

import java.io.*;

public class FileGameConfiguration implements GameConfiguration
{
	//attributes
	private int numberOfCycles;
	private int numberLemmingsInBoard;
	private int numberOfDeadLemmings;
	private int numberOfExitLemmings;
	private int numberOfLemmingsToWin;
	private GameObjectContainer container;
	public static final FileGameConfiguration NONE = new FileGameConfiguration();

	//constructor
	public FileGameConfiguration() 
	{
	}
	
	public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException
	{
		this();
		try (BufferedReader a = new BufferedReader(new FileReader(fileName)))
		{	
			 String line; 
	         line = a.readLine();	//read 1st line
	         String[] l=line.split(" ");
	    
	         if(l.length!=5) //invalid game status
	        	 throw new GameLoadException(Messages.ERROR_INVALID_FILE_CONF.formatted(fileName)+ (Messages.LINE_SEPARATOR + Messages.ERROR2.formatted(Messages.INVALID_GAME_STATUS.formatted(line))));
	         
	         this.numberOfCycles=Integer.parseInt(l[0]);
	         this.numberLemmingsInBoard=Integer.parseInt(l[1]);
	         this.numberOfDeadLemmings=Integer.parseInt(l[2]);
	         this.numberOfExitLemmings=Integer.parseInt(l[3]);
	         this.numberOfLemmingsToWin=Integer.parseInt(l[4]);
	 		 
	         line = a.readLine();	//read 2nd line
	 		 this.container=new GameObjectContainer();
	 		 
	 		 while (line != null)
	 		 {
	 			this.container.add(GameObjectFactory.parse(line, game));
	 			line = a.readLine();	//read following line
	 		 }
	 	} 
		catch (ObjectParseException | OffBoardException e) //unknown game object, invalid object position
		{
	 		throw new GameLoadException(Messages.ERROR_INVALID_FILE_CONF.formatted(fileName) + Messages.LINE_SEPARATOR + Messages.ERROR2.formatted(e.getMessage()));
	 	}		
		catch (IOException e) //file not found
		{
			throw new GameLoadException(Messages.ERROR_INVALID_FILE_CONF.formatted(fileName) + (Messages.LINE_SEPARATOR + Messages.ERROR2.formatted(Messages.FILE_NOT_FOUND.formatted(fileName))));
		}
	}
	
	public FileGameConfiguration(FileGameConfiguration f) {
		this.numberOfCycles=f.numberOfCycles;
		this.numberLemmingsInBoard=f.numberLemmingsInBoard;
		this.numberOfDeadLemmings=f.numberOfDeadLemmings;
		this.numberOfExitLemmings=f.numberOfExitLemmings;
		this.numberOfLemmingsToWin=f.numberOfLemmingsToWin;
		this.container=f.container;
	}
	@Override
	public int getCycle() {	return this.numberOfCycles;	}

	@Override
	public int numLemmingsInBoard() { return this.numberLemmingsInBoard; }

	@Override
	public int numLemmingsDead() { return this.numberOfDeadLemmings; }

	@Override
	public int numLemingsExit() { return this.numberOfExitLemmings;	}

	@Override
	public int numLemmingToWin() { return this.numberOfLemmingsToWin; }

	@Override
	public GameObjectContainer getGameObjects() { return new GameObjectContainer(this.container); }
}
