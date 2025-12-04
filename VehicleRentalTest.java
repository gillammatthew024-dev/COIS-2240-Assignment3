import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor; 

import java.lang.reflect.Modifier; 

class VehicleRentalTest {

	@Test
	public void testLicensePlate() {
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
	@Test
	public void testRentandReturnVehicle() 
	{
		Car car = new Car("Ford", "Focus", 2021, 4);
		Customer customer = new Customer(1, "Matt Gillam");
		Vehicle.VehicleStatus status = car.getStatus();
		assertTrue(Vehicle.VehicleStatus.valueOf("Available").equals(status));
		RentalSystem rentalSystem = RentalSystem.getInstance(); 
		LocalDate date = LocalDate.now(); 
		boolean rented = rentalSystem.rentVehicle(car, customer, date, 69420);
		status = car.getStatus();
		assertTrue(rented);
		assertTrue(Vehicle.VehicleStatus.valueOf("Rented").equals(status));
		date = LocalDate.now(); //slightly later date for slightly better realism lol, the vehicle was held for like 2 ms
		rentalSystem.returnVehicle(car, customer, date, 0); 
		status = car.getStatus();
		assertTrue(rented);
		assertTrue(Vehicle.VehicleStatus.valueOf("Available").equals(status));
		assertFalse(rentalSystem.returnVehicle(car, customer, date, 0)); 
	}
	@Test
	public void testSingletonRentalSystem() throws Exception
	{
		Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor(); 
		
		assertTrue(constructor.getModifiers() == Modifier.PRIVATE);
		assertTrue(RentalSystem.getInstance() != null);
		
	}

}
