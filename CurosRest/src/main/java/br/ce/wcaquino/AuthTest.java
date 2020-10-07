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

}
