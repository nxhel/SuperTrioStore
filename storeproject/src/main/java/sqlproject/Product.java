package sqlproject;

import java.sql.*;
import java.util.Map;

/**
 * The Product class represents a product in the SuperTrio Store Database System.
 */
public class Product implements SQLData{
    // FIELDS
    public static final String TYPENAME = "PRODUCT_TYP";
    private int productId;
    private String productName;
    private String category;
    private double price;

    /**
     * Constructor of Product object with the specified parameters.
     *
     * @param productName The name of the product.
     * @param category    The category of the product.
     * @param price       The price of the product.
     */
    public Product(String productName, String category, double price){
        this.productName = productName;
        this.category = category;
        this.price = price;
    }
    
    // getters and setters
    public int getProductId(){
        return this.productId;
    }

    public void setProductId(int productId){
        this.productId = productId;
    }

    public String getProductName(){
        return this.productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public String getCategory(){
        return this.category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Adds a product to the database.
     *
     * @param conn                      The database connection.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class cannot be located.
     */
    public void addProductToDatabase(Connection conn)throws SQLException, ClassNotFoundException{
        try{
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put("PRODUCT_TYP", Class.forName("sqlproject.Product"));
            Product productToAdd = new Product (this.productName,this.category,this.price );
            String sql = "{call add_package.add_product(?)}";
            try(CallableStatement stmt = conn.prepareCall(sql)){
                stmt.setObject (1, productToAdd);
                stmt.execute();
            }
        }
        catch (SQLException e) {
            System.out.println("Error Occured in AddToDatabase Product");
        }
        catch (ClassNotFoundException a ){
            System.out.println("Error Occured (ClassNotFoundExpection) for Product");
        }   
    }

    /**
     * Gets the SQL type name for the Product class.
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
     * Writes the Product object to a SQL output stream.
     *
     * @param stream         The SQL output stream.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getProductId());
        stream.writeString(getProductName());
        stream.writeString(getCategory());
        stream.writeDouble(getPrice());
    }

    /**
     * Reads the Product object from a SQL input stream.
     *
     * @param stream         The SQL input stream.
     * @param typeName       The SQL type name.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        setProductName(stream.readString());
        setCategory(stream.readString());
        setPrice((stream.readDouble()));
    }
}