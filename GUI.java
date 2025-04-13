package parcelSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class GUI {
    private JFrame frame;
    private JList<String> parcelList;
    private JList<String> customerQueueList;
    private JTextArea currentParcelDetails;
    private Manager manager;
    private Worker worker;
    int tempQueue = 31; // assuming the file has 30 people.


    public GUI(Manager manager) {
        this.manager = manager;
        this.worker = new Worker(manager.getParcelMap(), manager.getCustomerQueue());
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Parcel Depot System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);

        // Panels
        JPanel parcelsPanel = createParcelsPanel();
        JPanel customerQueuePanel = createCustomerQueuePanel();
        JPanel currentParcelPanel = createCurrentParcelPanel();

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton processNextButton = new JButton("Process Next Customer");
        JButton refreshButton = new JButton("Refresh");
        JButton reportButton = new JButton("Generate Report");
        JButton removeCustomer = new JButton("Remove Customer");
        JButton addCustomer = new JButton("Add Customer");
        
        removeCustomer.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		removeCustomer();
        	}
        });
        
        addCustomer.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		addCustomer();
        	}
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        processNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processNextCustomer();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        buttonPanel.add(processNextButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(addCustomer);
        buttonPanel.add(removeCustomer);

        // Add Panels to Frame
        frame.add(parcelsPanel, BorderLayout.WEST);
        frame.add(customerQueuePanel, BorderLayout.CENTER);
        frame.add(currentParcelPanel, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        refreshData();
    }

    private JPanel createParcelsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Parcels to be Processed"));

        parcelList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(parcelList);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCustomerQueuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));

        customerQueueList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(customerQueueList);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCurrentParcelPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Current Parcel Being Processed"));

        currentParcelDetails = new JTextArea();
        currentParcelDetails.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(currentParcelDetails);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void refreshData() {
        // Refresh Parcel List
        DefaultListModel<String> parcelListModel = new DefaultListModel<>();
        for (Parcel parcel : manager.getParcelMap().getAllParcels()) {
            parcelListModel.addElement(parcel.toString());
        }
        parcelList.setModel(parcelListModel);

        // Refresh Customer Queue
        DefaultListModel<String> customerListModel = new DefaultListModel<>();
        for (Customer customer : manager.getCustomerQueue().getAllCustomers()) {
            customerListModel.addElement(customer.toString());
        }
        customerQueueList.setModel(customerListModel);

        // Update Current Parcel Details
        currentParcelDetails.setText("Current Parcel ID: " + worker.getCurrentParcel());
    }

    private void processNextCustomer() {
        worker.processNextCustomer();
        refreshData();
    }

    private void generateReport() {
        List<Parcel> processedParcels = worker.getProcessedParcels();

        StringBuilder report = new StringBuilder();
        report.append("Processed Parcels\n\n");
        if (processedParcels.isEmpty()) {
            report.append("No parcels have been processed since the last report.\n");
        } else {
            for (Parcel parcel : processedParcels) {
                // Include collection fee in the report
                report.append(parcel.toString()).append(", Collection Fee: £").append(parcel.calculateFee()).append("\n");
            }
        }

        // Create Report Panel
        JPanel reportPanel = new JPanel(new BorderLayout());
        JTextArea reportArea = new JTextArea(report.toString());
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        JButton printButton = new JButton("Print to File");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printToFile(report.toString());
            }
        });

        reportPanel.add(scrollPane, BorderLayout.CENTER);
        reportPanel.add(printButton, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(frame, reportPanel, "Report", JOptionPane.PLAIN_MESSAGE);
        worker.clearProcessedParcels();
    }

    private void printToFile(String reportContent) {
        try (FileWriter writer = new FileWriter("log.txt")) {
            writer.write(reportContent);
            JOptionPane.showMessageDialog(frame, "Report successfully saved to log.txt", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error writing log to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void removeCustomer() {
        // Get the selected customer information from the customer queue list
        String selectedCustomerInfo = customerQueueList.getSelectedValue();

        // Check if a customer is selected
        if (selectedCustomerInfo != null) {
            try {
                // Extract customer information from the selected text
                // Assuming format: "Sequence Number: <seqNo>, Name: <name>, Parcel ID: <parcelID>"
                String[] parts = selectedCustomerInfo.split(",");
                String namePart = parts[1].split(":")[1].trim(); // Extract the Name
                String parcelIdPart = parts[2].split(":")[1].trim(); // Extract the Parcel ID
                System.out.println(selectedCustomerInfo);
                System.out.println("Customer Name: " + namePart);
                System.out.println("Parcel ID: " + parcelIdPart);

                // Retrieve the queue from the manager
                QueueCustomer queueCustomer = manager.getCustomerQueue();

                // Find the customer by name and Parcel ID in the queue
                Customer customerToRemove = null;
                for (Customer customer : queueCustomer.getAllCustomers()) {
                    if (customer.getName().equals(namePart) && customer.getParcelID().equals(parcelIdPart)) {
                        customerToRemove = customer;
                        break;
                    }
                }

                if (customerToRemove != null) {
                    // Remove the customer by sequence number from the queue
                    boolean removed = queueCustomer.removeCustomerBySeqNo(customerToRemove);

                    // If removal is successful, also remove the corresponding parcel
                    if (removed) {
                        Parcel parcel = manager.getParcelMap().getParcelID(customerToRemove.getParcelID());
                        if (parcel != null) {
                            manager.getParcelMap().removeParcel(parcel.getParcelID());
                            JOptionPane.showMessageDialog(frame, "Customer and corresponding parcel removed successfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Customer removed, but parcel not found.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to remove the customer from the queue.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Customer not found in the queue.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Refresh the displayed customer queue
                refreshData();

            } catch (Exception e) {
                // Handle unexpected format issues or other exceptions
                JOptionPane.showMessageDialog(frame, "Error processing selected customer data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            // Handle the case when no customer is selected
            JOptionPane.showMessageDialog(frame, "Please select a customer to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    	
    	
    	
    
    
    public void addCustomer() {
        JTextField customerNameField = new JTextField();
        JTextField parcelIDField = new JTextField();
        JTextField lengthField = new JTextField();
        JTextField widthField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField depoDaysField = new JTextField();
        
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Customer Name:"));
        inputPanel.add(customerNameField);
        inputPanel.add(new JLabel("Parcel ID(Format X***):"));
        inputPanel.add(parcelIDField);
        inputPanel.add(new JLabel("Length:"));
        inputPanel.add(lengthField);
        inputPanel.add(new JLabel("Width:"));
        inputPanel.add(widthField);
        inputPanel.add(new JLabel("Height:"));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("Weight:"));
        inputPanel.add(weightField);
        inputPanel.add(new JLabel("Depot Days:"));
        inputPanel.add(depoDaysField);

        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Customer and Parcel", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = customerNameField.getText();
                String parcelID = parcelIDField.getText();
                int length = Integer.parseInt(lengthField.getText());
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                int weight = Integer.parseInt(weightField.getText());
                int depoDays = Integer.parseInt(depoDaysField.getText());
                
                
                

                Parcel newParcel = new Parcel(parcelID, length, width, height, weight, depoDays);
                Customer newCustomer = new Customer(tempQueue++, name,parcelID);

                manager.getCustomerQueue().addCustomer(newCustomer);
                manager.getParcelMap().addParcel(newParcel);

                JOptionPane.showMessageDialog(frame, "Customer and parcel added successfully!");
                refreshData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    	
    	
    }
    


    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.loadParcelData("Parcels.csv");
        manager.loadCustomerData("Custs.csv");

        SwingUtilities.invokeLater(() -> new GUI(manager));
    }
}