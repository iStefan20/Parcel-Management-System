package parcelSystem;

public class Parcel {
    private String parcelID;
    private int length, width, height, weight;
    private int depoDays;
    private boolean isCollected;

    public Parcel(String parcelID, int length, int width, int height, int weight, int depoDays) {
        this.parcelID = parcelID;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.depoDays = depoDays;
        this.isCollected = false; // Initially, the parcel is not collected
    }

    // Getters
    public String getParcelID() {
        return parcelID;
    }

    public int getVol() {
        return length * width * height;
    }

    public int getDepoDays() {
        return depoDays;
    }

    public boolean isCollected() {
        return isCollected;
    }

    // Setters
    public void setCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    // Calculate collection fee
    public double calculateFee() {
        double baseFee = getVol() * 0.01 + weight * 0.1;

        // Apply a 20% surcharge for parcels in the depot for more than 7 days
        if (depoDays > 7) {
            baseFee = 1.2;
        }

        // Apply a 10% discount for parcels weighing more than 6 kg
        if (weight > 6) {
            baseFee= 0.9;
        }

        return Math.round(baseFee * 100.0) / 100.0; // Round to 2 decimal places
    }

    @Override
    public String toString() {
        return "Parcel ID: " + parcelID +
               ", Dimensions: " + length + "x" + width + "x" + height +
               ", Weight: " + weight + "kg" +
               ", Days in Depot: " + depoDays +
               ", Collected: " + isCollected;
    }
}