package sqlproject;

import java.sql.*;
import java.util.Map;

/**
 * The Warehouse class represents a warehouse entity in the SuperTrio Store Database System.
 */
public class Warehouse implements SQLData{
    public static final String TYPENAME = "WAREHOUSE_TYP";
    private int warehouseId;
    private String warehouseName;
    private int addressId;

    /**
     * Constructor of Warehouse object with the specified parameters.
     *
     * @param warehouseId   The ID of the warehouse.
     * @param warehouseName The name of the warehouse.
     * @param addressId     The ID of the address associated with the warehouse.
     */
    public Warehouse( int warehouseId, String warehouseName, int addressId){
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.addressId = addressId;
    
    }

    // Getters and setters
    public int getWareHouseId(){
        return this.warehouseId;
    }

    public void setWareHouseId(int warehouseId){
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName(){
        return this.warehouseName;
    }

    public void setWarehouseName(String warehouseName){
        this.warehouseName = warehouseName;
    }

    public int getAddressId(){
        return this.addressId;
    }

    public void setAddressId(int addressId){
        this.addressId = addressId;
    }

    /**
     * Adds the warehouse to the database.
     *
     * @param conn                      The database connection.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class is not found.
     */
    public void addToDatabase(Connection conn)throws SQLException, ClassNotFoundException{
        try{
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put("WAREHOUSE_TYP", Class.forName("sqlproject.Warehouse"));
            Warehouse warehouseToAdd = new Warehouse (this.warehouseId, this.warehouseName, this.addressId);
            String sql = "{call add_package.add_warehouse(?)}";
            try(CallableStatement stmt = conn.prepareCall(sql)){
                stmt.setObject (1, warehouseToAdd);
                stmt.execute();
            }
        }
        catch (SQLException e) {
            System.out.println("Error Occured in AddToDatabase Warehouse");
        }
        catch (ClassNotFoundException a ){
            System.out.println("Error Occured (ClassNotFoundExpection) for Warehouse");
        }   
    }

    /**
     * Gets the SQL type name for the Warehouse class.
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
     * Writes the Warehouse object to a SQL output stream.
     *
     * @param stream         The SQL output stream.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getWareHouseId());
        stream.writeString(getWarehouseName());
        stream.writeInt(getAddressId());

    }

    /**
     * Reads the Warehouse object from a SQL input stream.
     *
     * @param stream         The SQL input stream.
     * @param typeName       The SQL type name.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        setWareHouseId(stream.readInt());
        setWarehouseName(stream.readString());
        setWareHouseId(stream.readInt());
    }
}