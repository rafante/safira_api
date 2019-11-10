package br.com.eliteconsult.financeiro

public class ContaBancaria {

    Banco banco
    String agencia
    String conta

    static constraints = {
        agencia(size: 4..10)
    }

    String toString() {
        return this.banco.toString() + "-" + this.agencia + "/" + this.conta
    }
}
