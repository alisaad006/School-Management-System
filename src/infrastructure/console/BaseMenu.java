package infrastructure.console;

import java.util.Scanner;

public abstract class BaseMenu {

    protected final Scanner scanner;

    protected BaseMenu(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null.");
        }
        this.scanner = scanner;
    }

    protected String readString(String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("  Input cannot be empty. Please try again.");
        }
    }

    protected int readInt(String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid whole number.");
            }
        }
    }

    protected double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    protected double readScore(String prompt) {
        while (true) {
            double score = readDouble(prompt);
            if (score >= 0 && score <= 100) {
                return score;
            }
            System.out.println("  Score must be between 0 and 100.");
        }
    }

    protected int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("  Value must be greater than 0.");
        }
    }
}
