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
class UsuarioPerfilController {

    UsuarioPerfilService usuarioPerfilService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond usuarioPerfilService.list(params), model:[usuarioPerfilCount: usuarioPerfilService.count()]
    }

    def show(Long id) {
        respond usuarioPerfilService.get(id)
    }

    @Transactional
    def save(UsuarioPerfil usuarioPerfil) {
        if (usuarioPerfil == null) {
            render status: NOT_FOUND
            return
        }
        if (usuarioPerfil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuarioPerfil.errors
            return
        }

        try {
            usuarioPerfilService.save(usuarioPerfil)
        } catch (ValidationException e) {
            respond usuarioPerfil.errors
            return
        }

        respond usuarioPerfil, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(UsuarioPerfil usuarioPerfil) {
        if (usuarioPerfil == null) {
            render status: NOT_FOUND
            return
        }
        if (usuarioPerfil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuarioPerfil.errors
            return
        }

        try {
            usuarioPerfilService.save(usuarioPerfil)
        } catch (ValidationException e) {
            respond usuarioPerfil.errors
            return
        }

        respond usuarioPerfil, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        usuarioPerfilService.delete(id)

        render status: NO_CONTENT
    }
}
