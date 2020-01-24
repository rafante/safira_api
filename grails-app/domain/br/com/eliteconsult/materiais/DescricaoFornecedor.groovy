package br.com.eliteconsult.materiais

import br.com.eliteconsult.cadastros.ParceiroNegocios

/*Classe de informações de descrições de acordo 
 *com o Fornecedor. Utlizada para ordens de compra 
 *e importação de entrada de materiais.*/

public class DescricaoFornecedor {

    String codigo
    String descricao

    static belongsTo = [
            material: Material,
            fornecedor: ParceiroNegocios]

    String toString() {
        return descricao
    }


}