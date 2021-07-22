package cn.redflagsoft.base.util.database;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 提供产生HSQL到Oracle库移植脚本的功能
 * @author ZhangJ.
 *
 */
public class HSql2OracleMigration {
    private static boolean beTest = true;
    private static String outRoot="C:/HSql2Oracle_script/";//脚本文件输出路径

    /**
     * 获取HSQL连接
     * @return
     * @throws Exception
     */
    private static Connection getHSQLConnection() throws Exception {
        Class.forName("org.hsqldb.jdbcDriver");
        String url = "jdbc:hsqldb:hsql://192.168.18.5:2010/";
        return DriverManager.getConnection(url, "sa", "");
    }

    /**
     * 获取Oracle连接
     * @return
     * @throws Exception
     */
    private static Connection getOracleConnection() throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@192.168.18.15:1521:orcl";
        String username = "test";
        String password = "bestbnf";

        Class.forName(driver); // load Oracle driver
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    

    
    
    /**
     * @param args
     */
    public static void main2(String[] args) {
        File fileOutRoot=new File(outRoot);
        fileOutRoot.mkdir();
        System.out.println("脚本文件输出路径为："+outRoot);
        PrintStream originalOutStream=System.out;
        
        Connection conn_src = null, conn_dst = null;
        ResultSet rs_tables = null, rs_views = null;
        PreparedStatement pstmt = null;
        ParameterMetaData paramMetaData = null;
        try {
            conn_src = getHSQLConnection();
            conn_dst = getOracleConnection();

            DatabaseMetaData meta_src = conn_src.getMetaData();

            // tables
            ArrayList list_tables = new ArrayList();
            rs_tables = meta_src.getTables(null, null, null, new String[] {
                    "TABLE" });
            while (rs_tables.next()) {
                String tableOrViewName = rs_tables.getString("TABLE_NAME");
                list_tables.add(tableOrViewName);

                PrintStream stream=setFileOutput(outRoot+tableOrViewName+".sql");
                createOracleTable(conn_src,meta_src, tableOrViewName, conn_dst);
                closeFileOutput(stream);
            }

            // views
            ArrayList list_views = new ArrayList();
            rs_views = meta_src.getTables(null, null, null,
                    new String[] { "VIEW" });
            while (rs_views.next()) {
                String tableOrViewName = rs_views.getString("TABLE_NAME");
                list_views.add(tableOrViewName);
                
                PrintStream stream=setFileOutput(outRoot+tableOrViewName+".sql");
                createOracleView(conn_src,meta_src, tableOrViewName, conn_dst);
                closeFileOutput(stream);
            }

            //drop tables and views
            PrintStream stream=setFileOutput(outRoot+"_drop_objects.sql");
            Iterator it1=list_tables.iterator();
            while (it1.hasNext()) {
                String name = (String) it1.next();
                System.out.println("drop table "+name+" cascade constraints"+";");
            }
            Iterator it2=list_views.iterator();
            while (it2.hasNext()) {
                String name = (String) it2.next();
                System.out.println("drop view "+name+";");
            }
            closeFileOutput(stream);
            
            //batch create
            stream=setFileOutput(outRoot+"_create_objects.sql");
            //System.out.println("set feedback off;");
            System.out.println("set define off;");
            System.out.println("spool "+"_create_objects.sql.log");
            Iterator it11=list_tables.iterator();
            while (it11.hasNext()) {
                String name = (String) it11.next();
                System.out.println("select '"+name+"' from dual;");
                System.out.println("@"+name+".sql");
            }
            Iterator it12=list_views.iterator();
            while (it12.hasNext()) {
                String name = (String) it12.next();
                System.out.println("@"+name+".sql");
            }
            System.out.println("spool off");
            closeFileOutput(stream);
            
            System.setOut(originalOutStream);
            System.out.println("脚本已经创建完成。");
            System.out.println("注意：\n" +
            		"\t1.视图脚本未创建。\n" +
            		"\t2.HSQL  varchar(30)原则上可存30汉字，实际上能存的远超30汉字；Oracle varchar2(30)只能存10汉字。转换时长度自动扩为4倍（最大4000bytes）。\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs_tables != null)
                    rs_tables.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn_src!=null)
                    conn_src.close();
                if (conn_dst!=null)
                    conn_dst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static PrintStream setFileOutput(String file) throws FileNotFoundException{
        File outFile=new File(file);
        if(outFile.exists())
            outFile.delete();
        
        PrintStream outStream=new PrintStream(outFile);
        System.setOut(outStream);
        return outStream;
    }
    
    private static void closeFileOutput(PrintStream outStream){
        outStream.flush();
        outStream.close();
    }

    private static void createOracleTable(Connection conn_src,DatabaseMetaData meta,
            String tblName, Connection conn_dst) throws SQLException {
        String DATA_FORMAT_1="yyyy-MM-dd";
        String TIMESTAMP_FORMAT_JAVA="yyyy-MM-dd HH:mm:ss";
        String TIMESTAMP_FORMAT_ORACLE="yyyy-mm-dd hh24:mi:ss";
        
        // 处理table
        String  sql_cols_defines = "";
        Hashtable cols_type=new Hashtable();
        ResultSet rsColumns = meta.getColumns(null, null, tblName, null);
        while (rsColumns.next()) {
            String columnName = rsColumns.getString("COLUMN_NAME")
                    .toUpperCase();

            // 获取类型并强制转换
            String columnType = rsColumns.getString("TYPE_NAME");
            int size = rsColumns.getInt("COLUMN_SIZE");
    		String ssize = rsColumns.getString("COLUMN_SIZE");
    		String dg = rsColumns.getString("DECIMAL_DIGITS");
            
            
            cols_type.put(columnName, columnType);
            if ("INTEGER".equalsIgnoreCase(columnType)||"INT".equalsIgnoreCase(columnType)) {
                columnType="NUMBER(10,0)";
            } else if ("DOUBLE".equalsIgnoreCase(columnType)||"FLOAT".equalsIgnoreCase(columnType)) {
                columnType="NUMBER(19,6)";
            } else if ("VARCHAR".equalsIgnoreCase(columnType)||"VARCHAR_IGNORECASE".equalsIgnoreCase(columnType)
                    ||"LONGVARCHAR".equalsIgnoreCase(columnType)) {
                columnType="VARCHAR2";
                if (size > 0)
                    columnType += "(" + (4*size >4000 ? 4000: 4*size) + ")";
            } else if ("CHAR".equalsIgnoreCase(columnType)||"CHARACTER".equalsIgnoreCase(columnType)) {
                columnType="VARCHAR2";
                if (size > 0)
                    columnType += "(" + (4*size >4000 ? 4000: 4*size) + ")";
            } else if ("DATE".equalsIgnoreCase(columnType)) {
                columnType="DATE";
            } else if ("TIME".equalsIgnoreCase(columnType)) {
                columnType="FIXME->TIME";
                System.err.println(columnType);
            } else if ("TIMESTAMP".equalsIgnoreCase(columnType)||"DATETIME".equalsIgnoreCase(columnType)) {
                columnType="TIMESTAMP";
            } else if ("DECIMAL".equalsIgnoreCase(columnType) || "NUMERIC".equalsIgnoreCase(columnType)) {
                columnType="NUMBER";
                if(ssize != null){
                	columnType += "(" + ssize;
        			if(dg != null){
        				columnType += "," + dg;
        			}
        			columnType += ")";
        		}
                
            } else if ("BOOLEAN".equalsIgnoreCase(columnType)) {
                columnType="NUMBER(1)";
            } else if ("BIT".equalsIgnoreCase(columnType)) {
                columnType="FIXME->BIT";
                System.err.println(columnType);
            } else if ("TINYINT".equalsIgnoreCase(columnType)) {
                columnType="NUMBER(3,0)";
            } else if ("SMALLINT".equalsIgnoreCase(columnType)) {
                columnType="NUMBER(6,0)";
            } else if ("BIGINT".equalsIgnoreCase(columnType)) {
                columnType="NUMBER(19,0)";
            } else if ("REAL".equalsIgnoreCase(columnType)) {
                columnType="FIXME->REAL";
                System.err.println(columnType);
            } else if ("BINARY".equalsIgnoreCase(columnType)) {
                columnType="CLOB";
            } else if ("VARBINARY".equalsIgnoreCase(columnType)) {
                columnType="FIXME->VARBINARY";
                System.err.println(columnType);
            } else if ("LONGVARBINARY".equalsIgnoreCase(columnType)) {
                columnType="FIXME->VARBINARY";
                System.err.println(columnType);
            } else if ("OTHER".equalsIgnoreCase(columnType)) {
                columnType="FIXME->OTHER";
                System.err.println(columnType);
            } else if ("OBJECT".equalsIgnoreCase(columnType)) {
                columnType="FIXME->OBJECT";
                System.err.println(columnType);
            } else {
                columnType="FIXME->未知类型";
                System.err.println(columnType);
            }

            int nullable = rsColumns.getInt("NULLABLE");
            int position = rsColumns.getInt("ORDINAL_POSITION");
            String defaultValue=rsColumns.getString("COLUMN_DEF");
            
            String sql_tmp = columnName
                    + " "
                    + columnType
                    + " "
                    + ((defaultValue != null && !"".equals(defaultValue)) ? " default "
                            + defaultValue
                            : "") 
                    + ((nullable == DatabaseMetaData.columnNullable) ? ""
                            : " NOT NULL ")
                            + ",";
            sql_cols_defines += sql_tmp;
        }
        String sql_table_create = "create table " + tblName + " " + " ("
                + sql_cols_defines.substring(0, sql_cols_defines.length() - 1) + ")";

        String sql_table_drop ="drop table "+tblName+" cascade constraints";
        
        if (!beTest) {
            //TODO:drop table (sql_table_drop)
            
            Statement statement1 = conn_dst.createStatement();
            statement1.executeUpdate(sql_table_create);
            statement1.close();
        } else {
            System.out.println(sql_table_drop + ";");
            System.out.println(sql_table_create + ";");
        }

        //生成insert预警
        if (true) {
            Statement statement2 = conn_src.createStatement();
            ResultSet rs2=statement2.executeQuery("select * from "+tblName);
            while(rs2.next()){
                StringBuffer sql_cols=new StringBuffer();
                StringBuffer sql_vals=new StringBuffer();
                for (Iterator it = cols_type.keySet().iterator(); it
                        .hasNext();) {
                    String columnName=(String) it.next();
                    String columnType = (String) cols_type.get(columnName);
                    
                    String valPrefix="",valSuffix="";
                    if ("INTEGER".equalsIgnoreCase(columnType)||"INT".equalsIgnoreCase(columnType)) {
                        valPrefix=""+rs2.getInt(columnName);
                        valSuffix="";
                    } else if ("DOUBLE".equalsIgnoreCase(columnType)) {
                        valPrefix=""+  rs2.getDouble(columnName);
                        valSuffix="";
                    } else if ("FLOAT".equalsIgnoreCase(columnType)) {
                        valPrefix=""+ rs2.getFloat(columnName);
                        valSuffix="";
                    } else if ("VARCHAR".equalsIgnoreCase(columnType)||"VARCHAR_IGNORECASE".equalsIgnoreCase(columnType)
                            ||"LONGVARCHAR".equalsIgnoreCase(columnType)) {
                        String tmp=rs2.getString(columnName);
                        if(tmp!=null){
                            valPrefix="'"+   tmp;
                            valSuffix="'";
                        }else{
                            valPrefix="'";
                            valSuffix="'";
                        }
                    } else if ("CHAR".equalsIgnoreCase(columnType)||"CHARACTER".equalsIgnoreCase(columnType)) {
                        String tmp=rs2.getString(columnName);
                        if(tmp!=null){
                            valPrefix="'"+   tmp;
                            valSuffix="'";
                        }else{
                            valPrefix="'";
                            valSuffix="'";
                        }
                    } else if ("DATE".equalsIgnoreCase(columnType)) {
                        Date tmp=rs2.getDate(columnName);
                        if(tmp!=null){
                            SimpleDateFormat format_1=new SimpleDateFormat(DATA_FORMAT_1);
    
                            valPrefix="to_date('"+ format_1.format(tmp);
                            valSuffix="','"+DATA_FORMAT_1+"')";
                        }else{
                            valPrefix="null";
                            valSuffix="";
                        }
                    } else if ("TIME".equalsIgnoreCase(columnType)) {
                        valPrefix=""+ rs2.getTime(columnName);
                        valSuffix="";
                    } else if ("TIMESTAMP".equalsIgnoreCase(columnType)||"DATETIME".equalsIgnoreCase(columnType)) {
                        Timestamp tmp=rs2.getTimestamp(columnName);
                        if(tmp!=null){
                            SimpleDateFormat format_1=new SimpleDateFormat(TIMESTAMP_FORMAT_JAVA);
                            valPrefix="to_timestamp('"+ format_1.format(tmp);
                            valSuffix="','"+TIMESTAMP_FORMAT_ORACLE+"')";
                        }else{
                            valPrefix="null";
                            valSuffix="";
                        }
                    } else if ("DECIMAL".equalsIgnoreCase(columnType)) {
                        valPrefix=""+rs2.getInt(columnName);
                        valSuffix="";
                    } else if ("NUMERIC".equalsIgnoreCase(columnType)) {
                        valPrefix=""+ rs2.getInt(columnName);
                        valSuffix="";
                    } else if ("BOOLEAN".equalsIgnoreCase(columnType)) {
                        Boolean tmp=rs2.getBoolean(columnName);
                        if(tmp!=null && !tmp){//False
                            valPrefix=""+0;
                            valSuffix="";
                        }else if(tmp!=null && tmp){//True
                            valPrefix=""+1;
                            valSuffix="";
                        }else{
                            valPrefix=""+null;
                            valSuffix="";
                        }
                    } else if ("BIT".equalsIgnoreCase(columnType)) {
                        String tmp=rs2.getString(columnName);
                        if(tmp!=null){
                            valPrefix="'"+   tmp;
                            valSuffix="'";
                        }else{
                            valPrefix="'";
                            valSuffix="'";
                        }
                    } else if ("TINYINT".equalsIgnoreCase(columnType)) {
                        valPrefix=""+ rs2.getInt(columnName);
                        valSuffix="";
                    } else if ("SMALLINT".equalsIgnoreCase(columnType)) {
                        valPrefix=""+rs2.getInt(columnName);
                        valSuffix="";
                    } else if ("BIGINT".equalsIgnoreCase(columnType)) {
                        valPrefix=""+rs2.getBigDecimal(columnName);
                        valSuffix="";
                    } else if ("REAL".equalsIgnoreCase(columnType)) {
                        valPrefix=""+ rs2.getDouble(columnName);
                        valSuffix="";
                    } else if ("BINARY".equalsIgnoreCase(columnType)) {
                        valPrefix="'"+rs2.getClob(columnName);
                        valSuffix="'";
                    } else if ("VARBINARY".equalsIgnoreCase(columnType)) {
                        valPrefix="'"+rs2.getClob(columnName);
                        valSuffix="'";
                    } else if ("LONGVARBINARY".equalsIgnoreCase(columnType)) {
                        valPrefix="'"+ rs2.getClob(columnName);
                        valSuffix="'";
                    } else if ("OTHER".equalsIgnoreCase(columnType)) {
                        valPrefix="'"+rs2.getObject(columnName);
                        valSuffix="'";
                    } else if ("OBJECT".equalsIgnoreCase(columnType)) {
                        valPrefix="'"+rs2.getObject(columnName);
                        valSuffix="'";
                    } else {
                        valPrefix="'"+rs2.getObject(columnName);
                        valSuffix="'";
                    }

                    sql_cols.append(columnName+",");
                    sql_vals.append(valPrefix+valSuffix+" ,");
                }
                String tmp=sql_vals.toString();
                
                String sql_insert = "insert into " + tblName + "("
                    + sql_cols.substring(0, sql_cols.length() - 1) + ") values("+ tmp.substring(0, tmp.length() - 1) +")";
                System.out.println(sql_insert + ";");
            }
            rs2.close();
            statement2.close();
        } 

        // 处理key
        ArrayList listKeys = new ArrayList();
        String keys="";
        ResultSet rsKey = meta.getPrimaryKeys(null, null, tblName);
        while (rsKey.next()) {
            String columnName = rsKey.getString("COLUMN_NAME").toUpperCase();
            listKeys.add(columnName);
            keys+=columnName+",";
        }
        if(keys.length()>0){
            String sql="alter table "+tblName+" add constraint PK_"+tblName+" primary key("+keys.substring(0, keys.length() - 1)+")";
            if (!beTest) {
                Statement statement5 = conn_dst.createStatement();
                statement5.executeUpdate(sql);
                statement5.close();
            } else {
                System.out.println(sql + ";");
            }
        }

        // 处理index(含联合索引)
        HashMap mapIndex=new HashMap();
        ResultSet rsIndex = null;
        rsIndex = meta.getIndexInfo(null, null, tblName, false, true);
        while (rsIndex.next()) {
            // String dbCatalog = rsIndex.getString("TABLE_CATALOG");
            // String dbSchema = rsIndex.getString("TABLE_SCHEMA");
            String dbTableName = rsIndex.getString("TABLE_NAME");
            boolean dbNoneUnique = rsIndex.getBoolean("NON_UNIQUE");
            String dbIndexQualifier = rsIndex.getString("INDEX_QUALIFIER");
            String dbIndexName = rsIndex.getString("INDEX_NAME");
            short dbType = rsIndex.getShort("TYPE");
            short dbOrdinalPosition = rsIndex.getShort("ORDINAL_POSITION");
            String dbColumnName = rsIndex.getString("COLUMN_NAME");
            String dbAscOrDesc = rsIndex.getString("ASC_OR_DESC");
            int dbCardinality = rsIndex.getInt("CARDINALITY");
            int dbPages = rsIndex.getInt("PAGES");
            String dbFilterCondition = rsIndex.getString("FILTER_CONDITION");

            if(mapIndex.get(dbIndexName)==null){
                String sql_index1 = "create " + (dbNoneUnique ? "" : "unique")
                        + " index " + dbIndexName + " on " + dbTableName + "("
                        + dbColumnName + "";
            
                mapIndex.put(dbIndexName, sql_index1);                
            }else{
                String tmp=(String)mapIndex.get(dbIndexName)+","+dbColumnName;  
                mapIndex.put(dbIndexName, tmp);     
            }
        }
        for (Iterator iterator = mapIndex.keySet().iterator(); iterator.hasNext();) {
            Object object = iterator.next();
            if(object==null) continue;
            
            if (!beTest) {
                Statement statement2 = conn_dst.createStatement();
                statement2.executeUpdate((String)mapIndex.get(object)+")");
                statement2.close();
            } else {
                System.out.println((String)mapIndex.get(object) +")"+ ";");
            }
        }
        

        // 处理foreign key
        ResultSet rsForeignKey = null;
        rsIndex = meta.getImportedKeys(null, null, tblName);
        while (rsIndex.next()) {
            // String PKTABLE_CAT = rsIndex.getString("PKTABLE_CAT");
            // String PKTABLE_SCHEM = rsIndex.getString("PKTABLE_SCHEM");
            String PKTABLE_NAME = rsIndex.getString("PKTABLE_NAME");
            String PKCOLUMN_NAME = rsIndex.getString("PKCOLUMN_NAME");

            // String FKTABLE_CAT = rsIndex.getString("FKTABLE_CAT");
            // String FKTABLE_SCHEM = rsIndex.getString("FKTABLE_SCHEM");
            String FKTABLE_NAME = rsIndex.getString("FKTABLE_NAME");
            String FKCOLUMN_NAME = rsIndex.getString("FKCOLUMN_NAME");

            String FK_NAME = rsIndex.getString("FK_NAME");
            String PK_NAME = rsIndex.getString("PK_NAME");

            String sql_foreignkey = "alter table " + tblName + " add constraint " + FK_NAME
                    + " foreign key (" + FKCOLUMN_NAME + ") references "
                    + PKTABLE_NAME + "(" + PKCOLUMN_NAME + ")";
            
            
            ///////////////////////////////////////////////////////////////////
            int updateRule = rsIndex.getInt("UPDATE_RULE");
            int deleteRule = rsIndex.getInt("DELETE_RULE");
             if(updateRule == 0){
            	 sql_foreignkey += " ON UPDATE CASCADE";
            }
            if(deleteRule == 0){
            	sql_foreignkey += " ON DELETE CASCADE";
            }
            ////////////////////////////////////////////////////////////////////
            
            

            if (!beTest) {
                Statement statement3 = conn_dst.createStatement();
                statement3.executeUpdate(sql_foreignkey);
                statement3.close();
            } else {
                System.out.println(sql_foreignkey + ";");
            }
        }

        if (!beTest) {

        } else {
            System.out.println("commit;");
        }
    }
    
    private static void createOracleView(Connection conn_src,DatabaseMetaData meta,
            String tblName, Connection conn_dst) throws SQLException {
        //TODO: 处理view
        System.out.println("--视图"+tblName+"的脚本无法自动生成");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) throws Exception{
    	doInConnection(getHSQLConnection());
    	//createOracleModifyScript();
    }
    
    private static void doInConnection(Connection conn) throws Exception{
    	
    	StringBuffer script = new StringBuffer();
    	DatabaseMetaData meta = conn.getMetaData();
    	ResultSet rs = meta.getTables(null, null, null, new String[]{"TABLE"});
    	while(rs.next()){
    		String tableName = rs.getString("TABLE_NAME");
    		//System.out.println("create table " + tableName + "(");
    		
    		createTableScript(meta, tableName, script);
    	}
    	
    	System.out.println(script.toString());
    }
    
    
    
    private static void createTableScript(DatabaseMetaData meta, String table, StringBuffer script) throws SQLException{
    	script.append("CREATE TABLE " + table + "(\n");
		
		//COLUMNS
		ResultSet columns = meta.getColumns(null, null, table, null);
		StringBuffer colsb = new StringBuffer();
		while(columns.next()){
			createColumnScript(columns, colsb);
		}
		script.append(colsb);
		
		
		
		//PK
		ResultSet pks = meta.getPrimaryKeys(null, null, table);
		StringBuffer pksb = new StringBuffer();
		while(pks.next()){
			createPrimaryKeys(pks, pksb);
		}
		if(pksb.length() > 0){
			script.append(",\n")
				.append("  PRIMARY KEY(").append(pksb).append(")");
		}
		
		
		//FK
		ResultSet fks = meta.getImportedKeys(null, null, table);
		while(fks.next()){
			//System.out.println("--------------------------------------\n" + table + "\n");
			createImportedKeys(fks, script);
		}
		
		script.append("\n);\n\n");
		
		//INDEX
		ResultSet indexInfo = meta.getIndexInfo(null, null, table, false, true);
		createIndexInfo(indexInfo, script);
		
		
		script.append("\n\n");
    }
    






	private static void createColumnScript(ResultSet columns, StringBuffer script) throws SQLException{
		if(script.length() > 0){
			script.append(",\n");
		}
		
		script.append("  ");
    	script.append(columns.getString("COLUMN_NAME")).append(" ");
    	String typeName = columns.getString("TYPE_NAME");
		String size = columns.getString("COLUMN_SIZE");
		String dg = columns.getString("DECIMAL_DIGITS");
//		if(size != null){
//			script.append("(").append(size);
//			
//			if(dg != null){
//				script.append("," + dg);
//			}
//			script.append(")");
//		}
		
		//appendColumnType(script, typeName, size, dg);
		appendOracleColumnType(script, typeName, size, dg);
		
		String nullable = columns.getString("NULLABLE");
		if("0".equals(nullable)){
			script.append(" NOT NULL");
		}
		
		String def = columns.getString("COLUMN_DEF");
		if(def != null){
			if("0.0E0".equals(def)){
				def = "0";
			}
			script.append(" DEFAULT " + def);
		}
    }
	
	private static void appendColumnType(StringBuffer script, String type, String size, String decimalDigits){
		script.append(type);
		if(size != null){
			script.append("(").append(size);
			if(decimalDigits != null){
				script.append("," + decimalDigits);
			}
			script.append(")");
		}
	}
	
	
    /**
	 * @param pks
	 * @param pksb
     * @throws SQLException 
	 */
	private static void createPrimaryKeys(ResultSet pks, StringBuffer pksb) throws SQLException {
		if(pksb.length() > 0){
			pksb.append(", ");
		}
		String pk = pks.getString("COLUMN_NAME");
		pksb.append(pk);
	}
	
	/**
	 * @param fks
	 * @param script
	 * @throws SQLException 
	 */
	private static void createImportedKeys(ResultSet fks, StringBuffer script) throws SQLException {
		  String PKTABLE_NAME = fks.getString("PKTABLE_NAME");
          String PKCOLUMN_NAME = fks.getString("PKCOLUMN_NAME");
          String FKCOLUMN_NAME = fks.getString("FKCOLUMN_NAME");
          int updateRule = fks.getInt("UPDATE_RULE");
          int deleteRule = fks.getInt("DELETE_RULE");
          
          String sql = "  FOREIGN KEY(" + FKCOLUMN_NAME + ") REFERENCES "
          	+ PKTABLE_NAME + "(" + PKCOLUMN_NAME + ")";
          if(updateRule == 0){
        	  sql += " ON UPDATE CASCADE";
          }
          if(deleteRule == 0){
        	  sql += " ON DELETE CASCADE";
          }
          //System.out.println(sql);
          
          
          //showResultSetMetaData(fks);
          
         script.append(",\n");
         script.append(sql);
	}
	
	private static void showResultSetMetaData(ResultSet rs) throws SQLException{
		System.out.println("--------------------------------------------\n");
		ResultSetMetaData meta = rs.getMetaData();
		for(int i = 1 ; i < meta.getColumnCount() ; i++){
			System.out.println(meta.getColumnName(i) + " : " + rs.getString(i));
		}
	}
	
	
	/**
	 * @param indexInfo
	 * @param script
	 * @throws SQLException 
	 */
	private static void createIndexInfo(ResultSet indexInfo, StringBuffer script) throws SQLException {
		Map<String, List<String>> indexMap = new HashMap<String, List<String>>();
		while(indexInfo.next()){
		    String tableName = indexInfo.getString("TABLE_NAME");
	        boolean nonUnique = indexInfo.getBoolean("NON_UNIQUE");
	        String indexName = indexInfo.getString("INDEX_NAME");
	        String columnName = indexInfo.getString("COLUMN_NAME");
	        
	        if(indexName.startsWith("SYS_IDX_")){
	        	continue;
	        }
	        
	        String key = "CREATE " + (nonUnique ? "" : "UNIQUE ") + "INDEX " + indexName
	        	+ " ON " + tableName;
	        List<String> columns = indexMap.get(key);
	        if(columns == null){
	        	columns = new ArrayList<String>();
	        	indexMap.put(key, columns);
	        }
	        
	        columns.add(columnName);
	        //showResultSetMetaData(indexInfo);
		}
		
		for(Map.Entry<String, List<String>> en: indexMap.entrySet()){
			String value = en.getValue().toString();
			script.append(en.getKey()).append("(").append(value.substring(1, value.length() - 1)).append(");\n");
		}
	}
	
	
	
	private static void appendOracleColumnType(StringBuffer script, String columnType, String size, String decimalDigits){
		if ("INTEGER".equalsIgnoreCase(columnType) || "INT".equalsIgnoreCase(columnType)) {
            columnType="NUMBER(10,0)";
        } else if ("DOUBLE".equalsIgnoreCase(columnType) || "FLOAT".equalsIgnoreCase(columnType)) {
            columnType="NUMBER(19,6)";
        } else if ("VARCHAR".equalsIgnoreCase(columnType)
        		|| "VARCHAR_IGNORECASE".equalsIgnoreCase(columnType)
                || "LONGVARCHAR".equalsIgnoreCase(columnType)
                || "CHAR".equalsIgnoreCase(columnType)
                || "CHARACTER".equalsIgnoreCase(columnType)) {
            columnType="VARCHAR2";
            int s = Integer.valueOf(size) * 4;
            columnType += "(" + (s > 4000 ? 4000 : s) + ")";
        } else if ("DATE".equalsIgnoreCase(columnType)) {
            columnType="DATE";
        } else if ("TIME".equalsIgnoreCase(columnType)) {
            columnType="FIXME->TIME";
            System.err.println(columnType);
        } else if ("TIMESTAMP".equalsIgnoreCase(columnType)
        		||"DATETIME".equalsIgnoreCase(columnType)) {
            columnType="TIMESTAMP";
        } else if ("DECIMAL".equalsIgnoreCase(columnType) || "NUMERIC".equalsIgnoreCase(columnType)) {
            columnType="NUMBER";
            if(size != null){
            	columnType += "(" + size;
    			if(decimalDigits != null){
    				columnType += "," + decimalDigits;
    			}
    			columnType += ")";
    		}
        } else if ("BOOLEAN".equalsIgnoreCase(columnType)) {
            columnType="NUMBER(1)";
        } else if ("BIT".equalsIgnoreCase(columnType)) {
            columnType="FIXME->BIT";
            System.err.println(columnType);
        } else if ("TINYINT".equalsIgnoreCase(columnType)) {
            columnType="NUMBER(3,0)";
        } else if ("SMALLINT".equalsIgnoreCase(columnType)) {
            columnType="NUMBER(6,0)";
        } else if ("BIGINT".equalsIgnoreCase(columnType)) {
            columnType="NUMBER(19,0)";
        } else if ("REAL".equalsIgnoreCase(columnType)) {
            columnType="FIXME->REAL";
            System.err.println(columnType);
        } else if ("BINARY".equalsIgnoreCase(columnType)) {
            columnType="CLOB";
        } else if ("VARBINARY".equalsIgnoreCase(columnType)) {
            columnType="FIXME->VARBINARY";
            System.err.println(columnType);
        } else if ("LONGVARBINARY".equalsIgnoreCase(columnType)) {
            columnType="FIXME->VARBINARY";
            System.err.println(columnType);
        } else if ("OTHER".equalsIgnoreCase(columnType)) {
            columnType="FIXME->OTHER";
            System.err.println(columnType);
        } else if ("OBJECT".equalsIgnoreCase(columnType)) {
            columnType="FIXME->OBJECT";
            System.err.println(columnType);
        } else {
            columnType="FIXME->未知类型";
            System.err.println(columnType);
        }
		
		
		script.append(columnType);
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////
	private static void createOracleModifyScript() throws SQLException, Exception{
    	StringBuffer script = new StringBuffer();
    	DatabaseMetaData meta = getOracleConnection().getMetaData();
    	ResultSet rs = meta.getTables(null, null, null, new String[]{"TABLE"});
    	while(rs.next()){
    		String tableName = rs.getString("TABLE_NAME");
    		
    		
    		if(tableName.indexOf('$') != -1){
    			continue;
    		}
    		if(tableName.indexOf('=') != -1){
    			continue;
    		}
    		//System.out.println(tableName + "");
    		
    		ResultSet columns = meta.getColumns(null, null, tableName, null);
    		while(columns.next()){
    			String name = columns.getString("COLUMN_NAME");
            	String type = columns.getString("TYPE_NAME");
        		String size = columns.getString("COLUMN_SIZE");
        		String dg = columns.getString("DECIMAL_DIGITS");
        		if("NUMBER".equals(type) && "19".equals(size) && "4".equals(dg)){
        			script.append("ALTER TABLE ")
        				.append(tableName).append(" MODIFY ")
        				.append(name).append(" NUMBER(19,6)");
        			
               		String nullable = columns.getString("NULLABLE");
               		//System.out.println(nullable);
               		
            		if("0".equals(nullable)){
            			script.append(" NOT NULL");
            		}
            		
            		String def = columns.getString("COLUMN_DEF");
            		if(def != null){
            			if("0.0E0".equals(def)){
            				def = "0";
            			}
            			script.append(" DEFAULT " + def);
            		}
            		
            		script.append(";\n");
        		}
        		
 
    		}
    		columns.close();
    		
    		
    	}
    	rs.close();
    	
    	
    	System.out.println("\n\n\n---------------------------------------\n--将小数4位改成6位的oracle脚本\n\n");
    	System.out.println(script.toString());
	}
}
