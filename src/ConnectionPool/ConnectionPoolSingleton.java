package ConnectionPool;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPoolSingleton {

	private static ConnectionPoolSingleton connectionPool;
	private static DataSource pool;

	// prevent other classes instantiating Connection pool
	private ConnectionPoolSingleton() {
	}

	public static synchronized ConnectionPoolSingleton getConnectionPool() {
		if (connectionPool == null) connectionPool = new ConnectionPoolSingleton();
		setPool(createConnectionPool());

		return connectionPool;
	}

	private static DataSource createConnectionPool() {
		HikariConfig config = new HikariConfig();

		config.setJdbcUrl(String.format("jdbc:mysql:///%s", "servletcoursework"));
		config.setUsername("root");
		config.setPassword("wu31wMas9nclNh05");
		config.addDataSourceProperty("ipTypes", "PUBLIC");

		String connectionName = "servletcoursework-336513:europe-west2:servletcourseworkdb2";
		config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
		config.addDataSourceProperty("cloudSqlInstance", connectionName);

		return new HikariDataSource(config);
	}

	public DataSource getPool() {
		return pool;
	}

	private static void setPool(DataSource pool) {
		ConnectionPoolSingleton.pool = pool;
	}

}
