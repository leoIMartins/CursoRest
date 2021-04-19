package br.li.martins.rest.tests.refact;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import br.li.martins.rest.core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthTest extends BaseTest {
	
	@Test
	public void naoDeveAcessarApiSemToken() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");	// Neste cenário não deve ser usado o token
		
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
		;
	}
}
