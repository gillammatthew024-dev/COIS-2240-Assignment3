import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VehicleRentalTest {

	@Test
	public void testLicensePlate() throws IllegalArgumentException{
		Car car = new Car("Ford", "Focus", 2021, 4);
		String plate1 = "AAA100";
		car.setLicensePlate(plate1);
		assertTrue(car.getLicensePlate().equals(plate1));
		String plate2 = "ABC567";
		car.setLicensePlate(plate2);
		assertTrue(car.getLicensePlate().equals(plate2));
		String plate3 = "ZZZ999";
		car.setLicensePlate(plate3);
		assertTrue(car.getLicensePlate().equals(plate3));
		String plate4 = null; 
		assertThrows(IllegalArgumentException.class,() -> {
			car.setLicensePlate(plate4);
		});
		assertFalse(car.getLicensePlate().equals(plate4));
		String plate5 = "";
		assertThrows(IllegalArgumentException.class,() -> {
			car.setLicensePlate(plate5);
		});
		assertFalse(car.getLicensePlate().equals(plate5));
		String plate6 = "AAA1000";
		assertThrows(IllegalArgumentException.class,() -> {
			car.setLicensePlate(plate6);
		});
		assertFalse(car.getLicensePlate().equals(plate6));
		String plate7 = "ZZZ99"; 
		assertThrows(Exception.class,() -> {
			car.setLicensePlate(plate7);
		});
		assertFalse(car.getLicensePlate().equals(plate7));
		
	}

}
