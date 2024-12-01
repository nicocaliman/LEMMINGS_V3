package tp1.logic.lemmingRoles;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;

public interface LemmingRole	//LemmingRole = modify lemmming´s role
{
	 public void start( Lemming lemming );
	 public void play( Lemming lemming );
	 public String getIcon( Lemming lemming );
	 public LemmingRole parse(String input);
	 public String getHelp();
	 public String getName();
	 public boolean equals(Object ob);
	 public boolean receiveInteraction(GameItem other, Lemming lemming);
	 public boolean interactWith(Lemming receiver, Lemming lemming);
	 public boolean interactWith(Wall wall, Lemming lemming);
	 public boolean interactWith(ExitDoor door, Lemming lemming);
}
