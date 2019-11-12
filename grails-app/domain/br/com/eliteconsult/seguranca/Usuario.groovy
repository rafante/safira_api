package br.com.eliteconsult.seguranca

import br.com.eliteconsult.cadastros.Empresa

public class Usuario {

    transient springSecurityService

    String username
    String password
    String nome
    String email
    String documento
    Empresa empresa
    boolean fornecedor
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    boolean available

    static hasMany = [usuarioPerfil: UsuarioPerfil]

    static defaultValueField = "username"
    static defaultFilterFields = "username;nome"

    static constraints = {
        username blank: false, unique: true
        password blank: false
        nome blank: false
        empresa blank: false
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Perfil> getAuthorities() {
        UsuarioPerfil.findAllByUsuario(this).collect { it.perfil } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }

    String toString() {
        return username + " - " + nome
    }

    String getAsString() {
        return this.toString()
    }
}