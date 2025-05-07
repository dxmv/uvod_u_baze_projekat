DELIMITER $$

CREATE PROCEDURE insert_objava (
    IN pDatumObjave   DATE,
    IN pPrimalacNaziv VARCHAR(32),
    IN pRazlog        TEXT,
    IN pSeansaId      INT
)
BEGIN
    DECLARE vPrimalacId INT;

    -- Roll back everything on any SQL error
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    /* 1. Resolve Primalac */
    SELECT primalacId
      INTO vPrimalacId
      FROM Primalac
     WHERE naziv = pPrimalacNaziv;

    IF vPrimalacId IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Primalac nije pronađen';
    END IF;

    /* 2. Ensure seansa exists */
    IF NOT EXISTS (SELECT 1 FROM Seansa WHERE seansaId = pSeansaId) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Seansa nije pronađena';
    END IF;


    /* 3. Insert */
    INSERT INTO ObjavaPodataka (
        datumObjave,
        primalacId,
        fk_seansaId,
        razlog
    )
    VALUES (
        pDatumObjave,
        vPrimalacId,
        pSeansaId,
        pRazlog
    );

    COMMIT;
END$$
DELIMITER ;
