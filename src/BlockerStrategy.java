import java.awt.Point;

public class BlockerStrategy implements Pstrategy {

	/**
	 * Method used for returning the name of the strategy.
	 * 
	 * @return The name of the strategy
	 ********************************************************/
	@Override
	public String getName() {return "Blocker";}

	/**
	 * AI logic to determine where the ship should move towards.
	 * 
	 * @param the point of the pirate's current location and the ship they are listening to
	 * 
	 * @return the point the pirate should be trying to head towards.
	 ******************************************************************************************/
	@Override
	public Point target(Point currentLocation, Ship ship) {
		// Pirate will try to identify where the player is going and try to get in front
		Point previousShip = ship.previousLocation;
		Point currentShip = ship.currentLocation;
		
		int xdif = currentShip.x - previousShip.x;
		int ydif = currentShip.y - previousShip.y;
		
		Point temp = ship.currentLocation;
		temp.translate(xdif * 3, ydif * 3);
		return temp;
	}

}
