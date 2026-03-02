package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() {
		
		Header authHeader = new Header("Authorization",getToken(FD));
		given()
			.baseUri(getProperty("BASE_URI"))
			.header(authHeader)
			.accept(ContentType.JSON)
			.log().all()
		.when()
			.get("userdetails")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(1000L))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
	}
}
