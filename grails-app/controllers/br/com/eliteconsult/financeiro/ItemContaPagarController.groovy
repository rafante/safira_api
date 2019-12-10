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
class ItemContaPagarController {

    ItemContaPagarService itemContaPagarService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond itemContaPagarService.list(params), model:[itemContaPagarCount: itemContaPagarService.count()]
    }

    def show(Long id) {
        respond itemContaPagarService.get(id)
    }

    @Transactional
    def save(ItemContaPagar itemContaPagar) {
        if (itemContaPagar == null) {
            render status: NOT_FOUND
            return
        }
        if (itemContaPagar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemContaPagar.errors
            return
        }

        try {
            itemContaPagarService.save(itemContaPagar)
        } catch (ValidationException e) {
            respond itemContaPagar.errors
            return
        }

        respond itemContaPagar, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ItemContaPagar itemContaPagar) {
        if (itemContaPagar == null) {
            render status: NOT_FOUND
            return
        }
        if (itemContaPagar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemContaPagar.errors
            return
        }

        try {
            itemContaPagarService.save(itemContaPagar)
        } catch (ValidationException e) {
            respond itemContaPagar.errors
            return
        }

        respond itemContaPagar, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        itemContaPagarService.delete(id)

        render status: NO_CONTENT
    }
}
