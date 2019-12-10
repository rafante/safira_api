package br.com.eliteconsult.seguranca

import grails.gorm.services.Service

@Service(Perfil)
interface PerfilService {

    Perfil get(Serializable id)

    List<Perfil> list(Map args)

    Long count()

    void delete(Serializable id)

    Perfil save(Perfil perfil)

}