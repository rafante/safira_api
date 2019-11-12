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
class MunicipioController {

    MunicipioService municipioService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond municipioService.list(params), model:[municipioCount: municipioService.count()]
    }

    def show(Long id) {
        respond municipioService.get(id)
    }

    @Transactional
    def save(Municipio municipio) {
        if (municipio == null) {
            render status: NOT_FOUND
            return
        }
        if (municipio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond municipio.errors
            return
        }

        try {
            municipioService.save(municipio)
        } catch (ValidationException e) {
            respond municipio.errors
            return
        }

        respond municipio, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Municipio municipio) {
        if (municipio == null) {
            render status: NOT_FOUND
            return
        }
        if (municipio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond municipio.errors
            return
        }

        try {
            municipioService.save(municipio)
        } catch (ValidationException e) {
            respond municipio.errors
            return
        }

        respond municipio, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        municipioService.delete(id)

        render status: NO_CONTENT
    }
}
