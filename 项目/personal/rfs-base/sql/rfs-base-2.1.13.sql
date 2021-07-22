
-- 监察规则（督查规则）类型
alter table RISK_RULE add RULE_TYPE NUMBER(10) default 1;

-- 监察规则（督查规则）类型
alter table RISK add RULE_TYPE NUMBER(10) default 1;

-- 监察规则（督查规则）类型
alter table risk_invalid add RULE_TYPE NUMBER(10) default 1;

--修改caution表字段的长度跟 risk,riskRule中字段长度一致
alter table caution modify JURAL_LIMIT varchar2(4000);