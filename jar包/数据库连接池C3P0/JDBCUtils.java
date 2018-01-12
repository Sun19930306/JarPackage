package com.itqf.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 获取连接的工具类！
 * 从c3p0连接池中获取
 * @author admin
 *
 */
public class JDBCUtils {
  
	public static ComboPooledDataSource source = new ComboPooledDataSource();
	
	public static DataSource getSource(){
		return source;
	}
	
	
	//获取连接的方法
	
	public static Connection getConnection() throws SQLException{
		
		return source.getConnection();
	}
	
	
	//释放资源的方法
    public static void close(ResultSet resultSet) {
		
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void close(Connection connection) {
		
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void close(Statement statement,Connection connection) {
		close(statement);
		close(connection);
	}

	public static void close(ResultSet resultSet,Statement statement,Connection connection) {
		close(resultSet);
		close(statement);
		close(connection);
	}
	
	
	
	
}
