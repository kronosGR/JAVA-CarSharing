package carsharing;

import carsharing.Model.Car;
import carsharing.Model.Company;
import carsharing.Model.Customer;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Menu {
    private static Scanner sc = new Scanner(System.in);
    private static CompanyDao companies = new CompanyDao();
    private static CarDao cars = new CarDao();
    private static CustomerDao customers = new CustomerDao();
    private static Customer activeCustomer = null;

    public static void showMenu() {
        boolean active = true;
        while (active) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");

            int sel = Integer.parseInt(sc.nextLine());

            switch (sel) {
                case 1 -> showManagerMenu();
                case 2 -> showCustomerMenu();
                case 3 -> createCustomer();
                case 0 -> active = false;
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private static void showManagerMenu() {
        boolean active = true;
        while (active) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            int sel = Integer.parseInt(sc.nextLine());

            switch (sel) {
                case 0 -> active = false;
                case 1 -> companyList();
                case 2 -> createCompany();
                default -> System.out.println("Invalid selection");
            }
        }
    }


    private static void companyList() {
        List<Company> companiesList = companies.getAll();

        if (companiesList.isEmpty()) {
            System.out.println();
            System.out.println("The company list is empty!");
            return;
        }

        boolean active = true;
        while (active) {
            System.out.println();
            System.out.println("Choose the company:");

            for (Company company : companiesList) {
                System.out.println(company.toString());
            }
            System.out.println("0. Back");
            int opt = Integer.parseInt(sc.nextLine());
            int p = opt > companiesList.size() ? -1 : opt == 0 ? 0 : 1;
            switch (p) {
                case 0 -> active = false;
                case 1 -> {
                    carList(companiesList.get(opt - 1));
                    active = false;
                }
            }
        }
    }

    private static void carList(Company company) {
        boolean active = true;
        System.out.println("'" + company.getName() + "' company");
        while (active) {
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            int opt = Integer.parseInt(sc.nextLine());

            switch (opt) {
                case 0 -> active = false;
                case 1 -> listCars(company);
                case 2 -> createCar(company);
            }
        }
    }

    private static void createCompany() {
        System.out.println("Enter the company name:");
        String name = sc.nextLine();
        companies.create(new Company(0, name));
        System.out.println();
        System.out.println("The company was created!");
    }

    private static void listCars(Company company) {
        List<Car> list = cars.getCarsByCompany(company);
        if (list.isEmpty()) {
            System.out.println();
            System.out.println("The car list is empty!");
            return;
        }
        System.out.println();
        System.out.println("Car list:");
        IntStream.range(0, list.size()).forEach(idx -> {
            System.out.println(idx + 1 + ". " + list.get(idx).getName());
        });
        System.out.println();
    }

    private static void createCar(Company company) {
        System.out.println("Enter the car name:");
        String name = sc.nextLine();
        cars.create(new Car(0, name, company.getId()));
        System.out.println();
        System.out.println("The car was added!");
    }

    private static void showCustomerMenu() {
        List<Customer> customerList = customers.getAll();
        if (customerList.isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
            return;
        }

        boolean active = true;
        while (active) {
            System.out.println();
            System.out.println("Customer list:");
            customerList.forEach(c -> System.out.println(c.toString()));
            System.out.println("0. Back");

            int opt = Integer.parseInt(sc.nextLine());
            int par = opt > customerList.size() ? -1 : opt == 0 ? 0 : 1;
            switch (par) {
                case 0 -> active = false;
                case 1 -> {
                    activeCustomer = customerList.get(opt - 1);
                    showCustomerCarMenu();
                    active = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void createCustomer() {
        System.out.println("Enter the customer name:");
        String name = sc.nextLine();
        customers.create(new Customer(0, name));
        System.out.println();
        System.out.println("The customer was added!");
        System.out.println();
    }

    private static void showCustomerCarMenu() {
        boolean active = true;
        while (active) {
            System.out.println();
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");

            int opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 0 -> active = false;
                case 1 -> rentCar();
                case 2 -> returnTheCar();
                case 3 -> rentedCar();

                default -> System.out.println("Invalid option");

            }
        }
    }

    private static void rentedCar() {
        if (activeCustomer.getRentedCarId() == 0) {
        System.out.println("\nYou didn't rent a car!\n");
        return;
    }
        Car car = cars.getById(activeCustomer.getRentedCarId()).get();
        System.out.println();
        System.out.println("Your rented car:");
        System.out.println(car.getName());
        System.out.println("Company:");
        System.out.println(companies.getById(car.getCompanyId()).get().getName());
        System.out.println();
    }

    private static void returnTheCar() {
        if (activeCustomer.getRentedCarId() == 0) {
            System.out.println();
            System.out.println("You didn't rent a car!");
            System.out.println();
            return;
        }

        activeCustomer = new Customer(activeCustomer.getId(), activeCustomer.getName(), 0);
        customers.update(activeCustomer);

        System.out.println();
        System.out.println("You've returned a rented car!");
        System.out.println();
    }

    private static void rentCar() {
        if (activeCustomer.getRentedCarId() != 0) {
            System.out.println();
            System.out.println("You've already rented a car!");
            return;
        }

        // choose a company

        List<Company> companiesList = companies.getAll();
        if (companiesList.isEmpty()) {
            System.out.println();
            System.out.println("The company list is empty!");
            return;
        }

        boolean active = true;
        while (active) {
            System.out.println();
            System.out.println("Choose a company:");

            for (Company company : companiesList) {
                System.out.println(company.toString());
            }
            System.out.println("0. Back");
            int opt = Integer.parseInt(sc.nextLine());
            int p = opt > companiesList.size() ? -1 : opt == 0 ? 0 : 1;
            switch (p) {
                case 0 -> active = false;
                case 1 -> {
                    showCarMenu(companiesList.get(opt - 1));
                    active = false;
                }
            }
        }
    }

    private static void showCarMenu(Company company) {
        List<Car> carList = cars.getAvailableCars(company);

        if (carList.isEmpty()) {
            System.out.println("No available cars in the " + company.getName() + " company");
            return;
        }

        System.out.println();
        System.out.println("Choose a car:");
        IntStream.range(0, carList.size()).forEach(i -> System.out.println(i + 1 + ". " + carList.get(i).getName()));
        System.out.println("0. Back");

        int option = Integer.parseInt(sc.nextLine());
        if (option == 0) return;
        activeCustomer = new Customer(activeCustomer.getId(), activeCustomer.getName(), carList.get(option - 1).getId());
        customers.update(activeCustomer);
        System.out.printf("%nYou rented '%s'%n%n", carList.get(option - 1).getName());
    }


}
