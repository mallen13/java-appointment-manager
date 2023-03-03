# Inventory Management System
## Purpose

The purpose of this application is to provide a user-friendly interface to manage appointments and customers. The application will allow the user to add, modify, delete, and view customers/ appointments, as well as generate reports.

## About
Application features include:

- login page that will display in French if that is the user's locale
- customers page that allows for viewing, editing, adding, and deleting 
- appointments page that displays in the users local time zone
- backend MySQL database that stores data in UTC, then converts to users time in UI

Additional Features:
- various alerts for input and login validations
- upcoming appointment notifications
- ensuring no customers deleted with active appointments 


## Author
- Author: Matthew Allen
- Contact Information: mall671@wgu.edu
- Student Application Version: 1.0
- Date: 3/1/2023

## Dev Software Versions
- IDE: IntelliJ Community Version 2021.1.3
- Full JDK Version: 17.0.1
- JavaFX Version: JavaFX-SDK-17.0.1

## Directions to Run the Program

- Open the project in IntelliJ IDEA
- Make sure that the JDK and JavaFX-SDK are properly setup
- Run the Main class in the src directory
- The application will open in a new window

## Additional Reports Description
- The first report shows the number of appointments by month and type
- The second appointment shows the number of appointments by customer
- The third report shows appointments by contact name

## MYSQL Connector Driver
MySQL Connector Driver Version: mysql-connector-java-8.0.25

## Screenshots

### Login Page:

![loginPage](https://github.com/mallen13/java-appointment-manager/blob/master/appointmentsPage.PNG)


### Customers Page:

![customersPage](https://github.com/mallen13/java-appointment-manager/blob/master/customersPage.PNG)


### Appointments Page:

![appointmentsPage](https://github.com/mallen13/java-appointment-manager/blob/master/appointmentsPage.PNG)


### Reports Page:

![reportsPage](https://github.com/mallen13/java-appointment-manager/blob/master/reportsPage.PNG)

