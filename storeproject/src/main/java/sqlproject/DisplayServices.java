package sqlproject;


import java.sql.*;
import java.util.*;
import oracle.jdbc.*;
/**
 * The DisplayServices class provides methods for displaying various data from the database
 * to the user.The Display services has methods susch as diplaying orders by customer, products 
 * by category, orders, flagged review/customers, avg reviewScore,  etc. Furthemore, it interacts with the database
 *  using JDBC and calls stored procedures to perform
 * specific database operations.
 */

/**
 * The DisplayServices class provides methods for displaying information from the SuperTrio Store Database System.
 */
public class DisplayServices {
    // Fields
    private static final Scanner scan = new Scanner(System.in);
    private Connection conn;
    private String user;
    private String pwd;

     /**
     * Constructor of DisplayServices object with the specified username and password.
     *
     * @param user The username for connecting to the database.
     * @param pwd  The password for connecting to the database.
     */
    public DisplayServices(String user, String pwd){
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
     * Closes the database connection.
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
     * List all the inventory present in the System + total quantity of products by warehouse AND in total.
     */
    public void ListInventory(){ 
        System.out.println();
        System.out.println("Printing the Inventory...");
        System.out.println("Printing the total quantity by warehouse...");

        try {
            CallableStatement cs = conn.prepareCall("{call displayPackage.ShowInventory(?)}");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
        
            ResultSet rs = (ResultSet) cs.getObject(1);
            
            while (rs.next()) {
                String productName = rs.getString("productName");
                int warehouseId = rs.getInt("warehouseId");
                int warehouseQty = rs.getInt("warehouse_qty");
                
             System.out.println("Warehouse ID:" + warehouseId + ", Product: " + productName + ", Quantity: " + warehouseQty);
            }
            System.out.println();

            if (rs != null || cs!=null) {
                rs.close();
                cs.close();
            }

            System.out.println("Printing the total quantity by products...");
            CallableStatement callableStatement = conn.prepareCall("{call displayPackage.ShowInventoryTotal(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
           
            ResultSet result = (ResultSet) callableStatement.getObject(1);
            
            while (result.next()) {
                String productName = result.getString("productName");
                int totalQuantity = result.getInt("total_quantity");
                System.out.println("Total quantity for product : "  + productName + " is " + totalQuantity);
            }
            System.out.println("Inventory Listed Succesfully");
            System.out.println();

            if (result != null || callableStatement !=null) {
                result.close();
                callableStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
   
    /**
     * Shows the average review score for a specific product.
     */
    public void showReviewScore(){
        System.out.println("Showing avg review score...");
        System.out.println("Please Enter a product");
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
            System.out.println("Sorry, but your product name doesn't correspond to any of our products.Try inputing again:");
           }
         }

        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ ? = call displayPackage.reviewScore_avg(?)}");
            callableStatement.registerOutParameter(1, Types.DOUBLE);
            callableStatement.setString(2, userChosenProduct);
            callableStatement.execute();

            double avgScore = callableStatement.getDouble(1);
            System.out.println("The average rating for " + userChosenProduct +" is " + avgScore);
            // Retrieve the returned array
            callableStatement.close();
        }catch(SQLException e){
            System.out.println("Error Occured in ShowReviewScore");
             e.printStackTrace();
        }
    }

    /**
     * Shows flagged customers .
     */
    public void showFlaggedCustomer(){
        System.out.println("Printing Flagged Customers");

        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ call displayPackage.getFlaggedCustomers(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();

            ResultSet result = (ResultSet) callableStatement.getObject(1);
            while (result.next()) {
                String flaggedCustomerName = result.getString(1);
                System.out.println("Flagged Customer Name: " + flaggedCustomerName );
                
            }

           
            if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("Error occured in showFlaggedCustomer");
                e.printStackTrace();
            }
        }
    

    /**
     * Prints all of the Flagged Reviews AND gives option ot the user to (UPDATE/DELETE/NOTHING) THE REVIEW.
     */
    public void showFlagReviews(){
         System.out.println("Printing Flagged Reviews");

         try{
            CallableStatement callableStatement = this.conn.prepareCall("{ call displayPackage.getFlaggedReviews(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();

            ResultSet result = (ResultSet) callableStatement.getObject(1);
            while (result.next()) {
                
                int  reviewId = result.getInt(1);
                String reviewDescription = result.getString(2);
                String flaggedCustomerName = result.getString(3);

                System.out.println("ReviewId : " + reviewId + " with description : " + reviewDescription + " For  Customer Name: " + flaggedCustomerName + " is flagged ");
                System.out.println("For deleting PRESS 1, For updating PRESS 2, for nothing PRESS 3" );
                try{
                    int choice=scan.nextInt(); 
                    while (!(choice==1)&&(choice==2)&&(choice==3)){
                        System.out.println("Wrong input, try again");
                        choice= scan.nextInt();
                    }
                    this.HanldeFlags(choice, reviewId);
            }catch (InputMismatchException e){
                    System.out.println("Enter again");
                }
            }  
            if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("Error occured in showFlaggedReviews");
                e.printStackTrace();
            }

    }
    
    /**
     * Shows products by category.
     */
    public void showProductByCategory (){
        System.out.println("Showing the products by Category...");
        System.out.println("Please Enter a Category");
        String userChosenCategory = "";
        boolean valid = false;
         while(!valid){
            try{
                userChosenCategory = scan.nextLine();
                valid = ValidateServices.validateProductCategory(userChosenCategory, this.conn);
                if(!valid){
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Sorry, but your product category can not be found.Try inputing again:");
           }
        }
        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ ? = call DISPLAYPACKAGE.product_ByCategory(?)}");
            callableStatement.registerOutParameter(1, Types.ARRAY, "DISPLAYPACKAGE.PRODUCTS_ARRAY");
            callableStatement.setString(2, userChosenCategory);
            callableStatement.execute();
            // Retrieve the returned array
            Array productsArray = callableStatement.getArray(1);
            printArrayItem(productsArray);
            callableStatement.close();
        }catch(SQLException e){
            System.out.println("Error Occured in listProducts");
             e.printStackTrace();
        }
    }

    public void printArrayItem (Array sqlarray) 
    {
        try{
            if (sqlarray != null) {
                String[] products = (String[]) sqlarray.getArray();
                System.out.println("Here is the list of the items you wanted...");
                for (String product : products) {
                    System.out.println(product);
                }
            }
            else{
                System.out.println("Nothing was found");
            }
            System.out.println();
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("inside printArrayItem");
        }

    }

    /**
     * Handles actions related to flagged reviews.
     *
     * @param choice   The user's choice.
     * @param reviewId The ID of the flagged review.
     */
    public void HanldeFlags(int choice, int reviewId){
        if(choice==1){
            System.out.println("Deleting...");
            RemoveServices removeServices = new RemoveServices(this.user, this.pwd);
            removeServices.removeFlaggedReview (reviewId);
        }
        if(choice==2){
            System.out.println("Updating...");
            UpdateServices updateServices = new UpdateServices(this.user, this.pwd);
            updateServices.updateFlaggedReviewByAdmin(reviewId);
        }
         if(choice==3){
           return;
        }

    }

    /**
     * Displays the audit table showing changes to the database.
     */
    public void DisplayAuditTable(){ 
        System.out.println();
        System.out.println("Displaying the Changes");
    
        try {
            CallableStatement cs = conn.prepareCall("{call displayPackage.DisplayAuditTable(?)}");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            // Retrieve the output cursor
            ResultSet rs = (ResultSet) cs.getObject(1);
            // Process the result set (retrieve data or iterate through rows)
            while (rs.next()) {
                String tableName = rs.getString("Table_name");
                String dateChanged = rs.getString("changeDate");
                String theAction = rs.getString("action");
                // display the retrieved information
             System.out.println("Changes Done on Date : " + dateChanged + ". Table Affected: " + tableName + " Change Action : " + theAction);
            }
            System.out.println();

            if (rs != null || cs!=null) {
                rs.close();
                cs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    /**
     * Displays orders by customers based on their email.
     */
    public void DisplayOrdersbyCustomers(){ 
        System.out.println("Showin Orders Based on  customer...");
        System.out.println("Please Enter the Email");
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
            System.out.println("Sorry, but the email provided cannot be found.Try inputing again:");
           }
         }

        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ call displayPackage.DisplayOrdersByCustomers(?,?)}");
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
            callableStatement.setString(1, userChosenEmail);
            callableStatement.execute();
            // Retrieve the output cursor
            ResultSet rs = (ResultSet) callableStatement.getObject(2);
            // Process the result set (retrieve data or iterate through rows)

            while (rs.next()) {
                String theproductName = rs.getString("productName");
                String productQuantity = rs.getString("product_qty");
                String theStoreName = rs.getString("storeName");
                String totalPriceOrder = rs.getString("price_order");
                // display the retrieved information
                System.out.println( theproductName + " Has Been Orderd " + productQuantity + " Times At " + theStoreName +
                " . The Total Price Of the Order is " + totalPriceOrder +"$");
            }
            System.out.println();

            if (rs != null || callableStatement!=null) {
                rs.close();
                callableStatement.close();
            }
        }   catch (SQLException e) {
            e.printStackTrace();
        } 
    } 
}
