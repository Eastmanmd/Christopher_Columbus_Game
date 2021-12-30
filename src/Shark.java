import java.awt.Point;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Circle;


public class Shark implements Monster, Runnable {
	
	boolean[][] mapGrid; //access to the ocean grid with locations of islands and open ocean
	int xLocation; // x coordinate of the shark
	int yLocation; // y coordinate of the shark
	int xoceanDimension; // stores the x dimension of the ocean grid
	int yoceanDimension; // stores the y dimension of the ocean grid
	
	Random rand = new Random();
	int scale; //scaling factor used on the ocean grid
	int radius = 10; //radius/size of the shark
	
	Circle circle; //sharks are stored as circles 
	Image sharkImage;
	ImageView sharkImageView;
	Point sharkLocation; 
	
	boolean halt;
	Point treasureIsland;

	/**
	 * Constructor for the Shark class
	 * 
	 * @param map: the OceanMap the game will be played on
	 * @param scale: the scale of the images to display on the map
	 ***************************************************************/
	public Shark(OceanMap map, int scale)
	{
		this.scale = scale;
		this.mapGrid = map.getMap();
		this.treasureIsland = map.getTreasure();
		xoceanDimension = map.getXDimensions();
		yoceanDimension = map.getYDimensions();
		
		sharkLocation = addShark(); //place shark on Map
		
		// default image for sharks
		sharkImage = new Image("sharkFin.png", scale, scale, true, true);
		sharkImageView = new ImageView(sharkImage);
		sharkImageView.setX(scale * sharkLocation.x);
		sharkImageView.setY(scale * sharkLocation.y);
		
		circle = new Circle();
		circle.setRadius(radius);
		setXLocation(sharkLocation.x);
		setYLocation(sharkLocation.y);
		
		halt = false;
	}
	
	/**
	 * Method to run the thread witch determines when the shark moves.
	 * 
	 ********************************************************************/
	@Override
	public void run() {
		while (halt != true) 
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			moveMonster();
		}
	}

	/**
	 * Method to move the shark when it is not in the group.
	 * 
	 **************************************************************/
	@Override
	public void moveMonster() {
		int xMove = getXLocation() + rand.nextInt(4)-1;
		if (xMove >= 0 && xMove < xoceanDimension - 1) 
		{
			int yMove = getYLocation() + rand.nextInt(4)-1;	
			if (yMove >= 0 && yMove < yoceanDimension - 1)
			{
				// check to see if the space is available to be moved to
				if (mapGrid[xMove][yMove] == false && !treasureIsland.equals(new Point(xMove, yMove)))
				{
					setXLocation(xMove);
//					setYLocation(yMove);
				}
			}
		}
	}
	
	/**
	 * Method to move the shark when it is in a group.
	 * 
	 ************************************************************/
	public void moveMonsterGroupMove() {
		int xMove = getXLocation() + rand.nextInt(3)-1;
		if (xMove >= 0 && xMove < xoceanDimension - 1) 
		{
			int yMove = getYLocation() + rand.nextInt(3)-1;
			if (yMove >= 0 && yMove < yoceanDimension - 1)
			{
				// check if the space is available to be moved to
				if (mapGrid[xMove][yMove] == false && !treasureIsland.equals(new Point(xMove, yMove)))
				{
					setXLocation(xMove);
					setYLocation(yMove);
				}
			}
		}	
	}

	/**
	 * Method for getting the xLocation of the shark
	 * 
	 * @return integer of xLocation
	 *********************************************************/
	@Override
	public int getXLocation() {
		return xLocation;
	}

	/**
	 * Method for getting the yLocation of the shark
	 * 
	 * @return integer of yLocaiton
	 *******************************************************/
	@Override
	public int getYLocation() {
		return yLocation;
	}
	
	@Override
	public Point getLocation() {
		return new Point(xLocation, yLocation);
	}

	/**
	 * Method for setting the xLocation
	 * 
	 * @param x: the value to set
	 ***********************************************/
	@Override
	public void setXLocation(int x) {
		circle.setCenterX(x*scale + (scale/2));
		sharkImageView.setX(x*scale);
		xLocation = x;
	}

	/**
	 * Method for setting the yLocation
	 * 
	 * @param y: the value to set
	 ********************************************/
	@Override
	public void setYLocation(int y) {
		circle.setCenterY(y*scale + (scale/2));
		sharkImageView.setY(y*scale);
		yLocation = y;
		
	}

	/**
	 * Method that does not apply to this class but needed for interface
	 * 
	 ********************************************************************/
	@Override
	public void addMonster(Shark shark) {
		// doesn't apply for leaf node
		
	}

	/**
	 * Method that does not apply to this class but needed for interface
	 * 
	 ********************************************************************/
	@Override
	public void removeMonster(Shark shark) {
		// doesn't apply for leaf node 
		
	}

	/**
	 * Method that will push all the shark to the stage
	 * 
	 * @param sceneGraph: the stage witch the sharks will be pushed to
	 ********************************************************************/
	@Override
	public void pushToStage(ObservableList<Node> sceneGraph) {
//		sceneGraph.add(circle);
		sceneGraph.add(sharkImageView);
	}
	
	/**
	 * Determines a location where the shark can be on the map, and returns, sets
	 * the shark to that value and returns the given position.
	 * 
	 * @return the new point where that shark was randomly assigned to be.
	 ****************************************************************************/
	public Point addShark()
	{
		boolean sharkOnMap = false;
		int x = rand.nextInt(xoceanDimension);
		int y = rand.nextInt(yoceanDimension);
		
		while(sharkOnMap == false) {
			x = rand.nextInt(xoceanDimension);
			y = rand.nextInt(yoceanDimension);
			if (mapGrid[x][y] == false) {      //check if the position has an island
				xLocation = x;
				yLocation = y;
				sharkOnMap = true;	
			}
		}
		return new Point(x,y);
			
	}
	
	/**
	 * Method for returning the Circle object displaying the shark
	 * 
	 * @return circle object displaying the shark on the map
	 **************************************************************/
	public Circle getShark()
	{
		return circle; 
	}

	/**
	 * Method for determining if the shark overlaps with the player
	 * 
	 * @return true if player overlaps with shark location, else false
	 *******************************************************************/
	public boolean overlap(Point p) {
		if (this.getLocation().equals(p)) {return true;}
		return false;
	}
	
	/**
	 * Method to set if the shark should be moving or not.
	 * 
	 *
	 * @param b: boolean to be be used to set the value
	 *********************************************************/
	@Override
	public void setHalt(boolean b) {
		halt = b;
	}
	
}
