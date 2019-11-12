package br.com.eliteconsult.cadastros

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class EnderecoController {

    EnderecoService enderecoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond enderecoService.list(params), model:[enderecoCount: enderecoService.count()]
    }

    def show(Long id) {
        respond enderecoService.get(id)
    }

    @Transactional
    def save(Endereco endereco) {
        if (endereco == null) {
            render status: NOT_FOUND
            return
        }
        if (endereco.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond endereco.errors
            return
        }

        try {
            enderecoService.save(endereco)
        } catch (ValidationException e) {
            respond endereco.errors
            return
        }

        respond endereco, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Endereco endereco) {
        if (endereco == null) {
            render status: NOT_FOUND
            return
        }
        if (endereco.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond endereco.errors
            return
        }

        try {
            enderecoService.save(endereco)
        } catch (ValidationException e) {
            respond endereco.errors
            return
        }

        respond endereco, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        enderecoService.delete(id)

        render status: NO_CONTENT
    }
}
