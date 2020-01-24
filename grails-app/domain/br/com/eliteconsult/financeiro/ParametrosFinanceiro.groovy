package br.com.eliteconsult.financeiro

import br.com.eliteconsult.cadastros.Empresa

class ParametrosFinanceiro {

    Empresa empresa
    String nossoNumero
    String agencia
    String conta
    ContaCorrente contaCorrente

    static transients = ['agenciaSemDigito', 'contaSemDigito', 'agenciaDigito', 'contaDigito', 'nossoNumeroSemDigito', 'nossoNumeroDigito']

    String getAgenciaSemDigito() {
        return agencia ? agencia.subSequence(0, 4) : ""
    }

    String getAgenciaDigito() {
        return agencia ? agencia.charAt(agencia.length() - 1) : ""
    }

    String getContaSemDigito() {
        return conta ? conta.subSequence(0, conta.length() - 2) : ""
    }

    String getContaDigito() {
        return conta ? conta.charAt(conta.length() - 1) : ""
    }

    String getNossoNumeroSemDigito() {
        return nossoNumero ? nossoNumero.subSequence(0, nossoNumero.length() - 2) : ""
    }

    String getNossoNumeroDigito() {
        return nossoNumero ? nossoNumero.charAt(nossoNumero.length() - 1) : ""
    }
}
