-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-05-01 18:43:04.594

-- tables
-- Table: BeleskeSeanse
CREATE TABLE BeleskeSeanse (
    beleskeId int  NOT NULL,
    text longtext  NOT NULL,
    fk_seansaId int  NOT NULL,
    CONSTRAINT BeleskeSeanse_pk PRIMARY KEY (beleskeId,fk_seansaId)
);

-- Table: CenaPoSatu
CREATE TABLE CenaPoSatu (
    cenaId int  NOT NULL,
    cena decimal(12,2)  NOT NULL,
    datumPromene date  NOT NULL,
    fk_seansaId int  NOT NULL,
    CONSTRAINT CenaPoSatu_pk PRIMARY KEY (cenaId,fk_seansaId)
);

-- Table: CentarZaObuku
CREATE TABLE CentarZaObuku (
    centarId int  NOT NULL,
    naziv nvarchar(32)  NOT NULL,
    email nvarchar(32)  NOT NULL,
    brojTelefona nvarchar(16)  NOT NULL,
    ulica nvarchar(32)  NOT NULL,
    brojUlice int  NOT NULL,
    opstina nvarchar(32)  NOT NULL,
    CONSTRAINT CentarZaObuku_pk PRIMARY KEY (centarId)
);

-- Table: Fakultet
CREATE TABLE Fakultet (
    fakultetId int  NOT NULL,
    ime nvarchar(64)  NOT NULL,
    fk_uniId int  NOT NULL,
    CONSTRAINT Fakultet_pk PRIMARY KEY (fakultetId)
);

-- Table: Fakultet_Oblast
CREATE TABLE Fakultet_Oblast (
    fk_fakultetId int  NOT NULL,
    fk_oblastId int  NOT NULL,
    CONSTRAINT Fakultet_Oblast_pk PRIMARY KEY (fk_fakultetId,fk_oblastId)
);

-- Table: Kandidat
CREATE TABLE Kandidat (
    kandidatId int  NOT NULL,
    telefon nvarchar(16)  NOT NULL,
    prebivaliste nvarchar(64)  NOT NULL,
    ime nvarchar(32)  NOT NULL,
    prezime nvarchar(32)  NOT NULL,
    jmbg nvarchar(16)  NOT NULL,
    datumRodj date  NOT NULL,
    email nvarchar(32)  NOT NULL,
    psiholog boolean  NOT NULL,
    fk_fakultetId int  NOT NULL,
    fk_centarId int  NOT NULL,
    fk_stepenId int  NOT NULL,
    fk_sertId int  NULL,
    CONSTRAINT Kandidat_pk PRIMARY KEY (kandidatId)
);

-- Table: Klijent
CREATE TABLE Klijent (
    klijentId int  NOT NULL,
    ime nvarchar(32)  NOT NULL,
    prezime nvarchar(32)  NOT NULL,
    datumRodj date  NOT NULL,
    pol varchar(1)  NOT NULL,
    email nvarchar(32)  NOT NULL,
    telefon nvarchar(16)  NOT NULL,
    datumPrijave date  NOT NULL,
    ranijePosetio boolean  NOT NULL,
    opisProblema longtext  NOT NULL,
    CONSTRAINT Klijent_pk PRIMARY KEY (klijentId)
);

-- Table: Kurs
CREATE TABLE Kurs (
    kursId int  NOT NULL,
    datumPromene date  NOT NULL,
    kursDInara decimal(12,2)  NOT NULL,
    fk_valutaId int  NOT NULL,
    CONSTRAINT Kurs_pk PRIMARY KEY (kursId,fk_valutaId)
);

-- Table: ObjavaPodataka
CREATE TABLE ObjavaPodataka (
    objavaId int  NOT NULL,
    datumObjave date  NOT NULL,
    primalac longblob  NOT NULL,
    fk_seansaId int  NOT NULL,
    razlog text  NOT NULL,
    CONSTRAINT ObjavaPodataka_pk PRIMARY KEY (objavaId)
);

