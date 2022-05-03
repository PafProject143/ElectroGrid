package com;

import model.Employee;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Employees")
public class EmployeeService {
	Employee employeeObj = new Employee();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readEmployees() {
		return employeeObj.readEmployees();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertEmployee(@FormParam("ename") String ename, @FormParam("gender") String gender,
			@FormParam("email") String email, @FormParam("phonenumber") String phonenumber) {
		String output = employeeObj.insertEmployee(ename, gender, email, phonenumber);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateEmployee(String employeeData) {
		// Convert the input string to a JSON object
		JsonObject employeeObject = new JsonParser().parse(employeeData).getAsJsonObject();

		// Read the values from the JSON object
		String eid = employeeObject.get("eid").getAsString();
		String ename = employeeObject.get("ename").getAsString();
		String gender = employeeObject.get("gender").getAsString();
		String email = employeeObject.get("email").getAsString();
		String phonenumber = employeeObject.get("phonenumber").getAsString();
		String output = employeeObj.updateEmployee(eid, ename, gender, email, phonenumber);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteEmployee(String employeeData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(employeeData, "", Parser.xmlParser());

		// Read the value from the element <eid>
		String eid = doc.select("eid").text();
		String output = employeeObj.deleteEmployee(eid);
		return output;
	}
}