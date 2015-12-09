package model;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Vitor on 05/11/2015.
 */
public class XMLWriter {

	private static final String ENVIAR_LOTE_RPS_ENVIO = "EnviarLoteRpsEnvio";
	private static final String LOTE_RPS = "LoteRps";
	private static final String NUMERO_LOTE = "NumeroLote";
	private static final String CNPJ = "Cnpj";
	private static final String INSCRICAO_MUNICIPAL = "InscricaoMunicipal";
	private static final String QUANTIDADE_RPS = "QuantidadeRps";
	private static final String LISTA_RPS = "ListaRps";
	private static final String RPS = "Rps";
	private static final String INF_RPS = "InfRps";
	private static final String IDENTIFICACAO_RPS = "IdentificacaoRps";
	private static final String NUMERO = "Numero";
	private static final String SERIE = "Serie";
	private static final String TIPO = "Tipo";
	private static final String DATA_EMISSAO = "DataEmissao";
	private static final String NATUREZAO_OPERACAO = "NaturezaOperacao";
	private static final String OPTANTE_SIMPLES_NACIONAL = "OptanteSimplesNacional";
	private static final String INCENTIVADOR_CULTURAL = "IncentivadorCultural";
	private static final String STATUS = "Status";
	private static final String SERVICO = "Servico";
	private static final String VALORES = "Valores";
	private static final String VALOR_SERVICOS = "ValorServicos";
	private static final String VALOR_DEDUCOES = "ValorDeducoes";
	private static final String VALOR_PIS = "ValorPis";
	private static final String VALOR_COFINS = "ValorCofins";
	private static final String VALOR_INSS = "ValorInss";
	private static final String VALOR_IR = "ValorIr";
	private static final String VALOR_CSLL = "ValorCsll";
	private static final String ISS_RETIDO = "IssRetido";
	private static final String VALOR_ISS = "ValorIss";
	private static final String OUTRAS_RETENCOES = "OutrasRetencoes";
	private static final String ALIQUOTA = "Aliquota";
	private static final String DESCONTO_INCONDICIONADO = "DescontoIncondicionado";
	private static final String DESCONTO_CONDICIONADO = "DescontoCondicionado";
	private static final String ITEM_LISTA_SERVICO = "ItemListaServico";
	private static final String CODIGO_TRIBUTACAO_MUNICIPIO = "CodigoTributacaoMunicipio";
	private static final String DISCRIMINACAO = "Discriminacao";
	private static final String CODIGO_MUNICIPIO = "CodigoMunicipio";
	private static final String PRESTADOR = "Prestador";
	private static final String TOMADOR = "Tomador";
	private static final String IDENTIFICACAO_TOMADOR = "IdentificacaoTomador";
	private static final String CPF_CNPJ = "CpfCnpj";
	private static final String CPF = "Cpf";
	private static final String RAZAO_SOCIAL = "RazaoSocial";
	private static final String ENDERECO = "Endereco";
	private static final String COMPLEMENTO = "Complemento";
	private static final String BAIRRO = "Bairro";
	private static final String UF = "Uf";
	private static final String CEP = "Cep";
	private static final String CONTATO = "Contato";
	private static final String EMAIL = "Email";

	private static final String OUTPUT_ENCODING = "UTF-8";

	private XMLWriter() {}

