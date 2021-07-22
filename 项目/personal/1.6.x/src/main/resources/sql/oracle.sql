-------------------------------------------------------
-- Oracle database schema
--
-- Oracle 10g
-------------------------------------------------------


CREATE TABLE SEC_USERS (
USERNAME VARCHAR2(50) NOT NULL PRIMARY KEY,
PASSWORD VARCHAR2(50) NOT NULL,
ENABLED NUMBER(1) NOT NULL,
ACCOUNT_EXPIRE_TIME DATE,
ACCOUNT_NON_LOCKED NUMBER(1),
CREDENTIALS_EXPIRE_TIME DATE,
LAST_LOGIN_TIME DATE,
LOGIN_COUNT NUMBER(10,0) DEFAULT 0,
TRY_LOGIN_COUNT NUMBER(10,0) DEFAULT 0,
USERID NUMBER(19,0) NOT NULL,
LOGIN_ADDRESS VARCHAR2(150),
ONLINE_STATUS NUMBER(2) DEFAULT 0,
MTIME DATE,
CTIME DATE,
CREATOR VARCHAR2(50),
MODIFIER VARCHAR2(50)
);
 

CREATE TABLE SEC_UA(
ID NUMBER(19,0) NOT NULL PRIMARY KEY,
USERNAME VARCHAR2(50) NOT NULL,
AUTHORITY VARCHAR2(50) NOT NULL,
FOREIGN KEY(USERNAME) REFERENCES SEC_USERS(USERNAME) ON DELETE CASCADE
);   

CREATE UNIQUE INDEX IX_UA ON SEC_UA(USERNAME, AUTHORITY);



CREATE TABLE SEC_GROUPS(
ID NUMBER(19,0) NOT NULL PRIMARY KEY,
NAME VARCHAR2(50) NOT NULL
);

CREATE UNIQUE INDEX IX_GOUPS_NAME ON SEC_GROUPS(NAME);


CREATE TABLE SEC_GA(
ID NUMBER(19,0) NOT NULL PRIMARY KEY,
GROUPID NUMBER(19,0) NOT NULL,
AUTHORITY VARCHAR2(50) NOT NULL,
FOREIGN KEY(GROUPID) REFERENCES SEC_GROUPS(ID) ON DELETE CASCADE
);
CREATE UNIQUE INDEX IX_GA ON SEC_GA(GROUPID, AUTHORITY);

CREATE TABLE SEC_GM(
ID NUMBER(19,0) NOT NULL PRIMARY KEY,
GROUPID NUMBER(19,0) NOT NULL,
USERNAME VARCHAR2(50) NOT NULL,
FOREIGN KEY(GROUPID) REFERENCES SEC_GROUPS(ID) ON DELETE CASCADE,
FOREIGN KEY(USERNAME) REFERENCES SEC_USERS(USERNAME) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IX_GM ON SEC_GM(GROUPID, USERNAME);







CREATE TABLE T_SYS_PROPERTY(
PROPNAME VARCHAR2(500) NOT NULL, 
PROPVALUE VARCHAR2(2000) NOT NULL,
PRIMARY KEY(PROPNAME)
);


CREATE TABLE T_SYS_ID(
ID VARCHAR2(32),
CURR NUMBER(19,0) DEFAULT 0,
STEP NUMBER(10,0) DEFAULT 1,
DESCRIPTION VARCHAR2(50),
PRIMARY KEY(ID)
);


CREATE TABLE T_SYS_ATTACHMENT(
ID NUMBER(19,0),
FILE_NAME VARCHAR2(100) NOT NULL,
CONTENT_TYPE VARCHAR2(100),
FILE_TYPE VARCHAR2(20) NOT NULL,
FILE_SIZE NUMBER(19,0) DEFAULT 0,
FETCH_COUNT NUMBER(10,0) DEFAULT 0,
STORE_NAME VARCHAR2(100) DEFAULT 'store0',
STORE_TIME DATE,
LOCATION VARCHAR2(200),
READABLE_FORMAT VARCHAR2(20),
PROTECTED_FORMAT VARCHAR2(20),
PRIMARY KEY(ID)
);


CREATE TABLE SEC_USERS_PROP(
USERNAME VARCHAR2(50) NOT NULL,
PROPNAME VARCHAR2(50) NOT NULL,
PROPVALUE VARCHAR2(200) NOT NULL,
PRIMARY KEY(USERNAME, PROPNAME),
FOREIGN KEY(USERNAME) REFERENCES SEC_USERS(USERNAME) ON DELETE CASCADE
);


CREATE TABLE SYS_PROPS_LOCALE(
  NAME         VARCHAR2(100)    NOT NULL,
  PROPVALUE    VARCHAR2(4000)   NOT NULL,
  LOCALE       VARCHAR2(100)    NOT NULL,
  PRIMARY KEY (NAME, LOCALE)
);

CREATE TABLE SYS_PROPS (
  NAME        VARCHAR2(100)  NOT NULL,
  PROPVALUE   VARCHAR2(4000) NOT NULL,
  PRIMARY KEY (NAME)
);


CREATE TABLE SYS_MODULES(
NAME VARCHAR2(100) NOT NULL,
CREATIONTIME NUMBER(19) NOT NULL,
MODIFICATIONTIME NUMBER(19) NOT NULL,
DATA BLOB NULL,
PRIMARY KEY(NAME)
);
