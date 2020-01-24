package br.com.eliteconsult.materiais

public class TabelaConversao {

    BigDecimal quantidade

    static belongsTo = [
            material: Material,
            unidade_medida: UnidadeMedida]

}