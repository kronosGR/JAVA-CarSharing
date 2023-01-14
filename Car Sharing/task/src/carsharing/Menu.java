package carsharing;

import carsharing.Model.Company;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Scanner sc = new Scanner(System.in);
    private static CompanyDao companies = new CompanyDao();

    public static void showMenu() {
        boolean active = true;
        while (active) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");

            int sel = sc.nextInt();

            switch (sel) {
                case 1 -> showManagerMenu();
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

            int sel = sc.nextInt();
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
            System.out.println("Choose a company:");

            for (Company company : companiesList) {
                System.out.println(company.toString());
            }
            System.out.println("0. Back");
            int opt = Integer.parseInt(sc.nextLine());

            switch (opt) {
                case 0 -> active = false;
                case 1 -> carList(companiesList.get(opt - 1));
            }
        }
    }

    private static void carList(Company company) {
        boolean active = false;
        System.out.println("'"+company.getName()+"' company");
        while(active){
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            int opt = Integer.parseInt(sc.nextLine());

            switch (opt){
                case 0-> active = false;
                // todo 1 2
            }
        }
    }

    private static void createCompany() {
        System.out.println("Enter the company name:");
        sc.nextLine();
        String name = sc.nextLine();
        companies.create(new Company(0, name));
        System.out.println();
        System.out.println("The company was created!");
    }
}
