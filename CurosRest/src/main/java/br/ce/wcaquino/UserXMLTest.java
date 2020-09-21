package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.net.http.HttpResponse.BodyHandler;

import org.junit.Test;


public class UserXMLTest {
	
	@Test
	
	public void devoTrabalharComXML() {
		
		given()
		.when()
		.get("http://restapi.wcaquino.me/usersXML/3")
		.then()
		.statusCode(200)
		
		.rootPath("user")
		.body("name", is("Ana Julia"))
		.body("@id", is("3"))
		
		.rootPath("user.filhos")
		.body("name[0]", is("Zezinho"))
		.body("name[1]", is("Luizinho"))
		.body("name.size()", is(2))

		.detachRootPath("filhos")
		.body("filhos.name", hasItem("Luizinho"))
		
		.appendRootPath("filhos")
		.body("name", hasItems("Luizinho", "Zezinho"))
		
		
		; 
		
	}
	
	@Test
	public void devoFazerPesquisasAvancadasComXML() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/usersXML") // url de consulta
		.then()
			.statusCode(200) // validate status OK
			.body("users.user.size()", is(3)) // test size array
			.body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2)) // validate list users age >= 25
			
			.rootPath("users") // set pathern USERS
			.body("user.@id", hasItems("1", "2", "3")) // validate list of id's 
			.body("user.findAll{it.age == 25}.name", is("Maria Joaquina"))// validade age of user
			.body("user.findAll{it.name.toString().contains('n')}.name", hasItems("Maria Joaquina", "Ana Julia"))//validate list with N word in the names list
			.body("user.salary.find{it != null}.toDouble()", is(1234.5678))//validate first salary 
			.body("user.age.collect{it.toInteger() * 2}", hasItems(40, 50, 60))// use collect method to transform info's
			.body("user.name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}", is("MARIA JOAQUINA"))
			;
	}
	

}
