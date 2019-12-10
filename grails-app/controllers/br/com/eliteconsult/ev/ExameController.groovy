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
class ExameController {

    ExameService exameService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond exameService.list(params), model:[exameCount: exameService.count()]
    }

    def show(Long id) {
        respond exameService.get(id)
    }

    @Transactional
    def save(Exame exame) {
        if (exame == null) {
            render status: NOT_FOUND
            return
        }
        if (exame.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond exame.errors
            return
        }

        try {
            exameService.save(exame)
        } catch (ValidationException e) {
            respond exame.errors
            return
        }

        respond exame, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Exame exame) {
        if (exame == null) {
            render status: NOT_FOUND
            return
        }
        if (exame.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond exame.errors
            return
        }

        try {
            exameService.save(exame)
        } catch (ValidationException e) {
            respond exame.errors
            return
        }

        respond exame, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        exameService.delete(id)

        render status: NO_CONTENT
    }
}
