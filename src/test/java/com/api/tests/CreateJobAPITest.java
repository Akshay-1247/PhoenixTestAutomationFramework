package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.api.utils.DateTimeUtil.*;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {
	
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description = "Creating the createJobAPI payload")
	public void setup() {
		
		Customer customer = new Customer("Akshay","H" ,"9809809800" ,"","saitama@yopmail.com" , "" );
		CustomerAddress customerAddress = new CustomerAddress("Mumbai", "Mumbai", "Mumbai","Mumbai", "Mumbai","400703", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "686798561951218", "686798561951218", "686798561951218", getTimeWithDaysAgo(10), 
				Product.IPHONE.getCode(), Model.IPHONE_11.getCode());
		Problems problems = new Problems(Problem.POOR_BATTERY_LIFE.getCode(), "Battery Health");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_status.IN_WARRANTY.getCode(), OEM.APPLE.getCode(), customer, customerAddress, customerProduct, problemsList);
		
		
	}
	
	@Test(description = "Verify if the create job api is able to create inwarranty job",groups = {"api","regression","smoke"})
	public void createJobAPITest() {
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
			.post("job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"));
		
	}
}
