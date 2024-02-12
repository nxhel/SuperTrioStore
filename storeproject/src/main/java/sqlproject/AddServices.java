package sqlproject;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * The AddServices class provides methods for adding various data to the database,
 * such as products, warehouses, orders, customers, addresses, and reviews.
 * It interacts with the database using JDBC and calls stored procedures to perform
 * specific database operations.
 */
public class AddServices {
    // Fields
    private Connection conn;
    private String user;
    private String pwd;
    public final Scanner scan = new Scanner(System.in);

    /**
     * Constructor of the AddServices object with the specified username and password for
     * connecting to the database.
     *
     * @param user The username for database connection.
     * @param pwd  The password for database connection.
     */
    public AddServices(String user, String pwd){
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
     * Gets the database connection associated with this AddServices object.
     *
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
     * Adds a product to the database.
     *
     * @param productName               The name of the product.
     * @param category                  The category of the product.
     * @param price                     The price of the product.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class definition for the Product is not found.
     */
    public void addProduct(String productName, String category, double price) throws SQLException, ClassNotFoundException{
        try {
            Product newProduct = new Product(productName, category, price);
            newProduct.addProductToDatabase(this.conn);
        } catch (SQLException e) {
            System.out.println("Error occurred while adding the Product to the database.");
            e.printStackTrace();
        }
    }

     /**
     * Finds the product ID by its name.
     *
     * @return The product ID.
     */
    public int findProductIdByName() {
        int productId = 0;
        System.out.println("Enter your product:");
        String productName = "";
        boolean valid = false;
        while(!valid){
            try{
                productName = scan.nextLine();
                valid = ValidateServices.validateProductName(productName, this.conn);
                if(!valid){
                    throw new IllegalArgumentException();
                }
            }catch(IllegalArgumentException e){
                System.out.println("Sorry, this product does not exist. Try another one:");
            }
        }
        try {
            String sql = "{call find_id_package.FIND_PRODUCT_ID_BY_NAME(?, ?)}";
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setString(1, productName);
                stmt.registerOutParameter(2, Types.INTEGER);

                stmt.execute();

                productId = stmt.getInt(2);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while finding the product ID.");
            e.printStackTrace();
        }

        return productId;
    }
    
     /**
     * Finds the warehouse ID by its name.
     *
     * @return The warehouse ID.
     */
    public int findWarehouseByName() {
        int warehouseId = 0;
        System.out.println("Which warehouse would you like to place your product?");
        String warehouseName = "";
        boolean valid = false;
        while(!valid){
            try{
                warehouseName = scan.nextLine();
                valid = ValidateServices.validateWarehouseName(warehouseName, this.conn);
                if(!valid){
                    throw new IllegalArgumentException();
                }
            }catch(IllegalArgumentException e){
                System.out.println("Sorry, this warehouse does not exist. Try another one:");
            }
        }
        try {
            CallableStatement callableStatement = this.conn.prepareCall("{ ? = call find_id_package.getWarehouseId (?)}");
            callableStatement.registerOutParameter(1, Types.INTEGER); // Registering OUT parameter for the total price
            callableStatement.setString(2, warehouseName);
            callableStatement.execute();
            warehouseId = callableStatement.getInt(1);
            callableStatement.close();
        } catch (SQLException e) {
            System.out.println("Error occurred while finding the warehouse ID.");
            e.printStackTrace();
        }

        return warehouseId;
    }

    /**
     * Adds warehouse availability to the database.
     *
     * @throws SQLException            If a database access error occurs.
     * @throws ClassNotFoundException  If the class definition for the WarehouseAvailability is not found.
     */
    public void addWarehouseAvail() throws SQLException, ClassNotFoundException{
        int productId = findProductIdByName();
        int warehouseId = findWarehouseByName();
        System.out.println("Enter Quantity:");
        try {
            int qty = Integer.parseInt(scan.nextLine());
            WarehouseAvailability newAvail = new WarehouseAvailability(productId, warehouseId, qty);
            newAvail.addWarehouseAvailToDatabase(this.conn);
        } catch (SQLException e) {
            System.out.println("Error occurred while adding the Order to the database.");
            e.printStackTrace();
        }
    }

    /**
     * Finds the store ID by it's name.
     *
     * @return The store ID.
     */
    public int findStoreId() {
        int storeId = 0; 
        System.out.println("Enter your Store: ");
        String store = "";
        boolean valid = false;
        while(!valid){
            try{
                store = scan.nextLine();
                valid = ValidateServices.validateStoreName(store, this.conn);
                if(!valid){
                    throw new IllegalArgumentException();
                }
            }catch(IllegalArgumentException e){
                System.out.println("Sorry, this store does not exist. Try another one:");
            }
        }

        try {
            String sql = "{call find_id_package.FIND_STORE_ID_BY_NAME(?, ?)}";
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setString(1, store);
                stmt.registerOutParameter(2, Types.INTEGER);

                stmt.execute();

                storeId = stmt.getInt(2);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while finding the store ID.");
            e.printStackTrace();
        }

        return storeId;
    }

     /**
     * Finds the city ID by it's name.
     *
     * @return The city ID.
     */
    public int findCityId() {
        int cityId = 0;
        System.out.println("Enter your City: ");
        String city = "";
        boolean valid = false;
        while(!valid){ 
            try{
                city = scan.nextLine();
                valid = ValidateServices.validateCityName(city, this.conn);
                if(valid){
                    break;
                }else{
                    throw new IllegalArgumentException();
                }
            }catch(IllegalArgumentException e){
                System.out.println("Sorry, this city does not exist.");
            }   
        }

        try {
            String sql = "{call find_id_package.FIND_CITY_ID_BY_NAME(?, ?)}";
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setString(1, city);
                stmt.registerOutParameter(2, Types.INTEGER);

                stmt.execute();

                cityId = stmt.getInt(2);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while finding the city ID.");
            e.printStackTrace();
        }

        return cityId;
    }

    /**
     * Finds the existing customer's ID by their email.
     *
     * @return The customer ID.
     */
    public int findExistingCustomer() {
        int customerId = 0;
        System.out.println("Enter your email: ");
        String email = "";
        boolean valid = false;
        while(!valid){ 
            try{
                email = scan.nextLine();
                valid = ValidateServices.validateEmail(email, this.conn);
                if(valid){
                    break;
                }else{
                    throw new IllegalArgumentException();
                }
            }catch(IllegalArgumentException e){
                System.out.println("Sorry, this email does not exist.");
            }   
        }

        try {
            String sql = "{call find_id_package.FIND_CUSTOMER_ID_BY_EMAIL(?, ?)}";
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setString(1, email);
                stmt.registerOutParameter(2, Types.INTEGER);
                stmt.execute();
                customerId = stmt.getInt(2);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while finding the Customer ID.");
            e.printStackTrace();
        }
        return customerId;
    }

     /**
     * Adds an order to the database.
     *
     * @param productId                 The ID of the product in the order.
     * @param storeid                   The ID of the store where the order is placed.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class definition for the Orders is not found.
     */
    public void addOrder(int productId , int storeid) throws SQLException, ClassNotFoundException{
        System.out.println("How many would you like to order?");
        int productQty = 0;
        boolean valid = false;
        while(!valid){// true
            try{
                productQty = Integer.parseInt(scan.nextLine());
                valid = ValidateServices.validateQuantity(productId, productQty, this.conn);
                if(!valid){
                    throw new IllegalArgumentException();
                }
            }catch(IllegalArgumentException e){
                System.out.println("Sorry, but we currently don't have enough in stock.Try inputing again:");
            }catch(InputMismatchException e){
                System.out.println("invalid input.");
            }
        }
       
        try {
            double orderPrice = totalPrice(productId,productQty);
            Orders newOrder = new Orders(productId, productQty, storeid, orderPrice);
            newOrder.addOrderToDatabase(this.conn);
        } catch (SQLException e) {
            System.out.println("Error occurred while adding the Order to the database.");
            e.printStackTrace();
        }
    }
    

     /**
     * Adds an order to the database for an existing customer.
     *
     * @param customerid                The ID of the existing customer.
     * @param productId                 The ID of the product in the order.
     * @param storeid                   The ID of the store where the order is placed.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class definition for the Orders is not found.
     */
    public void addOrderToExisting(int customerid, int productId, int storeid) throws SQLException, ClassNotFoundException{
        System.out.println("How many would you like to order?");
        int productQty = 0;
        boolean valid = false;
        while(!valid){// true
            try{
                productQty = Integer.parseInt(scan.nextLine());
                valid = ValidateServices.validateQuantity(productId, productQty, this.conn);
                if(!valid){
                    throw new IllegalArgumentException();
                }
            }catch(IllegalArgumentException e){
                System.out.println("Sorry, but we currently don't have enough in stock.Try inputing again:");
            }catch(InputMismatchException e){
                System.out.println("invalid input.");
            }
        }
       
        try {
            double orderPrice = totalPrice(productId,productQty);
            Orders newOrder = new Orders(customerid, productId, productQty, storeid, orderPrice);
            newOrder.addOrderToExisting(this.conn);
        } catch (SQLException e) {
            System.out.println("Error occurred while adding the Order to the database.");
            e.printStackTrace();
        }
    }

    /**
     * Adds a new customer to the database.
     *
     * @param firstname                 The first name of the new customer.
     * @param lastname                  The last name of the new customer.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class definition for the Customer is not found.
     */
    public void addCustomer(String firstname, String lastname) throws SQLException, ClassNotFoundException {
        System.out.println("Enter your email: ");
        String email = "";
        boolean valid = true; // Change the initial value to false
        while (valid) {
            try {
                email = scan.nextLine();
                valid = ValidateServices.validateEmail(email, this.conn);
                if (valid) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Sorry, this email already exists. Try another one:");
            }
        }
        try {
            Customer newCustomer = new Customer(firstname, lastname, email);
            newCustomer.addCustomerToDatabase(this.conn);
        } catch (SQLException e) {
            System.out.println("Error occurred while adding the Customer to the database.");
            e.printStackTrace();
        }
    }

     /**
     * Adds an address to the database.
     *
     * @param street                    The street address.
     * @param cityId                    The ID of the city associated with the address.
     * @param country                   The country of the address.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class definition for the Address is not found.
     */
    public void addAddress(String street, int cityId, String country) throws SQLException, ClassNotFoundException{
        try {
            Address newAddress = new Address(street, cityId, country);
            newAddress.addAddressToDatabase(this.conn);
        } catch (SQLException e) {
            System.out.println("Error occurred while adding the Address to the database.");
            e.printStackTrace();
        }
    }

    /**
     * Adds a review to the database.
     *
     * @throws SQLException            If a database access error occurs.
     * @throws ClassNotFoundException  If the class definition for the Review is not found.
     */
    public void addReview() throws SQLException, ClassNotFoundException{
        int productId = findProductIdByName();
        int customerId = findExistingCustomer();
        System.out.println("Enter your product rating: ");
        int productRating = 0;
        boolean valid = false;
        while(!valid){// true
            try{   
                productRating = Integer.parseInt(scan.nextLine());
                if(productRating >= 0 && productRating <=5){
                    valid = true;
                }else{
                    throw new InputMismatchException();
                }
            }catch(InputMismatchException e){
                System.out.println("Sorry, rating has to be between 1 and 5.");
            }
        }
        try {
            System.out.println("Write your review: ");
            String description = scan.nextLine();
            Review newReview = new Review(productId, customerId, productRating, 0,description);
            newReview.addReviewToDatabase(this.conn);
        } catch (SQLException e) {
            System.out.println("Error occurred while adding the review to the database.");
            e.printStackTrace();
        }
    }

    /**
     * Calculates the total price of an order.
     *
     * @param productId     The ID of the product in the order.
     * @param qty           The quantity of the product in the order.
     * @return              The total price of the order.
     */
    public double totalPrice(int productId, int qty){
        double totalPrice = 0.0;

        try{
            CallableStatement callableStatement = this.conn.prepareCall("{ ? = call find_id_package.getTotalPrice (?,?)}");
            callableStatement.registerOutParameter(1, Types.DOUBLE); // Registering OUT parameter for the total price
            callableStatement.setInt(2, productId);
            callableStatement.setInt(3, qty);
            callableStatement.execute();
            totalPrice = callableStatement.getDouble(1);
        
            callableStatement.close();
        }catch(SQLException e){
            System.out.println("Error Occurred in getTotalPrice");
            e.printStackTrace();
        }
        return totalPrice;
    }
    
    /**
     * Adds a new product to the database with user interactions, does the same as addProduct.
     *
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class definition for the Product is not found.
     */
    public void addANewProduct() throws SQLException, ClassNotFoundException{
        try{
        System.out.println("Enter the name of your product");
        String productName = scan.nextLine();
            
        System.out.println("Enter the category");
        String category = scan.nextLine();
            
        System.out.println("Enter the price");
        double price = Double.parseDouble(scan.nextLine());
        
        addProduct(productName, category, price);
        }catch(NumberFormatException e){
            System.out.println("invalid input.");
        }
    }

    /**
     * Processes orders for an existing customer.
     *
     * @throws SQLException            If a database access error occurs.
     * @throws ClassNotFoundException  If the class definition for the Orders is not found.
     */
    public void existingCustomer() throws SQLException, ClassNotFoundException{
        int customerid = findExistingCustomer();
        int productId = findProductIdByName();
        int storeid = findStoreId();
        addOrderToExisting(customerid, productId, storeid);
    }

    /**
     * Processes orders for a new customer.
     *
     * @throws SQLException            If a database access error occurs.
     * @throws ClassNotFoundException  If the class definition for various entities is not found.
     */
    public void newCustomer() throws SQLException, ClassNotFoundException{
        System.out.println("from which store you want to buy?:");
        int storeid = findStoreId();
        int productId = findProductIdByName();

        System.out.println("Enter firstname:");
        String firstname = scan.nextLine();

        System.out.println("Enter lastname:");
        String lastname = scan.nextLine();

        System.out.println("Enter street address:");
        String address = scan.nextLine();
        System.out.println("Enter your city: 1.Montreal 2.Toronto 3. Calgary"); //assuming we can only order if from these 3 places 
        int city = findCityId();
        addAddress(address, city, "Canada");
        addCustomer(firstname, lastname);
        addOrder(productId, storeid);
    }
}
