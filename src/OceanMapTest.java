import static org.junit.Assert.*;
import org.junit.*;

public class OceanMapTest {
	
	/**
	 * Test the OceamMap to make sure IllegalArgumentException is flagged when input too many islands.
	 * 
	 *******************************************************************************************************/
	@Test(expected=IllegalArgumentException.class)
	public void testTooMuchLand() {
		new OceanMap(2, 5, 100);
	}
	
	
	/**
	 * Test to see if OceanMap has it's dimensions set up correctly.
	 * 
	 ***********************************************************************/
	@Test
	public void sizeTest1() {
		new OceanMap(10,10,10);
		assertTrue(OceanMap.xdimension == 10 && OceanMap.ydimension == 10 && OceanMap.islandCount == 10);
	}
	
	
	/**
	 * Test to see if OceanMap has it's dimensions set up correctly.
	 * 
	 ***********************************************************************/
	@Test
	public void sizeTest2() {
		new OceanMap(20,15,5);
		assertTrue(OceanMap.xdimension == 20 && OceanMap.ydimension == 15 && OceanMap.islandCount == 5);
	}
	
	
	/**
	 * Test that IllegalArgumentExpception is flagged when giving a negative size.
	 * 
	 **********************************************************************************/
	@Test(expected=IllegalArgumentException.class)
	public void sizeTest3() {
		new OceanMap(-1, 5, 1);
	}
	
	
	/**
	 * Test that IllegalArgumentExpception is flagged when giving a zero size.
	 * 
	 **********************************************************************************/
	@Test(expected=IllegalArgumentException.class)
	public void sizeTest4() {
		new OceanMap(4, 0, 1);
	}
	
	
	/**
	 * Test that IllegalArgumentExpception is flagged when giving a negative island.
	 * 
	 **********************************************************************************/
	@Test(expected=IllegalArgumentException.class)
	public void sizeTest5() {
		new OceanMap(2, 5, -3);
	}
	
	
	/**
	 * Test case to make sure when getting the OceanMap it gives the same thing every time.
	 * 
	 *****************************************************************************************/
	@Test
	public void instanceTest() {
		new OceanMap(10,10,10);
		OceanMap a = OceanMap.getInstance();
		OceanMap b = OceanMap.getInstance();
		assertTrue(a == b);
	}
}