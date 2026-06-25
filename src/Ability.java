import java.util.Random;
public class Ability {
    private String name;
    private String key;
    private int power;
    private static Random rand = new Random();
    public Ability(String name, String key, int power) {
        this.name = name;
        this.key = key;
        this.power = power;
    }
    public String getName() {
        return name;
    }
    public String getKey() {
        return key;
    }
    public int getPower() {
        return power;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public void activate(VehicleInterface vehicle) {
        System.out.println(vehicle.getName() + " activates " + name + "!");
        if (!(vehicle instanceof Car)) {
            System.out.println("Cannot use ability on this vehicle type");
            return;
        }
        Car car = (Car) vehicle;
        switch (key) {
            case "SpeedBoost":
                car.setBoostValue(car.getBoostValue() + power);
                System.out.println("Speed increased by " + power + "!");
                break;
            case "Heal":
                car.heal(power);
                System.out.println("Health restored by " + power + "!");
                break;
            case "Shield":
                int healAmount = power / 2;
                car.heal(healAmount);
                car.setBoostValue(car.getBoostValue() + 10);
                System.out.println("Shield activated!");
                System.out.println("Healed by " + healAmount + ", speed increased by 10!");
                break;
            case "BatteryRegen":
                String type = car.getType();
                if (type.equals("Electric") || type.equals("Hybrid")) {
                    car.setBattery(car.getBattery() + power);
                    System.out.println("Battery restored by " + power + "!");
                } else {
                    int fuelAmount = power / 2;
                    car.setFuel(car.getFuel() + fuelAmount);
                    System.out.println("Converted to fuel! Fuel increased by " + fuelAmount + "!");
                }
                break;
            case "FuelSaver":
                car.setFuel(car.getFuel() + power);
                System.out.println("Fuel restored by " + power + "!");
                break;
            case "DamageBoost":
                car.setBoostValue(car.getBoostValue() + power);
                System.out.println("Damage boost! Speed increased by " + power + "!");
                break;
            case "LuckyCharm":
                int luck = rand.nextInt(3);
                if (luck == 0) {
                    car.heal(15);
                    System.out.println("Lucky! Health increased by 15!");
                } else if (luck == 1) {
                    car.setFuel(car.getFuel() + 20);
                    System.out.println("Lucky! Fuel increased by 20!");
                } else {
                    car.setBoostValue(car.getBoostValue() + 25);
                    System.out.println("Lucky! Speed increased by 25!");
                }
                break;
            default:
                System.out.println("Unknown ability!");
                break;
        }
    }
    public static Ability getRandomAbility() {
        Random rand = new Random();
        int choice = rand.nextInt(7) +1 ;
        switch(choice) {
            case 1:
                return new Ability("Speed Boost", "SpeedBoost", 30);
            case 2:
                return new Ability("Heal", "Heal", 25);
            case 3:
                return new Ability("Shield", "Shield", 40);
            case 4:
                return new Ability("Battery Regen", "BatteryRegen", 50);
            case 5:
                return new Ability("Fuel Saver", "FuelSaver", 35);
            case 6:
                return new Ability("Damage Boost", "DamageBoost", 20);
            case 7:
                return new Ability("Lucky Charm", "LuckyCharm", 0);
            default:
                return new Ability("Speed Boost", "SpeedBoost", 30);
        }
    }
    @Override
    public String toString() {
        return name + " - Power: " + power;
    }
}