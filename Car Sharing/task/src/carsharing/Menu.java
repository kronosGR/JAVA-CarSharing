package carsharing;

import java.util.Scanner;

public class Menu {
    private static Scanner sc = new Scanner(System.in);

    public static void showMenu() {
        boolean active = true;
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");

        int sel = sc.nextInt();

        switch (sel) {
            case 1 -> showManagerMenu();
            case 0 -> active = false;
            default -> System.out.println("Invalid selection");
        }

    }

    private static void showManagerMenu() {
        boolean active = true;
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");

        int sel = sc.nextInt();
        switch (sel) {
            case 0 -> active = false;
            default -> System.out.println("Invalid selection");
        }
    }
}
