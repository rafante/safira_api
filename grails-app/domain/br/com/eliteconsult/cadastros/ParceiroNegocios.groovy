package br.com.eliteconsult.cadastros

import base.Utils

public class ParceiroNegocios {

    String apelido //nome fantasia (ex.: Petrobrás)
    String cnpj_cpf
    String inscricao_municipal
    String inscricao_estadual
    String nome //ele tem um nome que seria a Razão Social do cara (ex.: PETROLEO BRASILEIRO S.A. PETROBRAS)
    String suframa

    Boolean cliente
    Boolean fornecedor
    Boolean representanteVenda
    Boolean transportadora

    // Cliente
    BigDecimal credito

    // Representante de Venda
    BigDecimal percentual_comissao
    TipoComissao tipo_comissao
    TipoVendedor tipo_vendedor
    PrazoPagamento prazo_pagamento_default

    static belongsTo = [endereco_comercial: Endereco, //os Parceiros de Negócio tem um endereco comercial
                        endereco_cobranca : Endereco, //um endereco de cobranca
                        endereco_entrega  : Endereco, //e um endereço de entrega
                        tabela_preco      : TabelaPreco,
                        grupo_parceiro    : GrupoParceiroNegocios,
                        sub_grupo_parceiro: SubGrupoParceiroNegocios]

    static hasMany = [historicoContato: HistoricoContato]

    static transients = ['documentoFormatado', 'uf', 'logradouro', 'cidade', 'cep', 'bairro', 'numero']

    static defaultValueField = "nome"
    static defaultFilterFields = "nome;cnpj_cpf;endereco_comercial.municipio;cliente;fornecedor;representanteVenda;transportadora"
    static defaultAutoCompleteFields = "nome;cnpj_cpf"

    static constraints = {
        endereco_cobranca(cascade: "all-delete-orphan")
        endereco_comercial(cascade: "all-delete-orphan")
        endereco_entrega(cascade: "all-delete-orphan")
        historicoContato(cascade: "all-delete-orphan")
        historicoContato(cascade: "all-delete-orphan")
    }

    String getDocumentoFormatado() {
        if (cnpj_cpf.length() == 11) {
            return "${cnpj_cpf.subSequence(0, 3)}.${cnpj_cpf.subSequence(3, 6)}.${cnpj_cpf.subSequence(6, 9)}-${cnpj_cpf.subSequence(9, 11)}"
        } else if (cnpj_cpf.length() == 14) {
            return "${cnpj_cpf.subSequence(0, 2)}.${cnpj_cpf.subSequence(2, 5)}.${cnpj_cpf.subSequence(5, 8)}/${cnpj_cpf.subSequence(8, 12)}-${cnpj_cpf.subSequence(12, 14)}"
        }
    }

    String getUf() {
        return getEnderecoPrincipal()?.uf
    }

    String getLogradouro() {
        return getEnderecoPrincipal()?.logradouro
    }

    String getCidade() {
        return getEnderecoPrincipal()?.cidade
    }

    String getCep() {
        return getEnderecoPrincipal()?.cepFormatado
    }

    String getNumero() {
        return getEnderecoPrincipal()?.numero
    }

    String getBairro() {
        return getEnderecoPrincipal()?.bairro
    }

    String toString() {
        return id.toString().padLeft(8, '0') + '-' + nome
    }

    String getSPEDReg0150(sep) {
        return sep + "0150" +
                sep + this.cnpj_cpf +
                sep + this.nome +
                sep + this.endereco_comercial?.municipio?.estado?.pais?.cod_pais +
                sep + (this.cnpj_cpf?.length() == 14 ? this.cnpj_cpf : "") + // CNPJ
                sep + (this.cnpj_cpf?.length() == 11 ? this.cnpj_cpf : "") + // CPF
                sep + this.inscricao_estadual +
                sep + this.endereco_comercial?.municipio?.id +
                sep + (this.suframa != "0" ? this.suframa : "") +
                sep + this.endereco_comercial?.logradouro +
                sep + this.endereco_comercial?.numero +
                sep + this.endereco_comercial?.complemento +
                sep + this.endereco_comercial?.bairro + sep + '\r\n'
    }

    //este método retorna primeiro o endereço comercial, se ele nao tiver sido cadastrado, retorna o de cobrança se nao tiver sido cadastrado, retorna o e entrega
    //se nao tiver nenhum cadastrado, vai retornar null porque o último é o endereco de entrega e se ele nao tiver sido cadastrao vai ser null
    //portanto vc quer o item.contaReceber.parceiroNegocios.enderecoPrincipal() - ai vamos ver o que o Endereco tem
    def getEnderecoPrincipal() {
        return endereco_comercial ?: endereco_cobranca ?: endereco_entrega
    }
}

//enum TipoComissao implements org.springframework.context.MessageSourceResolvable {
//
//    SOB_VENDA,
//    SOB_RECEBIMENTO
//
//    public Object[] getArguments() { [] as Object[] }
//    public String[] getCodes() { [ name() ] }
//    public String getDefaultMessage() { name() }
//}

enum TipoComissao {

    SOB_VENDA(0),
    SOB_RECEBIMENTO(1)

    final Integer id

    private TipoComissao(Integer id) {
        this.id = id
    }

    String toString() {
        name()
    }

    Integer value() {
        this.id
    }
}

//enum TipoVendedor implements org.springframework.context.MessageSourceResolvable {
//
//    INTERNO,
//    EXTERNO
//
//    public Object[] getArguments() { [] as Object[] }
//    public String[] getCodes() { [ name() ] }
//    public String getDefaultMessage() { name() }
//}

enum TipoVendedor {

    INTERNO(0),
    EXTERNO(1)

    final Integer id

    private TipoVendedor(Integer id) {
        this.id = id
    }

    String toString() {
        name()
    }

    Integer value() {
        this.id
    }
}
