import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class LeadTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Lead name: ");
        String name = scanner.nextLine();

        System.out.print("Lead email: ");
        String email = scanner.nextLine();

        System.out.print("Budget: ");
        double budget;

        try {
            budget = Double.parseDouble(scanner.nextLine());
            if (budget < 0) {
                System.out.println("Budget cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for the budget.");
            return;
        }

        Path file = Paths.get("leads.csv");

        try {
            boolean needsHeader = Files.notExists(file) || Files.size(file) == 0;

            String row = escapeCsv(name) + ","
                    + escapeCsv(email) + ","
                    + budget + System.lineSeparator();

            if (needsHeader) {
                row = "Name,Email,Budget" + System.lineSeparator() + row;
            }

            Files.write(
                    file,
                    row.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            System.out.println("Lead saved to leads.csv");

            if (budget >= 5000) {
                System.out.println("Sales alert: Follow up with " + name);
            }
        } catch (IOException e) {
            System.out.println("Could not save the lead: " + e.getMessage());
        }

        scanner.close();
    }

    private static String escapeCsv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
