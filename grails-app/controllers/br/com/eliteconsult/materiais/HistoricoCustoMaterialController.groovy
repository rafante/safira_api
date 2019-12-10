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
class HistoricoCustoMaterialController {

    HistoricoCustoMaterialService historicoCustoMaterialService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond historicoCustoMaterialService.list(params), model:[historicoCustoMaterialCount: historicoCustoMaterialService.count()]
    }

    def show(Long id) {
        respond historicoCustoMaterialService.get(id)
    }

    @Transactional
    def save(HistoricoCustoMaterial historicoCustoMaterial) {
        if (historicoCustoMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoCustoMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoCustoMaterial.errors
            return
        }

        try {
            historicoCustoMaterialService.save(historicoCustoMaterial)
        } catch (ValidationException e) {
            respond historicoCustoMaterial.errors
            return
        }

        respond historicoCustoMaterial, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(HistoricoCustoMaterial historicoCustoMaterial) {
        if (historicoCustoMaterial == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoCustoMaterial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoCustoMaterial.errors
            return
        }

        try {
            historicoCustoMaterialService.save(historicoCustoMaterial)
        } catch (ValidationException e) {
            respond historicoCustoMaterial.errors
            return
        }

        respond historicoCustoMaterial, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        historicoCustoMaterialService.delete(id)

        render status: NO_CONTENT
    }
}
