CREATE TABLE Province(
    ProvinceId NUMBER(4)    GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    Province   VARCHAR2(50) NOT NULL
);
INSERT INTO Province ( Province ) VALUES('Ontario');
INSERT INTO Province ( Province ) VALUES('Quebec');
INSERT INTO Province ( Province ) VALUES('Alberta');

CREATE TABLE City(
    CityId     NUMBER(4) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    City       VARCHAR2(50) NOT NULL,
    ProvinceId NUMBER(4) REFERENCES Province(ProvinceId) ON DELETE CASCADE 
);

INSERT INTO City ( City, ProvinceId ) VALUES('Toronto', (SELECT ProvinceId FROM province where(province='Ontario')));
INSERT INTO City ( City, ProvinceId ) VALUES('Montreal', (SELECT ProvinceId FROM province where(province='Quebec')));
INSERT INTO City ( City, ProvinceId ) VALUES('Calgary', (SELECT ProvinceId FROM province where(province='Alberta')));
INSERT INTO City ( City, ProvinceId ) VALUES('Brossard', (SELECT ProvinceId FROM province where(province='Quebec')));
INSERT INTO City ( City, ProvinceId ) VALUES('Laval', (SELECT ProvinceId FROM province where(province='Quebec')));
INSERT INTO City ( City, ProvinceId ) VALUES('Saint Laurent', (SELECT ProvinceId FROM province where(province='Quebec')));
INSERT INTO City ( City, ProvinceId ) VALUES('Quebec City', (SELECT ProvinceId FROM province where(province='Quebec')));
INSERT INTO City ( City, ProvinceId ) VALUES('Ottawa', (SELECT ProvinceId FROM province where(province='Ontario')));

CREATE TABLE Address (
    AddressId NUMBER(4)     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Street    VARCHAR2(50),
    CityId    NUMBER(4)     REFERENCES City(CityId) ON DELETE CASCADE,
    Country   VARCHAR2(50)  NOT NULL
);

