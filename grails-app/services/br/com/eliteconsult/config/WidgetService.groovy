package br.com.eliteconsult.config

import grails.gorm.services.Service

@Service(Widget)
interface WidgetService {

    Widget get(Serializable id)

    List<Widget> list(Map args)

    Long count()

    void delete(Serializable id)

    Widget save(Widget widget)

}