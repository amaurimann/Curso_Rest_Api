package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
				.get("http://restapi.wcaquino.me/users/1")
			.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18));
		}
		
		@Test
		public void deveVerificarPrimeiroNivelOutrasFormas() {
			Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/users/1");
			
			//Method path
			System.out.println(response.path("id"));
			Assert.assertEquals(new Integer(1), response.path("id"));
			Assert.assertEquals(new Integer(1), response.path("%s", "id"));
			
			//Method jsonpath
			JsonPath jpath = new JsonPath(response.asString());
			Assert.assertEquals(1, jpath.getInt("id"));
			
			//Method from
			int id = JsonPath.from(response.asString()).getInt("id");
			Assert.assertEquals(1, id);

		}
		@Test
		public void deveVerificarSegundoNivel() {
			given()
			.when()
				.get("http://restapi.wcaquino.me/users/2")
			.then()
			.statusCode(200)
			.body("name", containsString("Joaquina"))
			.body("endereco.rua", is("Rua dos bobos"));
		}
}