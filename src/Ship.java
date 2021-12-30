import java.awt.Point;
import java.util.Observable;
//import java.util.Observer;

public class Ship extends Observable{
	
	Point currentLocation; // the current location of the ship
	Point previousLocation; // SLF - record of where the ship moved from
	OceanMap oceanMap;
	boolean sunk;
	
	/**
	 * Constructor: constructs the Ship class
	 * 
	 * @param oceanMap: ocean map class containing details about the ocean grid 
	 *  
	 ********************************************************************************/
	public Ship(OceanMap oceanMap) {
		this.oceanMap = oceanMap;
		currentLocation = oceanMap.getShipLocation();
		previousLocation = currentLocation; // SLF - initializes the previous location
		sunk = false;
		
	}
	
	
	
	
	/**
	 * returns the current location of the ship on the ocean grid 
	 *
	 * @return the point where the ship is located at.
	 *
	 ********************************************************************************/
	public Point getShipLocation() {
		return currentLocation;
	}
	
	
	
	
	/**
	 * returns the previous location of the ship on the ocean grid 
	 *
	 * @return the point the ship was previously at
	 *
	 ********************************************************************************/
	public Point getPreviousLocation() {
		return previousLocation;
	}
	
	
	
	
	/**
	 * moves the ship to the right (east) without going off the ocean grid and going
	 * on the island.
	 *
	 ********************************************************************************/
	public void moveRight() {
		if(currentLocation.x < oceanMap.getXDimensions()-1 && oceanMap.isIslandPresent(currentLocation.x+1, currentLocation.y) != true && sunk != true) {
			previousLocation = currentLocation;
			currentLocation.x++; //moves the ship east
			setChanged();
			notifyObservers();
		}
	}
	
	
	
	
	/**
	 * moves the ship to the left (west) without going off the ocean grid and going on
	 *  an island
	 *
	 ********************************************************************************/
	public void moveLeft() {
		if(currentLocation.x > 0 && oceanMap.isIslandPresent(currentLocation.x-1, currentLocation.y) != true && sunk != true) {
			previousLocation = currentLocation;
			currentLocation.x--; //moves the ship west
			setChanged();
			notifyObservers();
		}
	}
	
	
	
		
	/**
	 * moves the ship up (north) without going off the ocean grid and going on an
	 * island
	 *
	 ********************************************************************************/
	public void moveUp() {
		if(currentLocation.y > 0 && oceanMap.isIslandPresent(currentLocation.x, currentLocation.y-1) != true && sunk != true) {
			previousLocation = currentLocation;
			currentLocation.y--; //moves the ship north
			setChanged();
			notifyObservers();
		}
	}
	
	
	
	
	/**
	 * moves the ship down (south) without going off the ocean grid and going on an
	 * island
	 *
	 ********************************************************************************/
	public void moveDown() {
		if(currentLocation.y < oceanMap.getYDimensions()-1 && oceanMap.isIslandPresent(currentLocation.x, currentLocation.y+1) != true && sunk != true) {
			previousLocation = currentLocation;
			currentLocation.y++;   //moves the ship down
			setChanged();
			notifyObservers();
		}
	}
	
	
	/**
	 * Sets the sunk parameter of the ship, will determine if the ship can move.
	 * 
	 * @param s boolean variable to control if the ship can move
	 ************************************************************/
	public void setSunk(boolean s) {
		this.sunk = s;
	}
}
