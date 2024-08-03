DROP TABLE IF EXISTS COORDINATES CASCADE;
DROP TABLE IF EXISTS ORGANIZATION_TYPE CASCADE;
DROP TABLE IF EXISTS UNIT_OF_MEASURE CASCADE;
DROP TABLE IF EXISTS ADDRESS CASCADE;
DROP TABLE IF EXISTS ORGANIZATION CASCADE;
DROP TABLE IF EXISTS PRODUCT CASCADE;

CREATE TABLE IF NOT EXISTS COORDINATES
(
    ID SERIAL PRIMARY KEY,
    X  DOUBLE PRECISION NOT NULL,
    Y  DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS ORGANIZATION_TYPE
(
    ID   INTEGER PRIMARY KEY,
    NAME VARCHAR(32) NOT NULL
);

INSERT INTO ORGANIZATION_TYPE
VALUES (0, 'COMMERCIAL'),
       (1, 'GOVERNMENT'),
       (2, 'TRUST'),
       (3, 'OPEN_JOINT_STOCK_COMPANY')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS UNIT_OF_MEASURE
(
    ID   INTEGER PRIMARY KEY,
    NAME VARCHAR(32) NOT NULL
);

INSERT INTO UNIT_OF_MEASURE
VALUES (0, 'CENTIMETERS'),
       (1, 'PCS'),
       (2, 'LITERS'),
       (3, 'GRAMS')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS ADDRESS
(
    ID       SERIAL PRIMARY KEY,
    STREET   VARCHAR(256)                                 NOT NULL,
    ZIP_CODE VARCHAR(32) CHECK ( LENGTH(ZIP_CODE) <= 21 ) NOT NULL
);

CREATE TABLE IF NOT EXISTS ORGANIZATION
(
    ID                   BIGSERIAL PRIMARY KEY,
    NAME                 VARCHAR(256) CHECK ( LENGTH(NAME) > 0 )      NOT NULL,
    ANNUAL_TURNOVER      DOUBLE PRECISION CHECK ( ANNUAL_TURNOVER > 0.0 ),
    ORGANIZATION_TYPE_ID INTEGER REFERENCES ORGANIZATION_TYPE ON DELETE CASCADE,
    ADDRESS_ID           INTEGER REFERENCES ADDRESS ON DELETE CASCADE NOT NULL
);

CREATE TABLE IF NOT EXISTS PRODUCT
(
    ID                 SERIAL PRIMARY KEY,
    NAME               VARCHAR(128) CHECK ( LENGTH(NAME) > 0 )           NOT NULL,
    COORDINATES_ID     INTEGER REFERENCES COORDINATES                    NOT NULL,
    CREATION_DATE      DATE                                              NOT NULL,
    PRICE              REAL CHECK ( PRICE > 0.0 ),
    UNIT_OF_MEASURE_ID INTEGER REFERENCES UNIT_OF_MEASURE                NOT NULL,
    MANUFACTURER       INTEGER REFERENCES ORGANIZATION ON DELETE CASCADE NOT NULL,
    CREATOR_ID         INTEGER REFERENCES APPLICATION_USERS              NOT NULL
);


