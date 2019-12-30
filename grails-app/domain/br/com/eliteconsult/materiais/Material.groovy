package br.com.eliteconsult.materiais

import br.com.eliteconsult.cadastros.CentroCusto
import br.com.eliteconsult.cadastros.Grupo
import br.com.eliteconsult.cadastros.GrupoTributacao
import br.com.eliteconsult.cadastros.NCM
import br.com.eliteconsult.cadastros.SubGrupo
import br.com.eliteconsult.ev.ItemExame
import br.com.eliteconsult.servicos.TipoServico

public class Material {

    String apelido
    BigDecimal custo_total = 0
    String descricao
    String ean
    BigDecimal estoque = 0
    BigDecimal estoque_inicial = 0
    BigDecimal estoque_minimo = 0
    BigDecimal fator_estoque_minimo = 0
    BigDecimal estoque_maximo = 0
    BigDecimal fator_estoque_maximo = 0
    BigDecimal peso = 0
    String observacoes
    BigDecimal ponto_pedido = 0
    BigDecimal fator_ponto_pedido = 0
    TipoMaterial tipo_material
    Finalidade finalidade
    String cod_barra
    String ex_ipi
    String cod_gen
    BigDecimal aliq_icms = 0
    Boolean vendido_separadamente
    Boolean abaixoEstoqueMinimo
    Boolean acimaEstoqueMaximo
    Boolean controleLoteValidade
    String textoFinalidade = ""

    static transients = ["estoqueDisponivel", "unidadesMedidaPossiveisUI", "textoFinalidade", "valorEstoque"]

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao;finalidade;grupo;sub_grupo;abaixoEstoqueMinimo;acimaEstoqueMaximo;item_exame.exame.codExame"
    static defaultAutoCompleteFields = "descricao"

    static belongsTo = [
            ncm             : NCM,
            grupo           : Grupo,
            sub_grupo       : SubGrupo,
            grupo_tributacao: GrupoTributacao,
            localizacao     : Localizacao,
            unidade_medida  : UnidadeMedida,
            tipo_servico    : TipoServico,
            ult_doc_ent     : EntradaMaterial
    ]

    static hasMany = [
            tabela_preco_material : TabelaPrecoMaterial,
            descricao_fornecedor  : DescricaoFornecedor,
            LDM                   : LDM,
            tabela_conversao      : TabelaConversao,
            historicoCustoMaterial: HistoricoCustoMaterial,
            materialLote          : MaterialLote,
            item_exame            : ItemExame
    ]

    static constraints = {
        /**
         * Apelido ou nome resumido do material.
         * Tamanho 20
         */
        apelido(size: 0..100)
        /**
         * Valor de custo do material.
         * Caso o material possua LDM, o custo total � obtido pela soma dos custos dos
         * materiais de composi��o. Se for um material sem composi��o, o custo � de livre
         * digita��o.
         * Se for um Material (mat�ria-prima) o custo � definido pela m�dia ponderada.
         */
        custo_total(scale: 5)
        ult_doc_ent()
        /**
         * Descri��o completa do Material
         * Tamanho 60
         */
        descricao(size: 0..100, blank: false, nullable: false)
        /**
         * C�digo EAN.
         * Tamanho 20.
         */
        ean(size: 0..20, nullable: true)

        estoque(scale: 5, nullable: false)
        estoque_inicial(scale: 5, nullable: false)
        /**
         * Estoque m�nimo do material. Esse valor ser� calculado mas pode ser alterado
         * manualmente.
         */
        estoque_minimo(scale: 5, nullable: false)
        fator_estoque_minimo(scale: 5, nullable: false)
        estoque_maximo(scale: 5, nullable: false)
        fator_estoque_maximo(scale: 5, nullable: false)
        peso(scale: 5, nullable: false)
        /**
         * Grupo de Material
         */
        grupo()
        /**
         * Subgrupo do Material
         */
        sub_grupo()

        grupo_tributacao()
        /**
         * Localiza��o f�sica do material
         */
        localizacao()
        /**
         * C�digo NCM.
         * Tamanho 20.
         */
        ncm(size: 0..20)
        /**
         * Obseva��es.
         * Tamanho 255.
         */
        observacoes()
        /**
         * Ponto intermedi�rio de estoque do material
         */
        ponto_pedido()
        fator_ponto_pedido(scale: 5)

        /**
         * 0 - Normal
         * 1 - Composição
         * 2 - Kit
         */
        tipo_material(blank: false, nullable: false)

        /**
         00 – Mercadoria para Revenda;
         01 – Matéria-Prima;
         02 – Embalagem;
         03 – Produto em Processo;
         04 – Produto Acabado;
         05 – Subproduto;
         06 – Produto Intermediário;
         07 – Material de Uso e Consumo;
         08 – Ativo Imobilizado;
         09 – Serviços;
         10 – Outros insumos;
         99 – Outras
         */
        finalidade(blank: false, nullable: false)

        /**
         * Unidade de medida padr�o do Material.
         * Tamanho 5
         */
        unidade_medida()

        aliq_icms(scale: 5)
        vendido_separadamente()
        tipo_servico()
        tabela_preco_material()
        descricao_fornecedor()
        LDM()
        tabela_conversao()
        item_exame()
    }

