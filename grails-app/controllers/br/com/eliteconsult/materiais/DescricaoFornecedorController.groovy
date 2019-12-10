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
class DescricaoFornecedorController {

    DescricaoFornecedorService descricaoFornecedorService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond descricaoFornecedorService.list(params), model:[descricaoFornecedorCount: descricaoFornecedorService.count()]
    }

    def show(Long id) {
        respond descricaoFornecedorService.get(id)
    }

    @Transactional
    def save(DescricaoFornecedor descricaoFornecedor) {
        if (descricaoFornecedor == null) {
            render status: NOT_FOUND
            return
        }
        if (descricaoFornecedor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond descricaoFornecedor.errors
            return
        }

        try {
            descricaoFornecedorService.save(descricaoFornecedor)
        } catch (ValidationException e) {
            respond descricaoFornecedor.errors
            return
        }

        respond descricaoFornecedor, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(DescricaoFornecedor descricaoFornecedor) {
        if (descricaoFornecedor == null) {
            render status: NOT_FOUND
            return
        }
        if (descricaoFornecedor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond descricaoFornecedor.errors
            return
        }

        try {
            descricaoFornecedorService.save(descricaoFornecedor)
        } catch (ValidationException e) {
            respond descricaoFornecedor.errors
            return
        }

        respond descricaoFornecedor, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        descricaoFornecedorService.delete(id)

        render status: NO_CONTENT
    }
}
