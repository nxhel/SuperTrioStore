# 331Project

## Name
The superTrio SuperStore

## Project Description
The goal of this project is to create a program that will let the superStore manager manipulate and 
modify the data in the System. For instance, the superStore administartor could add, update view and delete data in the system.Since this project is from the SuperStore adminstratoor view , the functions and procedure were made accordingly and based on this interpretation.

## Instructions for Database Initialization

**Grasp the Database System Management**
0.Prior to executing any scripts or operations within the application, it is suggested that the review the Entity-Relationship Diagram (ERD) inside the following repository  as understanding it provides a better comprehension and visual overview of the database structure, relationships, enabling better comprehension and navigation of the application's functionalities

**Delete Script Execution:**
1.Run the provided deletion script *sql/remove.sql* to remove existing packages, types, tables and any other existing data within the database that align with our database schema.This step is ensential as it ensures a clean slate for the subsequent setup process.

**Setup Script Execution:**
2.Execute the setup script *sql/setup.sql* to create essential tables, types, triggers and any other necessary structures within the database.The setup script is designed to establish a fresh and complete database environment with all required components.

**Running Database Packages**
3.Running all the database packages is a crucial step before accessing the database via the Java interface, enabling efficient and smooth interaction with the database.

**Accessing the Database via Java Interface:**
4.After successfully executing the scripts, utilize the Java interface provided to interact with the database.
Use the interface to perform various operations, access data, and manage the database functionalities seamlessly.

## Sql Folder

Inside the sql Folder we have scripts such as *sql/remove.sql* and *sql/setup.sql* who are useful for the initialization of the 
database (they delete and add all the tables neeed with respective sample data). Furthemore, we have sql files that have the key word *package* that are files with procedures, functions and types, objects. The Packages files are divided depending on the action done by the user. For that reason we have 
*sql/DisplayPackage.sql*,
*sql/RemovePackage.sql* , 
*sql/validatePackage.sql*,
*sql/UpdatePackage.sql*,
*sql/findInDatabase.sql*,
*sql/addToDatabse.sql*.

## Store Project folder

Inside the store project folder, we have all of the necessary classes inside the java application that will call when needed packagages with precise functions/procedure to handle the task that the user wants. Inside this folder we have

## Store Project Java Classes:
`main/java/sqlproject/Product.java`
`main/java/sqlproject/Adress.java`
`main/java/sqlproject/City.java`
`main/java/sqlproject/Orders.java`
`main/java/sqlproject/Product.java`
`main/java/sqlproject/Province.java`
`main/java/sqlproject/Review.java`
`main/java/sqlproject/Warehouse.java `
`main/java/sqlproject/WarehouseAvailability.java`

The classes above are also existing tables inside the sql Databse. We have created the following classes in java so that we could use them in the future to generate objects and do operations with them. Since the goal of this project is to implement an interaction witht the user, we have created Services Classes inside the Java Folder to create corresponding functions that would call procedures or functions based on what the user wants. We made sure to match the Service class based on what the User wants to accomplish.

### Java Services Classes:
`main/java/sqlproject/AddServices.java*`
`main/java/sqlproject/DisplayServices.java`
`main/java/sqlproject/RemoveServices.java`
`main/java/sqlproject/UpdateServices.java`
`main/java/sqlproject/ValidateServices.java`

As mentioned previously, the classes above all contain functions that call procedure/functions in java. However, before executing the callable stattements for a specific actions (add/delete/update/display), each functions in java calls another function coming from *main/java/sqlproject/ValidateServices.java* thay will validate the user's input. For example, if the user enters an invalid customer email or product for instance,  then the callable statement in charge of adding/deleting/updating or displaying will not be excuted until the user enters a correct input.

### Java Application Helper
*main/java/sqlproject/ApplicationHelper.java*

The ApplicationHelper class manages user interactions and task handling in a SQL-related application. It facilitates different operations like adding, updating, deleting, and displaying various entities from the database. The class has a constructor that 
takes all of the services available (*RemoveServices rs, DisplayServices ds, UpdateServices us, AddServices as*).

Functionality:
The ApplicationHelper class acts as a the main controller of the database as it , manages user inputs, presentes menu options, and delegates tasks to specific service classes for interacting with the database. It serves as an interface for users to perform various operations seamlessly.

`./handleUserTask(int userChoiceTask)`
The HandleUserTask method receives a user's choice as an integer and performs different database-related operations based on that choice, printing the selected action (add, update, delete, or display) and executing the corresponding method accordingly, or displaying an error message for an invalid input.

`./handleOperationForAdd()`
The handleOperationForAdd method initiates the addition process by prompting the user to select a number  between 1 and 5. It then performs an action based on the user's choice, invoking specific methods related to adding a new product, adding a product to a warehouse, adding a review, placing an order for a new customer, or placing an order for an existing customer, or displays
(all found in the AddServices Class) an error message for an invalid selection.


`./handleOperationForUpdate()` 
The handleOperationForUpdate method prompts the user for a choice between 1 and 4 to perform an update operation. It verifies the input and executes specific methods associated with updating a product, inventory, review description, or flagging a review based on the user's selection (all found in the Update Services Class) or displays an error message for an invalid input.

`./handleOperationForDisplay()`
The handleOperationForDisplay method presents a menu to the user, allowing a selection between 1 and 6 for various display-related functionalities. After verifying the input, it executes specific methods associated with displaying products by category, inventory listing, customer orders, audit history, flagged customers and reviews, or the average review score based on the user's choice,( all found in the Display Services Classe) displays an error message for an invalid input.

`./handleOperationForRemove()`
The handleOperationForRemove method displays a menu of removal options to the user, prompting a selection between 1 and 5. Upon validating the input, it executes specific methods associated with removing a product, customer, review, order, or warehouse based on the user's choice (all found in Remove Services), or displays an error message for an invalid input.

`./getUserTask()`
Retrieves the user's selected task choice : The getUserTask method displays a menu of operations, prompting the user to enter a number between 0 and 4. It continuously requests input until a valid integer within the specified range is entered. Once valid input is received, it returns the chosen task number.


`./displayOperations(), displayDisplay(), displayUpdates(), displayAdd(), displayRemove()`
Helper methods displaying available operation options for users. 

`./closeAllServices()`
Closes all services (Connection) utilized by the ApplicationHelper.


## Authors and acknowledgment
Student : Iana Feniuc Id: 2243911, Student : Shakela Hossain Id :1435730 & Student :Nihel Madani-Fouatih Id:2035915

## Divison Of Tasks 

Everyone has contributed to the ERD ,Setup.sql (created tables, inserted, created objects and types were done by everyone).

Iana significantly contributed to the complete development of the Update Services Class in Java, along with the UpdatePackage.sql in SQL. Additionally, Iana played a pivotal role in establishing the Audit Tables. Furthermore, Iana showcased expertise by contributing extensively to the logic and implementation of both the Application Class and ApplicationHelper Class in Java.

Shakela demonstrated remarkable contributions by taken charge of the entire Add Services Class development in Java and was responsible for crafting AddToDatabase.sql and FindInDatabase.sql in SQL. Furthermore, Shakela made substantial contributions to the validationServices.sql.

Nihel made outstanding contributions by leading the development of the Remove Services Class and Display Services Class in Java. Additionally, Nihel played a pivotal role in establishing DisplayPackage.sql and removePackage.sql in SQL. Moreover, Nihel played a key role in shaping the logic and implementing essential components within the Application Class and ApplicationHelper Class in Java and finally contributed to validateService.sql.


## Delivered
The whole entirety of this project will be delievered on November 23rd 2023

## Project status
Done
