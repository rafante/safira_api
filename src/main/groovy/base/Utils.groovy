package base

import br.com.eliteconsult.cadastros.CentroCusto
import br.com.eliteconsult.cadastros.Empresa
import br.com.eliteconsult.cadastros.Endereco
import br.com.eliteconsult.cadastros.Estado
import br.com.eliteconsult.cadastros.FormaPagamento
import br.com.eliteconsult.cadastros.Grupo
import br.com.eliteconsult.cadastros.GrupoParceiroNegocios
import br.com.eliteconsult.cadastros.GrupoTributacao
import br.com.eliteconsult.cadastros.HistoricoContato
import br.com.eliteconsult.cadastros.ItemPrazoPagamento
import br.com.eliteconsult.cadastros.Municipio
import br.com.eliteconsult.cadastros.Pais
import br.com.eliteconsult.cadastros.ParceiroNegocios
import br.com.eliteconsult.cadastros.PrazoPagamento
import br.com.eliteconsult.cadastros.Setor
import br.com.eliteconsult.cadastros.SubGrupo
import br.com.eliteconsult.cadastros.SubGrupoParceiroNegocios
import br.com.eliteconsult.cadastros.TabelaPreco
import br.com.eliteconsult.cadastros.TipoComissao
import br.com.eliteconsult.cadastros.TipoDocumento
import br.com.eliteconsult.cadastros.TipoVendedor
import br.com.eliteconsult.financeiro.Natureza
import br.com.eliteconsult.financeiro.PlanoContas
import br.com.eliteconsult.materiais.UnidadeMedida

import java.text.DateFormat
import java.text.Normalizer
import java.text.SimpleDateFormat

class Utils {

