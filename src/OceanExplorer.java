//***************************************************************************
//
//  Ali Eastman Oku
//  Z-1893417
//  Stefan Larsen Friesema
//  Z-1919661
// 	Final Project
//
//***************************************************************************


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.awt.Point;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;
import javafx.scene.control.Label;


public class OceanExplorer extends Application {

	boolean[][] islandMap;  //stores the presence of islands on the ocean grid
	final int xdimension = 20; // x dimension of ocean grid
	final int ydimension = 16; // y dimension of ocean grid
	final int scale = 50;
	final int islandCount = 15;   //stores the total number of islands on the ocean grid
	Pane myPane;
	Image shipImage;
	ImageView shipImageView;
	Image pirateShipImage;        //first pirate ship
	ImageView pirateShipImageView;
	Image pirateShipImage2;        //second pirate ship
	ImageView pirateShipImageView2;
	Image pirateShipImage3;       //third pirate ship
	ImageView pirateShipImageView3;
	Image pirateIslandImage;
	ImageView pirateIslandImageView;
	Image islandImage;
	ImageView islandImageView;
	Image OceanImage;
	ImageView OceanImageView;
	OceanMap oceanMap;
	Scene scene;
	Ship ship;
	PirateShip pirate1;
	PirateShip pirate2;
	PirateShip pirate3;
	PirateShipFactory factory; // Factory for making pirate ships
	Shark shark1;
	Shark shark2;
	Shark shark3;
	Thread threadShark;
	SharkGroup shiverSharks;
	Thread threadShiverSharks;
	Image treasureImage;
	ImageView treasureImageView;
	TreasureChest treasure;
	boolean success = false;
	Image winnerImage;
	ImageView winnerImageView;
	Label labelGameOver;
	Label labelWin;
	Image treasureBackground;
	ImageView treasureBackgroundView;

	/**
	 * The main method that is called to start the game, will set things up and then plays the game.
	 * Also called to reset the game.
	 * 
	 */
	@Override
	public void start(Stage oceanStage) throws Exception {

		// set up the game board
		oceanMap = new OceanMap (xdimension, ydimension, islandCount);
		islandMap = oceanMap.getMap();
		factory = new PirateShipFactory();
		
		myPane = new AnchorPane ();
		drawMap();
//		drawIslands();
		loadIslandImage();
		
		// add the player's ship
		ship = new Ship(oceanMap);
		loadShipImage();
		
		// SLF - building the PirateShips
		pirate1 = factory.createPirate(ship, oceanMap, "waiter", scale, oceanMap.getPirateLocation()); // set location to pirate island
		pirate2 = factory.createPirate(ship, oceanMap, "blocker", scale);
		pirate3 = factory.createPirate(ship, oceanMap, "chaser", scale);
		loadPirateIslandImage();
		pirateShipImageView = pirate1.getImageView();
		pirateShipImageView2 = pirate2.getImageView();
		pirateShipImageView3 = pirate3.getImageView();
		myPane.getChildren().addAll(pirateShipImageView, pirateShipImageView2, pirateShipImageView3);
//		loadPirateShipImage();
//		loadPirateShipImage2();
//		loadPirateShipImage3();
		
		// set the treasure location
		treasure = new TreasureChest(oceanMap);
		loadTreasureImage();

		// set up the sharks
		shark1 = new Shark(oceanMap, scale);
		shark2 = new Shark(oceanMap, scale);
		shark3 = new Shark(oceanMap, scale);
		
		shark1.pushToStage(myPane.getChildren());
		threadShark = new Thread(shark1);
		threadShark.start();
		
		//add two sharks to the shark group
		shiverSharks = new SharkGroup();
		shiverSharks.addMonster(shark2);
		shiverSharks.addMonster(shark3);
		shiverSharks.pushToStage(myPane.getChildren());
		threadShiverSharks = new Thread(shiverSharks);
		threadShiverSharks.start();
		
		labelGameOver = new Label("Game Over");
		labelGameOver.setLayoutX(xdimension * scale * 0.25); // set the x position of the gameOver scene
		labelGameOver.setLayoutY(ydimension * scale * 0.4); // set the y position of the gameOver scene
		labelGameOver.setFont(new Font("Arial", 100)); // set the font/size of the gameOver scene
		myPane.getChildren().add(labelGameOver);		
    	
		labelWin = new Label("You Win");
		labelWin.setFont(new Font("Arial", 100));
		labelWin.setVisible(false);
		labelWin.setLayoutX(xdimension * scale * 0.3); // set the x position of the win screen
		labelWin.setLayoutY(ydimension * scale * 0.1); // set the y position of the win screen

		// build and set the reset button
		Button reset = new Button("Reset");
		reset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
		        try {
		        	success = false;
		        	setHaltAll(true); // to shut down the different threads running in the background
					start(oceanStage);
				} catch (Exception e1) {
			
					e1.printStackTrace();
				}
		    }
		});
		reset.setLayoutX(10); // set x position of button
		reset.setLayoutY((scale * ydimension) + 10); // set y position of button
		myPane.getChildren().add(reset);		
		
		// set up the window the game plays in
		scene = new Scene(myPane, xdimension * scale, (ydimension * scale) + 40); // set size of window (game board + space for reset button)
		oceanStage.setScene(scene);
		oceanStage.setTitle("Columbus Game");
		oceanStage.show();
		startSailing();
	}
	
	
	
	/**
	 * Draws the ocean grid using the input dimension and adds the ocean to the root pane 
	 *
	 ********************************************************************************/
	public void drawMap() {
		for (int x =0; x < xdimension; x++) {
			for (int y = 0; y < ydimension; y++) {
				if (islandMap[x][y] == false) {
					OceanImage = new Image("ocean.jpeg", scale, scale, true, true);
					OceanImageView = new ImageView(OceanImage);
					OceanImageView.setX(x * scale);
					OceanImageView.setY(y * scale);
					myPane.getChildren().add(OceanImageView);
				}
			}
		}
	}
	
	
	// SLF - old code that is irrelevant, we now place island images instead of rectangles
