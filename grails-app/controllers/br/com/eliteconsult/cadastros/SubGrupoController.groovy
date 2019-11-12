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
class SubGrupoController {

    SubGrupoService subGrupoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond subGrupoService.list(params), model:[subGrupoCount: subGrupoService.count()]
    }

    def show(Long id) {
        respond subGrupoService.get(id)
    }

    @Transactional
    def save(SubGrupo subGrupo) {
        if (subGrupo == null) {
            render status: NOT_FOUND
            return
        }
        if (subGrupo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond subGrupo.errors
            return
        }

        try {
            subGrupoService.save(subGrupo)
        } catch (ValidationException e) {
            respond subGrupo.errors
            return
        }

        respond subGrupo, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SubGrupo subGrupo) {
        if (subGrupo == null) {
            render status: NOT_FOUND
            return
        }
        if (subGrupo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond subGrupo.errors
            return
        }

        try {
            subGrupoService.save(subGrupo)
        } catch (ValidationException e) {
            respond subGrupo.errors
            return
        }

        respond subGrupo, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        subGrupoService.delete(id)

        render status: NO_CONTENT
    }
}
