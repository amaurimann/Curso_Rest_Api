package br.ce.wcaquino;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundo {

	public static void main(String[] args) {
		// Fazendo a requisição
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me:80/ola");

		// Coletando respostas Status code de respostas HTTP Lista
		// https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status
		System.out.println(response.getBody().asString().equals("Ola Mundo!"));

		// Validando Estatus Code
		System.out.println(response.statusCode() == 200);

		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}
}
