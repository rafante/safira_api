package br.com.eliteconsult.seguranca

import grails.gorm.services.Service

@Service(UsuarioPerfil)
interface UsuarioPerfilService {

    UsuarioPerfil get(Serializable id)

    List<UsuarioPerfil> list(Map args)

    Long count()

    void delete(Serializable id)

    UsuarioPerfil save(UsuarioPerfil usuarioPerfil)

}