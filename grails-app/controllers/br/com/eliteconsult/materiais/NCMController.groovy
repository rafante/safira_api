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
class NCMController {

    NCMService NCMService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond NCMService.list(params), model:[NCMCount: NCMService.count()]
    }

    def show(Long id) {
        respond NCMService.get(id)
    }

    @Transactional
    def save(NCM NCM) {
        if (NCM == null) {
            render status: NOT_FOUND
            return
        }
        if (NCM.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond NCM.errors
            return
        }

        try {
            NCMService.save(NCM)
        } catch (ValidationException e) {
            respond NCM.errors
            return
        }

        respond NCM, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(NCM NCM) {
        if (NCM == null) {
            render status: NOT_FOUND
            return
        }
        if (NCM.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond NCM.errors
            return
        }

        try {
            NCMService.save(NCM)
        } catch (ValidationException e) {
            respond NCM.errors
            return
        }

        respond NCM, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        NCMService.delete(id)

        render status: NO_CONTENT
    }
}
