package model;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

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


	private XMLWriter() {}

	public static void generateXML(RPSBulk rpsBulk, String path) throws IOException {

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
			
			//TODO: Implement Servico.

			infRps.appendChild(servico);

			//TODO: Implement the rest of the XML generator.

			rpsElement.appendChild(infRps);
			listaRps.appendChild(rpsElement);
		}
		loteRps.appendChild(listaRps);
		enviarLoteRpsEnvio.appendChild(loteRps);

		Document doc = new Document(enviarLoteRpsEnvio);
		Serializer serializer = new Serializer(System.out, "ISO-8859-1");
		serializer.setIndent(4);
		serializer.setMaxLength(64);
		serializer.write(doc);

		/*FileOutputStream fos = new FileOutputStream(path, false);
		Document doc = new Document(enviarLoteRpsEnvio);
		Serializer serializer = new Serializer(fos, "ISO-8859-1");
		serializer.setIndent(4);
		serializer.setMaxLength(64);
		serializer.write(doc);*/
	}
}
