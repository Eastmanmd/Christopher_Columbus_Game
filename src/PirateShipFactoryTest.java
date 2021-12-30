import static org.junit.Assert.*;
import org.junit.*;
import java.awt.Point;

public class PirateShipFactoryTest {
	
	PirateShipFactory factory;
	OceanMap oceanMap;
	Ship ship;
	
	/**
	 * method that is set up at the start of each test.
	 * 
	 ***********************************************************/
	public void setUp() {
		factory = new PirateShipFactory();
		oceanMap = new OceanMap(10,10,10);
		ship = new Ship(oceanMap);
	}
	
	
	/**
	 * This test is to make sure that when trying to make a Waiting Pirate Ship, that the correct
	 * Pirate Ship is made by the factory.
	 * 
	 *********************************************************************************************/
	@Test
	public void waitTest() {
		setUp();
		PirateShip p1 = factory.createPirate(ship, oceanMap, "waiter", 50);
		assertTrue(p1.strategy.getName() == "Waiter");
	}
	
	
	/**
	 * This is a test to see if the blocker pirate type is made correctly by the pirate factory
	 *
	 *********************************************************************************************/
	@Test
	public void blockTest() {
		setUp();
		PirateShip p1 = factory.createPirate(ship, oceanMap, "blocker", 50);
		assertTrue(p1.strategy.getName() == "Blocker");
	}
	
	
	/**
	 * This is a test case for when an unknown pirate type is given to the factory. It should
	 * default to the chaser pirate type when something unknown is given.
	 * 
	 ******************************************************************************************/
	@Test
	public void noneTest() {
		setUp();
		PirateShip p1 = factory.createPirate(ship, oceanMap, "unknow", 50);
		assertTrue(p1.strategy.getName() == "Chaser");
	}
	
	
	/**
	 * Test to see if the PirateShipFactory is correctly overriding the ship starting location.
	 *  
	 *********************************************************************************************/
	@Test
	public void startLocationTest() {
		setUp();
		Point loc = new Point(0,0);
		PirateShip p1 = factory.createPirate(ship, oceanMap, "waiter", 50, loc);
		assertTrue(p1.getPirateLocation().equals(loc));
	}
	
	
	/**
	 * Test that all the pirates created by the factory are properly saved in PirateList
	 * 
	 *******************************************************************************************/
	@Test
	public void listTest() {
		setUp();
		factory.createPirate(ship, oceanMap, "waiter", 50);
		factory.createPirate(ship, oceanMap, "blocker", 50);
		factory.createPirate(ship, oceanMap, "chaser", 50);
		assertTrue(factory.PirateList.size() == 3);
	}
}
