package com.itqf.demo1;
/**
 * JDBC简化操作工具类
 *   1.注册一次驱动
 *   2.简化获取连接的方式
 *   3.简化关闭资源的方式
 *   4.简化解析资源的方式
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

//此处只执行一次！
public class JDBCUtils {

	public static String user;
	public static String password;
	public static String driver;
	public static String url;

	static {
		// ResourceBundle可以直接加载src下方的配置文件
		// 使用getBundle方法 不需要写后缀名
		ResourceBundle bundle = ResourceBundle.getBundle("jdbcinfo");

		user = bundle.getString("user");
		password = bundle.getString("password");
		driver = bundle.getString("driverClass");
		url = bundle.getString("url");

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取连接的方式 return 返回的是获取的连接
	 * 
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		return DriverManager.getConnection(url, user, password);
	}

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

	public static void close(Statement statement, Connection connection) {
		close(statement);
		close(connection);
	}

	public static void close(ResultSet resultSet, Statement statement, Connection connection) {
		close(resultSet);
		close(statement);
		close(connection);
	}

	public static List<Map<String, String>> parseMapAuto(ResultSet resultSet) throws SQLException {

		// TODO result 解析成List<Map>
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		// ResultSetMetaData对象
		// ResultSetMetaData 当前ResultSet对象列的信息 列名和列数
		ResultSetMetaData metaData = resultSet.getMetaData();

		int count = metaData.getColumnCount(); // 返回有多少列

		while (resultSet.next()) {
			// 移动到了一行！
			// 一行封装成一个map对象
			Map<String, String> map = new HashMap<String, String>();
			// 全自动
			// 列的下标是从1开始 column day07 not found
			for (int i = 1; i <= count; i++) {
				// name列名！如果查询起了别名 name是别名
				String name = metaData.getColumnName(i);
				String value = resultSet.getString(name);

				map.put(name, value);
			}
			list.add(map);
		}

		return list;
	}

}
