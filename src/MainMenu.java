import java.util.Scanner;
public class MainMenu {
    private Scanner S = new Scanner(System.in);
    private final String[] VALID_CHOICES = {"1", "2", "3", "4"};
    public void show() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            String choice = S.nextLine().trim();
            while (!isValidChoice(choice)) {
                System.out.println("Invalid choice. Please enter 1-4.");
                System.out.print("Enter your choice (1-4): ");
                choice = S.nextLine().trim();
            }
            switch (choice) {
                case "1":
                    startNewGame();
                    break;
                case "2":
                    showInstructions();
                    break;
                case "3":
                    showCredits();
                    break;
                case "4":
                    System.out.println("\nThank you for playing! Goodbye!");
                    running = false;
                    break;
            }
        }
    }
    private boolean isValidChoice(String choice) {
        for (int i = 0; i < 4; i++) {
            if (VALID_CHOICES[i].equals(choice)) {
                return true;
            }
        }
        return false;
    }
    private void displayMainMenu() {
        System.out.println(" CAR RACING SIMULATION ");
        System.out.println("------------------------");
        System.out.println("1. Start New Race");
        System.out.println("2. How to Play");
        System.out.println("3. Credits");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }
    private void startNewGame() {
        boolean playAgain = true;
        while (playAgain) {
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t----------------------------------");
            System.out.println(" \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tSTARTING NEW RACE ");
            RaceGame game = new RaceGame();
            game.startRace();
            System.out.print("\nPlay again? (y/n): ");
            String response = S.nextLine().trim().toLowerCase();
            if (!response.equals("y") && !response.equals("yes")) {
                playAgain = false;
                System.out.println("Returning to main menu\n");
            }
        }
    }
    private void showInstructions() {
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tHOW TO PLAY");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t----------------------------------");
        System.out.println("OBJECTIVE");
        System.out.println("------------------------");
        System.out.println("Be the first to win the most rounds!");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t----------------------------------");
        System.out.println("\n GAME FLOW:");
        System.out.println("------------------------");
        System.out.println(" 1. Choose 2-6 players");
        System.out.println(" 2. Select your car type");
        System.out.println(" 3. Vote on game settings");
        System.out.println(" 4. Race in multiple rounds");
        System.out.println(" 5. First to finish each round wins");
        System.out.println(" 6. Player with most wins becomes champion!");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t----------------------------------");
        System.out.println("\n GAME MECHANICS:");
        System.out.println("------------------------");
        System.out.println(" • Each car has Health, Fuel, and Lives");
        System.out.println(" • Refueling costs Health");
        System.out.println(" • Random events may either assist or challenge you");
        System.out.println(" • Deploy your abilities wisely for maximum effect");
        System.out.println(" • Manage resources carefully!");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t----------------------------------");
        System.out.println("\n CAR TYPES:");
        System.out.println("------------------------");
        System.out.println(" • Sport: Fast but fragile");
        System.out.println(" • Electric: Efficient battery use");
        System.out.println(" • Hybrid: Balanced performance");
        System.out.println(" • Gasoline: Reliable all-rounder");
        System.out.println(" • Truck: Durable but slow");
        System.out.println("\nPress Enter to go back");
        S.nextLine();
    }
    private void showCredits() {
        System.out.println(" \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tCREDITS");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t----------------------------------");
        System.out.println("Game Developed by:");
        System.out.println("Nejar, Ahmed and Mark");
        System.out.println("\nPress Enter to go back");
        S.nextLine();
    }
}