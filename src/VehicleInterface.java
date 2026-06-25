public interface VehicleInterface {
    void move();
    void refuel();
    void takeDamage(int damage);
    int calculateMoveDistance();
    String getVehicleInfo();
    String getName();
    String getType();
    int getHealth();
    int getFuel();
    int getBattery();
    int getLives();
    boolean isOut();
    void setHealth(int health);
    void setFuel(int fuel);
    void setBattery(int battery);
    void setOut(boolean out);
    void setLives(int lives);
}