package br.com.eliteconsult.materiais

import br.com.eliteconsult.seguranca.Usuario
import br.com.eliteconsult.cadastros.CentroCusto
import br.com.eliteconsult.cadastros.ParceiroNegocios
import br.com.eliteconsult.cadastros.PrazoPagamento
import br.com.eliteconsult.cadastros.TipoDocumento
import br.com.eliteconsult.config.Periodicidade
import br.com.eliteconsult.financeiro.ContaCorrente
import br.com.eliteconsult.financeiro.ContaPagar
import br.com.eliteconsult.financeiro.PlanoContas
import br.com.eliteconsult.materiais.StatusEntrada

public class EntradaMaterial {

    Date data_entrada
    Date data_pedido_compra
    String num_pedido_compra
    String observacao
    StatusEntrada status = StatusEntrada.COTACAO
    Usuario usuario_correcao
    String motivo
    String documento
    Integer numParcelas = 1
    Date primeiroVencimento = new Date()
    Periodicidade periodicidade
    Integer dias = 30

    Boolean valuesLoaded
    BigDecimal totalLiquido
    BigDecimal totalImpostos
    BigDecimal totalBruto
    BigDecimal icms
    BigDecimal ipi
    BigDecimal pis
    BigDecimal cofins
    BigDecimal frete

    StatusEntrada statusOld

    static defaultValueField = "id"
    static defaultFilterFields = "id"

    static transients = ["totalLiquido", "totalBruto", "icms", "ipi", "pis", "cofins", "valuesLoaded", "statusOld"]

    static belongsTo = [
            fornecedor: ParceiroNegocios,
            prazo_pagamento: PrazoPagamento,
            contaPagar: ContaPagar,
            tipoDocumento: TipoDocumento,
            centroCusto: CentroCusto,
            planoContas: PlanoContas,
            contaCorrente: ContaCorrente
    ]

    static hasMany = [itemEntrada: ItemEntrada]

    static constraints = {
        data_entrada()
        frete()
        data_pedido_compra()
        num_pedido_compra()
        fornecedor(blank: false, nullable: false, asgDefaultFilter: [fornecedor: true])
        observacao()
        prazo_pagamento()
        status(blank: false, nullable: false, size: 0..13)
        itemEntrada()
        documento(size: 0..20)
        contaCorrente()
        planoContas(validator: { val, obj->
            if(obj?.planoContas?.planoContas == null)
                return ['entradaMaterial.mensagens.PlanoContasPai']
        })
        contaPagar(validator: { val, obj ->
            if (obj.contaPagar && !obj.contaPagar?.canDelete()) {
                return ['entradaMaterial.mensagens.contaPagarCompensada']
            }
        })
    }

    static  mapping = {
        status enumType: 'string'
    }

    boolean canEdit() {
        return (status == StatusEntrada.COTACAO || (status == StatusEntrada.PEDIDO_COMPRA))
    }

    Boolean corrigirEntrada(usuario, motivo_correcao) {
        if (this.validate()) {
            geraMovimentoMaterial(-1, false, "entradaItem", "corrigir")

            this.status = StatusEntrada.PEDIDO_COMPRA
            this.usuario_correcao = usuario
            this.motivo = motivo_correcao
            this.data_entrada = null
            this.contaPagar?.delete()
            this.contaPagar = null
            return true
        } else return false
    }

    Boolean geraMovimentoMaterial(int modificador, Boolean atualizaCusto, String controller, String action) {
        try {
            ParametrosMateriais parametrosMateriais = ParametrosMateriais.get(1);
            for (ItemEntrada it in itemEntrada) {
                if (atualizaCusto) {
                    if (it.material.isNormal()) {
                        def conversao = it.material.getConversao(it.unidade_medida)

                        if (parametrosMateriais.tipoCalculoCustoEntradaDefault == TipoCalculoCustoEntrada.ULTIMO_CUSTO) {
                            // Valor da Entrada
                            it.material.custo_total = it.valor
                        } else if (parametrosMateriais.tipoCalculoCustoEntradaDefault == TipoCalculoCustoEntrada.CUSTO_MEDIO) {
                            //((Estoque atual x Custo Atual) + (Quantidade da Entrada x Valor da Entrada)) / (Estoque Atual + Quantidade da Entrada)
                            it.material.custo_total = ((it.material.estoque * it.material.custo_total) +
                                    (it.quantidade * it.valor)) / (it.material.estoque + it.quantidade)
                        }

                        it.material.custo_total = (it.material.custo_total * (1 / conversao) as BigDecimal).divide(1, 2, BigDecimal.ROUND_FLOOR)
                    }
                    it.material.ult_doc_ent = this
                    it.material.alimentaHistoricoCusto()
                    if (!it.material.save()) return false
                }

                it.geraMovimentoMaterial(modificador, atualizaCusto, controller, action, centroCusto)
            }
        } catch (e) {
            return false
        }

        return true
    }

