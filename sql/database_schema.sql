SELECT * FROM passengers WHERE email = 'your@email.com';
SELECT database(); -- Check which database is active
Use bus_booking;
SELECT * FROM passengers;
SELECT * FROM passengers WHERE email = '?';
SELECT * FROM passengers WHERE LOWER(email) = LOWER('your@email.com');
SELECT email FROM passengers
SELECT email, LENGTH(email) FROM passengers;
SELECT email, password_hash FROM passengers WHERE email = 'kevinkinyua@gmail.com';
SELECT email, password_hash FROM passengers WHERE email = 'brucebarasa@gmail.com';
UPDATE passengers
SET password_hash = '$2a$10$N8BO..I2PSHbvB/.SoIpROy1MZDK2J6uAPKQSMS6Yd2woiLgTXLAu'
WHERE email = 'brucebarasa@gmail.com';

SELECT * FROM passengers WHERE LOWER(email) = LOWER('?')

UPDATE passengers SET email = TRIM(email);
SELECT email, HEX(email) FROM passengers WHERE email LIKE '%brucebarasa%';

SELECT email, password_hash FROM passengers WHERE email = 'danjuma@gmail.com';

UPDATE passengers
SET password_hash = '$2a$10$DeHieTAd3F8sXBzpkAShrOXBLaaCn/xSApun2WrcYdg5SOf.Nt6Qe'
WHERE email = 'danjuma@gmail.com';

SELECT email, password_hash FROM passengers LIMIT 5;

SHOW Tables;