-- Table: Oblast
CREATE TABLE Oblast (
    oblastId int  NOT NULL,
    naziv nvarchar(32)  NOT NULL,
    CONSTRAINT Oblast_pk PRIMARY KEY (oblastId)
);

-- Table: OblastTerapije
CREATE TABLE OblastTerapije (
    oblastId int  NOT NULL,
    ime nvarchar(32)  NOT NULL,
    CONSTRAINT OblastTerapije_pk PRIMARY KEY (oblastId)
);

-- Table: Placanje
CREATE TABLE Placanje (
    placanjeId int  NOT NULL,
    rata int  NOT NULL COMMENT '1 ili 2, ako je puna rata onda je uvek 1',
    iznos decimal(12,2)  NOT NULL,
    provizija int  NOT NULL,
    datum date  NOT NULL,
    nacinPlacanja nvarchar(16)  NOT NULL,
    Seansa_seansaId int  NOT NULL,
    fk_klijentId int  NOT NULL,
    fk_valutaId int  NOT NULL,
    svrha text  NOT NULL,
    CONSTRAINT Placanje_pk PRIMARY KEY (placanjeId)
);

-- Table: PsihoTest
CREATE TABLE PsihoTest (
    testId int  NOT NULL,
    naziv nvarchar(32)  NOT NULL,
    oblast nvarchar(32)  NOT NULL,
    cenaRSD decimal(12,2)  NOT NULL,
    CONSTRAINT PsihoTest_pk PRIMARY KEY (testId)
);

-- Table: Seansa
CREATE TABLE Seansa (
    seansaId int  NOT NULL,
    datum date  NOT NULL,
    vremePocetka time  NOT NULL,
    trajanje int  NOT NULL,
    besplatnaSeansa boolean  NOT NULL,
    fk_kandidatId int  NOT NULL,
    fk_klijentId int  NOT NULL,
    CONSTRAINT Seansa_pk PRIMARY KEY (seansaId)
);

-- Table: SeansaTest
CREATE TABLE SeansaTest (
    rezultat int  NOT NULL,
    fk_seansaId int  NOT NULL,
    seansaTestId int  NOT NULL,
    fk_psihoTestId int  NOT NULL,
    CONSTRAINT SeansaTest_pk PRIMARY KEY (seansaTestId)
);

-- Table: Sertifikat
CREATE TABLE Sertifikat (
    sertId int  NOT NULL,
    datumSert date  NOT NULL,
    fk_oblastId int  NOT NULL,
    CONSTRAINT Sertifikat_pk PRIMARY KEY (sertId)
);

-- Table: StepenStudija
CREATE TABLE StepenStudija (
    stepenId int  NOT NULL,
    naziv nvarchar(16)  NOT NULL,
    CONSTRAINT StepenStudija_pk PRIMARY KEY (stepenId)
);

-- Table: Supervizija
CREATE TABLE Supervizija (
    datumPocetka date  NOT NULL,
    datumKraja date  NULL,
    fk_kandidatId int  NOT NULL,
    fk_supervizorId int  NOT NULL,
    CONSTRAINT Supervizija_pk PRIMARY KEY (fk_kandidatId,fk_supervizorId)
);

-- Table: Univerzitet
CREATE TABLE Univerzitet (
    uniId int  NOT NULL,
    ime nvarchar(64)  NOT NULL,
    fk_usmrenjeId int  NOT NULL,
    CONSTRAINT Univerzitet_pk PRIMARY KEY (uniId)
);

-- Table: UzeUsmerenje
CREATE TABLE UzeUsmerenje (
    usmerenjeId int  NOT NULL,
    naziv nvarchar(32)  NOT NULL,
    CONSTRAINT UzeUsmerenje_pk PRIMARY KEY (usmerenjeId)
);

