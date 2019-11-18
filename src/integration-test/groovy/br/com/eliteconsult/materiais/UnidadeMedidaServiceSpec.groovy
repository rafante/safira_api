package br.com.eliteconsult.materiais

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class UnidadeMedidaServiceSpec extends Specification {

    UnidadeMedidaService unidadeMedidaService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new UnidadeMedida(...).save(flush: true, failOnError: true)
        //new UnidadeMedida(...).save(flush: true, failOnError: true)
        //UnidadeMedida unidadeMedida = new UnidadeMedida(...).save(flush: true, failOnError: true)
        //new UnidadeMedida(...).save(flush: true, failOnError: true)
        //new UnidadeMedida(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //unidadeMedida.id
    }

    void "test get"() {
        setupData()

        expect:
        unidadeMedidaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaService.list(max: 2, offset: 2)

        then:
        unidadeMedidaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        unidadeMedidaService.count() == 5
    }

    void "test delete"() {
        Long unidadeMedidaId = setupData()

        expect:
        unidadeMedidaService.count() == 5

        when:
        unidadeMedidaService.delete(unidadeMedidaId)
        sessionFactory.currentSession.flush()

        then:
        unidadeMedidaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        UnidadeMedida unidadeMedida = new UnidadeMedida()
        unidadeMedidaService.save(unidadeMedida)

        then:
        unidadeMedida.id != null
    }
}
