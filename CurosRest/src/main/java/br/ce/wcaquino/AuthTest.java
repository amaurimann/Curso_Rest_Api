package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;


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
	
	
	@Test
	public void deveFazerAutenticacaoComTokenJWT() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "amaurimoraismann@gmail.com");
		login.put("senha", "123456");
		
		//login an api
		//Receber o token
		String token  = given() //String token recebe o resultado dessa requisiçao abaixo
		.log().all()
		.body(login)
		.contentType(ContentType.JSON)//informando tipo de arquivo que esta sendo enviado, arquivo usuario e senha de login
	.when()
		.post("http://barrigarest.wcaquino.me/signin")
	.then()
		.log().all()
		.statusCode(200)
		.extract().path("token");
		
		;	
		
		//obter as contas
		given()
			.log().all()
			.header("Authorization", "JWT " + token) // enviando token jwt para requisicao autorizar acesso
		.when()
			.get("http://barrigarest.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", hasItem("Conta de teste"))
		;
	}
	
	@Test
	
	public void deveAcessarAplicacaoWEB() {
		//login
		String cookie = given()
		.log().all()
		.formParam("email", "amaurimoraismann@gmail.com")
		.formParam("senha", "123456")
		.contentType(ContentType.URLENC.withCharset("UTF-8"))//encode
	.when()
		.post("http://seubarriga.wcaquino.me/logar")
	.then()
		.log().all()
		.statusCode(200)
		.extract().header("set-cookie")
	;
	
		cookie = cookie.split("=")[1].split(";")[0];
		System.out.println(cookie);
		
		//obter conta
		//http://seubarriga.wcaquino.me/contas
		String body = given()
			.log().all()
			.cookie("connect.sid", cookie)
		.when()
			.get("http://seubarriga.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("html.body.table.tbody.tr[0].td[0]", is("Conta de teste"))
			.extract().body().asString();
		;
		XmlPath xmlPath = new XmlPath(CompatibilityMode.HTML, body);
		System.out.println(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));
	}
				
}
