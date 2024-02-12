CREATE TABLE AuditHistory(
    audit_id NUMBER(4) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Table_name VARCHAR2(30) NOT NULL,
    changeDate DATE,
    action VARCHAR2(6)
);
/
CREATE OR REPLACE TRIGGER TrackProducts
    AFTER INSERT OR UPDATE OR DELETE
    ON Product
    FOR EACH ROW
    BEGIN
        IF INSERTING THEN
            --SINCE WE ARE ADDING THERE IS NO OLD VALUE
            INSERT INTO AuditHistory(Table_name, changeDate, action)
            VALUES('Product', SYSDATE, 'INSERT');

            ELSIF UPDATING THEN
                INSERT INTO AuditHistory(Table_name, changeDate, action)
                VALUES('Product', SYSDATE, 'UPDATE');

            ELSE
                --SINCE WE ARE DELETING THERE IS NO NEW VALUES
                INSERT INTO AuditHistory(Table_name, changeDate, action)
                VALUES('Product', SYSDATE, 'DELETE');
            END IF;
            COMMIT;
        EXCEPTION
            WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error occured while modifying the PRODUCT table!');
    END;
/

CREATE OR REPLACE TRIGGER TrackAddress
    AFTER INSERT OR UPDATE OR DELETE
    ON Address
    FOR EACH ROW
    BEGIN
    IF INSERTING THEN
        --SINCE WE ARE ADDING THERE IS NO OLD VALUE
        INSERT INTO AuditHistory(Table_name, changeDate, action)
        VALUES('Address', SYSDATE, 'INSERT');

    ELSIF UPDATING THEN 
        INSERT INTO AuditHistory(Table_name, changeDate, action)
        VALUES('Address', SYSDATE, 'UPDATE');

    ELSE
        --SINCE WE ARE DELETING THERE IS NO NEW VALUES
        INSERT INTO AuditHistory(Table_name, changeDate, action)
        VALUES('Address', SYSDATE, 'DELETE');
    END IF;
    COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occured while modifying the ADDRESS table!');
    END;
/
CREATE OR REPLACE TRIGGER TrackOrder
    AFTER INSERT OR UPDATE OR DELETE
    ON Orders
    FOR EACH ROW
    BEGIN
    IF INSERTING THEN
        --SINCE WE ARE ADDING THERE IS NO OLD VALUE
        INSERT INTO AuditHistory(Table_name, changeDate, action)
        VALUES('Orders', SYSDATE, 'INSERT');
    ELSIF UPDATING THEN
        INSERT INTO AuditHistory(Table_name, changeDate, action)
        VALUES('Orders', SYSDATE, 'UPDATE');
    ELSE
        --SINCE WE ARE DELETING THERE IS NO NEW VALUES
        INSERT INTO AuditHistory(Table_name, changeDate, action)
        VALUES('Orders', SYSDATE, 'DELETE');
    END IF;
    COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occured while modifying the ORDERS table!');
    END;
/

CREATE OR REPLACE TRIGGER TrackCustomers
    AFTER INSERT OR UPDATE OR DELETE
    ON Customer
    FOR EACH ROW
    BEGIN
        IF INSERTING THEN
        --SINCE WE ARE ADDING THERE IS NO OLD VALUE
        INSERT INTO AuditHistory(Table_name, changeDate, action)
        VALUES('Customer', SYSDATE, 'INSERT');
    ELSIF UPDATING THEN
        INSERT INTO AuditHistory(Table_name, changeDate, action)
        VALUES('Customer', SYSDATE, 'UPDATE');
    ELSE
        --SINCE WE ARE DELETING THERE IS NO NEW VALUES
        INSERT INTO AuditHistory(Table_name, changeDate, action)
        VALUES('Customer', SYSDATE, 'DELATE');
    END IF;
    COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occured while modifying the CUSTOMER table!');
    END;

