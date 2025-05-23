DELIMITER //

CREATE PROCEDURE InsertKlijentAndSeansa (
    -- Klijent parameters
    IN p_klijent_ime NVARCHAR(32),
    IN p_klijent_prezime NVARCHAR(32),
    IN p_klijent_datumRodj DATE,
    IN p_klijent_pol VARCHAR(1),
    IN p_klijent_email NVARCHAR(32),
    IN p_klijent_telefon NVARCHAR(16),
    IN p_klijent_datumPrijave DATE,
    IN p_klijent_ranijePosetio BOOLEAN,
    IN p_klijent_opisProblema LONGTEXT,
    -- Seansa parameters
    IN p_seansa_datum DATE,
    IN p_seansa_vremePocetka TIME,
    IN p_seansa_trajanje INT,
    IN p_seansa_besplatnaSeansa BOOLEAN,
    IN p_seansa_fk_kandidatId INT
)
BEGIN
    DECLARE v_new_klijentId INT;
    DECLARE v_fk_cenaId INT;

    -- Insert the new Klijent
    INSERT INTO Klijent (
        ime,
        prezime,
        datumRodj,
        pol,
        email,
        telefon,
        datumPrijave,
        ranijePosetio,
        opisProblema
    ) VALUES (
        p_klijent_ime,
        p_klijent_prezime,
        p_klijent_datumRodj,
        p_klijent_pol,
        p_klijent_email,
        p_klijent_telefon,
        p_klijent_datumPrijave,
        p_klijent_ranijePosetio,
        p_klijent_opisProblema
    );

    -- Get the ID of the newly inserted Klijent
    SET v_new_klijentId = LAST_INSERT_ID();

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
        p_seansa_datum,
        p_seansa_vremePocetka,
        p_seansa_trajanje,
        p_seansa_besplatnaSeansa,
        p_seansa_fk_kandidatId,
        v_new_klijentId,
        v_fk_cenaId
    );
END //

DELIMITER ;