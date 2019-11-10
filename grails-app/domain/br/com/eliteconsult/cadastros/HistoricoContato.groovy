package br.com.eliteconsult.cadastros

public class HistoricoContato {

    String contato
    Date data = new Date()
    String historico

    static belongsTo = [parceiroNegocios: ParceiroNegocios]

    static constraints = {
        parceiroNegocios(asgDefaultFilter: [cliente: true])

        /**
         * Data do contato
         */
        data()
        /**
         * Descri��o do contato.
         * Tamanho 255.
         */
        historico()
        /**
         * Nome de contato.
         */
        contato()
    }

}