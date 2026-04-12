package com.api.tests.datadriven;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.utils.SpecUtil;

public class CreateJobAPIDataDrivenTest {
	
	
	@Test(description = "Verify if the create job api is able to create inwarranty job",groups = {"api","regression","smoke","datadriven"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "CreateJobApiDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {
		
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
