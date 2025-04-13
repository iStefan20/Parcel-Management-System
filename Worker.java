package parcelSystem;

import java.util.ArrayList;
import java.util.List;

public class Worker {
    private ParcelMap parcelMap;
    private QueueCustomer customerQueue;
    private Log log;
    private Parcel currentParcel;
    private List<Parcel> recentlyProcessedParcels;

    public Worker(ParcelMap parcelMap, QueueCustomer customerQueue) {
        this.parcelMap = parcelMap;
        this.customerQueue = customerQueue;
        this.log = Log.getInstance();
        this.currentParcel = null;
        this.recentlyProcessedParcels = new ArrayList<>();
    }

    public void processNextCustomer() {
        if (customerQueue.isEmpty()) {
            log.logEvent("No customer in the queue.");
            currentParcel = null;
            return;
        }

        Customer customer = customerQueue.removeCustomer();
        Parcel parcel = parcelMap.getParcelID(customer.getParcelID());
        if (parcel == null || parcel.isCollected()) {
            currentParcel = null;
            return;
        }

        double fee = parcel.calculateFee();
        parcel.setCollected(true);
        log.logEvent("Parcel ID: " + parcel.getParcelID() + " collected. Fee: " + fee);

        // Add the processed parcel to the list
        recentlyProcessedParcels.add(parcel);

        currentParcel = parcel;
    }

    public String getCurrentParcel() {
        return currentParcel != null ? currentParcel.getParcelID() : "No parcel currently being processed";
    }

    public List<Parcel> getProcessedParcels() {
        return recentlyProcessedParcels;
    }

    public void clearProcessedParcels() {
        recentlyProcessedParcels.clear();
    }
}