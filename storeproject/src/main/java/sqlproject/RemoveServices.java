package sqlproject;
/**
 * The RemoveServices class provides methods for removing various data to the database,
 * such as product, warehouse, review, order, customer, depending on what the user wants do do.
 */

import java.sql.*;
import java.util.*;
import oracle.jdbc.*;

/**
 * The RemoveServices class provides methods for removing entities from the SuperTrio Store Database System.
 */
public class RemoveServices {
    // Fields
    private static final Scanner scan = new Scanner(System.in);
    private Connection conn;
    private String user;
    private String pwd;

    /**
     * @Constructor RemoveServices object with the specified username and password for
     * connecting to the database.
     * @param user The username for database connection.
     * @param pwd  The password for database connection.
     */
    public RemoveServices(String user, String pwd){
        this.user=user;
        this.pwd=pwd;
        try{
            String url = "jdbc:oracle:thin:@198.168.52.211:1521/pdbora19c.dawsoncollege.qc.ca";
            this.conn = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description Gets the database connection associated with this AddServices object.
     * @return The database connection.
     */
    public Connection getConn(){
        return this.conn;
    }
    
    
    /**
     * Closes the database connection if it is open.
     */
    public void close() {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database...");
            e.printStackTrace();
        }
    }

    /**
     * Removes a product from the System
     */
    public void removeProduct (){
        System.out.println("Deleting a product...");
        System.out.println("Please Enter a product name:");
        String userChosenProduct = "";
        boolean valid = false;
         while(!valid){
            try{
                userChosenProduct = scan.nextLine();
                valid = ValidateServices.validateProductName(userChosenProduct, this.conn);
                if(!valid){
                    throw new IllegalArgumentException();
                }
            }
            catch(IllegalArgumentException e){
                System.out.println("Sorry, but " +userChosenProduct + " doesn't correspond to any of our products.Try inputing again:");
            }
        }
        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ call removePackage.removeProduct (?)}");
            callableStatement.setString(1, userChosenProduct);
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Product removed succesfully");
        }catch(SQLException e){
            System.out.println("Error Occured in removeProduct");
             e.printStackTrace();
        }
    }

    /**
     * Removes a warehouse from the System
     */
    public void removeWarehouse (){
        System.out.println("Deleting a warehouse...");
        System.out.println("Please Enter a Warehuose Name");
        String userChosenWarehouse = "";
        boolean valid = false;
        while(!valid){
            try{
                userChosenWarehouse = scan.nextLine();
                valid = ValidateServices.validateWarehouseName(userChosenWarehouse, this.conn);
                if(!valid){
                    throw new IllegalArgumentException();
                }
            }
            catch(IllegalArgumentException e){
                System.out.println("Sorry, but " + userChosenWarehouse + "cannot be found.Try inputing again: ");
            }
        }
        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ call removePackage.removeWarehouse (?)}");
            callableStatement.setString(1, userChosenWarehouse);
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Warehouse removed succesfully");
        }catch(SQLException e){
            System.out.println("Error Occured in removeWarehouse");
             e.printStackTrace();
        }
    }

     /**
     * Removes a customer from the System
     */
    public void removeCustomer (){
        System.out.println("Deleting a Customer...");
        System.out.println("Please Enter the customer's email");
        String userChosenEmail = "";
        boolean valid = false;
        while(!valid){
            try{
                userChosenEmail = scan.nextLine();
                valid = ValidateServices.validateEmail(userChosenEmail, this.conn);
                if(!valid){
                    throw new IllegalArgumentException();
                }
            }
            catch(IllegalArgumentException e){
                System.out.println("Sorry, but " + userChosenEmail + " is invalid. Try inputing again:");
            }
        }
        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ call removePackage.removeCustomer (?)}");
            callableStatement.setString(1, userChosenEmail);
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Customer removed succesfully");
        }catch(SQLException e){
            System.out.println("Error Occured in removeCustomer");
             e.printStackTrace();
        }
    }

    /**
     * Removes an Order from the System
     */
    public void removeOrder(){
        System.out.println("Deleting an order ..., Please Enter the orderId");
        String userChosenOrderId = "";
        int ChosenOrderId=0;
        boolean valid = false;
        while(!valid){
            try{
                userChosenOrderId = scan.next();
                ChosenOrderId = Integer.parseInt(userChosenOrderId);
                valid = ValidateServices.validateOrderId(ChosenOrderId, this.conn);
                if(!valid)
                {
                    throw new IllegalArgumentException();
                }
            }
            catch(InputMismatchException ex){
                System.out.println("Sorry, but " + userChosenOrderId + " is invalid. Try inputing again:");
            
            }
            catch(IllegalArgumentException e){
                System.out.println("Sorry, but " + userChosenOrderId + " is invalid. Try inputing again:");
            }
        }
        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ call removePackage.removeOrder (?)}");
            callableStatement.setInt(1, ChosenOrderId);
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Order removed succesfully");
        }catch(SQLException e){
            System.out.println("Error Occured in removeOrder");
             e.printStackTrace();
        }
    }
    
     /**
     * Removes a Rewiew from the System
     */
    public void removeReview (){
        System.out.println("Deleting a review, Please Enter the reviewID...");
        int userChosenReview =0;
        boolean valid = false;
        while(!valid){
            try{
                userChosenReview = scan.nextInt();
                valid = ValidateServices.validateReviewId(userChosenReview, this.conn);
                if(!valid){
                    throw new IllegalArgumentException();
                }
            }
            catch(IllegalArgumentException e){
                System.out.println("Sorry, but " + userChosenReview + "cannot be found.Try inputing again: ");
            }
        }
        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ call removePackage.removeReview (?)}");
            callableStatement.setInt(1, userChosenReview);
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Review removed succesfully");
        }catch(SQLException e){
            System.out.println("Error Occured in removeReview");
             e.printStackTrace();
        }

    }
    /*
     * Deletes a flagged review
     */
    public void removeFlaggedReview (int reviewId){
        System.out.println("Deleting a Flagged Review.");
        int userChosenReview =reviewId;
        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ call removePackage.removeReview (?)}");
            callableStatement.setInt(1, userChosenReview);
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Review removed succesfully");
        }catch(SQLException e){
            System.out.println("Error Occured in removeReview");
             e.printStackTrace();
        }
    }
}
