/* ===================================================================== */
/* 0.  Drop FK-checks just for the load phase (optional but convenient)   */
SET FOREIGN_KEY_CHECKS = 0;
START Transaction;

/* ----- UzeUsmerenje (10 narrow orientations for universities) -------- */
INSERT INTO UzeUsmerenje (naziv) VALUES
  ('Prirodne nauke'),
  ('Tehničke nauke'),
  ('Medicinske nauke'),
  ('Biomedicinske nauke'),
  ('Poljoprivredne nauke'),
  ('Društvene nauke'),
  ('Humanističke nauke'),
  ('Umetničke nauke'),
  ('Informacione tehnologije'),
  ('Multidisciplinarne studije');

/* ----- Oblast (general academic areas, 10) --------------------------- */
INSERT INTO Oblast (naziv) VALUES
  ('Matematika'),
  ('Fizika'),
  ('Biologija'),
  ('Hemija'),
  ('Informatika'),
  ('Psihologija'),
  ('Sociologija'),
  ('Ekonomija'),
  ('Filozofija'),
  ('Istorija');

/* ----- OblastTerapije (10 therapy specialisations from spec) --------- */
INSERT INTO OblastTerapije (ime) VALUES
  ('Kognitivno-bihejvioralna terapija'),
  ('Terapija prihvatanja i posvećenosti'),
  ('Humanistička terapija'),
  ('Geštalt terapija'),
  ('Porodična terapija'),
  ('Sistemska terapija'),
  ('Psihodinamska terapija'),
  ('Mindfulness terapija'),
  ('Eksperimentalna terapija'),
  ('Somatska terapija');           -- :contentReference[oaicite:2]{index=2}&#8203;:contentReference[oaicite:3]{index=3}

/* ----- Valuta (10 commonly used/mentioned currencies) --------------- */
INSERT INTO Valuta (sifra, puniNaziv) VALUES
  ('RSD','Srpski dinar'),
  ('EUR','Evro'),
  ('USD','Američki dolar'),
  ('GBP','Britanska funta'),
  ('CHF','Švajcarski franak'),
  ('JPY','Japanski jen'),
  ('CNY','Kineski juan'),
  ('AUD','Australijski dolar'),
  ('CAD','Kanadski dolar'),
  ('CZK','Češka kruna');

/* ===================================================================== */
/* 2.  Enumerations WITH parent FKs                                      */

/* ----- Kurs – one “today’s” rate for each valuta --------------------- */
INSERT INTO Kurs (datumPromene, kursDInara, fk_valutaId) VALUES
  (CURDATE(), 1.00,  1),   -- RSD base
  (CURDATE(),117.45, 2),   -- EUR
  (CURDATE(),102.30, 3),   -- USD
  (CURDATE(),136.90, 4),   -- GBP
  (CURDATE(),122.80, 5),   -- CHF
  (CURDATE(), 0.85,  6),   -- JPY   (per 1 ¥ → RSD)
  (CURDATE(),14.50,  7),   -- CNY
  (CURDATE(),69.20,  8),   -- AUD
  (CURDATE(),75.10,  9),   -- CAD
  (CURDATE(), 4.70, 10);   -- CZK

/* ----- Univerzitet (5 examples, each tied to an usmerenje) ----------- */
INSERT INTO Univerzitet (ime, fk_usmrenjeId) VALUES
  ('Univerzitet u Beogradu',          6),
  ('Univerzitet u Novom Sadu',        7),
  ('Universitas Studiorum Novi Pazar',1),
  ('Univerzitet umetnosti u Beogradu',8),
  ('Medicinski univerzitet Kragujevac',3);

/* ----- Fakultet (10 faculties mapped to the 5 univerziteta) ---------- */
INSERT INTO Fakultet (ime, fk_uniId) VALUES
  ('Fakultet organizacionih nauka',       1),
  ('Elektrotehnički fakultet',            1),
  ('Filozofski fakultet',                 2),
  ('Prirodno-matematički fakultet',       2),
  ('Departman za informacione tehnologije',3),
  ('Fakultet umetnosti',                  4),
  ('Medicinski fakultet',                 5),
  ('Farmaceutski fakultet',               5),
  ('Fakultet političkih nauka',           1),
  ('Fakultet tehničkih nauka',            2);

/* ----- Fakultet_Oblast (each faculty ↔ 1–2 related areas) ------------ */
INSERT INTO Fakultet_Oblast (fk_fakultetId, fk_oblastId) VALUES
  (1, 10), (1,  6),      -- FON ↔ Istorija, Psihologija
  (2,  1), (2,  5),      -- ETF ↔ Matematika, Informatika
  (3,  7),               -- Filozofski ↔ Sociologija
  (4,  1), (4,  2),      -- PMF ↔ Matematika, Fizika
  (5,  5),               -- IT Dept ↔ Informatika
  (6,  9),               -- Umetnosti ↔ Filozofija
  (7,  3), (7,  4),      -- Medicinski ↔ Biologija, Hemija
  (8,  4),               -- Farmacija ↔ Hemija
  (9,  8),               -- FPN ↔ Ekonomija
  (10,2);                -- FTN ↔ Fizika

/* ----- UzeUsmerenje_Oblast (map every usmerenje to at least 1 oblast) */
INSERT INTO UzeUsmerenje_Oblast (fk_usmerenjeId, fk_oblastId) VALUES
  (1, 1), (1, 2),
  (2, 5),
  (3, 3),
  (4, 3),
  (5, 3),
  (6, 7),
  (7,10),
  (8, 9),
  (9, 5),
  (10,5);

/* ----- CentarZaObuku (10 training centres) -------------------------- */
INSERT INTO CentarZaObuku (naziv,email,brojTelefona,ulica,brojUlice,opstina) VALUES
  ('Centar “Novi početak”',      'info@novipocetak.rs', '+38111234567','Bulevar Kralja Aleksandra',73,'Beograd'),
  ('Centar “Psiha+”',            'kontakt@psihaplus.rs', '+38111234568','Kralja Milana',          18,'Beograd'),
  ('Institut za Gestalt',        'info@gestaltinst.rs',  '+38111234569','Njegoševa',              6 ,'Novi Sad'),
  ('ACT Mind Lab',               'team@actmindlab.rs',   '+38121455670','Bulevar oslobođenja',   1 ,'Novi Sad'),
  ('Humanist Hub',               'hello@humanhub.rs',    '+38134455671','Karađorđeva',           5 ,'Kragujevac'),
  ('CBC (C-B terapija centar)',  'office@cbc.rs',        '+38118234572','Vožda Karađorđa',       12,'Niš'),
  ('Porodična terapija Niš',     'support@porodica.rs',  '+38118234573','Knjaza Miloša',         15,'Niš'),
  ('Mindfulness Studio',         'studio@mindful.rs',    '+38111234574','Gundulićev venac',      14,'Beograd'),
  ('Sistemska praksa',           'sistem@sip.rs',        '+38121234575','Stražilovska',          4 ,'Novi Sad'),
  ('Somatic Center',             'info@somatic.rs',      '+38121234576','Heroja Pinkija',        20,'Subotica');

COMMIT;


