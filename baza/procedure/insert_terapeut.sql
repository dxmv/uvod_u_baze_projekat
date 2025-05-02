DELIMITER $$

CREATE PROCEDURE insert_terapeut (
  IN pTelefon        VARCHAR(16),
  IN pPrebivaliste   VARCHAR(64),
  IN pIme            VARCHAR(32),
  IN pPrezime        VARCHAR(32),
  IN pJMBG           VARCHAR(16),
  IN pDatumRodj      DATE,
  IN pEmail          VARCHAR(32),
  IN pPsiholog       BOOLEAN,
  IN pFkFakultetId   INT,
  IN pFkCentarId     INT,
  IN pFkStepenId     INT,
  IN pCertDate       DATE,
  IN pOblastIme      VARCHAR(64)
)
BEGIN
  DECLARE vOblastId INT;
  DECLARE vSertId   INT;

  -- If anything goes wrong, rollback and re‑throw
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    RESIGNAL;
  END;

  START TRANSACTION;

    -- 1. Lookup the therapy‑field
    SELECT oblastId
      INTO vOblastId
      FROM OblastTerapije
     WHERE ime = pOblastIme
     LIMIT 1;

    -- 2. Insert the certificate
    INSERT INTO Sertifikat (datumSert, fk_oblastId)
    VALUES (pCertDate, vOblastId);
    SET vSertId = LAST_INSERT_ID();

    -- 3. Insert the candidate as therapist
    INSERT INTO Kandidat
      (telefon, prebivaliste, ime, prezime, jmbg,
       datumRodj, email, psiholog,
       fk_fakultetId, fk_centarId, fk_stepenId, fk_sertId)
    VALUES
      (pTelefon, pPrebivaliste, pIme, pPrezime, pJMBG,
       pDatumRodj, pEmail, pPsiholog,
       pFkFakultetId, pFkCentarId, pFkStepenId, vSertId);

  COMMIT;
END$$

DELIMITER ;
