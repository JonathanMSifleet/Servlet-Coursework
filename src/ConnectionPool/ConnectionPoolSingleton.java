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
		
		String connectionName = "servletcoursework-336513:europe-west2:servletcourseworkdb2";
		String databaseName = "servletcoursework";
		String password = "wu31wMas9nclNh05";

		// connection settings:
		config.setJdbcUrl(String.format("jdbc:mysql:///%s", databaseName));
		config.setUsername("root");
		config.setPassword(password);
		config.addDataSourceProperty("ipTypes", "PUBLIC");
		
		config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
		config.addDataSourceProperty("cloudSqlInstance", connectionName);

		config.setMaximumPoolSize(99);
		config.setMinimumIdle(5);
		
		// establish connection timeout: 10 seconds
		config.setConnectionTimeout(10 * 1000);
		// timeout: 5 minutes
		config.setIdleTimeout(5 * 60 * 1000);
		// max connection length: 30 minutes
		config.setMaxLifetime(30 * 60 * 1000);

		return new HikariDataSource(config);
	}

	public DataSource getPool() {
		return pool;
	}

	private static void setPool(DataSource pool) {
		ConnectionPoolSingleton.pool = pool;
	}

}
