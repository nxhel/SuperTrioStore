package sqlproject;

import java.sql.*;
import java.util.Map;

/**
 * The Store class represents a store entity in the SuperTrio Store Database System.
 */
public class Store implements SQLData{
    // FIELDS
    public static final String TYPENAME = "STORE_TYP";
    private int storeId;
    private String storeName;

    /**
     * Constructor of Store object with the specified parameters.
     *
     * @param storeId   The ID of the store.
     * @param storeName The name of the store.
     */
    public Store(int storeId, String storeName){
        this.storeId = storeId;
        this.storeName = storeName;
    }

    // Getters and setters
    public int getStoreId(){
        return this.storeId;
    }

    public void setStoreId(int storeId){
        this.storeId = storeId;
    }

    public String getStoreName(){
        return this.storeName;
    }

    public void setStoreName(String storeName){
        this.storeName = storeName;
    }

    /**
     * Gets the SQL type name for the Store class.
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
     * Writes the Store object to a SQL output stream.
     *
     * @param stream         The SQL output stream.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getStoreId());
        stream.writeString(getStoreName());
    }

    /**
     * Reads the Store object from a SQL input stream.
     *
     * @param stream         The SQL input stream.
     * @param typeName       The SQL type name.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        setStoreId(stream.readInt());
        setStoreName(stream.readString());
    }

    /**
     * Adds the store to the database.
     *
     * @param conn The database connection.
     * @throws SQLException            If a database access error occurs.
     * @throws ClassNotFoundException  If the class is not found.
     */
    public void addToDatabase(Connection conn)throws SQLException, ClassNotFoundException{
        try{
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put("STORE_TYP", Class.forName("sqlproject.Store"));
            Store store = new Store(this.storeId, this.storeName);
            String sql = "{call add_package.add_store(?)}";
            try(CallableStatement stmt = conn.prepareCall(sql)){
                stmt.setObject (1, store);
                stmt.execute();
            }
        }
        catch (SQLException e){
            System.out.println("Map problem!");
        }
        catch (ClassNotFoundException a ){
            System.out.println("Class store not found!");
        }
    }
}
