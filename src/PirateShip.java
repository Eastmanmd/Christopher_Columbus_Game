import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PirateShip implements Observer {
	
	Point currentLocation; // the current location of the Pirate Ship
	Point target; // a point for the Pirate Ship to move towards
	OceanMap oceanMap;
	Pstrategy strategy;
	Image image;
	ImageView imageView;
	
	
	/**
	 * Constructor: constructs the Pirate Class
	 * 
	 *  @param oceanMap: ocean map class containing details about the ocean grid 
	 *  
	 ********************************************************************************/
	public PirateShip(Observable observable, OceanMap oceanMap, Pstrategy strategy) {
		Random rand = new Random();
		observable.addObserver(this);
		this.oceanMap = oceanMap;
		this.strategy = strategy;
		
		boolean shipPlaced = false;
		
		while(shipPlaced == false){
			int x = rand.nextInt(OceanMap.xdimension);
			int y = rand.nextInt(OceanMap.ydimension);
			
			if(oceanMap.getMap()[x][y] == false){
				currentLocation = new Point(x,y);
				shipPlaced = true;
			}
		}
	}

	/**
	 * Method called when the player has moved, will cause the pirate ship to move.
	 * 
	 ********************************************************************************/
	@Override
	public void update(Observable observable, Object arg) {
		if (observable instanceof Ship) {
			Ship ship = (Ship)observable;
			this.target = strategy.target(currentLocation, ship);
			movePirate();
		}
	}
	
	
	/**
	 * Move the pirate ship to the right 
	 *  
	 **********************************************************************************************************************************/
	public void moveRight() {
		if(currentLocation.x < oceanMap.getXDimensions()-1 && oceanMap.isIslandPresent(currentLocation.x+1, currentLocation.y) != true && !oceanMap.getTreasure().equals(new Point(currentLocation.x+1, currentLocation.y))) {
			currentLocation.x++; //moves the ship east
		}
	}
	
	
	/**
	 * Moves the pirate ship to the left 
	 *  
	 **********************************************************************************************************************************/
	public void moveLeft() {
		if(currentLocation.x > 0 && oceanMap.isIslandPresent(currentLocation.x-1, currentLocation.y) != true && !oceanMap.getTreasure().equals(new Point(currentLocation.x-1, currentLocation.y))) {
			currentLocation.x--; //moves the ship west
		}
	}
	
	
	/**
	 * Moves the pirate ship up 
	 *  
	 **********************************************************************************************************************************/
	public void moveUp() {
		if(currentLocation.y > 0 && oceanMap.isIslandPresent(currentLocation.x, currentLocation.y-1) != true && !oceanMap.getTreasure().equals(new Point(currentLocation.x, currentLocation.y-1))) {
			currentLocation.y--; //moves the ship north
		}
	}
	
	
	/**
	 * Moves the pirate ship down
	 *  
	 **********************************************************************************************************************************/
	public void moveDown() {
		if(currentLocation.y < oceanMap.getYDimensions()-1 && oceanMap.isIslandPresent(currentLocation.x, currentLocation.y+1) != true && !oceanMap.getTreasure().equals(new Point(currentLocation.x, currentLocation.y+1))) {
			currentLocation.y++;   //moves the ship down
		}
	}
	
	
	/**
	 * Moves the pirate ship down
	 *  
	 **********************************************************************************************************************************/
	public Point getPirateLocation() {
		return currentLocation;
	}
	
	
	/**
	 * Moves the pirate ship based on the location of the ship, will not let pirates move onto treasure space
	 *  
	 **********************************************************************************************************************************/
	public void movePirate() {		
		if ((currentLocation.x - target.x) < 0 && oceanMap.isIslandPresent(currentLocation.x+1, currentLocation.y) != true) {
			moveRight();
		}
		
		else if ((currentLocation.x - target.x) > 0 && oceanMap.isIslandPresent(currentLocation.x-1, currentLocation.y) != true) {
			moveLeft();
		}
		
		else if ((currentLocation.y - target.y ) < 0 && oceanMap.isIslandPresent(currentLocation.x, currentLocation.y+1) != true) {
			moveDown();
			
		}
		
		else if ((currentLocation.y - target.y ) > 0 && oceanMap.isIslandPresent(currentLocation.x, currentLocation.y-1) != true) {
			moveUp();
		}
		

	}
	
	/**
	 * Sets the location of the pirate ship to the specified location 
	 *  
	 **********************************************************************************************************************************/
	public void setLocation(Point newLocation) {
		currentLocation = newLocation;
	}

	
	/**
	 * Sets the strategy of the pirate ship
	 * 
	 * *********************************************************************************************************************************/
	public void setStrategy(Pstrategy newStrategy) {
		strategy = newStrategy;
	}
	
	/**
	 * Sets the image and ImageView of the pirate ship
	 *
	 ***********************************************************************************************************************************/
	public void setImage(Image newimage, int scale) {
		image = newimage;
		imageView = new ImageView(image);
		imageView.setX(currentLocation.x * scale);
		imageView.setY(currentLocation.y * scale);
	}
	
	/**
	 * Returns the ImageView
	 * 
	 * @return the imageView for this particular pirate
	 ***********************************************************************************************************************************/
	public ImageView getImageView() {
		return imageView;
	}
}
