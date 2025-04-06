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
--truncate table buses
--DELETE FROM buses;
--DELETE FROM seats;
--delete from routes
-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Truncate your tables
TRUNCATE TABLE seats;
TRUNCATE TABLE routes;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;


ALTER TABLE buses MODIFY bus_type VARCHAR(20);
INSERT INTO routes (route_name, start_location, end_location, distance_km, estimated_duration, fare) VALUES
('Nairobi-Mombasa', 'Nairobi', 'Mombasa', 485, '08:30:00', 1500),
('Mombasa-Nairobi', 'Mombasa', 'Nairobi', 485, '08:30:00', 1500),
('Nairobi-Kisumu', 'Nairobi', 'Kisumu', 350, '06:15:00', 1200),
('Kisumu-Nairobi', 'Kisumu', 'Nairobi', 350, '06:15:00', 1200),
('Nairobi-Busia', 'Nairobi', 'Busia', 450, '07:30:00', 1400),
('Busia-Nairobi', 'Busia', 'Nairobi', 450, '07:30:00', 1400);

ALTER TABLE buses
ADD COLUMN departure_time TIME;

INSERT INTO buses (bus_id, bus_number, bus_type, capacity, operator_name, route_id, departure_time) VALUES
('1', 'KCG 101A', 'Economy', 41, 'John Mwangi', '1', '08:00:00'), -- Nairobi to Mombasa
('2', 'KCE 202B', 'Economy', 41, 'Grace Otieno', '2', '21:00:00'), -- Mombasa to Nairobi
('3', 'KBN 301C', 'Economy', 41, 'David Okello', '3', '08:00:00'), -- Nairobi to Kisumu
('4', 'KAY 402F', 'Economy', 41, 'Lilian Oduor', '4', '21:00:00'), -- Kisumu to Nairobi
('5', 'KCN 501G', 'Economy', 41, 'Samuel Kiprotich', '5', '08:00:00'), -- Nairobi to Busia
('6', 'KDA 602J', 'Economy', 41, 'Beatrice Chebet', '6', '21:00:00'); -- Busia to Nairobi
TRUNCATE TABLE routes;
SET foreign_key_checks = 0;
-- Insert seat data for Bus 1 (and similarly for other buses)
-- For example, Bus 1 has seats 1A-1D, 2A-2D, ..., 9A-9D, 10A-10E



-- Insert seats for each bus
SET @bus_id = 2;

-- Insert seats for Bus 1
SET @bus_id = 6;
INSERT INTO seats (bus_id, seat_number, seat_status) VALUES
(@bus_id, '1A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '1B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '1C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '1D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '2A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '2B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '2C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '2D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '3A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '3B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '3C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '3D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '4A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '4B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '4C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '4D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '5A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '5B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '5C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '5D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '6A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '6B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '6C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '6D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '7A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '7B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '7C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '7D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '8A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '8B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '8C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '8D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '9A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '9B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '9C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '9D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '10A', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '10B', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '10C', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '10D', IF(RAND() < 0.5, 'available', 'booked')),
(@bus_id, '10E', IF(RAND() < 0.5, 'available', 'booked'));

SET foreign_key_checks = 1;
