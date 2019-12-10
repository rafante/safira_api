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
class MovimentoFinanceiroController {

    MovimentoFinanceiroService movimentoFinanceiroService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond movimentoFinanceiroService.list(params), model:[movimentoFinanceiroCount: movimentoFinanceiroService.count()]
    }

    def show(Long id) {
        respond movimentoFinanceiroService.get(id)
    }

    @Transactional
    def save(MovimentoFinanceiro movimentoFinanceiro) {
        if (movimentoFinanceiro == null) {
            render status: NOT_FOUND
            return
        }
        if (movimentoFinanceiro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond movimentoFinanceiro.errors
            return
        }

        try {
            movimentoFinanceiroService.save(movimentoFinanceiro)
        } catch (ValidationException e) {
            respond movimentoFinanceiro.errors
            return
        }

        respond movimentoFinanceiro, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MovimentoFinanceiro movimentoFinanceiro) {
        if (movimentoFinanceiro == null) {
            render status: NOT_FOUND
            return
        }
        if (movimentoFinanceiro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond movimentoFinanceiro.errors
            return
        }

        try {
            movimentoFinanceiroService.save(movimentoFinanceiro)
        } catch (ValidationException e) {
            respond movimentoFinanceiro.errors
            return
        }

        respond movimentoFinanceiro, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        movimentoFinanceiroService.delete(id)

        render status: NO_CONTENT
    }
}
