package br.com.eliteconsult.servicos

public class TipoServico {
    String cod_tipo_servico
    String descricao

    static constraints = {
        cod_tipo_servico(unique: true, blank: false, size: 1..5)
        data_inclusao(shared: "data_inclusao")
        data_ultima_alteracao(shared: "data_ultima_alteracao")
        usuario(shared: "usuario")
    }

    String toString() {
        Utils.Substring(descricao, 50)
    }
}