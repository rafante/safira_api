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
class PlanoContasController {

    PlanoContasService planoContasService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond planoContasService.list(params), model:[planoContasCount: planoContasService.count()]
    }

    def show(Long id) {
        respond planoContasService.get(id)
    }

    @Transactional
    def save(PlanoContas planoContas) {
        if (planoContas == null) {
            render status: NOT_FOUND
            return
        }
        if (planoContas.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond planoContas.errors
            return
        }

        try {
            planoContasService.save(planoContas)
        } catch (ValidationException e) {
            respond planoContas.errors
            return
        }

        respond planoContas, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PlanoContas planoContas) {
        if (planoContas == null) {
            render status: NOT_FOUND
            return
        }
        if (planoContas.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond planoContas.errors
            return
        }

        try {
            planoContasService.save(planoContas)
        } catch (ValidationException e) {
            respond planoContas.errors
            return
        }

        respond planoContas, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        planoContasService.delete(id)

        render status: NO_CONTENT
    }
}
