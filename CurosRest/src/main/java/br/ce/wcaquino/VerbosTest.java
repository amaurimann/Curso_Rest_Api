package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

//import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.http.ContentType;
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
	
	@Test
	public void deveSalvarUsuarioViaXML() {
		given()
			.log().all() // print the logs in console
			.contentType(ContentType.XML) //define a type of info has a sending . XML
			.body("<user><name>Jose</name><age>50</age></user>") // SETing new infos . name = Jose and age = 50
		.when()
			.post("http://restapi.wcaquino.me/usersXML")//save new infos, like SET . seting new name and age to users
		.then()
			.log().all()
			.statusCode(201)// status code 201 identified a new resource created
			.body("user.@id", is(notNullValue()))// to this Test the id is random, so to validete this we need considerer jus a not null value in id to sucess
			.body("user.name", is("Jose"))// after set new infos validate name value
			.body("user.age", is("50"))//after set new infos validate age value
		
		;
	}
	
	@Test
	public void deveAlterarUsuario() {
		given()
			.log().all() // print the logs in console
			.contentType("application/json") //define a type of info has a sending . Json
			.body("{\"name\" : \"Usuario alterado\",\"age\" : 80}\n") // SETing new infos . name = Usuario alterado and age = 80
		.when()
			.put("http://restapi.wcaquino.me/users/1")//save new infos, like SET . seting new name and age to users in INDEX 1
		.then()
			.log().all()
			.statusCode(200)// status code 200 identified sucess
			.body("id", is(1))// id PUT, changed index 1
			.body("name", is("Usuario alterado"))// after set new infos validate name value
			.body("age", is(80))//after set new infos validate age value
			.body("salary", is(1234.5678f))
		
		;
	}
	
	@Test
	public void devoCustomizarURL() {
		given()
			.log().all() // print the logs in console
			.contentType("application/json") //define a type of info has a sending . Json
			.body("{\"name\" : \"Usuario alterado\",\"age\" : 80}\n") // SETing new infos . name = Usuario alterado and age = 80
			.pathParam("entidade", "users")
			.pathParam("userId", "1")
		.when()
			.put("http://restapi.wcaquino.me/{entidade}/{userId}")//save new infos, like SET . seting new name and age to users in INDEX 1
		.then()
			.log().all()
			.statusCode(200)// status code 200 identified sucess
			.body("id", is(1))// id PUT, changed index 1
			.body("name", is("Usuario alterado"))// after set new infos validate name value
			.body("age", is(80))//after set new infos validate age value
			.body("salary", is(1234.5678f))
		
		;

	}
	
	@Test
	public void devoCustomizarURLParte2() {
		given()
			.log().all() // print the logs in console
			.contentType("application/json") //define a type of info has a sending . Json
			.body("{\"name\" : \"Usuario alterado\",\"age\" : 80}\n") // SETing new infos . name = Usuario alterado and age = 80
		.when()
			.put("http://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")//save new infos, like SET . seting new name and age to users in INDEX 1
		.then()
			.log().all()
			.statusCode(200)// status code 200 identified sucess
			.body("id", is(1))// id PUT, changed index 1
			.body("name", is("Usuario alterado"))// after set new infos validate name value
			.body("age", is(80))//after set new infos validate age value
			.body("salary", is(1234.5678f))
		
		;

	}
	
	@Test
	public void devoRemoverUsuario() {
		given()
			.log().all()
		.when()
			.delete("http://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204)
		;
		
	}
	
	@Test
	public void devoRemoverUsuarioInexistente() {
		given()
			.log().all()
		.when()
			.delete("http://restapi.wcaquino.me/users/1000")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
		;	
	}
	
	@Test
	public void deveSalvarUsuarioUsandoMap() {
		//Serialização de Json via map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "usuario via map");
		params.put("age", 25);
		
		
		given()
			.log().all() // print the logs in console
			.contentType("application/json") //define a type of info has a sending . Json
			.body(params)
			.when()
			.post("http://restapi.wcaquino.me/users")//save new infos, like SET . seting new name and age to users
		.then()
			.log().all()
			.statusCode(201)// status code 201 identified a new resource created
			.body("id", is(notNullValue()))// to this Test the id is random, so to validete this we need considerer jus a not null value in id to sucess
			.body("name", is("usuario via map"))// after set new infos validate name value
			.body("age", is(25))//after set new infos validate age value
		
		;
	}
}