	public static void generateXML(RPSBulk rpsBulk, String outputFile) throws IOException {

		Element enviarLoteRpsEnvio = new Element(ENVIAR_LOTE_RPS_ENVIO);

		Element loteRps = new Element(LOTE_RPS);
		loteRps.addAttribute(new Attribute("Id", rpsBulk.getId()));

		Element numeroLote = new Element(NUMERO_LOTE);
		numeroLote.appendChild(rpsBulk.getNumeroLote());
		loteRps.appendChild(numeroLote);

		Element cnpj = new Element(CNPJ);
		cnpj.appendChild(rpsBulk.getCnpj());
		loteRps.appendChild(cnpj);

		Element inscricaoMunicipal = new Element(INSCRICAO_MUNICIPAL);
		inscricaoMunicipal.appendChild(rpsBulk.getInscricaoMunicipal());
		loteRps.appendChild(inscricaoMunicipal);

		Element quantidadeRps = new Element(QUANTIDADE_RPS);
		quantidadeRps.appendChild(Integer.toString(rpsBulk.getQuantidadeRps()));
		loteRps.appendChild(quantidadeRps);

		Element listaRps = new Element(LISTA_RPS);

		for (RPS rps : rpsBulk.getListaRps()) {
			Element rpsElement = new Element(RPS);

			Element infRps = new Element(INF_RPS);
			infRps.addAttribute(new Attribute("Id", rps.getId()));

			Element identificacaoRps = new Element(IDENTIFICACAO_RPS);

			Element numero = new Element(NUMERO);
			numero.appendChild(rps.getNumero());
			identificacaoRps.appendChild(numero);
			
			Element serie = new Element(SERIE);
			serie.appendChild(rps.getSerie());
			identificacaoRps.appendChild(serie);

			Element tipo = new Element(TIPO);
			tipo.appendChild(rps.getTipo());
			identificacaoRps.appendChild(tipo);

			infRps.appendChild(identificacaoRps);

			Element dataEmissao = new Element(DATA_EMISSAO);
			dataEmissao.appendChild(rps.getDataEmissao());
			infRps.appendChild(dataEmissao);

			Element naturezaOperacao = new Element(NATUREZAO_OPERACAO);
			naturezaOperacao.appendChild(rps.getNaturezaOperacao());
			infRps.appendChild(naturezaOperacao);

			Element optanteSimplesNacional = new Element(OPTANTE_SIMPLES_NACIONAL);
			optanteSimplesNacional.appendChild(rps.getOptanteSimplesNacional());
			infRps.appendChild(optanteSimplesNacional);

			Element incentivadorCultural = new Element(INCENTIVADOR_CULTURAL);
			incentivadorCultural.appendChild(rps.getIncentivadorCultural());
			infRps.appendChild(incentivadorCultural);

			Element status = new Element(STATUS);
			status.appendChild(rps.getStatus());
			infRps.appendChild(status);

			Element servico = new Element(SERVICO);

			Element valores = new Element(VALORES);

			Element valorServicos = new Element(VALOR_SERVICOS);
			valorServicos.appendChild(rps.getValorServicos());
			valores.appendChild(valorServicos);

			Element valorDeducoes = new Element(VALOR_DEDUCOES);
			valorDeducoes.appendChild(rps.getValorDeducoes());
			valores.appendChild(valorDeducoes);

			Element valorPis = new Element(VALOR_PIS);
			valorPis.appendChild(rps.getValorPis());
			valores.appendChild(valorPis);

			Element valorCofins = new Element(VALOR_COFINS);
			valorCofins.appendChild(rps.getValorCofins());
			valores.appendChild(valorCofins);

			Element valorInss = new Element(VALOR_INSS);
			valorInss.appendChild(rps.getValorInss());
			valores.appendChild(valorInss);

			Element valorIr = new Element(VALOR_IR);
			valorIr.appendChild(rps.getValorIr());
			valores.appendChild(valorIr);

			Element valorCsll = new Element(VALOR_CSLL);
			valorCsll.appendChild(rps.valorCsll);
			valores.appendChild(valorCsll);

			Element issRetido = new Element(ISS_RETIDO);
			issRetido.appendChild(rps.getIssRetido());
			valores.appendChild(issRetido);

			Element valorIss = new Element(VALOR_ISS);
			valorIss.appendChild(rps.getValorIss());
			valores.appendChild(valorIss);

			Element outrasRetencoes = new Element(OUTRAS_RETENCOES);
			outrasRetencoes.appendChild(rps.getOutrasRetencoes());
			valores.appendChild(outrasRetencoes);

			Element aliquota = new Element(ALIQUOTA);
			aliquota.appendChild(rps.getAliquota());
			valores.appendChild(aliquota);

			Element descontoIncondicionado = new Element(DESCONTO_INCONDICIONADO);
			descontoIncondicionado.appendChild(rps.getDescontoIncondicionado());
			valores.appendChild(descontoIncondicionado);

			Element descontoCondicionado = new Element(DESCONTO_CONDICIONADO);
			descontoCondicionado.appendChild(rps.getDescontoCondicionado());
			valores.appendChild(descontoCondicionado);

			servico.appendChild(valores);

			Element itemListaServico = new Element(ITEM_LISTA_SERVICO);
			itemListaServico.appendChild(rps.getItemListaServico());
			servico.appendChild(itemListaServico);

			Element codigoTributacaoMunicipio = new Element(CODIGO_TRIBUTACAO_MUNICIPIO);
			codigoTributacaoMunicipio.appendChild(rps.getCodigoTributacaoMunicipio());
			servico.appendChild(codigoTributacaoMunicipio);

			Element discriminacao = new Element(DISCRIMINACAO);
			discriminacao.appendChild(rps.getDiscriminacao());
			servico.appendChild(discriminacao);

			Element servicosCodigoMunicipio = new Element(CODIGO_MUNICIPIO);
			servicosCodigoMunicipio.appendChild(rps.getServicos_codigoMunicipio());
			servico.appendChild(servicosCodigoMunicipio);

			infRps.appendChild(servico);

			Element prestador = new Element(PRESTADOR);

			Element prestadorCnpj = new Element(CNPJ);
			prestadorCnpj.appendChild(rps.getPrestador_cnpj());
			prestador.appendChild(prestadorCnpj);

			Element prestadorInscricaoMunicipal = new Element(INSCRICAO_MUNICIPAL);
			prestadorInscricaoMunicipal.appendChild(rps.getInscricaoMunicipal());
			prestador.appendChild(prestadorInscricaoMunicipal);

			infRps.appendChild(prestador);

			Element tomador = new Element(TOMADOR);

			Element identificaoTomador = new Element(IDENTIFICACAO_TOMADOR);

			Element cpfCnpj = new Element(CPF_CNPJ);
			
			if(rps.getTomador_cpf() == null) {
				Element tomadorCnpj = new Element(CNPJ);
				tomadorCnpj.appendChild(rps.getTomador_cnpj());
				cpfCnpj.appendChild(tomadorCnpj);
			}
			else {
				Element tomadorCpf = new Element(CPF);
				tomadorCpf.appendChild(rps.getTomador_cpf());
				cpfCnpj.appendChild(tomadorCpf);
			}
			identificaoTomador.appendChild(cpfCnpj);
			
			tomador.appendChild(identificaoTomador);
			
			Element razaoSocial = new Element(RAZAO_SOCIAL);
			razaoSocial.appendChild(rps.getRazaoSocial());
			tomador.appendChild(razaoSocial);
			
			Element endereco = new Element(ENDERECO);

			Element enderecoEndereco = new Element(ENDERECO);
			enderecoEndereco.appendChild(rps.getEndereco());
			endereco.appendChild(enderecoEndereco);

			Element enderecoNumero = new Element(NUMERO);
			enderecoNumero.appendChild(rps.getEndereco_numero());
			endereco.appendChild(enderecoNumero);

			Element complemento = new Element(COMPLEMENTO);
			complemento.appendChild(rps.getComplemento());
			endereco.appendChild(complemento);

			Element bairro = new Element(BAIRRO);
			bairro.appendChild(rps.getBairro());
			endereco.appendChild(bairro);

			Element enderecoCodigoMunicipio = new Element(CODIGO_MUNICIPIO);
			enderecoCodigoMunicipio.appendChild(rps.getEndereco_codigoMunicipio());
			endereco.appendChild(enderecoCodigoMunicipio);

			Element uf = new Element(UF);
			uf.appendChild(rps.getUf());
			endereco.appendChild(uf);

			Element cep = new Element(CEP);
			cep.appendChild(rps.getCep());
			endereco.appendChild(cep);
			
			tomador.appendChild(endereco);

			Element contato = new Element(CONTATO);

			Element email = new Element(EMAIL);
			email.appendChild(rps.getEmail());
			contato.appendChild(email);

			tomador.appendChild(contato);

			infRps.appendChild(tomador);

			rpsElement.appendChild(infRps);
			listaRps.appendChild(rpsElement);
		}
		loteRps.appendChild(listaRps);
		enviarLoteRpsEnvio.appendChild(loteRps);

		Document doc = new Document(enviarLoteRpsEnvio);

		//Writes to XML console.
	/*	Serializer serializer = new Serializer(System.out, OUTPUT_ENCODING);
		serializer.setIndent(2);
		serializer.setMaxLength(0);
		serializer.write(doc);*/

		//Writes XML to file.
		FileOutputStream fos = new FileOutputStream(outputFile, false);
		Serializer serializer = new Serializer(fos, OUTPUT_ENCODING);
		serializer.setIndent(2);
		serializer.setMaxLength(0);
		serializer.write(doc);
		fos.close();
	}
}