-- Table: UzeUsmerenje_Oblast
CREATE TABLE UzeUsmerenje_Oblast (
    fk_usmerenjeId int  NOT NULL,
    fk_oblastId int  NOT NULL,
    CONSTRAINT UzeUsmerenje_Oblast_pk PRIMARY KEY (fk_usmerenjeId,fk_oblastId)
);

-- Table: Valuta
CREATE TABLE Valuta (
    valutaId int  NOT NULL,
    sifra nvarchar(4)  NOT NULL,
    puniNaziv nvarchar(32)  NOT NULL,
    CONSTRAINT Valuta_pk PRIMARY KEY (valutaId)
);

-- foreign keys
-- Reference: CenaPoSatu_Seansa (table: CenaPoSatu)
ALTER TABLE CenaPoSatu ADD CONSTRAINT CenaPoSatu_Seansa FOREIGN KEY CenaPoSatu_Seansa (fk_seansaId)
    REFERENCES Seansa (seansaId);

-- Reference: Fakultet_Oblast_Fakultet (table: Fakultet_Oblast)
ALTER TABLE Fakultet_Oblast ADD CONSTRAINT Fakultet_Oblast_Fakultet FOREIGN KEY Fakultet_Oblast_Fakultet (fk_fakultetId)
    REFERENCES Fakultet (fakultetId);

-- Reference: Fakultet_Oblast_Oblast (table: Fakultet_Oblast)
ALTER TABLE Fakultet_Oblast ADD CONSTRAINT Fakultet_Oblast_Oblast FOREIGN KEY Fakultet_Oblast_Oblast (fk_oblastId)
    REFERENCES Oblast (oblastId);

-- Reference: Fakultet_Univerzitet (table: Fakultet)
ALTER TABLE Fakultet ADD CONSTRAINT Fakultet_Univerzitet FOREIGN KEY Fakultet_Univerzitet (fk_uniId)
    REFERENCES Univerzitet (uniId);

-- Reference: Kandidat_CentarZaObuku (table: Kandidat)
ALTER TABLE Kandidat ADD CONSTRAINT Kandidat_CentarZaObuku FOREIGN KEY Kandidat_CentarZaObuku (fk_centarId)
    REFERENCES CentarZaObuku (centarId);

-- Reference: Kandidat_Fakultet (table: Kandidat)
ALTER TABLE Kandidat ADD CONSTRAINT Kandidat_Fakultet FOREIGN KEY Kandidat_Fakultet (fk_fakultetId)
    REFERENCES Fakultet (fakultetId);

-- Reference: Kandidat_Sertifikat (table: Kandidat)
ALTER TABLE Kandidat ADD CONSTRAINT Kandidat_Sertifikat FOREIGN KEY Kandidat_Sertifikat (fk_sertId)
    REFERENCES Sertifikat (sertId);

-- Reference: Kandidat_StepenStudija (table: Kandidat)
ALTER TABLE Kandidat ADD CONSTRAINT Kandidat_StepenStudija FOREIGN KEY Kandidat_StepenStudija (fk_stepenId)
    REFERENCES StepenStudija (stepenId);

-- Reference: Kandidat_Supervizor (table: Supervizija)
ALTER TABLE Supervizija ADD CONSTRAINT Kandidat_Supervizor FOREIGN KEY Kandidat_Supervizor (fk_supervizorId)
    REFERENCES Kandidat (kandidatId);

-- Reference: Kurs_Valuta (table: Kurs)
ALTER TABLE Kurs ADD CONSTRAINT Kurs_Valuta FOREIGN KEY Kurs_Valuta (fk_valutaId)
    REFERENCES Valuta (valutaId);

-- Reference: ObjavaPodataka_Seansa (table: ObjavaPodataka)
ALTER TABLE ObjavaPodataka ADD CONSTRAINT ObjavaPodataka_Seansa FOREIGN KEY ObjavaPodataka_Seansa (fk_seansaId)
    REFERENCES Seansa (seansaId);

