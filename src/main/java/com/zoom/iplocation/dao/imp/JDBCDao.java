package com.zoom.iplocation.dao.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.zoom.iplocation.entity.ZmZcServerGroup;
import com.zoom.iplocation.utils.PropertyUtils;

public class JDBCDao {
	public static void batchInsertTable(List<ZmZcServerGroup> zmZcServerGroupList) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sql = "insert into zmlog.server_group (address,location) values(?,?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(sql);
			dbConnection.setAutoCommit(false);
			for (ZmZcServerGroup zmZcServerGroup : zmZcServerGroupList) {
				preparedStatement.setString(1, zmZcServerGroup.getAddress());
				preparedStatement.setString(2, zmZcServerGroup.getLocation());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();

			dbConnection.commit();
		} catch (SQLException e) {

			System.out.println(e.getMessage());
			dbConnection.rollback();

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;
		Map<String, String> propeties = PropertyUtils.getProperty();
		try {

			Class.forName(propeties.get("jdbc.driver"));

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(propeties.get("jdbc.url"), propeties.get("jdbc.username"),
					propeties.get("jdbc.password"));
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

}