    static mapping = {
        abaixoEstoqueMinimo formula: 'CASE WHEN estoque < estoque_minimo THEN 1 ELSE 0 END'
        acimaEstoqueMaximo formula: 'CASE WHEN estoque > estoque_maximo THEN 1 ELSE 0 END'
        custo_total scale: 5
        finalidade enumType: 'string'
    }

    String toString() {
        return id.toString().padLeft(8, "0") + "-" + descricao
    }

    BigDecimal getEstoqueOrEstoqueFuturo() {
        return this.estoque
    }

    BigDecimal getTotalEstoqueSaldo(){
        return this.estoque * this.custo_total
    }

    boolean canProduce() {
        return this.finalidade == Finalidade.PRODUTO_INTERMEDIARIO
    }

    BigDecimal getValorEstoque() {
        return (estoque ?: 0) * (getCustoTotalCalc() ?: 0)
    }

    MovimentoMaterial geraMovimentoMaterial(MaterialLote lote, BigDecimal quantidade, BigDecimal valor, UnidadeMedida unidade_medida_mov, String controller, String action, Long id, CentroCusto centroCusto) {
        // Caso a unidade de medida de movimentaçao não tenha sido passada, recupera a unidade de medida basica
        unidade_medida_mov = unidade_medida_mov ?: this.unidade_medida

        int modificador = quantidade < 0 ? -1 : 1

        MovimentoMaterial movimento = new MovimentoMaterial()
        movimento.centroCusto = centroCusto
        movimento.data_movimento = new Date()
        movimento.status = StatusMovimento.CONCLUIDO
        movimento.save()

        ItemMovimentoMaterial itemMovimento = new ItemMovimentoMaterial()
        itemMovimento.material = this
        itemMovimento.unidade_medida = unidade_medida_mov
        itemMovimento.quantidade = quantidade * getConversao(unidade_medida_mov)

        movimento.itemMovimentoMaterial = [itemMovimento]
        itemMovimento.movimentoMaterial = movimento
        itemMovimento.save()
        movimento.save()

        def quantidadeEntrando = Utils.ToDouble(quantidade * getConversao(unidade_medida_mov))

        // Atualiza o estoque do lote caso tenha sido passado como parâmetro
        if (lote) {
            lote.estoque = Utils.ToDouble(lote.estoque) + quantidadeEntrando
            if (!lote.save())
                return null
        }

        if (this.isNormal()) {
            //atualiza custo
            def custoAnterior = this.estoque * this.getCustoTotalCalc()
            def custoEntrando = quantidadeEntrando * valor

            def custoTotal = 0

            def deltaEstoque = this.estoque + quantidadeEntrando
            custoTotal = deltaEstoque ? (custoAnterior + custoEntrando) / deltaEstoque : this.custo_total
            this.custo_total = custoTotal
        }
        this.estoque = Utils.ToDouble(this.estoque) + Utils.ToDouble(quantidade * getConversao(unidade_medida_mov))

        return movimento
    }

    Boolean geraMovimento(MaterialLote lote, BigDecimal quantidade, UnidadeMedida unidade_medida_mov, String controller, String action, Long id, CentroCusto centroCusto) {
        // Caso a unidade de medida de movimentaçao não tenha sido passada, recupera a unidade de medida basica
        unidade_medida_mov = unidade_medida_mov ?: this.unidade_medida

        // Atualiza o estoque do lote caso tenha sido passado como parâmetro
        if (lote) {
            lote.estoque = Utils.ToDouble(lote.estoque) + Utils.ToDouble(quantidade * getConversao(unidade_medida_mov))
            if (!lote.save(flush: true))
                return false
        }

        this.estoque = Utils.ToDouble(this.estoque) + Utils.ToDouble(quantidade * getConversao(unidade_medida_mov))

        return true
    }

    Boolean geraMovimento(MaterialLote materialLote, quantidade, UnidadeMedida unidade_medida_mov, CentroCusto centroCusto) {
        return geraMovimento(materialLote, quantidade, unidade_medida_mov, null, null, null, centroCusto)
    }

    MovimentoMaterial geraMovimentoMaterial(MaterialLote materialLote, BigDecimal quantidade, UnidadeMedida unidade_medida_mov, String controller, String action, Long id) {
        return geraMovimentoMaterial(materialLote, quantidade, unidade_medida_mov, controller, action, id, null)
    }

