package br.ce.wcaquino;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void testOlaMundo() {
		// Fazendo a requisição
		Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");

		// Coletando respostas Status code de respostas HTTP Lista
		// https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));

		// Validando Estatus Code
		Assert.assertTrue(response.statusCode() == 200);
		Assert.assertTrue("O Status code deveria ser 201", response.statusCode() == 200);
		Assert.assertEquals(200, response.statusCode());

		// throw new RuntimeException(); // code q pega erro no meio do caminho

		ValidatableResponse validacao = response.then();// code q pega falha mas executa tudo
		validacao.statusCode(200);

	}

	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		// method 1
		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();// code q pega falha mas executa tudo
		validacao.statusCode(200);
		// method 2
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);
		// method 3
		given()// Pré condições
				.when()// Ação
				.get("http://restapi.wcaquino.me/ola").then()// Assertivas
				.statusCode(200);

	}

	@Test
	public void devoConhecerMatchersHamcrest() {
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(128, Matchers.is(128));
		Assert.assertThat(128, Matchers.isA(Integer.class));
		Assert.assertThat(128d, Matchers.isA(Double.class));
		Assert.assertThat(128d, Matchers.greaterThan(120d));
		Assert.assertThat(128d, Matchers.lessThan(130d));

		List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1, 3, 5, 7, 9));
		assertThat(impares, containsInAnyOrder(1, 3, 9, 7, 5));
		assertThat(impares, hasItem(9));
		assertThat(impares, hasItems(9, 1, 3));

		assertThat("Maria", is(not("João")));
		assertThat("Maria", (not("João")));
		assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));

	}

	@Test
	public void devoValidadrBody() {
		given()// Pré condições
				.when()// Ação
				.get("http://restapi.wcaquino.me/ola").then()// Assertivas
				.statusCode(200)
				.body(is("Ola Mundo!"))
				.body(containsString("Mundo"))
				.body(is(not(nullValue())));
	}

}
