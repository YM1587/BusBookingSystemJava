use bus_booking;
select * from passengers;
SHOW TABLES;
SELECT * FROM passengers
INTO OUTFILE 'D:/Bus booking system/Passengers_extract.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
SELECT * FROM passengers
INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Passengers_extract.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
SHOW VARIABLES LIKE 'secure_file_priv';
ALTER TABLE routes
ADD COLUMN fare DECIMAL(10, 2);
ALTER TABLE buses
ADD COLUMN route_id INT,  -- Add routeId column
ADD CONSTRAINT fk_route  -- Define the foreign key constraint
FOREIGN KEY (route_id)   -- Reference the routeId in the Route table
REFERENCES routes(route_id);  -- Ensure the reference is to the route_id in the routes table
select * from buses
select * from seats
select * from routes