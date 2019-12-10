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
class LDMController {

    LDMService LDMService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond LDMService.list(params), model:[LDMCount: LDMService.count()]
    }

    def show(Long id) {
        respond LDMService.get(id)
    }

    @Transactional
    def save(LDM LDM) {
        if (LDM == null) {
            render status: NOT_FOUND
            return
        }
        if (LDM.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond LDM.errors
            return
        }

        try {
            LDMService.save(LDM)
        } catch (ValidationException e) {
            respond LDM.errors
            return
        }

        respond LDM, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(LDM LDM) {
        if (LDM == null) {
            render status: NOT_FOUND
            return
        }
        if (LDM.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond LDM.errors
            return
        }

        try {
            LDMService.save(LDM)
        } catch (ValidationException e) {
            respond LDM.errors
            return
        }

        respond LDM, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        LDMService.delete(id)

        render status: NO_CONTENT
    }
}
