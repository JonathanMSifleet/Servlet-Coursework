package connectionPool;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Responsible for maintaining ConnectionPool.
 */
public class ConnectionPoolSingleton {
	private static ConnectionPoolSingleton connectionPool;
	private static DataSource pool;

	/**
	 * Prevent other classes instantiating Connection pool.
	 */
	private ConnectionPoolSingleton() {
	}

	/**
	 * Gets the connection pool, or creates one if it doesn't exist.
	 *
	 * @return Connection Pool
	 */
	public static synchronized ConnectionPoolSingleton getConnectionPool() {
		if (connectionPool == null) {
			connectionPool = new ConnectionPoolSingleton();
			pool = createConnectionPool();
		}

		return connectionPool;
	}

	/**
	 * Creates new connection pool based on configuration.
	 *
	 * @return New connection pool
	 */
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
		config.setMinimumIdle(1);

		// establish connection timeout: 10 seconds
		config.setConnectionTimeout(10 * 1000);
		// time before connection can be reused: 5 seconds
		config.setIdleTimeout(30 * 1000);
		// max connection length: 2 minutes
		config.setMaxLifetime(2 * 60 * 1000);

		return new HikariDataSource(config);
	}

	/**
	 * Gets the pool.
	 *
	 * @return the pool
	 */
	public DataSource getPool() {
		return pool;
	}

}