     static void criaBancoTeste(){
        def brasil = new Pais(cod_pais: 'BR', nome: 'Brasil')
        assert brasil.save(flush: true, failOnError: true)

        def mg = new Estado(nome: 'Minas Gerais', sigla: 'MG', pais: brasil)
        assert mg.save(flush: true, failOnError: true)

        def beloHorizonte = new Municipio(nome: 'Belo Horizonte', estado: mg)
        assert beloHorizonte.save(flush: true, failOnError: true)

        def endereco_comercial1 = new Endereco(bairro: 'Santa Tereza', municipio: beloHorizonte, cep: '31010220', complemento: 'apto 202', contato: 'Bruno Rafante', email: 'rafante2@gmail.com', logradouro: 'Rua Mármore', numero: '209', telefone1: '31988898523', telefone2: '31993740147', celular1: '0000000000',celular2: '1111111111').save(flush: true, failOnError: true)
        assert endereco_comercial1.save(flush: true, failOnError: true)

        def endereco_entrega1 = new Endereco(bairro: 'Santa Tereza', municipio: beloHorizonte, cep: '31010220', complemento: 'apto 202', contato: 'Bruno Rafante', email: 'rafante2@gmail.com', logradouro: 'Rua Mármore', numero: '209', telefone1: '31988898523', telefone2: '31993740147', celular1: '0000000000',celular2: '1111111111').save(flush: true, failOnError: true)
        assert endereco_entrega1.save(flush: true, failOnError: true)

        def endereco_cobranca1 = new Endereco(bairro: 'Santa Tereza', municipio: beloHorizonte, cep: '31010220', complemento: 'apto 202', contato: 'Bruno Rafante', email: 'rafante2@gmail.com', logradouro: 'Rua Mármore', numero: '209', telefone1: '31988898523', telefone2: '31993740147', celular1: '0000000000',celular2: '1111111111').save(flush: true, failOnError: true)
        assert endereco_cobranca1.save(flush: true, failOnError: true)

         def endereco_comercial2 = new Endereco(bairro: 'Santa Tereza', municipio: beloHorizonte, cep: '31010220', complemento: 'apto 202', contato: 'Bruno Rafante', email: 'rafante2@gmail.com', logradouro: 'Rua Mármore', numero: '209', telefone1: '31988898523', telefone2: '31993740147', celular1: '0000000000',celular2: '1111111111').save(flush: true, failOnError: true)
         assert endereco_comercial2.save(flush: true, failOnError: true)

         def endereco_entrega2 = new Endereco(bairro: 'Santa Tereza', municipio: beloHorizonte, cep: '31010220', complemento: 'apto 202', contato: 'Bruno Rafante', email: 'rafante2@gmail.com', logradouro: 'Rua Mármore', numero: '209', telefone1: '31988898523', telefone2: '31993740147', celular1: '0000000000',celular2: '1111111111').save(flush: true, failOnError: true)
         assert endereco_entrega2.save(flush: true, failOnError: true)

         def endereco_cobranca2 = new Endereco(bairro: 'Santa Tereza', municipio: beloHorizonte, cep: '31010220', complemento: 'apto 202', contato: 'Bruno Rafante', email: 'rafante2@gmail.com', logradouro: 'Rua Mármore', numero: '209', telefone1: '31988898523', telefone2: '31993740147', celular1: '0000000000',celular2: '1111111111').save(flush: true, failOnError: true)
         assert endereco_cobranca2.save(flush: true, failOnError: true)

        def empresa = new Empresa(nome: 'Bruno Batista Rafante 07643635650', cnpj_cpf: '24594165000105', apelido: 'Rafante Informática', endereco_comercial: endereco_comercial2, endereco_entrega: endereco_entrega2, endereco_cobranca: endereco_cobranca2, cnae: '95.11-8-00', suframa: 'aaa', ind_ativ: 1, ind_nat_pj: '00', inscricao_estadual: '0034225920038', inscricao_municipal: '11437140011', serial_certificado_digital: 'aaa')
        assert  empresa.save(flush: true, failOnError: true)

        def centroCusto1 = new CentroCusto(descricao: 'Administração', codigo: '01')
        assert centroCusto1.save(flush: true, failOnError: true)

        def centroCusto2 = new CentroCusto(descricao: 'Almoxarifado', codigo: '02', filhos: [centroCusto1])
        assert centroCusto2.save(flush: true, failOnError: true)

        def centroCusto3 = new CentroCusto(descricao: 'Almoxarifado', codigo: '02', centroCusto: centroCusto2)
        assert centroCusto3.save(flush: true, failOnError: true)

        def pgtoDinheiro = new FormaPagamento(descricao: 'Dinheiro', controle_cheque: false)
        assert pgtoDinheiro.save(flush: true, failOnError: true)

        def pgtoCartao = new FormaPagamento(descricao: 'Cartão', controle_cheque: false)
        assert pgtoCartao.save(flush: true, failOnError: true)

        def pgtoCheque = new FormaPagamento(descricao: 'Cheque', controle_cheque: true)
        assert pgtoCheque.save(flush: true, failOnError: true)

        def pgtoTransferencia = new FormaPagamento(descricao: 'Transferência Bancária', controle_cheque: false)
        assert pgtoTransferencia.save(flush: true, failOnError: true)

         def grupoCA25 = new Grupo(descricao: 'CA-25')
         assert grupoCA25.save(flush: true, failOnError: true)

         def grupoCA50 = new Grupo(descricao: 'CA-50')
         assert grupoCA50.save(flush: true, failOnError: true)

         def grupoVergalhao = new Grupo(descricao: 'Vercalhão')
         assert grupoVergalhao.save(flush: true, failOnError: true)

         def grupoPNAco = new GrupoParceiroNegocios(descricao: 'AÇO')
         assert grupoPNAco.save(flush: true, failOnError: true)

         def grupoPNFerro = new GrupoParceiroNegocios(descricao: 'FERRO')
         assert grupoPNFerro.save(flush: true, failOnError: true)

         def grupoTributacao1 = new GrupoTributacao(descricao: 'Tributáveis no município')
         assert grupoTributacao1.save(flush: true, failOnError: true)

         def grupoTributacao2 = new GrupoTributacao(descricao: 'Tributáveis no estado')
         assert grupoTributacao2.save(flush: true, failOnError: true)

         def prazo30_60 = new PrazoPagamento(descricao: '30/60 dias', itemPrazoPagamento: [])
         prazo30_60.addToItemPrazoPagamento(new ItemPrazoPagamento(prazo: 30, percentual: 50))
         prazo30_60.addToItemPrazoPagamento(new ItemPrazoPagamento(prazo: 60, percentual: 50))

         assert prazo30_60.save(flush: true, failOnError: true)

         def prazo30_60_90 = new PrazoPagamento(descricao: '30/60/90 dias', itemPrazoPagamento: [])
         prazo30_60_90.addToItemPrazoPagamento(new ItemPrazoPagamento(prazo: 30, percentual: 33))
         prazo30_60_90.addToItemPrazoPagamento(new ItemPrazoPagamento(prazo: 60, percentual: 33))
         prazo30_60_90.addToItemPrazoPagamento(new ItemPrazoPagamento(prazo: 90, percentual: 34))

         assert prazo30_60_90.save(flush: true, failOnError: true)

         def subGrupoAcoLiso = new SubGrupo(descricao: 'Aço liso', grupo: grupoCA25)
         assert subGrupoAcoLiso.save(flush: true, failOnError: true)

         def subGrupo2 = new SubGrupo(descricao: 'Aço escovado', grupo: grupoCA50)
         assert subGrupo2.save(flush: true, failOnError: true)

         def subGrupo3 = new SubGrupo(descricao: 'Ferro fosco', grupo: grupoVergalhao)
         assert subGrupo3.save(flush: true, failOnError: true)

         def subGrpPNAcoLiso = new SubGrupoParceiroNegocios(descricao: 'Aço liso', grupo_parceiro: grupoPNAco)
         assert subGrpPNAcoLiso.save(flush: true, failOnError: true)

         def subGrpPNAcoEscovado = new SubGrupoParceiroNegocios(descricao: 'Aço escovado', grupo_parceiro: grupoPNAco)
         assert subGrpPNAcoEscovado.save(flush: true, failOnError: true)

         def subGrupo3Parceiro = new SubGrupoParceiroNegocios(descricao: 'Ferro fosco', grupo_parceiro: grupoPNAco)
         assert subGrupo3Parceiro.save(flush: true, failOnError: true)

         def setor1 = new Setor(descricao: 'Qualidade')
         assert setor1.save(flush: true, failOnError: true)

         def setor2 = new Setor(descricao: 'Testes')
         assert setor2.save(flush: true, failOnError: true)

         def tabelaPreco1 = new TabelaPreco(descricao: 'Normal', margem_minima: 90, margem_sugerida: 100)
         assert tabelaPreco1.save(flush: true, failOnError: true)

         def tabelaPreco2 = new TabelaPreco(descricao: 'Black Friday', margem_minima: 30, margem_sugerida: 40)
         assert tabelaPreco2.save(flush: true, failOnError: true)

         def tabelaPreco3 = new TabelaPreco(descricao: 'Preços Altos', margem_minima: 95, margem_sugerida: 110)
         assert tabelaPreco3.save(flush: true, failOnError: true)

         def notaFiscal = new TipoDocumento(descricao: 'Nota Fiscal')
         notaFiscal.save(flush: true, failOnError: true)

         def fatura = new TipoDocumento(descricao: 'Fatura')
         fatura.save(flush: true, failOnError: true)

         def enrico = new ParceiroNegocios(
                 apelido: 'Enrico e Luiza Consultoria',
                 nome: 'Enrico e Luiza Consultoria Financeira ME',
                 percentual_comissao: 0,
                 cnpj_cpf: '30385854000102',
                 inscricao_municipal: '11231412',
                 inscricao_estadual: '191572546243',
                 cliente: false,
                 fornecedor: true,
                 representanteVenda: true,
                 transportadora: true,
                 tipo_comissao: TipoComissao.SOB_VENDA,
                 tipo_vendedor: TipoVendedor.EXTERNO,
                 endereco_cobranca: endereco_cobranca1,
                 endereco_entrega: endereco_entrega1,
                 endereco_comercial: endereco_comercial1,
                 tabela_preco: tabelaPreco3,
                 grupo_parceiro: grupoPNAco,
                 sub_grupo_parceiro: subGrpPNAcoEscovado,
                 suframa: '1234567890',
                 credito: 10.50,
                 prazo_pagamento_default: prazo30_60,
         )
         enrico.addToHistoricoContato(new HistoricoContato(contato: 'Enrico', historico: 'Add'))
         enrico.save(flush: true, failOnError: true)

         def severino = new ParceiroNegocios(
                 apelido: 'Severino e Elias Transportes Ltda',
                 nome: 'Severino e Elias',
                 percentual_comissao: 1,
                 cnpj_cpf: '31394630000111',
//                 inscricao_municipal: '11233412',
                 inscricao_estadual: '170948714731',
                 cliente: true,
                 fornecedor: false,
                 representanteVenda: false,
                 transportadora: true,
                 tipo_comissao: TipoComissao.SOB_RECEBIMENTO,
                 tipo_vendedor: TipoVendedor.INTERNO,
                 endereco_cobranca: endereco_cobranca2,
                 endereco_entrega: endereco_entrega2,
                 endereco_comercial: endereco_comercial2,
                 tabela_preco: tabelaPreco1,
                 sub_grupo: grupoPNFerro,
//                 sub_grupo_parceiro: ,
                 suframa: '1234567890',
                 credito: 10.50,
                 prazo_pagamento_default: prazo30_60,
         )
         severino.addToHistoricoContato(new HistoricoContato(contato: 'Severino', historico: 'Add'))
         severino.addToHistoricoContato(new HistoricoContato(contato: 'Elias', historico: 'RM'))
         severino.save(flush: true, failOnError: true)

         def unidade = new UnidadeMedida(descricao: 'Unidade', unidade: 'UN')
         unidade.save(flush: true, failOnError: true)

         def kilo = new UnidadeMedida(descricao: 'Kilo', unidade: 'KG')
         kilo.save(flush: true, failOnError: true)

         def metro = new UnidadeMedida(descricao: 'Metro', unidade: 'MT')
         metro.save(flush: true, failOnError: true)

         def planoCaixaGeral = new PlanoContas(descricao: 'Caixa Geral', codigo: '1-01-001', natureza: Natureza.DESPESA)
         planoCaixaGeral.save(flush: true, failOnError: true)

         def planoBancos = new PlanoContas(descricao: 'Bancos', codigo: '1-01-002', natureza: Natureza.DESPESA)
         planoBancos.save(flush: true, failOnError: true)

         def planoAplicacoesFinanceiras = new PlanoContas(descricao: 'Aplicações Financeiras', codigo: '1-01-003', natureza: Natureza.DESPESA)
         planoAplicacoesFinanceiras.save(flush: true, failOnError: true)
    }

