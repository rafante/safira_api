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
class TabelaPrecoMaterialController {

    TabelaPrecoMaterialService tabelaPrecoMaterialService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tabelaPrecoMaterialService.list(params), model:[tabelaPrecoMaterialCount: tabelaPrecoMaterialService.count()]
    }

    def show(Long id) {
        respond tabelaPrecoMaterialService.get(id)
    }

    @Transactional
    def save(TabelaPrecoMaterial tabelaPrecoMaterial) {
        if (tabelaPrecoMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (tabelaPrecoMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tabelaPrecoMaterial.errors
            return
        }

        try {
            tabelaPrecoMaterialService.save(tabelaPrecoMaterial)
        } catch (ValidationException e) {
            respond tabelaPrecoMaterial.errors
            return
        }

        respond tabelaPrecoMaterial, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TabelaPrecoMaterial tabelaPrecoMaterial) {
        if (tabelaPrecoMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (tabelaPrecoMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tabelaPrecoMaterial.errors
            return
        }

        try {
            tabelaPrecoMaterialService.save(tabelaPrecoMaterial)
        } catch (ValidationException e) {
            respond tabelaPrecoMaterial.errors
            return
        }

        respond tabelaPrecoMaterial, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        tabelaPrecoMaterialService.delete(id)

        render status: NO_CONTENT
    }
}
