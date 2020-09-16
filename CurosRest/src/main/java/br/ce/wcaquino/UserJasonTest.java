package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserJasonTest {

	@Test
	public void deveVerificarPrimeiroNivel() {
		given()
		.when()
		.get("http://restapi.wcaquino.me/users/1").then().statusCode(200)
		.body("id", is(1))
				.body("name", containsString("Silva"))
				.body("age", greaterThan(18));
	}

	@Test
	public void deveVerificarPrimeiroNivelOutrasFormas() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/users/1");

		// Method path
		System.out.println(response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("%s", "id"));

		// Method jsonpath
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));

		// Method from
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);

	}

	@Test
	public void deveVerificarSegundoNivel() {
		given()
		.when()
		.get("http://restapi.wcaquino.me/users/2")
		.then().statusCode(200)
				.body("name", containsString("Joaquina"))
				.body("endereco.rua", is("Rua dos bobos"));
	}

	@Test
	public void deveVerificarLista() {
		given()
		.when()
		.get("http://restapi.wcaquino.me/users/3")
		.then().statusCode(200)
				.body("name", containsString("Ana")).body("filhos", hasSize(2))
				.body("filhos[0].name", is("Zezinho"))
				.body("filhos[1].name", is("Luizinho"))
				.body("filhos.name", hasItem("Zezinho"))
				.body("filhos.name", hasItems("Zezinho", "Luizinho"));
	}

	@Test
	public void deveVerificarErroUsuarioInexistente() {
		given()
		.when()
		.get("http://restapi.wcaquino.me/users/4")
		.then().statusCode(404)
		.body("error", is("Usuário inexistente"));
	}

	@Test
	public void deveVerificarListaRaiz() {
		given()
		.when()
		.get("http://restapi.wcaquino.me/users")
		.then().statusCode(200)
		.body("$", hasSize(3))
				.body("name", hasItem("João da Silva"))
				.body("name", hasItems("João da Silva", "Maria Joaquina"))
				.body("age[0]", is(30))
				.body("age[1]", is(25))
				.body("filhos.name", hasItem(Arrays.asList("Zezinho", "Luizinho")))
				.body("salary", contains(1234.5678f, 2500, null))

		;
	}
	
	@Test
	public void deveFazerVerificacoesAvancadas() {
		given()
		.when()
		.get("http://restapi.wcaquino.me/users")
		.then()
		.statusCode(200)
		.body("$", hasSize(3))
		.body("age.findAll{it <= 25}.size()", is(2))
		.body("age.findAll{it <= 25 && it > 20}.size()", is(1))
		.body("age.findAll{it <= 25 && it > 20}", hasItem(25))
		.body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
		.body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
		.body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))//[-1] ultimo elemento
		.body("find{it.age <= 25}.name", is("Maria Joaquina"))
		.body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina", "Ana Júlia"))
		.body("findAll{it.name.length() > 10}.name", hasItems("João da Silva", "Maria Joaquina"))
		.body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
		.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
		.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1)))
		.body("age.collect{it * 2}", hasItems(60, 50, 40))
		.body("id.max()", is(3))
		.body("salary.min()", is(1234.5678f))
		.body("salary.findAll{it != null}.sum()", is(closeTo(3734.5687f, 0.001)))
		.body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)))
		
		;
	}
	
	
	
	
	
	
	
	
	
	
	

}
