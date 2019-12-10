package br.com.eliteconsult.ev

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ItemExameController {

    ItemExameService itemExameService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond itemExameService.list(params), model:[itemExameCount: itemExameService.count()]
    }

    def show(Long id) {
        respond itemExameService.get(id)
    }

    @Transactional
    def save(ItemExame itemExame) {
        if (itemExame == null) {
            render status: NOT_FOUND
            return
        }
        if (itemExame.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemExame.errors
            return
        }

        try {
            itemExameService.save(itemExame)
        } catch (ValidationException e) {
            respond itemExame.errors
            return
        }

        respond itemExame, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ItemExame itemExame) {
        if (itemExame == null) {
            render status: NOT_FOUND
            return
        }
        if (itemExame.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemExame.errors
            return
        }

        try {
            itemExameService.save(itemExame)
        } catch (ValidationException e) {
            respond itemExame.errors
            return
        }

        respond itemExame, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        itemExameService.delete(id)

        render status: NO_CONTENT
    }
}
