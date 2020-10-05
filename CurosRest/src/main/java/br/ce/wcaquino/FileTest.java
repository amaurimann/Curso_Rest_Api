package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.junit.Test;


public class FileTest {
	
	@Test
	
	public void deveObrigarEnvioArquivo() {
		//chamando rota de upload de arquivo
		given()
			.log().all()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404) //deveria ser 400
			.body("error", is("Arquivo não enviado"))
		;
		
		
		
	}
	
	
@Test
	
	public void deveFazerUploadDoArquivo() {
		//chamando rota de upload de arquivo
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/URL.pdf"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200) //sucesso
			.body("name", is("URL.pdf"))
		;
		
		
		
	}

}
