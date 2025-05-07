/* ================================================================ */
/*  FULL SEED SCRIPT (22 core entities)                             */
/* ================================================================ */
START TRANSACTION;

/* ---------- 1.  ENUM / LOOK-UP TABLES --------------------------- */
INSERT INTO StepenStudija (naziv) VALUES
  ('Osnovne'),
  ('Master'),
  ('Doktorske');                                       -- 3

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
  ('Multidisciplinarne studije');                      -- 10

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
  ('Istorija');                                        -- 10

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
  ('Somatska terapija');                               -- 10

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
  ('CZK','Češka kruna');                               -- 10

/* ---------- 2.  ENUMs WITH PARENT FK ---------------------------- */
INSERT INTO Kurs (datumPromene, kursDInara, fk_valutaId) VALUES
  (CURDATE(),  1.00 , 1),  -- RSD
  (CURDATE(),117.45, 2),   -- EUR
  (CURDATE(),102.30, 3),
  (CURDATE(),136.90, 4),
  (CURDATE(),122.80, 5),
  (CURDATE(),  0.85, 6),
  (CURDATE(), 14.50, 7),
  (CURDATE(), 69.20, 8),
  (CURDATE(), 75.10, 9),
  (CURDATE(),  4.70,10);   -- CZK

/* ---------- 3.  UNIVERSITIES & FACULTIES ------------------------ */
INSERT INTO Univerzitet (ime, fk_usmrenjeId) VALUES
  ('Univerzitet u Beogradu',            6),
  ('Univerzitet u Novom Sadu',          7),
  ('Universitas Studiorum Novi Pazar',  1),
  ('Univerzitet umetnosti u Beogradu',  8),
  ('Medicinski univerzitet Kragujevac', 3),
  ('Poljoprivredni univerzitet Zemun',  5),
  ('Univerzitet u Nišu',                2),
  ('Univerzitet u Kragujevcu',          6),
  ('Univerzitet Educons',              10),
  ('International University NP',       7);            -- 10

INSERT INTO Fakultet (ime, fk_uniId) VALUES
  ('Fakultet organizacionih nauka', 1),
  ('Elektrotehnički fakultet',      1),
  ('Filozofski fakultet',           2),
  ('Prirodno-matematički fakultet', 2),
  ('Departman za IT',               3),
  ('Fakultet umetnosti',            4),
  ('Medicinski fakultet',           5),
  ('Poljoprivredni fakultet',       6),
  ('Mašinski fakultet',             7),
  ('Ekonomski fakultet',            8);                -- 10

INSERT INTO Fakultet_Oblast (fk_fakultetId, fk_oblastId) VALUES
  (2,1),(2,5), (3,7), (4,1),(4,2),
  (5,5), (6,9), (7,3),(7,4), (8,3), (9,2),(9,5),
  (10,8),(3,6),(4,6),(6,8),(2,4),(1,10);              -- 20 links

INSERT INTO UzeUsmerenje_Oblast (fk_usmerenjeId, fk_oblastId) VALUES
  (1,1),(1,2),(2,5),(3,3),(4,3),(5,3),
  (6,7),(7,10),(8,9),(9,5),(10,5),(2,1),(8,6),(4,2); -- 14 links

/* ---------- 4.  TRAINING CENTRES & TESTS ------------------------ */
INSERT INTO CentarZaObuku (naziv,email,brojTelefona,ulica,brojUlice,opstina) VALUES
  ('Centar "Novi početak"','info@novi.rs','+38111234567','Bulevar Kralja Aleksandra',73,'Beograd'),
  ('Psiha+','kontakt@psiha.rs','+38111234568','Kralja Milana',18,'Beograd'),
  ('Institut Gestalt','info@gestalt.rs','+38111234569','Njegoševa',6,'Novi Sad'),
  ('ACT Mind Lab','team@actmind.rs','+38121455670','Bulevar oslobođenja',1,'Novi Sad'),
  ('Humanist Hub','hello@hub.rs','+38134455671','Karađorđeva',5,'Kragujevac'),
  ('CBC centar','office@cbc.rs','+38118234572','Vožda Karađorđa',12,'Niš'),
  ('Porodična terapija Niš','support@porodica.rs','+38118234573','Knjaza Miloša',15,'Niš'),
  ('Mindfulness Studio','studio@mindful.rs','+38111234574','Gundulićev venac',14,'Beograd'),
  ('Sistemska praksa','sistem@sip.rs','+38121234575','Stražilovska',4,'Novi Sad'),
  ('Somatic Center','info@somatic.rs','+38121234576','Heroja Pinkija',20,'Subotica');

