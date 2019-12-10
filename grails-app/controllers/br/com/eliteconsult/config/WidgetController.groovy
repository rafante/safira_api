package br.com.eliteconsult.config

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class WidgetController {

    WidgetService widgetService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond widgetService.list(params), model:[widgetCount: widgetService.count()]
    }

    def show(Long id) {
        respond widgetService.get(id)
    }

    @Transactional
    def save(Widget widget) {
        if (widget == null) {
            render status: NOT_FOUND
            return
        }
        if (widget.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond widget.errors
            return
        }

        try {
            widgetService.save(widget)
        } catch (ValidationException e) {
            respond widget.errors
            return
        }

        respond widget, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Widget widget) {
        if (widget == null) {
            render status: NOT_FOUND
            return
        }
        if (widget.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond widget.errors
            return
        }

        try {
            widgetService.save(widget)
        } catch (ValidationException e) {
            respond widget.errors
            return
        }

        respond widget, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        widgetService.delete(id)

        render status: NO_CONTENT
    }
}
