package br.li.martins.rest.tests.refact;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import br.li.martins.rest.core.BaseTest;
import br.li.martins.rest.tests.Movimentacao;
import br.li.martins.rest.utils.BarrigaUtils;
import br.li.martins.rest.utils.DataUtils;

public class MovimentacaoTest extends BaseTest {
	
	@Test
	public void deveInserirMovimentacaoSucesso() {
		Movimentacao mov = getMovimentacaoValida();
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
			.body("descricao", is("Descrcao da movimentacao"))
			.body("valor", is("100.00"))
		;
	}
	
	@Test
	public void deveValidarCamposObrigatoriosMovimentacao() {
		given()
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(8))
			.body("msg", hasItems(
					"Data da Movimenta��o � obrigat�rio",
					"Data do pagamento � obrigat�rio",
			        "Descri��o � obrigat�rio",
			        "Interessado � obrigat�rio",
			        "Valor � obrigat�rio",
			        "Valor deve ser um n�mero",
			        "Conta � obrigat�rio",
			        "Situa��o � obrigat�rio"))
		;
	}
	
	@Test
	public void naoDeveInserirMovimentacaoComDataFutura() {
		Movimentacao mov = getMovimentacaoValida();
		mov.setData_transacao(DataUtils.getDataDiferencaDias(2));
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(1))
			.body("msg", hasItem("Data da Movimenta��o deve ser menor ou igual � data atual"))
		;
	}
	
	@Test
	public void naoDeveRemoverContaComMovimentacao() {
		Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta com movimentacao");
		
		given()
			.pathParam("id", CONTA_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
		;
	}
	
	@Test
	public void deveRemoverMovimentacao() {
		Integer MOV_ID = BarrigaUtils.getIdMovPelaDescricao("Movimentacao para exclusao");
		
		given()
			.pathParam("id", MOV_ID)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204)
		;
	}
	
	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(BarrigaUtils.getIdContaPeloNome("Conta para movimentacoes"));
		//mov.setUsuario_id(usuario_id);
		mov.setDescricao("Descrcao da movimentacao");
		mov.setEnvolvido("Envolvido na mov");
		mov.setTipo("REC");
		mov.setData_transacao(DataUtils.getDataDiferencaDias(-1));
		mov.setData_pagamento(DataUtils.getDataDiferencaDias(5));
		mov.setValor(100f);
		mov.setStatus(true);
		return mov;
	}
}
