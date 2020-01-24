package br.com.eliteconsult.cadastros

public class HistoricoContato {

    String contato
    Date data = new Date()
    String historico

    static belongsTo = [parceiroNegocios: ParceiroNegocios]

}