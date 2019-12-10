package br.com.eliteconsult.financeiro

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class BalanceteController {

    BalanceteService balanceteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond balanceteService.list(params), model:[balanceteCount: balanceteService.count()]
    }

    def show(Long id) {
        respond balanceteService.get(id)
    }

    @Transactional
    def save(Balancete balancete) {
        if (balancete == null) {
            render status: NOT_FOUND
            return
        }
        if (balancete.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond balancete.errors
            return
        }

        try {
            balanceteService.save(balancete)
        } catch (ValidationException e) {
            respond balancete.errors
            return
        }

        respond balancete, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Balancete balancete) {
        if (balancete == null) {
            render status: NOT_FOUND
            return
        }
        if (balancete.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond balancete.errors
            return
        }

        try {
            balanceteService.save(balancete)
        } catch (ValidationException e) {
            respond balancete.errors
            return
        }

        respond balancete, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        balanceteService.delete(id)

        render status: NO_CONTENT
    }
}
