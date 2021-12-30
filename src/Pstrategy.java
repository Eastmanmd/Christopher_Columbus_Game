
import java.awt.Point;

public interface Pstrategy {
	/**
	 * Abstract classes that will be overridden by given instances of the interface.
	 * 
	 *********************************************************************************/
	public String getName(); // returns the name of the strategy
	public Point target(Point currentLocation, Ship ship); // uses logic to figure out where to move to
}
