alter table BASE_TASK_BEAN
   drop primary key cascade;

drop table BASE_TASK_BEAN cascade constraints;

/*==============================================================*/
/* Table: BASE_TASK_BEAN                                        */
/*==============================================================*/
create table BASE_TASK_BEAN  (
   ID                   NUMBER(19)                      not null,
   TP                   NUMBER(10)                     default 0,
   STATUS               NUMBER(3)                      default 0,
   REMARK               VARCHAR2(500),
   CREATOR              NUMBER(19),
   CTIME                TIMESTAMP,
   MODIFIER             NUMBER(19),
   MTIME                TIMESTAMP,
   BASE_TASK            VARCHAR2(255)                   not null,
   PARAM_TYPE           VARCHAR2(255),
   PARAM_VALUE          VARCHAR2(4000),
   EXEC_TIME            TIMESTAMP,
   EXEC_NUM             NUMBER(10)                     default 0 not null,
   SCHEDULE_EXEC_TIME   TIMESTAMP,
   RESULT_              VARCHAR(4000),
   TASK_INSTANCE        BLOB
);

comment on table BASE_TASK_BEAN is
'基本任务';

comment on column BASE_TASK_BEAN.BASE_TASK is
'任务类名。';

comment on column BASE_TASK_BEAN.PARAM_TYPE is
'参数类型的类名。';

comment on column BASE_TASK_BEAN.PARAM_VALUE is
'使用JSON格式序列化。';

comment on column BASE_TASK_BEAN.EXEC_TIME is
'该任务实际执行的时间。';

comment on column BASE_TASK_BEAN.EXEC_NUM is
'当该任务执行失败时，新建同样的任务，新建的任务该值在原值上+1';

comment on column BASE_TASK_BEAN.SCHEDULE_EXEC_TIME is
'期望何时执行。可为空，为空时由系统自己调度下次执行时间。';

comment on column BASE_TASK_BEAN.TASK_INSTANCE is
'任务实例的序列化数据';

alter table BASE_TASK_BEAN
   add constraint PK_BASE_TASK_BEAN primary key (ID);

   
   
   
   
   
   
 -- 2014-05-08
 -- 业务定义是否可以编辑办事指引
 alter table TASK_DEF add HAS_GUIDE number(10) default 0;
 