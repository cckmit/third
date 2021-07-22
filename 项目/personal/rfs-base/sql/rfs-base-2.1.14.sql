
alter table BIZ_DUTYERS_CONFIG
   drop primary key cascade;

drop table BIZ_DUTYERS_CONFIG cascade constraints;

/*==============================================================*/
/* Table: BIZ_DUTYERS_CONFIG                                    */
/*==============================================================*/
create table BIZ_DUTYERS_CONFIG  (
   ID                   NUMBER(19)                      not null,
   TP                   NUMBER(10)                     default 0,
   STATUS               NUMBER(3)                      default 0,
   REMARK               VARCHAR2(500),
   CREATOR              NUMBER(19),
   CTIME                TIMESTAMP,
   MODIFIER             NUMBER(19),
   MTIME                TIMESTAMP,
   DEF_OBJ_TYPE         NUMBER(10)                      not null,
   DEF_ID               NUMBER(10)                      not null,
   DISPLAY_ORDER        NUMBER(10)                      not null,
   DUTY_ENTITY_ID       NUMBER(19),
   DUTY_ENTITY_NAME     VARCHAR2(200),
   DUTY_DEPT_ID         NUMBER(19),
   DUTY_DEPT_NAME       VARCHAR2(200),
   DUTYER_ID            NUMBER(19),
   DUTYER_NAME          VARCHAR2(100),
   DUTYER_LEADER1_ID    NUMBER(19),
   DUTYER_LEADER1_NAME  VARCHAR2(100),
   DUTYER_LEADER2_ID    NUMBER(19),
   DUTYER_LEADER2_NAME  VARCHAR2(100)
);

comment on table BIZ_DUTYERS_CONFIG is
'业务三级责任人配置表';

alter table BIZ_DUTYERS_CONFIG
   add constraint PK_BIZ_DUTYERS_CONFIG primary key (ID);


alter table TASK add DUTY_ENTITY_ID NUMBER(19);
alter table TASK add DUTY_ENTITY_NAME VARCHAR2(200);
alter table TASK add DUTY_DEPT_ID NUMBER(19);
alter table TASK add DUTY_DEPT_NAME VARCHAR2(200);
alter table TASK add DUTYER_LEADER1_ID NUMBER(19);
alter table TASK add DUTYER_LEADER1_NAME VARCHAR2(100);
alter table TASK add DUTYER_LEADER2_ID NUMBER(19);
alter table TASK add DUTYER_LEADER2_NAME VARCHAR2(100);

alter table TASK_DEF add DUTYER_TYPE NUMBER(10) default 0;

alter table WORK_DEF add DUTYER_TYPE NUMBER(10) default 0;

alter table WORK add DUTYER_ID NUMBER(19);
alter table WORK add DUTYER_NAME VARCHAR2(100);

alter table WORK add DUTY_ENTITY_ID NUMBER(19);
alter table WORK add DUTY_ENTITY_NAME VARCHAR2(200);
alter table WORK add DUTY_DEPT_ID NUMBER(19);
alter table WORK add DUTY_DEPT_NAME VARCHAR2(200);

alter table WORK add DUTYER_LEADER1_ID NUMBER(19);
alter table WORK add DUTYER_LEADER1_NAME VARCHAR2(100);
alter table WORK add DUTYER_LEADER2_ID NUMBER(19);
alter table WORK add DUTYER_LEADER2_NAME VARCHAR2(100);



alter table PROGRESS add BELONG_TIME TIMESTAMP;

comment on column PROGRESS.BELONG_TIME is
'所属月份/所属时间，用于统计。如果是所属月份，则取当月1号。';

alter table ISSUE add BELONG_TIME TIMESTAMP;

comment on column ISSUE.BELONG_TIME is
'所属月份/所属时间，用于统计。如果是所属月份，则取当月1号。';



--2012-11-29
alter table TASK_INVALID add DUTY_ENTITY_ID NUMBER(19);
alter table TASK_INVALID add DUTY_ENTITY_NAME VARCHAR2(200);
alter table TASK_INVALID add DUTY_DEPT_ID NUMBER(19);
alter table TASK_INVALID add DUTY_DEPT_NAME VARCHAR2(200);
alter table TASK_INVALID add DUTYER_LEADER1_ID NUMBER(19);
alter table TASK_INVALID add DUTYER_LEADER1_NAME VARCHAR2(100);
alter table TASK_INVALID add DUTYER_LEADER2_ID NUMBER(19);
alter table TASK_INVALID add DUTYER_LEADER2_NAME VARCHAR2(100);



alter table WORK_INVALID add DUTYER_ID NUMBER(19);
alter table WORK_INVALID add DUTYER_NAME VARCHAR2(100);

alter table WORK_INVALID add DUTY_ENTITY_ID NUMBER(19);
alter table WORK_INVALID add DUTY_ENTITY_NAME VARCHAR2(200);
alter table WORK_INVALID add DUTY_DEPT_ID NUMBER(19);
alter table WORK_INVALID add DUTY_DEPT_NAME VARCHAR2(200);

alter table WORK_INVALID add DUTYER_LEADER1_ID NUMBER(19);
alter table WORK_INVALID add DUTYER_LEADER1_NAME VARCHAR2(100);
alter table WORK_INVALID add DUTYER_LEADER2_ID NUMBER(19);
alter table WORK_INVALID add DUTYER_LEADER2_NAME VARCHAR2(100);


--2012-12-25
alter table PROGRESS add ISSUE1 VARCHAR2(2000);
alter table PROGRESS add ISSUE2 VARCHAR2(2000);

