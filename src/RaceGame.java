import java.util.*;
import java.util.stream.Collectors;
public class RaceGame {
    private Scanner S = new Scanner(System.in);
    private List<VehicleInterface> vehicles = new ArrayList<>();
    private RaceTrack track;
    private Weather weatherObj;
    private String difficulty;
    private Random rand = new Random();
    private Map<VehicleInterface, Integer> roundWins = new HashMap<>();
    public RaceGame() {
    }
    private boolean validateYesNoInput(String input) {
        if (input == null || input.trim().isEmpty()) return false;
        String s = input.trim().toLowerCase();
        return s.equals("y") || s.equals("yes") || s.equals("n") || s.equals("no");
    }
    private String getValidYesNoInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = S.nextLine().trim();
            if (validateYesNoInput(input)) {
                return input.toLowerCase().charAt(0) + "";
            }
            System.out.println(" Invalid input! Please enter 'y' for yes or 'n' for no.");
        }
    }
    private boolean isValidCarChoice(String choice, List<String> options) {
        if (choice == null) return false;
        choice = choice.trim();
        if (choice.matches("\\d+")) {
            int number = Integer.parseInt(choice); // user enters 1,2,3...
            int index = number - 1;                // list index starts from 0
            return index >= 0 && index < options.size();
        }
        int i = 0;
        while (i < options.size()) {
            if (options.get(i).equalsIgnoreCase(choice)) {
                return true;
            }
            i++;
        }
        return false;
    }
    private String getValidChoice(String prompt, List<String> validChoices) {
        while (true) {
            System.out.print(prompt);
            String input = S.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Empty input! Please enter a valid choice.");
                continue;
            }
            if (validChoices.stream().anyMatch(choice -> choice.equalsIgnoreCase(input))) {
                return input;
            }
            System.out.println(" Invalid choice! Valid options are: " + validChoices);
            System.out.println("   Example: " + String.join(", ", validChoices));
        }
    }
    public void startRace() {
        setupGame();
        startCountdown();
        roundWins.clear();
        boolean gameEnded = false;
        for (int round = 1; round <= track.getNumRounds() && !gameEnded; round++) {
            boolean roundResult = playRound(round);
            if (roundResult) {
                gameEnded = true;
                break;
            }
            if (round < track.getNumRounds() && !gameEnded) {
                System.out.println("\n Preparing for next round...");
                System.out.println("Press Enter to continue...");
                S.nextLine();
            }
        }
        if (!gameEnded) {
            declareWinner();
        }
        System.out.println("\n RACE COMPLETED! ");
    }
    private void setupGame() {
        System.out.println("  CAR RACING SIMULATION ");
        setupPlayers();
        difficulty = voteOption("Select Difficulty", Arrays.asList("Easy", "Medium", "Hard"));
        String weatherName = voteOption("Select Weather", Arrays.asList("Sunny", "Rain", "Fog", "Storm", "Random"));
        if (weatherName.equals("Random")) {
            List<String> weathers = Arrays.asList("Sunny", "Rain", "Fog", "Storm");
            weatherName = weathers.get(rand.nextInt(weathers.size()));
            System.out.println(" Random weather selected: " + weatherName);
        }
        weatherObj = Weather.fromName(weatherName);
        int numRounds = Integer.parseInt(voteOption("Choose number of rounds (1-8)",
                Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8")));
        int totalDistance = Integer.parseInt(voteOption("Choose total distance in meters",
                Arrays.asList("500", "750", "1000", "1250", "1500")));
        track = new RaceTrack(totalDistance, numRounds);
        assignRandomAbilities();
        System.out.println("\n SETUP COMPLETE\n ");
        System.out.println("\nGame Configuration:");
        System.out.println(" Difficulty: " + difficulty);
        System.out.println(" Weather: " + weatherObj);
        System.out.println(" Track: " + track);
        System.out.println("\n Drivers Ready:");
        for (VehicleInterface vehicle : vehicles) {
            String ability = (vehicle instanceof Car && ((Car) vehicle).getAbility() != null)
                    ? ((Car) vehicle).getAbility().getName() : "None";
            String vehicleType = vehicle.getType();
            System.out.printf(" %-15s - %-10s | Type: %-8s | Ability: %s\n",
                    vehicle.getName(), vehicleType, vehicleType, ability);
        }
        System.out.println("\nPress Enter to start the race...");
        S.nextLine();
    }
    private void setupPlayers() {
        int numberOfPlayers = 0;
        while (numberOfPlayers < 2 || numberOfPlayers > 6) {
            System.out.print("\nEnter number of players (2-6): ");
            String input = S.nextLine().trim();
            if (input.length() == 0) {
                System.out.println(" Input cannot be empty!");
                continue;
            }
            boolean valid = true;
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) < '0' || input.charAt(i) > '9') {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Invalid input! Please enter a number between 2 and 6.");
                continue;
            }
            numberOfPlayers = Integer.parseInt(input);
            if (numberOfPlayers < 2 || numberOfPlayers > 6) {
                System.out.println(" Please enter a number between 2 and 6.");
            }
        }
        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.println("\n── Player " + i + " Setup ──");
            System.out.print("Enter driver name: ");
            String name = S.nextLine().trim();
            if (name.isEmpty()) name = "Driver" + i;
            String typeChoice = getCarTypeForPlayer(name,
                    Arrays.asList("Gasoline", "Sport", "Electric", "Hybrid", "Truck", "Random"));
            VehicleInterface vehicle;
            if (typeChoice.equals("Random")) {
                List<String> types = Arrays.asList("Gasoline", "Sport", "Electric", "Truck");
                typeChoice = types.get(rand.nextInt(types.size()));
                System.out.println("Randomly selected: " + typeChoice);
            }
            switch (typeChoice) {
                case "Sport":
                    vehicle = new SportCar(name);
                    break;
                case "Electric":
                    vehicle = new ElectricCar(name);
                    break;
                case "Truck":
                    vehicle = new TruckCar(name);
                    break;
                case "Hybrid":
                    vehicle = new Car(name, "Hybrid", 60, 105, 16, 14, 100, 60, 80, 5, 3);
                    break;
                case "Basic":
                default:
                    vehicle = new Car(name, "Gasoline", 60, 110, 15, 10, 100, 100, 0, 8, 3);
                    break;
            }
            vehicles.add(vehicle);
            System.out.println( name + " created with " + typeChoice + " car");
            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                System.out.println("   " + car.getEngineInfo());
            }
        }
    }
    private String getCarTypeForPlayer(String playerName, List<String> options) {
        System.out.println( playerName + ", choose your car type");
        System.out.println("Options: " + options);
        System.out.println("\nEnter the number (1-" + options.size() + ") or name:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + options.get(i));
        }
        while (true) {
            System.out.print("Your choice: ");
            String choice = S.nextLine().trim();
            if (choice.isEmpty()) {
                System.out.println(" Empty input! Please enter a choice.");
                continue;
            }
            if (!isValidCarChoice(choice, options)) {
                System.out.println(" Invalid choice! Please enter:");
                System.out.println("   - A number between 1 and " + options.size());
                System.out.println("   - Or one of these names: " + options);
                continue;
            }
            String selected;
            int idx = -1;
            selected = null;
            boolean isNumber = true;
            for (int i = 0; i < choice.length(); i++) {
                if (choice.charAt(i) < '0' || choice.charAt(i) > '9') {
                    isNumber = false;
                    break;
                }
            }
            if (isNumber) {
                idx = Integer.parseInt(choice) - 1;
                if (idx >= 0 && idx < options.size()) {
                    selected = options.get(idx);
                }
            }
            if (selected == null) {
                for (String opt : options) {
                    if (opt.equalsIgnoreCase(choice)) {
                        selected = opt;
                        break;
                    }
                }
            }
            System.out.println("Selected: " + selected);
            return selected;
        }
    }
    private void assignRandomAbilities() {
        for (VehicleInterface vehicle : vehicles) {
            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                if (car.getAbility() == null) {
                    car.assignRandomAbility();
                }
            }
        }
    }
    private String voteOption(String prompt, List<String> options) {
        while (true) {
            System.out.println(prompt);
            System.out.println("Options: " + options);
            Map<String, Integer> votes = collectVotes(options);
            List<String> topChoices = getTopChoices(votes);
            if (topChoices.size() == 1) {
                System.out.println("Selected: " + topChoices.get(0));
                return topChoices.get(0);
            }
            System.out.println(" Tie between: " + topChoices + ". Revoting...");
            votes = collectVotes(topChoices);
            topChoices = getTopChoices(votes);
            if (topChoices.size() == 1) {
                System.out.println(" Selected after revote: " + topChoices.get(0));
                return topChoices.get(0);
            }
            String pick = topChoices.get(rand.nextInt(topChoices.size()));
            System.out.println(" Still tied. Randomly selected: " + pick);
            return pick;
        }
    }
    private Map<String, Integer> collectVotes(List<String> options) {
        Map<String, Integer> votes = new HashMap<>();
        for (String opt : options) votes.put(opt, 0);
        System.out.println("\nEnter the number (1-" + options.size() + ") or name:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + options.get(i));
        }
        for (VehicleInterface vehicle : vehicles) {
            int attempts = 0;
            final int MAX_ATTEMPTS = 2;
            while (attempts < MAX_ATTEMPTS) {
                System.out.print("\n" + vehicle.getName() + "'s choice: ");
                String choice = S.nextLine().trim();
                if (choice.isEmpty()) {
                    attempts++;
                    if (attempts < MAX_ATTEMPTS) {
                        System.out.println(" Empty input! Please enter a choice.");
                        System.out.println("Attempt " + attempts + "/" + MAX_ATTEMPTS);
                    } else {
                        String randomChoice = options.get(rand.nextInt(options.size()));
                        votes.put(randomChoice, votes.get(randomChoice) + 1);
                        System.out.println(" Time's up! Randomly assigned: " + randomChoice);
                        break;
                    }
                    continue;
                }
                if (!isValidCarChoice(choice, options)) {
                    attempts++;
                    if (attempts < MAX_ATTEMPTS) {
                        System.out.println(" Invalid choice! Please enter:");
                        System.out.println("   - A number between 1 and " + options.size());
                        System.out.println("   - Or one of these names: " + options);
                        System.out.println("Attempt " + attempts + "/" + MAX_ATTEMPTS);
                    } else {
                        String randomChoice = options.get(rand.nextInt(options.size()));
                        votes.put(randomChoice, votes.get(randomChoice) + 1);
                        System.out.println(" Time's up! Randomly assigned: " + randomChoice);
                        break;
                    }
                    continue;
                }
                String selected = null;
                if (choice != null && !choice.trim().isEmpty()) {
                    choice = choice.trim();
                    boolean isNumber = true;
                    for (int i = 0; i < choice.length(); i++) {
                        if (choice.charAt(i) < '0' || choice.charAt(i) > '9') {
                            isNumber = false;
                            break;
                        }
                    }
                    if (isNumber) {
                        int idx = Integer.parseInt(choice) - 1;
                        if (idx >= 0 && idx < options.size()) {
                            selected = options.get(idx);
                        }
                    }
                    if (selected == null) {
                        int i = 0;
                        while (i < options.size()) {
                            if (options.get(i).equalsIgnoreCase(choice)) {
                                selected = options.get(i);
                                break;
                            }
                            i++;
                        }
                    }
                }
                if (selected == null) {
                    System.out.println(" Invalid choice!");
                } else {
                    System.out.println(" Selected: " + selected);
                }
                votes.put(selected, votes.get(selected) + 1);
                break;
            }
        }
        System.out.println("\n Vote results: " + votes);
        return votes;
    }
    private List<String> getTopChoices(Map<String, Integer> votes) {
        List<String> topChoices = new ArrayList<>();
        if (votes.isEmpty()) return topChoices;
        int maxVotes = Integer.MIN_VALUE;
        for (Integer v : votes.values()) {
            if (v > maxVotes) {
                maxVotes = v;
            }
        }
        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            if (entry.getValue() == maxVotes) {
                topChoices.add(entry.getKey());
            }
        }
        return topChoices;
    }
    private void startCountdown() {
        System.out.println(" RACE COUNTDOWN\n ");
        System.out.println("\nRace starts in:");
        for (int i = 3; i > 0; i--) {
            System.out.println( i );
        }
        System.out.println("\n  GO! GO! GO! ");
    }
    private boolean playRound(int round) {
        System.out.println(" ROUND " + round + " / " + track.getNumRounds());
        System.out.println("\nTarget: First to reach " + track.getRoundDistance() + " meters");
        System.out.println("\nWeather: " + weatherObj);
        System.out.println("\n Difficulty: " + difficulty);
        for (VehicleInterface vehicle : vehicles) {
            if (!vehicle.isOut() && vehicle instanceof Car) {
                ((Car) vehicle).resetForNewRound();
            }
        }
        VehicleInterface roundWinner = null;
        int turn = 0;
        int maxTurns = 10;
        while (roundWinner == null && turn < maxTurns) {
            turn++;
            System.out.println(" TURN " + turn);
            for (VehicleInterface vehicle : vehicles) {
                if (vehicle.isOut()) continue;
                if (roundWinner != null) break;
                playerTurn(vehicle);
                if (vehicle instanceof Car) {
                    Car car = (Car) vehicle;
                    if (car.getRoundPosition() >= track.getRoundDistance()) {
                        roundWinner = vehicle;
                        break;
                    }
                }
            }
            if (roundWinner == null && turn < maxTurns) {
                System.out.println("\nEnd of Turn " + turn + " Standings:");
                displayRoundStatus();
            }
        }
        if (roundWinner == null) {
            Car closestCar = null;
            int maxPosition = -1;
            for (VehicleInterface v : vehicles) {
                if (!v.isOut() && v instanceof Car) {
                    Car car = (Car) v;
                    if (car.getRoundPosition() > maxPosition) {
                        maxPosition = car.getRoundPosition();
                        closestCar = car;
                    }
                }
            }
            roundWinner = closestCar;
            if (roundWinner != null) {
                System.out.println("\n Time's up! " + roundWinner.getName() + " is closest to finish!");
            }
        }
        if (roundWinner != null) {
            roundWins.put(roundWinner, roundWins.getOrDefault(roundWinner, 0) + 1);
            System.out.println("\n" + "🎉".repeat(25));
            System.out.println(" 🏆 " + roundWinner.getName() + " WINS ROUND " + round + "! 🏆");
            System.out.println("🎉".repeat(25));
        }
        roundSummary(round);
        return checkForChampion();
    }
    private void playerTurn(VehicleInterface vehicle) {
        if (vehicle.isOut()) return;
        System.out.println( vehicle.getName() + "'s Turn\n");
        System.out.println("\nVehicle Type: " + vehicle.getType());
        System.out.println(vehicle.getVehicleInfo());
        if (vehicle instanceof Car) {
            Car car = (Car) vehicle;
            System.out.printf(" Position: %d/%dm\n", car.getRoundPosition(), track.getRoundDistance());
            showProgressBar(car);
            if (car.getRoundPosition() >= track.getRoundDistance() * 0.9) {
                System.out.println( car.getName() + " is approaching the finish line!");
            } else if (car.getRoundPosition() >= track.getRoundDistance() * 0.5) {
                System.out.println( car.getName() + " is at the halfway point!");
            }
            if (car.getHealth() < 20) {
                System.out.println( car.getName() + " is in critical condition!");
            } else if (car.getHealth() < 50) {
                System.out.println( car.getName() + " is taking heavy damage!");
            }
            System.out.print("\n Use special feature? (y/n): ");
            String featureAnswer = getValidYesNoInput("");
            if (featureAnswer.equals("y")) {
                car.applySpecialFeature();
            }
            if (car.getAbility() != null && !car.isAbilityUsed()) {
                String abilityAnswer = getValidYesNoInput("\n Use special ability '" + car.getAbility().getName() + "'? (y/n): ");
                if (abilityAnswer.equals("y")) {
                    car.useAbility();
                }
            }
            if (car.getHealth() > 10) {
                String answer = getValidYesNoInput(" Refuel? (Costs 5 health) (y/n): ");
                if (answer.equals("y")) {
                    System.out.println( car.getName() + " makes a pit stop!");
                    car.refuel();
                    System.out.println("   → +30 fuel | -5 health | Pit stop completed");
                }
            }
            System.out.println("\n🌤 Weather Effects:");
            applyWeatherEffects(car);
            System.out.println("⚙ Difficulty Effects:");
            car.applyMoodDifficulty(difficulty);
            if (rand.nextDouble() < getEventProbability()) {
                System.out.println("\n Random Track Event:");
                String event = getRandomEvent();
                car.applyRandomEvent(event);
            }
            System.out.println("\n Turn Bonus:");
            applyMandatoryBoost(car);
            int baseDistance = car.calculateMoveDistance();
            int finalDistance = (int)(baseDistance * 1.3);
            System.out.println("\n Movement Calculation:");
            System.out.println("   → Base distance: " + baseDistance + "m");
            System.out.println("   → With 30% bonus: " + finalDistance + "m");
            car.move();
            System.out.println("   → New position: " + car.getRoundPosition() + "/" + track.getRoundDistance() + "m");
            car.checkLives();
            if (car.isOut()) {
                System.out.println( car.getName() + " HAS BEEN ELIMINATED!");
                System.out.println("   → No lives remaining | Race over for this car");
            }
        }
    }
    private void showProgressBar(Car car) {
        int totalBars = 20;
        int progress = (car.getRoundPosition() * totalBars) / track.getRoundDistance();
        if (progress > totalBars) progress = totalBars;
        String bar = "[";
        int i = 0;
        while (i < totalBars) {
            if (i < progress) {
                bar += "█";
            } else if (i == progress && progress < totalBars) {
                bar += "🏎️";
            } else {
                bar += "░";
            }
            i++;
        }
        bar += "] " + (progress * 5) + "%";
        System.out.println("\nProgress: " + bar);
    }

    private void applyMandatoryBoost(Car car) {
        int randomBoost = 1 + rand.nextInt(5);
        switch(randomBoost) {
            case 1:
                System.out.println( car.getName() + " gets a speed boost from the track surface!");
                System.out.println("   → +15 speed | Perfect asphalt conditions");
                car.setBoostValue(car.getBoostValue() + 15);
                break;
            case 2:
                System.out.println( car.getName() + " finds a repair kit on the track!");
                System.out.println("   → +8 health | Found medical supplies");
                car.heal(8);
                break;
            case 3:
                if (car.getType().equals("Electric") || car.getType().equals("Hybrid")) {
                    System.out.println( car.getName() + " passes through a charging zone!");
                    System.out.println("   → +15 battery | Induction charging from track");
                    int battery = car.getBattery() + 15;
                    if (battery > 100) battery = 100;
                    car.setBattery(battery);
                } else {
                    System.out.println( car.getName() + " finds a fuel canister!");
                    int maxFuel = car.getType().equals("Sport") ? 90 :
                            car.getType().equals("Truck") ? 120 :
                                    car.getType().equals("Hybrid") ? 60 : 100;
                    System.out.println("   → +15 fuel | Found extra fuel supply (Max: " + maxFuel + ")");
                    int fuel = car.getFuel() + 15;
                    if (fuel > maxFuel) fuel = maxFuel;
                    car.setFuel(fuel);
                }
                break;
            case 4:
                System.out.println( car.getName() + " uses drafting behind another car!");
                System.out.println("   → +10 speed | Aerodynamic advantage");
                car.setBoostValue(car.getBoostValue() + 10);
                break;
            case 5:
                System.out.println( car.getName() + " has normal track conditions.");
                System.out.println("   → No boost this turn | Standard racing conditions");
                break;
        }
    }
    private void applyWeatherEffects(Car car) {
        String weatherType = weatherObj.getType();
        switch (weatherType) {
            case "Rain":
                if (rand.nextDouble() < 0.25) {
                    System.out.println( car.getName() + " struggles in the rain!");
                    System.out.println("   → -10 position | Poor visibility and wet track");
                    int position = car.getRoundPosition() - 10;
                    if (position < 0) position = 0;
                    car.setRoundPosition(position);
                }
                if (rand.nextDouble() < 0.15) {
                    System.out.println( car.getName() + " hydroplanes on standing water!");
                    System.out.println("   → -15 speed | Loss of traction");
                    int boost = car.getBoostValue() - 15;
                    if (boost < 0) boost = 0;
                    car.setBoostValue(boost);
                }
                break;
            case "Fog":
                if (rand.nextDouble() < 0.3) {
                    System.out.println( car.getName() + " gets disoriented in the fog!");
                    System.out.println("   → -20 position | Lost track of racing line");
                    int position = car.getRoundPosition() - 20;
                    if (position < 0) position = 0;
                    car.setRoundPosition(position);
                }
                if (rand.nextDouble() < 0.2) {
                    System.out.println( car.getName() + " slows down due to poor visibility!");
                    System.out.println("   → -12 speed | Reduced speed for safety");
                    int boost = car.getBoostValue() - 12;
                    if (boost < 0) boost = 0;
                    car.setBoostValue(boost);
                }
                break;
            case "Storm":
                if (rand.nextDouble() < 0.35) {
                    System.out.println( car.getName() + " gets struck by lightning!");
                    System.out.println("   → -8 health | Electrical damage from storm");
                    car.takeDamage(8);
                }
                if (rand.nextDouble() < 0.25) {
                    System.out.println( car.getName() + " battles strong winds!");
                    System.out.println("   → -10 fuel | Extra energy used against wind");
                    int fuel = car.getFuel() - 10;
                    if (fuel < 0) fuel = 0;
                    car.setFuel(fuel);
                }
                if (rand.nextDouble() < 0.2) {
                    System.out.println( car.getName() + " hits a large water puddle!");
                    System.out.println("   → -25 position | Major hydroplaning incident");
                    int position = car.getRoundPosition() - 25;
                    if (position < 0) position = 0;
                    car.setRoundPosition(position);
                }
                if (difficulty.equals("Hard") && rand.nextDouble() < 0.1) {
                    System.out.println( car.getName() + " caught in a tornado!");
                    System.out.println("   → -1 LIFE DIRECTLY | Extreme weather hazard");
                    car.setLives(Math.max(0, car.getLives() - 1));
                    if (car.getLives() <= 0) {
                        car.setOut(true);
                        System.out.println("   → ELIMINATED by extreme weather!");
                    }
                }
                break;
            case "Sunny":
                if (rand.nextDouble() < 0.1) {
                    System.out.println( car.getName() + " benefits from perfect conditions!");
                    System.out.println("   → +5 speed | Ideal racing weather");
                    car.setBoostValue(car.getBoostValue() + 5);
                }
                break;
        }
        double speedModifier = weatherObj.getSpeedModifier();
        if (speedModifier < 0) {
            int speedReduction = (int)(car.getBoostValue() * (-speedModifier));
            System.out.println( weatherType + " weather reduces overall speed!");
            System.out.println("   → -" + speedReduction + " speed | Weather penalty");
            int boost = car.getBoostValue() - speedReduction;
            if (boost < 0) boost = 0;
            car.setBoostValue(boost);
        }
    }
    private double getEventProbability() {
        switch (difficulty) {
            case "Easy": return 0.1;
            case "Medium": return 0.2;
            case "Hard": return 0.3;
            default: return 0.15;
        }
    }
    private String getRandomEvent() {
        double r = rand.nextDouble();
        if (r < 0.20) return "Rock";
        if (r < 0.35) return "Oil";
        if (r < 0.45) return "Slip";
        if (r < 0.55) return "WaterPuddle";
        if (r < 0.65) return "SandPatch";
        if (r < 0.75) return "BatteryDrain";
        if (r < 0.82) return "Crash";
        if (r < 0.89) return "EngineFailure";
        if (r < 0.95) return "TireBlowout";
        return "BoostBonus";
    }
    private void displayRoundStatus() {
        System.out.println("\nCurrent Standings:");
        List<Car> cars = new ArrayList<>();
        for (VehicleInterface v : vehicles) {
            if (!v.isOut() && v instanceof Car) {
                cars.add((Car) v);
            }
        }
        cars.sort((a, b) -> b.getRoundPosition() - a.getRoundPosition());
        for (Car c : cars) {
            int progress = (c.getRoundPosition() * 100) / track.getRoundDistance();
            if (progress > 100) progress = 100;
            String bar = "";
            for (int i = 0; i < 20; i++) {
                if (i < progress / 5) bar += "█";
                else bar += "░";
            }
            System.out.println(c.getName() + ": " + progress + "% " + bar + " (" + c.getRoundPosition() + "m)");
        }
    }
    private void roundSummary(int round) {
        System.out.println("\n ROUND " + round + " SUMMARY ");

        List<VehicleInterface> sortedVehicles = new ArrayList<>(vehicles);
        sortedVehicles.sort((a, b) -> {
            if (a instanceof Car && b instanceof Car) {
                return ((Car) b).getRoundPosition() - ((Car) a).getRoundPosition();
            }
            return 0;
        });

        int position = 1;
        for (VehicleInterface vehicle : sortedVehicles) {
            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                if (!car.isOut()) {
                    System.out.println(position + ". " + car.getName() + " [" + car.getType() + "] - " +
                            car.getRoundPosition() + "| ️ " + car.getHealth() +
                            " | " + car.getFuel() + " |  " + car.getLives());
                    position++;
                } else {
                    System.out.println(car.getName() + " - ELIMINATED (Lives: " + car.getLives() + ")");
                }
            }
        }
        System.out.println("\n Round Wins So Far:");
        roundWins.forEach((vehicle, wins) -> {
            System.out.printf(" %-15s: %d win(s)\n", vehicle.getName(), wins);
        });
        System.out.println("\nPress Enter to continue...");
        S.nextLine();
    }
    private boolean checkForChampion() {
        int majority = (track.getNumRounds() / 2) + 1;
        for (VehicleInterface vehicle : vehicles) {
            if (roundWins.getOrDefault(vehicle, 0) >= majority) {
                System.out.println( vehicle.getName() + " has won " + majority + " rounds and is declared CHAMPION! ");
                return true;
            }
        }
        return false;
    }
    private void declareWinner() {
        System.out.println("\n FINAL CHAMPIONSHIP ");
        vehicles.sort((a, b) -> {
            int winsA = roundWins.getOrDefault(a, 0);
            int winsB = roundWins.getOrDefault(b, 0);
            if (winsB != winsA) return winsB - winsA;
            if (a instanceof Car && b instanceof Car) {
                return ((Car) b).getPosition() - ((Car) a).getPosition();
            }
            return 0;
        });
        System.out.println(" \nFINAL RESULTS");

        for (int i = 0; i < vehicles.size(); i++) {
            VehicleInterface vehicle = vehicles.get(i);
            int wins = roundWins.getOrDefault(vehicle, 0);
            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                switch (i) {
                    case 0:
                        System.out.printf("\n CHAMPION: %-15s [%-7s] - %d win(s), %dm total | Lives: %d\n",
                                car.getName(), car.getType(), wins, car.getPosition(), car.getLives());
                        break;
                    case 1:
                        System.out.printf(" 2nd Place: %-15s [%-7s] - %d win(s), %dm total | Lives: %d\n",
                                car.getName(), car.getType(), wins, car.getPosition(), car.getLives());
                        break;
                    case 2:
                        System.out.printf(" 3rd Place: %-15s [%-7s] - %d win(s), %dm total | Lives: %d\n",
                                car.getName(), car.getType(), wins, car.getPosition(), car.getLives());
                        break;
                    default:
                        System.out.printf("%d. %-15s [%-7s] - %d win(s), %dm total | Lives: %d\n",
                                i + 1, car.getName(), car.getType(), wins, car.getPosition(), car.getLives());
                }
            }
        }
        System.out.println(" THANK YOU FOR PLAYING! ");
    }
}