    static String GetOnlyNumerics(String str) {

        if (str == null) {
            return null
        }

        StringBuffer strBuff = new StringBuffer()
        char c;

        for (Integer i = 0; i < str.length(); i++) {
            c = str.charAt(i)

            if (Character.isDigit(c)) {
                strBuff.append(c)
            }
        }
        return strBuff.toString()
    }

    static String GetOnlyNumericsAndLetters(String str) {

        if (str == null) {
            return null
        }

        StringBuffer strBuff = new StringBuffer()
        char c;

        for (Integer i = 0; i < str.length(); i++) {
            c = str.charAt(i)

            if (Character.isDigit(c) || Character.isLetter(c)) {
                strBuff.append(c)
            }
        }
        return strBuff.toString()
    }

    static String LeftZero(String number, Integer i) {
        return LeftZero(ToInteger(number), i)
    }

    static String LeftZero(Integer number, Integer i) {
        return String.format('%0' + i.toString() + 'd', number)
    }

    static String Substring(String str, Integer i) {
        if (str == null) return

        if (str.length() <= i)
            return str
        else
            return str.substring(0, i)
    }

    static ToAny(type, obj) {
        switch (type) {
            case Integer:
                return ToInteger(obj)
            case Long:
                return ToLong(obj)
            case Double:
                return ToDouble(obj)
            case BigDecimal:
                return ToBigDecimal(obj)
            case Date:
                return ToDate(obj)
            case Boolean:
                return ToBoolean(obj)

            default:
                return ToString(obj, "")
        }
    }

