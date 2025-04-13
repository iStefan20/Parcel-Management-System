package parcelSystem;

import java.util.Objects;

public class Customer {
    private int SeqNo;   
    private String name; 
    private String parcelID; 

    // Constructor
    public Customer(int SeqNo, String name, String parcelID) {
        this.SeqNo = SeqNo;
        this.name = name;
        this.parcelID = parcelID;
    }

    // Getters
    public int getSeqNo() {
        return SeqNo;
    }

    public String getName() {
        return name;
    }

    public String getParcelID() {
        return parcelID;
    }

    // Override toString to provide a custom display format
    @Override
    public String toString() {
        return "Sequence Number: " + SeqNo + ", Name: " + name + ", Parcel ID: " + parcelID;
    }

    // Override equals() and hashCode() for comparison based on SeqNo
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return SeqNo == customer.SeqNo; // Comparing by SeqNo
    }

    @Override
    public int hashCode() {
        return Objects.hash(SeqNo); // Hash based on SeqNo for consistency
    }
}