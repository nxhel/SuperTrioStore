CREATE OR REPLACE PACKAGE update_package AS
    --Procedures
    PROCEDURE updateProduct(vproductId NUMBER, vproductName VARCHAR2,vproductCategory VARCHAR2, vproductPrice NUMBER);
    PROCEDURE updateInventory(vproductId NUMBER, vwarehouseId NUMBER, newQuantity NUMBER);
    PROCEDURE updateReview(vproductId NUMBER, vcustomerId NUMBER, vrating NUMBER, newDescription VARCHAR2);
    PROCEDURE updateFlaggedReview(vreviewId NUMBER,newDescription VARCHAR2);
    --Function
    FUNCTION getProductId(vproductName VARCHAR2) RETURN NUMBER;
    FUNCTION validateProductName ( vproductName VARCHAR2) RETURN NUMBER ;
    FUNCTION getWarehouseId(vwarehouseName VARCHAR2) RETURN NUMBER;
    FUNCTION getCustomerId(vemail VARCHAR2) RETURN NUMBER;
    --Exceptions
    email_not_found EXCEPTION;
    warehouseName_not_found EXCEPTION;
    productName_not_found EXCEPTION;
END update_package;
/
CREATE OR REPLACE PACKAGE BODY update_package AS
    PROCEDURE updateProduct(vproductId NUMBER, vproductName VARCHAR2,vproductCategory VARCHAR2, vproductPrice NUMBER)
        AS
        BEGIN
            IF NOT(vproductName='NULL') THEN
                UPDATE Product SET ProductName=vproductName WHERE ProductId=vproductId;
            END IF;
            IF NOT(vproductCategory='NULL') THEN
                UPDATE Product SET ProductCategory=vproductCategory WHERE ProductId=vproductId;
            END IF; 
            IF NOT(vproductPrice=0) THEN
                UPDATE Product SET Price=vproductPrice WHERE ProductId=vproductId;
            END IF; 
        COMMIT;
    END updateProduct;

    FUNCTION validateProductName ( vproductName VARCHAR2) 
        RETURN NUMBER IS
            countOfExistantProducts NUMBER;
        BEGIN
            SELECT COUNT(ProductId) INTO countOfExistantProducts FROM Product WHERE ProductName =vproductName;
        RETURN countOfExistantProducts;
    END validateProductName;

    FUNCTION getProductId(vproductName VARCHAR2)
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

    PROCEDURE updateInventory(vproductId NUMBER, vwarehouseId NUMBER, newQuantity NUMBER)
        AS
        BEGIN
            UPDATE Availability_Warehouse_Product SET Warehouse_qty = newQuantity  WHERE ProductId=vproductId AND WarehouseId = vwarehouseId;
        COMMIT;
    END updateInventory;

    FUNCTION getWarehouseId(vwarehouseName VARCHAR2) 
        RETURN NUMBER IS
            vwarehouseId NUMBER;
        BEGIN
            SELECT WarehouseId INTO vwarehouseId FROM Warehouse WHERE WarehouseName = vwarehouseName;
            IF vwarehouseId IS NULL THEN
                RAISE warehouseName_not_found;
            END IF;
        RETURN(vwarehouseId);
        EXCEPTION 
            WHEN warehouseName_not_found THEN
            dbms_output.put_line('The warehouse name doesnt exist!');
    END getWarehouseId;

    PROCEDURE updateReview(vproductId NUMBER, vcustomerId NUMBER, vrating NUMBER, newDescription VARCHAR2)
        AS
            numberCurrentFlagReview NUMBER;
            updatedNumberFlagReview NUMBER;
        BEGIN
            IF(newDescription = 'NULL') THEN
                SELECT Review_flag INTO numberCurrentFlagReview FROM Review WHERE (ProductId=vproductId AND CustomerId = vcustomerId) AND Rating=vrating;
                updatedNumberFlagReview := numberCurrentFlagReview +1;
                UPDATE Review SET Review_flag = updatedNumberFlagReview WHERE (ProductId=vproductId AND CustomerId = vcustomerId) AND Rating=vrating;
            ELSE
                UPDATE Review SET Description = newDescription  WHERE (ProductId=vproductId AND CustomerId = vcustomerId) AND Rating=vrating;
            END IF;
            COMMIT;
    END updateReview;
    
    PROCEDURE updateFlaggedReview(vreviewId NUMBER,newDescription VARCHAR2)
    AS
        wippingFlaggedReview NUMBER;
    BEGIN
        wippingFlaggedReview:=0;
        UPDATE Review SET Review_flag = wippingFlaggedReview WHERE ReviewId=vreviewId;
        UPDATE Review SET Description = newDescription WHERE ReviewId=vreviewId;
        COMMIT;
    END updateFlaggedReview;

    FUNCTION getCustomerId (vemail VARCHAR2) 
        RETURN NUMBER IS
            vcustomerId NUMBER;
    BEGIN
        SELECT CustomerId INTO vcustomerId FROM Customer WHERE Email = vemail;
        IF vcustomerId IS NULL THEN
            RAISE email_not_found;
        END IF;
        RETURN(vcustomerId);
        COMMIT;
        EXCEPTION 
            WHEN email_not_found THEN
            dbms_output.put_line('The email of the customer doesnt exist!');
    END getCustomerId;
END update_package;



