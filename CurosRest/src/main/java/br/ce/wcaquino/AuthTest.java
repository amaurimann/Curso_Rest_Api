package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;


public class AuthTest {
	
	@Test
	
	public void deveAcessarSWAPI() {
		
		given()
			.log().all()
		.when()
			.get("http://swapi.dev/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		
		;
		
	}

}
