package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Bill {

	// Database Connection
	public Connection connect() {
		
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bill", "root", "root");

			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	// Insert
	public String insertBill(String date, String timeperiod, String unitprice, String totalprice) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " insert into bills (`bid`,`date`,`timeperiod`,`unitprice`,`totalprice`)"
					+ " values (?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, timeperiod);
			preparedStmt.setString(4, unitprice);
			preparedStmt.setString(5, totalprice);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Read
	public String readBills() {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>EmployeeName</th>" + "<th>Gender</th><th>Email</th>"
					+ "<th>PhoneNumber</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from bills";
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String bid = Integer.toString(rs.getInt("bid"));
				String date = rs.getString("date");
				String timeperiod = rs.getString("timeperiod");
				String unitprice = Double.toString(rs.getDouble("unitprice"));
				String totalprice = rs.getString("totalprice");

				// Add a row into the html table
				output += "<tr><td>" + date + "</td>";
				output += "<td>" + timeperiod + "</td>";
				output += "<td>" + unitprice + "</td>";
				output += "<td>" + totalprice + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' " + " type='button' value='Update'></td>"
						+ "<td><form method='post' action='items.jsp'>" + "<input name='btnRemove' "
						+ " type='submit' value='Remove'>" + "<input name='bid' type='hidden' " + " value='" + bid
						+ "'>" + "</form></td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the bills.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Update
	public String updateBills(String id, String date, String timeperiod, String unitprice, String totalprice) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE bills SET date=?, timeperiod=?, unitprice=?, totalprice=? WHERE bid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, date);
			preparedStmt.setString(2, timeperiod);
			preparedStmt.setString(3, unitprice);
			preparedStmt.setString(4, totalprice);
			preparedStmt.setInt(5, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating an bill.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Delete
	public String deleteBill(String bid) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from bills where bid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(bid));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";

		} catch (Exception e) {
			output = "Error while deleting a bill.";
			System.err.println(e.getMessage());
		}

		return output;

	}
}