    static Integer ToInteger(Object obj) {
        ToInteger(obj, 0)
    }

    static Integer ToInteger(Object obj, Integer defaultValue) {
        try {
            return obj.toInteger()
        } catch (e) {
            return defaultValue
        }
    }

    static Long ToLong(Object obj) {
        ToLong(obj, 0)
    }

    static Long ToLong(Object obj, Integer defaultValue) {
        try {
            return obj.toLong()
        } catch (e) {
            return defaultValue
        }
    }

    static String ToString(Object obj, String defaultValue) {
        if (obj && obj.toString()) return obj.toString()
        else
            return defaultValue
    }

    static Double ToDouble(Object obj) {
        try {
            return obj.toDouble()
        } catch (e) {
            try {
                obj = obj?.toString().replace(".", "").replace(",", ".")

                return obj.toDouble()
            }
            catch (e1) {
                return 0
            }
        }
    }

    static BigDecimal ToBigDecimal(Object obj) {
        try {
            return obj.toBigDecimal()
        } catch (e) {
            try {
                obj = obj?.toString().replace(".", "").replace(",", ".")

                return obj.toBigDecimal()
            }
            catch (e1) {
                return 0
            }
        }
    }

    static Date ToDate(Object obj) {
        if (!obj) return new Date()
        if (obj == "") return null

        if (obj in Date) return obj

        try {
            return Date.parse('dd/MM/yyyy', obj.toString())
        }
        catch (e1) {
            try {
                return Date.parse('dd.MM.yy', obj.toString())
            }
            catch (e2) {
                try {
                    return Date.parse('dd.MM.yyyy', obj.toString())
                }
                catch (e3) {
                    try {
                        return Date.parse('yyyy-MM-dd', obj.toString())
                    }
                    catch (e4) {
                        try {
                            return Date.parse('yyyy-MM-dd', obj.toString())
                        } catch (e5) {
                            try {
                                obj = obj.toString()
                                        .replace("JAN", "01")
                                        .replace("FEV", "02")
                                        .replace("MAR", "03")
                                        .replace("ABR", "04")
                                        .replace("MAI", "05")
                                        .replace("JUN", "06")
                                        .replace("JUL", "07")
                                        .replace("AGO", "08")
                                        .replace("SET", "09")
                                        .replace("OUT", "10")
                                        .replace("NOV", "11")
                                        .replace("DEZ", "12")

                                return Date.parse('dd-MM-yyyy', obj.toString())
                            } catch (e6) {
                                DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                                Date date = (Date) formatter.parse(obj.toString());
                                return date
                            }
                        }
                    }
                }
            }
        }
    }

