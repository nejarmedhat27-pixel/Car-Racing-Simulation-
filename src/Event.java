public class Event {
    private String key;
    private String description;
    private int healthImpact;
    private int distanceImpact;
    private double probability;
    public Event(String key, String description, int healthImpact, int distanceImpact, double probability) {
        this.key = key;
        this.description = description;
        this.healthImpact = healthImpact;
        this.distanceImpact = distanceImpact;
        this.probability = probability;
    }
    public String getKey() { return key; }
    public String getDescription() { return description; }
    public int getHealthImpact() { return healthImpact; }
    public int getDistanceImpact() { return distanceImpact; }
    public double getProbability() { return probability; }
    public static Event RockEvent() {
        return new Event("Rock", "Hit a rock", 10, -8, 0.15);
    }
    public static Event OilEvent() {
        return new Event("Oil", "Slipped on oil", 5, -12, 0.12);
    }
    public static Event SlipEvent() {
        return new Event("Slip", "Lost control and slipped", 8, -10, 0.10);
    }
    public static Event BatteryDrain() {
        return new Event("BatteryDrain", "Battery drained suddenly", 6, 0, 0.07);
    }
    public static Event FuelLoss() {
        return new Event("FuelLoss", "Fuel leak", 5, 0, 0.06);
    }
    public static Event MegaBoost() {
        return new Event("MegaBoost", "Found Nitro! Big boost", 0, 25, 0.05);
    }
}