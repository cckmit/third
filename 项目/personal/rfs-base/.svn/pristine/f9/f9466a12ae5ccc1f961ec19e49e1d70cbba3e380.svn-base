/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2012-8-9 17:45:24                            */
/*==============================================================*/


alter table INFO_CONFIG
   drop primary key cascade;

drop table INFO_CONFIG cascade constraints;

alter table INFO_DETAIL
   drop primary key cascade;

drop table INFO_DETAIL cascade constraints;

alter table INFO_STAT
   drop primary key cascade;

drop table INFO_STAT cascade constraints;

/*==============================================================*/
/* Table: INFO_CONFIG                                           */
/*==============================================================*/
create table INFO_CONFIG  (
   ID                   NUMBER(19)                      not null,
   TP                   NUMBER(10)                     default 0,
   STATUS               NUMBER(3)                      default 0,
   REMARK               VARCHAR2(500),
   CREATOR              NUMBER(19),
   CTIME                TIMESTAMP,
   MODIFIER             NUMBER(19),
   MTIME                TIMESTAMP,
   NAME                 VARCHAR2(100)                   not null,
   OBJECT_TYPE          NUMBER(10)                      not null,
   VALUE                VARCHAR2(1000)                  not null,
   TAG                  NUMBER(6)                       not null,
   ROLE_ID              NUMBER(19)                      not null,
   ROLE_NAME            VARCHAR2(200)                   not null,
   DUTYENTITYID         NUMBER(19)                      not null,
   DUTYENTITYNAME       VARCHAR2(200)                   not null,
   DISPLAY_ORDER        NUMBER(10)                     default 0 not null
);

comment on table INFO_CONFIG is
'用于统计的信息项配置、定义';

comment on column INFO_CONFIG.ROLE_ID is
'用于信息项统计的角色ID，跟系统角色无直接关联关系。';

comment on column INFO_CONFIG.ROLE_NAME is
'用于信息项统计的角色名称，跟系统角色无直接关联关系。';

alter table INFO_CONFIG
   add constraint PK_INFO_CONFIG primary key (ID);

/*==============================================================*/
/* Table: INFO_DETAIL                                           */
/*==============================================================*/
create table INFO_DETAIL  (
   ID                   NUMBER(19)                      not null,
   TP                   NUMBER(10)                     default 0,
   STATUS               NUMBER(3)                      default 0,
   REMARK               VARCHAR2(500),
   CREATOR              NUMBER(19),
   CTIME                TIMESTAMP,
   MODIFIER             NUMBER(19),
   MTIME                TIMESTAMP,
   OBJECT_TYPE          NUMBER(10)                     default 0 not null,
   OBJECT_ID            NUMBER(19)                      not null,
   INFOCONFIGID         NUMBER(19)                      not null,
   DUTYENTITYID         NUMBER(19)                      not null,
   DUTYENTITYNAME       VARCHAR2(200)                   not null
);

comment on table INFO_DETAIL is
'信息项统计明细';

alter table INFO_DETAIL
   add constraint PK_INFO_DETAIL primary key (ID);

/*==============================================================*/
/* Table: INFO_STAT                                             */
/*==============================================================*/
create table INFO_STAT  (
   ID                   NUMBER(19)                      not null,
   TP                   NUMBER(10)                     default 0,
   STATUS               NUMBER(3)                      default 0,
   REMARK               VARCHAR2(500),
   CREATOR              NUMBER(19),
   CTIME                TIMESTAMP,
   MODIFIER             NUMBER(19),
   MTIME                TIMESTAMP,
   INFOCONFIGID         NUMBER(19)                      not null,
   COMPLETEINFOCOUNT    NUMBER(10)                     default 0 not null,
   INCOMPLETEINFOCOUNT  NUMBER(10)                     default 0 not null,
   OBJECT_TYPE          NUMBER(10)                     default 0 not null,
   DUTYENTITYID         NUMBER(19)                      not null,
   DUTYENTITYNAME       VARCHAR2(200)                   not null
);

comment on table INFO_STAT is
'信息项统计表';

alter table INFO_STAT
   add constraint PK_INFO_STAT primary key (ID);

