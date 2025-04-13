package parcelSystem;

import java.util.*;

public class ParcelMap {
    private Map<String, Parcel> parcelMap;

    // Constructor
    public ParcelMap() {
        this.parcelMap = new HashMap<>();
    }

    // Add a parcel to the map
    public void addParcel(Parcel parcel) {
        parcelMap.put(parcel.getParcelID(), parcel);
    }

    // Retrieve a parcel by its ID
    public Parcel getParcelID(String parcelID) {
        return parcelMap.get(parcelID);
    }

    // Check if a parcel exists in the map by its ID
    public boolean containsParcel(String parcelID) {
        return parcelMap.containsKey(parcelID);
    }

    // Get all parcels (useful for iteration or GUI display)
    public Collection<Parcel> getAllParcels() {
        return parcelMap.values();
    }

    // Get the size of the map (number of parcels)
    public int size() {
        return parcelMap.size();
    }

    // Remove a parcel from the map by its ID
    public void removeParcel(String parcelID) {
        parcelMap.remove(parcelID);
    }

    // Debugging method to print all parcels
    public void printAllParcels() {
        for (Parcel parcel : parcelMap.values()) {
            System.out.println(parcel);
        }
    }
}