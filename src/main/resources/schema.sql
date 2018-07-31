CREATE TABLE IF NOT EXISTS Organization (
    id        INTEGER PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    inn       VARCHAR(10) NOT NULL,
    kpp       VARCHAR(9) NOT NULL,
    address   VARCHAR(100) NOT NULL,
    phone     VARCHAR(20) NOT NULL,
    is_active BOOLEAN NOT NULL,
    version   INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS Office (
    id       INTEGER PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(50) NOT NULL,
    org_id    INTEGER NOT NULL,
    address  VARCHAR(100) NOT NULL,
    phone    VARCHAR(20) NOT NULL,
    is_active BOOLEAN NOT NULL,
    version  INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS User (
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    first_name       VARCHAR(50) NOT NULL,
    last_name        VARCHAR(50) NOT NULL,
    middle_name      VARCHAR(50),
    office_id        INTEGER NOT NULL,
    position         VARCHAR(50) NOT NULL,
    phone            VARCHAR(20) NOT NULL,
    doc_code         INTEGER NOT NULL,
    doc_number       VARCHAR(20) NOT NULL,
    doc_date         DATE NOT NULL,
    citizenship_code INTEGER NOT NULL,
    is_identified    BOOLEAN NOT NULL,
    version          INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS Doc (
    code INTEGER PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Country (
    code INTEGER PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE INDEX IX_Office_Organization_Id ON Office (org_id);
ALTER TABLE Office ADD FOREIGN KEY (org_id) REFERENCES Organization (id);

CREATE INDEX IX_User_Office_Id ON User (office_id);
ALTER TABLE User ADD FOREIGN KEY (office_id) REFERENCES Office (id);

CREATE INDEX IX_User_Doc_Code ON User (doc_code);
ALTER TABLE User ADD FOREIGN KEY (doc_code) REFERENCES Doc (code);

CREATE INDEX IX_User_Country_Code ON User (citizenship_code);
ALTER TABLE User ADD FOREIGN KEY (citizenship_code) REFERENCES Country (code);


/* Schema from old project below */

CREATE TABLE IF NOT EXISTS Person (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    age        INTEGER  NOT NULL
);

CREATE TABLE IF NOT EXISTS House (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    address    VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Person_House (
    person_id   INTEGER  NOT NULL,
    house_id    INTEGER  NOT NULL,

    PRIMARY KEY (person_id, house_id)
);

CREATE INDEX IX_Person_House_Id ON Person_House (house_id);
ALTER TABLE Person_House ADD FOREIGN KEY (house_id) REFERENCES House(id);

CREATE INDEX IX_House_Person_Id ON Person_House (person_id);
ALTER TABLE Person_House ADD FOREIGN KEY (person_id) REFERENCES Person(id);