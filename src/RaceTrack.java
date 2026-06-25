public class RaceTrack {
    private int totalDistance;
    private int numRounds;
    private int roundDistance;

    public RaceTrack(int totalDistance, int numRounds) {
        if (totalDistance < 100) {
            this.totalDistance = 100;
        } else {
            this.totalDistance = totalDistance;
        }
        if (numRounds < 1) {
            this.numRounds = 1;
        } else {
            this.numRounds = numRounds;
        }
        calculateRoundDistance();
    }
    private void calculateRoundDistance() {
        this.roundDistance = totalDistance / numRounds;
        if (roundDistance < 50) {
            roundDistance = 50;
        }
    }
    public int getTotalDistance() { return totalDistance; }
    public int getNumRounds() { return numRounds; }
    public int getRoundDistance() { return roundDistance; }
    public void setTotalDistance(int distance) {
        if (distance < 100) {
            this.totalDistance = 100;
        } else {
            this.totalDistance = distance;
        }
        calculateRoundDistance();
    }
    public void setNumRounds(int rounds) {
        if (rounds < 1) {
            this.numRounds = 1;
        } else {
            this.numRounds = rounds;
        }
        calculateRoundDistance();
    }
    @Override
    public String toString() {
        return "Track: " + totalDistance + "m, " + numRounds + " rounds, " + roundDistance + "m per round";
    }
}