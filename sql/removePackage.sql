CREATE OR REPLACE PACKAGE removePackage AS

    PROCEDURE removeProduct(theProductName product.productName%TYPE);
    PROCEDURE removeWarehouse(theWarehouseName warehouse.warehousename%TYPE);
    PROCEDURE removeCustomer (thecustomerEmail customer.email%TYPE);
    PROCEDURE removeOrder (theOrderId orders.OrderId%TYPE);
    PROCEDURE removeReview (theReviewId review.ReviewId%TYPE);
    
END removePackage;
/
CREATE OR REPLACE PACKAGE BODY removePackage AS

    PROCEDURE removeReview (theReviewId review.ReviewId%TYPE) AS
        BEGIN 
            DELETE FROM Review where reviewId=theReviewId;
            COMMIT;
        END removeReview;
        
    PROCEDURE removeProduct(theProductName product.productName%TYPE) AS
        BEGIN 
            DELETE FROM Product where productName=theProductName;
            COMMIT;
        END removeProduct;
    
    PROCEDURE removeWarehouse(theWarehouseName warehouse.warehousename%TYPE) AS
        BEGIN
            DELETE FROM warehouse where warehousename=theWarehouseName;
            COMMIT;
        END removeWarehouse;
        
    PROCEDURE removeOrder (theOrderId orders.orderId%TYPE) AS
        BEGIN
            DELETE FROM orders where orderid=theOrderId;
            COMMIT;
        END removeOrder;
        
    PROCEDURE removeCustomer (thecustomerEmail customer.email%TYPE) AS
        BEGIN
            DELETE FROM customer WHERE email=thecustomerEmail;
            COMMIT;
        END removeCustomer;
        
END removePackage ;
/




