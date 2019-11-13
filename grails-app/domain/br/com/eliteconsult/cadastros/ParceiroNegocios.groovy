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
        /**
         * Nome do Parceiro de Neg�cio
         * Tamanho 60
         */
        nome(size: 0..60, blank: false, nullable: false)

        /**
         * Apelido (nome fantasia, nome de tratamento)
         */
        apelido()

        /**
         * Tamanho 14
         * Valida��o de CPF ou CNPJ
         */
        cnpj_cpf(size: 0..14, unique: true, validator: { val, obj ->
            val = val?.trim()
            // Valida campo vazio
            if (!val) {
                if (ParametrosGerais.read(1).cpfCnpjObrigatorio) return false
            } else if (val) {
                if (val?.length() == 11) {
                    if (!Utils.ValidaCpf(val)) return false
                } else if (val?.length() == 14) {
                    if (!Utils.ValidaCnpj(val)) return false
                } else return false
            }

        })

        /**
         * Tamanho 14. Realizar valida��o.
         */
        inscricao_estadual(size: 0..14, nullable: true)
        inscricao_municipal(size: 0..14, nullable: true)

        endereco_cobranca(cascade: "all-delete-orphan")
        endereco_comercial(cascade: "all-delete-orphan")
        endereco_entrega(cascade: "all-delete-orphan")
        historicoContato(cascade: "all-delete-orphan")

        cliente()
        fornecedor()
        representanteVenda()
        transportadora()

        /**
         * Valor de cr�dito para compras
         */
        credito(scale: 2)

        /**
         * Percentual de comiss�o do representante
         */
        percentual_comissao(size: 0..15, scale: 2)
        /**
         * 0 - Comiss�o sob venda.
         * 1 - Comiss�o sob recebimento.
         */
        tipo_comissao()

        /**
         * 0 - Interno
         * 1 - Externo
         */
        tipo_vendedor(size: 0..7)

        suframa()

        historicoContato(cascade: "all-delete-orphan")

        prazo_pagamento_default()

        grupo_parceiro(nullable: true)
        sub_grupo_parceiro(nullable: true)

        historicoContato(nullable: true)
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
