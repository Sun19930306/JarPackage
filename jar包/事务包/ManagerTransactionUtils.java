package com.qf.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class ManagerTransactionUtils {
   
	//得到Connection,要确保同一个线程使用的Connection是一致的。
	private static  ThreadLocal<Connection> threadLocal =new ThreadLocal<>();
	
	public  static Connection getConnection() {
		//1.先从threadlocal查找
		Connection connection = threadLocal.get();
		if (connection==null) {
			//从数据源中获取连接
			 connection =C3P0Utils.getConnection();
			 //用threadlocal存储使用的Connection
			 threadLocal.set(connection);
		}
		return connection;
	}
	//开启事务
	public static void  startTransaction() {
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//关闭事务
	public static void  closeTransaction() {
		try {
			getConnection().commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//回滚事务
	public static void  rollBackTransaction() {
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
