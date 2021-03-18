package br.ce.limartins.rest;

import static io.restassured.RestAssured.*;

import org.junit.Test;

import io.restassured.http.ContentType;

public class EnvioDadosTeste {

	@Test
	public void deveEnviarValorViaQuery() {
		given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/v2/users?format=json")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON)
		;
	}
}