    MovimentoMaterial geraMovimentoMaterial(BigDecimal quantidade, UnidadeMedida unidade_medida_mov, String controller, String action, Long id) {
        return geraMovimentoMaterial(null, quantidade, unidade_medida_mov, controller, action, id)
    }

    BigDecimal getConversao(UnidadeMedida unidade_medida_conv) {
        // Caso seja a mesma unidade de medida basica, retorna 1
        if (unidade_medida_conv == this.unidade_medida) return 1

        // Busca a unidade de medida a ser convertida
        TabelaConversao conversao = this.tabela_conversao?.find({ it.unidade_medida == unidade_medida_conv })
        return conversao ? conversao.quantidade : 1
    }

    def isNormal() {
        return this.tipo_material == TipoMaterial.NORMAL
    }

    def recalculaComposicao() {
        this.custo_total = 0
        this.peso = 0
        for (LDM ldm in this.LDM) {
            this.custo_total += ldm?.getCustoTotal() ?: 0
            this.peso += ldm?.getPesoTotal() ?: 0
        }
    }

    def alimentaHistoricoCusto() {
        // Caso o custo total ou o último documento de entrada tenha sido modificado, salva o histórico
        if (this.isDirty('custo_total') || this.isDirty('ult_doc_ent')) {
            HistoricoCustoMaterial itemHistoricoMaterial = new HistoricoCustoMaterial()
            itemHistoricoMaterial.material = this
            itemHistoricoMaterial.data = new Date()
            itemHistoricoMaterial.entradaMaterial = this.ult_doc_ent
            itemHistoricoMaterial.custo_total = this.custo_total
            itemHistoricoMaterial.save()

            this.addToHistoricoCustoMaterial(itemHistoricoMaterial)
        }
    }

    BigDecimal getCusto_total() {
        return getCustoTotalCalc()
    }

    def getCustoTotalCalc() {
        return this.isNormal() ? this.custo_total : this.getCustoTotalComposicao()
    }

    def getPesoCalc() {
        return this.isNormal() ? this.peso : this.getPesoTotalComposicao()
    }

    def getCustoTotalComposicao() {
        def ret = 0
        for (LDM ldm in this.LDM) {
            ret += ldm?.getCustoTotal() ?: 0
        }

        return ret
    }

    def getPesoTotalComposicao() {
        def ret = 0
        for (LDM ldm in this.LDM) {
            ret += ldm?.getPesoTotal() ?: 0
        }

        return ret
    }

    def getCustoTotalRel(){
        return (BigDecimal)Math.abs(custo_total)
    }

    def getPosicaoEstoque(Date data) {
        def estoque_sum = ItemMovimentoMaterial.withCriteria {
            projections {
                movimentoMaterial {
                    lt("data_movimento", data)
                }
                sum("quantidade")
            }
            eq("material", this)
        }
        return this.estoque_inicial + (estoque_sum[0] ?: 0)
    }

    BigDecimal getEstoqueDisponivel() {
        return this.estoque
    }

    def getUnidadesMedidaPossiveisUI() {
        def ret = []

        // Adiciona as unidades de medida de acordo com as tabelas de conversão
        this.getUnidadesMedidaPossiveis()?.each {
            def um = [id: it.id, value: it.toString()]
            if (it?.id == this.unidade_medida?.id) {
                um.selected = true
            }
            ret << um
        }

        return ret
    }

    def getLotesPossiveisUI(){
        def ret = []

        if(this.controleLoteValidade){
            return MaterialLote.findAllByMaterial(this).toList()
        }
        return []
    }

    def getUnidadesMedidaPossiveis() {
        def ret = []

        // Adiciona a unidade de medida básica
        ret << this.unidade_medida

        // Adiciona as unidades de medida de acordo com as tabelas de conversão
        this.tabela_conversao?.each {
            ret << it.unidade_medida
        }

        return ret.unique({ a, b -> a.id <=> b.id })
    }

}

enum Finalidade {

    MERCADORIA_PARA_REVENDA(0),
    MATERIA_PRIMA(1),
    EMBALAGEM(2),
    PRODUTO_EM_PROCESSO(3),
    PRODUTO_ACABADO(4),
    SUBPRODUTO(5),
    PRODUTO_INTERMEDIARIO(6),
    MATERIAL_DE_USO_E_CONSUMO(7),
    ATIVO_IMOBILIZADO(8),
    SERVICOS(9),
    OUTROS_INSUMOS(10),
    OUTROS(99)

    final Integer id

    private Finalidade(Integer id) {
        this.id = id
    }

    String toString() {
        name()
    }

    Integer value() {
        this.id
    }
}

enum TipoMaterial {

    NORMAL(0),
    COMPOSICAO(1)

    final Integer id

    private TipoMaterial(Integer id) {
        this.id = id
    }

    String toString() {
        name()
    }

    Integer value() {
        this.id
    }
}
