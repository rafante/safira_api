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
class EntradaMaterialController {

    EntradaMaterialService entradaMaterialService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond entradaMaterialService.list(params), model:[entradaMaterialCount: entradaMaterialService.count()]
    }

    def show(Long id) {
        respond entradaMaterialService.get(id)
    }

    @Transactional
    def save(EntradaMaterial entradaMaterial) {
        if (entradaMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (entradaMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entradaMaterial.errors
            return
        }

        try {
            entradaMaterialService.save(entradaMaterial)
        } catch (ValidationException e) {
            respond entradaMaterial.errors
            return
        }

        respond entradaMaterial, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(EntradaMaterial entradaMaterial) {
        if (entradaMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (entradaMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entradaMaterial.errors
            return
        }

        try {
            entradaMaterialService.save(entradaMaterial)
        } catch (ValidationException e) {
            respond entradaMaterial.errors
            return
        }

        respond entradaMaterial, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        entradaMaterialService.delete(id)

        render status: NO_CONTENT
    }
}