//	/**
//	 * adds islands to the ocean grid using the boolean information present on the island
//	 *  map  
//	 *
//	 ********************************************************************************/
//	public void drawIslands() {
//		for (int x =0; x < xdimension; x++) {
//			for (int y = 0; y < ydimension; y++) {
//				if (islandMap[x][y] == true) {
//					Rectangle rect = new Rectangle (x*scale, y*scale, scale, scale);
//					rect.setStroke(Color.BLACK);
//					rect.setFill(Color.GREEN);
//					myPane.getChildren().add(rect);
//				}
//			}
//		}
//	}
//	
//	
	
	/**
	 * Loads the image of the ship and adds the ship image to the grid using based on 
	 * the location of the ship 
	 *
	 ********************************************************************************/
	private void loadShipImage() {
		shipImage = new Image("ship.png", scale, scale, true, true);
		shipImageView = new ImageView(shipImage);
		shipImageView.setX(oceanMap.getShipLocation().x * scale);
		shipImageView.setY(oceanMap.getShipLocation().y * scale);
		myPane.getChildren().add(shipImageView);
	}
	
	
	
	// SLF - don't need this code, I worked it into the pirate factory
//	/**
//	 * Loads the pirate ship image and adds the ship image to the grid using based on 
//	 * the location of the pirates 
//	 *
//	 ********************************************************************************/
//	private void loadPirateShipImage() {
//		pirateShipImage = new Image("pirateShip.png", scale, scale, true, true);
//		pirateShipImageView = new ImageView(pirateShipImage);
//		pirateShipImageView.setX(pirate1.getPirateLocation().x * scale);
//		pirateShipImageView.setY(pirate1.getPirateLocation().y * scale);
//		myPane.getChildren().add(pirateShipImageView);
//	}
//	
//	
//	
//	
//	/**
//	 * Loads the pirate ship image and adds the ship image to the grid using based on 
//	 * the location of the pirates 
//	 *
//	 ********************************************************************************/
//	private void loadPirateShipImage2() {
//		pirateShipImage2 = new Image("pirateShip2.png", scale, scale, true, true);
//		pirateShipImageView2 = new ImageView(pirateShipImage2);
//		
//		pirateShipImageView2.setX(pirate2.getPirateLocation().x * scale);
//		pirateShipImageView2.setY(pirate2.getPirateLocation().y * scale);
//		
//		myPane.getChildren().add(pirateShipImageView2);
//	}
//	
//	
//	
//	/**
//	 * Loads the pirate ship image and adds the ship image to the grid using based on 
//	 * the location of the pirates 
//	 *
//	 ********************************************************************************/
//	private void loadPirateShipImage3() {
//		pirateShipImage3 = new Image("pirateShip3.png", scale, scale, true, true);
//		pirateShipImageView3 = new ImageView(pirateShipImage3);
//		
//		pirateShipImageView3.setX(pirate3.getPirateLocation().x * scale);
//		pirateShipImageView3.setY(pirate3.getPirateLocation().y * scale);
//		
//		myPane.getChildren().add(pirateShipImageView3);
//	}

	
	
	
	/**
	 * Loads the islands image using the information present on the island map 
	 *
	 ********************************************************************************/
	private void loadIslandImage() {
		for (int x =0; x < xdimension; x++) {
			for (int y = 0; y < ydimension; y++) {
				if (islandMap[x][y] == true) {
					islandImage = new Image("island.jpg", scale, scale, true, true);
					islandImageView = new ImageView(islandImage);
					islandImageView.setX(x * scale);
					islandImageView.setY(y * scale);
					myPane.getChildren().add(islandImageView);
				}
			}
		}
	}
	
	
	
	
	/**
	 * Loads the pirate islands image using the information present on the island map 
	 *
	 ********************************************************************************/
	private void loadPirateIslandImage() {
		pirateIslandImage = new Image("pirateisland.PNG", scale, scale, true, true);
		pirateIslandImageView = new ImageView(pirateIslandImage);
		pirateIslandImageView.setX(oceanMap.getPirateLocation().x * scale);
		pirateIslandImageView.setY(oceanMap.getPirateLocation().y * scale);
		myPane.getChildren().add(pirateIslandImageView);
	}
	
	
	/**
	 * Loads the treasure image using the information present on the island map 
	 *
	 ********************************************************************************/
	private void loadTreasureImage() {
		// set an island image at the treasure location to be behind the treasure image
		treasureBackground = new Image("island.jpg", scale, scale, true, true);
		treasureBackgroundView = new ImageView(treasureBackground);
		treasureBackgroundView.setX(oceanMap.getTreasure().x * scale);
		treasureBackgroundView.setY(oceanMap.getTreasure().y * scale);
		myPane.getChildren().add(treasureBackgroundView);
		
		treasureImage = new Image("treasure.png", scale, scale, true, true);
		treasureImageView = new ImageView(treasureImage);
		treasureImageView.setX(oceanMap.getTreasure().x * scale);
		treasureImageView.setY(oceanMap.getTreasure().y * scale);
		treasureImageView.setStyle("-fx-background-color: transparent;"); // to get the image to be transparent
		myPane.getChildren().add(treasureImageView);
		
		winnerImage = new Image("treasure.png", scale, scale, true, true);
		winnerImageView = new ImageView(winnerImage);
		winnerImageView.setX(oceanMap.getTreasure().x * scale);
		winnerImageView.setY(oceanMap.getTreasure().y * scale);
	}
	
	
	/**
	 * The main method for playing the game, call this once the game has been set up to begin playing, this method loops through
	 * each turn listening for player inputs and updating the game. It will also check to see if the player has won or lost the game.
	 * 
	 ******************************************************************************************************************************/
    private void startSailing(){
    	labelGameOver.setVisible(false);
    	labelWin.setVisible(false);
    	winnerImageView.setVisible(false);
    	setHaltAll(false);
    	scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
	    	@Override
	    	public void handle(KeyEvent ke) {
	    		switch(ke.getCode()){
	    			case RIGHT:
	    				ship.moveRight();						
	    				break;
	    			case LEFT:
	    				ship.moveLeft();
	    				break;
	    			case DOWN:
	    				ship.moveDown();
	    				break;
	    			case UP:
	    				ship.moveUp();
	    				break;
	    			default:
	    				break;	
	    		}
	    		shipImageView.setX(ship.getShipLocation().x*scale);
	    		shipImageView.setY(ship.getShipLocation().y*scale);
	    		
	    		pirateShipImageView.setX((pirate1.getPirateLocation().x)*scale);
	    		pirateShipImageView.setY((pirate1.getPirateLocation().y)*scale);
	    		
	    		pirateShipImageView2.setX((pirate2.getPirateLocation().x)*scale);
	    		pirateShipImageView2.setY((pirate2.getPirateLocation().y)*scale);
	    		
	    		pirateShipImageView3.setX((pirate3.getPirateLocation().x)*scale);
	    		pirateShipImageView3.setY((pirate3.getPirateLocation().y)*scale);
	    		
	    		if(ship.getShipLocation().equals(treasure.getLocationTreasure())) {
	    			success = true;
	    			winNotification();	
	    		}
	    		else if (gameOver()) {
		    		labelGameOver.setVisible(true);
		    		setHaltAll(true);
	    		}
			}	
    	});
    } 
    
    
    /**
     * method to see if a pirate or shark has caught the player and forces a game over
     * 
     * @return true if game has lost
     *********************************************************************************/
    private boolean gameOver() {
    	Point playerLoc = ship.getShipLocation();
    	if (factory.overlap(playerLoc)) {return true;}
    	if (shiverSharks.overlap(playerLoc)) {return true;}
    	if (shark1.overlap(playerLoc)) {return true;}
    	return false; // nothing overlapped
    }
    
    
    /**
     * This method is called when the player wins the game, displaying the win screen and halting movement.
     * 
     *******************************************************************************************************/
    private void winNotification() {
    	winnerImage = new Image("treasure.gif", xdimension * scale, ydimension * scale, false, false);
		winnerImageView = new ImageView(winnerImage);
		winnerImageView.setX(0 * scale);
		winnerImageView.setY(0 * scale);
		myPane.getChildren().add(winnerImageView);
    	winnerImageView.setVisible(true);
    	setHaltAll(true); // stops everything from moving
    	labelWin.setVisible(true);
    	myPane.getChildren().add(labelWin);
    }
    
    
    /**
     * method to set halt/sunk for all things that need to be set halt/sunk for, the purpose is to stop the movement
     * of sharks, player and pirates when the game is over. 
     * 
     * @param b boolean value to be set with
     **************************************************************************************************************/
    private void setHaltAll(boolean b) {
    	shiverSharks.setHalt(b);
    	shark1.setHalt(b);
    	ship.setSunk(b);    	
    }
	
    
    
    /**
     * This method will run the two Test classes to see if things are working out. Idealized this method will not be
     * called in the final product.
     * 
     ****************************************************************************************************************/
    public static void test() {
    	System.out.println("OceanMapTest:");
    	Result result = JUnitCore.runClasses(OceanMapTest.class);
    	for (org.junit.runner.notification.Failure failure : result.getFailures())
    		System.out.println(failure.toString());
    	if (result.wasSuccessful()) {System.out.println("All Test Were Sucessful");}
    	System.out.println("Run Time: " + result.getRunTime() + "ms");
    	
    	System.out.println("\nPirateFactoryTest:");
    	result = JUnitCore.runClasses(PirateShipFactoryTest.class);
    	for (org.junit.runner.notification.Failure failure : result.getFailures())
    		System.out.println(failure.toString());
    	if (result.wasSuccessful()) {System.out.println("All Test Were Sucessful");}
    	System.out.println("Run Time: " + result.getRunTime() + "ms");
    	
    }
	
   /**
    * Method that is automatically called when the program is called from console
    * 
    * @param args: optional arguments to be passed in, not used
    ******************************************************************************/
	public static void main(String[] args) {
//		test(); // run test to see if things are working
		launch(args); // play the game
	}

}