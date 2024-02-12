package sqlproject;

import java.sql.*;
import java.util.Map;

/**
 * The WarehouseAvailability class represents a WarehouseAvailability in the SuperTrio Store Database System.
 */
public class WarehouseAvailability implements SQLData{
    // FIELDS
    public static final String TYPENAME = "AVAILABILITY_WAREHOUSE_TYP";
    private int productId;
    private int warehouseId;
    private int warehouseQty;

    /**
     * Constructor for WarehouseAvailability with the specified parameters.
     * 
     * @param productId     The ID of the product.
     * @param warehouseId   The ID of the warehouse.
     * @param warehouseQty  The quantity of the product available in the warehouse.
     */
    public WarehouseAvailability( int productId, int warehouseId, int warehouseQty){
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.warehouseQty = warehouseQty;
    }

    // Getters and setters
    public int getProductId(){
        return this.productId;
    }

    public void setProductId(int productId){
        this.productId = productId;
    }

    public int getWarehouseId(){
        return this.warehouseId;
    }

    public void setWarehouseId(int warehouseId){
        this.warehouseId = warehouseId;
    }

    public int getWarehouseQty(){
        return this.warehouseQty;
    }

    public void setWarehouseQty(int warehouseQty){
        this.warehouseQty = warehouseQty;
    }

    /**
     * Adds a WarehouseAvailability to the database.
     *
     * @param conn                      The database connection.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class cannot be located.
     */
    public void addWarehouseAvailToDatabase(Connection conn)throws SQLException, ClassNotFoundException{
        try{
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put("AVAILABILITY_WAREHOUSE_TYP", Class.forName("sqlproject.WarehouseAvailability"));
            WarehouseAvailability warehouseAvailToAdd = new WarehouseAvailability (this.productId,this.warehouseId,this.warehouseQty );
            String sql = "{call add_package.add_warehouse_avail(?)}";
            try(CallableStatement stmt = conn.prepareCall(sql)){
                stmt.setObject (1, warehouseAvailToAdd);
                stmt.execute();
            }
        }
        catch (SQLException e) {
            System.out.println("Error Occured in addWarehouseAvailToDatabase WarehouseAvail");
        }
        catch (ClassNotFoundException a ){
            System.out.println("Error Occured (ClassNotFoundExpection) for WarehouseAvail");
        }   
    }

    /**
     * Gets the SQL type name for the WarehouseAvailability class.
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
     * Writes the WarehouseAvailability object to a SQL output stream.
     *
     * @param stream         The SQL output stream.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getProductId());
        stream.writeInt(getWarehouseId());
        stream.writeInt(getWarehouseQty());
    }

    /**
     * Reads the WarehouseAvailability object from a SQL input stream.
     *
     * @param stream         The SQL input stream.
     * @param typeName       The SQL type name.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        setProductId(stream.readInt());
        setWarehouseId(stream.readInt());
        setWarehouseQty(stream.readInt());
    }
}