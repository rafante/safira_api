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
class ParametrosFinanceiroController {

    ParametrosFinanceiroService parametrosFinanceiroService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond parametrosFinanceiroService.list(params), model:[parametrosFinanceiroCount: parametrosFinanceiroService.count()]
    }

    def show(Long id) {
        respond parametrosFinanceiroService.get(id)
    }

    @Transactional
    def save(ParametrosFinanceiro parametrosFinanceiro) {
        if (parametrosFinanceiro == null) {
            render status: NOT_FOUND
            return
        }
        if (parametrosFinanceiro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parametrosFinanceiro.errors
            return
        }

        try {
            parametrosFinanceiroService.save(parametrosFinanceiro)
        } catch (ValidationException e) {
            respond parametrosFinanceiro.errors
            return
        }

        respond parametrosFinanceiro, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ParametrosFinanceiro parametrosFinanceiro) {
        if (parametrosFinanceiro == null) {
            render status: NOT_FOUND
            return
        }
        if (parametrosFinanceiro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parametrosFinanceiro.errors
            return
        }

        try {
            parametrosFinanceiroService.save(parametrosFinanceiro)
        } catch (ValidationException e) {
            respond parametrosFinanceiro.errors
            return
        }

        respond parametrosFinanceiro, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        parametrosFinanceiroService.delete(id)

        render status: NO_CONTENT
    }
}
