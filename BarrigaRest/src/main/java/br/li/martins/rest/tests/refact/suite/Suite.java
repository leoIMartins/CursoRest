package br.li.martins.rest.tests.refact.suite;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.li.martins.rest.core.BaseTest;
import br.li.martins.rest.tests.refact.AuthTest;
import br.li.martins.rest.tests.refact.ContasTest;
import br.li.martins.rest.tests.refact.MovimentacaoTest;
import br.li.martins.rest.tests.refact.SaldoTest;
import io.restassured.RestAssured;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
	ContasTest.class,
	MovimentacaoTest.class,
	SaldoTest.class,
	AuthTest.class
	
})
public class Suite extends BaseTest {

	@BeforeClass
	public static void login() {
		Map<String, String> login = new HashMap<>();
		login.put("email", "tecnoinfomod1@gmail.com");
		login.put("senha", "admin");
		
		String TOKEN = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
		
		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
		
		RestAssured.get("/reset").then().statusCode(200);
	}
}
