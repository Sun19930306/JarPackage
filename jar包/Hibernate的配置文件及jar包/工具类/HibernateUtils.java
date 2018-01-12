package com.qf.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

	private static SessionFactory sessionFactory=null;
	static {
		Configuration configuration = new Configuration();
		configuration.configure();
		sessionFactory = configuration.buildSessionFactory();
	}

	public static Session openSession() {
		return sessionFactory.openSession();
	}

	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	//openSession产生的session要关闭
	public static void closeSession(Session session) {
		  session.close();
	}
}
