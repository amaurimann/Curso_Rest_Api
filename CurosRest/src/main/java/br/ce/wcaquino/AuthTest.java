package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import org.junit.Test;


public class AuthTest {
	
	@Test
	
	public void deveAcessarSWAPI() {
		
		given()
			.log().all()
		.when()
			.get("http://swapi.dev/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		
		;
	}
	
	//0b9f4497ef28f6a3fa756df8dbf10b69 api key
	//http://api.openweathermap.org/data/2.5/weather?q=Fortaleza,BR&appid=0b9f4497ef28f6a3fa756df8dbf10b69&&units=metric
	
	@Test
	public void deveObterClima() {
		
		given()
			.log().all()
			.queryParam("q", "Fortaleza,BR")
			.queryParam("appid", "0b9f4497ef28f6a3fa756df8dbf10b69")
			.queryParam("units", "metric")
		.when()
			.get("http://api.openweathermap.org/data/2.5/weather")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Fortaleza"))
			.body("coord.lon", is(-38.52f))
			.body("main.temp", greaterThan(25f))
		
		;
		
	}
	
	
	@Test
	public void naoDeveAcessarSemSenha() {
		
		given()
		.log().all()
	.when()
		.get("http://restapi.wcaquino.me/basicauth")
	.then()
		.log().all()
		.statusCode(401)//Status 401 = não autorizado
		
		;	
		
	}
	
	
	@Test
	public void DeveFazerAutenticacaoBasica() {
		
		given()
		.log().all()
	.when()
		.get("http://admin:senha@restapi.wcaquino.me/basicauth")//passando usuario e senha para acesso no browser
	.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		
		;	
		
	}
	
	@Test
	public void DeveFazerAutenticacaoBasica2() {
		
		given()
		.log().all()
		.auth().basic("admin", "senha")//passando usuario e senha para acesso no browser
	.when()
		.get("http://restapi.wcaquino.me/basicauth")
	.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		
		;	
		
	}
	
	@Test
	public void DeveFazerAutenticacaoBasicaChallenge() {
		
		given()
		.log().all()
		.auth().preemptive().basic("admin", "senha")//passando usuario e senha para acesso no browser
	.when()
		.get("http://restapi.wcaquino.me/basicauth2")
	.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		
		;	
		
	}

}
