import java.util.List;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class RentalSystem {
    private List<Vehicle> vehicles;
    private List<Customer> customers;
    private RentalHistory rentalHistory;
    private static RentalSystem RentalSystemInstance; 
    
    private RentalSystem() 
    {
    	vehicles = new ArrayList<>();
    	customers = new ArrayList<>();
    	rentalHistory = new RentalHistory();
    	loadData(); 
    }
    
    public static synchronized RentalSystem getInstance()
    { 
    	if (RentalSystemInstance == null)
    		RentalSystemInstance = new RentalSystem();
    	return RentalSystemInstance;
    	
    }
    private void saveVehicle(Vehicle vehicle)
    {
    	String type;
    	if (vehicle instanceof Car)
    		type = "Car"; 
    	else if (vehicle instanceof SportCar)
    		type = "SportCar"; 
    	else if (vehicle instanceof Minibus) 
    		type = "MiniBus";
    	else if (vehicle instanceof PickupTruck) 
    		type = "PickUpTruck";
    	else 
    		type = "";
    	try (FileWriter writer = new FileWriter("vehicles.txt", true)) 
    	{
    		writer.write(type + " " + vehicle.getInfo() + "\n");
    		writer.close();
    	} catch (IOException e) 
    	{ 
    	}
    	
    }
    private void saveCustomer(Customer customer)
    {
    	try (FileWriter writer = new FileWriter("customers.txt", true)) 
    	{
    		writer.write(customer.toString() + "\n");
    		writer.close();
    	} catch (IOException e) 
    	{ 
    	}
    	
    }
    private void saveRecord(RentalRecord record)
    {
    	try (FileWriter writer = new FileWriter("rentalrecords.txt", true)) 
    	{
    		writer.write(record.toString() + " | " + record.getRecordType() + "\n");
    		writer.close();
    	} catch (IOException e) 
    	{ 
    	}
    	
    }
    private void loadData() 
    {
    	try (BufferedReader vehicleReader = new BufferedReader(new FileReader("vehicles.txt"))) 
    	{
    		String line; 
    		String[] prop; 
    		String[] fields;
    		while ((line = vehicleReader.readLine()) != null) 
    		{
    			String[] properties = line.split(" \\| "); 
    			if (properties[0].equals("Car")) 
    			{
    				fields = new String[6];
    				for (int i = 1; i < properties.length; i++) 
    				{
    					if (i == 6) 
    					{
    						prop = properties[i].split(": ");
    						fields[i-1] = prop[1]; 
    					}
    					else 
    						fields[i-1] = properties[i]; 
    				}
    				Car newCar = new Car(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[5])); 
    				newCar.setLicensePlate(fields[0]); 
    				Vehicle.VehicleStatus status = Vehicle.VehicleStatus.valueOf(fields[4]);
    				newCar.setStatus(status);
    				vehicles.add(newCar);
    				
    			}
    			else if (properties[0].equals("SportCar")) 
    			{
    				fields = new String[8];
    				for (int i = 1; i < properties.length; i++) 
    				{
    					if (i == 6) 
    					{
    						prop = properties[i].split(": ");
    						fields[i-1] = prop[1]; 
    					}
    					else if (i == 7) 
    					{ 
    						prop = properties[i].split(": ");
    						fields[i-1] = prop[1]; 
    					}
    					else if (i == 8) 
    					{ 
    						prop = properties[i].split(": ");
    						fields[i-1] = prop[1]; 
    					}
    					else 
    						fields[i-1] = properties[i]; 
    				}
    				boolean has_turbo = fields[7].equals("YES") ? true : false; 
    				SportCar newCar = new SportCar(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[5]), Integer.parseInt(fields[6]), has_turbo); 
    				newCar.setLicensePlate(fields[0]); 
    				Vehicle.VehicleStatus status = Vehicle.VehicleStatus.valueOf(fields[4]);
    				newCar.setStatus(status);
    				vehicles.add(newCar);
    				
    			}
    			else if (properties[0].equals("PickUpTruck")) 
    			{
    				fields = new String[7];
    				for (int i = 1; i < properties.length; i++) 
    				{
    					if (i == 6) 
    					{
    						prop = properties[i].split(": ");
    						fields[i-1] = prop[1]; 
    					}
    					else if (i == 7) 
    					{ 
    						prop = properties[i].split(": ");
    						fields[i-1] = prop[1]; 
    					}
    					else if (i == 8) 
    					{ 
    						prop = properties[i].split(": ");
    						fields[i-1] = prop[1]; 
    					}
    					else 
    						fields[i-1] = properties[i]; 
    				}
    				
    				boolean has_trailer = fields[6].equals("YES") ? true : false; 
    				PickupTruck newCar = new PickupTruck(fields[1], fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[5]), has_trailer); 
    				newCar.setLicensePlate(fields[0]); 
    				Vehicle.VehicleStatus status = Vehicle.VehicleStatus.valueOf(fields[4]);
    				newCar.setStatus(status);
    				vehicles.add(newCar);
    			}	
    			else if (properties[0].equals("MiniBus")) 
    			{
    				fields = new String[6];
    				for (int i = 1; i < properties.length; i++) 
    				{
    					if (i == 6) 
    					{
    						prop = properties[i].split(": ");
    						fields[i-1] = prop[1]; 
    					}
    					else 
    						fields[i-1] = properties[i]; 
    				}
    				boolean is_accessible = fields[4].equals("YES") ? true : false;
    				Minibus newMinibus = new Minibus(fields[1], fields[2], Integer.parseInt(fields[3]), is_accessible); 
    				newMinibus.setLicensePlate(fields[0]); 
    				Vehicle.VehicleStatus status = Vehicle.VehicleStatus.valueOf(fields[4]);
    				newMinibus.setStatus(status);
    				vehicles.add(newMinibus);
    			}
    		}
    	}catch(IOException e) {}
    	
    try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) 
    {
    	String line;
    	String[] properties;
    	String id; 
    	String name;
    	while ((line = reader.readLine()) != null) 
    	{
    		properties = line.split(" \\| ");
    		name = properties[1].split(": ")[1]; 
    		id = properties[0].split(": ")[1]; 
    		Customer customer = new Customer(Integer.parseInt(id), name);
    		customers.add(customer);
    	}
    }catch(IOException e) {}
    	
    try (BufferedReader reader = new BufferedReader(new FileReader("rentalrecords.txt"))) 
    {
    	String line;
    	String[] properties;
    	String lplate;
    	Vehicle vehicle;
    	Customer customer;
    	LocalDate date; 
    	double total_amount;
    	String rental_type; 
    	while ((line = reader.readLine()) != null) 
    	{
    		properties = line.split(" \\| ");
    		lplate = properties[1].split(": ")[1]; 
    		vehicle = findVehicleByPlate(lplate);
    		customer = findCustomerByName(properties[2].split(": ")[1]); 
    		date = LocalDate.parse(properties[3].split(": ")[1]); 
    		total_amount = Double.parseDouble(properties[4].split(": ")[1].substring(1)); //get the total amount after the 4th : and remove $ prefix
    		rental_type = properties[0]; 
    		if(rental_type.equals("RENT"))
    			vehicle.setStatus(Vehicle.VehicleStatus.valueOf("Rented"));
    		else 
    			vehicle.setStatus(Vehicle.VehicleStatus.valueOf("Available"));
    			
    		RentalRecord rr = new RentalRecord(vehicle, customer, date, total_amount, rental_type);
    		rentalHistory.addRecord(rr);
    	}
    }catch(IOException e) {}
    }
    public boolean addVehicle(Vehicle vehicle) {
    	if (findVehicleByPlate(vehicle.getLicensePlate()) != null) 
    	{
    		System.out.println("vehicle with this license plate already exists in system");
    		return false;
    	}
        vehicles.add(vehicle);
        saveVehicle(vehicle);
        return true;
    }

    public boolean addCustomer(Customer customer) {
    	if (findCustomerByName(customer.getCustomerName()) != null)
    	{
    		System.out.println("Customer with this name already exists");
    		return false;
    	}
        customers.add(customer);
        saveCustomer(customer);
        return true;
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Available) {
            vehicle.setStatus(Vehicle.VehicleStatus.Rented);
            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            saveRecord(record);
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Rented) {
            vehicle.setStatus(Vehicle.VehicleStatus.Available);
            RentalRecord record = new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(record);
            saveRecord(record);
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }   

    public void displayVehicles(Vehicle.VehicleStatus status) {
        // Display appropriate title based on status
        if (status == null) {
            System.out.println("\n=== All Vehicles ===");
        } else {
            System.out.println("\n=== " + status + " Vehicles ===");
        }
        
        // Header with proper column widths
        System.out.printf("|%-16s | %-12s | %-12s | %-12s | %-6s | %-18s |%n", 
            " Type", "Plate", "Make", "Model", "Year", "Status");
        System.out.println("|--------------------------------------------------------------------------------------------|");
    	  
        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (status == null || vehicle.getStatus() == status) {
                found = true;
                String vehicleType;
                if (vehicle instanceof Car) {
                    vehicleType = "Car";
                } else if (vehicle instanceof Minibus) {
                    vehicleType = "Minibus";
                } else if (vehicle instanceof PickupTruck) {
                    vehicleType = "Pickup Truck";
                } else {
                    vehicleType = "Unknown";
                }
                System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-6d | %-18s |%n", 
                    vehicleType, vehicle.getLicensePlate(), vehicle.getMake(), vehicle.getModel(), vehicle.getYear(), vehicle.getStatus().toString());
            }
        }
        if (!found) {
            if (status == null) {
                System.out.println("  No Vehicles found.");
            } else {
                System.out.println("  No vehicles with Status: " + status);
            }
        }
        System.out.println();
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        if (rentalHistory.getRentalHistory().isEmpty()) {
            System.out.println("  No rental history found.");
        } else {
            // Header with proper column widths
            System.out.printf("|%-10s | %-12s | %-20s | %-12s | %-12s |%n", 
                " Type", "Plate", "Customer", "Date", "Amount");
            System.out.println("|-------------------------------------------------------------------------------|");
            
            for (RentalRecord record : rentalHistory.getRentalHistory()) {                
                System.out.printf("| %-9s | %-12s | %-20s | %-12s | $%-11.2f |%n", 
                    record.getRecordType(), 
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerName(),
                    record.getRecordDate().toString(),
                    record.getTotalAmount()
                );
            }
            System.out.println();
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }
    public Customer findCustomerByName(String name) {
        for (Customer c : customers)
            if (c.getCustomerName().equals(name))
                return c;
        return null;
    }
}