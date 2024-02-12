package sqlproject;
import java.sql.*;
import java.util.Map;

/**
 * The Customer class represents a customer in the SuperTrio Store Database System.
 * It implements the SQLData interface for custom mapping to SQL types.
 */
public class Customer implements SQLData{
    // FIELDS
    public static final String TYPENAME = "CUSTOMER_TYP";
    private int customerId;
    private String firstname;
    private String lastname;
    private String email;
    private int addressId;
    
    /**
     * Constructor of Customer object with the specified attributes.
     *
     * @param firstname  The first name of the customer.
     * @param lastname   The last name of the customer.
     * @param email      The email address of the customer.
     */
    public Customer(String firstname, String lastname, String email){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    // setters and getters
    public int getCustomerId(){
        return this.customerId;
    }

    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public int getAddressId(){
        return this.addressId;
    }

    public void setAddressId(int addressId){
        this.addressId = addressId;
    }

    /**
     * Gets the SQL type name for the Customer class.
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
     * Adds the customer to the database using a stored procedure.
     *
     * @param conn                      The database connection.
     * @throws SQLException             If a SQL error occurs.
     * @throws ClassNotFoundException   If the required class is not found.
     */
    public void addCustomerToDatabase(Connection conn)throws SQLException, ClassNotFoundException{
        try{
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put("CUSTOMER_TYP", Class.forName("sqlproject.Customer"));
            Customer CustomerToAdd = new Customer (this.firstname, this.lastname, this.email);
            String sql = "{call add_package.add_customer(?)}";
            try(CallableStatement stmt = conn.prepareCall(sql)){
                stmt.setObject (1, CustomerToAdd);
                stmt.execute();
            }
        }
        catch (SQLException e) {
            System.out.println("Error Occured in AddToDatabase Customer");
        }
        catch (ClassNotFoundException a ){
            System.out.println("Error Occured (ClassNotFoundExpection) for Customer");
        }   
    }

    /**
     * Writes the Customer object to a SQL output stream.
     *
     * @param stream         The SQL output stream.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getCustomerId());
        stream.writeString(getFirstname());
        stream.writeString(getLastname());
        stream.writeString(getEmail());
        stream.writeInt(getAddressId());
    }

    /**
     * Reads the Customer object from a SQL input stream.
     *
     * @param stream         The SQL input stream.
     * @param typeName       The SQL type name.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        // setCustomerId(stream.readInt());
        setFirstname(stream.readString());
        setLastname(stream.readString());
        setEmail(stream.readString());
        setAddressId(stream.readInt());
    }
}

    

