package br.com.eliteconsult.financeiro

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PlanoContasServiceSpec extends Specification {

    PlanoContasService planoContasService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PlanoContas(...).save(flush: true, failOnError: true)
        //new PlanoContas(...).save(flush: true, failOnError: true)
        //PlanoContas planoContas = new PlanoContas(...).save(flush: true, failOnError: true)
        //new PlanoContas(...).save(flush: true, failOnError: true)
        //new PlanoContas(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //planoContas.id
    }

    void "test get"() {
        setupData()

        expect:
        planoContasService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PlanoContas> planoContasList = planoContasService.list(max: 2, offset: 2)

        then:
        planoContasList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        planoContasService.count() == 5
    }

    void "test delete"() {
        Long planoContasId = setupData()

        expect:
        planoContasService.count() == 5

        when:
        planoContasService.delete(planoContasId)
        sessionFactory.currentSession.flush()

        then:
        planoContasService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PlanoContas planoContas = new PlanoContas()
        planoContasService.save(planoContas)

        then:
        planoContas.id != null
    }
}
