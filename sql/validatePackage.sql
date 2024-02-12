CREATE OR REPLACE PACKAGE validatePackage AS
    FUNCTION validateProductName ( vproductName VARCHAR2) RETURN NUMBER ;
    FUNCTION validateCityName ( vCityName VARCHAR2) RETURN NUMBER;
    FUNCTION validateProductCategory (theProductCategory IN product.productCategory%TYPE ) RETURN NUMBER ;
    FUNCTION ValidateQuantity(vproductId NUMBER) RETURN NUMBER;
    FUNCTION validateWarehouseName ( vwarehouseName VARCHAR2) RETURN NUMBER ;
    FUNCTION validateEmail (vemail VARCHAR2) RETURN NUMBER ;
    FUNCTION validateStoreName ( vstoreName VARCHAR2) RETURN NUMBER;
    FUNCTION validateOrderId (theOrderId IN orders.orderId%TYPE ) RETURN NUMBER ;
    FUNCTION validateReviewId (theReviewId IN orders.orderId%TYPE ) RETURN NUMBER ;
    productName_not_found EXCEPTION;
    FUNCTION validateIfProductExistInWarehouse(vproductId NUMBER, vwarehouseId NUMBER) RETURN NUMBER;
    FUNCTION validateIfReviewExist(vproductId NUMBER, vcustomerId NUMBER, vrating NUMBER) RETURN NUMBER;
END validatePackage;
/
CREATE OR REPLACE PACKAGE BODY validatePackage AS
---------------------------------------------------------------------------------
----------------------P R O D U C T    R  E L A  T E D ---------------------------

    FUNCTION getProductId(vproductName VARCHAR2) --This already exists in FindInDatabase
    RETURN NUMBER IS
    vproductID NUMBER;
        BEGIN
            SELECT ProductId INTO vproductID FROM Product WHERE ProductName=vproductName;
        RETURN(vproductID);
    
        IF vproductID IS NULL THEN
            RAISE productName_not_found;
        END IF;
        COMMIT;
        EXCEPTION 
        WHEN productName_not_found THEN
            dbms_output.put_line('The product name doesnt exist!');
        END getProductId;

    FUNCTION validateProductName ( vproductName VARCHAR2) 
        RETURN NUMBER IS
            countOfExistantProducts NUMBER;
        BEGIN
            SELECT COUNT(ProductId) INTO countOfExistantProducts FROM Product WHERE LOWER(productName) = LOWER(vproductName);
            RETURN countOfExistantProducts;
        END validateProductName;

    FUNCTION validateCityName ( vCityName VARCHAR2) 
        RETURN NUMBER IS
            countOfExistantCity NUMBER;
        BEGIN
            SELECT COUNT(CityId) INTO countOfExistantCity FROM City WHERE LOWER(city) = LOWER(vCityName);
            RETURN countOfExistantCity;
        END validateCityName;
    
    FUNCTION validateProductCategory (theProductCategory IN product.productCategory%TYPE) 
        RETURN NUMBER IS
            countOfExistantProducts NUMBER;
        BEGIN
            SELECT COUNT(ProductId) INTO countOfExistantProducts FROM Product WHERE LOWER(productCategory) = LOWER(theProductCategory);
            RETURN countOfExistantProducts;
        END validateProductCategory;
    
    FUNCTION ValidateQuantity(vproductId NUMBER)
        RETURN NUMBER IS
            quantity_available NUMBER;
        BEGIN
            SELECT SUM(Warehouse_qty) INTO quantity_available FROM Availability_Warehouse_Product WHERE ProductId = vproductId;
            RETURN quantity_available;
        END ValidateQuantity;
    
    FUNCTION validateWarehouseName ( vwarehouseName VARCHAR2) 
        RETURN NUMBER IS
            countOfExistantWarehouses NUMBER;
        BEGIN
            SELECT COUNT(WarehouseId) INTO countOfExistantWarehouses FROM Warehouse WHERE LOWER(WarehouseName) = LOWER(vwarehouseName);
            RETURN countOfExistantWarehouses;
        END validateWarehouseName;

    FUNCTION validateStoreName ( vstoreName VARCHAR2) 
        RETURN NUMBER IS
            countOfExistantStore NUMBER;
        BEGIN
            SELECT COUNT(StoreId) INTO countOfExistantStore FROM Store WHERE LOWER(storename) = LOWER(vstoreName);
            RETURN countOfExistantStore;
        END validateStoreName;
    
    FUNCTION validateEmail( vemail VARCHAR2) 
        RETURN NUMBER IS
            countOfExistantCustomers NUMBER;
        BEGIN
            SELECT COUNT(CustomerId) INTO countOfExistantCustomers FROM Customer WHERE LOWER(Email) = LOWER(vemail);
            RETURN countOfExistantCustomers;
        END validateEmail;
    
    FUNCTION validateOrderId (theOrderId IN orders.orderId%TYPE ) 
        RETURN NUMBER IS
            countOfExistantWarehouses NUMBER;
        BEGIN
             SELECT COUNT(theOrderId) INTO countOfExistantWarehouses FROM ORDERS WHERE orderId =theOrderId;
            RETURN countOfExistantWarehouses;
        END;
        
    FUNCTION validateIfProductExistInWarehouse(vproductId NUMBER, vwarehouseId NUMBER)
        RETURN NUMBER IS
        validityCount NUMBER;
        BEGIN
            SELECT COUNT(ProductId) INTO validityCount FROM Availability_Warehouse_Product  WHERE ProductId=vproductId AND WarehouseId = vwarehouseId;
            RETURN validityCount;
        END validateIfProductExistInWarehouse;
        
    FUNCTION validateIfReviewExist(vproductId NUMBER, vcustomerId NUMBER, vrating NUMBER)
        RETURN NUMBER IS
        validityCount NUMBER;
        BEGIN
            SELECT COUNT(ReviewId) INTO validityCount FROM Review  WHERE (ProductId=vproductId AND CustomerId = vcustomerId) AND Rating=vrating;
            RETURN validityCount;
        END validateIfReviewExist;

    FUNCTION validateReviewId (theReviewId IN orders.orderId%TYPE )
        RETURN NUMBER IS
            countOfExistantProducts NUMBER;
         BEGIN
            SELECT COUNT(reviewId) INTO countOfExistantProducts FROM REVIEW WHERE reviewId = theReviewId;
            RETURN countOfExistantProducts;
        END validateReviewId;
END validatePackage;
/

