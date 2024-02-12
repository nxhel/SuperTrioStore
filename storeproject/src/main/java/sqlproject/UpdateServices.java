package sqlproject;

/**
 * @author Iana Feniuc
 * @version 2023-11-23
*/

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.InputMismatchException;
import java.util.Scanner;

import oracle.security.o3logon.a;


/**
 * Service class for updating various data in the SQLProject database.
 * This class provides methods for updating products, inventory, reviews, and flagged reviews.
 */
public class UpdateServices {

    //Private Fields
    private Connection conn;
    private String user;
    private String pwd;

    //Scanner to be used everywhere in the code
    public final Scanner scan = new Scanner(System.in);

    //Constructor
    /**
     * @param user               The user Id.
     * @param pwd                The user passeword.
     */
    public UpdateServices(String user, String pwd){
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
     * Updates the information of a product in the database.
     * User is prompted to input the product name and choose which information to update (name, category, or price).
     * The updated information is then stored in the database.
>>>>>>> d9295c7fcddfd47ab1a4d6c57ae563547c3cc451
     */
    public void updateProduct(){
         System.out.println("What product do you want to update ? Input its name:");
         String productName = "";
         boolean valid = false;
         while(!valid){
        try{
            productName = scan.nextLine();
            valid = ValidateServices.validateProductName(productName, this.conn);
            if(!valid){
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Sorry, but your product name doesn't correspond to any of our products.Try inputing again:");
           }
         }
         String newProductName = "NULL";
         String newCategory = "NULL";
         double newPrice = 0;
         int productId ;
         String updateName ;
         
         //Just for didsplay of information
         String [] productColumns = new String[3];
         productColumns[0]="Product Name";
         productColumns[1]= "Product Category";
         productColumns[2]="Product Price";
        

         for(int i=0;i<productColumns.length;i++){
         System.out.println("Do you want to update the "+productColumns[i]+"? Input y(for yes) or n(for no).");
            
         while (true) {
            try {

                updateName = scan.next();

                // If the user enters a valid number, exit the loop
                if(!(updateName.charAt(0)=='y'|| updateName.charAt(0)=='n')){
                    throw new InputMismatchException();
                }

                break;

            } catch (InputMismatchException e) {
                // If the user enters something that is not a char
                System.out.println("Invalid input. Please enter y(for yes) or n(for n):");
                // Consume the invalid input to avoid an infinite loop
                scan.nextLine();
            }
        }
  
        if(updateName.charAt(0)=='y'){
            System.out.println("Input the new "+productColumns[i]+"? ");
            if(i==0){
                scan.nextLine();  
                newProductName = scan.nextLine();
            }
            else if(i==1){
                scan.nextLine();  
                newCategory = scan.nextLine();       
            }
            else{
                scan.nextLine();
                newPrice = scan.nextDouble();
            }
        }
    }
         
        try {
            CallableStatement stmt = this.conn.prepareCall("{? = call update_package.getProductId(?)}");
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setString(2, productName );
                stmt.execute();
                productId = stmt.getInt(1);
            CallableStatement stmt2 = this.conn.prepareCall("{call update_package.updateProduct(?,?,?,?)}");
                stmt2.setInt(1, productId);
                stmt2.setString(2, newProductName);
                stmt2.setString(3, newCategory);
                stmt2.setDouble(4, newPrice);
                stmt2.executeUpdate();
            
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        
        System.out.println("Product "+productName+" was updated sucessefully !");
    }

    /**
     * Updates the inventory quantity of a product in a specific warehouse.
     * User is prompted to input the product name, warehouse name, and the new quantity.
     * The updated information is then stored in the database.

     */
    public void updateIventory(){
        System.out.println("What product do you want to update ? Input its name: ");
        String productName = "";
        boolean valid = false;
         while(!valid){
        try{
            productName = scan.nextLine();
            valid = ValidateServices.validateProductName(productName, this.conn);
            if(!valid){
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Sorry, but your product name doesn't correspond to any of our products./n+Try inputing again:");
           }
         }
        
        System.out.println("What warehouse is it stored in ? Input its name:");
        String warehouseName = "";
      
        boolean valid2 = false;
          while(!valid2){
         try{
             warehouseName = scan.nextLine();
             valid2 = ValidateServices.validateWarehouseName(warehouseName, this.conn);
 
             if(!valid2){
                 throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Sorry, but your warehouse name doesn't correspond to any of our warehouses./n+Try inputing again:");
           }
         }

        System.out.println("Input the new quantity?");
        int new_warehouse_qty = scan.nextInt();

        int productId;
        int warehouseId;

        try {
            CallableStatement product = this.conn.prepareCall("{? = call UPDATE_PACKAGE.getProductId(?)}");
                product.registerOutParameter(1, Types.INTEGER);
                product.setString(2, productName );
                product.execute();
                productId = product.getInt(1);
            CallableStatement warehouse = this.conn.prepareCall("{? = call update_package.getWarehouseId(?)}");
                warehouse.registerOutParameter(1, Types.INTEGER);
                warehouse.setString(2,warehouseName );
                warehouse.execute();
                warehouseId = warehouse.getInt(1);    

                boolean validInput = ValidateServices.validateIfProductExistInWarehouse(productId,warehouseId, this.conn);

                if(!validInput){
                    System.out.println("Sorry but the correspodning product doesn't exist in this warehouse!");
                    return;
                }

            CallableStatement stmt = this.conn.prepareCall("{call update_package.updateInventory(?,?,?)}");
                stmt.setInt(1, productId);
                stmt.setInt(2, warehouseId);
                stmt.setInt(3, new_warehouse_qty);
                stmt.executeUpdate();
           
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("The Inventory was updated sucessefully !");
    }

    
    /**
     * Updates the description of a review based on customer email, product name, and rating.
     * User is prompted to input the necessary information and the new description.
     * The updated information is then stored in the database.
>>>>>>> d9295c7fcddfd47ab1a4d6c57ae563547c3cc451
     */
    public void updateReviewDescription(){

        System.out.println("For which customer you want to update the review? Input their email: ");
        String customerEmail = "";
        boolean valid = false;
        while(!valid){
        try{
            customerEmail = scan.next();
            valid = ValidateServices.validateEmail(customerEmail, this.conn);
            if(!valid){
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Sorry, but your email doesn't correspond to any of our customers./n+Try inputing again:");
           }
         }
        scan.nextLine();
        System.out.println("For which product you want to update the review? Input the product name: ");
        String productName ="";
        boolean valid2 = false;
         while(!valid2){
        try{
            productName = scan.nextLine();
            valid2 = ValidateServices.validateProductName(productName, this.conn);
            if(!valid2){
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Sorry, but your product name doesn't correspond to any of our products./n+Try inputing again:");
           }
         }
        System.out.println("What was the rating for this review? Input a number between 1 and 5:");
        int rating;
        while(true){
            try{
                rating = scan.nextInt();
                if(rating<1 || rating>5){
                    throw new InputMismatchException();
                }
                break;
            }
            catch (InputMismatchException e){
                // If the user enters something that is not a integer
                System.out.println("Invalid input. Please enter a number between 1 and 5:");
                // Consume the invalid input to avoid an infinite loop
                scan.nextLine();
            }
        }

        System.out.println("Input the new description:");
        scan.nextLine();
        String newDescription = scan.nextLine(); 

        int customerId;
        int productId;

        try {
                CallableStatement product = this.conn.prepareCall("{? = call update_package.getProductId(?)}");
                product.registerOutParameter(1, Types.INTEGER);
                product.setString(2, productName );
                product.execute();
                productId = product.getInt(1);

                CallableStatement customer = this.conn.prepareCall("{? = call update_package.getCustomerId(?)}");
                customer.registerOutParameter(1, Types.INTEGER);
                customer.setString(2, customerEmail );
                customer.execute();
                customerId = customer.getInt(1);

                boolean validInput = ValidateServices.validateIfReviewExist(productId,customerId,rating, this.conn);

                if(!validInput){
                    System.out.println("Sorry but the correspodning review doesn't exist !");
                    return;
                }


                CallableStatement stmt = this.conn.prepareCall("{call update_package.updateReview(?,?,?,?)}");
                stmt.setInt(1, productId);
                stmt.setInt(2, customerId);
                stmt.setInt(3, rating);
                stmt.setString(4, newDescription);
                stmt.executeUpdate();
        
            } 
        catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("The review description was updated sucessefully!");
    }

    
    /**
     * Updates the description of a flagged review based on the review ID.
     * User is prompted to input the new description.
     * The updated information is then stored in the database.
     *
     * @param reviewId The ID of the flagged review to be updated.
     */
    public void updateFlaggedReviewByAdmin(int reviewId){
        System.out.println("This review will be modified since its been flagged many times and its content is harmful to our audinece :( ");
        System.out.println("Input the new description fo this review:");
        String newDescription = scan.nextLine(); 

        try{
        CallableStatement stmt = this.conn.prepareCall("{call update_package.updateFlaggedReview(?,?)}");
                stmt.setInt(1, reviewId);
                stmt.setString(2, newDescription);
                stmt.executeUpdate();
        }
        catch (SQLException e) {
                e.printStackTrace();
            }
             System.out.println("The review was updated successefully!");

    }
    
   /**
     * Flags a review based on customer email, product name, and rating.
     * User is prompted to input the necessary information.
     * The flag is then added to the review in the database.
     */
    public void FlagReview(){

        String newDescription = "NULL";

        System.out.println("For which customer you want to add a flag? Input their email: ");
        String customerEmail = "";
        boolean valid = false;
        while(!valid){
        try{
            customerEmail = scan.next();
            valid = ValidateServices.validateEmail(customerEmail, this.conn);
            if(!valid){
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Sorry, but your email doesn't correspond to any of our customers./n+Try inputing again:");
           }
         }
        scan.nextLine();
        System.out.println("For which product you want to add a flag? Input the product name: ");
        String productName ="";
        boolean valid2 = false;
         while(!valid2){
        try{
            productName = scan.nextLine();
            valid2 = ValidateServices.validateProductName(productName, this.conn);
            if(!valid2){
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Sorry, but your product name doesn't correspond to any of our products./n+Try inputing again:");
           }
         }
        System.out.println("What was the rating on this review? Input a number between 1 and 5:");
        int rating;
        while(true){
            try{
                rating = scan.nextInt();
                if(rating<1 || rating>5){
                    throw new InputMismatchException();
                }
                break;
            }
            catch (InputMismatchException e){
                // If the user enters something that is not a integer
                System.out.println("Invalid input. Please enter a number between 1 and 5:");
                // Consume the invalid input to avoid an infinite loop
                scan.nextLine();
            }
        }

        int customerId;
        int productId;

        try {
                CallableStatement product = this.conn.prepareCall("{? = call update_package.getProductId(?)}");
                product.registerOutParameter(1, Types.INTEGER);
                product.setString(2, productName );
                product.execute();
                productId = product.getInt(1);

                CallableStatement customer = this.conn.prepareCall("{? = call update_package.getCustomerId(?)}");
                customer.registerOutParameter(1, Types.INTEGER);
                customer.setString(2, customerEmail );
                customer.execute();
                customerId = customer.getInt(1);

                boolean validInput = ValidateServices.validateIfReviewExist(productId,customerId,rating, this.conn);

                if(!validInput){
                    System.out.println("Sorry but the correspodning review doesn't exist !");
                    return;
                }

                CallableStatement stmt = this.conn.prepareCall("{call update_package.updateReview(?,?,?,?)}");
                stmt.setInt(1, productId);
                stmt.setInt(2, customerId);
                stmt.setInt(3, rating);
                stmt.setString(4, newDescription);
                stmt.executeUpdate();
        
            } 
        catch (SQLException e) {
                e.printStackTrace();
            }
         System.out.println("The review was flagged sucessefully!");
    }

    
    /**
     * Closes the database connection.
     * This method should be called when the service is no longer in use.
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
}