import java.awt.Point;

public class TreasureChest {
	
	Point treasureLocation;
	OceanMap map; //access to the map grid
	
	/**
	 * Constructor that will set up the TreasureChest class
	 * 
	 * @param map: OceanMap that will be saved by the class
	 *******************************************************/
	public TreasureChest (OceanMap map)
	{
		this.map = map;
		treasureLocation = map.getTreasure();
	}
	
	/**
	 * Method to return the treasure Location
	 * 
	 * @return point where the treasure will be located at.
	 *******************************************************/
	public Point getLocationTreasure()
	{
		return  treasureLocation;
	}
	

}
