package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Employee {

	// Database Connection
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp", "root", "root");

			// For testing
			System.out.print("successfully connected.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	// Create
	public String insertEmployee(String ename, String gender, String email, String phonenumber) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into employees (`eid`,`ename`,`gender`,`email`,`phonenumber`)"
					+ " values (?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, ename);
			preparedStmt.setString(3, gender);
			preparedStmt.setString(4, email);
			preparedStmt.setString(5, phonenumber);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "successfully inserted.";
		} catch (Exception e) {
			output = "error while inserting an employee.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Read
	public String readEmployees() {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='2'><tr><th>Employee Name</th>" + "<th>Gender</th><th>Email</th>"
					+ "<th>Phone Number</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from employees";
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				@SuppressWarnings("unused")
				String eid = Integer.toString(rs.getInt("eid"));
				String ename = rs.getString("ename");
				String gender = rs.getString("gender");
				String email = rs.getString("email");
				String phonenumber = rs.getString("phonenumber");

				// Add a row into the html table
				output += "<tr><td>" + ename + "</td>";
				output += "<td>" + gender + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + phonenumber + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'></td>"
						+ "<td><input name='btnRemove' type='submit' value='Remove'></td>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "error while reading employees.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Update
	public String updateEmployee(String ID, String ename, String gender, String email, String phonenumber) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE employees SET ename=?, gender=?, email=?, phonenumber=? WHERE eid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, ename);
			preparedStmt.setString(2, gender);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, phonenumber);
			preparedStmt.setInt(5, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "successfully updated.";
		} catch (Exception e) {
			output = "error while updating an employee.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Delete
	public String deleteEmployee(String eid) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {
				return "error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from employees where eid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(eid));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "successfully deleted.";

		} catch (Exception e) {
			output = "error while deleting an employee.";
			System.err.println(e.getMessage());
		}

		return output;

	}

}