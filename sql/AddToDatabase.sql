CREATE OR REPLACE PACKAGE add_package AS
   PROCEDURE add_address( vaddress IN address_typ );
   PROCEDURE add_customer( vcustomer IN customer_typ );
   PROCEDURE add_product (newProduct IN product_typ);
   PROCEDURE add_review (newReview IN review_typ);
   PROCEDURE add_order( vorder IN orders_typ );
   PROCEDURE add_order_toExistingCustomer( vorder IN orders_typ );
   PROCEDURE add_warehouse_avail (newWarehouseAvail IN availability_Warehouse_typ);
END add_Package;
/
CREATE OR REPLACE PACKAGE BODY add_package AS
    PROCEDURE add_address( vaddress IN address_typ ) IS
        BEGIN
            INSERT INTO Address (street, cityid, Country) VALUES(
                vaddress.street,
                vaddress.cityid,
                vaddress.Country);
        COMMIT;
        END add_address;  
        
    PROCEDURE add_customer( vcustomer IN customer_typ ) IS
        v_address_id NUMBER(4);
        BEGIN
            SELECT max(addressid) INTO v_address_id FROM Address;
                INSERT INTO Customer(firstname,lastname,email,addressId)VALUES(
                    vcustomer.firstname,
                    vcustomer.lastname,
                    vcustomer.email,
                    v_address_id);
        END add_customer;  

    PROCEDURE add_product (newProduct IN product_typ)IS
        BEGIN
            INSERT INTO PRODUCT ( productName, productCategory, price ) VALUES (
                newProduct.productName,
                newProduct.productCategory,
                newProduct.price);
        END add_product;
        
    PROCEDURE add_store( vstore IN store_typ ) IS
        BEGIN
            INSERT INTO Store VALUES(
                vstore.storeId,
                vstore.storeName);
        END add_store;  
        
    PROCEDURE add_review (newReview IN review_typ) IS
        BEGIN
            INSERT INTO REVIEW (productId,customerId,rating,review_flag,Description ) VALUES (
                newReview.ProductId,
                newReview.CustomerId,
                newReview.Rating,
                newReview.Review_flag,
                newReview.Description);
        END add_review;
        
    PROCEDURE add_order( vorder IN orders_typ ) IS
        v_customer_id NUMBER(4);
        BEGIN
            SELECT max(customerid) INTO v_customer_id FROM Customer;
            INSERT INTO orders (customerId, productId, orderDate, product_qty, theStoreId, price_order) VALUES(
                v_customer_id,
                vorder.productId,
                SYSDATE,
                vorder.product_qty,
                vorder.theStoreId,
                vorder.price_order);
        END add_order;  
    
    PROCEDURE add_order_toExistingCustomer( vorder IN orders_typ ) IS
        BEGIN
            INSERT INTO orders (customerId, productId, orderDate, product_qty, theStoreId, price_order) VALUES(
                vorder.customerId,
                vorder.productId,
                SYSDATE,
                vorder.product_qty,
                vorder.theStoreId,
                vorder.price_order);
        END add_order_toExistingCustomer;  

    PROCEDURE add_warehouse (newWarehouse IN warehouse_typ) IS
        BEGIN
            INSERT INTO WAREHOUSE (WarehouseName,AddressId) VALUES(
                newWarehouse.WarehouseName,
                newWarehouse.AddressId);
        END add_warehouse;

    PROCEDURE add_warehouse_avail (newWarehouseAvail IN availability_Warehouse_typ) IS
        BEGIN
            INSERT INTO Availability_Warehouse_Product (productId,WarehouseId,warehouse_qty) VALUES(
                newWarehouseAvail.productId,
                newWarehouseAvail.WarehouseId,
                newWarehouseAvail.warehouse_qty);
        END add_warehouse_avail;

END add_package;