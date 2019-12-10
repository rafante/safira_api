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
class ItemEntradaController {

    ItemEntradaService itemEntradaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond itemEntradaService.list(params), model:[itemEntradaCount: itemEntradaService.count()]
    }

    def show(Long id) {
        respond itemEntradaService.get(id)
    }

    @Transactional
    def save(ItemEntrada itemEntrada) {
        if (itemEntrada == null) {
            render status: NOT_FOUND
            return
        }
        if (itemEntrada.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemEntrada.errors
            return
        }

        try {
            itemEntradaService.save(itemEntrada)
        } catch (ValidationException e) {
            respond itemEntrada.errors
            return
        }

        respond itemEntrada, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ItemEntrada itemEntrada) {
        if (itemEntrada == null) {
            render status: NOT_FOUND
            return
        }
        if (itemEntrada.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemEntrada.errors
            return
        }

        try {
            itemEntradaService.save(itemEntrada)
        } catch (ValidationException e) {
            respond itemEntrada.errors
            return
        }

        respond itemEntrada, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        itemEntradaService.delete(id)

        render status: NO_CONTENT
    }
}
