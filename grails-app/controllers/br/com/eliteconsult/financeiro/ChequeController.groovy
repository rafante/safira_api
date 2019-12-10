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
class ChequeController {

    ChequeService chequeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond chequeService.list(params), model:[chequeCount: chequeService.count()]
    }

    def show(Long id) {
        respond chequeService.get(id)
    }

    @Transactional
    def save(Cheque cheque) {
        if (cheque == null) {
            render status: NOT_FOUND
            return
        }
        if (cheque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cheque.errors
            return
        }

        try {
            chequeService.save(cheque)
        } catch (ValidationException e) {
            respond cheque.errors
            return
        }

        respond cheque, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Cheque cheque) {
        if (cheque == null) {
            render status: NOT_FOUND
            return
        }
        if (cheque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cheque.errors
            return
        }

        try {
            chequeService.save(cheque)
        } catch (ValidationException e) {
            respond cheque.errors
            return
        }

        respond cheque, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        chequeService.delete(id)

        render status: NO_CONTENT
    }
}
