package br.com.eliteconsult.materiais

public class HistoricoCustoMaterial {
    Date data
    BigDecimal custo_total

    static belongsTo = [material: Material, entradaMaterial: EntradaMaterial]

}