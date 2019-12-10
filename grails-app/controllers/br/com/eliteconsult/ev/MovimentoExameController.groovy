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
class MovimentoExameController {

    MovimentoExameService movimentoExameService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond movimentoExameService.list(params), model:[movimentoExameCount: movimentoExameService.count()]
    }

    def show(Long id) {
        respond movimentoExameService.get(id)
    }

    @Transactional
    def save(MovimentoExame movimentoExame) {
        if (movimentoExame == null) {
            render status: NOT_FOUND
            return
        }
        if (movimentoExame.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond movimentoExame.errors
            return
        }

        try {
            movimentoExameService.save(movimentoExame)
        } catch (ValidationException e) {
            respond movimentoExame.errors
            return
        }

        respond movimentoExame, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MovimentoExame movimentoExame) {
        if (movimentoExame == null) {
            render status: NOT_FOUND
            return
        }
        if (movimentoExame.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond movimentoExame.errors
            return
        }

        try {
            movimentoExameService.save(movimentoExame)
        } catch (ValidationException e) {
            respond movimentoExame.errors
            return
        }

        respond movimentoExame, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        movimentoExameService.delete(id)

        render status: NO_CONTENT
    }
}
