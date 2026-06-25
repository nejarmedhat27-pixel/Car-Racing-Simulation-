public class TruckCar extends Car {
    public TruckCar(String name) {
        super(name, "Truck", 40, 85, 8, 6, 150, 150, 0, 18, 5);
    }
    @Override
    public String getSpecialFeature() {
        return "Heavy Duty - Takes 20% less damage";
    }
    @Override
    public void applySpecialFeature() {
        System.out.println(getName() + " uses heavy-duty protection!");
        heal(15);
        System.out.println("   → +15 health from reinforced frame!");
    }
    @Override
    public void takeDamage(int damage) {
        int reducedDamage = (int)(damage * 0.8);
        super.takeDamage(reducedDamage);
    }
    @Override
    public void applyRandomEvent(String event) {
        if (event.equals("Rock")) {
            System.out.println(getName() + "'s heavy frame reduces rock damage!");
            takeDamage(5);
        } else if (event.equals("Oil")) {
            System.out.println( getName() + "'s wide tires handle oil better!");
            takeDamage(2);
            setPosition(Math.max(0, getPosition() - 10));
            setRoundPosition(Math.max(0, getRoundPosition() - 10));
        } else {
            super.applyRandomEvent(event);
        }
    }
    @Override
    public void move() {
        int baseDistance = calculateMoveDistance();
        int truckDistance = (int)(baseDistance * 0.9);
        setPosition(getPosition() + truckDistance);
        setRoundPosition(getRoundPosition() + truckDistance);
        System.out.println( getName() + " moves steadily forward");
    }
}