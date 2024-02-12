package sqlproject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Utility class for validating various entities in the SQLProject.
 * This class provides methods for validating product names, city names, quantities, warehouse names,
 * store names, emails, product existence in a warehouse, review existence, product categories, order IDs,
 * and review IDs using stored procedures in the database.
 */
public class ValidateServices {

    /**
     * Validates if the product with the specific name exists.
     * 
     * @param productName   The name of the product to be validated.
     * @param conn          The database connection.
     * @return              True if the product name is valid (unique), false otherwise.
     */
    public static boolean validateProductName(String productName,Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateProductName(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, productName);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates if the City with the specific name exist.
     * 
     * @param cityName   The name of the city to be validated.
     * @param conn       The database connection.
     * @return           True if the city name is valid (unique), false otherwise.
     */
    public static boolean validateCityName(String cityName,Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateCityName(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, cityName);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates if the City with the specific name exist.
     * 
     * @param productId             The productId to be validated.
     * @param numberWantedProducts  The qty of product to be validated.
     * @param conn                  The database connection.
     * @return                      True if the qty of product to order is available.
     */
    public static boolean validateQuantity(int productId, int numberWantedProducts, Connection conn) {
        int quantity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateQuantity(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, productId);
            stmt.execute();
            quantity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if (quantity < numberWantedProducts) {
            return false;
        } else {
            return true;
        }
    }
    
    // idem with differnt params
    public static boolean validateWarehouseName(String warehouseName, Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateWarehouseName(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, warehouseName);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }

    // idem with differnt params
    public static boolean validateStoreName(String storeName, Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateStoreName(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, storeName);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }
    
    // idem with differnt params
    public static boolean validateEmail(String email, Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateEmail(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, email);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }

     /**
     * Validates the existence of a product in a warehouse based on product and warehouse IDs.
     * 
     * @param productId   The ID of the product.
     * @param warehouseId The ID of the warehouse.
     * @param conn        The database connection.
     * @return            True if the product exists in the warehouse, false otherwise.
     */
    public static boolean validateIfProductExistInWarehouse(int productId,int warehouseId, Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateIfProductExistInWarehouse(?,?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, productId);
            stmt.setInt(3, warehouseId);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates the existence of a review based on product, customer, and rating information.
     * 
     * @param productId  The ID of the product.
     * @param customerId The ID of the customer.
     * @param rating     The rating given in the review.
     * @param conn       The database connection.
     * @return           True if the review exists, false otherwise.
     */
    public static boolean validateIfReviewExist(int productId,int customerId, int rating, Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateIfReviewExist(?,?,?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, productId);
            stmt.setInt(3, customerId);
            stmt.setInt(4, rating);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }

    // idem with differnt params
    public static boolean validateProductCategory(String productCategory,Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateProductCategory(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, productCategory);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }

    // idem with different params
    public static boolean validateOrderId(int theOrderId,Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateOrderId(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, theOrderId);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }

    // idem with differen params
    public static boolean validateReviewId( int theOrderId,Connection conn) {
        int validity = 0;
        try {
            CallableStatement stmt = conn.prepareCall("{? = call validatePackage.validateReviewId(?)}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, theOrderId);
            stmt.execute();
            validity = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (validity == 0) {
            return false;
        } else {
            return true;
        }
    }
}
