package safira

import base.Utils
import br.com.eliteconsult.cadastros.CentroCusto
import br.com.eliteconsult.cadastros.Empresa
import br.com.eliteconsult.cadastros.Endereco
import br.com.eliteconsult.cadastros.Estado
import br.com.eliteconsult.cadastros.Municipio
import br.com.eliteconsult.cadastros.Pais
import br.com.eliteconsult.cadastros.ParametrosGerais
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
