# 📦 Parcel Management System

This Java-based Parcel Management System simulates operations within a parcel depot. It models a realistic flow where customers queue to collect their parcels, and depot workers process each transaction by calculating collection fees, updating records, and maintaining logs. The system follows modern **software architecture principles** using a **three-tier architecture** and **MVC pattern**, with a responsive **Swing-based GUI**.

## ✅ Features

- Load parcel and customer data from text files
- Manage customer queue and parcel collection
- Automatically calculate parcel collection fees
- Update parcel statuses (processed/unprocessed)
- Log all events using a Singleton-based log system
- Export a summary log to a human-readable text file
- Interactive Swing GUI for processing and tracking
- Uses Lists, Maps, and Sets for optimal data management

## 🧱 Software Architecture

This system adheres to a **three-tier architecture**:

- **Presentation Layer** – GUI (Swing-based), structured using the MVC pattern
- **Business Logic Layer** – Worker class processes customer interactions and fee calculations
- **Data Access Layer** – File reading for customers and parcels; data stored using collections

## 🎯 Design Patterns

- **Singleton Pattern** – Used in the `Log` class to ensure only one instance manages logging
- **MVC Pattern** – Applied in GUI structure to separate concerns:
  - `Model`: Customer, Parcel, QueueOfCustomers, ParcelMap
  - `View`: Swing GUI components (frames, panels)
  - `Controller`: Logic in Worker and Manager classes that connect model and view

## 🧩 Main Classes

- `Parcel` – Represents a parcel’s structure (ID, weight, dimensions, arrival date, etc.)
- `Customer` – Represents a customer collecting parcels
- `QueueOfCustomers` – Manages a queue or list of customers
- `ParcelMap` – Stores parcels using a map (e.g., `HashMap<String, Parcel>`)
- `Log` – Singleton class for recording depot events; exports logs to file
- `Worker` – Handles customer interaction, fee calculation, and parcel release
- `Manager` – Driver class: initializes data, GUI, and controls application flow

## 🖥️ GUI Functionality

The GUI is implemented using **Java Swing** and includes:

- Panel showing list of unprocessed parcels
- Panel displaying the current customer queue
- Area showing details of the parcel currently being processed
- Buttons to simulate parcel collection, update displays, or log actions

> **Note:** All GUI elements are built manually using Swing. No GUI generators or JavaFX are used.

## 💾 How to Run

1. Clone this repository and open it in your preferred Java IDE.
2. Ensure you have your data files:
   - `customers.txt`
   - `parcels.txt`
3. Run the `Manager.java` class.
4. Use the GUI to interact with the parcel depot system.

## 🧪 Testing Strategy

- **Iterative Development**:
  - Initial versions used console I/O to validate data loading and logic
  - Gradual implementation of Swing GUI with commits for each major feature:
    - Class setup
    - Console testing
    - GUI panels
    - Logging system
- **Fee Calculation**:
  - Implemented in a testable `calculateFee()` method in the `Worker` class

## 📂 Folder Structure

