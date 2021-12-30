import java.awt.Point;

public class WaitStrategy implements Pstrategy {

	/**
	 * Method used for returning the name of the strategy.
	 * 
	 * @return The name of the strategy
	 ********************************************************/
	@Override
	public String getName() {return "Waiter";}

	/**
	 * AI logic to determine where the ship should move towards.
	 * 
	 * @param the point of the pirate's current location and the ship they are listening to
	 * 
	 * @return the point the pirate should be trying to head towards.
	 ******************************************************************************************/
	@Override
	public Point target(Point currentLocation, Ship ship) {
		Point currentShip = ship.currentLocation;
		int xdif = currentLocation.x - currentShip.x;
		int ydif = currentLocation.y - currentShip.y;
		
		if (Math.abs(xdif) + Math.abs(ydif) < 5) {  return currentShip;  } // when player is close
		return currentLocation; // else stay still
	}

}
