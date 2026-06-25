import java.util.Random;
public class Car extends AbstractVehicle {
    private String randomAbility;
    private int finishedRounds;
    private Engine engine;
    private int carBaseSpeed;
    private int carAcceleration;
    private int carHandling;
    private int carMaxSpeed;
    private int carFuelConsumptionRate;
    public Car(String name, String type, int baseSpeed, int maxSpeed,
               int acceleration, int handling, int health, int fuel,
               int battery, int fuelConsumptionRate, int lives) {
        super(name, type, baseSpeed, maxSpeed, acceleration, handling,
                health, fuel, battery, fuelConsumptionRate, lives);
        this.carBaseSpeed = baseSpeed;
        this.carAcceleration = acceleration;
        this.carHandling = handling;
        this.carMaxSpeed = maxSpeed;
        this.carFuelConsumptionRate = fuelConsumptionRate;
        this.engine = EngineFactory.createEngineForType(type);
        if (type.equals("Electric")) {
            setFuel(0);
            setBattery(100);
        } else if (type.equals("Hybrid")) {
            setFuel(60);
            setBattery(80);
        } else if (type.equals("Sport")) {
            setFuel(90);
            setBattery(0);
        } else if (type.equals("Truck")) {
            setFuel(120);
            setBattery(0);
        } else {
            setFuel(100);
            setBattery(0);
        }
        this.randomAbility = "";
        this.finishedRounds = 0;
    }
    public Car(String name, String type, int baseSpeed, int maxSpeed,
               int acceleration, int handling, int health, int fuel,
               int battery, int fuelConsumptionRate, int lives, Engine engine) {
        this(name, type, baseSpeed, maxSpeed, acceleration, handling,
                health, fuel, battery, fuelConsumptionRate, lives);
        this.engine = engine;
    }
    @Override
    public String getSpecialFeature() {
        return "Gasoline Car - No special features";
    }
    @Override
    public void applySpecialFeature() {
        System.out.println( getName() + " is a Gasoline car with no special features.");
    }
    @Override
    public void move() {
        int distance = calculateMoveDistance();
        setPosition(getPosition() + distance);
        setRoundPosition(getRoundPosition() + distance);
        if (!getType().equals("Electric")) {
            int fuelUsed = distance / 20;
            setFuel(getFuel() - fuelUsed);
        }
        if (getType().equals("Electric") || getType().equals("Hybrid")) {
            int batteryUsed = distance / 25;
            setBattery(getBattery() - batteryUsed);
        }
        setBoostValue(0);
    }
    @Override
    public void refuel() {
        if (getHealth() > 10) {
            int fuelToAdd = 30;
            if (getType().equals("Electric")) {
                int newBattery = getBattery() + fuelToAdd;
                if (newBattery > 100) newBattery = 100;
                setBattery(newBattery);
                System.out.println(getName() + " charged to " + getBattery() + "/100 battery");
            } else if (getType().equals("Hybrid")) {
                int newFuel = getFuel() + fuelToAdd;
                if (newFuel > 60) newFuel = 60;
                setFuel(newFuel);
                System.out.println(getName() + " refueled to " + getFuel() + "/60 fuel");
            } else if (getType().equals("Sport")) {
                int newFuel = getFuel() + fuelToAdd;
                if (newFuel > 90) newFuel = 90;
                setFuel(newFuel);
                System.out.println(getName() + " refueled to " + getFuel() + "/90 fuel");
            } else if (getType().equals("Truck")) {
                int newFuel = getFuel() + fuelToAdd;
                if (newFuel > 120) newFuel = 120;
                setFuel(newFuel);
                System.out.println(getName() + " refueled to " + getFuel() + "/120 fuel");
            } else {
                int newFuel = getFuel() + fuelToAdd;
                if (newFuel > 100) newFuel = 100;
                setFuel(newFuel);
                System.out.println(getName() + " refueled to " + getFuel() + "/100 fuel");
            }
            setHealth(getHealth() - 5);
        } else {
            System.out.println(getName() + " is too damaged to refuel!");
        }
    }
    @Override
    public void takeDamage(int damage) {
        if (damage < 0) {
            heal(-damage);
        } else {
            int newHealth = getHealth() - damage;
            if (newHealth < 0) newHealth = 0;
            setHealth(newHealth);

            if (getHealth() <= 0) {
                loseLife();
            }
        }
    }
    @Override
    public int calculateMoveDistance() {
        int speed = getBaseSpeed() + getBoostValue();
        if (getFuel() <= 0) {
            speed = speed - 20;
        } else if (getFuel() < 20) {
            speed = speed - 10;
        }
        if (getHealth() < 50) {
            speed = speed - 10;
        }
        if (speed < 0) {
            speed = 0;
        }
        return speed;
    }
    public void applyRandomEvent(String event) {
        switch (event) {
            case "Rock":
                System.out.println(getName() + " hit a rock!");
                takeDamage(10);
                break;
            case "Oil":
                System.out.println(getName() + " hit oil spill!");
                takeDamage(5);
                setPosition(getPosition() - 20);
                setRoundPosition(getRoundPosition() - 20);
                break;
            case "Slip":
                System.out.println(getName() + " slipped!");
                setPosition(getPosition() - 30);
                setRoundPosition(getRoundPosition() - 30);
                break;
            case "BatteryDrain":
                if (getType().equals("Electric") || getType().equals("Hybrid")) {
                    System.out.println(getName() + " battery drained!");
                    setBattery(getBattery() - 20);
                } else {
                    System.out.println(getName() + " fuel leaked!");
                    setFuel(getFuel() - 15);
                }
                break;
            case "BoostBonus":
                System.out.println(getName() + " found boost!");
                setBoostValue(getBoostValue() + 25);
                break;
            case "WaterPuddle":
                System.out.println(getName() + " hit water!");
                setPosition(getPosition() - 15);
                setRoundPosition(getRoundPosition() - 15);
                break;
            case "SandPatch":
                System.out.println(getName() + " in sand!");
                setBoostValue(getBoostValue() - 10);
                break;
            case "Crash":
                System.out.println(getName() + " crashed!");
                setLives(getLives() - 1);
                setHealth(getHealth() - 30);
                if (getLives() <= 0) {
                    setOut(true);
                }
                break;
            case "EngineFailure":
                System.out.println(getName() + " engine failed!");
                setLives(getLives() - 1);
                setFuel(getFuel() - 40);
                if (getLives() <= 0) {
                    setOut(true);
                }
                break;
            case "TireBlowout":
                System.out.println(getName() + " tire blew!");
                setPosition(getPosition() - 20);
                setRoundPosition(getRoundPosition() - 20);
                takeDamage(15);
                break;
        }
        if (getPosition() < 0) setPosition(0);
        if (getRoundPosition() < 0) setRoundPosition(0);
        if (getBattery() < 0) setBattery(0);
        if (getFuel() < 0) setFuel(0);
        if (getBoostValue() < 0) setBoostValue(0);
    }
    public void takeExtremeDamage(int damage, String reason) {
        System.out.println("EXTREME DAMAGE: " + getName() + " - " + reason);
        System.out.println("   → -" + damage + " health | Potential life loss");
        int newHealth = getHealth() - damage;
        if (newHealth < 0) newHealth = 0;
        setHealth(newHealth);
        if (damage >= 50) {
            System.out.println("   → -1 LIFE | Critical damage sustained");
            setLives(Math.max(0, getLives() - 1));
        }
        if (getHealth() <= 0) {
            loseLife();
        } else if (getLives() <= 0) {
            setOut(true);
            System.out.println(getName() + " has been completely destroyed!");
        }
    }
    public void applyMoodDifficulty(String difficulty) {
        if (difficulty.equalsIgnoreCase("medium")) {
            setFuel(getFuel() - 2);
        }
        else if (difficulty.equalsIgnoreCase("hard")) {
            setFuel(getFuel() - 4);
            setHealth(getHealth() - 1);
            Random rand = new Random();
            if (rand.nextDouble() < 0.05) { // 5% chance
                System.out.println("Extreme difficulty hurts " + getName() + "!");
                setLives(getLives() - 1);
                if (getLives() <= 0) {
                    setOut(true);
                    System.out.println(getName() + " is eliminated!");
                } else {
                    System.out.println(getName() + " has " + getLives() + " lives left");
                }
            }
        }
        if (getFuel() < 0) {
            setFuel(0);
        }
        if (getHealth() < 0) {
            setHealth(0);
        }
    }
    public int getBaseSpeed() { return carBaseSpeed; }
    public int getAcceleration() { return carAcceleration; }
    public int getHandling() { return carHandling; }
    public int getMaxSpeed() { return carMaxSpeed; }
    public int getFuelConsumptionRate() { return carFuelConsumptionRate; }
    public String getRandomAbility() { return randomAbility; }
    public void setRandomAbility(String ability) { this.randomAbility = ability; }
    public int getFinishedRounds() { return finishedRounds; }
    public void incrementFinishedRounds() { this.finishedRounds++; }
    public Engine getEngine() { return engine; }
    public void setEngine(Engine engine) { this.engine = engine; }
    public String getEngineInfo() {
        if (engine != null) {
            return "Engine: " + engine.toString();
        }
        return "No engine information";
    }
}