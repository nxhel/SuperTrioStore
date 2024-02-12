package sqlproject;
import java.util.Scanner;
import java.sql.*;

/**
 * The Application class serves as the begining of the application where the user enters the SuperTrio Store Database System.
 * It allows users to interact with various services such as adding, removing, updating, and displaying
 * information in the database. The main method initializes necessary services and provides a user interface.
 */
public class Application {
    /**
     * The main method that serves as the entry point to the application.
     *
     * @throws SQLException            If a database access error occurs.
     * @throws ClassNotFoundException  If the class definition for the ApplicationHelper is not found.
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException{

        System.out.println("----WELCOME TO THE SUPERTRIO STORE DATABSE SYSTEM-----");
        // Prompt the user for username and password
        String user = new String(System.console().readLine("Enter your username: "));
        String pwd = new String(System.console().readPassword("Enter your password: "));
       
        // Initialize services
        RemoveServices rs = new RemoveServices(user, pwd);
        DisplayServices ds = new DisplayServices(user, pwd);
        UpdateServices us = new UpdateServices(user, pwd);
        AddServices as = new AddServices(user, pwd);
        ApplicationHelper applicationHelper = new ApplicationHelper(rs, ds, us, as);
        
        boolean userExit = false;

        while(!userExit){
            System.out.println();
            System.out.println("----Please Select Your Action For Today----");
            // Get the user's selected task
            int taskToday =  applicationHelper.getUserTask();
            if(taskToday==0){
                System.out.println("---GoodBye, see you next time!!---");
                userExit=true; 
            }
            else{
                // Handle the selected user task
                applicationHelper.HandleUserTask(taskToday);
                }
            } 
            // Close all services when the application exits
            applicationHelper.closeAllServices();
        }
    }