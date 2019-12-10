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
class ItemMovimentoMaterialController {

    ItemMovimentoMaterialService itemMovimentoMaterialService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond itemMovimentoMaterialService.list(params), model:[itemMovimentoMaterialCount: itemMovimentoMaterialService.count()]
    }

    def show(Long id) {
        respond itemMovimentoMaterialService.get(id)
    }

    @Transactional
    def save(ItemMovimentoMaterial itemMovimentoMaterial) {
        if (itemMovimentoMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (itemMovimentoMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemMovimentoMaterial.errors
            return
        }

        try {
            itemMovimentoMaterialService.save(itemMovimentoMaterial)
        } catch (ValidationException e) {
            respond itemMovimentoMaterial.errors
            return
        }

        respond itemMovimentoMaterial, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ItemMovimentoMaterial itemMovimentoMaterial) {
        if (itemMovimentoMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (itemMovimentoMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemMovimentoMaterial.errors
            return
        }

        try {
            itemMovimentoMaterialService.save(itemMovimentoMaterial)
        } catch (ValidationException e) {
            respond itemMovimentoMaterial.errors
            return
        }

        respond itemMovimentoMaterial, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        itemMovimentoMaterialService.delete(id)

        render status: NO_CONTENT
    }
}