    static Boolean ToBoolean(obj) {
        return (obj && (obj != "false"))
    }

    static boolean ValidaCpf(String strCpf) {
        if (!strCpf.substring(0, 1).equals("")) {
            try {
                boolean validado = true;
                int d1, d2;
                int digito1, digito2, resto;
                int digitoCPF;
                String nDigResult;
                strCpf = strCpf.replace('.', ' ');
                strCpf = strCpf.replace('-', ' ');
                strCpf = strCpf.replaceAll(" ", "");
                d1 = d2 = 0;
                digito1 = digito2 = resto = 0;

                for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
                    digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

                    //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.  
                    d1 = d1 + (11 - nCount) * digitoCPF;

                    //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.  
                    d2 = d2 + (12 - nCount) * digitoCPF;
                };

                //Primeiro resto da divisão por 11.  
                resto = (d1 % 11);

                //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
                if (resto < 2)
                    digito1 = 0;
                else
                    digito1 = 11 - resto;

                d2 += 2 * digito1;

                //Segundo resto da divisão por 11.  
                resto = (d2 % 11);

                //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
                if (resto < 2)
                    digito2 = 0;
                else
                    digito2 = 11 - resto;

                //Digito verificador do CPF que está sendo validado.  
                String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

                //Concatenando o primeiro resto com o segundo.  
                nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

                //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.  
                return nDigVerific.equals(nDigResult);
            } catch (Exception e) {
                System.err.println("Erro !" + e);
                return false;
            }
        } else return false;
    }

    static boolean ValidaCnpj(String str_cnpj) {
        if (!str_cnpj.substring(0, 1).equals("")) {
            try {
                str_cnpj = str_cnpj.replace('.', ' ');
                str_cnpj = str_cnpj.replace('/', ' ');
                str_cnpj = str_cnpj.replace('-', ' ');
                str_cnpj = str_cnpj.replaceAll(" ", "");
                int soma = 0, aux, dig;
                String cnpj_calc = str_cnpj.substring(0, 12);

                if (str_cnpj.length() != 14)
                    return false;
                char[] chr_cnpj = str_cnpj.toCharArray();
                /* Primeira parte */
                for (int i = 0; i < 4; i++)
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                        soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                for (int i = 0; i < 8; i++)
                    if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
                        soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ?
                        "0" : Integer.toString(dig);
                /* Segunda parte */
                soma = 0;
                for (int i = 0; i < 5; i++)
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                        soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                for (int i = 0; i < 8; i++)
                    if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
                        soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ?
                        "0" : Integer.toString(dig);
                return str_cnpj.equals(cnpj_calc);
            } catch (Exception e) {
                System.err.println("Erro !" + e);
                return false;
            }
        } else return false;

    }

    public static FormataValorSPED(valor) {
        return valor
    }

