package br.com.eliteconsult.seguranca

class Perfil {

    String authority
    Boolean dinamico = true

    static mapping = {
        cache true
        dinamico defaultValue: true
    }

    String toString() {
        return authority
    }
}
