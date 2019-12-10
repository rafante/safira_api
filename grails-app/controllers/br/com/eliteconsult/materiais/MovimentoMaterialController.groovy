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
class MovimentoMaterialController {

    MovimentoMaterialService movimentoMaterialService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond movimentoMaterialService.list(params), model:[movimentoMaterialCount: movimentoMaterialService.count()]
    }

    def show(Long id) {
        respond movimentoMaterialService.get(id)
    }

    @Transactional
    def save(MovimentoMaterial movimentoMaterial) {
        if (movimentoMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (movimentoMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond movimentoMaterial.errors
            return
        }

        try {
            movimentoMaterialService.save(movimentoMaterial)
        } catch (ValidationException e) {
            respond movimentoMaterial.errors
            return
        }

        respond movimentoMaterial, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MovimentoMaterial movimentoMaterial) {
        if (movimentoMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (movimentoMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond movimentoMaterial.errors
            return
        }

        try {
            movimentoMaterialService.save(movimentoMaterial)
        } catch (ValidationException e) {
            respond movimentoMaterial.errors
            return
        }

        respond movimentoMaterial, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        movimentoMaterialService.delete(id)

        render status: NO_CONTENT
    }
}
