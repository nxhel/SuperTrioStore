CREATE OR REPLACE PACKAGE find_id_package AS
   PROCEDURE FIND_PRODUCT_ID_BY_NAME(p_productName IN VARCHAR2, p_productId OUT NUMBER);
   PROCEDURE FIND_STORE_ID_BY_NAME(s_storeName IN VARCHAR2, s_storeId OUT NUMBER);
   PROCEDURE FIND_CITY_ID_BY_NAME(c_city IN VARCHAR2, c_cityId OUT NUMBER);
   PROCEDURE FIND_CUSTOMER_ID_BY_EMAIL(c_email IN VARCHAR2, c_customerid OUT NUMBER);
   FUNCTION getProductId(vproductName VARCHAR2) RETURN NUMBER;
   FUNCTION getCustomerId(vemail VARCHAR2) RETURN NUMBER;
   FUNCTION getWarehouseId(vwarehouseName VARCHAR2) RETURN NUMBER;
   FUNCTION getTotalPrice(p_productid NUMBER, qty NUMBER) RETURN NUMBER;
   productName_not_found EXCEPTION;
   store_not_found EXCEPTION;
   city_not_found EXCEPTION;
   warehouseName_not_found EXCEPTION;
   email_not_found EXCEPTION;
   price_not_found EXCEPTION;
END find_id_Package;
/
CREATE OR REPLACE PACKAGE BODY find_id_package AS

    PROCEDURE FIND_PRODUCT_ID_BY_NAME(p_productName IN VARCHAR2, p_productId OUT NUMBER) AS
        BEGIN
            SELECT productId INTO p_productId
            FROM Product
            WHERE LOWER(productName) = LOWER(p_productName);
            IF p_productId IS NULL THEN
                RAISE productName_not_found;
            END IF;
            COMMIT;
        END FIND_PRODUCT_ID_BY_NAME;
        
    PROCEDURE FIND_STORE_ID_BY_NAME(s_storeName IN VARCHAR2, s_storeId OUT NUMBER) AS
        BEGIN
            SELECT storeid INTO s_storeId
            FROM Store
            WHERE LOWER(storeName) = LOWER(s_storeName);
            IF s_storeId IS NULL THEN
                RAISE store_not_found;
            END IF;
            COMMIT;
        END FIND_STORE_ID_BY_NAME;
        
    PROCEDURE FIND_CITY_ID_BY_NAME(c_city IN VARCHAR2, c_cityId OUT NUMBER) AS
        BEGIN
            SELECT cityid INTO c_cityId
            FROM City
            WHERE LOWER(city) = LOWER(c_city);
            IF c_cityId IS NULL THEN
                RAISE city_not_found;
            END IF;
            COMMIT;
        END FIND_CITY_ID_BY_NAME;
    
    PROCEDURE FIND_CUSTOMER_ID_BY_EMAIL(c_email IN VARCHAR2, c_customerid OUT NUMBER) AS 
        BEGIN
            SELECT customerid INTO c_customerid
            FROM Customer
            WHERE LOWER(email) = LOWER(c_email);
            IF c_customerid IS NULL THEN
                RAISE email_not_found;
            END IF;
            COMMIT;
        END FIND_CUSTOMER_ID_BY_EMAIL;
        
--this section is primarily functions, use procedure or function according to preference of coder
    FUNCTION getProductId(vproductName VARCHAR2)
        RETURN NUMBER IS
            vproductID NUMBER;
        BEGIN
            SELECT ProductId INTO vproductID FROM Product WHERE LOWER(ProductName)=LOWER(vproductName);
            RETURN(vproductID);
            IF vproductID IS NULL THEN
                RAISE productName_not_found;
            END IF;
            COMMIT;
            EXCEPTION 
                WHEN productName_not_found THEN
                dbms_output.put_line('The product name does not exist!');
        END getProductId;

    FUNCTION getWarehouseId(vwarehouseName VARCHAR2) 
        RETURN NUMBER IS
            vwarehouseId NUMBER;
        BEGIN
            SELECT WarehouseId INTO vwarehouseId FROM Warehouse WHERE LOWER(WarehouseName) = LOWER(vwarehouseName);
            IF vwarehouseId IS NULL THEN
                RAISE warehouseName_not_found;
            END IF;
            RETURN(vwarehouseId);
            COMMIT;
            EXCEPTION 
                WHEN warehouseName_not_found THEN
                dbms_output.put_line('The warehouse name does not exist!');
        END getWarehouseId;
    
    FUNCTION getCustomerId(vemail VARCHAR2) 
        RETURN NUMBER IS
            vcustomerId NUMBER;
        BEGIN
            SELECT CustomerId INTO vcustomerId FROM Customer WHERE LOWER(Email) = LOWER(vemail);
            IF vcustomerId IS NULL THEN
                RAISE email_not_found;
            END IF;
            RETURN(vcustomerId);
            COMMIT;
            EXCEPTION 
                WHEN email_not_found THEN
                dbms_output.put_line('The email of the customer does not exist!');
        END getCustomerId;
        
    FUNCTION getTotalPrice(p_productid NUMBER, qty NUMBER) 
        RETURN NUMBER IS
            productPrice NUMBER(8,2);
            totalPrice NUMBER(8.2);
        BEGIN
            SELECT price INTO productPrice FROM Product WHERE productid = p_productid;
            totalPrice := productPrice*qty;
            IF productPrice IS NULL THEN
                RAISE price_not_found;
            END IF;
            RETURN(totalPrice);
            COMMIT;
        END getTotalPrice;
    
END find_id_package;
