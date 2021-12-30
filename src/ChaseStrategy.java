import java.awt.Point;

public class ChaseStrategy implements Pstrategy {

	/**
	 * Method used for returning the name of the strategy.
	 * 
	 * @return The name of the strategy
	 ********************************************************/
	@Override
	public String getName() {return "Chaser";}

	/**
	 * AI logic to determine where the ship should move towards.
	 * 
	 * @param the point of the pirate's current location and the ship they are listening to
	 * 
	 * @return the point the pirate should be trying to head towards.
	 ******************************************************************************************/
	@Override
	public Point target(Point currentLocation, Ship ship) {
		// Chaser Pirate will target the player's position
		return ship.currentLocation;
	}

}
