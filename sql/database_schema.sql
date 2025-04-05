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
select * from routes
