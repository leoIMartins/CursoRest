package br.ce.limartins.rest;

import static io.restassured.RestAssured.given;

import org.junit.Test;

public class HTML {

	@Test
	public void deveFazerBuscasComHTML() {
		given()
			.log().all()
		.when()
			.get("")
		.then()
		;
	}
}
