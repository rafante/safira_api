package base

class Perfil {

    String authority
    Boolean dinamico = true

    static mapping = {
        cache true
        dinamico defaultValue: true
    }

    static constraints = {
        authority blank: false, unique: true
        dinamico blank: false, nullable: false
    }

    String toString() {
        return authority
    }
}
