import java.awt.Point;

import javafx.collections.ObservableList;
import javafx.scene.Node;

//Implementing the Monster class using the composite design pattern 
//This is the Component Node

public interface Monster {
	
	//move the monster around the grid 
	public void moveMonster();
	public void moveMonsterGroupMove();
	
	//retrieve and set X and Y positions on the grid 
	public int getXLocation();
	public int getYLocation ();
	public Point getLocation();
	public void setXLocation(int x);
	public void setYLocation(int y);
	
	// method to stop monster when gameOver/Victory
	public void setHalt(boolean h);
	
	// method to see if sharks overlap with player
	public boolean overlap(Point p);
	
	//Only implemented on the composite node 
	public void addMonster (Shark shark);
	public void removeMonster (Shark shark);
	
	//Adds monster to the stage and the scene (Map Grid)
	void pushToStage(ObservableList<Node> sceneGraph);
	
}
