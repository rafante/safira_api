package br.com.eliteconsult.materiais

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ItemSaidaController {

    ItemSaidaService itemSaidaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond itemSaidaService.list(params), model:[itemSaidaCount: itemSaidaService.count()]
    }

    def show(Long id) {
        respond itemSaidaService.get(id)
    }

    @Transactional
    def save(ItemSaida itemSaida) {
        if (itemSaida == null) {
            render status: NOT_FOUND
            return
        }
        if (itemSaida.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemSaida.errors
            return
        }

        try {
            itemSaidaService.save(itemSaida)
        } catch (ValidationException e) {
            respond itemSaida.errors
            return
        }

        respond itemSaida, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ItemSaida itemSaida) {
        if (itemSaida == null) {
            render status: NOT_FOUND
            return
        }
        if (itemSaida.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemSaida.errors
            return
        }

        try {
            itemSaidaService.save(itemSaida)
        } catch (ValidationException e) {
            respond itemSaida.errors
            return
        }

        respond itemSaida, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        itemSaidaService.delete(id)

        render status: NO_CONTENT
    }
}
