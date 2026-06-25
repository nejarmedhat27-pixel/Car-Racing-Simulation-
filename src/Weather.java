public class Weather {
    private String type;
    private double speedModifier;
    private double eventProbabilityModifier;
    private int healthRisk;
    public Weather(String type, double speedModifier, double eventProbabilityModifier, int healthRisk) {
        this.type = type;
        this.speedModifier = speedModifier;
        this.eventProbabilityModifier = eventProbabilityModifier;
        this.healthRisk = healthRisk;
    }
    public String getType() { return type; }
    public double getSpeedModifier() { return speedModifier; }
    public double getEventProbabilityModifier() { return eventProbabilityModifier; }
    public int getHealthRisk() { return healthRisk; }
    public static Weather fromName(String name) {
        switch (name) {
            case "Rain": return new Weather("Rain", -0.08, 0.06, 1);
            case "Fog": return new Weather("Fog", -0.05, 0.08, 0);
            case "Storm": return new Weather("Storm", -0.12, 0.16, 2);
            case "Sunny":
            default: return new Weather("Sunny", 0.0, 0.00, 0);
        }
    }
    public String getDescription() {
        switch (type) {
            case "Sunny": return "Clear skies, perfect racing conditions";
            case "Rain": return "Wet track, reduced visibility";
            case "Fog": return "Poor visibility, tricky conditions";
            case "Storm": return "Heavy rain and wind, dangerous!";
            default: return "Normal racing conditions";
        }
    }
    @Override
    public String toString() {
        return type + " (" + getDescription() + ")";
    }
}