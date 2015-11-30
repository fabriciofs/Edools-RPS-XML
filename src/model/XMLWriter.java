package model;

import nu.xom.Attribute;
import nu.xom.Element;

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

		//TODO: Implement the rest of the XML generator.

		enviarLoteRpsEnvio.appendChild(loteRps);

		System.out.println(enviarLoteRpsEnvio.toXML());
		/*FileOutputStream fos = new FileOutputStream(path, false);
		Document doc = new Document(enviarLoteRpsEnvio);
		Serializer serializer = new Serializer(fos, "ISO-8859-1");
		serializer.setIndent(4);
		serializer.setMaxLength(64);
		serializer.write(doc);*/
	}
}
