package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;

public class MasterAPITest {
	
	@Test(description = "Verify if the master api response is shown correctly",groups = {"api","regression","smoke"})
	public void masterAPITest() {
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.post("master")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data", hasKey("mst_oem"))
			.body("$", hasKey("message"))
			.body("$", hasKey("data"))
			.body("data.mst_oem.size()", greaterThan(0))
			.body("data.mst_model.size()", equalTo(3))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
			
	}
	
	@Test(description = "Verify if the master api is giving correct status for invalid token",groups = {"api","negative","regression","smoke"})
	public void invalidTokenMasterAPITest() {
		given()
			.spec(requestSpec())
		.when()
			.post("master")
		.then()
			.spec(responseSpec_TEXT(401));
			
	}
}