INSERT INTO PsihoTest (naziv, oblast, cenaRSD) VALUES
  ('MMPI-2','Psihologija ličnosti',6500),
  ('BDI-II','Klin. psihologija',2500),
  ('Hamilton A','Klin. psihologija',2500),
  ('WAIS-IV','Kognitivne funkcije',8500),
  ('NEO-PI-3','Psihologija ličnosti',6000),
  ('Stroop','Kognitivne funkcije',1200),
  ('WCST','Neuropsihologija',4000),
  ('Rorschach','Projektivni',5000),
  ('TMT','Neuropsihologija',1500),
  ('MoCA','Screening',1000);

/* ---------- 5.  SERTIFIKATI (only 5, linked to OblastTerapije) --- */
INSERT INTO Sertifikat (datumSert, fk_oblastId) VALUES
  ('2023-01-15',1),
  ('2023-02-20',2),
  ('2023-03-10',3),
  ('2023-04-05',4),
  ('2023-05-11',5);                                    -- 5

/* ---------- 6.  KANDIDATI (5 with certs, 5 without) -------------- */
INSERT INTO Kandidat
(telefon, prebivaliste, ime, prezime, jmbg, datumRodj, email, psiholog,
 fk_fakultetId, fk_centarId, fk_stepenId, fk_sertId, sifra)
VALUES
  ('+38160111111','Beograd','Ana','Petrović','0101990123456','1990-01-01','ana@ex.rs',1,1,1,1,1,'test'),
  ('+38160111112','Novi Sad','Marko','Jovanović','0202980123457','1989-02-02','marko@ex.rs',0,2,2,2,2,'test'),
  ('+38160111113','Niš','Ivana','Milić','0303970123458','1991-03-03','ivana@ex.rs',1,3,3,3,3,'test'),
  ('+38160111114','Subotica','Petar','Stanković','0404960123459','1988-04-04','petar@ex.rs',0,4,4,1,1,'test'),
  ('+38160111115','Kragujevac','Milica','Kovačević','0505950123460','1992-05-05','milica@ex.rs',1,5,5,2,2,'test'),
  ('+38160111116','Beograd','Nikola','Radović','0606940123461','1994-06-06','nikola@ex.rs',1,6,6,1,NULL,'test'),
  ('+38160111117','Novi Sad','Jelena','Gajić','0707930123462','1993-07-07','jelena@ex.rs',0,7,7,2,NULL,'test'),
  ('+38160191118','Niš','Vladimir','Matić','0808920123463','1995-08-08','vladimir@ex.rs',1,8,8,3,NULL,'test'),
  ('+38160191119','Subotica','Teodora','Živković','0909910123464','1996-09-09','teodora@ex.rs',0,9,9,1,NULL,'test'),
  ('+38160191120','Beograd','Aleksandar','Ilić','1010900123465','1990-10-10','aleksa@ex.rs',1,10,10,2,NULL,'test');

/* ---------- 7.  KLIJENTI ------------------------------------------ */
INSERT INTO Klijent
  (ime,prezime,datumRodj,pol,email,telefon,datumPrijave,ranijePosetio,opisProblema)
