package br.com.eliteconsult.seguranca

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PerfilController {

    PerfilService perfilService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond perfilService.list(params), model:[perfilCount: perfilService.count()]
    }

    def show(Long id) {
        respond perfilService.get(id)
    }

    @Transactional
    def save(Perfil perfil) {
        if (perfil == null) {
            render status: NOT_FOUND
            return
        }
        if (perfil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond perfil.errors
            return
        }

        try {
            perfilService.save(perfil)
        } catch (ValidationException e) {
            respond perfil.errors
            return
        }

        respond perfil, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Perfil perfil) {
        if (perfil == null) {
            render status: NOT_FOUND
            return
        }
        if (perfil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond perfil.errors
            return
        }

        try {
            perfilService.save(perfil)
        } catch (ValidationException e) {
            respond perfil.errors
            return
        }

        respond perfil, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        perfilService.delete(id)

        render status: NO_CONTENT
    }
}
