package com.itqf.demo1;
/**
 * JDBC�򻯲���������
 *   1.ע��һ������
 *   2.�򻯻�ȡ���ӵķ�ʽ
 *   3.�򻯹ر���Դ�ķ�ʽ
 *   4.�򻯽�����Դ�ķ�ʽ
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

//�˴�ִֻ��һ�Σ�
public class JDBCUtils {

	public static String user;
	public static String password;
	public static String driver;
	public static String url;

	static {
		// ResourceBundle����ֱ�Ӽ���src�·��������ļ�
		// ʹ��getBundle���� ����Ҫд��׺��
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
	 * ��ȡ���ӵķ�ʽ return ���ص��ǻ�ȡ������
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

		// TODO result ������List<Map>
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		// ResultSetMetaData����
		// ResultSetMetaData ��ǰResultSet�����е���Ϣ ����������
		ResultSetMetaData metaData = resultSet.getMetaData();

		int count = metaData.getColumnCount(); // �����ж�����

		while (resultSet.next()) {
			// �ƶ�����һ�У�
			// һ�з�װ��һ��map����
			Map<String, String> map = new HashMap<String, String>();
			// ȫ�Զ�
			// �е��±��Ǵ�1��ʼ column day07 not found
			for (int i = 1; i <= count; i++) {
				// name�����������ѯ���˱��� name�Ǳ���
				String name = metaData.getColumnName(i);
				String value = resultSet.getString(name);

				map.put(name, value);
			}
			list.add(map);
		}

		return list;
	}

}
