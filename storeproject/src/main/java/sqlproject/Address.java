package sqlproject;

import java.sql.*;
import java.util.Map;

/**
 * The Address class represents a physical address and implements the SQLData interface for
 * interaction with a SQL database. It contains methods to set and to retrieve address details (get set methods),
 * as well as methods for database interaction (getSQLTypeName, writeSQL, readSQL)
 */
public class Address implements SQLData{
    /**
     * The SQL type name associated with the Address class.
     */
    public static final String TYPENAME = "ADDRESS_TYP";
    // Fields
    private int addressId;
    private String street;
    private int cityId;
    private String country;

    /**
     * Constructor of an Address object with the specified street, city ID, and country.
     *
     * @param street  The street address.
     * @param cityId  The ID of the city associated with the address.
     * @param country The country of the address.
     */
    public Address(String street, int cityId, String country){
        this.street = street;
        this.cityId = cityId;
        this.country = country;
    }

    // Getter and Setter Methods(name self-explanatory)

    public int getAddressId(){
        return this.addressId;
    }

    public void setAddressId(int addressId){
        this.addressId = addressId;
    }

    public String getStreet(){
        return this.street;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public int getCityId(){
        return this.cityId;
    }

    public void setCityId(int cityId){
        this.cityId = cityId;
    }

    public String getCountry(){
        return this.country;
    }

    public void setCountry(String country){
        this.country = country;
    }
    
    // Database Interaction Methods

    /**
     * Adds the address to the database using a stored procedure.
     *
     * @param conn                      The database connection.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class definition for the ADDRESS_TYP is not found.
     */
    public void addAddressToDatabase(Connection conn)throws SQLException, ClassNotFoundException{
        try{
            // Get the type map from the connection
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            // Associate the ADDRESS_TYP with the Address class
            map.put("ADDRESS_TYP", Class.forName("sqlproject.Address"));
            // Create a new Address object with current details
            Address AddressToAdd = new Address (this.street, this.cityId, this.country);
            // Prepare and execute the stored procedure to add the address to the database
            String sql = "{call add_package.add_address(?)}";
            try(CallableStatement stmt = conn.prepareCall(sql)){
                stmt.setObject (1, AddressToAdd);
                stmt.execute();
            }
        }
        catch (SQLException e) {
            System.out.println("Error Occured in AddToDatabase Address");
        }
        catch (ClassNotFoundException a ){
            System.out.println("Error Occured (ClassNotFoundExpection) for Address");
        }   
    }

    // SQLData Interface Methods

    /**
     * Gets the SQL type name associated with the Address class.
     *
     * @return               The SQL type name.
     * @throws SQLException  If a database access error occurs.
     */
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return TYPENAME;
    }

    /**
     * Writes the Address object to a SQL output stream.
     *
     * @param stream         The SQL output stream.
     * @throws SQLException  If a database access error occurs.
     */
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getAddressId());
        stream.writeString(getStreet());
        stream.writeInt(getCityId());
        stream.writeString(getCountry());
    }

    /**
     * Reads the Address object from a SQL input stream.
     *
     * @param stream          The SQL input stream.
     * @param typeName        The SQL type name.
     * @throws SQLException   If a database access error occurs.
     */
    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        // setAddressId(stream.readInt());
        setStreet(stream.readString());
        setCityId(stream.readInt());
        setCountry(stream.readNString());
    }
}