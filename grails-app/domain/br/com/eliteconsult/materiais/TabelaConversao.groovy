package br.com.eliteconsult.materiais

public class TabelaConversao {

    BigDecimal quantidade

    static belongsTo = [
            material: Material,
            unidade_medida: UnidadeMedida]

    static constraints = {

        material()

        /**
         * Unidade de Conversão
         * Tamanho 5
         */
        unidade_medida()

        /**
         * Proporção de quantidade da Unidade de Medida
         */
        quantidade(scale: 3)

    }

}