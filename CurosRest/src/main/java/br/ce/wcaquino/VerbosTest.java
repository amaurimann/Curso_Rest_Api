package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
//import org.hamcrest.Matchers;
import org.junit.Test;
//import io.restassured.RestAssured;


public class VerbosTest {
	
	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all() // print the logs in console
			.contentType("application/json") //define a type of info has a sending . Json
			.body("{\"name\" : \"Jose\",\"age\" : 50}\n") // SETing new infos . name = Jose and age = 50
		.when()
			.post("http://restapi.wcaquino.me/users")//save new infos, like SET . seting new name and age to users
		.then()
			.log().all()
			.statusCode(201)// status code 201 identified a new resource created
			.body("id", is(notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50))
		
		;
		
		
	}

}

