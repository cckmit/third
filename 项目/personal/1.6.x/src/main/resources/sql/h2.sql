CREATE TABLE SYS_PROPS_LOCALE(
  NAME         VARCHAR(100)    NOT NULL,
  PROPVALUE    VARCHAR(4000)   NOT NULL,
  LOCALE       VARCHAR(100)    NOT NULL,
  PRIMARY KEY (NAME, LOCALE)
);

CREATE TABLE SYS_PROPS (
  NAME        VARCHAR(100)  NOT NULL,
  PROPVALUE   VARCHAR(4000) NOT NULL,
  PRIMARY KEY (NAME)
);

CREATE TABLE SYS_MODULES(
NAME VARCHAR(100) NOT NULL,
CREATIONTIME BIGINT NOT NULL,
MODIFICATIONTIME BIGINT NOT NULL,
DATA BLOB,
PRIMARY KEY(NAME)
);



-------------------
alter table SEC_GROUPS_PROP drop constraint FK2EECE7E0EB8A06E4;
alter table SEC_USERS_PROP drop constraint FK7B9AEC287D4A71F6;
drop table SEC_GA if exists;
drop table SEC_GM if exists;
drop table SEC_GROUPS if exists;
drop table SEC_GROUPS_PROP if exists;
drop table SEC_UA if exists;
drop table SEC_USERS if exists;
drop table SEC_USERS_PROP if exists;
drop table T_SYS_ATTACHMENT if exists;
drop table T_SYS_ID if exists;

create table SEC_GA (ID bigint not null, 
	GROUPID bigint not null, 
	AUTHORITY varchar(50) not null, 
	primary key (ID));

create table SEC_GM (
	ID bigint not null, 
	GROUPID bigint not null, 
	USERNAME varchar(50) not null, 
	primary key (ID));

create table SEC_GROUPS (
	ID bigint not null, 
	NAME varchar(50) not null, 
	primary key (ID));

create table SEC_GROUPS_PROP (
	GROUPID bigint not null, 
	PROPVALUE varchar(200), 
	PROPNAME varchar(50) not null, 
	primary key (GROUPID, PROPNAME));

create table SEC_UA (
	ID bigint not null, 
	USERNAME varchar(50) not null, 
	AUTHORITY varchar(50) not null, 
	primary key (ID));

create table SEC_USERS (
	USERNAME varchar(50) not null, 
	PASSWORD varchar(50) not null, 
	ENABLED boolean not null, 
	ACCOUNT_EXPIRE_TIME timestamp, 
	ACCOUNT_NON_LOCKED boolean, 
	CREDENTIALS_EXPIRE_TIME timestamp, 
	LAST_LOGIN_TIME timestamp, 
	LOGIN_TIME timestamp, 
	LOGIN_COUNT integer default 0, 
	TRY_LOGIN_COUNT integer default 0, 
	USERID bigint not null, 
	LOGIN_ADDRESS varchar(150), 
	ONLINE_STATUS integer default 0, 
	CTIME timestamp, 
	CREATOR varchar(50), 
	MTIME timestamp, 
	MODIFIER varchar(50), 
	primary key (USERNAME));

create table SEC_USERS_PROP (
	USERNAME varchar(50) not null, 
	PROPVALUE varchar(200), 
	PROPNAME varchar(50) not null, 
	primary key (USERNAME, PROPNAME));

create table T_SYS_ATTACHMENT (
	ID bigint not null, 
	FILE_NAME varchar(100) not null, 
	CONTENT_TYPE varchar(100), 
	FILE_TYPE varchar(20) not null, 
	FILE_SIZE bigint not null default 0, 
	FETCH_COUNT integer default 0, 
	STORE_NAME varchar(100) DEFAULT 'store0', 
	STORE_TIME timestamp not null, 
	LOCATION varchar(200), 
	READABLE_FORMAT varchar(20), 
	PROTECTED_FORMAT varchar(20), 
	USERID bigint not null, primary key (ID));

create table T_SYS_ID (
	ID varchar(32) not null, 
	CURR bigint not null default 0, 
	STEP integer not null default 1, 
	DESCRIPTION varchar(50), 
	primary key (ID));


alter table SEC_GROUPS_PROP add constraint FK2EECE7E0EB8A06E4 foreign key (GROUPID) references SEC_GROUPS;
alter table SEC_USERS_PROP add constraint FK7B9AEC287D4A71F6 foreign key (USERNAME) references SEC_USERS;

