package br.com.eliteconsult.ev

import br.com.eliteconsult.cadastros.Setor
import br.com.eliteconsult.materiais.Recipiente

class Exame{

    Long codExame
    String mnemonico
    String descricao
    BigDecimal precoCusto
    BigDecimal precoVenda
    Boolean ativo

    static defaultFilterFields = "codExame;mnemonico;descricao;precoCusto;precoVenda;ativo;setor;recipiente;material"
    static defaultAutoCompleteFields = "id;codExame;mnemonico;descricao"

    static belongsTo = [setor: Setor, recipiente: Recipiente]

    static constraints = {
        codExame(unique: true)
        mnemonico(size: 1..10, unique: true)
        descricao()
        precoCusto()
        precoVenda()
        ativo()
        setor()
        recipiente()
    }

    @Override
    String toString() {
        return id + " - " + descricao + " " + codExame + " " + mnemonico
    }
}
