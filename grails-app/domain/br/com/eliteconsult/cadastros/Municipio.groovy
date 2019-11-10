package br.com.eliteconsult.cadastros

class Municipio {

    // TODO: ERP + SPED - Carregar tabela do IBGE
    // TODO: ERP + SPED - Adaptar processo do SPED

    String nome
    static belongsTo = [estado: Estado] //Tem uma bilonga pro Estado

    static defaultValueField = "nome"
    static defaultFilterFields = "nome"

    static constraints = {
        nome(size: 0..60, blank: false, nullable: false)
        estado(cascade: 'save-update', blank: false, nullable: false)
    }

    public static Municipio GetOrCreateAndGet(sigla_estado, nome) {
        def estado = Estado.GetOrCreateAndGet(sigla_estado)

        def municipio = Municipio.find { nome == nome && estado == estado }
        if (!municipio) {
            municipio = new Municipio(nome: nome, estado: estado)
            municipio.save(flush: true);
        }

        return municipio
    }

    static Municipio BuscaMunicipio(String nome, String uf) {
        String query = "from base.Municipio as m " +
                "where m.nome = ? and m.uf = ?"

        return Municipio.find(query, [nome.toUpperCase(), uf.toUpperCase()],
                [cache: true])
    }

    static mapping = {
        nome index: 'municipio_index0'
        uf index: 'municipio_index0'
    }

    String toString() {
        return id.toString().padLeft(4, '0') + '-' + nome + "/" + estado?.sigla
    }
}
