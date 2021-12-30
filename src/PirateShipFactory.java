import java.awt.Point;
import java.util.Observable;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class PirateShipFactory {
	
	List<PirateShip> PirateList;
	
	/**
	 * Constructor for the PirateShipFactory
	 * 
	 ***************************************/
	public PirateShipFactory() {
		PirateList = new ArrayList<PirateShip>();
	}

	
	/**
	 * General case constructor for pirate ships, factory builds the pirate ship requested.
	 * 
	 * @param observable: the player the pirate ship will be observing
	 * @param oceanMap: the OceanMap that is being used as a game board
	 * @param type: string that indicates what kind of strategy should be installed in the pirate ship
	 * @param scale: the scale of the images that will be placed on the game board
	 * @return the constructed pirate ship
	 ***************************************************************************************************/
	public PirateShip createPirate(Observable observable, OceanMap oceanMap, String type, int scale) {
		type = type.toLowerCase(); // to help prevent misspellings
		Pstrategy strategy = new ChaseStrategy(); // default is the ChaseStrategy
		Image image = new Image("pirateShip.png", scale, scale, true, true); // default image
		if (type == "chaser") {
			strategy = new ChaseStrategy();
			image = new Image("pirateShip.png", scale, scale, true, true); // blue pirate
		}
		else if (type == "blocker") {
			strategy = new BlockerStrategy();
			image = new Image("pirateShip2.png", scale, scale, true, true); // red pirate
		}
		else if (type == "waiter") {
			strategy = new WaitStrategy();
			image = new Image("pirateShip3.png", scale, scale, true, true); // green pirate
		}
		
		// create and return the pirate ship
		PirateShip pirate = new PirateShip(observable, oceanMap, strategy);
		pirate.setImage(image, scale);
		PirateList.add(pirate); // add to list of pirates, for ease of use when checking if player overlaps with pirates
		return pirate;
	}
	
	
	/**
	 * Method for factory to make a pirate ship, with an override to place the pirate ship at a given location.
	 * 
	 * @param observable: the player the pirate ship will be observing
	 * @param oceanMap: the OceanMap that is being used as a game board
	 * @param type: string that indicates what kind of strategy should be installed in the pirate ship
	 * @param scale: the scale of the images that will be placed on the game board
	 * @param starting: a point to override the staring location of the pirate ship
	 * @return the constructed pirate ship
	 ***************************************************************************************************/
	public PirateShip createPirate(Observable observable, OceanMap oceanMap, String type, int scale, Point starting) {
		PirateShip pirate = this.createPirate(observable, oceanMap, type, scale);
		pirate.setLocation(starting);
		pirate.getImageView().setX((pirate.getPirateLocation().x)*scale);
		pirate.getImageView().setY((pirate.getPirateLocation().y)*scale);
		return pirate;
	}
	
	
	/**
	 * Method to check if any pirate ship constructed by this factory overlaps with the players position
	 * 
	 * @param p: Point, players position to check
	 * @return true/false if there was an overlap
	 ****************************************************************************************************/
	public boolean overlap(Point p){
		// iterate through all the Pirates to see if any of them overlap
		for (int i = 0; i < PirateList.size(); i++) {
			PirateShip s = PirateList.get(i);
			if (s.currentLocation.equals(p)) {return true;}
		}
		return false;
	}
}
