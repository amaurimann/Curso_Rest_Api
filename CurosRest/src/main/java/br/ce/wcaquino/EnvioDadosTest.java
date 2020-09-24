package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class EnvioDadosTest {

	@Test
	public void deveEnviarValorViaQuery() {
		
		given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/v2/users?format=json")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON)
			
		;	
	}
	
	@Test
	public void deveEnviarValorViaQueryViaParametro() {
		
		given()
			.log().all()
			.queryParam("format", "xml")
			.queryParam("outra", "coisa")
		.when()
			.get("http://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			.contentType(containsString("utf-8"))
			
		;
		
	}

}
