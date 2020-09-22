package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.net.http.HttpResponse.BodyHandler;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.xml.element.Node;


public class UserXMLTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "https://restapi.wcaquino.me";
		//RestAssured.port = 443; //http:
		//RestAssured.basePath = "/v2"; //http://restapi.wcaquino.me/v2/users
		
	}
	
	@Test
	
	public void devoTrabalharComXML() {
		
		given()
		.when()
			.log().all()
			.get("/usersXML/3")
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
			.get("/usersXML") // url de consulta
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
	
	@Test
	public void devoFazerPesquisasAvancadasComXMLEJava() {
		ArrayList<Node> nomes = given()
		.when()
			.get("/usersXML") // url de consulta
		.then()
			.statusCode(200)
			.extract().path("users.user.name.findAll{it.toString().contains('n')}");
			//.extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}");
		;
		Assert.assertEquals(2, nomes.size());
		Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
		Assert.assertTrue("ANA JULIA".equalsIgnoreCase(nomes.get(1).toString()));
		//Assert.assertEquals("Maria Joaquina".toUpperCase(), nome.toUpperCase());
		//System.out.println(nome.toString());
		//System.out.println(nomes);
		
}
	
	@Test
	public void devoFazerPesquisasAvancadasComXPath() {
		
		given()
		.when()
			.get("/usersXML")
		.then()
		.statusCode(200)
		.body(hasXPath("count(/users/user)", is("3")))
		.body(hasXPath("/users/user[@id = '1']"))
		.body(hasXPath("//user[@id = '2']"))
		.body(hasXPath("//name[text() = 'Luizinho']/../../name", is("Ana Julia")))
		.body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
		
		.body(hasXPath("/users/user/name", is("João da Silva")))
		.body(hasXPath("//name", is("João da Silva")))
		.body(hasXPath("/users/user[2]/name", is("Maria Joaquina")))
		.body(hasXPath("/users/user[last()]/name", is("Ana Julia")))
		.body(hasXPath("count(/users/user/name[contains(., 'n')])", is("2")))
		.body(hasXPath("//user[age < 24]/name", is("Ana Julia")))
		.body(hasXPath("//user[age > 20 and age < 30]/name", is("Maria Joaquina")))
		.body(hasXPath("//user[age > 20][age < 30]/name", is("Maria Joaquina")))
		;
	}
	
	
	
}
