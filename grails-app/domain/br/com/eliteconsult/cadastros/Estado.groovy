package br.com.eliteconsult.cadastros

class Estado {

    String sigla //Que por sua vez tem a sigla MG, TO, AM, etc da hora
    String nome
    static belongsTo = [pais: Pais] //que por sua vez tem uma bilonga pro pa�s

    static defaultValueField = "nome"
    static defaultFilterFields = "nome"

    static constraints = {
        sigla(size: 2..2)
        nome()
        pais(cascade: 'save-update')
    }

    public static Estado GetOrCreateAndGet(sigla) {
        def estado = Estado.find { sigla == sigla }

        if (!estado) {
            estado = new Estado(sigla: sigla)
            estado.save(flush: true)
        }

        return estado
    }

    String toString() {
        return sigla ?: nome
    }
}
