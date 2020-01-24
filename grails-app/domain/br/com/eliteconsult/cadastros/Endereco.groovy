package br.com.eliteconsult.cadastros

import groovy.sql.Sql

public class Endereco {

    String bairro //bairro eh bairro mesmo
    String cep
    String complemento
    String contato
    String email
    String logradouro //logadouro caso vc n�o saiba � rua, travessa, ponte, avenida etc. (ex.: rua m�rmore, ou beco 13 de maio, ou av Afonso Pena)
    String numero //numero eh numero mesmo, etc
    String telefone1
    String telefone2
    String celular1
    String celular2

    //entao o que vc quer �
    //item.contaReceber.parceiroNegocios.getEnderecoPrincipal().logradouro ou .bairro, ou .numero, etc

    def dataSource
    //static transients = ['municipio_old']
    static belongsTo = [municipio: Municipio] //esse belongs to aqui

    static defaultValueField = "logradouro"
    static defaultFilterFields = "logradouro;bairro"

    String getUf() {
        return municipio?.estado?.sigla
    }

    String getCidade() {
        return municipio?.nome
    }

    String getCepFormatado() {
        String cep = cep
        if(!cep)
            return cep
        cep = "${cep.subSequence(0,5)}-${cep.subSequence(5,8)}"
        return cep
    }

    //ou vc pode pegar o toString: item.contaReceber.parceiroNegocios.enderecoPrincipal.toString() porque? porque o toString dele vai pegar todos os
    //campos e ordenar eles direitinho com espa�o e tal (logradouro depois numero depois complemento etc sacou? sim eh isso ele ja pega tudo de uma vez
    //tudo do endereco n�? lembrando que o anderson mostrou l� que t� vindo errado o nome do cara, o endereco do cara etc tem que consertar o que tiver ivndo errado
    //ta puxando tudo do mesmo cara sim  mas eu tinha feito isso proposito para poder organizar la para depois pegar de outras pessoas
    //nao faz muito sentido porque eh um servi�o dobrado, mas enfim saindo o resultado certo no final hehe ne hahah manda brasa a�
    String toString() {
        return emptyIfInitial(logradouro) + " " +
                emptyIfInitial(numero) + " " +
                emptyIfInitial(complemento) + " " +
                emptyIfInitial(bairro) + " " +
                emptyIfInitial(municipio?.nome) + " " +
                emptyIfInitial(cep)
    }

    Boolean isEmpty() {
        return (!logradouro)
    }

    String emptyIfInitial(String value) {
        if (value) return value
        else return ""
    }

    String getMunicipioOld(){
        if(!municipio_old)
            setMunicipioOld()
     return municipio_old
    }

    void setMunicipioOld(){
        def sql = new Sql(dataSource)
        def rows = sql.rows("SELECT municipio_old FROM endereco WHERE id = "+id)
        if(rows && rows.size()){
            this.municipio_old = rows.get(0).municipio_old.toString()
        }
    }
}