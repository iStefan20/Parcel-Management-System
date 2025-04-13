package parcelSystem;

import java.io.*;
import java.util.*;

public class Manager {
    private ParcelMap parcelMap;
    private QueueCustomer customerQueue;
    private Log log;

    // Constructor
    public Manager() {
        parcelMap = new ParcelMap();
        customerQueue = new QueueCustomer();
        log = Log.getInstance();
    }
    
    public ParcelMap getParcelMap() {
        return parcelMap;
    }
    
    public QueueCustomer getCustomerQueue() {
        return customerQueue;
    }

    // Method to process Parcel data
    public void loadParcelData(String parcelFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(parcelFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split data by comma
                String parcelID = parts[0].trim();
                int length = Integer.parseInt(parts[1].trim());
                int width = Integer.parseInt(parts[2].trim());
                int height = Integer.parseInt(parts[3].trim());
                int weight = Integer.parseInt(parts[4].trim());
                int depoDays = Integer.parseInt(parts[5].trim());

                Parcel parcel = new Parcel(parcelID, length, width, height, weight, depoDays);
                parcelMap.addParcel(parcel);
            }
            log.logEvent("Parcel data loaded successfully.");
        } catch (IOException | NumberFormatException e) {
            log.logEvent("Error loading parcel data: " + e.getMessage());
            System.err.println("Error reading parcel data: " + e.getMessage());
        }
    }

    // Method to process Customer data
    public void loadCustomerData(String customerFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(customerFilePath))) {
            String line;
            int queueNumber = 1; // Start queue numbers from 1
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split data by comma
                String name = parts[0].trim();
                String parcelId = parts[1].trim();

                Customer customer = new Customer(queueNumber, name, parcelId);
                customerQueue.addCustomer(customer);
                queueNumber++; // Increment queue number
            }
            log.logEvent("Customer data loaded successfully.");
        } catch (IOException e) {
            log.logEvent("Error loading customer data: " + e.getMessage());
            System.err.println("Error reading customer data: " + e.getMessage());
        }
    }

    // Method to remove a customer and their associated parcel
    public void removeCustomerAndParcel(int seqNo) {
        // Find customer in the queue by sequence number
        Customer customerToRemove = null;
        for (Customer customer : customerQueue.getAllCustomers()) {
            if (customer.getSeqNo() == seqNo) {
                customerToRemove = customer;
                break;
            }
        }

        if (customerToRemove != null) {
            // Remove the customer from the queue by Parcel ID
            boolean removedFromQueue = customerQueue.removeCustomerBySeqNo(customerToRemove);
            if (removedFromQueue) {
                // Remove the corresponding parcel from the ParcelMap
                Parcel parcel = parcelMap.getParcelID(customerToRemove.getParcelID());
                if (parcel != null) {
                    parcelMap.removeParcel(parcel.getParcelID());
                    log.logEvent("Customer and parcel removed successfully.");
                    System.out.println("Customer and parcel removed successfully.");
                } else {
                    log.logEvent("Customer removed, but parcel not found.");
                    System.out.println("Customer removed, but parcel not found.");
                }
            } else {
                log.logEvent("Error removing customer from queue.");
                System.out.println("Error removing customer from queue.");
            }
        } else {
            log.logEvent("Customer not found.");
            System.out.println("Customer not found.");
        }
    }

    // Main driver method
    public void run() {
        Worker worker = new Worker(parcelMap, customerQueue);

        // Process customers
        while (!customerQueue.isEmpty()) {
            worker.processNextCustomer();
        }

        log.logEvent("All customers processed.");
        System.out.println("All customers processed.");
    }

    public static void main(String[] args) {
        Manager manager = new Manager();

        // Load data from files
        manager.loadParcelData("Parcels.csv");
        manager.loadCustomerData("Custs.csv");

        // Run the simulation
        manager.run();

        // Write logs to file
        manager.log.writeLogToFile("log.txt");
    }
}
