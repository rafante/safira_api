package safira

import base.Utils
import br.com.eliteconsult.cadastros.CentroCusto
import br.com.eliteconsult.cadastros.Empresa
import br.com.eliteconsult.cadastros.Endereco
import br.com.eliteconsult.cadastros.Estado
import br.com.eliteconsult.cadastros.FormaPagamento
import br.com.eliteconsult.cadastros.Grupo
import br.com.eliteconsult.cadastros.GrupoParceiroNegocios
import br.com.eliteconsult.cadastros.GrupoTributacao
import br.com.eliteconsult.cadastros.ItemPrazoPagamento
import br.com.eliteconsult.cadastros.Municipio
import br.com.eliteconsult.cadastros.Pais
import br.com.eliteconsult.cadastros.PrazoPagamento
import br.com.eliteconsult.cadastros.SubGrupo
import br.com.eliteconsult.cadastros.SubGrupoParceiroNegocios
import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification

@Integration
@Rollback
class ExemploSpec extends Specification {

    def centroCustoService

    def setupData(){
        Utils.criaBancoTeste()
    }

    def "país - criar"(){
        setupData()

        expect:
        Pais.count() == 1
    }

    def "estado - criar"(){
        setupData()

        expect:
        Estado.count() == 1
    }

    def "municipio - criar"(){
        setupData()

        expect:
        Municipio.count() == 1
    }

    def "endereco - criar"() {
        setupData()

        expect:
        Endereco.count() == 3
    }

    def "empresa - criar"(){
        setupData()

        expect:
        Empresa.count() == 1
    }

    def "centros de custo - criar"(){
        setupData()

        expect:
        CentroCusto.count() == 3
    }

    def "centros de custo - ler 1"(){
        setupData()

        expect:
        CentroCusto.list()[0] != null
    }

    def "forma de pagamento - criar"(){
        setupData()

        expect:
        FormaPagamento.count() == 4
    }

    def "forma de pagamento - garantir 1 controle cheque"(){
        setupData()

        expect:
        FormaPagamento.findByControle_cheque(true) != null
    }

    def "grupo - criar"(){
        setupData()

        expect:
        Grupo.count() == 2
    }

    def "grupo parceiro negocios - criar"(){
        setupData()

        expect:
        GrupoParceiroNegocios.count() == 2
    }

    def "grupo tributacao - criar"(){
        setupData()

        expect:
        GrupoTributacao.count() == 2
    }

    def "prazo pagamento - criar"(){
        setupData()

        expect:
        PrazoPagamento.count() == 2
    }

    def "item prazo pagamento - criar"(){
        setupData()

        expect:
        ItemPrazoPagamento.count() == 5
    }

    def "item prazo pagamento - tem pai"(){
        setupData()

        expect:
        ItemPrazoPagamento.findAllByPrazoPagamentoIsNull().isEmpty()
    }

    def "subgrupo - criar"(){
        setupData()

        expect:
        SubGrupo.count() == 3
    }

    def "subgrupo - tem pai"(){
        setupData()

        expect:
        SubGrupo.findAllByGrupoIsNull().isEmpty()
    }

    def "subgrupo parceiro negocios - criar"(){
        setupData()

        expect:
        SubGrupoParceiroNegocios.count() == 3
    }

    def "subgrupo parceiro negocios - tem pai"(){
        setupData()

        given:
        List<SubGrupoParceiroNegocios> lista = SubGrupoParceiroNegocios.list()

        expect:
        lista.find {it.grupo_parceiro == null} == null
    }

    /*def setup() { //roda antes de cada método de teste
    }

    def cleanup() { // roda depois de cada método de teste
    }

    def setupSpec(){ //roda antes do primeiro método de teste

    }

    def cleanupSpec(){ //roda depois do último método de teste

    }

    void "test something"() {
        given:
        //cria/inicializa variáveis ou mocks ou stubs

        when:
        //alguma coisa acontece

        then:
        //Então faz asserts e verifica o comportamento
    }*/
}
