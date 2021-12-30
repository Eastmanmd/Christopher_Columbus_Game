import java.awt.Point;
import java.util.Random;

public class OceanMap {
	
	boolean islands[][];
	static int xdimension = 20;
	static int ydimension = 16;
	static int islandCount = 15;
	Random rand = new Random();
	Point shipLocation;
	Point pirateLocation;   //stores the location of the pirate island
	Point treasureLocation; //stores the location of the treasure on the ocean grid 
	static private OceanMap instance;
	
	/**
	 * Constructor: constructs the OceanMap class
	 * 
	 *  @param xdimension: stores the x dimension of the ocean grid
	 *  @param ydimension: stores the y dimension of the ocean grid
	 *  @param islandCount the total number of islands to be placed on the ocean grid 
	 *
	 ********************************************************************************/
	public OceanMap (int xdimension, int ydimension, int islandCount) {
		if (xdimension <= 0 || ydimension <= 0 || islandCount < 0) {
			throw new IllegalArgumentException("Values too low."); // throws exception if value input are too small.
		}
		if (islandCount >= 2 * xdimension * ydimension) {
			throw new IllegalArgumentException("Too Many Islands"); // throws exception if over half of game squares would be islands
		}
		OceanMap.xdimension = xdimension;
		OceanMap.ydimension = ydimension;
		OceanMap.islandCount = islandCount;
		createGrid();
		placeIslands();
		shipLocation = placeShip();
		pirateLocation = placePirateIsland();
		treasureLocation = placeTreasure();
		instance = this;
	}
	
	/**
	 * implementation of singleton design, ensures only one instance of OceanMap class
	 * is instantiated 
	 * 
	 * returns the unique instance of OceanMap
	 ********************************************************************************/
	public static OceanMap getInstance() {
		if (instance == null) {
			new OceanMap(xdimension, ydimension, islandCount); //default dimensions and island count
		}
		
		return instance;
	}
	
	
	/**
	 * creates the ocean grid and initializes all the grid as ocean water  
	 *
	 ********************************************************************************/
	private void createGrid() {
		islands = new boolean [xdimension][ydimension];
		for (int x =0; x < xdimension; x++) {
			for (int y = 0; y < ydimension; y++) {
				islands[x][y] = false;
			}
		}
	}
	
	/**
	 * random creates the position of islands and marks the position of the islands
	 * on the ocean grid  
	 *
	 ********************************************************************************/
	private void placeIslands() {
		int islandsPlaced = islandCount;
		while (islandsPlaced > 0) {
			int x = rand.nextInt(xdimension);       //randomly generate a position x corresponding to the x-coordinate of the island
			int y = rand.nextInt(ydimension);       //randomly generate a position y corresponding to the y-coordinate of the island
			if (islands[x][y] == false) {
				islands[x][y] = true;   //marks the position of island 
				islandsPlaced--;
			}		
		}
	}
	
	
	
	/**
	 * randomly places the ship on the ocean grid ensuring the ship is not placed on 
	 * an island   
	 *
	 * @return the point where the player will be starting the game at.
	 ********************************************************************************/
	private Point placeShip() {
		boolean shipPlaced = false;
		int x = rand.nextInt(xdimension);
		int y = rand.nextInt(ydimension);
		
		while(shipPlaced == false) {
			x = rand.nextInt(xdimension);
			y = rand.nextInt(ydimension);
			if (islands[x][y] == false) {      //check if the position has an island
				shipPlaced = true;	
			}
		}
		return new Point(x,y);
	}
	
	
	/**
	 * randomly places the Pirate Island on the ocean grid ensuring the islands is not placed on 
	 * another island   
	 *
	 * @return Point object that belongs to the location of Pirate Island
	 ******************************************************************************************/
	private Point placePirateIsland() {
		boolean islandPlaced = false;
		int x = rand.nextInt(xdimension);
		int y = rand.nextInt(ydimension);
		
		while(islandPlaced == false) {
			x = rand.nextInt(xdimension);
			y = rand.nextInt(ydimension);
			if (islands[x][y] == false) {      //check if the position has an island
				islands[x][y] = true;
				islandPlaced = true;	
			}
		}
		return new Point(x,y);
	}
	
	
	/**
	 * randomly places the treasure on the ocean grid ensuring the treasure is not placed on 
	 * another islands, pirate ships or pirates.    
	 *
	 * @return The point where the treasure island is located (AKA the goal)
	 ******************************************************************************************/
	private Point placeTreasure() {
		boolean treasurePlaced = false;
		int x = rand.nextInt(xdimension);
		int y = rand.nextInt(ydimension);
		
		while(treasurePlaced == false) {
			x = rand.nextInt(xdimension);
			y = rand.nextInt(ydimension);
			if (islands[x][y] == false) {      //check if the position has an island
				treasurePlaced = true;	
			}
		}
		return new Point(x,y);
	}
	
	
	public Point getTreasure()
	{
		return treasureLocation;
	}
	
	
	
	/**
	 * returns the location of the pirate island on the ocean grid 
	 *
	 *@return the location of the pirate ship 
	 ********************************************************************************/
	public Point getPirateLocation() {
		return pirateLocation;
	}
	
	
	/**
	 * gets the ocean grid with the position of open ocean and islands    
	 *
	 *@return the ocean grid with the position of open ocean and islands 
	 ********************************************************************************/
	public boolean[][] getMap(){
		return islands;
	}
	
	
	/**
	 * returns the location of ship on the ocean grid 
	 *
	 *@return the location of the ship 
	 ********************************************************************************/
	public Point getShipLocation() {
		return shipLocation;
	}
	
	
	/**
	 * returns the x dimension of the ocean grid     
	 *
	 *@return the x dimension of the ocean grid
	 ********************************************************************************/
	public int getXDimensions() {
		return xdimension;
	}
	
	
	/**
	 * returns the y dimension of the ocean grid     
	 *
	 *@return the y dimension of the ocean grid
	 ********************************************************************************/
	public int getYDimensions() {
		return ydimension;
	}
	
	
	/**
	 * returns true/false if an island is present     
	 *
	 *@return true if there is an island present on that location of the ocean grid
	 *		  false if there is no island present on that location of the ocean grid 
	 ********************************************************************************/
	public boolean isIslandPresent(int x, int y) {
		return islands[x][y];
	}
	
}
