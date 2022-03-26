package com.tang.webserviceServer.utils;

import java.util.HashMap;
import java.util.Map;

import static com.tang.webserviceServer.utils.Constants.TableName.*;


public class Constants {


    public final static Map<String, String> ENTITY_SQL = new HashMap<String, String>() {
        {
            put(TNN_WSINTER_LOG, "INSERT INTO `tnn_wsinter_log`(`JKMC`,`URL`,`JKID`,`OPERATOR`,`PARAMS`,`RESULT`,`SUCCESS`) VALUES (?,?,?,?,?,?,?)");
            put(QUERY_PAY," select '450100' xzqh,id hzlsh,jgzzh,htcode,'1'hzlx,0 sffczh,DKYHMC dfyhmc,DKYHLHH dfyhlhh,DKZH dfzh,DKZHMC dfhm,DKJE bchke from tnn_release_apply where PROCESSED = 0\n" +
                    "  union all\n" +
                    "  select '450100' xzqh,id hzlsh,jgzzh,htcode,'1'hzlx,0 sffczh,DFYHMC dfyhmc,DFYHLHH dfyhlhh,DFZH dfzh,DFHM dfhm,BCHKE bchke from tnn_trans_rent where PROCESSED = 0");
            put(UPDATE_RELEASE_APPLY, "UPDATE `tnn_release_apply` set PROCESSED = 1 where id = ");
            put(UPDATE_TRANS_RENT, "UPDATE `tnn_trans_rent` set PROCESSED = 1 where id = ");
            put(QUERY_JGZZH,"select '450100' xzqh,jgzzh,htcode from tnn_enter_contract");
            put(DELETE_SERIAL, "DELETE FROM `tnn_sub_account_serial` where htcode = ?");
            put(TNN_SUB_ACCOUNT_SERIAL, "INSERT INTO `tnn_sub_account_serial`(`HTCODE`,`JYLSH`,`JYRQ`,`JYSJ`,`DFZH`,`DFHM`,`JYQD`,`SZBZ`,`JYQJE`,`JYJE`,`JYHJE`,`ZY`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            put(QUERY_CLIENT,"select bankname,interfaceaddress,targetnamespace from tnn_sup_bank");
            put(QUERY_BANK_WITH_JGZH,"select jgyhid from tnn_mas_account where jgzh = ?");
            put(QUERY_BANK_WITH_JGZZH,"select jgyhid from tnn_mas_account a join tnn_enter_protocol b on a.JGZH = b.JGZH where b.jgzzh = ?");

        }
    };

    public interface WsMethod{

        String MAS_ACCOUNT_STATUS = "queryJgzh";
        String MAS_ACCOUNT_SERIAL = "detailJgzh";
        String SUB_ACCOUNT_MATCH = "matchJgzzh";
        String SUB_ACCOUNT_FREEZE = "frozenJgzh";
        String SUB_ACCOUNT_SEARCH = "queryJgzzh";
        String SUB_ACCOUNT_SERIAL = "detailJgzzh";
        String SUB_ACCOUNT_PAY = "payJgzzh";
        String SUB_ACCOUNT_PAY_FEEDBACK = "payResponse";
    }

    public interface TableName{

        String TNN_WSINTER_LOG = "TNN_WSINTER_LOG";
        String QUERY_PAY = "QUERY_PAY";
        String QUERY_JGZZH = "QUERY_JGZZH";
        String UPDATE_RELEASE_APPLY = "UPDATE_RELEASE_APPLY";
        String UPDATE_TRANS_RENT = "UPDATE_TRANS_RENT";
        String DELETE_SERIAL = "DELETE_SERIAL";
        String TNN_SUB_ACCOUNT_SERIAL = "TNN_SUB_ACCOUNT_SERIAL";
        String QUERY_CLIENT = "QUERY_CLIENT";
        String QUERY_BANK_WITH_JGZH = "QUERY_BANK_WITH_JGZH";
        String QUERY_BANK_WITH_JGZZH = "QUERY_BANK_WITH_JGZZH";
    }

    public interface FieldName{

        String JGZH = "jgzh";
        String JGZZH = "jgzzh";
        String JGYHID = "jgyhid";
    }

    public interface ResultFlg{

        String FLAT = "0";
        String ERECT = "1";

    }

    public interface WsResult{

        String SUCCESS = "true";
        String FAIL = "false";
    }

    public interface Schema{

        String RENT_SUP = "rent_sup";
    }

}
