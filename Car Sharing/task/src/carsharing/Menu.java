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
                case 1 -> showCustomerMenu();
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

    private static void showCustomerMenu(){
        List<Customer> customerList = customers.getAll();
        if (customerList.isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
            return;
        }

        boolean active = true;
        while (active){
            System.out.println();
            System.out.println("Customer list:");
            customerList.forEach(c->System.out.println(c.toString()));
            //TODO
        }
    }

    private static void createCustomer(){
        System.out.println("Enter the customer name:");
        String name = sc.nextLine();
        customers.create(new Customer(0, name));
        System.out.println();
        System.out.println("The customer was added!");
        System.out.println();
    }
}
