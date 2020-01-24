package br.com.eliteconsult.materiais

import br.com.eliteconsult.cadastros.TabelaPreco

public class TabelaPrecoMaterial {

    TabelaPreco tabela
    BigDecimal valor
    BigDecimal margem
    Boolean fixar_preco = true

    static defaultFilterFields = "tabela;material;material.grupo;material.sub_grupo;material.localizacao"

    static belongsTo = [material: Material]

    static transients = ["precoSugerido", "precoMinimo"]

    String toString() {
        return material?.descricao + "(" + tabela?.descricao + ") ->" +
                valor
    }


    BigDecimal getPrecoSugerido() {
        def mlv_sugerida = tabela?.margem_sugerida
        def parametros = ParametrosPrecos.findById(1)

        return material?.custo_total / ((100 - parametros?.aliquota_cofins
                - parametros?.aliquota_csll - parametros?.aliquota_icms
                - parametros?.aliquota_ipi - parametros?.aliquota_irpj - parametros?.aliquota_pis
                - parametros?.aliquota_simples - parametros?.percentual_comissao
                - tabela?.margem_sugerida) / 100)

    }

    BigDecimal getPrecoMinimo() {
        def parametros = ParametrosPrecos.findById(1)

        return material?.custo_total / ((100 - parametros?.aliquota_cofins
                - parametros?.aliquota_csll - parametros?.aliquota_icms
                - parametros?.aliquota_ipi - parametros?.aliquota_irpj - parametros?.aliquota_pis
                - parametros?.aliquota_simples - parametros?.percentual_comissao
                - tabela?.margem_minima) / 100)

    }

    BigDecimal getValor() {
        def parametros = ParametrosPrecos.findById(1)

        if (fixar_preco) {
            return valor
        } else {
            return material?.custo_total / ((100 - parametros?.aliquota_cofins
                    - parametros?.aliquota_csll - parametros?.aliquota_icms
                    - parametros?.aliquota_ipi - parametros?.aliquota_irpj - parametros?.aliquota_pis
                    - parametros?.aliquota_simples - parametros?.percentual_comissao
                    - margem) / 100)
        }
    }

    BigDecimal getMargem() {
        def parametros = ParametrosPrecos.findById(1)

        if (fixar_preco) {
            // (preco_praticado - ((preco_praticado * ((soma_das_aliquotas+comissao)/100)) - custo_total)/preco_praticado)*100
            def aliquotas = valor * ((parametros?.aliquota_cofins
                    + parametros?.aliquota_csll + parametros?.aliquota_icms
                    + parametros?.aliquota_ipi + parametros?.aliquota_irpj + parametros?.aliquota_pis
                    + parametros?.aliquota_simples + parametros?.percentual_comissao) / 100)
            try {
                return ((valor - aliquotas - material?.custo_total) / valor) * 100
            } catch (e) {
                return 0
            }
        } else {
            return margem
        }
    }
}