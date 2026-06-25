import java.util.Random;
public abstract class AbstractVehicle implements VehicleInterface {
    private String name;
    private String type;
    private int baseSpeed;
    private int maxSpeed;
    private int acceleration;
    private int handling;
    private int position;
    private int roundPosition;
    private int health;
    private int lives;
    private int fuel;
    private int battery;
    private int fuelConsumptionRate;
    private int boostValue;
    private boolean out;
    private Random rand = new Random();
    private Ability ability;
    private boolean abilityUsed;
    public AbstractVehicle(String name, String type, int baseSpeed, int maxSpeed,
                           int acceleration, int handling, int health, int fuel,
                           int battery, int fuelConsumptionRate, int lives) {
        this.name = name;
        this.type = type;
        this.baseSpeed = baseSpeed;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.handling = handling;
        this.health = health;
        this.lives = lives;
        this.fuel = fuel;
        this.battery = battery;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.position = 0;
        this.roundPosition = 0;
        this.boostValue = 0;
        this.out = false;
        this.ability = null;
        this.abilityUsed = false;
    }
    public abstract String getSpecialFeature();
    public abstract void applySpecialFeature();
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getType() {
        return type;
    }
    @Override
    public int getHealth() {
        return health;
    }
    @Override
    public int getFuel() {
        return fuel;
    }
    @Override
    public int getBattery() {
        return battery;
    }
    @Override
    public int getLives() {
        return lives;
    }
    @Override
    public boolean isOut() {
        return out;
    }
    @Override
    public String getVehicleInfo() {
        return name + " [" + type + "]\n" +
                "Health: " + health + "\n" +
                "Fuel: " + fuel + "\n" +
                "Battery: " + battery + "\n" +
                "Lives: " + lives;
    }
    @Override
    public void setHealth(int health) {
        if (health < 0) {
            this.health = 0;
        } else if (health > 100) {
            this.health = 100;
        } else {
            this.health = health;
        }
    }
    @Override
    public void setFuel(int fuel) {
        if (fuel < 0) {
            this.fuel = 0;
        } else {
            this.fuel = fuel;
        }
    }
    @Override
    public void setBattery(int battery) {
        if (battery < 0) {
            this.battery = 0;
        } else {
            this.battery = battery;
        }
    }
    @Override
    public void setOut(boolean out) {
        this.out = out;
    }
    @Override
    public void setLives(int lives) {
        this.lives = lives;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public int getRoundPosition() {
        return roundPosition;
    }
    public void setRoundPosition(int roundPosition) {
        this.roundPosition = roundPosition;
    }
    public int getBoostValue() {
        return boostValue;
    }
    public void setBoostValue(int boostValue) {
        this.boostValue = boostValue;
    }
    public Ability getAbility() {
        return ability;
    }
    public void setAbility(Ability ability) {
        this.ability = ability;
        this.abilityUsed = false;
    }
    public boolean isAbilityUsed() {
        return abilityUsed;
    }
    public void setAbilityUsed(boolean used) {
        this.abilityUsed = used;
    }
    public void useAbility() {
        if (ability == null) {
            System.out.println( name + " has no ability!");
            return;
        }
        if (abilityUsed) {
            System.out.println( name + " already used ability this round!");
            return;
        }
        ability.activate(this);
        abilityUsed = true;
        System.out.println( name + " used: " + ability.getName());
    }
    public void assignRandomAbility() {
        this.ability = Ability.getRandomAbility();
        this.abilityUsed = false;
    }
    public void heal(int amount) {
        if (amount <= 0) {
            System.out.println("Cannot heal with zero or negative amount!");
            return;
        }
        health = health + amount;
        if (health > 100) {
            health = 100;
        }
    }
    public void checkLives() {
        if (health <= 0) {
            loseLife();
        }
    }
    public void loseLife() {
        lives--;
        if (lives <= 0) {
            out = true;
            System.out.println(name + " is out of the race!");
        } else {
            health = 100;
            position = position - 50;
            if (position < 0) position = 0;
            roundPosition = roundPosition - 50;
            if (roundPosition < 0) roundPosition = 0;
            System.out.println(name + " lost a life! " + lives + " lives left.");
        }
    }
    public void resetRoundPosition() {
        this.roundPosition = 0;
    }
    public void resetForNewRound() {
        this.roundPosition = 0;
        this.boostValue = 0;
        this.abilityUsed = false;
        System.out.println(name + " ready for next round.");
    }
}