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
class MaterialController {

    MaterialService materialService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond materialService.list(params), model:[materialCount: materialService.count()]
    }

    def show(Long id) {
        respond materialService.get(id)
    }

    @Transactional
    def save(Material material) {
        if (material == null) {
            render status: NOT_FOUND
            return
        }
        if (material.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond material.errors
            return
        }

        try {
            materialService.save(material)
        } catch (ValidationException e) {
            respond material.errors
            return
        }

        respond material, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Material material) {
        if (material == null) {
            render status: NOT_FOUND
            return
        }
        if (material.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond material.errors
            return
        }

        try {
            materialService.save(material)
        } catch (ValidationException e) {
            respond material.errors
            return
        }

        respond material, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        materialService.delete(id)

        render status: NO_CONTENT
    }
}