    Boolean geraContaPagar(String controller, String action) {
        def contaPagar = new ContaPagar()
        contaPagar.centroCusto = this.centroCusto
        contaPagar.planoContas = this.planoContas
        contaPagar.contaCorrente = this.contaCorrente
        contaPagar.dataEmissao = this.data_entrada
        contaPagar.dataDocumento = this.data_entrada
        contaPagar.parceiroNegocios = this.fornecedor
        contaPagar.tipoDocumento = this.tipoDocumento
        contaPagar.documento = this.documento
        contaPagar.descricao = "Entrada de Materiais " + this.id
        contaPagar.valorTotal = this.getTotalBruto()

        contaPagar.controller = controller
        contaPagar.action = action
        contaPagar.lancamento_id = this.id
        contaPagar.itensContaPagar = contaPagar.geraParcelas(contaPagar.valorTotal, this.numParcelas, this.primeiroVencimento, this.periodicidade, this.dias)

        this.contaPagar = contaPagar
        if(!this.contaPagar.save(flush: true)){
            return false
        }
        return this.save(flush: true)
    }

    Boolean validaDadosFinanceiro() {
        return (this.numParcelas > 0) && this.primeiroVencimento && this.periodicidade?.id &&
                this.planoContas && this.centroCusto && (this.getTotalBruto() > 0)
    }

    String toString() {
        return id
    }

    def getParcelas(){
        def parcelas = []
        contaPagar.itensContaPagar.each{
            parcelas << [dataVencimento: it.dataVencimento, valor: it.valor]
        }
        return parcelas
    }

    BigDecimal getTotalLiquido() {
        def _totalLiquido = 0
        for (it in itemEntrada) {
            _totalLiquido += it.totalLiquido ?: 0
        }

        return _totalLiquido
    }

    BigDecimal getTotalImpostos() {
        def _totalImpostos = 0
        for (it in itemEntrada) {
            _totalImpostos += it.totalImpostos ?: 0
        }

        return _totalImpostos
    }

    BigDecimal getTotalBruto() {
        BigDecimal _totalBruto = (this.frete ?: 0) // Inicializa a variável com o valor do frete e soma com os itens
        for (it in itemEntrada) {
            _totalBruto += it.totalBruto ?: 0
        }

        return _totalBruto
    }

    BigDecimal getIcms() {
        BigDecimal _icms = 0
        for (it in itemEntrada) {
            _icms += it.icms ?: 0
        }

        return _icms
    }

    BigDecimal getIpi() {
        BigDecimal _ipi = 0
        for (it in itemEntrada) {
            _ipi += it.ipi ?: 0
        }

        return _ipi
    }

    BigDecimal getPis() {
        BigDecimal _pis = 0
        for (it in itemEntrada) {
            _pis += it.pis ?: 0
        }

        return _pis
    }

    BigDecimal getCofins() {
        BigDecimal _cofins = 0
        for (it in itemEntrada) {
            _cofins += it.cofins ?: 0
        }

        return _cofins
    }

    def getPossibleStatus() {
        if (this.status == StatusEntrada.COTACAO) return [StatusEntrada.COTACAO, StatusEntrada.PEDIDO_COMPRA, StatusEntrada.ENTRADA, StatusEntrada.CANCELADA]
        else if (this.status == StatusEntrada.PEDIDO_COMPRA) return [StatusEntrada.COTACAO, StatusEntrada.PEDIDO_COMPRA, StatusEntrada.ENTRADA, StatusEntrada.CANCELADA]
        else if (this.status == StatusEntrada.ENTRADA) return [StatusEntrada.ENTRADA, "CORRIGIR"]
        else if (this.status == StatusEntrada.CANCELADA) return [StatusEntrada.CANCELADA, "CORRIGIR"]
    }

}

enum StatusEntrada {
    COTACAO(0),
    PEDIDO_COMPRA(1),
    ENTRADA(2),
    CANCELADA(3)

    final Integer id

    private StatusEntrada(Integer id) {
        this.id = id
    }

    String toString() {
        name()
    }

    Integer value() {
        this.id
    }
}