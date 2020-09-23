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
			.body("id", is(notNullValue()))// to this Test the id is random, so to validete this we need considerer jus a not null value in id to sucess
			.body("name", is("Jose"))// after set new infos validate name value
			.body("age", is(50))//after set new infos validate age value
		
		;
	}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
		.log().all() // print the logs in console
		.contentType("application/json") //define a type of info has a sending . Json
		.body("{\"age\" : 50}\n") // SETing new infos . age = 50
	.when()
		.post("http://restapi.wcaquino.me/users")//save new infos, like SET . seting new name and age to users
	.then()
		.log().all()
		.statusCode(400)// status code 400 to identifi bad request
		.body("id", is(nullValue()))// in this Test the id is null because is not seting a new object with name
		.body("error", is("Name é um atributo obrigatório"))
	
	;
	}

}

