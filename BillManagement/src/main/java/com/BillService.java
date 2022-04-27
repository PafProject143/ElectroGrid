package com;

import model.Bill;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Bills")
public class BillService {
	Bill billObj = new Bill();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBills() {
		return billObj.readBills();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(@FormParam("date") String date, @FormParam("timeperiod") String timeperiod,
			@FormParam("unitprice") String unitprice, @FormParam("totalprice") String totalprice) {
		String output = billObj.insertBill(date, timeperiod, unitprice, totalprice);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBills(String billData) {
		// Convert the input string to a JSON object
		JsonObject billObject = new JsonParser().parse(billData).getAsJsonObject();
		// Read the values from the JSON object
		String bid = billObject.get("bid").getAsString();
		String date = billObject.get("date").getAsString();
		String timeperiod = billObject.get("timeperiod").getAsString();
		String unitprice = billObject.get("unitprice").getAsString();
		String totalprice = billObject.get("totalprice").getAsString();
		String output = billObj.updateBills(bid, date, timeperiod, unitprice, totalprice);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBill(String billData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(billData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String bid = doc.select("bid").text();
		String output = billObj.deleteBill(bid);
		return output;
	}
}






















