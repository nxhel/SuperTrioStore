package sqlproject;

import java.sql.*;
import java.util.Map;

/**
 * The Orders class represents an order in the SuperTrio Store Database System.
 */
public class Orders implements SQLData{
    // FIELDS
    public static final String TYPENAME = "ORDERS_TYP";
    private int orderId;
    private int customerId;
    private int productId;
    private String orderDate;
    private int productQty;
    private int storeId;
    private double orderPrice;

    /**
     * Constructor of Orders object with the specified parameters.
     *
     * @param productId   The ID of the product in the order.
     * @param productQty  The quantity of the product in the order.
     * @param storeId     The ID of the store where the order is placed.
     * @param orderPrice  The total price of the order.
     */
    public Orders(int productId, int productQty, int storeId, double orderPrice){
        this.productId = productId;
        this.productQty = productQty;
        this.storeId = storeId;
        this.orderPrice = orderPrice;
    }

    /**
     * overloaded constructor of Orders object with additional customer-related parameters.
     *
     * @param customerId  The ID of the customer placing the order.
     * @param productId   The ID of the product in the order.
     * @param productQty  The quantity of the product in the order.
     * @param storeId     The ID of the store where the order is placed.
     * @param orderPrice  The total price of the order.
     */
    public Orders(int customerId, int productId, int productQty, int storeId, double orderPrice){
        this(productId, productQty, storeId, orderPrice);
        this.customerId = customerId;
    }

    // setters and getters
    public int getOrderId(){
        return this.orderId;
    }

    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

    public int getCustomerId(){
        return this.customerId;
    }

    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    public int getProductId(){
        return this.productId;
    }

    public void setProductId(int productId){
        this.productId = productId;
    }

    public String getOrderDate(){
        return this.orderDate;
    }

    public void setOrderDate(String orderDate){
        this.orderDate = orderDate;
    }

    public int getStoreId(){
        return this.storeId;
    }

    public void setStoreId(int storeId){
        this.storeId = storeId;
    }

    public int getProductQty(){
        return this.productQty;
    }

    public void setProductQty(int productQty){
        this.productQty = productQty;
    }

    public double getOrderPrice(){
        return this.orderPrice;
    }

    public void setOrderPrice(double orderPrice){
        this.orderPrice = orderPrice;
    }

    /**
     * Adds an order to the database.
     *
     * @param conn                      The database connection.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class cannot be located.
     */
    public void addOrderToDatabase(Connection conn)throws SQLException, ClassNotFoundException{
        try{
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put("ORDERS_TYP", Class.forName("sqlproject.Orders"));
            Orders orderToAdd = new Orders (this.productId, this.productQty,this.storeId, this.orderPrice);
            String sql = "{call add_package.add_order(?)}";
            try(CallableStatement stmt = conn.prepareCall(sql)){
                stmt.setObject (1, orderToAdd);
                stmt.execute();
            }
        }catch (SQLException e) {
            System.out.println("Error Occured in AddToDatabase Orders");
        }catch (ClassNotFoundException a ){
            System.out.println("Error Occured (ClassNotFoundExpection) for Orders");
        }   
    }

    /**
     * Adds an order to an existing customer in the database.
     *
     * @param conn                      The database connection.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class cannot be located.
     */
    public void addOrderToExisting(Connection conn)throws SQLException, ClassNotFoundException{
        try{
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put("ORDERS_TYP", Class.forName("sqlproject.Orders"));
            Orders orderToAdd = new Orders (this.customerId, this.productId, this.productQty,this.storeId, this.orderPrice);
            String sql = "{call add_package.add_order_toExistingCustomer(?)}";
            try(CallableStatement stmt = conn.prepareCall(sql)){
                stmt.setObject (1, orderToAdd);
                stmt.execute();
            }
        }catch (SQLException e) {
            System.out.println("Error Occured in AddToDatabase Orders");
        }catch (ClassNotFoundException a ){
            System.out.println("Error Occured (ClassNotFoundExpection) for Orders");
        }   
    }
    
    /**
     * Gets the SQL type name for the Orders class.
     *
     * @return               The SQL type name.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return TYPENAME;
    }

    /**
     * Writes the Orders object to a SQL output stream.
     *
     * @param stream         The SQL output stream.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getOrderId());
        stream.writeInt(getCustomerId());
        stream.writeInt(getProductId());
        stream.writeString(getOrderDate());
        stream.writeInt(getProductQty());
        stream.writeInt(getStoreId());
        stream.writeDouble(getOrderPrice());
    }

    /**
     * Reads the Orders object from a SQL input stream.
     *
     * @param stream         The SQL input stream.
     * @param typeName       The SQL type name.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        // setOrderId(stream.readInt());
        setCustomerId(stream.readInt());
        setProductId(stream.readInt());
        setOrderDate(stream.readString()); 
        setStoreId(stream.readInt());
        setProductQty(stream.readInt());
        setOrderPrice(stream.readDouble());
    }
}
