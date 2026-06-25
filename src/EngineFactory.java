public class EngineFactory {
    public static Engine createEngineForType(String carType) {
        switch (carType.toLowerCase()) {
            case "sport":
                return createSportEngine();
            case "electric":
                return createElectricMotor();
            case "truck":
                return createTruckEngine();
            case "hybrid":
                return createHybridEngine();
            default:
                return createBasicEngine();
        }
    }
    public static Engine createBasicEngine() {
        return new Engine("Gasoline V4", 150, 65, false);
    }
    public static Engine createSportEngine() {
        return new Engine("Sport V8", 300, 55, true);
    }
    public static Engine createElectricMotor() {
        return new Engine("Electric Motor", 200, 90, true);
    }
    public static Engine createTruckEngine() {
        return new Engine("Diesel V6", 250, 60, false);
    }
    public static Engine createHybridEngine() {
        return new Engine("Hybrid", 180, 85, true);
    }
}