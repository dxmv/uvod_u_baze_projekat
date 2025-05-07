

DELIMITER $$

CREATE PROCEDURE insert_kandidat_sa_supervizorom (
    IN pIme              VARCHAR(32),
    IN pPrezime          VARCHAR(32),
    IN pEmail            VARCHAR(32),
    IN pTelefon          VARCHAR(16),
    IN pJMBG             VARCHAR(16),
    IN pDatumRodj        DATE,
    IN pPrebivaliste     VARCHAR(64),
    IN pPsiholog         BOOLEAN,
    IN pSifra            VARCHAR(32),
    IN pFakultetIme      VARCHAR(100),
    IN pStepenNaziv      VARCHAR(50),
    IN pCentarNaziv      VARCHAR(100),

    IN pSupervisorJMBG   VARCHAR(16),   -- supervisor is looked-up by JMBG
    IN pDatumSupervizije DATE           -- supervision start date
)
BEGIN
    /* --- local variables ---------------------------------- */
    DECLARE vFakultetId     INT;
    DECLARE vStepenId       INT;
    DECLARE vCentarId       INT;
    DECLARE vSupervisorId   INT;
    DECLARE vNewKandidatId  INT;

    /* --- error handler: roll back everything on failure ---- */
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    /* 1.  Resolve Fakultet --------------------------------- */
    SELECT fakultetId INTO vFakultetId
    FROM Fakultet
    WHERE ime = pFakultetIme
    LIMIT 1;

    IF vFakultetId IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Fakultet nije pronađen';
    END IF;

    /* 2.  Resolve Stepen studija --------------------------- */
    SELECT stepenId INTO vStepenId
    FROM StepenStudija
    WHERE naziv = pStepenNaziv
    LIMIT 1;

    IF vStepenId IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Stepen studija nije pronađen';
    END IF;

    /* 3.  Resolve Centar za obuku -------------------------- */
    SELECT centarId INTO vCentarId
    FROM CentarZaObuku
    WHERE naziv = pCentarNaziv
    LIMIT 1;

    IF vCentarId IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Centar za obuku nije pronađen';
    END IF;

    /* 4.  Resolve Supervisor (existing Kandidat) ----------- */
    SELECT kandidatId INTO vSupervisorId
    FROM Kandidat
    WHERE jmbg = pSupervisorJMBG
    LIMIT 1;

    IF vSupervisorId IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Supervizor sa datim JMBG-om ne postoji';
    END IF;

    /* 5.  Insert new Kandidat (no certificate) ------------- */
    INSERT INTO Kandidat (
        ime, prezime, email, telefon, jmbg,
        datumRodj, prebivaliste, psiholog,
        fk_fakultetId, fk_stepenId, fk_centarId, fk_sertId, sifra
    )
    VALUES (
        pIme, pPrezime, pEmail, pTelefon, pJMBG,
        pDatumRodj, pPrebivaliste, pPsiholog,
        vFakultetId, vStepenId, vCentarId, NULL, pSifra
    );

    SET vNewKandidatId = LAST_INSERT_ID();

    /* 6.  Create the supervision link ---------------------- */
    INSERT INTO Supervizija (
        datumPocetka, datumKraja,
        fk_kandidatId, fk_supervizorId
    )
    VALUES (
        pDatumSupervizije, NULL,      
        vNewKandidatId, vSupervisorId
    );

    COMMIT;
END$$

DELIMITER ;
