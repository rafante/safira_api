package br.com.eliteconsult.cadastros

import base.Utils

class Empresa {

    String nome
    String apelido
    String cnpj_cpf
    String inscricao_estadual
    String inscricao_municipal
    String cnae
    String serial_certificado_digital
    Endereco endereco_comercial
    Endereco endereco_cobranca
    Endereco endereco_entrega
//    byte[] logomarca

    // TODO: ERP + SPED - Implementar os seguintes campos na view
    String suframa
    String ind_nat_pj
    Integer ind_ativ

    static defaultValueField = "nome"
    static defaultFilterFields = "nome;cnpj_cpf"

    static belongsTo = [
            empresa: Empresa
    ]

    static hasMany = [
            filiais: Empresa
    ]

    static transients = ['documentoFormatado', 'uf', 'logradouro', 'cidade', '', 'bairro', 'numero']

    static constraints = {
        empresa(nullable: true)
        nome(size: 0..60)
        apelido()
        cnpj_cpf(unique: true, size: 0..14, blank: false, nullable: false,
                validator: { val, obj ->

                    val = val.trim()
                    if (val.length() == 11) {
                        if (!Utils.ValidaCpf(val)) return false
                    } else if (val.length() == 14) {
                        if (!Utils.ValidaCnpj(val)) return false
                    } else return false

                })

        inscricao_estadual(size: 0..14)
        endereco_comercial(cascade: '   all-delete-orphan')
        endereco_cobranca(cascade: 'all-delete-orphan')
        endereco_entrega(cascade: 'all-delete-orphan')
        cnae(nullable: true)
        serial_certificado_digital(size: 0..100)

        //SUFRAMA	Inscrição da pessoa jurídica na Suframa	C	009*	-
        suframa(shared: "suframa")

        //IND_NAT_PJ	Indicador da natureza da pessoa jurídica:
        //00 – Pessoa jurídica em geral
        //01 – Sociedade cooperativa
        //02 – Entidade sujeita ao PIS/Pasep exclusivamente com base na Folha de Salários;	N	002*	-
        ind_nat_pj(size: 2..2)

        //IND_ATIV	Indicador de tipo de atividade preponderante:
        //0 – Industrial ou equiparado a industrial;
        //1 – Prestador de serviços;
        //2 - Atividade de comércio;	N	001	-
        //3 – Atividade financeira;
        //4 – Atividade imobiliária;
        //9 – Outros.
        ind_ativ(range: 0..1)

//        logomarca(maxSize: 204800) // max 200k file
    }

    def getEnderecoPrincipal() {
        return endereco_comercial ?: endereco_cobranca ?: endereco_entrega
    }

    String getUf(){
        return getEnderecoPrincipal()?.uf
    }

    String getLogradouro(){
        return getEnderecoPrincipal()?.logradouro
    }

    String getCidade(){
        return getEnderecoPrincipal()?.cidade
    }

    String getCep(){
        return getEnderecoPrincipal()?.cepFormatado
    }

    String getNumero(){
        return getEnderecoPrincipal()?.numero
    }

    String getBairro(){
        return getEnderecoPrincipal()?.bairro
    }

    String getDocumentoFormatado() {
        if (cnpj_cpf.length() == 11) {
            return "${cnpj_cpf.subSequence(0, 3)}.${cnpj_cpf.subSequence(3, 6)}.${cnpj_cpf.subSequence(6, 9)}-${cnpj_cpf.subSequence(9, 11)}"
        } else if (cnpj_cpf.length() == 14) {
            return "${cnpj_cpf.subSequence(0, 2)}.${cnpj_cpf.subSequence(2, 5)}.${cnpj_cpf.subSequence(5, 8)}/${cnpj_cpf.subSequence(8, 12)}-${cnpj_cpf.subSequence(12, 14)}"
        }
    }

    String toString() {
        return nome
    }

    String getSPEDReg0000(sep, dataini, datafim) {
        return sep + '0000' +
                sep + '003' +
                sep + '0' + sep + sep +
                sep + dataini.format("ddMMyyyy") +
                sep + datafim.format("ddMMyyyy") +
                sep + this.nome +
                sep + this.cnpj_cpf +
                sep + this.endereco_comercial?.municipio?.estado.sigla + this.ie +
                sep + this.municipio?.id +
                sep + this.suframa +
                sep + '00' +
                sep + this.ind_ativ + sep + '\r\n'
    }

    String getSPEDReg0140(sep) {
        return sep + "0140" +
                sep + this.cnpj_cpf +
                sep + this.nome +
                sep + this.cnpj +
                sep + this.uf +
                sep + this.ie +
                sep + this.municipio?.id +
                sep + this.im +
                sep + (this.suframa != "0" ? this.suframa : "") +
                sep + '\r\n'
    }

    // Empresa A
    String getSPEDRegA010(sep) {
        return sep + 'A010' +
                sep + this.cnpj_cpf + sep + '\r\n'
    }

    // Empresa C
    String getSPEDRegC010(sep) {
        return sep + 'C010' +
                sep + this.cnpj_cpf + sep + sep + '\r\n'
    }

    def getPropertyRecursively(String property) {
        try {
            property?.tokenize('.').inject this, { obj, prop ->
                obj ? obj[prop] : null
            }
        } catch (e) {
            return null
        }
    }

}
