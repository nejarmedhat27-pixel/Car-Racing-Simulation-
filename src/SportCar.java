public class SportCar extends Car {
    public SportCar(String name) {
        super(name, "Sport", 75, 130, 25, 18, 90, 90, 0, 12, 2);
    }
    @Override
    public String getSpecialFeature() {
        return "Sport Mode - Extra speed on straight paths";
    }
    @Override
    public void applySpecialFeature() {
        System.out.println( getName() + " activates Sport Mode!");
        setBoostValue(getBoostValue() + 20);
        System.out.println("   → Speed boost +20 for this turn!");
    }
    @Override
    public int calculateMoveDistance() {
        int distance = super.calculateMoveDistance();
        if (distance > 80) {
            distance += 15;
            System.out.println(getName() + " uses sport mode!");
        }
        return distance;
    }
    @Override
    public void refuel() {
        if (getHealth() > 15) {
            int fuelToAdd = 40;
            if (getFuel() + fuelToAdd > 100) {
                setFuel(100);
            } else {
                setFuel(getFuel() + fuelToAdd);
            }
            if (getHealth() - 8 < 0) {
                setHealth(0);
            } else {
                setHealth(getHealth() - 8);
            }
            System.out.println("Sport car quick refuel! +40 fuel, -8 health");
        } else {
            System.out.println(getName() + " is too damaged to refuel!");
        }
    }
    @Override
    public void takeDamage(int damage) {
        int amplifiedDamage = damage + (damage / 5);
        super.takeDamage(amplifiedDamage);
    }
}