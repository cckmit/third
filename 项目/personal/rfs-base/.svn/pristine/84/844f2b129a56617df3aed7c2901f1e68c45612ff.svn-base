-----------------------------------------------------------------
drop index BIZ_MSG_UIX;

alter table BIZ_MSG
   drop primary key cascade;

drop table BIZ_MSG cascade constraints;

/*==============================================================*/
/* Table: BIZ_MSG                                               */
/*==============================================================*/
create table BIZ_MSG  (
   ID                   NUMBER(19)                      not null,
   TP                   NUMBER(10)                     default 0,
   STATUS               NUMBER(3)                      default 0,
   REMARK               VARCHAR2(500),
   CREATOR              NUMBER(19),
   CTIME                TIMESTAMP,
   MODIFIER             NUMBER(19),
   MTIME                TIMESTAMP,
   UNIQUE_ID            VARCHAR2(50)                    not null,
   MSG                  VARCHAR2(1000)                  not null,
   DESCRIPTION          VARCHAR2(500),
   PARAMS               VARCHAR2(500)
);

comment on table BIZ_MSG is
'业务消息';

alter table BIZ_MSG
   add constraint PK_BIZ_MSG primary key (ID);

/*==============================================================*/
/* Index: BIZ_MSG_UIX                                           */
/*==============================================================*/
create unique index BIZ_MSG_UIX on BIZ_MSG (
   UNIQUE_ID ASC
);