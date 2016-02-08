package org.xine.paginator.business.customers.boundary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class CustomerFastLaneReader {

	@Resource(name = "jdbc/sample")
	DataSource ds;

	public List<String> getCustomerNames() {
		final List<String> allNames = new ArrayList<>();

		try (final Connection con = this.ds.getConnection()) {

			final Statement statement = con.createStatement();
			final ResultSet resultSet = statement.executeQuery("select name from CUSTOMER");
			while (resultSet.next()) {
				allNames.add(resultSet.getString("name"));
			}

			resultSet.close();
			statement.close();

		} catch (final SQLException e) {
			throw new RuntimeException("Cannot perform query: " + e, e);
		}

		return allNames;
	}
}
