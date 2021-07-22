package cn.redflagsoft.base.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class GenerateCalendarUtil {
	
	private static final int[] YEAR = new int[]{2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020};
	private static final int[] MONTH = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ,11};
	private static final int MONTH_SUMMARY = 11;
	private static final int FIRST_MONTH = 0;
	private static final int FIRST_DATE = 1;
	private static final int SATURDAY = 7;
	private static final int SUNDAY = 1;
	private static final byte STA_TYPE = 1;
	private static final byte SUN_TYPE = 2;
	private static final byte ON_USE_STATUS = 1;
	private static final String STA_NOTE = "周六";
	private static final String SUN_NOTE = "周日";
	private static final String REMARK = "";
	private static final boolean isAutoCommit = false;
	private static final String HSQLDB = "org.hsqldb.jdbcDriver";
	private static final String HSQLDB_DRIVER_URL = "jdbc:hsqldb:hsql://192.168.18.5:9008/";
	private static final String HSQLDB_USER_NAME = "sa";
	private static final String HSQLDB_PASSWORD = "";
	private static final String INSERT_SQL = "INSERT INTO HOLIDAY(ID,TP,HOL_DATE,STATUS,NOTE,REMARK) VALUES(?,?,?,?,?,?)";
	private static final String QUERY_SQL = "SELECT MAX(ID) FROM HOLIDAY";
	private static int INDEX = 1;
	private static Connection connect;
	private static PreparedStatement ps;
	private static Statement stmt;
	private static ResultSet rs;
	
	public static void main(String[] args) throws Exception {
		generateWeekDay();
	}	
	
	/**
	 * 加载连接对象
	 */
	public static void getConnection() {
		try {
			Class.forName(HSQLDB);
			connect = DriverManager.getConnection(HSQLDB_DRIVER_URL, HSQLDB_USER_NAME, HSQLDB_PASSWORD);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置是否自动提交事务
	 */
	public static void setAutoCommit() {
		try {
			if(connect == null) getConnection();
			connect.setAutoCommit(isAutoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 提交事务
	 */
	public static void commit() {
		try {
			if(connect == null) throw new NullPointerException("Connection 空引用异常 ... ");
			connect.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 准备语句对象
	 */
	public static void preparedStatement() {
		try {
			if(connect == null) throw new NullPointerException("Connection 空引用异常 ... ");
			ps = connect.prepareStatement(INSERT_SQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前最大索引
	 */
	public static void getMaxCount() {
		try {
			stmt = connect.createStatement();
			rs = stmt.executeQuery(QUERY_SQL);
			if(rs.next()) {
				INDEX = rs.getInt(1) + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	/**
	 * 设置参数
	 */
	public static void execute(Long index, byte type, long holDate, byte status, String note, String remark) {
		try {
			ps.setLong(1, index);
			ps.setByte(2, type);
			ps.setDate(3, new Date(holDate));
			ps.setByte(4, status);
			ps.setString(5, note);
			ps.setString(6, remark);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭连接
	 */
	public static void closeAll() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (connect != null) {
				connect.close();
				connect = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void generateWeekDay() {
		getConnection();
		setAutoCommit();
		preparedStatement();
		getMaxCount();
		
		int length = 10000;
		int previous = 0;
		int day_of_week;
		int sta;
		
		GregorianCalendar calendar = new GregorianCalendar(Locale.CHINA);
		
		for(int i=0; i<YEAR.length; i++) {
			for(int j = 0; j<length; j++) {
				if(previous != YEAR[i]) {
					previous = YEAR[i];
					calendar.set(YEAR[i], FIRST_MONTH, FIRST_DATE);
				}
				day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
				sta = getSaturday(day_of_week);//6
				//星期日
				if(sta == SUNDAY) {
					execute(Long.valueOf(INDEX), SUN_TYPE, calendar.getTimeInMillis(), ON_USE_STATUS, SUN_NOTE, REMARK);
					INDEX++;
				}
				//星期六
				if(sta == SATURDAY) {
					calendar.add(Calendar.DATE, sta - day_of_week);
					if(calendar.get(Calendar.YEAR) != YEAR[i]) {
						break;
					}
					execute(Long.valueOf(INDEX), STA_TYPE, calendar.getTimeInMillis(), ON_USE_STATUS, STA_NOTE, REMARK);
					INDEX++;
					calendar.add(Calendar.DATE, 1);
					execute(Long.valueOf(INDEX), SUN_TYPE, calendar.getTimeInMillis(), ON_USE_STATUS, SUN_NOTE, REMARK);
					INDEX++;
				}
				calendar.add(Calendar.DATE, 1);
			}
			//提交数据
			commit();
		}
		//释放资源
		closeAll();
	}
	
	public static int getSaturday(int day) {
		//1 2 3 4 5 6 7
		//日一二三四五六七
		while(day != SUNDAY && day != SATURDAY) {
			day++;
		}
		return day;
	}
}
