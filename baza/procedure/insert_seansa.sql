DELIMITER //

CREATE PROCEDURE InsertSeansa (
    IN p_datum DATE,
    IN p_vremePocetka TIME,
    IN p_trajanje INT,
    IN p_besplatnaSeansa BOOLEAN,
    IN p_fk_kandidatId INT,
    IN p_fk_klijentId INT
)
BEGIN
    DECLARE v_fk_cenaId INT;

    -- Get the latest cenaId from CenaPoSatu
    SELECT cenaId INTO v_fk_cenaId
    FROM CenaPoSatu
    ORDER BY datumPromene DESC, cenaId DESC
    LIMIT 1;

    -- Insert the new Seansa
    INSERT INTO Seansa (
        datum,
        vremePocetka,
        trajanje,
        besplatnaSeansa,
        fk_kandidatId,
        fk_klijentId,
        fk_cenaId
    ) VALUES (
        p_datum,
        p_vremePocetka,
        p_trajanje,
        p_besplatnaSeansa,
        p_fk_kandidatId,
        p_fk_klijentId,
        v_fk_cenaId
    );
END //

DELIMITER ;