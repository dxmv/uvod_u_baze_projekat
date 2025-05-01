DELIMITER //

CREATE PROCEDURE InsertStepenStudija()
BEGIN
    -- Declare variables to check if values already exist
    DECLARE osnovne_exists INT;
    DECLARE master_exists INT;
    DECLARE doktorske_exists INT;
    
    -- Check if values already exist
    SELECT COUNT(*) INTO osnovne_exists FROM StepenStudija WHERE naziv = 'Osnovne';
    SELECT COUNT(*) INTO master_exists FROM StepenStudija WHERE naziv = 'Master';
    SELECT COUNT(*) INTO doktorske_exists FROM StepenStudija WHERE naziv = 'Doktorske';
    
    -- Insert values if they don't already exist
    IF osnovne_exists = 0 THEN
        INSERT INTO StepenStudija (naziv) VALUES ('Osnovne');
        SELECT 'Value "Osnovne" inserted successfully.' AS Message;
    ELSE
        SELECT 'Value "Osnovne" already exists.' AS Message;
    END IF;
    
    IF master_exists = 0 THEN
        INSERT INTO StepenStudija (naziv) VALUES ('Master');
        SELECT 'Value "Master" inserted successfully.' AS Message;
    ELSE
        SELECT 'Value "Master" already exists.' AS Message;
    END IF;
    
    IF doktorske_exists = 0 THEN
        INSERT INTO StepenStudija (naziv) VALUES ('Doktorske');
        SELECT 'Value "Doktorske" inserted successfully.' AS Message;
    ELSE
        SELECT 'Value "Doktorske" already exists.' AS Message;
    END IF;
END //

DELIMITER ;