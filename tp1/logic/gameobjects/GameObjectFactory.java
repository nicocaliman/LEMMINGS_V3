package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.view.Messages;


public class GameObjectFactory {
	//added game objects
	private static final List<GameObject> availableObjects = Arrays.asList(
			 new ExitDoor(null, null),
			 new Lemming(null, null),
			 new MetalWall(null, null),
			 new Wall(null, null)
		     
	);
	
	public static GameObject parse(String input, GameWorld game) throws ObjectParseException, OffBoardException 
	{
		GameObject object=null;
		for (GameObject go: availableObjects) 
		{
			object = go.parse(input, game);
			if(object != null) return object;
		}
		throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(input));
	}
}
