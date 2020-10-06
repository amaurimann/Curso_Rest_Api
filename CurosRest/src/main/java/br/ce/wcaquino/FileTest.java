package br.ce.wcaquino;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
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

@Test

	public void deveFazerUploadDoArquivoGrande() {
	//chamando rota de upload de arquivo
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/#bot_control_despedida.csv"))// CAMINHO DO ARQUIVO A SER ENVIADO
		.when()
			.post("http://restapi.wcaquino.me/upload") // COMANDO DE UPLOAD SOB URL
			.then()
			.log().all() // LOG ALL
			.time(lessThan(10000l))// DEFINE MAX TIME DO TEST
			.statusCode(413) //sucesso
	;
	
}

	@Test

	public void deveBaixarArquivo() throws IOException {
		byte[] image = given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/download")// receber arquivo com .GET
		.then()
			.log().all()
			.statusCode(200)
			.extract().asByteArray();
		;
		File imagem = new File("src/main/resources/file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		System.out.println(imagem.length());
		Assert.assertThat(imagem.length(), lessThan(1000000l));
	
}


}
