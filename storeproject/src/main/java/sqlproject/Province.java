package sqlproject;

import java.sql.*;

public class Province implements SQLData{
    public static final String TYPENAME = "PROVINCE_TYP";
    private int provinceId;
    private String province;

    public Province(int provinceId, String province){
        this.provinceId = provinceId;
        this.province = province;
    }

    public int getProvinceId(){
        return this.provinceId;
    }

    public void setProvinceId(int provinceId){
        this.provinceId = provinceId;
    }

    public String getProvince(){
        return this.province;
    }

    public void setProvince(String province){
        this.province = province;
    }


    @Override
    public String getSQLTypeName() throws SQLException
    {
        return TYPENAME;
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getProvinceId());
        stream.writeString(getProvince());
    }

    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        setProvinceId(stream.readInt());
        setProvince(stream.readString());
    }
}
