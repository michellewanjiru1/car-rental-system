import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Car {
    private String licensePlate;
    private String model;
    private boolean isRented;

    public Car(String licensePlate, String model) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.isRented = false;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public boolean isRented() {
        return isRented;
    }

    public void rent() {
        if (!isRented) {
            isRented = true;
        } else {
            throw new IllegalStateException("Car is already rented");
        }
    }

    public void returnCar() {
        if (isRented) {
            isRented = false;
        } else {
            throw new IllegalStateException("Car is not rented");
        }
    }

    @Override
    public String toString() {
        return "Car [licensePlate=" + licensePlate + ", model=" + model + ", isRented=" + isRented + "]";
    }
}

class Customer {
    private String id;
    private String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + "]";
    }
}

class RentalAgency {
    private List<Car> cars;
    private List<Customer> customers;

    public RentalAgency() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
        System.out.println("Added car: " + car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("Added customer: " + customer);
    }

    public Car findAvailableCar(String model) {
        for (Car car : cars) {
            if (car.getModel().equals(model) && !car.isRented()) {
                return car;
            }
        }
        return null;
    }

    public void rentCar(String customerId, String model) {
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        Car car = findAvailableCar(model);
        if (car == null) {
            throw new IllegalArgumentException("Car not available");
        }

        car.rent();
        System.out.println("Rented car: " + car + " to customer: " + customer);
    }

    public void returnCar(String licensePlate) {
        Car car = findCarByLicensePlate(licensePlate);
        if (car == null) {
            throw new IllegalArgumentException("Car not found");
        }

        car.returnCar();
        System.out.println("Returned car: " + car);
    }

    private Customer findCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    private Car findCarByLicensePlate(String licensePlate) {
        for (Car car : cars) {
            if (car.getLicensePlate().equals(licensePlate)) {
                return car;
            }
        }
        return null;
    }
}

public class simplCarRentalSystem {
    public static void main(String[] args) {
        RentalAgency rentalAgency = new RentalAgency();
        Scanner scanner = new Scanner(System.in);

        // Add some cars
        rentalAgency.addCar(new Car("ABC123", "Toyota Camry"));
        rentalAgency.addCar(new Car("XYZ789", "Honda Accord"));

        // Add some customers
        rentalAgency.addCustomer(new Customer("CUST01", "John Doe"));
        rentalAgency.addCustomer(new Customer("CUST02", "Jane Smith"));

        // Interact with the system
        System.out.println("Welcome to the Car Rental System");
        while (true) {
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("Enter Car Model: ");
                    String model = scanner.nextLine();
                    try {
                        rentalAgency.rentCar(customerId, model);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter Car License Plate: ");
                    String licensePlate = scanner.nextLine();
                    try {
                        rentalAgency.returnCar(licensePlate);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