VALUES
  ('Luka','Nikolić','1995-05-05','M','luka@ex.rs','+381601220001',CURDATE(),0,'Anksioznost'),
  ('Sara','Jovanović','1992-08-12','F','sara@ex.rs','+381601220002',CURDATE(),1,'Depresija'),
  ('Ivan','Petrović','1980-11-30','M','ivan@ex.rs','+381601220003',CURDATE(),0,'Stres'),
  ('Mina','Kostić','1999-02-14','F','mina@ex.rs','+381601220004',CURDATE(),0,'Fobija'),
  ('Đorđe','Stojanović','1988-10-10','M','djole@ex.rs','+381601220005',CURDATE(),1,'Insomnija'),
  ('Jovana','Mitić','1993-07-07','F','jovana@ex.rs','+381601220006',CURDATE(),0,'Panični napadi'),
  ('Stefan','Vasić','1991-01-03','M','stefan@ex.rs','+381601220007',CURDATE(),1,'Burn-out'),
  ('Nataša','Rajić','1984-04-22','F','natasa@ex.rs','+381601220008',CURDATE(),0,'Perfekcionizam'),
  ('Miloš','Obradović','1996-09-15','M','milos@ex.rs','+381601220009',CURDATE(),0,'Socijalna anksioznost'),
  ('Tamara','Milovanović','1990-12-01','F','tamara@ex.rs','+381601220010',CURDATE(),1,'Samopouzdanje');

/* ---------- 8.  CENA PO SATU ------------------------------------- */
INSERT INTO CenaPoSatu (cena, datumPromene) VALUES
  (3500, CURDATE());

/* ---------- 9.  SEANSE ------------------------------------------- */
INSERT INTO Seansa
  (datum,vremePocetka,trajanje,besplatnaSeansa,fk_kandidatId,fk_klijentId,fk_cenaId)
VALUES
  (CURDATE(),'10:00:00',60,0,1,1,1),
  (CURDATE(),'11:30:00',50,0,2,2,1),
  (CURDATE(),'13:00:00',60,1,3,3,1),
  (CURDATE(),'15:00:00',45,0,4,4,1),
  (CURDATE(),'16:30:00',60,0,5,5,1),
  (CURDATE(),'09:00:00',30,1,6,6,1),
  (CURDATE(),'10:45:00',60,0,7,7,1),
  (CURDATE(),'12:15:00',50,0,8,8,1),
  (CURDATE(),'14:00:00',60,0,9,9,1),
  (CURDATE(),'17:00:00',60,0,10,10,1);

/* ---------- 10.  BELESKE SEANSE ---------------------------------- */
INSERT INTO BeleskeSeanse (text,fk_seansaId) VALUES
  ('Klijent opisao simptome anksioznosti',1),
  ('Progres u suočavanju s depresijom',2),
  ('Besplatno uvodno savetovanje',3),
  ('Klijent identifikovao glavnu fobiju',4),
  ('Diskutovan problem insomnije',5),
  ('Kratka konsultacija o strategijama disanja',6),
  ('Rad na burn-out modelu',7),
  ('Tehnike mindfulness-a predstavljene',8),
  ('Vežbe za javni nastup',9),
  ('Rad na samopouzdanju',10);

/* ---------- 11.  PRIMALAC --------------------------------------- */
INSERT INTO Primalac (primalacId, naziv) VALUES
  (1, 'Supervizor'),
  (2, 'Policies'),
  (3, 'Sud');

/* ---------- 12.  OBJAVA PODATAKA -------------------------------- */
INSERT INTO ObjavaPodataka (datumObjave,primalacId,fk_seansaId,razlog) VALUES
  (CURDATE(),1,1,'Prosleđivanje lekaru'),
  (CURDATE(),2,2,'Konzilijarna procena'),
  (CURDATE(),1,3,'Rad sa supervizorom'),
  (CURDATE(),2,4,'Konsultacija sa psihologom'),
  (CURDATE(),3,5,'Praktični savet'),
  (CURDATE(),1,6,'Psihološka procena'),
  (CURDATE(),2,7,'Terapijska strategija'),
  (CURDATE(),1,8,'Kognitivna terapija'),
  (CURDATE(),3,9,'Praktični savet'),
  (CURDATE(),2,10,'Konsultacija');

COMMIT;