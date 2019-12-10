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
class ParametrosEVController {

    ParametrosEVService parametrosEVService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond parametrosEVService.list(params), model:[parametrosEVCount: parametrosEVService.count()]
    }

    def show(Long id) {
        respond parametrosEVService.get(id)
    }

    @Transactional
    def save(ParametrosEV parametrosEV) {
        if (parametrosEV == null) {
            render status: NOT_FOUND
            return
        }
        if (parametrosEV.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parametrosEV.errors
            return
        }

        try {
            parametrosEVService.save(parametrosEV)
        } catch (ValidationException e) {
            respond parametrosEV.errors
            return
        }

        respond parametrosEV, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ParametrosEV parametrosEV) {
        if (parametrosEV == null) {
            render status: NOT_FOUND
            return
        }
        if (parametrosEV.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parametrosEV.errors
            return
        }

        try {
            parametrosEVService.save(parametrosEV)
        } catch (ValidationException e) {
            respond parametrosEV.errors
            return
        }

        respond parametrosEV, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        parametrosEVService.delete(id)

        render status: NO_CONTENT
    }
}
