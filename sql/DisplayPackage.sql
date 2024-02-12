CREATE OR REPLACE PACKAGE displayPackage AS
---theTYPE
    TYPE products_array IS VARRAY(100) OF product.productName%TYPE;
--The following procedures return a cursor that will be used in java to print the Tablecolumns.
    PROCEDURE DisplayOrdersByCustomers(theCustomerEmail IN customer.email%TYPE,outputCursor OUT SYS_REFCURSOR);
    PROCEDURE DisplayAuditTable (outputCursor OUT SYS_REFCURSOR);
    PROCEDURE ShowInventory(outputCursor OUT SYS_REFCURSOR);
    PROCEDURE ShowInventoryTotal(outputCursor OUT SYS_REFCURSOR);
    PROCEDURE getFlaggedReviews(FlaggedReviews OUT SYS_REFCURSOR);
    PROCEDURE getFlaggedCustomers(flaggedCustomers OUT SYS_REFCURSOR);
--The following functions return either a calculated number or an array of userDefined Type
    FUNCTION product_ByCategory(productCat IN product.productCategory%TYPE) RETURN products_array;
    FUNCTION reviewScore_avg (theProductName IN product.productName%TYPE) RETURN NUMBER;
    FUNCTION CountProductOrdered(theProductName IN product.productName%TYPE) RETURN NUMBER;
---Exceptions
    category_not_found EXCEPTION;
    product_not_found EXCEPTION;
    product_not_ordered EXCEPTION;
END displayPackage;
/
CREATE OR REPLACE PACKAGE BODY displayPackage AS
     
    PROCEDURE DisplayOrdersByCustomers(theCustomerEmail IN customer.email%TYPE,outputCursor OUT SYS_REFCURSOR) IS
        BEGIN 
            OPEN outputCursor FOR
                SELECT p.productName , o.product_qty, s.storeName , o.price_order ,o.orderDate
                FROM ORDERS o
                INNER JOIN STORE s ON o.thestoreId = s.storeId
                JOIN PRODUCT p  ON o.productId = p.productId
                JOIN CUSTOMER c ON o.customerId=c.customerId
                WHERE c.email = theCustomerEmail;
    END DisplayOrdersByCustomers;
    
    PROCEDURE DisplayAuditTable (outputCursor OUT SYS_REFCURSOR) IS
        BEGIN 
            OPEN outputCursor FOR
                SELECT * FROM  AuditHistory;
        END DisplayAuditTable;
        
    PROCEDURE ShowInventory (outputCursor OUT SYS_REFCURSOR) AS
        BEGIN
            OPEN outputCursor FOR
            SELECT p.productName, i.warehouseId, i.warehouse_qty
            FROM product p
            JOIN Availability_Warehouse_Product i ON p.productId = i.productId;
    END ShowInventory;

    PROCEDURE ShowInventoryTotal (outputCursor OUT SYS_REFCURSOR) AS
        BEGIN
            OPEN outputCursor FOR
                SELECT p.productName, SUM(i.warehouse_qty) AS total_quantity
                FROM product p
                JOIN Availability_Warehouse_Product i ON p.productId = i.productId
                GROUP BY p.productName;
    END ShowInventoryTotal;
    
    PROCEDURE getFlaggedReviews(FlaggedReviews OUT SYS_REFCURSOR) IS 
        BEGIN 
            OPEN FlaggedReviews FOR 
                SELECT reviewId , r.Description, c.FIRSTNAME
                FROM REVIEW R
                JOIN CUSTOMER c  ON c.customerId = r.customerId
                WHERE r.Review_flag > 0;
    END getFlaggedReviews;
    
    PROCEDURE getFlaggedCustomers(flaggedCustomers OUT SYS_REFCURSOR) IS 
    BEGIN 
        OPEN flaggedCustomers FOR
            SELECT DISTINCT c.FIRSTNAME
            FROM CUSTOMER c
            JOIN REVIEW r ON c.customerId = r.customerId
            WHERE r.Review_flag > 0;
    END getFlaggedCustomers;
    
    
    FUNCTION product_ByCategory(productCat IN product.productCategory%TYPE) RETURN products_array IS
        productArray products_array;
        category_match NUMBER := 0;
            BEGIN
                SELECT COUNT(*) INTO category_match FROM PRODUCT WHERE productCategory = productCat;
                IF category_match = 0 THEN 
                    RAISE category_not_found;
                END IF;
                
                SELECT productName BULK COLLECT INTO productArray
                FROM PRODUCT
                WHERE productCategory = productCat;
                
                RETURN productArray;
                
                EXCEPTION
                WHEN category_not_found THEN
                    dbms_output.put_line( 'Category Not Found' ); 
                WHEN OTHERS THEN
                    dbms_output.put_line( 'An Error Has Occured' ); 
    END product_ByCategory;
    
    FUNCTION reviewScore_avg (theProductName IN product.productName%TYPE) RETURN NUMBER IS
        averageScore NUMBER ;
        theProdId product.productId%TYPE;
            BEGIN
                SELECT productId INTO theProdId FROM PRODUCT WHERE productName = theProductName;
                IF theProdId IS NULL THEN 
                    RAISE product_not_found;
                END IF;
                
                SELECT NVL(AVG(rating), 0) INTO averageScore FROM REVIEW WHERE PRODUCTID = theProdId;
                RETURN averageScore;
                
                EXCEPTION
                    WHEN product_not_found THEN
                        dbms_output.put_line( 'Product Not Found' ); 
                    WHEN OTHERS THEN
                    dbms_output.put_line( 'Error Has Occured' ); 
    END reviewScore_avg;
    
    FUNCTION CountProductOrdered(theProductName IN product.productName%TYPE) RETURN NUMBER
        IS countProduct NUMBER;
        theProdId product.productId%TYPE;
        
        BEGIN 
            SELECT productId INTO theProdId FROM PRODUCT WHERE productName = theProductName;
                IF theProdId IS NOT NULL THEN 
                   SELECT COUNT(*) INTO countProduct FROM ORDERS WHERE productId=theProdId;
                END IF;
                
            RETURN countProduct;
            
            EXCEPTION WHEN NO_DATA_FOUND THEN
                RETURN 0;
        END CountProductOrdered;
END displayPackage;
/

