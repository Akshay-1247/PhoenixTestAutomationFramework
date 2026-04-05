package com.api.utils;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() {
		
		Customer customer = new Customer("Akshay","H" ,"9809809800" ,"","saitama@yopmail.com" , "" );
		CustomerAddress customerAddress = new CustomerAddress("Mumbai", "Mumbai", "Mumbai","Mumbai", "Mumbai","400703", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct("2026-01-10T18:30:00.000Z", "686798556619518", "686798556619518", "686798556619518", "2026-01-10T18:30:00.000Z", 3, 3);
		Problems problems = new Problems(2, "Battery Health");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 2, customer, customerAddress, customerProduct, problemsList);
		
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
