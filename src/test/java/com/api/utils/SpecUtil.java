package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.http.ContentType.JSON;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.pojo.UserCredentials;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	
	public static RequestSpecification requestSpec() {
		// To take care of the common request sections (methods)
		RequestSpecification request= new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(JSON)
		.setAccept(JSON)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return request;
	}
	
	public static RequestSpecification requestSpec(Object userCredentials) {
		// To take care of the common request sections (methods)
		RequestSpecification requestSpecification= new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(JSON)
		.setAccept(JSON)
		.setBody(userCredentials)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role) {
		// To take care of the common request sections (methods)
		RequestSpecification requestSpecification= new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(JSON)
		.setAccept(JSON)
		.addHeader("Authorization", AuthTokenProvider.getToken(role))
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return requestSpecification;
	}
	
	public static ResponseSpecification responseSpec_OK() {
		
		ResponseSpecification responseSpecification= new ResponseSpecBuilder()
		.expectContentType(JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(1000L))
		.log(LogDetail.ALL)
		.build();
		return responseSpecification;
		
	}
	
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		
		ResponseSpecification responseSpecification= new ResponseSpecBuilder()
		.expectContentType(JSON)
		.expectStatusCode(statusCode)
		.expectResponseTime(Matchers.lessThan(1000L))
		.log(LogDetail.ALL)
		.build();
		return responseSpecification;
		
	}
	
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		
		ResponseSpecification responseSpecification= new ResponseSpecBuilder()
		.expectStatusCode(statusCode)
		.expectResponseTime(Matchers.lessThan(1000L))
		.log(LogDetail.ALL)
		.build();
		return responseSpecification;
		
	}
	
	
	
	
}
