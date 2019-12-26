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
class BancoController {

    BancoService bancoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bancoService.list(params), model:[bancoCount: bancoService.count()]
    }

    def show(Long id) {
        respond bancoService.get(id)
    }

    def teste(){
        respond {}
    }

    @Transactional
    def save(Banco banco) {
        if (banco == null) {
            render status: NOT_FOUND
            return
        }
        if (banco.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond banco.errors
            return
        }

        try {
            bancoService.save(banco)
        } catch (ValidationException e) {
            respond banco.errors
            return
        }

        respond banco, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Banco banco) {
        if (banco == null) {
            render status: NOT_FOUND
            return
        }
        if (banco.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond banco.errors
            return
        }

        try {
            bancoService.save(banco)
        } catch (ValidationException e) {
            respond banco.errors
            return
        }

        respond banco, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        bancoService.delete(id)

        render status: NO_CONTENT
    }
}