CREATE OR REPLACE TYPE address_typ AS OBJECT(
    AddressId NUMBER(4),
    Street  VARCHAR2(50),
    CityId  NUMBER(4),
    Country  VARCHAR2(50)
);
/
INSERT INTO Address ( Street, CityId, Country ) VALUES('100 Atwater Street', (SELECT CityId FROM city where(city='Toronto')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('100 Young Street', (SELECT CityId FROM city where(city='Toronto')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('100 Saint Laurent', (SELECT CityId FROM city where(city='Montreal')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES(NULL, (SELECT CityId FROM city where(city='Calgary')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES(NULL, (SELECT CityId FROM city where(city='Brossard')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('104 Gill Street', (SELECT CityId FROM city where(city='Toronto')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('105 Young Street', (SELECT CityId FROM city where(city='Toronto')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('boul Saint Laurent', (SELECT CityId FROM city where(city='Montreal')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('87 boul Saint Laurent', (SELECT CityId FROM city where(city='Montreal')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('76 boul Decalthon', (SELECT CityId FROM city where(city='Laval')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('22222 Happy Street', (SELECT CityId FROM city where(city='Laval')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('Dawson College', (SELECT CityId FROM city where(city='Montreal')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('100 rue William', (SELECT CityId FROM city where(city='Saint Laurent')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('304 Rue Franois-Perrault', (SELECT CityId FROM city where(city='Montreal')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('86700 Weston Rd', (SELECT CityId FROM city where(city='Toronto')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('170  Sideroad', (SELECT CityId FROM city where(city='Quebec City')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('1231 Trudea road', (SELECT CityId FROM city where(city='Ottawa')), 'Canada');
INSERT INTO Address ( Street, CityId, Country ) VALUES('16  Whitlock Rd', (SELECT CityId FROM city where(city='Calgary')), 'Canada');

CREATE TABLE Customer (
    CustomerId NUMBER(4)    GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Firstname  VARCHAR2(50) NOT NULL,
    Lastname   VARCHAR2(50) NOT NULL,
    Email      VARCHAR(50)  UNIQUE,
    AddressId  NUMBER(4)    REFERENCES Address(AddressId) ON DELETE CASCADE
);

--Type Cutsomer
CREATE OR REPLACE TYPE customer_typ AS OBJECT(
    customerId NUMBER(4),
    firstname  VARCHAR2(50),
    lastname   VARCHAR2(50),
    email      VARCHAR(50),
    addressId  NUMBER(4)
);
/

INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Daneil', 'Hanne', 'daneil@yahoo.com', (SELECT addressid from Address where street='100 Atwater Street'));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('John', 'Boura', 'bdoura@gmail.com', (SELECT addressid from Address where street='100 Young Street'));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Ari', 'Brown','b.a@gmail.com', NULL);
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Amanda','Harry','am.harry@yahioo.com',(SELECT addressid from Address where street='100 Saint Laurent'));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Jack','Jonhson','johnson.a@gmail.com',(SELECT addressid from Address a INNER JOIN city c ON a.cityid = c.cityid where a.street IS NULL AND c.city='Calgary' ));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Martin','Alexandre','marting@yahoo.com',(SELECT addressid from Address a INNER JOIN city c ON a.cityid = c.cityid where a.street IS NULL AND c.city='Brossard' ));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Mahsa','Sadeghi','ms@gmail.com',(SELECT addressid from Address where street='104 Gill Street'));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('John','Belle','abcd@yahoo.com',(SELECT addressid from Address where street='105 Young Street'));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Alex','Brown','alex@gmail.com',(SELECT addressid from Address where street='boul Saint Laurent'));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Martin','Li','m.li@gmail.com',(SELECT addressid from Address where street='87 boul Saint Laurent'));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Olivia','Smith','smith@hotmail.com',(SELECT addressid from Address where street='76 boul Decalthon'));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Noah','Garcia','g.noah@yahoo.com',(SELECT addressid from Address where street='22222 Happy Street'));
INSERT INTO Customer ( Firstname, Lastname, Email, AddressId ) VALUES('Mahsa','sadeghi','msadeghi@dawsoncollege.qc.ca',(SELECT addressid from Address where street='Dawson College'));


CREATE TABLE Product(
    ProductId       NUMBER(4)     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ProductName     VARCHAR2(30)  NOT NULL,
    ProductCategory VARCHAR2(200) NOT NULL,
    Price           NUMBER(8,2)   NOT NULL
);
--CREATE THE TYPE FOR PRODUCT
CREATE OR REPLACE TYPE product_typ AS OBJECT(
    ProductId       NUMBER(4),
    ProductName     VARCHAR2(30),
    ProductCategory VARCHAR2(200),
    Price           NUMBER(8,2)  
);
/

INSERT INTO product ( productName, productCategory, price ) VALUES ('Laptop ASUS 104S','Electronics',970); 
INSERT INTO product ( productName, productCategory, price ) VALUES ('Apple','Grocery',5);
INSERT INTO product ( productName, productCategory, price ) VALUES ('Sims Cd','Video Games',16); ----?
INSERT INTO product ( productName, productCategory, price ) VALUES ('Orange','Grocery',2);  
INSERT INTO product ( productName, productCategory, price ) VALUES ('Barbie Movie','DVD',30); ----? 
INSERT INTO product ( productName, productCategory, price ) VALUES ('L''Oreal Normal Hair','Health ',10); ---- 
INSERT INTO product ( productName, productCategory, price ) VALUES ('BMW iX Lego','Toys',40); ----?
INSERT INTO product ( productName, productCategory, price ) VALUES ('BMW i6','Cars',50000); ----?
INSERT INTO product ( productName, productCategory, price ) VALUES ('Truck 500c','Vehicle',856600); ----?
INSERT INTO product ( productName, productCategory, price ) VALUES ('Paper Towel','Cleaning Supplies',(50/3)); ----? Changed from Beauty to Cleaning Supplies 
INSERT INTO product ( productName, productCategory, price ) VALUES ('Plum','Grocery',(7/10)); ----?
INSERT INTO product ( productName, productCategory, price ) VALUES ('Lamborghini Lego','Toys',40); ----? 
INSERT INTO product ( productName, productCategory, price ) VALUES ('Chicken','Grocery',9.5); ----?
INSERT INTO product ( productName, productCategory, price ) VALUES ('Pasta','Grocery',13.5); ----?
INSERT INTO product ( productName, productCategory, price ) VALUES ('PS5','Electronics',200); ----?
INSERT INTO product ( productName, productCategory, price ) VALUES ('Tomato','Grocery',2); ----?
INSERT INTO product ( productName, productCategory, price ) VALUES ('Train X745','Toys',30);

--Inserted Data By us 
INSERT INTO product ( productName, productCategory, price ) VALUES ('Fortnite','Game',89.50);
INSERT INTO product ( productName, productCategory, price ) VALUES ('Mincraft','Game',45);
INSERT INTO product ( productName, productCategory, price ) VALUES ('Titanic','DVD',19.99);
INSERT INTO product ( productName, productCategory, price ) VALUES ('Home alone','DVD',21.23);

CREATE TABLE Store (
    StoreId   NUMBER(4) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    StoreName VARCHAR2(50) NOT NULL
);

--Type Store
CREATE OR REPLACE TYPE store_typ AS OBJECT(
    storeId   NUMBER(4),
    storeName VARCHAR2(50)
);
/

INSERT INTO store ( storeName ) VALUES ('Marche Adonis');
INSERT INTO store ( storeName ) VALUES ('Marche Atwater');
INSERT INTO store ( storeName ) VALUES ('Dawson Store');
INSERT INTO store ( storeName ) VALUES ('Store Magic');
INSERT INTO store ( storeName ) VALUES ('Movie Store');
INSERT INTO store ( storeName ) VALUES ('Super Rue Champlain');
INSERT INTO store ( storeName ) VALUES ('Toys R Us');
INSERT INTO store ( storeName ) VALUES ('Dealer One');
INSERT INTO store ( storeName ) VALUES ('Dealer Montreal');
INSERT INTO store ( storeName ) VALUES ('Movie Start');
INSERT INTO store ( storeName ) VALUES ('Star Store');

--Inserted Data By Us
INSERT INTO store ( storeName ) VALUES ('MyNMs');
INSERT INTO store ( storeName ) VALUES ('Haloween Party');
INSERT INTO store ( storeName ) VALUES ('Babies R Us');
INSERT INTO store ( storeName ) VALUES ('Russian Cook');

CREATE TABLE Review (
    ReviewId    NUMBER(4) GENERATED ALWAYS AS IDENTITY,
    ProductId   NUMBER(4) REFERENCES Product(ProductId) ON DELETE CASCADE,
    CustomerId  NUMBER(4) REFERENCES Customer(CustomerId) ON DELETE CASCADE,
    Rating      NUMBER(1),
    Review_flag NUMBER(1),
    Description VARCHAR2(200),
    CONSTRAINT CHK_review CHECK (Review_flag>=0)
);

CREATE OR REPLACE TYPE review_typ AS OBJECT(
    ReviewId    NUMBER(4),
    ProductId   NUMBER(4),
    CustomerId  NUMBER(4),
    Rating      NUMBER(1),
    Review_flag NUMBER(1),
    Description VARCHAR2(200)
);
/

INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Laptop ASUS 104S'),(SELECT customerId FROM CUSTOMER WHERE Email='msadeghi@dawsoncollege.qc.ca'),4,0,'It was affordable.');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Apple'),(SELECT customerId FROM CUSTOMER WHERE Email='alex@gmail.com'),3,0,'Quality was not good');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Sims Cd'),(SELECT customerId FROM CUSTOMER WHERE Email='marting@yahoo.com'),2,1,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Orange'),(SELECT customerId FROM CUSTOMER WHERE Email='daneil@yahoo.com'),5,0,'Highly recommend');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Barbie Movie'),(SELECT customerId FROM CUSTOMER WHERE Email='alex@gmail.com'),1,0,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='L''Oreal Normal Hair'),(SELECT customerId FROM CUSTOMER WHERE Email='marting@yahoo.com'),1,0,'Did not worth the price');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='BMW iX Lego'),(SELECT customerId FROM CUSTOMER WHERE Email='msadeghi@dawsoncollege.qc.ca'),1,0,'missing some parts');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='BMW i6'),(SELECT customerId FROM CUSTOMER WHERE Email='bdoura@gmail.com'),5,1,'Trash');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Truck 500c'),(SELECT customerId FROM CUSTOMER WHERE Email='b.a@gmail.com'),2,NULL,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Paper Towel'),(SELECT customerId FROM CUSTOMER WHERE Email='am.harry@yahioo.com'),5,NULL,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Plum'),(SELECT customerId FROM CUSTOMER WHERE Email='johnson.a@gmail.com'),4,NULL,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='L''Oreal Normal Hair'),(SELECT customerId FROM CUSTOMER WHERE Email='marting@yahoo.com'),3,NULL,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Lamborghini Lego'),(SELECT customerId FROM CUSTOMER WHERE Email='msadeghi@dawsoncollege.qc.ca'),1,0,'Missing some parts');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Plum'),(SELECT customerId FROM CUSTOMER WHERE Email='msadeghi@dawsoncollege.qc.ca'),4,NULL,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Lamborghini Lego'),(SELECT customerId FROM CUSTOMER WHERE Email='ms@gmail.com'),1,0,'Great Product');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='BMW i6'),(SELECT customerId FROM CUSTOMER WHERE Email='abcd@yahoo.com'),5,1,'Bad Quality');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Sims Cd'),(SELECT customerId FROM CUSTOMER WHERE Email='alex@gmail.com'),1,0,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Barbie Movie'),(SELECT customerId FROM CUSTOMER WHERE Email='alex@gmail.com'),4,0,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Chicken'),(SELECT customerId FROM CUSTOMER WHERE Email='m.li@gmail.com'),4,NULL,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Pasta'),(SELECT customerId FROM CUSTOMER WHERE Email='smith@hotmail.com'),5,NULL,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='PS5'),(SELECT customerId FROM CUSTOMER WHERE Email='g.noah@yahoo.com'),NULL,NULL,'');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='BMW iX Lego'),(SELECT customerId FROM CUSTOMER WHERE Email='msadeghi@dawsoncollege.qc.ca'),1,2,'worse car i have droven!');
INSERT INTO review ( productId,customerId,rating,review_flag,Description ) VALUES ((SELECT productId FROM PRODUCT WHERE productName='Pasta'),(SELECT customerId FROM CUSTOMER WHERE Email='smith@hotmail.com'),4,NULL,'');

CREATE TABLE orders(
    OrderId     NUMBER(4) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    CustomerId  NUMBER(4) REFERENCES Customer(CustomerId) ON DELETE CASCADE ,
    ProductId   NUMBER(4) REFERENCES Product(ProductId) ON DELETE CASCADE ,
    OrderDate   DATE ,
    Product_qty NUMBER(3) NOT NULL,
    theStoreId  NUMBER(4) REFERENCES Store(StoreId) ON DELETE CASCADE,
    Price_order NUMBER(8,2) NOT NULL
);

--Type orders
CREATE OR REPLACE TYPE orders_typ AS OBJECT(
    orderId     NUMBER(4),
    customerId  NUMBER(4),
    productId   NUMBER(4),
    orderDate   DATE,
    product_qty NUMBER(3),
    theStoreId  NUMBER(4),
    price_order NUMBER(8,2)
);
/

--NOT SURE FOR THE PRICE
INSERT INTO orders ( CustomerId, ProductId, OrderDate, Product_qty, TheStoreId, Price_order ) VALUES ((SELECT CustomerId FROM Customer WHERE Email = 'msadeghi@dawsoncollege.qc.ca'),
(SELECT ProductId FROM Product WHERE ProductName = 'Laptop ASUS 104S'), DATE'2023-04-21',1,(SELECT StoreId FROM Store WHERE StoreName = 'Marche Adonis'),(SELECT Price FROM Product WHERE ProductName = 'Laptop ASUS 104S'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='alex@gmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Apple'), DATE'2023-10-23',2,(SELECT storeId FROM STORE WHERE storeName='Marche Atwater'),(SELECT price from PRODUCT WHERE productName='Apple'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='marting@yahoo.com'),(SELECT productId FROM PRODUCT WHERE productName='Sims Cd'), DATE'2023-10-01',3,(SELECT storeId FROM STORE WHERE storeName='Dawson Store'),(SELECT price from PRODUCT WHERE productName='Sims Cd'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='daneil@yahoo.com'),(SELECT productId FROM PRODUCT WHERE productName='Orange'), DATE'2023-10-23',1,(SELECT storeId FROM STORE WHERE storeName='Store Magic'),(SELECT price from PRODUCT WHERE productName='Orange'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='alex@gmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Barbie Movie'), DATE'2023-10-23',1,(SELECT storeId FROM STORE WHERE storeName='Store Magic'),(SELECT price from PRODUCT WHERE productName='Barbie Movie'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='marting@yahoo.com'),(SELECT productId FROM PRODUCT WHERE productName='L''Oreal Normal Hair'), DATE'2023-10-10',1,(SELECT storeId FROM STORE WHERE storeName='Super Rue Champlain'),(SELECT price from PRODUCT WHERE productName='L''Oreal Normal Hair'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='msadeghi@dawsoncollege.qc.ca'),(SELECT productId FROM PRODUCT WHERE productName='BMW iX Lego'), DATE'2023-10-11',1,(SELECT storeId FROM STORE WHERE storeName='Toys R Us'),(SELECT price from PRODUCT WHERE productName='BMW iX Lego'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES( 
(SELECT customerId FROM CUSTOMER WHERE Email='bdoura@gmail.com'),(SELECT productId FROM PRODUCT WHERE productName='BMW i6'), DATE'2023-10-10',1,(SELECT storeId FROM STORE WHERE storeName='Dealer One'),(SELECT price from PRODUCT WHERE productName='BMW i6'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='b.a@gmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Truck 500c'),NULL,1,(SELECT storeId FROM STORE WHERE storeName='Dealer Montreal'),(SELECT price from PRODUCT WHERE productName='Truck 500c'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='am.harry@yahioo.com'),(SELECT productId FROM PRODUCT WHERE productName='Paper Towel'),NULL,3,(SELECT storeId FROM STORE WHERE storeName='Movie Start'),(SELECT price from PRODUCT WHERE productName='Paper Towel'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='johnson.a@gmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Plum'), DATE'2020-05-06',6,(SELECT storeId FROM STORE WHERE storeName='Marche Atwater'),(SELECT price from PRODUCT WHERE productName='Plum'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='marting@yahoo.com'),(SELECT productId FROM PRODUCT WHERE productName='L''Oreal Normal Hair'), DATE'2019-09-12',3,(SELECT storeId FROM STORE WHERE storeName='Super Rue Champlain'),(SELECT price from PRODUCT WHERE productName='L''Oreal Normal Hair'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='msadeghi@dawsoncollege.qc.ca'),(SELECT productId FROM PRODUCT WHERE productName='Lamborghini Lego'), DATE'2010-10-11',1,(SELECT storeId FROM STORE WHERE storeName='Toys R Us'),(SELECT price from PRODUCT WHERE productName='Lamborghini Lego'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='msadeghi@dawsoncollege.qc.ca'),(SELECT productId FROM PRODUCT WHERE productName='Plum'), DATE'2022-05-06',7,(SELECT storeId FROM STORE WHERE storeName='Marche Atwater'),(SELECT price from PRODUCT WHERE productName='Plum'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='ms@gmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Lamborghini Lego'), DATE'2023-10-07',2,(SELECT storeId FROM STORE WHERE storeName='Toys R Us'),(SELECT price from PRODUCT WHERE productName='Lamborghini Lego'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='abcd@yahoo.com'),(SELECT productId FROM PRODUCT WHERE productName='BMW i6'), DATE'2023-08-10',1,(SELECT storeId FROM STORE WHERE storeName='Dealer One'),(SELECT price from PRODUCT WHERE productName='BMW i6'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='alex@gmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Sims Cd'), DATE'2023-10-23',1,(SELECT storeId FROM STORE WHERE storeName='Movie Store'),(SELECT price from PRODUCT WHERE productName='Sims Cd'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='alex@gmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Barbie Movie'), DATE'2023-10-02',1,(SELECT storeId FROM STORE WHERE storeName='Toys R Us'),(SELECT price from PRODUCT WHERE productName='Barbie Movie'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='m.li@gmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Chicken'), DATE'2019-04-03',1,(SELECT storeId FROM STORE WHERE storeName='Marche Adonis'),(SELECT price from PRODUCT WHERE productName='Chicken'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='smith@hotmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Pasta'), DATE'2021-12-29',3,(SELECT storeId FROM STORE WHERE storeName='Marche Atwater'),(SELECT price from PRODUCT WHERE productName='Pasta'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='g.noah@yahoo.com'),(SELECT productId FROM PRODUCT WHERE productName='PS5'), DATE'2020-01-20',1,(SELECT storeId FROM STORE WHERE storeName='Star Store'),(SELECT price from PRODUCT WHERE productName='PS5'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='msadeghi@dawsoncollege.qc.ca'),(SELECT productId FROM PRODUCT WHERE productName='BMW iX Lego'), DATE'2022-10-11',1,(SELECT storeId FROM STORE WHERE storeName='Toys R Us'),(SELECT price from PRODUCT WHERE productName='BMW iX Lego'));

INSERT INTO orders ( customerId,productId,orderDate,product_qty,theStoreId,price_order ) VALUES(
(SELECT customerId FROM CUSTOMER WHERE Email='smith@hotmail.com'),(SELECT productId FROM PRODUCT WHERE productName='Pasta'), DATE'2021-12-29',3,(SELECT storeId FROM STORE WHERE storeName='Store Magic'),(SELECT price from PRODUCT WHERE productName='Pasta')+1.5);


CREATE TABLE Warehouse (
    WarehouseId   NUMBER(4)    GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    WarehouseName VARCHAR2(50) NOT NULL,
    AddressId     NUMBER(4)    REFERENCES Address(AddressId) ON DELETE CASCADE 
);
/
--CREATING THE TYPE
CREATE OR REPLACE TYPE warehouse_typ AS OBJECT(
    WarehouseId   NUMBER(4) ,
    WarehouseName VARCHAR2(50),
    AddressId     NUMBER(4)   
);
/

INSERT INTO warehouse ( WarehouseName,addressId ) VALUES ('Warehouse A',(Select addressId FROM ADDRESS WHERE (street='100 rue William')));
INSERT INTO warehouse ( WarehouseName,addressId ) VALUES ('Warehouse B',(Select addressId FROM ADDRESS WHERE (street='304 Rue François-Perrault')));
INSERT INTO warehouse ( WarehouseName,addressId ) VALUES ('Warehouse C',(Select addressId FROM ADDRESS WHERE (street='86700 Weston Rd')));
INSERT INTO warehouse ( WarehouseName,addressId ) VALUES ('Warehouse D', (Select addressId FROM ADDRESS WHERE (street='170  Sideroad')));
INSERT INTO warehouse ( WarehouseName,addressId ) VALUES ('Warehouse E', (Select addressId FROM ADDRESS WHERE (street='1231 Trudea road')));
INSERT INTO warehouse ( WarehouseName,addressId ) VALUES ('Warehouse F', (Select addressId FROM ADDRESS WHERE (street='16  Whitlock Rd')));

CREATE TABLE Availability_Warehouse_Product (
    ProductId     NUMBER(4) REFERENCES Product(ProductId) ON DELETE CASCADE,
    WarehouseId   NUMBER(4) REFERENCES Warehouse(WarehouseId) ON DELETE CASCADE, 
    Warehouse_qty NUMBER(6) 
    CONSTRAINT CHK_qty CHECK (Warehouse_qty>=0)
);
/

CREATE OR REPLACE TYPE  availability_Warehouse_typ AS OBJECT (
    ProductId     NUMBER(4),
    WarehouseId   NUMBER(4), 
    Warehouse_qty NUMBER(6)
);
/

INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Laptop ASUS 104S'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse A'))), 1000);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Apple'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse B'))), 24980);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Sims Cd'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse C'))), 103);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Orange'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse D'))), 35405);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Barbie Movie'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse E'))), 40);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('L''Oreal Normal Hair'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse F'))), 450);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('BMW iX Lego'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse A'))), 10);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('BMW i6'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse A'))), 6);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Truck 500c'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse E'))), 1000);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Paper Towel'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse F'))), 3532);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Plum'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse C'))), 43242);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Paper Towel'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse B'))), 39484);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Plum'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse D'))), 6579);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Lamborghini Lego'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse E'))), 98765);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Chicken'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse F'))), 43523);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Pasta'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse A'))), 2132);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('PS5'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse D'))), 123);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Tomato'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse A'))), 352222);
INSERT INTO Availability_Warehouse_Product 
VALUES((SELECT ProductId FROM Product WHERE (ProductName=('Train X745'))),(SELECT WarehouseId FROM Warehouse WHERE (WarehouseName=('Warehouse E'))), 4543);

CREATE TABLE AuditHistory(
    audit_id NUMBER(4) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Table_name VARCHAR2(30) NOT NULL,
    changeDate DATE,
    action VARCHAR2(6)
);
/
CREATE TRIGGER TrackProducts
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

CREATE TRIGGER TrackAddress
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
CREATE TRIGGER TrackOrder
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

CREATE TRIGGER TrackCustomers
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
/
COMMIT;