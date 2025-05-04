DELIMITER $$

CREATE PROCEDURE insert_terapeut (
  IN pIme            VARCHAR(32),
  IN pPrezime        VARCHAR(32),
  IN pEmail          VARCHAR(32),
  IN pTelefon        VARCHAR(16),
  IN pJMBG           VARCHAR(16),
  IN pDatumRodj      DATE,
  IN pPrebivaliste   VARCHAR(64),
  IN pPsiholog       BOOLEAN,
  IN pFakultetIme    VARCHAR(100),
  IN pStepenNaziv    VARCHAR(50),
  IN pCentarNaziv    VARCHAR(100),
  IN pOblastIme      VARCHAR(64),
  IN pDatumSert      DATE,
  IN pSifra          VARCHAR(32)
)
BEGIN
  DECLARE vFakultetId INT;
  DECLARE vStepenId INT;
  DECLARE vCentarId INT;
  DECLARE vOblastId INT;
  DECLARE vSertId INT;
  
  -- Error handling - if anything goes wrong, rollback the entire transaction
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    RESIGNAL;
  END;

  START TRANSACTION;
  
    -- 1. Find fakultet ID by name
    SELECT fakultetId INTO vFakultetId
    FROM Fakultet
    WHERE ime = pFakultetIme
    LIMIT 1;
    
    IF vFakultetId IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fakultet nije pronađen';
    END IF;
    
    -- 2. Find stepen studija ID by name
    SELECT stepenId INTO vStepenId
    FROM StepenStudija
    WHERE naziv = pStepenNaziv
    LIMIT 1;
    
    IF vStepenId IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Stepen studija nije pronađen';
    END IF;
    
    -- 3. Find centar za obuku ID by name
    SELECT centarId INTO vCentarId
    FROM CentarZaObuku
    WHERE naziv = pCentarNaziv
    LIMIT 1;
    
    IF vCentarId IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Centar za obuku nije pronađen';
    END IF;
    
    -- 4. Find oblast terapije ID by name
    SELECT oblastId INTO vOblastId
    FROM OblastTerapije
    WHERE ime = pOblastIme
    LIMIT 1;
    
    IF vOblastId IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Oblast terapije nije pronađena';
    END IF;

    -- 5. Insert the certificate
    INSERT INTO Sertifikat (datumSert, fk_oblastId)
    VALUES (pDatumSert, vOblastId);
    
    SET vSertId = LAST_INSERT_ID();

    -- 6. Insert the candidate as therapist
    INSERT INTO Kandidat (
      ime, 
      prezime, 
      email, 
      telefon, 
      jmbg,
      datumRodj, 
      prebivaliste, 
      psiholog,
      fk_fakultetId, 
      fk_stepenId, 
      fk_centarId, 
      fk_sertId,
      sifra
    )
    VALUES (
      pIme, 
      pPrezime, 
      pEmail, 
      pTelefon, 
      pJMBG,
      pDatumRodj, 
      pPrebivaliste, 
      pPsiholog,
      vFakultetId, 
      vStepenId, 
      vCentarId, 
      vSertId,
      pSifra
    );

  COMMIT;
END$$

DELIMITER ;
