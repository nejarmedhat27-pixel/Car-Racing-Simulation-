public class Engine {
    private String engineType;
    private int horsepower;
    private int efficiency;
    private boolean isTurboCharged;
    public Engine(String engineType, int horsepower, int efficiency, boolean isTurboCharged) {
        this.engineType = engineType;
        this.horsepower = horsepower;
        this.efficiency = efficiency;
        this.isTurboCharged = isTurboCharged;
    }
    public int calculatePower(int baseSpeed) {
        int power = baseSpeed + (horsepower / 10);
        if (isTurboCharged) {
            power += 15;
        }
        return power;
    }
    public int calculateFuelConsumption(int distance) {
        int baseConsumption = distance / 25;
        if (efficiency > 80) {
            baseConsumption = (int)(baseConsumption * 0.7);
        } else if (efficiency < 50) {
            baseConsumption = (int)(baseConsumption * 1.3);
        }
        return baseConsumption;
    }
    public String getEngineType() { return
            engineType;
    }
    public int getHorsepower() { return
            horsepower;
    }
    public int getEfficiency() { return
            efficiency;
    }
    public boolean isTurboCharged() { return
            isTurboCharged;
    }
    @Override
    public String toString() {
        return engineType + " Engine (" + horsepower + " HP, Efficiency: " + efficiency + "%)" +
                (isTurboCharged ? " [Turbo]" : "");
    }
}