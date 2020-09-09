package br.ce.wcaquino;

import static io.restassured.RestAssured.*;

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


				//throw new RuntimeException(); // code q pega erro no meio do caminho

				ValidatableResponse validacao = response.then();//code q pega falha mas executa tudo
				validacao.statusCode(200);
				
	}
				
				@Test
				public void devoConhecerOutrasFormasRestAssured() {
					//method 1
					Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
					ValidatableResponse validacao = response.then();//code q pega falha mas executa tudo
					validacao.statusCode(200);
					//method 2
					get("http://restapi.wcaquino.me/ola").then().statusCode(200);
					//method 3
					given()//Pré condições
					.when()//Ação
						.get("http://restapi.wcaquino.me/ola")
					.then()//Assertivas
						.statusCode(200);
					
					
				}
		
	}


