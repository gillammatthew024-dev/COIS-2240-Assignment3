import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RentalSystemGui extends Application {
    PrintStream old = System.out;
    RentalSystem rental_system = RentalSystem.getInstance(); 
    
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
            GridPane mommyGrid = new GridPane(); 
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            //textfield components
            TextField type = new TextField();
            type.setPromptText("Enter type (Car | Sports Car | Minibus | Pickup Truck)");

            TextField lp = new TextField();
            lp.setPromptText("License Plate");

            TextField model = new TextField();
            model.setPromptText("Model");

            TextField make = new TextField();
            make.setPromptText("Make");

            TextField year = new TextField();
            year.setPromptText("Year");

            TextField seats = new TextField();
            seats.setPromptText("Seats");

            TextField hp = new TextField();
            hp.setPromptText("Horsepower");

            TextField cs = new TextField();
            cs.setPromptText("Cargo Size");

            TextField turbo = new TextField();
            turbo.setPromptText("Turbo? Enter yes/no");

            TextField trailer = new TextField();
            trailer.setPromptText("Trailer? Enter yes/no");

            TextField accessible = new TextField();
            accessible.setPromptText("Accessible? Enter yes/no");
            
            ListView<TextField> listView = new ListView(); 
            listView.getItems().addAll(
             type, lp, model, make, year,
             seats, hp, cs, turbo, trailer, accessible
             );
            
            TextArea output_box = new TextArea("you're cooked");
            ChoiceBox<String> choiceBoxReturn = new ChoiceBox<String>();
            ChoiceBox<String> choiceBoxRent = new ChoiceBox<String>();
            output_box.setPrefWidth(300);
            output_box.setPrefHeight(200);
            Button print = new Button("Add Vehicle");
            print.setOnAction(event -> 
            	{
            		try {
            		switch (type.getText()) {
            	    case "Car":
            	        Car car = new Car(model.getText(), make.getText(), Integer.parseInt(year.getText()), Integer.parseInt(seats.getText()));
            	        car.setLicensePlate(lp.getText());
            	        rental_system.addVehicle(car);
            	        help_load_cbs(choiceBoxRent);
            	        break;

            	    case "Sports Car":
            	    	SportCar sport_car = new SportCar(model.getText(), make.getText(), Integer.parseInt(year.getText()), Integer.parseInt(seats.getText()), Integer.parseInt(hp.getText()), turbo.getText().equals("yes") ? true : false);
            	        sport_car.setLicensePlate(lp.getText());
            	        rental_system.addVehicle(sport_car);
            	        help_load_cbs(choiceBoxRent);
            	        break;

            	    case "Minibus":
            	    	Minibus minibus = new Minibus(model.getText(), make.getText(), Integer.parseInt(year.getText()), accessible.getText().equals("yes") ? true : false);
            	        minibus.setLicensePlate(lp.getText());
            	        rental_system.addVehicle(minibus);
            	        help_load_cbs(choiceBoxRent);
            	        break;

            	    case "Pickup Truck":
            	    	PickupTruck pickup = new PickupTruck(model.getText(), make.getText(), Integer.parseInt(year.getText()), Integer.parseInt(cs.getText()), trailer.getText().equals("yes") ? true : false);
            	        pickup.setLicensePlate(lp.getText());
            	        rental_system.addVehicle(pickup);
            	        help_load_cbs(choiceBoxRent);
            	        
            	        break;

            	    default:
            	    	IllegalArgumentException illegal_argument_exception = new IllegalArgumentException("please enter correct vehicle details"); 
            	        throw illegal_argument_exception;
            		}
            	} catch (Exception e) 
            		{
            			output_box.setText("Error" + e.getMessage());
            			printStreamtoSystem();
            		}
               }	
            );
            
            //addCustomer
            TextField id = new TextField();
            id.setPromptText("Enter Customer ID");

            TextField name = new TextField();
            name.setPromptText("Name");
            
            Button submit = new Button("Submit"); 
            
            submit.setOnAction(event -> 
        	{
        		try {
        			Customer customer = new Customer(Integer.parseInt(id.getText()), name.getText());
        			rental_system.addCustomer(customer);
        	} catch (Exception e) 
        		{
        			output_box.setText("Error" + e.getMessage());
        		}
           }	
           );
            ListView<TextField> listView2 = new ListView<TextField>();
            listView2.getItems().addAll(id, name);
            
            Button rentVehicle = new Button("Rent"); 
            Button returnVehicle = new Button("Return");
           
            //vehicle is a choice box for renting components since u can choose exactly one vehicle to rent.

            fill_helper(choiceBoxReturn, true, false);
          //available vehicles
          //vehicle is a choice box for renting components since u can choose exactly one vehicle to rent.
            fill_helper(choiceBoxRent, true, true);
            //for customers too
            ChoiceBox<String> choiceBoxCustomers = new ChoiceBox<String>();
            fill_helper(choiceBoxCustomers, false, false);
            mommyGrid.add(choiceBoxCustomers, 1, 1); 
            
            rentVehicle.setOnAction(event -> 
        	{
        		String licensep = choiceBoxRent.getValue().split("\\| ")[2].trim();
        		Vehicle vehicle =  rental_system.findVehicleByPlate(licensep); 
        		LocalDate date = LocalDate.now(); 
        		Customer customer = rental_system.findCustomerByName(choiceBoxCustomers.getValue().split(" \\| ")[1].split(": ")[1].trim());
        		rental_system.rentVehicle(vehicle, customer, date, 500);
        		choiceBoxReturn.getItems().clear();
        		fill_helper(choiceBoxReturn, true, false);
        	} 
            );
            returnVehicle.setOnAction(event -> 
        	{
        		String licensep = choiceBoxReturn.getValue().split("\\| ")[2].trim();
        		Vehicle vehicle =  rental_system.findVehicleByPlate(licensep); 
        		LocalDate date = LocalDate.now(); 
        		Customer customer = rental_system.findCustomerByName(choiceBoxCustomers.getValue().split(" \\| ")[1].split(": ")[1].trim());
        		rental_system.returnVehicle(vehicle, customer, date, 500);
        		choiceBoxRent.getItems().clear();
        		fill_helper(choiceBoxRent, true, true);
        	} 
        	
           );
            Button displayHistory = new Button("Display Rental History"); 
            
            displayHistory.setOnAction(event -> {
            	String rental_history = printStreamtoStringRentalHistory(); 
            	output_box.setText(rental_history); 
            	printStreamtoStringCustomers(); 
            });
            GridPane stacked =  new GridPane();
            stacked.add(output_box, 0, 0); 
            stacked.add(displayHistory, 1, 0);

            GridPane selector = new GridPane();
            
            selector.add(choiceBoxRent, 0, 0);
            selector.add(choiceBoxReturn, 1, 0);
            selector.add(rentVehicle, 0, 1);
            selector.add(returnVehicle, 1, 1);
            GridPane daddyGrid = new GridPane(); 
            daddyGrid.add(selector, 0, 0);
            daddyGrid.add(stacked, 0, 1);

            GridPane grid = new GridPane(); 
            grid.setHgap(10);
            grid.setVgap(10);
            grid.add(print, 0, 1);
            grid.add(listView, 0, 0);
            grid.add(listView2, 1, 0);
            grid.add(submit, 1, 1);
            mommyGrid.add(grid, 0, 0);
            mommyGrid.add(daddyGrid, 1, 0);
            
            root.setCenter(mommyGrid);
           
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    //sets print stream to a string temporarily so the output of display vehicles / customers / rental history
    private String printStreamtoStringVehicles(boolean available) 
    {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        PrintStream ps = new PrintStream(baos); 
        System.setOut(ps); 
        if (available)
        {
        	rental_system.displayVehicles(Vehicle.VehicleStatus.valueOf("Available"));
        }
        else {
        	rental_system.displayVehicles(Vehicle.VehicleStatus.valueOf("Rented"));
        }
        String string = baos.toString();
        return string; 
    }
    private String printStreamtoStringCustomers() 
    {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        PrintStream ps = new PrintStream(baos); 
        System.setOut(ps); 
        rental_system.displayAllCustomers(); 
        String string = baos.toString();
        return string; 
    }
    private String printStreamtoStringRentalHistory() 
    {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos); 
        System.setOut(ps); 
        rental_system.displayRentalHistory(); 
        String string = baos.toString();
        return string; 
    }
    private void printStreamtoSystem() 
    {
    	System.setOut(old);
    
    }
    private void fill_helper(ChoiceBox cb, boolean condition, boolean available) 
    { 
    	String vehicles = printStreamtoStringVehicles(available); 
    	String customers = printStreamtoStringCustomers();
        printStreamtoSystem();
        if (condition)
        {
	    	int i = 0; 
	    	for (String vehicle: vehicles.split("\n"))
	        {
	    		if (i > 3)
	    		{
	    			cb.getItems().add(vehicle);
	    		}
	    			
	        	i++; 
	        } 
        }
        else 
        {
        	for (String customer: customers.split("\n"))
	        {
        		cb.getItems().add(customer);
	        } 
        }
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
    private void help_load_cbs(ChoiceBox cb) 
    { 
    	cb.getItems().clear();
		fill_helper(cb, true, true);
    }
}
