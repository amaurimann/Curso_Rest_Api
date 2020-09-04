package br.ce.wcaquino;

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
				Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me:80/ola");

				// Coletando respostas Status code de respostas HTTP Lista
				// https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status
				Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));

				// Validando Estatus Code
				Assert.assertTrue(response.statusCode() == 200);
				Assert.assertTrue("O Status code deveria ser 201", response.statusCode() == 200);
				Assert.assertEquals(201, response.statusCode());


				//throw new RuntimeException(); // code q pega erro no meio do caminho

				ValidatableResponse validacao = response.then();//code q pega falha mas executa tudo
				validacao.statusCode(200);
	}

}
