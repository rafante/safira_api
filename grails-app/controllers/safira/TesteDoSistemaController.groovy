package safira

import base.Utils
import br.com.eliteconsult.cadastros.CentroCusto
import br.com.eliteconsult.cadastros.Empresa
import br.com.eliteconsult.cadastros.Endereco
import br.com.eliteconsult.cadastros.Estado
import br.com.eliteconsult.cadastros.Municipio
import br.com.eliteconsult.cadastros.Pais
import grails.gorm.transactions.Transactional
import grails.rest.*
import grails.converters.*

class TesteDoSistemaController {
	static responseFormats = ['json', 'xml']
	
    def index() {
        criarBancoTeste()
        respond('{ "teste":"funcionou" }')
    }

    @Transactional
    def criarBancoTeste(){
        Utils.criaBancoTeste()
    }
}
