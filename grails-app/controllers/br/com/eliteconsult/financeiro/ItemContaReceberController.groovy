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
class ItemContaReceberController {

    ItemContaReceberService itemContaReceberService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond itemContaReceberService.list(params), model:[itemContaReceberCount: itemContaReceberService.count()]
    }

    def show(Long id) {
        respond itemContaReceberService.get(id)
    }

    @Transactional
    def save(ItemContaReceber itemContaReceber) {
        if (itemContaReceber == null) {
            render status: NOT_FOUND
            return
        }
        if (itemContaReceber.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemContaReceber.errors
            return
        }

        try {
            itemContaReceberService.save(itemContaReceber)
        } catch (ValidationException e) {
            respond itemContaReceber.errors
            return
        }

        respond itemContaReceber, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ItemContaReceber itemContaReceber) {
        if (itemContaReceber == null) {
            render status: NOT_FOUND
            return
        }
        if (itemContaReceber.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemContaReceber.errors
            return
        }

        try {
            itemContaReceberService.save(itemContaReceber)
        } catch (ValidationException e) {
            respond itemContaReceber.errors
            return
        }

        respond itemContaReceber, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        itemContaReceberService.delete(id)

        render status: NO_CONTENT
    }
}
