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
class MaterialLoteController {

    MaterialLoteService materialLoteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond materialLoteService.list(params), model:[materialLoteCount: materialLoteService.count()]
    }

    def show(Long id) {
        respond materialLoteService.get(id)
    }

    @Transactional
    def save(MaterialLote materialLote) {
        if (materialLote == null) {
            render status: NOT_FOUND
            return
        }
        if (materialLote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond materialLote.errors
            return
        }

        try {
            materialLoteService.save(materialLote)
        } catch (ValidationException e) {
            respond materialLote.errors
            return
        }

        respond materialLote, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MaterialLote materialLote) {
        if (materialLote == null) {
            render status: NOT_FOUND
            return
        }
        if (materialLote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond materialLote.errors
            return
        }

        try {
            materialLoteService.save(materialLote)
        } catch (ValidationException e) {
            respond materialLote.errors
            return
        }

        respond materialLote, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        materialLoteService.delete(id)

        render status: NO_CONTENT
    }
}
