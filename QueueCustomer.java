package parcelSystem;
import java.util.*;

public class QueueCustomer {
    private Queue<Customer> customerQueue;

    public QueueCustomer() {
        customerQueue = new LinkedList<>();
    }

    // Add customer 
    public void addCustomer(Customer customer) {
        customerQueue.add(customer);
    }

    // Remove the next customer from the queue (FIFO)
    public Customer removeCustomer() {
        return customerQueue.poll();
    }

    // Peek the next customer without removing them
    public Customer peekCustomer() {
        return customerQueue.peek();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return customerQueue.isEmpty();
    }

    // Get the size of the queue
    public int size() {
        return customerQueue.size();
    }

    // Get all customers as a LinkedList
    public Queue<Customer> getAllCustomers() {
        return new LinkedList<>(customerQueue);
    }

    // Iterator for the customer queue
    public Iterator<Customer> iterator() {
        return customerQueue.iterator();
    }

    @Override
    public String toString() {
        return "Customer Queue: " + customerQueue;
    }

    // Remove customer by SeqNo
    public boolean removeCustomerBySeqNo(Customer customerToRemove) {
        Iterator<Customer> iterator = customerQueue.iterator();

        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (customer.getSeqNo() == customerToRemove.getSeqNo()) {
                iterator.remove(); // Remove customer from queue
                return true; // Customer removed successfully
            }
        }

        return false; // Return false if no customer with the given SeqNo was found
    }

    // Get customer by Sequence Number (SeqNo)
    public Customer getCustomerBySeqNo(Customer customerToRemove) {
        for (Customer customer : customerQueue) {
            if (customer.getSeqNo() == customerToRemove.getSeqNo()) {
                return customer; // Return the customer if found
            }
        }
        return null; // Return null if no customer is found with the given SeqNo
    }
}