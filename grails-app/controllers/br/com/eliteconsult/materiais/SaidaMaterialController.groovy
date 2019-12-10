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
class SaidaMaterialController {

    SaidaMaterialService saidaMaterialService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond saidaMaterialService.list(params), model:[saidaMaterialCount: saidaMaterialService.count()]
    }

    def show(Long id) {
        respond saidaMaterialService.get(id)
    }

    @Transactional
    def save(SaidaMaterial saidaMaterial) {
        if (saidaMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (saidaMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond saidaMaterial.errors
            return
        }

        try {
            saidaMaterialService.save(saidaMaterial)
        } catch (ValidationException e) {
            respond saidaMaterial.errors
            return
        }

        respond saidaMaterial, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SaidaMaterial saidaMaterial) {
        if (saidaMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (saidaMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond saidaMaterial.errors
            return
        }

        try {
            saidaMaterialService.save(saidaMaterial)
        } catch (ValidationException e) {
            respond saidaMaterial.errors
            return
        }

        respond saidaMaterial, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        saidaMaterialService.delete(id)

        render status: NO_CONTENT
    }
}
