package sqlproject;

import java.sql.*;
import java.util.Map;

/**
 * The Review class represents a review entity in the SuperTrio Store Database System.
 */
public class Review implements SQLData{
    // FIELDS
    public static final String TYPENAME = "REVIEW_TYP";
    private int reviewId;
    private int productId;
    private int customerId;
    private int rating;
    private int reviewFlag;
    private String description;

    /**
     * Constructor for Review object with the specified parameters.
     *
     * @param productId    The ID of the product being reviewed.
     * @param customerId   The ID of the customer submitting the review.
     * @param rating       The rating given in the review.
     * @param reviewFlag   The flag indicating if the review is flagged.
     * @param description  The description or content of the review.
     */
    public Review(int productId, int customerId, int rating, int reviewFlag, String description){
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.reviewFlag = reviewFlag;
        this.description = description;
    }

    // getters and setters
    public int getReviewId(){
        return this.reviewId;
    }

    public void setReviewId(int reviewId){
        this.reviewId = reviewId;
    }

    public int getProductId(){
        return this.productId;
    }

    public void setProductId(int productId){
        this.productId = productId;
    }

    public int getCustomerId(){
        return this.customerId;
    }

    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    public int getRating(){
        return this.rating;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public int getReviewFlag(){
        return this.reviewFlag;
    }

    public void setReviewFlag(int reviewFlag){
        this.reviewFlag = reviewFlag;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Adds the review to the database.
     *
     * @param conn                      The database connection.
     * @throws SQLException             If a database access error occurs.
     * @throws ClassNotFoundException   If the class is not found.
     */
    public void addReviewToDatabase(Connection conn)throws SQLException, ClassNotFoundException{
        try{
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put("REVIEW_TYP", Class.forName("sqlproject.Review"));
            Review reviewToAdd = new Review( this.productId, this.customerId, this.rating, this.reviewFlag, this.description);
            String sql = "{call add_package.add_review(?)}";
            try(CallableStatement stmt = conn.prepareCall(sql)){
                stmt.setObject (1, reviewToAdd);
                stmt.execute();
            }
        }
        catch (SQLException e){
            System.out.println("Error Occured in AddToDatabase Review");
        }
        catch (ClassNotFoundException a ){
            System.out.println("Error Occured (ClassNotFoundExpection) for Review");
        }   
    }

    /**
     * Gets the SQL type name for the Review class.
     *
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
     * Writes the Review object to a SQL output stream
     *
     * @param stream The SQLOutput stream.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(getReviewId());
        stream.writeInt(getProductId());
        stream.writeInt(getCustomerId());
        stream.writeInt(getRating());
        stream.writeInt(getReviewFlag());
        stream.writeString(getDescription());
    }

    /**
     * Reads the Review object from a SQL input stream.
     *
     * @param stream         The SQL input stream.
     * @param typeName       The SQL type name.
     * @throws SQLException  If a SQL error occurs.
     */
    @Override
    public void readSQL (SQLInput stream, String typeName)throws SQLException
    {
        setProductId(stream.readInt());
        setCustomerId(stream.readInt());
        setRating(stream.readInt());
        setReviewFlag(stream.readInt());
        setDescription(stream.readString());
    }
}