--2013-03-25
alter table info_config  add ref_task_type NUMBER(10) default 0;





/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2013-6-25 14:53:57                           */
/*==============================================================*/

alter table ORG_CAT_TO_ORG_GROUP
   drop constraint FK_177;

alter table ORG_CAT_TO_ORG_GROUP
   drop constraint FK_178;

alter table ORG_CAT_TO_ROLE
   drop constraint FK_175;

alter table ORG_CAT_TO_ROLE
   drop constraint FK_176;

alter table ORG_CAT
   drop primary key cascade;

drop table ORG_CAT cascade constraints;

drop index IX_ORG_CAT_ORG_GROUP;

alter table ORG_CAT_TO_ORG_GROUP
   drop primary key cascade;

drop table ORG_CAT_TO_ORG_GROUP cascade constraints;

drop index IX_ORG_CAT_ROLE;

alter table ORG_CAT_TO_ROLE
   drop primary key cascade;

drop table ORG_CAT_TO_ROLE cascade constraints;

/*==============================================================*/
/* Table: ORG_CAT                                               */
/*==============================================================*/
create table ORG_CAT  (
   ID                   NUMBER(19)                      not null,
   TP                   NUMBER(10)                     default 0,
   STATUS               NUMBER(3)                      default 0,
   REMARK               VARCHAR2(500),
   CREATOR              NUMBER(19),
   CTIME                TIMESTAMP,
   MODIFIER             NUMBER(19),
   MTIME                TIMESTAMP,
   CODE                 VARCHAR2(60),
   SN                   VARCHAR2(100),
   NAME                 VARCHAR2(200)                   not null,
   ABBR                 VARCHAR2(200),
   DESCRIPTION          VARCHAR2(1000),
   DISPLAY_ORDER        NUMBER(10)                     default 0 not null
);

comment on table ORG_CAT is
'单位类别，机构';

alter table ORG_CAT
   add constraint PK_ORG_CAT primary key (ID);

/*==============================================================*/
/* Table: ORG_CAT_TO_ORG_GROUP                                  */
/*==============================================================*/
create table ORG_CAT_TO_ORG_GROUP  (
   ID                   NUMBER(19)                      not null,
   ORG_CAT_ID           NUMBER(19)                      not null,
   ORG_GROUP_ID         NUMBER(19)                      not null,
   TP                   NUMBER(10)                     default 0 not null,
   STATUS               NUMBER(3)                      default 0 not null,
   CREATOR              NUMBER(19),
   CTIME                TIMESTAMP,
   MODIFIER             NUMBER(19),
   MTIME                TIMESTAMP,
   DELETER              NUMBER(19),
   DTIME                TIMESTAMP
);

comment on table ORG_CAT_TO_ORG_GROUP is
'单位类别（机构）分配的单位分组';

alter table ORG_CAT_TO_ORG_GROUP
   add constraint PK_ORG_CAT_TO_ORG_GROUP primary key (ID);

/*==============================================================*/
/* Index: IX_ORG_CAT_ORG_GROUP                                  */
/*==============================================================*/
create unique index IX_ORG_CAT_ORG_GROUP on ORG_CAT_TO_ORG_GROUP (
   ORG_CAT_ID ASC,
   ORG_GROUP_ID ASC
);

/*==============================================================*/
/* Table: ORG_CAT_TO_ROLE                                       */
/*==============================================================*/
create table ORG_CAT_TO_ROLE  (
   ID                   NUMBER(19)                      not null,
   ORG_CAT_ID           NUMBER(19)                      not null,
   ROLE_ID              NUMBER(19)                      not null,
   TP                   NUMBER(10)                     default 0 not null,
   STATUS               NUMBER(3)                      default 0 not null,
   CREATOR              NUMBER(19),
   CTIME                TIMESTAMP,
   MODIFIER             NUMBER(19),
   MTIME                TIMESTAMP,
   DELETER              NUMBER(19),
   DTIME                TIMESTAMP
);

comment on table ORG_CAT_TO_ROLE is
'单位类别（机构）可分配的角色';

alter table ORG_CAT_TO_ROLE
   add constraint PK_ORG_CAT_TO_ROLE primary key (ID);

/*==============================================================*/
/* Index: IX_ORG_CAT_ROLE                                       */
/*==============================================================*/
create unique index IX_ORG_CAT_ROLE on ORG_CAT_TO_ROLE (
   ORG_CAT_ID ASC,
   ROLE_ID ASC
);

alter table ORG_CAT_TO_ORG_GROUP
   add constraint FK_177 foreign key (ORG_CAT_ID)
      references ORG_CAT (ID)
      on delete cascade;

alter table ORG_CAT_TO_ORG_GROUP
   add constraint FK_178 foreign key (ORG_GROUP_ID)
      references ORG_GROUP (ID)
      on delete cascade;

alter table ORG_CAT_TO_ROLE
   add constraint FK_175 foreign key (ORG_CAT_ID)
      references ORG_CAT (ID)
      on delete cascade;

alter table ORG_CAT_TO_ROLE
   add constraint FK_176 foreign key (ROLE_ID)
      references SEC_GROUPS (ID)
      on delete cascade;

      
      
-- Org
alter table ORG add    CATEGORY_ID          NUMBER(19);
alter table ORG add   CATEGORY_NAME        VARCHAR2(200);
alter table ORG
   add constraint FK_179 foreign key (CATEGORY_ID)
      references ORG_CAT (ID);

      

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
