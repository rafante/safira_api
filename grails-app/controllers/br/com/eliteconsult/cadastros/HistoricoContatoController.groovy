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
class HistoricoContatoController {

    HistoricoContatoService historicoContatoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond historicoContatoService.list(params), model:[historicoContatoCount: historicoContatoService.count()]
    }

    def show(Long id) {
        respond historicoContatoService.get(id)
    }

    @Transactional
    def save(HistoricoContato historicoContato) {
        if (historicoContato == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoContato.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoContato.errors
            return
        }

        try {
            historicoContatoService.save(historicoContato)
        } catch (ValidationException e) {
            respond historicoContato.errors
            return
        }

        respond historicoContato, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(HistoricoContato historicoContato) {
        if (historicoContato == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoContato.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoContato.errors
            return
        }

        try {
            historicoContatoService.save(historicoContato)
        } catch (ValidationException e) {
            respond historicoContato.errors
            return
        }

        respond historicoContato, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        historicoContatoService.delete(id)

        render status: NO_CONTENT
    }
}
