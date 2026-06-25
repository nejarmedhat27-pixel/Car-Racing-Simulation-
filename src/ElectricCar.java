import java.util.Random;
public class ElectricCar extends Car {
    private Random rand = new Random();
    public ElectricCar(String name) {
        super(name, "Electric", 58, 105, 20, 15, 100, 0, 120, 0, 3);
    }
    public String getSpecialFeature() {
        return "Electric Engine - Battery regeneration and efficiency";
    }
    @Override
    public void applySpecialFeature() {
        System.out.println(getName() + " charges battery!");
        int newBattery = getBattery() + 10;
        if (newBattery > 100) {
            newBattery = 100;
        }
        setBattery(newBattery);
        System.out.println("Battery charged to " + newBattery);
    }
    @Override
    public int calculateMoveDistance() {
        int distance = super.calculateMoveDistance();
        if (getBattery() > 70) {
            distance += 10;
            System.out.println(getName() + " uses battery boost!");
        }
        return distance;
    }
    @Override
    public void move() {
        super.move();
        if (getBattery() < 20) {
            int newBattery = getBattery() + 3;
            setBattery(newBattery);
            System.out.println(getName() + " gets 3 battery from moving!");
        }
    }
    @Override
    public void refuel() {
        if (getHealth() > 10) {
            int batteryToAdd = 40;
            int newBattery = getBattery() + batteryToAdd;
            if (newBattery > 100) {
                newBattery = 100;
            }
            int newHealth = getHealth() - 4;
            if (newHealth < 0) {
                newHealth = 0;
            }
            setBattery(newBattery);
            setHealth(newHealth);
            System.out.println(getName() + " charged! Battery +40, Health -4");
        } else {
            System.out.println(getName() + " is too damaged to charge!");
        }
    }
}