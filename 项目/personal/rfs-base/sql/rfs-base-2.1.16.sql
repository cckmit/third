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
'��������';

comment on column BASE_TASK_BEAN.BASE_TASK is
'����������';

comment on column BASE_TASK_BEAN.PARAM_TYPE is
'�������͵�������';

comment on column BASE_TASK_BEAN.PARAM_VALUE is
'ʹ��JSON��ʽ���л���';

comment on column BASE_TASK_BEAN.EXEC_TIME is
'������ʵ��ִ�е�ʱ�䡣';

comment on column BASE_TASK_BEAN.EXEC_NUM is
'��������ִ��ʧ��ʱ���½�ͬ���������½��������ֵ��ԭֵ��+1';

comment on column BASE_TASK_BEAN.SCHEDULE_EXEC_TIME is
'������ʱִ�С���Ϊ�գ�Ϊ��ʱ��ϵͳ�Լ������´�ִ��ʱ�䡣';

comment on column BASE_TASK_BEAN.TASK_INSTANCE is
'����ʵ�������л�����';

alter table BASE_TASK_BEAN
   add constraint PK_BASE_TASK_BEAN primary key (ID);

   
   
   
   
   
   
 -- 2014-05-08
 -- ҵ�����Ƿ���Ա༭����ָ��
 alter table TASK_DEF add HAS_GUIDE number(10) default 0;
 