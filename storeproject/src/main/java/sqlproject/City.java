package sqlproject;

import java.sql.*;

/**
 * The City class represents a city in the SuperTrio Store Database System.
 * It implements the SQLData interface for custom mapping to SQL types.
 */
public class City implements SQLData{
    // FIELDS
    public static final String TYPENAME = "CITY_TYP";
    private int cityId;
    private String city;
    private int provinceId;

    /**
     * Constructor of City object with the specified attributes.
     *
     * @param cityId      The ID of the city.
     * @param city        The name of the city.
     * @param provinceId  The ID of the province to which the city belongs.
     */
    public City(int cityId, String city, int provinceId){
        this.cityId = cityId;
        this.city = city;
        this.provinceId = provinceId;
    }

    // setters and getters
    public int getCityId(){
        return this.cityId;
    }

    public void setCityId(int cityId){
        this.cityId = cityId;
    }

    public String getCity(){
        return this.city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public int getProvinceId(){
        return this.provinceId;
    }

    public void setProvinceId(int provinceId){
        this.provinceId = provinceId;
    }

    /**
     * Gets the SQL type name for the City class.
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
     * Writes the City object to a SQL output stream.
     *
     * @param stream         The SQL output stream.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getCityId());
        stream.writeString(getCity());
        stream.writeInt(getProvinceId());
    }

    /**
     * Reads the City object from a SQL input stream.
     *
     * @param stream         The SQL input stream.
     * @param typeName       The SQL type name.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        setCityId(stream.readInt());
        setCity(stream.readString());
        setProvinceId(stream.readInt());
    }
}