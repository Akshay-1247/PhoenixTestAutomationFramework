package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import com.api.utils.SpecUtil;
import static com.api.constant.Role.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.RestAssured.*;

public class CountApiTest {
	
	@Test(description = "Verify if the count api response is shown correctly",groups = {"api","regression","smoke"})
	public void verifyCountAPIResponse() {
		given()
			.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
			.get("dashboard/count")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data",notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", not(emptyOrNullString()))
			.body("data.key", containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
			.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
	}
	
	@Test(description = "Verify if the count api is giving correct status for invalid token",groups = {"api","negative","regression","smoke"})
	public void countAPITest_MissingAuthToken() {
		given()
			.spec(SpecUtil.requestSpec())
		.when()
			.get("dashboard/count")
		.then()
			.spec(SpecUtil.responseSpec_TEXT(401));
	}
}
