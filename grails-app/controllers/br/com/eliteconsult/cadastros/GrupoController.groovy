package br.com.eliteconsult.cadastros

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class GrupoController {

    GrupoService grupoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond grupoService.list(params), model:[grupoCount: grupoService.count()]
    }

    def show(Long id) {
        respond grupoService.get(id)
    }

    @Transactional
    def save(Grupo grupo) {
        if (grupo == null) {
            render status: NOT_FOUND
            return
        }
        if (grupo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grupo.errors
            return
        }

        try {
            grupoService.save(grupo)
        } catch (ValidationException e) {
            respond grupo.errors
            return
        }

        respond grupo, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Grupo grupo) {
        if (grupo == null) {
            render status: NOT_FOUND
            return
        }
        if (grupo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grupo.errors
            return
        }

        try {
            grupoService.save(grupo)
        } catch (ValidationException e) {
            respond grupo.errors
            return
        }

        respond grupo, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        grupoService.delete(id)

        render status: NO_CONTENT
    }
}
