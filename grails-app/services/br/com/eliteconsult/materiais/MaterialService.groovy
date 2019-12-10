package br.com.eliteconsult.materiais

import grails.gorm.services.Service
import  br.com.eliteconsult.materiais.Finalidade

@Service(Material)
interface MaterialService {

    Material get(Serializable id)

    List<Material> list(Map args)

    Long count()

    void delete(Serializable id)

    Material save(Material material)

}