//    public static ByteArrayOutputStream ExportReportPdf(JasperPrint jp) throws JRException, FileNotFoundException {
//        JRPdfExporter exporter = new JRPdfExporter();
//
//        ByteArrayOutputStream fos = new ByteArrayOutputStream();
//
//        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);
//        exporter.exportReport();
//
//        return fos
//    }

//    public static JasperReportOutput(response, JasperPrint jp, params) {
//        def outputStream
//        switch (params.extension.toUpperCase()) {
//            case 'PDF':
//                response.contentType = "application/pdf"
//                outputStream = Utils.ExportReportPdf(jp).toByteArray()
//                break
//
//            case 'XLS':
//                response.contentType = "application/xls"
//                response.setHeader("Content-Disposition", "attachment;filename=\"" + params["action"] + ".xls" + "\"")
//                outputStream = Utils.ExportReportXls(jp).toByteArray()
//                break
//        }
//
//        response.outputStream << outputStream
//        response.outputStream.flush()
//        response.outputStream.close()
//
//        return outputStream
//    }

//    public static ByteArrayOutputStream ExportReportXls(JasperPrint jp) throws JRException, FileNotFoundException {
//        JRXlsExporter exporter = new JRXlsExporter();
//
//        ByteArrayOutputStream fos = new ByteArrayOutputStream();
//
//        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);
//        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE)
//        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE)
//        exporter.exportReport();
//
//        return fos
//    }

    public static GetProperty(object, String property) {
        try {
            property?.tokenize('.').inject object, { obj, prop ->
                obj ? obj[prop] : null
            }
        } catch (e) {
            return null
        }
    }

//    public static GrailsDomainClass GetDomainClass(GrailsApplication grailsApplication, domain) {
//        domain = domain
//        def domainClass = grailsApplication.getDomainClass(domain)
//
//        return domainClass
//    }

    public static String RemoveAccents(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
    }

    public static Date GetInicioMes(mes, ano) {
        Calendar c = GregorianCalendar.instance
        c.set(ano, mes - 1, 1)
        return c.getTime()
    }

    public static String lat1ToUtf8(String lat1){
        return new String(lat1.getBytes("ISO-8859-1"), "UTF-8")
    }

    public static Date GetFimMes(mes, ano) {
        Calendar c = GregorianCalendar.instance
        c.set(ano, mes - 1, c.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))
        return c.getTime()
    }
}