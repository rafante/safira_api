package br.com.eliteconsult.financeiro

class ContaCorrente {

    String descricao
    Banco banco
    String agencia
    String conta
    TipoConta tipoConta
    Boolean ativa

    BigDecimal limiteCreditoEspecial
    BigDecimal percentJurosCredEspecial
    BigDecimal limiteCapitalGiro
    BigDecimal percentJurosCapitalGiro
    BigDecimal codigoCarteira
    BigDecimal codigoComplemento
    BigDecimal codigoTransmissao

    static defaultFilterFields = "descricao;banco;agencia;conta;tipoConta;ativa"
    static defaultAutoCompleteFields = "conta;agencia;descricao;banco.descricao;banco.codigo"

    static transients = ['descricaoRel']

    String getDescricaoRel(){
        return this.toString()
    }

    String toString() {
        return this.banco && this.agencia && this.conta ?
                this.banco.toString() + "-" + this.agencia + "/" + this.conta :
                this.descricao
    }

}