-- Reference: OblastTerapije_Sertifikat (table: Sertifikat)
ALTER TABLE Sertifikat ADD CONSTRAINT OblastTerapije_Sertifikat FOREIGN KEY OblastTerapije_Sertifikat (fk_oblastId)
    REFERENCES OblastTerapije (oblastId);

-- Reference: Placanje_Klijent (table: Placanje)
ALTER TABLE Placanje ADD CONSTRAINT Placanje_Klijent FOREIGN KEY Placanje_Klijent (fk_klijentId)
    REFERENCES Klijent (klijentId);

-- Reference: Placanje_Seansa (table: Placanje)
ALTER TABLE Placanje ADD CONSTRAINT Placanje_Seansa FOREIGN KEY Placanje_Seansa (Seansa_seansaId)
    REFERENCES Seansa (seansaId);

-- Reference: Placanje_Valuta (table: Placanje)
ALTER TABLE Placanje ADD CONSTRAINT Placanje_Valuta FOREIGN KEY Placanje_Valuta (fk_valutaId)
    REFERENCES Valuta (valutaId);

-- Reference: SeansaTest_PsihoTest (table: SeansaTest)
ALTER TABLE SeansaTest ADD CONSTRAINT SeansaTest_PsihoTest FOREIGN KEY SeansaTest_PsihoTest (fk_psihoTestId)
    REFERENCES PsihoTest (testId);

-- Reference: SeansaTest_Seansa (table: SeansaTest)
ALTER TABLE SeansaTest ADD CONSTRAINT SeansaTest_Seansa FOREIGN KEY SeansaTest_Seansa (fk_seansaId)
    REFERENCES Seansa (seansaId);

-- Reference: Seansa_BeleskeSeanse (table: BeleskeSeanse)
ALTER TABLE BeleskeSeanse ADD CONSTRAINT Seansa_BeleskeSeanse FOREIGN KEY Seansa_BeleskeSeanse (fk_seansaId)
    REFERENCES Seansa (seansaId);

-- Reference: Seansa_Kandidat (table: Seansa)
ALTER TABLE Seansa ADD CONSTRAINT Seansa_Kandidat FOREIGN KEY Seansa_Kandidat (fk_kandidatId)
    REFERENCES Kandidat (kandidatId);

-- Reference: Seansa_Klijent (table: Seansa)
ALTER TABLE Seansa ADD CONSTRAINT Seansa_Klijent FOREIGN KEY Seansa_Klijent (fk_klijentId)
    REFERENCES Klijent (klijentId);

-- Reference: Supervizor_Kandidat (table: Supervizija)
ALTER TABLE Supervizija ADD CONSTRAINT Supervizor_Kandidat FOREIGN KEY Supervizor_Kandidat (fk_kandidatId)
    REFERENCES Kandidat (kandidatId);

-- Reference: Univerzitet_UzeUsmerenje (table: Univerzitet)
ALTER TABLE Univerzitet ADD CONSTRAINT Univerzitet_UzeUsmerenje FOREIGN KEY Univerzitet_UzeUsmerenje (fk_usmrenjeId)
    REFERENCES UzeUsmerenje (usmerenjeId);

-- Reference: UzeUsmerenje_Oblast_Oblast (table: UzeUsmerenje_Oblast)
ALTER TABLE UzeUsmerenje_Oblast ADD CONSTRAINT UzeUsmerenje_Oblast_Oblast FOREIGN KEY UzeUsmerenje_Oblast_Oblast (fk_oblastId)
    REFERENCES Oblast (oblastId);

-- Reference: UzeUsmerenje_Oblast_UzeUsmerenje (table: UzeUsmerenje_Oblast)
ALTER TABLE UzeUsmerenje_Oblast ADD CONSTRAINT UzeUsmerenje_Oblast_UzeUsmerenje FOREIGN KEY UzeUsmerenje_Oblast_UzeUsmerenje (fk_usmerenjeId)
    REFERENCES UzeUsmerenje (usmerenjeId);

-- End of file.

