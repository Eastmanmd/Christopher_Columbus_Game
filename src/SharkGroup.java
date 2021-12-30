import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

//This is the composite class (composite design pattern)
//changes the moving pattern of sharks within the group

public class SharkGroup implements Monster, Runnable{
	
	boolean[][] mapGrid; //access to the ocean grid with locations of islands and open ocean
	int oceanDimensions;  //stores the dimensions of the ocean grid
	int xoceanDimension;
	int yoceanDimension;
	
	Random rand = new Random();
	int scale; //scaling factor used on the ocean grid
	int radius = 10; //radius/size of the shark
	
	int xLocation; // x coordinate of the shark
	int yLocation; // y coordinate of the shark
	 
	Point sharkLocation; 
	
	List<Monster> shiverOfSharks;
	
	boolean halt;
	
	/**
	 * Constructor for the SharkGroup
	 * 
	 *****************************************/
	public SharkGroup() {
		halt = false;
		shiverOfSharks = new ArrayList<Monster>();
	}
	
	/**
	 * Runs the thread that will determine when sharks can move, and update the sharks
	 * 
	 *********************************************************************************/
	@Override
	public void run() {
		while (halt != true) // will stop sharks from moving when halt is true
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			moveMonster();  
		}
		
	}

	/**
	 * Method called to move all Sharks in the group.
	 * 
	 **************************************************/
	@Override
	public void moveMonster() {
		for (int i = 0; i < shiverOfSharks.size(); i++) {
			shiverOfSharks.get(i).moveMonsterGroupMove();
        }
	}
	
	/**
	 * Method for getting the xLocation of the shark group
	 * 
	 * @return integer of xLocation
	 *********************************************************/
	@Override
	public int getXLocation() {
		return xLocation;
	}

	/**
	 * Method for getting the yLocation of the shark group
	 * 
	 * @return integer of yLocaiton
	 *******************************************************/
	@Override
	public int getYLocation() {
		return yLocation;
	}

	/**
	 * Method for setting the xLocation
	 * 
	 * @param x: the value to set
	 ***********************************************/
	@Override
	public void setXLocation(int x) {
		for (int i = 0; i < shiverOfSharks.size(); i++) {
			shiverOfSharks.get(i).setXLocation(x);
        }
	}

	/**
	 * Method for setting the yLocation
	 * 
	 * @param y: the value to set
	 ********************************************/
	@Override
	public void setYLocation(int y) {
		for (int i = 0; i < shiverOfSharks.size(); i++) {
			shiverOfSharks.get(i).setYLocation(y);
        }
	}

	/**
	 * Method for adding a monster to the group.
	 * 
	 * @param shark: the shark to be added
	 **********************************************/
	@Override
	public void addMonster(Shark shark) {	
		shark.getShark().setFill(Color.RED);
		shark.sharkImage = new Image("sharkFinR.png", shark.scale, shark.scale, true, true);
		shark.sharkImageView = new ImageView(shark.sharkImage);
		shark.sharkImageView.setX(shark.getXLocation()*shark.scale);
		shark.sharkImageView.setY(shark.getYLocation()*shark.scale);
		shiverOfSharks.add(shark);
	}

	/**
	 * Method for removing shark from the group.
	 * 
	 * @param shark: the shark to be removed
	 ********************************************/
	@Override
	public void removeMonster(Shark shark) {
		shark.getShark().setFill(Color.BLACK);
		shark.sharkImage = new Image("sharkFin.png", shark.scale, shark.scale, true, true);
		shark.sharkImageView = new ImageView(shark.sharkImage);
		shark.sharkImageView.setX(shark.getXLocation()*shark.scale);
		shark.sharkImageView.setY(shark.getYLocation()*shark.scale);
		shiverOfSharks.remove(shark);
	}

	/**
	 * Method that will push all the sharks in the group to the stage
	 * 
	 * @param sceneGraph: the stage witch the sharks will be pushed to
	 ********************************************************************/
	@Override
	public void pushToStage(ObservableList<Node> sceneGraph) {
		for (int i = 0; i < shiverOfSharks.size(); i++) {
			shiverOfSharks.get(i).pushToStage(sceneGraph);
        }
	}

	/**
	 * Empty method does nothing for this class but needed for interface
	 * 
	 *******************************************************************/
	@Override
	public void moveMonsterGroupMove() {
		// Does not apply for composite node 
	}
	
	/**
	 *  Method that will determine if any sharks in the group overlap with the players position
	 *  
	 * @param p: point object of the players location
	 * @return true if at least one shark overlaps with p, else false
	 ******************************************************************************************/
	public boolean overlap(Point p) {
		for (int i = 0; i < shiverOfSharks.size(); i ++) {
			Monster s = shiverOfSharks.get(i);
			if (s.overlap(p)) {return true;} // if a single monster in the list overlaps return true
		}
		return false; // no monsters overlapped
	}

	/**
	 * Method to return the position point of the shark group
	 * 
	 * @return point where the shark group is located on the board
	 *******************************************************************/
	@Override
	public Point getLocation() {
		// Probably not needed but giving it anyway
		return new Point(xLocation, yLocation);
	}
	
	/**
	 * Method to halt the movements of all the sharks in the group
	 * 
	 * @param b: boolean to set to all the sharks
	 *****************************************************************/
	@Override
	public void setHalt(boolean b) {
		halt = b;
		for (int i = 0; i < shiverOfSharks.size(); i++) {
			shiverOfSharks.get(i).setHalt(b);
		}
	}

}
